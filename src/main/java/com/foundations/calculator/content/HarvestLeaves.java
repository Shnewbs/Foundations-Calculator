package com.foundations.calculator.content;

import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.BlockHitResult;

public class HarvestLeaves extends LeavesBlock {
    public static final IntegerProperty AGE=BlockStateProperties.AGE_4;
    private final String fruit;
    public HarvestLeaves(String fruit,Properties p){super(p);this.fruit=fruit;registerDefaultState(defaultBlockState().setValue(AGE,0));}
    protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>b){super.createBlockStateDefinition(b);b.add(AGE);}
    protected boolean isRandomlyTicking(BlockState s){return true;}
    protected void randomTick(BlockState s,ServerLevel level,BlockPos p,RandomSource random){
        super.randomTick(s,level,p,random);
        if(level.getBlockState(p).is(this)&&s.getValue(AGE)<4&&random.nextInt(CalculatorConfig.integer("plants.leafGrowthChance",8))==0)level.setBlock(p,s.cycle(AGE),2);
    }
    protected InteractionResult useWithoutItem(BlockState s,Level level,BlockPos p,Player player,BlockHitResult hit){
        int age=s.getValue(AGE);if((!fruit.equals("pear")&&!fruit.equals("diamond"))||age<CalculatorConfig.integer("plants.leafMatureAge",2))return InteractionResult.PASS;
        if(!level.isClientSide()){
            String primary=fruit.equals("pear")?"pear":"weakened_diamond";
            String secondary=fruit.equals("pear")?"rotten_pear":"flawless_diamond";
            if(age<4)popResource(level,p,new ItemStack(Content.item(primary),CalculatorConfig.integer("nutrition.leafYield",1)));
            if(age>=3)popResource(level,p,new ItemStack(Content.item(secondary),CalculatorConfig.integer("nutrition.leafYield",1)));
            level.setBlock(p,s.setValue(AGE,CalculatorConfig.integer("plants.leafHarvestResetAge",0)),3);
        }return InteractionResult.sidedSuccess(level.isClientSide());
    }
}
