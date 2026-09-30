package com.foundations.calculator.menu;

import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

/** Prevents pickup-all and drag operations from bypassing a portable menu's held-slot lock. */
public final class HeldItemLock {
    public static void install(AbstractContainerMenu menu, Inventory inventory, int heldSlot) {
        for (int i = 0; i < menu.slots.size(); i++) {
            Slot old = menu.slots.get(i);
            if (old.container != inventory || old.getContainerSlot() != heldSlot) continue;
            Slot locked = new Slot(inventory, heldSlot, old.x, old.y) {
                public boolean mayPickup(Player player) { return false; }
                public boolean mayPlace(ItemStack stack) { return false; }
            };
            locked.index = i;
            menu.slots.set(i, locked);
        }
    }
    private HeldItemLock() {}
}
