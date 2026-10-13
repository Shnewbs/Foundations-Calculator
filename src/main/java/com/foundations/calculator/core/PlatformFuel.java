package com.foundations.calculator.core;

import com.foundations.calculator.content.MachineBlockEntity;
import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CookingFuel;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ints.ResolvableInt;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.loot.NeoForgeLootContextParams;

/** Evaluate native datapack fuel providers with the machine's actual processing context. */
public final class PlatformFuel {
    private PlatformFuel() {}
    public static boolean isFuel(ItemStack stack) { return stack.has(DataComponents.COOKING_FUEL); }
    public static int burnTime(MachineBlockEntity machine, ItemStack stack) {
        if (!(machine.getLevel() instanceof ServerLevel level) || stack.isEmpty()) return 0;
        // Fuel providers inspect a consistent snapshot of the real machine inventory.
        var inventory = new SimpleContainer(IntStream.range(0, machine.inventory.getSlots())
            .mapToObj(slot -> machine.inventory.getStackInSlot(slot).copy()).toArray(ItemStack[]::new));
        var params = new LootParams.Builder(level)
            .withParameter(LootContextParams.BLOCK_STATE, machine.getBlockState())
            .withParameter(LootContextParams.BLOCK_ENTITY, machine)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(machine.getBlockPos()))
            .withParameter(LootContextParams.CONTAINER, inventory)
            .withOptionalParameter(NeoForgeLootContextParams.QUERIED_STACK, stack)
            .create(LootContextParamSets.CONTAINER_PROCESS);
        var context = new LootContext.Builder(params).create(Optional.empty());
        return ResolvableInt.getFromItem(stack, DataComponents.COOKING_FUEL, CookingFuel::burnTime, context, 0);
    }
}
