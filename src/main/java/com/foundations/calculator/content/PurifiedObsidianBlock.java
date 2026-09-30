package com.foundations.calculator.content;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
public final class PurifiedObsidianBlock extends ConnectedDecorBlock {
    public PurifiedObsidianBlock(Properties p){super(p);}
    public boolean canEntityDestroy(BlockState state,BlockGetter level,BlockPos pos,Entity entity){return false;}
}
