# Complete server configuration reference

Generated from the same catalog loaded by the mod: **4,272 settings**. See [CONFIGURATION.md](../CONFIGURATION.md) for precedence, file locations, reload behavior and examples. Values shown here are shipped defaults; recipe-specific `-1` means inherit.

## machines

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `machines.crankEnergyPerUse` | `160` | 1 … 1000000 | Crank energy per use. See the category rules in CONFIGURATION.md. |
| `machines.energyTransferPerTick` | `0` | 0 … 2147483647 | Energy transfer per tick. See the category rules in CONFIGURATION.md. |
| `machines.processEnergyMultiplier` | `1.0` | 0.0 … 100.0 | Process energy multiplier. See the category rules in CONFIGURATION.md. |
| `machines.processTimeMultiplier` | `1.0` | 0.01 … 100.0 | Process time multiplier. See the category rules in CONFIGURATION.md. |
| `machines.scarecrowInterval` | `500` | 1 … 1000000 | Scarecrow interval. See the category rules in CONFIGURATION.md. |
| `machines.scarecrowRange` | `3` | 1 … 32 | Scarecrow range. See the category rules in CONFIGURATION.md. |
| `machines.atomicMultiplierEnergy` | `1500000000` | 1 … 1500000000 | Atomic multiplier energy. See the category rules in CONFIGURATION.md. |
| `machines.allowGrenades` | `true` | true / false | Allow grenades. See the category rules in CONFIGURATION.md. |
| `machines.unstableLocatorChangesTime` | `true` | true / false | Unstable locator changes time. See the category rules in CONFIGURATION.md. |
| `machines.unstableLocatorAffectsOwner` | `true` | true / false | Unstable locator affects owner. See the category rules in CONFIGURATION.md. |

## enableLegacyResearchRecipes

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `enableLegacyResearchRecipes` | `false` | true / false | Enable legacy research recipes. See the category rules in CONFIGURATION.md. |

