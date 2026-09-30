# Foundations Calculator — Master Status, Finalization Plan, Source Map & Expansion Roadmap

**Document date:** 2026-09-29  
**Current source baseline audited:** `Foundations Calculator 0.0.2a.R2`  
**Minecraft:** 1.21.1  
**NeoForge:** 21.1.250  
**Java:** 21  
**Mod ID:** `foundations_calculator`  
**Guide API:** `com.foundations:foundations-guide-api:1.0.0`  
**Gameplay network protocol:** `6`

> This file is intended to be the handoff document for returning to Calculator development later without having to reconstruct the project from chat history. It separates four different states that are easy to blur together during an alpha: **implemented**, **verified offline**, **still requiring native/in-game acceptance**, and **future expansion ideas**.

---

## Status legend

- **[x] Implemented** — present in the current `0.0.2a.R2` source.
- **[x/offline] Verified offline** — source/static/isolated tests executed against the current R2 tree.
- **[~] Implemented, acceptance pending** — code exists, but the native Minecraft/integration/manual gate still has to prove it.
- **[ ] Remaining work** — a concrete task still required before 2a should be frozen.
- **[FUTURE]** — an expansion recommendation, not functionality claimed to exist today.

---

# 1. Executive status

`0.0.2a.R2` is currently the **final 2a candidate**, not yet the final frozen 2a baseline.

The port is no longer in the state of “machines launch but major content is missing.” The current repository contains the Calculator and required former Sonar Core functionality in one NeoForge mod, a full recipe/data/config layer, modern energy integrations, research, world machines, greenhouses, automation/protection handling, viewer/scripting integrations, a substantial Field Guide and Guide API, 64-bit internal energy storage, and a large test/validation harness.

The feature scope for 2a should remain **frozen**. The correct next work is not another gameplay feature. The correct next work is:

1. clean up several source/documentation/version-drift findings found in this audit;
2. run the full native validator and get every native test green;
3. execute copied-world migration and long-energy tests;
4. verify the R2 upgrade and bulk-storage fixes in game;
5. run full survival/progression acceptance;
6. run mixed-integration, protection, multiplayer and performance/soak acceptance;
7. archive the passing evidence and freeze R2 (or one final bug-fix revision if testing finds a real defect).

If those gates pass, **stop changing 2a** and move new feature work into the next alpha.

---

# 2. Source of truth and provenance

## 2.1 Current authoritative project

Use the complete `FoundationsCalculator-0.0.2a.R2` source tree as the development source of truth.

Do **not** reconstruct the project from an old R5/R6/R7 updater payload unless recovering from a disaster. The R2 tree already contains the accumulated fixes from R5 through R2.

Important root files:

- `gradle.properties` — current mod version and build metadata.
- `build.gradle` — NeoForge and optional integration dependency setup.
- `README.md` — current release summary.
- `PORT_STATUS.md` — current final-2a-candidate status.
- `CHANGELOG.md` — R2-specific changes.
- `VALIDATE_0_0_2a_R2.bat` — authoritative local final validator.
- `docs/0.0.2a.R2_ACCEPTANCE.md` — manual copied-world acceptance.
- `validation/0.0.2a.R2/` — location where local validation evidence should be preserved.

## 2.2 Upstream provenance retained in the repository

The modern Foundations port was derived from the 1.12.2 SonarSonic projects documented by the repository:

- Calculator upstream commit: `c796f1dc24e937f95cb5d8573f485b66e87164b4`
- Sonar Core upstream commit: `f5b64e033e2c07af55c2d5f474052fb83fba5ca1`

The required Core functionality is intentionally integrated into the Calculator mod. There is no separate runtime Sonar Core JAR requirement.

## 2.3 Deliberate compatibility boundary

Old Sonar Core binary addons and 1.12-only adapters are **not** binary compatible with this 1.21.1 mod. Modern integrations should use current 1.21.1 APIs and the public Foundations extension points rather than trying to make the old addon JARs load unchanged.

---

# 3. Current project accounting

The following was re-audited against the exact R2 source tree on 2026-09-29.

| Area | Current R2 accounting |
|---|---:|
| Registry catalog | **303 entries** |
| Blocks | **153** |
| Standalone items | **150** |
| Entries marked pending | **0** |
| Recipe JSON files | **910** |
| Custom process recipes | **699** |
| Shaped crafting recipes | **143** |
| Shapeless crafting recipes | **66** |
| Furnace smelting recipes | **2** |
| Server configuration keys | **4,272** |
| Field Guide chapters | **10** |
| Field Guide entries | **109** |
| Machine guide entries | **45** |
| Module guide entries | **13** |
| Guide item links | **300** |
| Guide authored word count | approximately **13,290** |
| Patchouli mirror entries | **109** |
| Declared native GameTests | **124** |
| JUnit `@Test` methods | **13** |
| Asset validator model combinations | **4,046** |

`Content.PENDING` is empty and `tools/pending_content.json` is empty. There is no current registry entry intentionally labelled as a “port pending” placeholder.

---

# 4. Development history — what has been done

This is a condensed history of the current source line. The repository includes older archived documentation for deeper details.

## 4.1 Early modern port / R4 foundation

The current source retains the functional port built before the power/guide finalization work. Major systems present from the R4-era implementation include:

- portable Basic, Scientific and Flawless calculators;
- placed Atomic Calculator, Docking Station and Dynamic Calculator systems;
- machine processing/circuit chain;
- generators, cubes and extractors;
- storage machines;
- greenhouses and world machines;
- crop/wood/tool families and former Core decorative content;
- reusable End Diamond behavior;
- wrench face cycling and settings/inventory/energy-preserving dismantling;
- Research Chamber and sample-based unlocks;
- calculator mastery tracking;
- Smelting Module and Atomic Terrain Module;
- native JEI and EMI process categories;
- Jade machine status;
- optional KubeJS support;
- CraftTweaker API;
- public plant and energy extension hooks.

The port deliberately uses shared modern menus rather than reproducing every historical GUI pixel-for-pixel.

## 4.2 R5 — modern power integrations and item-model normalization

R5 established the broad external-energy baseline and held-item model cleanup:

- native NeoForge FE;
- Mekanism joule integration;
- GregTech CEu EU integration with real voltage/amperage packet behavior;
- Modern Industrialization EU integration;
- GrandPower long-valued FE integration;
- AE2 powered-item adapter;
- per-machine/global GT voltage and amperage settings;
- explicit item display transforms for the building-block models so large placed structures do not appear enormous in hand/inventory.

The current GT integration remains pinned to the documented 1.21.1 development snapshot used by this source line because the older public 7.0.2 target had upstream server/current-KubeJS problems in the validated setup.

## 4.3 R6 — conversion policy and diagnostics

R6 expanded energy configuration from “adapter exists” into a controlled server policy:

- explicit/inherited conversion ratios;
- direction gates;
- item charging versus block-port scopes;
- conversion losses;
- native-first routing option;
- conservative integer-quantum conversion behavior;
- power diagnostics and mismatch warnings;
- server-authoritative power reporting.

The implementation avoids rounding tiny quantities up into free energy and treats invalid/unrepresentable conversion plans as refused transfers rather than creating value.

## 4.4 R7 — automation, protection and repeated-work hardening

R7 addressed inconsistencies found by auditing actual machine paths:

- Transfer Upgrade/export behavior aligned with item automation and side rules;
- greenhouse automation aligned with configured side/master automation rules;
- owner-attributed loaded-only permission preflights for world-changing machines;
- protected greenhouse build/demolish/farming paths;
- world-action preflight event path;
- reduced repeated sync work;
- reinforced-chest opener tracking instead of scanning every player every tick;
- cached greenhouse geometry/fallow work;
- reduced repeated recipe/guide searches.

The current protection layer intentionally requires an owner by default and keeps unsafe/unbounded external plant callbacks disabled unless a server explicitly opts into the less-protected legacy behavior.

