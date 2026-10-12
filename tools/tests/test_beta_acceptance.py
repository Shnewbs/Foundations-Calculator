import hashlib
import json
from pathlib import Path
import sys
import tempfile
import unittest
sys.path.insert(0, str(Path(__file__).resolve().parents[1]))
from beta_acceptance import acceptance_status, GATES

class BetaAcceptanceTest(unittest.TestCase):
    def test_missing_evidence_blocks_publication(self):
        with tempfile.TemporaryDirectory() as d:
            root=Path(d)
            self.assertEqual(acceptance_status(root,root,'source'),(False,list(GATES)))
    def test_stale_source_and_tampered_artifact_block_publication(self):
        with tempfile.TemporaryDirectory() as d:
            root=Path(d);artifact=root/'checks.txt';artifact.write_text('Recorded checks')
            gate={'status':'PASS','evidence':'checks.txt','sha256':hashlib.sha256(artifact.read_bytes()).hexdigest()}
            record={'source_sha256':'source','gates':{key:gate for key in GATES}}
            (root/'acceptance.json').write_text(json.dumps(record))
            self.assertTrue(acceptance_status(root,root,'source')[0])
            self.assertFalse(acceptance_status(root,root,'new source')[0])
            artifact.write_text('Modified checks')
            self.assertFalse(acceptance_status(root,root,'source')[0])