## machine

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `machine.advanced_greenhouse.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.advanced_greenhouse.capacity` | `350000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.advanced_greenhouse.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.advanced_greenhouse.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.advanced_greenhouse.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.advanced_greenhouse.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.advanced_greenhouse.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.advanced_greenhouse.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.advanced_greenhouse.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.advanced_greenhouse.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.advanced_greenhouse.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.advanced_greenhouse.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.advanced_greenhouse.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.advanced_power_cube.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.advanced_power_cube.capacity` | `100000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.advanced_power_cube.transferRate` | `64000` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.advanced_power_cube.chargeRate` | `100000` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.advanced_power_cube.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.advanced_power_cube.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.advanced_power_cube.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.advanced_power_cube.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.advanced_power_cube.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.algorithm_assimilator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.algorithm_assimilator.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.algorithm_assimilator.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.algorithm_assimilator.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.algorithm_assimilator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.algorithm_assimilator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.algorithm_assimilator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.algorithm_assimilator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.algorithm_assimilator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.algorithm_separator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.algorithm_separator.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.algorithm_separator.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.algorithm_separator.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.algorithm_separator.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.algorithm_separator.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.algorithm_separator.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.algorithm_separator.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.algorithm_separator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.algorithm_separator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.algorithm_separator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.algorithm_separator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.algorithm_separator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.algorithm_separator.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.analysing_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.analysing_chamber.capacity` | `100000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.analysing_chamber.transferRate` | `12800` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.analysing_chamber.chargeRate` | `12800` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.analysing_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.analysing_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.analysing_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.analysing_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.analysing_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.analysing_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.analysing_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.analysing_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.analysing_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.analysing_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.atomic_calculator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.atomic_calculator.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.atomic_calculator.transferRate` | `12800` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.atomic_calculator.chargeRate` | `12800` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.atomic_calculator.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.atomic_calculator.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.atomic_calculator.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.atomic_calculator.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.atomic_calculator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.atomic_calculator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.atomic_calculator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.atomic_calculator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.atomic_calculator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.atomic_calculator.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.atomic_multiplier.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.atomic_multiplier.capacity` | `1500000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.atomic_multiplier.transferRate` | `2147483647` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.atomic_multiplier.chargeRate` | `2147483647` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.atomic_multiplier.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.atomic_multiplier.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.atomic_multiplier.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.atomic_multiplier.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.atomic_multiplier.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.atomic_multiplier.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.atomic_multiplier.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.atomic_multiplier.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.atomic_multiplier.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.atomic_multiplier.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.basic_greenhouse.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.basic_greenhouse.capacity` | `350000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.basic_greenhouse.transferRate` | `400` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.basic_greenhouse.chargeRate` | `400` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.basic_greenhouse.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.basic_greenhouse.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.basic_greenhouse.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.basic_greenhouse.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.basic_greenhouse.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.basic_greenhouse.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.basic_greenhouse.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.basic_greenhouse.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.basic_greenhouse.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.calculator_locator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.calculator_locator.capacity` | `50000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.calculator_locator.transferRate` | `2147483647` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.calculator_locator.chargeRate` | `5000000` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.calculator_locator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.calculator_locator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.calculator_locator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.calculator_locator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.calculator_locator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.calculator_plug.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.calculator_plug.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.calculator_plug.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.calculator_plug.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.calculator_plug.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.calculator_plug.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.calculator_plug.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.calculator_plug.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.calculator_plug.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.calculator_screen_block.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.calculator_screen_block.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.calculator_screen_block.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.calculator_screen_block.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.calculator_screen_block.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.calculator_screen_block.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.calculator_screen_block.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.calculator_screen_block.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.calculator_screen_block.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.co2_generator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.co2_generator.capacity` | `1000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.co2_generator.transferRate` | `64000` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.co2_generator.chargeRate` | `64000` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.co2_generator.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.co2_generator.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.co2_generator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.co2_generator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.co2_generator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.co2_generator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.co2_generator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.conductor_mast.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.conductor_mast.capacity` | `50000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.conductor_mast.transferRate` | `2147483647` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.conductor_mast.chargeRate` | `100000` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.conductor_mast.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.conductor_mast.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.conductor_mast.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.conductor_mast.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.conductor_mast.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.conductor_mast.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.conductor_mast.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.conductor_mast.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.conductor_mast.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.crank_handle.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.crank_handle.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.crank_handle.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.crank_handle.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.crank_handle.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.crank_handle.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.crank_handle.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.crank_handle.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.crank_handle.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.creative_power_cube.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.creative_power_cube.capacity` | `2147483647` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.creative_power_cube.transferRate` | `2147483647` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.creative_power_cube.chargeRate` | `2147483647` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.creative_power_cube.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.creative_power_cube.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.creative_power_cube.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.creative_power_cube.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.creative_power_cube.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.docking_station.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.docking_station.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.docking_station.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.docking_station.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.docking_station.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.docking_station.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.docking_station.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.docking_station.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.docking_station.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.docking_station.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.docking_station.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.docking_station.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.docking_station.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.docking_station.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.dynamic_calculator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.dynamic_calculator.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.dynamic_calculator.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.dynamic_calculator.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.dynamic_calculator.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.dynamic_calculator.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.dynamic_calculator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.dynamic_calculator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.dynamic_calculator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.dynamic_calculator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.dynamic_calculator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.extraction_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.extraction_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.extraction_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.extraction_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.extraction_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.extraction_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.extraction_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.extraction_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.extraction_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.extraction_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.extraction_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.extraction_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.extraction_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.extraction_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.fabrication_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.fabrication_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.fabrication_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.fabrication_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.fabrication_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.fabrication_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.fabrication_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.fabrication_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.fabrication_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.fabrication_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.fabrication_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.fabrication_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.fabrication_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.fabrication_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.flawless_greenhouse.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.flawless_greenhouse.capacity` | `500000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.flawless_greenhouse.transferRate` | `64000` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.flawless_greenhouse.chargeRate` | `64000` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.flawless_greenhouse.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.flawless_greenhouse.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.flawless_greenhouse.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.flawless_greenhouse.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.flawless_greenhouse.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.flawless_greenhouse.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.flawless_greenhouse.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.flawless_greenhouse.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.flawless_greenhouse.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.gas_lantern_off.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.gas_lantern_off.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.gas_lantern_off.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.gas_lantern_off.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.gas_lantern_off.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.gas_lantern_off.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.gas_lantern_off.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.gas_lantern_off.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.gas_lantern_off.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.glowstone_extractor.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.glowstone_extractor.capacity` | `1000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.glowstone_extractor.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.glowstone_extractor.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.glowstone_extractor.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.glowstone_extractor.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.glowstone_extractor.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.glowstone_extractor.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.glowstone_extractor.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.hand_cranked_generator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.hand_cranked_generator.capacity` | `1000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.hand_cranked_generator.transferRate` | `400` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.hand_cranked_generator.chargeRate` | `400` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.hand_cranked_generator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.hand_cranked_generator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.hand_cranked_generator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.hand_cranked_generator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.hand_cranked_generator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.health_processor.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.health_processor.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.health_processor.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.health_processor.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.health_processor.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.health_processor.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.health_processor.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.health_processor.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.health_processor.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.hunger_processor.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.hunger_processor.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.hunger_processor.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.hunger_processor.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.hunger_processor.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.hunger_processor.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.hunger_processor.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.hunger_processor.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.hunger_processor.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.magnetic_flux.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.magnetic_flux.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.magnetic_flux.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.magnetic_flux.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.magnetic_flux.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.magnetic_flux.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.magnetic_flux.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.magnetic_flux.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.magnetic_flux.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.module_workstation.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.module_workstation.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.module_workstation.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.module_workstation.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.module_workstation.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.module_workstation.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.module_workstation.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.module_workstation.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.module_workstation.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.power_cube.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.power_cube.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.power_cube.transferRate` | `400` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.power_cube.chargeRate` | `4` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.power_cube.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.power_cube.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.power_cube.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.power_cube.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.power_cube.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.precision_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.precision_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.precision_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.precision_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.precision_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.precision_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.precision_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.precision_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.precision_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.precision_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.precision_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.precision_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.precision_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.precision_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.processing_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.processing_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.processing_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.processing_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.processing_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.processing_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.processing_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.processing_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.processing_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.processing_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.processing_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.processing_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.processing_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.processing_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.rain_sensor.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.rain_sensor.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.rain_sensor.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.rain_sensor.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.rain_sensor.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.rain_sensor.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.rain_sensor.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.rain_sensor.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.rain_sensor.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.reassembly_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.reassembly_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.reassembly_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.reassembly_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.reassembly_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.reassembly_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.reassembly_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.reassembly_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.reassembly_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.reassembly_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.reassembly_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.reassembly_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.reassembly_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.reassembly_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.redstone_extractor.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.redstone_extractor.capacity` | `1000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.redstone_extractor.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.redstone_extractor.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.redstone_extractor.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.redstone_extractor.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.redstone_extractor.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.redstone_extractor.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.redstone_extractor.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.reinforced_chest.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.reinforced_chest.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.reinforced_chest.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.reinforced_chest.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.reinforced_chest.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.reinforced_chest.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.reinforced_chest.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.reinforced_chest.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.reinforced_chest.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.reinforced_furnace.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.reinforced_furnace.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.reinforced_furnace.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.reinforced_furnace.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.reinforced_furnace.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.reinforced_furnace.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.reinforced_furnace.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.reinforced_furnace.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.reinforced_furnace.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.reinforced_furnace.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.reinforced_furnace.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.reinforced_furnace.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.reinforced_furnace.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.reinforced_furnace.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.research_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.research_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.research_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.research_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.research_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.research_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.research_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.research_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.research_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.research_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.research_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.research_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.research_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.research_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.restoration_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.restoration_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.restoration_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.restoration_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.restoration_chamber.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.restoration_chamber.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.restoration_chamber.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.restoration_chamber.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.restoration_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.restoration_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.restoration_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.restoration_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.restoration_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.restoration_chamber.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.scarecrow.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.scarecrow.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.scarecrow.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.scarecrow.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.scarecrow.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.scarecrow.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.scarecrow.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.scarecrow.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.scarecrow.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.starch_extractor.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.starch_extractor.capacity` | `1000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.starch_extractor.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.starch_extractor.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.starch_extractor.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.starch_extractor.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.starch_extractor.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.starch_extractor.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.starch_extractor.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.stone_assimilator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.stone_assimilator.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.stone_assimilator.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.stone_assimilator.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.stone_assimilator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.stone_assimilator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.stone_assimilator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.stone_assimilator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.stone_assimilator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.stone_separator.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.stone_separator.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.stone_separator.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.stone_separator.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.stone_separator.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.stone_separator.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.stone_separator.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.stone_separator.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.stone_separator.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.stone_separator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.stone_separator.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.stone_separator.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.stone_separator.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.stone_separator.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.storage_chamber.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.storage_chamber.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.storage_chamber.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.storage_chamber.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.storage_chamber.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.storage_chamber.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.storage_chamber.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.storage_chamber.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.storage_chamber.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.transmitter.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.transmitter.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.transmitter.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.transmitter.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.transmitter.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.transmitter.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.transmitter.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.transmitter.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.transmitter.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.weather_controller.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.weather_controller.capacity` | `1000000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.weather_controller.transferRate` | `64000` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.weather_controller.chargeRate` | `64000` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.weather_controller.energyOverride` | `-1` | -1 … 2147483647 | -1 uses recipe or built-in cost. 0 makes processing free. |
| `machine.weather_controller.ticksOverride` | `-1` | -1 … 1000000 | -1 uses recipe or built-in duration. Positive values override ticks. |
| `machine.weather_controller.energyMultiplier` | `1.0` | 0.0 … 100.0 | Multiplier on applicable processing costs, after the global multiplier. |
| `machine.weather_controller.timeMultiplier` | `1.0` | 0.01 … 100.0 | Multiplier on applicable processing duration, after the global multiplier. |
| `machine.weather_controller.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.weather_controller.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.weather_controller.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.weather_controller.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.weather_controller.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.weather_controller.retainProgressWithoutPower` | `true` | true / false | Keep partial processing progress during a power shortage. |
| `machine.weather_station.enabled` | `true` | true / false | Allow this machine to operate. Existing inventory and block remain recoverable. |
| `machine.weather_station.capacity` | `50000` | 1 … 2147483647 | Maximum stored FE; existing excess is preserved. |
| `machine.weather_station.transferRate` | `3200` | 1 … 2147483647 | Maximum FE per tick per block connection; global positive transfer override takes precedence. |
| `machine.weather_station.chargeRate` | `3200` | 1 … 2147483647 | Maximum FE per tick used for the machine’s battery transfer. |
| `machine.weather_station.defaultRedstoneMode` | `0` | 0 … 3 | 0 always; 1 signal required; 2 signal disables; 3 paused. |
| `machine.weather_station.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-machine long-valued FE capacity multiplier applied after the base capacity. |
| `machine.weather_station.itemAutomation` | `true` | true / false | Allow item capabilities to insert/extract through configured faces. |
| `machine.weather_station.energyInput` | `true` | true / false | Allow external energy insertion through eligible faces. |
| `machine.weather_station.energyOutput` | `true` | true / false | Allow external energy extraction through eligible faces. |
| `machine.advanced_greenhouse.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.advanced_greenhouse.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.advanced_greenhouse.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.advanced_greenhouse.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.advanced_power_cube.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.advanced_power_cube.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.advanced_power_cube.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.advanced_power_cube.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.algorithm_assimilator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.algorithm_assimilator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.algorithm_assimilator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.algorithm_assimilator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.algorithm_separator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.algorithm_separator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.algorithm_separator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.algorithm_separator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.analysing_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.analysing_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.analysing_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.analysing_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.atomic_calculator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.atomic_calculator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.atomic_calculator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.atomic_calculator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.atomic_multiplier.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.atomic_multiplier.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.atomic_multiplier.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.atomic_multiplier.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.basic_greenhouse.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.basic_greenhouse.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.basic_greenhouse.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.basic_greenhouse.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.calculator_locator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.calculator_locator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.calculator_locator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.calculator_locator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.calculator_plug.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.calculator_plug.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.calculator_plug.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.calculator_plug.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.calculator_screen_block.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.calculator_screen_block.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.calculator_screen_block.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.calculator_screen_block.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.co2_generator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.co2_generator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.co2_generator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.co2_generator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.conductor_mast.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.conductor_mast.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.conductor_mast.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.conductor_mast.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.crank_handle.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.crank_handle.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.crank_handle.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.crank_handle.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.creative_power_cube.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.creative_power_cube.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.creative_power_cube.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.creative_power_cube.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.docking_station.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.docking_station.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.docking_station.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.docking_station.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.dynamic_calculator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.dynamic_calculator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.dynamic_calculator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.dynamic_calculator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.extraction_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.extraction_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.extraction_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.extraction_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.fabrication_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.fabrication_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.fabrication_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.fabrication_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.flawless_greenhouse.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.flawless_greenhouse.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.flawless_greenhouse.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.flawless_greenhouse.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.gas_lantern_off.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.gas_lantern_off.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.gas_lantern_off.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.gas_lantern_off.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.glowstone_extractor.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.glowstone_extractor.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.glowstone_extractor.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.glowstone_extractor.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.hand_cranked_generator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.hand_cranked_generator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.hand_cranked_generator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.hand_cranked_generator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.health_processor.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.health_processor.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.health_processor.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.health_processor.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.hunger_processor.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.hunger_processor.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.hunger_processor.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.hunger_processor.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.magnetic_flux.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.magnetic_flux.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.magnetic_flux.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.magnetic_flux.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.module_workstation.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.module_workstation.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.module_workstation.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.module_workstation.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.power_cube.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.power_cube.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.power_cube.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.power_cube.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.precision_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.precision_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.precision_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.precision_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.processing_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.processing_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.processing_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.processing_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.rain_sensor.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.rain_sensor.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.rain_sensor.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.rain_sensor.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.reassembly_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.reassembly_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.reassembly_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.reassembly_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.redstone_extractor.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.redstone_extractor.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.redstone_extractor.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.redstone_extractor.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.reinforced_chest.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.reinforced_chest.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.reinforced_chest.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.reinforced_chest.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.reinforced_furnace.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.reinforced_furnace.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.reinforced_furnace.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.reinforced_furnace.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.research_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.research_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.research_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.research_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.restoration_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.restoration_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.restoration_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.restoration_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.scarecrow.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.scarecrow.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.scarecrow.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.scarecrow.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.starch_extractor.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.starch_extractor.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.starch_extractor.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.starch_extractor.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.stone_assimilator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.stone_assimilator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.stone_assimilator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.stone_assimilator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.stone_separator.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.stone_separator.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.stone_separator.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.stone_separator.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.storage_chamber.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.storage_chamber.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.storage_chamber.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.storage_chamber.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.transmitter.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.transmitter.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.transmitter.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.transmitter.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.weather_controller.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.weather_controller.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.weather_controller.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.weather_controller.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.weather_station.euInputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.weather_station.euOutputVoltage` | `0` | 0 … 2147483647 | 0 inherits the global GregTech setting. |
| `machine.weather_station.euInputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |
| `machine.weather_station.euOutputAmperage` | `0` | 0 … 1024 | 0 inherits the global GregTech setting. |

## content

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `content.advanced_assembly.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.advanced_greenhouse.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.advanced_power_cube.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.advanced_terrain_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.algorithm_assimilator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.algorithm_separator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_leaves.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_log.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_piping.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_planks.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_sapling.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.amethyst_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.analysing_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.atomic_assembly.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.atomic_binder.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.atomic_calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.atomic_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.atomic_multiplier.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.atomic_terrain_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.baby_grenade.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.basic_greenhouse.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.broccoli.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.broccoli_seeds.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.calculator_assembly.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.calculator_locator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.calculator_plug.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.calculator_screen.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.calculator_screen_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_0.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_1.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_10.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_11.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_12.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_13.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_2.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_3.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_4.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_5.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_6.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_7.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_8.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_board_9.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_0.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_1.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_10.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_11.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_12.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_13.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_2.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_3.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_4.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_5.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_6.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_7.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_8.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_damaged_9.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_0.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_1.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_10.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_11.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_12.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_13.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_2.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_3.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_4.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_5.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_6.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_7.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_8.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.circuit_dirty_9.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.clear_stable_glass.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.co2_generator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.coal_dust.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.conductor_mast.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.controlled_fuel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.cooked_broccoli.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.crafting_calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.crank_handle.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.creative_power_cube.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.crop_broccoli.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.crop_fiddledew.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.crop_prunae.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.diamond_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.diamond_leaves.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.diamond_log.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.diamond_planks.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.diamond_sapling.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.diamond_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.docking_station.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.dynamic_calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_diamond.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_diamond_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.electric_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_diamond.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_diamond_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_forged_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_forged_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_forged_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_forged_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.end_forged_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.energy_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.energy_upgrade.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_coal.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_ingot.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.enriched_gold_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.extraction_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fabrication_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fiddledew_fruit.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_coal.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.fire_diamond_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_assembly.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_diamond_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_glass.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.flawless_greenhouse.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.gas_lantern_off.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.glowstone_extractor.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.grenade.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.grenade_casing.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.hand_cranked_generator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.health_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.health_processor.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.hunger_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.hunger_processor.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.info_calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.jump_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.lantern.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.large_amethyst.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.large_tanzanite.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.locator_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.magnetic_flux.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.module_workstation.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.nutrition_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.obsidian_key.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear_leaves.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear_log.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear_planks.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear_sapling.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.pear_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.power_cube.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.precision_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.processing_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.prunae_seeds.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.purified_coal.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.purified_obsidian.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.rain_sensor.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reassembly_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_extractor.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_ingot.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_ingot_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.redstone_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_chest.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_brick.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_brick_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_brick_gate.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_brick_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_gate.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_dirt_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_furnace.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_ingot.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_iron_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_brick.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_brick_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_brick_gate.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_brick_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_gate.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_stone_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.reinforced_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.research_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.restoration_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.rotten_pear.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.scarecrow.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.scientific_calculator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.shard_amethyst.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.shard_tanzanite.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.sickle.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.small_amethyst.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.small_stone.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.small_tanzanite.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.smelting_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.soil.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.speed_upgrade.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_glass.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_black.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_blue.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_brown.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_cyan.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_green.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_light_blue.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_light_grey.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_lime.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_magenta.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_normal.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_orange.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_pink.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_plain.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_purple.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_red.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_black_rimmed_yellow.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_blue.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_brown.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_cyan.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_green.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_light_blue.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_light_grey.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_lime.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_magenta.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_normal.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_orange.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_pink.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_plain.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_purple.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_red.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_black.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_blue.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_brown.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_cyan.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_green.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_light_blue.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_light_grey.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_lime.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_magenta.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_normal.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_orange.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_pink.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_plain.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_purple.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_red.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_rimmed_yellow.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stable_stone_yellow.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.starch_extractor.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stone_assimilator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.stone_separator.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.storage_chamber.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.storage_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_fence.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_leaves.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_log.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_piping.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_planks.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_sapling.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.tanzanite_stairs.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.terrain_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.transfer_upgrade.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.transmitter.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.void_upgrade.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.warp_module.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond_axe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond_block.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond_hoe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond_pickaxe.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond_shovel.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weakened_diamond_sword.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weather_controller.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.weather_station.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |
| `content.wrench.enabled` | `true` | true / false | Disable use, crafting output and recipe processing without removing registry IDs. Reload recipes after changing. |

