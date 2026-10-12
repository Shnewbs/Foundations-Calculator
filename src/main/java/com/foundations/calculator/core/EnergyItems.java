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
        return (int)Math.min(Integer.MAX_VALUE,discharge(inventory,slot,destination,(long)limit));
    }
    public static long discharge(ItemStackHandler inventory,int slot,StoredEnergy destination,long limit) {
        ItemStack stack=inventory.getStackInSlot(slot);
        if(stack.getItem() instanceof com.foundations.calculator.content.CalculatorItem && !PowerPolicy.FE.input(PowerPolicy.Scope.ITEM))return 0;
        var battery=com.foundations.calculator.api.FoundationsEnergy.longItem(stack);
        if(battery!=null && battery.canExtract()) {
            long available=battery.extract(Math.max(0L,limit),true);
            long accepted=destination.receive(available,true);
            long extracted=battery.extract(accepted,false);
            return destination.receive(extracted,false);
        }
        int value=fuelValue(stack);
        // A consumable is indivisible: keep it until its entire FE value fits.
        if(value<=0 || destination.receiveEnergy(value,true)!=value) return 0;
        if(inventory.extractItem(slot,1,false).isEmpty()) return 0;
        return destination.receiveEnergy(value,false);
    }
}
