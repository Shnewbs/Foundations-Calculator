package com.foundations.calculator;

import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.content.CalculatorItem;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.content.MachineBlockEntity;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.GameTestHolder;
import net.neoforged.neoforge.gametest.PrefixGameTestTemplate;

/** Final 0.0.2a.R1 guards for migration and long-buffer behavior. */
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Alpha2FinalizationGameTests {
    @GameTest(template="empty",batch="alpha2_final")
    public static void machineLongEnergySurvivesSaveAndCapacityDrop(GameTestHelper h) {
        var machine=R5GameTests.place(h);
        R6GameTests.setting("energy.machineCapacityMultiplier",100000.0,()->{
            h.assertTrue(machine.energy.receive(3_000_000_000L,false)==3_000_000_000L,"Machine accepts long FE before save");
            var saved=machine.saveWithoutMetadata(h.getLevel().registryAccess());
            R6GameTests.setting("energy.machineCapacityMultiplier",1.0,()->{
                var copy=new MachineBlockEntity(machine.getBlockPos(),machine.getBlockState());
                copy.loadWithComponents(saved,h.getLevel().registryAccess());
                h.assertTrue(copy.energy.stored()==3_000_000_000L,"Lowered capacity cannot truncate persisted long FE");
                h.assertTrue(copy.energy.stored()>copy.energy.capacity(),"Restored buffer may remain safely over current capacity");
                h.assertTrue(copy.energy.receive(1,false)==0,"Over-capacity restored buffer refuses new FE");
                copy.energy.extract(1_000_000_000L,false);
                h.assertTrue(copy.energy.stored()==2_000_000_000L,"Over-capacity restored buffer drains incrementally");
            });
        });h.succeed();
    }

    @GameTest(template="empty",batch="alpha2_final")
    public static void itemLongEnergySurvivesSerializationAndCapacityDrop(GameTestHelper h) {
        ItemStack stack=new ItemStack(Content.item("energy_module"));var item=(CalculatorItem)stack.getItem();
        R6GameTests.setting("energy.itemCapacityMultiplier",100000.0,()->{
            h.assertTrue(item.longStorage(stack).receive(3_000_000_000L,false)==3_000_000_000L,"Item accepts long FE before save");
            var saved=stack.save(h.getLevel().registryAccess());
            R6GameTests.setting("energy.itemCapacityMultiplier",1.0,()->{
                var restored=ItemStack.parse(h.getLevel().registryAccess(),saved).orElseThrow();var restoredItem=(CalculatorItem)restored.getItem();
                h.assertTrue(restoredItem.energyLong(restored)==3_000_000_000L,"Lowered item capacity cannot truncate persisted long FE");
                h.assertTrue(restoredItem.longStorage(restored).receive(1,false)==0,"Over-capacity item refuses new FE");
                restoredItem.longStorage(restored).extract(1_000_000_000L,false);
                h.assertTrue(restoredItem.energyLong(restored)==2_000_000_000L,"Over-capacity item drains incrementally");
            });
        });h.succeed();
    }

    @GameTest(template="empty",batch="alpha2_final")
    public static void legacyIntegerNbtPromotesWithoutLoss(GameTestHelper h) {
        CompoundTag legacy=new CompoundTag();legacy.putInt("Energy",1_987_654_321);
        h.assertTrue(legacy.getLong("Energy")==1_987_654_321L,"Numeric NBT promotes the R9 int Energy tag to long without loss");h.succeed();
    }

    @GameTest(template="empty",batch="alpha2_final")
    public static void legacyItemComponentMigratesOnFirstWrite(GameTestHelper h) {
        ItemStack stack=new ItemStack(Content.item("energy_module"));var item=(CalculatorItem)stack.getItem();
        stack.set(Content.ENERGY.get(),12345);
        h.assertTrue(item.energyLong(stack)==12345L,"Legacy int item component remains readable");
        h.assertTrue(item.longStorage(stack).receive(5,false)==5,"First long write succeeds");
        h.assertTrue(stack.getOrDefault(Content.ENERGY_LONG.get(),-1L)==12350L,"First write creates exact long component");
        h.assertTrue(stack.getOrDefault(Content.ENERGY.get(),-1)==12350,"Legacy bounded mirror stays synchronized");h.succeed();
    }

    @GameTest(template="empty",batch="alpha2_final")
    public static void publicLongBlockRouteKeepsFullFoundationsCapacity(GameTestHelper h) {
        var machine=R5GameTests.place(h);
        R6GameTests.setting("energy.machineCapacityMultiplier",100000.0,()->{
            machine.energy.receive(3_000_000_000L,false);
            var route=FoundationsEnergy.longBlockRoute(h.getLevel(),machine.getBlockPos(),Direction.NORTH);
            h.assertTrue(route.storage()!=null&&route.id().equals("foundations"),"Own machine selects the Foundations long route");
            h.assertTrue(route.storage().stored()==3_000_000_000L,"Public long route reports full storage above int range");
            h.assertTrue(route.storage().capacity()>Integer.MAX_VALUE,"Public long route reports full long capacity");
        });h.succeed();
    }

    @GameTest(template="empty",batch="alpha2_final")
    public static void disabledPresetIsScaleIdentity(GameTestHelper h) {
        R6GameTests.setting("balance.applyPreset",false,()->R6GameTests.setting("energy.machineCapacityMultiplier",1.0,()->
            R6GameTests.setting("machine.power_cube.capacityMultiplier",1.0,()->{
                long configured=CalculatorConfig.machine("power_cube","capacity",50000);
                h.assertTrue(CalculatorConfig.machineCapacity("power_cube",50000)==configured,"Disabled presets preserve explicit base capacity");
            })));
        h.succeed();
    }

    private Alpha2FinalizationGameTests() {}
}
