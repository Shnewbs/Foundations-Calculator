package com.foundations.calculator;

import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.gametest.*;
import net.neoforged.neoforge.capabilities.Capabilities;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class WorldProgramsGameTests {
    private static MachineBlockEntity machine(GameTestHelper h,String id){BlockPos pos=new BlockPos(8,2,8);h.setBlock(pos,Content.block(id));var machine=(MachineBlockEntity)h.getBlockEntity(pos);machine.program.putUUID("Owner",UUID.fromString("9a2ee808-d023-447a-8fe9-0c872a1d5717"));return machine;}
    private static void tick(GameTestHelper h,MachineBlockEntity m,int n){for(int i=0;i<n;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);}
    @GameTest(template="large") public static void atomicMultiplierPreservesComponentsAndChargesOnce(GameTestHelper h){
        MachineBlockEntity m=machine(h,"atomic_multiplier");ItemStack in=new ItemStack(Items.DIRT,2);var data=new net.minecraft.nbt.CompoundTag();data.putString("Proof","retained");UtilityItems.put(in,data);m.inventory.setStackInSlot(0,in);
        for(int i=1;i<=7;i++)m.inventory.setStackInSlot(i,new ItemStack(Content.item("circuit_board_"+i),2));
        m.energy.receiveEnergy(1500000000,false);tick(h,m,1001);
        h.assertTrue(m.inventory.getStackInSlot(0).getCount()==1&&m.inventory.getStackInSlot(1).getCount()==1,"Consumes one input and seven boards once");
        h.assertTrue(m.inventory.getStackInSlot(14).getCount()==4&&CircuitData.tag(m.inventory.getStackInSlot(14)).getString("Proof").equals("retained"),"Four copies retain components");
        h.assertTrue(m.energy.getEnergyStored()==0,"Exactly 1.5 billion FE consumed");h.succeed();
    }
    @GameTest(template="large") public static void multiplierBlacklistIsEnforced(GameTestHelper h){
        MachineBlockEntity m=machine(h,"atomic_multiplier");m.inventory.setStackInSlot(0,new ItemStack(Items.NETHER_STAR));for(int i=1;i<=7;i++)m.inventory.setStackInSlot(i,new ItemStack(Content.item("circuit_board_0")));m.energy.receiveEnergy(1500000000,false);tick(h,m,1100);
        h.assertTrue(m.inventory.getStackInSlot(0).getCount()==1&&m.energy.getEnergyStored()==1500000000,"Blacklisted item cannot consume or multiply");h.succeed();
    }
    @GameTest(template="large") public static void greenhouseBuildConsumesOnlyPlacedMaterials(GameTestHelper h){
        MachineBlockEntity m=machine(h,"basic_greenhouse");m.energy.receiveEnergy(350000,false);
        m.inventory.setStackInSlot(0,new ItemStack(Items.OAK_LOG,64));m.inventory.setStackInSlot(1,new ItemStack(Items.OAK_STAIRS,64));m.inventory.setStackInSlot(2,new ItemStack(Items.GLASS,64));m.inventory.setStackInSlot(3,new ItemStack(Items.OAK_PLANKS,64));m.program.putInt("HouseState",1);
        var blueprint=GreenhouseProgram.blueprint(m);tick(h,m,blueprint.size()+2);
        int used=0;for(int i=0;i<4;i++)used+=64-m.inventory.getStackInSlot(i).getCount();
        h.assertTrue(used==blueprint.size(),"Exactly one resource per unique position; duplicate legacy roof entries do not consume twice");
        h.assertTrue(m.program.getInt("HouseState")==2,"Basic greenhouse formed");
        h.assertTrue(m.energy.getEnergyStored()==350000-100*blueprint.size(),"Build FE is charged only for placed blocks");h.succeed();
    }
    @GameTest(template="large") public static void greenhouseDoesNotOverwriteObstructions(GameTestHelper h){
        MachineBlockEntity m=machine(h,"basic_greenhouse");var part=GreenhouseProgram.blueprint(m).getFirst();h.getLevel().setBlockAndUpdate(part.pos(),Blocks.DIAMOND_BLOCK.defaultBlockState());m.energy.receiveEnergy(5000,false);m.inventory.setStackInSlot(0,new ItemStack(Items.OAK_LOG,12));m.program.putInt("HouseState",1);tick(h,m,40);
        h.assertTrue(h.getLevel().getBlockState(part.pos()).is(Blocks.DIAMOND_BLOCK),"Builder must not replace an occupied block");h.assertTrue(m.inventory.getStackInSlot(0).getCount()==12&&m.energy.getEnergyStored()==5000,"Blocked builder consumes nothing");h.succeed();
    }
    @GameTest(template="large") public static void workstationOwnsModulesExactlyOnce(GameTestHelper h){
        MachineBlockEntity m=machine(h,"module_workstation");ItemStack calc=new ItemStack(Content.item("flawless_calculator"));m.inventory.setStackInSlot(20,calc);m.inventory.setStackInSlot(0,new ItemStack(Content.item("storage_module")));
        h.assertTrue(calc.get(Content.MODULES.get()).getStackInSlot(0).is(Content.item("storage_module")),"Module immediately written into calculator");
        ItemStack removed=m.inventory.extractItem(20,1,false);h.assertTrue(m.inventory.getStackInSlot(0).isEmpty(),"Removing calculator clears projected module slots");
        m.inventory.setStackInSlot(20,removed);h.assertTrue(m.inventory.getStackInSlot(0).is(Content.item("storage_module")),"Reinsertion restores modules");
        m.inventory.extractItem(0,1,false);h.assertTrue(m.inventory.getStackInSlot(20).get(Content.MODULES.get()).stream().allMatch(ItemStack::isEmpty),"Removing module clears calculator copy");h.succeed();
    }
    @GameTest(template="large") public static void bulkStorageKeepsLargeCountsAndComponents(GameTestHelper h){
        MachineBlockEntity m=machine(h,"reinforced_chest");for(int i=0;i<4;i++)h.assertTrue(m.bulk.insertItem(26,new ItemStack(Items.DIAMOND,64),false).isEmpty(),"Four ordinary stacks fit one bin");
        h.assertTrue(m.bulk.count(26)==256&&m.bulk.insertItem(26,new ItemStack(Items.DIAMOND),false).getCount()==1,"Bin capacity enforced");
        var saved=m.saveWithoutMetadata(h.getLevel().registryAccess());MachineBlockEntity copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.loadWithComponents(saved,h.getLevel().registryAccess());
        h.assertTrue(copy.bulk.count(26)==256&&copy.bulk.extractItem(26,1000,false).getCount()==64&&copy.bulk.count(26)==192,"Separate count persists and extraction returns legal stacks");h.succeed();
    }
    @GameTest(template="large") public static void circuitStorageSeparatesVariantAndCategory(GameTestHelper h){
        MachineBlockEntity m=machine(h,"storage_chamber");ItemStack dirty=new ItemStack(Content.item("circuit_dirty_3"),64);
        h.assertTrue(m.bulk.insertItem(0,dirty,true).getCount()==64,"Wrong circuit variant slot rejects input");
        for(int i=0;i<16;i++)h.assertTrue(m.bulk.insertItem(3,dirty,false).isEmpty(),"Full circuit capacity available");
        h.assertTrue(m.bulk.count(3)==1024,"1,024 circuits fit");h.assertTrue(!m.bulk.insertItem(4,new ItemStack(Content.item("circuit_damaged_4")),false).isEmpty(),"Cannot mix damaged and dirty categories");h.succeed();
    }
    @GameTest(template="large") public static void terrainModuleCostsOneAndCycles(GameTestHelper h){
        var player=h.makeMockPlayer(GameType.SURVIVAL);ItemStack module=new ItemStack(Content.item("terrain_module"));player.setItemInHand(InteractionHand.MAIN_HAND,module);module.getCapability(Capabilities.EnergyStorage.ITEM).receiveEnergy(2,false);
        BlockPos p=h.absolutePos(new BlockPos(8,2,8));h.getLevel().setBlockAndUpdate(p,Blocks.STONE.defaultBlockState());
        var context=new UseOnContext(player,InteractionHand.MAIN_HAND,new BlockHitResult(Vec3.atCenterOf(p),Direction.UP,p,false));
        UtilityItems.onBlock("terrain_module",module,module,context);
        h.assertTrue(h.getLevel().getBlockState(p).is(Blocks.GRASS_BLOCK)&&module.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==1,"Terrain transformation uses one FE");h.succeed();
    }
    @GameTest(template="large") public static void processingMachineDischargesBattery(GameTestHelper h){
        MachineBlockEntity m=machine(h,"stone_separator");ItemStack battery=new ItemStack(Content.item("energy_module"));battery.getCapability(Capabilities.EnergyStorage.ITEM).receiveEnergy(750,false);m.inventory.setStackInSlot(20,battery);tick(h,m,1);
        h.assertTrue(m.energy.getEnergyStored()==750&&battery.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==0,"Discharge conserves FE");h.succeed();
    }
    @GameTest(template="large") public static void controlledFuelAndLanternReturnBuckets(GameTestHelper h){
        MachineBlockEntity m=machine(h,"gas_lantern_off");m.inventory.setStackInSlot(0,new ItemStack(Items.LAVA_BUCKET));tick(h,m,2);
        h.assertTrue(m.burnTime>0&&m.getBlockState().getValue(MachineBlock.ACTIVE),"Fuel lights the lantern");h.assertTrue(m.inventory.getStackInSlot(14).is(Items.BUCKET),"Fuel bucket retained");h.succeed();
    }
    @GameTest(template="large") public static void weatherControlRequiresPowerAndSignal(GameTestHelper h){
        MachineBlockEntity m=machine(h,"weather_controller");m.program.putInt("Mode",1);m.program.putBoolean("Target",true);h.getLevel().setWeatherParameters(12000,0,false,false);m.energy.receiveEnergy(250000,false);tick(h,m,100);
        h.assertTrue(!h.getLevel().isRaining()&&m.energy.getEnergyStored()==250000,"No redstone means no weather change");
        h.getLevel().setBlockAndUpdate(m.getBlockPos().east(),Blocks.REDSTONE_BLOCK.defaultBlockState());tick(h,m,100);
        h.assertTrue(h.getLevel().getLevelData().isRaining()&&m.energy.getEnergyStored()==0,"Powered controller completes at exact cost");h.getLevel().setWeatherParameters(12000,0,false,false);h.succeed();
    }
    @GameTest(template="large") public static void locatorRejectsIncompleteRing(GameTestHelper h){
        MachineBlockEntity m=machine(h,"calculator_locator");h.assertTrue(MachinePrograms.locatorSize(h.getLevel(),m.getBlockPos())==0,"Empty locator does not generate");
        ItemStack module=new ItemStack(Content.item("locator_module"));var player=h.makeMockPlayer(GameType.SURVIVAL);UtilityItems.use("locator_module",module,module,h.getLevel(),player);m.inventory.setStackInSlot(0,module);tick(h,m,30);
        h.assertTrue(m.energy.getEnergyStored()==0&&!m.getBlockState().getValue(MachineBlock.ACTIVE),"Bound owner alone cannot power incomplete multiblock");h.succeed();
    }
    @GameTest(template="large") public static void programStateSurvivesReload(GameTestHelper h){
        MachineBlockEntity m=machine(h,"advanced_greenhouse");m.program.putInt("Carbon",97865);m.program.putInt("HouseState",1);m.program.putBoolean("Paused",true);m.energy.receiveEnergy(230000,false);
        var tag=m.saveWithoutMetadata(h.getLevel().registryAccess());MachineBlockEntity copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.loadWithComponents(tag,h.getLevel().registryAccess());
        h.assertTrue(copy.program.getInt("Carbon")==97865&&copy.program.getBoolean("Paused")&&copy.energy.getEnergyStored()==230000,"Multiblock state and high FE survive reload");h.succeed();
    }

    @GameTest(template="large") public static void stableLocatorGeneratesWithoutOnlineOwner(GameTestHelper h){
        MachineBlockEntity m=machine(h,"calculator_locator");BlockPos center=m.getBlockPos();
        for(int x=-1;x<=1;x++)for(int z=-1;z<=1;z++)if(x!=0||z!=0){
            h.getLevel().setBlockAndUpdate(center.offset(x,-1,z),Content.block("stable_stone_normal").defaultBlockState());
            h.getLevel().setBlockAndUpdate(center.offset(x,0,z),Content.block("calculator_plug").defaultBlockState());
            var plug=(MachineBlockEntity)h.getLevel().getBlockEntity(center.offset(x,0,z));ItemStack circuit=new ItemStack(Content.item("circuit_board_0"));var tag=new net.minecraft.nbt.CompoundTag();tag.putInt("Stable",1);tag.putBoolean("Analysed",true);UtilityItems.put(circuit,tag);plug.inventory.setStackInSlot(0,circuit);
        }
        for(int n=-1;n<=1;n++)for(int y=-1;y<=0;y++)for(BlockPos p:List.of(center.offset(n,y,2),center.offset(n,y,-2),center.offset(2,y,n),center.offset(-2,y,n)))h.getLevel().setBlockAndUpdate(p,Content.block("stable_stone_normal").defaultBlockState());
        ItemStack module=new ItemStack(Content.item("locator_module"));var tag=new net.minecraft.nbt.CompoundTag();tag.putUUID("Owner",java.util.UUID.randomUUID());UtilityItems.put(module,tag);m.inventory.setStackInSlot(0,module);
        tick(h,m,1);h.assertTrue(m.program.getInt("Size")==1&&m.program.getInt("Stability")==8&&m.energy.getEnergyStored()>0,"A complete stable ring generates without an online owner");
        h.getLevel().setBlockAndUpdate(center.east(),Blocks.AIR.defaultBlockState());m.program.remove("Size");int before=m.energy.getEnergyStored();tick(h,m,1);h.assertTrue(m.energy.getEnergyStored()==before&&!m.getBlockState().getValue(MachineBlock.ACTIVE),"Broken ring stops generation");h.succeed();
    }
    @GameTest(template="large",timeoutTicks=120) public static void greenhouseHarvestReplantsAndKeepsDrops(GameTestHelper h){
        MachineBlockEntity m=machine(h,"basic_greenhouse");for(var part:GreenhouseProgram.blueprint(m))h.getLevel().setBlockAndUpdate(part.pos(),(switch(part.type()){case LOG->Blocks.OAK_LOG;case STAIRS->Blocks.OAK_STAIRS;case GLASS->Blocks.GLASS;case PLANKS->Blocks.OAK_PLANKS;}).defaultBlockState());
        var p=GreenhouseProgram.plantArea(m).getFirst();h.getLevel().setBlockAndUpdate(p.below(),Blocks.FARMLAND.defaultBlockState().setValue(FarmBlock.MOISTURE,7));h.getLevel().setBlockAndUpdate(p,Blocks.WHEAT.defaultBlockState().setValue(CropBlock.AGE,7));m.inventory.setStackInSlot(4,new ItemStack(Items.WHEAT_SEEDS,8));m.energy.receiveEnergy(10000,false);
        h.runAtTickTime(80,()->{h.assertTrue(m.program.getInt("Harvested")>=1,"Mature crop harvested");h.assertTrue(h.getLevel().getBlockState(p).is(Blocks.WHEAT),"Seed replanted");h.assertTrue(java.util.stream.IntStream.range(14,20).anyMatch(slot->m.inventory.getStackInSlot(slot).is(Items.WHEAT)),"Harvest kept in outputs");h.succeed();});
    }
    @GameTest(template="large") public static void displayPacketDoesNotResizeClientInventory(GameTestHelper h){
        MachineBlockEntity m=machine(h,"stone_separator");m.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE));m.progress=40;m.totalTicks=200;
        var copy=new MachineBlockEntity(m.getBlockPos(),m.getBlockState());copy.handleUpdateTag(m.getUpdateTag(h.getLevel().registryAccess()),h.getLevel().registryAccess());
        h.assertTrue(copy.inventory.getSlots()==25&&copy.inventory.getStackInSlot(0).is(Items.IRON_ORE)&&copy.progress==40,"Partial display packets preserve inventory shape");h.succeed();
    }
    @GameTest(template="large") public static void sidePortsRespectDirectionAndNeverCreateEnergy(GameTestHelper h){
        MachineBlockEntity m=machine(h,"power_cube");m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),1);m.program.putInt("Side"+Direction.SOUTH.get3DDataValue(),2);
        var north=m.energyPort(Direction.NORTH);var south=m.energyPort(Direction.SOUTH);
        h.assertTrue(north.receiveEnergy(100,false)==100&&north.extractEnergy(50,false)==0,"Input side cannot extract");h.assertTrue(south.receiveEnergy(100,false)==0&&south.extractEnergy(50,false)==50&&m.energy.getEnergyStored()==50,"Output side cannot inject and exact energy is conserved");h.succeed();
    }
    @GameTest(template="large") public static void installedBatteriesPoolCapacityWithoutCreatingEnergy(GameTestHelper h){
        ItemStack calculator=new ItemStack(Content.item("flawless_calculator"));
        var modules=NonNullList.withSize(16,ItemStack.EMPTY);modules.set(0,new ItemStack(Content.item("energy_module")));modules.set(15,new ItemStack(Content.item("energy_module")));
        calculator.set(Content.MODULES.get(),net.minecraft.world.item.component.ItemContainerContents.fromItems(modules));
        var energy=calculator.getCapability(Capabilities.EnergyStorage.ITEM);int capacity=energy.getMaxEnergyStored();
        h.assertTrue(capacity==1200000,"Installed batteries extend base capacity");
        h.assertTrue(energy.receiveEnergy(Integer.MAX_VALUE,true)==capacity&&energy.getEnergyStored()==0,"Simulated charge cannot mutate installed modules");
        h.assertTrue(energy.receiveEnergy(Integer.MAX_VALUE,false)==capacity&&energy.getEnergyStored()==capacity,"Actual charge fills the pooled capacity once");
        h.assertTrue(energy.extractEnergy(1100000,true)==1100000&&energy.getEnergyStored()==capacity,"Simulated discharge is read-only");
        h.assertTrue(energy.extractEnergy(1100000,false)==1100000&&energy.getEnergyStored()==100000,"Discharge spans base and installed batteries exactly");
        h.assertTrue(energy.extractEnergy(Integer.MAX_VALUE,false)==100000&&energy.extractEnergy(1,false)==0,"Empty pool cannot provide extra FE");h.succeed();
    }
    @GameTest(template="large") public static void bulkMenuClickAndQuickMoveConserveItems(GameTestHelper h){
        MachineBlockEntity m=machine(h,"reinforced_chest");var player=h.makeMockPlayer(GameType.SURVIVAL);player.setPos(m.getBlockPos().getCenter());
        var menu=new com.foundations.calculator.menu.BulkStorageMenu(1,player.getInventory(),m);menu.setCarried(new ItemStack(Items.DIAMOND,64));
        menu.clicked(0,0,net.minecraft.world.inventory.ClickType.PICKUP,player);
        h.assertTrue(menu.getCarried().isEmpty()&&m.bulk.count(0)==64,"Manual deposit owns each item once");
        menu.clicked(0,1,net.minecraft.world.inventory.ClickType.PICKUP,player);
        h.assertTrue(menu.getCarried().getCount()==32&&m.bulk.count(0)==32,"Right click extracts half an ordinary stack");
        menu.quickMoveStack(player,0);
        h.assertTrue(m.bulk.count(0)==0&&player.getInventory().countItem(Items.DIAMOND)==32&&menu.getCarried().getCount()==32,"Shift extraction preserves both inventory and cursor items");h.succeed();
    }
}
