package com.foundations.calculator.core;

/** Deterministic per-position phase; keeps exactly the configured cadence, including across restarts. */
public final class StaggeredWork {
    public static int phase(long position, int interval) {
        if (interval <= 0) throw new IllegalArgumentException("interval must be positive");
        long mixed = position;
        mixed = (mixed ^ (mixed >>> 33)) * 0xff51afd7ed558ccdl;
        mixed = (mixed ^ (mixed >>> 33)) * 0xc4ceb9fe1a85ec53l;
        mixed ^= mixed >>> 33;
        return (int) Math.floorMod(mixed, (long) interval);
    }
    public static boolean due(long tick, long position, int interval, boolean stagger) {
        if (interval <= 0) throw new IllegalArgumentException("interval must be positive");
        return Math.floorMod(tick, (long) interval) == (stagger ? phase(position, interval) : 0);
    }
    private StaggeredWork() {}
}
