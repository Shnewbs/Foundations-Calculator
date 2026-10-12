package com.foundations.calculator.compat;

import java.util.function.*;
import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.core.PowerPolicy.Scope;
import dev.technici4n.grandpower.api.ILongEnergyStorage;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import com.foundations.calculator.api.EnergyPort;

public final class GrandPowerEnergy {
    private static final PowerPolicy POLICY = PowerPolicy.GRANDPOWER;
    public static EnergyConversion conversion() { return POLICY.conversion(1, true); }
    public static void register() {
        FoundationsEnergy.registerItem("grandPower", stack -> wrap(stack.getCapability(ILongEnergyStorage.ITEM),
                GrandPowerEnergy::conversion, () -> POLICY.input(Scope.ITEM), () -> POLICY.output(Scope.ITEM)));
        FoundationsEnergy.registerBlock("grandPower", (level,pos,side) -> wrap(level.getCapability(ILongEnergyStorage.BLOCK,pos,side),
                GrandPowerEnergy::conversion, () -> POLICY.input(Scope.BLOCK), () -> POLICY.output(Scope.BLOCK)));
    }
    public static void capabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(ILongEnergyStorage.BLOCK, Content.MACHINE_ENTITY.get(), (machine,side) -> expose(machine.longEnergyPort(side),
                GrandPowerEnergy::conversion, () -> POLICY.input(Scope.BLOCK), () -> POLICY.output(Scope.BLOCK)));
        for (var entry : Content.ITEMS_BY_ID.values()) if (entry.get() instanceof CalculatorItem item && item.capacity > 0)
            event.registerItem(ILongEnergyStorage.ITEM, (stack,ctx) -> expose(item.longStorage(stack), GrandPowerEnergy::conversion,
                    () -> POLICY.input(Scope.ITEM), () -> POLICY.output(Scope.ITEM)), item);
    }
    public static EnergyPort wrap(ILongEnergyStorage energy, Supplier<EnergyRatio> ratio, BooleanSupplier enabled) {
        return energy == null ? null : LongEnergyBridge.toFE(storage(energy), ratio, enabled);
    }
    public static EnergyPort wrap(ILongEnergyStorage energy, Supplier<EnergyConversion> conversion,
                                      BooleanSupplier input, BooleanSupplier output) {
        return energy == null ? null : LongEnergyBridge.toFE(storage(energy), conversion, input, output);
    }
    private static LongEnergyBridge.Storage storage(ILongEnergyStorage energy) {
        return new LongEnergyBridge.Storage() {
            public long receive(long n,boolean simulate) { return energy.receive(n,simulate); }
            public long extract(long n,boolean simulate) { return energy.extract(n,simulate); }
            public long stored() { return energy.getAmount(); } public long capacity() { return energy.getCapacity(); }
            public boolean canReceive() { return energy.canReceive(); } public boolean canExtract() { return energy.canExtract(); }
        };
    }
    public static ILongEnergyStorage expose(com.foundations.calculator.api.LongEnergyStorage energy, Supplier<EnergyConversion> conversion,
                                           BooleanSupplier input, BooleanSupplier output) {
        return energy == null ? null : expose(LongEnergyBridge.fromLong(energy, conversion, input, output));
    }
    public static ILongEnergyStorage expose(EnergyPort energy, Supplier<EnergyRatio> ratio, BooleanSupplier enabled) {
        return energy == null ? null : expose(LongEnergyBridge.fromFE(energy, ratio, enabled));
    }
    public static ILongEnergyStorage expose(EnergyPort energy, Supplier<EnergyConversion> conversion,
                                           BooleanSupplier input, BooleanSupplier output) {
        return energy == null ? null : expose(LongEnergyBridge.fromFE(energy, conversion, input, output));
    }
    private static ILongEnergyStorage expose(LongEnergyBridge.Storage storage) {
        return new ILongEnergyStorage() {
            public long receive(long n,boolean simulate) { return storage.receive(n,simulate); }
            public long extract(long n,boolean simulate) { return storage.extract(n,simulate); }
            public long getAmount() { return storage.stored(); } public long getCapacity() { return storage.capacity(); }
            public boolean canReceive() { return storage.canReceive(); } public boolean canExtract() { return storage.canExtract(); }
        };
    }
    private GrandPowerEnergy() {}
}
