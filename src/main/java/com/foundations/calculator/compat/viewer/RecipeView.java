package com.foundations.calculator.compat.viewer;

import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeHolder;

public record RecipeView(RecipeHolder<ProcessRecipe> holder) {
    public ProcessRecipe recipe(){return holder.value();}
    public static List<RecipeView> all(){var level=Minecraft.getInstance().level;if(level==null)return List.of();return level.getRecipeManager().getAllRecipesFor(Content.PROCESS_TYPE.get()).stream().filter(r->RecipePolicies.enabled(r,level)).map(RecipePolicies::apply).map(RecipeView::new).toList();}
    public static List<String> families(){Set<String> names=new TreeSet<>(List.of("calculator","scientific","atomic","flawless","stone_separator","algorithm_separator","extraction_chamber","precision_chamber","restoration_chamber","reassembly_chamber","processing_chamber","conductor_mast","health_processor","starch_extractor","redstone_extractor","glowstone_extractor","harvest","fabrication_chamber","research","analysis_0","analysis_1","analysis_2","analysis_3","analysis_4","analysis_5","analysis_6"));all().forEach(r->names.add(r.recipe().machine()));return List.copyOf(names);}
    public static String machine(String family){return switch(family){case "scientific"->"scientific_calculator";case "atomic"->"atomic_calculator";case "flawless"->"flawless_calculator";case "harvest"->"algorithm_assimilator";case "research"->"research_chamber";default->family.startsWith("analysis_")?"analysing_chamber":family;};}
    public static ItemStack icon(String family){String id=machine(family);return new ItemStack(Content.ITEMS_BY_ID.containsKey(id)?Content.item(id):Content.item("info_calculator"));}
    public static Component title(String family){return Component.literal("Foundations: "+family.replace('_',' '));}
    public static List<ItemStack> inputs(CountedIngredient ingredient){return Arrays.stream(ingredient.ingredient().getItems()).map(s->{var copy=s.copyWithCount(ingredient.count());if(ingredient.circuitState()>0){var tag=CircuitData.tag(copy);tag.putBoolean("Analysed",true);tag.putInt("Stable",ingredient.circuitState()==2?1:0);copy.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA,net.minecraft.world.item.component.CustomData.of(tag));}return copy;}).toList();}
    public static List<ItemStack> outputs(ProcessRecipe.Result result){if(result.randomCircuit().isEmpty())return List.of(result.stack());List<ItemStack> stacks=new ArrayList<>();for(int i=0;i<14;i++)stacks.add(new ItemStack(Content.item(result.randomCircuit()+"_"+i),result.stack().getCount()));return stacks;}
    public String cost(){
        String family=recipe().machine();if(family.startsWith("analysis_"))return String.format("Reward: %,d FE",recipe().energy());
        boolean portable=Set.of("calculator","scientific","flawless").contains(family);
        int energy=family.equals("research")&&!CalculatorConfig.flag("research.consumeEnergy",true)?0:CalculatorConfig.energy(machine(family),RecipePolicies.energy(holder,portable?1:0));
        return String.format("%,d FE · %s",energy,portable?"instant":CalculatorConfig.ticks(machine(family),recipe().ticks())+" ticks");
    }
    public String detail(){if(recipe().research())return "Research: "+recipe().researchGroup().replace('_',' ');if(recipe().machine().equals("research"))return "Unlock: "+recipe().researchGroup().replace('_',' ');if(recipe().machine().startsWith("analysis_"))return "Analyser reward roll: "+recipe().value();return recipe().value()>0?"Value: "+recipe().value():"";}
}
