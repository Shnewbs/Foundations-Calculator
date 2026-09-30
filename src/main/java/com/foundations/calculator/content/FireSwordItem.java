package com.foundations.calculator.content;
import net.minecraft.world.item.*;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.world.entity.LivingEntity;
public final class FireSwordItem extends SwordItem {
    public FireSwordItem(Tier tier,Properties p){super(tier,p);}
    public boolean hurtEnemy(ItemStack stack,LivingEntity target,LivingEntity attacker){target.igniteForSeconds(CalculatorConfig.integer("tools.fireSwordSeconds",4));return super.hurtEnemy(stack,target,attacker);}
}
