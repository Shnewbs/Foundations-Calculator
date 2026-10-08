package com.foundations.calculator.platform;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.LongSupplier;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

/** Live face policy over one shared storage journal: never snapshot each face separately. */
public final class SidedEnergyHandler implements EnergyHandler {
    private final EnergyHandler storage;
    private final BooleanSupplier input, output;
    private final LongSupplier rate;

    public SidedEnergyHandler(EnergyHandler storage, BooleanSupplier input,
            BooleanSupplier output, LongSupplier rate) {
        this.storage=Objects.requireNonNull(storage);
        this.input=Objects.requireNonNull(input);
        this.output=Objects.requireNonNull(output);
        this.rate=Objects.requireNonNull(rate);
    }
    @Override public long getAmountAsLong(){return storage.getAmountAsLong();}
    @Override public long getCapacityAsLong(){return storage.getCapacityAsLong();}
    @Override public int insert(int amount,TransactionContext tx){
        if(amount<0)throw new IllegalArgumentException("Negative transfer");
        return input.getAsBoolean()?storage.insert((int)Math.min(amount,Math.max(0,rate.getAsLong())),tx):0;
    }
    @Override public int extract(int amount,TransactionContext tx){
        if(amount<0)throw new IllegalArgumentException("Negative transfer");
        return output.getAsBoolean()?storage.extract((int)Math.min(amount,Math.max(0,rate.getAsLong())),tx):0;
    }
}
