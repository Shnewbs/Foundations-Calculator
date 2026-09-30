package com.foundations.calculator.api;

import com.foundations.calculator.content.MachineBlockEntity;
import com.foundations.calculator.core.MachineWorldActions;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;
import javax.annotation.Nullable;
import java.util.UUID;

/** Server-side preflight. Cancelling prevents Calculator's commit and its material/energy/drop effects.
 * Fired on NeoForge.EVENT_BUS. Permission results are never positively cached. */
public final class MachineWorldActionEvent extends Event implements ICancellableEvent {
    private final MachineBlockEntity machine;
    private final BlockPos target;
    private final MachineWorldActions.Action action;
    private final BlockState before;
    @Nullable private final BlockState replacement;
    public MachineWorldActionEvent(MachineBlockEntity machine,BlockPos target,MachineWorldActions.Action action,
                                   BlockState before,@Nullable BlockState replacement) {
        this.machine=machine;this.target=target.immutable();this.action=action;
        this.before=before;this.replacement=replacement;
    }
    public MachineBlockEntity getMachine() { return machine; }
    public BlockPos getTarget() { return target; }
    @Nullable public UUID getOwner() { return machine.owner(); }
    public MachineWorldActions.Action getAction() { return action; }
    public BlockState getBefore() { return before; }
    /** Null means a callback whose final state is not predicted (growth / opted-in legacy plant adapter). */
    @Nullable public BlockState getReplacement() { return replacement; }
}
