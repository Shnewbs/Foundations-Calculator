package com.foundations.calculator.content;
import net.minecraft.world.entity.*;
import com.foundations.calculator.core.CalculatorConfig;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
public final class GrenadeEntity extends ThrowableItemProjectile {
    public GrenadeEntity(EntityType<? extends GrenadeEntity> type,Level level){super(type,level);}
    public GrenadeEntity(Level level,LivingEntity owner,boolean baby){super(baby?Content.BABY_GRENADE.get():Content.GRENADE.get(),owner,level);}
    protected Item getDefaultItem(){return Content.item(getType()==Content.BABY_GRENADE.get()?"baby_grenade":"grenade");}
    protected void onHit(HitResult hit){super.onHit(hit);if(!level().isClientSide()){level().explode(this,getX(),getY(),getZ(),(float)(getType()==Content.BABY_GRENADE.get()?CalculatorConfig.decimal("world.babyGrenadeStrength",1):CalculatorConfig.decimal("world.grenadeStrength",5)),CalculatorConfig.flag("world.grenadeFire",true),CalculatorConfig.flag("world.grenadeBlockDamage",true)?Level.ExplosionInteraction.TNT:Level.ExplosionInteraction.NONE);discard();}}
}
