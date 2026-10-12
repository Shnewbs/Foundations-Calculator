package com.foundations.calculator.platform;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import net.neoforged.neoforge.transfer.transaction.Transaction;

final class SidedEnergyHandlerTest {
    @Test void facesShareRollbackAndNotifyOnlyOnce(){
        var changed=new AtomicInteger();
        var store=new TransactionalEnergyStorage(50,()->100,()->100,()->true,()->true,changed::incrementAndGet);
        var a=new SidedEnergyHandler(store,()->true,()->true,()->100);
        var b=new SidedEnergyHandler(store,()->true,()->true,()->100);
        try(var root=Transaction.openRoot()){
            assertEquals(20,a.insert(20,root));
            try(var nested=Transaction.open(root)){assertEquals(30,b.extract(30,nested));nested.commit();}
        }
        assertEquals(50,store.getAmountAsLong());assertEquals(0,changed.get());
        try(var tx=Transaction.openRoot()){a.insert(20,tx);b.extract(10,tx);tx.commit();}
        assertEquals(60,store.getAmountAsLong());assertEquals(1,changed.get());
    }
    @Test void gatesAndLimitsRemainLive(){
        var store=new TransactionalEnergyStorage(50,()->100,()->100,()->true,()->true,()->{});
        var enabled=new AtomicBoolean(false);var rate=new AtomicLong(5);
        var face=new SidedEnergyHandler(store,enabled::get,()->false,rate::get);
        try(var tx=Transaction.openRoot()){
            assertEquals(0,face.insert(10,tx));enabled.set(true);assertEquals(5,face.insert(10,tx));
            rate.set(0);assertEquals(0,face.insert(10,tx));assertEquals(0,face.extract(10,tx));
            assertThrows(IllegalArgumentException.class,()->face.insert(-1,tx));tx.commit();
        }
        assertEquals(55,store.getAmountAsLong());
    }
    @Test void faceRateAboveIntLimitDoesNotNarrowOrOverflow(){
        var store=new TransactionalEnergyStorage(3_000_000_000L,()->Long.MAX_VALUE,()->Long.MAX_VALUE,()->true,()->true,()->{});
        var face=new SidedEnergyHandler(store,()->true,()->true,()->Long.MAX_VALUE);
        try(var tx=Transaction.openRoot()){assertEquals(Integer.MAX_VALUE,face.insert(Integer.MAX_VALUE,tx));tx.commit();}
        assertEquals(5_147_483_647L,face.getAmountAsLong());assertEquals(Long.MAX_VALUE,face.getCapacityAsLong());
    }
}
