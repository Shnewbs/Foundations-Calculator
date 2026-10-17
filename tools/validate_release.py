"""Run the same native release gates locally or in CI, preserving fresh evidence."""
import argparse
import datetime
import hashlib
import json
import os
from pathlib import Path
import shlex
import subprocess
import sys
from release_inputs import source_fingerprint
from release_versions import candidate_type

ROOT = Path(__file__).resolve().parents[1]


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--gradle-command', help='Optional Gradle launcher command; default uses the checked-in wrapper')
    args = parser.parse_args()
    os.chdir(ROOT)
    version = next(line.split('=', 1)[1] for line in (ROOT / 'gradle.properties').read_text().splitlines() if line.startswith('mod_version='))
    candidate_type(version)
    evidence = ROOT / 'validation' / version
    evidence.mkdir(parents=True, exist_ok=True)
    # Never let a failed new attempt reuse an earlier successful release audit.
    for name in ['release_checks.json', 'build-receipt.json', 'gametest-standalone.log', 'gametest-integrations.log']:
        (evidence / name).unlink(missing_ok=True)
    wrapper = ['cmd', '/c', 'gradlew.bat'] if os.name == 'nt' else ['bash', 'gradlew']
    gradle = shlex.split(args.gradle_command, posix=os.name != 'nt') if args.gradle_command else wrapper
    subprocess.run([sys.executable, '-m', 'unittest', 'discover', '-s', 'tools/tests'], check=True)
    started = datetime.datetime.now(datetime.timezone.utc).isoformat()
    for script in ['generate_config.py', 'generate_config_docs.py', 'author_field_guide.py', 'validate_assets.py', 'validate_progression.py']:
        subprocess.run([sys.executable, 'tools/' + script], check=True)
    subprocess.run([sys.executable,'tools/benchmark_discount_cache.py'],check=True)
    subprocess.run([sys.executable,'tools/check_neoforge_compatibility.py'],check=True)
    fingerprint = source_fingerprint(ROOT)
    phases = [('native-build', ['clean', 'test', 'build']),
              ('gametest-standalone', ['runGameTestServer']),
              ('gametest-integrations', ['runGameTestServer', '-PwithIntegrations', '-PwithGrandpower', '-PwithKubeJS'])]
    for name, options in phases:
        with (evidence / (name + '.log')).open('w') as log:
            result = subprocess.run(gradle + options + ['--console=plain'], stdout=log, stderr=subprocess.STDOUT)
        if result.returncode:
            raise RuntimeError(f'{name} failed; see {evidence / (name + ".log")}')
        if fingerprint != source_fingerprint(ROOT):
            raise RuntimeError('Release inputs changed during validation; restart on the final source')
    jar = ROOT / 'build' / 'libs' / f'FoundationsCalculator-{version}.jar'
    sources = jar.with_name(f'FoundationsCalculator-{version}-sources.jar')
    receipt = {'version': version, 'started_utc': started,
               'finished_utc': datetime.datetime.now(datetime.timezone.utc).isoformat(),
               'source_sha256': fingerprint, 'jar_sha256': hashlib.sha256(jar.read_bytes()).hexdigest(),
               'sources_sha256': hashlib.sha256(sources.read_bytes()).hexdigest()}
    (evidence / 'build-receipt.json').write_text(json.dumps(receipt, indent=2) + '\n')
    subprocess.run([sys.executable, 'tools/check_release.py'], check=True)
    print(f'Native release gates passed for {version}. Client, migration and performance acceptance are reported separately.')


if __name__ == '__main__':
    main()
