"""Structural progression sanity for Calculator data. It is not a survival playthrough.
Vanilla/foreign items and tags are treated as externally obtainable; research state and loot are not simulated.
"""
from pathlib import Path
import json,sys
ROOT=Path(__file__).resolve().parents[1]
recipe_root=ROOT/'src/main/resources/data/foundations_calculator/recipe'
recipes=[]; errors=[]
for p in recipe_root.rglob('*.json'):
    try: recipes.append((p,json.loads(p.read_text())))
    except Exception as e: errors.append(f'{p.relative_to(ROOT)}: {e}')
if errors:
    print('\n'.join(errors),file=sys.stderr);sys.exit(1)

def ids_from_ingredient(obj):
    if not isinstance(obj,dict): return set()
    if 'item' in obj:
        v=obj['item']; return {v} if isinstance(v,str) else set()
    # Ingredient alternatives are represented differently by vanilla/data generators; recurse values/lists.
    out=set()
    for v in obj.values():
        if isinstance(v,dict): out|=ids_from_ingredient(v)
        elif isinstance(v,list):
            for x in v:
                if isinstance(x,dict): out|=ids_from_ingredient(x)
    return out

def foundations_only(ids): return {i for i in ids if isinstance(i,str) and i.startswith('foundations_calculator:')}

def result_ids(data):
    out=set();r=data.get('result')
    if isinstance(r,str):out.add(r)
    elif isinstance(r,dict):
        x=r.get('id') or r.get('item');
        if isinstance(x,str):out.add(x)
    for row in data.get('results',[]):
        if isinstance(row,dict):
            st=row.get('stack',row);x=st.get('id') or st.get('item') if isinstance(st,dict) else None
            if isinstance(x,str):out.add(x)
    return out

def ingredients(data):
    out=set()
    if data.get('type')=='foundations_calculator:process':
        for row in data.get('ingredients',[]):
            if isinstance(row,dict): out|=ids_from_ingredient(row.get('ingredient',row))
    else:
        ing=data.get('ingredients')
        if isinstance(ing,list):
            for row in ing:out|=ids_from_ingredient(row)
        key=data.get('key',{})
        if isinstance(key,dict):
            for row in key.values():out|=ids_from_ingredient(row)
    return out

# Base recipes whose explicit item dependencies are all outside Foundations are immediately reachable.
reachable=set();changed=True
while changed:
    changed=False
    for p,d in recipes:
        req=foundations_only(ingredients(d))
        if req<=reachable:
            # Process recipes also need their workstation if it is a registered Foundations content id.
            machine=d.get('machine') if d.get('type')=='foundations_calculator:process' else None
            machine_id='foundations_calculator:'+machine if isinstance(machine,str) else None
            if machine_id and machine not in {'calculator','scientific','flawless','dynamic_module','atomic'} and machine_id not in reachable:
                continue
            for out in foundations_only(result_ids(d)):
                if out not in reachable:reachable.add(out);changed=True

all_outputs=set();bad_values=[]
for p,d in recipes:
    all_outputs|=foundations_only(result_ids(d))
    if d.get('type')=='foundations_calculator:process':
        if d.get('energy',0)<0 or d.get('ticks',1)<=0:bad_values.append(str(p.relative_to(ROOT)))
        for r in d.get('results',[]):
            if isinstance(r,dict):
                st=r.get('stack',r)
                if isinstance(st,dict) and int(st.get('count',1))<=0:bad_values.append(str(p.relative_to(ROOT)))

# Core workstations expected to be obtainable through data, not creative-only internals.
core=['info_calculator','calculator','scientific_calculator','power_cube','extraction_chamber','restoration_chamber','reassembly_chamber','processing_chamber','analysing_chamber','research_chamber','module_workstation']
missing_core=[x for x in core if 'foundations_calculator:'+x not in reachable]
# Some content intentionally comes only from world/process rewards; report rather than fail every such output.
unreachable=sorted(all_outputs-reachable)
status='PASS' if not errors and not bad_values and not missing_core else 'FAIL'
report={
 'status':status,'recipes_parsed':len(recipes),'reachable_foundations_outputs':len(reachable),
 'all_foundations_recipe_outputs':len(all_outputs),'missing_core_progression':missing_core,
 'invalid_process_values':bad_values,'unreachable_recipe_outputs_sample':unreachable[:80],
 'scope':'Structural recipe graph only. Foreign items/tags are treated as obtainable; research, loot, worldgen and player behavior are not simulated.'
}
out=ROOT/'validation/0.0.2a';out.mkdir(parents=True,exist_ok=True)
(out/'progression-structure.json').write_text(json.dumps(report,indent=2)+'\n')
print(json.dumps(report,indent=2))
sys.exit(0 if status=='PASS' else 2)
