package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.*;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class MachineOperationGameTests {
    private static MachineBlockEntity place(GameTestHelper h,BlockPos pos,String kind){
        h.setBlock(pos,Content.block(kind));return (MachineBlockEntity)h.getBlockEntity(pos);
    }
    @GameTest(template="large",timeoutTicks=260)
    public static void furnaceRunsFromCoalInsertedThroughMenu(GameTestHelper h){
        var machine=place(h,new BlockPos(8,4,8),"reinforced_furnace");
        var player=h.makeMockPlayer(GameType.SURVIVAL);player.setPos(machine.getBlockPos().getCenter());
        var menu=CalculatorMenu.forMachine(1,player.getInventory(),machine);
        menu.setCarried(new ItemStack(Items.COAL));menu.clicked(20,0,ClickType.PICKUP,player);
        h.assertTrue(menu.getCarried().isEmpty()&&machine.inventory.getStackInSlot(20).is(Items.COAL),"Power slot must accept original coal fuel");
        menu.setCarried(new ItemStack(Items.RAW_IRON));menu.clicked(0,0,ClickType.PICKUP,player);
        h.runAtTickTime(220,()->{
            h.assertTrue(machine.inventory.getStackInSlot(14).is(Items.IRON_INGOT),"Player-supplied coal powers a real smelting cycle");
            h.assertTrue(machine.inventory.getStackInSlot(20).isEmpty()&&machine.energy.getEnergyStored()==0,"One coal supplies exactly 500 FE");h.succeed();
        });
    }
    @GameTest(template="large",timeoutTicks=80)
    public static void installingModulesDoesNotDischargeCalculator(GameTestHelper h){
        var workstation=place(h,new BlockPos(8,4,8),"module_workstation");
        var calculator=new ItemStack(Content.item("flawless_calculator"));
        calculator.getCapability(Capabilities.EnergyStorage.ITEM).receiveEnergy(10000,false);
        workstation.inventory.setStackInSlot(20,calculator);
        workstation.inventory.setStackInSlot(0,new ItemStack(Content.item("storage_module")));
        h.runAtTickTime(30,()->{
            h.assertTrue(calculator.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==10000,"Workstation must retain all calculator charge");
            h.assertTrue(workstation.energy.getEnergyStored()==0,"Unpowered workstation cannot absorb energy");h.succeed();
        });
    }
    @GameTest(template="large",timeoutTicks=280)
    public static void handCrankThroughCubePowersSeparator(GameTestHelper h){
        var base=new BlockPos(7,4,8);
        var generator=place(h,base,"hand_cranked_generator");
        place(h,base.east(),"power_cube");var separator=place(h,base.east(2),"stone_separator");
        var player=h.makeMockPlayer(GameType.SURVIVAL);player.setPos(generator.getBlockPos().getCenter());
        separator.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE));
        for(int tick=1;tick<=30;tick++)h.runAtTickTime(tick,()->generator.getBlockState().useWithoutItem(h.getLevel(),player,
            new BlockHitResult(generator.getBlockPos().getCenter(),Direction.UP,generator.getBlockPos(),false)));
        h.runAtTickTime(250,()->{
            h.assertTrue(separator.inventory.getStackInSlot(14).is(Content.item("reinforced_iron_ingot")),"Crank -> Power Cube -> Separator must deliver usable FE");
            h.assertTrue(separator.inventory.getStackInSlot(14).getCount()==4,"Original ore recipe completes once");h.succeed();
        });
    }
    @GameTest(template="large")
    public static void processingEnergyPortCannotBeDrainedByCable(GameTestHelper h){
        var machine=place(h,new BlockPos(8,4,8),"stone_separator");var port=machine.energyPort(Direction.NORTH);
        h.assertTrue(port.receiveEnergy(1000,false)==1000,"Default processing port receives FE");
        h.assertTrue(!port.canExtract()&&port.extractEnergy(1000,false)==0&&machine.energy.getEnergyStored()==1000,"Default cable access must not drain a processing machine");h.succeed();
    }
    @GameTest(template="large",timeoutTicks=80)
    public static void powerCubeShiftClickChargesCalculatorFromRedstone(GameTestHelper h){
        var cube=place(h,new BlockPos(8,4,8),"power_cube");var player=h.makeMockPlayer(GameType.SURVIVAL);player.setPos(cube.getBlockPos().getCenter());
        var menu=CalculatorMenu.forMachine(1,player.getInventory(),cube);
        player.getInventory().setItem(9,new ItemStack(Items.REDSTONE));menu.quickMoveStack(player,25);
        player.getInventory().setItem(9,new ItemStack(Content.item("calculator")));menu.quickMoveStack(player,25);
        h.assertTrue(cube.inventory.getStackInSlot(0).is(Items.REDSTONE)&&cube.inventory.getStackInSlot(20).is(Content.item("calculator")),"Shift-click routes discharge fuel and the chargeable item separately");
        h.runAtTickTime(30,()->{
            int charge=cube.inventory.getStackInSlot(20).getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored();
            h.assertTrue(charge>0&&charge+cube.energy.getEnergyStored()==1000,"One redstone's 1000 FE moves into the calculator without loss or duplication");h.succeed();
        });
    }
    @GameTest(template="large",timeoutTicks=80)
    public static void consumablePowerWaitsUntilEntireFuelFits(GameTestHelper h){
        var cube=place(h,new BlockPos(8,4,8),"power_cube");
        cube.energy.receiveEnergy(cube.energy.getMaxEnergyStored()-999,false);
        cube.inventory.setStackInSlot(0,new ItemStack(Items.REDSTONE));
        h.runAtTickTime(10,()->{
            h.assertTrue(cube.inventory.getStackInSlot(0).getCount()==1,"Indivisible fuel must not be partially wasted");
            cube.energy.extractEnergy(1,false);
        });
        h.runAtTickTime(20,()->{
            h.assertTrue(cube.inventory.getStackInSlot(0).isEmpty()&&cube.energy.getEnergyStored()==cube.energy.getMaxEnergyStored(),"Exactly 1000 FE of room consumes exactly one redstone");h.succeed();
        });
    }
    @GameTest(template="large")
    public static void greenhouseShiftClickKeepsSeedsOutOfMaterials(GameTestHelper h){
        var house=place(h,new BlockPos(8,4,8),"basic_greenhouse");var player=h.makeMockPlayer(GameType.SURVIVAL);
        var menu=CalculatorMenu.forMachine(1,player.getInventory(),house);
        player.getInventory().setItem(9,new ItemStack(Items.WHEAT_SEEDS));menu.quickMoveStack(player,25);
        player.getInventory().setItem(9,new ItemStack(Items.OAK_LOG));menu.quickMoveStack(player,25);
        h.assertTrue(house.inventory.getStackInSlot(4).is(Items.WHEAT_SEEDS)&&house.inventory.getStackInSlot(0).is(Items.OAK_LOG),"Seeds and frame materials must reach their respective slots");h.succeed();
    }
    @GameTest(template="large",timeoutTicks=80)
    public static void extractorShiftClickGeneratesAndChargesItem(GameTestHelper h){
        var extractor=place(h,new BlockPos(8,4,8),"redstone_extractor");var player=h.makeMockPlayer(GameType.SURVIVAL);
        var menu=CalculatorMenu.forMachine(1,player.getInventory(),extractor);
        for(var item:new Item[]{Items.COAL,Items.REDSTONE_BLOCK,Content.item("calculator")}){
            player.getInventory().setItem(9,new ItemStack(item));menu.quickMoveStack(player,25);
        }
        h.assertTrue(extractor.inventory.getStackInSlot(0).is(Items.COAL)&&extractor.inventory.getStackInSlot(1).is(Items.REDSTONE_BLOCK)&&extractor.inventory.getStackInSlot(20).is(Content.item("calculator")),"Fuel, feed and charge item route to distinct slots");
        h.runAtTickTime(30,()->{
            h.assertTrue(extractor.burnTime>0&&extractor.inventory.getStackInSlot(20).getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()>0,"Extractor must generate and charge without an external energy source");h.succeed();
        });
    }
    @GameTest(template="large",timeoutTicks=340)
    public static void consecutiveCubesDeliverFuelPowerToProcessor(GameTestHelper h){
        var base=new BlockPos(6,4,8);var first=place(h,base,"power_cube");
        var second=place(h,base.east(),"advanced_power_cube");var separator=place(h,base.east(2),"stone_separator");
        first.inventory.setStackInSlot(0,new ItemStack(Items.REDSTONE,4));separator.inventory.setStackInSlot(0,new ItemStack(Items.IRON_ORE));
        h.runAtTickTime(310,()->{
            h.assertTrue(separator.inventory.getStackInSlot(14).getCount()==4,"Automatic unequal-capacity cubes must deliver enough FE to process");
            h.assertTrue(first.energy.getEnergyStored()+second.energy.getEnergyStored()+separator.energy.getEnergyStored()==3500,"Four redstone pay one 500 FE recipe without loss");h.succeed();
        });
    }
    @GameTest(template="large")
    public static void operatingStatusExplainsInputPowerAndBlockedOutput(GameTestHelper h){
        var furnace=place(h,new BlockPos(8,4,8),"reinforced_furnace");
        h.assertTrue(MachineDiagnostics.machine(furnace).status()==MachineStatus.NEED_INPUT,"Empty machine explains missing inputs");
        furnace.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON));
        var empty=MachineDiagnostics.machine(furnace);h.assertTrue(empty.status()==MachineStatus.NEED_POWER&&empty.requiredEnergy()==500,"Valid recipe explains its FE requirement");
        furnace.energy.receiveEnergy(500,false);
        h.assertTrue(MachineDiagnostics.machine(furnace).status()==MachineStatus.RUNNING,"Powered valid recipe is processing");
        furnace.inventory.setStackInSlot(0,new ItemStack(Items.STICK));
        h.assertTrue(MachineDiagnostics.machine(furnace).status()==MachineStatus.NO_RECIPE,"Invalid input is distinct from missing power");
        furnace.pending.add(new ItemStack(Items.IRON_INGOT));
        h.assertTrue(MachineDiagnostics.machine(furnace).status()==MachineStatus.OUTPUT_BLOCKED,"Pending output explains a stopped processor");h.succeed();
    }
}
