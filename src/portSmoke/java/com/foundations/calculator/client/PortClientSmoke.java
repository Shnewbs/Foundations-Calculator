package com.foundations.calculator.client;

import java.nio.file.*;
import java.util.concurrent.atomic.AtomicInteger;
import com.foundations.calculator.FoundationsCalculator;
import com.foundations.calculator.content.*;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/** Opt-in native client regressions, excluded from release JARs. */
@EventBusSubscriber(modid=FoundationsCalculator.ID,value=Dist.CLIENT)
public final class PortClientSmoke {
    private static boolean started;private static int ticks;private static final AtomicInteger captures=new AtomicInteger();
    private static final BlockPos ORIGIN=new BlockPos(0,64,0);
    private static void require(boolean condition,String message){if(!condition)throw new IllegalStateException(message);}
    private static void capture(Minecraft mc,String file){Screenshot.takeScreenshot(mc.gameRenderer.mainRenderTarget(),image->{
        try{Path path=Path.of("../validation/port-client/screenshots",file);Files.createDirectories(path.getParent());image.writeToFile(path);captures.incrementAndGet();}
        catch(Exception e){throw new IllegalStateException(e);}finally{image.close();}
    });}
    private static void overlays(Minecraft mc){
        var machine=(MachineBlockEntity)mc.level.getBlockEntity(ORIGIN.offset(-3,0,-3));
        require(machine!=null,"World chamber must exist before render checks");
        var renderer=(MachineRenderer)(Object)mc.getBlockEntityRenderDispatcher().getRenderer(machine);
        var calls=new AtomicInteger();
        var collector=(net.minecraft.client.renderer.SubmitNodeCollector)java.lang.reflect.Proxy.newProxyInstance(PortClientSmoke.class.getClassLoader(),new Class<?>[]{net.minecraft.client.renderer.SubmitNodeCollector.class},(proxy,method,args)->{
            if(method.getName().equals("order"))return proxy;
            if(method.getName().equals("submitModelPart")||method.getName().equals("submitModel")){
                int overlay=(Integer)args[method.getName().equals("submitModelPart")?4:5];
                require(overlay==net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,"World model must use NO_OVERLAY rather than the red hurt texture");calls.incrementAndGet();
            }return null;
        });
        var state=new MachineRenderer.State();state.kind="extraction_chamber";state.blockPos=machine.getBlockPos();state.lightCoords=15728880;
        renderer.submit(state,new com.mojang.blaze3d.vertex.PoseStack(),collector,new net.minecraft.client.renderer.state.level.CameraRenderState());
        require(calls.get()>=2,"Both chamber frame and mechanism must be checked");
        System.out.println("FOUNDATIONS_PORT_RENDER_OVERLAYS_PASSED");
    }
    @SubscribeEvent public static void tick(ClientTickEvent.Post event){
        if(!Boolean.getBoolean("foundations.portClientSmoke"))return;var mc=Minecraft.getInstance();var screen=mc.gui.screen();
        if(!started&&screen!=null&&screen.getClass().getSimpleName().equals("AccessibilityOnboardingScreen")){mc.gui.setScreen(new TitleScreen());return;}
        if(!started&&screen instanceof TitleScreen){started=true;mc.createWorldOpenFlows().openWorld("FoundationsPortClient",()->{throw new IllegalStateException("Native smoke world refused to open");});return;}
        if(mc.level==null||mc.player==null)return;ticks++;
        if(ticks==20)mc.getSingleplayerServer().execute(()->{
            var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();var level=player.level();player.setGameMode(GameType.CREATIVE);level.setDayTime(6000);
            for(int x=-10;x<=10;x++)for(int z=-10;z<=10;z++)level.setBlockAndUpdate(ORIGIN.offset(x,-1,z),Blocks.STONE.defaultBlockState());
            int i=0;for(String kind:java.util.List.of("extraction_chamber","precision_chamber","research_chamber","reinforced_chest"))level.setBlockAndUpdate(ORIGIN.offset(-3+2*i++,0,-3),Content.block(kind).defaultBlockState());
            var m=(MachineBlockEntity)level.getBlockEntity(ORIGIN.offset(-3,0,-3));m.outputBuffer.set(6,new ItemStack(Items.DIAMOND,7));
            player.teleportTo(.5,66,4.5);player.setYRot(180);player.setXRot(15);
        });
        if(ticks==100){
            mc.player.setYRot(180);mc.player.setXRot(15);mc.gui.setScreen(null);
            for(var entry:Content.ITEMS_BY_ID.entrySet()){
                String name=new ItemStack(entry.getValue().get()).getHoverName().getString();
                require(!name.startsWith("item.foundations_calculator.")&&!name.startsWith("block.foundations_calculator."),"Untranslated registered item "+entry.getKey()+": "+name);
            }
            require(new ItemStack(Content.item("research_chamber")).getHoverName().getString().equals("Research Chamber"),"Research Chamber must use its translated block name");
            System.out.println("FOUNDATIONS_PORT_ITEM_NAMES_PASSED items="+Content.ITEMS_BY_ID.size());overlays(mc);
        }
        if(ticks==120)capture(mc,"01-native-chamber-colors.png");
        if(ticks==140)mc.getSingleplayerServer().execute(()->{
            var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();var m=(MachineBlockEntity)player.level().getBlockEntity(ORIGIN.offset(-3,0,-3));player.teleportTo(-2.5,64,-1.5);
            player.openMenu(m,buf->{buf.writeBoolean(true);buf.writeBlockPos(m.getBlockPos());});
        });
        if(ticks==180){require(mc.player.containerMenu instanceof CalculatorMenu,"Native machine menu opens");var menu=(CalculatorMenu)mc.player.containerMenu;mc.gameMode.handleInventoryButtonClick(menu.containerId,1001);}
        if(ticks==210){var menu=(CalculatorMenu)mc.player.containerMenu;require(menu.outputRow()==1&&menu.slots.get(17).getItem().is(Items.DIAMOND),"Native overflow page synchronizes its actual item");capture(mc,"02-native-output-page.png");System.out.println("FOUNDATIONS_PORT_OUTPUT_MENU_PASSED");}
        if(ticks>=280&&captures.get()==2){System.out.println("FOUNDATIONS_PORT_CLIENT_PASSED");mc.stop();}
    }
    private PortClientSmoke(){}
}
