package com.foundations.calculator.guide;
import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import com.foundations.calculator.network.*;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;

/** Server-owned read-only snapshots. No world traversal, NBT access, commands or reflection. */
public final class GuideSnapshots {
    private static final Map<ServerPlayer,Long> last=new WeakHashMap<>();
    private static final Map<String,List<String>> GROUPS=Map.of(
        "protection",List.of("protection.requireOwner","protection.allowLegacyPlantCallbacks"),
        "farming",List.of("greenhouse.plantEnergy","greenhouse.harvestEnergy","greenhouse.growEnergy","greenhouse.farmlandEnergy","greenhouse.waterEnergy","greenhouse.buildEnergy","greenhouse.lanternBurnMultiplier"),
        "research",List.of("research.requireUnlock","research.shareServerWide","research.trackMastery","research.consumeSample","enableLegacyResearchRecipes"));
    public static boolean permitted(String key){
        if(key.equals("power")||GROUPS.containsKey(key))return true;
        if(key.startsWith("machine/")){String id=key.substring(8);return Content.BLOCKS_BY_ID.containsKey(id)&&Content.block(id) instanceof com.foundations.calculator.content.MachineBlock;}
        if(key.startsWith("module/")){String id=key.substring(7);return Content.ITEMS_BY_ID.containsKey(id)&&CalculatorConfig.keys().stream().anyMatch(k->k.startsWith("module."+id+"."));}
        return false;
    }
    private static String label(String key){
        String leaf=key.substring(key.lastIndexOf('.')+1);
        return switch(leaf){
            case "capacity" -> "Capacity"; case "maxModules" -> "Maximum modules"; case "itemAutomation" -> "Item automation";
            case "energyInput" -> "Energy input"; case "energyOutput" -> "Energy output"; case "energyOverride" -> "Recipe energy override";
            case "energyMultiplier" -> "Recipe energy multiplier"; case "ticksOverride" -> "Process time override"; case "timeMultiplier" -> "Process time multiplier";
            case "euInputVoltage" -> "EU input voltage"; case "euOutputVoltage" -> "EU output voltage"; case "euInputAmperage" -> "EU input amperage";
            case "euOutputAmperage" -> "EU output amperage"; case "retainProgressWithoutPower" -> "Retain progress without power";
            default -> {
                String spaced=leaf.replace('_',' ').replaceAll("([a-z0-9])([A-Z])","$1 $2");
                yield spaced.isEmpty()?key:Character.toUpperCase(spaced.charAt(0))+spaced.substring(1);
            }
        };
    }
    private static String value(String key,Object value){
        String s=String.valueOf(value);
        if(s.equalsIgnoreCase("true"))return "Yes";if(s.equalsIgnoreCase("false"))return "No";
        try{
            long n=Long.parseLong(s);String formatted=String.format(java.util.Locale.ROOT,"%,d",n);String lower=key.toLowerCase(java.util.Locale.ROOT);
            if(lower.endsWith("capacity")||lower.contains("energy")&&!lower.contains("multiplier"))return formatted+" FE";
            if(lower.contains("voltage"))return formatted+" EU";if(lower.contains("amperage"))return formatted+" A";
            if(lower.contains("ticks")||lower.endsWith("holdticks"))return formatted+" ticks";return formatted;
        }catch(NumberFormatException ignored){return s;}
    }
    private static void setting(List<String> rows,String key){
        if(!CalculatorConfig.keys().contains(key))return;
        CalculatorConfig.value(key).ifPresent(v->rows.add(label(key)+": "+value(key,v.get())));
    }
    public static List<String> snapshot(ServerPlayer player,String key){
        if(!permitted(key))return List.of("This guide key is not published by Calculator.");
        if(!CalculatorConfig.SPEC.isLoaded())return List.of("Server configuration is not loaded; no defaults substituted.");
        var rows=new ArrayList<String>();rows.add("Server snapshot. Refresh after configuration changes. No machine upgrades are assumed.");
        if(key.equals("power")){if(!CalculatorConfig.flag("power.diagnostics.enabled",true))return List.of("Power diagnostics disabled by server.");rows.addAll(PowerDiagnostics.overview());}
        else if(GROUPS.containsKey(key)){
            for(String setting:GROUPS.get(key))setting(rows,setting);
            if(key.equals("research")){
                var data=ResearchData.get(player.server);rows.add("Your unlocked groups: "+data.groups(player.getUUID()).size());
                var mastery=data.mastery(player.getUUID());for(String family:ResearchData.FAMILIES)rows.add(family+": "+mastery.getOrDefault(family,0L)+" / "+ResearchData.target(family));
                rows.add("Full sample list and unlock status: Research button. Reading does not grant unlocks.");
            }
        }else if(key.startsWith("machine/")){
            String id=key.substring(8);rows.add("Machine: "+(CalculatorConfig.machineEnabled(id)?"Enabled":"Disabled"));
            rows.add("Effective port limit: "+MachineProfiles.transfer(id)+" FE/t; item charging limit: "+MachineProfiles.charging(id)+" FE/t");
            for(String suffix:List.of("capacity","itemAutomation","energyInput","energyOutput","energyOverride","energyMultiplier","ticksOverride","timeMultiplier","euInputVoltage","euOutputVoltage","euInputAmperage","euOutputAmperage","retainProgressWithoutPower"))setting(rows,"machine."+id+"."+suffix);
            setting(rows,"machines.processEnergyMultiplier");setting(rows,"machines.processTimeMultiplier");
            if(id.equals("dynamic_calculator")){setting(rows,"world.dynamicStructureRadius");setting(rows,"world.dynamicRequiresStructure");}
            if(id.contains("greenhouse"))for(String k:CalculatorConfig.keys().stream().filter(k->k.startsWith("greenhouse.")).sorted().limit(48).toList())setting(rows,k);
            if(id.equals("weather_controller"))for(String k:List.of("world.weatherEnergyPerTick","world.weatherDuration","world.weatherCooldown","world.weatherHoldTicks"))setting(rows,k);
            if(id.equals("atomic_multiplier"))setting(rows,"machines.atomicMultiplierEnergy");
        }else if(key.startsWith("module/")){
            String id=key.substring(7);rows.add("Module: "+(CalculatorConfig.enabled(id)?"Enabled":"Disabled"));
            for(String k:CalculatorConfig.keys().stream().filter(k->k.startsWith("module."+id+".")).sorted().limit(64).toList())setting(rows,k);
        }
        return bounded(rows);
    }
    private static List<String> bounded(List<String> rows){var out=new ArrayList<String>();int total=0;for(String line:rows){if(out.size()>=96)break;String s=line.substring(0,Math.min(512,line.length()));if(total+s.length()>24576)break;out.add(s);total+=s.length();}return List.copyOf(out);}
    public static void reply(ServerPlayer player,GuideQueryPayload query){
        long now=player.serverLevel().getGameTime();Long previous=last.get(player);
        if(previous!=null&&now>=previous&&now-previous<20)return; // global per-player throttle, including known keys
        last.put(player,now);
        if(!permitted(query.key()))return; // unknown keys also spend this player's request budget
        if(!CalculatorConfig.flag("guide.liveValues",true)){PacketDistributor.sendToPlayer(player,new GuideSnapshotPayload(query.token(),query.key(),false,List.of("Guide live values disabled by server.")));return;}
        try{PacketDistributor.sendToPlayer(player,new GuideSnapshotPayload(query.token(),query.key(),CalculatorConfig.SPEC.isLoaded(),snapshot(player,query.key())));}
        catch(RuntimeException ex){PacketDistributor.sendToPlayer(player,new GuideSnapshotPayload(query.token(),query.key(),false,List.of("Snapshot unavailable; static documentation is still usable.")));com.mojang.logging.LogUtils.getLogger().warn("Guide snapshot failed for {}",query.key(),ex);}
    }
    private GuideSnapshots(){}
}
