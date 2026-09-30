package com.foundations.calculator;

import com.foundations.calculator.api.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import java.util.*;
import java.util.function.Consumer;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.gametest.*;
import com.mojang.authlib.GameProfile;

/** Real-runtime regression tests. Not counted as passes until a native R7 GameTest server runs. */
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class R7GameTests {
    private static final UUID OWNER=UUID.fromString("e7c8c92b-7c7c-48d4-b30e-cc1d8e48f901");
    private static MachineBlockEntity machine(GameTestHelper h,String kind) {
        var pos=new BlockPos(8,4,8);h.setBlock(pos,Content.block(kind));
        var machine=(MachineBlockEntity)h.getBlockEntity(pos);machine.program.putUUID("Owner",OWNER);return machine;
    }
    private static void setFace(MachineBlockEntity machine,Direction face,int mode) {
        machine.program.putInt("Side"+face.get3DDataValue(),mode);
    }
    private static void frame(GameTestHelper h,MachineBlockEntity machine) {
        for(var part:GreenhouseProgram.blueprint(machine))h.getLevel().setBlockAndUpdate(part.pos(),
            (switch(part.type()){case LOG->Blocks.OAK_LOG;case STAIRS->Blocks.OAK_STAIRS;case GLASS->Blocks.GLASS;case PLANKS->Blocks.OAK_PLANKS;}).defaultBlockState());
    }
    private static void farmSettings(Runnable task) {
        R6GameTests.setting("greenhouse.basicPlantInterval",1,()->
        R6GameTests.setting("greenhouse.autoFarmland",false,()->
        R6GameTests.setting("greenhouse.autoWater",false,()->task.run())));
    }
    private static void denyAction(MachineWorldActions.Action action,Runnable task) {
        Consumer<MachineWorldActionEvent> listener=event->{if(event.getAction()==action)event.setCanceled(true);};
        NeoForge.EVENT_BUS.addListener(listener);try{task.run();}finally{NeoForge.EVENT_BUS.unregister(listener);}
    }
    @GameTest(template="large",batch="r7") public static void transferUpgradeHonorsEveryFace(GameTestHelper h) {
        var machine=machine(h,"processing_chamber");machine.inventory.setStackInSlot(21,new ItemStack(Content.item("transfer_upgrade")));
        for(var face:Direction.values())setFace(machine,face,3);
        for(var face:Direction.values()) {
            var target=machine.getBlockPos().relative(face);h.getLevel().setBlockAndUpdate(target,Content.block("reinforced_chest").defaultBlockState());
            var chest=(MachineBlockEntity)h.getLevel().getBlockEntity(target);machine.inventory.setStackInSlot(14,new ItemStack(Items.DIAMOND,8));
            for(int mode:new int[]{3,1}){setFace(machine,face,mode);MachinePrograms.export(machine);
                h.assertTrue(machine.inventory.getStackInSlot(14).getCount()==8&&chest.bulk.count(0)==0,"Disabled/input source face cannot export: "+face);}
            setFace(machine,face,2);MachinePrograms.export(machine);
            h.assertTrue(machine.inventory.getStackInSlot(14).getCount()==4&&chest.bulk.count(0)==4,"Output face moves exactly four items: "+face);
            setFace(machine,face,3);
        }h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void itemAutomationDisableClosesCachedAndActivePaths(GameTestHelper h) {
        var machine=machine(h,"processing_chamber");var face=Direction.NORTH;setFace(machine,face,2);
        machine.inventory.setStackInSlot(21,new ItemStack(Content.item("transfer_upgrade")));machine.inventory.setStackInSlot(14,new ItemStack(Items.DIAMOND,8));
        h.getLevel().setBlockAndUpdate(machine.getBlockPos().north(),Content.block("reinforced_chest").defaultBlockState());
        var cached=machine.automation(face);
        R6GameTests.setting("machine.processing_chamber.itemAutomation",false,()->{
            MachinePrograms.export(machine);
            h.assertTrue(cached.extractItem(14,4,false).isEmpty()&&machine.inventory.getStackInSlot(14).getCount()==8,"Active and cached paths obey disabled automation");
        });
        MachinePrograms.export(machine);h.assertTrue(machine.inventory.getStackInSlot(14).getCount()==4,"Restoring automation works without reloading the block");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void inventoryValidityAgreesWithInsertionGate(GameTestHelper h) {
        var machine=machine(h,"processing_chamber");var face=Direction.NORTH;var cached=machine.automation(face);
        for(int mode:new int[]{2,3}){setFace(machine,face,mode);
            h.assertTrue(!cached.isItemValid(0,new ItemStack(Items.DIRT))&&cached.insertItem(0,new ItemStack(Items.DIRT),false).getCount()==1,"Output/disabled inventory advertises no insertion");}
        setFace(machine,face,1);h.assertTrue(cached.isItemValid(0,new ItemStack(Items.DIRT)),"Input side recovers cached validity");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void greenhouseImportsFollowFrontSideAndMasterSwitch(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");frame(h,house);
        h.getLevel().setBlockAndUpdate(house.getBlockPos().north(),Content.block("reinforced_chest").defaultBlockState());
        var chest=(MachineBlockEntity)h.getLevel().getBlockEntity(house.getBlockPos().north());chest.bulk.insertItem(0,new ItemStack(Items.WHEAT_SEEDS,8),false);
        R6GameTests.setting("greenhouse.carbonInterval",1,()->R6GameTests.setting("greenhouse.autoPlant",false,()->farmSettings(()->{
            for(int mode:new int[]{2,3}){setFace(house,Direction.NORTH,mode);GreenhouseProgram.tick(house);h.assertTrue(chest.bulk.count(0)==8&&house.inventory.getStackInSlot(4).isEmpty(),"No import through output/disabled face");}
            setFace(house,Direction.NORTH,1);
            R6GameTests.setting("machine.basic_greenhouse.itemAutomation",false,()->{GreenhouseProgram.tick(house);h.assertTrue(chest.bulk.count(0)==8,"Master switch blocks greenhouse import");});
            GreenhouseProgram.tick(house);h.assertTrue(chest.bulk.count(0)==0&&house.inventory.getStackInSlot(4).getCount()==8,"Input face imports seeds once");
        })));h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void greenhouseBlockedExportRetainsHarvest(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");frame(h,house);setFace(house,Direction.NORTH,3);
        h.getLevel().setBlockAndUpdate(house.getBlockPos().north(),Content.block("reinforced_chest").defaultBlockState());
        var chest=(MachineBlockEntity)h.getLevel().getBlockEntity(house.getBlockPos().north());
        var crop=GreenhouseProgram.plantArea(house).getFirst();h.getLevel().setBlockAndUpdate(crop.below(),Blocks.FARMLAND.defaultBlockState());
        h.getLevel().setBlockAndUpdate(crop,Blocks.WHEAT.defaultBlockState().setValue(CropBlock.AGE,7));house.energy.load(1000);
        R6GameTests.setting("greenhouse.autoPlant",false,()->farmSettings(()->GreenhouseProgram.tick(house)));
        h.assertTrue(chest.bulk.count(0)==0&&house.pending.stream().anyMatch(s->s.is(Items.WHEAT)),"Blocked direct export retains wheat locally");
        h.assertTrue(house.program.getInt("Harvested")==1&&house.energy.getEnergyStored()==850,"One successful harvest costs once");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void cancelledBuildDoesNotConsumeResources(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");house.program.putInt("HouseState",1);house.energy.load(1000);
        house.inventory.setStackInSlot(0,new ItemStack(Items.OAK_LOG,64));house.inventory.setStackInSlot(1,new ItemStack(Items.OAK_STAIRS,64));
        house.inventory.setStackInSlot(2,new ItemStack(Items.GLASS,64));house.inventory.setStackInSlot(3,new ItemStack(Items.OAK_PLANKS,64));
        var target=GreenhouseProgram.blueprint(house).getFirst().pos();var before=h.getLevel().getBlockState(target);
        denyAction(MachineWorldActions.Action.BUILD,()->GreenhouseProgram.tick(house));
        h.assertTrue(h.getLevel().getBlockState(target).equals(before)&&house.energy.getEnergyStored()==1000&&house.pending.isEmpty(),"Denied builder changes no block, energy or drops");
        for(int i=0;i<4;i++)h.assertTrue(house.inventory.getStackInSlot(i).getCount()==64,"Denied builder retains every material");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void placeEventIsOwnerAttributedAndPreMutation(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");var target=house.getBlockPos().east(2);var before=Blocks.AIR.defaultBlockState();
        h.getLevel().setBlockAndUpdate(target,before);boolean[] observed={false};
        Consumer<BlockEvent.EntityPlaceEvent> listener=event->{if(!event.getPos().equals(target))return;
            observed[0]=true;h.assertTrue(event.getEntity().getUUID().equals(OWNER),"Placement uses stored owner UUID");
            h.assertTrue(event.getPlacedBlock().is(Blocks.OAK_LOG)&&event.getState().is(Blocks.OAK_LOG),"Event accessors contain the proposed state");
            h.assertTrue(h.getLevel().isEmptyBlock(target)&&event.getBlockSnapshot().getState().isAir(),"World and snapshot have not been changed");event.setCanceled(true);};
        NeoForge.EVENT_BUS.addListener(listener);
        try{h.assertTrue(!MachineWorldActions.replace(house,target,before,Blocks.OAK_LOG.defaultBlockState(),new ItemStack(Items.OAK_LOG),MachineWorldActions.Action.BUILD),"Cancelled placement refused");}
        finally{NeoForge.EVENT_BUS.unregister(listener);}
        h.assertTrue(observed[0]&&h.getLevel().isEmptyBlock(target),"Cancellation requires no temporary block or rollback");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void cancelledDemolitionCannotDuplicateRecovery(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");frame(h,house);house.program.putInt("HouseState",3);
        var target=GreenhouseProgram.blueprint(house).getFirst().pos();var before=h.getLevel().getBlockState(target);
        Consumer<BlockEvent.BreakEvent> listener=event->{if(event.getPos().equals(target))event.setCanceled(true);};
        NeoForge.EVENT_BUS.addListener(listener);try{for(int i=0;i<5;i++)GreenhouseProgram.tick(house);}finally{NeoForge.EVENT_BUS.unregister(listener);}
        h.assertTrue(h.getLevel().getBlockState(target).equals(before)&&house.pending.isEmpty(),"Repeated denied demolition creates no recovery items");
        GreenhouseProgram.tick(house);h.assertTrue(h.getLevel().isEmptyBlock(target)&&house.pending.size()==1,"Granted demolition recovers one item");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void cancelledSoilAndWaterDoNotDebitEnergy(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");frame(h,house);house.energy.load(10000);
        for(var crop:GreenhouseProgram.plantArea(house))h.getLevel().setBlockAndUpdate(crop.below(),Blocks.DIRT.defaultBlockState());
        var water=house.getBlockPos().south(2).below();h.getLevel().setBlockAndUpdate(water,Blocks.DIRT.defaultBlockState());
        R6GameTests.setting("greenhouse.carbonInterval",1,()->denyAction(MachineWorldActions.Action.FARMLAND,()->
            denyAction(MachineWorldActions.Action.WATER,()->GreenhouseProgram.tick(house))));
        h.assertTrue(house.energy.getEnergyStored()==10000&&h.getLevel().getBlockState(water).is(Blocks.DIRT),"Denied auto-water has no cost");
        for(var crop:GreenhouseProgram.plantArea(house))h.assertTrue(h.getLevel().getBlockState(crop.below()).is(Blocks.DIRT),"Denied farmland stays dirt");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void cancelledHarvestRetainsCropEnergyAndCounters(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");frame(h,house);var pos=GreenhouseProgram.plantArea(house).getFirst();
        h.getLevel().setBlockAndUpdate(pos.below(),Blocks.FARMLAND.defaultBlockState());var mature=Blocks.WHEAT.defaultBlockState().setValue(CropBlock.AGE,7);
        h.getLevel().setBlockAndUpdate(pos,mature);house.energy.load(1000);
        farmSettings(()->denyAction(MachineWorldActions.Action.HARVEST,()->GreenhouseProgram.tick(house)));
        h.assertTrue(h.getLevel().getBlockState(pos).equals(mature)&&house.energy.getEnergyStored()==1000&&house.pending.isEmpty()&&house.program.getInt("Harvested")==0,"Denied harvest has no cost, counter or drop");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void cancelledPlantingRetainsSeedsAndEnergy(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");frame(h,house);var pos=GreenhouseProgram.plantArea(house).getFirst();
        h.getLevel().setBlockAndUpdate(pos.below(),Blocks.FARMLAND.defaultBlockState());house.inventory.setStackInSlot(4,new ItemStack(Items.WHEAT_SEEDS,8));house.energy.load(1000);
        farmSettings(()->denyAction(MachineWorldActions.Action.PLANT,()->GreenhouseProgram.tick(house)));
        h.assertTrue(h.getLevel().isEmptyBlock(pos)&&house.inventory.getStackInSlot(4).getCount()==8&&house.energy.getEnergyStored()==1000,"Denied planting retains seed and energy");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void deniedGrowthAndLeafHarvestHaveNoYield(GameTestHelper h) {
        var machine=machine(h,"scarecrow");var pos=machine.getBlockPos().east(2);h.getLevel().setBlockAndUpdate(pos.below(),Blocks.FARMLAND.defaultBlockState());
        var crop=Blocks.WHEAT.defaultBlockState();h.getLevel().setBlockAndUpdate(pos,crop);
        denyAction(MachineWorldActions.Action.GROW,()->h.assertTrue(!MachinePrograms.grow(machine,pos),"Growth denied"));
        h.assertTrue(h.getLevel().getBlockState(pos).equals(crop),"Crop age unchanged");
        var leaf=Content.block("pear_leaves").defaultBlockState().setValue(HarvestLeaves.AGE,3);h.getLevel().setBlockAndUpdate(pos,leaf);
        denyAction(MachineWorldActions.Action.LEAF_HARVEST,()->h.assertTrue(MachinePrograms.harvestLeaves(machine,pos).isEmpty(),"No fruit on denied leaf reset"));
        h.assertTrue(h.getLevel().getBlockState(pos).equals(leaf),"Denied leaf age unchanged");
        var health=machine(h,"health_processor");var nutrientLeaf=health.getBlockPos().east();
        var mature=Content.block("tanzanite_leaves").defaultBlockState().setValue(HarvestLeaves.AGE,3);
        h.getLevel().setBlockAndUpdate(nutrientLeaf,mature);
        R6GameTests.setting("nutrition.networkInterval",1,()->denyAction(MachineWorldActions.Action.LEAF_HARVEST,()->MachinePrograms.nutritionNetwork(health,"health")));
        h.assertTrue(health.nutrient==0&&h.getLevel().getBlockState(nutrientLeaf).equals(mature),"Nutrition networks cannot bypass denied leaf harvest");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void ownerlessUnbreakableAndUnloadedActionsFailClosed(GameTestHelper h) {
        var machine=machine(h,"basic_greenhouse");var pos=machine.getBlockPos().east(2);h.getLevel().setBlockAndUpdate(pos,Blocks.DIRT.defaultBlockState());
        machine.program.remove("Owner");
        h.assertTrue(!MachineWorldActions.replace(machine,pos,Blocks.DIRT.defaultBlockState(),Blocks.AIR.defaultBlockState(),ItemStack.EMPTY,MachineWorldActions.Action.DEMOLISH),"Owner required by default");
        machine.program.putUUID("Owner",OWNER);h.getLevel().setBlockAndUpdate(pos,Blocks.BEDROCK.defaultBlockState());
        h.assertTrue(!MachineWorldActions.replace(machine,pos,Blocks.BEDROCK.defaultBlockState(),Blocks.AIR.defaultBlockState(),ItemStack.EMPTY,MachineWorldActions.Action.DEMOLISH),"Cannot change unbreakable block");
        var far=new BlockPos(20000000,64,20000000);h.assertTrue(!h.getLevel().hasChunkAt(far),"Remote test chunk starts unloaded");
        h.assertTrue(!MachineWorldActions.replace(machine,far,Blocks.AIR.defaultBlockState(),Blocks.DIRT.defaultBlockState(),ItemStack.EMPTY,MachineWorldActions.Action.BUILD)&&!h.getLevel().hasChunkAt(far),"Permission checks do not load remote chunks");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void listenerChangingTargetCannotBeOverwritten(GameTestHelper h) {
        var machine=machine(h,"basic_greenhouse");var pos=machine.getBlockPos().east(2);h.getLevel().setBlockAndUpdate(pos,Blocks.DIRT.defaultBlockState());
        Consumer<MachineWorldActionEvent> listener=event->{if(event.getTarget().equals(pos))h.getLevel().setBlockAndUpdate(pos,Blocks.DIAMOND_BLOCK.defaultBlockState());};
        NeoForge.EVENT_BUS.addListener(listener);try{
            h.assertTrue(!MachineWorldActions.replace(machine,pos,Blocks.DIRT.defaultBlockState(),Blocks.AIR.defaultBlockState(),ItemStack.EMPTY,MachineWorldActions.Action.DEMOLISH),"Changed expected state aborts Calculator commit");
        }finally{NeoForge.EVENT_BUS.unregister(listener);}
        h.assertTrue(h.getLevel().getBlockState(pos).is(Blocks.DIAMOND_BLOCK),"Does not roll back a different listener's change");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void unchangedMachinesDoNotRequestRepeatPackets(GameTestHelper h) {
        var machine=machine(h,"processing_chamber");h.assertTrue(machine.syncClientState(),"Initial state published");
        for(int i=0;i<100;i++)h.assertTrue(!machine.syncClientState(),"Unchanged state requests no packet");
        machine.energy.load(200);h.assertTrue(machine.syncClientState()&&!machine.syncClientState(),"Energy change publishes once");
        R6GameTests.setting("machine.processing_chamber.enabled",false,()->R6GameTests.setting("performance.clientSyncInterval",1,()->{
            machine.program.putInt("Side2",3);MachineBlockEntity.tick(h.getLevel(),machine.getBlockPos(),machine.getBlockState(),machine);
            h.assertTrue(!machine.syncClientState(),"Disabled tick still publishes changed settings");
        }));h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void chestLidTracksMultipleOpenersWithoutGlobalScan(GameTestHelper h) {
        var chest=machine(h,"reinforced_chest");
        var first=FakePlayerFactory.get(h.getLevel(),new GameProfile(OWNER,"R7TestA"));
        var second=FakePlayerFactory.get(h.getLevel(),new GameProfile(UUID.fromString("ce8b1c0c-5a88-47ad-b8c8-f14ac5d40093"),"R7TestB"));
        chest.startOpen(first);chest.startOpen(first);chest.startOpen(second);chest.stopOpen(first);
        h.assertTrue(chest.program.getBoolean("LidOpen"),"One remaining viewer keeps chest open");
        chest.stopOpen(second);h.assertTrue(!chest.program.getBoolean("LidOpen"),"Last viewer closes lid");chest.stopOpen(first);
        h.assertTrue(!chest.program.getBoolean("LidOpen"),"Duplicate close cannot underflow openers");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void greenhouseGeometryCachesAndInvalidates(GameTestHelper h) {
        var house=machine(h,"basic_greenhouse");var first=GreenhouseProgram.plantArea(house);var frame=GreenhouseProgram.blueprint(house);
        h.assertTrue(first.size()==8,"Basic planted area has eight cells");
        for(int i=0;i<100;i++)h.assertTrue(first==GreenhouseProgram.plantArea(house)&&frame==GreenhouseProgram.blueprint(house),"Unchanged geometry reused");
        h.getLevel().setBlockAndUpdate(house.getBlockPos(),house.getBlockState().setValue(MachineBlock.FACING,Direction.EAST));
        h.assertTrue(first!=GreenhouseProgram.plantArea(house)&&!first.equals(GreenhouseProgram.plantArea(house)),"Rotation invalidates geometry");
        var flawless=machine(h,"flawless_greenhouse");flawless.program.putInt("HouseSize",3);var three=GreenhouseProgram.plantArea(flawless);
        flawless.program.putInt("HouseSize",4);h.assertTrue(three.size()==6&&GreenhouseProgram.plantArea(flawless).size()==8,"Flawless length change invalidates rows");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void recipeSelectionInvalidatesOnCountAndConfig(GameTestHelper h) {
        var machine=machine(h,"extraction_chamber");machine.inventory.setStackInSlot(0,new ItemStack(Items.DIRT,2));var cache=new MachineRecipeSelection();
        var first=cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1);
        h.assertTrue(first.isPresent()&&first==cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1),"Unchanged match reused");
        machine.inventory.getStackInSlot(0).setCount(3);
        h.assertTrue(first!=cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1),"In-place count edit invalidates selection");
        var counted=cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1);
        machine.inventory.getStackInSlot(0).set(net.minecraft.core.component.DataComponents.CUSTOM_NAME,net.minecraft.network.chat.Component.literal("R7 component edit"));
        var named=cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1);
        h.assertTrue(counted!=named,"Component-only edit invalidates selection");
        RecipeIndex.clear();h.assertTrue(named!=cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1),"Recipe reload generation invalidates selection");
        String key="recipe."+first.get().id()+".enabled";
        R6GameTests.setting(key,false,()->h.assertTrue(cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1).stream().noneMatch(r->r.id().equals(first.get().id())),"Disabled cached recipe is not reused"));
        h.assertTrue(cache.find(h.getLevel(),"extraction_chamber",OWNER,machine.inventory,1).isPresent(),"Restored recipe is selectable");h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void researchUnlockChangesInvalidateButMasteryDoesNot(GameTestHelper h) {
        var data=ResearchData.get(h.getLevel().getServer());long initial=ResearchData.revision(h.getLevel());String group="r7_test_"+UUID.randomUUID();
        try {
            h.assertTrue(data.unlock(OWNER,group)&&ResearchData.revision(h.getLevel())!=initial,"Unlock changes selection generation");
            long unlocked=ResearchData.revision(h.getLevel());ResearchData.completed(h.getLevel(),OWNER,"calculator");
            h.assertTrue(ResearchData.revision(h.getLevel())==unlocked,"Mastery completion does not flush selectors");
            h.assertTrue(data.revoke(OWNER,group)&&ResearchData.revision(h.getLevel())!=unlocked,"Revoke invalidates generation");
        }finally{data.revoke(OWNER,group);}h.succeed();
    }
    @GameTest(template="large",batch="r7") public static void persistencePreservesDurableStateNotRuntimeCaches(GameTestHelper h) {
        var machine=machine(h,"basic_greenhouse");machine.energy.load(777);machine.inventory.setStackInSlot(4,new ItemStack(Items.WHEAT_SEEDS,8));
        machine.pending.add(new ItemStack(Items.WHEAT,3));machine.program.putBoolean("Checked",true);machine.program.putBoolean("LidOpen",true);
        machine.program.putInt("Side2",3);GreenhouseProgram.plantArea(machine);machine.syncClientState();
        var tag=machine.saveWithFullMetadata(h.getLevel().registryAccess());
        var restored=(MachineBlockEntity)BlockEntity.loadStatic(machine.getBlockPos(),machine.getBlockState(),tag,h.getLevel().registryAccess());
        h.assertTrue(restored!=null&&restored.energy.getEnergyStored()==777&&restored.inventory.getStackInSlot(4).getCount()==8&&restored.pending.getFirst().getCount()==3,"Energy, inventory and pending outputs survive save/load");
        h.assertTrue(OWNER.equals(restored.owner())&&restored.program.getInt("Side2")==3&&!restored.program.contains("Checked")&&!restored.program.getBoolean("LidOpen"),"Owner/sides persist; structural checks and lid state reset");h.succeed();
    }
    private R7GameTests() {}
}
