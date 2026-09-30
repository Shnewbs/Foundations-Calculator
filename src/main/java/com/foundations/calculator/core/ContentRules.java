package com.foundations.calculator.core;

import com.foundations.calculator.FoundationsCalculator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.minecraft.server.MinecraftServer;

@EventBusSubscriber(modid=FoundationsCalculator.ID)
public final class ContentRules {
    private static void filter(MinecraftServer server){var manager=server.getRecipeManager();var level=server.overworld();var recipes=manager.getRecipes().stream().filter(r->RecipePolicies.enabled(r,level)).toList();if(recipes.size()!=manager.getRecipes().size())manager.replaceRecipes(recipes);}
    @SubscribeEvent public static void reload(OnDatapackSyncEvent e){filter(e.getPlayerList().getServer());}
    @SubscribeEvent public static void started(ServerStartedEvent e){filter(e.getServer());}
    @SubscribeEvent public static void use(PlayerInteractEvent.RightClickItem e){if(!RecipePolicies.itemEnabled(e.getItemStack()))e.setCanceled(true);}
    @SubscribeEvent public static void block(PlayerInteractEvent.RightClickBlock e){if(!RecipePolicies.itemEnabled(e.getItemStack()))e.setCanceled(true);}
    @SubscribeEvent public static void mine(PlayerInteractEvent.LeftClickBlock e){if(!RecipePolicies.itemEnabled(e.getItemStack()))e.setCanceled(true);}
    @SubscribeEvent public static void attack(AttackEntityEvent e){if(!RecipePolicies.itemEnabled(e.getEntity().getMainHandItem()))e.setCanceled(true);}
    @SubscribeEvent public static void placed(BlockEvent.EntityPlaceEvent e){var id=BuiltInRegistries.BLOCK.getKey(e.getPlacedBlock().getBlock());if(id.getNamespace().equals(FoundationsCalculator.ID)&&!CalculatorConfig.enabled(id.getPath()))e.setCanceled(true);}
    @SubscribeEvent public static void fuel(net.neoforged.neoforge.event.furnace.FurnaceFuelBurnTimeEvent e){var id=BuiltInRegistries.ITEM.getKey(e.getItemStack().getItem());if(id.getNamespace().equals(FoundationsCalculator.ID)){int override=ContentProperties.burnTime(id.getPath());if(override>=0)e.setBurnTime(override);}}
    @SubscribeEvent public static void harvest(net.neoforged.neoforge.event.entity.player.PlayerEvent.HarvestCheck e){var id=BuiltInRegistries.BLOCK.getKey(e.getTargetBlock().getBlock());if(id.getNamespace().equals(FoundationsCalculator.ID)&&ContentProperties.freelyHarvestable(id.getPath()))e.setCanHarvest(true);}
    private ContentRules(){}
}
