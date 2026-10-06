package com.foundations.calculator.content;

import com.foundations.calculator.core.*;
import net.minecraft.core.registries.*;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.event.level.BlockEvent;

/** Two-step material binding; replacements respect break/place protection and never copy block entities. */
public final class AtomicTerrainModule {
    public static InteractionResult use(ItemStack module,ItemStack battery,UseOnContext c){
        var player=c.getPlayer();var level=c.getLevel();var pos=c.getClickedPos();var state=level.getBlockState(pos);
        if(player==null||!level.mayInteract(player,pos)||!player.mayUseItemAt(pos,c.getClickedFace(),battery))return InteractionResult.FAIL;
        if(level.getBlockEntity(pos)!=null||state.getDestroySpeed(level,pos)<0||state.is(TagKey.create(Registries.BLOCK,Content.id("atomic_terrain_blacklist"))))return InteractionResult.FAIL;
        if(level.isClientSide())return InteractionResult.SUCCESS;
        var tag=CircuitData.tag(module);
        if(player.isShiftKeyDown()){
            if(!tag.contains("TerrainSource")||tag.contains("TerrainTarget")){tag.putString("TerrainSource",BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString());tag.remove("TerrainTarget");player.displayClientMessage(Component.literal("Source selected. Sneak-use the replacement material next."),true);}
            else {tag.put("TerrainTarget",NbtUtils.writeBlockState(state));player.displayClientMessage(Component.literal("Replacement selected. Use on the source material to replace it."),true);}
            UtilityItems.put(module,tag);return InteractionResult.CONSUME;
        }
        if(!tag.contains("TerrainTarget")||!tag.getStringOr("TerrainSource","").equals(BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString()))return InteractionResult.PASS;
        var replacement=NbtUtils.readBlockState(level.holderLookup(Registries.BLOCK),tag.getCompoundOrEmpty("TerrainTarget"));
        if(replacement.isAir()||replacement.hasBlockEntity()||replacement.getDestroySpeed(level,pos)<0||replacement.is(state.getBlock())||!replacement.canSurvive(level,pos)||replacement.is(TagKey.create(Registries.BLOCK,Content.id("atomic_terrain_blacklist"))))return InteractionResult.FAIL;
        int slot=-1;boolean consume=!player.isCreative()&&CalculatorConfig.flag("module.atomic_terrain_module.requireReplacementItem",true);
        if(consume){for(int i=0;i<player.getInventory().items.size();i++)if(player.getInventory().getItem(i).is(replacement.getBlock().asItem())){slot=i;break;}if(slot<0){player.displayClientMessage(Component.literal("A replacement block is required in your inventory."),true);return InteractionResult.FAIL;}}
        int cost=CalculatorConfig.integer("module.atomic_terrain_module.cost",1);
        if(!(battery.getItem() instanceof CalculatorItem power)||!player.isCreative()&&power.energy(battery)<cost)return InteractionResult.FAIL;
        if(NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level,pos,state,player)).isCanceled())return InteractionResult.FAIL;
        var snapshot=BlockSnapshot.create(level.dimension(),level,pos);
        if(!level.setBlock(pos,replacement,2))return InteractionResult.FAIL;
        if(NeoForge.EVENT_BUS.post(new BlockEvent.EntityPlaceEvent(snapshot,level.getBlockState(pos.below()),player)).isCanceled()){
            snapshot.restore(3);return InteractionResult.FAIL;
        }
        if(consume)player.getInventory().removeItem(slot,1);
        if(!player.isCreative())power.storage(battery).extractEnergy(cost,false);
        if(CalculatorConfig.flag("module.atomic_terrain_module.dropOriginal",true)&&!player.isCreative())for(ItemStack drop:Block.getDrops(state,(ServerLevel)level,pos,null,player,player.getMainHandItem()))player.getInventory().placeItemBackInInventory(drop);
        level.updateNeighborsAt(pos,replacement.getBlock());level.sendBlockUpdated(pos,state,replacement,3);return InteractionResult.CONSUME;
    }
    private AtomicTerrainModule(){}
}
