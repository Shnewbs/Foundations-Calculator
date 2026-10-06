from pathlib import Path
import json,re,shutil,itertools
R=Path(__file__).resolve().parents[1];RES=R/'src/main/resources';N='foundations_calculator';A=RES/'assets'/N
entries=json.loads((R/'tools/content_catalog.json').read_text());machines=set(re.findall(r'addBlock\("([^"]+)"[^\n]+,true,', (R/'src/main/java/com/foundations/calculator/content/Content.java').read_text()))
repo={'calculator':R.parent/'upstream-calculator/src/main/resources/assets/calculator','sonarcore':R.parent/'upstream-core/src/main/resources/assets/sonarcore'}
def load(p):
 s=p.read_text();s=re.sub(r'(?<=[{,])\s*(\d+)\s*:',lambda m:'"'+m[1]+'":',s);s=re.sub(r',\s*([}\]])',r'\1',s)
 return json.loads(s)
def write(p,obj):p.parent.mkdir(parents=True,exist_ok=True);p.write_text(json.dumps(obj,indent=2)+'\n')
def texture(t):
 t=t.lower()
 if t=='blocks/planks_oak':return 'minecraft:block/oak_planks'
 for namespace in repo:
  if t.startswith(namespace+':'):return N+':legacy_'+namespace+'/'+t.split(':')[1]
 return t.replace('minecraft:blocks/','minecraft:block/').replace('minecraft:items/','minecraft:item/')
def model(t):
 t=t.lower()
 if t=='builtin/generated':return 'minecraft:item/generated'
 for namespace in repo:
  if t.startswith(namespace+':'):
   path=t.split(':')[1]
   if not path.startswith(('block/','item/')):path='block/'+path
   return N+':legacy_'+namespace+'/'+path
 if ':' not in t:t='minecraft:'+t
 ns,path=t.split(':')
 if not path.startswith(('block/','item/','builtin/')):path='block/'+path
 return ns+':'+path
# Preserve original artwork byte-for-byte. Only resource paths and JSON syntax change.
for ns,root in repo.items():
 for p in (root/'textures').rglob('*'):
  if p.is_file():
   # The original repository includes unused filenames with spaces, which the
   # modern resource loader rejects before model resolution.
   if not re.fullmatch(r'[a-z0-9_./-]+',str(p.relative_to(root/'textures')).lower()):continue
   target=A/'textures'/('legacy_'+ns)/str(p.relative_to(root/'textures')).lower();target.parent.mkdir(parents=True,exist_ok=True);shutil.copy2(p,target)
 if (root/'models').exists():
  for p in (root/'models').rglob('*.json'):
   obj=load(p)
   if 'parent'in obj:obj['parent']=model(obj['parent'])
   if 'textures'in obj:obj['textures']={k:texture(v) for k,v in obj['textures'].items()}
   if p.stem=='calculatorlocator':obj.setdefault('textures',{})['-1']='minecraft:block/iron_block'
   if obj.get('parent') in ['forge:item-layer','minecraft:item/builtin/generated']:obj['parent']='minecraft:item/generated'
   write(A/'models'/('legacy_'+ns)/str(p.relative_to(root/'models')).lower(),obj)
missing=[]
def derived(defaults,overrides=None):
 d=dict(defaults);o=overrides or {};tex={**defaults.get('textures',{}),**o.get('textures',{})};d.update(o)
 parent=model(d.get('model','cube_all'))
 if parent=='minecraft:block/cube_all' and 'all'not in tex:
  if all(s in tex for s in ['north','south','east','west','up','down']):parent='minecraft:block/cube'
  elif tex:tex['all']=next(iter(tex.values()))
 if parent=='minecraft:block/leaves':parent='minecraft:block/cube_all'
 if not tex and parent.startswith('minecraft:'):tex={'all':'minecraft:block/stone'}
 result={'parent':parent,'textures':{k:texture(v) for k,v in tex.items()}}
 return result
