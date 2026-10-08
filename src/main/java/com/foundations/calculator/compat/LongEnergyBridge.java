package com.foundations.calculator.compat;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import com.foundations.calculator.core.EnergyConversion;
import com.foundations.calculator.core.EnergyRatio;
import com.foundations.calculator.api.LongEnergyStorage;
import com.foundations.calculator.api.EnergyPort;

/** Exact, overflow-safe bridges. A positive return is always in the caller's own unit. */
public final class LongEnergyBridge {
    public interface Storage extends LongEnergyStorage {}
    /** Backward-compatible API for third-party callers and existing lossless tests. */
    public static EnergyPort toFE(Storage nativeStorage, Supplier<EnergyRatio> ratio, BooleanSupplier enabled) {
        return toFE(nativeStorage, () -> lossless(ratio.get()), enabled, enabled);
    }
    public static Storage fromFE(EnergyPort fe, Supplier<EnergyRatio> ratio, BooleanSupplier enabled) {
        return fromFE(fe, () -> lossless(ratio.get()), enabled, enabled);
    }
    private static EnergyConversion lossless(EnergyRatio ratio) {
        return ratio == null ? null : EnergyConversion.of(ratio, 0, 0);
    }
    /** Wrap an external native store: receive is FE -> native (output policy), extract is native -> FE. */
    public static EnergyPort toFE(Storage nativeStorage, Supplier<EnergyConversion> conversion,
                                     BooleanSupplier input, BooleanSupplier output) {
        return new EnergyPort() {
            public int receiveEnergy(int maximum, boolean simulate) {
                if (!canReceive() || maximum <= 0) return 0;
                var plan = conversion.get(); if (plan == null) return 0;
                var ratio = plan.output(); long offered = ratio.nativeFromFE(maximum);
                if (offered == 0) return 0;
                long accepted = ratio.wholeNative(Math.clamp(nativeStorage.receive(offered, true), 0, offered));
                if (accepted == 0) return 0;
                return simulate ? ratio.feFromNative(accepted) : ratio.feCeilFromNative(Math.clamp(nativeStorage.receive(accepted, false), 0, accepted));
            }
            public int extractEnergy(int maximum, boolean simulate) {
                if (!canExtract() || maximum <= 0) return 0;
                var plan = conversion.get(); if (plan == null) return 0;
                var ratio = plan.input(); long offered = ratio.nativeFromFE(maximum);
                if (offered == 0) return 0;
                long available = ratio.wholeNative(Math.clamp(nativeStorage.extract(offered, true), 0, offered));
                if (available == 0) return 0;
                return ratio.feFromNative(simulate ? available : Math.clamp(nativeStorage.extract(available, false), 0, available));
            }
            public int getEnergyStored() { var plan = conversion.get(); return plan == null ? 0 : plan.base().feFromNative(nativeStorage.stored()); }
            public int getMaxEnergyStored() { var plan = conversion.get(); return plan == null ? 0 : plan.base().feFromNative(nativeStorage.capacity()); }
            public boolean canReceive() { return output.getAsBoolean() && conversion.get() != null && nativeStorage.canReceive(); }
            public boolean canExtract() { return input.getAsBoolean() && conversion.get() != null && nativeStorage.canExtract(); }
        };
    }
    /** Expose a Foundations FE store to a native caller: receive uses input; extract uses output. */
    public static Storage fromFE(EnergyPort fe, Supplier<EnergyConversion> conversion,
                                  BooleanSupplier input, BooleanSupplier output) {
        return new Storage() {
            public long receive(long maximum, boolean simulate) {
                if (!canReceive() || maximum <= 0) return 0;
                var plan = conversion.get(); if (plan == null) return 0;
                var ratio = plan.input(); int offered = ratio.feFromNative(maximum);
                if (offered == 0) return 0;
                int accepted = ratio.wholeFE(Math.clamp(fe.receiveEnergy(offered, true), 0, offered));
                if (accepted == 0) return 0;
                return simulate ? ratio.nativeFromFE(accepted) : ratio.nativeCeilFromFE(Math.clamp(fe.receiveEnergy(accepted, false), 0, accepted));
            }
            public long extract(long maximum, boolean simulate) {
                if (!canExtract() || maximum <= 0) return 0;
                var plan = conversion.get(); if (plan == null) return 0;
                var ratio = plan.output(); int offered = ratio.feFromNative(maximum);
                if (offered == 0) return 0;
                int available = ratio.wholeFE(Math.clamp(fe.extractEnergy(offered, true), 0, offered));
                if (available == 0) return 0;
                return ratio.nativeFromFE(simulate ? available : Math.clamp(fe.extractEnergy(available, false), 0, available));
            }
            public long stored() { var plan = conversion.get(); return plan == null ? 0 : plan.base().nativeFromFE(fe.getEnergyStored()); }
            public long capacity() { var plan = conversion.get(); return plan == null ? 0 : plan.base().nativeFromFE(fe.getMaxEnergyStored()); }
            public boolean canReceive() { return input.getAsBoolean() && conversion.get() != null && fe.canReceive(); }
            public boolean canExtract() { return output.getAsBoolean() && conversion.get() != null && fe.canExtract(); }
        };
    }

    /** Expose a long-valued Foundations FE store to native long APIs without the int FE ceiling. */
    public static Storage fromLong(LongEnergyStorage fe, Supplier<EnergyConversion> conversion,
                                   BooleanSupplier input, BooleanSupplier output) {
        return new Storage() {
            public long receive(long maximum, boolean simulate) {
                if(!canReceive()||maximum<=0)return 0;var plan=conversion.get();if(plan==null)return 0;
                var ratio=plan.input();long offered=ratio.feLongFromNative(maximum);if(offered==0)return 0;
                long accepted=ratio.wholeFELong(Math.clamp(fe.receive(offered,true),0L,offered));if(accepted==0)return 0;
                return simulate?ratio.nativeFromFELong(accepted):ratio.nativeCeilFromFELong(Math.clamp(fe.receive(accepted,false),0L,accepted));
            }
            public long extract(long maximum, boolean simulate) {
                if(!canExtract()||maximum<=0)return 0;var plan=conversion.get();if(plan==null)return 0;
                var ratio=plan.output();long offered=ratio.feLongFromNative(maximum);if(offered==0)return 0;
                long available=ratio.wholeFELong(Math.clamp(fe.extract(offered,true),0L,offered));if(available==0)return 0;
                return ratio.nativeFromFELong(simulate?available:Math.clamp(fe.extract(available,false),0L,available));
            }
            public long stored(){var plan=conversion.get();return plan==null?0:plan.base().nativeFromFELong(fe.stored());}
            public long capacity(){var plan=conversion.get();return plan==null?0:plan.base().nativeFromFELong(fe.capacity());}
            public boolean canReceive(){return input.getAsBoolean()&&conversion.get()!=null&&fe.canReceive();}
            public boolean canExtract(){return output.getAsBoolean()&&conversion.get()!=null&&fe.canExtract();}
        };
    }
    private LongEnergyBridge() {}
}
