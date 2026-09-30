"""Compile the real conversion/policy code with Java 21 and minimal TEST-ONLY interfaces.
This is NOT a NeoForge integration test, mod build, or compatibility certification.
No fixture classes are included in the distributable Gradle source sets.
"""
from pathlib import Path
import subprocess,shutil,json,datetime
root=Path(__file__).resolve().parents[1]
out=root/'build/offline-power-tests'
if out.exists():shutil.rmtree(out)
out.mkdir(parents=True)
main=root/'src/main/java/com/foundations/calculator'
sources=[main/'core'/name for name in ['EnergyRatio.java','EnergyConversion.java','StoredEnergy.java','TransferBudget.java','PowerPolicy.java']]
sources += [main/'api/LongEnergyStorage.java', main/'compat/LongEnergyBridge.java', root/'src/testSupport/java/com/foundations/calculator/core/PowerCoreAssertions.java', root/'src/testSupport/java/com/foundations/calculator/core/LongEnergyAssertions.java']
sources += list((root/'tools/offline-test-fixtures').rglob('*.java'))
subprocess.run(['javac','--release','21','-encoding','UTF-8','-d',str(out),*[str(p) for p in sources]],check=True)
lines=[]
for cls in ['PowerCoreAssertions','PowerPolicyAssertions','LongEnergyAssertions']:
 result=subprocess.run(['java','-ea','-cp',str(out),'com.foundations.calculator.core.'+cls],check=True,text=True,capture_output=True)
 print(result.stdout,end='');lines.append(result.stdout.strip())
validation=root/'validation/r7';validation.mkdir(parents=True,exist_ok=True)
(validation/'power-offline-tests.txt').write_text('\n'.join(lines)+'\n',encoding='utf-8')
(validation/'power-offline-tests.json').write_text(json.dumps({'utc':datetime.datetime.now(datetime.timezone.utc).isoformat(),'status':'PASS','scope':'Java 21 real arithmetic and policy code; minimal test-only FE interface/config. No Minecraft or optional-mod runtime.','results':lines},indent=2)+'\n',encoding='utf-8')
