"""Export the historical tree configurations into the native 26.3 feature registry."""
import json
from pathlib import Path

root = Path(__file__).resolve().parents[1]
directory = root / 'src/main/resources/data/foundations_calculator/worldgen'

def native(value):
    if isinstance(value, list):
        return [native(entry) for entry in value]
    if not isinstance(value, dict):
        return value
    if value.get('type') == 'minecraft:simple_state_provider':
        return native(value['state'])
    return { {'Name': 'id', 'Properties': 'properties'}.get(key, key): native(entry) for key, entry in value.items() }

target = directory / 'feature'
target.mkdir(exist_ok=True)
for path in (directory / 'configured_feature').glob('*.json'):
    original = json.loads(path.read_text())
    feature = native(original['config'])
    feature['type'] = original['type']
    feature['below_trunk_provider'] = feature.pop('dirt_provider')
    feature.pop('force_dirt', None)
    (target / path.name).write_text(json.dumps(feature, indent=2) + '\n')
print('Native tree features exported:', len(list(target.glob('*.json'))))
