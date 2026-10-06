package com.foundations.calculator.content;

import java.util.*;
import net.minecraft.core.*;

/** Immutable geometry only. Live block validity and permissions are never cached here. */
public final class GreenhouseGeometry {
    final BlockPos origin;
    final Direction forward;
    final int tier, length;
    public final List<GreenhouseBlueprint.BlockPlace> blueprint;
    public final List<BlockPos> plants, water;
    public final Set<Long> plantCoordinates;
    public GreenhouseGeometry(MachineBlockEntity machine) {
        origin=machine.getBlockPos().immutable();forward=machine.getBlockState().getValue(MachineBlock.FACING).getOpposite();
        tier=GreenhouseProgram.tier(machine);
        length=Math.clamp(machine.program.getIntOr("HouseSize",0),0,com.foundations.calculator.core.CalculatorConfig.integer("greenhouse.maxFlawlessLength",64));
        blueprint=tier<3?List.copyOf(new GreenhouseBlueprint(origin,forward,tier).blocks()):List.of();
        List<BlockPos> plants=new ArrayList<>(),water=new ArrayList<>();
        if(tier==3) {
            for(int front=1;front<=length;front++) {
                for(int side=1;side<=2;side++) plants.add(relative(side,front,0));
                water.add(relative(0,front,-1));water.add(relative(3,front,-1));
            }
        } else {
            int range=tier==1?1:3,front=tier==1?2:4;
            for(int x=-range;x<=range;x++)for(int z=-range;z<=range;z++) {
                if(tier==1&&x==0&&z==0||tier==2&&Math.abs(x)==3&&Math.abs(z)==3)continue;
                plants.add(relative(x,front+z,0));
            }
            if(tier==1) water.add(relative(0,2,-1));
            else for(int x:new int[]{-3,3})for(int z:new int[]{1,7})water.add(relative(x,z,-1));
        }
        this.plants=List.copyOf(plants);this.water=List.copyOf(water);
        Set<Long> positions=new HashSet<>();for(var pos:plants)positions.add(pos.asLong());
        plantCoordinates=Set.copyOf(positions);
    }
    private BlockPos relative(int side,int front,int up) {
        return origin.relative(forward,front).relative(forward.getClockWise(),side).above(up);
    }
    public boolean matches(MachineBlockEntity machine) {
        return origin.equals(machine.getBlockPos())&&forward==machine.getBlockState().getValue(MachineBlock.FACING).getOpposite()
            &&tier==GreenhouseProgram.tier(machine)&&length==Math.clamp(machine.program.getIntOr("HouseSize",0),0,
                com.foundations.calculator.core.CalculatorConfig.integer("greenhouse.maxFlawlessLength",64));
    }
}