lang={}
for e in entries:
 id=e['id'];ns='sonarcore' if e['field'].startswith('SonarCore.') else 'calculator';root=repo[ns];legacy=e['legacy']
 label=id.replace('_',' ').title().replace('Co2','CO₂');lang[('block' if e['kind']=='block' else 'item')+'.'+N+'.'+id]=label
 if e['kind']=='item':
  src=root/'models/item'/f'{legacy}.json'
  if id.startswith('circuit_'):
   base,num=id.rsplit('_',1);suffix={'circuit_board':'','circuit_dirty':'_dirty','circuit_damaged':'_damaged'}[base];obj={'parent':'minecraft:item/generated','textures':{'layer0':N+':legacy_calculator/items/circuits/circuit'+str(int(num)+1)+suffix}}
  elif src.exists():obj={'parent':N+':legacy_'+ns+'/item/'+legacy}
  else:
   base=id.rsplit('_',1)[0] if id.startswith('circuit_') else id
   # Circuit sprite filenames use the original damage index.
   candidates=list((root/'textures/items').glob('*.png'))
   matches=[p for p in candidates if re.sub('[^a-z0-9]','',p.stem.lower())==re.sub('[^a-z0-9]','',legacy)]
   if not matches and id.startswith('circuit_'):
    circuitkind={'circuit_board':'circuit','circuit_dirty':'dirtycircuit','circuit_damaged':'damagedcircuit'}.get(base,base)
    matches=[p for p in candidates if circuitkind in p.stem.lower()]
   if matches:tex=N+':legacy_'+ns+'/items/'+matches[0].stem.lower()
   else:tex='minecraft:item/redstone';missing.append(id)
   obj={'parent':'minecraft:item/generated','textures':{'layer0':tex}}
  write(A/'models/item'/f'{id}.json',obj);continue
 statefile=root/'blockstates'/f'{legacy}.json'
 old=load(statefile) if statefile.exists() else {};defaults=old.get('defaults',{});variants=old.get('variants',{})
 base=derived(defaults)
 if e['constructor']=='material':
  mat=load(repo['calculator']/'blockstates/material.json');base=derived(mat['defaults'],mat['variants']['variant'][legacy])
 elif not old:
  existing=root/'models/block'/f'{legacy}.json'
  if existing.exists():base={'parent':N+':legacy_'+ns+'/block/'+legacy}
  else:base={'parent':'minecraft:block/cube_all','textures':{'all':'minecraft:block/stone'}};missing.append(id)
 states={};defaultmodel=N+':block/'+id
 if id.endswith('_stairs'):
  tex=base.get('textures',{});common=tex.get('side',tex.get('north',tex.get('all','minecraft:block/stone')));stairtex={'bottom':common,'top':common,'side':common}
  for suffix,parent in [('', 'stairs'),('_inner','inner_stairs'),('_outer','outer_stairs')]:write(A/'models/block'/f'{id}{suffix}.json',{'parent':'minecraft:block/'+parent,'textures':stairtex})
  for facing,half,shape in itertools.product(['east','south','west','north'],['bottom','top'],['straight','inner_left','inner_right','outer_left','outer_right']):
   rot={'east':0,'south':90,'west':180,'north':270}[facing]
   if shape.endswith('left'):rot=(rot+270)%360
   if half=='top' and shape!='straight':rot=(rot+90)%360
   suffix='_inner' if shape.startswith('inner') else '_outer' if shape.startswith('outer') else ''
   states[f'facing={facing},half={half},shape={shape}']={'model':defaultmodel+suffix,'y':rot,'uvlock':True,**({'x':180} if half=='top' else {})}
 elif id.endswith('_fence'):
  tex=next(iter(base.get('textures',{}).values()),'minecraft:block/oak_planks')
  for suffix,parent in [('_post','fence_post'),('_side','fence_side'),('','fence_inventory')]:write(A/'models/block'/f'{id}{suffix}.json',{'parent':'minecraft:block/'+parent,'textures':{'texture':tex}})
  multi=[{'apply':{'model':defaultmodel+'_post'}}]+[{'when':{side:'true'},'apply':{'model':defaultmodel+'_side','y':rot,'uvlock':True}} for side,rot in [('north',0),('east',90),('south',180),('west',270)]]
  write(A/'blockstates'/f'{id}.json',{'multipart':multi})
 elif id.endswith('_gate'):
  tex=next(iter(base.get('textures',{}).values()),'minecraft:block/oak_planks')
  for suffix,parent in [('', 'template_fence_gate'),('_open','template_fence_gate_open'),('_wall','template_fence_gate_wall'),('_wall_open','template_fence_gate_wall_open')]:write(A/'models/block'/f'{id}{suffix}.json',{'parent':'minecraft:block/'+parent,'textures':{'texture':tex}})
  for facing,opened,wall in itertools.product(['north','south','east','west'],['true','false'],['true','false']):
   suffix=('_wall' if wall=='true' else '')+('_open' if opened=='true' else '')
   states[f'facing={facing},open={opened},in_wall={wall}']={'model':defaultmodel+suffix,'y':{'south':0,'west':90,'north':180,'east':270}[facing],'uvlock':True}
 elif id.startswith('crop_'):
  for age in range(8):
   obj=derived(defaults,variants.get('age',{}).get(str(age),{}));obj['render_type']='minecraft:cutout';write(A/'models/block'/f'{id}_{age}.json',obj);states[f'age={age}']={'model':defaultmodel+'_'+str(age)}
 elif id in ['pear_leaves','diamond_leaves','amethyst_leaves','tanzanite_leaves']:
  for age in range(5):
   v=variants.get('growth',{}).get('ready' if age>=2 else 'fresh',{})
   obj=derived(defaults,v);obj['render_type']='minecraft:cutout';write(A/'models/block'/f'{id}_{age}.json',obj);states[f'age={age}']={'model':defaultmodel+'_'+str(age)}
  write(A/'models/block'/f'{id}.json',derived(defaults))
 elif id.endswith('_log'):
  # Legacy variants encode rotation; reuse their top/side textures.
  tex=base.get('textures',{});side=tex.get('side',tex.get('north','minecraft:block/oak_log'));end=tex.get('end',tex.get('up','minecraft:block/oak_log_top'))
  write(A/'models/block'/f'{id}.json',{'parent':'minecraft:block/cube_column','textures':{'side':side,'end':end}})
  states={'axis=y':{'model':defaultmodel},'axis=x':{'model':defaultmodel,'x':90,'y':90},'axis=z':{'model':defaultmodel,'x':90}}
 elif id.startswith('stable_stone') or 'glass'in id:
  write(A/'models/block'/f'{id}.json',base)
  valid=[(k,v) for k,v in variants.items() if 'north='in k]
  if valid:
   for i,(k,v) in enumerate(valid):
    if isinstance(v,list):v=v[0]
    derivedmodel=derived(defaults,v)
    if 'glass'in id:derivedmodel['render_type']='minecraft:translucent'
    write(A/'models/block'/f'{id}_{i}.json',derivedmodel);states[k]={'model':defaultmodel+'_'+str(i)}
  else:states={'':{'model':defaultmodel}}
 elif id=='lantern':
  states={'hanging=false':{'model':'minecraft:block/lantern'},'hanging=true':{'model':'minecraft:block/lantern_hanging'}};write(A/'models/block'/f'{id}.json',{'parent':'minecraft:block/lantern'})
 else:
  if id.endswith(('_sapling','_leaves')):base['render_type']='minecraft:cutout'
  write(A/'models/block'/f'{id}.json',base)
  if id in machines:states={f'facing={side}':{'model':defaultmodel,'y':rot} for side,rot in [('north',0),('east',90),('south',180),('west',270)]}
  else:states={'':{'model':defaultmodel}}
 if states:write(A/'blockstates'/f'{id}.json',{'variants':states})
 if not id.startswith('crop_'):write(A/'models/item'/f'{id}.json',{'parent':defaultmodel})
write(A/'lang/en_us.json',lang)
(R/'tools/asset_migration_report.json').write_text(json.dumps({'fallback_visuals':missing},indent=2))
print('Asset fallback list:',missing)

# R2 renderer and connected-pipe models override legacy static fallbacks.
for p in (R/"tools/asset_overrides").rglob("*.json"):
 target=A/p.relative_to(R/"tools/asset_overrides");target.parent.mkdir(parents=True,exist_ok=True);shutil.copy2(p,target)

# R4 gameplay and English labels layer over the pinned legacy import.
import runpy
runpy.run_path(str(R/'tools/generate_extensions.py'),run_name='__main__')

# R5: legacy block meshes do not inherit modern item display transforms.
import runpy
runpy.run_path(str(R/"tools/fix_item_transforms.py"))
