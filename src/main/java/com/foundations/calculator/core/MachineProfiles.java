package com.foundations.calculator.core;
/** Default FE transfer and charging profiles from Calculator 1.12.2. */
public final class MachineProfiles {
    private MachineProfiles() {}
    public static boolean storage(String machine){return machine.equals("power_cube")||machine.equals("advanced_power_cube");}
    public static boolean generator(String machine){return java.util.Set.of("hand_cranked_generator","creative_power_cube","calculator_locator","conductor_mast","analysing_chamber","starch_extractor","redstone_extractor","glowstone_extractor").contains(machine);}
    public static int transfer(String machine){int override=CalculatorConfig.TRANSFER_RATE.get();int base=override>0?override:CalculatorConfig.machine(machine,"transferRate",switch(machine){
        case "power_cube","hand_cranked_generator","basic_greenhouse"->400;
        case "advanced_power_cube","flawless_greenhouse","co2_generator","weather_controller"->64000;
        case "analysing_chamber","atomic_calculator"->12800;
        case "atomic_multiplier","calculator_locator","conductor_mast","creative_power_cube"->Integer.MAX_VALUE;
        default->3200;
    });return CalculatorConfig.scaledTransfer(base);}
    public static int charging(String machine){int fallback=switch(machine){case "power_cube"->4;case "advanced_power_cube","conductor_mast"->100000;case "calculator_locator"->5000000;case "creative_power_cube"->Integer.MAX_VALUE;default->unscaledTransfer(machine);};int base=CalculatorConfig.machine(machine,"chargeRate",fallback);return CalculatorConfig.scaledTransfer(base);}
    private static int unscaledTransfer(String machine){int override=CalculatorConfig.TRANSFER_RATE.get();return override>0?override:CalculatorConfig.machine(machine,"transferRate",switch(machine){case "power_cube","hand_cranked_generator","basic_greenhouse"->400;case "advanced_power_cube","flawless_greenhouse","co2_generator","weather_controller"->64000;case "analysing_chamber","atomic_calculator"->12800;case "atomic_multiplier","calculator_locator","conductor_mast","creative_power_cube"->Integer.MAX_VALUE;default->3200;});}
}
