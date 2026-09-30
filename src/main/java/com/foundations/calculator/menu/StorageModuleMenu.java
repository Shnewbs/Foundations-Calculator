package com.foundations.calculator.menu;

import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/** Uses vanilla chest networking while retaining the held module's identity on the server. */
public final class StorageModuleMenu extends ChestMenu {
    private final Inventory inventory;
    private final InteractionHand hand;
    private final ItemStack module,carrier;
    private final int heldSlot;

    public StorageModuleMenu(int id, Inventory inventory, InteractionHand hand) {
        this(id,inventory,hand,inventory.player.getItemInHand(hand),()->{});
    }
    public StorageModuleMenu(int id,Inventory inventory,InteractionHand hand,ItemStack module,Runnable save){
        super(MenuType.GENERIC_9x6, id, inventory, contents(module,save), 6);
        this.inventory = inventory;
        this.hand = hand;
        this.module=module;carrier = inventory.player.getItemInHand(hand);
        heldSlot = hand == InteractionHand.MAIN_HAND ? inventory.selected : 40;
        HeldItemLock.install(this, inventory, heldSlot);
        for (int i = 0; i < 54; i++) {
            Slot original = slots.get(i);
            Slot guarded = new Slot(getContainer(), i, original.x, original.y) {
                public boolean mayPlace(ItemStack stack) {
                    return getContainerSlot()<com.foundations.calculator.core.CalculatorConfig.integer("storage.moduleSlots",54)&&stack != module && stack.getItem().canFitInsideContainerItems();
                }
            };
            guarded.index = i;
            slots.set(i, guarded);
        }
    }
    private static SimpleContainer contents(ItemStack module,Runnable save) {
        NonNullList<ItemStack> items = NonNullList.withSize(54, ItemStack.EMPTY);
        module.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(items);
        SimpleContainer container = new SimpleContainer(items.toArray(ItemStack[]::new));
        container.addListener(c -> {
            NonNullList<ItemStack> saved = NonNullList.withSize(54, ItemStack.EMPTY);
            for (int i = 0; i < 54; i++) saved.set(i, c.getItem(i));
            module.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(saved));save.run();
        });
        return container;
    }
    public boolean stillValid(Player player) {
        return player.getItemInHand(hand) == carrier && (hand != InteractionHand.MAIN_HAND || inventory.selected == heldSlot);
    }
    private boolean locked(int slot) {
        return slot >= 54 && slot < slots.size() && slots.get(slot).getContainerSlot() == heldSlot;
    }
    public void clicked(int slot, int button, ClickType type, Player player) {
        if (!stillValid(player) || locked(slot) || type == ClickType.SWAP && button == heldSlot) return;
        super.clicked(slot, button, type, player);
    }
    public ItemStack quickMoveStack(Player player, int slot) {
        if (!stillValid(player) || slot < 0 || slot >= slots.size() || locked(slot)) return ItemStack.EMPTY;
        return super.quickMoveStack(player, slot);
    }
}
