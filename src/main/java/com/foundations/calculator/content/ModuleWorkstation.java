package com.foundations.calculator.content;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

/** Installed modules have one owner: the calculator component. Workstation slots edit that owner. */
public final class ModuleWorkstation {
    private ModuleWorkstation() {}
    public static boolean isModule(ItemStack stack){String id=Content.path(stack);return stack.is(net.minecraft.world.item.Items.ENDER_PEARL)||java.util.Set.of("grenade","atomic_assembly","flawless_assembly").contains(id)||id.endsWith("_module")&&!id.equals("atomic_module")||id.equals("calculator")||id.equals("scientific_calculator")||id.equals("atomic_calculator")||id.equals("crafting_calculator")||id.equals("info_calculator");}
    public static void changed(MachineBlockEntity m,int slot){
        if(m.updatingModules)return;
        if(slot==20)load(m);else if(slot<16)flush(m);
    }
    public static void tick(MachineBlockEntity m){if(m.installedCalculator!=m.inventory.getStackInSlot(20))load(m);}
    private static void load(MachineBlockEntity m){
        m.updatingModules=true;
        try{m.installedCalculator=m.inventory.getStackInSlot(20);
            var contents=m.installedCalculator.getOrDefault(Content.MODULES.get(),ItemContainerContents.EMPTY);
            var items=NonNullList.withSize(16,ItemStack.EMPTY);contents.copyInto(items);
            for(int i=0;i<16;i++)m.inventory.setStackInSlot(i,items.get(i));
        }finally{m.updatingModules=false;}
    }
    public static void flush(MachineBlockEntity m){
        ItemStack calculator=m.inventory.getStackInSlot(20);if(calculator.isEmpty())return;
        var items=NonNullList.withSize(16,ItemStack.EMPTY);for(int i=0;i<16;i++)items.set(i,m.inventory.getStackInSlot(i));
        calculator.set(Content.MODULES.get(),ItemContainerContents.fromItems(items));m.setChanged();
    }
    public static ItemStack selected(ItemStack calculator){
        int slot=com.foundations.calculator.core.CircuitData.tag(calculator).getIntOr("SelectedModule",0)-1;
        if(slot<0||slot>=com.foundations.calculator.core.CalculatorConfig.integer("module.flawless_calculator.maxModules",16))return ItemStack.EMPTY;
        var items=NonNullList.withSize(16,ItemStack.EMPTY);calculator.getOrDefault(Content.MODULES.get(),ItemContainerContents.EMPTY).copyInto(items);return items.get(slot);
    }
    public static void saveSelected(ItemStack calculator,ItemStack module){
        int slot=com.foundations.calculator.core.CircuitData.tag(calculator).getIntOr("SelectedModule",0)-1;if(slot<0||slot>=16)return;
        var items=NonNullList.withSize(16,ItemStack.EMPTY);calculator.getOrDefault(Content.MODULES.get(),ItemContainerContents.EMPTY).copyInto(items);items.set(slot,module);calculator.set(Content.MODULES.get(),ItemContainerContents.fromItems(items));
    }
}
