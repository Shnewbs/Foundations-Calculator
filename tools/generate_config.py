from pathlib import Path
import json,re
p=Path(__file__).resolve().parents[1];rows=[]
def add(key,default,minimum=None,maximum=None,description=''):
 r=dict(key=key,default=default,description=description or key.replace('.',' / '));
 if minimum is not None:r.update(min=minimum,max=maximum)
 rows.append(r)
# Preserve existing world-config keys.
for key,d,lo,hi in [('machines.crankEnergyPerUse',160,1,1000000),('machines.energyTransferPerTick',0,0,2147483647),('machines.processEnergyMultiplier',1.0,0.0,100.0),('machines.processTimeMultiplier',1.0,.01,100.0),('machines.scarecrowInterval',500,1,1000000),('machines.scarecrowRange',3,1,32),('machines.atomicMultiplierEnergy',1500000000,1,1500000000)]:add(key,d,lo,hi)
for key,d in [('machines.allowGrenades',True),('machines.unstableLocatorChangesTime',True),('machines.unstableLocatorAffectsOwner',True),('enableLegacyResearchRecipes',False)]:add(key,d)
s=(p/'src/main/java/com/foundations/calculator/content/Content.java').read_text();machines=re.findall(r'addBlock\("([^"]+)".*?,true,false\);',s);machines+=['research_chamber']
capacity={'basic_greenhouse':350000,'advanced_greenhouse':350000,'flawless_greenhouse':500000,'co2_generator':1000000,'weather_controller':1000000,'atomic_multiplier':1500000000,'calculator_locator':50000000,'hand_cranked_generator':1000,'power_cube':50000,'advanced_power_cube':100000,'creative_power_cube':2147483647,'analysing_chamber':100000,'conductor_mast':50000000,'starch_extractor':1000000,'redstone_extractor':1000000,'glowstone_extractor':1000000}
transfers={'power_cube':400,'hand_cranked_generator':400,'basic_greenhouse':400,'advanced_power_cube':64000,'flawless_greenhouse':64000,'co2_generator':64000,'weather_controller':64000,'analysing_chamber':12800,'atomic_calculator':12800,'atomic_multiplier':2147483647,'calculator_locator':2147483647,'conductor_mast':2147483647,'creative_power_cube':2147483647}
charges={'power_cube':4,'advanced_power_cube':100000,'conductor_mast':100000,'calculator_locator':5000000,'creative_power_cube':2147483647}
for name in sorted(set(machines)):
 k='machine.'+name+'.'
 cost_machines=set('atomic_calculator docking_station reinforced_furnace stone_separator algorithm_separator extraction_chamber restoration_chamber reassembly_chamber precision_chamber processing_chamber analysing_chamber fabrication_chamber conductor_mast dynamic_calculator basic_greenhouse advanced_greenhouse flawless_greenhouse co2_generator weather_controller atomic_multiplier research_chamber'.split())
 timed=cost_machines-{'dynamic_calculator','co2_generator'}
 retains=set('atomic_calculator docking_station reinforced_furnace stone_separator algorithm_separator extraction_chamber restoration_chamber reassembly_chamber precision_chamber processing_chamber analysing_chamber fabrication_chamber research_chamber weather_controller atomic_multiplier'.split())
 add(k+'enabled',True,description='Allow this machine to operate. Existing inventory and block remain recoverable.')
 for field,d,lo,hi in [('capacity',capacity.get(name,50000),1,2147483647),('transferRate',transfers.get(name,3200),1,2147483647),('chargeRate',charges.get(name,transfers.get(name,3200)),1,2147483647),('energyOverride',-1,-1,2147483647),('ticksOverride',-1,-1,1000000),('energyMultiplier',1.0,0.0,100.0),('timeMultiplier',1.0,.01,100.0),('defaultRedstoneMode',0,0,3)]:
  if field in {'energyOverride','energyMultiplier'} and name not in cost_machines:continue
  if field in {'ticksOverride','timeMultiplier'} and name not in timed:continue
  add(k+field,d,lo,hi,{'energyOverride':'-1 uses recipe or built-in cost. 0 makes processing free.','ticksOverride':'-1 uses recipe or built-in duration. Positive values override ticks.','defaultRedstoneMode':'0 always; 1 signal required; 2 signal disables; 3 paused.'}.get(field,''))
 add(k+'capacityMultiplier',1.0,.01,1000000.0,'Per-machine long-valued FE capacity multiplier applied after the base capacity.')
 for field,d in [('itemAutomation',True),('energyInput',True),('energyOutput',True),('retainProgressWithoutPower',True)]:
  if field=='retainProgressWithoutPower' and name not in retains:continue
  add(k+field,d)
