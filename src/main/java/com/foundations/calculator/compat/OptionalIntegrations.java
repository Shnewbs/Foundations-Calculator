package com.foundations.calculator.compat;
import com.foundations.calculator.FoundationsCalculator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
@EventBusSubscriber(modid=FoundationsCalculator.ID,bus=EventBusSubscriber.Bus.MOD)
public final class OptionalIntegrations {
    @SubscribeEvent public static void setup(FMLCommonSetupEvent event){event.enqueueWork(()->{com.foundations.calculator.core.ContentProperties.finishRegistration();if(ModList.get().isLoaded("ae2"))AE2Energy.register();if(ModList.get().isLoaded("mekanism"))MekanismEnergy.register();if(ModList.get().isLoaded("gtceu"))GregTechEnergy.register();if(ModList.get().isLoaded("modern_industrialization"))ModernIndustrializationEnergy.register();if(ModList.get().isLoaded("grandpower"))GrandPowerEnergy.register();});}
    @SubscribeEvent(priority=net.neoforged.bus.api.EventPriority.HIGH) public static void capabilities(net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent event){
        if(ModList.get().isLoaded("mekanism"))MekanismEnergy.capabilities(event);
        if(ModList.get().isLoaded("gtceu"))GregTechEnergy.capabilities(event);
        if(ModList.get().isLoaded("modern_industrialization"))ModernIndustrializationEnergy.capabilities(event);
        if(ModList.get().isLoaded("grandpower"))GrandPowerEnergy.capabilities(event);
    }
    private OptionalIntegrations(){}
}
