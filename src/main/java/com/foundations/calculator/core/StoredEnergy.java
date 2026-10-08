package com.foundations.calculator.core;

import com.foundations.calculator.api.EnergyPort;
import com.foundations.calculator.api.LongEnergyStorage;
import com.foundations.calculator.platform.TransactionalEnergyStorage;
import java.util.function.LongSupplier;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/** One canonical long storage journal shared by all machine faces and native adapters. */
public final class StoredEnergy extends TransactionalEnergyStorage implements EnergyPort,LongEnergyStorage {
    public StoredEnergy(long capacity,Runnable dirty){this(checked(capacity),dirty);}
    private static LongSupplier checked(long capacity){
        if(capacity<=0)throw new IllegalArgumentException("capacity");return ()->capacity;
    }
    public StoredEnergy(LongSupplier capacity,Runnable dirty){
        super(0,()->Math.max(1,capacity.getAsLong()),()->Long.MAX_VALUE,()->true,()->true,dirty);
    }
    public void load(long amount){loadStoredAmount(amount);}
    public long receive(long amount,boolean simulate){
        try(var tx=Transaction.open(Transaction.getCurrentOpenedTransaction())){
            long moved=insertLong(Math.max(0,amount),tx);if(!simulate)tx.commit();return moved;
        }
    }
    public long extract(long amount,boolean simulate){
        try(var tx=Transaction.open(Transaction.getCurrentOpenedTransaction())){
            long moved=extractLong(Math.max(0,amount),tx);if(!simulate)tx.commit();return moved;
        }
    }
    public long stored(){return getAmountAsLong();}
    public long capacity(){return getCapacityAsLong();}
    public boolean canExtract(){return true;}
    public boolean canReceive(){return true;}
    public int receiveEnergy(int amount,boolean simulate){return (int)receive(Math.max(0,amount),simulate);}
    public int extractEnergy(int amount,boolean simulate){return (int)extract(Math.max(0,amount),simulate);}
    public int getEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,stored());}
    public int getMaxEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,capacity());}
}
