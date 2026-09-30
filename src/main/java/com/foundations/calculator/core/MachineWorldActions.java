package com.foundations.calculator.core;

import com.foundations.calculator.api.MachineWorldActionEvent;
import com.foundations.calculator.content.MachineBlockEntity;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.BlockSnapshot;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent;

/** Owner-attributed, loaded-only world writes. No temporary placement or rollback on an ordinary denial. */
public final class MachineWorldActions {
    public enum Action { BUILD, DEMOLISH, FARMLAND, WATER, PLANT, HARVEST, GROW, LEAF_HARVEST }
    private static final UUID OWNERLESS = UUID.fromString("7ef9d63d-9c75-468a-b0ed-a8b3b6134c33");
    public static boolean reject(MachineBlockEntity machine,BlockPos pos,Action action,String reason) {
        String text=action.name().toLowerCase(java.util.Locale.ROOT)+": "+reason;
        if(!text.equals(machine.program.getString("WorldActionBlocked"))||
            machine.program.getLong("WorldActionPos")!=pos.asLong()) {
            machine.program.putString("WorldActionBlocked",text);machine.program.putLong("WorldActionPos",pos.asLong());
            machine.setChanged();
        }
        return false;
    }
    public static boolean loaded(ServerLevel level,BlockPos pos) {
        return !level.isOutsideBuildHeight(pos)&&level.getWorldBorder().isWithinBounds(pos)&&level.hasChunkAt(pos);
    }
    /** Check current owner and each target afresh; does not mutate the level or consume the held stack. */
    public static boolean permits(MachineBlockEntity machine,BlockPos pos,BlockState expected,
                                  @Nullable BlockState replacement,ItemStack held,Action action) {
        if(!(machine.getLevel() instanceof ServerLevel level)||machine.isRemoved())return false;
        if(!loaded(level,machine.getBlockPos())||!loaded(level,pos)||
            replacement!=null&&!replacement.isAir()&&!loaded(level,pos.below()))
            return reject(machine,pos,action,"outside loaded area or world bounds");
        if(!level.getBlockState(pos).equals(expected)||level.getBlockEntity(pos)!=null||expected.getDestroySpeed(level,pos)<0)
            return reject(machine,pos,action,"changed, protected block entity, or unbreakable target");
        UUID owner=machine.owner();
        if(owner==null&&CalculatorConfig.flag("protection.requireOwner",true))
            return reject(machine,pos,action,"no owner; an authorized player must place or use the controller");
        // NeoForge owns this cache and unloads it with the level. Never retain a FakePlayer on a block entity.
        var actor=FakePlayerFactory.get(level,new GameProfile(owner==null?OWNERLESS:owner,"[Foundations]"));
        actor.gameMode.changeGameModeForPlayer(GameType.SURVIVAL);
        double oldX=actor.getX(),oldY=actor.getY(),oldZ=actor.getZ();
        ItemStack previous=actor.getMainHandItem();
        actor.setPos(pos.getX()+.5,pos.getY()+.5,pos.getZ()+.5);
        actor.setItemInHand(InteractionHand.MAIN_HAND,held.copy());
        try {
            if(!level.mayInteract(actor,pos)||!actor.mayUseItemAt(pos,Direction.UP,held))
                return reject(machine,pos,action,"owner interaction denied");
            if(NeoForge.EVENT_BUS.post(new MachineWorldActionEvent(machine,pos,action,expected,replacement)).isCanceled())
                return reject(machine,pos,action,"machine action event cancelled");
            if(!expected.isAir()&&NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level,pos,expected,actor)).isCanceled())
                return reject(machine,pos,action,"break/change event cancelled");
            if(replacement!=null&&!replacement.isAir()) {
                var snapshot=BlockSnapshot.create(level.dimension(),level,pos);
                // Preflight: event accessors expose the proposed state; the world/snapshot remain unchanged.
                var place=new BlockEvent.EntityPlaceEvent(snapshot,level.getBlockState(pos.below()),actor) {
                    @Override public BlockState getPlacedBlock() { return replacement; }
                    @Override public BlockState getState() { return replacement; }
                };
                if(NeoForge.EVENT_BUS.post(place).isCanceled())return reject(machine,pos,action,"place event cancelled");
            }
            if(machine.isRemoved()||machine.getLevel()!=level||!java.util.Objects.equals(machine.owner(),owner)||
                !loaded(level,machine.getBlockPos())||!loaded(level,pos)||!level.getBlockState(pos).equals(expected)||level.getBlockEntity(pos)!=null)
                return reject(machine,pos,action,"target changed during permission event");
            // A success elsewhere must not hide the last blocked target; it clears on a successful retry there.
            if(machine.program.contains("WorldActionPos")&&machine.program.getLong("WorldActionPos")==pos.asLong()) {
                machine.program.remove("WorldActionBlocked");machine.program.remove("WorldActionPos");machine.setChanged();
            }
            return true;
        } finally {
            actor.setItemInHand(InteractionHand.MAIN_HAND,previous);actor.setPos(oldX,oldY,oldZ);
        }
    }
    public static boolean replace(MachineBlockEntity machine,BlockPos pos,BlockState expected,BlockState replacement,
                                  ItemStack held,Action action) {
        return replace(machine,pos,expected,replacement,held,action,()->true);
    }
    /** ready is rechecked after listeners, before the write. Caller commits cost/drops only after true. */
    public static boolean replace(MachineBlockEntity machine,BlockPos pos,BlockState expected,BlockState replacement,
                                  ItemStack held,Action action,BooleanSupplier ready) {
        if(expected.equals(replacement)||replacement.hasBlockEntity()||!ready.getAsBoolean())return false;
        if(!permits(machine,pos,expected,replacement,held,action)||!ready.getAsBoolean())return false;
        var level=(ServerLevel)machine.getLevel();
        return level.getBlockState(pos).equals(expected)&&level.setBlockAndUpdate(pos,replacement);
    }
    public static boolean legacyAllowed(MachineBlockEntity machine,BlockPos pos,Action action) {
        return CalculatorConfig.flag("protection.allowLegacyPlantCallbacks",false)||
            reject(machine,pos,action,"unbounded plant callback disabled; use a protection-aware adapter");
    }
    private MachineWorldActions() {}
}
