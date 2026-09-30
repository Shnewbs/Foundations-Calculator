package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class R5GameTests {
    public static MachineBlockEntity place(GameTestHelper h){var p=new BlockPos(3,2,3);h.setBlock(p,Content.block("power_cube"));return (MachineBlockEntity)h.getBlockEntity(p);}
    @GameTest(template="empty",batch="r5_energy") public static void nativeMekanismPortsAndItems(GameTestHelper h){if(net.neoforged.fml.ModList.get().isLoaded("mekanism"))R5EnergyAssertions.mekanism(h);h.succeed();}
    @GameTest(template="empty",batch="r5_energy") public static void nativeGregTechPacketsAndGates(GameTestHelper h){if(net.neoforged.fml.ModList.get().isLoaded("gtceu"))R5GregTechAssertions.packets(h);h.succeed();}
    @GameTest(template="empty",batch="r5_energy",timeoutTicks=100) public static void gregTechCableDeliversPowerWithRealLoss(GameTestHelper h){if(net.neoforged.fml.ModList.get().isLoaded("gtceu"))R5GregTechAssertions.cable(h);else h.succeed();}
    @GameTest(template="empty",batch="r5_energy") public static void nativeGregTechItemsAndRoundTrip(GameTestHelper h){if(net.neoforged.fml.ModList.get().isLoaded("gtceu"))R5GregTechAssertions.items(h);h.succeed();}
    @GameTest(template="empty",batch="r5_energy") public static void nativeIndustrializationPorts(GameTestHelper h){if(net.neoforged.fml.ModList.get().isLoaded("modern_industrialization"))R5EnergyAssertions.mi(h);h.succeed();}
    @GameTest(template="empty",batch="r5_energy") public static void grandPowerLongLimits(GameTestHelper h){if(net.neoforged.fml.ModList.get().isLoaded("grandpower"))R5EnergyAssertions.grandpower(h);h.succeed();}
    @GameTest(template="empty",batch="r5_energy") public static void fractionalAndFullBufferTransfersConserveFE(GameTestHelper h){
        var fe=new StoredEnergy(101,()->{});var nativeStorage=com.foundations.calculator.compat.LongEnergyBridge.fromFE(fe,()->EnergyRatio.nativePerFE(2.5),()->true);
        h.assertTrue(nativeStorage.receive(Long.MAX_VALUE,true)==250&&fe.getEnergyStored()==0,"Native long simulation is overflow-safe and cannot mutate");
        h.assertTrue(nativeStorage.receive(Long.MAX_VALUE,false)==250&&fe.getEnergyStored()==100,"Only whole conversion quanta enter storage");
        h.assertTrue(nativeStorage.receive(4,false)==0&&fe.getEnergyStored()==100,"Sub-quantum requests cannot create FE");
        h.assertTrue(nativeStorage.extract(249,false)==245&&fe.getEnergyStored()==2,"Fractional remainder stays with original storage");
        h.assertTrue(nativeStorage.extract(Long.MAX_VALUE,false)==5&&fe.getEnergyStored()==0,"A round trip conserves every transferred unit");h.succeed();
    }
}
