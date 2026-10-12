package com.foundations.calculator.core;

import java.util.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

public final class ProcessTransactions {
    public static ProcessInput input(ItemStackHandler inventory,int slots){
        List<ItemStack> result=new ArrayList<>();for(int i=0;i<slots;i++)result.add(inventory.getStackInSlot(i));return new ProcessInput(result);
    }
    /** Commit only on the server, after a fresh allocation; never accept client-provided results. */
    public static List<ItemStack> consume(ItemStackHandler inv,int[] used,ProcessRecipe recipe,Level level){
        List<ItemStack> produced=new ArrayList<>();
        for(int i=0;i<used.length;i++){
            ItemStack current=inv.getStackInSlot(i);
            ItemStack remainder=current.getCraftingRemainingItem();
            if(!remainder.isEmpty())for(int n=0;n<used[i];n++)produced.add(remainder.copy());
            inv.extractItem(i,used[i],false);
        }
        for(ProcessRecipe.Result output:recipe.outputs()){
            ItemStack stack=output.roll(level.getRandom());if(!stack.isEmpty())produced.add(stack);
        }
        return produced;
    }
    public static void flush(List<ItemStack> pending,ItemStackHandler inv){
        for(var iterator=pending.listIterator();iterator.hasNext();){
            ItemStack s=iterator.next();
            for(int i=14;i<20&&!s.isEmpty();i++)s=inv.insertItem(i,s,false);
            if(s.isEmpty())iterator.remove();else iterator.set(s);
        }
    }
    private ProcessTransactions(){}
}
