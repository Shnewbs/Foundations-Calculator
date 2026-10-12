package com.foundations.calculator.api;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import com.foundations.calculator.content.CalculatorItem;
import com.foundations.calculator.content.MachineBlockEntity;
import com.foundations.calculator.core.CalculatorConfig;
import com.foundations.calculator.core.PowerPolicy;
import com.foundations.calculator.core.PowerPolicy.Scope;
import net.minecraft.core.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import com.foundations.calculator.api.EnergyPort;

/** Explicit routing. A selected native port refusing energy does NOT fall through to FE. */
public final class FoundationsEnergy {
    @FunctionalInterface public interface BlockAdapter { EnergyPort find(Level level,BlockPos pos,Direction face); }
    @FunctionalInterface public interface BlockSender { int send(MachineBlockEntity source,Direction face,int maximum); }
    @FunctionalInterface public interface LongBlockAdapter { LongEnergyStorage find(Level level,BlockPos pos,Direction face); }
    public record Route(String id, EnergyPort storage) {}
    public record LongRoute(String id, LongEnergyStorage storage) {}
    private record ItemAdapter(String id,Function<ItemStack,EnergyPort> factory) {}
    private record NamedBlockAdapter(String id,BlockAdapter factory) {}
    private record LongItemAdapter(String id,Function<ItemStack,LongEnergyStorage> factory) {}
    private record NamedLongBlockAdapter(String id,LongBlockAdapter factory) {}
    private static final List<BlockSender> SENDERS = new CopyOnWriteArrayList<>();
    private static final List<ItemAdapter> ITEMS = new CopyOnWriteArrayList<>();
    private static final List<NamedBlockAdapter> BLOCKS = new CopyOnWriteArrayList<>();
    private static final List<LongItemAdapter> LONG_ITEMS = new CopyOnWriteArrayList<>();
    private static final List<NamedLongBlockAdapter> LONG_BLOCKS = new CopyOnWriteArrayList<>();
    public static void registerSender(BlockSender sender) { SENDERS.add(Objects.requireNonNull(sender)); }
    public static void registerItem(Function<ItemStack,EnergyPort> adapter) { registerItem("external",adapter); }
    public static void registerBlock(BlockAdapter adapter) { registerBlock("external",adapter); }
    public static void registerItem(String id,Function<ItemStack,EnergyPort> adapter) {
        ITEMS.add(new ItemAdapter(Objects.requireNonNull(id),Objects.requireNonNull(adapter)));
    }
    public static void registerBlock(String id,BlockAdapter adapter) {
        BLOCKS.add(new NamedBlockAdapter(Objects.requireNonNull(id),Objects.requireNonNull(adapter)));
    }
    public static void registerLongItem(String id,Function<ItemStack,LongEnergyStorage> adapter) {
        LONG_ITEMS.add(new LongItemAdapter(Objects.requireNonNull(id),Objects.requireNonNull(adapter)));
    }
    public static void registerLongBlock(String id,LongBlockAdapter adapter) {
        LONG_BLOCKS.add(new NamedLongBlockAdapter(Objects.requireNonNull(id),Objects.requireNonNull(adapter)));
    }
    /** GT cables retain their voltage-aware path, independently of generic FE/native preference. */
    public static int sendNative(MachineBlockEntity source,Direction face,int maximum) {
        for (var sender:SENDERS) { int n=sender.send(source,face,maximum); if(n>=0)return Math.clamp(n,0,Math.max(0,maximum)); }
        return -1;
    }
    private static EnergyPort externalFE(EnergyPort fe,Scope scope) {
        return PowerPolicy.gate(fe,()->PowerPolicy.FE.output(scope),()->PowerPolicy.FE.input(scope));
    }
    private static EnergyPort selected(String[] label,String id,EnergyPort storage) {
        if(label!=null)label[0]=id;return storage;
    }
    private static EnergyPort resolveItem(ItemStack stack,String[] label) {
        if (stack.isEmpty()) return selected(label,"none",null);
        boolean own = stack.getItem() instanceof CalculatorItem;
        boolean nativeFirst = !own && CalculatorConfig.flag("power.routing.preferNative",true);
        if (nativeFirst) for (var adapter:ITEMS) {
            var result=adapter.factory.apply(stack); if(result!=null)return selected(label,adapter.id,result);
        }
        var fe=com.foundations.calculator.platform.EnergyFacades.bounded(net.neoforged.neoforge.transfer.access.ItemAccess.forStack(stack).getCapability(Capabilities.Energy.ITEM));
        if(fe!=null)return selected(label,"fe",externalFE(fe,Scope.ITEM));
        if (!nativeFirst && !own) for(var adapter:ITEMS) {
            var result=adapter.factory.apply(stack); if(result!=null)return selected(label,adapter.id,result);
        }
        return selected(label,"none",null);
    }
    private static EnergyPort resolveBlock(Level level,BlockPos pos,Direction face,String[] label) {
        if(!level.hasChunkAt(pos))return selected(label,"unloaded",null);
        boolean own=level.getBlockEntity(pos) instanceof MachineBlockEntity;
        boolean nativeFirst=!own&&CalculatorConfig.flag("power.routing.preferNative",true);
        if(nativeFirst)for(var adapter:BLOCKS) {
            var result=adapter.factory.find(level,pos,face); if(result!=null)return selected(label,adapter.id,result);
        }
        var fe=com.foundations.calculator.platform.EnergyFacades.bounded(level.getCapability(Capabilities.Energy.BLOCK,pos,face));
        if(fe!=null)return selected(label,"fe",externalFE(fe,Scope.BLOCK));
        if(!nativeFirst&&!own)for(var adapter:BLOCKS) {
            var result=adapter.factory.find(level,pos,face); if(result!=null)return selected(label,adapter.id,result);
        }
        return selected(label,"none",null);
    }
    public static Route itemRoute(ItemStack stack) {
        String[] label={"none"};var storage=resolveItem(stack,label);return new Route(label[0],storage);
    }
    public static Route blockRoute(Level level,BlockPos pos,Direction face) {
        String[] label={"none"};var storage=resolveBlock(level,pos,face,label);return new Route(label[0],storage);
    }
    public static EnergyPort item(ItemStack stack) { return resolveItem(stack,null); }
    public static EnergyPort block(Level level,BlockPos pos,Direction face) { return resolveBlock(level,pos,face,null); }

