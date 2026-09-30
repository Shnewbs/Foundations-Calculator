package com.foundations.calculator.core;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
final class PowerCoreTest {
    @Test void exactConversionsGatesPacketsAndRoundTrips() { assertTrue(PowerCoreAssertions.run()>90000); }
}