## 4.5 R8 / R8.1 — structured Field Guide and Guide API

The old browser-like information view was expanded into a structured documentation system:

- versioned, mod-neutral Foundations Guide API 1.0.0;
- chapters and stable entry IDs;
- searchable content;
- item/recipe references;
- bookmarks/history;
- bounded live server snapshots;
- guide interoperability metadata;
- resource-driven publication path;
- generated Patchouli mirror;
- `dont_generate_book=true` so Patchouli does not create a second Calculator book;
- same Calculator guide remains usable without Patchouli or a future master reader.

R8.1 added a real Welcome/Getting Started flow and Patchouli interoperability while retaining one guide item.

## 4.6 R9 — Field Guide presentation finalization

R9 changed the standalone reader from a documentation-browser feel into the Foundations guidebook family:

- two-page book layout;
- soft-grey leather shell;
- parchment page block and center binding;
- external chapter tabs;
- responsive single-page/narrow mode;
- styled headings, notes, tables, live-value blocks and blueprint layers;
- player-friendly live-value labels instead of raw TOML key dumps;
- no raw `End of entry` IDs in normal reading;
- soft-grey leather Field Guide item art with Calculator accents.

The Field Guide was later reduced to a **640×360 normal desktop cap** because the first version occupied too much of the screen compared with the other Foundations guides.

## 4.7 0.0.2a — scale/integration alpha

The next alpha added the major scale layer:

- 64-bit canonical internal machine energy storage;
- long-valued powered Calculator/module energy component;
- legacy int energy component retained as a saturated compatibility/downgrade value;
- public `LongEnergyStorage` API;
- long item/block route methods and adapter registration;
- global machine/item capacity and transfer multipliers;
- per-machine/per-module capacity multipliers;
- optional balance presets;
- client preferred display unit selection for FE / GT EU / Mekanism J / MI EU;
- read-only operator configuration reports;
- structural progression validator;
- Guide entry for long buffers, units and scaling.

Important boundary: **storage is now genuinely long-valued, but not every operation in the mod is a long-valued operation.** Standard NeoForge FE is int-valued, process-recipe energy remains int-valued, and several per-operation transfer/rate profiles remain int-valued. This is intentional in 2a and is a future scale-expansion opportunity rather than evidence that the long buffer does not work.

## 4.8 0.0.2a-HF1

HF1 fixed two concrete acceptance problems:

- reduced normal Field Guide footprint to 640×360;
- corrected GregTech long-storage reporting to use `LongEnergyStorage.stored()` and `.capacity()`.

## 4.9 0.0.2a.R1 — long-energy finalization

R1 froze features and hardened long-energy migration:

- over-capacity energy survives if a server later lowers configured capacity;
- an over-capacity store refuses new input and can drain naturally instead of truncating value;
- same preservation semantics applied to powered Calculator/module items;
- Calculator Screen reads through the public long route;
- automatic storage-cube equalization uses true long capacities and saturating arithmetic;
- six additional migration/long-buffer GameTests;
- final release/JAR/source/config/GameTest gate updated for 2a.

## 4.10 0.0.2a.R2 — upgrade/storage correctness

R2 fixed the two real upgrade bugs found during manual acceptance:

- Reinforced Furnace advertised Speed/Energy upgrades but its special smelting path bypassed the shared upgrade math;
- Analysing Chamber advertised Speed/Energy upgrades but its analysing path bypassed the shared upgrade math.

R2 routes both through the common `upgradeEnergyCost()` and `upgradeProcessTicks()` behavior, aligns diagnostics with actual upgraded cost, adds upgrade guidance, and fixes Reinforced Chest bulk-bin icons so the vanilla stack count no longer appears on top of the separately rendered authoritative bin quantity.

R2 adds four more native GameTests and raises the final runtime floor to **124**.

---

# 5. Implemented systems — current subsystem inventory

## 5.1 Calculators and portable items

**Status: [x] implemented; [~] final full progression acceptance still pending.**

The current mod supports the portable Calculator families and installed-module workflow rather than treating calculators as simple decorative crafting items.

Implemented behavior includes:

- Basic / Scientific / Flawless calculator families;
- Atomic Calculator placed machine;
- Dynamic/assembly-related calculator behavior;
- installed module selection and persistence;
- module inventories travelling with the Flawless Calculator;
- Energy Modules extending stored energy;
- long-valued powered-item storage in 2a;
- item instructions/tooltips and client energy display controls;
- persistent modern DataComponents for relevant item state.

Key source:

- `src/main/java/com/foundations/calculator/content/CalculatorItem.java`
- `src/main/java/com/foundations/calculator/api/LongEnergyStorage.java`
- data-component registration under the content/bootstrap classes.

## 5.2 Machine processing framework

**Status: [x] implemented; [~] native and survival acceptance pending.**

`MachineBlockEntity` is the central implementation for inventory, energy, side configuration, upgrades, process recipes, pending outputs, automation wrappers, persistence and client synchronization.

Important current architecture facts:

- 25-slot shared machine inventory layout;
- per-machine input-count rules;
- output and battery role rules;
- upgrade-capable machine list;
- energy-use roles;
- sided item and energy automation;
- long internal energy port plus bounded FE facade;
- shared upgrade cost/time helpers;
- client update snapshots;
- recipe-selection caching;
- pending-output persistence.

Key source:

- `src/main/java/com/foundations/calculator/content/MachineBlockEntity.java`

Current maintainability warning: a number of machine-role decisions are still encoded as hardcoded `switch`/`Set.of(...)` rules in this class. That works, but it creates an opportunity for two declarations to get out of sync—as happened with special-path upgrades before R2. This is a top future refactor candidate.

## 5.3 Upgrade system

**Status: [x] implemented; R2 corrections present; [~] in-game acceptance pending.**

Current upgrade types:

- Speed Upgrade;
- Energy Upgrade;
- Transfer Upgrade;
- Void Upgrade.

Current shared math:

- Speed uses configured `upgrades.speedBonus` in the effective processing-duration calculation;
- Energy uses configured `upgrades.energyDiscount` in the effective FE-per-cycle calculation;
- Transfer exports configured items-per-transfer per installed upgrade up to the configured maximum;
- Void applies only after normal output insertion remains blocked and the server permits void behavior.

R2 specifically corrected Reinforced Furnace and Analysing Chamber special paths.

Key source:

- `MachineBlockEntity.upgradeEnergyCost(...)`
- `MachineBlockEntity.upgradeProcessTicks(...)`
- `MachineBlockEntity.upgrades(...)`
- special furnace/analyser code inside `MachineBlockEntity`
- Transfer path in `MachinePrograms`
- upgrade item tooltips in `CalculatorItem`

## 5.4 Energy storage and conversion

**Status: [x] implemented; [x/offline] isolated math/policy tests pass; [~] native optional-mod acceptance pending.**

Canonical stored energy is FE expressed as a `long` internally.

Implemented layers:

- `StoredEnergy` long machine buffer;
- `LongEnergyStorage` public API;
- int-bounded `IEnergyStorage` compatibility facade;
- long portable-item energy;
- conversion ratios/quantization;
- direction/scoping gates;
- conversion losses;
- simulation-safe transfer math;
- native-first routing policy;
- mismatch diagnostics;
- long capacity/preset scaling.

A configured capacity reduction **does not** discard existing energy in current R2 code. This behavior is important because one retained 2a document still says the opposite; see the source-drift section below.

Key source:

- `src/main/java/com/foundations/calculator/core/StoredEnergy.java`
- `src/main/java/com/foundations/calculator/core/EnergyRatio.java`
- `EnergyConversion.java`
- `LongEnergyBridge.java`
- `PowerPolicy.java`
- `FoundationsEnergy.java`

## 5.5 External energy integrations

**Status: [x] adapters implemented; [~] real installed-mod final certification pending.**

Current supported paths:

