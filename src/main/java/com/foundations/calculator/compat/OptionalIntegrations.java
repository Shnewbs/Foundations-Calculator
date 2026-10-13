package com.foundations.calculator.compat;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
/** Target integrations are activated only when their matching platform API is available. */
public final class OptionalIntegrations {
    @SubscribeEvent public static void setup(FMLCommonSetupEvent event){event.enqueueWork(()->{com.foundations.calculator.core.ContentProperties.finishRegistration();if(ModList.get().isLoaded("ae2"))AE2Energy.register();});}
    private OptionalIntegrations(){}
}
