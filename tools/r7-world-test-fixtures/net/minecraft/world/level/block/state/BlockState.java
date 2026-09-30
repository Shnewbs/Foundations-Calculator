package net.minecraft.world.level.block.state;
import net.minecraft.core.BlockPos; import net.minecraft.server.level.ServerLevel;
public record BlockState(String name,boolean hasBlockEntity,float hardness) { public static final BlockState AIR=new BlockState("air",false,0); public boolean isAir(){return name.equals("air");} public float getDestroySpeed(ServerLevel level,BlockPos pos){return hardness;} }
