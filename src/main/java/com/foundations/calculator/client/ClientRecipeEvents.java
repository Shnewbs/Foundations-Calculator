package com.foundations.calculator.client;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.core.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
@EventBusSubscriber(modid=FoundationsCalculator.ID,value=Dist.CLIENT)
public final class ClientRecipeEvents {
    @SubscribeEvent public static void recipes(net.neoforged.neoforge.client.event.RecipesUpdatedEvent e){RecipeIndex.clear();}
    @SubscribeEvent public static void logout(net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut e){com.foundations.calculator.client.guide.GuideValues.clear();ResearchData.clientGroups=java.util.Set.of();ResearchData.clientMastery=java.util.Map.of();RecipeIndex.clear();}
    private ClientRecipeEvents(){}
}
