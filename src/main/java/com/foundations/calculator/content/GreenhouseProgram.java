package com.foundations.calculator.content;

import java.util.*;
import com.foundations.calculator.api.FoundationsPlants;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.*;

/** Greenhouse validation, resource-consuming construction and powered crop tending. */
public final class GreenhouseProgram {
    private GreenhouseProgram() {}
    public static int tier(MachineBlockEntity m){return m.kind().equals("basic_greenhouse")?1:m.kind().equals("advanced_greenhouse")?2:3;}
    private static Direction forward(MachineBlockEntity m){return m.getBlockState().getValue(MachineBlock.FACING).getOpposite();}
    private static BlockPos relative(MachineBlockEntity m,int side,int front,int up){return m.getBlockPos().relative(forward(m),front).relative(forward(m).getClockWise(),side).above(up);}
    private static GreenhouseGeometry geometry(MachineBlockEntity m){
        if(m.greenhouseGeometry==null||!m.greenhouseGeometry.matches(m))m.greenhouseGeometry=new GreenhouseGeometry(m);
        return m.greenhouseGeometry;
    }
    public static List<GreenhouseBlueprint.BlockPlace> blueprint(MachineBlockEntity m){return geometry(m).blueprint;}
    public static void tick(MachineBlockEntity m){
        Level l=m.getLevel();int tier=tier(m),state=m.program.getInt("HouseState");
        if(tier<3&&(state==1||state==3)){build(m,state==3);return;}
        if(m.workDue(CalculatorConfig.integer("greenhouse.structureCheckInterval",20))||!m.program.contains("Checked")){
            boolean complete=tier==3?checkFlawless(m):checkBlueprint(m);
            m.program.putBoolean("Checked",true);m.program.putInt("HouseState",complete?2:0);m.setActive(complete);m.setChanged();
        }
        if(m.program.getInt("HouseState")!=2||m.program.getBoolean("Paused")||l.hasNeighborSignal(m.getBlockPos()))return;
        List<BlockPos> area=plantArea(m);
        if(m.workDue(CalculatorConfig.integer("greenhouse.carbonInterval",20))){
            importSeeds(m);int plants=0,lanterns=0;
            for(BlockPos p:area)if(l.hasChunkAt(p)&&l.getBlockState(p).getBlock() instanceof BonemealableBlock)plants++;
            int gas=0;
            if(tier==3){if(l.hasChunkAt(relative(m,3,0,0))&&l.getBlockEntity(relative(m,3,0,0)) instanceof MachineBlockEntity gen&&gen.kind().equals("co2_generator"))gas=gen.program.getInt("GasAdd")*2;}
            else{
                int range=tier==1?1:3,front=tier==1?2:4;
                for(int x=-range;x<=range;x++)for(int z=-range;z<=range;z++)for(int y=0;y<(tier==1?3:5);y++){
                    if(l.hasChunkAt(relative(m,x,front+z,y))&&l.getBlockEntity(relative(m,x,front+z,y)) instanceof MachineBlockEntity lamp&&lamp.kind().equals("gas_lantern_off")&&lamp.getBlockState().getValue(MachineBlock.ACTIVE))lanterns++;
                }gas=(int)Math.min(Integer.MAX_VALUE,(long)lanterns*CalculatorConfig.integer("greenhouse.lanternCarbon",50));
            }
            int effective=tier==2?plants/5:plants;
            m.program.putInt("Carbon",(int)Math.clamp(m.program.getInt("Carbon")+(l.isDay()?(long)gas-effective*(long)CalculatorConfig.integer("greenhouse.dayCarbonUse",8):(long)gas+effective*(long)CalculatorConfig.integer("greenhouse.nightCarbonGain",2)),0,100000));
            farmland(m,area);m.setChanged();
        }
        int plantInterval=tier==1?CalculatorConfig.integer("greenhouse.basicPlantInterval",60):tier==2?CalculatorConfig.integer("greenhouse.advancedPlantInterval",10):CalculatorConfig.integer("greenhouse.flawlessPlantInterval",2);
        if(m.workDue(plantInterval))tend(m,area);
        int oxygen=100000-m.program.getInt("Carbon"),band=oxygen>=90000?0:oxygen>=50000?1:oxygen>=30000?2:oxygen>=10000?3:4;
        int[][] intervals={{400,300,200,150,80},{300,200,100,50,15},{200,100,50,25,15}};
        m.totalTicks=CalculatorConfig.ticks(m.kind(),CalculatorConfig.integer("greenhouse."+(tier==1?"basic":tier==2?"advanced":"flawless")+"GrowthBand"+band,intervals[tier-1][band]));
        if(++m.progress>=m.totalTicks){
            m.progress=0;for(int i=0;i<(tier==3?Math.max(1,geometry(m).length):1)&&!area.isEmpty();i++){
                BlockPos p=area.get(l.random.nextInt(area.size()));if(m.energy.getEnergyStored()>=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.growEnergy",150))&&l.hasChunkAt(p)&&MachinePrograms.grow(m,p)){m.energy.extractEnergy(CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.growEnergy",150)),false);m.program.putInt("Grown",m.program.getInt("Grown")+1);}
            }
        }m.setChanged();
    }
    public static boolean acceptsInput(MachineBlockEntity m,int slot,ItemStack stack){
        if(tier(m)<3&&slot<4)return stack.getItem() instanceof BlockItem item&&matches(item.getBlock().defaultBlockState(),GreenhouseBlueprint.BlockType.values()[slot]);
        return FoundationsPlants.accepts(stack,tier(m));
    }
    private static boolean matches(BlockState s,GreenhouseBlueprint.BlockType type){return switch(type){
        case LOG->s.is(BlockTags.LOGS);case STAIRS->s.is(BlockTags.WOODEN_STAIRS);case PLANKS->s.is(BlockTags.PLANKS);
        case GLASS->s.getBlock() instanceof TransparentBlock||s.getBlock() instanceof StainedGlassBlock||BuiltInRegistries.BLOCK.getKey(s.getBlock()).getPath().contains("glass");};}
    private static boolean checkBlueprint(MachineBlockEntity m){
        for(var part:blueprint(m))if(!m.getLevel().hasChunkAt(part.pos())||!matches(m.getLevel().getBlockState(part.pos()),part.type())){m.program.putLong("MissingPos",part.pos().asLong());return false;}m.program.remove("MissingPos");return true;
    }
    private static void build(MachineBlockEntity m,boolean demolish){
        if(demolish?!CalculatorConfig.flag("greenhouse.allowDemolish",true):!CalculatorConfig.flag("greenhouse.allowBuild",true))return;
        ServerLevel level=(ServerLevel)m.getLevel();
        for(var part:blueprint(m)){
            BlockPos pos=part.pos();if(!MachineWorldActions.loaded(level,pos))return;
            BlockState current=level.getBlockState(pos);boolean match=matches(current,part.type());
            if(demolish){
                if(!match||level.getBlockEntity(pos)!=null)continue;
                // Recovery happens AFTER a permitted, successful removal, never before it.
                if(MachineWorldActions.replace(m,pos,current,Blocks.AIR.defaultBlockState(),ItemStack.EMPTY,MachineWorldActions.Action.DEMOLISH)){
                    ItemStack recovered=new ItemStack(current.getBlock());if(!recovered.isEmpty())m.pending.add(recovered);m.setChanged();
                }
                return;
            }
            if(match)continue;
            m.program.putLong("MissingPos",pos.asLong());
            int cost=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.buildEnergy",100));
            if(!current.canBeReplaced()||level.getBlockEntity(pos)!=null||m.energy.getEnergyStored()<cost)return;
            int slot=part.type().ordinal();ItemStack resource=m.inventory.getStackInSlot(slot);
            if(!(resource.getItem() instanceof BlockItem item)||!matches(item.getBlock().defaultBlockState(),part.type()))return;
            BlockState placed=item.getBlock().defaultBlockState();
            if(placed.hasProperty(StairBlock.FACING)){
                Direction face=switch(part.meta()&3){case 0->Direction.EAST;case 1->Direction.WEST;case 2->Direction.SOUTH;default->Direction.NORTH;};
                placed=placed.setValue(StairBlock.FACING,face).setValue(StairBlock.HALF,(part.meta()&4)!=0?Half.TOP:Half.BOTTOM);
            }
            ItemStack expectedResource=resource.copy();
            if(MachineWorldActions.replace(m,pos,current,placed,resource,MachineWorldActions.Action.BUILD,
                ()->m.energy.getEnergyStored()>=cost&&ItemStack.matches(expectedResource,m.inventory.getStackInSlot(slot)))){
                m.inventory.extractItem(slot,1,false);m.energy.extractEnergy(cost,false);m.setChanged();
            }
            return;
        }
        m.program.putInt("HouseState",demolish?0:2);m.program.putBoolean("Paused",demolish);
        m.program.remove("MissingPos");m.setActive(!demolish);m.setChanged();
    }
    public static List<BlockPos> plantArea(MachineBlockEntity m){return geometry(m).plants;}
    /** One fallow decode/filter/encode per tending pass, instead of per crop position. */
    private static void tend(MachineBlockEntity m,List<BlockPos> area){
        ServerLevel level=(ServerLevel)m.getLevel();
        long[] original=m.program.getLongArray("Fallow");Set<Long> fallow=new HashSet<>();
        boolean replant=CalculatorConfig.flag("greenhouse.replant",true);
        if(!replant)for(long position:original)if(geometry(m).plantCoordinates.contains(position))fallow.add(position);
        int harvestCost=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.harvestEnergy",150));
        int plantCost=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.plantEnergy",50));
        for(BlockPos pos:area){
            if(!MachineWorldActions.loaded(level,pos)||!MachineWorldActions.loaded(level,pos.below()))continue;
            if(FoundationsPlants.canHarvest(level,pos)&&m.energy.getEnergyStored()>=harvestCost){
                var result=FoundationsPlants.harvest(m,level,pos);
                if(result.harvested()){
                    if(!replant)fallow.add(pos.asLong());
                    m.energy.extractEnergy(harvestCost,false);
                    for(ItemStack drop:result.drops())storeCrop(m,drop);
                    m.program.putInt("Harvested",(int)Math.min(Integer.MAX_VALUE,(long)m.program.getInt("Harvested")+1));m.setChanged();
                }
            }
            if(CalculatorConfig.flag("greenhouse.autoPlant",true)&&level.isEmptyBlock(pos)&&!fallow.contains(pos.asLong())&&m.energy.getEnergyStored()>=plantCost){
                for(int slot=tier(m)==3?0:4;slot<14;slot++){
                    ItemStack seed=m.inventory.getStackInSlot(slot);
                    if(FoundationsPlants.plant(m,seed,level,pos,tier(m))){
                        m.inventory.extractItem(slot,1,false);m.energy.extractEnergy(plantCost,false);break;
                    }
                }
            }
        }
        long[] updated=fallow.stream().mapToLong(Long::longValue).sorted().toArray();
        if(!Arrays.equals(original,updated)){m.program.putLongArray("Fallow",updated);m.setChanged();}
    }
    private static boolean stable(BlockState state){return state.is(Content.block("flawless_greenhouse"))||state.is(Content.block("co2_generator"))||BuiltInRegistries.BLOCK.getKey(state.getBlock()).getNamespace().equals("foundations_calculator")&&BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath().startsWith("stable_stone");}
    private static boolean glass(BlockState state){return state.is(Content.block("flawless_glass"));}
    private static boolean slab(BlockState state){return state.is(Blocks.QUARTZ_SLAB)&&state.getValue(SlabBlock.TYPE)==SlabType.BOTTOM;}
    private static boolean end(MachineBlockEntity m,int front){
        if(!(m.getLevel() instanceof ServerLevel level))return false;
        for(int side=0;side<4;side++)for(int y=-1;y<=2;y++)if(!MachineWorldActions.loaded(level,relative(m,side,front,y)))return false;
        for(int side=0;side<4;side++)if(!stable(m.getLevel().getBlockState(relative(m,side,front,-1)))||!slab(m.getLevel().getBlockState(relative(m,side,front,2))))return false;
        for(int side:List.of(0,3))for(int y=0;y<2;y++)if(!relative(m,side,front,y).equals(m.getBlockPos())&&!stable(m.getLevel().getBlockState(relative(m,side,front,y))))return false;
        return true;
    }
    public static boolean checkFlawless(MachineBlockEntity m){
        m.program.putInt("HouseSize",0);if(!end(m,0))return false;
        for(int front=1;front<=CalculatorConfig.integer("greenhouse.maxFlawlessLength",64)+1;front++){
            for(int side=0;side<4;side++)for(int y=-1;y<=2;y++)
                if(!MachineWorldActions.loaded((ServerLevel)m.getLevel(),relative(m,side,front,y)))return false;
            if(front>1&&end(m,front)){m.program.putInt("HouseSize",front-1);return true;}
            for(int side=0;side<4;side++)if(!slab(m.getLevel().getBlockState(relative(m,side,front,2))))return false;
            for(int side:List.of(0,3))for(int y=0;y<2;y++)if(!glass(m.getLevel().getBlockState(relative(m,side,front,y))))return false;
        }return false;
    }
    private static void farmland(MachineBlockEntity m,List<BlockPos> area){
        ServerLevel level=(ServerLevel)m.getLevel();
        int soilCost=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.farmlandEnergy",50));
        int waterCost=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.waterEnergy",1000));
        if(CalculatorConfig.flag("greenhouse.autoFarmland",true))for(BlockPos crop:area){
            BlockPos pos=crop.below();if(!MachineWorldActions.loaded(level,pos))continue;
            BlockState current=level.getBlockState(pos);
            if((current.is(Blocks.DIRT)||current.is(Blocks.GRASS_BLOCK))&&MachineWorldActions.replace(m,pos,current,
                Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE,7),new ItemStack(Items.IRON_HOE),MachineWorldActions.Action.FARMLAND,
                ()->m.energy.getEnergyStored()>=soilCost))m.energy.extractEnergy(soilCost,false);
        }
        if(CalculatorConfig.flag("greenhouse.autoWater",true))for(BlockPos pos:geometry(m).water){
            if(!MachineWorldActions.loaded(level,pos))continue;BlockState current=level.getBlockState(pos);
            if((current.is(Blocks.DIRT)||current.is(Blocks.GRASS_BLOCK)||current.is(Blocks.FARMLAND))&&MachineWorldActions.replace(m,pos,current,
                Blocks.WATER.defaultBlockState(),new ItemStack(Items.WATER_BUCKET),MachineWorldActions.Action.WATER,
                ()->m.energy.getEnergyStored()>=waterCost))m.energy.extractEnergy(waterCost,false);
        }
    }
    private static void storeCrop(MachineBlockEntity m,ItemStack stack){
        if(FoundationsPlants.accepts(stack,tier(m))){for(int slot=tier(m)==3?0:4;slot<14&&!stack.isEmpty();slot++)stack=m.inventory.insertItem(slot,stack,false);}
        if(!stack.isEmpty()){
            Direction outputFace=forward(m).getOpposite();BlockPos front=m.getBlockPos().relative(outputFace);
            if(m.itemOutput(outputFace)&&m.getLevel().hasChunkAt(front)){
                var dest=m.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,front,forward(m));
                if(dest!=null)stack=ItemHandlerHelper.insertItemStacked(dest,stack,false);
            }
            if(!stack.isEmpty())m.pending.add(stack);
        }
    }
    private static void importSeeds(MachineBlockEntity m){
        Direction inputFace=forward(m).getOpposite();
        if(!m.itemInput(inputFace))return;
        BlockPos front=m.getBlockPos().relative(inputFace);if(!m.getLevel().hasChunkAt(front))return;
        IItemHandler source=m.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,front,forward(m));if(source==null)return;
        for(int slot=0;slot<source.getSlots();slot++){
            ItemStack seed=source.extractItem(slot,32,true);if(!FoundationsPlants.accepts(seed,tier(m)))continue;
            for(int dest=tier(m)==3?0:4;dest<14;dest++){
                ItemStack left=m.inventory.insertItem(dest,seed,true);int count=seed.getCount()-left.getCount();
                if(count>0){ItemStack extracted=source.extractItem(slot,count,false);ItemStack rem=m.inventory.insertItem(dest,extracted,false);if(!rem.isEmpty())m.pending.add(rem);return;}
            }
        }
    }
    public static void carbonGenerator(MachineBlockEntity m){
        Level l=m.getLevel();BlockPos p=m.getBlockPos().relative(forward(m).getClockWise().getOpposite(),3);
        if(!l.hasChunkAt(p)||!(l.getBlockEntity(p) instanceof MachineBlockEntity house)||!house.kind().equals("flawless_greenhouse")){m.program.putInt("GasAdd",0);m.setActive(false);return;}
        if(m.burnTime==0){ItemStack fuel=m.inventory.getStackInSlot(0);int burn=fuel.getBurnTime(RecipeType.SMELTING);
            if(burn>0&&m.energy.getEnergyStored()>=CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.co2FuelEnergy",100000))){
                m.program.putBoolean("Controlled",fuel.is(Content.item("controlled_fuel")));m.program.putInt("FuelGas",burn/100);
                ItemStack rem=fuel.getCraftingRemainingItem();m.inventory.extractItem(0,1,false);if(!rem.isEmpty())m.pending.add(rem);
                m.burnTime=CalculatorConfig.integer("greenhouse.co2FuelTicks",10000);m.energy.extractEnergy(CalculatorConfig.energy(m.kind(),CalculatorConfig.integer("greenhouse.co2FuelEnergy",100000)),false);
            }
        }
        boolean controlled=m.program.getBoolean("Controlled"),running=!controlled||house.program.getInt("Carbon")<=CalculatorConfig.integer("greenhouse.controlledMinimumCarbon",92000)||m.program.getBoolean("GasRunning")&&house.program.getInt("Carbon")<CalculatorConfig.integer("greenhouse.controlledMaximumCarbon",100000);
        m.program.putBoolean("GasRunning",running);m.program.putInt("GasAdd",running&&m.burnTime>0?(controlled?800:m.program.getInt("FuelGas")):0);
        if(running&&m.burnTime>0)m.burnTime--;m.setActive(running&&m.burnTime>0);m.setChanged();
    }
}
