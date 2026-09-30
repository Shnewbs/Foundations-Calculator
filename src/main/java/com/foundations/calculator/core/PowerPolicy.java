package com.foundations.calculator.core;

import java.util.List;
import java.util.function.BooleanSupplier;
import net.neoforged.neoforge.energy.IEnergyStorage;

/** Live server policies. Only rebuilt conversion plans allocate exact-arithmetic objects. */
public final class PowerPolicy {
    public enum Scope { BLOCK, ITEM }
    public static final PowerPolicy FE = new PowerPolicy("fe", "FE", "", null);
    public static final PowerPolicy GREGTECH = new PowerPolicy("gtceu", "GregTech EU", "gtceu", "compat.gtceu.enabled");
    public static final PowerPolicy MEKANISM = new PowerPolicy("mekanism", "Mekanism J", "mekanism", "compat.mekanism");
    public static final PowerPolicy MI = new PowerPolicy("modernIndustrialization", "MI EU", "modern_industrialization", "compat.modernIndustrialization");
    public static final PowerPolicy AE2 = new PowerPolicy("ae2", "AE2 items", "ae2", "compat.ae2");
    public static final PowerPolicy GRANDPOWER = new PowerPolicy("grandPower", "GrandPower FE", "grandpower", "compat.grandPower");
    public static final List<PowerPolicy> ALL = List.of(FE, GREGTECH, MEKANISM, MI, AE2, GRANDPOWER);
    public final String id, label, modId;
    private final String legacy, prefix;
    private volatile Cached cached;
    private record Cached(double rate, boolean nativePerFE, int inLoss, int outLoss, EnergyConversion conversion) {}

    private PowerPolicy(String id, String label, String modId, String legacy) {
        this.id = id; this.label = label; this.modId = modId; this.legacy = legacy; prefix = "power." + id + ".";
    }
    public boolean enabled() { return legacy == null || CalculatorConfig.flag(legacy, true); }
    public boolean enabled(Scope scope) {
        return !(this == AE2 && scope == Scope.BLOCK) && enabled() && CalculatorConfig.flag(prefix + (scope == Scope.BLOCK ? "blockPorts" : "itemCharging"), true);
    }
    /** Directions are always relative to the Foundations FE store, even when wrapping an external item. */
    public boolean input(Scope scope) {
        return enabled(scope) && CalculatorConfig.flag(prefix + "input", true)
                && (this != GREGTECH || CalculatorConfig.flag("compat.gtceu.euToFE", true));
    }
    public boolean output(Scope scope) {
        return enabled(scope) && CalculatorConfig.flag(prefix + "output", true)
                && (this != GREGTECH || CalculatorConfig.flag("compat.gtceu.feToEU", true));
    }
    public int inputLoss() { return CalculatorConfig.integer(prefix + "inputLossPercent", 0); }
    public int outputLoss() { return CalculatorConfig.integer(prefix + "outputLossPercent", 0); }
    public EnergyConversion conversion(double effectiveRate, boolean nativePerFE) {
        int in = inputLoss(), out = outputLoss();
        Cached previous = cached;
        if (previous != null && Double.doubleToLongBits(previous.rate) == Double.doubleToLongBits(effectiveRate)
                && previous.nativePerFE == nativePerFE && previous.inLoss == in && previous.outLoss == out)
            return previous.conversion;
        EnergyConversion plan;
        try {
            plan = EnergyConversion.of(nativePerFE ? EnergyRatio.nativePerFE(effectiveRate)
                    : EnergyRatio.fePerNative(effectiveRate), in, out);
        } catch (IllegalArgumentException | ArithmeticException invalid) {
            // An unsafe override closes the port; it never silently switches to an unrelated ratio.
            plan = null;
        }
        cached = new Cached(effectiveRate, nativePerFE, in, out, plan);
        return plan;
    }
    /** Guard a direct FE capability without changing the raw storage used by native adapters. */
    public static IEnergyStorage guardFE(IEnergyStorage storage, Scope scope) {
        return gate(storage, () -> FE.input(scope), () -> FE.output(scope));
    }
    public static IEnergyStorage gate(IEnergyStorage storage, BooleanSupplier canReceive, BooleanSupplier canExtract) {
        if (storage == null) return null;
        return new IEnergyStorage() {
            public int receiveEnergy(int n, boolean simulate) { return n > 0 && canReceive() ? storage.receiveEnergy(n, simulate) : 0; }
            public int extractEnergy(int n, boolean simulate) { return n > 0 && canExtract() ? storage.extractEnergy(n, simulate) : 0; }
            public int getEnergyStored() { return storage.getEnergyStored(); }
            public int getMaxEnergyStored() { return storage.getMaxEnergyStored(); }
            public boolean canReceive() { return canReceive.getAsBoolean() && storage.canReceive(); }
            public boolean canExtract() { return canExtract.getAsBoolean() && storage.canExtract(); }
        };
    }
}
