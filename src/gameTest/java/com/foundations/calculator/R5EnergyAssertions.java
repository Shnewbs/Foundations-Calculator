package com.foundations.calculator;

import com.foundations.calculator.content.*;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;

public final class R5EnergyAssertions {
    public static void mekanism(GameTestHelper h){
        var m=R5GameTests.place(h);var port=h.getLevel().getCapability(mekanism.common.capabilities.Capabilities.STRICT_ENERGY.block(),m.getBlockPos(),Direction.NORTH);
        h.assertTrue(port!=null,"Foundations must advertise a native Mekanism port");
        h.assertTrue(port.insertEnergy(250,mekanism.api.Action.SIMULATE)==0&&m.energy.getEnergyStored()==0,"Mekanism native simulation is read-only");
        h.assertTrue(port.insertEnergy(250,mekanism.api.Action.EXECUTE)==0&&m.energy.getEnergyStored()==100,"Native J insertion charges the same FE storage");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),3);
        h.assertTrue(port.insertEnergy(250,mekanism.api.Action.EXECUTE)==250&&port.extractEnergy(250,mekanism.api.Action.EXECUTE)==0,"Cached Mekanism ports observe disabled faces");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),2);
        h.assertTrue(port.extractEnergy(250,mekanism.api.Action.EXECUTE)==250&&m.energy.getEnergyStored()==0,"Native J extraction conserves power");
        var stack=new ItemStack(Content.item("energy_module"));var item=stack.getCapability(mekanism.common.capabilities.Capabilities.STRICT_ENERGY.item());
        h.assertTrue(item!=null&&item.insertEnergy(250,mekanism.api.Action.EXECUTE)==0&&stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==100,"Mekanism chargers see Foundations powered items");
    }
    public static void mi(GameTestHelper h){
        var m=R5GameTests.place(h);var port=h.getLevel().getCapability(aztech.modern_industrialization.api.energy.EnergyApi.SIDED,m.getBlockPos(),Direction.NORTH);
        int ratio=aztech.modern_industrialization.config.MIServerConfig.INSTANCE.forgeEnergyPerEu.get();
        h.assertTrue(port!=null&&port.receive(8,true)==8&&m.energy.getEnergyStored()==0,"MI EU capability and simulation");
        h.assertTrue(port.receive(8,false)==8&&m.energy.getEnergyStored()==8*ratio,"MI ratio charges FE exactly");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),3);h.assertTrue(port.receive(1,false)==0&&port.extract(1,false)==0,"MI cached face disables immediately");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),2);h.assertTrue(port.extract(8,false)==8&&m.energy.getEnergyStored()==0,"MI EU round trip");
        var stack=new ItemStack(Content.item("energy_module"));var item=stack.getCapability(aztech.modern_industrialization.api.energy.EnergyApi.ITEM);h.assertTrue(item!=null&&item.receive(1,false)==1&&stack.getCapability(Capabilities.EnergyStorage.ITEM).getEnergyStored()==ratio,"MI charges Foundations items");
    }
    public static void grandpower(GameTestHelper h){
        var m=R5GameTests.place(h);var port=h.getLevel().getCapability(dev.technici4n.grandpower.api.ILongEnergyStorage.BLOCK,m.getBlockPos(),Direction.NORTH);
        h.assertTrue(port!=null&&port.receive(Long.MAX_VALUE,true)==400&&m.energy.getEnergyStored()==0,"GrandPower long request respects port limit without overflow");
        h.assertTrue(port.receive(Long.MAX_VALUE,false)==400&&m.energy.getEnergyStored()==400,"GrandPower shares exact FE storage");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),3);h.assertTrue(port.receive(Long.MAX_VALUE,false)==0&&port.extract(Long.MAX_VALUE,false)==0,"GrandPower obeys cached side gates");
    }
}
