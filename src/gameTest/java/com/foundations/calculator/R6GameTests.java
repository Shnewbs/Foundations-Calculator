package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.gametest.*;
import net.neoforged.neoforge.capabilities.Capabilities;

/** R6 runtime regressions. Conditional adapter tests run only with their optional mod installed. */
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class R6GameTests {
    @SuppressWarnings("unchecked") public static <T> void setting(String key,T value,Runnable test) {
        var spec=(ModConfigSpec.ConfigValue<T>)CalculatorConfig.value(key).orElseThrow();T before=spec.get();
        try{spec.set(value);CalculatorConfig.settingsChanged();test.run();}finally{spec.set(before);CalculatorConfig.settingsChanged();}
    }
    @GameTest(template="empty",batch="r6_power") public static void lossAndDirectionsOnCachedPort(GameTestHelper h) {
        var storage=new StoredEnergy(10000,()->{});
        var policy=PowerPolicy.MI;
        setting("power.modernIndustrialization.inputLossPercent",10,()->setting("power.modernIndustrialization.outputLossPercent",20,()->{
            var port=com.foundations.calculator.compat.LongEnergyBridge.fromFE(storage,()->policy.conversion(4,false),
                ()->policy.input(PowerPolicy.Scope.BLOCK),()->policy.output(PowerPolicy.Scope.BLOCK));
            h.assertTrue(port.receive(100,true)==100&&storage.getEnergyStored()==0,"Loss simulation is read-only");
            h.assertTrue(port.receive(100,false)==100&&storage.getEnergyStored()==360,"Input loss applied");
            setting("power.modernIndustrialization.output",false,()->h.assertTrue(port.extract(100,false)==0,"Cached output gate obeys server config"));
            h.assertTrue(port.extract(100,false)==72&&storage.getEnergyStored()==0,"Lossy roundtrip cannot increase energy");
        }));h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void feGateDoesNotDisableRawNativeStorage(GameTestHelper h) {
        var machine=R5GameTests.place(h);var cached=h.getLevel().getCapability(Capabilities.EnergyStorage.BLOCK,machine.getBlockPos(),Direction.NORTH);
        setting("power.fe.blockPorts",false,()->{
            h.assertTrue(cached.receiveEnergy(100,false)==0&&!cached.canExtract(),"FE facade is closed");
            h.assertTrue(machine.energyPort(Direction.NORTH).receiveEnergy(100,false)==100,"Raw storage still available to native adapters");
        });h.assertTrue(cached.extractEnergy(100,false)==100,"FE facade recovers live");h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void powerReportsDoNotTransferEnergy(GameTestHelper h) {
        var machine=R5GameTests.place(h);machine.energy.load(200);long tick=h.getLevel().getGameTime();machine.euOutputBudget.consume(tick,1);
        var lines=PowerDiagnostics.machine(machine);var overview=PowerDiagnostics.overview();
        h.assertTrue(!lines.isEmpty()&&!overview.isEmpty()&&machine.energy.getEnergyStored()==200,"Reports are read-only");
        h.assertTrue(machine.euOutputBudget.remaining(tick,4)==3,"Diagnostics cannot reset packet budget");h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void changedServerRatioRebuildsCachedPlan(GameTestHelper h) {
        var policy=PowerPolicy.MEKANISM;var a=policy.conversion(2.5,true);h.assertTrue(a==policy.conversion(2.5,true),"Unchanged settings reuse plan");
        setting("power.mekanism.inputLossPercent",10,()->{
            var b=policy.conversion(2.5,true);h.assertTrue(a!=b&&b.input().nativeUnits()==25&&b.input().feUnits()==9,"Changed settings rebuild exact plan");
        });h.assertTrue(policy.conversion(2.5,true).inputLossPercent()==0,"Restored config takes effect immediately");h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void nativeMekanismOverrideScopeAndRouting(GameTestHelper h) {
        if(net.neoforged.fml.ModList.get().isLoaded("mekanism"))R6NativeAssertions.mekanism(h);h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void nativeMiOverrideAndLoss(GameTestHelper h) {
        if(net.neoforged.fml.ModList.get().isLoaded("modern_industrialization"))R6NativeAssertions.mi(h);h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void nativeGtPacketLossAndScopes(GameTestHelper h) {
        if(net.neoforged.fml.ModList.get().isLoaded("gtceu"))R6NativeAssertions.gregtech(h);h.succeed();
    }
    @GameTest(template="empty",batch="r6_power") public static void nativeGrandPowerScopeAndLoss(GameTestHelper h) {
        if(net.neoforged.fml.ModList.get().isLoaded("grandpower"))R6NativeAssertions.grandpower(h);h.succeed();
    }
    private R6GameTests(){}
}
