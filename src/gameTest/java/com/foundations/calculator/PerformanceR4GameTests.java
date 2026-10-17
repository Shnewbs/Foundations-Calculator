package com.foundations.calculator;

import com.foundations.calculator.content.*;
import net.minecraft.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.*;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class PerformanceR4GameTests {
    @GameTest(template="large",batch="performance_r4") public static void allPipeConnectionsKeepExactGeometry(GameTestHelper h){
        var pipe=Content.block("amethyst_piping");
        for(int mask=0;mask<64;mask++){
            var state=pipe.defaultBlockState();var expected=Block.box(5,5,5,11,11,11);
            for(Direction d:Direction.values()){
                boolean connected=(mask&(1<<d.get3DDataValue()))!=0;state=state.setValue(ConnectedDecorBlock.SIDES.get(d),connected);
                if(connected)expected=Shapes.or(expected,switch(d){case UP->Block.box(5,11,5,11,16,11);case DOWN->Block.box(5,0,5,11,5,11);case EAST->Block.box(11,5,5,16,11,11);case WEST->Block.box(0,5,5,5,11,11);case NORTH->Block.box(5,5,0,11,11,5);case SOUTH->Block.box(5,5,11,11,11,16);});
            }
            var shape=state.getShape(h.getLevel(),h.absolutePos(new BlockPos(8,4,8)));
            h.assertTrue(shape==state.getShape(h.getLevel(),h.absolutePos(new BlockPos(8,4,8))),"Pipe shape instances are reused");
            h.assertTrue(!Shapes.joinIsNotEmpty(shape,expected,BooleanOp.NOT_SAME),"Cached connection shape remains exactly equal to original geometry");
        }h.succeed();
    }
    private static final class CountingDisplay extends MachineBlockEntity {
        int changes;
        CountingDisplay(BlockPos pos,BlockState state){super(pos,state);}
        @Override public void setChanged(){super.setChanged();changes++;}
    }
    @GameTest(template="large",batch="performance_r4") public static void idleDisplaysStopDirtyingChunksButFollowEnergyChanges(GameTestHelper h){
        var local=new BlockPos(8,4,8);h.setBlock(local,Content.block("calculator_screen_block"));
        var display=new CountingDisplay(h.absolutePos(local),Content.block("calculator_screen_block").defaultBlockState());display.setLevel(h.getLevel());
        MachinePrograms.tick(display);h.assertTrue(display.program.contains("ScreenEnergy")&&display.program.contains("ScreenCapacity"),"Initial zero snapshot is still persisted");
        display.changes=0;MachinePrograms.tick(display);h.assertTrue(display.changes==0,"Unchanged display does not dirty its chunk");
        var face=display.getBlockState().getValue(MachineBlock.FACING);var neighbor=local.relative(face.getOpposite());h.setBlock(neighbor,Content.block("power_cube"));
        var bank=(MachineBlockEntity)h.getBlockEntity(neighbor);bank.energy.load(321);
        MachinePrograms.tick(display);h.assertTrue(display.program.getLong("ScreenEnergy")==321&&display.changes>0,"Changed canonical energy remains live");
        display.changes=0;MachinePrograms.tick(display);h.assertTrue(display.changes==0,"Stable powered display avoids redundant dirty writes");h.succeed();
    }
    private PerformanceR4GameTests(){}
}
