"""Run actual production policies against small isolated test fixtures, never a game runtime.
Requires Python 3.10+ and JDK 21. No network; no fixture enters Gradle main source sets.
"""
from pathlib import Path
import datetime,json,shutil,subprocess,sys
root=Path(__file__).resolve().parents[1]
main=root/'src/main/java/com/foundations/calculator'
validation=root/'validation/r7';validation.mkdir(parents=True,exist_ok=True)
results=[]
def run_suite(name,sources,entry,args=()):
    out=root/'build'/('offline-'+name)
    if out.exists():shutil.rmtree(out)
    out.mkdir(parents=True)
    subprocess.run(['javac','--release','21','-encoding','UTF-8','-d',str(out),*[str(p) for p in sources]],check=True)
    process=subprocess.run(['java','-ea','-cp',str(out),entry,*map(str,args)],check=True,capture_output=True,text=True)
    print(process.stdout,end='');results.append(process.stdout.strip())
    (validation/(name+'.log')).write_text(process.stdout+process.stderr,encoding='utf-8')
run_suite('r7-core',[main/'core'/p for p in ['AutomationRules.java','StaggeredWork.java','ChangeStamp.java']]+[root/'src/testSupport/java/com/foundations/calculator/core/R7CoreAssertions.java'],'com.foundations.calculator.core.R7CoreAssertions')
run_suite('r7-world-policy',[main/'core/MachineWorldActions.java',main/'api/MachineWorldActionEvent.java',*sorted((root/'tools/r7-world-test-fixtures').rglob('*.java'))],'com.foundations.calculator.core.R7WorldActionAssertions')
run_suite('r7-syntax',[root/'tools/java/ParseJavaSources.java'],'ParseJavaSources',[root/'src'])
subprocess.run([sys.executable,str(root/'tools/run_power_offline_tests.py')],check=True)
(validation/'offline-summary.json').write_text(json.dumps({'utc':datetime.datetime.now(datetime.timezone.utc).isoformat(),'status':'PASS','native_build':False,'minecraft_runtime':False,'windows_updater_executed':False,'performance_benchmark':False,'results':results,'power_results':'power-offline-tests.json'},indent=2)+'\n',encoding='utf-8')
