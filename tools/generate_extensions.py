from pathlib import Path
import json,runpy,copy
p=Path(__file__).resolve().parents[1];N='foundations_calculator';res=p/'src/main/resources';
def write(path,data):
 f=res/path;f.parent.mkdir(parents=True,exist_ok=True);f.write_text(json.dumps(data,indent=2)+'\n')
rdir=Path('data')/N/'recipe'
# Preserve the original category on every research conversion.
source=json.loads((p/'tools/upstream_recipes.json').read_text())
for f in (res/rdir/'calculator').glob('*.json'):
 r=json.loads(f.read_text());i=int(f.stem);groups=[a['research'].lower() for a in source[i]['args'] if isinstance(a,dict) and 'research'in a and a['research']!='NONE']
 if groups:r['research_group']=groups[0];f.write_text(json.dumps(r,indent=2)+'\n')
# Repair the five malformed declarations by repeating the supplied input.
# This preserves two-input conversion balance, avoiding the old 1-to-2 duplication typo.
fixes={145:('small_stone','minecraft:stone',1,'stone'),147:('soil','minecraft:dirt',1,'dirt'),312:('minecraft:oak_planks','minecraft:dark_oak_planks',2,'planks'),359:('minecraft:birch_leaves','minecraft:acacia_leaves',2,'leaves'),362:('minecraft:oak_leaves','minecraft:dark_oak_leaves',2,'leaves')}
for i,(inp,out,n,g) in fixes.items():
 inp=inp if ':'in inp else N+':'+inp
 write(rdir/'calculator'/f'{i:04d}.json',dict(type=N+':process',machine='calculator',ingredients=[{'ingredient':{'item':inp},'count':2}],results=[{'stack':{'id':out,'count':n}}],energy=0,ticks=1,value=0,research=True,research_group=g))
groups={
 'reinforced_stone':[N+':reinforced_stone_block',N+':reinforced_dirt_block'],
 'crop':['minecraft:wheat','minecraft:potato','minecraft:carrot','#c:crops'],
 'slimeball':['#c:slime_balls'],'iron':['#c:ores/iron','#c:ingots/iron','#c:storage_blocks/iron'],
 'emerald':['#c:ores/emerald','#c:gems/emerald','#c:storage_blocks/emerald'],
 'flower':['#minecraft:flowers'],'cactus':['minecraft:cactus'],'nether':['minecraft:obsidian','minecraft:netherrack','minecraft:soul_sand'],
 'end':['minecraft:end_stone','minecraft:ender_pearl'],'sand':['#c:sands'],'grass':['minecraft:grass_block'],
 'sandstone':['#c:sandstone/blocks'],'dirt':['#minecraft:dirt'],'stone':['#c:stones'],'gravel':['minecraft:gravel'],
 'cobblestone':['#c:cobblestones'],'wood':['#minecraft:logs'],'planks':['#minecraft:planks','#minecraft:wooden_stairs'],
 'leaves':['#minecraft:leaves'],'sapling':['#minecraft:saplings'],'glass':['#c:glass_blocks'],'glass_pane':['#c:glass_panes'],
 'furnace':['minecraft:furnace'],'dye':['#c:dyes'],'carpet':['#minecraft:wool_carpets'],'clay_block':['minecraft:clay_ball','minecraft:clay','minecraft:terracotta'],
 'foliage':['minecraft:dead_bush','minecraft:vine','minecraft:lily_pad','minecraft:tall_grass','minecraft:large_fern','#minecraft:tall_flowers'],
 'wool':['#minecraft:wool']}
for g,items in groups.items():
 ingredient=[({'tag':i[1:]}if i.startswith('#')else{'item':i})for i in items]
 write(rdir/'research'/f'{g}.json',dict(type=N+':process',machine='research',ingredients=[{'ingredient':ingredient}],results=[],energy=1000,ticks=100,research_group=g))
# New feature acquisition recipes are explicit R4 additions.
craft={
 'research_chamber':(['IRI','CBC','IRI'],{'I':N+':reinforced_iron_ingot','R':'minecraft:redstone','C':N+':circuit_board_0','B':'minecraft:bookshelf'}),
 'smelting_module':(['IRI','CFC','IRI'],{'I':N+':reinforced_iron_ingot','R':'minecraft:redstone','C':N+':calculator_assembly','F':N+':reinforced_furnace'}),
 'atomic_terrain_module':(['IDI','CAC','ITI'],{'I':N+':reinforced_iron_ingot','D':N+':flawless_diamond','C':N+':calculator_assembly','A':N+':atomic_module','T':N+':advanced_terrain_module'})}
for id,(pattern,keys)in craft.items():write(rdir/'crafting'/('r4_'+id+'.json'),{'type':'minecraft:crafting_shaped','category':'misc','pattern':pattern,'key':{k:{'item':v}for k,v in keys.items()},'result':{'id':N+':'+id}})
assets=Path('assets')/N
write(assets/'blockstates/research_chamber.json',{'variants':{f'facing={f}':{'model':N+':block/research_chamber','y':i*90}for i,f in enumerate(['north','east','south','west'])}})
write(assets/'models/block/research_chamber.json',{'parent':'minecraft:block/cube','textures':{'particle':N+':legacy_calculator/model/analysing_chamber_side_1','up':N+':legacy_calculator/model/analysing_chamber_slot1','down':N+':legacy_calculator/model/analysing_chamber2_slot1','north':N+':legacy_calculator/model/fabrication_chamber_front','east':N+':legacy_calculator/model/analysing_chamber_side_1','south':N+':legacy_calculator/model/analysing_chamber_side_1','west':N+':legacy_calculator/model/analysing_chamber_side_1'}})
write(assets/'models/item/research_chamber.json',{'parent':N+':block/research_chamber'})
write(assets/'models/item/smelting_module.json',{'parent':N+':item/crafting_calculator'})
write(assets/'models/item/atomic_terrain_module.json',{'parent':N+':item/advanced_terrain_module'})
write(Path('data')/N/'loot_table/blocks/research_chamber.json',{'type':'minecraft:block','pools':[{'rolls':1,'entries':[{'type':'minecraft:item','name':N+':research_chamber'}]}]})
f=res/assets/'lang/en_us.json';d=json.loads(f.read_text());d['config.jade.plugin_foundations_calculator.machine_status']='Foundations machine status';d.update({f'block.{N}.research_chamber':'Research Chamber',f'item.{N}.smelting_module':'Smelting Module',f'item.{N}.atomic_terrain_module':'Atomic Terrain Module'});f.write_text(json.dumps(d,indent=2)+'\n')
for path in [res/'foundations/content-properties.json',p/'tools/content_catalog.json']:
 data=json.loads(path.read_text())
 if isinstance(data,list):
  for id,kind in [('research_chamber','block'),('smelting_module','item'),('atomic_terrain_module','item')]:
   if not any(e['id']==id for e in data):data.append({'id':id,'kind':kind,'field':'Foundations.'+id,'legacy':id,'constructor':'R4 revival'})
 else:
  data['blocks']['research_chamber']={}
  for id in craft:data['items'][id]={}
 path.write_text(json.dumps(data,indent=2)+'\n')
f=res/'data'/N/'kubejs/recipe_schema/process.json';d=json.loads(f.read_text());
if not any(k['name']=='research_group'for k in d['keys']):d['keys'].append({'name':'research_group','type':'string','optional':'general'})
f.write_text(json.dumps(d,indent=2)+'\n')
print('Added research groups, repaired conversions, recipes and content resources.')

# Keep modern block item transforms after standalone data generation.
runpy.run_path(str(p/"tools/fix_item_transforms.py"))
