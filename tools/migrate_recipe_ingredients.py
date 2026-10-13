"""Convert the inherited vanilla ingredient encoding to native holder/tag strings."""
from pathlib import Path
import json

root = Path(__file__).resolve().parents[1]

def ingredient(value):
    if isinstance(value, dict) and set(value) == {'item'}:
        return value['item']
    if isinstance(value, dict) and set(value) == {'tag'}:
        return '#' + value['tag']
    if isinstance(value, list):
        return [ingredient(entry) for entry in value]
    return value

changed = 0
for path in (root / 'src/main/resources/data').glob('*/recipe/**/*.json'):
    recipe = json.loads(path.read_text())
    original = json.dumps(recipe, sort_keys=True)
    if recipe.get('type') == 'foundations_calculator:process':
        for entry in recipe.get('ingredients', []):
            entry['ingredient'] = ingredient(entry['ingredient'])
    else:
        for key in ['ingredient', 'base', 'addition', 'template']:
            if key in recipe:
                recipe[key] = ingredient(recipe[key])
        if 'ingredients' in recipe:
            recipe['ingredients'] = [ingredient(entry) for entry in recipe['ingredients']]
        if 'key' in recipe:
            recipe['key'] = {key: ingredient(value) for key, value in recipe['key'].items()}
    if json.dumps(recipe, sort_keys=True) != original:
        path.write_text(json.dumps(recipe, indent=2) + '\n')
        changed += 1
print(f'Migrated {changed} recipe files to native ingredients')
