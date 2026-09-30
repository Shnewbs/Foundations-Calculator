package com.foundations.calculator.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record CountedIngredient(Ingredient ingredient, int count, int circuitState) {
    public static final Codec<CountedIngredient> CODEC = RecordCodecBuilder.create(i -> i.group(
        Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(CountedIngredient::ingredient),
        Codec.intRange(1,64).optionalFieldOf("count",1).forGetter(CountedIngredient::count),
        Codec.intRange(0,2).optionalFieldOf("circuit_state",0).forGetter(CountedIngredient::circuitState)
    ).apply(i,CountedIngredient::new));
    public boolean test(ItemStack stack) {
        return ingredient.test(stack) && (circuitState == 0 || CircuitData.analysed(stack))
            && (circuitState != 2 || CircuitData.stable(stack));
    }
}