| System | Current Calculator support |
|---|---|
| NeoForge FE | Native block/item compatibility; int-valued API facade |
| Mekanism | Native J block/item conversion path |
| GregTech CEu | Native EU, real voltage/amperage packets, batteries/items |
| Modern Industrialization | EU block/item adapter |
| GrandPower | Long-valued FE path |
| AE2 | Powered-item adapter; no custom native AE grid port |

Important boundary: AE2 grid networks should use their normal FE acceptance route. The dedicated Calculator AE2 adapter is for powered items, not a replacement grid-energy protocol.

Old IC2/Tesla-era interfaces are intentionally not claimed as current support.

Key source:

- `compat/GregTechEnergy.java`
- `compat/MekanismEnergy.java`
- `compat/ModernIndustrializationEnergy.java`
- `compat/GrandPowerEnergy.java`
- `compat/AE2Energy.java`
- `docs/INTEGRATIONS.md`

## 5.6 Research and mastery

**Status: [x] implemented; [~] complete survival/progression acceptance pending.**

Current system includes:

- player-owned or optionally server-wide research;
- sample-based Research Chamber studies;
- configurable study time and FE cost;
- configurable sample consumption;
- research recipe-group gating;
- client synchronization;
- calculator-family mastery counters;
- original target counts retained by default;
- mastery remains informational instead of inventing unproven reward/gating behavior.

Key source:

- `ResearchData.java`
- `ResearchProgram.java`
- process recipe research-group handling.

## 5.7 Storage systems

**Status: [x] implemented; R2 UI correction present; [~] manual final UI/storage acceptance pending.**

Current major bulk storage:

- Reinforced Chest: 27 bins, default 256 items/bin;
- Storage Chamber: 14 bins, default 1,024 circuits/bin with circuit-category/variant behavior;
- Algorithm Assimilator uses bulk storage behavior as well.

R2 changes the synced representative icon stack to count 1 while keeping the authoritative bulk quantity separate, fixing the confusing “vanilla 64 plus actual 76” visual case.

Key source:

- `BulkStorage.java`
- `BulkStorageMenu.java`
- `BulkStorageScreen.java`
- bulk paths in `MachineBlockEntity`.

## 5.8 Greenhouses, crops and external plants

**Status: [x] implemented; [~] real third-party crop/protection acceptance is integration-dependent.**

Current greenhouse system includes:

- Basic and Advanced blueprint construction;
- Flawless manually assembled structure;
- cached greenhouse geometry;
- build/demolish/farming paths;
- CO₂ behavior;
- plant growth/harvest logic;
- native CropBlock path;
- generic NeoForge-compatible plant path;
- public `PlantAdapter` extension contract for special crops.

Complex crops with unusual multi-block growth/harvest semantics are not automatically safe just because a generic plant item exists. Dedicated adapters are the intended extension path.

Key source:

- `GreenhouseProgram.java`
- `GreenhouseBlueprint.java`
- `FoundationsPlants.java`
- `MachineWorldActions.java`
- `docs/MULTIBLOCKS.md`

## 5.9 World machines and special programs

**Status: [x] implemented; test coverage varies by machine family.**

`MachinePrograms` dispatches special-machine behavior including:

- research;
- weather/rain functions;
- lantern behavior;
- magnetic flux;
- scarecrow;
- assimilators;
- locator/plugs;
- Calculator Screen;
- Atomic Multiplier;
- greenhouse/CO₂ paths;
- Module Workstation;
- Conductor Mast / Weather Station / Transmitter paths;
- transfer-upgrade export.

Key source:

- `src/main/java/com/foundations/calculator/content/MachinePrograms.java`

As with `MachineBlockEntity`, the special program dispatcher is currently switch-driven. That is a future maintainability target.

## 5.10 Owner/protection model

**Status: [x] implemented; [x/offline] policy fixtures pass; [~] actual claim-mod certification pending.**

`MachineWorldActions` currently provides:

- build-height/world-border/chunk-loaded checks;
- owner requirement by default;
- owner-attributed FakePlayer interaction;
- `mayInteract` and `mayUseItemAt` checks;
- public machine-action event;
- BreakEvent / EntityPlaceEvent preflight;
- rejection of block entities and unbreakable targets on reviewed paths;
- recheck after event listeners before mutation;
- safe default against unbounded legacy plant callbacks.

This is a strong generic NeoForge preflight, but no source-only test can promise that every third-party claim mod interprets every machine action the same way. Real installed claim mods remain part of acceptance.

## 5.11 Recipe/viewer/script ecosystem

**Status: [x] implemented; [~] integration pack gate pending.**

Current support includes:

- custom `foundations_calculator:process` recipe type;
- normal JSON datapacks;
- KubeJS builder/schema and raw custom JSON usage;
- CraftTweaker add/addJson/remove/removeMachine functions;
- JEI process categories and transfer;
- EMI process categories;
- Jade server-authoritative machine information;
- recipe reload/cache invalidation.

Recipe content is now data-driven enough that pack authors can change/add processes without editing Java.

## 5.12 Configuration system

**Status: [x] implemented; large by design.**

Current generated server configuration contains **4,272 unique keys**.

Top-level key-group counts from the current generated catalog:

| Group | Keys |
|---|---:|
| recipe | 3,007 |
| machine | 680 |
| content | 303 |
| module | 49 |
| greenhouse | 42 |
| power | 38 |
| generation | 24 |
| world | 24 |
| compat | 20 |
| nutrition | 12 |
| machines | 10 |
| research | 9 |
| plants | 8 |
| circuits | 8 |
| upgrades | 6 |
| tools | 5 |
| fuel | 5 |
| performance | 5 |
| storage | 4 |
| automation | 3 |
| energy | 3 |
| protection | 2 |
| balance | 2 |
| additional single controls | legacy-research/API/guide keys |

2a adds optional scale/balance controls and read-only admin summaries without replacing raw server TOML authority.

Client configuration includes theme/appearance, animations, chest animation, 3D tools, display options, item instructions/tooltips, preferred energy unit and Master Guide preference.

## 5.13 Field Guide, Patchouli and Guide API

**Status: [x] implemented; [x/offline] authoring/parity pass; [~] final client/manual acceptance pending.**

Current Field Guide:

- 10 chapters;
- 109 entries;
- 45 machine pages;
- 13 module pages;
- about 13.3k words;
- item links, live values, recipes and blueprints;
- soft-grey book item and two-page Foundations reader;
- search/bookmarks/history;
- 640×360 normal desktop footprint plus responsive narrow mode.

Interoperability:

- Guide API 1.0.0 embedded with the mod;
- resource-first publication path;
- optional Master Guide host interface;
- generated Patchouli mirror with the existing Calculator Field Guide item rather than a duplicate.

The **Master Guide mod itself does not exist yet**. That is a future separate project.

## 5.14 Assets and presentation

**Status: [x] broadly validated; [~] one known fallback visual remains a polish candidate.**

The current asset audit traversed 4,046 model combinations with no missing project model/texture, unbound texture, invalid path/bounds or declared-atlas error.

Offline validator limitation: it did not have the Minecraft vanilla asset JAR available, so `minecraft_assets_checked=false` is not the same as a fresh client render pass.

One migration report still identifies `calculator_screen_block` as a fallback visual. It works as a content entry, but it is a future art-parity/polish candidate.

Translations remain intentionally outside the current release scope; current resources are English-first.

---

# 6. Current validation evidence

The following checks were re-run on the exact R2 source during this audit.

## 6.1 Passed in this environment

- [x/offline] **96,300** deterministic power/conversion-core checks.
- [x/offline] **16** power-policy checks.
- [x/offline] **24** long-energy checks.
- [x/offline] **22,343** automation/cadence/change-snapshot checks.
- [x/offline] **124** world-action policy checks.
- [x/offline] Java 21 syntax-only parser: **138 files**.
- [x/offline] Structural recipe validator: **910 recipes**, no missing core progression target, no invalid process energy/time/result values.
- [x/offline] Guide authoring: **109 entries**.
- [x/offline] Patchouli mirror: **109 entries**, no duplicate guide item.
- [x/offline] Asset validator: **4,046** model combinations, no project-resource errors.
- [x/offline] Native GameTest declarations counted: **124**.
- [x/offline] Current JUnit methods counted: **13**.

