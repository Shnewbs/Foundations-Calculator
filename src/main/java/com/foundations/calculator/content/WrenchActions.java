package com.foundations.calculator.content;

import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.BlockEvent;

public final class WrenchActions {
    public static InteractionResult use(UseOnContext c){
        var player=c.getPlayer();var level=c.getLevel();var pos=c.getClickedPos();var state=level.getBlockState(pos);
        if(player==null||!level.mayInteract(player,pos)||!player.mayUseItemAt(pos,c.getClickedFace(),c.getItemInHand()))return InteractionResult.FAIL;
        if(!(level.getBlockEntity(pos) instanceof MachineBlockEntity m))return InteractionResult.PASS;
        if(player.isShiftKeyDown()&&CalculatorConfig.flag("tools.wrenchDismantle",true)){
            if(level.isClientSide)return InteractionResult.SUCCESS;
            if(NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level,pos,state,player)).isCanceled())return InteractionResult.FAIL;
            if(CalculatorConfig.flag("tools.wrenchPreserveMachine",true)){
                if(m.kind().equals("module_workstation"))ModuleWorkstation.flush(m);
                ItemStack packed=new ItemStack(state.getBlock());
                packed.set(DataComponents.BLOCK_ENTITY_DATA,CustomData.of(m.saveWithFullMetadata(level.registryAccess())));
                m.suppressDrops=true;
                if(level.removeBlock(pos,false))player.getInventory().placeItemBackInInventory(packed);else m.suppressDrops=false;
            }else level.destroyBlock(pos,true,player);
            return InteractionResult.CONSUME;
        }
        if(CalculatorConfig.flag("tools.wrenchSideCycling",true)){
            if(!level.isClientSide){int side=c.getClickedFace().get3DDataValue();m.action(player,20+side);player.displayClientMessage(Component.literal(c.getClickedFace().getName()+": "+new String[]{"Automatic","Input","Output","Disabled"}[m.program.getInt("Side"+side)]),true);}
        }else if(!level.isClientSide)level.setBlockAndUpdate(pos,state.cycle(MachineBlock.FACING));
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
    private WrenchActions(){}
}
