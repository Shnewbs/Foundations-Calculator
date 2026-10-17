"""Check every declared block/item name and the block-item aliases, without booting Minecraft."""
import json,re
from pathlib import Path
root=Path(__file__).resolve().parents[1]
text=(root/'src/main/java/com/foundations/calculator/content/Content.java').read_text()
names=json.loads((root/'src/main/resources/assets/foundations_calculator/lang/en_us.json').read_text())
blocks=re.findall(r'addBlock\("([^"\n]+)"',text)
items=re.findall(r'ITEMS_BY_ID.put\("([^"\n]+)"',text)
missing=[]
for prefix,ids in [('block',blocks),('item',items+blocks)]:
 for name in ids:
  key=prefix+'.foundations_calculator.'+name
  if not names.get(key) or names[key]==key:missing.append(key)
assert not missing,'Missing translated names: '+str(missing)
print(f'NAME_COVERAGE_PASS blocks={len(blocks)} standalone_items={len(items)} block_item_aliases={len(blocks)}')
