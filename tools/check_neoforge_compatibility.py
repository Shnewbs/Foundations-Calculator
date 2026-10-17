"""Compile and run against the exact advertised minimum; normal validation then runs the current pin.
This gate tests real loader APIs. It does not infer compatibility across Minecraft versions.
"""
import json,os,re,subprocess,tomllib
from pathlib import Path
root=Path(__file__).resolve().parents[1]
metadata=tomllib.loads((root/'src/main/resources/META-INF/neoforge.mods.toml').read_text())
deps=metadata['dependencies']['foundations_calculator']
span=next(d['versionRange'] for d in deps if d['modId']=='neoforge')
floor=re.fullmatch(r'\[([^,]+),[^)]+\)',span)[1]
native=next(d['versionRange'] for d in deps if d['modId']=='minecraft')!='[1.21.1]'
env=os.environ.copy();env['ORG_GRADLE_PROJECT_neoforgeVersion']=floor
build=root/'validation/neoforge-minimum';build.mkdir(parents=True,exist_ok=True)
(build/'neoforge-compatibility.json').unlink(missing_ok=True)
logpath=build/'neoforge-compatibility-run.log'
args=['bash','gradlew','clean',*(['compileJava','jar','sourcesJar'] if native else ['runGameTestServer']),'--console=plain']
with logpath.open('w') as log:
 result=subprocess.run(args,cwd=root,env=env,stdout=log,stderr=subprocess.STDOUT)
text=logpath.read_text();print(text,flush=True)
if result.returncode:raise SystemExit('Advertised minimum NeoForge does not compile/run; see neoforge-compatibility.log')
if native:
 with logpath.open('a') as log:
  result=subprocess.run(['python3','tools/check_port_startup.py'],cwd=root,env=env,stdout=log,stderr=subprocess.STDOUT)
 if result.returncode:raise SystemExit('Minimum NeoForge server smoke failed; see neoforge-compatibility.log')
 report=json.loads((root/'build/port-startup.json').read_text())
else:
 counts=re.findall(r'All (\d+) required tests passed',text)
 report={'passed':bool(counts),'gametests':int(counts[-1]) if counts else 0}
 if not report['passed']:raise SystemExit('Minimum NeoForge did not complete GameTests')
report.update(neoforge_minimum=floor,declared_range=span,scope='Real minimum-version compile and native server runtime; the default pin is tested separately')
(build/'neoforge-compatibility.json').write_text(json.dumps(report,indent=2)+'\n')
(build/'neoforge-compatibility.log').write_text(logpath.read_text())
print(json.dumps(report,indent=2))
