package com.foundations.calculator.core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnergyOperationsTest {
    @Test void scalingCrossesIntegerLimit(){assertEquals(10_000_000_000L,EnergyOperations.scale(1_000_000L,100,100));}
    @Test void ordinaryDefaultsKeepTheirExactValues(){assertEquals(500,EnergyOperations.scale(500,1,1));assertEquals(357,EnergyOperations.discounted(500,4,.1));}
    @Test void largeUnchangedCostsKeepLowBits(){assertEquals(Long.MAX_VALUE-13,EnergyOperations.scale(Long.MAX_VALUE-13,1));assertEquals(Long.MAX_VALUE-13,EnergyOperations.discounted(Long.MAX_VALUE-13,0,.1));}
    @Test void decimalMultipliersDoNotLoseWholeFe(){assertEquals(9_007_199_254_740_993L,EnergyOperations.scale(90_071_992_547_409_930L,.1));}
    @Test void zeroFactorsDisableEvenOversizedOperations(){assertEquals(0,EnergyOperations.scale(Long.MAX_VALUE,100,0));assertEquals(0,EnergyOperations.scale(0,100));}
    @Test void overflowSaturatesAfterAllFactors(){assertEquals(Long.MAX_VALUE,EnergyOperations.scale(Long.MAX_VALUE,2));assertEquals(Long.MAX_VALUE/2,EnergyOperations.scale(Long.MAX_VALUE,2,.25));}
    @Test void upgradesDiscountLongCostsWithoutAnIntegerCap(){assertEquals(7_142_857_142L,EnergyOperations.discounted(10_000_000_000L,4,.1));}
    @Test void invalidMultipliersCannotCreateEnergy(){assertThrows(IllegalArgumentException.class,()->EnergyOperations.scale(1,Double.NaN));assertThrows(IllegalArgumentException.class,()->EnergyOperations.discounted(1,1,-.1));}
    @Test void cachedDiscountMatchesExactArithmeticAndLiveInputs(){
        var cache=new EnergyOperations.DiscountCache();var random=new java.util.Random(1734096);
        for(int i=0;i<20000;i++){
            long base=i%7==0?Long.MAX_VALUE:Math.abs(random.nextLong()>>>1);int upgrades=random.nextInt(1025);double discount=new double[]{0,.1,.25,1,100}[i%5];
            long expected=EnergyOperations.discounted(base,upgrades,discount);
            assertEquals(expected,cache.apply(base,upgrades,discount));assertEquals(expected,cache.apply(base,upgrades,discount));
        }
        assertEquals(357,cache.apply(500,4,.1));assertEquals(250,cache.apply(500,4,.25));assertEquals(166,cache.apply(500,8,.25));
    }
    @Test void cachedDiscountPreservesValidationAndCannotPoisonNextCall(){
        var cache=new EnergyOperations.DiscountCache();assertEquals(357,cache.apply(500,4,.1));
        assertThrows(IllegalArgumentException.class,()->cache.apply(500,4,Double.NaN));
        assertThrows(IllegalArgumentException.class,()->cache.apply(500,0,-.1));
        assertEquals(0,cache.apply(0,4,Double.NaN));assertEquals(500,cache.apply(500,0,.1));assertEquals(357,cache.apply(500,4,.1));
    }
}
