package com.foundations.calculator;

import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.gametest.*;
import net.neoforged.neoforge.capabilities.Capabilities;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class CalculatorGameTests {
    @GameTest(template="empty") public static void recipesLoad(GameTestHelper h){
        var recipes=h.getLevel().getRecipeManager().getAllRecipesFor(Content.PROCESS_TYPE.get());
        h.assertTrue(recipes.size()>=666,"All converted process recipes must decode; got "+recipes.size());
        long total=h.getLevel().getRecipeManager().getRecipes().stream().filter(recipe->recipe.id().getNamespace().equals(FoundationsCalculator.ID)).count();
        h.assertTrue(total>=874,"All crafting, smelting and processing recipes must decode; got "+total);
        ProcessInput in=new ProcessInput(List.of(new ItemStack(Items.COBBLESTONE),new ItemStack(Items.OAK_PLANKS)));
        h.assertTrue(recipes.stream().anyMatch(r->r.value().machine().equals("calculator")&&r.value().matches(in,h.getLevel())&&r.value().outputs().getFirst().stack().is(Content.item("reinforced_stone_block"))),"Starter calculation exists");h.succeed();
    }
    @GameTest(template="empty",timeoutTicks=260) public static void processingConsumesOnce(GameTestHelper h){
        BlockPos pos=new BlockPos(2,1,2);h.setBlock(pos,Content.block("stone_separator"));
        MachineBlockEntity m=(MachineBlockEntity)h.getBlockEntity(pos);
        m.energy.receiveEnergy(500,false);m.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE));
        h.runAtTickTime(220,()->{
            h.assertTrue(m.inventory.getStackInSlot(0).isEmpty(),"Consumed one ore");
            h.assertTrue(m.inventory.getStackInSlot(14).is(Content.item("reinforced_iron_ingot"))&&m.inventory.getStackInSlot(14).getCount()==4,"Four ingots produced");
            h.assertTrue(m.inventory.getStackInSlot(15).is(Content.item("small_stone"))&&m.inventory.getStackInSlot(15).getCount()==2,"Byproduct preserved");
            h.assertTrue(m.energy.getEnergyStored()==0,"Charged exactly 500 FE");h.succeed();
        });
    }
    @GameTest(template="empty") public static void energyAndPendingResultsSurviveSave(GameTestHelper h){
        BlockPos pos=new BlockPos(2,1,2);h.setBlock(pos,Content.block("advanced_power_cube"));
        MachineBlockEntity m=(MachineBlockEntity)h.getBlockEntity(pos);
        m.energy.receiveEnergy(73123,false);m.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE,3));m.pending.add(new ItemStack(Items.DIAMOND,2));
        var saved=m.saveWithoutMetadata(h.getLevel().registryAccess());
        MachineBlockEntity copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.loadWithComponents(saved,h.getLevel().registryAccess());
        h.assertTrue(copy.energy.getEnergyStored()==73123,"FE must persist above 16 bits");h.assertTrue(copy.inventory.getStackInSlot(0).getCount()==3,"Inputs persist");h.assertTrue(copy.pending.size()==1&&copy.pending.getFirst().getCount()==2,"Committed outputs persist");h.succeed();
    }
    @GameTest(template="empty") public static void automationCannotInjectOutputsOrExtractInputs(GameTestHelper h){
        BlockPos pos=new BlockPos(2,1,2);h.setBlock(pos,Content.block("stone_separator"));MachineBlockEntity m=(MachineBlockEntity)h.getBlockEntity(pos);
        var items=h.getLevel().getCapability(Capabilities.ItemHandler.BLOCK,h.absolutePos(pos),Direction.NORTH);
        h.assertTrue(items!=null,"Item capability must be available without Sonar Core");
        h.assertTrue(items.insertItem(0,new ItemStack(Items.IRON_ORE),false).isEmpty(),"Can insert input");
        h.assertTrue(!items.insertItem(14,new ItemStack(Items.DIAMOND),false).isEmpty(),"Cannot insert into output");
        h.assertTrue(items.extractItem(0,1,false).isEmpty(),"Cannot steal reserved input through output automation");h.succeed();
    }
    @GameTest(template="empty") public static void taggedComponentsRoundTrip(GameTestHelper h){
        ItemStack calculator=new ItemStack(Content.item("calculator"));var storage=calculator.getCapability(Capabilities.EnergyStorage.ITEM);
        h.assertTrue(storage!=null,"Item FE capability exists");storage.receiveEnergy(555,false);
        var saved=calculator.save(h.getLevel().registryAccess());ItemStack loaded=ItemStack.parse(h.getLevel().registryAccess(),saved).orElseThrow();
        h.assertTrue(loaded.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==555,"Item FE component persists");h.succeed();
    }
    @GameTest(template="empty") public static void blockedOutputsDoNotConsumeAnotherBatch(GameTestHelper h){
        BlockPos pos=new BlockPos(2,1,2);h.setBlock(pos,Content.block("stone_separator"));
        MachineBlockEntity m=(MachineBlockEntity)h.getBlockEntity(pos);
        m.energy.receiveEnergy(1000,false);m.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE,3));
        for(int i=14;i<20;i++)m.inventory.setStackInSlot(i,new ItemStack(Items.COBBLESTONE,64));
        for(int i=0;i<420;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        h.assertTrue(m.inventory.getStackInSlot(0).getCount()==2,"Only one batch can commit with blocked outputs");
        h.assertTrue(m.energy.getEnergyStored()==500,"Only one batch charged");
        h.assertTrue(m.pending.stream().mapToInt(ItemStack::getCount).sum()==6,"All six committed result items retained");
        for(int i=14;i<20;i++)m.inventory.setStackInSlot(i,ItemStack.EMPTY);
        MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        h.assertTrue(m.pending.isEmpty()&&m.inventory.getStackInSlot(0).getCount()==2,"Draining committed results does not recraft them");h.succeed();
    }
    @GameTest(template="empty") public static void analysisConsumesOneCircuitAndUsesRecipeRewards(GameTestHelper h){
        BlockPos pos=new BlockPos(2,1,2);h.setBlock(pos,Content.block("analysing_chamber"));
        MachineBlockEntity m=(MachineBlockEntity)h.getBlockEntity(pos);
        ItemStack boards=new ItemStack(Content.item("circuit_board_0"),3);var tag=new net.minecraft.nbt.CompoundTag();
        tag.putInt("Stable",1);tag.putInt("Energy",1);
        boards.set(net.minecraft.core.component.DataComponents.CUSTOM_DATA,net.minecraft.world.item.component.CustomData.of(tag));
        m.inventory.setStackInSlot(0,boards);MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        h.assertTrue(m.inventory.getStackInSlot(0).getCount()==2,"Exactly one circuit analysed");
        h.assertTrue(m.energy.getEnergyStored()==1000,"Analysis energy reward resolved through recipes");
        ItemStack result=m.inventory.getStackInSlot(14);
        h.assertTrue(result.getCount()==1&&CircuitData.analysed(result)&&CircuitData.stable(result),"Circuit returned analysed with stability intact");
        h.assertTrue(!CircuitData.tag(result).contains("Energy"),"Spent reward state removed");h.succeed();
    }
    @GameTest(template="empty") public static void recipeTransactionsKeepContainerRemainders(GameTestHelper h){
        var handler=new net.neoforged.neoforge.items.ItemStackHandler(25);handler.setStackInSlot(0,new ItemStack(Items.LAVA_BUCKET));
        var recipe=new ProcessRecipe("calculator",List.of(new CountedIngredient(net.minecraft.world.item.crafting.Ingredient.of(Items.LAVA_BUCKET),1,0)),List.of(new ProcessRecipe.Result(new ItemStack(Items.STONE),1,"")),0,1,0,false);
        var result=ProcessTransactions.consume(handler,recipe.allocation(ProcessTransactions.input(handler,1)),recipe,h.getLevel());
        h.assertTrue(handler.getStackInSlot(0).isEmpty(),"Input consumed");
        h.assertTrue(result.stream().anyMatch(i->i.is(Items.BUCKET))&&result.stream().anyMatch(i->i.is(Items.STONE)),"Bucket and recipe output both retained");h.succeed();
    }
    @GameTest(template="empty") public static void storageModulePersistsAndLocksItsHeldSlot(GameTestHelper h){
        var player=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);var hand=net.minecraft.world.InteractionHand.MAIN_HAND;
        ItemStack module=new ItemStack(Content.item("storage_module"));player.setItemInHand(hand,module);
        var menu=new com.foundations.calculator.menu.StorageModuleMenu(1,player.getInventory(),hand);
        menu.getContainer().setItem(53,new ItemStack(Items.DIAMOND,11));
        var restored=ItemStack.parse(h.getLevel().registryAccess(),module.save(h.getLevel().registryAccess())).orElseThrow();
        h.assertTrue(restored.get(net.minecraft.core.component.DataComponents.CONTAINER).getStackInSlot(53).getCount()==11,"Last storage slot persists");
        h.assertTrue(!menu.slots.get(0).mayPlace(new ItemStack(Content.item("storage_module"))),"Nested modules forbidden");
        var held=menu.slots.stream().filter(slot->slot.container==player.getInventory()&&slot.getContainerSlot()==player.getInventory().selected).findFirst().orElseThrow();
        h.assertTrue(!held.mayPickup(player)&&!held.mayPlace(new ItemStack(Items.DIRT)),"Pickup-all and dragging cannot move the open module");
        player.getInventory().selected=1;h.assertTrue(!menu.stillValid(player),"Changing the held slot closes the menu");h.succeed();
    }
    @GameTest(template="empty") public static void nutritionDoesNotCreateExtraPoints(GameTestHelper h){
        BlockPos pos=new BlockPos(2,1,2);h.setBlock(pos,Content.block("hunger_processor"));MachineBlockEntity m=(MachineBlockEntity)h.getBlockEntity(pos);
        ItemStack module=new ItemStack(Content.item("hunger_module"));m.inventory.setStackInSlot(0,new ItemStack(Items.BREAD));m.inventory.setStackInSlot(20,module);
        for(int i=0;i<4;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        h.assertTrue(NutritionData.get(module,"hunger")==5&&m.nutrient==0,"One bread contributes exactly five points across partial transfers");
        NutritionData.set(module,"hunger",999);h.assertTrue(NutritionData.add(module,"hunger",500)==1&&NutritionData.get(module,"hunger")==1000,"Module capacity is enforced");h.succeed();
    }
    @GameTest(template="empty") public static void portableCalculationCommitsOnce(GameTestHelper h){
        var player=h.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);var hand=net.minecraft.world.InteractionHand.MAIN_HAND;
        ItemStack calculator=new ItemStack(Content.item("calculator"));player.setItemInHand(hand,calculator);
        calculator.getCapability(Capabilities.EnergyStorage.ITEM).receiveEnergy(1,false);
        var menu=com.foundations.calculator.menu.CalculatorMenu.forItem(1,player.getInventory(),hand);
        menu.handler.setStackInSlot(0,new ItemStack(Items.COBBLESTONE));menu.handler.setStackInSlot(1,new ItemStack(Items.OAK_PLANKS));
        h.assertTrue(menu.clickMenuButton(player,0),"Starter recipe calculates");
        h.assertTrue(!menu.clickMenuButton(player,0),"Repeated packet cannot duplicate output");
        h.assertTrue(menu.handler.getStackInSlot(14).is(Content.item("reinforced_stone_block"))&&calculator.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==0,"Output and charge committed together");h.succeed();
    }
    @GameTest(template="empty") public static void kubeJsRecipeIfInstalled(GameTestHelper h){
        if(net.neoforged.fml.ModList.get().isLoaded("kubejs")){
            var recipe=h.getLevel().getRecipeManager().byKey(net.minecraft.resources.Identifier.parse("foundations_calculator:kubejs_smoke"));
            h.assertTrue(recipe.isPresent(),"KubeJS runtime must register the smoke recipe");
            h.assertTrue(((ProcessRecipe)recipe.orElseThrow().value()).outputs().getFirst().stack().is(Items.DIAMOND),"Native builder creates a recipe");
            var edited=h.getLevel().getRecipeManager().byKey(net.minecraft.resources.Identifier.parse("foundations_calculator:kubejs_edit_smoke"));
            var r=(ProcessRecipe)edited.orElseThrow().value();
            h.assertTrue(h.getLevel().getRecipeManager().byKey(net.minecraft.resources.Identifier.parse("foundations_calculator:kubejs_remove_smoke")).isEmpty(),"KubeJS removal applies");
            h.assertTrue(((ProcessRecipe)recipe.orElseThrow().value()).energy()==17,"Builder preserves custom FE cost");
            h.assertTrue(r.inputs().get(1).test(new ItemStack(Items.STRUCTURE_VOID)),"Nested input replacement applies");
            h.assertTrue(r.machine().equals("calculator")&&r.outputs().getFirst().stack().is(Items.EMERALD),"KubeJS output replacement must reach RecipeManager");
        }h.succeed();
    }
    @GameTest(template="empty",batch="reload",timeoutTicks=600) public static void datapackReloadRefreshesMachineRecipes(GameTestHelper h){
        var before=RecipeIndex.forMachine(h.getLevel(),"calculator");
        h.assertTrue(!before.isEmpty(),"Recipe index initially populated");
        var server=h.getLevel().getServer();var future=server.reloadResources(server.getPackRepository().getSelectedIds());
        h.startSequence().thenWaitUntil(()->h.assertTrue(future.isDone(),"Wait for resource reload"))
            .thenExecute(()->{
                future.join();
                var after=RecipeIndex.forMachine(h.getLevel(),"calculator");
                h.assertTrue(after!=before&&!after.isEmpty(),"Recipe index rebuilt after datapack/KubeJS reload");
                if(net.neoforged.fml.ModList.get().isLoaded("kubejs"))h.assertTrue(after.stream().anyMatch(r->r.id().getPath().equals("kubejs_smoke")),"Script recipe remains usable after reload");
            }).thenSucceed();
    }

}
