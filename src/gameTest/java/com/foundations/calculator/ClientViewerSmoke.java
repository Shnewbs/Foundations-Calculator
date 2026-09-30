package com.foundations.calculator;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.compat.viewer.*;
/** Loaded only when an optional viewer is present. */
final class ClientViewerSmoke {
    static void emi(){var manager=dev.emi.emi.api.EmiApi.getRecipeManager();var category=manager.getCategories().stream().filter(c->c.getId().equals(Content.id("processing_chamber"))).findFirst().orElseThrow();var recipes=manager.getRecipes(category);if(recipes.isEmpty())throw new IllegalStateException("No native EMI processing recipes");dev.emi.emi.api.EmiApi.displayRecipe(recipes.getFirst());com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_EMI_PASSED: {} processing recipes",recipes.size());}
    static void jei(){var runtime=JeiIntegration.runtime;if(runtime==null)throw new IllegalStateException("JEI runtime missing");var type=new mezz.jei.api.recipe.RecipeType<>(Content.id("processing_chamber"),RecipeView.class);long count=runtime.getRecipeManager().createRecipeLookup(type).get().count();if(count==0)throw new IllegalStateException("No native JEI processing recipes");runtime.getRecipesGui().showTypes(java.util.List.of(type));com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_JEI_PASSED: {} processing recipes",count);}
}
