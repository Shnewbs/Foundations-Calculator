package com.foundations.calculator.content;

import com.foundations.calculator.core.*;
import java.util.List;
import net.minecraft.network.chat.Component;

public final class ResearchProgram {
    public static void tick(MachineBlockEntity m){
        if(!m.program.hasUUID("Owner")){m.progress=0;return;}
        var data=ResearchData.get(m.getLevel().getServer());var owner=m.program.getUUID("Owner");
        var input=new ProcessInput(List.of(m.inventory.getStackInSlot(0)));
        var recipe=RecipeIndex.forMachine(m.getLevel(),"research").stream().filter(r->r.value().matches(input,m.getLevel())&&!data.groups(owner).contains(r.value().researchGroup())).findFirst();
        if(recipe.isEmpty()){m.progress=0;return;}
        var r=recipe.get();String id=r.id().toString();
        if(!id.equals(m.program.getStringOr("ResearchRecipe",""))){m.progress=0;m.program.putString("ResearchRecipe",id);}
        m.totalTicks=CalculatorConfig.ticks(m.kind(),r.value().ticks());
        int cost=CalculatorConfig.flag("research.consumeEnergy",true)?CalculatorConfig.energy(m.kind(),RecipePolicies.energy(r,0)):0;
        if(m.energy.getEnergyStored()<cost){if(!CalculatorConfig.machineFlag(m.kind(),"retainProgressWithoutPower"))m.progress=0;return;}
        if(++m.progress>=m.totalTicks){
            if(data.unlock(owner,r.value().researchGroup())){
                m.energy.extractEnergy(cost,false);
                if(CalculatorConfig.flag("research.consumeSample",false))m.pending.addAll(ProcessTransactions.consume(m.inventory,r.value().allocation(input),r.value(),m.getLevel()));
                ResearchData.syncAll(m.getLevel().getServer());
                var player=m.getLevel().getServer().getPlayerList().getPlayer(owner);
                if(player!=null)player.displayClientMessage(Component.literal("Research unlocked: "+r.value().researchGroup().replace('_',' ')),false);
                m.program.putString("LastResearch",r.value().researchGroup());
            }m.progress=0;
        }m.setChanged();
    }
    private ResearchProgram(){}
}
