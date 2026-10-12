import sys
import unittest
from pathlib import Path
sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from release_versions import candidate_type

class ReleaseVersionsTest(unittest.TestCase):
    def test_beta_names(self):
        for name in ['0.1.0beta', '0.1.0beta.R2', '0.1.0-beta.1', '0.1b']:
            self.assertEqual(candidate_type(name), 'beta')
    def test_alphas_stay_alpha(self):
        for name in ['0.0.5a.R2', '0.0.3a-dev.1', '0.1a']:
            self.assertEqual(candidate_type(name), 'alpha')
    def test_final_release(self):
        self.assertEqual(candidate_type('0.1.0'), 'release')
    def test_unvalidated_port_name_does_not_publish(self):
        for name in ['26.1.2-0.1.0beta-port.1', '0.1.0beta-port.2', '0.1.0beta-dev.1', 'latest']:
            with self.assertRaises(ValueError): candidate_type(name)