These are useful correctness guards. They are **not** a substitute for dependency-resolved compilation or a running Minecraft server/client.

## 6.2 Structural progression result

The current static recipe graph reports:

- 910 recipes parsed;
- 258 reachable Foundations outputs;
- 275 total Foundations recipe outputs;
- no missing core progression target;
- no invalid process values.

It also reports a sample of 17 recipe outputs not reachable through its bounded recipe-only model:

- `amethyst_fence`
- `amethyst_log`
- `amethyst_planks`
- `amethyst_stairs`
- `broccoli`
- `diamond_fence`
- `diamond_planks`
- `diamond_stairs`
- `fiddledew_fruit`
- `pear`
- `pear_fence`
- `pear_planks`
- `pear_stairs`
- `tanzanite_fence`
- `tanzanite_log`
- `tanzanite_planks`
- `tanzanite_stairs`

**Do not classify these 17 as broken just from this report.** The validator intentionally does not simulate world generation, loot, crop growth, research or arbitrary player actions. They are a targeted manual-review list.

## 6.3 Native validation still required

The authoritative final local validator is:

```bat
VALIDATE_0_0_2a_R2.bat
```

It performs:

1. `gradlew.bat clean test build --console=plain`
2. verifies `build\libs\FoundationsCalculator-0.0.2a.R2.jar`
3. runs standalone GameTests and requires at least 124
4. runs optional-integration + GrandPower + KubeJS GameTests
5. executes `tools\check_release.py`
6. directs the tester to the manual copied-world acceptance checklist.

The authoritative expected release artifact is:

```text
build/libs/FoundationsCalculator-0.0.2a.R2.jar
```

No final 2a freeze should be declared until this native gate passes in the real development environment.

---

# 7. Concrete source-audit findings to clean up before archival

These are not speculative feature ideas. They are specific current-source drift found during the R2 audit.

## 7.1 Power diagnostics still print R1

**Priority: P0 cosmetic/diagnostic correctness**

File:

```text
src/main/java/com/foundations/calculator/core/PowerDiagnostics.java
```

Current hardcoded line includes:

```text
Foundations Calculator 0.0.2a.R1 | effective server power configuration
```

Current source is R2. The diagnostic should either:

- say `0.0.2a.R2`, or preferably
- derive the displayed version from one authoritative version source so future revisions cannot drift.

This does not change energy behavior, but it should be fixed before R2 is archived as final evidence.

## 7.2 Long-energy document contradicts current implementation

**Priority: P0 documentation correctness**

File:

```text
docs/0.0.2a_LONG_ENERGY_AND_SCALE.md
```

It still states that capacity reductions clamp loaded energy to the current effective capacity.

Current `StoredEnergy.load(long)` and R1/R2 finalization behavior deliberately preserve already-stored over-capacity energy, refuse additional receive, and allow the excess to drain normally.

Update the document to match the code and R1/R2 tests.

## 7.3 Generic power config descriptions contain GT-specific wording

**Priority: P1 generated-document polish**

Generator:

```text
tools/generate_config.py
```

The generic `outputLossPercent` description says:

```text
Outgoing GT packets round their FE cost up.
```

Because that description is used for the generic adapter schema, the generated configuration docs repeat the GT-specific sentence for Mekanism, MI, AE2 and GrandPower too.

Recommended fix:

- generic adapters: describe conservative outgoing conversion rounding without saying “GT packets”;
- GT-specific config: retain packet-specific rounding language in the GT description.

Then regenerate:

- `tools/configuration_catalog.json`
- `src/main/resources/foundations/configuration.json`
- `docs/CONFIG_REFERENCE.md`
- any generated configuration documentation that consumes the same catalog.

## 7.4 Patchouli book version still says 8.1

**Priority: P1 interoperability metadata cleanup**

Generator:

```text
tools/generate_patchouli_bridge.py
```

Current generated book metadata has:

```json
"version": "8.1"
```

That reflects the guide-revision era rather than the current Calculator release.

Recommended fix: derive the Patchouli book revision from current guide/mod metadata rather than hardcoding an old UI revision. Do not change the stable book ID.

## 7.5 Current guide text still contains historical R7/R9 wording

**Priority: P1 player-facing documentation polish**

Generator:

```text
tools/author_field_guide.py
```

Examples include:

- “R9 also publishes…”
- “The R7 safety default…”
- coverage/provenance text beginning with “R7-HF1 production programs…”

Some historical context is valid, but current player-facing instructions should generally describe the **current behavior**, not require the player to know the internal development revision history.

Recommended cleanup:

- “R9 also publishes…” → “Calculator also publishes…”
- “The R7 safety default…” → “By default…”
- provenance report → current-source wording, while retaining the fact that blueprint maps are generated from production blueprint code via isolated fixtures.

Regenerate both native and Patchouli guide resources after changing the generator.

## 7.6 Release checker error message says R1

**Priority: P0 release-tool polish**

File:

```text
tools/check_release.py
```

The assertion correctly expects guide revision `0.0.2a.R2`, but its failure message says:

```text
Guide revision is not R1
```

Change the message to R2 or make the expected revision part of the generated message.

## 7.7 One fallback migrated visual remains

**Priority: optional art polish; not a release blocker unless visually unacceptable**

`tools/asset_migration_report.json` reports `calculator_screen_block` as the single fallback visual.

If the block looks acceptable in the current client, this can move to the next alpha. If it visibly stands out from the rest of the port, re-author its model/texture before the final art freeze.

---

# 8. Final 2a plan — phased

# Phase 0 — Source/documentation normalization

**Goal:** make the current source identify and describe itself accurately before collecting final validation evidence.

- [ ] Remove the stale R1 version string from `PowerDiagnostics.java`.
- [ ] Fix long-energy capacity-reduction documentation to state that existing excess is preserved.
- [ ] Split generic output-loss wording from GT packet-specific wording.
- [ ] Regenerate config catalogs/docs after wording change.
- [ ] Replace stale `8.1` Patchouli version metadata with current/derived revision metadata.
- [ ] Make current Field Guide prose version-neutral where the revision number is not useful to the player.
- [ ] Update `CONTENT_COVERAGE.json` provenance text when the guide is regenerated.
- [ ] Fix the R1 typo in `tools/check_release.py`.
- [ ] Run guide and config generators and confirm they produce a clean diff with no accidental content-count changes.

**Exit condition:** source/docs/tools consistently identify R2/current behavior and generated outputs reproduce deterministically.

---

# Phase 1 — Native automated gate

**Goal:** prove the exact R2 source compiles and its automated Minecraft test matrix passes.

- [ ] Run `VALIDATE_0_0_2a_R2.bat` from a clean source checkout/copy.
- [ ] Native `clean test build` passes.
- [ ] Expected `FoundationsCalculator-0.0.2a.R2.jar` is created.
- [ ] All JUnit tests pass; no tests silently skipped because of test-classpath setup.
- [ ] Standalone GameTest server reports at least **124** passing tests.
- [ ] Integration/KubeJS GameTest server reports at least **124** passing tests.
- [ ] Integration log proves required optional adapters were actually present/used rather than merely absent-skipped.
- [ ] KubeJS log contains no Calculator schema/script registration errors.
- [ ] `tools/check_release.py` passes.
- [ ] Release audit confirms no test classes leaked into the production JAR.
- [ ] Release audit confirms Java 21 bytecode and source/resource identity.
- [ ] Archive all generated logs under `validation/0.0.2a.R2/`.

**Do not proceed by ignoring a native compiler error simply because syntax-only checks pass.** R2 should not be frozen until dependency-resolved compilation is clean.

