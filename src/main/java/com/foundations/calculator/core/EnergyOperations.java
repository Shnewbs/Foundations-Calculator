package com.foundations.calculator.core;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Exact decimal configuration arithmetic, bounded only by the native long store. */
public final class EnergyOperations {
    private static final BigDecimal MAX = BigDecimal.valueOf(Long.MAX_VALUE);
    private EnergyOperations() {}

    public static long scale(long base, double... factors) {
        if (base <= 0) return 0;
        boolean unchanged = true;
        for (double factor : factors) {
            if (!Double.isFinite(factor) || factor < 0) throw new IllegalArgumentException("energy factor");
            if (factor == 0) return 0;
            unchanged &= factor == 1;
        }
        if (unchanged) return base;
        BigDecimal result = BigDecimal.valueOf(base);
        for (double factor : factors) {
            if (!Double.isFinite(factor) || factor < 0) throw new IllegalArgumentException("energy factor");
            if (factor == 0) return 0;
            result = result.multiply(BigDecimal.valueOf(factor));
        }
        return bounded(result);
    }

    public static long discounted(long base, int upgrades, double discount) {
        if (base <= 0) return 0;
        if (!Double.isFinite(discount) || discount < 0) throw new IllegalArgumentException("energy discount");
        if (upgrades <= 0 || discount == 0) return base;
        BigDecimal divisor = BigDecimal.ONE.add(BigDecimal.valueOf(discount).multiply(BigDecimal.valueOf(Math.max(0, upgrades))));
        return bounded(BigDecimal.valueOf(base).divide(divisor, 0, RoundingMode.DOWN));
    }

    /** One entry per consumer: no global key growth and no stale live settings. */
    public static final class DiscountCache {
        private boolean initialized;
        private long base,result,discountBits;
        private int upgrades;
        public long apply(long base,int upgrades,double discount){
            if(base<=0||upgrades<=0||discount==0)return discounted(base,upgrades,discount);
            long bits=Double.doubleToLongBits(discount);
            if(initialized&&this.base==base&&this.upgrades==upgrades&&discountBits==bits)return result;
            long computed=discounted(base,upgrades,discount);
            this.base=base;this.upgrades=upgrades;discountBits=bits;result=computed;initialized=true;
            return computed;
        }
    }

    private static long bounded(BigDecimal result) {
        return result.compareTo(MAX) >= 0 ? Long.MAX_VALUE : result.longValue();
    }
}
