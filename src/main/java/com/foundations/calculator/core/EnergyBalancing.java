package com.foundations.calculator.core;

import java.math.BigInteger;

/** Exact capacity-weighted balancing, with an allocation-free path for ordinary stores. */
public final class EnergyBalancing {
    public static long transfer(long stored,long capacity,long otherStored,long otherCapacity,long limit) {
        if(stored<=0||capacity<=0||otherCapacity<=0||limit<=0)return 0;
        otherStored=Math.max(0,otherStored);
        long maximum=Math.min(stored,limit);
        if(capacity==otherCapacity)return stored<=otherStored?0:Math.min(maximum,(stored-otherStored)/2);
        if(stored<=Long.MAX_VALUE/otherCapacity&&otherStored<=Long.MAX_VALUE/capacity
                &&capacity<=Long.MAX_VALUE-otherCapacity){
            long surplus=stored*otherCapacity-otherStored*capacity;
            return surplus<=0?0:Math.min(maximum,surplus/(capacity+otherCapacity));
        }
        BigInteger own=BigInteger.valueOf(stored),ownCap=BigInteger.valueOf(capacity);
        BigInteger target=BigInteger.valueOf(otherStored),targetCap=BigInteger.valueOf(otherCapacity);
        BigInteger surplus=own.multiply(targetCap).subtract(target.multiply(ownCap));
        if(surplus.signum()<=0)return 0;
        return surplus.divide(ownCap.add(targetCap)).min(BigInteger.valueOf(maximum)).longValueExact();
    }
    private EnergyBalancing(){}
}
