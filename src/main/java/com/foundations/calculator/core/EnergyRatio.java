package com.foundations.calculator.core;

import java.math.BigDecimal;
import java.math.BigInteger;

/** Exact integer transfer quanta. Unrepresentable leftovers stay in their source storage. */
public record EnergyRatio(long nativeUnits, int feUnits) {
    public EnergyRatio {
        if (nativeUnits <= 0 || feUnits <= 0) throw new IllegalArgumentException("Positive energy quanta required");
    }
    public static EnergyRatio nativePerFE(double value) { return decimal(value, false); }
    public static EnergyRatio fePerNative(double value) { return decimal(value, true); }
    private static EnergyRatio decimal(double value, boolean inverse) {
        if (!Double.isFinite(value) || value <= 0) throw new IllegalArgumentException("Invalid energy ratio");
        BigDecimal d = BigDecimal.valueOf(value).stripTrailingZeros();
        BigInteger numerator = d.unscaledValue();
        BigInteger denominator = BigInteger.TEN.pow(Math.max(0, d.scale()));
        if (d.scale() < 0) numerator = numerator.multiply(BigInteger.TEN.pow(-d.scale()));
        return inverse ? reduced(denominator, numerator) : reduced(numerator, denominator);
    }
    private static EnergyRatio reduced(BigInteger nativeUnits, BigInteger feUnits) {
        BigInteger gcd = nativeUnits.gcd(feUnits);
        return new EnergyRatio(nativeUnits.divide(gcd).longValueExact(), feUnits.divide(gcd).intValueExact());
    }
    /** Used only when a configuration changes, never for each transfer. */
    public EnergyRatio scaled(long nativeFactor, long feFactor) {
        if (nativeFactor <= 0 || feFactor <= 0) throw new IllegalArgumentException("Positive scale required");
        return reduced(BigInteger.valueOf(nativeUnits).multiply(BigInteger.valueOf(nativeFactor)),
                BigInteger.valueOf(feUnits).multiply(BigInteger.valueOf(feFactor)));
    }
    public long nativeFromFELong(long fe) {
        return Math.min(Math.max(0L, fe) / feUnits, Long.MAX_VALUE / nativeUnits) * nativeUnits;
    }
    public long feLongFromNative(long amount) {
        long quanta=Math.max(0L,amount)/nativeUnits;
        return quanta>Long.MAX_VALUE/feUnits?Long.MAX_VALUE:quanta*feUnits;
    }
    public long nativeFromFE(int fe) { return nativeFromFELong(fe); }
    public int feFromNative(long amount) { return (int)Math.min(Integer.MAX_VALUE,feLongFromNative(amount)); }
    /** Conservative debit helpers; callers bound the amount by a previously representable offer. */
    public long nativeCeilFromFELong(long fe) {
        if (fe <= 0) return 0;
        long quanta = fe / feUnits + (fe % feUnits == 0 ? 0L : 1L);
        return Math.min(quanta, Long.MAX_VALUE / nativeUnits) * nativeUnits;
    }
    public long feLongCeilFromNative(long amount) {
        if (amount <= 0) return 0;
        long quanta = amount / nativeUnits + (amount % nativeUnits == 0 ? 0L : 1L);
        return quanta>Long.MAX_VALUE/feUnits?Long.MAX_VALUE:quanta*feUnits;
    }
    public long nativeCeilFromFE(int fe) { return nativeCeilFromFELong(fe); }
    public int feCeilFromNative(long amount) { return (int)Math.min(Integer.MAX_VALUE,feLongCeilFromNative(amount)); }
    public long wholeNative(long amount) { return Math.max(0L, amount) / nativeUnits * nativeUnits; }
    public long wholeFELong(long amount) { return Math.max(0L,amount)/feUnits*feUnits; }
    public int wholeFE(int amount) { return (int)Math.min(Integer.MAX_VALUE,wholeFELong(amount)); }
}
