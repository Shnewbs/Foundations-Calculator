"""Build the shared API with Java 21, or verify the shipped Maven artifact without network/Python packages.
No Minecraft stubs are used. --check is validation only; --test also runs actual contract assertions.
"""
from pathlib import Path
import argparse,hashlib,json,shutil,subprocess,tempfile,zipfile
ROOT=Path(__file__).resolve().parents[1]
API=ROOT/'guide-api'; MAIN=API/'src/main/java'; REPO=API/'repository/com/foundations/foundations-guide-api'; VERSION='1.0.0'
def sha(p):return hashlib.sha256(p.read_bytes()).hexdigest()
def fingerprint():return {p.relative_to(API).as_posix():sha(p) for p in sorted(MAIN.rglob('*.java'))}
def write_zip(path, files):
    with zipfile.ZipFile(path,'w',zipfile.ZIP_DEFLATED,compresslevel=9) as z:
        for name,data in sorted(files.items()):
            info=zipfile.ZipInfo(name,(2026,9,28,0,0,0));info.compress_type=zipfile.ZIP_DEFLATED;info.external_attr=0o644<<16;z.writestr(info,data)
def check():
    m=json.loads((API/'artifact-integrity.json').read_text())
    if m['sources']!=fingerprint():raise SystemExit('API source changed: run python tools/rebuild_guide_api.py, then rerun checks. Do not silently use a stale library.')
    for name,expected in m['artifacts'].items():
        p=API/name
        if not p.is_file() or sha(p)!=expected:raise SystemExit('API artifact integrity failure: '+name)
    print('PASS API source/artifact fingerprints')
def build():
    dest=REPO/VERSION;dest.mkdir(parents=True,exist_ok=True)
    with tempfile.TemporaryDirectory(prefix='foundations-guide-api-') as tmp:
        classes=Path(tmp)/'classes';classes.mkdir()
        subprocess.run(['javac','--release','21','-encoding','UTF-8','-d',str(classes),*[str(p) for p in sorted(MAIN.rglob('*.java'))]],check=True)
        manifest=b'Manifest-Version: 1.0\r\nFMLModType: LIBRARY\r\nAutomatic-Module-Name: com.foundations.guide.api\r\nImplementation-Title: Foundations Guide API\r\nImplementation-Version: 1.0.0\r\n\r\n'
        files={p.relative_to(classes).as_posix():p.read_bytes() for p in classes.rglob('*.class')};files['META-INF/MANIFEST.MF']=manifest
        files['META-INF/LICENSE']= (ROOT/'LICENSE').read_bytes()
        jar=dest/f'foundations-guide-api-{VERSION}.jar';write_zip(jar,files)
        write_zip(dest/f'foundations-guide-api-{VERSION}-sources.jar',{p.relative_to(MAIN).as_posix():p.read_bytes() for p in MAIN.rglob('*.java')})
    (dest/f'foundations-guide-api-{VERSION}.pom').write_text(f'<project xmlns="http://maven.apache.org/POM/4.0.0"><modelVersion>4.0.0</modelVersion><groupId>com.foundations</groupId><artifactId>foundations-guide-api</artifactId><version>{VERSION}</version><name>Foundations Guide API</name><description>Java 21 loader-neutral field guide contract. No Calculator dependency.</description><licenses><license><name>MIT</name></license></licenses></project>\n')
    (REPO/'maven-metadata.xml').write_text(f'<metadata><groupId>com.foundations</groupId><artifactId>foundations-guide-api</artifactId><versioning><latest>{VERSION}</latest><release>{VERSION}</release><versions><version>{VERSION}</version></versions><lastUpdated>20260928000000</lastUpdated></versioning></metadata>\n')
    for p in list(REPO.rglob('*')):
        if p.is_file() and p.suffix not in {'.sha1','.sha256','.md5'}:
            p.with_name(p.name+'.sha1').write_text(hashlib.sha1(p.read_bytes()).hexdigest()+'\n');p.with_name(p.name+'.sha256').write_text(sha(p)+'\n')
    data={'artifact':'com.foundations:foundations-guide-api:'+VERSION,'sources':fingerprint(),'artifacts':{p.relative_to(API).as_posix():sha(p) for p in sorted(REPO.rglob('*')) if p.is_file()}}
    (API/'artifact-integrity.json').write_text(json.dumps(data,indent=2)+'\n');check()
def test():
    check()
    with tempfile.TemporaryDirectory(prefix='guide-contract-') as tmp:
        sources=list(MAIN.rglob('*.java'))+list((API/'src/test/java').rglob('*.java'))
        subprocess.run(['javac','--release','21','-encoding','UTF-8','-d',tmp,*map(str,sorted(sources))],check=True)
        assets=ROOT/'src/main/resources/assets'
        if not assets.exists():assets=API/'examples/calculator-corpus/assets'
        subprocess.run(['java','-cp',tmp,'com.foundations.guide.api.GuideContractAssertions',str(assets)],check=True)
if __name__=='__main__':
    a=argparse.ArgumentParser();a.add_argument('--check',action='store_true');a.add_argument('--test',action='store_true');args=a.parse_args()
    if args.check:check()
    else:build()
    if args.test:test()
