package com.foundations.calculator.content;

import com.foundations.calculator.core.*;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

/** One atomic input/output/remainder transaction, shared by open menus and inventory processing. */
public final class SmeltingModule {
    public static NonNullList<ItemStack> contents(ItemStack module){var items=NonNullList.withSize(3,ItemStack.EMPTY);module.getOrDefault(DataComponents.CONTAINER,ItemContainerContents.EMPTY).copyInto(items);return items;}
    public static int ticks(){return CalculatorConfig.integer("module.smelting_module.ticks",1000);}
    public static void tick(ItemStack module,ItemStack battery,Level level){
        if(level.isClientSide||!CalculatorConfig.enabled("smelting_module")||!(battery.getItem() instanceof CalculatorItem power))return;
        var tag=CircuitData.tag(module);if(tag.contains("SmeltTick")&&tag.getLong("SmeltTick")==level.getGameTime())return;tag.putLong("SmeltTick",level.getGameTime());
        var items=contents(module);var input=items.get(0);var recipe=level.getRecipeManager().getRecipeFor(RecipeType.SMELTING,new SingleRecipeInput(input),level);
        if(recipe.isEmpty()||!RecipePolicies.enabled(recipe.get(),level)){tag.putInt("SmeltProgress",0);UtilityItems.put(module,tag);return;}
        String id=recipe.get().id().toString();if(!tag.getString("SmeltRecipe").equals(id)){tag.putString("SmeltRecipe",id);tag.putInt("SmeltProgress",0);}
        ItemStack output=recipe.get().value().assemble(new SingleRecipeInput(input),level.registryAccess()),remainder=input.getCraftingRemainingItem();
        int cost=CalculatorConfig.integer("module.smelting_module.cost",1000);
        if(!fits(items.get(2),output)||!fits(items.get(1),remainder)||power.energy(battery)<cost){UtilityItems.put(module,tag);return;}
        int progress=tag.getInt("SmeltProgress")+1;
        if(progress>=ticks()){
            items.set(2,combine(items.get(2),output));items.set(1,combine(items.get(1),remainder));input.shrink(1);
            power.storage(battery).extractEnergy(cost,false);module.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(items));progress=0;
        }
        tag.putInt("SmeltProgress",progress);UtilityItems.put(module,tag);
    }
    private static boolean fits(ItemStack into,ItemStack add){return add.isEmpty()||into.isEmpty()&&add.getCount()<=add.getMaxStackSize()||ItemStack.isSameItemSameComponents(into,add)&&(long)into.getCount()+add.getCount()<=into.getMaxStackSize();}
    private static ItemStack combine(ItemStack into,ItemStack add){return add.isEmpty()?into:into.isEmpty()?add.copy():into.copyWithCount(into.getCount()+add.getCount());}
    private SmeltingModule(){}
}
