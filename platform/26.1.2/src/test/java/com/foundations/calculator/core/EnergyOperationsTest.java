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
}
