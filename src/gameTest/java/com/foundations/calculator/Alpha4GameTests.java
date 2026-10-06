package com.foundations.calculator;
import com.foundations.calculator.core.*;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.gametest.*;
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Alpha4GameTests {
    @GameTest(template="empty",batch="alpha4") public static void nativePortTransfersAboveIntLimit(GameTestHelper h) {
        var pos=new net.minecraft.core.BlockPos(3,2,3);
        h.setBlock(pos,com.foundations.calculator.content.Content.block("advanced_power_cube"));
        var machine=(com.foundations.calculator.content.MachineBlockEntity)h.getBlockEntity(pos);
        R6GameTests.setting("energy.machineCapacityMultiplier",100000.0,()->
            R6GameTests.setting("energy.transferMultiplier",1000000.0,()-> {
                var port=machine.longEnergyPort(null);
                long amount=3_000_000_000L;
                h.assertTrue(port.receive(amount,true)==amount&&machine.energy.stored()==0,"Simulated long input is side-effect free");
                h.assertTrue(port.receive(amount,false)==amount&&machine.energy.stored()==amount,"Native port exceeds int limit");
                h.assertTrue(MachineProfiles.transfer(machine.kind())==Integer.MAX_VALUE,"Standard FE rate remains int-bounded");
                h.assertTrue(port.extract(amount,true)==amount&&machine.energy.stored()==amount,"Simulated long output is side-effect free");
                h.assertTrue(port.extract(amount,false)==amount&&machine.energy.stored()==0,"Native output exceeds int limit");
            }));h.succeed();
    }
    @GameTest(template="empty",batch="alpha4") public static void zeroTransferMultiplierDisablesBothFacades(GameTestHelper h) {
        var machine=R5GameTests.place(h);
        R6GameTests.setting("energy.transferMultiplier",0.0,()-> {
            h.assertTrue(machine.longEnergyPort(null).receive(100,false)==0,"Zero native transfer cap");
            h.assertTrue(machine.energyPort(null).receiveEnergy(100,false)==0,"Zero standard transfer cap");
            h.assertTrue(machine.energy.stored()==0,"Disabled rates preserve storage");
        });h.succeed();
    }
    @GameTest(template="empty",batch="alpha4") public static void disabledFaceStillRejectsLongTransfer(GameTestHelper h) {
        var machine=R5GameTests.place(h);
        machine.program.putInt("Side"+net.minecraft.core.Direction.NORTH.get3DDataValue(),3);
        h.assertTrue(machine.longEnergyPort(net.minecraft.core.Direction.NORTH).receive(Long.MAX_VALUE,false)==0,"Disabled face refuses input");
        h.assertTrue(machine.energy.stored()==0,"Rejected transfer does not mutate storage");h.succeed();
    }
}
