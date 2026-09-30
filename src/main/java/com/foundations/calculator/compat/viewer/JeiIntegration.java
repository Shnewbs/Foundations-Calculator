package com.foundations.calculator.compat.viewer;

import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import com.foundations.calculator.network.TransferPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.network.PacketDistributor;
import mezz.jei.api.*;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.*;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.recipe.transfer.*;
import mezz.jei.api.registration.*;

@JeiPlugin
public final class JeiIntegration implements IModPlugin {
    public static mezz.jei.api.runtime.IJeiRuntime runtime;
    public void onRuntimeAvailable(mezz.jei.api.runtime.IJeiRuntime value){runtime=value;}
    public void onRuntimeUnavailable(){runtime=null;}
    private final Map<String,RecipeType<RecipeView>> types=new LinkedHashMap<>();
    public ResourceLocation getPluginUid(){return Content.id("jei");}
    public void registerCategories(IRecipeCategoryRegistration r){if(!CalculatorConfig.flag("compat.jei",true))return;types.clear();for(String family:RecipeView.families()){var type=new RecipeType<>(Content.id(family),RecipeView.class);types.put(family,type);var icon=r.getJeiHelpers().getGuiHelper().createDrawableItemStack(RecipeView.icon(family));r.addRecipeCategories(new Category(type,family,icon));}}
    public void registerRecipes(IRecipeRegistration r){if(!CalculatorConfig.flag("compat.jei",true))return;var all=RecipeView.all();types.forEach((family,type)->r.addRecipes(type,all.stream().filter(v->v.recipe().machine().equals(family)).toList()));}
    public void registerRecipeCatalysts(IRecipeCatalystRegistration r){types.forEach((family,type)->r.addRecipeCatalyst(RecipeView.icon(family),type));}
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration r){types.forEach((family,type)->r.addRecipeTransferHandler(new Transfer(type,r.getTransferHelper()),type));}
    private record Category(RecipeType<RecipeView> getRecipeType,String family,IDrawable getIcon) implements IRecipeCategory<RecipeView> {
        public Component getTitle(){return RecipeView.title(family);}public int getWidth(){return 228;}public int getHeight(){return 102;}
        public ResourceLocation getRegistryName(RecipeView recipe){return recipe.holder().id();}
        public void setRecipe(IRecipeLayoutBuilder b,RecipeView view,IFocusGroup focus){
            var recipe=view.recipe();for(int i=0;i<recipe.inputs().size();i++){var input=recipe.inputs().get(i);b.addSlot(RecipeIngredientRole.INPUT,2+i%7*18,8+i/7*18).addItemStacks(RecipeView.inputs(input)).setStandardSlotBackground().addTooltipCallback((slot,tip)->{if(input.circuitState()>0)tip.add(Component.literal(input.circuitState()==2?"Analysed and stable":"Analysed"));});}
            for(int i=0;i<recipe.outputs().size();i++){var output=recipe.outputs().get(i);b.addSlot(RecipeIngredientRole.OUTPUT,166+i%3*18,8+i/3*18).addItemStacks(RecipeView.outputs(output)).setStandardSlotBackground().addTooltipCallback((slot,tip)->{tip.add(Component.literal(String.format("Chance: %.2f%%",output.chance()*100)));if(!output.randomCircuit().isEmpty())tip.add(Component.literal("One random circuit variant"));});}
        }
        public void draw(RecipeView view,IRecipeSlotsView slots,GuiGraphics g,double x,double y){var font=Minecraft.getInstance().font;g.drawString(font,"→",140,18,0xff555555,false);g.drawString(font,view.cost(),2,60,0xff555555,false);g.drawString(font,font.plainSubstrByWidth(view.detail(),224),2,76,0xff555555,false);}
    }
    private record Transfer(RecipeType<RecipeView> getRecipeType,IRecipeTransferHandlerHelper helper) implements IRecipeTransferHandler<CalculatorMenu,RecipeView>{
        public Class<? extends CalculatorMenu> getContainerClass(){return CalculatorMenu.class;}public Optional<MenuType<CalculatorMenu>> getMenuType(){return Optional.of(Content.MENU.get());}
        public IRecipeTransferError transferRecipe(CalculatorMenu menu,RecipeView recipe,IRecipeSlotsView slots,Player player,boolean maximum,boolean commit){
            if(RecipeTransfer.plan(menu,recipe.recipe(),player,1)==null)return helper.createUserErrorWithTooltip(Component.literal("Check the machine, ingredients, and inventory space."));
            if(commit)PacketDistributor.sendToServer(new TransferPayload(menu.containerId,recipe.holder().id(),maximum?64:1));return null;
        }
    }
}
