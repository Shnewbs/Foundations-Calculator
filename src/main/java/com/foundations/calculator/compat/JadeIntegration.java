package com.foundations.calculator.compat;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

@WailaPlugin
public final class JadeIntegration implements IWailaPlugin {
    public void register(IWailaCommonRegistration registration){registration.registerBlockDataProvider(Provider.INSTANCE,MachineBlockEntity.class);}
    public void registerClient(IWailaClientRegistration registration){registration.registerBlockComponent(Provider.INSTANCE,MachineBlock.class);}
    public enum Provider implements IServerDataProvider<BlockAccessor>,IBlockComponentProvider {
        INSTANCE;
        public ResourceLocation getUid(){return Content.id("machine_status");}
        public void appendServerData(CompoundTag data,BlockAccessor accessor){
            if(!CalculatorConfig.flag("compat.jade",true)||!(accessor.getBlockEntity() instanceof MachineBlockEntity m))return;
            var snapshot=MachineDiagnostics.machine(m);var n=new CompoundTag();n.putString("Status",snapshot.status().label);n.putInt("Required",snapshot.requiredEnergy());n.putInt("Progress",m.progress);n.putInt("Ticks",m.totalTicks);n.putInt("Points",m.nutrient);n.putString("Research",m.program.getString("LastResearch"));
            if(m.kind().endsWith("greenhouse"))n.putInt("Carbon",m.program.getInt("Carbon"));
            if(m.bulkInventory()){long count=0;for(int i=0;i<m.bulk.getSlots();i++)count+=m.bulk.count(i);n.putLong("Stored",count);}
            data.put("Foundations",n);
        }
        public void appendTooltip(ITooltip tooltip,BlockAccessor accessor,IPluginConfig config){
            if(!CalculatorConfig.flag("compat.jade",true)||!accessor.getServerData().contains("Foundations"))return;var n=accessor.getServerData().getCompound("Foundations");
            tooltip.add(Component.literal(n.getString("Status")));
            if(n.getInt("Required")>0)tooltip.add(Component.literal(String.format("Required: %,d FE",n.getInt("Required"))));
            if(n.getInt("Progress")>0)tooltip.add(Component.literal("Progress: "+(int)Math.clamp(100.0*n.getInt("Progress")/Math.max(1,n.getInt("Ticks")),0,100)+"%"));
            if(n.getInt("Points")>0)tooltip.add(Component.literal("Points: "+n.getInt("Points")));
            if(n.contains("Carbon"))tooltip.add(Component.literal("CO₂: "+n.getInt("Carbon")/1000+"%"));
            if(n.contains("Stored"))tooltip.add(Component.literal("Stored items: "+n.getLong("Stored")));
            if(!n.getString("Research").isEmpty())tooltip.add(Component.literal("Researched: "+n.getString("Research").replace('_',' ')));
        }
    }
}
