package com.foundations.calculator.content;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.effect.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
public final class ThrownMaterialEntity extends ThrowableItemProjectile {
    public ThrownMaterialEntity(EntityType<? extends ThrownMaterialEntity> type,Level level){super(type,level);}
    public ThrownMaterialEntity(Level level,LivingEntity owner,boolean soil){super(soil?Content.SOIL_PROJECTILE.get():Content.STONE_PROJECTILE.get(),owner,level);}
    protected Item getDefaultItem(){return Content.item(getType()==Content.SOIL_PROJECTILE.get()?"soil":"small_stone");}
    protected void onHit(HitResult hit){super.onHit(hit);if(!level().isClientSide()){if(hit instanceof EntityHitResult entity){if(getType()==Content.SOIL_PROJECTILE.get()){if(entity.getEntity() instanceof Player player)player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,100,1));}else entity.getEntity().hurt(damageSources().thrown(this,getOwner()),4);}discard();}}
}
