package com.foundations.calculator;

import java.io.File;
import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.client.*;
import com.foundations.calculator.core.MachineStatus;
import com.foundations.calculator.menu.CalculatorMenu;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/** Opt-in real OpenGL client regression walkthrough. Never included in a release JAR. */
@EventBusSubscriber(modid=FoundationsCalculator.ID,value=Dist.CLIENT)
public final class ClientSmoke {
    private static boolean started;private static int ticks,smeltWait;private static BlockPos origin;
    private static final List<String> DISPLAY=List.of("conductor_mast","weather_station","transmitter","scarecrow","crank_handle","extraction_chamber","processing_chamber","precision_chamber","docking_station","magnetic_flux","atomic_multiplier","flawless_greenhouse");
    @SubscribeEvent public static void tick(ClientTickEvent.Post event){
        if(Boolean.getBoolean("foundations.powerSmoke")||!Boolean.getBoolean("foundations.clientSmoke")||Boolean.getBoolean("foundations.viewerSmoke"))return;Minecraft mc=Minecraft.getInstance();
        if(!started&&mc.screen instanceof TitleScreen){started=true;mc.createWorldOpenFlows().openWorld("FoundationsSmoke",()->{throw new IllegalStateException("Smoke world refused to load");});return;}
        if(mc.level==null||mc.player==null)return;
        ticks++;
        if(ticks==20){mc.getSingleplayerServer().execute(()->{
            ServerPlayer player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();var level=player.serverLevel();origin=new BlockPos(0,64,0);
            player.setGameMode(GameType.CREATIVE);level.setDayTime(6000);level.setWeatherParameters(12000,0,false,false);
            for(int x=-15;x<=15;x++)for(int z=-15;z<=15;z++)level.setBlockAndUpdate(origin.offset(x,-1,z),Blocks.STONE.defaultBlockState());
            for(int i=0;i<DISPLAY.size();i++){BlockPos p=origin.offset((i%4)*4-6,0,-(i/4)*5-4);level.setBlockAndUpdate(p,Content.block(DISPLAY.get(i)).defaultBlockState());}
            for(String id:List.of("basic_greenhouse","module_workstation","reinforced_chest","weather_controller")){BlockPos p=origin.offset(10,0,List.of("basic_greenhouse","module_workstation","reinforced_chest","weather_controller").indexOf(id)*2);level.setBlockAndUpdate(p,Content.block(id).defaultBlockState());}
            player.teleportTo(origin.getX()+.5,origin.getY()+2,origin.getZ()+6.5);player.setYRot(180);player.setXRot(18);
        });}
        if(ticks==120){mc.player.setYRot(180);mc.player.setXRot(18);mc.setScreen(null);}
        if(ticks==160)capture(mc,"01-world-models.png");
        if(ticks==180)open(mc,"basic_greenhouse",0);
        if(ticks==220)capture(mc,"02-greenhouse-menu.png");
        if(ticks==240)open(mc,"module_workstation",2);
        if(ticks==280)capture(mc,"03-module-workstation.png");
        if(ticks==300)open(mc,"reinforced_chest",4);
        if(ticks==340)capture(mc,"04-bulk-storage.png");
        if(ticks==360)open(mc,"weather_controller",6);
        if(ticks==400)capture(mc,"05-weather-controls.png");
        if(ticks==420){mc.player.closeContainer();GuideScreen.open();}
        if(ticks==450)capture(mc,"06-field-guide.png");
        if(ticks==480)mc.setScreen(new Gallery());
        if(ticks==520)capture(mc,"07-item-gallery.png");
        if(ticks==550){mc.setScreen(null);mc.player.closeContainer();mc.getSingleplayerServer().execute(()->{
            var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();
            player.setGameMode(GameType.SURVIVAL);player.getInventory().clearContent();
            player.getInventory().setItem(9,new ItemStack(Items.RAW_IRON));player.getInventory().setItem(10,new ItemStack(Items.COAL));
            player.getInventory().setItem(11,new ItemStack(Items.REDSTONE));player.getInventory().setItem(12,new ItemStack(Content.item("calculator")));
            player.serverLevel().setBlockAndUpdate(origin.offset(0,0,3),Content.block("reinforced_furnace").defaultBlockState());
            player.serverLevel().setBlockAndUpdate(origin.offset(2,0,3),Content.block("power_cube").defaultBlockState());
            player.teleportTo(.5,64,5.5);player.inventoryMenu.broadcastChanges();
        });}
        if(ticks==580)useBlock(mc,origin.offset(0,0,3));
        if(ticks==620){var menu=machineMenu(mc,"reinforced_furnace");require(menu.status()==MachineStatus.NEED_INPUT,"Empty furnace status must synchronize");click(mc,25,ClickType.QUICK_MOVE);}
        if(ticks==640){var menu=machineMenu(mc,"reinforced_furnace");require(menu.status()==MachineStatus.NEED_POWER&&menu.requiredEnergy()==500,"Furnace must synchronize its 500 FE requirement");capture(mc,"08-furnace-needs-power.png");}
        if(ticks==650)click(mc,26,ClickType.QUICK_MOVE);
        if(ticks==680){require(machineMenu(mc,"reinforced_furnace").status()==MachineStatus.RUNNING,"Coal must start real server processing");capture(mc,"09-furnace-processing.png");}
        if(ticks==920){var menu=machineMenu(mc,"reinforced_furnace");require(menu.handler.getStackInSlot(14).is(Items.IRON_INGOT)&&menu.energy()==0,"Normal client fuel/input clicks must produce iron exactly once");capture(mc,"10-furnace-output.png");}
        if(ticks==940){click(mc,14,ClickType.PICKUP);click(mc,25,ClickType.PICKUP);}
        if(ticks==960){mc.player.closeContainer();useBlock(mc,origin.offset(2,0,3));}
        if(ticks==1000){machineMenu(mc,"power_cube");click(mc,27,ClickType.QUICK_MOVE);click(mc,28,ClickType.QUICK_MOVE);}
        if(ticks==1040){var menu=machineMenu(mc,"power_cube");var energy=menu.handler.getStackInSlot(20).getCapability(Capabilities.EnergyStorage.ITEM);
            require(energy!=null&&energy.getEnergyStored()>0&&Math.abs(menu.energy()+energy.getEnergyStored()-1000)<=4,"Client FE and item packets must agree within one 4-FE charging tick: cube="+menu.energy()+", item="+(energy==null?-1:energy.getEnergyStored()));
            require(menu.status()==MachineStatus.CHARGING,"Charging state must synchronize");capture(mc,"11-cube-charging.png");
        }
        if(ticks==1060)click(mc,20,ClickType.QUICK_MOVE);
        if(ticks==1080){mc.player.closeContainer();mc.getSingleplayerServer().execute(()->{
            var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();
            require(player.getInventory().countItem(Items.IRON_INGOT)==1&&player.getInventory().countItem(Items.RAW_IRON)==0&&player.getInventory().countItem(Items.COAL)==0,"Server must retain the player's single completed furnace result");
            var calculator=player.getInventory().items.stream().filter(s->s.is(Content.item("calculator"))).findFirst().orElseThrow();
            var cube=(MachineBlockEntity)player.serverLevel().getBlockEntity(origin.offset(2,0,3));
            require(calculator.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()+cube.energy.getEnergyStored()==1000,"Withdrawn calculator retains its charge on the server");
            com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_MACHINE_INTERACTIONS_PASSED");
            player.serverLevel().setBlockAndUpdate(origin.offset(0,0,3),Content.block("processing_chamber").defaultBlockState());
            player.getInventory().setItem(13,new ItemStack(Content.item("circuit_dirty_12")));player.getInventory().setItem(14,new ItemStack(Items.REDSTONE));player.inventoryMenu.broadcastChanges();
        });}
        if(ticks==1100)useBlock(mc,origin.offset(0,0,3));
        if(ticks==1140){machineMenu(mc,"processing_chamber");click(mc,29,ClickType.QUICK_MOVE);click(mc,30,ClickType.QUICK_MOVE);}
        if(ticks==1180){var menu=machineMenu(mc,"processing_chamber");require(menu.status()==MachineStatus.RUNNING&&menu.energy()==1000&&menu.progress()>0,"Dirty chip must begin processing after ordinary client insertion");capture(mc,"12-dirty-chip-processing.png");}
        if(ticks==1680){var menu=machineMenu(mc,"processing_chamber");require(menu.handler.getStackInSlot(14).is(Content.item("circuit_board_12"))&&menu.handler.getStackInSlot(0).isEmpty()&&menu.energy()==0,"Dirty chip must become the corresponding board for exactly 1000 FE");capture(mc,"13-circuit-board-output.png");}
        if(ticks==1700)click(mc,14,ClickType.QUICK_MOVE);
        if(ticks==1720){mc.player.closeContainer();mc.getSingleplayerServer().execute(()->{
            var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();require(player.getInventory().countItem(Content.item("circuit_board_12"))==1&&player.getInventory().countItem(Content.item("circuit_dirty_12"))==0,"Completed circuit board must be withdrawable and retained on the server");
            com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_CIRCUIT_PROCESSING_PASSED");player.getInventory().clearContent();var module=new ItemStack(Content.item("smelting_module"));((CalculatorItem)module.getItem()).storage(module).receiveEnergy(1000,false);player.setItemInHand(InteractionHand.MAIN_HAND,module);player.getInventory().setItem(9,new ItemStack(Items.RAW_GOLD));player.inventoryMenu.broadcastChanges();
        });}
        if(ticks==1780)mc.gameMode.useItem(mc.player,InteractionHand.MAIN_HAND);
        if(ticks==1820){require(mc.player.containerMenu instanceof com.foundations.calculator.menu.SmeltingModuleMenu,"Smelting module must open its portable menu");click(mc,3,ClickType.QUICK_MOVE);}
        if(ticks==1860){var menu=(com.foundations.calculator.menu.SmeltingModuleMenu)mc.player.containerMenu;require(menu.progress()>0&&menu.energy()==1000,"Portable smelting progress and energy must synchronize");capture(mc,"14-portable-smelting.png");}
        if(ticks==1900&&net.neoforged.fml.ModList.get().isLoaded("emi"))ClientViewerSmoke.emi();
        if(ticks==1940&&net.neoforged.fml.ModList.get().isLoaded("emi"))capture(mc,"15-emi-process-recipes.png");
        if(ticks==1980&&net.neoforged.fml.ModList.get().isLoaded("jei")&&!net.neoforged.fml.ModList.get().isLoaded("emi"))ClientViewerSmoke.jei();
        if(ticks==2020&&net.neoforged.fml.ModList.get().isLoaded("jei")&&!net.neoforged.fml.ModList.get().isLoaded("emi"))capture(mc,"16-jei-process-recipes.png");
        if(ticks==2040){mc.player.closeContainer();mc.getSingleplayerServer().execute(()->{var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();com.foundations.calculator.core.ResearchData.get(player.server).unlock(player.getUUID(),"iron");com.foundations.calculator.core.ResearchData.sync(player);});}
        if(ticks==2080){require(com.foundations.calculator.core.ResearchData.clientGroups.contains("iron"),"Research unlock packet must reach the client");mc.setScreen(new ResearchScreen(null));}
        if(ticks==2120)capture(mc,"17-research-browser.png");
        if(ticks==2125)mc.screen.mouseClicked(mc.screen.width-185,15,0);
        if(ticks==2140)capture(mc,"23-mastery-browser.png");
        if(ticks==2160)mc.setScreen(new net.neoforged.neoforge.client.gui.ConfigurationScreen(net.neoforged.fml.ModList.get().getModContainerById(FoundationsCalculator.ID).orElseThrow(),null));
        if(ticks==2200)capture(mc,"18-config-screen.png");
        if(ticks==2240){mc.setScreen(null);open(mc,"reinforced_chest",4);}
        if(ticks==2280){var chest=(MachineBlockEntity)mc.level.getBlockEntity(origin.offset(10,0,4));require(chest.lid>.9f,"Reinforced chest lid must open while its menu is in use");capture(mc,"19-animated-chest-menu.png");}
        if(ticks==2320){mc.player.closeContainer();mc.gameMode.useItem(mc.player,InteractionHand.MAIN_HAND);}
        if(ticks==2880){require(mc.player.containerMenu instanceof com.foundations.calculator.menu.SmeltingModuleMenu,"Portable menu must reopen");var menu=(com.foundations.calculator.menu.SmeltingModuleMenu)mc.player.containerMenu;if(!(menu.slots.get(2).getItem().is(Items.GOLD_INGOT)&&menu.energy()==0)&&smeltWait++<400){ticks--;return;}require(menu.slots.get(2).getItem().is(Items.GOLD_INGOT)&&menu.energy()==0,"Portable smelting must finish after accounting for paused configuration screens: progress="+menu.progress()+", FE="+menu.energy()+", output="+menu.slots.get(2).getItem());capture(mc,"20-portable-smelting-output.png");click(mc,2,ClickType.QUICK_MOVE);}
        if(ticks==2920){mc.player.closeContainer();mc.getSingleplayerServer().execute(()->{var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();require(player.getInventory().countItem(Items.GOLD_INGOT)==1&&player.getInventory().countItem(Items.RAW_GOLD)==0,"Portable output must reach the server inventory exactly once");com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_R4_FEATURES_PASSED");});com.foundations.calculator.core.ClientConfig.TOOL_MODELS.set(false);com.foundations.calculator.core.ClientConfig.THEME.set(com.foundations.calculator.core.ClientConfig.Theme.CLASSIC);mc.reloadResourcePacks().thenRun(()->mc.execute(()->mc.setScreen(new Gallery())));}
        if(ticks==3040)capture(mc,"21-flat-tool-models.png");
        if(ticks==3080){mc.setScreen(null);mc.gameMode.useItem(mc.player,InteractionHand.MAIN_HAND);}
        if(ticks==3120)capture(mc,"22-classic-palette.png");
        if(ticks==3140){mc.player.closeContainer();mc.getSingleplayerServer().execute(()->{var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();player.teleportTo(.5,64,5.5);});}
        if(ticks==3160)mc.player.lookAt(net.minecraft.commands.arguments.EntityAnchorArgument.Anchor.EYES,origin.offset(0,0,3).getCenter());
        if(ticks==3200&&net.neoforged.fml.ModList.get().isLoaded("jade"))capture(mc,"24-jade-machine-hud.png");
        if(ticks==3240){com.foundations.calculator.core.ClientConfig.TOOL_MODELS.set(true);com.foundations.calculator.core.ClientConfig.THEME.set(com.foundations.calculator.core.ClientConfig.Theme.FOUNDATIONS);com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_SMOKE_PASSED");mc.stop();}
    }
    private static void require(boolean condition,String detail){if(!condition)throw new IllegalStateException(detail);}
    private static CalculatorMenu machineMenu(Minecraft mc,String kind){require(mc.player.containerMenu instanceof CalculatorMenu,"Right-click must open a machine menu");var menu=(CalculatorMenu)mc.player.containerMenu;require(menu.kind.equals(kind),"Opened wrong machine: "+menu.kind);return menu;}
    private static void useBlock(Minecraft mc,BlockPos p){mc.gameMode.useItemOn(mc.player,InteractionHand.MAIN_HAND,new BlockHitResult(p.getCenter(),Direction.UP,p,false));}
    private static void click(Minecraft mc,int slot,ClickType type){mc.gameMode.handleInventoryMouseClick(mc.player.containerMenu.containerId,slot,0,type,mc.player);}
    private static void open(Minecraft mc,String id,int z){mc.getSingleplayerServer().execute(()->{var player=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();var m=(MachineBlockEntity)player.serverLevel().getBlockEntity(origin.offset(10,0,z));
        if(id.equals("module_workstation")){m.inventory.setStackInSlot(20,new ItemStack(Content.item("flawless_calculator")));m.inventory.setStackInSlot(0,new ItemStack(Content.item("storage_module")));}
        if(id.equals("reinforced_chest"))for(int i=0;i<4;i++)m.bulk.insertItem(0,new ItemStack(Items.DIAMOND,64),false);
        player.teleportTo(m.getBlockPos().getX()-1,m.getBlockPos().getY(),m.getBlockPos().getZ());player.openMenu(m,buf->{buf.writeBoolean(true);buf.writeBlockPos(m.getBlockPos());});
    });}
    private static void capture(Minecraft mc,String file){Screenshot.grab(new File("../validation"),file,mc.getMainRenderTarget(),message->System.out.println("SMOKE_SCREENSHOT "+file));}
    private static final class Gallery extends Screen {
        Gallery(){super(Component.literal("Foundations Calculator · registry gallery"));}
        public void render(GuiGraphicsExtractor g,int mx,int my,float partial){super.render(g,mx,my,partial);g.fill(0,0,width,height,0xff18242d);g.drawString(font,title,12,8,0xffa5e6ec,false);int i=0,cols=Math.max(1,(width-24)/24);for(var item:Content.ITEMS_BY_ID.values()){int x=12+(i%cols)*24,y=30+(i/cols)*24;g.renderItem(new ItemStack(item.get()),x,y);i++;}}
        public boolean isPauseScreen(){return false;}
    }
}