items=json.loads((p/'tools/content_catalog.json').read_text());ids=sorted({e['id'] for e in items}|{'research_chamber','smelting_module','atomic_terrain_module'})
for name in ids:add('content.'+name+'.enabled',True,description='Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing.')
for name,base in [('calculator',1000),('scientific_calculator',2000),('flawless_calculator',1000000),('crafting_calculator',5000),('terrain_module',400),('advanced_terrain_module',2000),('atomic_terrain_module',20000),('smelting_module',50000),('energy_module',100000),('warp_module',10000),('jump_module',10000)]:
 add('module.'+name+'.capacity',base,1,100000000)
 add('module.'+name+'.capacityMultiplier',1.0,.01,1000000.0,'Per-item/module long-valued FE capacity multiplier.')
ints={'module.crafting_calculator.cost':1,'module.terrain_module.cost':1,'module.advanced_terrain_module.cost':1,'module.atomic_terrain_module.cost':1,'module.smelting_module.cost':1000,'module.smelting_module.ticks':1000,'module.warp_module.cost':1000,'module.jump_module.cost':100,'module.ender_pearl.cost':1000,'module.grenade.cost':10000,'module.end_diamond.cooldown':10,'module.ender_pearl.cooldown':10,'module.grenade.cooldown':10,'module.jump_module.cooldown':0,'module.warp_module.cooldown':0,'module.flawless_calculator.maxModules':16,'nutrition.healthCapacity':1000,'nutrition.hungerCapacity':1000,'nutrition.combinedHealthCapacity':2147483647,'nutrition.combinedHungerCapacity':2147483647,'nutrition.restoreInterval':10,'nutrition.automaticRestoreLimit':2,'nutrition.manualRestoreLimit':20,'nutrition.machineTransfer':4,'nutrition.networkInterval':20,'nutrition.networkMaxVisited':512,'nutrition.leafYield':1,'upgrades.maxPerType':16,'upgrades.itemsPerTransfer':4,'upgrades.maxTransfer':64,'generation.extractorNutrientCapacity':5000,'generation.extractorNutrientCost':400,'generation.starchPerTick':40,'generation.redstonePerTick':80,'generation.glowstonePerTick':160,'generation.crankHandleCooldown':18,'generation.mastBaseWait':1500,'generation.mastMinWait':250,'generation.mastWaitReduction':135,'generation.mastRandomWait':300,'generation.mastBurstTicks':200,'generation.mastBaseGeneration':25,'generation.weatherStationBonus':5,'generation.mastRadius':20,'generation.weatherStationRadius':10,'generation.structureCheckInterval':25,'generation.locatorMaxRadius':11,'generation.locatorStableThreshold':7,'generation.locatorTimeThreshold':5,'generation.locatorTimeAdvance':100,'generation.locatorEffectInterval':50,'generation.locatorEffectDuration':1000,'greenhouse.buildEnergy':100,'greenhouse.plantEnergy':50,'greenhouse.growEnergy':150,'greenhouse.harvestEnergy':150,'greenhouse.farmlandEnergy':50,'greenhouse.waterEnergy':1000,'greenhouse.structureCheckInterval':20,'greenhouse.carbonInterval':20,'greenhouse.maxFlawlessLength':64,'greenhouse.basicPlantInterval':60,'greenhouse.advancedPlantInterval':10,'greenhouse.flawlessPlantInterval':2,'greenhouse.lanternCarbon':50,'greenhouse.dayCarbonUse':8,'greenhouse.nightCarbonGain':2,'greenhouse.co2FuelTicks':10000,'greenhouse.co2FuelEnergy':100000,'greenhouse.controlledMinimumCarbon':92000,'greenhouse.controlledMaximumCarbon':100000,'greenhouse.lanternBurnMultiplier':10,'world.weatherEnergyPerTick':2500,'world.weatherDuration':100,'world.weatherCooldown':30,'world.weatherHoldTicks':12000,'world.magnetRadius':10,'world.assimilatorInterval':30,'world.assimilatorMinimumLeaves':10,'world.assimilatorScanRadius':2,'world.assimilatorScanHeight':7,'world.atomicMultiplierTicks':1000,'world.atomicMultiplierCopies':4,'world.dynamicStructureRadius':3,'world.dynamicCheckInterval':20,'world.dynamicInterval':1,'plants.leafMatureAge':2,'plants.leafGrowthChance':8,'plants.leafHarvestResetAge':0,'plants.prunaeMinimumTier':2,'plants.fiddledewMinimumTier':3,'plants.broccoliMinimumTier':0,'circuits.stabilityRoll':6,'circuits.energyRoll':200,'circuits.item1Roll':50,'circuits.item2Roll':100,'circuits.item3Roll':1000,'circuits.item4Roll':2000,'circuits.item5Roll':10000,'circuits.item6Roll':20000,'storage.reinforcedChestPerBin':256,'storage.circuitChamberPerBin':1024,'storage.assimilatorPerBin':64,'storage.moduleSlots':54,'tools.fireSwordSeconds':4,'tools.obsidianKeyDamage':1,'fuel.redstone':1000,'fuel.coal':500,'fuel.charcoal':500,'fuel.coal_block':4500,'fuel.redstone_block':9000}
zero={'cost','cooldown','Energy','PerTick','TimeAdvance','Bonus','leafYield','lanternCarbon','dayCarbonUse','nightCarbonGain','leafHarvestResetAge','broccoliMinimumTier','obsidianKeyDamage','fireSwordSeconds','energy'}
for key,d in ints.items():
 lo=0 if any(key.endswith(s) for s in zero) else 1
 hi=2147483647
 if 'Radius' in key:hi=64
 if key=='world.assimilatorScanHeight':hi=64
 if key=='nutrition.leafYield':hi=64
 if key.startswith('fuel.'):lo=0
 if key.startswith('greenhouse.controlled'):hi=100000
 if key=='module.flawless_calculator.maxModules':hi=16
 if key=='storage.moduleSlots':hi=54
 if key=='plants.leafMatureAge' or key=='plants.leafHarvestResetAge':hi=4
 if key.endswith('MinimumTier'):hi=3
 if key=='greenhouse.maxFlawlessLength':hi=64
 if key=='world.dynamicStructureRadius':lo=1;hi=8
 if key=='generation.locatorMaxRadius':hi=32
 if key=='nutrition.networkMaxVisited':hi=65536
 if key=='world.atomicMultiplierCopies':hi=64
 add(key,d,lo,hi)
