# Foundations Calculator 0.0.1a.R7
## Automation, owner protection and tick-work revision

Minecraft **1.21.1**, NeoForge **21.1.250**, Java **21**. The core remains embedded. **Source candidate only: no dependency-resolved R7 JAR or native runtime pass is included.** The wrapper download failed with `UnknownHostException: services.gradle.org`; the actual failure is preserved in `validation/r7/native-build-attempt.log`. R5 remains the last runtime-validated release recorded by this project. A successful local build is required before testing R7.

## Implemented changes

### Item automation follows one policy

Transfer Upgrades now use the same machine master switch, sided permissions and output-slot interface as external item pipes. Input and Disabled faces cannot be used as active export faces. Auto and Output faces can export, subject to the existing item/slot rules, neighbor capability and configured transfer quota. Cached inventory views read the current side/master policy on every operation; disabling automation does not require reconnecting a pipe. `isItemValid` agrees with insertion permissions, including the existing downward-input exception.

Greenhouse seed import honors its front-side input and `itemAutomation` settings. Its external harvest output honors front-side output and the same master. Blocked exports retain products in internal/pending storage. Machine-internal crop tending and manual menu operations are not disabled by a switch intended for external item automation. Side modes are unchanged: 0 Auto, 1 Input, 2 Output, 3 Disabled. Direct energy controls remain separate and all R6 conversion controls are retained.

### Owner-attributed world actions

`MachineWorldActions` centralizes preflight for greenhouse construction, demolition, farmland, water, single-block crop planting/harvesting, normal crop growth, Scarecrow growth, Assimilator leaf harvesting and health/hunger nutrition-network leaf collection. Controller and target must be loaded, in bounds, and unchanged. Existing block entities and unbreakable targets are refused. There is no forced chunk load and no positive permission cache.

The acting identity is an owner-UUID FakePlayer from NeoForge's per-level factory. The machine does not retain a FakePlayer reference. Held item and position are restored in `finally`, including a throwing listener. Permission handling checks owner interaction rights, posts `MachineWorldActionEvent`, posts `BlockEvent.BreakEvent` for replacement/removal of an existing non-air block, and posts a predicted `BlockEvent.EntityPlaceEvent` for known non-air replacements. Target, owner and controller validity are rechecked after listeners. A target changed by another listener is not overwritten or rolled back by Calculator.

**Preflight placement semantics:** `getState()`/`getPlacedBlock()` describe the proposed block, while the world and `BlockSnapshot` still contain the old block. This intentionally avoids temporarily placing and reverting a block when permission is denied. It is not a reproduction of every aspect of Minecraft's ordinary post-placement event sequence. Claim integrations expecting different hooks or actual post-placement world state require dedicated validation or a listener for `MachineWorldActionEvent`.

Recovery items from demolition are queued only after successful removal. A denied native build, soil/water action, planting, harvest or growth does not consume Calculator's materials/FE or award its drops/counters. Build resources and soil/water energy are checked again after event listeners. This cannot undo arbitrary side effects deliberately performed by a third-party listener or a broken capability. Ordinary future fluid flow and block-neighbor behavior are not globally intercepted by this guard. Weather, teleports and pre-existing player tools are not being advertised as a new universal permissions framework.

Controllers placed by a player keep their owner. Old ownerless controllers pause protected operations by default; an authorized player can use the controller to assign its missing owner. An existing owner's UUID is not reassigned. Owner, storage, inventory, pending outputs, sides and research data retain their existing save format. The new `protection.requireOwner=false` compatibility option uses a synthetic identity; it does not turn off all permission events.

### External plants: explicit compatibility boundary

Ordinary `CropBlock` planting/harvesting has a bounded single-block path. Known vanilla/Calculator crop growth uses the owner-checked path. An opaque `SpecialPlantable`, arbitrary bonemeal callback, or external multi-block growth operation cannot safely be treated as a one-block change: it may modify positions outside the checked target.

Consequently **`protection.allowLegacyPlantCallbacks=false` is the new default**. Unbounded callbacks, including sapling/tree growth without a protected adapter, pause rather than pretend to be claim-safe. An addon can implement `PlantAdapter.supportsProtectedActions`, `plantProtected`, `harvestProtected`, `canGrowProtected` and `growProtected`, using the supplied machine and shared guard for every affected position. Adding default interface methods preserves source compatibility for existing implementations; it does not certify arbitrary compiled addons.

Setting the legacy option to true deliberately restores the opaque callback path after origin checks. It relaxes protection for its additional changes. Use it only after validating the specific plant and permission setup. The old raw public plant/growth methods are retained for addon compatibility, with permission responsibility remaining with their caller.

Power inspection now reports the last blocked protected world action and its position. Successful retries clear that target's warning; unrelated successes do not hide a failure elsewhere. No transfer-history database or per-tick diagnostic neighbor scanner was added. R6's Power button remains a chat report, not an inline panel.

### Reduced repeated work

