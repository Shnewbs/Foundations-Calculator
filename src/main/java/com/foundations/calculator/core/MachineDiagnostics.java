package com.foundations.calculator.core;

import java.util.*;
import com.foundations.calculator.content.*;
import net.minecraft.core.Direction;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;
import static com.foundations.calculator.core.MachineStatus.*;

/** Server-computed operating feedback. No client decides whether a recipe can run. */
public final class MachineDiagnostics {
    public record Snapshot(MachineStatus status,int requiredEnergy) {}
    private MachineDiagnostics() {}
    private static Snapshot state(MachineStatus status){return new Snapshot(status,0);}
    private static Snapshot power(MachineStatus running,int required,int available){return new Snapshot(available<required?NEED_POWER:running,required);}
    public static String family(String kind){return switch(kind){case "atomic_calculator"->"atomic";case "scientific_calculator"->"scientific";case "flawless_calculator"->"flawless";default->kind;};}
    public static String family(MachineBlockEntity m){return m.kind().equals("docking_station")?family(Content.path(m.inventory.getStackInSlot(20))):family(m.kind());}
    public static boolean recipeIngredient(Level level,String family,ItemStack stack){return level!=null&&RecipeIndex.allForMachine(level,family).stream().anyMatch(r->r.value().inputs().stream().anyMatch(i->i.test(stack)));}
    public static int cost(RecipeHolder<ProcessRecipe> recipe,MachineBlockEntity m){
        int base=CalculatorConfig.energy(m.kind(),RecipePolicies.energy(recipe,m.kind().equals("docking_station")?10:0));
        return m.upgradeEnergyCost(base);
    }
    public static Snapshot machine(MachineBlockEntity m){
        if(!CalculatorConfig.machineEnabled(m.kind()))return state(DISABLED);
        int redstone=m.redstoneMode();boolean powered=m.getLevel().hasNeighborSignal(m.getBlockPos());if(redstone==3||redstone==1&&!powered||redstone==2&&powered)return state(PAUSED);
        if(!m.pending.isEmpty())return state(OUTPUT_BLOCKED);
        var level=m.getLevel();String kind=m.kind();int energy=m.energy.getEnergyStored();
        if(kind.equals("research_chamber")){
            if(m.inventory.getStackInSlot(0).isEmpty())return state(NEED_INPUT);if(m.owner()==null)return state(NEED_OWNER);
            var sample=new ProcessInput(List.of(m.inventory.getStackInSlot(0)));
            var recipes=RecipeIndex.allForMachine(level,"research").stream().filter(r->r.value().matches(sample,level)).toList();if(recipes.isEmpty())return state(NO_RECIPE);
            var known=ResearchData.get(level.getServer()).groups(m.owner());var next=recipes.stream().filter(r->!known.contains(r.value().researchGroup())).findFirst();if(next.isEmpty())return state(RESEARCH_KNOWN);
            var recipe=next.get();int cost=CalculatorConfig.flag("research.consumeEnergy",true)?CalculatorConfig.energy(kind,RecipePolicies.energy(recipe,0)):0;
            return power(RESEARCH_READY,cost,energy);
        }
        if(kind.equals("module_workstation"))return state(m.inventory.getStackInSlot(20).isEmpty()?NEED_FLAWLESS:MODULES_READY);
        if(kind.endsWith("greenhouse")){
            int house=m.program.getIntOr("HouseState",0);
            if(house==1)return power(BUILDING,CalculatorConfig.energy(kind,CalculatorConfig.integer("greenhouse.buildEnergy",100)),energy);if(house==3)return state(DEMOLISHING);if(house!=2)return state(INCOMPLETE);
            if(m.program.getBooleanOr("Paused",false)||level.hasNeighborSignal(m.getBlockPos()))return state(PAUSED);
            return power(FARMING,CalculatorConfig.energy(kind,CalculatorConfig.integer("greenhouse.growEnergy",150)),energy);
        }
        if(kind.equals("weather_controller")){
            if(m.program.getIntOr("Cooldown",0)>0)return state(COOLDOWN);
            if(!level.hasNeighborSignal(m.getBlockPos()))return state(NEED_REDSTONE);
            boolean current=switch(m.program.getIntOr("Mode",0)){case 1->level.getLevelData().isRaining();case 2->level.getLevelData().isThundering();default->!level.isDay();};
            return current==m.program.getBooleanOr("Target",false)?state(TARGET_REACHED):power(RUNNING,CalculatorConfig.energy(kind,CalculatorConfig.integer("world.weatherEnergyPerTick",2500)),energy);
        }
        if(kind.equals("magnetic_flux"))return state(level.hasNeighborSignal(m.getBlockPos())?PAUSED:level.getCapability(Capabilities.ItemHandler.BLOCK,m.getBlockPos().below(),Direction.UP)==null?NEED_STORAGE:COLLECTING);
        if(kind.equals("gas_lantern_off"))return state(m.burnTime>0?RUNNING:NEED_FUEL);
        if(kind.equals("co2_generator")){
            var forward=m.getBlockState().getValue(MachineBlock.FACING).getOpposite();
            if(!(level.getBlockEntity(m.getBlockPos().relative(forward.getClockWise().getOpposite(),3)) instanceof MachineBlockEntity other)||!other.kind().equals("flawless_greenhouse"))return state(NEED_GREENHOUSE);
            if(m.burnTime>0)return state(m.program.getIntOr("GasAdd",0)>0?GAS_RUNNING:GAS_CONTROLLED);
            if(m.inventory.getStackInSlot(0).getBurnTime(RecipeType.SMELTING)<=0)return state(NEED_FUEL);
            return power(GAS_RUNNING,CalculatorConfig.energy(kind,CalculatorConfig.integer("greenhouse.co2FuelEnergy",100000)),energy);
        }
        if(kind.equals("stone_assimilator")||kind.equals("algorithm_assimilator"))return state(CHECK_TREE);
        if(kind.equals("calculator_screen_block"))return state(ENERGY_DISPLAY);
        if(kind.equals("calculator_locator")){
            if(m.program.getIntOr("Size",0)==0)return state(INCOMPLETE);
            var tag=CircuitData.tag(m.inventory.getStackInSlot(0));if(!com.foundations.calculator.core.UuidTags.has(tag,"Owner"))return state(NEED_LOCATOR);
            return state(m.program.getIntOr("Stability",0)<CalculatorConfig.integer("generation.locatorStableThreshold",7)&&level.getPlayerByUUID(com.foundations.calculator.core.UuidTags.get(tag,"Owner"))==null?NEED_OWNER:GENERATING);
        }
        if(kind.equals("conductor_mast")&&m.inventory.getStackInSlot(0).isEmpty())return state(m.program.getIntOr("StrikeTicks",0)>0?GENERATING:WAITING_LIGHTNING);
        if(kind.equals("hand_cranked_generator")||MachineProfiles.storage(kind)||kind.equals("creative_power_cube")){
            var item=com.foundations.calculator.api.FoundationsEnergy.item(m.inventory.getStackInSlot(20));
            if(energy>0&&item!=null&&item.getEnergyStored()<item.getMaxEnergyStored())return state(CHARGING);
            return state(energy>0?POWER_AVAILABLE:kind.equals("hand_cranked_generator")?CRANK:NEED_POWER);
        }
        if(kind.endsWith("extractor"))return state(m.burnTime>0?GENERATING:m.nutrient<CalculatorConfig.integer("generation.extractorNutrientCost",400)?NEED_FEED:NEED_FUEL);
        if(kind.equals("health_processor")||kind.equals("hunger_processor")){
            String point=kind.equals("health_processor")?"health":"hunger";
            if(NutritionData.capacity(m.inventory.getStackInSlot(20),point)==0)return state(NEED_MODULE);
            return state(m.nutrient>0?CHARGING_MODULE:NEED_INPUT);
        }
        if(kind.equals("analysing_chamber")){
            ItemStack in=m.inventory.getStackInSlot(0);if(in.isEmpty())return state(NEED_INPUT);
            return !Content.path(in).startsWith("circuit_board_")?state(NO_RECIPE):CircuitData.analysed(in)?state(ALREADY_ANALYSED):power(RUNNING,m.upgradeEnergyCost(CalculatorConfig.energy(kind,0)),energy);
        }
        if(kind.equals("dynamic_calculator")){
            if(!m.program.getBooleanOr("DynamicFormed",false))return state(INCOMPLETE);
            return lanes(level,m.inventory,energy,m.owner(),m.kind());
        }
        if(kind.equals("docking_station")&&m.inventory.getStackInSlot(20).isEmpty())return state(NEED_CALCULATOR);
        var input=ProcessTransactions.input(m.inventory,m.inputCount());if(input.isEmpty())return state(NEED_INPUT);
        if(kind.equals("atomic_multiplier")){
            if(java.util.stream.IntStream.range(0,8).anyMatch(i->m.inventory.getStackInSlot(i).isEmpty()))return state(NEED_INPUT);
            return power(RUNNING,CalculatorConfig.energy(kind,CalculatorConfig.MULTIPLIER_ENERGY.get()),energy);
        }
        if(kind.equals("reinforced_furnace"))return level.getRecipeManager().getRecipeFor(RecipeType.SMELTING,new SingleRecipeInput(m.inventory.getStackInSlot(0)),level).isEmpty()?state(NO_RECIPE):power(RUNNING,m.upgradeEnergyCost(CalculatorConfig.energy(kind,500)),energy);
        var recipe=RecipeIndex.forMachine(level,family(m),m.owner()).stream().filter(r->r.value().matches(input,level)).findFirst();
        return recipe.isEmpty()?state(NO_RECIPE):power(RUNNING,cost(recipe.get(),m),energy);
    }
    public static Snapshot portable(Level level,ItemStackHandler slots,int inputs,String kind,ItemStack calculator){
        return portable(level,slots,inputs,kind,calculator,null);
    }
    public static Snapshot portable(Level level,ItemStackHandler slots,int inputs,String kind,ItemStack calculator,java.util.UUID owner){
        int energy=calculator.getItem() instanceof CalculatorItem c?c.energy(calculator):0;
        if(kind.equals("dynamic_module"))return lanes(level,slots,energy,owner,kind);
        var input=ProcessTransactions.input(slots,inputs);if(input.isEmpty())return state(NEED_INPUT);
        var match=RecipeIndex.forMachine(level,family(kind),owner).stream().filter(r->r.value().matches(input,level)).findFirst();
        return match.isEmpty()?state(NO_RECIPE):power(READY,CalculatorConfig.energy(kind,RecipePolicies.energy(match.get(),1)),energy);
    }
    private static Snapshot lanes(Level level,ItemStackHandler slots,int energy,java.util.UUID owner,String kind){
        boolean hasInput=false;
        for(int lane=0;lane<3;lane++){
            int start=lane*2,count=lane==2?3:2;var stacks=new ArrayList<ItemStack>();for(int i=0;i<count;i++)stacks.add(slots.getStackInSlot(start+i));
            var input=new ProcessInput(stacks);hasInput|=!input.isEmpty();
            var match=RecipeIndex.forMachine(level,new String[]{"calculator","scientific","atomic"}[lane],owner).stream().filter(r->r.value().matches(input,level)).findFirst();
            if(match.isPresent())return power(READY,CalculatorConfig.energy(kind,RecipePolicies.energy(match.get(),1)),energy);
        }
        return state(hasInput?NO_RECIPE:NEED_INPUT);
    }
}
