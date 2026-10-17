ServerEvents.recipes(event => {
  event.recipes.foundations_calculator.process(
    'calculator',
    [{ ingredient: 'minecraft:bedrock', count: 1 }, { ingredient: 'minecraft:barrier', count: 1 }],
    [{ stack: 'minecraft:diamond' }]
  ).energy(17).id('foundations_calculator:kubejs_smoke')
  function chamberTable(machine) {
    let results = []
    for (let i = 0; i < 54; i++) results.push({stack: 'minecraft:raw_iron', chance: 1})
    event.recipes.foundations_calculator.process(machine,
      [{ ingredient: 'minecraft:bedrock', count: 1 }], results)
      .energy(17).ticks(1).id('foundations_calculator:kubejs_outputs_' + machine)
  }
  chamberTable('extraction_chamber')
  chamberTable('precision_chamber')
  // Filters operate on datapack recipes, which are loaded before this event.
  event.replaceOutput({ id: 'foundations_calculator:kubejs_edit_smoke' }, 'minecraft:diamond', 'minecraft:emerald')
  event.replaceInput({ id: 'foundations_calculator:kubejs_edit_smoke' }, 'minecraft:barrier', 'minecraft:structure_void')
  event.remove({ id: 'foundations_calculator:kubejs_remove_smoke' })
})
