package com.foundations.calculator;

import java.io.File;
import com.foundations.calculator.client.MachineRenderer;
import com.foundations.calculator.content.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid=FoundationsCalculator.ID,value=Dist.CLIENT)
public final class ClientPowerSmoke {
    private static boolean started;private static int ticks;private static volatile boolean gtTestsPassed;
    private static final String[] HELD={"power_cube","reinforced_chest","processing_chamber","weather_station","conductor_mast","scarecrow","wrench"};
    private static void require(boolean value,String text){if(!value)throw new IllegalStateException(text);}
    private static void capture(Minecraft mc,String name){Screenshot.grab(new File("../validation"),name+".png",mc.getMainRenderTarget(),message->{});}
    @SubscribeEvent public static void tick(ClientTickEvent.Post event){
        if(!Boolean.getBoolean("foundations.powerSmoke"))return;var mc=Minecraft.getInstance();
        if(!started&&mc.screen instanceof TitleScreen){started=true;mc.createWorldOpenFlows().openWorld("FoundationsSmoke",()->{throw new IllegalStateException("Smoke world refused");});return;}
        if(mc.level==null||mc.player==null)return;ticks++;
        if(ticks==20)mc.getSingleplayerServer().execute(()->{
            var p=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();p.setGameMode(GameType.CREATIVE);p.getInventory().clearContent();p.serverLevel().setDayTime(6000);p.serverLevel().setWeatherParameters(12000,0,false,false);
            for(int x=-5;x<20;x++)for(int z=-4;z<8;z++)p.serverLevel().setBlockAndUpdate(new BlockPos(x,63,z),Blocks.STONE.defaultBlockState());
            for(int i=0;i<HELD.length;i++){p.getInventory().setItem(i,new ItemStack(Content.item(HELD[i])));if(!HELD[i].equals("wrench"))p.serverLevel().setBlockAndUpdate(new BlockPos(i*3,64,0),Content.block(HELD[i]).defaultBlockState());}
            p.teleportTo(.5,64,5.5);p.setYRot(180);p.setXRot(0);p.inventoryMenu.broadcastChanges();
        });
        if(ticks==45&&net.neoforged.fml.ModList.get().isLoaded("gtceu"))mc.getSingleplayerServer().execute(()->{
            var level=mc.getSingleplayerServer().overworld();
            var functions=java.util.List.of(
                new net.minecraft.gametest.framework.TestFunction("r5_gt_integrated","eu_packets","foundations_calculator:empty",100,0,true,h->{R5GregTechAssertions.packets(h);h.succeed();}),
                new net.minecraft.gametest.framework.TestFunction("r5_gt_integrated","eu_items","foundations_calculator:empty",100,0,true,h->{R5GregTechAssertions.items(h);h.succeed();}),
                new net.minecraft.gametest.framework.TestFunction("r5_gt_integrated","eu_cable","foundations_calculator:empty",100,0,true,R5GregTechAssertions::cable)
            );
            var tests=functions.stream().map(f->new net.minecraft.gametest.framework.GameTestInfo(f,net.minecraft.world.level.block.Rotation.NONE,level,net.minecraft.gametest.framework.RetryOptions.noRetries())).toList();
            for(var info:tests)info.addListener(new net.minecraft.gametest.framework.GameTestListener(){
                public void testStructureLoaded(net.minecraft.gametest.framework.GameTestInfo t){}
                public void testPassed(net.minecraft.gametest.framework.GameTestInfo t,net.minecraft.gametest.framework.GameTestRunner runner){if(tests.stream().allMatch(x->x.isDone()&&x.getError()==null)){gtTestsPassed=true;com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_INTEGRATED_GT_TESTS_PASSED packets, real battery, real cable loss");}}
                public void testFailed(net.minecraft.gametest.framework.GameTestInfo t,net.minecraft.gametest.framework.GameTestRunner runner){throw new IllegalStateException("Integrated GregTech test failed "+t.getTestFunction().testName(),t.getError());}
                public void testAddedForRerun(net.minecraft.gametest.framework.GameTestInfo old,net.minecraft.gametest.framework.GameTestInfo next,net.minecraft.gametest.framework.GameTestRunner runner){}
            });
            net.minecraft.gametest.framework.GameTestRunner.Builder.fromInfo(tests,level).newStructureSpawner(new net.minecraft.gametest.framework.StructureGridSpawner(new BlockPos(48,64,0),3,false)).build().start();
        });
        if(ticks==65){
            int checked=0;
            for(var entry:Content.BLOCKS_BY_ID.entrySet()){
                var item=entry.getValue().get().asItem();if(item==Items.AIR||entry.getKey().startsWith("crop_"))continue;var model=mc.getItemRenderer().getModel(new ItemStack(item),mc.level,mc.player,0);
                for(var context:new ItemDisplayContext[]{ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,ItemDisplayContext.FIRST_PERSON_LEFT_HAND,ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,ItemDisplayContext.THIRD_PERSON_LEFT_HAND}){
                    var transform=model.getTransforms().getTransform(context);require(transform.scale.x()<=.401&&transform.scale.y()<=.401&&transform.scale.z()<=.401,"Oversized or missing hand transform: "+entry.getKey()+" "+context);
                }checked++;
            }
            for(String kind:MachineBlock.MODELED){var m=new MachineBlockEntity(BlockPos.ZERO,Content.block(kind).defaultBlockState());var renderer=(MachineRenderer)mc.getBlockEntityRenderDispatcher().getRenderer(m);var b=renderer.itemBounds(m);require(Double.isFinite(b.getSize())&&b.getSize()>0,"Invalid animated item bounds "+kind);com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_ITEM_BOUNDS {} {}",kind,b);}
            com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_ITEM_TRANSFORMS_PASSED {} blocks, both hands",checked);
        }
        if(ticks>=80&&ticks<80+HELD.length*40){int index=(ticks-80)/40;if((ticks-80)%40==0){mc.player.getInventory().selected=index;mc.player.setYRot(180);mc.player.setXRot(0);}if((ticks-80)%40==25)capture(mc,"held-"+HELD[index]);}
        if(ticks==380){mc.options.mainHand().set(net.minecraft.world.entity.HumanoidArm.LEFT);mc.player.getInventory().selected=0;}
        if(ticks==405)capture(mc,"held-power_cube-left");
        if(ticks==420){mc.options.mainHand().set(net.minecraft.world.entity.HumanoidArm.RIGHT);mc.setScreen(new Gallery());}
        if(ticks==445)capture(mc,"block-items-gallery");
        if(ticks==460){mc.setScreen(null);mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);mc.player.getInventory().selected=4;}
        if(ticks==485)capture(mc,"third-person-conductor-mast");
        if(ticks==500){require(!net.neoforged.fml.ModList.get().isLoaded("gtceu")||gtTestsPassed,"Integrated GregTech checks must finish");com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_POWER_SMOKE_PASSED");mc.stop();}
    }
    private static final class Gallery extends Screen {
        Gallery(){super(Component.literal("R5 block item models"));}
        public void render(GuiGraphics g,int x,int y,float partial){
            g.fill(0,0,width,height,0xff18242d);g.drawString(font,title,20,15,0xffedf4f8,false);
            var names=new java.util.ArrayList<>(MachineBlock.MODELED);names.sort(String::compareTo);names.addAll(java.util.List.of("power_cube","advanced_power_cube","calculator_locator","fabrication_chamber","analysing_chamber","research_chamber"));
            for(int i=0;i<names.size();i++){int px=20+i%4*150,py=45+i/4*70;g.fill(px,py,px+138,py+60,0xff30424e);g.pose().pushPose();g.pose().translate(px+8,py+8,0);g.pose().scale(2,2,2);g.renderItem(new ItemStack(Content.item(names.get(i))),0,0);g.pose().popPose();g.drawString(font,names.get(i).replace('_',' '),px+4,py+48,0xffedf4f8,false);}
        }
        public boolean isPauseScreen(){return false;}
    }
}
