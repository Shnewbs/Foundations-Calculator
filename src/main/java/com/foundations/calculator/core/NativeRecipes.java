package com.foundations.calculator.core;
import java.util.*;
import com.foundations.calculator.content.Content;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.SlotDisplayContext;
import net.minecraft.world.level.Level;
/** Client snapshots replace the removed client RecipeManager; the server remains authoritative. */
public final class NativeRecipes {
    private static RecipeMap client=RecipeMap.EMPTY;
    private NativeRecipes() {}
    public static RecipeMap map(Level level){return level.isClientSide()?client:level.getServer().getRecipeManager().recipeMap();}
    public static void replaceClient(List<RecipeHolder<?>> recipes){client=RecipeMap.create(recipes);RecipeIndex.clear();}
    public static void clearClient(){client=RecipeMap.EMPTY;RecipeIndex.clear();}
    public static Collection<RecipeHolder<ProcessRecipe>> processes(Level level){return map(level).byType(Content.PROCESS_TYPE.get());}
    public static ItemStack result(Recipe<?> recipe,Level level){if(recipe instanceof ProcessRecipe process)return process.outputs().isEmpty()?ItemStack.EMPTY:process.outputs().getFirst().stack().copy();var displays=recipe.display();return displays.isEmpty()?ItemStack.EMPTY:displays.getFirst().result().resolveForFirstStack(SlotDisplayContext.fromLevel(level));}
}
