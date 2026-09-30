package com.foundations.calculator.compat;

import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.core.PowerPolicy.Scope;
import aztech.modern_industrialization.api.energy.*;
import aztech.modern_industrialization.config.MIServerConfig;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.energy.IEnergyStorage;

public final class ModernIndustrializationEnergy {
    private static final PowerPolicy POLICY = PowerPolicy.MI;
    public static double inheritedRatio() { return MIServerConfig.INSTANCE.forgeEnergyPerEu.get(); }
    public static double effectiveRatio() {
        double override = CalculatorConfig.decimal("power.modernIndustrialization.fePerEU",0);
        return override > 0 ? override : inheritedRatio();
    }
    public static EnergyConversion conversion() { return POLICY.conversion(effectiveRatio(),false); }
    public static void register() {
        FoundationsEnergy.registerItem("modernIndustrialization", stack -> GrandPowerEnergy.wrap(stack.getCapability(EnergyApi.ITEM),
                ModernIndustrializationEnergy::conversion, () -> POLICY.input(Scope.ITEM), () -> POLICY.output(Scope.ITEM)));
        FoundationsEnergy.registerBlock("modernIndustrialization", (level,pos,side) -> GrandPowerEnergy.wrap(level.getCapability(EnergyApi.SIDED,pos,side),
                ModernIndustrializationEnergy::conversion, () -> POLICY.input(Scope.BLOCK), () -> POLICY.output(Scope.BLOCK)));
    }
    public static void capabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(EnergyApi.SIDED, Content.MACHINE_ENTITY.get(), (machine,side) -> expose(machine.longEnergyPort(side)));
        for (var entry : Content.ITEMS_BY_ID.values()) if (entry.get() instanceof CalculatorItem item && item.capacity > 0)
            event.registerItem(EnergyApi.ITEM, (stack,ctx) -> GrandPowerEnergy.expose(item.longStorage(stack), ModernIndustrializationEnergy::conversion,
                    () -> POLICY.input(Scope.ITEM), () -> POLICY.output(Scope.ITEM)), item);
    }
    public static MIEnergyStorage expose(com.foundations.calculator.api.LongEnergyStorage energy) {
        if (energy == null) return null;
        var power = GrandPowerEnergy.expose(energy, ModernIndustrializationEnergy::conversion,
                () -> POLICY.input(Scope.BLOCK), () -> POLICY.output(Scope.BLOCK));
        return mi(power);
    }
    private static MIEnergyStorage mi(dev.technici4n.grandpower.api.ILongEnergyStorage power) {
        return new MIEnergyStorage() {
            public boolean canConnect(CableTier tier) { return power.canReceive() || power.canExtract(); }
            public long receive(long n,boolean simulate) { return power.receive(n,simulate); }
            public long extract(long n,boolean simulate) { return power.extract(n,simulate); }
            public long getAmount() { return power.getAmount(); } public long getCapacity() { return power.getCapacity(); }
            public boolean canReceive() { return power.canReceive(); } public boolean canExtract() { return power.canExtract(); }
        };
    }
    public static MIEnergyStorage expose(IEnergyStorage energy) {
        if (energy == null) return null;
        var power = GrandPowerEnergy.expose(energy, ModernIndustrializationEnergy::conversion,
                () -> POLICY.input(Scope.BLOCK), () -> POLICY.output(Scope.BLOCK));
        return mi(power);
    }
    private ModernIndustrializationEnergy() {}
}