## module

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `module.calculator.capacity` | `1000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.calculator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.scientific_calculator.capacity` | `2000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.scientific_calculator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.flawless_calculator.capacity` | `1000000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.flawless_calculator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.crafting_calculator.capacity` | `5000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.crafting_calculator.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.terrain_module.capacity` | `400` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.terrain_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.advanced_terrain_module.capacity` | `2000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.advanced_terrain_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.atomic_terrain_module.capacity` | `20000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.atomic_terrain_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.smelting_module.capacity` | `50000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.smelting_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.energy_module.capacity` | `100000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.energy_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.warp_module.capacity` | `10000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.warp_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.jump_module.capacity` | `10000` | 1 … 100000000 | Maximum stored FE; existing excess is preserved. |
| `module.jump_module.capacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Per-item/module long-valued FE capacity multiplier. |
| `module.crafting_calculator.cost` | `1` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.terrain_module.cost` | `1` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.advanced_terrain_module.cost` | `1` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.atomic_terrain_module.cost` | `1` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.smelting_module.cost` | `1000` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.smelting_module.ticks` | `1000` | 1 … 2147483647 | Ticks. See the category rules in CONFIGURATION.md. |
| `module.warp_module.cost` | `1000` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.jump_module.cost` | `100` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.ender_pearl.cost` | `1000` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.grenade.cost` | `10000` | 0 … 2147483647 | Cost. See the category rules in CONFIGURATION.md. |
| `module.end_diamond.cooldown` | `10` | 0 … 2147483647 | Cooldown. See the category rules in CONFIGURATION.md. |
| `module.ender_pearl.cooldown` | `10` | 0 … 2147483647 | Cooldown. See the category rules in CONFIGURATION.md. |
| `module.grenade.cooldown` | `10` | 0 … 2147483647 | Cooldown. See the category rules in CONFIGURATION.md. |
| `module.jump_module.cooldown` | `0` | 0 … 2147483647 | Cooldown. See the category rules in CONFIGURATION.md. |
| `module.warp_module.cooldown` | `0` | 0 … 2147483647 | Cooldown. See the category rules in CONFIGURATION.md. |
| `module.flawless_calculator.maxModules` | `16` | 1 … 16 | Max modules. See the category rules in CONFIGURATION.md. |
| `module.jump_module.velocity` | `1.0` | 0.01 … 4.0 | Velocity. See the category rules in CONFIGURATION.md. |
| `module.ender_pearl.velocity` | `1.5` | 0.1 … 5.0 | Velocity. See the category rules in CONFIGURATION.md. |
| `module.grenade.velocity` | `1.5` | 0.1 … 5.0 | Velocity. See the category rules in CONFIGURATION.md. |
| `module.end_diamond.velocity` | `1.5` | 0.1 … 5.0 | Velocity. See the category rules in CONFIGURATION.md. |
| `module.end_diamond.enabled` | `true` | true / false | Enable this content/recipe. |
| `module.warp_module.crossDimension` | `false` | true / false | Cross dimension. See the category rules in CONFIGURATION.md. |
| `module.warp_module.loadDestination` | `false` | true / false | Load destination. See the category rules in CONFIGURATION.md. |
| `module.warp_module.requireStableBlock` | `true` | true / false | Require stable block. See the category rules in CONFIGURATION.md. |
| `module.atomic_terrain_module.requireReplacementItem` | `true` | true / false | Require replacement item. See the category rules in CONFIGURATION.md. |
| `module.atomic_terrain_module.dropOriginal` | `true` | true / false | Drop original. See the category rules in CONFIGURATION.md. |
| `module.smelting_module.backgroundProcessing` | `true` | true / false | Background processing. See the category rules in CONFIGURATION.md. |

## nutrition

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `nutrition.healthCapacity` | `1000` | 1 … 2147483647 | Health capacity. See the category rules in CONFIGURATION.md. |
| `nutrition.hungerCapacity` | `1000` | 1 … 2147483647 | Hunger capacity. See the category rules in CONFIGURATION.md. |
| `nutrition.combinedHealthCapacity` | `2147483647` | 1 … 2147483647 | Combined health capacity. See the category rules in CONFIGURATION.md. |
| `nutrition.combinedHungerCapacity` | `2147483647` | 1 … 2147483647 | Combined hunger capacity. See the category rules in CONFIGURATION.md. |
| `nutrition.restoreInterval` | `10` | 1 … 2147483647 | Restore interval. See the category rules in CONFIGURATION.md. |
| `nutrition.automaticRestoreLimit` | `2` | 1 … 2147483647 | Automatic restore limit. See the category rules in CONFIGURATION.md. |
| `nutrition.manualRestoreLimit` | `20` | 1 … 2147483647 | Manual restore limit. See the category rules in CONFIGURATION.md. |
| `nutrition.machineTransfer` | `4` | 1 … 2147483647 | Machine transfer. See the category rules in CONFIGURATION.md. |
| `nutrition.networkInterval` | `20` | 1 … 2147483647 | Network interval. See the category rules in CONFIGURATION.md. |
| `nutrition.networkMaxVisited` | `512` | 1 … 65536 | Network max visited. See the category rules in CONFIGURATION.md. |
| `nutrition.leafYield` | `1` | 0 … 64 | Leaf yield. See the category rules in CONFIGURATION.md. |
| `nutrition.foodSaturation` | `0.2` | 0.0 … 20.0 | Food saturation. See the category rules in CONFIGURATION.md. |

## upgrades

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `upgrades.maxPerType` | `16` | 1 … 2147483647 | Max per type. See the category rules in CONFIGURATION.md. |
| `upgrades.itemsPerTransfer` | `4` | 1 … 2147483647 | Items per transfer. See the category rules in CONFIGURATION.md. |
| `upgrades.maxTransfer` | `64` | 1 … 2147483647 | Max transfer. See the category rules in CONFIGURATION.md. |
| `upgrades.speedBonus` | `0.25` | 0.0 … 100.0 | Speed bonus. See the category rules in CONFIGURATION.md. |
| `upgrades.energyDiscount` | `0.1` | 0.0 … 100.0 | Energy discount. See the category rules in CONFIGURATION.md. |
| `upgrades.allowVoid` | `true` | true / false | Allow void. See the category rules in CONFIGURATION.md. |

## generation

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `generation.extractorNutrientCapacity` | `5000` | 1 … 2147483647 | Extractor nutrient capacity. See the category rules in CONFIGURATION.md. |
| `generation.extractorNutrientCost` | `400` | 1 … 2147483647 | Extractor nutrient cost. See the category rules in CONFIGURATION.md. |
| `generation.starchPerTick` | `40` | 0 … 2147483647 | Starch per tick. See the category rules in CONFIGURATION.md. |
| `generation.redstonePerTick` | `80` | 0 … 2147483647 | Redstone per tick. See the category rules in CONFIGURATION.md. |
| `generation.glowstonePerTick` | `160` | 0 … 2147483647 | Glowstone per tick. See the category rules in CONFIGURATION.md. |
| `generation.crankHandleCooldown` | `18` | 1 … 2147483647 | Crank handle cooldown. See the category rules in CONFIGURATION.md. |
| `generation.mastBaseWait` | `1500` | 1 … 2147483647 | Mast base wait. See the category rules in CONFIGURATION.md. |
| `generation.mastMinWait` | `250` | 1 … 2147483647 | Mast min wait. See the category rules in CONFIGURATION.md. |
| `generation.mastWaitReduction` | `135` | 1 … 2147483647 | Mast wait reduction. See the category rules in CONFIGURATION.md. |
| `generation.mastRandomWait` | `300` | 1 … 2147483647 | Mast random wait. See the category rules in CONFIGURATION.md. |
| `generation.mastBurstTicks` | `200` | 1 … 2147483647 | Mast burst ticks. See the category rules in CONFIGURATION.md. |
| `generation.mastBaseGeneration` | `25` | 1 … 2147483647 | Mast base generation. See the category rules in CONFIGURATION.md. |
| `generation.weatherStationBonus` | `5` | 0 … 2147483647 | Weather station bonus. See the category rules in CONFIGURATION.md. |
| `generation.mastRadius` | `20` | 1 … 64 | Mast radius. See the category rules in CONFIGURATION.md. |
| `generation.weatherStationRadius` | `10` | 1 … 64 | Weather station radius. See the category rules in CONFIGURATION.md. |
| `generation.structureCheckInterval` | `25` | 1 … 2147483647 | Structure check interval. See the category rules in CONFIGURATION.md. |
| `generation.locatorMaxRadius` | `11` | 1 … 32 | Locator max radius. See the category rules in CONFIGURATION.md. |
| `generation.locatorStableThreshold` | `7` | 1 … 2147483647 | Locator stable threshold. See the category rules in CONFIGURATION.md. |
| `generation.locatorTimeThreshold` | `5` | 1 … 2147483647 | Locator time threshold. See the category rules in CONFIGURATION.md. |
| `generation.locatorTimeAdvance` | `100` | 0 … 2147483647 | Locator time advance. See the category rules in CONFIGURATION.md. |
| `generation.locatorEffectInterval` | `50` | 1 … 2147483647 | Locator effect interval. See the category rules in CONFIGURATION.md. |
| `generation.locatorEffectDuration` | `1000` | 1 … 2147483647 | Locator effect duration. See the category rules in CONFIGURATION.md. |
| `generation.locatorMultiplier` | `2.0` | 0.0 … 100.0 | Locator multiplier. See the category rules in CONFIGURATION.md. |
| `generation.mastMultiplier` | `4.0` | 0.0 … 100.0 | Mast multiplier. See the category rules in CONFIGURATION.md. |

## greenhouse

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `greenhouse.buildEnergy` | `100` | 0 … 2147483647 | Build energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.plantEnergy` | `50` | 0 … 2147483647 | Plant energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.growEnergy` | `150` | 0 … 2147483647 | Grow energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.harvestEnergy` | `150` | 0 … 2147483647 | Harvest energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.farmlandEnergy` | `50` | 0 … 2147483647 | Farmland energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.waterEnergy` | `1000` | 0 … 2147483647 | Water energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.structureCheckInterval` | `20` | 1 … 2147483647 | Structure check interval. See the category rules in CONFIGURATION.md. |
| `greenhouse.carbonInterval` | `20` | 1 … 2147483647 | Carbon interval. See the category rules in CONFIGURATION.md. |
| `greenhouse.maxFlawlessLength` | `64` | 1 … 64 | Max flawless length. See the category rules in CONFIGURATION.md. |
| `greenhouse.basicPlantInterval` | `60` | 1 … 2147483647 | Basic plant interval. See the category rules in CONFIGURATION.md. |
| `greenhouse.advancedPlantInterval` | `10` | 1 … 2147483647 | Advanced plant interval. See the category rules in CONFIGURATION.md. |
| `greenhouse.flawlessPlantInterval` | `2` | 1 … 2147483647 | Flawless plant interval. See the category rules in CONFIGURATION.md. |
| `greenhouse.lanternCarbon` | `50` | 0 … 2147483647 | Lantern carbon. See the category rules in CONFIGURATION.md. |
| `greenhouse.dayCarbonUse` | `8` | 0 … 2147483647 | Day carbon use. See the category rules in CONFIGURATION.md. |
| `greenhouse.nightCarbonGain` | `2` | 0 … 2147483647 | Night carbon gain. See the category rules in CONFIGURATION.md. |
| `greenhouse.co2FuelTicks` | `10000` | 1 … 2147483647 | Co2 fuel ticks. See the category rules in CONFIGURATION.md. |
| `greenhouse.co2FuelEnergy` | `100000` | 0 … 2147483647 | Co2 fuel energy. See the category rules in CONFIGURATION.md. |
| `greenhouse.controlledMinimumCarbon` | `92000` | 1 … 100000 | Controlled minimum carbon. See the category rules in CONFIGURATION.md. |
| `greenhouse.controlledMaximumCarbon` | `100000` | 1 … 100000 | Controlled maximum carbon. See the category rules in CONFIGURATION.md. |
| `greenhouse.lanternBurnMultiplier` | `10` | 1 … 2147483647 | Lantern burn multiplier. See the category rules in CONFIGURATION.md. |
| `greenhouse.autoWater` | `true` | true / false | Auto water. See the category rules in CONFIGURATION.md. |
| `greenhouse.autoFarmland` | `true` | true / false | Auto farmland. See the category rules in CONFIGURATION.md. |
| `greenhouse.autoPlant` | `true` | true / false | Auto plant. See the category rules in CONFIGURATION.md. |
| `greenhouse.replant` | `true` | true / false | Replant. See the category rules in CONFIGURATION.md. |
| `greenhouse.allowBuild` | `true` | true / false | Allow build. See the category rules in CONFIGURATION.md. |
| `greenhouse.allowDemolish` | `true` | true / false | Allow demolish. See the category rules in CONFIGURATION.md. |
| `greenhouse.acceptGenericPlants` | `true` | true / false | Accept generic plants. See the category rules in CONFIGURATION.md. |
| `greenhouse.basicGrowthBand0` | `400` | 1 … 1000000 | Basic growth band0. See the category rules in CONFIGURATION.md. |
| `greenhouse.basicGrowthBand1` | `300` | 1 … 1000000 | Basic growth band1. See the category rules in CONFIGURATION.md. |
| `greenhouse.basicGrowthBand2` | `200` | 1 … 1000000 | Basic growth band2. See the category rules in CONFIGURATION.md. |
| `greenhouse.basicGrowthBand3` | `150` | 1 … 1000000 | Basic growth band3. See the category rules in CONFIGURATION.md. |
| `greenhouse.basicGrowthBand4` | `80` | 1 … 1000000 | Basic growth band4. See the category rules in CONFIGURATION.md. |
| `greenhouse.advancedGrowthBand0` | `300` | 1 … 1000000 | Advanced growth band0. See the category rules in CONFIGURATION.md. |
| `greenhouse.advancedGrowthBand1` | `200` | 1 … 1000000 | Advanced growth band1. See the category rules in CONFIGURATION.md. |
| `greenhouse.advancedGrowthBand2` | `100` | 1 … 1000000 | Advanced growth band2. See the category rules in CONFIGURATION.md. |
| `greenhouse.advancedGrowthBand3` | `50` | 1 … 1000000 | Advanced growth band3. See the category rules in CONFIGURATION.md. |
| `greenhouse.advancedGrowthBand4` | `15` | 1 … 1000000 | Advanced growth band4. See the category rules in CONFIGURATION.md. |
| `greenhouse.flawlessGrowthBand0` | `200` | 1 … 1000000 | Flawless growth band0. See the category rules in CONFIGURATION.md. |
| `greenhouse.flawlessGrowthBand1` | `100` | 1 … 1000000 | Flawless growth band1. See the category rules in CONFIGURATION.md. |
| `greenhouse.flawlessGrowthBand2` | `50` | 1 … 1000000 | Flawless growth band2. See the category rules in CONFIGURATION.md. |
| `greenhouse.flawlessGrowthBand3` | `25` | 1 … 1000000 | Flawless growth band3. See the category rules in CONFIGURATION.md. |
| `greenhouse.flawlessGrowthBand4` | `15` | 1 … 1000000 | Flawless growth band4. See the category rules in CONFIGURATION.md. |

