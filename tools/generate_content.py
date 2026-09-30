"""Regenerate the registry without overwriting hand-maintained systems or R4 content.
Edit registry_declarations.json and templates/Content.java.template to change registrations.
The upstream importer is a provenance refresh; review its catalog before regenerating.
"""
from pathlib import Path
import json,re
root=Path(__file__).resolve().parents[1]
rows=json.loads((root/'tools/registry_declarations.json').read_text())
catalog=json.loads((root/'tools/content_catalog.json').read_text())
ids=[re.search(r'(?:addBlock|ITEMS_BY_ID.put)\("([^"]+)"',line)[1] for line in rows]
assert len(ids)==len(set(ids)) and set(ids)=={entry['id'] for entry in catalog},'Registry declarations and catalog must agree'
source=(root/'tools/templates/Content.java.template').read_text().replace('@REGISTRATIONS@','\n'.join(rows))
(root/'src/main/java/com/foundations/calculator/content/Content.java').write_text(source)
(root/'tools/pending_content.json').write_text('[]\n')
print(len(rows),'content entries regenerated')
