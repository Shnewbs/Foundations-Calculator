import sys
import unittest
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from release_versions import candidate_type, candidate_stage

class ReleaseVersionsTest(unittest.TestCase):
    def test_beta_names(self):
        for name in ['0.1.0beta', '0.1.0beta.R2', '0.1.0-beta.1', '0.1b']:
            self.assertEqual(candidate_type(name), 'beta')
    def test_alphas_stay_alpha(self):
        for name in ['0.0.5a.R2', '0.0.3a-dev.1', '0.1a','0.1.0alpha.R1','26.1.2-0.1.0alpha.R1','26.3-0.1.0alpha.R1']:
            self.assertEqual(candidate_type(name), 'alpha')
    def test_final_release(self):
        self.assertEqual(candidate_type('0.1.0'), 'release')
    def test_unvalidated_port_name_does_not_publish(self):
        for name in ['26.1.2-0.1.0beta-port.1', '0.1.0beta-port.2', '0.1.0beta-dev.1', 'latest']:
            with self.assertRaises(ValueError): candidate_type(name)

    def test_rc_is_a_prerelease_on_each_target(self):
        for prefix in ['', '26.1.2-', '26.3-']:
            version=prefix+'0.1.0-rc.1'
            self.assertEqual(candidate_type(version),'beta')
            self.assertEqual(candidate_stage(version),'release_candidate')
        self.assertEqual(candidate_stage('0.1.0beta'),'beta')
        self.assertEqual(candidate_stage('0.1.0'),'release')
    def test_malformed_rc_names_cannot_publish(self):
        for version in ['26.4-0.1.0-rc.1','0.1.0-rc.dev','26.3-latest','26.3-0.1.0']:
            with self.assertRaises(ValueError):candidate_stage(version)
