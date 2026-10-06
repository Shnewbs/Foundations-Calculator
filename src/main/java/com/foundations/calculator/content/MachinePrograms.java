package com.foundations.calculator.content;

import java.util.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.*;

/** Server-only world interactions. All durable state belongs to the owning block entity. */
public final class MachinePrograms {
    private MachinePrograms() {}
    public static boolean tick(MachineBlockEntity m) {
        switch (m.kind()) {
            case "research_chamber" -> ResearchProgram.tick(m);
            case "rain_sensor" -> m.setActive(m.getLevel().isRaining());
            case "weather_controller" -> weather(m);
            case "gas_lantern_off" -> lantern(m);
            case "magnetic_flux" -> magnet(m);
            case "scarecrow" -> scarecrow(m);
            case "stone_assimilator", "algorithm_assimilator" -> assimilator(m);
            case "amethyst_piping", "tanzanite_piping" -> { /* traversed by the nutrition processor */ }
            case "calculator_plug" -> m.setActive(CircuitData.stable(m.inventory.getStackInSlot(0)));
            case "calculator_locator" -> locator(m);
            case "weather_station" -> weatherStation(m);
            case "calculator_screen_block" -> screen(m);
            case "transmitter", "crank_handle" -> {}
            case "atomic_multiplier" -> multiply(m);
            case "basic_greenhouse", "advanced_greenhouse", "flawless_greenhouse" -> GreenhouseProgram.tick(m);
            case "co2_generator" -> GreenhouseProgram.carbonGenerator(m);
            case "module_workstation" -> ModuleWorkstation.tick(m);
            case "conductor_mast" -> { conductor(m);return false; }
            default -> {return false;}
        }
        return true;
    }
    public static void discharge(MachineBlockEntity m) {
        if(!m.usesEnergy() || MachineProfiles.storage(m.kind()) || MachineProfiles.generator(m.kind())) return;
        if(EnergyItems.discharge(m.inventory,20,m.energy,MachineProfiles.transfer(m.kind()))>0) m.setChanged();
    }
    public static void export(MachineBlockEntity m) {
        if(!CalculatorConfig.machineFlag(m.kind(),"itemAutomation")||m.upgrades("transfer_upgrade")==0)return;
        int remaining=(int)Math.min(CalculatorConfig.integer("upgrades.maxTransfer",64),(long)m.upgrades("transfer_upgrade")*CalculatorConfig.integer("upgrades.itemsPerTransfer",4));
        for(Direction side:Direction.values()){
            if(remaining<=0)break;
            if(!m.itemOutput(side))continue;
            BlockPos target=m.getBlockPos().relative(side);if(!m.getLevel().hasChunkAt(target))continue;
            IItemHandler other=m.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,target,side.getOpposite());
            if(other==null)continue;
            IItemHandler source=m.automation(side);
            for(int i=14;i<20&&remaining>0;i++){
                ItemStack offered=source.extractItem(i,remaining,true);if(offered.isEmpty())continue;
                ItemStack left=ItemHandlerHelper.insertItemStacked(other,offered,false);
                int moved=offered.getCount()-left.getCount();source.extractItem(i,moved,false);remaining-=moved;
            }
        }
    }
    private static void weather(MachineBlockEntity m) {
        ServerLevel level=(ServerLevel)m.getLevel();CompoundTag s=m.program;
        if(s.getIntOr("Cooldown",0)>0){s.putInt("Cooldown",s.getIntOr("Cooldown",0)-1);m.setChanged();return;}
        int mode=s.getIntOr("Mode",0);boolean target=s.getBooleanOr("Target",false);
        boolean current=switch(mode){case 1->level.getLevelData().isRaining();case 2->level.getLevelData().isThundering();default->!level.isDay();};
        if(!level.hasNeighborSignal(m.getBlockPos())||current==target){m.progress=0;m.setActive(false);return;}
        m.totalTicks=CalculatorConfig.ticks(m.kind(),CalculatorConfig.integer("world.weatherDuration",100));
        if(m.energy.getEnergyStored()<CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("world.weatherEnergyPerTick",2500))){if(!CalculatorConfig.machineFlag(m.kind(),"retainProgressWithoutPower"))m.progress=0;return;}
        m.energy.extractEnergy(CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("world.weatherEnergyPerTick",2500)),false);m.setActive(true);
        if(++m.progress>=m.totalTicks){
            if(mode==0){long now=level.getDayTime();long desired=Math.floorDiv(now,24000)*24000+(target?CalculatorConfig.integer("world.weatherHoldTicks",12000):0);if(desired<=now)desired+=24000;level.setDayTime(desired);}
            else if(mode==1)level.setWeatherParameters(target?0:CalculatorConfig.integer("world.weatherHoldTicks",12000),target?CalculatorConfig.integer("world.weatherHoldTicks",12000):0,target,target&&level.isThundering());
            else level.setWeatherParameters(target?0:CalculatorConfig.integer("world.weatherHoldTicks",12000),target?CalculatorConfig.integer("world.weatherHoldTicks",12000):0,target,target);
            m.progress=0;s.putInt("Cooldown",CalculatorConfig.integer("world.weatherCooldown",30));m.setActive(false);
        }m.setChanged();
    }
    private static void screen(MachineBlockEntity m){
        var face=m.getBlockState().getValue(MachineBlock.FACING);var p=m.getBlockPos().relative(face.getOpposite());
        var energy=com.foundations.calculator.api.FoundationsEnergy.longBlock(m.getLevel(),p,face);
        m.program.putLong("ScreenEnergy",energy==null?0:Math.max(0L,energy.stored()));m.program.putLong("ScreenCapacity",energy==null?0:Math.max(0L,energy.capacity()));m.setChanged();
    }
    private static void lantern(MachineBlockEntity m) {
        if(m.burnTime==0){ItemStack fuel=m.inventory.getStackInSlot(0);int duration=fuel.getBurnTime(RecipeType.SMELTING);
            if(duration>0){ItemStack rem=fuel.getCraftingRemainingItem();m.inventory.extractItem(0,1,false);if(!rem.isEmpty())m.pending.add(rem);m.burnTime=(int)Math.min(Integer.MAX_VALUE,(long)CalculatorConfig.integer("greenhouse.lanternBurnMultiplier",10)*duration);}}
        boolean burning=m.burnTime>0;
        if(burning){m.burnTime--;m.setChanged();}m.setActive(burning);
    }
    private static void scarecrow(MachineBlockEntity m) {
        int ticks=m.program.getIntOr("GrowTicks",0)+1;m.program.putInt("GrowTicks",ticks);
        if(ticks>=CalculatorConfig.SCARECROW_INTERVAL.get()){
            m.program.putInt("GrowTicks",0);int range=CalculatorConfig.SCARECROW_RANGE.get();var r=m.getLevel().getRandom();
            BlockPos p=m.getBlockPos().offset(r.nextInt(2*range+1)-range,0,r.nextInt(2*range+1)-range);
            if(m.getLevel().hasChunkAt(p))grow(m,p);
        }m.setChanged();
    }
    public static boolean grow(MachineBlockEntity machine,BlockPos pos) {
        if(!(machine.getLevel() instanceof ServerLevel level)||!MachineWorldActions.loaded(level,pos))return false;
        BlockState state=level.getBlockState(pos);
        if(!(state.getBlock() instanceof BonemealableBlock plant)||!plant.isValidBonemealTarget(level,pos,state))return false;
        String namespace=BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace();
        boolean bounded=state.getBlock() instanceof CropBlock&&(namespace.equals("minecraft")||namespace.equals("foundations_calculator"));
        if(!bounded){
            var adapter=com.foundations.calculator.api.FoundationsPlants.growWithAdapter(machine,level,pos);
            if(adapter.isPresent())return adapter.get();
            if(!MachineWorldActions.legacyAllowed(machine,pos,MachineWorldActions.Action.GROW))return false;
        }
        if(!MachineWorldActions.permits(machine,pos,state,null,new ItemStack(Items.BONE_MEAL),MachineWorldActions.Action.GROW))return false;
        if(!plant.isBonemealSuccess(level,level.getRandom(),pos,state))return false;
        plant.performBonemeal(level,level.getRandom(),pos,state);
        // Native crops are single-block. Opaque opted-in callbacks retain their declared bonemeal success behavior.
        boolean changed=!state.equals(level.getBlockState(pos))||!bounded;
        if(changed)level.levelEvent(1505,pos,0);return changed;
    }
    /** Raw addon helper. Machine callers must use the owner-aware overload above. */
    public static boolean grow(ServerLevel level,BlockPos pos){
        BlockState state=level.getBlockState(pos);
        if(state.getBlock() instanceof BonemealableBlock b&&b.isValidBonemealTarget(level,pos,state)&&b.isBonemealSuccess(level,level.getRandom(),pos,state)){
            b.performBonemeal(level,level.getRandom(),pos,state);level.levelEvent(1505,pos,0);return true;
        }return false;
    }
    private static void magnet(MachineBlockEntity m) {
        Level level=m.getLevel();if(level.hasNeighborSignal(m.getBlockPos()))return;
        boolean whitelist=m.program.getBooleanOr("Whitelist",false),tags=m.program.getBooleanOr("MatchTags",false);
        Vec3 target=Vec3.atBottomCenterOf(m.getBlockPos()).add(0,.2,0);
        for(ItemEntity entity:level.getEntitiesOfClass(ItemEntity.class,new AABB(m.getBlockPos()).inflate(CalculatorConfig.integer("world.magnetRadius",10)))){
            ItemStack stack=entity.getItem();boolean matched=false;
            for(int i=0;i<8;i++){ItemStack filter=m.inventory.getStackInSlot(i);if(filter.isEmpty())continue;
                if(tags?filter.is(stack.getItem())||filter.getTags().anyMatch(stack::is):ItemStack.isSameItemSameComponents(filter,stack)){matched=true;break;}}
            if(matched!=whitelist)continue;
            if(entity.position().distanceToSqr(target)<2.25){
                BlockPos below=m.getBlockPos().below();IItemHandler dest=level.getCapability(Capabilities.ItemHandler.BLOCK,below,Direction.UP);
                if(dest!=null){ItemStack remaining=ItemHandlerHelper.insertItemStacked(dest,stack.copy(),false);if(remaining.isEmpty())entity.discard();else entity.setItem(remaining);}
            }else{entity.setDeltaMovement(target.subtract(entity.position()).normalize().scale(CalculatorConfig.decimal("world.magnetSpeed",0.15)));entity.hasImpulse=true;}
        }
    }
    private static void assimilator(MachineBlockEntity m) {
        boolean stone=m.kind().equals("stone_assimilator");Level level=m.getLevel();
        if(stone)for(String type:List.of("health","hunger")){
            int points=m.program.getIntOr(type,0);int sent=NutritionData.add(m.inventory.getStackInSlot(20),type,Math.min(CalculatorConfig.integer("nutrition.machineTransfer",4),points));
            if(sent>0){m.program.putInt(type,points-sent);m.setChanged();}
        }
        if(!m.workDue(CalculatorConfig.integer("world.assimilatorInterval",30)))return;
        BlockPos trunk=m.getBlockPos().relative(m.getBlockState().getValue(MachineBlock.FACING).getOpposite());
        for(int i=0;i<3;i++)if(!level.hasChunkAt(trunk.above(i))||!level.getBlockState(trunk.above(i)).is(BlockTags.LOGS))return;
        int leaves=0;for(BlockPos p:BlockPos.betweenClosed(trunk.offset(-CalculatorConfig.integer("world.assimilatorScanRadius",2),1,-CalculatorConfig.integer("world.assimilatorScanRadius",2)),trunk.offset(CalculatorConfig.integer("world.assimilatorScanRadius",2),CalculatorConfig.integer("world.assimilatorScanHeight",7),CalculatorConfig.integer("world.assimilatorScanRadius",2))))if(level.hasChunkAt(p)&&level.getBlockState(p).getBlock() instanceof HarvestLeaves)leaves++;
        if(leaves<CalculatorConfig.integer("world.assimilatorMinimumLeaves",10))return;
        for(BlockPos p:BlockPos.betweenClosed(trunk.offset(-CalculatorConfig.integer("world.assimilatorScanRadius",2),1,-CalculatorConfig.integer("world.assimilatorScanRadius",2)),trunk.offset(CalculatorConfig.integer("world.assimilatorScanRadius",2),CalculatorConfig.integer("world.assimilatorScanHeight",7),CalculatorConfig.integer("world.assimilatorScanRadius",2)))){
            if(!level.hasChunkAt(p))continue;
            BlockState state=level.getBlockState(p);if(!(state.getBlock() instanceof HarvestLeaves)||state.getValue(HarvestLeaves.AGE)<CalculatorConfig.integer("plants.leafMatureAge",2))continue;
            String type=state.is(Content.block("amethyst_leaves"))?"hunger":state.is(Content.block("tanzanite_leaves"))?"health":"";
            if(stone&&!type.isEmpty()){
                int points=m.program.getIntOr(type,0);if(points==Integer.MAX_VALUE)continue;
                if(!MachineWorldActions.replace(m,p.immutable(),state,state.setValue(HarvestLeaves.AGE,
                    CalculatorConfig.integer("plants.leafHarvestResetAge",0)),ItemStack.EMPTY,MachineWorldActions.Action.LEAF_HARVEST))continue;
                m.program.putInt(type,(int)Math.min(Integer.MAX_VALUE,(long)points+CalculatorConfig.integer("nutrition.leafYield",1)));m.setChanged();break;
            }else if(!stone&&type.isEmpty()){
                m.pending.addAll(harvestLeaves(m,p.immutable()));m.setChanged();break;
            }
        }
    }
    public static List<ItemStack> harvestLeaves(MachineBlockEntity machine,BlockPos pos) {
        var level=machine.getLevel();if(!(level instanceof ServerLevel server)||!MachineWorldActions.loaded(server,pos))return List.of();
        BlockState state=level.getBlockState(pos);
        if(!(state.getBlock() instanceof HarvestLeaves)||state.getValue(HarvestLeaves.AGE)<CalculatorConfig.integer("plants.leafMatureAge",2))return List.of();
        boolean pear=state.is(Content.block("pear_leaves"));if(!pear&&!state.is(Content.block("diamond_leaves")))return List.of();
        if(!MachineWorldActions.replace(machine,pos,state,state.setValue(HarvestLeaves.AGE,CalculatorConfig.integer("plants.leafHarvestResetAge",0)),
            ItemStack.EMPTY,MachineWorldActions.Action.LEAF_HARVEST))return List.of();
        int age=state.getValue(HarvestLeaves.AGE);List<ItemStack> drops=new ArrayList<>();
        if(age<4)drops.add(new ItemStack(Content.item(pear?"pear":"weakened_diamond")));
        if(age>=3)drops.add(new ItemStack(Content.item(pear?"rotten_pear":"flawless_diamond")));
        return drops;
    }
    /** Raw addon helper; machine callers use the guarded overload. */
    public static List<ItemStack> harvestLeaves(Level level,BlockPos pos){
        BlockState s=level.getBlockState(pos);if(!(s.getBlock() instanceof HarvestLeaves)||s.getValue(HarvestLeaves.AGE)<CalculatorConfig.integer("plants.leafMatureAge",2))return List.of();
        boolean pear=s.is(Content.block("pear_leaves"));if(!pear&&!s.is(Content.block("diamond_leaves")))return List.of();
        List<ItemStack> drops=new ArrayList<>();int age=s.getValue(HarvestLeaves.AGE);
        if(age<4)drops.add(new ItemStack(Content.item(pear?"pear":"weakened_diamond")));
        if(age>=3)drops.add(new ItemStack(Content.item(pear?"rotten_pear":"flawless_diamond")));
        level.setBlockAndUpdate(pos,s.setValue(HarvestLeaves.AGE,CalculatorConfig.integer("plants.leafHarvestResetAge",0)));return drops;
    }
    public static void nutritionNetwork(MachineBlockEntity m,String type){
        if(m.getLevel().getGameTime()%CalculatorConfig.integer("nutrition.networkInterval",20)!=0||m.nutrient==Integer.MAX_VALUE)return;
        String pipe=type.equals("health")?"tanzanite_piping":"amethyst_piping";
        String leaves=type.equals("health")?"tanzanite_leaves":"amethyst_leaves";
        Set<BlockPos> visited=new HashSet<>();Deque<BlockPos> queue=new ArrayDeque<>();queue.add(m.getBlockPos());visited.add(m.getBlockPos());
        while(!queue.isEmpty()&&visited.size()<CalculatorConfig.integer("nutrition.networkMaxVisited",512)){BlockPos p=queue.removeFirst();for(Direction d:Direction.values()){
            BlockPos n=p.relative(d);if(!visited.add(n)||!m.getLevel().hasChunkAt(n))continue;BlockState state=m.getLevel().getBlockState(n);
            if(state.is(Content.block(pipe))||state.is(Content.block(type.equals("health")?"tanzanite_log":"amethyst_log"))||state.is(Content.block(leaves)))queue.add(n);
            if(state.is(Content.block(leaves))&&state.getValue(HarvestLeaves.AGE)>=CalculatorConfig.integer("plants.leafMatureAge",2)){
                if(!MachineWorldActions.replace(m,n,state,state.setValue(HarvestLeaves.AGE,
                    CalculatorConfig.integer("plants.leafHarvestResetAge",0)),ItemStack.EMPTY,MachineWorldActions.Action.LEAF_HARVEST))continue;
                m.nutrient=(int)Math.min(Integer.MAX_VALUE,(long)m.nutrient+CalculatorConfig.integer("nutrition.leafYield",1));m.setChanged();return;
            }
        }}
    }
    private static void multiply(MachineBlockEntity m) {
        ItemStack input=m.inventory.getStackInSlot(0);
        if(input.isEmpty()||input.getMaxStackSize()<CalculatorConfig.integer("world.atomicMultiplierCopies",4)||input.is(TagKey.create(Registries.ITEM,Content.id("atomic_multiplier_blacklist")))){m.progress=0;return;}
        for(int i=1;i<=7;i++)if(!Content.path(m.inventory.getStackInSlot(i)).startsWith("circuit_board_")){m.progress=0;return;}
        int cost=CalculatorConfig.energy(m.kind(),CalculatorConfig.MULTIPLIER_ENERGY.get());m.totalTicks=CalculatorConfig.ticks(m.kind(),CalculatorConfig.integer("world.atomicMultiplierTicks",1000));
        if(m.energy.getEnergyStored()<cost){if(!CalculatorConfig.machineFlag(m.kind(),"retainProgressWithoutPower"))m.progress=0;return;}
        String fingerprint=BuiltInRegistries.ITEM.getKey(input.getItem()).toString();
        if(!fingerprint.equals(m.program.getStringOr("MultiplierInput",""))){m.progress=0;m.program.putString("MultiplierInput",fingerprint);}
        if(++m.progress>=m.totalTicks){
            ItemStack result=input.copyWithCount(CalculatorConfig.integer("world.atomicMultiplierCopies",4));for(int i=0;i<=7;i++)m.inventory.extractItem(i,1,false);
            m.energy.extractEnergy(cost,false);m.pending.add(result);m.progress=0;
        }m.setChanged();
    }
    private static boolean stable(Level l,BlockPos p){return l.hasChunkAt(p)&&BuiltInRegistries.BLOCK.getKey(l.getBlockState(p).getBlock()).getNamespace().equals("foundations_calculator")&&BuiltInRegistries.BLOCK.getKey(l.getBlockState(p).getBlock()).getPath().startsWith("stable_stone");}
    public static int locatorSize(Level l,BlockPos p){
        outer:for(int size=1;size<=CalculatorConfig.integer("generation.locatorMaxRadius",11);size++){
            for(int x=-size;x<=size;x++)for(int z=-size;z<=size;z++)if(x!=0||z!=0){
                if(!stable(l,p.offset(x,-1,z))||!l.getBlockState(p.offset(x,0,z)).is(Content.block("calculator_plug")))continue outer;
            }
            for(int i=-size;i<=size;i++)for(int y=-1;y<=0;y++)if(!stable(l,p.offset(i,y,size+1))||!stable(l,p.offset(i,y,-size-1))||!stable(l,p.offset(size+1,y,i))||!stable(l,p.offset(-size-1,y,i)))continue outer;
            return size;
        }return 0;
    }
    private static void locator(MachineBlockEntity m) {
        Level level=m.getLevel();CompoundTag s=m.program;
        if(level.getGameTime()%CalculatorConfig.integer("generation.structureCheckInterval",25)==0||!s.contains("Size")){
            int size=locatorSize(level,m.getBlockPos()),stable=0;s.putInt("Size",size);
            if(size>0)for(int x=-size;x<=size;x++)for(int z=-size;z<=size;z++)if(level.getBlockEntity(m.getBlockPos().offset(x,0,z)) instanceof MachineBlockEntity plug&&plug.kind().equals("calculator_plug")&&CircuitData.stable(plug.inventory.getStackInSlot(0)))stable++;
            s.putInt("Stability",stable);m.setChanged();
        }
        int size=s.getIntOr("Size",0),stable=s.getIntOr("Stability",0);ItemStack module=m.inventory.getStackInSlot(0);CompoundTag owner=CircuitData.tag(module);
        var player=owner.hasUUID("Owner")?level.getPlayerByUUID(owner.getUUID("Owner")):null;
        boolean active=Content.path(module).equals("locator_module")&&owner.hasUUID("Owner")&&size>0&&(stable>=CalculatorConfig.integer("generation.locatorStableThreshold",7)||player!=null)&&m.energy.stored()<m.energy.capacity();
        m.setActive(active);if(!active)return;
        int percent=Math.clamp(stable*100/((2*size+1)*(2*size+1)),0,100);
        int output=(int)Math.max(0,5+((int)(1000*Math.sqrt(size*1.8)-100*Math.sqrt(100-percent)))/Math.max(1,(int)(11-Math.sqrt(percent)))*size);
        output=(int)Math.min(Integer.MAX_VALUE,output*CalculatorConfig.decimal("generation.locatorMultiplier",2.0));m.energy.receiveEnergy(output,false);s.putInt("Generation",output);
        if(stable<CalculatorConfig.integer("generation.locatorTimeThreshold",5)&&CalculatorConfig.LOCATOR_TIME.get())((ServerLevel)level).setDayTime(level.getDayTime()+CalculatorConfig.integer("generation.locatorTimeAdvance",100));
        if(player!=null&&stable<CalculatorConfig.integer("generation.locatorStableThreshold",7)&&level.getGameTime()%CalculatorConfig.integer("generation.locatorEffectInterval",50)==0&&CalculatorConfig.LOCATOR_EFFECTS.get()){
            int luck=1+level.getRandom().nextInt(Math.max(1,40*(stable+1)-1));
            if(stable==0||stable<2&&luck==1)level.explode(null,player.getX(),player.getY(),player.getZ(),(float)(4*CalculatorConfig.decimal("world.locatorExplosionMultiplier",1)),true,CalculatorConfig.flag("world.locatorBlockDamage",true)?Level.ExplosionInteraction.BLOCK:Level.ExplosionInteraction.NONE);
            else if(stable<4&&luck==2)player.igniteForSeconds(20);
            else if(stable<4&&(luck==3||luck==4))level.explode(null,player.getX(),player.getY(),player.getZ(),(float)((luck==3?8:6)*CalculatorConfig.decimal("world.locatorExplosionMultiplier",1)),true,CalculatorConfig.flag("world.locatorBlockDamage",true)?Level.ExplosionInteraction.BLOCK:Level.ExplosionInteraction.NONE);
            else if(luck>=5&&luck<=14){
                var effect=switch(luck){case 5->MobEffects.CONFUSION;case 6->MobEffects.BLINDNESS;case 10->MobEffects.JUMP;case 11->MobEffects.WATER_BREATHING;case 12->MobEffects.MOVEMENT_SLOWDOWN;case 13->MobEffects.DAMAGE_BOOST;case 14->MobEffects.WITHER;default->null;};
                if(effect!=null)player.addEffect(new MobEffectInstance(effect,CalculatorConfig.integer("generation.locatorEffectDuration",1000),1));
                else if(luck<=9)player.getInventory().placeItemBackInInventory(new ItemStack(Content.item(luck==7?"grenade":luck==8?"scientific_calculator":"calculator")));
            }else if(luck==15)player.getInventory().placeItemBackInInventory(new ItemStack(Items.MILK_BUCKET));
        }
        m.setChanged();
    }
    private static void weatherStation(MachineBlockEntity m){
        if(m.getLevel().getGameTime()%CalculatorConfig.integer("generation.structureCheckInterval",25)!=0)return;BlockPos best=null;double distance=Double.MAX_VALUE;
        for(BlockPos p:BlockPos.betweenClosed(m.getBlockPos().offset(-CalculatorConfig.integer("generation.weatherStationRadius",10),0,-CalculatorConfig.integer("generation.weatherStationRadius",10)),m.getBlockPos().offset(CalculatorConfig.integer("generation.weatherStationRadius",10),0,CalculatorConfig.integer("generation.weatherStationRadius",10)))){
            if(!m.getLevel().hasChunkAt(p)||!(m.getLevel().getBlockEntity(p) instanceof MachineBlockEntity other)||!other.kind().equals("conductor_mast"))continue;
            double d=m.getBlockPos().distSqr(p);if(d<distance){distance=d;best=p.immutable();}
        }
        if(best==null)m.program.remove("Mast");else m.program.putLong("Mast",best.asLong());m.setActive(best!=null);m.setChanged();
    }
    private static void conductor(MachineBlockEntity m){
        CompoundTag s=m.program;int wait=s.getIntOr("StrikeWait",0);
        if(wait<=0&&m.energy.stored()<m.energy.capacity()){
            int transmitters=0,stations=0;
            for(BlockPos p:BlockPos.betweenClosed(m.getBlockPos().offset(-CalculatorConfig.integer("generation.mastRadius",20),0,-CalculatorConfig.integer("generation.mastRadius",20)),m.getBlockPos().offset(CalculatorConfig.integer("generation.mastRadius",20),0,CalculatorConfig.integer("generation.mastRadius",20)))){
                if(!m.getLevel().hasChunkAt(p)||!(m.getLevel().getBlockEntity(p) instanceof MachineBlockEntity other))continue;
                if(other.kind().equals("transmitter"))transmitters++;
                else if(other.kind().equals("weather_station")&&other.program.contains("Mast")&&other.program.getLongOr("Mast",0L)==m.getBlockPos().asLong())stations++;
            }
            wait=(int)Math.min(Integer.MAX_VALUE,Math.max(CalculatorConfig.integer("generation.mastMinWait",250),(long)CalculatorConfig.integer("generation.mastBaseWait",1500)-(long)CalculatorConfig.integer("generation.mastWaitReduction",135)*transmitters)+(long)m.getLevel().getRandom().nextInt(CalculatorConfig.integer("generation.mastRandomWait",300)));
            s.putInt("StrikePower",(int)Math.clamp((CalculatorConfig.integer("generation.mastBaseGeneration",25)+(double)stations*CalculatorConfig.integer("generation.weatherStationBonus",5))*Math.max(1,transmitters/4)*CalculatorConfig.decimal("generation.mastMultiplier",4),0,Integer.MAX_VALUE));
        }
        if(wait>0){wait--;s.putInt("StrikeWait",wait);if(wait==0){
            s.putInt("StrikeTicks",CalculatorConfig.integer("generation.mastBurstTicks",200));var bolt=EntityType.LIGHTNING_BOLT.create(m.getLevel());if(bolt!=null&&CalculatorConfig.flag("world.mastLightningVisual",true)){bolt.moveTo(Vec3.atBottomCenterOf(m.getBlockPos().above(4)));bolt.setVisualOnly(true);m.getLevel().addFreshEntity(bolt);}
        }}
        int ticks=s.getIntOr("StrikeTicks",0);if(ticks>0){m.energy.receiveEnergy(s.getIntOr("StrikePower",0),false);s.putInt("StrikeTicks",ticks-1);}m.setActive(ticks>0);m.setChanged();
    }
}
