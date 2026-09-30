package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.gametest.*;

/** 0.0.2a native-runtime guards. Full integration/copy-world acceptance is still manual. */
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Alpha2GameTests {
    @GameTest(template="empty",batch="alpha2") public static void machineBufferCanCrossIntCeiling(GameTestHelper h){
        var machine=R5GameTests.place(h);
        R6GameTests.setting("energy.machineCapacityMultiplier",100000.0,()->{
            h.assertTrue(machine.energy.capacity()>Integer.MAX_VALUE,"Scaled machine capacity is long-valued");
            long accepted=machine.energy.receive(3_000_000_000L,false);
            h.assertTrue(accepted==3_000_000_000L&&machine.energy.stored()==3_000_000_000L,"Machine retains >2.147B FE");
            h.assertTrue(machine.energy.getEnergyStored()==Integer.MAX_VALUE,"FE facade remains bounded");
        });h.succeed();
    }
    @GameTest(template="empty",batch="alpha2") public static void poweredItemUsesLongComponent(GameTestHelper h){
        ItemStack stack=new ItemStack(Content.item("energy_module"));var item=(CalculatorItem)stack.getItem();
        R6GameTests.setting("energy.itemCapacityMultiplier",100000.0,()->{
            long accepted=item.longStorage(stack).receive(3_000_000_000L,false);
            h.assertTrue(accepted==3_000_000_000L&&item.energyLong(stack)==3_000_000_000L,"Item retains long FE");
            h.assertTrue(stack.has(Content.ENERGY_LONG.get()),"Long component written");
            h.assertTrue(item.storage(stack).getEnergyStored()==Integer.MAX_VALUE,"Legacy FE view saturates safely");
        });h.succeed();
    }
    @GameTest(template="empty",batch="alpha2") public static void intEraEnergyStillLoads(GameTestHelper h){
        var store=new StoredEnergy(5_000_000_000L,()->{});store.load(1_500_000_000);
        h.assertTrue(store.stored()==1_500_000_000L,"R9 int energy promotes without loss");h.succeed();
    }
    @GameTest(template="empty",batch="alpha2") public static void scaleConfigurationIsRegistered(GameTestHelper h){
        h.assertTrue(CalculatorConfig.keys().contains("energy.machineCapacityMultiplier")&&CalculatorConfig.keys().contains("energy.itemCapacityMultiplier")&&
            CalculatorConfig.keys().contains("energy.transferMultiplier")&&CalculatorConfig.keys().contains("balance.applyPreset")&&CalculatorConfig.keys().contains("api.longEnergy.enabled"),
            "0.0.2a scale keys registered");h.succeed();
    }
    private Alpha2GameTests(){}
}
