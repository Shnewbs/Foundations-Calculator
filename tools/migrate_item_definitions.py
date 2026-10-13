#!/usr/bin/env python3
"""Emit native item definitions while retaining the existing model geometry and transforms."""
from pathlib import Path
import json,re
ROOT=Path(__file__).resolve().parents[1]
ASSETS=ROOT/'src/main/resources/assets/foundations_calculator'
source=(ROOT/'src/main/java/com/foundations/calculator/content/MachineBlock.java').read_text()
modeled=set(re.findall(r'"([a-z0-9_]+)"',source.split('MODELED=',1)[1].split(';',1)[0]))
out=ASSETS/'items';out.mkdir(exist_ok=True)
for path in (ASSETS/'models/item').glob('*.json'):
    name=path.stem
    regular={'type':'minecraft:model','model':'foundations_calculator:item/'+name}
    if name in modeled:
        base=json.loads(path.read_text())
        if base.get('parent')=='builtin/entity':
            base['parent']='minecraft:block/block';path.write_text(json.dumps(base,indent=2)+'\n')
        model={'type':'minecraft:special','base':'foundations_calculator:item/'+name,'model':{'type':'foundations_calculator:machine','kind':name}}
    elif re.fullmatch(r'.+_(sword|pickaxe|axe|shovel|hoe)',name) and (ASSETS/'models/flat_tools'/f'{name}.json').exists():
        model={'type':'minecraft:condition','property':'foundations_calculator:tool_models','on_true':regular,'on_false':{'type':'minecraft:model','model':'foundations_calculator:flat_tools/'+name}}
    else:model=regular
    (out/path.name).write_text(json.dumps({'model':model},indent=2)+'\n')
print('Native item definitions:',len(list(out.glob('*.json'))))
