"""Upload the exact audited GitHub release binary; never upload an unbuilt candidate."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import re
import sys
import tomllib
import urllib.error
import urllib.request
import uuid
import zipfile

PROJECT_ID = 1734096
API = 'https://minecraft.curseforge.com/api'


def candidate_type(version):
    if re.fullmatch(r'0\.0\.\d+a(?:\.R\d+)?(?:-dev\.\d+)?|0\.1a(?:\.R\d+)?', version):
        return 'alpha'
    if re.fullmatch(r'0\.1b(?:\.R\d+)?|\d+\.\d+\.\d+-(?:beta|rc)(?:[.-]\d+)?', version):
        return 'beta'
    if re.fullmatch(r'\d+\.\d+\.\d+', version):
        return 'release'
    raise ValueError('Unknown candidate naming; declare a supported version before publishing')


def metadata(jar, audit, changelog, version):
    evidence = json.loads(audit.read_text())
    digest = hashlib.sha256(jar.read_bytes()).hexdigest()
    if evidence.get('version') != version or evidence.get('jar_sha256') != digest or not evidence.get('native_build_and_current_source_match'):
        raise ValueError('Release binary does not match its passing audit')
    if not evidence.get('gametests') or min(evidence['gametests'].values()) <= 0:
        raise ValueError('Missing runtime validation evidence')
    with zipfile.ZipFile(jar) as archive:
        mod = tomllib.loads(archive.read('META-INF/neoforge.mods.toml').decode())
    calculator = next(m for m in mod['mods'] if m['modId'] == 'foundations_calculator')
    if calculator['version'] != version:
        raise ValueError('JAR version differs from release tag')
    mc = next(d for d in mod['dependencies']['foundations_calculator'] if d['modId'] == 'minecraft')['versionRange']
    if mc != '[1.21.1]':
        raise ValueError('New platform needs its own verified release metadata and Java/version mapping')
    return {
        'changelog': changelog.read_text(), 'changelogType': 'markdown',
        'displayName': f'Foundations Calculator — 1.21.1 — {version}',
        'gameVersionNames': ['1.21.1', 'NeoForge', 'Java 21', 'Client', 'Server'],
        'releaseType': candidate_type(version), 'isMarkedForManualRelease': False,
    }, digest


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--version', required=True)
    parser.add_argument('--directory', type=Path, default=Path('release-assets'))
    parser.add_argument('--dry-run', action='store_true')
    args = parser.parse_args()
    p = args.directory
    jar = p / f'FoundationsCalculator-{args.version}.jar'
    data, digest = metadata(jar, p / 'release_checks.json', p / 'release-notes.md', args.version)
    if args.dry_run:
        print(json.dumps(data, indent=2))
        return
    token = os.environ.get('CURSEFORGE_API_TOKEN', '')
    if not token:
        raise ValueError('CURSEFORGE_API_TOKEN repository secret is missing')
    # Verify all labels exist before a mutation; do not silently drop loader/platform labels.
    req = urllib.request.Request(API + '/game/versions', headers={'X-Api-Token': token})
    with urllib.request.urlopen(req, timeout=60) as response:
        available = json.load(response)
    req = urllib.request.Request(API + '/game/version-types', headers={'X-Api-Token': token})
    with urllib.request.urlopen(req, timeout=60) as response:
        types = {v['id']: v for v in json.load(response)}
    chosen = []
    for name in data.pop('gameVersionNames'):
        candidates = []
        for v in available:
            t = types.get(v['gameVersionTypeID'], {})
            slug = t.get('slug', '').lower()
            normalized = re.sub(r'[^a-z0-9]', '', slug + t.get('name', '').lower())
            expected = ('minecraft' in normalized and '121' in normalized) if name == '1.21.1' else (
                slug == 'java' if name == 'Java 21' else slug == 'modloader' if name == 'NeoForge' else slug == 'environment')
            if v['name'] == name and expected:
                candidates.append(v)
        if len(candidates) != 1:
            matches = [(v, types.get(v['gameVersionTypeID'], {})) for v in available if v['name'] == name]
            raise ValueError(f'Expected one Minecraft-qualified version for {name}; found {len(candidates)}; API matches: {matches}')
        chosen.append(candidates[0]['id'])
    data['gameVersions'] = chosen
    print('Resolved Minecraft-qualified CurseForge version IDs:', chosen)
    boundary = 'foundations-' + uuid.uuid4().hex
    body = (f'--{boundary}\r\nContent-Disposition: form-data; name="metadata"\r\n\r\n'.encode()
            + json.dumps(data).encode() + f'\r\n--{boundary}\r\nContent-Disposition: form-data; name="file"; filename="{jar.name}"\r\nContent-Type: application/java-archive\r\n\r\n'.encode()
            + jar.read_bytes() + f'\r\n--{boundary}--\r\n'.encode())
    req = urllib.request.Request(f'{API}/projects/{PROJECT_ID}/upload-file', data=body,
          headers={'X-Api-Token': token, 'Content-Type': f'multipart/form-data; boundary={boundary}'}, method='POST')
    # Do not retry POST automatically: ambiguous network failures can mean an accepted upload.
    with urllib.request.urlopen(req, timeout=180) as response:
        result = json.load(response)
    file_id = result.get('id')
    if not isinstance(file_id, int) or file_id <= 0:
        raise ValueError('Upload response did not contain a file ID')
    receipt = {'project_id': PROJECT_ID, 'file_id': file_id, 'version': args.version,
               'jar_sha256': digest, 'release_type': data['releaseType']}
    (p / 'curseforge-upload.json').write_text(json.dumps(receipt, indent=2) + '\n')
    print(f'CurseForge accepted file {file_id} as {data["releaseType"]}; moderation may still be pending.')


if __name__ == '__main__':
    try:
        main()
    except urllib.error.HTTPError as error:
        # Never echo the authenticated request, headers, or server response body.
        detail = error.read(4096).decode('utf-8', errors='replace')
        token = os.environ.get('CURSEFORGE_API_TOKEN', '')
        if token:
            detail = detail.replace(token, '[redacted]')
        # Only the API's small error payload is shown; never headers or the request.
        sys.exit(f'CurseForge HTTP {error.code}: {detail[:2000]}')
    except Exception as error:
        sys.exit(str(error))
