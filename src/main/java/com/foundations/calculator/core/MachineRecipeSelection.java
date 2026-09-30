package com.foundations.calculator.core;

import java.util.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemStackHandler;

/** One bounded selector per machine. Input copies include count and components, not just item ID. */
public final class MachineRecipeSelection {
    private List<ItemStack> inputs = List.of();
    private String family;
    private UUID owner;
    private long recipes = Long.MIN_VALUE, research = Long.MIN_VALUE, config = Long.MIN_VALUE;
    private Optional<RecipeHolder<ProcessRecipe>> selected = Optional.empty();
    public Optional<RecipeHolder<ProcessRecipe>> find(Level level,String family,UUID owner,ItemStackHandler inventory,int count) {
        long r = RecipeIndex.revision(), u = ResearchData.revision(level), c = CalculatorConfig.revision();
        boolean same = Objects.equals(this.family,family) && Objects.equals(this.owner,owner)
            && recipes == r && research == u && config == c && inputs.size() == count;
        if (same) for (int slot=0;slot<count;slot++) {
            if (!ItemStack.matches(inputs.get(slot),inventory.getStackInSlot(slot))) { same=false; break; }
        }
        if (same && CalculatorConfig.flag("performance.cacheMachineRecipes",true)) return selected;
        var input = ProcessTransactions.input(inventory,count);
        selected = input.isEmpty() ? Optional.empty() : RecipeIndex.forMachine(level,family,owner).stream()
            .filter(holder -> holder.value().matches(input,level)).findFirst();
        List<ItemStack> copy = new ArrayList<>(count);
        for (int slot=0;slot<count;slot++) copy.add(inventory.getStackInSlot(slot).copy());
        inputs=List.copyOf(copy);this.family=family;this.owner=owner;recipes=r;research=u;config=c;
        return selected;
    }
    public void clear() { inputs=List.of();family=null;owner=null;selected=Optional.empty();recipes=Long.MIN_VALUE; }
}
