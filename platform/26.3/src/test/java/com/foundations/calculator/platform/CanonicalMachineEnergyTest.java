package com.foundations.calculator.platform;

import com.foundations.calculator.core.StoredEnergy;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import net.neoforged.neoforge.transfer.transaction.Transaction;

final class CanonicalMachineEnergyTest {
    @Test void convenienceCallsJoinAndRollbackWithExternalRoot(){
        var changed=new AtomicInteger();var storage=new StoredEnergy(Long.MAX_VALUE,changed::incrementAndGet);storage.load(3_000_000_000L);
        var face=new SidedEnergyHandler(storage,()->true,()->true,()->Long.MAX_VALUE);
        try(var tx=Transaction.openRoot()){
            storage.receive(5_000_000_000L,false);face.extract(100,tx);
            assertEquals(7_999_999_900L,storage.stored());assertEquals(0,changed.get());
        }
        assertEquals(3_000_000_000L,storage.stored());assertEquals(0,changed.get());
    }
    @Test void simulationAndFaceShareOneCanonicalCommit(){
        var changed=new AtomicInteger();var storage=new StoredEnergy(100,changed::incrementAndGet);
        var port=EnergyFacades.bounded(storage);
        try(var tx=Transaction.openRoot()){
            assertEquals(50,port.receiveEnergy(50,true));assertEquals(0,storage.stored());
            storage.receive(60,false);assertEquals(20,port.extractEnergy(20,false));tx.commit();
        }
        assertEquals(40,storage.stored());assertEquals(1,changed.get());
    }
    @Test void persistencePreservesSurplusButRejectsLoadDuringTransfer(){
        var cap=new AtomicLong(100);var storage=new StoredEnergy(cap::get,()->{});storage.load(3_000_000_000L);
        assertEquals(0,storage.receive(1,false));cap.set(1);
        try(var tx=Transaction.openRoot()){
            assertThrows(IllegalStateException.class,()->storage.load(0));storage.extract(100,false);
        }
        assertEquals(3_000_000_000L,storage.stored());
    }
    @Test void committedLongMutationNotifiesOnceAndKeepsIntFacadeBounded(){
        var changed=new AtomicInteger();var storage=new StoredEnergy(Long.MAX_VALUE,changed::incrementAndGet);
        try(var tx=Transaction.openRoot()){storage.receive(5_000_000_000L,false);storage.extract(1_000_000_000L,false);tx.commit();}
        assertEquals(4_000_000_000L,storage.stored());assertEquals(Integer.MAX_VALUE,storage.getEnergyStored());assertEquals(1,changed.get());
    }
}
