# R7 development status

R7 is a source candidate. Current evidence lives in `validation/r7`, not the historical root validation. Run `python tools/run_r7_offline_tests.py` for isolated policies and parse-only checks; **neither is a native compile**. Native required GameTests total 102; at least 7 JUnit tests must pass. See the R7 acceptance checklist before deployment.

Recipe/config caches invalidate on native config events and RecipeIndex.clear. Code-side mutation of a raw CalculatorConfig ConfigValue requires settingsChanged(). Runtime geometry/openers/display snapshots are cleared on BE load/removal, not serialized as durable state. Do not cache player objects, permission positives, or live world validity in these caches.

The sections below document the preserved pipeline and **historical R5** graphical/native validation, not a fresh R7 success. Native build is still blocked here by missing downloads/cache. Isolated fixture code under tools is deliberately excluded from main/test/gameTest Gradle source sets.

# Development and provenance

Use JDK 21 and the checked-in Gradle wrapper. Main game code compiles against real Minecraft 1.21.1 / NeoForge 21.1.250 dependencies. The release contains only the main source set; `src/gameTest` and its scripts are fixtures, not shipped gameplay.

## Layout

- `content/`: registrations, items, blocks, shared machine entity, world programs, greenhouses, dynamic structure and modules.
- `core/`: FE, counted ingredients, allocation/transactions, bulk storage, nutrition/circuit state, recipes and configuration. There is no separate core module.
- `menu/`: authoritative server containers. `client/`: screens, field guide and block/item renderers.
- `src/main/resources`: original artwork, converted models, texture-atlas sources, modern recipes, tags, loot, sapling features, data maps and the optional KubeJS schema.
- `tools/content_catalog.json`, `legacy_fields.json`: registry and old metadata mapping.
- `tools/recipe_migration_report.json`: original import accounting; R4 repairs are explicit in `generate_extensions.py`.
- `tools/asset_overrides/`: hand-maintained replacements applied after legacy asset conversion.
- `tools/pending_content.json`: empty since R2; historical pending accounting retained by the generators.
- `reference/`: pinned upstream ZIP snapshots, outside Java source sets. See NOTICE for commit IDs.

## Regenerate imports

Extract the two pinned snapshots as sibling directories `upstream-calculator` and `upstream-core` beside this project. With JDK 21 on PATH and Python 3:

```sh
python3 tools/import_upstream.py
python3 tools/capture_crafting.py
python3 tools/generate_content.py
python3 tools/generate_data.py
python3 tools/generate_assets.py
python3 tools/import_greenhouse_blueprints.py ../upstream-calculator
python3 tools/import_legacy_models.py
python3 tools/validate_assets.py
```

Check the argument declaration at the top of an import script when changing source locations. Recipe capture uses small Java value-recording adapters to evaluate upstream declarations; these extract data and do not substitute for compiling the mod against Minecraft. Generated code/resources are included, so a normal build needs neither Python nor the old repositories.

Edit `tools/registry_declarations.json` and `tools/templates/Content.java.template` before regenerating Content.java. The declaration/catalog consistency check prevents losing revived content. Edit the data/extension/config generators for generated resources. A normal regeneration from checked-in source data is `generate_content.py`, `generate_data.py`, `generate_config.py`, then `generate_config_docs.py`; the data and asset imports apply R4 extensions automatically. The import/capture commands above refresh provenance and must be reviewed before merging their catalog with the maintained R4 registry. The main atlas declaration at `assets/minecraft/atlases/blocks.json` registers the preserved legacy texture directories. `validate_assets.py` checks inheritance, resources, atlas coverage, invalid paths and model bounds; vanilla references are checked against a cached official Minecraft client JAR when present.

## Verification

```sh
./gradlew clean build
./gradlew runGameTestServer
./gradlew runGameTestServer -PwithIntegrations -PwithKubeJS
```

JUnit covers ingredient allocation. GameTests exercise real registries/recipes, processing, overflow, persistence, automation, portable containers, nutrition, multiblocks, module ownership, bulk counts, display packets and FE conservation. The optional KubeJS run asserts native builder creation, removal, nested replacement and live resource reload. The Gradle GameTest task requires Minecraft's positive test-completion report in addition to exit code zero.

`runClient` / `runServer` provide normal development runs. The opt-in `runClient -PclientSmoke` walkthrough expects a world called `FoundationsSmoke` in `run/saves`. To reproduce it after GameTests, create an empty `run/saves/FoundationsSmoke` directory and copy `run/world/level.dat` into it (the flat chunks regenerate). The test builds a display scene, opens actual server menus, exercises ordinary client block-use and inventory-click packets with fuel/input insertion, smelting, output withdrawal, Power Cube charging and a complete dirty-chip cycle, also checks portable smelting, research/mastery synchronization, the animated chest, configuration UI, flat tools and classic palette, and saves screenshots to `validation/screenshots`, prints `FOUNDATIONS_CLIENT_SMOKE_PASSED`, and exits. Run it once with `-PwithIntegrations -PwithKubeJS` for EMI and the complete optional set, and separately with `-PwithJei -PwithJade -PwithKubeJS -PviewerSmoke` for the focused JEI UI/registered-handler transfer check. That targeted check uses real block-use and recipe-transfer packets and verifies authoritative ingredient/energy conservation. Use a fresh empty smoke world for each walkthrough. EMI replaces JEI’s runtime when both viewers are present. The portable-smelting checkpoint allows time for the configuration screen to pause the integrated server; the power checkpoint allows one 4-FE tick between separate client packets and still requires exact server conservation. It requires a working OpenGL display; the delivered walkthrough used Xvfb and Mesa software rendering. The smoke automation is excluded from release JARs.

The constrained build host required a local instrumentation workaround for an empty Java ProcessHandle executable path while NeoForm launched subprocesses. That workaround is outside this project and is not shipped or needed on normal JDK installations. Its authentication DNS endpoint was also unavailable; the resulting external public-key error does not prevent the offline development client/server tests. Mod/resource/script failures are not accepted as passing tests.

Validation establishes the tested operations, not every survival progression path or external mod combination. Exact evidence and limits are in `validation/RESULTS.md` and `PORT_STATUS.md`.

## R5 focused verification

`./gradlew runClient -PclientSmoke -PpowerSmoke -PwithIntegrations -PwithKubeJS` runs the R5 hand/model check against the optional mods. Use the fresh `FoundationsSmoke` world described above. It asserts the baked left/right first/third-person transforms for every registered block item, measures all animated machine meshes, captures seven held items, the left hand, a third-person mast and an inventory gallery, then reports `FOUNDATIONS_CLIENT_POWER_SMOKE_PASSED`.

`-PwithGtceu` loads the pinned official GTCEu 8.0.0-20260928.082845-98 snapshot (dependencies embedded by GT). `-PwithMi` loads MI 2.5.8 and GuideME (MI includes GrandPower 3.0.0); `-PwithGrandpower` can load the long-FE API alone. `-PwithIntegrations` includes these alongside the R4 optional integrations. All dependencies remain compile-only in the distributable.

The R5 server suite also verifies native block/item capabilities, simulations, disabled cached faces, overflow/rounding, GT amp budgets shared across faces, real GT batteries and real GT cable losses. Configuration documentation is generated from the same 4,164-setting catalog the runtime loads. `fix_item_transforms.py` is called after data and asset regeneration, so re-importing resources preserves the hand-size fix.

`-PwithoutGtceu -PwithIntegrations` isolates the other integrations when diagnosing upstream GT failures. The supported GT version is a pinned development snapshot; see `INTEGRATIONS.md` for the exact download and why the 7.0.2 public release is excluded.
