package com.foundations.calculator.core;

import com.google.gson.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import net.neoforged.neoforge.common.ModConfigSpec;

/** Server-authoritative, synchronized settings, including every shipped recipe and content ID. */
public final class CalculatorConfig {
    public static final ModConfigSpec SPEC;
    private static final Map<String,ModConfigSpec.ConfigValue<?>> VALUES=new LinkedHashMap<>();
    private static final Map<String,Object> DEFAULTS=new LinkedHashMap<>();
    public static final ModConfigSpec.BooleanValue RESEARCH_RECIPES,LOCATOR_TIME,LOCATOR_EFFECTS,GRENADES;
    public static final ModConfigSpec.IntValue SCARECROW_INTERVAL,SCARECROW_RANGE,MULTIPLIER_ENERGY,CRANK_ENERGY,TRANSFER_RATE;
    public static final ModConfigSpec.DoubleValue ENERGY_MULTIPLIER,SPEED_MULTIPLIER;
    static {
        ModConfigSpec.Builder b=new ModConfigSpec.Builder();
        try(var input=CalculatorConfig.class.getResourceAsStream("/foundations/configuration.json")){
            if(input==null)throw new IOException("Missing configuration catalog");
            for(JsonElement element:JsonParser.parseReader(new InputStreamReader(input,StandardCharsets.UTF_8)).getAsJsonArray()){
                JsonObject o=element.getAsJsonObject();String key=o.get("key").getAsString();JsonPrimitive d=o.getAsJsonPrimitive("default");
                List<String> path=List.of(key.split("\\."));b.comment(o.get("description").getAsString());
                ModConfigSpec.ConfigValue<?> value;Object fallback;
                if(d.isBoolean()){fallback=d.getAsBoolean();value=b.define(path,(boolean)fallback);}
                else if((d.getAsString().contains(".")||d.getAsString().toLowerCase(java.util.Locale.ROOT).contains("e"))){fallback=d.getAsDouble();value=b.defineInRange(path,(double)fallback,o.get("min").getAsDouble(),o.get("max").getAsDouble());}
                else {fallback=d.getAsInt();value=b.defineInRange(path,(int)fallback,o.get("min").getAsInt(),o.get("max").getAsInt());}
                VALUES.put(key,value);DEFAULTS.put(key,fallback);
            }
        }catch(Exception e){throw new ExceptionInInitializerError(e);}
        SPEC=b.build();
        CRANK_ENERGY=(ModConfigSpec.IntValue)VALUES.get("machines.crankEnergyPerUse");
        TRANSFER_RATE=(ModConfigSpec.IntValue)VALUES.get("machines.energyTransferPerTick");
        ENERGY_MULTIPLIER=(ModConfigSpec.DoubleValue)VALUES.get("machines.processEnergyMultiplier");
        SPEED_MULTIPLIER=(ModConfigSpec.DoubleValue)VALUES.get("machines.processTimeMultiplier");
        SCARECROW_INTERVAL=(ModConfigSpec.IntValue)VALUES.get("machines.scarecrowInterval");
        SCARECROW_RANGE=(ModConfigSpec.IntValue)VALUES.get("machines.scarecrowRange");
        MULTIPLIER_ENERGY=(ModConfigSpec.IntValue)VALUES.get("machines.atomicMultiplierEnergy");
        GRENADES=(ModConfigSpec.BooleanValue)VALUES.get("machines.allowGrenades");
        LOCATOR_TIME=(ModConfigSpec.BooleanValue)VALUES.get("machines.unstableLocatorChangesTime");
        LOCATOR_EFFECTS=(ModConfigSpec.BooleanValue)VALUES.get("machines.unstableLocatorAffectsOwner");
        RESEARCH_RECIPES=(ModConfigSpec.BooleanValue)VALUES.get("enableLegacyResearchRecipes");
    }
    private static final java.util.concurrent.atomic.AtomicLong REVISION = new java.util.concurrent.atomic.AtomicLong();
    public static long revision() { return REVISION.get(); }
    /** Call after management integrations change ConfigValue entries programmatically. */
    public static void settingsChanged() { REVISION.incrementAndGet(); RecipeIndex.clear(); }
    public static void configLoaded(net.neoforged.fml.event.config.ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == SPEC) settingsChanged();
    }
    public static void configReloaded(net.neoforged.fml.event.config.ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == SPEC) settingsChanged();
    }
    public static void configUnloaded(net.neoforged.fml.event.config.ModConfigEvent.Unloading event) {
        if (event.getConfig().getSpec() == SPEC) settingsChanged();
    }
    private static Object get(String key,Object fallback){var v=VALUES.get(key);return v==null?fallback:SPEC.isLoaded()?v.get():DEFAULTS.get(key);}
    public static int integer(String key,int fallback){return ((Number)get(key,fallback)).intValue();}
    public static double decimal(String key,double fallback){return ((Number)get(key,fallback)).doubleValue();}
    public static boolean flag(String key,boolean fallback){return (Boolean)get(key,fallback);}
    public static boolean enabled(String id){return flag("content."+id+".enabled",true);}
    public static boolean machineEnabled(String id){return enabled(id)&&flag("machine."+id+".enabled",true);}
    public static int machine(String id,String property,int fallback){return integer("machine."+id+"."+property,fallback);}
    public static String presetName(){return switch(integer("balance.preset",0)){case 1->"Classic";case 2->"Balanced";case 3->"Expert";case 4->"High Power";default->"Custom/R9";};}
    private static double preset(String kind){
        if(!flag("balance.applyPreset",false))return 1.0;
        return switch(integer("balance.preset",0)){
            case 1 -> 1.0; // Classic
            case 2 -> switch(kind){case "capacity","transfer"->2.0;case "energy"->0.85;case "time"->0.90;default->1.0;};
            case 3 -> switch(kind){case "capacity","transfer"->0.75;case "energy"->1.50;case "time"->1.15;default->1.0;};
            case 4 -> switch(kind){case "capacity"->16.0;case "transfer"->8.0;case "time"->0.75;default->1.0;};
            default -> 1.0;
        };
    }
    private static long scaled(long base,double... factors){
        if(base<=0)return 0;double value=base;for(double factor:factors)value*=Math.max(0,factor);
        if(!Double.isFinite(value)||value>=Long.MAX_VALUE)return Long.MAX_VALUE;
        return Math.max(1L,(long)Math.floor(value));
    }
    public static long machineCapacity(String id,int fallback){
        long base=machine(id,"capacity",fallback);
        return scaled(base,decimal("energy.machineCapacityMultiplier",1),decimal("machine."+id+".capacityMultiplier",1),preset("capacity"));
    }
    public static long itemCapacity(String id,int fallback){
        long base=integer("module."+id+".capacity",fallback);
        return scaled(base,decimal("energy.itemCapacityMultiplier",1),decimal("module."+id+".capacityMultiplier",1),preset("capacity"));
    }
    public static int scaledTransfer(int base){
        long value=scaled(Math.max(0,base),decimal("energy.transferMultiplier",1),preset("transfer"));
        return (int)Math.min(Integer.MAX_VALUE,value);
    }
    public static boolean machineFlag(String id,String property){return flag("machine."+id+"."+property,true);}
    public static int energy(String machine,int original){
        int override=machine(machine,"energyOverride",-1);
        double cost=(override>=0?override:original)*decimal("machines.processEnergyMultiplier",1)*decimal("machine."+machine+".energyMultiplier",1)*preset("energy");
        return (int)Math.clamp(cost,0,Integer.MAX_VALUE);
    }
    public static int ticks(String machine,int original){
        int override=machine(machine,"ticksOverride",-1);
        return (int)Math.clamp((override>0?override:original)*decimal("machines.processTimeMultiplier",1)*decimal("machine."+machine+".timeMultiplier",1)*preset("time"),1,Integer.MAX_VALUE);
    }
    public static Set<String> keys(){return Collections.unmodifiableSet(VALUES.keySet());}
    /** Primarily useful to management integrations; validation still belongs to the config spec. */
    public static Optional<ModConfigSpec.ConfigValue<?>> value(String key){return Optional.ofNullable(VALUES.get(key));}
    private CalculatorConfig(){}
}
