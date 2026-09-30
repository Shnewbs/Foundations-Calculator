package net.neoforged.neoforge.common.util;
import net.minecraft.server.level.ServerLevel;import net.minecraft.core.BlockPos;import net.minecraft.world.level.block.state.BlockState;
public record BlockSnapshot(ServerLevel level,BlockPos pos,BlockState state) { public static BlockSnapshot create(Object dimension,ServerLevel level,BlockPos pos){return new BlockSnapshot(level,pos,level.getBlockState(pos));}}
