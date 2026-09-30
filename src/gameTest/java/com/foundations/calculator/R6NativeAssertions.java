package com.foundations.calculator;

import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.foundations.calculator.api.FoundationsEnergy;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.gametest.framework.GameTestHelper;
import static com.foundations.calculator.R6GameTests.setting;

public final class R6NativeAssertions {
    public static void mekanism(GameTestHelper h) {
        var machine=R5GameTests.place(h);
        var port=h.getLevel().getCapability(mekanism.common.capabilities.Capabilities.STRICT_ENERGY.block(),machine.getBlockPos(),Direction.NORTH);
        setting("power.mekanism.joulesPerFE",5.0,()->setting("power.mekanism.inputLossPercent",10,()->{
            h.assertTrue(port.insertEnergy(100,mekanism.api.Action.SIMULATE)==0&&machine.energy.getEnergyStored()==0,"Custom J simulation");
            h.assertTrue(port.insertEnergy(100,mekanism.api.Action.EXECUTE)==0&&machine.energy.getEnergyStored()==18,"100 J at 5 J/FE less 10 percent = 18 FE");
            var stack=new ItemStack(Content.item("energy_module"));var item=stack.getCapability(mekanism.common.capabilities.Capabilities.STRICT_ENERGY.item());
            setting("power.mekanism.itemCharging",false,()->{
                h.assertTrue(item.insertEnergy(100,mekanism.api.Action.EXECUTE)==100,"Item scope disabled");
                h.assertTrue(port.insertEnergy(100,mekanism.api.Action.EXECUTE)==0,"Block scope remains independent");
            });
        }));
        var dual=net.minecraft.core.registries.BuiltInRegistries.ITEM.stream()
            .filter(item->net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item).getNamespace().equals("mekanism"))
            .map(ItemStack::new).filter(stack->stack.getCapability(mekanism.common.capabilities.Capabilities.STRICT_ENERGY.item())!=null
                &&stack.getCapability(net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage.ITEM)!=null).findFirst().orElseThrow();
        setting("power.routing.preferNative",true,()->h.assertTrue(FoundationsEnergy.itemRoute(dual).id().equals("mekanism"),"Native routing wins over facade"));
        setting("power.routing.preferNative",false,()->h.assertTrue(FoundationsEnergy.itemRoute(dual).id().equals("fe"),"Explicit FE-first preference"));
    }
    public static void mi(GameTestHelper h) {
        var machine=R5GameTests.place(h);var port=h.getLevel().getCapability(aztech.modern_industrialization.api.energy.EnergyApi.SIDED,machine.getBlockPos(),Direction.NORTH);
        setting("power.modernIndustrialization.fePerEU",8.0,()->setting("power.modernIndustrialization.inputLossPercent",10,()->setting("power.modernIndustrialization.outputLossPercent",20,()->{
            h.assertTrue(port.receive(50,false)==50&&machine.energy.getEnergyStored()==360,"Custom MI EU ratio and input loss");
            setting("power.modernIndustrialization.blockPorts",false,()->h.assertTrue(port.receive(50,false)==0&&port.extract(50,false)==0,"Cached MI scope gate"));
            h.assertTrue(port.extract(100,false)==36&&machine.energy.getEnergyStored()==0,"MI output includes loss without energy creation");
        })));
    }
    public static void gregtech(GameTestHelper h) {
        var machine=R5GameTests.place(h);var port=h.getLevel().getCapability(com.gregtechceu.gtceu.api.capability.GTCapability.CAPABILITY_ENERGY_CONTAINER,machine.getBlockPos(),Direction.NORTH);
        setting("compat.gtceu.fePerEU",4,()->setting("power.gtceu.inputLossPercent",10,()->{
            h.assertTrue(port.acceptEnergyFromNetwork(Direction.NORTH,32,1)==1&&machine.energy.getEnergyStored()==115,"GT credits floor(32*4*0.9) FE");
            setting("power.gtceu.blockPorts",false,()->h.assertTrue(port.acceptEnergyFromNetwork(Direction.NORTH,32,1)==0&&port.changeEnergy(-1)==0,"Cached GT block gate"));
            setting("power.gtceu.input",false,()->h.assertTrue(port.acceptEnergyFromNetwork(Direction.NORTH,32,1)==0,"GT input switch"));
            h.assertTrue(com.foundations.calculator.compat.GregTechEnergy.conversion().outputPacketFE(32,4)==128,"Lossless default output independent of input loss");
            setting("power.gtceu.outputLossPercent",10,()->h.assertTrue(com.foundations.calculator.compat.GregTechEnergy.conversion().outputPacketFE(32,4)==143,"GT output rounds cost upward"));
        }));
    }
    public static void grandpower(GameTestHelper h) {
        var machine=R5GameTests.place(h);var port=h.getLevel().getCapability(dev.technici4n.grandpower.api.ILongEnergyStorage.BLOCK,machine.getBlockPos(),Direction.NORTH);
        setting("power.grandPower.inputLossPercent",10,()->{
            h.assertTrue(port.receive(100,false)==100&&machine.energy.getEnergyStored()==90,"Optional long-FE input loss");
            setting("power.grandPower.output",false,()->h.assertTrue(port.extract(100,false)==0,"Cached long-FE output gate"));
            h.assertTrue(port.extract(100,false)==90&&machine.energy.getEnergyStored()==0,"No loss on unchanged output policy");
        });
    }
    private R6NativeAssertions(){}
}
