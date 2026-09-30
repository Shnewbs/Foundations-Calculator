package net.neoforged.neoforge.energy;
/** TEST FIXTURE ONLY. Never included in a Gradle source set or a distributable JAR. */
public interface IEnergyStorage {
    int receiveEnergy(int maximum,boolean simulate); int extractEnergy(int maximum,boolean simulate);
    int getEnergyStored(); int getMaxEnergyStored(); boolean canExtract(); boolean canReceive();
}
