package com.foundations.calculator.core;

import com.foundations.calculator.content.Content;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/** Server-owned, bounded point balances stored in the item's persistent component. */
public final class NutritionData {
    public static int capacity(ItemStack stack, String type) {
        String id = Content.path(stack);
        if (id.equals("nutrition_module")) return CalculatorConfig.integer(type.equals("health")?"nutrition.combinedHealthCapacity":"nutrition.combinedHungerCapacity",Integer.MAX_VALUE);
        return id.equals(type + "_module") ? CalculatorConfig.integer("nutrition."+type+"Capacity",1000) : 0;
    }
    public static int get(ItemStack stack, String type) {
        return Math.clamp(CircuitData.tag(stack).getIntOr(type,0), 0, capacity(stack, type));
    }
    public static int add(ItemStack stack, String type, int amount) {
        int old = get(stack, type);
        int accepted = Math.clamp(amount, 0, capacity(stack, type) - old);
        if (accepted > 0) set(stack, type, old + accepted);
        return accepted;
    }
    public static void set(ItemStack stack, String type, int points) {
        CompoundTag tag = CircuitData.tag(stack);
        tag.putInt(type, Math.clamp(points, 0, capacity(stack, type)));
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }
    public static void restore(Player player, ItemStack stack, int limit) {
        if (player.level().isClientSide() || !player.isAlive()) return;
        int hunger = Math.min(limit, Math.min(20 - player.getFoodData().getFoodLevel(), get(stack, "hunger")));
        if (hunger > 0) {
            player.getFoodData().eat(hunger,(float)CalculatorConfig.decimal("nutrition.foodSaturation",0.2));
            set(stack, "hunger", get(stack, "hunger") - hunger);
        }
        float missing = player.getMaxHealth() - player.getHealth();
        int health = Math.min(limit, Math.min((int)Math.ceil(missing), get(stack, "health")));
        if (health > 0) {
            player.heal(Math.min(health, missing));
            set(stack, "health", get(stack, "health") - health);
        }
    }
    private NutritionData() {}
}
