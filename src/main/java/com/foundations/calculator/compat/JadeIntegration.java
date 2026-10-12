package com.foundations.calculator.compat;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public final class JadeIntegration implements IWailaPlugin {
    public void register(IWailaCommonRegistration registration){registration.registerBlockDataProvider(Provider.INSTANCE,MachineBlockEntity.class);}
    public void registerClient(IWailaClientRegistration registration){registration.registerBlockComponent(Provider.INSTANCE,MachineBlock.class);}
    public enum Provider implements IServerDataProvider<BlockAccessor>,IBlockComponentProvider {
        INSTANCE;
        public Identifier getUid(){return Content.id("machine_status");}
        public void appendServerData(CompoundTag data,BlockAccessor accessor){
            if(!CalculatorConfig.flag("compat.jade",true)||!(accessor.getBlockEntity() instanceof MachineBlockEntity m))return;
            var snapshot=MachineDiagnostics.machine(m);var n=new CompoundTag();n.putString("Status",snapshot.status().label);n.putInt("Required",snapshot.requiredEnergy());n.putInt("Progress",m.progress);n.putInt("Ticks",m.totalTicks);n.putInt("Points",m.nutrient);n.putString("Research",m.program.getStringOr("LastResearch",""));
            if(m.kind().endsWith("greenhouse"))n.putInt("Carbon",m.program.getIntOr("Carbon",0));
            if(m.bulkInventory()){long count=0;for(int i=0;i<m.bulk.getSlots();i++)count+=m.bulk.count(i);n.putLong("Stored",count);}
            data.put("Foundations",n);
        }
        public void appendTooltip(ITooltip tooltip,BlockAccessor accessor,IPluginConfig config){
            if(!CalculatorConfig.flag("compat.jade",true)||!accessor.getServerData().contains("Foundations"))return;var n=accessor.getServerData().getCompoundOrEmpty("Foundations");
            tooltip.add(Component.literal(n.getStringOr("Status","")));
            if(n.getIntOr("Required",0)>0)tooltip.add(Component.literal(String.format("Required: %,d FE",n.getIntOr("Required",0))));
            if(n.getIntOr("Progress",0)>0)tooltip.add(Component.literal("Progress: "+(int)Math.clamp(100.0*n.getIntOr("Progress",0)/Math.max(1,n.getIntOr("Ticks",0)),0,100)+"%"));
            if(n.getIntOr("Points",0)>0)tooltip.add(Component.literal("Points: "+n.getIntOr("Points",0)));
            if(n.contains("Carbon"))tooltip.add(Component.literal("CO₂: "+n.getIntOr("Carbon",0)/1000+"%"));
            if(n.contains("Stored"))tooltip.add(Component.literal("Stored items: "+n.getLongOr("Stored",0L)));
            if(!n.getStringOr("Research","").isEmpty())tooltip.add(Component.literal("Researched: "+n.getStringOr("Research","").replace('_',' ')));
        }
    }
}
