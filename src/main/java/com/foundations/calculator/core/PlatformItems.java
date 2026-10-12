package com.foundations.calculator.core;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
/** Registry-aware stacks and stack-sensitive crafting remainders. */
public final class PlatformItems {
    private PlatformItems() {}
    public static ItemStack remainder(ItemStack stack){var remainder=stack.getCraftingRemainder();return remainder==null?ItemStack.EMPTY:remainder.create();}
    public static Tag save(ItemStack stack,HolderLookup.Provider lookup){return ItemStack.CODEC.encodeStart(lookup.createSerializationContext(NbtOps.INSTANCE),stack).getOrThrow();}
    public static java.util.Optional<ItemStack> load(Tag tag,HolderLookup.Provider lookup){return tag==null?java.util.Optional.empty():ItemStack.CODEC.parse(lookup.createSerializationContext(NbtOps.INSTANCE),tag).result();}
}
