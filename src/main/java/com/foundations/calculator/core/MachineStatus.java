package com.foundations.calculator.core;

public enum MachineStatus {
    DISABLED("Disabled by server config"), RESEARCH_READY("Research in progress"), RESEARCH_KNOWN("Sample known or no research recipe"),
    READY("Ready"), NEED_INPUT("Insert recipe inputs"), NO_RECIPE("No matching recipe"),
    NEED_POWER("Needs power"), RUNNING("Processing"), OUTPUT_BLOCKED("Outputs full"),
    POWER_AVAILABLE("Power available"), CHARGING("Charging item"), CRANK("Use crank to generate FE"),
    GENERATING("Generating FE"), NEED_FUEL("Insert fuel"), NEED_FEED("Insert extractor ingredient"),
    NEED_CALCULATOR("Insert a calculator"), NEED_FLAWLESS("Insert Flawless Calculator"),
    MODULES_READY("Ready to install modules"), NEED_MODULE("Insert a nutrition module"),
    CHARGING_MODULE("Charging module"), ALREADY_ANALYSED("Circuit already analysed"),
    INCOMPLETE("Structure incomplete"), BUILDING("Building greenhouse"), DEMOLISHING("Demolishing greenhouse"),
    FARMING("Farming"), PAUSED("Paused"), NEED_REDSTONE("Needs redstone signal"),
    COOLDOWN("Cooling down"), TARGET_REACHED("Target already reached"),
    NEED_STORAGE("Place an inventory below"), COLLECTING("Collecting drops"),
    NEED_LOCATOR("Insert bound Locator Module"), NEED_OWNER("Bound owner is not online"),
    WAITING_LIGHTNING("Waiting for lightning"), NEED_GREENHOUSE("Connect Flawless Greenhouse"),
    GAS_CONTROLLED("CO2 target reached"), GAS_RUNNING("Supplying CO2"),
    CHECK_TREE("Needs mature tree leaves"), ENERGY_DISPLAY("Attached FE display");
    public final String label;
    MachineStatus(String label){this.label=label;}
    public static MachineStatus decode(int value){return value>=0&&value<values().length?values()[value]:READY;}
}
