from pathlib import Path
import json,re,collections
R=Path(__file__).resolve().parents[1];RES=R/'src/main/resources';N='foundations_calculator'
fields=json.loads((R/'tools/legacy_fields.json').read_text());es=json.loads((R/'tools/content_catalog.json').read_text());rs=json.loads((R/'tools/upstream_recipes.json').read_text());craft=json.loads((R/'tools/upstream_crafting.json').read_text())
colors='white orange magenta light_blue yellow lime pink gray light_gray cyan purple blue brown green red black'.split();woods='oak spruce birch jungle acacia dark_oak'.split();corecolors='normal black blue brown cyan green light_blue light_grey lime magenta orange pink plain purple red yellow'.split()
for stem,base in [('stableStone','stable_stone'),('stablestonerimmedBlock','stable_stone_rimmed'),('stablestonerimmedblackBlock','stable_stone_black_rimmed')]:
 for i,c in enumerate(corecolors):fields[f'SonarCore.{stem}[{i}]']=base+'_'+c
oremap={'blockGlass':'c:glass_blocks','blockLapis':'c:storage_blocks/lapis','blockRedstone':'c:storage_blocks/redstone','cobblestone':'c:cobblestones','cropCarrot':'c:crops/carrot','cropPotato':'c:crops/potato','cropWheat':'c:crops/wheat','dustGlowstone':'c:dusts/glowstone','dustRedstone':'c:dusts/redstone','gemDiamond':'c:gems/diamond','gemEmerald':'c:gems/emerald','gemLapis':'c:gems/lapis','ingotGold':'c:ingots/gold','ingotIron':'c:ingots/iron','logWood':'minecraft:logs','plankWood':'minecraft:planks','oreGold':'c:ores/gold','oreIron':'c:ores/iron','oreRedstone':'c:ores/redstone','sand':'c:sands','sandstone':'c:sandstone/blocks','slimeball':'c:slime_balls','stone':'c:stones','treeLeaves':'minecraft:leaves','treeSapling':'minecraft:saplings','stickWood':'c:rods/wooden','glowstone':'c:storage_blocks/glowstone','gemAmethyst':N+':gems/amethyst','gemTanzanite':N+':gems/tanzanite','ingotGlowstone':'c:ingots/glowstone','ingotRedstone':N+':ingots/redstone','dustEnrichedGold':N+':dusts/enriched_gold','calculatorLeaves':N+':leaves','calculatorReinforcedStone':N+':reinforced_stone','sonarStableStone':N+':stable_stone'}
fallback={'blockGlass':'minecraft:glass','blockLapis':'minecraft:lapis_block','blockRedstone':'minecraft:redstone_block','cobblestone':'minecraft:cobblestone','cropCarrot':'minecraft:carrot','cropPotato':'minecraft:potato','cropWheat':'minecraft:wheat','dustGlowstone':'minecraft:glowstone_dust','dustRedstone':'minecraft:redstone','gemDiamond':'minecraft:diamond','gemEmerald':'minecraft:emerald','gemLapis':'minecraft:lapis_lazuli','ingotGold':'minecraft:gold_ingot','ingotIron':'minecraft:iron_ingot','logWood':'minecraft:oak_log','plankWood':'minecraft:oak_planks','oreGold':'minecraft:gold_ore','oreIron':'minecraft:iron_ore','oreRedstone':'minecraft:redstone_ore','sand':'minecraft:sand','sandstone':'minecraft:sandstone','slimeball':'minecraft:slime_ball','stone':'minecraft:stone','treeLeaves':'minecraft:oak_leaves','treeSapling':'minecraft:oak_sapling','stickWood':'minecraft:stick','glowstone':'minecraft:glowstone','gemAmethyst':N+':small_amethyst','gemTanzanite':N+':small_tanzanite','ingotRedstone':N+':redstone_ingot','dustEnrichedGold':N+':enriched_gold','calculatorLeaves':N+':amethyst_leaves','calculatorReinforcedStone':N+':reinforced_stone_block','sonarStableStone':N+':stable_stone_normal'}
def write(path,obj):
 p=RES/path;p.parent.mkdir(parents=True,exist_ok=True);p.write_text(json.dumps(obj,indent=2)+'\n')
