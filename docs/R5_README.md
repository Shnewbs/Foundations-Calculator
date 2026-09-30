> Archived R5 README (verbatim baseline below). Paths and validation statements refer to the original R5 root, not R6 acceptance. Use the current root README and POWER.md for R6.

# Foundations Calculator — 0.0.1a.R5

**Minecraft 1.21.1 · NeoForge 21.1.250 · Java 21**

Calculator and its required Sonar Core systems are integrated into one Foundations mod and one JAR. The mod ID is `foundations_calculator`. No separate core or Foundations framework is required. KubeJS support is optional.

R5 adds bidirectional native Mekanism joules, GregTech CEu EU, Modern Industrialization EU and GrandPower ports and powered-item charging, alongside FE and the AE2 item adapter. GregTech uses real voltage/amperage packets with per-machine controls. Held block models now have explicit left/right-hand transforms; articulated multi-block models fit a centered item cube without shrinking their placed structures.

R4 adds reusable End Diamond throws, wrench side cycling and safe dismantling, research unlocks and mastery tracking, portable smelting, atomic terrain replacement, JEI/EMI recipe displays and filling, Jade status, CraftTweaker, AE2/Mekanism energy adapters, external plant hooks, and an animated reinforced chest. Configuration covers every registered content entry and every shipped recipe, with additional startup item/block/material properties and client appearance controls.

The core remains embedded in this one mod. English labels are included; translations and old-world conversion are outside this release. This is still an alpha: see [PORT_STATUS.md](PORT_STATUS.md) for deliberate modern adaptations and [validation/RESULTS.md](validation/RESULTS.md) for the tested boundaries.

## Install

1. Use Minecraft **1.21.1**, **NeoForge 21.1.250**, and **Java 21**.
2. Put `FoundationsCalculator-0.0.1a.R5.jar` in `mods` on both client and server.
3. For scripts, optionally add KubeJS `2101.7.2-build.379` and Rhino `2101.2.7-build.81`, the tested 1.21.1 pair.
4. Close Minecraft and replace the earlier Foundations Calculator JAR with R5. Install optional integrations only if you want those features; see [docs/INTEGRATIONS.md](docs/INTEGRATIONS.md).

This build targets NeoForge; Forge and Fabric builds are not included. Do not install the old Calculator or Sonar Core JAR alongside it.

## Getting started

- Craft a Calculator and Hand Cranked Generator. Use the generator to produce FE; sneak-use opens its inventory. It sends FE to adjacent machines and charges the battery slot. A Crank Handle can be placed above it.
- Power Cubes discharge fuel/energy items in **Discharge** and charge calculators in **Charge**. Processing machines consume fuel or stored item energy in **Power**. Coal/charcoal supplies 500 FE; redstone 1,000 FE; coal blocks 4,500 FE; redstone blocks 9,000 FE. A consumable waits until its entire FE value fits. FE cables also supply processing machines. The Docking Station needs a calculator to select its recipe family.
- Basic, Scientific and Flawless portable calculators have two, two and four inputs. Press **Calculate**. The placed Atomic Calculator has three inputs. The Docking Station uses the family of its installed calculator.
- Open **Sides** for per-face controls: **A** uses the automatic energy role (generators output, processors receive, cubes receive/output); arrows force input/output, and × disables the face. Automatic cube-to-cube links equalize stored charge. Item automation inserts inputs and extracts outputs. Letters are Down, Up, North, South, West and East. Speed/energy upgrades affect processing; Transfer upgrades export outputs; Void upgrades explicitly permit disposal of blocked output.
- Shift-click sends fuel, chargeable items, upgrades, extractor ingredients and greenhouse seeds to the appropriate slots. The status line shows required FE, processing percentage, missing inputs, unmatched recipes and blocked output; hover it for the FE requirement.
- Extractors take furnace fuel first and starch/redstone/glowstone ingredients second. Health/hunger processors charge corresponding modules. Nutrition Modules restore health/hunger while carried.
- Use the **Info Calculator** for a searchable field guide and machine recipes from the current datapack, including KubeJS edits. Vanilla crafting recipes appear in the recipe book.

A quick functional check: put one raw iron in a Reinforced Furnace input and one coal in **Power**. After its cycle, collect one iron ingot. To charge a portable Calculator, put redstone in a Power Cube’s **Discharge** slot and the Calculator in **Charge**; a basic cube charges at the original 4 FE/tick.

Dirty chips go in the **Processing Chamber** (25 seconds by default) or **Restoration Chamber** (50 seconds), with **1,000 FE** available per cycle. Damaged chips use Processing or Reassembly. Feed the resulting clean board into the Analysing Chamber. Extraction produces dirty/damaged chips from raw materials; it does not clean them. These recipes are already included; the Info Calculator shows the active recipes and any KubeJS changes.

