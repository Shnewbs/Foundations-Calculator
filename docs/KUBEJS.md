# KubeJS support

The mod works without KubeJS. With 1.21.1 KubeJS installed, its JSON schema registers `event.recipes.foundations_calculator.process(...)`. The test configuration uses KubeJS `2101.7.2-build.379` and Rhino `2101.2.7-build.81`. These dependencies are only added to development runs when `-PwithKubeJS` is supplied and are not embedded in the release.

Put scripts in the instance's `kubejs/server_scripts` directory. Run `/reload` after edits. The mod invalidates its recipe index when datapacks synchronize, so machines do not retain old recipes.

## Builder

```js
ServerEvents.recipes(event => {
  event.recipes.foundations_calculator.process(
    'stone_separator',
    [{ ingredient: '#c:raw_materials/iron', count: 1 }],
    [
      { stack: '2x foundations_calculator:reinforced_iron_ingot' },
      { stack: 'foundations_calculator:small_stone', chance: 0.5 }
    ]
  ).energy(500).ticks(100).id('my_pack:raw_iron_separator')

  event.remove({ id: 'foundations_calculator:stone_separator/0647' }) // Original iron-ore separation recipe.
  event.replaceInput({ type: 'foundations_calculator:process' }, 'minecraft:iron_ingot', '#c:ingots/iron')
  event.replaceOutput({ type: 'foundations_calculator:process' }, 'minecraft:diamond', 'minecraft:emerald')
})
```

Inspect recipe paths or KubeJS recipe dumps when selecting other IDs. Removal/replacement filters operate on recipes loaded before the event. To alter a newly built recipe, configure its builder directly. Give overrides the same ID as the recipe they replace, or remove the earlier recipe to avoid ambiguous matches. Matching recipes run in lexical ID order.

## Portable calculation

```js
ServerEvents.recipes(event => {
  event.recipes.foundations_calculator.process(
    'calculator',
    [{ ingredient: 'minecraft:cobblestone' }, { ingredient: 'minecraft:flint' }],
    [{ stack: '2x foundations_calculator:reinforced_stone_block' }]
  ).energy(10).id('my_pack:reinforced_stone')
})
```

The supported portable families are `calculator`, `scientific`, and `flawless`. Atomic block recipes use `atomic`. The docking station selects the family of its docked calculator. Use snake-case machine IDs for the processing chambers and separators. Portable calculations are immediate and use the recipe energy, with a one-FE minimum before configuration scaling; their `ticks` value is ignored.

## Raw recipe format

The same recipe can be supplied through a datapack or `event.custom`:

```js
ServerEvents.recipes(event => {
  event.custom({
    type: 'foundations_calculator:process',
    machine: 'processing_chamber',
    ingredients: [{ ingredient: { item: 'foundations_calculator:circuit_board_0' }, count: 1, circuit_state: 2 }],
    results: [{ stack: { id: 'minecraft:diamond', count: 1 }, chance: 0.25 }],
    energy: 1000,
    ticks: 200,
    value: 0,
    research: false
  }).id('my_pack:stable_circuit_diamond')
})
```

| Field | Meaning |
|---|---|
| `machine` | Recipe family; one of the supported machine/family IDs |
| `ingredients` | One to fourteen entries; each `ingredient` accepts a vanilla/NeoForge ingredient and optional `count` from 1 to 64 |
| `circuit_state` | 0: no circuit-state restriction; 1: analysed; 2: analysed and stable |
| `results` | Zero to six results; `stack` uses 1.21.1 item-stack JSON (`id`, `count`, optional `components`) |
| `chance` | Independent output chance, from 0 to 1; defaults to 1 |
| `random_circuit` | Optional `circuit_board`, `circuit_dirty`, or `circuit_damaged`; selects a random subtype from 0–13 |
| `energy` | Total machine FE cost; special meaning for analysis rewards described below |
| `ticks` | Base processing time, 1–1,000,000; affected by time configuration and speed upgrades |
| `value` | Nutrient/health points or the analyser roll to match; nonnegative integer |
| `research_group` | Group ID (maximum 256 characters); KubeJS defaults to `general`. Study recipes use this group with `machine: "research"` and `research: false` |
| `research` | Defaults false. True requires the owner’s `research_group` unlock unless the server enables the legacy bypass or disables research gating |

Overlapping tags and repeated inputs reserve distinct quantities. Counted ingredients may span input slots. Inputs are consumed on the server, container remainders are retained, and full output slots cannot trigger duplicate processing.

## Nutrients and circuit analysis

For `starch_extractor`, `redstone_extractor`, `glowstone_extractor`, and `health_processor`, use `value` for the stored points obtained from the ingredients. The hunger processor uses `hunger_processor` value recipes if supplied, otherwise the item's vanilla/NeoForge food nutrition. Fuel burn times use the NeoForge furnace-fuel data map, so datapack and KubeJS fuel edits apply.

Analyser families are `analysis_0` through `analysis_6`. The ingredient normally matches `#foundations_calculator:circuit_boards`. `value` is the rolled number to match. Family 0 matches the Energy roll; families 1–6 match the six item rolls. Here, `energy` is FE **awarded**, not consumed. `results` are the item rewards. Remove an existing reward or override its ID before adding another result for the same category/roll. Random roll ranges are configured in the server `circuits` section.

```js
ServerEvents.recipes(event => {
  event.recipes.foundations_calculator.process(
    'analysis_0',
    [{ ingredient: '#foundations_calculator:circuit_boards' }],
    []
  ).value(1).energy(2000).ticks(1).id('foundations_calculator:analysis_0/001')
})
```

Recipe schemas describe nested input/output roles so `replaceInput`, `replaceOutput`, and input/output filters can see inside the counted entries. Empty compatibility tags are permitted by the scripting schema; an actually empty ingredient or invalid result still fails the mod's Minecraft recipe codec.

Official references: https://kubejs.com/wiki/tutorials/recipes and https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/

## Research samples

A study recipe has `machine: "research"`, `research_group: "your_group"`, sample ingredients and empty results. Keep `research: false` on the study itself. Put `research: true` and the same group on conversions it unlocks. The study’s `energy` and `ticks` fields apply, with the same per-recipe and `machine.research_chamber` profiles as other processes. Research recipes and samples appear in the built-in guide and recipe viewers.

The server’s per-shipped-recipe switches can suppress a scripted replacement using the same recipe ID. New script IDs do not gain TOML keys automatically; set their fields in your script. Global and applicable per-machine profiles still apply.
