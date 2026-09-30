package com.foundations.calculator.content;

import java.util.Map;
import net.minecraft.core.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;

public class ConnectedDecorBlock extends HalfTransparentBlock {
    public static final Map<Direction,BooleanProperty> SIDES=Map.of(
        Direction.NORTH,BlockStateProperties.NORTH,Direction.SOUTH,BlockStateProperties.SOUTH,
        Direction.WEST,BlockStateProperties.WEST,Direction.EAST,BlockStateProperties.EAST,
        Direction.UP,BlockStateProperties.UP,Direction.DOWN,BlockStateProperties.DOWN);
    public ConnectedDecorBlock(Properties p){
        super(p);BlockState s=stateDefinition.any();for(var b:SIDES.values())s=s.setValue(b,false);registerDefaultState(s);
    }
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> b){for(var p:SIDES.values())b.add(p);}
    public BlockState getStateForPlacement(BlockPlaceContext c){
        BlockState s=defaultBlockState();for(var e:SIDES.entrySet())s=s.setValue(e.getValue(),c.getLevel().getBlockState(c.getClickedPos().relative(e.getKey())).is(this));return s;
    }
    protected BlockState updateShape(BlockState s,Direction d,BlockState neighbor,LevelAccessor level,BlockPos p,BlockPos np){return s.setValue(SIDES.get(d),neighbor.is(this));}
}
