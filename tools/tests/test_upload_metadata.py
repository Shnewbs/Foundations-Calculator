import hashlib
import json
from pathlib import Path
import sys
import tempfile
import unittest
import zipfile
sys.path.insert(0,str(Path(__file__).resolve().parents[1]))
from publish_curseforge import metadata

class UploadMetadataTest(unittest.TestCase):
    def candidate(self,target,version):
        temporary=tempfile.TemporaryDirectory()
        self.addCleanup(temporary.cleanup)
        p=Path(temporary.name)
        jar=p/'candidate.jar'
        with zipfile.ZipFile(jar,'w') as z:
            z.writestr('META-INF/neoforge.mods.toml',f'''[[mods]]
modId="foundations_calculator"
version="{version}"
[[dependencies.foundations_calculator]]
modId="minecraft"
versionRange="[{target}]"
''')
        audit={'version':version,'minecraft_version':target,'jar_sha256':hashlib.sha256(jar.read_bytes()).hexdigest(),
               'native_build_and_current_source_match':True,'unit_tests':43,
               'gametests':{'standalone':144,'integrations':144},'required_gametests':144,
               'native_reports':[{'integrations_enabled':b,'passed':True,'native_regression_assertions':56} for b in [False,True]]}
        (p/'audit.json').write_text(json.dumps(audit));(p/'notes.md').write_text('Alpha test metadata')
        return p,jar,audit
    def test_all_three_alphas_get_correct_platform_labels(self):
        for target in ['1.21.1','26.1.2','26.3']:
            version=('' if target=='1.21.1' else target+'-')+'0.1.0alpha.R1'
            p,jar,_=self.candidate(target,version)
            data,_=metadata(jar,p/'audit.json',p/'notes.md',version)
            self.assertEqual(data['releaseType'],'alpha')
            self.assertEqual(data['gameVersionNames'],[target,'NeoForge','Java 21' if target=='1.21.1' else 'Java 25','Client','Server'])
    def test_wrong_target_and_incomplete_runtime_are_rejected(self):
        p,jar,audit=self.candidate('26.3','26.3-0.1.0alpha.R1')
        audit['minecraft_version']='26.1.2';(p/'audit.json').write_text(json.dumps(audit))
        with self.assertRaises(ValueError):metadata(jar,p/'audit.json',p/'notes.md',audit['version'])
        audit['minecraft_version']='26.3';audit['native_reports'][1]['passed']=False;(p/'audit.json').write_text(json.dumps(audit))
        with self.assertRaises(ValueError):metadata(jar,p/'audit.json',p/'notes.md',audit['version'])
    def test_stable_and_unaccepted_beta_are_not_assumed_ready(self):
        for version in ['0.1.0','0.1.0beta.R1']:
            p,jar,_=self.candidate('1.21.1',version)
            with self.assertRaises(ValueError):metadata(jar,p/'audit.json',p/'notes.md',version)
