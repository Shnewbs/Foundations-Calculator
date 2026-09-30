package com.foundations.calculator.core;

import java.util.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeHolder;

/** Plans against copies, then commits on the server. Packets never contain result or ingredient stacks. */
public final class RecipeTransfer {
    public static int[] range(CalculatorMenu menu,String family){
        if(menu.kind.equals("dynamic_module")||menu.kind.equals("dynamic_calculator"))return switch(family){case "calculator"->new int[]{0,2};case "scientific"->new int[]{2,2};case "atomic"->new int[]{4,3};default->null;};
        if(menu.kind.equals("analysing_chamber")&&family.startsWith("analysis_"))return new int[]{0,1};
        String current=menu.machine==null?MachineDiagnostics.family(menu.kind):menu.kind.equals("research_chamber")?"research":MachineDiagnostics.family(menu.machine);
        return current.equals(family)?new int[]{0,menu.inputs}:null;
    }
    public static List<ItemStack> plan(CalculatorMenu menu,ProcessRecipe recipe,Player player,int amount){
        int[] range=range(menu,recipe.machine());if(range==null||!menu.stillValid(player))return null;
        List<ItemStack> work=new ArrayList<>();for(var slot:menu.slots)work.add(slot.getItem().copy());
        for(int i=range[0];i<range[0]+range[1];i++){
            ItemStack stack=work.get(i);if(!store(menu,work,stack,25,work.size(),true))return null;work.set(i,ItemStack.EMPTY);
        }
        List<ItemStack> source=new ArrayList<>();for(int i=0;i<work.size();i++)source.add(i>=25&&menu.slots.get(i).mayPickup(player)?work.get(i):ItemStack.EMPTY);
        int[] available=source.stream().mapToInt(ItemStack::getCount).toArray();
        int[] required=recipe.inputs().stream().mapToInt(i->i.count()*Math.clamp(amount,1,64)).toArray();
        int[] used=IngredientAllocation.allocate(available,required,(i,s)->recipe.inputs().get(i).test(source.get(s)));if(used==null)return null;
        for(int i=25;i<work.size();i++)if(used[i]>0){
            ItemStack taken=work.get(i).copyWithCount(used[i]);work.get(i).shrink(used[i]);
            if(!store(menu,work,taken,range[0],range[0]+range[1],false))return null;
        }
        return work;
    }
    private static boolean store(CalculatorMenu menu,List<ItemStack> work,ItemStack stack,int start,int end,boolean checkSlots){
        ItemStack left=stack.copy();
        for(int pass=0;pass<2;pass++)for(int i=start;i<end&&!left.isEmpty();i++){
            var slot=menu.slots.get(i);if(checkSlots&&!slot.mayPlace(left))continue;
            var into=work.get(i);if(pass==0?into.isEmpty()||!ItemStack.isSameItemSameComponents(into,left):!into.isEmpty())continue;
            int n=Math.min(left.getCount(),Math.min(left.getMaxStackSize(),slot.getMaxStackSize(left))-into.getCount());if(n<=0)continue;
            work.set(i,into.isEmpty()?left.copyWithCount(n):into.copyWithCount(into.getCount()+n));left.shrink(n);
        }return left.isEmpty();
    }
    public static boolean transfer(CalculatorMenu menu,RecipeHolder<ProcessRecipe> holder,Player player,int amount){
        if(player.level().isClientSide||!ResearchData.allowed(player.level(),menu.machine==null?player.getUUID():menu.machine.owner(),holder.value())||!RecipePolicies.enabled(holder,player.level())||!CalculatorConfig.machineEnabled(menu.kind))return false;
        List<ItemStack> plan=null;for(int n=Math.clamp(amount,1,64);n>0&&plan==null;n--)plan=plan(menu,holder.value(),player,n);if(plan==null)return false;
        int[] range=range(menu,holder.value().machine());
        for(int i=range[0];i<range[0]+range[1];i++)menu.slots.get(i).set(plan.get(i));
        for(int i=25;i<plan.size();i++)if(!ItemStack.matches(menu.slots.get(i).getItem(),plan.get(i)))menu.slots.get(i).set(plan.get(i));menu.broadcastChanges();return true;
    }
    private RecipeTransfer(){}
}
