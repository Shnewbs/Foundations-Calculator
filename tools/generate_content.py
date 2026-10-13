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

# Keep the generated catalog on this branch's actual 26.x tool API.
import runpy
runpy.run_path(str(Path(__file__).resolve().parent / "migrate_tool_materials.py"))

# Native item descriptors and preserved special/flat tool presentation.
import runpy
runpy.run_path(str(Path(__file__).resolve().with_name("migrate_item_definitions.py")),run_name="__main__")

# Native ingredient codecs use item and tag strings.
runpy.run_path(str(Path(__file__).resolve().with_name("migrate_recipe_ingredients.py")),run_name="__main__")
