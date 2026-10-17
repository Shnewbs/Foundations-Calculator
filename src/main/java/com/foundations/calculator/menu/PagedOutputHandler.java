package com.foundations.calculator.menu;

import com.foundations.calculator.core.MachineOutputs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import java.util.function.IntSupplier;

/** Server-only view. Clients use an independent six-slot display to avoid page/content packet races. */
final class PagedOutputHandler extends ItemStackHandler {
    private final MachineOutputs output;
    private final IntSupplier row;
    PagedOutputHandler(MachineOutputs output,IntSupplier row){super(6);this.output=output;this.row=row;}
    private int target(int slot){return row.getAsInt()*3+slot;}
    @Override public ItemStack getStackInSlot(int slot){return output.get(target(slot));}
    @Override public void setStackInSlot(int slot,ItemStack stack){output.set(target(slot),stack);}
    @Override public ItemStack extractItem(int slot,int amount,boolean simulate){return output.extract(target(slot),amount,simulate);}
    @Override public ItemStack insertItem(int slot,ItemStack stack,boolean simulate){return stack;}
    @Override public boolean isItemValid(int slot,ItemStack stack){return false;}
    @Override public int getSlotLimit(int slot){return 64;}
}
