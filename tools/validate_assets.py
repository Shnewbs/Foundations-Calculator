from pathlib import Path
import json,re,sys,zipfile
R=Path(__file__).resolve().parents[1];A=R/'src/main/resources/assets';N='foundations_calculator'
seen=set();missing=set();missingtex=set();unbound=set()
unstitched=set();invalidpaths=set();invalidbounds=set()
atlas=json.loads((A/'minecraft/atlases/blocks.json').read_text())
atlas_directories=[s['prefix'] for s in atlas['sources'] if s['type']=='minecraft:directory']
vanillajars=list((Path.home()/'.gradle/caches/neoformruntime').rglob('*client*.jar'))
vanilla=set()
for p in vanillajars:
 try:
  with zipfile.ZipFile(p) as z:vanilla.update(z.namelist())
 except:pass
vanilla_tex_renames={'blocks/stonebrick':'block/stone_bricks','block/stonebrick':'block/stone_bricks'}
def checktex(t):
 if t.startswith('#'):return
 ns,name=(t if ':'in t else 'minecraft:'+t).split(':')
 if ns==N and not (A/ns/'textures'/(name+'.png')).exists():missingtex.add(t)
 if ns==N and not name.startswith(tuple(['block/','item/']+atlas_directories)):unstitched.add(t)
 if ns=='minecraft' and vanilla and f'assets/minecraft/textures/{name}.png'not in vanilla:missingtex.add(t)
def visit(model,child=None,parents=None):
 ns,path=model.split(':');key=(model,json.dumps(child or {},sort_keys=True));parents=parents or set()
 if model in parents:missing.add('CYCLE:'+model);return
 p=A/ns/'models'/(path+'.json')
 if ns=='minecraft':
  if vanilla and f'assets/minecraft/models/{path}.json'not in vanilla and not path.startswith('builtin/'):missing.add(model)
  return
 if not p.exists():missing.add(model);return
 if key in seen:return
 seen.add(key);data=json.loads(p.read_text());tex={**data.get('textures',{}),**(child or {})}
 for v in tex.values():checktex(v)
 for el in data.get('elements',[]):
  for face in el.get('faces',{}).values():
   t=face['texture'];visited=set()
   while t.startswith('#') and t[1:]in tex and t not in visited:visited.add(t);t=tex[t[1:]]
   if t.startswith('#'):unbound.add((model,t))
   else:checktex(t)
 if 'parent'in data:visit(data['parent'],tex,parents|{model})
for p in (A/N/'models/item').glob('*.json'):visit(N+':item/'+p.stem)
for p in (A/N/'blockstates').glob('*.json'):
 def walk(o):
  if isinstance(o,dict):
   if 'model'in o:visit(o['model'])
   for v in o.values():walk(v)
  elif isinstance(o,list):
   for v in o:walk(v)
 walk(json.loads(p.read_text()))
# Minecraft scans every model, including legacy models not reachable from a blockstate.
for p in A.rglob('*'):
 if p.is_file() and not re.fullmatch(r'[a-z0-9_./-]+',str(p.relative_to(A))):invalidpaths.add(str(p.relative_to(A)))
for p in (A/N/'models').rglob('*.json'):
 data=json.loads(p.read_text())
 for element in data.get('elements',[]):
  for key in ['from','to']:
   if any(v < -16 or v > 32 for v in element.get(key,[])):invalidbounds.add(str(p.relative_to(A)))
report={'missing_models':sorted(missing),'missing_textures':sorted(missingtex),'unbound_textures':sorted(unbound),'textures_outside_atlas':sorted(unstitched),'invalid_resource_paths':sorted(invalidpaths),'invalid_model_bounds':sorted(invalidbounds),'models_visited':len(seen),'minecraft_assets_checked':bool(vanilla)}
(R/'tools/asset_validation.json').write_text(json.dumps(report,indent=2));print(json.dumps(report,indent=2));sys.exit(1 if missing or missingtex or unbound or unstitched or invalidpaths or invalidbounds else 0)
