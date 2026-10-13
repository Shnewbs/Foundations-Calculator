package com.foundations.calculator.core;

import net.minecraft.core.NonNullList;
import net.minecraft.world.ItemStackWithSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/** Native transactional storage retaining the inventory format of existing worlds. */
public class BasicInventory extends ItemStacksResourceHandler implements ItemInventory {
    public BasicInventory(int size) { super(size); }
    public int getSlots() { return size(); }
    public ItemStack getStackInSlot(int slot) { return stacks.get(slot); }
    public void setStackInSlot(int slot, ItemStack stack) {
        set(slot, ItemResource.of(stack), stack.getCount());
    }
    public int getSlotLimit(int slot) { return Item.ABSOLUTE_MAX_STACK_SIZE; }
    public boolean isItemValid(int slot, ItemStack stack) { return true; }
    @Override public boolean isValid(int slot, ItemResource resource) {
        return !resource.isEmpty() && isItemValid(slot, resource.toStack());
    }
    @Override protected int getCapacity(int slot, ItemResource resource) {
        return Math.min(getSlotLimit(slot), resource.isEmpty() ? Item.ABSOLUTE_MAX_STACK_SIZE : resource.getMaxStackSize());
    }
    @Override protected void onContentsChanged(int slot, ItemStack previous) { onContentsChanged(slot); }
    protected void onContentsChanged(int slot) {}
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) return ItemStack.EMPTY;
        try (var transaction = Transaction.open(Transaction.getCurrentOpenedTransaction())) {
            int accepted = insert(slot, ItemResource.of(stack), stack.getCount(), transaction);
            if (!simulate) transaction.commit();
            return stack.copyWithCount(stack.getCount() - accepted);
        }
    }
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack current = getStackInSlot(slot);
        if (current.isEmpty() || amount <= 0) return ItemStack.EMPTY;
        try (var transaction = Transaction.open(Transaction.getCurrentOpenedTransaction())) {
            int removed = extract(slot, ItemResource.of(current), Math.min(amount, current.getMaxStackSize()), transaction);
            if (!simulate) transaction.commit();
            return current.copyWithCount(removed);
        }
    }
    @Override public void serialize(ValueOutput output) {
        var entries = output.list("Items", ItemStackWithSlot.CODEC);
        for (int slot = 0; slot < size(); slot++) {
            ItemStack stack = stacks.get(slot);
            if (!stack.isEmpty()) entries.add(new ItemStackWithSlot(slot, stack));
        }
        output.putInt("Size", size());
    }
    @Override public void deserialize(ValueInput input) {
        int size = input.getIntOr("Size", size());
        if (size < 0 || size > 4096) throw new IllegalArgumentException("Invalid inventory size: " + size);
        setStacks(NonNullList.withSize(size, ItemStack.EMPTY));
        input.listOrEmpty("Items", ItemStackWithSlot.CODEC).forEach(entry -> {
            if (entry.isValidInContainer(size)) stacks.set(entry.slot(), entry.stack());
        });
    }
}
