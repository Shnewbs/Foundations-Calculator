package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.BulkStorageMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.neoforged.neoforge.gametest.*;

/** Final 2a upgrade/storage regressions discovered during in-game acceptance. */
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class R2GameTests {
    private static MachineBlockEntity place(GameTestHelper h,String kind){
        var pos=new BlockPos(8,4,8);h.setBlock(pos,Content.block(kind));return (MachineBlockEntity)h.getBlockEntity(pos);
    }
    @GameTest(template="large",batch="r2_final") public static void reinforcedFurnaceHonorsSpeedAndEnergyUpgrades(GameTestHelper h){
        R6GameTests.setting("machine.reinforced_furnace.energyOverride",500,()->R6GameTests.setting("machine.reinforced_furnace.ticksOverride",20,()->{
            var m=place(h,"reinforced_furnace");m.inventory.setStackInSlot(0,new ItemStack(Items.RAW_IRON));
            m.inventory.setStackInSlot(21,new ItemStack(Content.item("speed_upgrade"),4));
            m.inventory.setStackInSlot(22,new ItemStack(Content.item("energy_upgrade"),4));
            int cost=m.upgradeEnergyCost(500);h.assertTrue(cost==357,"Four default Energy Upgrades reduce 500 FE to 357 FE");
            h.assertTrue(m.upgradeProcessTicks(20)==10,"Four default Speed Upgrades reduce 20 ticks to 10");
            h.assertTrue(MachineDiagnostics.machine(m).requiredEnergy()==357,"Furnace diagnostics report the upgraded FE cost");
            m.energy.receiveEnergy(cost,false);for(int i=0;i<9;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(0).is(Items.RAW_IRON),"Furnace must not finish before the upgraded duration");
            MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(14).is(Items.IRON_INGOT)&&m.energy.getEnergyStored()==0,"Furnace completes once using the upgraded duration and FE cost");
        }));h.succeed();
    }
    @GameTest(template="large",batch="r2_final") public static void analysingChamberHonorsSpeedAndEnergyUpgrades(GameTestHelper h){
        R6GameTests.setting("machine.analysing_chamber.energyOverride",100,()->R6GameTests.setting("machine.analysing_chamber.ticksOverride",20,()->{
            var m=place(h,"analysing_chamber");m.inventory.setStackInSlot(0,new ItemStack(Content.item("circuit_board_1")));
            m.inventory.setStackInSlot(21,new ItemStack(Content.item("speed_upgrade"),4));
            m.inventory.setStackInSlot(22,new ItemStack(Content.item("energy_upgrade"),4));
            int cost=m.upgradeEnergyCost(100);h.assertTrue(cost==71&&m.upgradeProcessTicks(20)==10,"Analyser uses shared upgrade math");
            h.assertTrue(MachineDiagnostics.machine(m).requiredEnergy()==71,"Analyser diagnostics report the upgraded FE cost");
            m.energy.receiveEnergy(cost,false);for(int i=0;i<10;i++)MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
            h.assertTrue(m.inventory.getStackInSlot(0).isEmpty()&&m.energy.getEnergyStored()==0,"Analyser completes using upgraded duration and FE cost");
        }));h.succeed();
    }
    @GameTest(template="large",batch="r2_final") public static void voidUpgradeClearsOnlyBlockedPendingOutput(GameTestHelper h){
        var m=place(h,"processing_chamber");for(int i=14;i<20;i++)m.inventory.setStackInSlot(i,new ItemStack(Items.COBBLESTONE,64));
        m.inventory.setStackInSlot(21,new ItemStack(Content.item("void_upgrade")));m.pending.add(new ItemStack(Items.DIAMOND));
        MachineBlockEntity.tick(h.getLevel(),m.getBlockPos(),m.getBlockState(),m);
        h.assertTrue(m.pending.isEmpty(),"Void Upgrade clears output only after all normal output slots reject it");
        for(int i=14;i<20;i++)h.assertTrue(m.inventory.getStackInSlot(i).is(Items.COBBLESTONE)&&m.inventory.getStackInSlot(i).getCount()==64,"Void Upgrade does not disturb existing output");
        h.succeed();
    }
    @GameTest(template="large",batch="r2_final") public static void reinforcedChestShowsOneIconCountAndSeparateBulkAmount(GameTestHelper h){
        var m=place(h,"reinforced_chest");m.bulk.insertItem(0,new ItemStack(Items.COBBLESTONE,64),false);m.bulk.insertItem(0,new ItemStack(Items.COBBLESTONE,12),false);
        var player=h.makeMockPlayer(GameType.SURVIVAL);player.setPos(m.getBlockPos().getCenter());var menu=new BulkStorageMenu(1,player.getInventory(),m);
        h.assertTrue(menu.count(0)==76,"Bulk count remains the authoritative 76 items");
        h.assertTrue(menu.slots.get(0).getItem().is(Items.COBBLESTONE)&&menu.slots.get(0).getItem().getCount()==1,"Menu icon is normalized to one item so vanilla does not draw a misleading stack count");
        menu.removed(player);h.succeed();
    }
    private R2GameTests(){}
}
