package com.foundations.calculator;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.gametest.*;
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Rc1GameTests {
    @GameTest(template="large",batch="rc1") public static void toolsAndModulesKeepProtectedAtomicBehavior(GameTestHelper h){
        Rc1Assertions.run(h.getLevel(),h.absolutePos(new BlockPos(8,4,8)),h::assertTrue);h.succeed();
    }
}
