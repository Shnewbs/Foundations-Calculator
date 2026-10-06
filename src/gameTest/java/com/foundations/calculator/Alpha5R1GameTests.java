package com.foundations.calculator;

import com.foundations.calculator.content.*;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.gametest.*;

@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Alpha5R1GameTests {
    @GameTest(template="empty",batch="alpha5_r1") public static void oversizedCubesBalanceWithoutOverflow(GameTestHelper h){
        var source=R5GameTests.place(h);
        var pos=new BlockPos(4,2,3);h.setBlock(pos,Content.block("power_cube"));
        var target=(MachineBlockEntity)h.getBlockEntity(pos);
        R6GameTests.setting("energy.machineCapacityMultiplier",1000000.0,()->
            R6GameTests.setting("energy.transferMultiplier",1000000.0,()->{
                source.energy.load(30_000_000_000L);target.energy.load(10_000_000_000L);
                MachineBlockEntity.tick(h.getLevel(),source.getBlockPos(),source.getBlockState(),source);
                h.assertTrue(source.energy.stored()==29_600_000_000L,"Source transfers its 400M FE limit despite overflowing cross-products");
                h.assertTrue(target.energy.stored()==10_400_000_000L,"Receiver gets exactly the transferred energy");
                h.assertTrue(source.energy.stored()+target.energy.stored()==40_000_000_000L,"Transfer conserves all long-valued energy");
            }));
        h.succeed();
    }
}
