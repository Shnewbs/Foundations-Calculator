package com.foundations.calculator;

import com.foundations.calculator.content.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Alpha5R2GameTests {
    private static void banks(GameTestHelper h,java.util.function.BiConsumer<MachineBlockEntity,MachineBlockEntity> action){
        var a=new BlockPos(3,2,3);var b=new BlockPos(4,2,3);
        h.setBlock(a,Content.block("advanced_power_cube"));h.setBlock(b,Content.block("advanced_power_cube"));
        var source=(MachineBlockEntity)h.getBlockEntity(a);var target=(MachineBlockEntity)h.getBlockEntity(b);
        R6GameTests.setting("energy.machineCapacityMultiplier",1000000.0,()->
            R6GameTests.setting("energy.transferMultiplier",1000000.0,()->{
                source.energy.load(40_000_000_000L);target.energy.load(10_000_000_000L);action.accept(source,target);
            }));
    }
    @GameTest(template="empty",batch="alpha5_r2") public static void nativeBanksBalanceAboveIntegerLimit(GameTestHelper h){
        banks(h,(source,target)->{
            MachineBlockEntity.tick(h.getLevel(),source.getBlockPos(),source.getBlockState(),source);
            h.assertTrue(source.energy.stored()==25_000_000_000L&&target.energy.stored()==25_000_000_000L,"Banks equalize with one exact 15B transfer");
        });h.succeed();
    }
    @GameTest(template="empty",batch="alpha5_r2") public static void nativeBankRouteRetainsDisabledFeGate(GameTestHelper h){
        banks(h,(source,target)->R6GameTests.setting("power.fe.blockPorts",false,()->{
            MachineBlockEntity.tick(h.getLevel(),source.getBlockPos(),source.getBlockState(),source);
            h.assertTrue(source.energy.stored()==40_000_000_000L&&target.energy.stored()==10_000_000_000L,"Disabled FE port cannot be bypassed by the internal long route");
        }));h.succeed();
    }
    @GameTest(template="empty",batch="alpha5_r2") public static void nativeBankRouteRetainsInputSideGate(GameTestHelper h){
        banks(h,(source,target)->{
            target.program.putInt("Side"+Direction.WEST.get3DDataValue(),2);
            MachineBlockEntity.tick(h.getLevel(),source.getBlockPos(),source.getBlockState(),source);
            h.assertTrue(source.energy.stored()==40_000_000_000L&&target.energy.stored()==10_000_000_000L,"Output-only receiver face rejects a long transfer");
        });h.succeed();
    }
}
