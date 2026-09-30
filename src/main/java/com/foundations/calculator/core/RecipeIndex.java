package com.foundations.calculator.core;

import java.util.*;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.content.Content;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;

/** Sorted, policy-applied families; values deliberately do not retain their weak RecipeManager key. */
@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class RecipeIndex {
    private record Index(Map<String,List<RecipeHolder<ProcessRecipe>>> families,
                         List<RecipeHolder<ProcessRecipe>> all) {}
    private static final Map<RecipeManager,Index> CACHE = new WeakHashMap<>();
    private static volatile long revision;
    public static long revision() { return revision; }
    public static List<RecipeHolder<ProcessRecipe>> forMachine(Level level,String machine) {
        return forMachine(level,machine,null);
    }
    public static List<RecipeHolder<ProcessRecipe>> forMachine(Level level,String machine,UUID player) {
        return allForMachine(level,machine).stream().filter(r -> ResearchData.allowed(level,player,r.value())).toList();
    }
    private static Index index(Level level) {
        return CACHE.computeIfAbsent(level.getRecipeManager(),manager -> {
            List<RecipeHolder<ProcessRecipe>> all = manager.getAllRecipesFor(Content.PROCESS_TYPE.get()).stream()
                .filter(r -> RecipePolicies.enabled(r,level)).map(RecipePolicies::apply)
                .sorted(Comparator.comparing(r -> r.id().toString())).toList();
            Map<String,List<RecipeHolder<ProcessRecipe>>> families = new HashMap<>();
            for (var recipe : all) families.computeIfAbsent(recipe.value().machine(), k -> new ArrayList<>()).add(recipe);
            families.replaceAll((key,recipes) -> List.copyOf(recipes));
            return new Index(Map.copyOf(families),all);
        });
    }
    public static synchronized List<RecipeHolder<ProcessRecipe>> allForMachine(Level level,String machine) {
        return index(level).families().getOrDefault(machine,List.of());
    }
    public static synchronized List<RecipeHolder<ProcessRecipe>> all(Level level) { return index(level).all(); }
    public static synchronized void clear() { CACHE.clear(); revision++; }
    @SubscribeEvent public static void reload(OnDatapackSyncEvent event) { clear(); }
    @SubscribeEvent public static void stop(ServerStoppedEvent event) { clear(); }
    private RecipeIndex() {}
}
