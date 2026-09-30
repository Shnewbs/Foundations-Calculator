package com.foundations.calculator.compat;

import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.action.recipe.ActionAddRecipe;
import com.blamejared.crafttweaker.api.data.MapData;
import com.blamejared.crafttweaker.api.ingredient.IIngredientWithAmount;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import net.minecraft.world.item.crafting.*;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(modDeps="foundations_calculator")
@ZenCodeType.Name("mods.foundations.Calculator")
public final class CraftTweakerIntegration {
    private enum Manager implements IRecipeManager<ProcessRecipe> {INSTANCE;public RecipeType<ProcessRecipe> getRecipeType(){return Content.PROCESS_TYPE.get();}}
    private static void enabled(){if(!CalculatorConfig.flag("compat.crafttweaker",true))throw new IllegalStateException("Foundations CraftTweaker integration is disabled in the server config.");}
    @ZenCodeType.Method public static void add(String name,String machine,IIngredientWithAmount[] inputs,IItemStack[] outputs,int energy,int ticks){
        enabled();if(inputs.length<1||inputs.length>14||outputs.length>6||energy<0||ticks<1||ticks>1000000)throw new IllegalArgumentException("Expected 1–14 inputs, 0–6 outputs, nonnegative FE, and 1–1000000 ticks.");
        var ingredients=Arrays.stream(inputs).map(i->{if(i.amount()<1||i.amount()>64)throw new IllegalArgumentException("Ingredient counts must be 1–64");return new CountedIngredient(i.ingredient().asVanillaIngredient(),i.amount(),0);}).toList();
        var results=Arrays.stream(outputs).map(i->new ProcessRecipe.Result(i.getInternal().copy(),1,"")).toList();
        var recipe=new ProcessRecipe(machine,ingredients,results,energy,ticks,0,false);
        CraftTweakerAPI.apply(new ActionAddRecipe<>(Manager.INSTANCE,new RecipeHolder<>(Manager.INSTANCE.fixRecipeId(name),recipe)));
    }
    /** All counted ingredients, random results, probabilities, values and research fields are supported by the recipe codec. */
    @ZenCodeType.Method public static void addJson(String name,MapData recipe){enabled();Manager.INSTANCE.addJsonRecipe(name,recipe);}
    @ZenCodeType.Method public static void remove(String id){enabled();Manager.INSTANCE.removeByName(id);}
    @ZenCodeType.Method public static void removeMachine(String machine){enabled();Manager.INSTANCE.removeMatching(r->r.value().machine().equals(machine));}
    private CraftTweakerIntegration(){}
}
