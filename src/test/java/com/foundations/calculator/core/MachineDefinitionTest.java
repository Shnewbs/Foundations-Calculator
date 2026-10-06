package com.foundations.calculator.core;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class MachineDefinitionTest {
    @Test void specialProcessorsKeepUpgradeSupport() {
        for (String id : new String[]{"reinforced_furnace","analysing_chamber"}) {
            var d=MachineDefinition.forMachine(id);
            assertTrue(d.upgrades()); assertTrue(d.outputs()); assertTrue(d.batterySlot());
        }
    }
    @Test void powerCubeHasDedicatedSlowChargingAndNoProcessOutput() {
        var d=MachineDefinition.forMachine("power_cube");
        assertTrue(d.energyStorage()); assertFalse(d.generator()); assertFalse(d.outputs());
        assertEquals(400,d.transferRate()); assertEquals(4,d.chargeRate());
    }
    @Test void workstationRetainsBatteryWithoutEnergyPort() {
        var d=MachineDefinition.forMachine("module_workstation");
        assertFalse(d.usesEnergy()); assertTrue(d.batterySlot()); assertEquals(16,d.inputCount());
    }
    @Test void worldMachineCapacityMatchesStoredEnergyDefaults() {
        assertEquals(1500000000,MachineDefinition.forMachine("atomic_multiplier").capacity());
        assertEquals(350000,MachineDefinition.forMachine("advanced_greenhouse").capacity());
        assertEquals(1000,MachineDefinition.forMachine("hand_cranked_generator").capacity());
    }
    @Test void profilesAreImmutableAndReused() {
        assertSame(MachineDefinition.forMachine("analysing_chamber"),MachineDefinition.forMachine("analysing_chamber"));
        assertSame(MachineDefinition.forMachine("unknown_a"),MachineDefinition.forMachine("unknown_b"));
        assertEquals(3200,MachineDefinition.forMachine("unknown_a").transferRate());
    }
}
