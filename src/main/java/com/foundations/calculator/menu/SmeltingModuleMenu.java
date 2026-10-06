package com.foundations.calculator.menu;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.CircuitData;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class SmeltingModuleMenu extends AbstractContainerMenu {
    public final ItemStack carrier,module;
    private final Inventory inventory;
    private final InteractionHand hand;
    private final int heldSlot,selectedSlot;
    private final SimpleContainer contents;
    private final ContainerData data;
    private boolean refreshing;
    public static SmeltingModuleMenu fromNetwork(int id,Inventory inv,FriendlyByteBuf buf){return new SmeltingModuleMenu(id,inv,buf.readEnum(InteractionHand.class));}
    public SmeltingModuleMenu(int id,Inventory inv,InteractionHand hand){
        super(Content.SMELTING_MENU.get(),id);this.inventory=inv;this.hand=hand;carrier=inv.player.getItemInHand(hand);heldSlot=hand==InteractionHand.MAIN_HAND?inv.selected:40;
        selectedSlot=CircuitData.tag(carrier).getIntOr("SelectedModule",0);module=Content.path(carrier).equals("smelting_module")?carrier:ModuleWorkstation.selected(carrier);
        contents=new SimpleContainer(SmeltingModule.contents(module).toArray(ItemStack[]::new));
        contents.addListener(c->{if(refreshing||inv.player.level().isClientSide())return;var list=net.minecraft.core.NonNullList.withSize(3,ItemStack.EMPTY);for(int i=0;i<3;i++)list.set(i,c.getItem(i));module.set(DataComponents.CONTAINER,ItemContainerContents.fromItems(list));save();});
        addSlot(new Slot(contents,0,30,36){public boolean mayPlace(ItemStack s){return s!=carrier&&s.getItem().canFitInsideContainerItems();}});
        addSlot(new Slot(contents,1,84,55){public boolean mayPlace(ItemStack s){return false;}});
        addSlot(new Slot(contents,2,138,36){public boolean mayPlace(ItemStack s){return false;}});
        for(int r=0;r<3;r++)for(int c=0;c<9;c++)addSlot(new Slot(inv,c+r*9+9,8+c*18,96+r*18));
        for(int c=0;c<9;c++)addSlot(new Slot(inv,c,8+c*18,154));HeldItemLock.install(this,inv,heldSlot);
        data=inv.player.level().isClientSide()?new SimpleContainerData(10):new ContainerData(){
            public int get(int i){
                long energy=carrier.getItem() instanceof CalculatorItem item?item.energyLong(carrier):0;
                if(i>=6)return (int)((energy>>>((i-6)*16))&65535L);
                int value=i<2?(int)Math.min(Integer.MAX_VALUE,energy):i<4?CircuitData.tag(module).getIntOr("SmeltProgress",0):SmeltingModule.ticks();
                return (i&1)==0?value&65535:value>>>16;
            }
            public void set(int i,int v){}public int getCount(){return 10;}
        };addDataSlots(data);
    }
    public long energyLong(){long value=0;for(int i=0;i<4;i++)value|=((long)data.get(6+i)&65535L)<<(i*16);return value;}
    public int energy(){return (int)Math.min(Integer.MAX_VALUE,energyLong());}
    public double progress(){int n=(data.get(2)&65535)|((data.get(3)&65535)<<16),t=(data.get(4)&65535)|((data.get(5)&65535)<<16);return Math.clamp((double)n/Math.max(1,t),0,1);}
    private void save(){if(module!=carrier)ModuleWorkstation.saveSelected(carrier,module);}
    public boolean stillValid(Player p){return p.getItemInHand(hand)==carrier&&(hand!=InteractionHand.MAIN_HAND||inventory.selected==heldSlot)&&CircuitData.tag(carrier).getIntOr("SelectedModule",0)==selectedSlot;}
    private boolean locked(int slot){return slot>=3&&slot<slots.size()&&slots.get(slot).container==inventory&&slots.get(slot).getContainerSlot()==heldSlot;}
    public void clicked(int slot,int button,ClickType type,Player p){if(!stillValid(p)||locked(slot)||type==ClickType.SWAP&&button==heldSlot)return;super.clicked(slot,button,type,p);}
    public ItemStack quickMoveStack(Player p,int index){if(!stillValid(p)||index<0||index>=slots.size()||locked(index))return ItemStack.EMPTY;Slot slot=slots.get(index);if(!slot.hasItem())return ItemStack.EMPTY;ItemStack s=slot.getItem(),copy=s.copy();if(!(index<3?moveItemStackTo(s,3,slots.size(),true):moveItemStackTo(s,0,1,false)))return ItemStack.EMPTY;if(s.isEmpty())slot.setByPlayer(ItemStack.EMPTY);else slot.setChanged();return copy;}
    public void broadcastChanges(){
        if(contents!=null&&!inventory.player.level().isClientSide()&&stillValid(inventory.player)){
            SmeltingModule.tick(module,carrier,inventory.player.level());save();refreshing=true;try{var items=SmeltingModule.contents(module);for(int i=0;i<3;i++)contents.setItem(i,items.get(i));}finally{refreshing=false;}
        }super.broadcastChanges();
    }
    public void removed(Player p){if(!p.level().isClientSide())save();super.removed(p);}
}
