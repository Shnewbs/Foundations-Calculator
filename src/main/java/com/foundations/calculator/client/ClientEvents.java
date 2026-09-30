package com.foundations.calculator.client;

import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.content.Content;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid=FoundationsCalculator.ID,bus=EventBusSubscriber.Bus.MOD,value=Dist.CLIENT)
public final class ClientEvents{
    @SubscribeEvent public static void guideResources(net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent e){e.registerReloadListener(new com.foundations.calculator.client.guide.GuideResources());}
    @SubscribeEvent public static void guides(net.neoforged.fml.event.lifecycle.FMLClientSetupEvent e){e.enqueueWork(com.foundations.calculator.client.guide.GuideValues::register);}
    @SubscribeEvent public static void config(net.neoforged.fml.event.lifecycle.FMLClientSetupEvent e){net.neoforged.fml.ModList.get().getModContainerById(FoundationsCalculator.ID).orElseThrow().registerExtensionPoint(net.neoforged.neoforge.client.gui.IConfigScreenFactory.class,(container,parent)->new net.neoforged.neoforge.client.gui.ConfigurationScreen(container,parent));}
    @SubscribeEvent public static void renderers(net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers e){e.registerBlockEntityRenderer(Content.MACHINE_ENTITY.get(),MachineRenderer::new);e.registerEntityRenderer(Content.SOIL_PROJECTILE.get(),net.minecraft.client.renderer.entity.ThrownItemRenderer::new);e.registerEntityRenderer(Content.STONE_PROJECTILE.get(),net.minecraft.client.renderer.entity.ThrownItemRenderer::new);e.registerEntityRenderer(Content.GRENADE.get(),net.minecraft.client.renderer.entity.ThrownItemRenderer::new);e.registerEntityRenderer(Content.BABY_GRENADE.get(),net.minecraft.client.renderer.entity.ThrownItemRenderer::new);}
    @SubscribeEvent public static void itemExtensions(net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent e){
        var extension=new net.neoforged.neoforge.client.extensions.common.IClientItemExtensions(){private MachineItemRenderer renderer;public net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer getCustomRenderer(){if(renderer==null)renderer=new MachineItemRenderer();return renderer;}};
        for(String id:com.foundations.calculator.content.MachineBlock.MODELED)e.registerItem(extension,Content.item(id));
    }
    @SubscribeEvent public static void screens(RegisterMenuScreensEvent e){e.register(Content.MENU.get(),CalculatorScreen::new);e.register(Content.SMELTING_MENU.get(),SmeltingModuleScreen::new);e.register(Content.BULK_MENU.get(),BulkStorageScreen::new);}
}
