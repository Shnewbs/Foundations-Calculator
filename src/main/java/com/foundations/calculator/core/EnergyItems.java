package com.foundations.calculator.core;

import net.minecraft.world.item.*;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;

/** The consumable discharge values formerly supplied by Sonar Core. */
public final class EnergyItems {
    private EnergyItems() {}
    public static int fuelValue(ItemStack stack) {
        if (stack.is(Items.REDSTONE)) return CalculatorConfig.integer("fuel.redstone",1000);
        if (stack.is(Items.COAL)) return CalculatorConfig.integer("fuel.coal",500);
        if(stack.is(Items.CHARCOAL))return CalculatorConfig.integer("fuel.charcoal",500);
        if (stack.is(Items.COAL_BLOCK)) return CalculatorConfig.integer("fuel.coal_block",4500);
        if (stack.is(Items.REDSTONE_BLOCK)) return CalculatorConfig.integer("fuel.redstone_block",9000);
        return 0;
    }
    public static boolean canDischarge(ItemStack stack) {
        var energy=com.foundations.calculator.api.FoundationsEnergy.item(stack);
        return fuelValue(stack)>0 || energy!=null && energy.canExtract();
    }
    public static boolean canCharge(ItemStack stack) {
        var energy=com.foundations.calculator.api.FoundationsEnergy.item(stack);
        return energy!=null && energy.canReceive();
    }
    public static int discharge(ItemStackHandler inventory,int slot,StoredEnergy destination,int limit) {
        ItemStack stack=inventory.getStackInSlot(slot);
        var battery=com.foundations.calculator.api.FoundationsEnergy.item(stack);
        if(battery!=null && battery.canExtract()) {
            int available=battery.extractEnergy(Math.max(0,limit),true);
            int accepted=destination.receiveEnergy(available,true);
            int extracted=battery.extractEnergy(accepted,false);
            return destination.receiveEnergy(extracted,false);
        }
        int value=fuelValue(stack);
        // A consumable is indivisible: keep it until its entire FE value fits.
        if(value<=0 || destination.receiveEnergy(value,true)!=value) return 0;
        if(inventory.extractItem(slot,1,false).isEmpty()) return 0;
        return destination.receiveEnergy(value,false);
    }
}
