import runpy,unittest
from pathlib import Path
class NamesTest(unittest.TestCase):
 def test_all_registered_names(self):runpy.run_path(str(Path(__file__).resolve().parents[1]/'check_item_names.py'))
