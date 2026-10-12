package com.foundations.calculator.compat;

import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.core.*;
import com.foundations.calculator.core.PowerPolicy.Scope;
import appeng.api.implementations.items.IAEItemPowerStorage;
import appeng.api.config.Actionable;
import com.foundations.calculator.api.EnergyPort;

/** AE2's item API supports fractional AE. There is deliberately no native AE2 grid port. */
public final class AE2Energy {
    private static final PowerPolicy POLICY = PowerPolicy.AE2;
    public static EnergyConversion conversion() { return POLICY.conversion(CalculatorConfig.decimal("compat.aeEnergyToFE",2),false); }
    public static void register() { FoundationsEnergy.registerItem("ae2", AE2Energy::wrap); }
    private static int floor(double amount) {
        return !Double.isFinite(amount) || amount <= 0 ? 0 : (int)Math.min(Integer.MAX_VALUE,Math.floor(amount));
    }
    private static int ceil(double amount) {
        return !Double.isFinite(amount) || amount <= 0 ? 0 : (int)Math.min(Integer.MAX_VALUE,Math.ceil(amount));
    }
    public static EnergyPort wrap(net.minecraft.world.item.ItemStack stack) {
        if (!(stack.getItem() instanceof IAEItemPowerStorage item)) return null;
        return new EnergyPort() {
            private double rate(EnergyRatio ratio) { return ratio.feUnits() / (double)ratio.nativeUnits(); }
            public int receiveEnergy(int maximum,boolean simulate) {
                if (!canReceive() || maximum <= 0) return 0;
                var plan = conversion(); if (plan == null) return 0;
                double rate = rate(plan.output());
                double offered = Math.min(maximum / rate, item.getChargeRate(stack));
                if (!Double.isFinite(offered) || offered <= 0) return 0;
                double remainder = item.injectAEPower(stack,offered,Actionable.SIMULATE);
                int accepted = Math.min(maximum, floor(Math.clamp(offered-remainder,0,offered)*rate));
                if (simulate || accepted == 0) return accepted;
                double amount = accepted / rate;
                double inserted = Math.clamp(amount-item.injectAEPower(stack,amount,Actionable.MODULATE),0,amount);
                // Never return free charge when an external item commits less than it simulated.
                return Math.min(accepted,ceil(inserted*rate));
            }
            public int extractEnergy(int maximum,boolean simulate) {
                if (!canExtract() || maximum <= 0) return 0;
                var plan = conversion(); if (plan == null) return 0;
                double rate = rate(plan.input());
                double offered = maximum / rate;
                int available = Math.min(maximum,floor(Math.clamp(item.extractAEPower(stack,offered,Actionable.SIMULATE),0,offered)*rate));
                if (simulate || available == 0) return available;
                return Math.min(available,floor(Math.clamp(item.extractAEPower(stack,available/rate,Actionable.MODULATE),0,available/rate)*rate));
            }
            public int getEnergyStored() { var plan=conversion(); return plan==null?0:floor(item.getAECurrentPower(stack)*rate(plan.base())); }
            public int getMaxEnergyStored() { var plan=conversion(); return plan==null?0:floor(item.getAEMaxPower(stack)*rate(plan.base())); }
            public boolean canExtract() { return POLICY.input(Scope.ITEM) && conversion()!=null && item.getPowerFlow(stack).isAllowExtraction(); }
            public boolean canReceive() { return POLICY.output(Scope.ITEM) && conversion()!=null && item.getPowerFlow(stack).isAllowInsertion(); }
        };
    }
    private AE2Energy() {}
}
