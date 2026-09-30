package com.foundations.calculator.client.guide;
import java.util.*;
import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

/** Per-reader recipe cache; native recipes/config are the source, never copied guide recipe numbers. */
public final class GuideRecipes {
    public record Part(ItemStack icon,String text){}
    public record View(int count,int page,List<Part> parts){}
    private final Map<String,List<RecipeHolder<?>>> cache=new HashMap<>();
    private Object manager;private long revision=-1,config=-1;
    public void clear(){cache.clear();manager=null;}
    public View view(String query,int page,String profile){
        var mc=Minecraft.getInstance();if(mc.level==null)return new View(0,0,List.of(new Part(ItemStack.EMPTY,"Recipe data unavailable: not in a world.")));
        var current=mc.level.getRecipeManager();
        if(manager!=current||revision!=RecipeIndex.revision()||config!=CalculatorConfig.revision()){clear();manager=current;revision=RecipeIndex.revision();config=CalculatorConfig.revision();}
        if(cache.size()>=128&&!cache.containsKey(query))cache.clear();
        var recipes=cache.computeIfAbsent(query,q->find(q));
        if(recipes.isEmpty())return new View(0,0,List.of(new Part(ItemStack.EMPTY,"No matching enabled recipe in this server's recipe set. A pack may remove it; world-driven operations may not use recipes.")));
        int index=Math.floorMod(page,recipes.size());var holder=recipes.get(index);var recipe=holder.value();var parts=new ArrayList<Part>();
        parts.add(new Part(ItemStack.EMPTY,"Recipe "+(index+1)+" / "+recipes.size()+" · "+holder.id()));
        if(recipe instanceof ProcessRecipe p){
            parts.add(new Part(ItemStack.EMPTY,"Process: "+p.machine().replace('_',' ')));
            int slot=1;
            for(var in:p.inputs()){
                ItemStack[] accepted=in.ingredient().getItems();var stack=accepted.length==0?ItemStack.EMPTY:accepted[0].copy();if(!stack.isEmpty())stack.setCount(in.count());
                String state=in.circuitState()==2?" · analysed AND stable":in.circuitState()==1?" · analysed":"";
                parts.add(new Part(stack,"Input "+slot+++": "+in.count()+" × "+(stack.isEmpty()?"unresolved ingredient/tag":stack.getHoverName().getString())+state+(accepted.length>1?" ("+accepted.length+" accepted variants)":"")));
            }
            int output=1;for(var r:p.outputs())parts.add(new Part(r.stack().copy(),"Output "+output+++": "+r.stack().getCount()+" × "+r.stack().getHoverName().getString()+" · "+String.format(Locale.ROOT,"%.2f%%",r.chance()*100)+(r.randomCircuit().isEmpty()?"":" · random "+r.randomCircuit()+" variant")));
            if(p.outputs().isEmpty())parts.add(new Part(ItemStack.EMPTY,"No ordinary item result. This recipe defines research, nutrition, or another program value."));
            parts.add(new Part(ItemStack.EMPTY,"Recipe fields (after per-recipe overrides): "+p.energy()+" FE; "+p.ticks()+" ticks; value "+p.value()+". Program rules and machine settings can modify their meaning."));
            if(!profile.isEmpty()&&CalculatorConfig.SPEC.isLoaded()){
                boolean normal=!p.machine().contains("extractor")&&!p.machine().startsWith("analysis_")&&!p.machine().endsWith("processor")&&!p.machine().equals("harvest");
                if(normal){
                    @SuppressWarnings("unchecked") var typed=(RecipeHolder<ProcessRecipe>)holder;
                    int minimum=profile.equals("docking_station")?10:Set.of("calculator","scientific_calculator","flawless_calculator","dynamic_calculator","dynamic_module").contains(profile)?1:0;
                    int cost=CalculatorConfig.energy(profile,RecipePolicies.energy(typed,minimum));if(p.machine().equals("research")&&!CalculatorConfig.flag("research.consumeEnergy",true))cost=0;
                    boolean instant=Set.of("calculator","scientific_calculator","flawless_calculator","dynamic_calculator","dynamic_module").contains(profile);
                    parts.add(new Part(ItemStack.EMPTY,"For "+profile.replace('_',' ')+": "+cost+" FE per operation"+(instant?"; immediate calculation":"; "+CalculatorConfig.ticks(profile,p.ticks())+" ticks before upgrades")+". Server-synchronized settings; installed upgrades are not assumed."));
                }
            }
            if(p.research())parts.add(new Part(ItemStack.EMPTY,(ResearchData.allowed(mc.level,mc.player==null?null:mc.player.getUUID(),p)?"Unlocked / no research restriction":"Research required")+": "+(p.researchGroup().isBlank()?"general":p.researchGroup())+". Use Research for sample requirements."));
        }else{
            int slot=0;int rowWidth=recipe instanceof ShapedRecipe shaped?shaped.getWidth():0;
            for(var ingredient:recipe.getIngredients()){
                int currentSlot=slot++;if(ingredient.isEmpty())continue;var choices=ingredient.getItems();var stack=choices.length==0?ItemStack.EMPTY:choices[0].copy();
                String where=rowWidth>0?"Grid row "+(currentSlot/rowWidth+1)+", column "+(currentSlot%rowWidth+1):"Ingredient "+slot;
                parts.add(new Part(stack,where+": "+(stack.isEmpty()?"unresolved ingredient":stack.getHoverName().getString())+(choices.length>1?" ("+choices.length+" accepted variants)":"")));
            }
            ItemStack result=recipe.getResultItem(mc.level.registryAccess());parts.add(new Part(result.copy(),"Result: "+result.getCount()+" × "+result.getHoverName().getString()));
            parts.add(new Part(ItemStack.EMPTY,rowWidth>0?"Shaped crafting. Coordinates are relative to this recipe's top-left corner.":"Recipe type: "+BuiltInRegistries.RECIPE_TYPE.getKey(recipe.getType())+". Use a matching crafting station."));
        }
        return new View(recipes.size(),index,List.copyOf(parts));
    }
    private List<RecipeHolder<?>> find(String query){
        var level=Minecraft.getInstance().level;if(level==null)return List.of();var out=new ArrayList<RecipeHolder<?>>();
        if(query.startsWith("machine:")){out.addAll(RecipeIndex.allForMachine(level,query.substring(8)));return List.copyOf(out);}
        if(query.startsWith("id:")){
            ResourceLocation id=ResourceLocation.tryParse(query.substring(3));if(id!=null)level.getRecipeManager().byKey(id).filter(r->RecipePolicies.enabled(r,level)).ifPresent(r->out.add(apply(r)));return List.copyOf(out);
        }
        boolean uses=query.startsWith("uses:");String raw=query.startsWith("item:")||uses?query.substring(5):"";ResourceLocation id=ResourceLocation.tryParse(raw);
        if(id==null||!BuiltInRegistries.ITEM.containsKey(id))return List.of();var item=BuiltInRegistries.ITEM.get(id);ItemStack stack=new ItemStack(item);
        for(var holder:level.getRecipeManager().getRecipes()){
            if(out.size()>=2048)break;
            if(!RecipePolicies.enabled(holder,level))continue;
            var recipe=holder.value();boolean matches;
            if(recipe instanceof ProcessRecipe p)matches=uses?p.inputs().stream().anyMatch(i->i.ingredient().test(stack)):p.outputs().stream().anyMatch(r->r.stack().is(item));
            else matches=uses?recipe.getIngredients().stream().anyMatch(i->i.test(stack)):recipe.getResultItem(level.registryAccess()).is(item);
            if(matches)out.add(apply(holder));
        }
        out.sort(Comparator.comparing(r->r.id().toString()));return List.copyOf(out);
    }
    private RecipeHolder<?> apply(RecipeHolder<?> r){if(r.value() instanceof ProcessRecipe p)return RecipePolicies.apply(new RecipeHolder<>(r.id(),p));return r;}
}
