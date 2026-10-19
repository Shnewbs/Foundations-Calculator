package com.foundations.calculator.core;

import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.level.BlockEvent;

/** Player tool writes ask permission before any world mutation, then recheck live resources. */
public final class PlayerWorldActions {
    private static boolean permits(UseOnContext context,BlockPos pos,BlockState expected,
                                   BlockState replacement,BooleanSupplier ready) {
        if(!(context.getLevel() instanceof ServerLevel level)||context.getPlayer()==null)return false;
        var player=context.getPlayer();var held=player.getItemInHand(context.getHand());var original=held.copy();
        if(!MachineWorldActions.loaded(level,pos)||replacement!=null&&!replacement.isAir()&&!MachineWorldActions.loaded(level,pos.below())
            ||level.getBlockEntity(pos)!=null||expected.getDestroySpeed(level,pos)<0
            ||!level.getBlockState(pos).equals(expected)||!ready.getAsBoolean()
            ||!level.mayInteract(player,pos)||!player.mayUseItemAt(pos,context.getClickedFace(),held))return false;
        if(!expected.isAir()&&NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level,pos,expected,player)).isCanceled())return false;
        if(replacement!=null&&!replacement.isAir()){
            var snapshot=BlockSnapshot.create(level.dimension(),level,pos);
            var event=new BlockEvent.EntityPlaceEvent(snapshot,level.getBlockState(pos.below()),player){
                @Override public BlockState getPlacedBlock(){return replacement;}
                @Override public BlockState getState(){return replacement;}
            };
            if(NeoForge.EVENT_BUS.post(event).isCanceled())return false;
        }
        return player.level()==level&&MachineWorldActions.loaded(level,pos)
            &&level.getBlockState(pos).equals(expected)&&level.getBlockEntity(pos)==null
            &&player.getItemInHand(context.getHand())==held&&ItemStack.matches(original,held)
            &&level.mayInteract(player,pos)&&player.mayUseItemAt(pos,context.getClickedFace(),held)
            &&ready.getAsBoolean();
    }
    public static boolean replace(UseOnContext context,BlockPos pos,BlockState expected,
                                  BlockState replacement,BooleanSupplier ready){
        return !expected.equals(replacement)&&permits(context,pos,expected,replacement,ready)
            &&context.getLevel().setBlockAndUpdate(pos,replacement);
    }
    public static boolean destroy(UseOnContext context,BlockPos pos,BlockState expected){
        return permits(context,pos,expected,null,()->true)&&context.getLevel().destroyBlock(pos,true,context.getPlayer());
    }
    private PlayerWorldActions(){}
}
