package com.foundations.calculator.compat.viewer;

import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import com.foundations.calculator.network.TransferPayload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.PacketDistributor;
import dev.emi.emi.api.*;
import dev.emi.emi.api.recipe.*;
import dev.emi.emi.api.recipe.handler.*;
import dev.emi.emi.api.stack.*;
import dev.emi.emi.api.widget.WidgetHolder;

@EmiEntrypoint
public final class EmiIntegration implements EmiPlugin {
    public void register(EmiRegistry registry){
        if(!CalculatorConfig.flag("compat.emi",true))return;
        Map<String,EmiRecipeCategory> categories=new HashMap<>();
        for(String family:RecipeView.families()){
            var category=new EmiRecipeCategory(Content.id(family),EmiStack.of(RecipeView.icon(family))){public Component getName(){return RecipeView.title(family);}};
            categories.put(family,category);registry.addCategory(category);registry.addWorkstation(category,EmiStack.of(RecipeView.icon(family)));
        }
        for(var recipe:RecipeView.all())registry.addRecipe(new Display(recipe,categories.get(recipe.recipe().machine())));
        registry.addRecipeHandler(Content.MENU.get(),new Handler());
    }
    private record Display(RecipeView view,EmiRecipeCategory getCategory) implements EmiRecipe {
        public Identifier getId(){return view.holder().id();}
        public List<EmiIngredient> getInputs(){return view.recipe().inputs().stream().map(i->EmiIngredient.of(RecipeView.inputs(i).stream().map(s->EmiStack.of(s.copyWithCount(1))).toList(),i.count())).toList();}
        public List<EmiStack> getOutputs(){List<EmiStack> outputs=new ArrayList<>();for(var result:view.recipe().outputs())for(var s:RecipeView.outputs(result))outputs.add(EmiStack.of(s).setChance((float)(result.chance()/(result.randomCircuit().isEmpty()?1:14))));return outputs;}
        public int getDisplayWidth(){return 228;}public int getDisplayHeight(){return 102;}
        public boolean supportsRecipeTree(){return !view.recipe().outputs().isEmpty()&&!view.recipe().machine().startsWith("analysis_");}
        public net.minecraft.world.item.crafting.RecipeHolder<?> getBackingRecipe(){return view.holder();}
        public void addWidgets(WidgetHolder widgets){
            var inputs=getInputs();for(int i=0;i<inputs.size();i++)widgets.addSlot(inputs.get(i),i%7*18,6+i/7*18);
            for(int i=0;i<view.recipe().outputs().size();i++){var output=view.recipe().outputs().get(i);widgets.addSlot(EmiIngredient.of(RecipeView.outputs(output).stream().map(EmiStack::of).toList()).setChance((float)output.chance()),164+i%3*18,6+i/3*18).recipeContext(this).appendTooltip(Component.literal(output.randomCircuit().isEmpty()?String.format("Chance: %.2f%%",output.chance()*100):"One random circuit variant"));}
            widgets.addText(Component.literal("→"),140,18,0xff555555,false);widgets.addText(Component.literal(view.cost()),2,60,0xff555555,false);widgets.addText(Component.literal(view.detail()),2,76,0xff555555,false);
        }
    }
    private static final class Handler implements EmiRecipeHandler<CalculatorMenu> {
        public EmiPlayerInventory getInventory(AbstractContainerScreen<CalculatorMenu> screen){return new EmiPlayerInventory(screen.getMenu().slots.stream().filter(slot->slot.mayPickup(Minecraft.getInstance().player)&&(slot.container==Minecraft.getInstance().player.getInventory()||slot.index<screen.getMenu().inputs)).map(slot->EmiStack.of(slot.getItem())).toList());}
        public boolean supportsRecipe(EmiRecipe recipe){return recipe instanceof Display;}
        public boolean canCraft(EmiRecipe recipe,EmiCraftContext<CalculatorMenu> context){return recipe instanceof Display display&&RecipeTransfer.plan(context.getScreenHandler(),display.view().recipe(),Minecraft.getInstance().player,1)!=null;}
        public boolean craft(EmiRecipe recipe,EmiCraftContext<CalculatorMenu> context){if(!canCraft(recipe,context))return false;var display=(Display)recipe;PacketDistributor.sendToServer(new TransferPayload(context.getScreenHandler().containerId,display.view().holder().id(),Math.clamp(context.getAmount(),1,64)));return true;}
    }
}