def vanilla(field,meta):
 name=field.split('.')[1].lower()
 if name in ['carpet','wool','stained_glass','stained_glass_pane','stained_hardened_clay']:return colors[meta]+'_'+('terracotta' if name=='stained_hardened_clay' else name)
 if name in ['log','log2','leaves','leaves2','planks','sapling']:
  suffix={'log':'log','log2':'log','leaves':'leaves','leaves2':'leaves','planks':'planks','sapling':'sapling'}[name];return woods[meta+(4 if name.endswith('2') else 0)]+'_'+suffix
 if name=='dye':return ['ink_sac','red_dye','green_dye','cocoa_beans','lapis_lazuli','purple_dye','cyan_dye','light_gray_dye','gray_dye','pink_dye','lime_dye','yellow_dye','light_blue_dye','magenta_dye','orange_dye','bone_meal'][meta]
 if name=='red_flower':return ['poppy','blue_orchid','allium','azure_bluet','red_tulip','orange_tulip','white_tulip','pink_tulip','oxeye_daisy'][meta]
 if name=='double_plant':return ['sunflower','lilac','tall_grass','large_fern','rose_bush','peony'][meta]
 if name=='tallgrass':return {1:'short_grass',2:'fern'}[meta]
 if name=='skull':return ['skeleton_skull','wither_skeleton_skull','zombie_head','player_head','creeper_head','dragon_head'][meta]
 if name=='sandstone':return ['sandstone','chiseled_sandstone','cut_sandstone'][meta]
 if name=='stonebrick':return ['stone_bricks','mossy_stone_bricks','cracked_stone_bricks','chiseled_stone_bricks'][meta]
 if name=='dirt':return ['dirt','coarse_dirt','podzol'][meta]
 return {'grass':'grass_block','nether_brick':'nether_bricks','noteblock':'note_block','bed':'red_bed','netherbrick':'nether_brick','reeds':'sugar_cane','melon':'melon_slice'}.get(name,name)
def itemid(a):
 if 'ore'in a:return fallback[a['ore']]
 field=a['item'];meta=a.get('meta',0)
 if field.startswith(('Items.','Blocks.')):return 'minecraft:'+vanilla(field,meta)
 return N+':'+fields.get(field+':'+str(meta),fields.get(field,'MISSING:'+field))
def ingredient(a):
 if isinstance(a,str):a={'ore':a}
 return {'tag':oremap[a['ore']]} if 'ore'in a else {'item':itemid(a)}
def counted(a):
 d={'ingredient':ingredient(a),'count':a.get('count',1)}
 if a.get('analysed'):d['circuit_state']=2 if a.get('stable') else 1
 return d
def stack(a):return {'id':itemid(a),'count':a.get('count',1)}
def output(a):
 if 'random_circuit'in a:
  value=a['random_circuit'];value={**value,'meta':0};return {'stack':stack(value),'chance':a['chance'],'random_circuit':fields[value['item']][:-2]}
 return {'stack':stack(a)}
types={'CalculatorRecipes':('calculator',2,1,0,1),'ScientificRecipes':('scientific',2,1,0,1),'AtomicCalculatorRecipes':('atomic',3,1,0,1),'FlawlessCalculatorRecipes':('flawless',4,1,0,1),'StoneSeparatorRecipes':('stone_separator',1,2,500,200),'AlgorithmSeparatorRecipes':('algorithm_separator',1,2,5000,200),'ExtractionChamberRecipes':('extraction_chamber',1,2,5000,1000),'PrecisionChamberRecipes':('precision_chamber',1,2,5000,500),'RestorationChamberRecipes':('restoration_chamber',1,1,1000,1000),'ReassemblyChamberRecipes':('reassembly_chamber',1,1,1000,1000),'ProcessingChamberRecipes':('processing_chamber',1,1,1000,500),'ConductorMastRecipes':('conductor_mast',1,1,0,50),'HealthProcessorRecipes':('health_processor',1,0,0,1),'StarchExtractorRecipes':('starch_extractor',1,0,0,1),'RedstoneExtractorRecipes':('redstone_extractor',1,0,0,1),'GlowstoneExtractorRecipes':('glowstone_extractor',1,0,0,1),'TreeHarvestRecipes':('harvest',1,2,0,1),'FabricationChamberRecipes':('fabrication_chamber',0,1,1000,200)}
analysis=[];skips=[];totals=collections.Counter()
for index,r in enumerate(rs):
 t=r['type'];args=r['args'];name=t.removesuffix('Recipes')
 if t=='AnalysingChamberRecipes':analysis.append({'category':args[0],'roll':args[1],'result':stack(args[2])});continue
 machine,ni,no,energy,ticks=types[t]
 if t=='CalculatorRecipes':ins=args[1:3];outs=args[:1]
 elif t=='FabricationChamberRecipes':ins=args[1:];outs=args[:1]
 else:ins=args[:ni];outs=args[ni:ni+no]
 if None in ins or any(not isinstance(x,dict) or not ('item' in x or 'ore' in x) for x in ins):skips.append({'source_index':index,'reason':'Original declaration does not contain the required number of item inputs','recipe':r});continue
 value=args[-1] if isinstance(args[-1],int) else 0
 if t=='ConductorMastRecipes':energy=value;value=0
 d={'type':N+':process','machine':machine,'ingredients':[counted(x) for x in ins],'results':[output(x) for x in outs],'energy':energy,'ticks':ticks,'value':value}
 d['research']=any(isinstance(x,dict) and x.get('research','NONE')!='NONE' for x in args)
 write(Path('data')/N/'recipe'/machine/f'{index:04d}.json',d);totals[machine]+=1
