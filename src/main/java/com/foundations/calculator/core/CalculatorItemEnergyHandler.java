package com.foundations.calculator.core;

import com.foundations.calculator.content.CalculatorItem;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

/** Mutate a copy (including installed energy modules), then exchange through the item journal. */
public final class CalculatorItemEnergyHandler implements EnergyHandler {
    private final CalculatorItem item;
    private final ItemAccess access;
    public CalculatorItemEnergyHandler(CalculatorItem item,ItemAccess access){this.item=item;this.access=access;}
    private boolean valid(){return access.getAmount()==1&&access.getResource().is(item);}
    public long getAmountAsLong(){return valid()?item.energyLong(access.getResource().toStack()):0;}
    public long getCapacityAsLong(){return valid()?item.maxEnergyLong(access.getResource().toStack()):0;}
    public int insert(int amount,TransactionContext tx){return transfer(amount,tx,true);}
    public int extract(int amount,TransactionContext tx){return transfer(amount,tx,false);}
    private int transfer(int amount,TransactionContext parent,boolean input){
        if(amount<0)throw new IllegalArgumentException("Negative transfer");
        if(!valid()||amount==0||!(input?PowerPolicy.FE.input(PowerPolicy.Scope.ITEM):PowerPolicy.FE.output(PowerPolicy.Scope.ITEM)))return 0;
        try(var tx=Transaction.open(parent)){
            var copy=access.getResource().toStack();var storage=item.longStorage(copy);
            int moved=(int)(input?storage.receive(amount,false):storage.extract(amount,false));
            if(moved==0||access.exchange(ItemResource.of(copy),1,tx)!=1)return 0;
            tx.commit();return moved;
        }
    }
}
