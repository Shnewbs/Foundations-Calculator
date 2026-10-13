package com.foundations.calculator.core;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class StaggeredWorkTest {
    @Test void nutritionFleetKeepsCadenceAndSpreadsScanPeaks(){
        int[] ticks=new int[20];
        for(long position=0;position<1000;position++){
            int calls=0;
            for(int tick=0;tick<40;tick++)if(StaggeredWork.due(tick,position,20,true)){
                calls++;if(tick<20)ticks[tick]++;
            }
            assertEquals(2,calls);
        }
        assertEquals(1000,java.util.Arrays.stream(ticks).sum());
        assertTrue(java.util.Arrays.stream(ticks).max().orElseThrow()<100,
            "Default staggered fleet must avoid the legacy 1000-scan tick burst");
    }
    @Test void disablingStaggeringPreservesLegacyTickPhase(){
        for(long position=0;position<100;position++)for(int tick=0;tick<40;tick++)
            assertEquals(tick%20==0,StaggeredWork.due(tick,position,20,false));
    }
}
