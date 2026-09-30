package com.foundations.calculator.core;

import com.foundations.calculator.compat.LongEnergyBridge;

/** Isolated arithmetic/storage regression for 0.0.2a long-valued canonical FE. */
public final class LongEnergyAssertions {
    private static int checks;
    private static void ok(boolean v,String m){checks++;if(!v)throw new AssertionError(m);}
    public static void main(String[] args){
        StoredEnergy store=new StoredEnergy(12_000_000_000L,()->{});
        ok(store.receive(5_000_000_000L,false)==5_000_000_000L,"receive 5B");
        ok(store.stored()==5_000_000_000L,"store keeps 5B");
        ok(store.getEnergyStored()==Integer.MAX_VALUE,"FE facade saturates stored");
        ok(store.getMaxEnergyStored()==Integer.MAX_VALUE,"FE facade saturates capacity");
        ok(store.extract(1_500_000_000L,true)==1_500_000_000L,"simulate extract");
        ok(store.stored()==5_000_000_000L,"simulate immutable");
        ok(store.extract(1_500_000_000L,false)==1_500_000_000L,"real extract");
        ok(store.stored()==3_500_000_000L,"post extract");

        EnergyRatio four=EnergyRatio.fePerNative(4.0);
        ok(four.feLongFromNative(2_000_000_000L)==8_000_000_000L,"long native->FE");
        ok(four.nativeFromFELong(8_000_000_000L)==2_000_000_000L,"long FE->native");
        EnergyConversion plan=EnergyConversion.of(four,0,0);
        StoredEnergy converted=new StoredEnergy(10_000_000_000L,()->{});
        var eu=LongEnergyBridge.fromLong(converted,()->plan,()->true,()->true);
        ok(eu.receive(2_000_000_000L,true)==2_000_000_000L,"long converted simulation");
        ok(converted.stored()==0,"converted simulation immutable");
        ok(eu.receive(2_000_000_000L,false)==2_000_000_000L,"long converted receive");
        ok(converted.stored()==8_000_000_000L,"converted stores 8B FE");
        ok(eu.stored()==2_000_000_000L,"native view reports 2B EU");
        ok(eu.extract(1_000_000_000L,false)==1_000_000_000L,"native extract");
        ok(converted.stored()==4_000_000_000L,"native extract debits 4B FE");

        store.load(Long.MAX_VALUE);
        ok(store.stored()==Long.MAX_VALUE,"load preserves persisted excess after a capacity decrease");
        ok(store.receive(1,false)==0,"over-capacity store refuses additional energy");
        ok(store.extract(Long.MAX_VALUE-12_000_000_000L,false)==Long.MAX_VALUE-12_000_000_000L,"excess drains without truncation");
        ok(store.stored()==12_000_000_000L,"draining reaches configured capacity exactly");

        java.util.concurrent.atomic.AtomicLong cap=new java.util.concurrent.atomic.AtomicLong(5_000_000_000L);
        StoredEnergy dynamic=new StoredEnergy(cap::get,()->{});dynamic.receive(4_000_000_000L,false);cap.set(50_000L);
        ok(dynamic.stored()==4_000_000_000L&&dynamic.capacity()==50_000L,"live capacity reduction preserves stored FE");
        ok(dynamic.receive(1,false)==0,"reduced capacity cannot accept more while overfull");
        dynamic.extract(1_000_000_000L,false);
        ok(dynamic.stored()==3_000_000_000L,"overfull dynamic store drains incrementally");
        System.out.println("LONG_ENERGY_PASS checks="+checks);
    }
    private LongEnergyAssertions(){}
}
