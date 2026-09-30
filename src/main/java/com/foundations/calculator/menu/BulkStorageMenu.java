package com.foundations.calculator.menu;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.BulkStorage;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.*;

public final class BulkStorageMenu extends AbstractContainerMenu {
    public final MachineBlockEntity machine;public final int size;private final ContainerData counts;
    public static BulkStorageMenu fromNetwork(int id,Inventory inv,FriendlyByteBuf buf){buf.readBoolean();return new BulkStorageMenu(id,inv,(MachineBlockEntity)inv.player.level().getBlockEntity(buf.readBlockPos()));}
    public BulkStorageMenu(int id,Inventory inv,MachineBlockEntity m){
        super(Content.BULK_MENU.get(),id);machine=m;size=m.bulk.getSlots();
        for(int i=0;i<size;i++){final int index=i;addSlot(new SlotItemHandler(new ItemStackHandler(size),i,16+(i%9)*24,40+(i/9)*30){
            public ItemStack getItem(){
                if(inv.player.level().isClientSide)return super.getItem();
                ItemStack sample=m.bulk.getStackInSlot(index);
                return sample.isEmpty()?ItemStack.EMPTY:sample.copyWithCount(1);
            }
            public boolean mayPlace(ItemStack stack){return false;}public boolean mayPickup(Player p){return false;}
        });}
        for(int row=0;row<3;row++)for(int col=0;col<9;col++)addSlot(new Slot(inv,col+row*9+9,43+col*18,153+row*18));
        for(int col=0;col<9;col++)addSlot(new Slot(inv,col,43+col*18,211));
        counts=inv.player.level().isClientSide?new SimpleContainerData(size*2):new ContainerData(){public int get(int i){return i%2==0?m.bulk.count(i/2)&65535:m.bulk.count(i/2)>>>16;}public void set(int i,int v){}public int getCount(){return size*2;}};addDataSlots(counts);m.startOpen(inv.player);
    }
    @Override public void removed(Player player){super.removed(player);machine.stopOpen(player);}
    public int count(int slot){return (counts.get(slot*2)&65535)|((counts.get(slot*2+1)&65535)<<16);}
    public boolean stillValid(Player p){return !machine.isRemoved()&&p.level()==machine.getLevel()&&p.distanceToSqr(machine.getBlockPos().getCenter())<=64;}
    public void clicked(int slot,int button,ClickType type,Player player){
        if(!stillValid(player))return;
        if(slot>=0&&slot<size){
            if(player.level().isClientSide)return;
            if(type==ClickType.QUICK_MOVE){quickMoveStack(player,slot);return;}
            if(type!=ClickType.PICKUP||(button!=0&&button!=1))return;
            ItemStack carried=getCarried();
            if(carried.isEmpty()){int n=button==0?64:(machine.bulk.getStackInSlot(slot).getCount()+1)/2;setCarried(machine.bulk.extractItem(slot,n,false));}
            else{ItemStack offered=carried.copyWithCount(button==0?carried.getCount():1);ItemStack left=machine.bulk.insertItem(slot,offered,false);carried.shrink(offered.getCount()-left.getCount());setCarried(carried);}
            broadcastChanges();return;
        }
        super.clicked(slot,button,type,player);
    }
    public ItemStack quickMoveStack(Player player,int slot){
        if(!stillValid(player)||player.level().isClientSide||slot<0||slot>=slots.size())return ItemStack.EMPTY;
        if(slot<size){ItemStack offered=machine.bulk.extractItem(slot,64,true),copy=offered.copy();if(!moveItemStackTo(offered,size,slots.size(),true))return ItemStack.EMPTY;machine.bulk.extractItem(slot,copy.getCount()-offered.getCount(),false);broadcastChanges();return copy;}
        Slot source=slots.get(slot);ItemStack stack=source.getItem(),copy=stack.copy();if(stack.isEmpty())return ItemStack.EMPTY;
        ItemStack remaining=stack.copy();for(int i=0;i<size&&!remaining.isEmpty();i++)remaining=machine.bulk.insertItem(i,remaining,false);
        if(remaining.getCount()==copy.getCount())return ItemStack.EMPTY;source.setByPlayer(remaining);source.setChanged();broadcastChanges();return copy;
    }
}
