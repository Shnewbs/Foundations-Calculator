package com.foundations.calculator;
import com.foundations.calculator.core.*;
import net.minecraft.gametest.framework.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.gametest.*;
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class Alpha5GameTests {
    @GameTest(template="empty",batch="alpha5") public static void onboardingAdvancementsLoad(GameTestHelper h) {
        for(String id:new String[]{"root","scientific","atomic","processing","research"})
            h.assertTrue(h.getLevel().getServer().getAdvancements().get(ResourceLocation.fromNamespaceAndPath(FoundationsCalculator.ID,id))!=null,"Advancement loads: "+id);
        h.succeed();
    }
    @GameTest(template="empty",batch="alpha5") public static void configuredValueTracksServerOverride(GameTestHelper h) {
        h.assertTrue(CalculatorConfig.configuredValue("unknown.setting").isEmpty(),"Unknown key is explicit");
        R6GameTests.setting("machines.energyTransferPerTick",7654,()->
            h.assertTrue(CalculatorConfig.configuredValue("machines.energyTransferPerTick").orElseThrow().equals(7654),"Read current server value"));
        h.succeed();
    }
    @GameTest(template="empty",batch="alpha5") public static void configCommandsRespectOperatorPermission(GameTestHelper h) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        var server=h.getLevel().getServer();
        var dispatcher=server.getCommands().getDispatcher();
        var source=server.createCommandSourceStack().withSuppressedOutput();
        var config=dispatcher.getRoot().getChild("foundations").getChild("config");
        h.assertTrue(!config.canUse(source.withPermission(0)),"Config inspection requires operator permission");
        h.assertTrue(config.canUse(source.withPermission(2)),"Operators may inspect config");
        h.assertTrue(dispatcher.execute("foundations config get machines.energyTransferPerTick",source)==1,"Known setting command succeeds");
        h.assertTrue(dispatcher.execute("foundations config get unknown.setting",source)==0,"Unknown setting command fails explicitly");
        h.assertTrue(dispatcher.execute("foundations config find ENERGY",source)==1,"Search accepts uppercase text");
        h.succeed();
    }

}
