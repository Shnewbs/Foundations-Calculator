package com.foundations.calculator.api;

/** Internal bounded convenience facade; platform capabilities use their own transactional API. */
public interface EnergyPort {
    int receiveEnergy(int amount,boolean simulate);
    int extractEnergy(int amount,boolean simulate);
    int getEnergyStored();
    int getMaxEnergyStored();
    boolean canExtract();
    boolean canReceive();
}
