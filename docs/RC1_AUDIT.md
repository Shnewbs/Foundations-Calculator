# 0.1.0-rc.1 audit and acceptance

This is a release candidate requested after alpha R6, not a stable release or a claim that all manual beta acceptance is complete. CurseForge represents RC builds with its **beta** release type. Ordinary beta and stable publication retain their existing acceptance rules. RC publication requires the native build, units, standalone and integration runtime regressions, minimum-loader runtime, binary/source/resource audit and actual client workflow. Both runtime modes must include the new positive RC regression marker. Publication receipts and final run results are recorded after those checks finish.

## Source and comparison scope

The base is current Git, not the attached historical 0.0.2a.R2 archive. Original Calculator `1.12.2` commit `c796f1dc24e937f95cb5d8573f485b66e87164b4` and Sonar Core commit `f5b64e033e2c07af55c2d5f474052fb83fba5ca1` are the provenance anchors. Review covers registration/declarations and the original tile-entity, item/module, recipe, circuit, inventory, nutrition, energy and world-action implementations against the three maintained branches. The existing recipe migration report records invalid original declarations and conversions; 910 authored port recipes remain unchanged.

| Original functionality | Port implementation / review outcome |
|---|---|
| Calculator, Scientific, Atomic, Flawless, Crafting and Info calculators | CalculatorItem, CalculatorMenu, PortableCraftingMenu, GuideScreen; original combination families retained, modern authoritative transactions and research policy |
| Dynamic Calculator and assembly lanes | DynamicProgram and dynamic module; fixed lane ranges, live structure checks, per-lane reload-sensitive selections |
| Extraction, Precision, Processing, Restoration, Reassembly and separators | Shared ProcessRecipe / MachineBlockEntity processing; counted ingredient allocation, crafting remainders and pending backpressure; previous six-to-54 output expansion retained |
| Analysing Chamber, random circuit states and analysis rewards | CircuitData / analyse(); sorted reward selection, no change to RNG draws or rewards; avoid building seven filtered lists per circuit |
| Fabrication Chamber | Existing port uses explicit recipe inputs and ordinary output inventory. Original connected Storage Chamber discovery/output-selection workflow is a known interface/automation difference; this audit does not silently replace the established port workflow |
| Research Chamber | Original registration was commented out; port deliberately revives it with persistent per-player/server-wide groups and mastery. Existing policy remains unchanged |
| Storage Chamber, Reinforced Chest, Algorithm Assimilator | Separate legal item samples and bulk counts, persistence, menus, extraction, category rules and native transaction rollback retained |
| Basic, Advanced, Flawless Greenhouses and CO2/lanterns | Blueprint resources, formation, carbon bands, seed import, tending, growth, farmland/water, owner protection, external plant adapter paths reviewed; schedules and callbacks retained |
| Health/Hunger processors, pipes, tree assimilators and nutrition modules | NutritionData, nutritionNetwork and leaf paths; disabled nutrition now cannot heal/feed from an already installed or held module; shared harvest yields |
| Power Cubes, Creative Cube, crank/handle, extractors, Locator/plugs, mast/transmitters/stations | Canonical long storage, side gates, balancing, fuel/remainders, structure and output paths reviewed; unreachable duplicate bank-balancing branch removed; disabled cranking rejected |
| Weather Controller, Rain Sensor, magnet and energy screen | Existing signal/weather/filter/energy display behavior retained; player screen placement now asks placement permission at the actual destination |
| Workstation and installed modules | Preserve one authoritative owner; invalidate an open storage projection when selection changes; idle smelters stop per-tick tag writes and healthy nutrition carriers avoid unchanged component rewrites |
| Terrain, advanced/atomic terrain, soil/small stone, sickle and obsidian key | New shared PlayerWorldActions preflight, fresh target/hand/resource checks, costs and drops only after successful change; no transient atomic-terrain placement/rollback on denial |
| Warp, Jump, Ender/End Diamond, Grenades, swords/tools, crops/leaves/saplings and materials | Existing module dispatch, energy, cooldown, world/load/border and config paths reviewed; no default effects or enabled-feature balance changed |
| Teleporter, Flawless Furnace, Flawless Capacitor | Source classes exist upstream, but their block registrations are commented out in the pinned 1.12.2 source. Their absence is not treated as a regression in active original content |

## NeoForge and Mojang boundary review

Primary references: [26.1 migration primer](https://docs.neoforged.net/primer/docs/26.1/), [26.3 primer](https://docs.neoforged.net/primer/docs/26.3/), [26.1 loader changes](https://neoforged.net/news/26.1release/) and [NeoForge transfer rework](https://neoforged.net/news/21.9release/). Follow actual target APIs; migration guides are non-exhaustive.

- 1.21.1 retains int FE capabilities with bounded long facades. 26.x uses native transaction-aware inventory/energy handlers and ValueInput/ValueOutput persistence; aborts, side changes, simulation and shared canonical storage remain covered by native runtime assertions.
- 26.x uses BreakBlockEvent; 1.21.1 uses BlockEvent.BreakEvent. The shared player-action policy has a target-specific event construction, rather than reflection or nonexistent compatibility fallbacks.
- 26.x item IDs/properties, block name prefixes, recipe/feature registry changes, component serialization, render-state extraction/submission, overlays, vertex colors and outlines remain target-native. R6 renderer fixes and actual client checks are retained.
- Minimums remain tested ranges: 1.21.1 NeoForge `[21.1.219,21.2)`, 26.1.2 `[26.1.2.100,26.1.3)`, 26.3 `[26.3.0.39-beta,26.4)`. Development pins remain 21.1.250, 26.1.2.114 and 26.3.0.52-beta. Newer loader availability alone does not justify changing compatibility claims.

## Confirmed fixes and remaining profiling

Cancellation and reentrancy tests exercise actual tools, event listeners and inventories. A denied replacement must preserve the original world state throughout placement preflight and consume no energy/materials. A listener changing the target or draining FE aborts the tool. Accepted atomic terrain still consumes exactly one replacement and its configured cost. Configuration tests cover disabled nutrition and already-open portable calculation. Idle smelting tests repeat 1,000 calls and assert no custom-data component is introduced, while removal of a working input still resets progress. Storage tests change SelectedModule and require the old projection to become invalid.

Performance changes remove redundant recipe filtering, idle module serialization and unreachable routing. Ordinary calculator energy queries no longer allocate and scan a 16-entry empty module inventory. Only reinforced chests register the client tick callback that advances chest lids; other machine animation remains render-driven. They preserve recipe order, random draws, probabilities, processing costs, output order and scan cadence. Existing bounded caches and native transaction boundaries are kept; no foreign handlers, players or permissions are newly cached. No whole-mod TPS/FPS improvement is claimed without a representative player-world profile.

Open acceptance: extended multiplayer/protection-mod combinations, a copied user-world upgrade from older releases, exhaustive survival progression, original Fabrication interface parity, and long-duration large-farm/network profiling. Automated runtime tests and client walkthroughs establish their tested operations, not those entire playthroughs.
