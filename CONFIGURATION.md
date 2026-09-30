# R7 configuration additions

Current catalog: **4,209 server settings**. All R6 keys/defaults are retained. See [the seven R7 protection/performance settings](docs/R7_AUTOMATION_PROTECTION_PERFORMANCE.md#seven-new-synchronized-server-settings) and [generated full reference](docs/CONFIG_REFERENCE.md).

Important new default: `protection.allowLegacyPlantCallbacks=false` pauses opaque/sapling growth without a protected adapter. `protection.requireOwner=true` pauses ownerless machine world actions. New performance controls can disable change-only sync, staggering or machine recipe caching, or adjust sync/opener-check cadence. Native load/reload invalidates configuration caches; direct code-side ConfigValue.set must be followed by CalculatorConfig.settingsChanged().

## Earlier configuration guidance (retained)

# Configuring Foundations Calculator R6

R6 defines **4,202 server settings**, 18 client settings, and startup JSON overrides for every content entry and all nine tool materials. The exact server keys, defaults and accepted ranges are in [docs/CONFIG_REFERENCE.md](docs/CONFIG_REFERENCE.md); the same data is provided as `tools/configuration_catalog.json` and is embedded in the JAR. Every shipped recipe and every catalog content ID is represented.

R6 is a source candidate pending native build/runtime validation. [POWER.md](docs/POWER.md) describes the 38 new power-policy keys and precise routing/loss behavior.

## Files and application

| File | Purpose | Application |
|---|---|---|
| `foundations_calculator-server.toml` | Server gameplay, recipes, machines, modules and integrations | NeoForge-managed SERVER config, synchronized to clients. Normal saved worlds use `serverconfig`; the GameTest runner uses its `config` folder. Place pack defaults in `defaultconfigs`. |
| `config/foundations_calculator-client.toml` | Palette, animations, rendering and hints | Local client; accessible through Mods → Foundations Calculator → Config. Resource reload is required for the 3D/flat tool switch. |
| `config/foundations_calculator-content.json` | Startup block, item and material properties | Restart required; distribute the same file to server and clients. Empty objects inherit the shipped properties. |

Close the game/server before editing config files for a repeatable pack setup. Most server values are read when an operation runs; a config reload therefore affects new work immediately. Changing enablement or recipe switches requires `/reload` to rebuild recipe lists and reconnecting to refresh recipe viewers. Re-enable a removed recipe with `/reload`; merely toggling it cannot restore a recipe already filtered out of the manager. Restart/rejoin for integration registration changes. Existing placed machines retain their chosen redstone mode; `defaultRedstoneMode` only supplies the default when one has not been saved.

A capacity reduction never deletes previously stored FE or bulk items. Over-capacity storage stops accepting more until withdrawn below the new limit. Lowering portable storage slots leaves excess existing contents recoverable. Lowering installed-module limits prevents further insertion; it does not discard modules. Registry IDs remain present when content is disabled, so existing items can be recovered or removed.

## Processing precedence

1. A datapack/KubeJS/CraftTweaker recipe supplies inputs, outputs, FE, ticks, chances and optional research group.
2. `recipe."namespace:path".enabled` can disable that shipped ID. A nonnegative `energyOverride` or positive `ticksOverride` replaces its base field. `chanceMultiplier` multiplies each output chance and clamps it to 0–1.
3. An applicable `machine.<id>.energyOverride`/`ticksOverride` replaces the resulting base. Global `machines.processEnergyMultiplier`/`processTimeMultiplier` and machine multipliers apply next.
4. Supported machine upgrades apply their configured speed bonus/energy discount. Duration is at least one tick and integer FE costs are rounded down.

Portable calculator recipes are immediate; their time fields do not delay the Calculate button. Portable calculation has a one-FE base floor unless an explicit per-recipe zero override or global zero multiplier removes it. Docking has a ten-FE base floor. Energy upgrade discounts and zero costs can make processing free. Analyser reward recipes use `energy` as **FE awarded**, while the analysing chamber’s own profile controls the cost/duration of analysing one board. Study recipes use their own `energy`/`ticks` and the research-chamber profile. Turning `research.consumeEnergy` off makes study free.

Values, ingredients/results, furnace cooking time/experience, loot tables, tags, tree shapes, burn-time data maps and recipes introduced under new script IDs are data, not duplicated as thousands of additional TOML fields. Change them with datapacks or the scripting integrations. Custom new recipe IDs do not acquire generated per-ID TOML keys automatically; they still obey global/applicable machine settings.

## Coverage by section

| Section | Controls |
|---|---|
| `machines` | Global FE/time scaling, global transfer override, crank output, scarecrow interval/range, grenade switch and locator effects |
| `machine.<id>` | Each of 45 machines: operation toggle, energy capacity/transfer/charging, face automation, energy directions and default redstone; cost/time/progress knobs where that machine actually uses them |
| `content.<id>.enabled` | All 303 content entries: prevent normal item use, attacks, placement, recipe inputs/outputs and applicable machine operation. Does not erase world blocks, registry IDs, creative entries or existing loot. |
| `recipe.<id>` | All 910 shipped recipes: enable switch; all 699 process recipes also have FE/time/chance overrides. Nutrient recipes use `value` as points and analysis families use `value` as their roll. |
| `module` | Capacities, smelting time/cost/background processing, terrain replacement/drop requirements, warp restrictions/cost/cooldown, jump and projectile costs/velocity/cooldowns, maximum installed modules |
| `nutrition` | Module capacities, restore intervals/limits, food saturation, transfer, network interval/size and leaf yields |
| `upgrades` / `automation` | Upgrade count/scaling, export rates, void switch, cube balancing, explicit-side battery/upgrade access |
| `generation` | Extractor capacity/points/generation, crank timing, mast wait/burst/power/range, station bonus, locator size/stability/effects/generation |
| `greenhouse` | Build/demolish/plant/replant/water/farmland switches and costs; CO₂ production/consumption/fuel; growth bands; scan/plant intervals; maximum Flawless length and generic plants |
| `world` | Weather cost/duration/cooldown, magnet radius/speed, assimilator scans, multiplier cost/time/count/blacklist data, dynamic structure/rate, explosion effects, lightning visuals and chest sounds |
| `plants` | Leaf maturity/growth/reset, greenhouse minimum crop tiers, diamond sapling support/consumption |
| `research` | Unlock requirement, sample consumption, energy consumption, sharing and four informational mastery targets/counter switch |
| `circuits` | Stability, FE and six item random-roll bounds (exclusive upper limits) |
| `storage` | Per-bin reinforced-chest/circuit/assimilator capacity and portable slots |
| `tools` / `fuel` | Fire Sword duration, key damage, wrench behavior; FE from coal/redstone consumables. Zero FE disables that fuel path; furnace burn time is separate. |
| `power` | Electrical API block/item scopes, direction gates, bounded loss policies, J/MI ratio overrides, explicit routing preference and on-demand server diagnostics. See POWER.md. |
| `compat` | JEI, EMI, Jade, CraftTweaker, AE2, Mekanism and external plant adapters; AE-to-FE ratio |

Machine energy fields do not add energy gameplay to purely decorative/structural machines. Storage/energy fields only affect existing stores/ports; they cannot add a battery slot to a machine that has none. Per-machine process fields are omitted for machines without that operation. A positive global transfer rate overrides per-machine transfer rates. FE I/O switches control external capabilities; a recipe or internal generator can still alter its own storage.

`greenhouse.autoPlant = false` prevents automatic seed placement. `greenhouse.replant = false` leaves a harvested position fallow across future farm cycles; turn it back on to resume planting. Generic external crops that are not normal `CropBlock`s need a harvest adapter. External adapters are described in [docs/INTEGRATIONS.md](docs/INTEGRATIONS.md).

## Server examples

```toml
[machine.processing_chamber]
capacity = 250000
energyOverride = 2000
ticksOverride = 100
defaultRedstoneMode = 1
retainProgressWithoutPower = true

[recipe."foundations_calculator:processing_chamber/0559"]
# Confirm the desired ID in the recipe reference or active recipe viewer.
enabled = true
chanceMultiplier = 1.0

[research]
requireUnlock = true
shareServerWide = false
consumeSample = false

[storage]
reinforcedChestPerBin = 100000

[module.smelting_module]
capacity = 100000
cost = 750
ticks = 200
backgroundProcessing = true
```

Redstone mode is `0` always, `1` requires power, `2` stops with power, or `3` paused. The GUI’s R button changes the saved mode. Existing machine-specific signal rules (weather triggering, greenhouse/magnet pause) still apply; choose a compatible mode.

## Startup content JSON

Use **bare Foundations IDs**, without a namespace, as object keys. Omitted properties inherit the original implementation. Do not copy a full default value set unless you intend to override it.

```json
{
  "blocks": {
    "processing_chamber": {"hardness": 4.0, "blastResistance": 30.0},
    "stable_stone_normal": {"lightLevel": 7, "requiresCorrectTool": true}
  },
  "items": {
    "reinforced_pickaxe": {"durability": 1200},
    "pear": {"nutrition": 10, "saturation": 0.5, "alwaysEdible": true},
    "fire_coal": {"burnTime": 40000},
    "end_forged_sword": {"unbreakable": false, "durability": 10000}
  },
  "materials": {
    "reinforced": {"miningSpeed": 7.0, "enchantability": 12, "repairItem": "minecraft:iron_ingot"}
  }
}
```

| Group | Property | Range / meaning |
|---|---|---|
| blocks | `hardness` | −1…1,000,000; −1 is unbreakable |
| blocks | `blastResistance` | 0…3,600,000 |
| blocks | `friction` | 0…2 |
| blocks | `speedFactor`, `jumpFactor` | 0…10 |
| blocks | `lightLevel` | Integer 0…15 |
| blocks | `requiresCorrectTool` | true/false |
| blocks | `fireSpread`, `flammability` | Integer 0…1,000; zero disables that fire property |
| items | `stackSize` | Integer 1…99; durable items must use 1 |
| items | `durability` | Integer 1…2,147,483,647; overrides tool material durability for that item |
| items | `nutrition` | Integer 0…1,000; also enables/replaces food properties |
| items | `saturation` | 0…100 saturation modifier; specify `nutrition` in the same entry |
| items | `alwaysEdible` | true/false; specify `nutrition` in the same entry |
| items | `fireResistant`, `unbreakable` | true/false; false explicitly removes the default component |
| items | `rarity` | COMMON, UNCOMMON, RARE or EPIC |
| items | `burnTime` | Integer 0…2,147,483,647 furnace ticks; zero disables furnace fuel |
| materials | `durability` | Integer 1…2,147,483,647 |
| materials | `enchantability` | Integer 0…2,147,483,647 |
| materials | `miningSpeed`, `attackDamage` | 0…10,000; material damage bonus, before weapon attributes |
| materials | `repairItem` | Namespaced item ID, e.g. `minecraft:iron_ingot` |
| materials | `incorrectBlocksForDrops` | Namespaced block-tag ID, e.g. `minecraft:incorrect_for_diamond_tool` |

Materials: `reinforced`, `redstone`, `enriched_gold`, `reinforced_iron`, `weakened_diamond`, `flawless_diamond`, `fire_diamond`, `electric`, `end_forged`. Per-item durability wins over a tier value. Invalid JSON, unsupported property names or out-of-range values fail with an error instead of silently replacing the file.

## Client controls

`appearance.theme`: `FOUNDATIONS` or `CLASSIC`. The classic setting is a gray Minecraft-style palette for these modern screens.

| Key under `appearance` | Default | Range |
|---|---|---|
| `machineAnimations` | true | boolean |
| `chestLidAnimation` | true | boolean |
| `threeDimensionalTools` | true | boolean; F3+T after change |
| `displayItemsInMachines` | true | boolean |
| `animationSpeed` | 1.0 | 0…10 |
| `chestLidSpeed` | 0.1 | 0.01…1 |
| `machineRenderDistance` | 64 | 16…256 blocks |
| `accent` | 4,697,273 | RGB integer 0…16,777,215 |
| `background` | 1,582,125 | RGB integer |
| `panel` | 3,162,702 | RGB integer |
| `text` | 15,594,744 | RGB integer |
| `mutedText` | 11,123,656 | RGB integer |
| `slotBackground` | 1,054,752 | RGB integer |
| `progress` | 4,574,163 | RGB integer |

Under `information`, `emptySlotHints`, `energyTooltips` and `itemInstructions` all default to true. The two themes and flat/3D item models are client presentation settings; they do not affect recipes or server logic.
