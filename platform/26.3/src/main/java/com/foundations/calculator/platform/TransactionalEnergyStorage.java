package com.foundations.calculator.platform;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

/** 26.3 storage boundary: long amounts, reversible transfers and commit-only notifications. */
public final class TransactionalEnergyStorage extends SnapshotJournal<Long> implements EnergyHandler {
    private long amount;
    private final LongSupplier capacity, rate;
    private final BooleanSupplier input, output;
    private final Runnable changed;

    public TransactionalEnergyStorage(long amount, LongSupplier capacity, LongSupplier rate,
            BooleanSupplier input, BooleanSupplier output, Runnable changed) {
        if(amount < 0) throw new IllegalArgumentException("Negative stored energy");
        this.amount=amount;
        this.capacity=Objects.requireNonNull(capacity);
        this.rate=Objects.requireNonNull(rate);
        this.input=Objects.requireNonNull(input);
        this.output=Objects.requireNonNull(output);
        this.changed=Objects.requireNonNull(changed);
    }
    @Override public long getAmountAsLong(){return amount;}
    @Override public long getCapacityAsLong(){return Math.max(0,capacity.getAsLong());}
    @Override public int insert(int requested,TransactionContext transaction){return (int)insertLong(requested,transaction);}
    @Override public int extract(int requested,TransactionContext transaction){return (int)extractLong(requested,transaction);}
    public long insertLong(long requested,TransactionContext transaction){
        if(requested<0)throw new IllegalArgumentException("Negative transfer");
        long cap=getCapacityAsLong();
        long accepted=input.getAsBoolean()&&amount<cap?Math.min(requested,Math.min(cap-amount,Math.max(0,rate.getAsLong()))):0;
        if(accepted>0){updateSnapshots(transaction);amount+=accepted;}
        return accepted;
    }
    public long extractLong(long requested,TransactionContext transaction){
        if(requested<0)throw new IllegalArgumentException("Negative transfer");
        long extracted=output.getAsBoolean()?Math.min(requested,Math.min(amount,Math.max(0,rate.getAsLong()))):0;
        if(extracted>0){updateSnapshots(transaction);amount-=extracted;}
        return extracted;
    }
    @Override protected Long createSnapshot(){return amount;}
    @Override protected void revertToSnapshot(Long snapshot){amount=snapshot;}
    @Override protected void onRootCommit(Long original){if(amount!=original)changed.run();}
}
