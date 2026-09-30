package com.foundations.calculator.core;

import java.util.List;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record ProcessInput(List<ItemStack> stacks) implements RecipeInput {
    public ItemStack getItem(int slot) { return stacks.get(slot); }
    public int size() { return stacks.size(); }
}
