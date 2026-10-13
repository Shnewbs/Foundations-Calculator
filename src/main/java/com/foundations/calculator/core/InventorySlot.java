package com.foundations.calculator.core;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/** A menu slot backed by the same native inventory used by automation. */
public class InventorySlot extends Slot {
    private final BasicInventory inventory;
    private final int index;
    public InventorySlot(BasicInventory inventory, int slot, int x, int y) {
        super(new SimpleContainer(0), slot, x, y);
        this.inventory = inventory;
        this.index = slot;
    }
    @Override public ItemStack getItem() { return inventory.getStackInSlot(index); }
    @Override public void set(ItemStack stack) { inventory.setStackInSlot(index, stack); }
    @Override public void setChanged() { inventory.onContentsChanged(index); }
    @Override public ItemStack remove(int amount) { return inventory.extractItem(index, amount, false); }
    @Override public boolean mayPlace(ItemStack stack) { return inventory.isItemValid(index, stack); }
    @Override public boolean mayPickup(Player player) { return !inventory.extractItem(index, 1, true).isEmpty(); }
    @Override public int getMaxStackSize() { return inventory.getSlotLimit(index); }
    @Override public int getMaxStackSize(ItemStack stack) { return Math.min(getMaxStackSize(), stack.getMaxStackSize()); }
    @Override public boolean isSameInventory(Slot other) { return other instanceof InventorySlot slot && slot.inventory == inventory; }
}
