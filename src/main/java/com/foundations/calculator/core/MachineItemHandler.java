package com.foundations.calculator.core;
import java.util.*;
import com.foundations.calculator.content.MachineBlockEntity;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.*;
/** Native transactions retain sided rules, bulk counts and module state on abort. */
public final class MachineItemHandler extends SnapshotJournal<MachineItemHandler.State> implements ResourceHandler<ItemResource> {
    record State(ItemStack[] inventory,CompoundTag bulk,CompoundTag program,List<ItemStack> pending) {}
    private final MachineBlockEntity machine;private final IItemHandler slots;
    public MachineItemHandler(MachineBlockEntity machine,Direction face){this.machine=machine;slots=machine.automation(face);}
    public static IItemHandler neighbor(Level level,BlockPos pos,Direction face){var handler=level.getCapability(Capabilities.Item.BLOCK,pos,face);return handler==null?null:IItemHandler.of(handler);}
    @Override protected State createSnapshot(){ItemStack[] inventory=new ItemStack[machine.inventory.getSlots()];for(int i=0;i<inventory.length;i++)inventory[i]=machine.inventory.getStackInSlot(i).copy();return new State(inventory,machine.bulk.save(machine.getLevel().registryAccess()),machine.program.copy(),machine.pending.stream().map(ItemStack::copy).toList());}
    @Override protected void revertToSnapshot(State state){machine.restoreAutomationInventory(state.inventory,state.bulk,state.program,state.pending);}
    public int size(){return slots.getSlots();}
    public ItemResource getResource(int slot){return ItemResource.of(slots.getStackInSlot(slot));}
    public long getAmountAsLong(int slot){return machine.bulkInventory()?machine.bulk.count(slot):slots.getStackInSlot(slot).getCount();}
    public long getCapacityAsLong(int slot,ItemResource resource){return machine.bulkInventory()?slots.getSlotLimit(slot):Math.min(slots.getSlotLimit(slot),resource.isEmpty()?net.minecraft.world.item.Item.ABSOLUTE_MAX_STACK_SIZE:resource.getMaxStackSize());}
    public boolean isValid(int slot,ItemResource resource){return !resource.isEmpty()&&slots.isItemValid(slot,resource.toStack());}
    public int insert(int slot,ItemResource resource,int amount,TransactionContext transaction){if(amount<0)throw new IllegalArgumentException("Negative insert amount");if(amount==0||resource.isEmpty())return 0;ItemStack offered=resource.toStack(amount);int accepted=amount-slots.insertItem(slot,offered,true).getCount();if(accepted<=0)return 0;updateSnapshots(transaction);return amount-slots.insertItem(slot,offered,false).getCount();}
    public int extract(int slot,ItemResource resource,int amount,TransactionContext transaction){if(amount<0)throw new IllegalArgumentException("Negative extract amount");if(amount==0||resource.isEmpty()||!resource.matches(slots.getStackInSlot(slot)))return 0;int extracted=slots.extractItem(slot,amount,true).getCount();if(extracted<=0)return 0;updateSnapshots(transaction);return slots.extractItem(slot,amount,false).getCount();}
}
