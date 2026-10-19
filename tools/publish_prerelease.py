"""Publish an audited alpha/beta on GitHub and upload the same bytes to CurseForge."""
import argparse
import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import tempfile
from release_versions import candidate_type, candidate_stage
from publish_curseforge import metadata

def gh(*args):
    return subprocess.run(['gh',*args],check=True,text=True,capture_output=True).stdout

def main():
    parser=argparse.ArgumentParser()
    parser.add_argument('--directory',type=Path,required=True)
    parser.add_argument('--audit',type=Path,required=True)
    parser.add_argument('--version',required=True)
    args=parser.parse_args()
    audit=json.loads(args.audit.read_text())
    target=audit.get('minecraft_version','1.21.1')
    kind=candidate_type(args.version)
    if kind not in {'alpha','beta'}:
        raise ValueError('Automatic publishing only creates alpha/beta prereleases')
    jar=args.directory/f'FoundationsCalculator-{args.version}.jar'
    sources=jar.with_name(f'FoundationsCalculator-{args.version}-sources.jar')
    assert audit['sources_sha256']==hashlib.sha256(sources.read_bytes()).hexdigest()
    tag='v'+args.version
    stage='Release candidate' if candidate_stage(args.version)=='release_candidate' else kind.title()
    notes=f'''Foundations Calculator — Minecraft {target} — {args.version}

**{stage} prerelease.** CurseForge category: {kind}. This is not a stable release.

Fixes protection preflight for terrain tools, soil/stone conversion, energy screen
placement, sickles, nutrition leaves and obsidian keys. Denied actions preserve
resources; callback target/resource changes are rechecked before committing.
Disabled nutrition, carrier items, cranking and open portable calculations obey
live server settings. Storage menus invalidate when their selected module changes.
Idle smelters avoid per-tick data writes; analysis and nutrition use allocation-free
first-match family selection. Shared leaf rules and unreachable routing are cleaned up.
Recipe costs, yields, chances, default capacities and machine scan cadence are retained.

Native unit tests: {audit['unit_tests']}. Server/integration, minimum-loader and
real-client evidence is required by the publication workflows. Audit attached.
Full player-world migration, survival/multiplayer playthrough and long-world soak
acceptance remain open; these are not claimed complete by the RC label.
See docs/RC1_AUDIT.md for original 1.12.2 comparisons and deliberate port differences.
Requires Java {'21' if target=='1.21.1' else '25'}.
'''
    if target!='1.21.1':
        notes+='\nMatching JEI, Jade, AE2 and GuideMe were tested. KubeJS, CraftTweaker and the other 1.21.1 energy integrations are not supported on this port.\n'
    with tempfile.TemporaryDirectory(prefix='calculator-publish-') as temporary:
        p=Path(temporary)
        shutil.copy2(jar,p/jar.name)
        shutil.copy2(args.audit,p/'release_checks.json')
        (p/'release-notes.md').write_text(notes)
        metadata(p/jar.name,p/'release_checks.json',p/'release-notes.md',args.version)
        existing=subprocess.run(['gh','release','view',tag,'--json','isPrerelease,assets'],text=True,capture_output=True)
        if existing.returncode==0:
            release=json.loads(existing.stdout)
            assert release['isPrerelease'],'Existing tag is not a prerelease'
            if any(a['name']=='curseforge-upload.json' for a in release['assets']):
                with tempfile.TemporaryDirectory() as downloaded:
                    gh('release','download',tag,'--pattern','curseforge-upload.json','--dir',downloaded)
                    receipt=json.loads((Path(downloaded)/'curseforge-upload.json').read_text())
                    assert receipt['jar_sha256']==audit['jar_sha256'],'Published version has different bytes; increment version'
                print(f'{tag}: matching accepted CurseForge receipt exists; skipping duplicate upload')
                return
            with tempfile.TemporaryDirectory() as downloaded:
                gh('release','download',tag,'--pattern',jar.name,'--dir',downloaded)
                assert hashlib.sha256((Path(downloaded)/jar.name).read_bytes()).hexdigest()==audit['jar_sha256'],'Version already exists with different bytes; increment version'
        else:
            # A missing tag is expected; verify repository access before creating it.
            gh('repo','view','--json','nameWithOwner')
            gh('release','create',tag,str(jar),str(sources),str(args.audit),
               '--target',os.environ['VALIDATED_COMMIT'],'--prerelease',
               '--title',f'Foundations Calculator — {target} — {args.version}',
               '--notes-file',str(p/'release-notes.md'))
        subprocess.run(['python','tools/publish_curseforge.py','--version',args.version,'--directory',str(p)],check=True)
        # Preserve the accepted receipt even if the GitHub attachment request fails.
        destination=Path('build/curseforge-upload.json')
        destination.parent.mkdir(exist_ok=True)
        shutil.copy2(p/'curseforge-upload.json',destination)
        gh('release','upload',tag,str(destination))
        print(f'{tag}: GitHub prerelease and CurseForge upload complete')

if __name__=='__main__':
    main()