# The reward table is read server-side; no random rolls or rewards are client-authoritative.
write(Path('data')/N/'analysis_rewards.json',analysis)
for index,r in enumerate(craft):
 args=r['args'];result=stack(r['output'])
 if r['type']=='shaped':
  pattern=[];cursor=0
  while cursor<len(args) and isinstance(args[cursor],str) and (len(args[cursor])>1 or cursor==0):pattern.append(args[cursor]);cursor+=1
  keys={args[i]:ingredient(args[i+1]) for i in range(cursor,len(args),2)}
  # Trim empty rows/columns just like vanilla's pattern parser.
  while pattern and not pattern[0].strip():pattern.pop(0)
  while pattern and not pattern[-1].strip():pattern.pop()
  used=[i for line in pattern for i,c in enumerate(line) if c!=' '];lo,hi=min(used),max(used)+1;pattern=[line[lo:hi] for line in pattern]
  d={'type':'minecraft:crafting_shaped','category':'misc','pattern':pattern,'key':keys,'result':result}
 else:d={'type':'minecraft:crafting_shapeless','category':'misc','ingredients':[ingredient(a) for a in args],'result':result}
 write(Path('data')/N/'recipe'/'crafting'/f'{index:03d}_{result["id"].split(":")[1]}.json',d)
for inp,out,xp in [('enriched_gold','enriched_gold_ingot',.8),('broccoli','cooked_broccoli',.2)]:
 write(Path('data')/N/'recipe'/f'smelt_{inp}.json',{'type':'minecraft:smelting','category':'misc','ingredient':{'item':N+':'+inp},'result':{'id':N+':'+out},'experience':xp,'cookingtime':200})
# Self-sufficient tag defaults; other mods and datapacks may add values.
values=collections.defaultdict(list)
for ore,tag in oremap.items():
 if ore in fallback:values[tag].append(fallback[ore])
for e in es:
 id=e['id'];full=N+':'+id
 for cond,tag in [(id.endswith('_log'),'minecraft:logs'),(id.endswith('_planks'),'minecraft:planks'),(id.endswith('_leaves'),'minecraft:leaves'),(id.endswith('_sapling'),'minecraft:saplings'),(id.startswith('stable_stone'),N+':stable_stone'),('glass' in id,'c:glass_blocks')]:
  if cond:values[tag].append(full)
values['c:ores/iron']+=['minecraft:deepslate_iron_ore'];values['c:ores/gold']+=['minecraft:deepslate_gold_ore'];values['c:ores/redstone']+=['minecraft:deepslate_redstone_ore']
values['c:cobblestones']+=['minecraft:cobbled_deepslate'];values['c:sands']+=['minecraft:red_sand'];values['c:sandstone/blocks']+=['minecraft:red_sandstone']
for wood in woods:
 for suffix,tag in [('log','minecraft:logs'),('planks','minecraft:planks'),('leaves','minecraft:leaves'),('sapling','minecraft:saplings')]:values[tag].append('minecraft:'+wood+'_'+suffix)
for tag,vals in values.items():
 ns,path=tag.split(':');write(Path('data')/ns/'tags/item'/f'{path}.json',{'replace':False,'values':list(dict.fromkeys(vals))})
# Block tool tags, flammability classifications, and leaves connectivity.
for tag,ids in {
 'minecraft:mineable/pickaxe':[e['id'] for e in es if e['kind']=='block' and not e['id'].startswith('crop_') and not any(e['id'].endswith('_'+s) for s in ['log','leaves','sapling','planks','fence','stairs','gate'])],
 'minecraft:logs':[e['id'] for e in es if e['id'].endswith('_log')],
 'minecraft:leaves':[e['id'] for e in es if e['id'].endswith('_leaves')],
 'minecraft:mineable/axe':[e['id'] for e in es if e['id'].endswith(('_log','_planks','_fence','_gate','_stairs'))],
 'minecraft:fences':[e['id'] for e in es if e['id'].endswith('_fence')],
 'minecraft:fence_gates':[e['id'] for e in es if e['id'].endswith('_gate')]
}.items():
 ns,path=tag.split(':');write(Path('data')/ns/'tags/block'/f'{path}.json',{'replace':False,'values':[N+':'+id for id in ids]})
