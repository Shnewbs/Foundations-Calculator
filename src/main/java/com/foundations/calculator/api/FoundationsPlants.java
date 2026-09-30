package com.foundations.calculator.api;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import com.foundations.calculator.content.CalculatorCrop;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.SpecialPlantable;

/** Public replacement for the embedded legacy Sonar Core planting registry. */
public final class FoundationsPlants {
    private static final List<PlantAdapter> ADAPTERS=new CopyOnWriteArrayList<>();
    public static void register(PlantAdapter adapter){ADAPTERS.add(Objects.requireNonNull(adapter));}
    private static boolean external(){return CalculatorConfig.flag("compat.externalPlants",true)&&CalculatorConfig.flag("greenhouse.acceptGenericPlants",true);}
    public static boolean accepts(ItemStack seed,int tier){
        if(seed.isEmpty())return false;
        if(seed.getItem() instanceof BlockItem item&&item.getBlock() instanceof CropBlock)return !(item.getBlock() instanceof CalculatorCrop crop)||crop.minimumTier()<=tier;
        return external()&&(ADAPTERS.stream().anyMatch(a->a.accepts(seed,tier))||seed.getItem() instanceof SpecialPlantable||seed.getItem() instanceof BlockItem item&&item.getBlock() instanceof BonemealableBlock);
    }
    public static boolean plant(ItemStack seed,ServerLevel level,BlockPos pos,int tier){
        if(!level.hasChunkAt(pos)||!level.isEmptyBlock(pos)||!accepts(seed,tier))return false;
        if(external()){
            for(PlantAdapter a:ADAPTERS)if(a.accepts(seed,tier)&&a.canPlant(seed,level,pos))return a.plant(seed.copyWithCount(1),level,pos);
            if(seed.getItem() instanceof SpecialPlantable a&&a.canPlacePlantAtPosition(seed,level,pos,Direction.UP)){
                a.spawnPlantAtPosition(seed.copyWithCount(1),level,pos,Direction.UP);return !level.isEmptyBlock(pos);
            }
        }
        if(seed.getItem() instanceof BlockItem item){var state=item.getBlock().defaultBlockState();return state.canSurvive(level,pos)&&level.setBlockAndUpdate(pos,state);}return false;
    }
    public static boolean canHarvest(ServerLevel level,BlockPos pos){
        if(!level.hasChunkAt(pos))return false;
        var state=level.getBlockState(pos);
        return state.getBlock() instanceof CropBlock crop&&crop.isMaxAge(state)||external()&&ADAPTERS.stream().anyMatch(a->a.canHarvest(level,pos));
    }
    public static List<ItemStack> harvest(ServerLevel level,BlockPos pos){
        if(!level.hasChunkAt(pos))return List.of();
        var state=level.getBlockState(pos);
        if(state.getBlock() instanceof CropBlock crop&&crop.isMaxAge(state)){
            var drops=Block.getDrops(state,level,pos,null);return level.setBlockAndUpdate(pos,Blocks.AIR.defaultBlockState())?drops:List.of();
        }
        if(external())for(PlantAdapter a:ADAPTERS)if(a.canHarvest(level,pos))return a.harvest(level,pos);
        return List.of();
    }
    public record HarvestResult(boolean harvested,List<ItemStack> drops) {
        public HarvestResult { drops=List.copyOf(drops); }
        public static final HarvestResult NONE=new HarvestResult(false,List.of());
    }
    public static boolean plant(com.foundations.calculator.content.MachineBlockEntity machine,ItemStack seed,
                                ServerLevel level,BlockPos pos,int tier) {
        if(!com.foundations.calculator.core.MachineWorldActions.loaded(level,pos)||!level.isEmptyBlock(pos)||!accepts(seed,tier))return false;
        var before=level.getBlockState(pos);
        // All CropBlock seeds have a bounded single-block planting path, including modded CropBlock crops.
        if(seed.getItem() instanceof BlockItem item&&item.getBlock() instanceof CropBlock) {
            var placed=item.getBlock().defaultBlockState();
            return placed.canSurvive(level,pos)&&com.foundations.calculator.core.MachineWorldActions.replace(machine,pos,before,placed,
                seed,com.foundations.calculator.core.MachineWorldActions.Action.PLANT);
        }
        if(external()) {
            for(PlantAdapter adapter:ADAPTERS)if(adapter.accepts(seed,tier)&&adapter.canPlant(seed,level,pos)) {
                if(!com.foundations.calculator.core.MachineWorldActions.permits(machine,pos,before,null,seed,
                    com.foundations.calculator.core.MachineWorldActions.Action.PLANT))return false;
                if(adapter.supportsProtectedActions())return adapter.plantProtected(seed.copyWithCount(1),level,pos,machine);
                if(!com.foundations.calculator.core.MachineWorldActions.legacyAllowed(machine,pos,
                    com.foundations.calculator.core.MachineWorldActions.Action.PLANT))return false;
                return adapter.plant(seed.copyWithCount(1),level,pos);
            }
        }
        // Ordinary BlockItems also have a single-block planting path. Opaque SpecialPlantable callbacks do not.
        if(seed.getItem() instanceof BlockItem item) {
            var placed=item.getBlock().defaultBlockState();return placed.canSurvive(level,pos)&&
                com.foundations.calculator.core.MachineWorldActions.replace(machine,pos,before,placed,seed,
                    com.foundations.calculator.core.MachineWorldActions.Action.PLANT);
        }
        return com.foundations.calculator.core.MachineWorldActions.legacyAllowed(machine,pos,
            com.foundations.calculator.core.MachineWorldActions.Action.PLANT)&&
            com.foundations.calculator.core.MachineWorldActions.permits(machine,pos,before,null,seed,
                com.foundations.calculator.core.MachineWorldActions.Action.PLANT)&&plant(seed,level,pos,tier);
    }
    public static HarvestResult harvest(com.foundations.calculator.content.MachineBlockEntity machine,ServerLevel level,BlockPos pos) {
        if(!com.foundations.calculator.core.MachineWorldActions.loaded(level,pos))return HarvestResult.NONE;
        var before=level.getBlockState(pos);
        if(before.getBlock() instanceof CropBlock crop&&crop.isMaxAge(before)) {
            // Loot is retained locally until the permission-checked removal succeeds. No drops spawn on denial.
            var drops=Block.getDrops(before,level,pos,null);
            return com.foundations.calculator.core.MachineWorldActions.replace(machine,pos,before,Blocks.AIR.defaultBlockState(),
                ItemStack.EMPTY,com.foundations.calculator.core.MachineWorldActions.Action.HARVEST)
                ?new HarvestResult(true,drops):HarvestResult.NONE;
        }
        if(external())for(PlantAdapter adapter:ADAPTERS)if(adapter.canHarvest(level,pos)) {
            if(!com.foundations.calculator.core.MachineWorldActions.permits(machine,pos,before,null,ItemStack.EMPTY,
                com.foundations.calculator.core.MachineWorldActions.Action.HARVEST))return HarvestResult.NONE;
            if(!adapter.supportsProtectedActions()&&!com.foundations.calculator.core.MachineWorldActions.legacyAllowed(machine,pos,
                com.foundations.calculator.core.MachineWorldActions.Action.HARVEST))return HarvestResult.NONE;
            var drops=adapter.supportsProtectedActions()?adapter.harvestProtected(level,pos,machine):adapter.harvest(level,pos);
            boolean changed=!before.equals(level.getBlockState(pos))||!drops.isEmpty();
            return changed?new HarvestResult(true,drops):HarvestResult.NONE;
        }
        return HarvestResult.NONE;
    }
    /** Empty means no protection-aware adapter claims growth at this position. */
    public static Optional<Boolean> growWithAdapter(com.foundations.calculator.content.MachineBlockEntity machine,
                                                    ServerLevel level,BlockPos pos) {
        if(!external()||!com.foundations.calculator.core.MachineWorldActions.loaded(level,pos))return Optional.empty();
        for(PlantAdapter adapter:ADAPTERS)if(adapter.supportsProtectedActions()&&adapter.canGrowProtected(level,pos)) {
            if(!com.foundations.calculator.core.MachineWorldActions.permits(machine,pos,level.getBlockState(pos),null,
                new ItemStack(Items.BONE_MEAL),com.foundations.calculator.core.MachineWorldActions.Action.GROW))return Optional.of(false);
            return Optional.of(adapter.growProtected(level,pos,machine));
        }
        return Optional.empty();
    }
    private FoundationsPlants(){}
}