## Modules, storage and multiblocks

The Module Workstation holds a Flawless Calculator and up to 16 installed modules. Sneak-use the calculator to select an installed module. Its contents and module state travel with it; installed Energy Modules extend the calculator's FE capacity. Atomic Assembly provides the three-lane dynamic module. Storage Modules hold 54 normal slots; Crafting Calculators open a 3×3 crafting grid.

Reinforced Chests have **27 bins × 256 items**. Circuit Storage Chambers have **14 bins × 1,024 circuits**, separated by variant and a shared dirty/damaged/analysed/stable category. Click to deposit or withdraw a normal stack; right-click withdraws half a normal stack; shift-click transfers to/from your inventory.

Greenhouses construct and farm their original layouts. The Flawless tier uses a manually assembled frame and a CO₂ generator. The Dynamic Calculator and Calculator Locator validate their multiblocks before operating. Conductor Masts link to Weather Stations and receive boosts from Transmitters. Construction details and operating costs are in [docs/MULTIBLOCKS.md](docs/MULTIBLOCKS.md).

Warp Modules bind by sneak-using stable stone and return for 1,000 FE. The destination must be loaded, clear and in the same dimension. Terrain Modules convert their supported blocks for one FE; sneak-use a block to cycle material. The Obsidian Key recovers obsidian, the Sickle harvests mature pear/diamond leaves, and the Magnetic Flux pulls filtered drops into an inventory below it.

## KubeJS and configuration

The `foundations_calculator:process` recipe type supports native KubeJS builders, `event.custom`, recipe removal, nested input/output replacement, tags and `/reload`. The mod also loads without KubeJS. See [docs/KUBEJS.md](docs/KUBEJS.md) and `examples/kubejs/server_scripts/`.

Server configuration uses `foundations_calculator-server.toml`. In normal worlds it is managed as a NeoForge server configuration; development GameTest runs place it in `run/config`. `energyTransferPerTick = 0` selects the original per-machine transfer profiles. Positive values override them. Machine enablement, capacities, transfer, charging, redstone, automation and applicable cost/time profiles are configurable, alongside module, nutrition, crops, greenhouse, world-effect, research and recipe controls.

Research conversions now unlock by studying samples in the Research Chamber. Study takes 100 ticks and 1,000 FE by default and preserves the sample. The Info Calculator’s **Research** button shows sample groups and progress. Its **Recipes / Mastery** tab tracks completed basic/scientific/atomic/flawless calculations. Unlocks belong to the placer/first opener of a machine; server-wide sharing is configurable. `enableLegacyResearchRecipes = true` bypasses the unlock requirement.

The Smelting Module holds input, container remainders and output, and works while carried or installed in a Flawless Calculator. It costs 1,000 FE and 1,000 ticks by default. Sneak-use the Atomic Terrain Module on a source block, then a replacement block; ordinary use replaces the selected source using a replacement item from your inventory and one FE. Block entities and protected/unbreakable blocks are excluded. Both modules have crafting recipes.

Use a Wrench normally to cycle the clicked face’s I/O mode. Sneak-use dismantles the machine and retains its contents, FE and settings by default. Use an End Diamond to throw a reusable ender pearl. The reinforced chest lid animates when opened.

[CONFIGURATION.md](CONFIGURATION.md) explains the server, client and startup JSON settings, precedence, reload behavior, example overrides and the complete generated reference. Ingredient/output changes, arbitrary new recipes, tags, loot tables, trees and furnace fuel data remain normal datapack/KubeJS/CraftTweaker data.

## Build and verify

Install **JDK 21**, set `JAVA_HOME`, then run:

```sh
./gradlew clean build
./gradlew runGameTestServer
./gradlew runGameTestServer -PwithIntegrations -PwithKubeJS
```

On Windows use `gradlew.bat` or double-click `BUILD.bat`. `build.sh` performs a clean build on Unix. The game mod is `build/libs/FoundationsCalculator-0.0.1a.R5.jar`; the `-sources.jar` is for developers.

The wrapper pins Gradle 9.2.1; the build pins ModDevGradle 2.0.147 and NeoForge 21.1.250. The first build needs internet access. Test fixtures and optional KubeJS dependencies are excluded from the release JAR. Python is only needed to regenerate imported assets/data or run their checker.

[validation/RESULTS.md](validation/RESULTS.md) records the checks. [docs/DEVELOPMENT.md](docs/DEVELOPMENT.md) explains source layout, reproduction and upstream provenance.

## License

MIT. Original Calculator/Sonar Core authorship and artwork remain credited to Ollie Lansdell / SonarSonic in [NOTICE.md](NOTICE.md) and [LICENSE](LICENSE).
