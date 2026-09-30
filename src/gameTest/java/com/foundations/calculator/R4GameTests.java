package com.foundations.calculator;

import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.*;
import com.foundations.calculator.api.*;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class R4GameTests {
    private static MachineBlockEntity place(GameTestHelper h,String kind){var p=new BlockPos(3,2,3);h.setBlock(p,Content.block(kind));var machine=(MachineBlockEntity)h.getBlockEntity(p);machine.program.putUUID("Owner",UUID.fromString("9a2ee808-d023-447a-8fe9-0c872a1d5717"));return machine;}
    @SuppressWarnings("unchecked") private static <T> void setting(String key,T value,Runnable test){var spec=(ModConfigSpec.ConfigValue<T>)CalculatorConfig.value(key).orElseThrow();T before=spec.get();try{spec.set(value);CalculatorConfig.settingsChanged();test.run();}finally{spec.set(before);CalculatorConfig.settingsChanged();}}
    @GameTest(template="large",batch="r4_config") public static void disabledReplantStaysDisabledAcrossFarmCycles(GameTestHelper h){
        setting("greenhouse.basicPlantInterval",1,()->setting("greenhouse.replant",false,()->{
            var m=place(h,"basic_greenhouse");for(var part:GreenhouseProgram.blueprint(m))h.getLevel().setBlockAndUpdate(part.pos(),(switch(part.type()){case LOG->Blocks.OAK_LOG;case STAIRS->Blocks.OAK_STAIRS;case GLASS->Blocks.GLASS;case PLANKS->Blocks.OAK_PLANKS;}).defaultBlockState());
            var pos=GreenhouseProgram.plantArea(m).getFirst();h.getLevel().setBlockAndUpdate(pos.below(),Blocks.FARMLAND.defaultBlockState());h.getLevel().setBlockAndUpdate(pos,Blocks.WHEAT.defaultBlockState().setValue(net.minecraft.world.level.block.CropBlock.AGE,7));m.inventory.setStackInSlot(4,new ItemStack(Items.WHEAT_SEEDS,8));m.energy.receiveEnergy(10000,false);GreenhouseProgram.tick(m);GreenhouseProgram.tick(m);h.assertTrue(h.getLevel().isEmptyBlock(pos),"Turning off replant must survive the next farm cycle");
            setting("greenhouse.replant",true,()->GreenhouseProgram.tick(m));h.assertTrue(h.getLevel().getBlockState(pos).is(Blocks.WHEAT),"Re-enabling replant resumes seed placement");
        }));h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void analysisProfileAndResearchDiagnosticsUseConfiguredCost(GameTestHelper h){
        setting("machine.analysing_chamber.energyOverride",10,()->setting("machine.analysing_chamber.ticksOverride",2,()->{var m=place(h,"analysing_chamber");m.inventory.setStackInSlot(0,new ItemStack(Content.item("circuit_board_1")));MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);h.assertTrue(MachineDiagnostics.machine(m).status()==MachineStatus.NEED_POWER&&m.progress==0,"Configured analyser cost blocks processing");m.energy.receiveEnergy(10,false);MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);h.assertTrue(m.progress==1,"Configured analyser duration applies");MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);h.assertTrue(m.inventory.getStackInSlot(0).isEmpty(),"Second powered tick completes analysis");}));
        var research=place(h,"research_chamber");research.program.putUUID("Owner",UUID.randomUUID());research.inventory.setStackInSlot(0,new ItemStack(Items.STONE));h.assertTrue(MachineDiagnostics.machine(research).status()==MachineStatus.NEED_POWER,"New research must report missing power before it has progress");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void liveCapacityDecreasePreservesExistingEnergy(GameTestHelper h){
        var m=place(h,"power_cube");m.energy.receiveEnergy(40000,false);
        setting("machine.power_cube.capacity",100,()->{h.assertTrue(m.energy.getMaxEnergyStored()==100&&m.energy.getEnergyStored()==40000,"Capacity reload must preserve previously stored FE");h.assertTrue(m.energy.receiveEnergy(1,false)==0,"Over-capacity banks cannot receive more FE");m.energy.extractEnergy(39950,false);h.assertTrue(m.energy.receiveEnergy(100,false)==50,"Normal capacity applies after draining excess");});h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void liveFreeFastMachineProfileReallyApplies(GameTestHelper h){
        setting("machine.stone_separator.energyOverride",0,()->setting("machine.stone_separator.ticksOverride",1,()->{
            var m=place(h,"stone_separator");m.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE));MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(0).isEmpty()&&m.inventory.getStackInSlot(14).getCount()==4,"A zero-FE, one-tick profile must process with no stored power");
        }));h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void recipeSwitchAndChanceOverrideApplyWithoutStaleCache(GameTestHelper h){
        var r=RecipeIndex.forMachine(h.getLevel(),"stone_separator").stream().filter(x->CalculatorConfig.value("recipe."+x.id()+".enabled").isPresent()).findFirst().orElseThrow();String prefix="recipe."+r.id()+".";
        setting(prefix+"enabled",false,()->h.assertTrue(RecipeIndex.forMachine(h.getLevel(),"stone_separator").stream().noneMatch(x->x.id().equals(r.id())),"Disabling a recipe must affect an already populated index"));
        setting(prefix+"chanceMultiplier",0.0,()->{var modified=RecipeIndex.forMachine(h.getLevel(),"stone_separator").stream().filter(x->x.id().equals(r.id())).findFirst().orElseThrow();h.assertTrue(modified.value().outputs().stream().allMatch(o->o.roll(h.getLevel().random).isEmpty()),"Chance zero suppresses all recipe outputs");});
        h.assertTrue(RecipeIndex.forMachine(h.getLevel(),"stone_separator").stream().anyMatch(x->x.id().equals(r.id())),"Re-enabling a process recipe restores it");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void batteryAutomationRequiresAnExplicitSide(GameTestHelper h){
        var m=place(h,"stone_separator");var port=m.automation(Direction.NORTH);var fuel=new ItemStack(Items.REDSTONE);
        h.assertTrue(!port.insertItem(20,fuel,false).isEmpty(),"Default sides retain manual battery behavior");m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),1);
        h.assertTrue(port.insertItem(20,fuel,false).isEmpty(),"Explicit input sides may insert configured batteries");
        setting("machine.stone_separator.itemAutomation",false,()->h.assertTrue(!port.insertItem(0,new ItemStack(Items.IRON_ORE),false).isEmpty(),"An existing capability must honor automation being disabled"));h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void bulkCountsAboveSixteenBitsRemainLossless(GameTestHelper h){
        setting("storage.reinforcedChestPerBin",100000,()->{var m=place(h,"reinforced_chest");for(int i=0;i<1100;i++)m.bulk.insertItem(0,new ItemStack(Items.STONE,64),false);var p=h.makeMockPlayer(GameType.SURVIVAL);var menu=new BulkStorageMenu(1,p.getInventory(),m);
            h.assertTrue(m.bulk.count(0)==70400&&menu.count(0)==70400,"Bulk counts use two synchronized halves");var saved=m.saveWithoutMetadata(h.getLevel().registryAccess());setting("storage.reinforcedChestPerBin",256,()->{var copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.loadWithComponents(saved,h.getLevel().registryAccess());h.assertTrue(copy.bulk.count(0)==70400,"Lowering a bin limit cannot delete its contents");});});h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void redstonePauseDoesNotConsumeInputsOrEnergy(GameTestHelper h){
        var m=place(h,"reinforced_furnace");m.energy.receiveEnergy(500,false);m.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON));m.program.putInt("RedstoneMode",3);
        for(int i=0;i<220;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        h.assertTrue(m.inventory.getStackInSlot(0).getCount()==1&&m.energy.getEnergyStored()==500&&m.progress==0,"Paused machine cannot work");h.assertTrue(MachineDiagnostics.machine(m).status()==MachineStatus.PAUSED,"Status must explain pause");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void researchUnlocksOnlyItsOwnerAndSurvivesSerialization(GameTestHelper h){
        var m=place(h,"research_chamber");var owner=UUID.randomUUID();m.program.putUUID("Owner",owner);m.energy.receiveEnergy(1000,false);m.inventory.setStackInSlot(0,new ItemStack(Items.STONE));
        h.assertTrue(RecipeIndex.forMachine(h.getLevel(),"calculator",owner).stream().noneMatch(r->r.value().research()&&r.value().researchGroup().equals("stone")),"Stone research begins locked");
        for(int i=0;i<100;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        var data=ResearchData.get(h.getLevel().getServer());h.assertTrue(data.groups(owner).contains("stone"),"Studying stone unlocks its category");h.assertTrue(m.inventory.getStackInSlot(0).getCount()==1&&m.energy.getEnergyStored()==0,"Default study preserves sample and costs 1000 FE");
        h.assertTrue(RecipeIndex.forMachine(h.getLevel(),"calculator",owner).stream().anyMatch(r->r.value().research()&&r.value().researchGroup().equals("stone")),"Owner can now use unlocked conversions");
        h.assertTrue(!data.groups(UUID.randomUUID()).contains("stone"),"Another player must remain locked");var copy=ResearchData.load(data.save(new net.minecraft.nbt.CompoundTag(),h.getLevel().registryAccess()),h.getLevel().registryAccess());h.assertTrue(copy.groups(owner).contains("stone"),"Research persists through save/load");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void portableSmeltingCommitsOnceAndSurvivesOpening(GameTestHelper h){
        setting("module.smelting_module.ticks",1,()->{var p=h.makeMockPlayer(GameType.SURVIVAL);var module=new ItemStack(Content.item("smelting_module"));p.setItemInHand(InteractionHand.MAIN_HAND,module);((CalculatorItem)module.getItem()).storage(module).receiveEnergy(3000,false);
            var inputs=NonNullList.withSize(3,ItemStack.EMPTY);inputs.set(0,new ItemStack(Items.RAW_IRON,2));module.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(inputs));
            SmeltingModule.tick(module,module,h.getLevel());SmeltingModule.tick(module,module,h.getLevel());var menu=new SmeltingModuleMenu(1,p.getInventory(),InteractionHand.MAIN_HAND);menu.broadcastChanges();
            h.assertTrue(menu.slots.get(0).getItem().getCount()==1&&menu.slots.get(2).getItem().is(Items.IRON_INGOT)&&menu.slots.get(2).getItem().getCount()==1,"Repeated same-tick updates and opening cannot double-smelt");h.assertTrue(((CalculatorItem)module.getItem()).energy(module)==2000,"Exactly one cycle charged");
            menu.clicked(2,0,net.minecraft.world.inventory.ClickType.PICKUP,p);menu.broadcastChanges();h.assertTrue(SmeltingModule.contents(module).get(2).isEmpty()&&menu.getCarried().getCount()==1,"Taking the output cannot be overwritten by a stale component");
            var restored=ItemStack.parse(h.getLevel().registryAccess(),module.save(h.getLevel().registryAccess())).orElseThrow();h.assertTrue(SmeltingModule.contents(restored).get(0).getCount()==1,"Remaining portable input persists");});h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void blockedPortableOutputRetainsInputAndPower(GameTestHelper h){
        setting("module.smelting_module.ticks",1,()->{var module=new ItemStack(Content.item("smelting_module"));var power=((CalculatorItem)module.getItem()).storage(module);power.receiveEnergy(1000,false);var contents=NonNullList.withSize(3,ItemStack.EMPTY);contents.set(0,new ItemStack(Items.RAW_IRON));contents.set(2,new ItemStack(Items.IRON_INGOT,64));module.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(contents));SmeltingModule.tick(module,module,h.getLevel());h.assertTrue(SmeltingModule.contents(module).get(0).getCount()==1&&power.getEnergyStored()==1000,"Full output prevents consumption");});h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void recipeTransferMovesOnlyIngredientsAndKeepsHeldCalculator(GameTestHelper h){
        var p=h.makeMockPlayer(GameType.SURVIVAL);var calculator=new ItemStack(Content.item("calculator"));p.setItemInHand(InteractionHand.MAIN_HAND,calculator);var menu=CalculatorMenu.forItem(1,p.getInventory(),InteractionHand.MAIN_HAND);
        p.getInventory().setItem(9,new ItemStack(Items.COBBLESTONE,3));p.getInventory().setItem(10,new ItemStack(Items.OAK_PLANKS,3));
        var input=new ProcessInput(List.of(new ItemStack(Items.COBBLESTONE),new ItemStack(Items.OAK_PLANKS)));var recipe=RecipeIndex.forMachine(h.getLevel(),"calculator",p.getUUID()).stream().filter(r->r.value().matches(input,h.getLevel())).findFirst().orElseThrow();
        h.assertTrue(RecipeTransfer.transfer(menu,recipe,p,2),"Recipe viewer can fill two recipe batches");h.assertTrue(menu.stillValid(p)&&p.getMainHandItem()==calculator,"Transfer cannot replace the locked calculator identity");h.assertTrue(menu.handler.getStackInSlot(0).getCount()+menu.handler.getStackInSlot(1).getCount()==4,"Only four matching ingredients moved");h.assertTrue(menu.handler.getStackInSlot(14).isEmpty(),"Recipe transfer never creates results");
        var data=ResearchData.get(h.getLevel().getServer());long before=data.mastery(p.getUUID()).getOrDefault("calculator",0L);((CalculatorItem)calculator.getItem()).storage(calculator).receiveEnergy(10,false);h.assertTrue(menu.clickMenuButton(p,0),"Transferred recipe can actually calculate");h.assertTrue(data.mastery(p.getUUID()).getOrDefault("calculator",0L)==before+1,"One committed recipe records exactly one mastery batch");var restored=ResearchData.load(data.save(new net.minecraft.nbt.CompoundTag(),h.getLevel().registryAccess()),h.getLevel().registryAccess());h.assertTrue(restored.mastery(p.getUUID()).getOrDefault("calculator",0L)==before+1,"Mastery survives save/load");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void wrenchCyclesFaceAndPreservesPackedMachine(GameTestHelper h){
        var m=place(h,"stone_separator");m.energy.receiveEnergy(713,false);m.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE,3));var p=h.makeMockPlayer(GameType.SURVIVAL);p.setPos(m.getBlockPos().getCenter());p.setItemInHand(InteractionHand.MAIN_HAND,new ItemStack(Content.item("wrench")));var ctx=new UseOnContext(p,InteractionHand.MAIN_HAND,new BlockHitResult(m.getBlockPos().getCenter(),Direction.NORTH,m.getBlockPos(),false));
        WrenchActions.use(ctx);h.assertTrue(m.program.getInt("Side"+Direction.NORTH.get3DDataValue())==1,"Wrench changes the clicked face");p.setShiftKeyDown(true);WrenchActions.use(ctx);h.assertTrue(h.getLevel().isEmptyBlock(m.getBlockPos()),"Sneaking removes the machine");
        var packed=p.getInventory().items.stream().filter(s->s.is(Content.item("stone_separator"))).findFirst().orElseThrow();var tag=packed.get(DataComponents.BLOCK_ENTITY_DATA).copyTag();var copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.loadWithComponents(tag,h.getLevel().registryAccess());h.assertTrue(copy.energy.getEnergyStored()==713&&copy.inventory.getStackInSlot(0).getCount()==3,"Packed machine retains energy and inventory");h.assertTrue(copy.program.getInt("Side"+Direction.NORTH.get3DDataValue())==1,"Packed side configuration survives");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void atomicTerrainRequiresReplacementAndPaysExactlyOnce(GameTestHelper h){
        var pos=h.absolutePos(new BlockPos(3,2,3));var target=pos.east();h.getLevel().setBlockAndUpdate(pos,Blocks.STONE.defaultBlockState());h.getLevel().setBlockAndUpdate(target,Blocks.DIRT.defaultBlockState());
        var p=h.makeMockPlayer(GameType.SURVIVAL);p.setPos(pos.getCenter());var module=new ItemStack(Content.item("atomic_terrain_module"));p.setItemInHand(InteractionHand.MAIN_HAND,module);var energy=((CalculatorItem)module.getItem()).storage(module);energy.receiveEnergy(10,false);
        java.util.function.Function<BlockPos,UseOnContext> ctx=b->new UseOnContext(p,InteractionHand.MAIN_HAND,new BlockHitResult(b.getCenter(),Direction.UP,b,false));p.setShiftKeyDown(true);AtomicTerrainModule.use(module,module,ctx.apply(pos));AtomicTerrainModule.use(module,module,ctx.apply(target));p.setShiftKeyDown(false);
        h.assertTrue(AtomicTerrainModule.use(module,module,ctx.apply(pos))==InteractionResult.FAIL&&h.getLevel().getBlockState(pos).is(Blocks.STONE),"Missing replacement must not alter the world");p.getInventory().setItem(9,new ItemStack(Items.DIRT));AtomicTerrainModule.use(module,module,ctx.apply(pos));h.assertTrue(h.getLevel().getBlockState(pos).is(Blocks.DIRT)&&p.getInventory().getItem(9).isEmpty()&&energy.getEnergyStored()==9,"Replacement block and one FE are committed together");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void publicPlantAdapterCanPlantAndHarvestWithoutLegacyCore(GameTestHelper h){
        var pos=h.absolutePos(new BlockPos(3,2,3));var marker=new ItemStack(Items.STRUCTURE_VOID);
        FoundationsPlants.register(new PlantAdapter(){public boolean accepts(ItemStack seed,int tier){return seed.is(Items.STRUCTURE_VOID);}public boolean canPlant(ItemStack seed,net.minecraft.server.level.ServerLevel l,BlockPos p){return l.isEmptyBlock(p);}public boolean plant(ItemStack seed,net.minecraft.server.level.ServerLevel l,BlockPos p){return l.setBlockAndUpdate(p,Blocks.MOSS_BLOCK.defaultBlockState());}public boolean canHarvest(net.minecraft.server.level.ServerLevel l,BlockPos p){return l.getBlockState(p).is(Blocks.MOSS_BLOCK);}public List<ItemStack> harvest(net.minecraft.server.level.ServerLevel l,BlockPos p){l.setBlockAndUpdate(p,Blocks.AIR.defaultBlockState());return List.of(new ItemStack(Items.MOSS_BLOCK));}});
        h.assertTrue(FoundationsPlants.plant(marker,h.getLevel(),pos,1)&&FoundationsPlants.canHarvest(h.getLevel(),pos),"Custom plant adapter participates in greenhouse operations");h.assertTrue(FoundationsPlants.harvest(h.getLevel(),pos).getFirst().is(Items.MOSS_BLOCK)&&h.getLevel().isEmptyBlock(pos),"Harvest removes the crop and returns its drops once");h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void craftTweakerRecipesIfInstalled(GameTestHelper h){
        if(net.neoforged.fml.ModList.get().isLoaded("crafttweaker")){
            var recipes=h.getLevel().getRecipeManager().getAllRecipesFor(Content.PROCESS_TYPE.get());var recipe=recipes.stream().filter(r->r.id().getPath().equals("foundations_ct_smoke")).findFirst().orElseThrow();h.assertTrue(recipe.value().energy()==17&&recipe.value().outputs().getFirst().stack().is(Items.DIAMOND),"CraftTweaker must add a native process recipe");
        }h.succeed();
    }    @GameTest(template="empty",batch="r4_config") public static void optionalEnergyConversionsConservePower(GameTestHelper h){
        if(net.neoforged.fml.ModList.get().isLoaded("mekanism"))EnergyIntegrationAssertions.mekanism(h);
        if(net.neoforged.fml.ModList.get().isLoaded("ae2"))EnergyIntegrationAssertions.ae2(h);
        h.succeed();
    }
    @GameTest(template="empty",batch="r4_config") public static void endDiamondIsReusableAndRespectsCooldown(GameTestHelper h){
        var p=h.makeMockPlayer(GameType.SURVIVAL);p.setPos(h.absolutePos(new BlockPos(3,3,3)).getCenter());var diamond=new ItemStack(Content.item("end_diamond"));p.setItemInHand(InteractionHand.MAIN_HAND,diamond);
        int before=h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.projectile.ThrownEnderpearl.class,p.getBoundingBox().inflate(8)).size();
        UtilityItems.use("end_diamond",diamond,diamond,h.getLevel(),p);UtilityItems.use("end_diamond",diamond,diamond,h.getLevel(),p);
        int after=h.getLevel().getEntitiesOfClass(net.minecraft.world.entity.projectile.ThrownEnderpearl.class,p.getBoundingBox().inflate(8)).size();
        h.assertTrue(after==before+1&&diamond.getCount()==1,"A reusable diamond launches one pearl and cannot bypass cooldown");h.succeed();
    }

}
