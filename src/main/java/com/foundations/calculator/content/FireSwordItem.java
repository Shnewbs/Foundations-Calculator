package com.foundations.calculator.content;
import net.minecraft.world.item.*;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.world.entity.LivingEntity;
/** Sword behavior is supplied by the modern sword properties, including durability/attributes. */
public final class FireSwordItem extends Item {
    public FireSwordItem(Properties p){super(p);}
    @Override public void hurtEnemy(ItemStack stack,LivingEntity target,LivingEntity attacker){target.igniteForSeconds(CalculatorConfig.integer("tools.fireSwordSeconds",4));super.hurtEnemy(stack,target,attacker);}
}
