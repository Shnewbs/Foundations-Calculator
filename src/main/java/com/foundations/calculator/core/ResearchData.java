package com.foundations.calculator.core;

import java.util.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;

/** Independent of players being online and preserved through respawns and dimension changes. */
public final class ResearchData extends SavedData {
    public static final List<String> FAMILIES=List.of("calculator","scientific","atomic","flawless");
    public static volatile Map<String,Long> clientMastery=Map.of();
    public static volatile Set<String> clientGroups=Set.of();
    private long unlockRevision;
    public static long revision(Level level) {
        return level.isClientSide ? clientGroups.hashCode() : get(level.getServer()).unlockRevision;
    }
    private final Map<UUID,Map<String,Long>> mastery=new HashMap<>();
    public Map<String,Long> mastery(UUID player){return player==null?Map.of():Collections.unmodifiableMap(mastery.getOrDefault(owner(player),Map.of()));}
    public static int target(String family){return CalculatorConfig.integer("research.mastery."+family,switch(family){case "calculator"->10000;case "scientific"->5000;case "atomic"->2500;default->1000;});}
    public static void completed(Level level,UUID player,String family){
        if(level.isClientSide||player==null||!FAMILIES.contains(family)||!CalculatorConfig.flag("research.trackMastery",true))return;
        var data=get(level.getServer());var counts=data.mastery.computeIfAbsent(owner(player),id->new HashMap<>());long old=counts.getOrDefault(family,0L);counts.put(family,old==Long.MAX_VALUE?old:old+1);data.setDirty();
        if(level.getGameTime()%20==0||old+1==target(family))syncAll(level.getServer());
    }
    public static void sync(net.minecraft.server.level.ServerPlayer player){net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player,new com.foundations.calculator.network.ResearchSyncPayload(get(player.server).groups(player.getUUID()).stream().sorted().limit(4096).toList(),get(player.server).mastery(player.getUUID())));}
    public static void syncAll(MinecraftServer server){server.getPlayerList().getPlayers().forEach(ResearchData::sync);}
    private static final UUID SHARED=new UUID(0,0);
    private final Map<UUID,Set<String>> unlocked=new HashMap<>();
    public static ResearchData get(MinecraftServer server){return server.overworld().getDataStorage().computeIfAbsent(new SavedData.Factory<>(ResearchData::new,ResearchData::load),"foundations_calculator_research");}
    private static UUID owner(UUID player){return CalculatorConfig.flag("research.shareServerWide",false)?SHARED:player;}
    public Set<String> groups(UUID player){return player==null?Set.of():Collections.unmodifiableSet(unlocked.getOrDefault(owner(player),Set.of()));}
    public boolean unlock(UUID player,String group){if(player==null||group.isBlank()||group.length()>256)return false;boolean added=unlocked.computeIfAbsent(owner(player),x->new TreeSet<>()).add(group);if(added){unlockRevision++;setDirty();}return added;}
    public boolean revoke(UUID player,String group){var set=unlocked.get(owner(player));boolean changed=set!=null&&set.remove(group);if(changed){unlockRevision++;setDirty();}return changed;}
    public static boolean allowed(Level level,UUID player,ProcessRecipe recipe){
        if(!recipe.research()||CalculatorConfig.flag("enableLegacyResearchRecipes",false)||!CalculatorConfig.flag("research.requireUnlock",true))return true;
        if(level.isClientSide)return clientGroups.contains(recipe.researchGroup().isBlank()?"general":recipe.researchGroup());
        return !level.isClientSide&&level.getServer()!=null&&get(level.getServer()).groups(player).contains(recipe.researchGroup().isBlank()?"general":recipe.researchGroup());
    }
    public CompoundTag save(CompoundTag tag,HolderLookup.Provider lookup){
        ListTag players=new ListTag();unlocked.forEach((id,groups)->{CompoundTag p=new CompoundTag();p.putUUID("Player",id);ListTag list=new ListTag();groups.stream().sorted().forEach(s->list.add(StringTag.valueOf(s)));p.put("Groups",list);players.add(p);});tag.put("Players",players);ListTag counts=new ListTag();mastery.forEach((id,values)->{CompoundTag row=new CompoundTag();row.putUUID("Player",id);values.forEach(row::putLong);counts.add(row);});tag.put("Mastery",counts);return tag;
    }
    public static ResearchData load(CompoundTag tag,HolderLookup.Provider lookup){ResearchData data=new ResearchData();for(Tag row:tag.getList("Players",Tag.TAG_COMPOUND)){CompoundTag p=(CompoundTag)row;if(!p.hasUUID("Player"))continue;Set<String> groups=new TreeSet<>();for(Tag g:p.getList("Groups",Tag.TAG_STRING))groups.add(g.getAsString());data.unlocked.put(p.getUUID("Player"),groups);}for(Tag entry:tag.getList("Mastery",Tag.TAG_COMPOUND)){var row=(CompoundTag)entry;if(row.hasUUID("Player")){Map<String,Long> counts=new HashMap<>();FAMILIES.forEach(family->counts.put(family,Math.max(0,row.getLong(family))));data.mastery.put(row.getUUID("Player"),counts);}}return data;}
}