for key,d,lo,hi in [('module.jump_module.velocity',1.0,.01,4.0),('module.ender_pearl.velocity',1.5,.1,5.0),('module.grenade.velocity',1.5,.1,5.0),('module.end_diamond.velocity',1.5,.1,5.0),('nutrition.foodSaturation',.2,0.0,20.0),('upgrades.speedBonus',.25,0.0,100.0),('upgrades.energyDiscount',.1,0.0,100.0),('generation.locatorMultiplier',2.0,0.0,100.0),('generation.mastMultiplier',4.0,0.0,100.0),('world.magnetSpeed',.15,.01,2.0),('world.grenadeStrength',5.0,0.0,32.0),('world.babyGrenadeStrength',1.0,0.0,32.0),('world.locatorExplosionMultiplier',1.0,0.0,8.0),('compat.aeEnergyToFE',2.0,.0001,1000000.0)]:add(key,d,lo,hi)
for key,d in [('world.chestSounds',True),('module.end_diamond.enabled',True),('module.warp_module.crossDimension',False),('module.warp_module.loadDestination',False),('module.warp_module.requireStableBlock',True),('module.atomic_terrain_module.requireReplacementItem',True),('module.atomic_terrain_module.dropOriginal',True),('module.smelting_module.backgroundProcessing',True),('tools.wrenchDismantle',True),('tools.wrenchSideCycling',True),('tools.wrenchPreserveMachine',True),('plants.diamondSaplingConsumesSupport',True),('plants.diamondSaplingRequiresSupport',True),('greenhouse.autoWater',True),('greenhouse.autoFarmland',True),('greenhouse.autoPlant',True),('greenhouse.replant',True),('greenhouse.allowBuild',True),('greenhouse.allowDemolish',True),('greenhouse.acceptGenericPlants',True),('upgrades.allowVoid',True),('world.grenadeFire',True),('world.grenadeBlockDamage',True),('world.locatorBlockDamage',True),('world.mastLightningVisual',True),('world.dynamicRequiresStructure',True),('research.requireUnlock',True),('research.consumeSample',False),('research.consumeEnergy',True),('research.shareServerWide',False),('compat.jei',True),('compat.emi',True),('compat.jade',True),('compat.crafttweaker',True),('compat.ae2',True),('compat.mekanism',True),('compat.externalPlants',True),('automation.balanceCubes',True),('automation.allowBatterySlotAccess',True),('automation.allowUpgradeSlotAccess',False)]:add(key,d)
for tier,values in {'basic':[400,300,200,150,80],'advanced':[300,200,100,50,15],'flawless':[200,100,50,25,15]}.items():
 for i,n in enumerate(values):add(f'greenhouse.{tier}GrowthBand{i}',n,1,1000000)
