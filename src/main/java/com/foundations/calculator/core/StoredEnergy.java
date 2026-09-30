package com.foundations.calculator.core;

import com.foundations.calculator.api.LongEnergyStorage;
import java.util.function.LongSupplier;
import net.neoforged.neoforge.energy.IEnergyStorage;

/**
 * Checked long-valued canonical FE store. IEnergyStorage methods are bounded facades because
 * NeoForge's FE capability is int-valued; native long adapters use the LongEnergyStorage side.
 */
public final class StoredEnergy implements IEnergyStorage, LongEnergyStorage {
    private long energy;
    private final LongSupplier capacity;
    private final Runnable dirty;

    public StoredEnergy(long capacity, Runnable dirty) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity");
        this.capacity = () -> capacity;
        this.dirty = dirty;
    }
    public StoredEnergy(LongSupplier capacity, Runnable dirty) {
        this.capacity = capacity;
        this.dirty = dirty;
    }

    /** Load persisted energy without truncating it to a newly-lowered configured capacity.
     * Over-capacity stores cannot receive more energy and drain normally until they fall
     * back under the current limit. This preserves the pre-existing R4/R9 contract. */
    public void load(long value) { energy = Math.max(0L, value); }
    public long receive(long amount, boolean simulate) {
        long room = Math.max(0L, capacity() - energy);
        long n = Math.max(0L, Math.min(amount, room));
        if (!simulate && n > 0) { energy += n; dirty.run(); }
        return n;
    }
    public long extract(long amount, boolean simulate) {
        long n = Math.max(0L, Math.min(amount, energy));
        if (!simulate && n > 0) { energy -= n; dirty.run(); }
        return n;
    }
    public long stored() { return energy; }
    public long capacity() { return Math.max(1L, capacity.getAsLong()); }
    public boolean canExtract() { return true; }
    public boolean canReceive() { return true; }

    @Override public int receiveEnergy(int amount, boolean simulate) {
        return (int)Math.min(Integer.MAX_VALUE, receive(Math.max(0, amount), simulate));
    }
    @Override public int extractEnergy(int amount, boolean simulate) {
        return (int)Math.min(Integer.MAX_VALUE, extract(Math.max(0, amount), simulate));
    }
    @Override public int getEnergyStored() { return (int)Math.min(Integer.MAX_VALUE, stored()); }
    @Override public int getMaxEnergyStored() { return (int)Math.min(Integer.MAX_VALUE, capacity()); }
}
