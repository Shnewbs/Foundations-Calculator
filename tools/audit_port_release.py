"""Bind native port test evidence and binary/source JARs to this exact checkout."""
import argparse
import hashlib
import json
from pathlib import Path
import struct
import tomllib
import xml.etree.ElementTree as ET
import zipfile
from release_versions import candidate_type, candidate_stage

ROOT = Path(__file__).resolve().parents[1]

def fingerprint():
    records = {}
    for directory in ['src', 'platform', 'guide-api', 'gradle', 'tools']:
        for p in (ROOT / directory).rglob('*'):
            if p.is_file() and '__pycache__' not in p.parts and p.suffix not in {'.pyc', '.log'}:
                records[p.relative_to(ROOT).as_posix()] = hashlib.sha256(p.read_bytes()).hexdigest()
    for name in ['build.gradle', 'settings.gradle', 'gradle.properties']:
        records[name] = hashlib.sha256((ROOT / name).read_bytes()).hexdigest()
    return hashlib.sha256(json.dumps(records, sort_keys=True).encode()).hexdigest()

def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--begin', action='store_true')
    args = parser.parse_args()
    receipt = ROOT / 'build/port-source-receipt.json'
    if args.begin:
        receipt.parent.mkdir(exist_ok=True)
        receipt.write_text(json.dumps({'source_sha256': fingerprint()}))
        return
    compatibility=json.loads((ROOT/'validation/neoforge-minimum/neoforge-compatibility.json').read_text())
    assert compatibility['passed'] and compatibility['declared_range'] in (ROOT/'src/main/resources/META-INF/neoforge.mods.toml').read_text(),'Minimum NeoForge validation is missing'
    client=json.loads((ROOT/'validation/port-client/client-checks.json').read_text())
    assert client['passed'],'Native client validation is missing'
    digest = fingerprint()
    if json.loads(receipt.read_text())['source_sha256'] != digest:
        raise ValueError('Source changed while native port validation ran')
    version = next(l.split('=', 1)[1] for l in (ROOT/'gradle.properties').read_text().splitlines() if l.startswith('mod_version='))
    jar = ROOT / f'build/libs/FoundationsCalculator-{version}.jar'
    sources = jar.with_name(f'FoundationsCalculator-{version}-sources.jar')
    with zipfile.ZipFile(jar) as z:
        meta = tomllib.loads(z.read('META-INF/neoforge.mods.toml').decode())
        assert next(m for m in meta['mods'] if m['modId']=='foundations_calculator')['version']==version
        mc = next(d for d in meta['dependencies']['foundations_calculator'] if d['modId']=='minecraft')['versionRange']
        target = mc.removeprefix('[').removesuffix(']')
        assert target in {'26.1.2', '26.3'} and mc==f'[{target}]'
        for n in z.namelist():
            if n.endswith('.class') and n.startswith('com/foundations/calculator/'):
                assert 'NativePortSmoke' not in n and 'PortClientSmoke' not in n and 'GameTests' not in n
                assert struct.unpack('>H',z.read(n)[6:8])[0]==69
                assert z.read(n)==(ROOT/'build/classes/java/main'/n).read_bytes()
    with zipfile.ZipFile(sources) as z:
        count=0
        for n in z.namelist():
            if n.endswith('.java'):
                candidates=[ROOT/'src/main/java'/n, ROOT/f'platform/{target}/src/main/java'/n]
                assert any(p.is_file() and p.read_bytes()==z.read(n) for p in candidates),f'Stale source {n}'
                count+=1
        assert count>50
    files=list((ROOT/'build/test-results/testPortEnergy').glob('TEST-*.xml'))
    assert files
    tests=0
    for p in files:
        t=ET.parse(p).getroot()
        assert all(int(t.get(k,0))==0 for k in ['failures','errors','skipped'])
        tests+=int(t.get('tests',0))
    assert tests>=43
    reports=[json.loads((ROOT/f'build/port-startup{suffix}.json').read_text()) for suffix in ['', '-integrations']]
    assert {r['integrations_enabled'] for r in reports}=={False,True}
    assert all(r['passed'] and r['native_regression_assertions']>=220 and not r['resource_errors'] for r in reports)
    if candidate_stage(version)=='release_candidate':
        for mode in ['', '-integrations']:
            assert 'FOUNDATIONS_RC1_REGRESSIONS_PASSED' in (ROOT/f'build/port-startup{mode}.log').read_text(),'Missing native RC regression marker'
    release_type=candidate_type(version)
    audit={'version':version,'minecraft_version':target,'release_type':release_type,
           'candidate_stage':candidate_stage(version),'release_eligible':release_type=='alpha' or candidate_stage(version)=='release_candidate','native_build_and_current_source_match':True,
           'source_sha256':digest,'jar_sha256':hashlib.sha256(jar.read_bytes()).hexdigest(),
           'sources_sha256':hashlib.sha256(sources.read_bytes()).hexdigest(),
           'unit_tests':tests,'native_reports':reports,'neoforge_compatibility':compatibility,'native_client':client,
           'manual_acceptance':'Pending broader port client gameplay, copied-world upgrade, multiplayer/protection and performance soak.'}
    (ROOT/'build/release_checks.json').write_text(json.dumps(audit,indent=2)+'\n')
    print(json.dumps(audit,indent=2))

if __name__=='__main__':
    main()
