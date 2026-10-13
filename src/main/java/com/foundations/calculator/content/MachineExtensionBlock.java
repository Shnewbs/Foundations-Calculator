package com.foundations.calculator.content;
import net.minecraft.core.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
public final class MachineExtensionBlock extends Block {
    public static final IntegerProperty HEIGHT=IntegerProperty.create("height",1,3);
    public MachineExtensionBlock(Properties p){super(p);registerDefaultState(stateDefinition.any().setValue(HEIGHT,1));}
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> b){b.add(HEIGHT);}
    protected RenderShape getRenderShape(BlockState s){return RenderShape.INVISIBLE;}
    protected VoxelShape getShape(BlockState s,BlockGetter l,BlockPos p,CollisionContext c){return box(4,0,4,12,16,12);}
    @Override protected BlockState updateShape(BlockState s,LevelReader l,net.minecraft.world.level.ScheduledTickAccess ticks,BlockPos p,Direction d,BlockPos np,BlockState n,net.minecraft.util.RandomSource random){BlockState base=l.getBlockState(p.below(s.getValue(HEIGHT)));return base.getBlock() instanceof MachineBlock?s:Blocks.AIR.defaultBlockState();}
    @Override protected void affectNeighborsAfterRemoval(BlockState s,net.minecraft.server.level.ServerLevel l,BlockPos p,boolean moving){BlockPos root=p.below(s.getValue(HEIGHT));if(l.getBlockState(root).getBlock() instanceof MachineBlock)l.destroyBlock(root,true);super.affectNeighborsAfterRemoval(s,l,p,moving);}
    protected InteractionResult useWithoutItem(BlockState s,Level l,BlockPos p,Player player,BlockHitResult hit){BlockPos root=p.below(s.getValue(HEIGHT));return l.getBlockState(root).useWithoutItem(l,player,new BlockHitResult(hit.getLocation(),hit.getDirection(),root,hit.isInside()));}
}
