ServerEvents.recipes(event => {
  event.recipes.foundations_calculator.process(
    'calculator',
    [{ ingredient: 'minecraft:bedrock', count: 1 }, { ingredient: 'minecraft:barrier', count: 1 }],
    [{ stack: 'minecraft:diamond' }]
  ).energy(17).id('foundations_calculator:kubejs_smoke')
  // Filters operate on datapack recipes, which are loaded before this event.
  event.replaceOutput({ id: 'foundations_calculator:kubejs_edit_smoke' }, 'minecraft:diamond', 'minecraft:emerald')
  event.replaceInput({ id: 'foundations_calculator:kubejs_edit_smoke' }, 'minecraft:barrier', 'minecraft:structure_void')
  event.remove({ id: 'foundations_calculator:kubejs_remove_smoke' })
})
