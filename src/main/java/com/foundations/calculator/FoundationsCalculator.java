package com.foundations.calculator;

import com.foundations.calculator.content.Content;
import com.foundations.calculator.core.CalculatorConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

/** A single mod: the former Sonar Core responsibilities live in core/, not another mod. */
@Mod(FoundationsCalculator.ID)
public final class FoundationsCalculator {
    public static final String ID = "foundations_calculator";
    public FoundationsCalculator(IEventBus bus, ModContainer container) {
        Content.register(bus);
        bus.addListener(CalculatorConfig::configLoaded);
        bus.addListener(CalculatorConfig::configReloaded);
        bus.addListener(CalculatorConfig::configUnloaded);
        bus.addListener(com.foundations.calculator.core.ContentProperties::applyComponents);
        container.registerConfig(ModConfig.Type.SERVER, CalculatorConfig.SPEC);
        container.registerConfig(ModConfig.Type.CLIENT,com.foundations.calculator.core.ClientConfig.SPEC);
    }
}
