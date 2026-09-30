package com.foundations.calculator.core;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import net.neoforged.fml.loading.FMLPaths;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.block.state.BlockBehaviour;

/** Startup-only registry properties. Invalid overrides fail loudly instead of discarding a pack's settings. */
public final class ContentProperties {
    private static final JsonObject DATA=load();
    private static JsonObject load(){
        Path path=FMLPaths.CONFIGDIR.get().resolve("foundations_calculator-content.json");
        try(var input=ContentProperties.class.getResourceAsStream("/foundations/content-properties.json")){
            JsonObject defaults=JsonParser.parseReader(new InputStreamReader(input,StandardCharsets.UTF_8)).getAsJsonObject();
            if(Files.exists(path)){
                JsonObject configured=JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                for(String group:new String[]{"blocks","items","materials"})if(configured.has(group))for(var e:configured.getAsJsonObject(group).entrySet())defaults.getAsJsonObject(group).add(e.getKey(),e.getValue());
            }else{Files.createDirectories(path.getParent());Files.writeString(path,new GsonBuilder().setPrettyPrinting().create().toJson(defaults)+"\n");}
            return defaults;
        }catch(Exception e){throw new IllegalStateException("Cannot load "+path+". Fix the JSON; existing settings have not been overwritten.",e);}
    }
    private static JsonObject entry(String group,String id){JsonObject values=DATA.getAsJsonObject(group);return values.has(id)?values.getAsJsonObject(id):new JsonObject();}
    private static double number(JsonObject o,String key,double fallback,double min,double max){if(!o.has(key))return fallback;double n=o.get(key).getAsDouble();if(!Double.isFinite(n)||n<min||n>max)throw new IllegalArgumentException("Content property "+key+" outside ["+min+", "+max+"]");return n;}
    public static BlockBehaviour.Properties block(String id,BlockBehaviour.Properties p){
        JsonObject o=entry("blocks",id);
        if(o.has("hardness"))p.destroyTime((float)number(o,"hardness",1,-1,1000000));
        if(o.has("blastResistance"))p.explosionResistance((float)number(o,"blastResistance",6,0,3600000));
        if(o.has("friction"))p.friction((float)number(o,"friction",.6,0,2));
        if(o.has("speedFactor"))p.speedFactor((float)number(o,"speedFactor",1,0,10));
        if(o.has("jumpFactor"))p.jumpFactor((float)number(o,"jumpFactor",1,0,10));
        if(o.has("lightLevel")){int light=(int)number(o,"lightLevel",0,0,15);p.lightLevel(s->light);}
        if(o.has("requiresCorrectTool")&&o.get("requiresCorrectTool").getAsBoolean())p.requiresCorrectToolForDrops();
        return p;
    }
    public static Item.Properties item(String id,Item.Properties p){
        JsonObject o=entry("items",id);
        if(o.has("stackSize"))p.stacksTo((int)number(o,"stackSize",64,1,99));
        if(o.has("durability"))p.durability((int)number(o,"durability",1,1,Integer.MAX_VALUE));
        if(o.has("nutrition")){
            var food=new FoodProperties.Builder().nutrition((int)number(o,"nutrition",0,0,1000)).saturationModifier((float)number(o,"saturation",.2,0,100));
            if(o.has("alwaysEdible")&&o.get("alwaysEdible").getAsBoolean())food.alwaysEdible();p.food(food.build());
        }
        if(o.has("fireResistant")&&o.get("fireResistant").getAsBoolean())p.fireResistant();
        return p;
    }
    public static int materialInt(String id,String field,int fallback){return (int)number(entry("materials",id),field,fallback,field.equals("enchantability")?0:1,Integer.MAX_VALUE);}
    public static float materialFloat(String id,String field,float fallback){return (float)number(entry("materials",id),field,fallback,0,10000);}
    public static String materialString(String id,String field,String fallback){var o=entry("materials",id);return o.has(field)?o.get(field).getAsString():fallback;}
    public static int burnTime(String id){return (int)number(entry("items",id),"burnTime",-1,0,Integer.MAX_VALUE);}
    public static boolean freelyHarvestable(String id){var o=entry("blocks",id);return o.has("requiresCorrectTool")&&!o.get("requiresCorrectTool").getAsBoolean();}
    public static void applyComponents(net.neoforged.neoforge.event.ModifyDefaultComponentsEvent event){
        com.foundations.calculator.content.Content.ITEMS_BY_ID.forEach((id,supplier)->{
            var o=entry("items",id);var item=supplier.get();
            if(o.has("stackSize")&&o.get("stackSize").getAsInt()>1&&(o.has("durability")||item.components().has(net.minecraft.core.component.DataComponents.MAX_DAMAGE)))throw new IllegalArgumentException(id+": durable items must have stackSize 1");
            event.modify(item,b->{
                if(o.has("durability")){b.set(net.minecraft.core.component.DataComponents.MAX_DAMAGE,(int)number(o,"durability",1,1,Integer.MAX_VALUE));b.set(net.minecraft.core.component.DataComponents.DAMAGE,0);b.set(net.minecraft.core.component.DataComponents.MAX_STACK_SIZE,1);}
                else if(o.has("stackSize"))b.set(net.minecraft.core.component.DataComponents.MAX_STACK_SIZE,(int)number(o,"stackSize",64,1,99));
                if(o.has("unbreakable")){if(o.get("unbreakable").getAsBoolean())b.set(net.minecraft.core.component.DataComponents.UNBREAKABLE,new net.minecraft.world.item.component.Unbreakable(true));else b.remove(net.minecraft.core.component.DataComponents.UNBREAKABLE);}
                if(o.has("fireResistant")){if(o.get("fireResistant").getAsBoolean())b.set(net.minecraft.core.component.DataComponents.FIRE_RESISTANT,net.minecraft.util.Unit.INSTANCE);else b.remove(net.minecraft.core.component.DataComponents.FIRE_RESISTANT);}
                if(o.has("rarity"))b.set(net.minecraft.core.component.DataComponents.RARITY,net.minecraft.world.item.Rarity.valueOf(o.get("rarity").getAsString().toUpperCase(java.util.Locale.ROOT)));
            });
        });
    }
    public static void finishRegistration(){
        var content=com.foundations.calculator.content.Content.BLOCKS_BY_ID;
        content.forEach((id,supplier)->{
            var o=entry("blocks",id);boolean wood=id.startsWith("amethyst_")||id.startsWith("tanzanite_")||id.startsWith("pear_")||id.startsWith("diamond_");
            int encouragement=0,flammability=0;
            if(wood&&id.endsWith("_leaves")){encouragement=30;flammability=60;}
            else if(wood&&id.endsWith("_log")){encouragement=5;flammability=5;}
            else if(wood&&(id.endsWith("_planks")||id.endsWith("_stairs")||id.endsWith("_fence")||id.endsWith("_gate"))){encouragement=5;flammability=20;}
            ((net.minecraft.world.level.block.FireBlock)net.minecraft.world.level.block.Blocks.FIRE).setFlammable(supplier.get(),(int)number(o,"fireSpread",encouragement,0,1000),(int)number(o,"flammability",flammability,0,1000));
        });
        var fields=java.util.Map.of("blocks",java.util.Set.of("hardness","blastResistance","friction","speedFactor","jumpFactor","lightLevel","requiresCorrectTool","fireSpread","flammability"),"items",java.util.Set.of("stackSize","durability","nutrition","saturation","alwaysEdible","fireResistant","unbreakable","rarity","burnTime"),"materials",java.util.Set.of("durability","miningSpeed","attackDamage","enchantability","incorrectBlocksForDrops","repairItem"));
        fields.forEach((group,allowed)->{for(var entry:DATA.getAsJsonObject(group).entrySet())for(String key:entry.getValue().getAsJsonObject().keySet())if(!allowed.contains(key))throw new IllegalArgumentException("Unknown content property: "+group+"."+entry.getKey()+"."+key);});
    }
    private ContentProperties(){}
}
