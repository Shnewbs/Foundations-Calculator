package com.foundations.calculator.core;

import net.neoforged.neoforge.energy.IEnergyStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/** Guards the real Gradle test compile AND runtime dependency; never part of the mod JAR. */
final class EnergyApiClasspathTest {
    @Test
    void usesTheResolvedNeoForgeEnergyInterface() {
        assertTrue(IEnergyStorage.class.isAssignableFrom(StoredEnergy.class));
        var resource = IEnergyStorage.class.getResource("IEnergyStorage.class");
        assertNotNull(resource, "NeoForge's FE interface must be present at test runtime");
        assertEquals("jar", resource.getProtocol(),
                "Resolve FE from the platform JAR, not a local offline fixture directory");
        assertNotNull(IEnergyStorage.class.getProtectionDomain().getCodeSource());
        assertNotEquals(StoredEnergy.class.getProtectionDomain().getCodeSource().getLocation(),
                IEnergyStorage.class.getProtectionDomain().getCodeSource().getLocation(),
                "The mod must not provide its own copy of the NeoForge interface");

        IEnergyStorage buffer = new StoredEnergy(100, () -> {});
        assertEquals(40, buffer.receiveEnergy(40, true));
        assertEquals(0, buffer.getEnergyStored(), "Simulation must be read-only");
        assertEquals(40, buffer.receiveEnergy(40, false));
        assertEquals(15, buffer.extractEnergy(15, false));
        assertEquals(25, buffer.getEnergyStored());
    }
}