## world

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `world.weatherEnergyPerTick` | `2500` | 0 … 2147483647 | Weather energy per tick. See the category rules in CONFIGURATION.md. |
| `world.weatherDuration` | `100` | 1 … 2147483647 | Weather duration. See the category rules in CONFIGURATION.md. |
| `world.weatherCooldown` | `30` | 1 … 2147483647 | Weather cooldown. See the category rules in CONFIGURATION.md. |
| `world.weatherHoldTicks` | `12000` | 1 … 2147483647 | Weather hold ticks. See the category rules in CONFIGURATION.md. |
| `world.magnetRadius` | `10` | 1 … 64 | Magnet radius. See the category rules in CONFIGURATION.md. |
| `world.assimilatorInterval` | `30` | 1 … 2147483647 | Assimilator interval. See the category rules in CONFIGURATION.md. |
| `world.assimilatorMinimumLeaves` | `10` | 1 … 2147483647 | Assimilator minimum leaves. See the category rules in CONFIGURATION.md. |
| `world.assimilatorScanRadius` | `2` | 1 … 64 | Assimilator scan radius. See the category rules in CONFIGURATION.md. |
| `world.assimilatorScanHeight` | `7` | 1 … 64 | Assimilator scan height. See the category rules in CONFIGURATION.md. |
| `world.atomicMultiplierTicks` | `1000` | 1 … 2147483647 | Atomic multiplier ticks. See the category rules in CONFIGURATION.md. |
| `world.atomicMultiplierCopies` | `4` | 1 … 64 | Atomic multiplier copies. See the category rules in CONFIGURATION.md. |
| `world.dynamicStructureRadius` | `3` | 1 … 8 | Dynamic structure radius. See the category rules in CONFIGURATION.md. |
| `world.dynamicCheckInterval` | `20` | 1 … 2147483647 | Dynamic check interval. See the category rules in CONFIGURATION.md. |
| `world.dynamicInterval` | `1` | 1 … 2147483647 | Dynamic interval. See the category rules in CONFIGURATION.md. |
| `world.magnetSpeed` | `0.15` | 0.01 … 2.0 | Magnet speed. See the category rules in CONFIGURATION.md. |
| `world.grenadeStrength` | `5.0` | 0.0 … 32.0 | Grenade strength. See the category rules in CONFIGURATION.md. |
| `world.babyGrenadeStrength` | `1.0` | 0.0 … 32.0 | Baby grenade strength. See the category rules in CONFIGURATION.md. |
| `world.locatorExplosionMultiplier` | `1.0` | 0.0 … 8.0 | Locator explosion multiplier. See the category rules in CONFIGURATION.md. |
| `world.chestSounds` | `true` | true / false | Chest sounds. See the category rules in CONFIGURATION.md. |
| `world.grenadeFire` | `true` | true / false | Grenade fire. See the category rules in CONFIGURATION.md. |
| `world.grenadeBlockDamage` | `true` | true / false | Grenade block damage. See the category rules in CONFIGURATION.md. |
| `world.locatorBlockDamage` | `true` | true / false | Locator block damage. See the category rules in CONFIGURATION.md. |
| `world.mastLightningVisual` | `true` | true / false | Mast lightning visual. See the category rules in CONFIGURATION.md. |
| `world.dynamicRequiresStructure` | `true` | true / false | Dynamic requires structure. See the category rules in CONFIGURATION.md. |

## plants

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `plants.leafMatureAge` | `2` | 1 … 4 | Leaf mature age. See the category rules in CONFIGURATION.md. |
| `plants.leafGrowthChance` | `8` | 1 … 2147483647 | Leaf growth chance. See the category rules in CONFIGURATION.md. |
| `plants.leafHarvestResetAge` | `0` | 0 … 4 | Leaf harvest reset age. See the category rules in CONFIGURATION.md. |
| `plants.prunaeMinimumTier` | `2` | 1 … 3 | Prunae minimum tier. See the category rules in CONFIGURATION.md. |
| `plants.fiddledewMinimumTier` | `3` | 1 … 3 | Fiddledew minimum tier. See the category rules in CONFIGURATION.md. |
| `plants.broccoliMinimumTier` | `0` | 0 … 3 | Broccoli minimum tier. See the category rules in CONFIGURATION.md. |
| `plants.diamondSaplingConsumesSupport` | `true` | true / false | Diamond sapling consumes support. See the category rules in CONFIGURATION.md. |
| `plants.diamondSaplingRequiresSupport` | `true` | true / false | Diamond sapling requires support. See the category rules in CONFIGURATION.md. |

## circuits

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `circuits.stabilityRoll` | `6` | 1 … 2147483647 | Stability roll. See the category rules in CONFIGURATION.md. |
| `circuits.energyRoll` | `200` | 1 … 2147483647 | Energy roll. See the category rules in CONFIGURATION.md. |
| `circuits.item1Roll` | `50` | 1 … 2147483647 | Item1 roll. See the category rules in CONFIGURATION.md. |
| `circuits.item2Roll` | `100` | 1 … 2147483647 | Item2 roll. See the category rules in CONFIGURATION.md. |
| `circuits.item3Roll` | `1000` | 1 … 2147483647 | Item3 roll. See the category rules in CONFIGURATION.md. |
| `circuits.item4Roll` | `2000` | 1 … 2147483647 | Item4 roll. See the category rules in CONFIGURATION.md. |
| `circuits.item5Roll` | `10000` | 1 … 2147483647 | Item5 roll. See the category rules in CONFIGURATION.md. |
| `circuits.item6Roll` | `20000` | 1 … 2147483647 | Item6 roll. See the category rules in CONFIGURATION.md. |

## storage

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `storage.reinforcedChestPerBin` | `256` | 1 … 2147483647 | Reinforced chest per bin. See the category rules in CONFIGURATION.md. |
| `storage.circuitChamberPerBin` | `1024` | 1 … 2147483647 | Circuit chamber per bin. See the category rules in CONFIGURATION.md. |
| `storage.assimilatorPerBin` | `64` | 1 … 2147483647 | Assimilator per bin. See the category rules in CONFIGURATION.md. |
| `storage.moduleSlots` | `54` | 1 … 54 | Module slots. See the category rules in CONFIGURATION.md. |

## tools

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `tools.fireSwordSeconds` | `4` | 0 … 2147483647 | Fire sword seconds. See the category rules in CONFIGURATION.md. |
| `tools.obsidianKeyDamage` | `1` | 0 … 2147483647 | Obsidian key damage. See the category rules in CONFIGURATION.md. |
| `tools.wrenchDismantle` | `true` | true / false | Wrench dismantle. See the category rules in CONFIGURATION.md. |
| `tools.wrenchSideCycling` | `true` | true / false | Wrench side cycling. See the category rules in CONFIGURATION.md. |
| `tools.wrenchPreserveMachine` | `true` | true / false | Wrench preserve machine. See the category rules in CONFIGURATION.md. |

## fuel

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `fuel.redstone` | `1000` | 0 … 2147483647 | Redstone. See the category rules in CONFIGURATION.md. |
| `fuel.coal` | `500` | 0 … 2147483647 | Coal. See the category rules in CONFIGURATION.md. |
| `fuel.charcoal` | `500` | 0 … 2147483647 | Charcoal. See the category rules in CONFIGURATION.md. |
| `fuel.coal_block` | `4500` | 0 … 2147483647 | Coal block. See the category rules in CONFIGURATION.md. |
| `fuel.redstone_block` | `9000` | 0 … 2147483647 | Redstone block. See the category rules in CONFIGURATION.md. |

## compat

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `compat.aeEnergyToFE` | `2.0` | 0.0001 … 1000000.0 | Ae energy to fe. See the category rules in CONFIGURATION.md. |
| `compat.jei` | `true` | true / false | Jei. See the category rules in CONFIGURATION.md. |
| `compat.emi` | `true` | true / false | Emi. See the category rules in CONFIGURATION.md. |
| `compat.jade` | `true` | true / false | Jade. See the category rules in CONFIGURATION.md. |
| `compat.crafttweaker` | `true` | true / false | Crafttweaker. See the category rules in CONFIGURATION.md. |
| `compat.ae2` | `true` | true / false | Ae2. See the category rules in CONFIGURATION.md. |
| `compat.mekanism` | `true` | true / false | Mekanism. See the category rules in CONFIGURATION.md. |
| `compat.externalPlants` | `true` | true / false | External plants. See the category rules in CONFIGURATION.md. |
| `compat.gtceu.enabled` | `true` | true / false | Enable this content/recipe. |
| `compat.gtceu.euToFE` | `true` | true / false | Eu to fe. See the category rules in CONFIGURATION.md. |
| `compat.gtceu.feToEU` | `true` | true / false | Fe to eu. See the category rules in CONFIGURATION.md. |
| `compat.gtceu.fePerEU` | `0` | 0 … 1000000 | 0 follows the higher of GregTech FE/EU ratios; positive fixes one symmetric ratio. |
| `compat.gtceu.inputVoltage` | `2147483647` | 1 … 2147483647 | Maximum EU packet voltage. Only complete packets fitting the FE port are accepted; excessive voltage is refused without explosions. |
| `compat.gtceu.outputVoltage` | `32` | 1 … 2147483647 | EU per outgoing packet. Default 32 is LV. Output refuses receivers/cables rated below this voltage. |
| `compat.gtceu.inputAmperage` | `4` | 1 … 1024 | Maximum EU packets accepted per machine per tick across all faces. |
| `compat.gtceu.outputAmperage` | `1` | 1 … 1024 | Maximum EU packets sent per machine per tick across all faces. |
| `compat.gtceu.chargerTier` | `1` | 0 … 14 | Highest GregTech item tier Foundations charging and discharging slots support. 1 is LV. |
| `compat.gtceu.itemTier` | `1` | 0 … 14 | Tier of Foundations powered items exposed to GregTech chargers. |
| `compat.modernIndustrialization` | `true` | true / false | Enable the MI EU integration. Base ratio inherits MI unless power.modernIndustrialization.fePerEU overrides it. |
| `compat.grandPower` | `true` | true / false | Expose and accept long-valued FE through GrandPower when present. |

