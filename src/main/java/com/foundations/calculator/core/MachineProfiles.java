package com.foundations.calculator.core;
/** Configuration-aware power rates backed by the shared machine definition defaults. */
public final class MachineProfiles {
    private MachineProfiles() {}
    public static boolean storage(String machine){return MachineDefinition.forMachine(machine).energyStorage();}
    public static boolean generator(String machine){return MachineDefinition.forMachine(machine).generator();}
    public static long transferLong(String machine){return CalculatorConfig.scaledTransferLong(unscaledTransfer(machine));}
    public static int transfer(String machine){return (int)Math.min(Integer.MAX_VALUE,transferLong(machine));}
    public static int charging(String machine){
        var definition=MachineDefinition.forMachine(machine);
        int fallback=MachineDefinition.hasDedicatedChargeRate(machine)?definition.chargeRate():unscaledTransfer(machine);
        return CalculatorConfig.scaledTransfer(CalculatorConfig.machine(machine,"chargeRate",fallback));
    }
    private static int unscaledTransfer(String machine){
        int override=CalculatorConfig.TRANSFER_RATE.get();
        return override>0?override:CalculatorConfig.machine(machine,"transferRate",MachineDefinition.forMachine(machine).transferRate());
    }
}