for e in es:
 if e['kind']!='block':continue
 id=e['id'];conditions=[]
 if id.startswith('crop_'):
  seed,produce={'crop_broccoli':('broccoli_seeds','broccoli'),'crop_prunae':('prunae_seeds','coal_dust'),'crop_fiddledew':('fiddledew_fruit','fiddledew_fruit')}[id]
  pools=[{'rolls':1,'entries':[{'type':'minecraft:item','name':N+':'+seed}]}]
  pools.append({'rolls':1,'entries':[{'type':'minecraft:item','name':N+':'+produce}],'conditions':[{'condition':'minecraft:block_state_property','block':N+':'+id,'properties':{'age':'7'}}]})
 elif id.endswith('_leaves'):
  silk={'condition':'minecraft:any_of','terms':[{'condition':'minecraft:match_tool','predicate':{'items':['minecraft:shears']}},{'condition':'minecraft:match_tool','predicate':{'predicates':{'minecraft:enchantments':[{'enchantments':'minecraft:silk_touch','levels':{'min':1}}]}}}]}
  pools=[{'rolls':1,'entries':[{'type':'minecraft:alternatives','children':[{'type':'minecraft:item','name':N+':'+id,'conditions':[silk]},{'type':'minecraft:item','name':N+':'+id.replace('_leaves','_sapling'),'conditions':[{'condition':'minecraft:random_chance','chance':.05}]}]}]}]
 else:pools=[{'rolls':1,'entries':[{'type':'minecraft:item','name':N+':'+id}]}]
 write(Path('data')/N/'loot_table/blocks'/f'{id}.json',{'type':'minecraft:block','pools':pools})
for name in ['amethyst','tanzanite','pear','diamond']:
 write(Path('data')/N/'worldgen/configured_feature'/f'{name}_tree.json',{'type':'minecraft:tree','config':{
  'trunk_provider':{'type':'minecraft:simple_state_provider','state':{'Name':N+':'+name+'_log','Properties':{'axis':'y'}}},
  'trunk_placer':{'type':'minecraft:straight_trunk_placer','base_height':4,'height_rand_a':2,'height_rand_b':1},
  'foliage_provider':{'type':'minecraft:simple_state_provider','state':{'Name':N+':'+name+'_leaves','Properties':{'distance':'7','persistent':'false','waterlogged':'false',**({'age':'0'} if name in ['pear','diamond','amethyst','tanzanite'] else {})}}},
  'foliage_placer':{'type':'minecraft:blob_foliage_placer','radius':2,'offset':0,'height':3},
  'minimum_size':{'type':'minecraft:two_layers_feature_size','limit':1,'lower_size':0,'upper_size':1},
  'dirt_provider':{'type':'minecraft:simple_state_provider','state':{'Name':'minecraft:dirt'}},'decorators':[],'ignore_vines':True,'force_dirt':False}})
# Analysis rewards use the same public recipe type as processing, including FE rolls.
write(Path('data')/N/'tags/item/circuit_boards.json',{'replace':False,'values':[N+':circuit_board_'+str(i) for i in range(14)]})
for index,a in enumerate(analysis):
 machine='analysis_'+str(a['category'])
 write(Path('data')/N/'recipe'/machine/f'{index:03d}.json',{'type':N+':process','machine':machine,'ingredients':[{'ingredient':{'tag':N+':circuit_boards'}}],'results':[{'stack':a['result']}],'value':a['roll'],'energy':0,'ticks':1})
 totals[machine]+=1
for roll,energy in enumerate([0,1000,500,250,10000,5000,100000,100,175,400,750,800]):
 if not roll:continue
 write(Path('data')/N/'recipe/analysis_0'/f'{roll:03d}.json',{'type':N+':process','machine':'analysis_0','ingredients':[{'ingredient':{'tag':N+':circuit_boards'}}],'results':[],'value':roll,'energy':energy,'ticks':1})
 totals['analysis_0']+=1
(R/'tools/recipe_migration_report.json').write_text(json.dumps({'process_counts':dict(totals),'crafting_count':len(craft),'analysis_rewards':len(analysis),'skipped_invalid_declarations':skips},indent=2))
print('Process recipes',sum(totals.values()),'crafting',len(craft),'rewards',len(analysis),'invalid legacy declarations',len(skips))

# R4 gameplay and English labels layer over the pinned legacy import.
import runpy
runpy.run_path(str(R/'tools/generate_extensions.py'),run_name='__main__')
