package com.foundations.calculator;

import com.foundations.calculator.content.*;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class MultiblockGameTests {
    private static MachineBlockEntity place(GameTestHelper h, String kind) {
        var pos = new BlockPos(8, 4, 8);
        h.setBlock(pos, Content.block(kind));
        return (MachineBlockEntity) h.getBlockEntity(pos);
    }

    @GameTest(template="large")
    public static void flawlessFrameAcceptsGeneratorAndControlledFuelPauses(GameTestHelper h) {
        var house = place(h, "flawless_greenhouse");
        var origin = house.getBlockPos();
        // Controller faces north; growing rows extend south and width extends west.
        for (int front = 0; front <= 3; front++) {
            for (int side = 0; side <= 3; side++) {
                h.getLevel().setBlockAndUpdate(origin.south(front).west(side).above(2), Blocks.QUARTZ_SLAB.defaultBlockState());
                h.getLevel().setBlockAndUpdate(origin.south(front).west(side).below(),
                    (front == 0 || front == 3 ? Content.block("stable_stone_normal") : Blocks.DIRT).defaultBlockState());
            }
            for (int side : new int[]{0, 3}) for (int y = 0; y <= 1; y++) {
                var pos = origin.south(front).west(side).above(y);
                if (!pos.equals(origin)) h.getLevel().setBlockAndUpdate(pos,
                    Content.block(front == 0 || front == 3 ? "stable_stone_normal" : "flawless_glass").defaultBlockState());
            }
        }
        h.getLevel().setBlockAndUpdate(origin.west(3), Content.block("co2_generator").defaultBlockState());
        var generator = (MachineBlockEntity) h.getLevel().getBlockEntity(origin.west(3));
        h.assertTrue(GreenhouseProgram.checkFlawless(house) && house.program.getInt("HouseSize") == 2,
            "Generator is a valid end-frame block; two growing rows form");
        generator.inventory.setStackInSlot(0, new ItemStack(Content.item("controlled_fuel")));
        generator.energy.receiveEnergy(100000, false);
        GreenhouseProgram.carbonGenerator(generator);
        h.assertTrue(generator.energy.getEnergyStored() == 0 && generator.program.getInt("GasAdd") == 800,
            "One fuel consumes 100,000 FE and supplies controlled gas");
        int remaining = generator.burnTime;
        house.program.putInt("Carbon", 100000);
        GreenhouseProgram.carbonGenerator(generator);
        h.assertTrue(generator.burnTime == remaining && generator.program.getInt("GasAdd") == 0,
            "Full CO2 pauses fuel consumption");
        house.program.putInt("Carbon", 92000);
        GreenhouseProgram.carbonGenerator(generator);
        h.assertTrue(generator.burnTime == remaining - 1 && generator.program.getInt("GasAdd") == 800,
            "Control resumes at 92 percent");
        h.getLevel().setBlockAndUpdate(origin.south().above(2), Blocks.AIR.defaultBlockState());
        h.assertTrue(!GreenhouseProgram.checkFlawless(house), "Missing roof invalidates the structure");
        h.succeed();
    }

    @GameTest(template="large")
    public static void dynamicCalculatorRequiresShellAndPreservesOutputs(GameTestHelper h) {
        var machine = place(h, "dynamic_calculator");
        var center = machine.getBlockPos().south(3);
        for (int y=-3; y<=3; y++) for (int x=-3; x<=3; x++) for (int z=-3; z<=3; z++) {
            var pos=center.offset(x,y,z);
            if (pos.equals(machine.getBlockPos())) continue;
            boolean side=Math.abs(x)==3 || Math.abs(z)==3;
            boolean frame=Math.abs(y)==3 && side || Math.abs(x)==3 && Math.abs(z)==3;
            var block=frame ? Content.block("stable_stone_normal") :
                side || Math.abs(y)==3 ? Content.block("flawless_glass") : Blocks.AIR;
            h.getLevel().setBlockAndUpdate(pos,block.defaultBlockState());
        }
        h.assertTrue(DynamicProgram.formed(machine), "Complete original shell forms");
        machine.inventory.setStackInSlot(0,new ItemStack(Items.COBBLESTONE,2));
        machine.inventory.setStackInSlot(1,new ItemStack(Items.OAK_PLANKS,2));
        machine.energy.receiveEnergy(1,false);
        DynamicProgram.tick(machine);
        h.assertTrue(machine.pending.size()==1 && machine.pending.getFirst().is(Content.item("reinforced_stone_block"))
            && machine.inventory.getStackInSlot(0).getCount()==1 && machine.energy.getEnergyStored()==0,
            "Basic lane consumes once and retains its result");
        h.getLevel().setBlockAndUpdate(center.above(3),Blocks.AIR.defaultBlockState());
        machine.program.remove("DynamicFormed");machine.energy.receiveEnergy(1,false);
        DynamicProgram.tick(machine);
        h.assertTrue(machine.inventory.getStackInSlot(0).getCount()==1 && machine.energy.getEnergyStored()==1,
            "Broken shell cannot consume inputs or energy");
        h.succeed();
    }

    @GameTest(template="large")
    public static void magnetHonorsWhitelistAndRetainsBlockedItems(GameTestHelper h) {
        var magnet=place(h,"magnetic_flux");
        h.getLevel().setBlockAndUpdate(magnet.getBlockPos().below(),Content.block("reinforced_chest").defaultBlockState());
        var chest=(MachineBlockEntity)h.getLevel().getBlockEntity(magnet.getBlockPos().below());
        magnet.program.putBoolean("Whitelist",true);
        magnet.inventory.setStackInSlot(0,new ItemStack(Items.DIAMOND));
        Vec3 target=Vec3.atBottomCenterOf(magnet.getBlockPos()).add(0,.2,0);
        var diamond=new ItemEntity(h.getLevel(),target.x,target.y,target.z,new ItemStack(Items.DIAMOND,7));
        var iron=new ItemEntity(h.getLevel(),target.x,target.y,target.z,new ItemStack(Items.IRON_INGOT,5));
        h.getLevel().addFreshEntity(diamond);h.getLevel().addFreshEntity(iron);
        MachinePrograms.tick(magnet);
        h.assertTrue(diamond.isRemoved() && chest.bulk.count(0)==7 && !iron.isRemoved() && iron.getItem().getCount()==5,
            "Only the whitelisted stack moves to storage");
        h.getLevel().setBlockAndUpdate(magnet.getBlockPos().below(),Blocks.STONE.defaultBlockState());
        var blocked=new ItemEntity(h.getLevel(),target.x,target.y,target.z,new ItemStack(Items.DIAMOND,3));
        h.getLevel().addFreshEntity(blocked);MachinePrograms.tick(magnet);
        h.assertTrue(!blocked.isRemoved() && blocked.getItem().getCount()==3,"Missing destination cannot delete items");
        h.succeed();
    }

    @GameTest(template="large")
    public static void mastUpperBlockBreakRemovesWholeMachineOnce(GameTestHelper h) {
        var mast=place(h,"conductor_mast");var pos=mast.getBlockPos();
        ((MachineBlock)mast.getBlockState().getBlock()).setPlacedBy(h.getLevel(),pos,mast.getBlockState(),
            h.makeMockPlayer(GameType.SURVIVAL),new ItemStack(Content.item("conductor_mast")));
        h.assertTrue(h.getLevel().getBlockState(pos.above(3)).is(Content.EXTENSION.get()),"Mast reserves its full height");
        h.getLevel().destroyBlock(pos.above(2),true);
        for(int y=0;y<4;y++)h.assertTrue(h.getLevel().isEmptyBlock(pos.above(y)),"Breaking upper part removes all sections");
        long drops=h.getLevel().getEntitiesOfClass(ItemEntity.class,new net.minecraft.world.phys.AABB(pos).inflate(4)).stream()
            .filter(e->e.getItem().is(Content.item("conductor_mast"))).mapToInt(e->e.getItem().getCount()).sum();
        h.assertTrue(drops==1,"Whole mast drops exactly one controller");h.succeed();
    }
}
