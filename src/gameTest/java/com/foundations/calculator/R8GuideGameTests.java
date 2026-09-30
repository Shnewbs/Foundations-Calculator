package com.foundations.calculator;
import com.foundations.calculator.guide.GuideSnapshots;
import com.foundations.calculator.network.*;
import com.foundations.calculator.core.CalculatorConfig;
import com.foundations.calculator.content.Content;
import com.foundations.guide.api.GuideApi;
import io.netty.buffer.Unpooled;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.gametest.framework.*;
import net.neoforged.neoforge.gametest.*;
import java.util.List;
@GameTestHolder(FoundationsCalculator.ID)
@PrefixGameTestTemplate(false)
public final class R8GuideGameTests {
 @GameTest(template="empty",batch="r8") public static void guideConfigExists(GameTestHelper h){h.assertTrue(CalculatorConfig.keys().contains("guide.liveValues"),"Guide server configuration registered");h.succeed();}
 @GameTest(template="empty",batch="r8") public static void guideReusesOriginalBook(GameTestHelper h){h.assertTrue(Content.ITEMS_BY_ID.containsKey("info_calculator")&&!Content.ITEMS_BY_ID.containsKey("field_guide"),"Reuse old item; no extra book");h.succeed();}
 @GameTest(template="empty",batch="r8") public static void guideRejectsUnknownOrPrivateKeys(GameTestHelper h){for(String k:List.of("uuid","inventory","config/secret","machine/../power_cube","machine/no_such_machine","module/no_such_module"))h.assertTrue(!GuideSnapshots.permitted(k),"Reject "+k);h.succeed();}
 @GameTest(template="empty",batch="r8") public static void guideKnownKeysAndAllMachines(GameTestHelper h){h.assertTrue(GuideSnapshots.permitted("power")&&GuideSnapshots.permitted("research")&&GuideSnapshots.permitted("machine/power_cube"),"Public keys");h.succeed();}
 @GameTest(template="empty",batch="r8") public static void guideReplyRoundTrips(GameTestHelper h){var b=new RegistryFriendlyByteBuf(Unpooled.buffer(),h.getLevel().registryAccess());try{var p=new GuideSnapshotPayload(72,"power",true,List.of("4 FE/EU","Input enabled"));GuideSnapshotPayload.CODEC.encode(b,p);h.assertTrue(p.equals(GuideSnapshotPayload.CODEC.decode(b)),"Snapshot codec");}finally{b.release();}h.succeed();}
 @GameTest(template="empty",batch="r8") public static void guideQueryRoundTrips(GameTestHelper h){var b=new RegistryFriendlyByteBuf(Unpooled.buffer(),h.getLevel().registryAccess());try{var p=new GuideQueryPayload(17,"machine/power_cube");GuideQueryPayload.CODEC.encode(b,p);h.assertTrue(p.equals(GuideQueryPayload.CODEC.decode(b)),"Query codec");}finally{b.release();}h.succeed();}
 @GameTest(template="empty",batch="r8") public static void guideResponseBounds(GameTestHelper h){boolean blocked=false;try{new GuideSnapshotPayload(1,"power",true,List.of("x".repeat(513)));}catch(IllegalArgumentException e){blocked=true;}h.assertTrue(blocked,"Response length bounded");h.succeed();}
 @GameTest(template="empty",batch="r8") public static void sharedApiLoadsOnDedicatedServer(GameTestHelper h){h.assertTrue(GuideApi.catalog()!=null&&GuideApi.ARTIFACT.equals("com.foundations:foundations-guide-api:1.0.0"),"Shared API loadable without client initialization");h.succeed();}
}
