package com.foundations.calculator.api;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;

/** Register during common setup. Called only on the logical server and only in loaded chunks. */
public interface PlantAdapter {
    boolean accepts(ItemStack seed,int greenhouseTier);
    boolean canPlant(ItemStack seed,ServerLevel level,BlockPos pos);
    /** Place exactly one plant. The greenhouse consumes one seed only when true is returned. */
    boolean plant(ItemStack seed,ServerLevel level,BlockPos pos);
    /** Opt in only if the protected methods check every affected position, including multi-block plants.
     * Use MachineWorldActions.replace/permits with the supplied machine. Never cache permission grants. */
    default boolean supportsProtectedActions() { return false; }
    default boolean plantProtected(ItemStack seed,ServerLevel level,BlockPos pos,
            com.foundations.calculator.content.MachineBlockEntity machine) { return false; }
    default List<ItemStack> harvestProtected(ServerLevel level,BlockPos pos,
            com.foundations.calculator.content.MachineBlockEntity machine) { return List.of(); }
    default boolean canGrowProtected(ServerLevel level,BlockPos pos) { return false; }
    default boolean growProtected(ServerLevel level,BlockPos pos,
            com.foundations.calculator.content.MachineBlockEntity machine) { return false; }
    boolean canHarvest(ServerLevel level,BlockPos pos);
    /** Commit one harvest and return its drops; do not spawn the same drops in the world. */
    List<ItemStack> harvest(ServerLevel level,BlockPos pos);
}
