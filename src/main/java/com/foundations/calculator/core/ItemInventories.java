package com.foundations.calculator.core;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public final class ItemInventories {
    private ItemInventories() {}
    public static ItemInventory nativeView(ResourceHandler<ItemResource> handler) {
        return new ItemInventory() {
            public int getSlots() { return handler.size(); }
            public ItemStack getStackInSlot(int slot) { return handler.getResource(slot).toStack(handler.getAmountAsInt(slot)); }
            public int getSlotLimit(int slot) { return handler.getCapacityAsInt(slot, ItemResource.EMPTY); }
            public boolean isItemValid(int slot, ItemStack stack) { return !stack.isEmpty() && handler.isValid(slot, ItemResource.of(stack)); }
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
                if (stack.isEmpty()) return ItemStack.EMPTY;
                try (var transaction = Transaction.open(Transaction.getCurrentOpenedTransaction())) {
                    int accepted = handler.insert(slot, ItemResource.of(stack), stack.getCount(), transaction);
                    if (!simulate) transaction.commit();
                    return stack.copyWithCount(stack.getCount() - accepted);
                }
            }
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                ItemStack current = getStackInSlot(slot);
                if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
                try (var transaction = Transaction.open(Transaction.getCurrentOpenedTransaction())) {
                    int removed = handler.extract(slot, ItemResource.of(current), Math.min(amount, current.getMaxStackSize()), transaction);
                    if (!simulate) transaction.commit();
                    return current.copyWithCount(removed);
                }
            }
        };
    }
    /** Fill matching stacks first, then empty slots, preserving item components. */
    public static ItemStack insertItemStacked(ItemInventory inventory, ItemStack stack, boolean simulate) {
        ItemStack remaining = stack;
        for (int pass = 0; pass < 2; pass++) {
            for (int slot = 0; slot < inventory.getSlots() && !remaining.isEmpty(); slot++) {
                ItemStack current = inventory.getStackInSlot(slot);
                if (pass == 0 ? !current.isEmpty() && ItemStack.isSameItemSameComponents(current, remaining) : current.isEmpty())
                    remaining = inventory.insertItem(slot, remaining, simulate);
            }
        }
        return remaining;
    }
}