    private static LongEnergyStorage guardedLong(LongEnergyStorage storage,Scope scope){
        if(storage==null)return null;
        return new LongEnergyStorage(){
            public long receive(long n,boolean simulate){return canReceive()&&n>0?storage.receive(n,simulate):0;}
            public long extract(long n,boolean simulate){return canExtract()&&n>0?storage.extract(n,simulate):0;}
            public long stored(){return storage.stored();}public long capacity(){return storage.capacity();}
            public boolean canReceive(){return PowerPolicy.FE.input(scope)&&storage.canReceive();}
            public boolean canExtract(){return PowerPolicy.FE.output(scope)&&storage.canExtract();}
        };
    }
    private static LongEnergyStorage longFE(EnergyPort fe) {
        if(fe==null)return null;
        return new LongEnergyStorage(){
            public long receive(long amount,boolean simulate){return fe.receiveEnergy((int)Math.min(Integer.MAX_VALUE,Math.max(0L,amount)),simulate);}
            public long extract(long amount,boolean simulate){return fe.extractEnergy((int)Math.min(Integer.MAX_VALUE,Math.max(0L,amount)),simulate);}
            public long stored(){return Math.max(0,fe.getEnergyStored());}public long capacity(){return Math.max(0,fe.getMaxEnergyStored());}
            public boolean canReceive(){return fe.canReceive();}public boolean canExtract(){return fe.canExtract();}
        };
    }
    private static LongEnergyStorage resolveLongItem(ItemStack stack,String[] label){
        if(stack.isEmpty())return selectedLong(label,"none",null);
        if(stack.getItem() instanceof CalculatorItem item&&CalculatorConfig.flag("api.longEnergy.enabled",true))return selectedLong(label,"foundations",guardedLong(item.longStorage(stack),Scope.ITEM));
        for(var adapter:LONG_ITEMS){var result=adapter.factory.apply(stack);if(result!=null)return selectedLong(label,adapter.id,result);}
        return selectedLong(label,"fe",longFE(resolveItem(stack,null)));
    }
    private static LongEnergyStorage resolveLongBlock(Level level,BlockPos pos,Direction face,String[] label){
        if(!level.hasChunkAt(pos))return selectedLong(label,"unloaded",null);
        if(level.getBlockEntity(pos) instanceof MachineBlockEntity machine&&CalculatorConfig.flag("api.longEnergy.enabled",true))return selectedLong(label,"foundations",guardedLong(machine.longEnergyPort(face),Scope.BLOCK));
        for(var adapter:LONG_BLOCKS){var result=adapter.factory.find(level,pos,face);if(result!=null)return selectedLong(label,adapter.id,result);}
        return selectedLong(label,"fe",longFE(resolveBlock(level,pos,face,null)));
    }
    private static LongEnergyStorage selectedLong(String[] label,String id,LongEnergyStorage storage){if(label!=null)label[0]=id;return storage;}
    public static LongRoute longItemRoute(ItemStack stack){String[] label={"none"};var storage=resolveLongItem(stack,label);return new LongRoute(label[0],storage);}
    public static LongRoute longBlockRoute(Level level,BlockPos pos,Direction face){String[] label={"none"};var storage=resolveLongBlock(level,pos,face,label);return new LongRoute(label[0],storage);}
    public static LongEnergyStorage longItem(ItemStack stack){return resolveLongItem(stack,null);}
    public static LongEnergyStorage longBlock(Level level,BlockPos pos,Direction face){return resolveLongBlock(level,pos,face,null);}
    private FoundationsEnergy() {}
}