for family,target in [('calculator',10000),('scientific',5000),('atomic',2500),('flawless',1000)]:add('research.mastery.'+family,target,1,2147483647,'Completed calculation batches needed for the mastery display; does not gate recipes.')
add('research.trackMastery',True,description='Record successful basic/scientific/atomic/flawless calculations for the research browser.')
# R5 native power systems. Zero per-machine overrides inherit the global setting.
for key in ['enabled','euToFE','feToEU']:add('compat.gtceu.'+key,True)
for key,d,lo,hi,desc in [
 ('fePerEU',0,0,1000000,'0 follows the higher of GregTech FE/EU ratios; positive fixes one symmetric ratio.'),
 ('inputVoltage',2147483647,1,2147483647,'Maximum EU packet voltage. Only complete packets fitting the FE port are accepted; excessive voltage is refused without explosions.'),
 ('outputVoltage',32,1,2147483647,'EU per outgoing packet. Default 32 is LV. Output refuses receivers/cables rated below this voltage.'),
 ('inputAmperage',4,1,1024,'Maximum EU packets accepted per machine per tick across all faces.'),
 ('outputAmperage',1,1,1024,'Maximum EU packets sent per machine per tick across all faces.'),
 ('chargerTier',1,0,14,'Highest GregTech item tier Foundations charging and discharging slots support. 1 is LV.'),
 ('itemTier',1,0,14,'Tier of Foundations powered items exposed to GregTech chargers.')]:add('compat.gtceu.'+key,d,lo,hi,desc)
for name in sorted(set(machines)):
 for field in ['InputVoltage','OutputVoltage','InputAmperage','OutputAmperage']:
  add('machine.'+name+'.eu'+field,0,0,1024 if 'Amperage' in field else 2147483647,'0 inherits the global GregTech setting.')
add('compat.modernIndustrialization',True,description='Enable the MI EU integration. Base ratio inherits MI unless power.modernIndustrialization.fePerEU overrides it.')
add('compat.grandPower',True,description='Expose and accept long-valued FE through GrandPower when present.')
# R6 policy controls: FE stays the canonical storage unit. No fixed FE/GrandPower exchange multiplier.
add('power.routing.preferNative',True,description='Prefer the native J/EU/AE item or block adapter over an external FE facade. A selected native port refusing power never falls through to FE. Foundations-to-Foundations links remain FE; GT cable output always uses native voltage-aware packets.')
add('power.diagnostics.enabled',True,description='Allow read-only /foundations power reports and the machine Power button. Reports query loaded neighbors only and never execute a transfer.')
add('power.diagnostics.cooldownTicks',20,1,1200,'Minimum ticks between player power reports; also applies to the GUI button.')
add('power.mekanism.joulesPerFE',0.0,0.0,1000000.0,'0 inherits Mekanism Joules per FE; positive sets one symmetric base ratio. Example 2.5 means 2.5 J per FE. Upstream disabled FE conversion is still respected. Long-valued APIs move whole representable quanta.')
add('power.modernIndustrialization.fePerEU',0.0,0.0,1000000.0,'0 inherits MI forgeEnergyPerEu; positive sets one symmetric base ratio. Example 4.0 means 4 FE per EU. Arbitrarily precise ratios can exceed representable storage/rate limits; diagnostics identifies blocked plans.')
for system in ['fe','gtceu','mekanism','modernIndustrialization','ae2','grandPower']:
 prefix='power.'+system+'.'
 if system!='ae2':add(prefix+'blockPorts',True,description='Enable this electrical API for Foundations machine connections and native block adapters. Other APIs have independent controls; disabling an API does not disable the physical storage.')
 add(prefix+'itemCharging',True,description='Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply.')
 add(prefix+'input',True,description='Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled.')
 add(prefix+'output',True,description='Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled.')
 if system!='fe':
  add(prefix+'inputLossPercent',0,0,99,'Percent conversion loss entering Foundations FE, after the base ratio. 0 is lossless. Fractional leftovers remain in source; fine ratios and losses may require a larger transfer quantum. Incoming GT packets round FE down.')
  add(prefix+'outputLossPercent',0,0,99,'Percent conversion loss leaving Foundations FE. 0 is lossless; cannot be negative or 100. Outgoing conversion rounds the FE cost up conservatively. Never increases round-trip energy.')
