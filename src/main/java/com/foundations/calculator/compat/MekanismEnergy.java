package com.foundations.calculator.compat;

import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.core.PowerPolicy.Scope;
import mekanism.api.Action;
import mekanism.api.energy.*;
import mekanism.common.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

public final class MekanismEnergy {
    private static final PowerPolicy POLICY = PowerPolicy.MEKANISM;
    public static boolean enabled() { return POLICY.enabled() && IEnergyConversionHelper.INSTANCE.feConversion().isEnabled(); }
    public static double inheritedRatio() { return IEnergyConversionHelper.INSTANCE.feConversion().getConversion(); }
    public static double effectiveRatio() {
        double override = CalculatorConfig.decimal("power.mekanism.joulesPerFE", 0);
        return override > 0 ? override : inheritedRatio();
    }
    public static EnergyConversion conversion() { return POLICY.conversion(effectiveRatio(), true); }
    public static void register() {
        FoundationsEnergy.registerItem("mekanism", stack -> wrap(stack.getCapability(Capabilities.STRICT_ENERGY.item()), Scope.ITEM));
        FoundationsEnergy.registerBlock("mekanism", (level,pos,face) -> wrap(level.getCapability(Capabilities.STRICT_ENERGY.block(),pos,face), Scope.BLOCK));
    }
    public static void capabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.STRICT_ENERGY.block(), Content.MACHINE_ENTITY.get(),
                (machine,side) -> expose(machine.longEnergyPort(side), Scope.BLOCK));
        for (var entry : Content.ITEMS_BY_ID.values()) if (entry.get() instanceof CalculatorItem item && item.capacity > 0)
            event.registerItem(Capabilities.STRICT_ENERGY.item(), (stack,ctx) -> expose(item.longStorage(stack), Scope.ITEM), item);
    }
    public static IEnergyStorage wrap(IStrictEnergyHandler handler) { return wrap(handler, Scope.BLOCK); }
    public static IEnergyStorage wrap(IStrictEnergyHandler handler, Scope scope) {
        if (handler == null) return null;
        return LongEnergyBridge.toFE(new LongEnergyBridge.Storage() {
            public long receive(long n, boolean simulate) { return n - handler.insertEnergy(n, simulate ? Action.SIMULATE : Action.EXECUTE); }
            public long extract(long n, boolean simulate) { return handler.extractEnergy(n, simulate ? Action.SIMULATE : Action.EXECUTE); }
            private long sum(boolean capacity) {
                long total = 0;
                for (int i = 0; i < handler.getEnergyContainerCount(); i++) {
                    long n = Math.max(0, capacity ? handler.getMaxEnergy(i) : handler.getEnergy(i));
                    total += Math.min(n, Long.MAX_VALUE - total);
                }
                return total;
            }
            public long stored() { return sum(false); } public long capacity() { return sum(true); }
            public boolean canReceive() { return true; } public boolean canExtract() { return true; }
        }, MekanismEnergy::conversion, () -> enabled() && POLICY.input(scope), () -> enabled() && POLICY.output(scope));
    }
    public static IStrictEnergyHandler expose(com.foundations.calculator.api.LongEnergyStorage fe, Scope scope) {
        if (fe == null) return null;
        var storage = LongEnergyBridge.fromLong(fe, MekanismEnergy::conversion,
                () -> enabled() && POLICY.input(scope), () -> enabled() && POLICY.output(scope));
        return handler(storage);
    }
    private static IStrictEnergyHandler handler(LongEnergyBridge.Storage storage) {
        return new IStrictEnergyHandler() {
            public int getEnergyContainerCount() { return 1; }
            public long getEnergy(int i) { return i == 0 ? storage.stored() : 0; }
            public long getMaxEnergy(int i) { return i == 0 ? storage.capacity() : 0; }
            public long getNeededEnergy(int i) { return Math.max(0, getMaxEnergy(i) - getEnergy(i)); }
            public void setEnergy(int i,long value) { throw new UnsupportedOperationException("Use insertEnergy/extractEnergy"); }
            public long insertEnergy(int i,long n,Action action) { return i == 0 && n > 0 ? n - storage.receive(n,action.simulate()) : n; }
            public long extractEnergy(int i,long n,Action action) { return i == 0 ? storage.extract(n,action.simulate()) : 0; }
        };
    }
    public static IStrictEnergyHandler expose(IEnergyStorage fe) { return expose(fe, Scope.BLOCK); }
    public static IStrictEnergyHandler expose(IEnergyStorage fe, Scope scope) {
        if (fe == null) return null;
        var storage = LongEnergyBridge.fromFE(fe, MekanismEnergy::conversion,
                () -> enabled() && POLICY.input(scope), () -> enabled() && POLICY.output(scope));
        return handler(storage);
    }
    private MekanismEnergy() {}
}