## research

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `research.requireUnlock` | `true` | true / false | Require unlock. See the category rules in CONFIGURATION.md. |
| `research.consumeSample` | `false` | true / false | Consume sample. See the category rules in CONFIGURATION.md. |
| `research.consumeEnergy` | `true` | true / false | Consume energy. See the category rules in CONFIGURATION.md. |
| `research.shareServerWide` | `false` | true / false | Share server wide. See the category rules in CONFIGURATION.md. |
| `research.mastery.calculator` | `10000` | 1 … 2147483647 | Completed calculation batches needed for the mastery display; does not gate recipes. |
| `research.mastery.scientific` | `5000` | 1 … 2147483647 | Completed calculation batches needed for the mastery display; does not gate recipes. |
| `research.mastery.atomic` | `2500` | 1 … 2147483647 | Completed calculation batches needed for the mastery display; does not gate recipes. |
| `research.mastery.flawless` | `1000` | 1 … 2147483647 | Completed calculation batches needed for the mastery display; does not gate recipes. |
| `research.trackMastery` | `true` | true / false | Record successful basic/scientific/atomic/flawless calculations for the research browser. |

## automation

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `automation.balanceCubes` | `true` | true / false | Balance cubes. See the category rules in CONFIGURATION.md. |
| `automation.allowBatterySlotAccess` | `true` | true / false | Allow battery slot access. See the category rules in CONFIGURATION.md. |
| `automation.allowUpgradeSlotAccess` | `false` | true / false | Allow upgrade slot access. See the category rules in CONFIGURATION.md. |

## power

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `power.routing.preferNative` | `true` | true / false | Prefer the native J/EU/AE item or block adapter over an external FE facade. A selected native port refusing power never falls through to FE. Foundations-to-Foundations links remain FE; GT cable output always uses native voltage-aware packets. |
| `power.diagnostics.enabled` | `true` | true / false | Allow read-only /foundations power reports and the machine Power button. Reports query loaded neighbors only and never execute a transfer. |
| `power.diagnostics.cooldownTicks` | `20` | 1 … 1200 | Minimum ticks between player power reports; also applies to the GUI button. |
| `power.mekanism.joulesPerFE` | `0.0` | 0.0 … 1000000.0 | 0 inherits Mekanism Joules per FE; positive sets one symmetric base ratio. Example 2.5 means 2.5 J per FE. Upstream disabled FE conversion is still respected. Long-valued APIs move whole representable quanta. |
| `power.modernIndustrialization.fePerEU` | `0.0` | 0.0 … 1000000.0 | 0 inherits MI forgeEnergyPerEu; positive sets one symmetric base ratio. Example 4.0 means 4 FE per EU. Arbitrarily precise ratios can exceed representable storage/rate limits; diagnostics identifies blocked plans. |
| `power.fe.blockPorts` | `true` | true / false | Enable this electrical API for Foundations machine connections and native block adapters. Other APIs have independent controls; disabling an API does not disable the physical storage. |
| `power.fe.itemCharging` | `true` | true / false | Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply. |
| `power.fe.input` | `true` | true / false | Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled. |
| `power.fe.output` | `true` | true / false | Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled. |
| `power.gtceu.blockPorts` | `true` | true / false | Enable this electrical API for Foundations machine connections and native block adapters. Other APIs have independent controls; disabling an API does not disable the physical storage. |
| `power.gtceu.itemCharging` | `true` | true / false | Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply. |
| `power.gtceu.input` | `true` | true / false | Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled. |
| `power.gtceu.output` | `true` | true / false | Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled. |
| `power.gtceu.inputLossPercent` | `0` | 0 … 99 | Percent conversion loss entering Foundations FE, after the base ratio. 0 is lossless. Fractional leftovers remain in source; fine ratios and losses may require a larger transfer quantum. Incoming GT packets round FE down. |
| `power.gtceu.outputLossPercent` | `0` | 0 … 99 | Percent conversion loss leaving Foundations FE. 0 is lossless; cannot be negative or 100. Outgoing conversion rounds the FE cost up conservatively. Never increases round-trip energy. |
| `power.mekanism.blockPorts` | `true` | true / false | Enable this electrical API for Foundations machine connections and native block adapters. Other APIs have independent controls; disabling an API does not disable the physical storage. |
| `power.mekanism.itemCharging` | `true` | true / false | Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply. |
| `power.mekanism.input` | `true` | true / false | Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled. |
| `power.mekanism.output` | `true` | true / false | Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled. |
| `power.mekanism.inputLossPercent` | `0` | 0 … 99 | Percent conversion loss entering Foundations FE, after the base ratio. 0 is lossless. Fractional leftovers remain in source; fine ratios and losses may require a larger transfer quantum. Incoming GT packets round FE down. |
| `power.mekanism.outputLossPercent` | `0` | 0 … 99 | Percent conversion loss leaving Foundations FE. 0 is lossless; cannot be negative or 100. Outgoing conversion rounds the FE cost up conservatively. Never increases round-trip energy. |
| `power.modernIndustrialization.blockPorts` | `true` | true / false | Enable this electrical API for Foundations machine connections and native block adapters. Other APIs have independent controls; disabling an API does not disable the physical storage. |
| `power.modernIndustrialization.itemCharging` | `true` | true / false | Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply. |
| `power.modernIndustrialization.input` | `true` | true / false | Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled. |
| `power.modernIndustrialization.output` | `true` | true / false | Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled. |
| `power.modernIndustrialization.inputLossPercent` | `0` | 0 … 99 | Percent conversion loss entering Foundations FE, after the base ratio. 0 is lossless. Fractional leftovers remain in source; fine ratios and losses may require a larger transfer quantum. Incoming GT packets round FE down. |
| `power.modernIndustrialization.outputLossPercent` | `0` | 0 … 99 | Percent conversion loss leaving Foundations FE. 0 is lossless; cannot be negative or 100. Outgoing conversion rounds the FE cost up conservatively. Never increases round-trip energy. |
| `power.ae2.itemCharging` | `true` | true / false | Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply. |
| `power.ae2.input` | `true` | true / false | Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled. |
| `power.ae2.output` | `true` | true / false | Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled. |
| `power.ae2.inputLossPercent` | `0` | 0 … 99 | Percent conversion loss entering Foundations FE, after the base ratio. 0 is lossless. Fractional leftovers remain in source; fine ratios and losses may require a larger transfer quantum. Incoming GT packets round FE down. |
| `power.ae2.outputLossPercent` | `0` | 0 … 99 | Percent conversion loss leaving Foundations FE. 0 is lossless; cannot be negative or 100. Outgoing conversion rounds the FE cost up conservatively. Never increases round-trip energy. |
| `power.grandPower.blockPorts` | `true` | true / false | Enable this electrical API for Foundations machine connections and native block adapters. Other APIs have independent controls; disabling an API does not disable the physical storage. |
| `power.grandPower.itemCharging` | `true` | true / false | Enable this API for item charging/discharging. Existing native item tier, charge-rate and access restrictions still apply. |
| `power.grandPower.input` | `true` | true / false | Allow energy from this API INTO Foundations FE storage. For an external battery this means discharging the battery into Calculator. Legacy direction switches, when present, must also be enabled. |
| `power.grandPower.output` | `true` | true / false | Allow energy from Foundations FE storage OUT to this API. For an external battery this means charging the battery. Legacy direction switches, when present, must also be enabled. |
| `power.grandPower.inputLossPercent` | `0` | 0 … 99 | Percent conversion loss entering Foundations FE, after the base ratio. 0 is lossless. Fractional leftovers remain in source; fine ratios and losses may require a larger transfer quantum. Incoming GT packets round FE down. |
| `power.grandPower.outputLossPercent` | `0` | 0 … 99 | Percent conversion loss leaving Foundations FE. 0 is lossless; cannot be negative or 100. Outgoing conversion rounds the FE cost up conservatively. Never increases round-trip energy. |

## protection

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `protection.requireOwner` | `true` | true / false | World-changing machines require an owner UUID. An authorized player can place or use an ownerless controller. Existing saved owners are retained. False uses the named Foundations fake-player identity, never an unrestricted actor. |
| `protection.allowLegacyPlantCallbacks` | `false` | true / false | Opt-in for opaque external plant/bonemeal callbacks with no declared protection-aware behavior. Only the origin is checked for legacy callbacks; their additional blocks, entities and fluids are the integration responsibility. Prefer protection-aware PlantAdapter methods. Native crops keep their bounded path. |

## performance

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `performance.changeOnlySync` | `true` | true / false | Send periodic block-entity packets only when client-visible fields changed. Chunk-load packets and open menu synchronization remain available. |
| `performance.clientSyncInterval` | `10` | 1 … 200 | Minimum periodic machine state publication interval in ticks. Idle, disabled and redstone-paused machines are still considered. Chest lid transitions publish immediately. |
| `performance.chestRecheckInterval` | `20` | 1 … 1200 | Validate only recorded chest openers at this interval. No world-player scan; empty opener sets do no work. |
| `performance.staggerWork` | `true` | true / false | Distribute greenhouse structure/carbon/plant, dynamic structure, assimilator and sync checks by a stable hash of machine position. Keeps configured cadence; does not throttle energy transfers or processing progress. |
| `performance.cacheMachineRecipes` | `true` | true / false | Cache recipe selection until input counts/components, family/owner, research unlocks, recipe/config generation changes. Commit always revalidates live input allocation. |

