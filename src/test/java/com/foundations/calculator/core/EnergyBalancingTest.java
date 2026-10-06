package com.foundations.calculator.core;

import java.math.BigInteger;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnergyBalancingTest {
    @Test void overflowingProductsRetainTheirRelativeDifference(){
        assertEquals(400_000_000L,EnergyBalancing.transfer(30_000_000_000L,50_000_000_000L,10_000_000_000L,50_000_000_000L,400_000_000L));
    }
    @Test void comparesFillFractionRatherThanRawAmount(){
        assertEquals(400,EnergyBalancing.transfer(800,1000,400,2000,1000));
        assertEquals(0,EnergyBalancing.transfer(400,2000,800,1000,1000));
    }
    @Test void capacitySumAndStoredAmountsMayReachLongLimit(){
        assertEquals(Long.MAX_VALUE/2,EnergyBalancing.transfer(Long.MAX_VALUE,Long.MAX_VALUE,0,Long.MAX_VALUE,Long.MAX_VALUE));
        assertEquals(0,EnergyBalancing.transfer(Long.MAX_VALUE,Long.MAX_VALUE,Long.MAX_VALUE,Long.MAX_VALUE,Long.MAX_VALUE));
    }
    @Test void emptyDisabledAndInvalidCapacitiesDoNotTransfer(){
        assertEquals(0,EnergyBalancing.transfer(0,100,0,100,10));
        assertEquals(0,EnergyBalancing.transfer(100,100,0,100,0));
        assertEquals(0,EnergyBalancing.transfer(100,0,0,100,10));
        assertEquals(0,EnergyBalancing.transfer(100,100,0,0,10));
    }
    @Test void boundedResultMatchesExactArithmeticAcrossSmallAndLargeStores(){
        var random=new Random(8675309);
        for(int i=0;i<5000;i++){
            long a=random.nextLong()&Long.MAX_VALUE,b=random.nextLong()&Long.MAX_VALUE;
            long ca=Math.max(1,random.nextLong()&Long.MAX_VALUE),cb=Math.max(1,random.nextLong()&Long.MAX_VALUE);
            if(i%2==0){a%=100000;b%=100000;ca=1+ca%100000;cb=1+cb%100000;}
            long limit=random.nextInt(Integer.MAX_VALUE);
            var numerator=BigInteger.valueOf(a).multiply(BigInteger.valueOf(cb)).subtract(BigInteger.valueOf(b).multiply(BigInteger.valueOf(ca)));
            long expected=numerator.signum()<=0?0:numerator.divide(BigInteger.valueOf(ca).add(BigInteger.valueOf(cb))).min(BigInteger.valueOf(Math.min(a,limit))).longValueExact();
            assertEquals(expected,EnergyBalancing.transfer(a,ca,b,cb,limit),"case "+i);
        }
    }
}
