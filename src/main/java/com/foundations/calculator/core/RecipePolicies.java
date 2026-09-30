package com.foundations.calculator.core;

import java.util.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import com.foundations.calculator.FoundationsCalculator;
import net.minecraft.core.registries.BuiltInRegistries;

public final class RecipePolicies {
    public static boolean itemEnabled(ItemStack stack){var id=BuiltInRegistries.ITEM.getKey(stack.getItem());return !id.getNamespace().equals(FoundationsCalculator.ID)||CalculatorConfig.enabled(id.getPath());}
    public static boolean enabled(RecipeHolder<?> recipe,Level level){
        return CalculatorConfig.flag("recipe."+recipe.id()+".enabled",true)&&itemEnabled(recipe.value().getResultItem(level.registryAccess()))
            &&(!(recipe.value() instanceof ProcessRecipe p)||p.outputs().stream().allMatch(o->itemEnabled(o.stack())));
    }
    public static int energy(RecipeHolder<ProcessRecipe> r,int minimum){return CalculatorConfig.integer("recipe."+r.id()+".energyOverride",-1)>=0?r.value().energy():Math.max(minimum,r.value().energy());}
    public static RecipeHolder<ProcessRecipe> apply(RecipeHolder<ProcessRecipe> holder){
        ProcessRecipe p=holder.value();String prefix="recipe."+holder.id()+".";
        int energy=CalculatorConfig.integer(prefix+"energyOverride",-1),ticks=CalculatorConfig.integer(prefix+"ticksOverride",-1);
        double chance=CalculatorConfig.decimal(prefix+"chanceMultiplier",1);
        if(energy<0&&ticks<=0&&chance==1)return holder;
        var outputs=p.outputs().stream().map(r->new ProcessRecipe.Result(r.stack(),Math.clamp(r.chance()*chance,0,1),r.randomCircuit())).toList();
        return new RecipeHolder<>(holder.id(),new ProcessRecipe(p.machine(),p.inputs(),outputs,energy>=0?energy:p.energy(),ticks>0?ticks:p.ticks(),p.value(),p.research(),p.researchGroup()));
    }
    private RecipePolicies(){}
}
