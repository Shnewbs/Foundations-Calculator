package com.foundations.calculator.menu;

import java.util.*;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.*;

public final class CalculatorMenu extends AbstractContainerMenu {
    public final ItemStackHandler handler;
    public final MachineBlockEntity machine;
    public final int inputs;
    public final String kind;
    private final Inventory playerInventory;
    private final ItemStack held;
    private final InteractionHand hand;
    private final int heldSlot;
    private final ContainerData data;
    private long diagnosticTick=Long.MIN_VALUE;
    private MachineDiagnostics.Snapshot diagnostic;
    private MachineDiagnostics.Snapshot diagnostic(){
        long tick=playerInventory.player.level().getGameTime();
        if(diagnostic==null||diagnosticTick!=tick){diagnosticTick=tick;diagnostic=machine==null?MachineDiagnostics.portable(playerInventory.player.level(),handler,inputs,kind,held,playerInventory.player.getUUID()):MachineDiagnostics.machine(machine);}
        return diagnostic;
    }
    public static CalculatorMenu fromNetwork(int id,Inventory inv,FriendlyByteBuf buf){
        if(buf.readBoolean())return forMachine(id,inv,(MachineBlockEntity)inv.player.level().getBlockEntity(buf.readBlockPos()));
        return forModuleItem(id,inv,buf.readEnum(InteractionHand.class),buf.readUtf(128));
    }
    public static CalculatorMenu forMachine(int id,Inventory inv,MachineBlockEntity machine){return new CalculatorMenu(id,inv,machine,null,null);}
    public static CalculatorMenu forItem(int id,Inventory inv,InteractionHand hand){return new CalculatorMenu(id,inv,null,hand,null);}
    public static CalculatorMenu forModuleItem(int id,Inventory inv,InteractionHand hand,String kind){return new CalculatorMenu(id,inv,null,hand,kind);}
    private CalculatorMenu(int id,Inventory inv,MachineBlockEntity machine,InteractionHand hand,String selectedKind){
        super(Content.MENU.get(),id);this.machine=machine;this.hand=hand;playerInventory=inv;
        held=machine==null?inv.player.getItemInHand(hand):ItemStack.EMPTY;heldSlot=hand==InteractionHand.MAIN_HAND?inv.selected:40;
        handler=machine==null?new ItemStackHandler(25):machine.inventory;
        kind=machine==null?(selectedKind==null?Content.path(held):selectedKind):machine.kind();
        inputs=machine!=null?machine.inputCount():(kind.equals("dynamic_module")?7:kind.equals("flawless_calculator")?4:kind.equals("atomic_calculator")?3:2);
        for(int i=0;i<25;i++){
            final int slot=i;
            int x,y;
            if(kind.equals("module_workstation")&&i<16){x=16+(i%8)*18;y=45+(i/8)*18;}
            else if(i<14){x=16+(i%7)*18;y=45+(i/7)*18;}
            else if(i<20){x=160+(i-14)%3*18;y=45+(i-14)/3*18;}
            else if(i==20){x=16;y=99;}
            else{x=88+(i-21)*18;y=99;}
            addSlot(new SlotItemHandler(handler,i,x,y){
                public boolean isActive(){return kind.equals("module_workstation")?slot<16||slot==20:slot<14?slot<inputs:slot<20?(machine==null||machine.hasOutputs()):slot==20?machine!=null&&machine.hasBatterySlot():machine!=null&&machine.supportsUpgrades();}
                public void setChanged(){super.setChanged();diagnosticTick=Long.MIN_VALUE;if(machine!=null){machine.setChanged();if(kind.equals("module_workstation"))ModuleWorkstation.changed(machine,slot);}}
                public boolean mayPickup(Player player){return isActive()&&super.mayPickup(player);}
                public boolean mayPlace(ItemStack s){
                    return isActive()&&handler.isItemValid(slot,s)&&((slot<inputs&&(slot<14||kind.equals("module_workstation")))||(slot>=20&&machine!=null))&&(!kind.equals("module_workstation")||slot==20||!handler.getStackInSlot(20).isEmpty());
                }
            });
        }
        for(int r=0;r<3;r++)for(int col=0;col<9;col++)addSlot(new Slot(inv,col+r*9+9,35+col*18,157+r*18));
        for(int col=0;col<9;col++)addSlot(new Slot(inv,col,35+col*18,215));
        if(machine==null)HeldItemLock.install(this,inv,heldSlot);
        if(inv.player.level().isClientSide)data=new SimpleContainerData(36);
        else data=new ContainerData(){
            public int get(int i){
                if(i==27)return machine==null?0:machine.redstoneMode();
                long longEnergy=machine!=null&&kind.equals("calculator_screen_block")?machine.program.getLong("ScreenEnergy"):machine==null&&held.getItem() instanceof CalculatorItem c?c.energyLong(held):machine==null?0:machine.energy.stored();
                long longCapacity=machine!=null&&kind.equals("calculator_screen_block")?machine.program.getLong("ScreenCapacity"):machine==null&&held.getItem() instanceof CalculatorItem c?c.maxEnergyLong(held):machine==null?0:machine.energy.capacity();
                if(i>=28&&i<32)return (int)((longEnergy>>>((i-28)*16))&65535L);
                if(i>=32&&i<36)return (int)((longCapacity>>>((i-32)*16))&65535L);
                if(i==24)return diagnostic().status().ordinal();
                if(i==25)return diagnostic().requiredEnergy()&65535;
                if(i==26)return diagnostic().requiredEnergy()>>>16;
                int energy=(int)Math.min(Integer.MAX_VALUE,longEnergy);
                int capacity=(int)Math.min(Integer.MAX_VALUE,longCapacity);
                int progress=machine==null?0:machine.progress,time=machine==null?1:machine.totalTicks,points=machine==null?0:machine.nutrient;
                if(i>=18&&machine!=null)return machine.program.getInt("Side"+(i-18));
                if(i>=10&&machine!=null){var s=machine.program;return switch(i){case 10->s.getInt("HouseState");case 11->s.getInt("Carbon")&65535;case 12->s.getInt("Carbon")>>>16;case 13->s.getInt("Mode");case 14->s.getBoolean("Target")?1:0;case 15->s.getBoolean("Paused")?1:0;case 16->s.getBoolean("Whitelist")?1:0;default->s.getBoolean("MatchTags")?1:0;};}
                return switch(i){case 0->energy&65535;case 1->energy>>>16;case 2->capacity&65535;case 3->capacity>>>16;case 4->progress&65535;case 5->progress>>>16;case 6->time&65535;case 7->time>>>16;case 8->points&65535;default->points>>>16;};
            }
            public void set(int i,int v){} public int getCount(){return 36;}
        };
        addDataSlots(data);
    }
    public MachineStatus status(){return MachineStatus.decode(data.get(24));}
    public int requiredEnergy(){return (data.get(25)&65535)|((data.get(26)&65535)<<16);}
    public int state(int index){return data.get(index);}
    private long longWords(int start){long value=0;for(int i=0;i<4;i++)value|=((long)data.get(start+i)&65535L)<<(i*16);return value;}
    public long energyLong(){return longWords(28);}
    public long capacityLong(){return longWords(32);}
    public int energy(){return (int)Math.min(Integer.MAX_VALUE,energyLong());}
    public int capacity(){return (int)Math.min(Integer.MAX_VALUE,capacityLong());}
    public int points(){return (data.get(8)&65535)|((data.get(9)&65535)<<16);}
    public double progress(){int progress=(data.get(4)&65535)|((data.get(5)&65535)<<16),time=(data.get(6)&65535)|((data.get(7)&65535)<<16);return time<=0?0:(double)progress/time;}
    public boolean stillValid(Player player){
        if(machine!=null)return !machine.isRemoved()&&player.level()==machine.getLevel()&&player.distanceToSqr(machine.getBlockPos().getCenter())<=64;
        return player.getItemInHand(hand)==held && (hand!=InteractionHand.MAIN_HAND||playerInventory.selected==heldSlot);
    }
    private boolean locked(int slot){return machine==null&&slot>=25&&slot<slots.size()&&slots.get(slot).container==playerInventory&&slots.get(slot).getContainerSlot()==heldSlot;}
    public void clicked(int slot,int button,ClickType type,Player player){
        if(locked(slot)||machine==null&&type==ClickType.SWAP&&button==heldSlot)return;
        super.clicked(slot,button,type,player);
    }
    public ItemStack quickMoveStack(Player player,int index){
        if(index<0||index>=slots.size()||locked(index))return ItemStack.EMPTY;
        Slot slot=slots.get(index);if(!slot.hasItem()||!slot.mayPickup(player))return ItemStack.EMPTY;
        ItemStack original=slot.getItem(),copy=original.copy();
        if(index<25){if(!moveItemStackTo(original,25,slots.size(),true))return ItemStack.EMPTY;}
        else if(!moveIntoMachine(original))return ItemStack.EMPTY;
        if(original.isEmpty())slot.setByPlayer(ItemStack.EMPTY);else slot.setChanged();
        slot.onTake(player,original);return copy;
    }
    private boolean moveIntoMachine(ItemStack stack){
        if(machine==null)return moveItemStackTo(stack,0,inputs,false);
        if(kind.equals("module_workstation"))return Content.path(stack).equals("flawless_calculator")?moveItemStackTo(stack,20,21,false):moveItemStackTo(stack,0,inputs,false);
        if(Content.path(stack).endsWith("_upgrade")&&machine.supportsUpgrades())return moveItemStackTo(stack,21,25,false);
        if(MachineProfiles.storage(kind)){
            if(EnergyItems.fuelValue(stack)>0)return moveItemStackTo(stack,0,1,false);
            if(EnergyItems.canCharge(stack))return moveItemStackTo(stack,20,21,false);
            return EnergyItems.canDischarge(stack)&&moveItemStackTo(stack,0,1,false);
        }
        if(kind.endsWith("extractor")){
            if(MachineDiagnostics.recipeIngredient(playerInventory.player.level(),kind,stack))return moveItemStackTo(stack,1,2,false);
            if(stack.getBurnTime(net.minecraft.world.item.crafting.RecipeType.SMELTING)>0)return moveItemStackTo(stack,0,1,false);
        }
        if(machine.hasBatterySlot()&&(NutritionData.capacity(stack,"health")>0||NutritionData.capacity(stack,"hunger")>0))return moveItemStackTo(stack,20,21,false);
        if(MachineDiagnostics.recipeIngredient(playerInventory.player.level(),MachineDiagnostics.family(machine),stack))return moveItemStackTo(stack,0,inputs,false);
        if(machine.hasBatterySlot()&&handler.isItemValid(20,stack)&&moveItemStackTo(stack,20,21,false))return true;
        return moveItemStackTo(stack,0,inputs,false);
    }
    public boolean clickMenuButton(Player player,int button){
        if(player.level().isClientSide||!stillValid(player))return false;
        if(machine!=null)return machine.action(player,button);
        if(kind.equals("dynamic_module"))return dynamicCalculation(player,button);
        if(button!=0)return false;
        if(!(held.getItem() instanceof CalculatorItem item))return false;
        String recipeKind=switch(kind){case "calculator"->"calculator";case "scientific_calculator"->"scientific";case "flawless_calculator"->"flawless";case "atomic_calculator"->"atomic";default->"none";};
        Level level=player.level();ProcessInput input=ProcessTransactions.input(handler,inputs);
        var match=RecipeIndex.forMachine(level,recipeKind,player.getUUID()).stream()
            .filter(r->r.value().machine().equals(recipeKind)&&r.value().matches(input,level))
            .sorted(Comparator.comparing(r->r.id().toString())).findFirst();
        if(match.isEmpty())return false;
        int cost=CalculatorConfig.energy(kind,RecipePolicies.energy(match.get(),1));
        if(item.energy(held)<cost)return false;
        ProcessRecipe recipe=match.get().value();int[] used=recipe.allocation(input);if(used==null)return false;
        ItemStackHandler copy=new ItemStackHandler(25);
        for(int i=0;i<25;i++)copy.setStackInSlot(i,handler.getStackInSlot(i).copy());
        List<ItemStack> results=ProcessTransactions.consume(copy,used,recipe,level);
        ProcessTransactions.flush(results,copy);if(!results.isEmpty())return false;
        item.storage(held).extractEnergy(cost,false);
        for(int i=0;i<25;i++)handler.setStackInSlot(i,copy.getStackInSlot(i));
        ResearchData.completed(level,player.getUUID(),recipeKind);
        broadcastChanges();return true;
    }
    private boolean dynamicCalculation(Player player,int lane){
        if(lane<0||lane>2||!(held.getItem() instanceof CalculatorItem item))return false;
        int start=lane==2?4:lane*2,count=lane==2?3:2;String family=new String[]{"calculator","scientific","atomic"}[lane];
        ItemStackHandler temp=new ItemStackHandler(25);for(int i=0;i<count;i++)temp.setStackInSlot(i,handler.getStackInSlot(start+i).copy());for(int i=14;i<20;i++)temp.setStackInSlot(i,handler.getStackInSlot(i).copy());
        var input=ProcessTransactions.input(temp,count);var found=RecipeIndex.forMachine(player.level(),family,player.getUUID()).stream().filter(r->r.value().matches(input,player.level())).findFirst();if(found.isEmpty())return false;
        var recipe=found.get().value();int cost=CalculatorConfig.energy(kind,RecipePolicies.energy(found.get(),1));if(item.energy(held)<cost)return false;
        var results=ProcessTransactions.consume(temp,recipe.allocation(input),recipe,player.level());ProcessTransactions.flush(results,temp);if(!results.isEmpty())return false;
        item.storage(held).extractEnergy(cost,false);for(int i=0;i<count;i++)handler.setStackInSlot(start+i,temp.getStackInSlot(i));for(int i=14;i<20;i++)handler.setStackInSlot(i,temp.getStackInSlot(i));ResearchData.completed(player.level(),player.getUUID(),family);broadcastChanges();return true;
    }
    public void removed(Player player){
        super.removed(player);
        if(machine==null&&!player.level().isClientSide){
            for(int i=0;i<25;i++){
                ItemStack stack=handler.extractItem(i,Integer.MAX_VALUE,false);
                if(!stack.isEmpty()){if(!player.isAlive()||(player instanceof net.minecraft.server.level.ServerPlayer sp && sp.hasDisconnected()))player.drop(stack,false);else player.getInventory().placeItemBackInInventory(stack);}
            }
        }
    }
}
