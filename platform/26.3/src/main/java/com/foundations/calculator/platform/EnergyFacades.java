package com.foundations.calculator.platform;

import com.foundations.calculator.api.EnergyPort;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;

/** Execute/simulate convenience calls join an existing transaction instead of opening a new root. */
public final class EnergyFacades {
    public static EnergyPort bounded(EnergyHandler handler){
        if(handler==null)return null;
        return new EnergyPort(){
            public int receiveEnergy(int amount,boolean simulate){
                try(var tx=Transaction.open(Transaction.getCurrentOpenedTransaction())){
                    int moved=handler.insert(Math.max(0,amount),tx);if(!simulate)tx.commit();return moved;
                }
            }
            public int extractEnergy(int amount,boolean simulate){
                try(var tx=Transaction.open(Transaction.getCurrentOpenedTransaction())){
                    int moved=handler.extract(Math.max(0,amount),tx);if(!simulate)tx.commit();return moved;
                }
            }
            public int getEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,handler.getAmountAsLong());}
            public int getMaxEnergyStored(){return (int)Math.min(Integer.MAX_VALUE,handler.getCapacityAsLong());}
            public boolean canReceive(){return receiveEnergy(1,true)>0;}
            public boolean canExtract(){return extractEnergy(1,true)>0;}
        };
    }
    private EnergyFacades(){}
}
