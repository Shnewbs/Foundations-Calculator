# 26.3 compile diagnostics

Source commit: `668802dfc9dce919ff6c863d28591f4b1824d59a`  
CI run: 38103679283  

- errors: **469**
- javac reported 469 errors (matches)
- warnings: 4
- distinct failure groups: 179
- files with errors: 63

Many errors cascade from a small number of removed APIs. Work the groups
below from the top; re-run CI after each group to see the cascade collapse.

## Most common failures

-   19  cannot find symbol: class ItemStackHandler
-   11  package net.neoforged.neoforge.items does not exist
-   10  cannot find symbol: variable GLFW
-    9  cannot find symbol: class SwordItem
-    9  cannot find symbol: method getRecipeManager()
-    9  cannot find symbol: variable SwordItem
-    9  cannot find symbol: class PickaxeItem
-    9  cannot find symbol: variable PickaxeItem
-    9  cannot find symbol: class AxeItem
-    9  cannot find symbol: variable AxeItem
-    9  cannot find symbol: class ShovelItem
-    9  cannot find symbol: variable ShovelItem
-    9  cannot find symbol: class HoeItem
-    9  cannot find symbol: variable HoeItem
-    8  cannot find symbol: class IItemHandler
-    8  cannot find symbol: method getCraftingRemainingItem()
-    7  cannot find symbol: class Unbreakable
-    7  no suitable method found for mulPose(Quaternionf)
-    6  cannot find symbol: variable ClickType
-    6  no suitable method found for placeItemBackInInventory(ItemStack)
-    6  cannot find symbol: variable ItemHandler
-    6  method does not override or implement a method from a supertype
-    6  server has private access in ServerPlayer
-    5  cannot find symbol: class ClickType
-    5  cannot find symbol: class MultiBufferSource
-    5  cannot find symbol: method render(GuiGraphicsExtractor,int,int,float)
-    5  cannot find symbol: variable super
-    5  incompatible types: Item cannot be converted to ItemStack
-    4  cannot find symbol: class MEnergyPort
-    4  cannot find symbol: class ThrowableItemProjectile
-    4  cannot find symbol: method renderTooltip(Font,List<FormattedCharSequence>,int,int)
-    4  cannot find symbol: method getBurnTime(RecipeType<SmeltingRecipe>)
-    4  method registerEntityRenderer in class RegisterRenderers cannot be applied to given types;
-    4  incompatible types: incompatible parameter types in lambda expression
-    4  incompatible types: ResourceKey<Recipe<?>> cannot be converted to Identifier
-    4  method of in class Builder<T> cannot be applied to given types;
-    4  cannot find symbol: variable CONFIGURED_FEATURE
-    4  cannot find symbol: method getType()
-    3  cannot assign a value to final variable imageWidth
-    3  cannot assign a value to final variable imageHeight
-    3  cannot find symbol: method renderTooltip(GuiGraphicsExtractor,int,int)
-    3  cannot find symbol: method renderTooltip(Font,MutableComponent,int,int)
-    3  cannot find symbol: method parse(Provider,Tag)
-    3  cannot find symbol: method isDay()
-    3  cannot find symbol: method getItems()
-    3  cannot find symbol: variable ModelResourceLocation
-    3  cannot find symbol: class BreakEvent
-    3  cannot find symbol: method hasPermission(int)
-    3  cannot find symbol: method canReceive()
-    3  cannot find symbol: method canExtract()
-    3  cannot find symbol: variable ItemHandlerHelper
-    3  cannot find symbol: method level()
-    2  cannot find symbol: class BlockEntityWithoutLevelRenderer
-    2  cannot find symbol: class Tier
-    2  cannot find symbol: class Properties
-    2  cannot find symbol: method bus()
-    2  cannot find symbol: variable Bus
-    2  cannot find symbol: class SlotItemHandler
-    2  cannot find symbol: method hasCraftingRemainingItem()
-    2  cannot find symbol: method save(Provider)

## Packages that no longer exist

-   11  net.neoforged.neoforge.items
-    1  org.lwjgl.glfw

## Errors per directory

-  248  src/main/java/com/foundations/calculator/content
-   94  src/main/java/com/foundations/calculator/client
-   52  src/main/java/com/foundations/calculator/core
-   30  src/main/java/com/foundations/calculator/menu
-   15  src/main/java/com/foundations/calculator/compat
-   14  src/main/java/com/foundations/calculator/compat/viewer
-    9  src/main/java/com/foundations/calculator/client/guide
-    4  src/main/java/com/foundations/calculator/network
-    2  src/main/java/com/foundations/calculator/guide
-    1  src/main/java/com/foundations/calculator

## Errors per file (top 60)

