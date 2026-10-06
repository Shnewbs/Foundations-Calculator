package com.foundations.calculator.core;

import com.foundations.calculator.content.Content;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

public final class CircuitData {
    public static CompoundTag tag(ItemStack s) { return s.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag(); }
    public static boolean analysed(ItemStack s) { return tag(s).getBooleanOr("Analysed",false); }
    public static boolean stable(ItemStack s) { return s.is(Content.item("soil"))||tag(s).getIntOr("Stable",0) == 1; }
    public static void initialize(ItemStack s, RandomSource random) {
        CompoundTag t=tag(s);
        if(t.contains("Stable"))return;
        t.putInt("Stable",1+random.nextInt(CalculatorConfig.integer("circuits.stabilityRoll",6))); t.putInt("Energy",1+random.nextInt(CalculatorConfig.integer("circuits.energyRoll",200)));
        int[] limits={50,100,1000,2000,10000,20000};
        for(int i=0;i<limits.length;i++)t.putInt("Item"+(i+1),1+random.nextInt(CalculatorConfig.integer("circuits.item"+(i+1)+"Roll",limits[i])));
        s.set(DataComponents.CUSTOM_DATA,CustomData.of(t));
    }
    public static void markAnalysed(ItemStack s) {
        CompoundTag t=tag(s);t.putBoolean("Analysed",true);t.putInt("Stable",t.getIntOr("Stable",0)==1?1:0);t.remove("Energy");for(int i=1;i<=6;i++)t.remove("Item"+i);s.set(DataComponents.CUSTOM_DATA,CustomData.of(t));
    }
    private CircuitData() {}
}