- Machine client sync compares the complete existing display packet state before copying it and requesting a packet. Unchanged state does not repeatedly send; changed settings still synchronize while idle, redstone-paused, disabled or output-blocked. Open menus retain their existing inventory/menu synchronization. Default periodic display cadence remains 10 ticks, with immediate chest-lid transitions.
- Reinforced chests track opener UUIDs through menu open/close. A periodic check validates only that small set for stale/disconnected viewers; it does not scan every player for every chest every tick. Runtime openers/lid state are not restored as a permanently open saved chest.
- A machine caches immutable greenhouse geometry: blueprint, planted cells, water cells and a position set. Rotation, tier/length or the relevant size limit invalidates geometry. World validity, protection and crop states are still checked live. Fallow data is decoded/filtered/encoded once per tending pass, not once per crop.
- Deterministic position phases stagger periodic greenhouse/carbon/tending, dynamic-structure and leaf-harvest checks. Recipe progress, generator output and energy transfers keep their existing tick behavior. Disabling stagger restores common phases. This does not reduce the configured number of periodic opportunities.
- Recipe families are sorted and policy-applied once per recipe/config generation. A bounded per-machine selection caches hits and misses until input count/components, owner, unlock generation, recipes or server configuration changes. Ingredients are revalidated at commit. Mastery counters do not invalidate recipe eligibility on every completed batch. Values in the weak manager cache do not retain the manager key.
- The Field Guide caches related recipe searches rather than rescan each rendered frame. It labels prose numbers as defaults and points to effective recipe values. A full dynamic, machine-specific handbook rewrite remains outside R7.

These are source-level efficiency changes, **not measured TPS, frame-rate or memory-leak claims**. Profile a native build under representative load before publishing performance figures.

## Seven new synchronized server settings

All **4,202 R6 keys and their defaults remain unchanged**. R7 adds seven keys for **4,209** total. Runtime and generated documentation catalogs match.

| Key | Default | Meaning |
|---|---|---|
| `protection.requireOwner` | `true` | Require a stored owner for protected machine world changes. |
| `protection.allowLegacyPlantCallbacks` | `false` | Explicit opt-in for origin-checked but otherwise opaque plant/growth callbacks. |
| `performance.changeOnlySync` | `true` | Suppress unchanged display-state packets. |
| `performance.clientSyncInterval` | `10` | Display comparison/periodic sync interval, 1–200 ticks. |
| `performance.chestRecheckInterval` | `20` | Validate tracked chest openers every 1–1,200 ticks. |
| `performance.staggerWork` | `true` | Spread periodic work using deterministic block-position phases. |
| `performance.cacheMachineRecipes` | `true` | Enable bounded per-machine recipe-selection reuse. |

Set these in the world's `foundations_calculator-server.toml`; pack defaults belong in `defaultconfigs`. Stop/edit/restart is the simplest repeatable configuration workflow. SERVER config loading/reloading invalidates policy caches. Code which directly calls `.set` on an exposed `CalculatorConfig.value` must call **`CalculatorConfig.settingsChanged()`** afterward. `/reload` handles data packs, not every TOML reload mechanism. Existing EU/J/AE ratios, scope gates, loss settings, native routing and per-machine energy limits are documented in `docs/POWER.md` and are not renamed.

## Install source and build

The separate combined updater accepts the exact recovered **R5 or R6 source**. Extract it and run `UPDATE_TO_R7.bat` from anywhere; choose the folder containing `gradlew.bat`. It checks the version and every changed-file preimage before modifying anything, validates payload hashes, backs up affected files under `.foundations_update_backups`, applies the delta and offers a local `clean test build`.

It refuses conflicting local edits rather than overwriting them. Child junctions/symlinks on update paths are rejected. A copy/check failure triggers restoration from backup. The explicit `ROLLBACK_UPDATE.bat` restores the actual prior version and refuses to erase new edits made after the update. Source changes do not install/delete mod JARs or edit worlds/configuration. A failed native build leaves the updated source and recovery backup available, not a fake successful binary.

For a fresh source checkout run `BUILD.bat` or `gradlew.bat clean test build --console=plain`. The expected real artifact is `build/libs/FoundationsCalculator-0.0.1a.R7.jar`. First build needs Java 21 and internet access to all pinned dependencies, including compile-only integrations. The unchanged GT dependency is a development snapshot, not a new stable release. Do not place a source ZIP in `mods`, rename an old JAR to R7, or replace a working mod until native build/runtime checks pass.

## Remaining release gates

Run `VALIDATE_R7.bat`, then `VALIDATE_R7.bat integrations`. The build requires Minecraft's positive report of **at least 102 required tests passed**, not simply exit code zero. There are 20 newly authored R7 GameTests; none ran in this environment. The isolated policy checks and syntax parser do not substitute for API compilation. The integration run must actually include its mods; standalone skips are not integration certification.

Use `docs/R7_ACCEPTANCE_CHECKLIST.md` for the copied-world client/progression/protection/restart checks and for profiling. Full survival progression, long-running multiplayer, actual claim-mod combinations and performance measurements are still outstanding. The original model-size fix, embedded core, all 910 recipe files, and R6 power systems are retained. Translations and old-world migration remain excluded; original pixel-perfect GUI parity, a complete dynamic handbook, specialized integrations and long-valued internal energy storage were not added in R7.