---

# Phase 2 — R9/R1/2a migration and long-energy acceptance

**Goal:** prove 2a can safely carry real state across restart and configuration changes.

Use a copy of a known working world. Never perform the first migration check on the only copy of a valued world.

### Machine storage

- [ ] Load a machine with an ordinary R9-sized FE value; confirm exact value after upgrade.
- [ ] Configure a buffer above 2,147,483,647 FE.
- [ ] Fill above the int boundary.
- [ ] Save, stop cleanly, restart and verify the exact full long value.
- [ ] Lower configured capacity below current stored energy.
- [ ] Restart.
- [ ] Confirm stored energy is **not truncated**.
- [ ] Confirm the over-capacity machine refuses new input.
- [ ] Confirm it can still output/drain normally until below the new capacity.

### Portable Calculator/modules

- [ ] Repeat >2.147B storage with a powered Calculator/module item.
- [ ] Restart with item in player inventory.
- [ ] Move it through container inventory/save boundaries.
- [ ] Test installed Energy Modules in a Flawless Calculator.
- [ ] Confirm long component remains authoritative and legacy int component stays safely saturated.

### Calculator Screen / storage equalization

- [ ] Display a >2.147B Foundations buffer on Calculator Screen and confirm the shown value is not int-saturated.
- [ ] Equalize very large compatible storage cubes and confirm no overflow/wraparound.
- [ ] Unload/reload chunks during/after equalization.

**Exit condition:** long-energy state survives migration, restart, config shrink and item/container persistence without value creation or loss.

---

# Phase 3 — External power integration acceptance

**Goal:** validate real API behavior rather than only conversion math.

For each installed integration, test both transfer directions where supported and at least one powered item where supported.

## FE

- [ ] Generic FE input to Calculator machine.
- [ ] Calculator output to FE receiver.
- [ ] Confirm per-operation int bounds do not corrupt the long backing store.

## GregTech CEu

- [ ] Valid voltage/amperage packet input.
- [ ] Valid output through GT cable/container.
- [ ] Over-voltage refusal.
- [ ] Amp-budget exhaustion behavior.
- [ ] GT battery charging/discharging.
- [ ] Explicit conversion override.
- [ ] Nonzero loss.
- [ ] Restart while machine contains long energy.

## Mekanism

- [ ] J → Calculator input.
- [ ] Calculator → J output.
- [ ] powered item charge/discharge.
- [ ] inherited ratio mode.
- [ ] explicit override.
- [ ] loss settings.

## Modern Industrialization

- [ ] EU input/output.
- [ ] item charging/discharging where API supports it.
- [ ] inherited and override conversion behavior.

## GrandPower

- [ ] prove full long stored/capacity visibility.
- [ ] transfer above normal FE observation ceiling over repeated operations.
- [ ] no truncation when interacting with long buffer.

## AE2

- [ ] powered AE2 item adapter behavior.
- [ ] verify native AE grid is **not** falsely claimed as a dedicated Calculator port.
- [ ] normal FE-grid compatibility still works through standard acceptors.

### Unit display

- [ ] FE display.
- [ ] GT EU display with explicit trustworthy ratio.
- [ ] Mekanism J display with explicit trustworthy ratio.
- [ ] MI EU display with explicit trustworthy ratio.
- [ ] inherited/unknown client ratio falls back to FE instead of inventing a value.

**Exit condition:** installed optional mods exchange the correct amount, side/rate/tier rules work, and long storage remains conserved.

---

# Phase 4 — R2-specific upgrade and storage acceptance

**Goal:** prove the exact issues that caused R2 are gone in the client/server runtime.

## Reinforced Furnace

- [ ] Baseline cycle with zero upgrades: record ticks and FE consumed.
- [ ] Install Speed upgrades: cycle time changes by configured formula.
- [ ] Install Energy upgrades: FE-per-cycle changes by configured formula.
- [ ] Combine Speed + Energy: both effects apply without double-counting.
- [ ] Restart with upgrades installed: effects remain.

## Analysing Chamber

- [ ] Repeat the same zero/Speed/Energy/combined comparison.
- [ ] Confirm analysed output state remains correct.
- [ ] Confirm diagnostics report the actual upgraded FE requirement.

## Transfer Upgrade

- [ ] Output only through an allowed output/automatic face.
- [ ] Disabled/Input face does not export.
- [ ] `itemAutomation=false` blocks it as intended.
- [ ] number moved respects `itemsPerTransfer × count` and `maxTransfer`.
- [ ] newly received resources do not create an unintended transfer loop.

## Void Upgrade

- [ ] With normal output room available, output is inserted, not voided.
- [ ] With output blocked and Void enabled, only the still-pending blocked output is discarded.
- [ ] Existing contents already in normal output slots are not erased.
- [ ] `upgrades.allowVoid=false` disables destruction.

## Reinforced Chest / Storage UI

- [ ] Insert a bulk quantity greater than 64.
- [ ] Icon shows the item, not a misleading vanilla `64` overlay.
- [ ] Separate displayed number equals authoritative bin quantity.
- [ ] Tooltip/withdraw/deposit behavior agrees with the displayed amount.
- [ ] Save/restart retains bin contents exactly.

**Exit condition:** the two previously broken special upgrade paths and the double-number storage UI are demonstrably corrected in runtime.

---

# Phase 5 — Field Guide and UI acceptance

**Goal:** prove the guide is useful and stable across actual client layouts.

- [ ] Compare Calculator guide footprint against other Foundations guides at the same GUI scale.
- [ ] Normal desktop target remains approximately 640×360 rather than taking over the full screen.
- [ ] Wide/two-page mode shows correct binding/margins/tabs.
- [ ] Narrow/windowed mode switches cleanly without clipping controls.
- [ ] Left contents and right entry content scroll independently where expected.
- [ ] Search works for entry text and item IDs.
- [ ] bookmarks/save/history/back/forward work.
- [ ] Welcome and First 10 Minutes are understandable to a new player.
- [ ] live server values refresh and use readable labels.
- [ ] no raw debug entry IDs appear in normal player presentation.
- [ ] all ten chapter tabs fit and remain clickable.
- [ ] blueprints and recipe panels remain readable at several GUI scales.
- [ ] Patchouli mirror opens the same logical guide without generating a second book item.
- [ ] removing Patchouli does not break the native guide.
- [ ] master-reader preference safely falls back to native reader when no compatible host exists.

**Exit condition:** guide has no scaling/navigation/content-regression issue severe enough to require a 2a hotfix.

---

# Phase 6 — Full progression acceptance

**Goal:** prove that “the pieces exist” also means a player can reasonably progress through them.

Run a fresh or controlled survival-style progression. Do not use commands to skip every dependency except where specifically testing a later isolated stage.

Suggested path:

1. first Calculator;
2. first practical power source;
3. Power Cube charging/discharging;
4. extraction of circuit material;
5. dirty/damaged chip processing;
6. restoration/reassembly as appropriate;
7. analysis;
8. Research Chamber sample study and unlock;
9. module crafting/Module Workstation;
10. installed module selection/use;
11. storage/automation;
12. at least one greenhouse tier;
13. one advanced/world-machine path;
14. save/restart and continue progression.

Check during the run:

- [ ] no recipe dead end;
- [ ] JEI/EMI agree with actual server recipes;
- [ ] field guide instructions agree with actual machine behavior;
- [ ] research locks explain themselves instead of silently doing nothing;
- [ ] blocked output/insufficient power/incorrect-side statuses are actionable;
- [ ] dismantling retains the state it promises to retain;
- [ ] KubeJS/CraftTweaker modified recipes still show/execute correctly if using an integration pack.

**Exit condition:** progression is not merely structurally reachable; it is practically usable.

---

# Phase 7 — Protection, multiplayer and performance/soak

**Goal:** prove 2a behaves correctly on the kinds of servers it is intended to survive.

## Protection

