package com.foundations.calculator.core;

/** Shared direction policy for passive ports and machine-initiated item transfers. */
public final class AutomationRules {
    public static boolean permits(boolean enabled, boolean supported, int sideMode, boolean output) {
        return enabled && supported && (sideMode == 0 || sideMode == (output ? 2 : 1));
    }
    private AutomationRules() {}
}
