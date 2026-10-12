package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Beta1GameTests {
    private static MachineBlockEntity place(GameTestHelper h,String kind){
        var pos=new BlockPos(3,2,3);h.setBlock(pos,Content.block(kind));return (MachineBlockEntity)h.getBlockEntity(pos);
    }
    private static void furnaceSettings(Runnable action){
        R6GameTests.setting("machine.reinforced_furnace.energyOverride",1_000_000,()->
        R6GameTests.setting("machines.processEnergyMultiplier",100.0,()->
        R6GameTests.setting("machine.reinforced_furnace.energyMultiplier",100.0,()->
        R6GameTests.setting("machine.reinforced_furnace.ticksOverride",1,action))));
    }
    @GameTest(template="empty",batch="beta1") public static void largeCostCannotRunOnIntegerMaximum(GameTestHelper h){
        furnaceSettings(()->{
            var m=place(h,"reinforced_furnace");m.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON));m.energy.load(Integer.MAX_VALUE);
            var state=MachineDiagnostics.machine(m);h.assertTrue(state.requiredEnergy()==10_000_000_000L&&state.status()==MachineStatus.NEED_POWER,"Diagnostics report the full 10B FE requirement");
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(0).is(Items.RAW_IRON)&&m.inventory.getStackInSlot(14).isEmpty()&&m.energy.stored()==Integer.MAX_VALUE,"Insufficient long energy consumes nothing");
        });h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void furnaceConsumesExactLongCost(GameTestHelper h){
        furnaceSettings(()->{
            var m=place(h,"reinforced_furnace");m.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON));m.energy.load(10_000_000_019L);
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(14).is(Items.IRON_INGOT)&&m.energy.stored()==19,"One cycle spends exactly 10B FE");
        });h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void longCostUpgradesMatchMenuDiagnostics(GameTestHelper h){
        furnaceSettings(()->{
            var m=place(h,"reinforced_furnace");m.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON));m.inventory.setStackInSlot(22,new ItemStack(Content.item("energy_upgrade"),4));m.energy.load(7_142_857_149L);
            var p=h.makeMockPlayer(GameType.SURVIVAL);p.setPos(m.getBlockPos().getCenter());var menu=CalculatorMenu.forMachine(1,p.getInventory(),m);
            h.assertTrue(menu.requiredEnergy()==7_142_857_142L,"Menu transmits all four long words of upgraded cost");
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(14).is(Items.IRON_INGOT)&&m.energy.stored()==7,"Upgrade math and execution agree above int range");
        });h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void portableCalculationConsumesLongCost(GameTestHelper h){
        var p=h.makeMockPlayer(GameType.SURVIVAL);var stack=new ItemStack(Content.item("calculator"));p.setItemInHand(InteractionHand.MAIN_HAND,stack);
        var input=new ProcessInput(java.util.List.of(new ItemStack(Items.COBBLESTONE),new ItemStack(Items.OAK_PLANKS)));
        var recipe=RecipeIndex.forMachine(h.getLevel(),"calculator",p.getUUID()).stream().filter(r->r.value().matches(input,h.getLevel())).findFirst().orElseThrow();
        R6GameTests.setting("recipe."+recipe.id()+".energyOverride",50_000_000,()->R6GameTests.setting("machines.processEnergyMultiplier",100.0,()->{
            var item=(CalculatorItem)stack.getItem();stack.set(Content.ENERGY_LONG.get(),5_000_000_013L);
            var menu=CalculatorMenu.forItem(1,p.getInventory(),InteractionHand.MAIN_HAND);
            menu.handler.setStackInSlot(0,new ItemStack(Items.COBBLESTONE));menu.handler.setStackInSlot(1,new ItemStack(Items.OAK_PLANKS));
            h.assertTrue(menu.requiredEnergy()==5_000_000_000L&&menu.clickMenuButton(p,0),"Portable menu accepts the true long-valued balance");
            h.assertTrue(item.energyLong(stack)==13&&menu.handler.getStackInSlot(0).isEmpty()&&menu.handler.getStackInSlot(1).isEmpty(),"Portable commit spends the exact long cost once");
        }));h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void nativeItemChargingCrossesIntegerLimit(GameTestHelper h){
        R6GameTests.setting("energy.transferMultiplier",100000.0,()->R6GameTests.setting("energy.itemCapacityMultiplier",100000.0,()->{
            var m=place(h,"advanced_power_cube");var stack=new ItemStack(Content.item("energy_module"));m.inventory.setStackInSlot(20,stack);m.energy.load(3_000_000_017L);
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(((CalculatorItem)stack.getItem()).energyLong(stack)==3_000_000_017L&&m.energy.stored()==0,"Native charging moves the full balance without an int cap");
        }));h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void nativeItemChargingRetainsFeDirectionGate(GameTestHelper h){
        R6GameTests.setting("power.fe.output",false,()->{
            var m=place(h,"advanced_power_cube");var stack=new ItemStack(Content.item("energy_module"));m.inventory.setStackInSlot(20,stack);m.energy.load(3000);
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(((CalculatorItem)stack.getItem()).energyLong(stack)==0&&m.energy.stored()==3000,"Long charging cannot bypass the existing FE output policy");
        });h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void nativeItemDischargeCrossesIntegerLimit(GameTestHelper h){
        R6GameTests.setting("energy.machineCapacityMultiplier",100000.0,()->{
            var m=place(h,"advanced_power_cube");var stack=new ItemStack(Content.item("energy_module"));stack.set(Content.ENERGY_LONG.get(),3_000_000_021L);m.inventory.setStackInSlot(0,stack);
            long moved=EnergyItems.discharge(m.inventory,0,m.energy,4_000_000_000L);
            h.assertTrue(moved==3_000_000_021L&&m.energy.stored()==moved&&((CalculatorItem)stack.getItem()).energyLong(stack)==0,"Native discharge preserves every FE above integer range");
        });h.succeed();
    }
    @GameTest(template="empty",batch="beta1") public static void machineStateSurvivesSaveLoadAboveIntegerRange(GameTestHelper h){
        var m=place(h,"reinforced_furnace");m.energy.load(9_007_199_254_740_993L);m.progress=83;m.burnTime=17;m.nutrient=29;
        m.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON,3));m.inventory.setStackInSlot(22,new ItemStack(Content.item("energy_upgrade"),4));m.pending.add(new ItemStack(Items.DIAMOND,2));
        var owner=java.util.UUID.randomUUID();UuidTags.put(m.program,"Owner",owner);m.program.putInt("Side2",3);m.program.putInt("RedstoneMode",3);
        var saved=m.saveWithoutMetadata(h.getLevel().registryAccess());var copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.loadWithComponents(saved,h.getLevel().registryAccess());
        h.assertTrue(copy.energy.stored()==9_007_199_254_740_993L&&copy.progress==83&&copy.burnTime==17&&copy.nutrient==29,"Saved counters and exact long balance survive reload");
        h.assertTrue(copy.inventory.getStackInSlot(0).getCount()==3&&copy.upgrades("energy_upgrade")==4&&copy.pending.size()==1&&copy.pending.getFirst().getCount()==2,"Inventory, upgrades and pending output survive reload");
        h.assertTrue(owner.equals(copy.owner())&&copy.program.getIntOr("Side2",0)==3&&copy.redstoneMode()==3,"Owner, side gates and pause state survive reload");h.succeed();
    }
}