- [ ] Test at least one actual claim/protection mod used by the target pack.
- [ ] Owner allowed inside own claim.
- [ ] Owner denied outside permission boundary.
- [ ] non-owner denied where expected.
- [ ] denied build/harvest action does not consume FE/materials or award drops.
- [ ] greenhouse construction/demolition respects claim boundary.
- [ ] Assimilator/nutrition/leaf interactions respect protection.
- [ ] special external plant adapter documents/checks its real affected positions.

## Multiplayer

- [ ] two players interact with the same machine without duplicated output/state corruption;
- [ ] research ownership/sharing mode behaves as configured;
- [ ] reinforced chest opener/lid state remains sane with multiple viewers;
- [ ] server/client long energy values stay synchronized;
- [ ] config is server authoritative.

## Performance/soak

Build a deliberately larger network than ordinary early-game use:

- many powered processing machines;
- long buffers;
- item automation;
- Transfer upgrades;
- greenhouses;
- one or more world machines;
- mixed power integrations if possible.

Then:

- [ ] compare server tick behavior to the stable R9/early-2a baseline;
- [ ] observe allocations/GC if a profiler is available;
- [ ] run long enough to cross periodic sync/structure intervals repeatedly;
- [ ] unload/reload chunks;
- [ ] stop/restart server several times;
- [ ] confirm no runaway log spam;
- [ ] confirm no energy/item conservation drift;
- [ ] confirm no repeated-world-scan hot path appears unexpectedly.

No source-only test should be used to claim a production TPS benchmark.

**Exit condition:** no reproducible correctness or performance regression significant enough to block a stable 2a freeze.

---

# 9. Final 2a freeze criteria

Freeze `0.0.2a.R2` (or one narrowly-scoped R3 only if testing finds an actual defect) when all of the following are true:

- [ ] Phase 0 drift/documentation cleanup is complete.
- [ ] Native build/JUnit passes.
- [ ] 124+ standalone GameTests pass.
- [ ] 124+ integration/KubeJS GameTests pass.
- [ ] release audit passes.
- [ ] migration/long-energy copied-world tests pass.
- [ ] every installed power adapter passes its practical transfer checks.
- [ ] R2 upgrade and bulk-storage bugs are confirmed fixed in game.
- [ ] guide/manual client acceptance passes.
- [ ] full survival progression passes.
- [ ] protection/multiplayer checks required by the deployment target pass.
- [ ] large-network soak shows no unacceptable regression.
- [ ] validation logs/screenshots are archived.

Then:

1. archive the final source ZIP and JAR;
2. archive SHA-256 hashes;
3. archive `validation/0.0.2a.R2/` logs;
4. tag/label the source as the frozen 2a baseline;
5. branch future feature work into the next alpha;
6. do not silently keep adding new 2a systems after the freeze.

---

# 10. Source troubleshooting map — “if this fails, start here”

This section is meant to make future debugging faster.

| Symptom | First source areas to inspect |
|---|---|
| Machine FE lost/capped after restart | `core/StoredEnergy.java`, `content/MachineBlockEntity.java` save/load |
| Powered Calculator/module FE lost | `content/CalculatorItem.java`, energy DataComponents |
| >2.147B value displays as 2.147B | `api/LongEnergyStorage.java`, `api/FoundationsEnergy.java`, Calculator Screen route, client sync |
| FE cable works but native J/EU does not | relevant `compat/*Energy.java`, `PowerPolicy.java`, `EnergyConversion.java` |
| GT packet refused unexpectedly | `GregTechEnergy.java`, GT voltage/amp config, `PowerDiagnostics.java` |
| Mek/MI ratio wrong | adapter + `EnergyRatio`/`EnergyConversion`, generated power config |
| Energy created/lost in round trip | `EnergyRatio.java`, `EnergyConversion.java`, `LongEnergyBridge.java`, loss settings |
| Speed/Energy upgrade has no effect | `MachineBlockEntity.supportsUpgrades`, `upgradeEnergyCost`, `upgradeProcessTicks`, special processing path |
| Transfer Upgrade ignores side | `MachinePrograms` transfer/export path, `MachineBlockEntity.itemOutput()` / side program state |
| Void removes wrong items | pending-output path in `MachineBlockEntity` / `ProcessTransactions`, void post-insertion logic |
| Reinforced Chest shows double quantity | `BulkStorageMenu.java`, `BulkStorageScreen.java`, sync stack normalization |
| Bulk items disappear | `BulkStorage.java`, save/load and menu transfer paths |
| Machine accepts wrong inventory slot | `MachineBlockEntity.inputCount`, automation wrapper, menu slot definitions |
| Machine uses wrong energy direction | `energySideInput`, `energySideOutput`, `MachineProfiles` |
| Machine UI status disagrees with behavior | `MachineDiagnostics.java`, actual process path, client update tags |
| Recipe exists but machine says no match | `RecipeIndex.java`, `ProcessRecipe.java`, `RecipePolicies.java`, machine recipe family |
| Output blocked/duplicated | `ProcessTransactions.java`, pending output persistence, flush path |
| JEI/EMI differs from server | viewer integration + server recipe index / recipe sync |
| KubeJS process recipe missing | `docs/KUBEJS.md`, KubeJS registration/schema, recipe reload invalidation |
| Research stays locked | `ResearchData.java`, `ResearchProgram.java`, process recipe research group |
| Research owned by wrong player | machine owner assignment + `ResearchData` sharing config |
| Greenhouse will not construct | `GreenhouseProgram.java`, `GreenhouseBlueprint.java`, `MachineWorldActions.java` |
| Greenhouse edits protected land | `MachineWorldActions.java`, owner, claim-mod event behavior, adapter callback scope |
| Special crop does nothing | `FoundationsPlants.java`, specific `PlantAdapter`, legacy callback config |
| Weather/world machine does nothing | `MachinePrograms.java`, relevant machine config and owner/world conditions |
| Guide content missing/stale | `tools/author_field_guide.py`, guide JSON, `GuideCatalog` / client guide package |
| Patchouli differs from native guide | `tools/generate_patchouli_bridge.py` and generated Patchouli resources |
| Live guide values wrong | `guide/GuideSnapshots.java`, network guide query/snapshot payloads |
| Master reader does not open | Guide API host/interop metadata and client preference/fallback |
| Config docs disagree with runtime | `tools/generate_config.py`, `CalculatorConfig.java`, generated configuration catalog |
| Final JAR contains wrong files/version | `tools/check_release.py`, `gradle.properties`, build task output |

---

# 11. Architecture hotspots worth knowing before adding features

## 11.1 `MachineBlockEntity` is powerful but centralized

Today it owns many concerns:

- inventory;
- energy;
- side modes;
- automation role rules;
- bulk storage selection;
- upgrades;
- recipes;
- persistence;
- sync;
- special-case machine role classification.

Examples of hardcoded declarations include `inputCount()`, `hasOutputs()`, `hasBatterySlot()`, `supportsUpgrades()`, `usesEnergy()` and energy/item side rules.

That centralization made the port practical, but it also means a new machine can be added to one list and accidentally omitted from another. The Reinforced Furnace/Analysing Chamber R2 bug is exactly the type of mismatch a more declarative model could prevent.

## 11.2 `MachinePrograms` is the special-machine dispatch hub

World/special machine behavior is switch-dispatched in `MachinePrograms`.

Before adding a new special machine, audit:

- machine content registration;
- machine profile;
- input/output count and slots;
- energy role;
- upgrade support;
- automation role;
- program dispatch;
- save/sync state;
- guide entry;
- configuration keys;
- GameTest coverage.

## 11.3 Long energy has two API layers by necessity

Do not “simplify” the current system by forcing every external API through an `int` FE store.

The intended layering is:

```text
Long Foundations FE store
        |
        +-- LongEnergyStorage / native long-capable routes
        |
        +-- bounded NeoForge IEnergyStorage facade
```

The int facade is compatibility; it is not the source of truth for a 2a buffer.

