package com.foundations.calculator.content;
import net.minecraft.core.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.*;
public final class NutritionPipeBlock extends ConnectedDecorBlock {
    private static final Direction[] DIRECTIONS=Direction.values();
    private static final VoxelShape[] SHAPES=createShapes();
    private static VoxelShape[] createShapes(){
        VoxelShape[] shapes=new VoxelShape[64];
        for(int mask=0;mask<shapes.length;mask++){
            VoxelShape shape=box(5,5,5,11,11,11);
            for(Direction d:DIRECTIONS)if((mask&(1<<d.get3DDataValue()))!=0)shape=Shapes.or(shape,switch(d){case UP->box(5,11,5,11,16,11);case DOWN->box(5,0,5,11,5,11);case EAST->box(11,5,5,16,11,11);case WEST->box(0,5,5,5,11,11);case NORTH->box(5,5,0,11,11,5);case SOUTH->box(5,5,11,11,11,16);});
            shapes[mask]=shape;
        }
        return shapes;
    }
    private final String kind;
    public NutritionPipeBlock(String kind,Properties p){super(p);this.kind=kind;}
    private boolean connects(BlockState s){return s.is(this)||s.is(Content.block(kind.startsWith("amethyst")?"hunger_processor":"health_processor"))||s.is(Content.block(kind.startsWith("amethyst")?"amethyst_log":"tanzanite_log"));}
    public BlockState getStateForPlacement(BlockPlaceContext c){BlockState s=defaultBlockState();for(var e:SIDES.entrySet())s=s.setValue(e.getValue(),connects(c.getLevel().getBlockState(c.getClickedPos().relative(e.getKey()))));return s;}
    protected BlockState updateShape(BlockState s,Direction d,BlockState n,LevelAccessor l,BlockPos p,BlockPos np){return s.setValue(SIDES.get(d),connects(n));}
    protected VoxelShape getShape(BlockState s,BlockGetter l,BlockPos p,CollisionContext c){
        int mask=0;for(Direction d:DIRECTIONS)if(s.getValue(SIDES.get(d)))mask|=1<<d.get3DDataValue();
        return SHAPES[mask];
    }
}
