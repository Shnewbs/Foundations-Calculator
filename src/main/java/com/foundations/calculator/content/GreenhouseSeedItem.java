package com.foundations.calculator.content;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.network.chat.Component;
/** The higher-tier crops are planted by a greenhouse; broccoli can be planted normally. */
public final class GreenhouseSeedItem extends ItemNameBlockItem {
    public GreenhouseSeedItem(net.minecraft.world.level.block.Block block,Properties p){super(block,p);}
    public InteractionResult useOn(UseOnContext c){
        if(getBlock() instanceof CalculatorCrop crop&&crop.minimumTier()>0){if(c.getPlayer()!=null&&!c.getLevel().isClientSide)c.getPlayer().displayClientMessage(Component.literal("Plant this crop using a tier "+crop.minimumTier()+" greenhouse."),true);return InteractionResult.FAIL;}return super.useOn(c);
    }
}
