package com.foundations.calculator.menu;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;

public final class PortableCraftingMenu extends CraftingMenu {
    private final Inventory inventory;
    private final InteractionHand hand;
    private final ItemStack held;
    private final int heldSlot;
    public PortableCraftingMenu(int id, Inventory inventory, InteractionHand hand) {
        super(id, inventory, ContainerLevelAccess.create(inventory.player.level(), inventory.player.blockPosition()));
        this.inventory = inventory;
        this.hand = hand;
        held = inventory.player.getItemInHand(hand);
        heldSlot = hand == InteractionHand.MAIN_HAND ? inventory.selected : 40;
        HeldItemLock.install(this, inventory, heldSlot);
    }
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand) == held && (hand != InteractionHand.MAIN_HAND || inventory.selected == heldSlot);
    }
    public void clicked(int slot, int button, ClickType type, Player player) {
        if (!stillValid(player) || type == ClickType.SWAP && button == heldSlot) return;
        super.clicked(slot, button, type, player);
    }
    public ItemStack quickMoveStack(Player player, int slot) {
        if (!stillValid(player) || slot < 0 || slot >= slots.size() || !slots.get(slot).mayPickup(player)) return ItemStack.EMPTY;
        return super.quickMoveStack(player, slot);
    }
}
