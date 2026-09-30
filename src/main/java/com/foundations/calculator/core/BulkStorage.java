package com.foundations.calculator.core;

import java.util.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import com.foundations.calculator.content.Content;

/** Counts are separate from ItemStack so saves and network stacks always remain legal. */
public final class BulkStorage implements IItemHandler {
    private final ItemStack[] samples;private final int[] amounts;private final java.util.function.IntSupplier capacity;private final boolean circuits;private final Runnable dirty;
    public BulkStorage(boolean circuits,Runnable dirty){this(circuits,circuits?1024:256,dirty);}
    public BulkStorage(boolean circuits,int limit,Runnable dirty){this(circuits,()->limit,dirty);}
    public BulkStorage(boolean circuits,java.util.function.IntSupplier limit,Runnable dirty){this.circuits=circuits;this.dirty=dirty;capacity=limit;samples=new ItemStack[circuits?14:27];Arrays.fill(samples,ItemStack.EMPTY);amounts=new int[samples.length];}
    public int getSlots(){return samples.length;}
    public int count(int slot){return amounts[slot];}
    public ItemStack getStackInSlot(int slot){return samples[slot].copyWithCount(Math.min(amounts[slot],samples[slot].getMaxStackSize()));}
    public int getSlotLimit(int slot){return Math.max(1,capacity.getAsInt());}
    public static String category(ItemStack stack){String path=Content.path(stack);if(path.startsWith("circuit_dirty_"))return "dirty";if(path.startsWith("circuit_damaged_"))return "damaged";if(path.startsWith("circuit_board_")&&CircuitData.analysed(stack))return CircuitData.stable(stack)?"stable":"analysed";return "";}
    public boolean isItemValid(int slot,ItemStack stack){
        if(!circuits)return true;String category=category(stack);if(category.isEmpty()||!Content.path(stack).endsWith("_"+slot))return false;
        for(ItemStack sample:samples)if(!sample.isEmpty()&&!category(sample).equals(category))return false;return true;
    }
    public ItemStack insertItem(int slot,ItemStack stack,boolean simulate){
        if(stack.isEmpty()||!isItemValid(slot,stack)||!samples[slot].isEmpty()&&!ItemStack.isSameItemSameComponents(samples[slot],stack))return stack;
        int accepted=Math.min(stack.getCount(),getSlotLimit(slot)-amounts[slot]);if(accepted<=0)return stack;
        if(!simulate){samples[slot]=stack.copyWithCount(1);amounts[slot]+=accepted;dirty.run();}return stack.copyWithCount(stack.getCount()-accepted);
    }
    public ItemStack extractItem(int slot,int amount,boolean simulate){
        int extracted=Math.min(Math.max(0,amount),Math.min(amounts[slot],samples[slot].getMaxStackSize()));if(extracted==0)return ItemStack.EMPTY;
        ItemStack result=samples[slot].copyWithCount(extracted);if(!simulate){amounts[slot]-=extracted;if(amounts[slot]==0)samples[slot]=ItemStack.EMPTY;dirty.run();}return result;
    }
    public CompoundTag save(HolderLookup.Provider lookup){CompoundTag nbt=new CompoundTag();ListTag entries=new ListTag();for(int i=0;i<samples.length;i++)if(amounts[i]>0){CompoundTag tag=new CompoundTag();tag.putInt("Slot",i);tag.putInt("Amount",amounts[i]);tag.put("Item",samples[i].save(lookup));entries.add(tag);}nbt.put("Entries",entries);return nbt;}
    public void load(CompoundTag nbt,HolderLookup.Provider lookup){Arrays.fill(samples,ItemStack.EMPTY);Arrays.fill(amounts,0);for(Tag t:nbt.getList("Entries",Tag.TAG_COMPOUND)){CompoundTag tag=(CompoundTag)t;int slot=tag.getInt("Slot");if(slot>=0&&slot<samples.length){samples[slot]=ItemStack.parse(lookup,tag.get("Item")).orElse(ItemStack.EMPTY).copyWithCount(1);amounts[slot]=samples[slot].isEmpty()?0:Math.max(0,tag.getInt("Amount"));}}}
}
