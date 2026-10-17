ServerEvents.recipes(event => {
  event.recipes.foundations_calculator.process(
    'calculator',
    [{ ingredient: 'minecraft:bedrock', count: 1 }, { ingredient: 'minecraft:barrier', count: 1 }],
    [{ stack: 'minecraft:diamond' }]
  ).energy(17).id('foundations_calculator:kubejs_smoke')
  for (const machine of ['extraction_chamber', 'precision_chamber']) {
    event.recipes.foundations_calculator.process(machine,
      [{ ingredient: 'minecraft:bedrock', count: 1 }],
      Array.from({length: 54}, () => ({stack: 'minecraft:raw_iron', chance: 1})))
      .energy(17).ticks(1).id('foundations_calculator:kubejs_outputs_' + machine)
  }
  // Filters operate on datapack recipes, which are loaded before this event.
  event.replaceOutput({ id: 'foundations_calculator:kubejs_edit_smoke' }, 'minecraft:diamond', 'minecraft:emerald')
  event.replaceInput({ id: 'foundations_calculator:kubejs_edit_smoke' }, 'minecraft:barrier', 'minecraft:structure_void')
  event.remove({ id: 'foundations_calculator:kubejs_remove_smoke' })
})