## recipe

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `recipe.foundations_calculator:algorithm_separator/0000.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:algorithm_separator/0000.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:algorithm_separator/0000.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:algorithm_separator/0000.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:algorithm_separator/0001.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:algorithm_separator/0001.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:algorithm_separator/0001.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:algorithm_separator/0001.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:algorithm_separator/0002.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:algorithm_separator/0002.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:algorithm_separator/0002.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:algorithm_separator/0002.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:algorithm_separator/0003.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:algorithm_separator/0003.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:algorithm_separator/0003.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:algorithm_separator/0003.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:algorithm_separator/0004.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:algorithm_separator/0004.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:algorithm_separator/0004.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:algorithm_separator/0004.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:algorithm_separator/0005.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:algorithm_separator/0005.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:algorithm_separator/0005.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:algorithm_separator/0005.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/001.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/001.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/001.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/001.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/002.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/002.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/002.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/002.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/003.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/003.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/003.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/003.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/004.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/004.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/004.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/004.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/005.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/005.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/005.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/005.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/006.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/006.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/006.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/006.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/007.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/007.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/007.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/007.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/008.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/008.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/008.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/008.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/009.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/009.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/009.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/009.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/010.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/010.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/010.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/010.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_0/011.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_0/011.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_0/011.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_0/011.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/000.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/000.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/000.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/000.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/001.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/001.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/001.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/001.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/002.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/002.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/002.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/002.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/003.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/003.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/003.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/003.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/004.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/004.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/004.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/004.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/005.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/005.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/005.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/005.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/006.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/006.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/006.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/006.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/007.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/007.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/007.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/007.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/008.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/008.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/008.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/008.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/009.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/009.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/009.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/009.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/010.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/010.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/010.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/010.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/011.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/011.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/011.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/011.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/012.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/012.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/012.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/012.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/013.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/013.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/013.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/013.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/014.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/014.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/014.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/014.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/015.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/015.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/015.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/015.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_1/016.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_1/016.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_1/016.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_1/016.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/017.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/017.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/017.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/017.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/018.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/018.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/018.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/018.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/019.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/019.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/019.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/019.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/020.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/020.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/020.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/020.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/021.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/021.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/021.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/021.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/022.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/022.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/022.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/022.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/023.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/023.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/023.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/023.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/024.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/024.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/024.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/024.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/025.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/025.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/025.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/025.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/026.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/026.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/026.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/026.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/027.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/027.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/027.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/027.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/028.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/028.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/028.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/028.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/029.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/029.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/029.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/029.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/030.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/030.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/030.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/030.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_2/031.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_2/031.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_2/031.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_2/031.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/032.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/032.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/032.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/032.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/033.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/033.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/033.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/033.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/034.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/034.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/034.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/034.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/035.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/035.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/035.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/035.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/036.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/036.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/036.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/036.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/037.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/037.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/037.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/037.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/038.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/038.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/038.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/038.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/039.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/039.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/039.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/039.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/040.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/040.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/040.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/040.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_3/041.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_3/041.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_3/041.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_3/041.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/042.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/042.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/042.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/042.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/043.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/043.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/043.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/043.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/044.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/044.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/044.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/044.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/045.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/045.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/045.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/045.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/046.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/046.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/046.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/046.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/047.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/047.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/047.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/047.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/048.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/048.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/048.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/048.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/049.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/049.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/049.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/049.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/050.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/050.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/050.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/050.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/051.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/051.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/051.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/051.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/052.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/052.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/052.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/052.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/053.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/053.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/053.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/053.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/054.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/054.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/054.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/054.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/055.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/055.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/055.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/055.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/056.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/056.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/056.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/056.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_4/057.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_4/057.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_4/057.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_4/057.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/058.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/058.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/058.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/058.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/059.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/059.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/059.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/059.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/060.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/060.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/060.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/060.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/061.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/061.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/061.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/061.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/062.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/062.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/062.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/062.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/063.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/063.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/063.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/063.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/064.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/064.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/064.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/064.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/065.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/065.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/065.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/065.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/066.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/066.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/066.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/066.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:analysis_5/067.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:analysis_5/067.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:analysis_5/067.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:analysis_5/067.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0074.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0074.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0074.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0074.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0075.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0075.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0075.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0075.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0076.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0076.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0076.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0076.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0077.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0077.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0077.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0077.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0078.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0078.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0078.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0078.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0079.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0079.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0079.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0079.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0080.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0080.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0080.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0080.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0081.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0081.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0081.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0081.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0082.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0082.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0082.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0082.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0083.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0083.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0083.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0083.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0084.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0084.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0084.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0084.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0085.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0085.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0085.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0085.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0086.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0086.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0086.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0086.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0087.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0087.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0087.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0087.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0088.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0088.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0088.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0088.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0089.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0089.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0089.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0089.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0090.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0090.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0090.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0090.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0091.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0091.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0091.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0091.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0092.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0092.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0092.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0092.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0093.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0093.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0093.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0093.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0094.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0094.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0094.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0094.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:atomic/0095.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:atomic/0095.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:atomic/0095.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:atomic/0095.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0096.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0096.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0096.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0096.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0097.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0097.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0097.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0097.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0098.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0098.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0098.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0098.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0099.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0099.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0099.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0099.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0100.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0100.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0100.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0100.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0101.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0101.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0101.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0101.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0102.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0102.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0102.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0102.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0103.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0103.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0103.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0103.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0104.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0104.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0104.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0104.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0105.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0105.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0105.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0105.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0106.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0106.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0106.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0106.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0107.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0107.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0107.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0107.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0108.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0108.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0108.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0108.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0109.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0109.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0109.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0109.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0110.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0110.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0110.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0110.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0111.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0111.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0111.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0111.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0112.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0112.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0112.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0112.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0113.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0113.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0113.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0113.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0114.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0114.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0114.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0114.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0115.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0115.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0115.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0115.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0116.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0116.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0116.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0116.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0117.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0117.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0117.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0117.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0118.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0118.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0118.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0118.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0119.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0119.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0119.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0119.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0120.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0120.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0120.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0120.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0121.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0121.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0121.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0121.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0122.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0122.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0122.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0122.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0123.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0123.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0123.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0123.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0124.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0124.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0124.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0124.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0125.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0125.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0125.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0125.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0126.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0126.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0126.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0126.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0127.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0127.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0127.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0127.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0128.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0128.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0128.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0128.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0129.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0129.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0129.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0129.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0130.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0130.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0130.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0130.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0131.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0131.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0131.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0131.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0132.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0132.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0132.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0132.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0133.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0133.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0133.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0133.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0134.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0134.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0134.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0134.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0135.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0135.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0135.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0135.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0136.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0136.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0136.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0136.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0137.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0137.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0137.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0137.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0138.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0138.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0138.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0138.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0139.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0139.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0139.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0139.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0140.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0140.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0140.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0140.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0141.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0141.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0141.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0141.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0142.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0142.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0142.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0142.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0143.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0143.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0143.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0143.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0144.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0144.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0144.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0144.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0145.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0145.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0145.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0145.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0146.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0146.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0146.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0146.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0147.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0147.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0147.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0147.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0148.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0148.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0148.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0148.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0149.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0149.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0149.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0149.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0150.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0150.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0150.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0150.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0151.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0151.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0151.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0151.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0152.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0152.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0152.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0152.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0153.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0153.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0153.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0153.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0154.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0154.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0154.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0154.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0155.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0155.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0155.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0155.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0156.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0156.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0156.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0156.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0157.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0157.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0157.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0157.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0158.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0158.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0158.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0158.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0159.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0159.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0159.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0159.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0160.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0160.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0160.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0160.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0161.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0161.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0161.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0161.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0162.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0162.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0162.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0162.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0163.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0163.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0163.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0163.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0164.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0164.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0164.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0164.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0165.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0165.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0165.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0165.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0166.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0166.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0166.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0166.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0167.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0167.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0167.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0167.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0168.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0168.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0168.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0168.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0169.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0169.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0169.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0169.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0170.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0170.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0170.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0170.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0171.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0171.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0171.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0171.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0172.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0172.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0172.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0172.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0173.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0173.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0173.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0173.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0174.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0174.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0174.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0174.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0175.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0175.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0175.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0175.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0176.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0176.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0176.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0176.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0177.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0177.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0177.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0177.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0178.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0178.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0178.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0178.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0179.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0179.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0179.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0179.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0180.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0180.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0180.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0180.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0181.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0181.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0181.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0181.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0182.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0182.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0182.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0182.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0183.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0183.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0183.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0183.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0184.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0184.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0184.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0184.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0185.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0185.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0185.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0185.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0186.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0186.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0186.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0186.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0187.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0187.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0187.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0187.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0188.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0188.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0188.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0188.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0189.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0189.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0189.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0189.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0190.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0190.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0190.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0190.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0191.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0191.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0191.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0191.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0192.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0192.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0192.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0192.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0193.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0193.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0193.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0193.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0194.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0194.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0194.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0194.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0195.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0195.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0195.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0195.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0196.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0196.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0196.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0196.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0197.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0197.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0197.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0197.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0198.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0198.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0198.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0198.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0199.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0199.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0199.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0199.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0200.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0200.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0200.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0200.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0201.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0201.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0201.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0201.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0202.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0202.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0202.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0202.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0203.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0203.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0203.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0203.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0204.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0204.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0204.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0204.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0205.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0205.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0205.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0205.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0206.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0206.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0206.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0206.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0207.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0207.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0207.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0207.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0208.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0208.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0208.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0208.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0209.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0209.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0209.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0209.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0210.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0210.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0210.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0210.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0211.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0211.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0211.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0211.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0212.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0212.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0212.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0212.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0213.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0213.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0213.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0213.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0214.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0214.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0214.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0214.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0215.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0215.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0215.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0215.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0216.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0216.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0216.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0216.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0217.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0217.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0217.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0217.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0218.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0218.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0218.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0218.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0219.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0219.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0219.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0219.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0220.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0220.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0220.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0220.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0221.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0221.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0221.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0221.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0222.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0222.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0222.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0222.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0223.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0223.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0223.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0223.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0224.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0224.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0224.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0224.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0225.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0225.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0225.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0225.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0226.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0226.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0226.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0226.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0227.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0227.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0227.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0227.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0228.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0228.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0228.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0228.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0229.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0229.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0229.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0229.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0230.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0230.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0230.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0230.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0231.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0231.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0231.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0231.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0232.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0232.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0232.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0232.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0233.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0233.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0233.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0233.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0234.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0234.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0234.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0234.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0235.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0235.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0235.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0235.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0236.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0236.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0236.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0236.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0237.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0237.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0237.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0237.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0238.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0238.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0238.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0238.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0239.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0239.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0239.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0239.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0240.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0240.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0240.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0240.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0241.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0241.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0241.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0241.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0242.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0242.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0242.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0242.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0243.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0243.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0243.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0243.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0244.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0244.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0244.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0244.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0245.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0245.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0245.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0245.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0246.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0246.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0246.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0246.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0247.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0247.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0247.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0247.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0248.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0248.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0248.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0248.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0249.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0249.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0249.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0249.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0250.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0250.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0250.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0250.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0251.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0251.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0251.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0251.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0252.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0252.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0252.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0252.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0253.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0253.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0253.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0253.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0254.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0254.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0254.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0254.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0255.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0255.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0255.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0255.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0256.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0256.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0256.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0256.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0257.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0257.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0257.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0257.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0258.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0258.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0258.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0258.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0259.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0259.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0259.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0259.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0260.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0260.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0260.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0260.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0261.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0261.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0261.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0261.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0262.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0262.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0262.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0262.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0263.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0263.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0263.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0263.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0264.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0264.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0264.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0264.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0265.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0265.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0265.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0265.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0266.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0266.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0266.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0266.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0267.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0267.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0267.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0267.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0268.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0268.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0268.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0268.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0269.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0269.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0269.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0269.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0270.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0270.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0270.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0270.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0271.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0271.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0271.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0271.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0272.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0272.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0272.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0272.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0273.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0273.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0273.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0273.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0274.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0274.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0274.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0274.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0275.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0275.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0275.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0275.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0276.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0276.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0276.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0276.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0277.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0277.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0277.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0277.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0278.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0278.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0278.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0278.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0279.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0279.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0279.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0279.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0280.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0280.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0280.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0280.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0281.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0281.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0281.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0281.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0282.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0282.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0282.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0282.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0283.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0283.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0283.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0283.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0284.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0284.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0284.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0284.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0285.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0285.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0285.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0285.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0286.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0286.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0286.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0286.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0287.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0287.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0287.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0287.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0288.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0288.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0288.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0288.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0289.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0289.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0289.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0289.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0290.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0290.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0290.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0290.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0291.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0291.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0291.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0291.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0292.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0292.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0292.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0292.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0293.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0293.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0293.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0293.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0294.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0294.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0294.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0294.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0295.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0295.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0295.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0295.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0296.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0296.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0296.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0296.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0297.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0297.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0297.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0297.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0298.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0298.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0298.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0298.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0299.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0299.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0299.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0299.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0300.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0300.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0300.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0300.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0301.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0301.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0301.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0301.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0302.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0302.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0302.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0302.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0303.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0303.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0303.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0303.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0304.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0304.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0304.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0304.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0305.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0305.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0305.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0305.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0306.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0306.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0306.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0306.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0307.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0307.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0307.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0307.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0308.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0308.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0308.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0308.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0309.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0309.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0309.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0309.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0310.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0310.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0310.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0310.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0311.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0311.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0311.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0311.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0312.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0312.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0312.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0312.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0313.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0313.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0313.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0313.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0314.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0314.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0314.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0314.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0315.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0315.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0315.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0315.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0316.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0316.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0316.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0316.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0317.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0317.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0317.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0317.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0318.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0318.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0318.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0318.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0319.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0319.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0319.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0319.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0320.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0320.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0320.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0320.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0321.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0321.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0321.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0321.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0322.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0322.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0322.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0322.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0323.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0323.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0323.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0323.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0324.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0324.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0324.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0324.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0325.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0325.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0325.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0325.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0326.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0326.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0326.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0326.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0327.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0327.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0327.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0327.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0328.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0328.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0328.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0328.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0329.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0329.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0329.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0329.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0330.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0330.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0330.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0330.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0331.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0331.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0331.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0331.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0332.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0332.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0332.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0332.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0333.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0333.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0333.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0333.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0334.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0334.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0334.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0334.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0335.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0335.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0335.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0335.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0336.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0336.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0336.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0336.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0337.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0337.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0337.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0337.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0338.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0338.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0338.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0338.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0339.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0339.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0339.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0339.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0340.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0340.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0340.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0340.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0341.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0341.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0341.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0341.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0342.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0342.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0342.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0342.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0343.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0343.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0343.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0343.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0344.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0344.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0344.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0344.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0345.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0345.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0345.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0345.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0346.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0346.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0346.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0346.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0347.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0347.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0347.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0347.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0348.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0348.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0348.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0348.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0349.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0349.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0349.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0349.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0350.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0350.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0350.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0350.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0351.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0351.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0351.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0351.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0352.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0352.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0352.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0352.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0353.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0353.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0353.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0353.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0354.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0354.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0354.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0354.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0355.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0355.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0355.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0355.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0356.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0356.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0356.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0356.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0357.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0357.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0357.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0357.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0358.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0358.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0358.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0358.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0359.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0359.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0359.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0359.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0360.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0360.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0360.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0360.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0361.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0361.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0361.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0361.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0362.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0362.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0362.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0362.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0363.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0363.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0363.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0363.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0364.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0364.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0364.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0364.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0365.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0365.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0365.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0365.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0366.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0366.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0366.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0366.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0367.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0367.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0367.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0367.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0368.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0368.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0368.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0368.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0369.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0369.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0369.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0369.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0370.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0370.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0370.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0370.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0371.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0371.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0371.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0371.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0372.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0372.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0372.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0372.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0373.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0373.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0373.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0373.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0374.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0374.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0374.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0374.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0375.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0375.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0375.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0375.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0376.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0376.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0376.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0376.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0377.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0377.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0377.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0377.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0378.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0378.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0378.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0378.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0379.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0379.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0379.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0379.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0380.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0380.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0380.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0380.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0381.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0381.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0381.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0381.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0382.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0382.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0382.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0382.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0383.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0383.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0383.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0383.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0384.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0384.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0384.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0384.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0385.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0385.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0385.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0385.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0386.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0386.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0386.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0386.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0387.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0387.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0387.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0387.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0388.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0388.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0388.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0388.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0389.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0389.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0389.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0389.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0390.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0390.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0390.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0390.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0391.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0391.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0391.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0391.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0392.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0392.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0392.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0392.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0393.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0393.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0393.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0393.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0394.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0394.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0394.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0394.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0395.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0395.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0395.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0395.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0396.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0396.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0396.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0396.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0397.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0397.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0397.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0397.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0398.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0398.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0398.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0398.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0399.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0399.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0399.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0399.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0400.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0400.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0400.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0400.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0401.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0401.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0401.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0401.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0402.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0402.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0402.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0402.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0403.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0403.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0403.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0403.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0404.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0404.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0404.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0404.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0405.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0405.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0405.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0405.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0406.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0406.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0406.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0406.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0407.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0407.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0407.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0407.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0408.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0408.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0408.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0408.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0409.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0409.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0409.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0409.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0410.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0410.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0410.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0410.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0411.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0411.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0411.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0411.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0412.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0412.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0412.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0412.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0413.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0413.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0413.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0413.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0414.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0414.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0414.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0414.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0415.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0415.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0415.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0415.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0416.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0416.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0416.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0416.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0417.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0417.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0417.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0417.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0418.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0418.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0418.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0418.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0419.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0419.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0419.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0419.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0420.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0420.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0420.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0420.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0421.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0421.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0421.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0421.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0422.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0422.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0422.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0422.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0423.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0423.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0423.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0423.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0424.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0424.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0424.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0424.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0425.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0425.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0425.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0425.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0426.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0426.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0426.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0426.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0427.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0427.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0427.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0427.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0428.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0428.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0428.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0428.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0429.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0429.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0429.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0429.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0430.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0430.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0430.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0430.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0431.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0431.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0431.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0431.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0432.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0432.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0432.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0432.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0433.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0433.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0433.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0433.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0434.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0434.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0434.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0434.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0435.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0435.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0435.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0435.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0436.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0436.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0436.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0436.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0437.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0437.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0437.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0437.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0438.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0438.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0438.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0438.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0439.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0439.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0439.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0439.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0440.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0440.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0440.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0440.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0441.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0441.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0441.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0441.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0442.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0442.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0442.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0442.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0443.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0443.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0443.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0443.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0444.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0444.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0444.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0444.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0445.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0445.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0445.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0445.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0446.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0446.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0446.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0446.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0447.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0447.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0447.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0447.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0448.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0448.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0448.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0448.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0449.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0449.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0449.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0449.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0450.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0450.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0450.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0450.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0451.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0451.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0451.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0451.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0452.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0452.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0452.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0452.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0453.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0453.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0453.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0453.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0454.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0454.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0454.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0454.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0455.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0455.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0455.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0455.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0456.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0456.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0456.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0456.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0457.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0457.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0457.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0457.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0458.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0458.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0458.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0458.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0459.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0459.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0459.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0459.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0460.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0460.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0460.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0460.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0461.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0461.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0461.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0461.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0462.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0462.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0462.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0462.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0463.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0463.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0463.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0463.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0464.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0464.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0464.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0464.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0465.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0465.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0465.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0465.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0466.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0466.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0466.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0466.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0467.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0467.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0467.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0467.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0468.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0468.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0468.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0468.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0469.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0469.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0469.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0469.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0470.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0470.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0470.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0470.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0471.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0471.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0471.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0471.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0472.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0472.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0472.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0472.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0473.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0473.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0473.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0473.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0474.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0474.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0474.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0474.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0475.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0475.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0475.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0475.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0476.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0476.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0476.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0476.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0477.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0477.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0477.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0477.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0478.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0478.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0478.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0478.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0479.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0479.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0479.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0479.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0480.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0480.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0480.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0480.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0481.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0481.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0481.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0481.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0482.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0482.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0482.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0482.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0483.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0483.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0483.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0483.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0484.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0484.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0484.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0484.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0485.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0485.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0485.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0485.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0486.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0486.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0486.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0486.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0487.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0487.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0487.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0487.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0488.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0488.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0488.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0488.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0489.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0489.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0489.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0489.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:calculator/0490.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:calculator/0490.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:calculator/0490.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:calculator/0490.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:conductor_mast/0491.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:conductor_mast/0491.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:conductor_mast/0491.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:conductor_mast/0491.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:conductor_mast/0492.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:conductor_mast/0492.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:conductor_mast/0492.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:conductor_mast/0492.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:conductor_mast/0493.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:conductor_mast/0493.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:conductor_mast/0493.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:conductor_mast/0493.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:crafting/000_reinforced_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/001_reinforced_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/002_reinforced_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/003_reinforced_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/004_reinforced_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/005_reinforced_iron_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/006_reinforced_iron_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/007_reinforced_iron_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/008_reinforced_iron_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/009_reinforced_iron_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/010_redstone_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/011_redstone_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/012_redstone_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/013_redstone_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/014_redstone_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/015_enriched_gold_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/016_enriched_gold_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/017_enriched_gold_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/018_enriched_gold_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/019_enriched_gold_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/020_weakened_diamond_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/021_weakened_diamond_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/022_weakened_diamond_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/023_weakened_diamond_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/024_weakened_diamond_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/025_flawless_diamond_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/026_flawless_diamond_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/027_flawless_diamond_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/028_flawless_diamond_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/029_flawless_diamond_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/030_fire_diamond_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/031_fire_diamond_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/032_fire_diamond_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/033_fire_diamond_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/034_fire_diamond_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/035_electric_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/036_electric_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/037_electric_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/038_electric_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/039_electric_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/040_end_forged_axe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/041_end_forged_pickaxe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/042_end_forged_shovel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/043_end_forged_hoe.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/044_end_forged_sword.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/045_calculator_screen.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/046_atomic_assembly.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/047_atomic_binder.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/048_info_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/049_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/050_crafting_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/051_scientific_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/052_atomic_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/053_dynamic_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/054_flawless_calculator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/055_hunger_module.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/056_health_module.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/057_power_cube.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/058_advanced_power_cube.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/059_basic_greenhouse.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/060_advanced_greenhouse.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/061_flawless_greenhouse.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/062_hunger_processor.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/063_health_processor.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/064_analysing_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/065_fabrication_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/066_atomic_multiplier.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/067_crank_handle.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/068_hand_cranked_generator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/069_calculator_locator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/070_calculator_plug.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/071_stone_separator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/072_extraction_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/073_restoration_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/074_reassembly_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/075_precision_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/076_reinforced_furnace.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/077_reinforced_chest.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/078_grenade_casing.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/079_obsidian_key.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/080_large_amethyst.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/081_small_amethyst.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/082_large_tanzanite.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/083_small_tanzanite.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/084_amethyst_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/085_large_amethyst.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/086_tanzanite_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/087_large_tanzanite.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/088_enriched_gold_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/089_enriched_gold_ingot.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/090_reinforced_iron_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/091_reinforced_iron_ingot.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/092_weakened_diamond_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/093_weakened_diamond.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/094_flawless_diamond_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/095_flawless_diamond.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/096_fire_diamond_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/097_fire_diamond.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/098_electric_diamond_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/099_electric_diamond.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/100_end_diamond_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/101_end_diamond.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/102_redstone_ingot_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/103_redstone_ingot.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/104_amethyst_planks.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/105_tanzanite_planks.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/106_pear_planks.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/107_diamond_planks.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/108_amethyst_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/109_tanzanite_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/110_pear_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/111_diamond_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/112_amethyst_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/113_tanzanite_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/114_pear_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/115_diamond_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/116_docking_station.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/117_weather_controller.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/118_stone_assimilator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/119_algorithm_assimilator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/120_calculator_assembly.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/121_advanced_assembly.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/122_atomic_module.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/123_algorithm_separator.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/124_flawless_assembly.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/125_module_workstation.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/126_reinforced_stone_brick.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/127_stable_stone_normal.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/128_stable_glass.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/129_clear_stable_glass.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/130_reinforced_stone_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/131_reinforced_stone_brick_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/132_reinforced_dirt_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/133_reinforced_dirt_brick_stairs.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/134_reinforced_stone_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/135_reinforced_stone_brick_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/136_reinforced_dirt_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/137_reinforced_dirt_brick_fence.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/138_reinforced_stone_gate.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/139_reinforced_stone_brick_gate.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/140_reinforced_dirt_gate.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/141_reinforced_dirt_brick_gate.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/142_stable_stone_normal.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/143_stable_stone_rimmed_normal.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/144_stable_stone_black_rimmed_normal.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/145_stable_stone_normal.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/146_stable_stone_black.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/147_stable_stone_rimmed_black.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/148_stable_stone_black_rimmed_black.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/149_stable_stone_black.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/150_stable_stone_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/151_stable_stone_rimmed_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/152_stable_stone_black_rimmed_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/153_stable_stone_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/154_stable_stone_brown.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/155_stable_stone_rimmed_brown.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/156_stable_stone_black_rimmed_brown.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/157_stable_stone_brown.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/158_stable_stone_cyan.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/159_stable_stone_rimmed_cyan.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/160_stable_stone_black_rimmed_cyan.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/161_stable_stone_cyan.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/162_stable_stone_green.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/163_stable_stone_rimmed_green.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/164_stable_stone_black_rimmed_green.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/165_stable_stone_green.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/166_stable_stone_light_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/167_stable_stone_rimmed_light_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/168_stable_stone_black_rimmed_light_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/169_stable_stone_light_blue.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/170_stable_stone_light_grey.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/171_stable_stone_rimmed_light_grey.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/172_stable_stone_black_rimmed_light_grey.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/173_stable_stone_light_grey.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/174_stable_stone_lime.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/175_stable_stone_rimmed_lime.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/176_stable_stone_black_rimmed_lime.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/177_stable_stone_lime.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/178_stable_stone_magenta.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/179_stable_stone_rimmed_magenta.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/180_stable_stone_black_rimmed_magenta.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/181_stable_stone_magenta.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/182_stable_stone_orange.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/183_stable_stone_rimmed_orange.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/184_stable_stone_black_rimmed_orange.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/185_stable_stone_orange.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/186_stable_stone_pink.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/187_stable_stone_rimmed_pink.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/188_stable_stone_black_rimmed_pink.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/189_stable_stone_pink.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/190_stable_stone_plain.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/191_stable_stone_rimmed_plain.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/192_stable_stone_black_rimmed_plain.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/193_stable_stone_plain.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/194_stable_stone_purple.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/195_stable_stone_rimmed_purple.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/196_stable_stone_black_rimmed_purple.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/197_stable_stone_purple.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/198_stable_stone_red.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/199_stable_stone_rimmed_red.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/200_stable_stone_black_rimmed_red.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/201_stable_stone_red.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/202_stable_stone_yellow.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/203_stable_stone_rimmed_yellow.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/204_stable_stone_black_rimmed_yellow.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/205_stable_stone_yellow.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/r4_atomic_terrain_module.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/r4_research_chamber.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:crafting/r4_smelting_module.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:extraction_chamber/0494.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:extraction_chamber/0494.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:extraction_chamber/0494.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:extraction_chamber/0494.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:extraction_chamber/0495.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:extraction_chamber/0495.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:extraction_chamber/0495.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:extraction_chamber/0495.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0496.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0496.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0496.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0496.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0497.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0497.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0497.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0497.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0498.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0498.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0498.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0498.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0499.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0499.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0499.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0499.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0500.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0500.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0500.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0500.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0501.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0501.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0501.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0501.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0502.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0502.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0502.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0502.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0503.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0503.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0503.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0503.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0504.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0504.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0504.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0504.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0505.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0505.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0505.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0505.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0506.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0506.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0506.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0506.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:fabrication_chamber/0507.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:fabrication_chamber/0507.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:fabrication_chamber/0507.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:fabrication_chamber/0507.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0508.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0508.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0508.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0508.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0509.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0509.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0509.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0509.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0510.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0510.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0510.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0510.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0511.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0511.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0511.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0511.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0512.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0512.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0512.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0512.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0513.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0513.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0513.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0513.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0514.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0514.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0514.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0514.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0515.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0515.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0515.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0515.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0516.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0516.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0516.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0516.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0517.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0517.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0517.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0517.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0518.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0518.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0518.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0518.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0519.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0519.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0519.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0519.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:flawless/0520.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:flawless/0520.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:flawless/0520.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:flawless/0520.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:glowstone_extractor/0521.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:glowstone_extractor/0521.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:glowstone_extractor/0521.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:glowstone_extractor/0521.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:glowstone_extractor/0522.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:glowstone_extractor/0522.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:glowstone_extractor/0522.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:glowstone_extractor/0522.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:glowstone_extractor/0523.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:glowstone_extractor/0523.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:glowstone_extractor/0523.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:glowstone_extractor/0523.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:harvest/0658.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:harvest/0658.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:harvest/0658.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:harvest/0658.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:harvest/0659.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:harvest/0659.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:harvest/0659.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:harvest/0659.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0524.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0524.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0524.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0524.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0525.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0525.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0525.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0525.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0526.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0526.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0526.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0526.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0527.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0527.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0527.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0527.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0528.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0528.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0528.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0528.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0529.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0529.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0529.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0529.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0530.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0530.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0530.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0530.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0531.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0531.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0531.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0531.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0532.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0532.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0532.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0532.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0533.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0533.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0533.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0533.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0534.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0534.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0534.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0534.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0535.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0535.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0535.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0535.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0536.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0536.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0536.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0536.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0537.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0537.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0537.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0537.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0538.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0538.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0538.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0538.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0539.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0539.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0539.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0539.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0540.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0540.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0540.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0540.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0541.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0541.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0541.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0541.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:health_processor/0542.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:health_processor/0542.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:health_processor/0542.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:health_processor/0542.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0543.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0543.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0543.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0543.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0544.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0544.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0544.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0544.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0545.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0545.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0545.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0545.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0546.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0546.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0546.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0546.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0547.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0547.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0547.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0547.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0548.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0548.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0548.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0548.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0549.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0549.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0549.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0549.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0550.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0550.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0550.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0550.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0551.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0551.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0551.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0551.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0552.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0552.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0552.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0552.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0553.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0553.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0553.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0553.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0554.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0554.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0554.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0554.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0555.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0555.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0555.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0555.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0556.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0556.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0556.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0556.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0557.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0557.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0557.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0557.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:precision_chamber/0558.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:precision_chamber/0558.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:precision_chamber/0558.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:precision_chamber/0558.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0559.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0559.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0559.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0559.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0560.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0560.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0560.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0560.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0561.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0561.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0561.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0561.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0562.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0562.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0562.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0562.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0563.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0563.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0563.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0563.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0564.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0564.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0564.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0564.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0565.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0565.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0565.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0565.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0566.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0566.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0566.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0566.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0567.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0567.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0567.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0567.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0568.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0568.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0568.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0568.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0569.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0569.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0569.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0569.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0570.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0570.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0570.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0570.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0571.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0571.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0571.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0571.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0572.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0572.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0572.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0572.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0573.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0573.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0573.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0573.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0574.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0574.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0574.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0574.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0575.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0575.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0575.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0575.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0576.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0576.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0576.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0576.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0577.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0577.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0577.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0577.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0578.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0578.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0578.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0578.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0579.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0579.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0579.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0579.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0580.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0580.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0580.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0580.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0581.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0581.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0581.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0581.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0582.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0582.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0582.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0582.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0583.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0583.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0583.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0583.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0584.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0584.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0584.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0584.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0585.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0585.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0585.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0585.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:processing_chamber/0586.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:processing_chamber/0586.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:processing_chamber/0586.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:processing_chamber/0586.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0587.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0587.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0587.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0587.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0588.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0588.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0588.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0588.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0589.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0589.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0589.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0589.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0590.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0590.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0590.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0590.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0591.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0591.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0591.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0591.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0592.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0592.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0592.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0592.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0593.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0593.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0593.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0593.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0594.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0594.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0594.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0594.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0595.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0595.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0595.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0595.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0596.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0596.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0596.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0596.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0597.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0597.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0597.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0597.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0598.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0598.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0598.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0598.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0599.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0599.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0599.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0599.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:reassembly_chamber/0600.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:reassembly_chamber/0600.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:reassembly_chamber/0600.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:reassembly_chamber/0600.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:redstone_extractor/0601.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:redstone_extractor/0601.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:redstone_extractor/0601.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:redstone_extractor/0601.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:redstone_extractor/0602.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:redstone_extractor/0602.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:redstone_extractor/0602.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:redstone_extractor/0602.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:redstone_extractor/0603.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:redstone_extractor/0603.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:redstone_extractor/0603.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:redstone_extractor/0603.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:redstone_extractor/0604.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:redstone_extractor/0604.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:redstone_extractor/0604.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:redstone_extractor/0604.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/cactus.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/cactus.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/cactus.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/cactus.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/carpet.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/carpet.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/carpet.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/carpet.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/clay_block.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/clay_block.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/clay_block.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/clay_block.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/cobblestone.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/cobblestone.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/cobblestone.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/cobblestone.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/crop.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/crop.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/crop.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/crop.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/dirt.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/dirt.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/dirt.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/dirt.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/dye.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/dye.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/dye.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/dye.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/emerald.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/emerald.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/emerald.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/emerald.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/end.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/end.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/end.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/end.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/flower.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/flower.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/flower.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/flower.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/foliage.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/foliage.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/foliage.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/foliage.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/furnace.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/furnace.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/furnace.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/furnace.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/glass.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/glass.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/glass.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/glass.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/glass_pane.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/glass_pane.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/glass_pane.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/glass_pane.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/grass.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/grass.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/grass.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/grass.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/gravel.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/gravel.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/gravel.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/gravel.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/iron.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/iron.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/iron.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/iron.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/leaves.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/leaves.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/leaves.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/leaves.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/nether.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/nether.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/nether.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/nether.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/planks.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/planks.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/planks.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/planks.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/reinforced_stone.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/reinforced_stone.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/reinforced_stone.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/reinforced_stone.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/sand.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/sand.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/sand.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/sand.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/sandstone.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/sandstone.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/sandstone.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/sandstone.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/sapling.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/sapling.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/sapling.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/sapling.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/slimeball.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/slimeball.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/slimeball.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/slimeball.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/stone.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/stone.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/stone.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/stone.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/wood.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/wood.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/wood.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/wood.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:research/wool.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:research/wool.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:research/wool.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:research/wool.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0605.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0605.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0605.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0605.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0606.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0606.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0606.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0606.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0607.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0607.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0607.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0607.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0608.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0608.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0608.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0608.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0609.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0609.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0609.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0609.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0610.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0610.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0610.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0610.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0611.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0611.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0611.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0611.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0612.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0612.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0612.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0612.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0613.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0613.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0613.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0613.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0614.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0614.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0614.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0614.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0615.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0615.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0615.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0615.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0616.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0616.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0616.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0616.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0617.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0617.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0617.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0617.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:restoration_chamber/0618.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:restoration_chamber/0618.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:restoration_chamber/0618.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:restoration_chamber/0618.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0619.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0619.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0619.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0619.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0620.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0620.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0620.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0620.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0621.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0621.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0621.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0621.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0622.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0622.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0622.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0622.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0623.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0623.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0623.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0623.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0624.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0624.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0624.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0624.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0625.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0625.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0625.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0625.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0626.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0626.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0626.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0626.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0627.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0627.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0627.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0627.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0628.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0628.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0628.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0628.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0629.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0629.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0629.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0629.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:scientific/0630.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:scientific/0630.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:scientific/0630.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:scientific/0630.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:smelt_broccoli.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:smelt_enriched_gold.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0631.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0631.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0631.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0631.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0632.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0632.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0632.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0632.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0633.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0633.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0633.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0633.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0634.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0634.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0634.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0634.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0635.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0635.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0635.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0635.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0636.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0636.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0636.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0636.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0637.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0637.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0637.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0637.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0638.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0638.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0638.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0638.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0639.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0639.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0639.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0639.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0640.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0640.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0640.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0640.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0641.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0641.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0641.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0641.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0642.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0642.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0642.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0642.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0643.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0643.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0643.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0643.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0644.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0644.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0644.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0644.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:starch_extractor/0645.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:starch_extractor/0645.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:starch_extractor/0645.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:starch_extractor/0645.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0646.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0646.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0646.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0646.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0647.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0647.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0647.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0647.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0648.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0648.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0648.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0648.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0649.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0649.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0649.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0649.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0650.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0650.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0650.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0650.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0651.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0651.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0651.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0651.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0652.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0652.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0652.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0652.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0653.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0653.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0653.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0653.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0654.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0654.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0654.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0654.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0655.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0655.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0655.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0655.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0656.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0656.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0656.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0656.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |
| `recipe.foundations_calculator:stone_separator/0657.enabled` | `true` | true / false | Enable this content/recipe. |
| `recipe.foundations_calculator:stone_separator/0657.energyOverride` | `-1` | -1 … 2147483647 | -1: recipe/default FE; 0 or more: replacement FE before machine/global scaling. Analysis recipes award this FE. |
| `recipe.foundations_calculator:stone_separator/0657.ticksOverride` | `-1` | -1 … 1000000 | -1 or 0: recipe/default duration; positive: replacement ticks where processing is timed. |
| `recipe.foundations_calculator:stone_separator/0657.chanceMultiplier` | `1.0` | 0.0 … 100.0 | Multiply each process result chance, clamped to 0–1. |

## energy

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `energy.machineCapacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Global multiplier for long-valued machine FE capacities. Applied after each machine base capacity. |
| `energy.itemCapacityMultiplier` | `1.0` | 0.01 … 1000000.0 | Global multiplier for long-valued Calculator/module FE capacities. |
| `energy.transferMultiplier` | `1.0` | 0.01 … 1000000.0 | Global multiplier for FE/native transfer limits. Does not change conversion ratios. |

## balance

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `balance.applyPreset` | `false` | true / false | Apply the selected Calculator balance preset on top of explicit server settings. Off preserves R9 behavior. |
| `balance.preset` | `0` | 0 … 4 | Balance preset: 0 Custom/R9, 1 Classic, 2 Balanced, 3 Expert, 4 High Power. Only active when applyPreset=true. |

## api

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `api.longEnergy.enabled` | `true` | true / false | Expose Foundations long-valued FE views to addons/native adapters while keeping NeoForge FE as a bounded facade. |

## guide

| Key | Default | Valid range | Meaning |
|---|---|---|---|
| `guide.liveValues` | `true` | true / false | Allow bounded read-only field-guide server snapshots. Static chapters remain usable when false. |

