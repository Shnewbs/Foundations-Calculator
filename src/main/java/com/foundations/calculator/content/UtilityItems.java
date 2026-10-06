package com.foundations.calculator.content;

import java.util.*;
import com.foundations.calculator.core.*;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;

public final class UtilityItems {
    private UtilityItems() {}
    public static void put(ItemStack stack,CompoundTag tag){stack.set(DataComponents.CUSTOM_DATA,CustomData.of(tag));}
    public static InteractionResult onBlock(String kind,ItemStack stack,ItemStack battery,UseOnContext c){
        Player player=c.getPlayer();if(!CalculatorConfig.enabled(kind))return InteractionResult.FAIL;if(player==null)return InteractionResult.PASS;
        Level level=c.getLevel();BlockPos pos=c.getClickedPos();if(!player.mayUseItemAt(pos,c.getClickedFace(),stack)||!level.mayInteract(player,pos))return InteractionResult.FAIL;
        var state=level.getBlockState(pos);
        if((kind.equals("soil")||kind.equals("small_stone"))&&player.isShiftKeyDown()&&(state.is(Blocks.DIRT)||state.is(Blocks.GRASS_BLOCK))){
            if(!level.isClientSide()&&level.setBlockAndUpdate(pos,(kind.equals("soil")?Blocks.FARMLAND:Blocks.GRAVEL).defaultBlockState())&&!player.isCreative())stack.shrink(1);return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if(kind.equals("calculator_screen")){
            if(!c.getClickedFace().getAxis().isHorizontal()||com.foundations.calculator.api.FoundationsEnergy.block(level,pos,c.getClickedFace())==null)return InteractionResult.FAIL;
            BlockPos placed=pos.relative(c.getClickedFace());if(!level.getBlockState(placed).canBeReplaced()||!player.mayUseItemAt(placed,c.getClickedFace(),stack))return InteractionResult.FAIL;
            if(!level.isClientSide()&&level.setBlockAndUpdate(placed,Content.block("calculator_screen_block").defaultBlockState().setValue(MachineBlock.FACING,c.getClickedFace()))&&!player.isCreative())stack.shrink(1);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if(kind.equals("atomic_terrain_module"))return AtomicTerrainModule.use(stack,battery,c);
        if(kind.equals("terrain_module")||kind.equals("advanced_terrain_module")){
            List<Block> choices=new ArrayList<>(List.of(Blocks.GRASS_BLOCK,Blocks.DIRT,Blocks.STONE));if(kind.startsWith("advanced"))choices.addAll(List.of(Blocks.GRAVEL,Blocks.SAND,Blocks.COBBLESTONE));
            CompoundTag tag=CircuitData.tag(stack);int mode=Math.floorMod(tag.getIntOr("TerrainMode",0),choices.size());
            if(player.isShiftKeyDown()){
                if(!level.isClientSide()){mode=(mode+1)%choices.size();tag.putInt("TerrainMode",mode);put(stack,tag);player.displayClientMessage(choices.get(mode).getName(),true);}return InteractionResult.sidedSuccess(level.isClientSide());
            }
            Block selected=choices.get(mode);if(!choices.contains(state.getBlock())||state.is(selected))return InteractionResult.PASS;
            int cost=CalculatorConfig.integer("module."+kind+".cost",1);
            if(!player.isCreative()&&(!(battery.getItem() instanceof CalculatorItem item)||item.energy(battery)<cost))return InteractionResult.FAIL;
            if(!level.isClientSide()&&level.setBlockAndUpdate(pos,selected.defaultBlockState())&&!player.isCreative())((CalculatorItem)battery.getItem()).storage(battery).extractEnergy(cost,false);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if(kind.equals("sickle")){
            if(!(state.getBlock() instanceof HarvestLeaves))return InteractionResult.PASS;
            if(!level.isClientSide())for(ItemStack drop:MachinePrograms.harvestLeaves(level,pos))player.getInventory().placeItemBackInInventory(drop);
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if(kind.equals("obsidian_key")&&(state.is(Blocks.OBSIDIAN)||state.is(Content.block("purified_obsidian")))){
            if(!level.isClientSide()){level.destroyBlock(pos,true,player);if(!player.isCreative())stack.hurtAndBreak(CalculatorConfig.integer("tools.obsidianKeyDamage",1),player,c.getHand()==InteractionHand.MAIN_HAND?EquipmentSlot.MAINHAND:EquipmentSlot.OFFHAND);}
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        if(kind.equals("warp_module")&&player.isShiftKeyDown()&&(!CalculatorConfig.flag("module.warp_module.requireStableBlock",true)||stable(level,pos))){
            if(!level.isClientSide()){var tag=CircuitData.tag(stack);tag.putLong("WarpPos",pos.asLong());tag.putString("WarpDimension",level.dimension().location().toString());put(stack,tag);player.displayClientMessage(Component.literal("Warp destination bound."),true);}
            return InteractionResult.sidedSuccess(level.isClientSide());
        }
        String nutrient=state.is(Content.block("amethyst_leaves"))?"hunger":state.is(Content.block("tanzanite_leaves"))?"health":"";
        if(!nutrient.isEmpty()&&NutritionData.capacity(stack,nutrient)>NutritionData.get(stack,nutrient)&&state.getValue(HarvestLeaves.AGE)>=CalculatorConfig.integer("plants.leafMatureAge",2)){
            if(!level.isClientSide()){NutritionData.add(stack,nutrient,CalculatorConfig.integer("nutrition.leafYield",1));level.setBlockAndUpdate(pos,state.setValue(HarvestLeaves.AGE,CalculatorConfig.integer("plants.leafHarvestResetAge",0)));}return InteractionResult.sidedSuccess(level.isClientSide());
        }return InteractionResult.PASS;
    }
    public static boolean use(String kind,ItemStack stack,ItemStack battery,Level level,Player player){
        if(kind.equals("soil")||kind.equals("small_stone")){
            var projectile=new ThrownMaterialEntity(level,player,kind.equals("soil"));projectile.shootFromRotation(player,player.getXRot(),player.getYRot(),0,1.5f,1);if(level.addFreshEntity(projectile)&&!player.isCreative())stack.shrink(1);level.playSound(null,player.blockPosition(),SoundEvents.SNOWBALL_THROW,SoundSource.PLAYERS,.5f,1);return true;
        }
        if(kind.equals("locator_module")){
            var tag=CircuitData.tag(stack);tag.putUUID("Owner",player.getUUID());tag.putString("OwnerName",player.getGameProfile().getName());put(stack,tag);player.displayClientMessage(Component.literal("Locator bound to "+player.getGameProfile().getName()),true);return true;
        }
        if(kind.equals("end_diamond")){
            if(!CalculatorConfig.flag("module.end_diamond.enabled",true)||player.getCooldowns().isOnCooldown(battery.getItem()))return true;
            var pearl=new net.minecraft.world.entity.projectile.ThrownEnderpearl(level,player);pearl.shootFromRotation(player,player.getXRot(),player.getYRot(),0,(float)CalculatorConfig.decimal("module.end_diamond.velocity",1.5),1);
            if(level.addFreshEntity(pearl)){player.getCooldowns().addCooldown(battery.getItem(),CalculatorConfig.integer("module.end_diamond.cooldown",10));level.playSound(null,player.blockPosition(),SoundEvents.ENDER_PEARL_THROW,SoundSource.PLAYERS,.5f,1);}
            return true;
        }
        if(kind.equals("ender_pearl")&&stack!=battery){
            int cost=CalculatorConfig.integer("module.ender_pearl.cost",1000);if(player.getCooldowns().isOnCooldown(battery.getItem()))return true;
            if(!(battery.getItem() instanceof CalculatorItem item)||!player.isCreative()&&item.energy(battery)<cost)return true;
            var pearl=new net.minecraft.world.entity.projectile.ThrownEnderpearl(level,player);pearl.shootFromRotation(player,player.getXRot(),player.getYRot(),0,(float)CalculatorConfig.decimal("module.ender_pearl.velocity",1.5),1);if(level.addFreshEntity(pearl)&&!player.isCreative()){item.storage(battery).extractEnergy(cost,false);player.getCooldowns().addCooldown(battery.getItem(),CalculatorConfig.integer("module.ender_pearl.cooldown",10));}return true;
        }
        if(kind.equals("grenade")||kind.equals("baby_grenade")){
            if(!CalculatorConfig.GRENADES.get())return true;
            int cost=CalculatorConfig.integer("module.grenade.cost",10000);if(player.getCooldowns().isOnCooldown(battery.getItem()))return true;
            boolean installed=stack!=battery;if(installed&&!player.isCreative()&&(!(battery.getItem() instanceof CalculatorItem item)||item.energy(battery)<cost))return true;
            GrenadeEntity grenade=new GrenadeEntity(level,player,kind.equals("baby_grenade"));grenade.shootFromRotation(player,player.getXRot(),player.getYRot(),0,(float)CalculatorConfig.decimal("module.grenade.velocity",1.5),1);
            if(level.addFreshEntity(grenade)){player.getCooldowns().addCooldown(battery.getItem(),CalculatorConfig.integer("module.grenade.cooldown",10));if(!player.isCreative()){if(installed)((CalculatorItem)battery.getItem()).storage(battery).extractEnergy(cost,false);else stack.shrink(1);}level.playSound(null,player.blockPosition(),SoundEvents.TNT_PRIMED,SoundSource.PLAYERS,1,1);}return true;
        }
        if(NutritionData.capacity(stack,"health")>0||NutritionData.capacity(stack,"hunger")>0){
            NutritionData.restore(player,stack,CalculatorConfig.integer("nutrition.manualRestoreLimit",20));player.displayClientMessage(Component.literal("Health: "+NutritionData.get(stack,"health")+" · Hunger: "+NutritionData.get(stack,"hunger")),true);return true;
        }
        if(kind.equals("energy_module")){player.displayClientMessage(Component.literal(((CalculatorItem)stack.getItem()).energy(stack)+" FE stored"),true);return true;}
        if(kind.equals("warp_module")){
            if(player.getCooldowns().isOnCooldown(battery.getItem()))return true;
            var target=CircuitData.tag(stack);
            if(!target.contains("WarpPos")){player.displayClientMessage(Component.literal("Sneak-use a destination block to bind the module."),true);return true;}
            var dimension=net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,net.minecraft.resources.Identifier.parse(target.getStringOr("WarpDimension","")));
            var destination=level.getServer().getLevel(dimension);
            if(destination==null||!dimension.equals(level.dimension())&&!CalculatorConfig.flag("module.warp_module.crossDimension",false))return true;
            BlockPos p=BlockPos.of(target.getLongOr("WarpPos",0L));
            if(!destination.getWorldBorder().isWithinBounds(p)||destination.isOutsideBuildHeight(p.above(2)))return true;
            if(!destination.hasChunkAt(p)){if(!CalculatorConfig.flag("module.warp_module.loadDestination",false))return true;destination.getChunkAt(p);}
            if(CalculatorConfig.flag("module.warp_module.requireStableBlock",true)&&!stable(destination,p)||!destination.isEmptyBlock(p.above())||!destination.isEmptyBlock(p.above(2)))return true;
            int cost=CalculatorConfig.integer("module.warp_module.cost",1000);
            if(!(battery.getItem() instanceof CalculatorItem item)||item.storage(battery).extractEnergy(cost,true)!=cost)return true;
            if(player instanceof net.minecraft.server.level.ServerPlayer serverPlayer){
                player.stopRiding();serverPlayer.teleportTo(destination,p.getX()+.5,p.getY()+1,p.getZ()+.5,player.getYRot(),player.getXRot());
                item.storage(battery).extractEnergy(cost,false);player.fallDistance=0;player.getCooldowns().addCooldown(battery.getItem(),CalculatorConfig.integer("module.warp_module.cooldown",0));
            }return true;
        }
        if(kind.equals("jump_module")){
            int cost=CalculatorConfig.integer("module.jump_module.cost",100);if(player.getCooldowns().isOnCooldown(battery.getItem()))return true;
            if(battery.getItem() instanceof CalculatorItem item&&item.storage(battery).extractEnergy(cost,true)==cost){item.storage(battery).extractEnergy(cost,false);player.push(0,CalculatorConfig.decimal("module.jump_module.velocity",1),0);player.getCooldowns().addCooldown(battery.getItem(),CalculatorConfig.integer("module.jump_module.cooldown",0));player.hurtMarked=true;player.fallDistance=0;}return true;
        }return false;
    }
    private static boolean stable(Level l,BlockPos p){var id=net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(l.getBlockState(p).getBlock());return id.getNamespace().equals("foundations_calculator")&&id.getPath().startsWith("stable_stone");}
}
