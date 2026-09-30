package com.foundations.calculator;

import java.io.File;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.menu.CalculatorMenu;
import com.foundations.calculator.compat.viewer.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.*;
import net.minecraft.core.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.*;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

/** Targeted JEI-only UI and actual registered-transfer-handler regression. */
@EventBusSubscriber(modid=FoundationsCalculator.ID,value=Dist.CLIENT)
public final class ClientJeiSmoke {
    private static boolean started;private static int ticks;private static final BlockPos POS=new BlockPos(0,64,0);
    private static void require(boolean value,String text){if(!value)throw new IllegalStateException(text);}
    @SubscribeEvent public static void tick(ClientTickEvent.Post e){
        if(!Boolean.getBoolean("foundations.viewerSmoke"))return;var mc=Minecraft.getInstance();
        if(!started&&mc.screen instanceof TitleScreen){started=true;mc.createWorldOpenFlows().openWorld("FoundationsSmoke",()->{throw new IllegalStateException("Smoke world refused");});return;}
        if(mc.level==null||mc.player==null)return;ticks++;
        if(ticks==20)mc.getSingleplayerServer().execute(()->{var p=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();p.setGameMode(GameType.SURVIVAL);p.getInventory().clearContent();p.getInventory().setItem(9,new ItemStack(Content.item("circuit_dirty_12")));for(int x=-3;x<=3;x++)for(int z=-3;z<=3;z++)p.serverLevel().setBlockAndUpdate(POS.offset(x,-1,z),Blocks.STONE.defaultBlockState());p.serverLevel().setBlockAndUpdate(POS,Content.block("processing_chamber").defaultBlockState());var m=(MachineBlockEntity)p.serverLevel().getBlockEntity(POS);m.energy.receiveEnergy(1000,false);p.teleportTo(.5,64,2.5);p.inventoryMenu.broadcastChanges();});
        if(ticks==80)mc.gameMode.useItemOn(mc.player,InteractionHand.MAIN_HAND,new BlockHitResult(POS.getCenter(),Direction.UP,POS,false));
        if(ticks==120){require(mc.player.containerMenu instanceof CalculatorMenu,"Normal use must open processing menu");ClientViewerSmoke.jei();}
        if(ticks==140)Screenshot.grab(new File("../validation"),"16-jei-process-recipes.png",mc.getMainRenderTarget(),message->{});
        if(ticks==160){var menu=(CalculatorMenu)mc.player.containerMenu;var runtime=JeiIntegration.runtime;var type=new mezz.jei.api.recipe.RecipeType<>(Content.id("processing_chamber"),RecipeView.class);var category=runtime.getRecipeManager().getRecipeCategory(type);var recipe=runtime.getRecipeManager().createRecipeLookup(type).get().filter(r->r.recipe().inputs().getFirst().test(new ItemStack(Content.item("circuit_dirty_12")))).findFirst().orElseThrow();var handler=runtime.getRecipeTransferManager().getRecipeTransferHandler(menu,category).orElseThrow();require(handler.transferRecipe(menu,recipe,null,mc.player,false,true)==null,"Registered JEI handler must submit a valid transfer");runtime.getRecipesGui().getParentScreen().ifPresent(mc::setScreen);}
        if(ticks==200){var menu=(CalculatorMenu)mc.player.containerMenu;require(menu.handler.getStackInSlot(0).is(Content.item("circuit_dirty_12"))&&menu.progress()>0,"JEI transfer packet must fill the real server machine and begin processing");Screenshot.grab(new File("../validation"),"25-jei-recipe-transfer.png",mc.getMainRenderTarget(),message->{});ClientConfig.THEME.set(ClientConfig.Theme.CLASSIC);}
        if(ticks==220)Screenshot.grab(new File("../validation"),"22-classic-palette.png",mc.getMainRenderTarget(),message->{});
        if(ticks==240)mc.getSingleplayerServer().execute(()->{var p=mc.getSingleplayerServer().getPlayerList().getPlayers().getFirst();var m=(MachineBlockEntity)p.serverLevel().getBlockEntity(POS);require(p.getInventory().countItem(Content.item("circuit_dirty_12"))==0&&m.inventory.getStackInSlot(0).getCount()==1&&m.energy.getEnergyStored()==1000,"JEI transfer moves ingredients once without creating output or spending the unfinished batch's power");com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_JEI_TRANSFER_PASSED");});
        if(ticks==260){ClientConfig.THEME.set(ClientConfig.Theme.FOUNDATIONS);com.mojang.logging.LogUtils.getLogger().info("FOUNDATIONS_CLIENT_JEI_SMOKE_PASSED");mc.stop();}
    }
}
