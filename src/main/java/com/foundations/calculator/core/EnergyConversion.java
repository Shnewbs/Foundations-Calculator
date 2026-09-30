package com.foundations.calculator.core;

/** One base exchange rate; independent, bounded losses can only decrease a round trip. */
public record EnergyConversion(EnergyRatio base, EnergyRatio input, EnergyRatio output,
                               int inputLossPercent, int outputLossPercent) {
    public EnergyConversion {
        java.util.Objects.requireNonNull(base);
        java.util.Objects.requireNonNull(input);
        java.util.Objects.requireNonNull(output);
        if (inputLossPercent < 0 || inputLossPercent > 99 || outputLossPercent < 0 || outputLossPercent > 99)
            throw new IllegalArgumentException("Loss must be between 0 and 99 percent");
    }
    public static EnergyConversion of(EnergyRatio base, int inputLoss, int outputLoss) {
        if (inputLoss < 0 || inputLoss > 99 || outputLoss < 0 || outputLoss > 99)
            throw new IllegalArgumentException("Loss must be between 0 and 99 percent");
        return new EnergyConversion(base,
                inputLoss == 0 ? base : base.scaled(100, 100 - inputLoss),
                outputLoss == 0 ? base : base.scaled(100 - outputLoss, 100), inputLoss, outputLoss);
    }
    /** GT is packet-based. Round the credited FE down for each whole incoming packet. */
    public int inputPacketFE(long voltage, int fePerEU) {
        if (voltage <= 0 || fePerEU <= 0 || voltage > Integer.MAX_VALUE / (long) fePerEU) return 0;
        return (int) (voltage * fePerEU * (100L - inputLossPercent) / 100L);
    }
    /** Whole outgoing EU packets cost rounded-up FE; zero signals an unrepresentable packet. */
    public int outputPacketFE(long voltage, int fePerEU) {
        if (voltage <= 0 || fePerEU <= 0 || voltage > Integer.MAX_VALUE / (long) fePerEU) return 0;
        long numerator = voltage * fePerEU * 100L;
        long efficiency = 100L - outputLossPercent;
        long cost = (numerator + efficiency - 1) / efficiency;
        return cost > Integer.MAX_VALUE ? 0 : (int) cost;
    }
}
