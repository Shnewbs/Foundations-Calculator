package com.foundations.calculator.content;
import net.minecraft.core.BlockPos;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
public final class CalculatorSapling extends SaplingBlock {
    private final boolean diamond;
    public CalculatorSapling(boolean diamond,TreeGrower grower,Properties p){super(grower,p);this.diamond=diamond;}
    protected boolean mayPlaceOn(BlockState state,BlockGetter level,BlockPos pos){return diamond&&CalculatorConfig.flag("plants.diamondSaplingRequiresSupport",true)?state.is(Content.block("end_diamond_block")):super.mayPlaceOn(state,level,pos);}
    public void advanceTree(ServerLevel level,BlockPos pos,BlockState state,RandomSource random){
        if(!diamond||state.getValue(STAGE)==0){super.advanceTree(level,pos,state,random);return;}
        BlockPos soil=pos.below();BlockState original=level.getBlockState(soil);if(CalculatorConfig.flag("plants.diamondSaplingRequiresSupport",true)&&!original.is(Content.block("end_diamond_block")))return;
        level.setBlock(soil,Blocks.DIRT.defaultBlockState(),2);super.advanceTree(level,pos,state,random);
        level.setBlockAndUpdate(soil,level.getBlockState(pos).is(this)||!CalculatorConfig.flag("plants.diamondSaplingConsumesSupport",true)?original:Blocks.GRASS_BLOCK.defaultBlockState());
    }
}
