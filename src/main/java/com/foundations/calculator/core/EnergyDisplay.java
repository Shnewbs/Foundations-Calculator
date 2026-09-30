package com.foundations.calculator.core;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/** Client-safe formatting for long-valued canonical FE. Never guesses an inherited external ratio. */
public final class EnergyDisplay {
    private static final DecimalFormat DECIMAL = new DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.ROOT));

    public record Value(double stored,double capacity,String unit) {}

    public static Value preferred(long stored,long capacity) {
        ClientConfig.EnergyUnit mode = ClientConfig.SPEC.isLoaded() ? ClientConfig.ENERGY_UNIT.get() : ClientConfig.EnergyUnit.AUTO;
        return switch(mode) {
            case GT_EU -> convert(stored,capacity,gtRate(),false,"EU");
            case MEKANISM_J -> convert(stored,capacity,CalculatorConfig.decimal("power.mekanism.joulesPerFE",0),true,"J");
            case MI_EU -> convert(stored,capacity,CalculatorConfig.decimal("power.modernIndustrialization.fePerEU",0),false,"EU");
            case AUTO,FE -> new Value(stored,capacity,"FE");
        };
    }
    private static double gtRate() {
        int explicit=CalculatorConfig.integer("compat.gtceu.fePerEU",0);
        return explicit>0?explicit:0;
    }
    /** nativePerFE=true means rate native units per FE; false means FE per native unit. */
    private static Value convert(long stored,long capacity,double rate,boolean nativePerFE,String unit) {
        if(!(rate>0) || !Double.isFinite(rate)) return new Value(stored,capacity,"FE");
        double s=nativePerFE?stored*rate:stored/rate;
        double c=nativePerFE?capacity*rate:capacity/rate;
        if(!Double.isFinite(s)||!Double.isFinite(c))return new Value(stored,capacity,"FE");
        return new Value(s,c,unit);
    }
    public static String compact(long value) {
        double n=Math.max(0,value);String[] suffix={"","k","M","G","T","P","E"};int i=0;
        while(n>=1000&&i<suffix.length-1){n/=1000;i++;}
        return (i==0?String.format(Locale.ROOT,"%,d",value):DECIMAL.format(n)+suffix[i]);
    }
    public static String formatPreferred(long stored,long capacity) {
        Value v=preferred(stored,capacity);
        if(v.unit().equals("FE"))return compact(stored)+" / "+compact(capacity)+" FE";
        return DECIMAL.format(v.stored())+" / "+DECIMAL.format(v.capacity())+" "+v.unit();
    }
    public static String formatFE(long stored,long capacity) {
        return String.format(Locale.ROOT,"%,d / %,d FE",Math.max(0,stored),Math.max(0,capacity));
    }
    private EnergyDisplay() {}
}
