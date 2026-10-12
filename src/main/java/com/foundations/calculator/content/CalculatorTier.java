package com.foundations.calculator.content;

import net.minecraft.tags.*;
import com.foundations.calculator.core.ContentProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
public record CalculatorTier(int getUses,float getSpeed,float getAttackDamageBonus,int getEnchantmentValue,
        TagKey<Block> getIncorrectBlocksForDrops,String repair) implements Tier {
    public Ingredient getRepairIngredient(){return Ingredient.of(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(net.minecraft.resources.Identifier.parse(repair.contains(":")?repair:"foundations_calculator:"+repair)));}
    public static CalculatorTier of(String material){CalculatorTier base=switch(material){
        case "reinforced"->new CalculatorTier(250,5,1.5f,5,BlockTags.INCORRECT_FOR_STONE_TOOL,"reinforced_stone_block");
        case "redstone"->new CalculatorTier(800,7.5f,2.5f,18,BlockTags.INCORRECT_FOR_IRON_TOOL,"redstone_ingot");
        case "enriched_gold"->new CalculatorTier(1000,8,0,20,BlockTags.INCORRECT_FOR_DIAMOND_TOOL,"enriched_gold_ingot");
        case "reinforced_iron"->new CalculatorTier(400,7,2,10,BlockTags.INCORRECT_FOR_IRON_TOOL,"reinforced_iron_ingot");
        case "weakened_diamond"->new CalculatorTier(1400,8,3,10,BlockTags.INCORRECT_FOR_DIAMOND_TOOL,"weakened_diamond");
        case "flawless_diamond"->new CalculatorTier(1800,14,5,30,BlockTags.INCORRECT_FOR_DIAMOND_TOOL,"flawless_diamond");
        case "fire_diamond"->new CalculatorTier(2600,16,7,30,BlockTags.INCORRECT_FOR_DIAMOND_TOOL,"fire_diamond");
        case "electric"->new CalculatorTier(10000,18,10,30,BlockTags.INCORRECT_FOR_NETHERITE_TOOL,"electric_diamond");
        default->new CalculatorTier(Integer.MAX_VALUE,50,16,30,BlockTags.INCORRECT_FOR_NETHERITE_TOOL,"end_diamond");
    };return new CalculatorTier(ContentProperties.materialInt(material,"durability",base.getUses()),ContentProperties.materialFloat(material,"miningSpeed",base.getSpeed()),ContentProperties.materialFloat(material,"attackDamage",base.getAttackDamageBonus()),ContentProperties.materialInt(material,"enchantability",base.getEnchantmentValue()),TagKey.create(net.minecraft.core.registries.Registries.BLOCK,net.minecraft.resources.Identifier.parse(ContentProperties.materialString(material,"incorrectBlocksForDrops",base.getIncorrectBlocksForDrops().location().toString()))),ContentProperties.materialString(material,"repairItem",base.repair()));}
}
