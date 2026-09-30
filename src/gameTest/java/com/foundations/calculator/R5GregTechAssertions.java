package com.foundations.calculator;

import com.foundations.calculator.compat.GregTechEnergy;
import com.foundations.calculator.content.*;
import com.foundations.calculator.core.*;
import com.gregtechceu.gtceu.api.capability.*;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;

public final class R5GregTechAssertions {
    public static void packets(GameTestHelper h){
        h.setBlock(new net.minecraft.core.BlockPos(3,2,3),Content.block("advanced_power_cube"));var m=(MachineBlockEntity)h.getBlockEntity(new net.minecraft.core.BlockPos(3,2,3));var level=h.getLevel();var port=level.getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER,m.getBlockPos(),Direction.NORTH);int ratio=GregTechEnergy.ratio();
        h.assertTrue(port!=null&&port.inputsEnergy(Direction.NORTH),"GT cables must see Foundations EU inputs");
        h.assertTrue(port.acceptEnergyFromNetwork(Direction.SOUTH,32,1)==0,"A cached port cannot spoof another face");
        h.assertTrue(port.acceptEnergyFromNetwork(Direction.NORTH,Long.MAX_VALUE,Long.MAX_VALUE)==0&&m.energy.getEnergyStored()==0,"Oversized packet cannot overflow or explode");
        long accepted=port.acceptEnergyFromNetwork(Direction.NORTH,32,100);h.assertTrue(accepted==4&&m.energy.getEnergyStored()==128*ratio,"Exactly four configured amps are accepted");
        var east=level.getCapability(GTCapability.CAPABILITY_ENERGY_CONTAINER,m.getBlockPos(),Direction.EAST);h.assertTrue(east.acceptEnergyFromNetwork(Direction.EAST,32,1)==0,"Requerying or changing face cannot bypass per-tick amp budget");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),3);h.assertTrue(!port.inputsEnergy(Direction.NORTH)&&port.changeEnergy(5)==0&&port.changeEnergy(-5)==0,"Disabled cached EU port cannot mutate storage");
        m.program.putInt("Side"+Direction.NORTH.get3DDataValue(),2);h.assertTrue(port.changeEnergy(-1)==-1&&m.energy.getEnergyStored()==127*ratio,"Output EU drains its exact FE equivalent");
    }
    public static void cable(GameTestHelper h){
        var source=R5GameTests.place(h);var sourcePos=source.getBlockPos();var cablePos=sourcePos.east();var targetPos=cablePos.east();
        var cableBlock=net.minecraft.core.registries.BuiltInRegistries.BLOCK.stream().filter(b->{var id=net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(b);return id.getNamespace().equals("gtceu")&&id.getPath().equals("tin_single_cable");}).findFirst().orElseThrow();
        h.getLevel().setBlockAndUpdate(targetPos,Content.block("processing_chamber").defaultBlockState());
        h.getLevel().setBlockAndUpdate(cablePos,cableBlock.defaultBlockState());
        var cable=(com.gregtechceu.gtceu.common.blockentity.CableBlockEntity)h.getLevel().getBlockEntity(cablePos);
        cable.setConnection(Direction.WEST,true,false);cable.setConnection(Direction.EAST,true,false);
        for(var face:Direction.values())source.program.putInt("Side"+face.get3DDataValue(),face==Direction.EAST?2:3);
        h.runAtTickTime(25,()->{
            var target=(MachineBlockEntity)h.getLevel().getBlockEntity(targetPos);source.energy.load(32*GregTechEnergy.ratio());
            MachineBlockEntity.tick(h.getLevel(),sourcePos,source.getBlockState(),source);
            int expected=(32-cable.getNodeData().getLossPerBlock())*GregTechEnergy.ratio();
            h.assertTrue(source.energy.getEnergyStored()==0&&target.energy.getEnergyStored()==expected,"Real GT cable consumes one source packet, applies cable loss, then charges native Foundations input. Got source="+source.energy.getEnergyStored()+", target="+target.energy.getEnergyStored()+", expected="+expected);
            h.succeed();
        });
    }
    public static void items(GameTestHelper h){
        var stack=new ItemStack(Content.item("energy_module"));var power=stack.getCapability(GTCapability.CAPABILITY_ELECTRIC_ITEM);var fe=((CalculatorItem)stack.getItem()).storage(stack);int ratio=GregTechEnergy.ratio();
        h.assertTrue(power!=null&&power.charge(32,1,false,true)==32&&fe.getEnergyStored()==0,"GT item capability simulation");
        h.assertTrue(power.charge(32,0,false,false)==0,"Under-tier GT charger must be refused");
        h.assertTrue(power.charge(100,1,false,false)==32&&fe.getEnergyStored()==32*ratio,"GT item respects tier transfer limit");
        power.setDischargeMode(true);h.assertTrue(stack.copy().getCapability(GTCapability.CAPABILITY_ELECTRIC_ITEM).isDischargeMode(),"Discharge mode survives a stack copy");
        h.assertTrue(power.discharge(32,1,false,true,false)==32&&fe.getEnergyStored()==0,"GT electric-item round trip conserves energy");
        var battery=net.minecraft.core.registries.BuiltInRegistries.ITEM.stream().filter(item->{var key=net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(item);return key.getNamespace().equals("gtceu")&&key.getPath().contains("battery")&&key.getPath().contains("lv");}).map(ItemStack::new).filter(s->s.getCapability(GTCapability.CAPABILITY_ELECTRIC_ITEM)!=null).findFirst().orElseThrow();
        var nativeBattery=battery.getCapability(GTCapability.CAPABILITY_ELECTRIC_ITEM);var bridge=GregTechEnergy.wrap(nativeBattery);long before=nativeBattery.getCharge();int amount=bridge.receiveEnergy(128,false);h.assertTrue(amount>0&&nativeBattery.getCharge()-before==amount/ratio,"Foundations charges a real GregTech battery");h.assertTrue(bridge.extractEnergy(amount,false)==amount&&nativeBattery.getCharge()==before,"Real GT battery round trip");
    }
}
