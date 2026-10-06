package com.foundations.calculator.platform;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import net.neoforged.neoforge.transfer.transaction.Transaction;

final class TransactionalEnergyStorageTest {
    private final AtomicInteger changes=new AtomicInteger();
    private final AtomicLong cap=new AtomicLong(Long.MAX_VALUE),rate=new AtomicLong(Long.MAX_VALUE);
    private TransactionalEnergyStorage storage(long amount){return new TransactionalEnergyStorage(amount,cap::get,rate::get,()->true,()->true,changes::incrementAndGet);}
    @Test void simulationAbortsWithoutNotification(){
        var s=storage(0);try(var tx=Transaction.openRoot()){assertEquals(3_000_000_000L,s.insertLong(3_000_000_000L,tx));}
        assertEquals(0,s.getAmountAsLong());assertEquals(0,changes.get());
    }
    @Test void rootCommitNotifiesOnce(){
        var s=storage(0);try(var tx=Transaction.openRoot()){s.insertLong(4_000_000_000L,tx);s.extractLong(1_000_000_000L,tx);assertEquals(0,changes.get());tx.commit();}
        assertEquals(3_000_000_000L,s.getAmountAsLong());assertEquals(1,changes.get());
    }
    @Test void committedChildStillRollsBackWithParent(){
        var s=storage(50);try(var root=Transaction.openRoot()){s.insert(10,root);try(var child=Transaction.open(root)){s.extract(20,child);child.commit();}}
        assertEquals(50,s.getAmountAsLong());assertEquals(0,changes.get());
    }
    @Test void abortedChildPreservesParentCommit(){
        var s=storage(50);try(var root=Transaction.openRoot()){s.insert(10,root);try(var child=Transaction.open(root)){s.extract(20,child);}assertEquals(60,s.getAmountAsLong());root.commit();}
        assertEquals(60,s.getAmountAsLong());assertEquals(1,changes.get());
    }
    @Test void reducedCapacityPreservesExistingEnergy(){
        var s=storage(3_000_000_000L);cap.set(100);try(var tx=Transaction.openRoot()){assertEquals(0,s.insert(100,tx));assertEquals(3_000_000_000L,s.extractLong(Long.MAX_VALUE,tx));tx.commit();}
        assertEquals(0,s.getAmountAsLong());
    }
    @Test void nearLongLimitDoesNotOverflow(){
        var s=storage(Long.MAX_VALUE-2);try(var tx=Transaction.openRoot()){assertEquals(2,s.insertLong(Long.MAX_VALUE,tx));tx.commit();}assertEquals(Long.MAX_VALUE,s.getAmountAsLong());
    }
    @Test void transferLimitsApplyToBothInterfaces(){
        var s=storage(100);rate.set(5);try(var tx=Transaction.openRoot()){assertEquals(5,s.insert(20,tx));assertEquals(5,s.extractLong(20,tx));tx.commit();}assertEquals(100,s.getAmountAsLong());assertEquals(0,changes.get());
    }
    @Test void disabledGatesAndNegativeRequestsCannotMutate(){
        var s=new TransactionalEnergyStorage(100,cap::get,rate::get,()->false,()->false,changes::incrementAndGet);
        try(var tx=Transaction.openRoot()){assertEquals(0,s.insert(10,tx));assertEquals(0,s.extract(10,tx));assertThrows(IllegalArgumentException.class,()->s.insertLong(-1,tx));assertThrows(IllegalArgumentException.class,()->s.extract(-1,tx));tx.commit();}
        assertEquals(100,s.getAmountAsLong());assertEquals(0,changes.get());
    }
}
