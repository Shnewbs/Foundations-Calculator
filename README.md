# Foundations Calculator 0.1.0alpha.R6

Minecraft **1.21.1**, NeoForge. This alpha adds optional expanded output storage and scrolling to both the Extraction Chamber and Precision Chamber. Their default capacity remains six slots; existing recipes and processing mechanics are unchanged.

Set `machine.extraction_chamber.outputSlots` and/or `machine.precision_chamber.outputSlots` to a value from 6 to 54 in the server configuration. Scroll over the output grid or drag its scrollbar to collect additional results. Automation and Transfer Upgrades can extract off-screen items. Lowering capacity retains saved overflow until it is collected.

Process recipes support up to 54 independently rolled results. See [pack recipe examples](docs/KUBEJS.md) and the [configuration reference](docs/CONFIG_REFERENCE.md). KubeJS/CraftTweaker runtime support is tested on 1.21.1; use native datapacks on the 26.x ports.

All three Minecraft versions have separate branches and binaries. Install matching mod versions on client and server. Builds must pass their native validation before GitHub/CurseForge publication; alpha does not imply completion of the beta acceptance checklist.

The R4 performance pass also fixes raw block-item translation keys and the 26.x red world-model overlay. NeoForge minimums are verified through native compilation and runtime checks before publication; see [the performance audit](docs/PERFORMANCE_R4.md).
