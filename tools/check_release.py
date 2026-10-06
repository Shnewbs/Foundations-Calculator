"""Release audit for Foundations Calculator 0.0.2a.R2.

Run only after the native build and both GameTest modes. This script verifies that the
candidate JAR matches current source/resources and that fresh local test evidence exists.
It intentionally does not certify the manual copied-world/client/performance checklist.
"""
from pathlib import Path
import json,re,zipfile,hashlib,struct,xml.etree.ElementTree as ET

root=Path(__file__).resolve().parents[1]
version=re.search(r'^mod_version=(.+)$',(root/'gradle.properties').read_text(),re.M)[1]
assert re.fullmatch(r'0\.0\.[0-9]+a(?:\.R[0-9]+)?(?:-dev\.[0-9]+)?',version),f'Unexpected release version: {version}'
evidence=root/'validation'/version
evidence.mkdir(parents=True,exist_ok=True)
jar=root/'build'/'libs'/f'FoundationsCalculator-{version}.jar'
assert jar.is_file(),'No native R2 JAR; run VALIDATE_0_0_2a_R2.bat first.'

with zipfile.ZipFile(jar) as z:
    names=z.namelist(); metadata=z.read('META-INF/neoforge.mods.toml').decode('utf-8')
    assert f'version="{version}"' in metadata and metadata.count('[[mods]]')==1,'Wrong mod metadata/version'
    nested=[n for n in names if n.endswith('.jar')]
    assert all('foundations-guide-api' in n for n in nested),f'Unexpected nested JAR(s): {nested}'
    assert len(nested)<=1,f'Expected at most the Guide API Jar-in-Jar, got: {nested}'
    classes=[n for n in names if n.endswith('.class') and not n.startswith('META-INF/jarjar/')]
    assert not any(('GameTests' in n or 'Assertions' in n or '/test/' in n) for n in classes),'Test classes leaked into release JAR'
    assert all(n.startswith('com/foundations/calculator/') for n in classes), 'Unexpected exploded class namespace in main JAR'
    assert all(struct.unpack('>H',z.read(n)[6:8])[0]==65 for n in classes),'Release contains non-Java-21 classes'
    for name in classes:
        built=root/'build/classes/java/main'/name
        assert built.is_file() and z.read(name)==built.read_bytes(),f'Stale/mismatched class {name}'
    for source in (root/'src/main/java').rglob('*.java'):
        rel=source.relative_to(root/'src/main/java').with_suffix('.class').as_posix()
        assert rel in names,f'Missing class for {source}'
    for source in (root/'src/main/resources').rglob('*'):
        if source.is_file() and source.name!='neoforge.mods.toml':
            rel=source.relative_to(root/'src/main/resources').as_posix()
            assert rel in names and z.read(rel)==source.read_bytes(),f'Stale/missing resource {rel}'
    recipes=[n for n in names if n.startswith('data/foundations_calculator/recipe/') and n.endswith('.json')]
    assert len(recipes)==910,f'Expected 910 recipes, got {len(recipes)}'
    assert 'assets/foundations_calculator/foundations_guides/field_guide/book.json' in names
    assert 'assets/foundations_calculator/patchouli_books/field_guide/book.json' in names

catalog=json.loads((root/'tools/configuration_catalog.json').read_text())
assert len(catalog)==4272 and len({v['key'] for v in catalog})==4272,'Server configuration key count changed unexpectedly'
assert catalog==json.loads((root/'src/main/resources/foundations/configuration.json').read_text()),'Runtime config catalog differs from generator catalog'
book=json.loads((root/'src/main/resources/assets/foundations_calculator/foundations_guides/field_guide/book.json').read_text())
assert book.get('revision')==version,'Guide revision does not match mod version'
assert len(book.get('entries',[]))==109,'Expected 109 Foundations guide entries'

unit_files=list((root/'build/test-results/test').glob('TEST-*.xml'))
assert unit_files,'No current JUnit evidence'
unit_count=0
for f in unit_files:
    t=ET.parse(f).getroot()
    assert int(t.get('failures','0'))==0 and int(t.get('errors','0'))==0 and int(t.get('skipped','0'))==0,f'JUnit failure/skip in {f.name}'
    unit_count+=int(t.get('tests','0'))
assert unit_count>=13,f'Expected at least 13 JUnit tests, got {unit_count}'

counts={}
for mode in ['standalone','integrations']:
    p=evidence/f'gametest-{mode}.log'
    assert p.exists(),f'No fresh {mode} GameTest evidence: {p}'
    text=p.read_text(errors='replace')
    matches=re.findall(r'All (\d+) required tests passed',text)
    assert matches and int(matches[-1])>=124,f'R2 runtime suite incomplete: {p}'
    for bad in ['[KubeJS Server/]: Error','Error parsing recipe schema','Parsing error loading recipe','Errors found in the scripts']:
        assert bad not in text,f'{bad} in {p}'
    if mode=='integrations':
        for marker in ['ae2','grandpower','gtceu','mekanism','modern_industrialization','kubejs']:
            assert marker in text.lower(),f'Integration runtime marker missing: {marker}'
        assert 'Reloaded with no KubeJS errors!' in text,'KubeJS positive reload marker missing'
    counts[mode]=int(matches[-1])

report={
    'version':version,
    'native_build_and_current_source_match':True,
    'unit_tests':unit_count,
    'gametests':counts,
    'recipes':910,
    'server_settings':len(catalog),
    'guide_entries':len(book['entries']),
    'jar_sha256':hashlib.sha256(jar.read_bytes()).hexdigest(),
    'manual_acceptance':'PENDING: copied-world migration, client visuals, real integrations and performance soak'
}
(evidence/'release_checks.json').write_text(json.dumps(report,indent=2)+'\n')
print(json.dumps(report,indent=2))