## 11.4 Generated data should be changed at the generator

Several resources are generated. When fixing a wording/count/schema issue, change the generator and regenerate instead of hand-editing only the generated output.

Important generators/checkers:

- `tools/generate_config.py`
- `tools/author_field_guide.py`
- `tools/generate_patchouli_bridge.py`
- other `tools/generate_*` scripts
- `tools/validate_progression.py`
- `tools/validate_assets.py`
- `tools/check_release.py`

---

# 12. Test coverage review and targeted gaps

The current 124 GameTests are spread across:

| Test class | Declared GameTests |
|---|---:|
| `Alpha2FinalizationGameTests` | 6 |
| `Alpha2GameTests` | 4 |
| `CalculatorGameTests` | 13 |
| `CircuitProcessingGameTests` | 3 |
| `MachineOperationGameTests` | 10 |
| `MultiblockGameTests` | 4 |
| `R2GameTests` | 4 |
| `R4GameTests` | 18 |
| `R5GameTests` | 7 |
| `R6GameTests` | 8 |
| `R7GameTests` | 20 |
| `R8GuideGameTests` | 8 |
| `WorldProgramsGameTests` | 19 |
| **Total** | **124** |

### Heuristic direct-reference gaps

A simple source scan was performed for direct machine-ID literals in GameTest source. This is only a **heuristic**: a machine can be covered through generic/shared-path tests without its ID appearing directly.

The following guide-listed machine IDs currently have no direct literal occurrence in GameTest source:

- `algorithm_assimilator`
- `algorithm_separator`
- `atomic_calculator`
- `calculator_screen_block`
- `creative_power_cube`
- `glowstone_extractor`
- `rain_sensor`
- `starch_extractor`
- `stone_assimilator`

Likewise, these module IDs have no direct literal occurrence in current GameTest source:

- `advanced_terrain_module`
- `crafting_calculator`
- `health_module`
- `jump_module`
- `nutrition_module`
- `warp_module`

**Recommendation:** if 2a manual acceptance finds any uncertainty in these areas, add focused tests before freezing. Otherwise, carry them as a targeted next-alpha test expansion rather than destabilizing 2a solely to increase a count.

---

# 13. Deliberate limitations / non-goals of current 2a

These should not be mistaken for accidental missing code.

## 13.1 No automatic 1.12 world converter

The project does not promise old Calculator/Sonar-Core world/ID migration from the original 1.12 mod. The current migration guarantees are about the recent Foundations source line (for example R9 → 2a energy format).

A historical world converter would be a separate project with a carefully defined input format and test worlds.

## 13.2 English-first resources

Translations have deliberately not been a current release target.

## 13.3 No binary compatibility with old Sonar Core addons

New addons must target current Foundations APIs/current mod APIs.

## 13.4 No claim of universal crop compatibility

Generic crop support cannot safely invent semantics for every third-party plant. Special plants need adapters.

## 13.5 AE2 support is not a custom native-grid implementation

The AE2-specific Calculator integration is primarily powered-item support. Grid power uses normal FE interoperability.

## 13.6 No claim that every conversion route in a giant pack is globally loop-proof

Calculator's own conversion math is conservative and its diagnostics warn about known mismatch conditions. A modpack can still introduce a second independent converter with incompatible ratios. Pack authors must align conversion policies.

## 13.7 Shared modern machine GUI is intentional

The port does not promise exact historical 1.12 GUI pixel parity for every machine.

---

# 14. Expansion roadmap — not currently implemented

Everything in this section is a **future recommendation**, not a claim about existing R2 behavior.

The best next alpha should improve architecture and scale first, then add more compatibility/content on top of that stronger base.

# Future Phase A — Data-driven machine definitions

**Recommended priority: very high.**

Create a `MachineDefinition`/profile layer that owns, in one place:

- input count;
- output role;
- battery slot role;
- energy input/output/storage/generator role;
- upgrade compatibility;
- transfer rates;
- item automation roles;
- special program handler;
- diagnostic name/capabilities;
- default capacity;
- guide/reference metadata where appropriate.

Why:

- reduces scattered `switch`/`Set.of(...)` declarations;
- makes feature additions harder to partially wire;
- prevents another “GUI says upgrade supported, execution path ignores it” class of bug;
- makes machine test generation easier;
- opens a path to pack-defined machine profiles without allowing arbitrary unsafe code.

This can remain a Java registry first. It does **not** need to become fully arbitrary JSON scripting on day one.

# Future Phase B — Complete 64-bit operation scaling

2a made **storage** long-valued. A later scale alpha can decide whether to generalize the rest:

- long process FE cost;
- long configured charge rate;
- long transfer budgets for APIs capable of it;
- long generator production;
- UI formatting for extremely large per-tick rates;
- overflow-safe multiplication in every preset/profile calculation.

Compatibility rule:

- NeoForge `IEnergyStorage` remains int-bounded per API contract;
- long-capable native routes can use long operations;
- do not fake a long FE API by violating the standard interface.

# Future Phase C — Foundations Master Guide mod

The current Calculator Guide API is ready to be one publisher in a larger guide ecosystem, but the aggregator is not built.

A separate Master Guide mod could:

- discover installed Foundations Guide API publishers;
- display Calculator, Soil, PL4, Economy, Steam Power, etc. in one book;
- preserve stable per-mod entry IDs;
- share bookmarks/history/search;
- open cross-guide links;
- keep each source mod independently usable when the master is absent;
- avoid creating one physical book per mod for players who prefer the master guide.

Do not move Calculator's guide content into the master mod. Calculator should remain the owner/source of its own documentation.

# Future Phase D — Guide API 1.x maturation

Potential additions after proving co-loading across several Foundations mods:

- explicit cross-guide link contract;
- standardized guide discovery metadata;
- richer portable diagram/table components;
- optional live snapshot schemas with clear server/client authority;
- master-reader capability negotiation;
- API compatibility test kit;
- controlled Maven/SDK publication process.

Keep arbitrary commands, arbitrary URLs and reflective code execution out of data-driven guide content.

# Future Phase E — More dedicated crop/plant adapters

Add explicit optional integrations for complex modern crop mods actually used in the target pack.

Adapter requirements should include:

- accepted seed/item;
- exact affected positions;
- protection preflight positions;
- plant operation;
- mature-state recognition;
- harvest reset/removal contract;
- returned drops;
- no double consumption/drop duplication.

Prefer this over enabling the broad legacy callback globally.

# Future Phase F — Additional modern technical integrations

Only add an integration when it provides real gameplay value and there is a stable/current 1.21.1 API.

Potential examples:

- deeper AE2 integration if a native grid-specific behavior is genuinely useful beyond FE;
- additional modern electrical APIs used by the pack;
- explicit interoperability with other Foundations modules.

Avoid re-implementing dead 1.12 interfaces merely to check a parity box.

Create rotation and Botania mana should remain separate gameplay systems unless a deliberate gameplay converter/block is designed; they are not simply another electrical unit ratio.

# Future Phase G — Upgrade-system expansion

Once machine definitions are centralized, upgrades can become more expressive without hidden special cases:

- declarative supported-upgrade set per machine;
- per-machine max counts;
- diminishing returns curves;
- speed/energy tradeoffs;
- explicit generator/storage upgrades if desired;
- richer machine UI showing before/after effective values;
- server-side upgrade policy presets;
- exact diagnostics explaining why an upgrade is ignored/limited.

Do not add more upgrade types until the support/diagnostic contract is centralized.

# Future Phase H — Configuration UX

4,272 keys provide enormous pack control, but raw TOML is not the friendliest operator experience.

Possible optional admin UX:

- searchable config/report GUI;
- machine-by-machine effective-setting inspector;
- named profile export/import;
- diff against defaults/preset;
- “why is this value effective?” provenance display;
- read-only client view plus permission-gated server edits if ever added.

Raw server configuration should remain authoritative and portable.

