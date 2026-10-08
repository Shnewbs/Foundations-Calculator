package com.foundations.calculator.compat;

import com.foundations.calculator.api.FoundationsEnergy;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.core.PowerPolicy.Scope;
import com.gregtechceu.gtceu.api.capability.*;
import com.gregtechceu.gtceu.config.ConfigHolder;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import com.foundations.calculator.api.EnergyPort;

/** GregTech CEu Modern: real voltage/amperage packets for blocks, electric-item API for batteries. */
public final class GregTechEnergy {
    private static final PowerPolicy POLICY=PowerPolicy.GREGTECH;
    private static boolean enabled(){return POLICY.enabled();}
    private static boolean receive(){return POLICY.input(Scope.BLOCK);}
    private static boolean send(){return POLICY.output(Scope.BLOCK);}
    public static int inheritedRatio(){return Math.max(1,Math.max(ConfigHolder.INSTANCE.compat.energy.feToEuRatio,ConfigHolder.INSTANCE.compat.energy.euToFeRatio));}
    public static boolean upstreamRatiosDiffer(){return ConfigHolder.INSTANCE.compat.energy.feToEuRatio!=ConfigHolder.INSTANCE.compat.energy.euToFeRatio;}
    public static int ratio(){int value=CalculatorConfig.integer("compat.gtceu.fePerEU",0);return value>0?value:inheritedRatio();}
    public static EnergyConversion conversion(){return POLICY.conversion(ratio(),false);}
    public static int setting(MachineBlockEntity m,String key,int fallback){int value=CalculatorConfig.machine(m.kind(),"eu"+Character.toUpperCase(key.charAt(0))+key.substring(1),0);return value>0?value:CalculatorConfig.integer("compat.gtceu."+key,fallback);}
    private static long time(MachineBlockEntity m){return m.getLevel()==null?0:m.getLevel().getGameTime();}
    public static void register(){
        FoundationsEnergy.registerItem("gtceu",stack->wrap(stack.getCapability(GTCapability.CAPABILITY_ELECTRIC_ITEM)));
        FoundationsEnergy.registerSender(GregTechEnergy::push);
    }
    public static void capabilities(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(GTCapability.CAPABILITY_ENERGY_CONTAINER,Content.MACHINE_ENTITY.get(),GregTechEnergy::expose);
        for(var entry:Content.ITEMS_BY_ID.values())if(entry.get() instanceof CalculatorItem item&&item.capacity>0)
            event.registerItem(GTCapability.CAPABILITY_ELECTRIC_ITEM,(stack,ctx)->electric(stack,item.longStorage(stack)),item);
    }
    public static IEnergyContainer expose(MachineBlockEntity m,Direction face){
        var port=m.longEnergyPort(face);if(port==null)return null;
        return new IEnergyContainer(){
            private boolean sameSide(Direction side){return face==null||side==null||face==side;}
            public boolean inputsEnergy(Direction side){return sameSide(side)&&receive()&&conversion()!=null&&port.canReceive();}
            public boolean outputsEnergy(Direction side){return sameSide(side)&&send()&&conversion()!=null&&port.canExtract();}
            public long acceptEnergyFromNetwork(Direction side,long voltage,long amperage){
                if(!inputsEnergy(side)||voltage<=0||amperage<=0||voltage>getInputVoltage())return 0;
                var plan=conversion();if(plan==null)return 0;int packet=plan.inputPacketFE(voltage,ratio());if(packet<=0)return 0;
                long amps=Math.min(amperage,m.euInputBudget.available(time(m),getInputAmperage()));
                amps=Math.min(amps,port.receive(Long.MAX_VALUE,true)/packet);
                if(amps==0)return 0;long accepted=port.receive(amps*packet,false);long used=accepted/packet;
                m.euInputBudget.consume(time(m),used);return used;
            }
            public long changeEnergy(long difference){
                // Capability clients cannot bypass sides or perform partial-EU mutations.
                if(difference==0||difference==Long.MIN_VALUE)return 0;
                var storage=LongEnergyBridge.fromLong(port,GregTechEnergy::conversion,GregTechEnergy::receive,GregTechEnergy::send);
                return difference>0?storage.receive(difference,false):-storage.extract(-difference,false);
            }
            public long getEnergyStored(){return Math.max(0L,port.stored())/ratio();}
            public long getEnergyCapacity(){return Math.max(0L,port.capacity())/ratio();}
            public long getInputAmperage(){return inputsEnergy(face)?setting(m,"inputAmperage",4):0;}
            public long getInputVoltage(){return inputsEnergy(face)?setting(m,"inputVoltage",Integer.MAX_VALUE):0;}
            public long getOutputAmperage(){return outputsEnergy(face)?setting(m,"outputAmperage",1):0;}
            public long getOutputVoltage(){return outputsEnergy(face)?setting(m,"outputVoltage",32):0;}
        };
    }
    /** -1 means no GregTech native target; zero means a native target refused this packet. */
    public static int push(MachineBlockEntity source,Direction face,int maximum){
        var level=source.getLevel();var targetPos=source.getBlockPos().relative(face);
        if(level==null||!level.hasChunkAt(targetPos)||level.getBlockEntity(targetPos) instanceof MachineBlockEntity)return -1;
        Direction targetFace=face.getOpposite();var target=level.getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER,targetPos,targetFace);
        if(target==null)return -1;
        if(!send()||!source.energySideOutput(face)||!target.inputsEnergy(targetFace)||maximum<=0)return 0;
        long voltage=setting(source,"outputVoltage",32);
        // Refuse over-voltage rather than burning a neighboring GT cable or machine.
        if(voltage<=0||voltage>target.getInputVoltage()||voltage>Integer.MAX_VALUE/ratio())return 0;
        var plan=conversion();if(plan==null)return 0;int packet=plan.outputPacketFE(voltage,ratio());if(packet<=0)return 0;long amps=Math.min(maximum/packet,target.getInputAmperage());
        amps=Math.min(amps,source.euOutputBudget.available(time(source),setting(source,"outputAmperage",1)));
        if(amps<=0)return 0;
        long accepted=Math.clamp(target.acceptEnergyFromNetwork(targetFace,voltage,amps),0,amps);
        source.euOutputBudget.consume(time(source),accepted);return (int)(accepted*packet);
    }
    /** Null means no native GT target. This never transfers energy or loads a chunk. */
    public static String targetStatus(MachineBlockEntity source,Direction face,int maximum){
        var level=source.getLevel();var pos=source.getBlockPos().relative(face);
        if(level==null||!level.hasChunkAt(pos)||level.getBlockEntity(pos) instanceof MachineBlockEntity)return null;
        var target=level.getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER,pos,face.getOpposite());
        if(target==null)return null;
        if(!send())return "GregTech EU: block output disabled";
        if(!source.energySideOutput(face))return "GregTech EU: source face does not output";
        if(!target.inputsEnergy(face.getOpposite()))return "GregTech EU: target input closed";
        long voltage=setting(source,"outputVoltage",32);
        if(voltage>target.getInputVoltage())return "GregTech EU: voltage exceeds receiver rating ("+voltage+" > "+target.getInputVoltage()+")";
        var plan=conversion();if(plan==null)return "GregTech EU: invalid conversion ratio";
        int cost=plan.outputPacketFE(voltage,ratio());
        if(cost<=0)return "GregTech EU: packet cannot fit integer FE range";
        if(maximum<cost)return "GregTech EU: needs "+cost+" FE for one "+voltage+" EU packet";
        if(target.getInputAmperage()<=0)return "GregTech EU: receiver amperage is zero";
        if(source.euOutputBudget.remaining(time(source),setting(source,"outputAmperage",1))<=0)
            return "GregTech EU: shared per-tick output amp budget exhausted";
        return "GregTech EU: "+voltage+" EU/packet, "+cost+" FE/packet; receiver advertises input (delivery not simulated)";
    }
    public static EnergyPort wrap(IElectricItem item){
        if(item==null)return null;
        return LongEnergyBridge.toFE(new LongEnergyBridge.Storage(){
            private int tier(){return CalculatorConfig.integer("compat.gtceu.chargerTier",1);}
            public long receive(long n,boolean simulate){return item.charge(n,tier(),false,simulate);}
            public long extract(long n,boolean simulate){return item.discharge(n,tier(),false,true,simulate);}
            public long stored(){return item.getCharge();}public long capacity(){return item.getMaxCharge();}
            public boolean canReceive(){return POLICY.output(Scope.ITEM)&&item.chargeable()&&tier()>=item.getTier();}
            public boolean canExtract(){return POLICY.input(Scope.ITEM)&&item.canProvideChargeExternally()&&tier()>=item.getTier();}
        },GregTechEnergy::conversion,()->POLICY.input(Scope.ITEM),()->POLICY.output(Scope.ITEM));
    }
    public static IElectricItem electric(ItemStack stack,com.foundations.calculator.api.LongEnergyStorage fe){
        var power=LongEnergyBridge.fromLong(fe,GregTechEnergy::conversion,()->POLICY.input(Scope.ITEM),()->POLICY.output(Scope.ITEM));
        return electricView(stack,power);
    }
    public static IElectricItem electric(ItemStack stack,EnergyPort fe){
        var power=LongEnergyBridge.fromFE(fe,GregTechEnergy::conversion,()->POLICY.input(Scope.ITEM),()->POLICY.output(Scope.ITEM));
        return electricView(stack,power);
    }
    private static IElectricItem electricView(ItemStack stack,LongEnergyBridge.Storage power){
        return new IElectricItem(){
            public int getTier(){return CalculatorConfig.integer("compat.gtceu.itemTier",1);}
            public long getTransferLimit(){int tier=getTier();long voltage=8;for(int i=0;i<tier;i++)voltage=Math.min(Integer.MAX_VALUE,voltage*4);return Math.min(voltage,Integer.MAX_VALUE/ratio());}
            public boolean chargeable(){return power.canReceive();}
            public boolean canProvideChargeExternally(){return power.canExtract();}
            public boolean isDischargeMode(){return stack.getOrDefault(DataComponents.CUSTOM_DATA,CustomData.EMPTY).copyTag().getBooleanOr("FoundationsEuDischarge",false);}
            public void setDischargeMode(boolean mode){CustomData.update(DataComponents.CUSTOM_DATA,stack,tag->tag.putBoolean("FoundationsEuDischarge",mode));}
            public long charge(long amount,int tier,boolean ignoreLimit,boolean simulate){if(!chargeable()||tier<getTier())return 0;return power.receive(ignoreLimit?amount:Math.min(amount,getTransferLimit()),simulate);}
            public long discharge(long amount,int tier,boolean ignoreLimit,boolean externally,boolean simulate){if(!POLICY.output(Scope.ITEM)||tier<getTier()||externally&&!canProvideChargeExternally())return 0;return power.extract(ignoreLimit?amount:Math.min(amount,getTransferLimit()),simulate);}
            public long getMaxCharge(){return power.capacity();}public long getCharge(){return power.stored();}
        };
    }
    private GregTechEnergy(){}
}
