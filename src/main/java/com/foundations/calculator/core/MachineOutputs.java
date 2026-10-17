package com.foundations.calculator.core;

import com.foundations.calculator.content.MachineBlockEntity;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.*;
import net.minecraft.world.item.ItemStack;

/** The six legacy slots retain their indices; additional slots have an independent save key. */
public final class MachineOutputs {
    public static final int MAX=54, EXTRA=MAX-6;
    private final MachineBlockEntity machine;
    private final ItemStack[] extra=new ItemStack[EXTRA];
    public static boolean supports(String kind){return kind.equals("extraction_chamber")||kind.equals("precision_chamber");}
    public MachineOutputs(MachineBlockEntity machine){this.machine=machine;java.util.Arrays.fill(extra,ItemStack.EMPTY);}
    private static void check(int slot){if(slot<0||slot>=MAX)throw new IndexOutOfBoundsException(slot);}
    public int configured(){return Math.clamp(CalculatorConfig.integer("machine."+machine.kind()+".outputSlots",6),6,MAX);}
    /** Lowered capacity never hides or destroys previously saved stacks. */
    public int visible(){int count=configured();for(int i=extra.length-1;i>=0;i--)if(!extra[i].isEmpty())return Math.max(count,i+7);return count;}
    public ItemStack get(int slot){check(slot);return slot<6?machine.inventory.getStackInSlot(14+slot):extra[slot-6];}
    public void set(int slot,ItemStack stack){check(slot);if(slot<6)machine.inventory.setStackInSlot(14+slot,stack);else{extra[slot-6]=stack;machine.setChanged();}}
    public ItemStack insert(int slot,ItemStack stack,boolean simulate){
        check(slot);if(slot>=configured()||stack.isEmpty())return stack;
        if(slot<6)return machine.inventory.insertItem(14+slot,stack,simulate);
        var into=get(slot);
        if(!into.isEmpty()&&!ItemStack.isSameItemSameComponents(into,stack))return stack;
        int accepted=Math.min(stack.getCount(),Math.min(64,stack.getMaxStackSize())-into.getCount());
        if(accepted<=0)return stack;
        if(!simulate)set(slot,stack.copyWithCount(into.getCount()+accepted));
        return stack.copyWithCount(stack.getCount()-accepted);
    }
    public ItemStack extract(int slot,int amount,boolean simulate){
        check(slot);if(slot<6)return machine.inventory.extractItem(14+slot,amount,simulate);
        var stack=get(slot);int taken=Math.min(Math.max(0,amount),Math.min(stack.getCount(),stack.getMaxStackSize()));
        if(taken==0)return ItemStack.EMPTY;
        var result=stack.copyWithCount(taken);
        if(!simulate)set(slot,stack.copyWithCount(stack.getCount()-taken));
        return result;
    }
    public void flush(java.util.List<ItemStack> pending){
        for(var it=pending.listIterator();it.hasNext();){
            var stack=it.next();for(int slot=0;slot<configured()&&!stack.isEmpty();slot++)stack=insert(slot,stack,false);
            if(stack.isEmpty())it.remove();else it.set(stack);
        }
    }
    public ItemStack[] snapshot(){return java.util.Arrays.stream(extra).map(ItemStack::copy).toArray(ItemStack[]::new);}
    public void restore(ItemStack[] items){if(items.length!=EXTRA)throw new IllegalArgumentException("Output snapshot size");for(int i=0;i<EXTRA;i++)extra[i]=items[i].copy();machine.setChanged();}
    public CompoundTag save(HolderLookup.Provider lookup){
        var result=new CompoundTag();var entries=new ListTag();
        for(int i=0;i<EXTRA;i++)if(!extra[i].isEmpty()){var row=new CompoundTag();row.putInt("Slot",i);row.put("Item",PlatformItems.save(extra[i],lookup));entries.add(row);}
        result.put("Entries",entries);return result;
    }
    public void load(CompoundTag tag,HolderLookup.Provider lookup){
        java.util.Arrays.fill(extra,ItemStack.EMPTY);
        for(Tag entry:tag.getListOrEmpty("Entries")){var row=(CompoundTag)entry;int slot=row.getIntOr("Slot",-1);if(slot>=0&&slot<EXTRA)extra[slot]=PlatformItems.load(row.get("Item"),lookup).orElse(ItemStack.EMPTY);}
    }
    public void serialize(net.minecraft.world.level.storage.ValueOutput output){
        var entries=output.childrenList("Entries");
        for(int i=0;i<EXTRA;i++)if(!extra[i].isEmpty()){var row=entries.addChild();row.putInt("Slot",i);row.store("Item",ItemStack.CODEC,extra[i]);}
    }
    public void deserialize(net.minecraft.world.level.storage.ValueInput input){
        java.util.Arrays.fill(extra,ItemStack.EMPTY);
        for(var row:input.childrenListOrEmpty("Entries")){int slot=row.getIntOr("Slot",-1);if(slot>=0&&slot<EXTRA)extra[slot]=row.read("Item",ItemStack.CODEC).orElse(ItemStack.EMPTY);}
    }
}
