package com.foundations.calculator.content;

import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.CropBlock;
public class CalculatorCrop extends CropBlock {
    private final String seed;
    public CalculatorCrop(String seed,Properties p){super(p);this.seed=seed;}
    public int minimumTier(){return CalculatorConfig.integer(seed.equals("broccoli_seeds")?"plants.broccoliMinimumTier":seed.equals("prunae_seeds")?"plants.prunaeMinimumTier":"plants.fiddledewMinimumTier",seed.equals("broccoli_seeds")?0:seed.equals("prunae_seeds")?2:3);}
    protected ItemLike getBaseSeedId(){return Content.item(seed);}
}