# R7 safety and tick scheduling. Protection events cannot be disabled globally.
add('protection.requireOwner',True,description='World-changing machines require an owner UUID. An authorized player can place or use an ownerless controller. Existing saved owners are retained. False uses the named Foundations fake-player identity, never an unrestricted actor.')
add('protection.allowLegacyPlantCallbacks',False,description='Opt-in for opaque external plant/bonemeal callbacks with no declared protection-aware behavior. Only the origin is checked for legacy callbacks; their additional blocks, entities and fluids are the integration responsibility. Prefer protection-aware PlantAdapter methods. Native crops keep their bounded path.')
add('performance.changeOnlySync',True,description='Send periodic block-entity packets only when client-visible fields changed. Chunk-load packets and open menu synchronization remain available.')
add('performance.clientSyncInterval',10,1,200,'Minimum periodic machine state publication interval in ticks. Idle, disabled and redstone-paused machines are still considered. Chest lid transitions publish immediately.')
add('performance.chestRecheckInterval',20,1,1200,'Validate only recorded chest openers at this interval. No world-player scan; empty opener sets do no work.')
add('performance.staggerWork',True,description='Distribute greenhouse structure/carbon/plant, dynamic structure, assimilator and sync checks by a stable hash of machine position. Keeps configured cadence; does not throttle energy transfers or processing progress.')
add('performance.cacheMachineRecipes',True,description='Cache recipe selection until input counts/components, family/owner, research unlocks, recipe/config generation changes. Commit always revalidates live input allocation.')
# Every shipped recipe has enable/cost/time/chance controls. Ingredients/results remain datapack/KubeJS/CraftTweaker data.
for f in sorted((p/'src/main/resources/data/foundations_calculator/recipe').rglob('*.json')):
 recipe=json.loads(f.read_text());rid='foundations_calculator:'+f.relative_to(p/'src/main/resources/data/foundations_calculator/recipe').with_suffix('').as_posix();key='recipe.'+rid+'.'
 add(key+'enabled',True)
 if recipe['type']=='foundations_calculator:process':
  add(key+'energyOverride',-1,-1,2147483647);add(key+'ticksOverride',-1,-1,1000000);add(key+'chanceMultiplier',1.0,0.0,100.0)
add('energy.machineCapacityMultiplier',1.0,.01,1000000.0,'Global multiplier for long-valued machine FE capacities. Applied after each machine base capacity.')
add('energy.itemCapacityMultiplier',1.0,.01,1000000.0,'Global multiplier for long-valued Calculator/module FE capacities.')
add('energy.transferMultiplier',1.0,0.0,1000000.0,'Global multiplier for FE/native transfer limits. Does not change conversion ratios.')
add('balance.applyPreset',False,description='Apply the selected Calculator balance preset on top of explicit server settings. Off preserves R9 behavior.')
add('balance.preset',0,0,4,'Balance preset: 0 Custom/R9, 1 Classic, 2 Balanced, 3 Expert, 4 High Power. Only active when applyPreset=true.')
add('api.longEnergy.enabled',True,description='Expose Foundations long-valued FE views to addons/native adapters while keeping NeoForge FE as a bounded facade.')
add('guide.liveValues',True,description='Allow bounded read-only field-guide server snapshots. Static chapters remain usable when false.')
keys=[r['key'] for r in rows];assert len(keys)==len(set(keys))
out=p/'src/main/resources/foundations/configuration.json';out.parent.mkdir(parents=True,exist_ok=True);out.write_text(json.dumps(rows,indent=2)+'\n')
(p/'tools/configuration_catalog.json').write_text(json.dumps(rows,indent=2)+'\n')
print(len(rows),'server settings;',len(ids),'content toggles;',len(set(machines)),'machine profiles')