-  117  src/main/java/com/foundations/calculator/content/Content.java
-   38  src/main/java/com/foundations/calculator/client/GuideScreen.java
-   33  src/main/java/com/foundations/calculator/content/MachinePrograms.java
-   20  src/main/java/com/foundations/calculator/content/MachineBlockEntity.java
-   17  src/main/java/com/foundations/calculator/content/UtilityItems.java
-   15  src/main/java/com/foundations/calculator/menu/CalculatorMenu.java
-   15  src/main/java/com/foundations/calculator/client/MachineRenderer.java
-   12  src/main/java/com/foundations/calculator/content/GrenadeEntity.java
-   10  src/main/java/com/foundations/calculator/content/GreenhouseProgram.java
-   10  src/main/java/com/foundations/calculator/content/ThrownMaterialEntity.java
-    9  src/main/java/com/foundations/calculator/core/MachineDiagnostics.java
-    9  src/main/java/com/foundations/calculator/compat/ModernIndustrializationEnergy.java
-    9  src/main/java/com/foundations/calculator/client/CalculatorScreen.java
-    8  src/main/java/com/foundations/calculator/client/guide/GuideRecipes.java
-    7  src/main/java/com/foundations/calculator/client/ClientEvents.java
-    7  src/main/java/com/foundations/calculator/menu/BulkStorageMenu.java
-    7  src/main/java/com/foundations/calculator/client/ToolModels.java
-    6  src/main/java/com/foundations/calculator/core/ContentProperties.java
-    6  src/main/java/com/foundations/calculator/compat/viewer/JeiIntegration.java
-    6  src/main/java/com/foundations/calculator/compat/viewer/EmiIntegration.java
-    5  src/main/java/com/foundations/calculator/core/ProcessTransactions.java
-    5  src/main/java/com/foundations/calculator/content/MachineBlock.java
-    5  src/main/java/com/foundations/calculator/core/ResearchEvents.java
-    4  src/main/java/com/foundations/calculator/core/BulkStorage.java
-    4  src/main/java/com/foundations/calculator/client/MachineItemRenderer.java
-    4  src/main/java/com/foundations/calculator/core/ProcessRecipe.java
-    4  src/main/java/com/foundations/calculator/content/GreenhouseSeedItem.java
-    4  src/main/java/com/foundations/calculator/content/FireSwordItem.java
-    4  src/main/java/com/foundations/calculator/client/ResearchScreen.java
-    4  src/main/java/com/foundations/calculator/client/SmeltingModuleScreen.java
-    4  src/main/java/com/foundations/calculator/client/BulkStorageScreen.java
-    4  src/main/java/com/foundations/calculator/core/ResearchData.java
-    3  src/main/java/com/foundations/calculator/menu/SmeltingModuleMenu.java
-    3  src/main/java/com/foundations/calculator/menu/StorageModuleMenu.java
-    3  src/main/java/com/foundations/calculator/content/DynamicProgram.java
-    3  src/main/java/com/foundations/calculator/core/RecipeIndex.java
-    3  src/main/java/com/foundations/calculator/content/AtomicTerrainModule.java
-    3  src/main/java/com/foundations/calculator/content/WrenchActions.java
-    2  src/main/java/com/foundations/calculator/core/MachineRecipeSelection.java
-    2  src/main/java/com/foundations/calculator/menu/PortableCraftingMenu.java
-    2  src/main/java/com/foundations/calculator/core/ContentRules.java
-    2  src/main/java/com/foundations/calculator/core/EnergyItems.java
-    2  src/main/java/com/foundations/calculator/content/CalculatorTier.java
-    2  src/main/java/com/foundations/calculator/network/Networking.java
-    2  src/main/java/com/foundations/calculator/compat/OptionalIntegrations.java
-    2  src/main/java/com/foundations/calculator/network/TransferPayload.java
-    2  src/main/java/com/foundations/calculator/core/PowerDiagnostics.java
-    2  src/main/java/com/foundations/calculator/compat/viewer/RecipeView.java
-    2  src/main/java/com/foundations/calculator/compat/GrandPowerEnergy.java
-    2  src/main/java/com/foundations/calculator/compat/JadeIntegration.java
-    2  src/main/java/com/foundations/calculator/guide/GuideSnapshots.java
-    2  src/main/java/com/foundations/calculator/content/SmeltingModule.java
-    1  src/main/java/com/foundations/calculator/client/ClientRecipeEvents.java
-    1  src/main/java/com/foundations/calculator/core/CountedIngredient.java
-    1  src/main/java/com/foundations/calculator/client/ItemMeshBounds.java
-    1  src/main/java/com/foundations/calculator/client/guide/GuideValues.java
-    1  src/main/java/com/foundations/calculator/FoundationsCalculator.java
-    1  src/main/java/com/foundations/calculator/core/RecipePolicies.java
-    1  src/main/java/com/foundations/calculator/core/MachineWorldActions.java
-    1  src/main/java/com/foundations/calculator/content/CalculatorItem.java

