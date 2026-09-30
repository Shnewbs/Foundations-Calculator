package com.foundations.calculator.core;
/** TEST CONFIG ONLY. Does not load NeoForge or claim to test synchronization. */
public final class CalculatorConfig {
    public static final java.util.Map<String,Object> TEST_VALUES=new java.util.HashMap<>();
    public static boolean flag(String key,boolean fallback){return (Boolean)TEST_VALUES.getOrDefault(key,fallback);}
    public static int integer(String key,int fallback){return ((Number)TEST_VALUES.getOrDefault(key,fallback)).intValue();}
    public static double decimal(String key,double fallback){return ((Number)TEST_VALUES.getOrDefault(key,fallback)).doubleValue();}
}
