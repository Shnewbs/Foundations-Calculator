package com.foundations.calculator.api;

/**
 * Foundations' long-valued energy contract. Values are expressed in canonical FE.
 * NeoForge IEnergyStorage remains available as a bounded compatibility facade; native
 * long-valued integrations should prefer this interface so buffers can exceed 2^31-1 FE.
 */
public interface LongEnergyStorage {
    long receive(long amount, boolean simulate);
    long extract(long amount, boolean simulate);
    long stored();
    long capacity();
    boolean canReceive();
    boolean canExtract();
}
