package com.foundations.calculator.content;

import com.foundations.calculator.core.CalculatorConfig;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;
import javax.annotation.Nullable;

public class MachineBlock extends BaseEntityBlock {
    public static final java.util.Set<String> MODELED=java.util.Set.of("reinforced_chest","extraction_chamber","precision_chamber","reassembly_chamber","restoration_chamber","processing_chamber","conductor_mast","transmitter","scarecrow","docking_station","crank_handle","weather_station","magnetic_flux");
    public static final MapCodec<MachineBlock> CODEC = simpleCodec(MachineBlock::new);
    public static final BooleanProperty ACTIVE=BlockStateProperties.POWERED;
    public static final DirectionProperty FACING=BlockStateProperties.HORIZONTAL_FACING;
    public MachineBlock(Properties p) { super(p);registerDefaultState(stateDefinition.any().setValue(FACING,net.minecraft.core.Direction.NORTH).setValue(ACTIVE,false)); }
    protected MapCodec<? extends BaseEntityBlock> codec(){return CODEC;}
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> b){b.add(FACING,ACTIVE);}
    public BlockState getStateForPlacement(BlockPlaceContext c){for(int i=1;i<height();i++)if(!c.getLevel().getBlockState(c.getClickedPos().above(i)).canBeReplaced()||c.getClickedPos().getY()+i>=c.getLevel().getMaxBuildHeight())return null;return defaultBlockState().setValue(FACING,c.getHorizontalDirection().getOpposite());}
    protected BlockState rotate(BlockState s,Rotation r){return s.setValue(FACING,r.rotate(s.getValue(FACING)));}
    protected BlockState mirror(BlockState s,Mirror m){return s.rotate(m.getRotation(s.getValue(FACING)));}
    protected boolean isSignalSource(BlockState state){return true;}
    protected int getSignal(BlockState state,BlockGetter level,BlockPos pos,net.minecraft.core.Direction side){return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(this).getPath().equals("rain_sensor")&&state.getValue(ACTIVE)?15:0;}
    protected int getDirectSignal(BlockState state,BlockGetter level,BlockPos pos,net.minecraft.core.Direction side){return getSignal(state,level,pos,side);}
    public String kind(){return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(this).getPath();}
    public int height(){return kind().equals("conductor_mast")?4:kind().equals("scarecrow")?3:1;}
    public void setPlacedBy(Level l,BlockPos p,BlockState s,net.minecraft.world.entity.LivingEntity placer,net.minecraft.world.item.ItemStack stack){super.setPlacedBy(l,p,s,placer,stack);if(!l.isClientSide()&&placer instanceof Player player&&l.getBlockEntity(p) instanceof MachineBlockEntity machine){com.foundations.calculator.core.UuidTags.put(machine.program,"Owner",player.getUUID());machine.setChanged();}if(!l.isClientSide())for(int i=1;i<height();i++)l.setBlockAndUpdate(p.above(i),Content.EXTENSION.get().defaultBlockState().setValue(MachineExtensionBlock.HEIGHT,i));}
    protected RenderShape getRenderShape(BlockState s){return MODELED.contains(kind())?RenderShape.ENTITYBLOCK_ANIMATED:RenderShape.MODEL;}
    public BlockEntity newBlockEntity(BlockPos p,BlockState s){return new MachineBlockEntity(p,s);}
    @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level,BlockState s,BlockEntityType<T> type){
        return level.isClientSide()?createTickerHelper(type,Content.MACHINE_ENTITY.get(),MachineBlockEntity::clientTick):createTickerHelper(type,Content.MACHINE_ENTITY.get(),MachineBlockEntity::tick);
    }
    protected net.minecraft.world.ItemInteractionResult useItemOn(net.minecraft.world.item.ItemStack stack,BlockState state,Level level,BlockPos pos,Player player,net.minecraft.world.InteractionHand hand,BlockHitResult hit){
        if(stack.is(Content.item("wrench"))){var result=WrenchActions.use(new net.minecraft.world.item.context.UseOnContext(player,hand,hit));return result==InteractionResult.FAIL?net.minecraft.world.ItemInteractionResult.FAIL:net.minecraft.world.ItemInteractionResult.sidedSuccess(level.isClientSide());}
        return super.useItemOn(stack,state,level,pos,player,hand,hit);
    }
    protected InteractionResult useWithoutItem(BlockState state,Level level,BlockPos pos,Player player,BlockHitResult hit){
        if(level.getBlockEntity(pos) instanceof MachineBlockEntity machine){
            if(!level.isClientSide()){
                if(!level.mayInteract(player,pos)||!player.mayUseItemAt(pos,hit.getDirection(),player.getMainHandItem()))return InteractionResult.FAIL;
                if(!com.foundations.calculator.core.UuidTags.has(machine.program,"Owner")){com.foundations.calculator.core.UuidTags.put(machine.program,"Owner",player.getUUID());machine.setChanged();}
                if(machine.kind().equals("crank_handle")){
                    if(level.getBlockEntity(pos.below()) instanceof MachineBlockEntity generator&&generator.kind().equals("hand_cranked_generator")&&(machine.lastCrankTick==Long.MIN_VALUE||level.getGameTime()-machine.lastCrankTick>=CalculatorConfig.integer("generation.crankHandleCooldown",18))){generator.energy.receiveEnergy(CalculatorConfig.CRANK_ENERGY.get(),false);machine.lastCrankTick=level.getGameTime();}
                }else if(machine.kind().equals("hand_cranked_generator") && !player.isShiftKeyDown()){
                    if(machine.lastCrankTick!=level.getGameTime()){
                        machine.energy.receiveEnergy(CalculatorConfig.CRANK_ENERGY.get(),false);machine.lastCrankTick=level.getGameTime();
                    }
                    player.displayClientMessage(net.minecraft.network.chat.Component.literal(String.format("%,d",machine.energy.stored())+" FE — sneak-use to open"),true);
                }else if(java.util.Set.of("transmitter","scarecrow","weather_station","rain_sensor").contains(machine.kind())){
                    String message=switch(machine.kind()){case "transmitter"->"Speeds up conductor masts within 20 blocks.";case "scarecrow"->"Grows nearby plants every "+CalculatorConfig.SCARECROW_INTERVAL.get()+" ticks.";case "rain_sensor"->level.isRaining()?"Rain detected · redstone 15":"Dry · redstone 0";default->machine.program.contains("Mast")?"Linked to mast at "+net.minecraft.core.BlockPos.of(machine.program.getLongOr("Mast",0L)).toShortString():"No conductor mast within 10 blocks.";};
                    player.displayClientMessage(net.minecraft.network.chat.Component.literal(message),true);
                }else ((ServerPlayer)player).openMenu(machine,b->{b.writeBoolean(true);b.writeBlockPos(pos);});
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }return InteractionResult.PASS;
    }
    protected void onRemove(BlockState old,Level level,BlockPos pos,BlockState state,boolean moving){
        if(!old.is(state.getBlock())){if(level.getBlockEntity(pos) instanceof MachineBlockEntity m)m.dropContents();for(int i=1;i<height();i++){var extension=level.getBlockState(pos.above(i));if(extension.is(Content.EXTENSION.get())&&extension.getValue(MachineExtensionBlock.HEIGHT)==i)level.removeBlock(pos.above(i),false);}}
        super.onRemove(old,level,pos,state,moving);
    }
}
