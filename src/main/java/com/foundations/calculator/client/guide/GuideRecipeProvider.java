package com.foundations.calculator.client.guide;
import java.util.*;
import com.foundations.guide.api.*;
import com.foundations.guide.api.GuideData.*;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
/** Translating live native recipes is the publisher's job, not a master-reader dependency on internal classes. */
public final class GuideRecipeProvider {
 private static final GuideRecipes recipes=new GuideRecipes();private static boolean registered;
 public static void register(){if(!registered){GuideApi.registerRecipes("foundations_calculator",GuideRecipeProvider::resolve);registered=true;}}
 public static void clear(){recipes.clear();}
 private static RecipePage resolve(RecipeRequest request){
  if(Minecraft.getInstance().level==null||!CalculatorConfig.SPEC.isLoaded())return RecipePage.unavailable("Server recipe/configuration data unavailable; defaults are not substituted");
  var view=recipes.view(request.query(),request.page(),request.profile());var blocks=new ArrayList<Block>();
  for(var part:view.parts()){
   if(part.icon().isEmpty())blocks.add(Block.paragraph(part.text()));
   else blocks.add(new Block("item",part.text(),BuiltInRegistries.ITEM.getKey(part.icon().getItem()).toString(),List.of(),Map.of("count",Integer.toString(part.icon().getCount()))));
  }
  return new RecipePage(State.READY,"Server-synchronized recipe data",view.count(),view.page(),blocks);
 }
 private GuideRecipeProvider(){}
}
