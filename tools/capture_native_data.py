"""Inspect resource schemas from the exact target's resolved Minecraft resource JAR."""
import json
import os
from pathlib import Path
import sys
import zipfile

seen = set()
for entry in sys.argv[1].split(os.pathsep):
    if not Path(entry).is_file() or not entry.endswith('.jar'):
        continue
    with zipfile.ZipFile(entry) as archive:
        paths = [name for name in archive.namelist() if name.startswith('data/minecraft/worldgen/') and name.endswith('.json')]
        for name in paths:
            if name.endswith('/oak.json') or name.endswith('/oak_bees_005.json') or '/configured_feature/oak' in name or '/feature/oak' in name:
                if name not in seen:
                    print(name)
                    print(json.dumps(json.loads(archive.read(name)), indent=2)[:10000])
                    seen.add(name)
        types = sorted({name.split('/')[3] for name in paths})
        if types:
            print('Native worldgen directories:', ', '.join(types))
print('Native tree samples inspected:', len(seen))
