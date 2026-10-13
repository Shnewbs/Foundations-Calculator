package com.foundations.calculator.core;

import net.minecraft.world.item.ItemStack;

/** The mod's inventory operations, independent of a particular automation API. */
public interface ItemInventory {
    int getSlots();
    ItemStack getStackInSlot(int slot);
    ItemStack insertItem(int slot, ItemStack stack, boolean simulate);
    ItemStack extractItem(int slot, int amount, boolean simulate);
    int getSlotLimit(int slot);
    boolean isItemValid(int slot, ItemStack stack);
}