# Future Phase I — Built-in performance instrumentation

Add optional lightweight counters rather than always-on heavy profiling:

- active machine counts by family;
- process cycles completed;
- recipe selection/cache hit/miss;
- automation transfers;
- greenhouse tend/build scan counts;
- sync packet counts;
- power adapter transfer counts;
- denied world actions;
- slow-operation warnings sampled at a controlled interval.

This would make future performance reports evidence-based without requiring a full profiler for every bug report.

# Future Phase J — Advancements / guided progression

Optional advancements could complement—not replace—the Field Guide:

- first Calculator;
- first powered machine;
- first clean circuit;
- first analysis;
- first research unlock;
- first installed module;
- first greenhouse;
- first automation connection.

They should not silently become a second research-gating system unless intentionally designed that way.

# Future Phase K — Art/audio/presentation polish

Potential non-system work:

- replace remaining Calculator Screen fallback visual;
- more bespoke guide illustrations;
- additional multiblock diagrams;
- consistent machine animation polish;
- sound/particle feedback where gameplay benefits;
- optional accessibility/readability settings.

# Future Phase L — Platform/API modernization

The pinned 1.21.1 build currently emits some deprecation warnings from NeoForge event-bus and JEI callback APIs.

Current policy is correct: do not risk breaking the pinned production target just to make warnings disappear.

When the project moves to a newer supported platform/API line:

- migrate deprecated event subscription forms;
- migrate JEI callbacks;
- centralize/refresh optional integration dependency pins;
- run full integration matrix again;
- do not mix a platform-port rewrite with unrelated major gameplay changes if avoidable.

# Future Phase M — Translations

If the project later wants localization:

- move player-facing generated text behind translation keys where practical;
- define guide localization strategy;
- keep item/machine IDs stable;
- add translation validation/fallback tooling.

This is future scope, not a current 2a requirement.

# Future Phase N — Historical-world conversion

Only pursue if there is real demand and access to representative old worlds.

A safe converter would need:

- explicit supported original versions;
- old ID/state mapping;
- old tile/entity NBT mapping;
- inventory/item state mapping;
- test worlds with known expected results;
- dry-run/report mode;
- backups and one-way migration warnings.

Do not advertise old-world conversion until those fixtures exist.

---

# 15. Suggested next-alpha priority order

After 2a is frozen, a strong next-alpha plan would be:

## Tier 1 — architecture and confidence

1. Data-driven/centralized machine definitions.
2. Targeted GameTests for the direct-reference gaps identified above.
3. Complete long-operation audit and decide which remaining int limits should become long-capable.
4. Built-in diagnostic/performance counters.

## Tier 2 — ecosystem

5. Foundations Master Guide standalone mod.
6. Guide API co-loading test kit/maturation.
7. Dedicated adapters for external crops actually used by the modpack.
8. Additional modern energy/tech integrations only where needed.

## Tier 3 — UX/content polish

9. Better effective-config GUI/reporting.
10. Advancements/onboarding.
11. remaining art/animation/sound polish.
12. localization if/when desired.

This order intentionally strengthens the architecture before adding another large collection of machine-specific switches.

---

# 16. Manual machine-reference inventory

The current Field Guide contains dedicated entries for these 45 machine/world-machine IDs:

1. `advanced_greenhouse`
2. `advanced_power_cube`
3. `algorithm_assimilator`
4. `algorithm_separator`
5. `analysing_chamber`
6. `atomic_calculator`
7. `atomic_multiplier`
8. `basic_greenhouse`
9. `calculator_locator`
10. `calculator_plug`
11. `calculator_screen_block`
12. `co2_generator`
13. `conductor_mast`
14. `crank_handle`
15. `creative_power_cube`
16. `docking_station`
17. `dynamic_calculator`
18. `extraction_chamber`
19. `fabrication_chamber`
20. `flawless_greenhouse`
21. `gas_lantern_off`
22. `glowstone_extractor`
23. `hand_cranked_generator`
24. `health_processor`
25. `hunger_processor`
26. `magnetic_flux`
27. `module_workstation`
28. `power_cube`
29. `precision_chamber`
30. `processing_chamber`
31. `rain_sensor`
32. `reassembly_chamber`
33. `redstone_extractor`
34. `reinforced_chest`
35. `reinforced_furnace`
36. `research_chamber`
37. `restoration_chamber`
38. `scarecrow`
39. `starch_extractor`
40. `stone_assimilator`
41. `stone_separator`
42. `storage_chamber`
43. `transmitter`
44. `weather_controller`
45. `weather_station`

When adding/removing a machine in a future alpha, use this list as a reminder that content, configuration, guide and tests should move together.

---

# 17. Module-reference inventory

Current dedicated guide entries cover 13 module/calculator-module items:

1. `advanced_terrain_module`
2. `atomic_terrain_module`
3. `crafting_calculator`
4. `energy_module`
5. `health_module`
6. `hunger_module`
7. `jump_module`
8. `locator_module`
9. `nutrition_module`
10. `smelting_module`
11. `storage_module`
12. `terrain_module`
13. `warp_module`

Future module work should verify:

- craftability;
- installability;
- selection behavior;
- stored state;
- energy cost/capacity;
- client tooltip;
- Field Guide entry;
- native GameTest or deliberate shared-path coverage.

---

# 18. Final-release evidence package recommendation

Once R2 passes, create one archival folder/ZIP containing:

```text
FoundationsCalculator-0.0.2a.R2.jar
FoundationsCalculator-0.0.2a.R2-sources.jar
FoundationsCalculator-0.0.2a.R2-source.zip
SHA256SUMS.txt
validation/0.0.2a.R2/gametest-standalone.log
validation/0.0.2a.R2/gametest-integrations.log
validation/0.0.2a.R2/<JUnit/build report or summary>
validation/0.0.2a.R2/<manual acceptance notes>
validation/0.0.2a.R2/<performance notes>
FOUNDATIONS_CALCULATOR_MASTER_STATUS_AND_ROADMAP_2026-09-29.md
```

Optional but useful evidence:

- screenshots of the final guide at representative GUI scales;
- screenshot of >2.147B energy surviving restart;
- screenshot/log of GT/Mek/MI/GrandPower transfer acceptance;
- screenshot of Reinforced Furnace/Analyser upgrade before/after values;
- screenshot of Reinforced Chest corrected bulk quantity UI.

The goal is to make the next development session start from evidence, not from “I think this was the last good JAR.”

---

# 19. Quick-return checklist for a future session

If returning after a long break:

1. Read this document.
2. Confirm `gradle.properties` version.
3. Run `VALIDATE_0_0_2a_R2.bat` if R2 was not previously frozen.
4. Check `validation/0.0.2a.R2/` for preserved passing logs.
5. Do **not** reopen resolved R5–R9 issues unless a current regression reproduces them.
6. Search `PORT_STATUS.md` and the current finalization doc before assuming a feature is missing.
7. For a new machine, audit every role in the `MachineBlockEntity`/`MachinePrograms` source map.
8. For generated content, edit the generator, not only the generated file.
9. Keep the Guide API stable unless a real cross-mod requirement needs a versioned change.
10. Put new feature work in the next alpha after the 2a freeze.

---

# 20. Bottom line

Foundations Calculator is already a large, functional modern port rather than an unfinished shell. The remaining 2a work is primarily **proof and cleanup**:

- fix the handful of current-source metadata/documentation drift items;
- get the exact R2 native build/test matrix fully green;
- prove long energy/migration and all native adapters in a copied real world;
- prove the R2 upgrade/storage fixes;
- finish survival, protection, multiplayer and soak acceptance;
- archive the evidence and freeze the alpha.

The most valuable expansion after that is **not another pile of hardcoded machine cases**. It is to centralize machine definitions, finish the long-operation architecture where useful, strengthen direct test coverage, and then build the shared Foundations ecosystem—especially the Master Guide and explicit modern integration adapters—on top of that cleaner base.

