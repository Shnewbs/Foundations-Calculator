package com.foundations.calculator.core;

import java.util.Map;
import java.util.Set;

/** Immutable machine defaults shared by processing, menus and power profiles.
 * Configuration overrides remain in CalculatorConfig; this registry owns defaults only.
 */
public record MachineDefinition(int inputCount, boolean outputs, boolean batterySlot,
        boolean upgrades, boolean usesEnergy, boolean energyStorage, boolean generator,
        int transferRate, int chargeRate, int capacity) {
    private static final Set<String> NO_OUTPUTS = Set.of("module_workstation","weather_controller","magnetic_flux","calculator_plug","calculator_locator","power_cube","advanced_power_cube","creative_power_cube","hand_cranked_generator","stone_assimilator","calculator_screen_block");
    private static final Set<String> NO_ENERGY = Set.of("rain_sensor","gas_lantern_off","magnetic_flux","scarecrow","stone_assimilator","algorithm_assimilator","calculator_plug","weather_station","transmitter","calculator_screen_block","crank_handle","module_workstation","storage_chamber","reinforced_chest");
    private static final Set<String> BATTERY = Set.of("module_workstation","stone_assimilator","health_processor","hunger_processor");
    private static final Set<String> UPGRADES = Set.of("docking_station","atomic_calculator","reinforced_furnace","stone_separator","algorithm_separator","extraction_chamber","restoration_chamber","reassembly_chamber","precision_chamber","processing_chamber","analysing_chamber","fabrication_chamber");
    private static final Set<String> STORAGE = Set.of("power_cube","advanced_power_cube");
    private static final Set<String> GENERATORS = Set.of("hand_cranked_generator","creative_power_cube","calculator_locator","conductor_mast","analysing_chamber","starch_extractor","redstone_extractor","glowstone_extractor");
    private static final Map<String, Integer> INPUTS = Map.ofEntries(
        Map.entry("weather_controller",0),Map.entry("calculator_screen_block",0),Map.entry("stone_assimilator",0),
        Map.entry("module_workstation",16),Map.entry("basic_greenhouse",14),Map.entry("advanced_greenhouse",14),Map.entry("flawless_greenhouse",14),
        Map.entry("magnetic_flux",8),Map.entry("atomic_multiplier",8),Map.entry("dynamic_calculator",7),Map.entry("atomic_calculator",3),
        Map.entry("docking_station",4),Map.entry("fabrication_chamber",14),Map.entry("reinforced_chest",14),Map.entry("storage_chamber",14),
        Map.entry("starch_extractor",2),Map.entry("redstone_extractor",2),Map.entry("glowstone_extractor",2));
    private static final Map<String, Integer> TRANSFER = Map.ofEntries(
        Map.entry("power_cube",400),Map.entry("hand_cranked_generator",400),Map.entry("basic_greenhouse",400),
        Map.entry("advanced_power_cube",64000),Map.entry("flawless_greenhouse",64000),Map.entry("co2_generator",64000),Map.entry("weather_controller",64000),
        Map.entry("analysing_chamber",12800),Map.entry("atomic_calculator",12800),Map.entry("atomic_multiplier",Integer.MAX_VALUE),
        Map.entry("calculator_locator",Integer.MAX_VALUE),Map.entry("conductor_mast",Integer.MAX_VALUE),Map.entry("creative_power_cube",Integer.MAX_VALUE));
    private static final Map<String, Integer> CHARGE = Map.of("power_cube",4,"advanced_power_cube",100000,"conductor_mast",100000,"calculator_locator",5000000,"creative_power_cube",Integer.MAX_VALUE);

    private static final Map<String, Integer> CAPACITY = Map.ofEntries(
        Map.entry("basic_greenhouse",350000),Map.entry("advanced_greenhouse",350000),Map.entry("flawless_greenhouse",500000),
        Map.entry("co2_generator",1000000),Map.entry("weather_controller",1000000),Map.entry("atomic_multiplier",1500000000),
        Map.entry("calculator_locator",50000000),Map.entry("hand_cranked_generator",1000),Map.entry("advanced_power_cube",100000),
        Map.entry("creative_power_cube",Integer.MAX_VALUE),Map.entry("analysing_chamber",100000),Map.entry("conductor_mast",50000000),
        Map.entry("starch_extractor",1000000),Map.entry("redstone_extractor",1000000),Map.entry("glowstone_extractor",1000000));

    private static MachineDefinition create(String id) {
        boolean energy = !NO_ENERGY.contains(id);
        int transfer = TRANSFER.getOrDefault(id,3200);
        return new MachineDefinition(INPUTS.getOrDefault(id,1),!NO_OUTPUTS.contains(id),energy||BATTERY.contains(id),
            UPGRADES.contains(id),energy,STORAGE.contains(id),GENERATORS.contains(id),transfer,CHARGE.getOrDefault(id,transfer),CAPACITY.getOrDefault(id,50000));
    }
    private static final MachineDefinition DEFAULT = create("");
    private static final Map<String, MachineDefinition> DEFINITIONS;
    static {
        var ids = new java.util.HashSet<String>();
        for (var group : java.util.List.of(NO_OUTPUTS,NO_ENERGY,BATTERY,UPGRADES,STORAGE,GENERATORS,INPUTS.keySet(),TRANSFER.keySet(),CHARGE.keySet(),CAPACITY.keySet())) ids.addAll(group);
        var definitions = new java.util.HashMap<String, MachineDefinition>();
        for (String id : ids) definitions.put(id,create(id));
        DEFINITIONS = Map.copyOf(definitions);
    }
    public static MachineDefinition forMachine(String id) { return DEFINITIONS.getOrDefault(id,DEFAULT); }
    public static boolean hasDedicatedChargeRate(String id) { return CHARGE.containsKey(id); }
}
