package net.zepalesque.redux.entity.projectile;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;

import javax.annotation.Nullable;

public class VeridiumDart extends AbstractDart {
    
    public VeridiumDart(EntityType<? extends VeridiumDart> type, Level level) {
        super(type, level);
        this.setBaseDamage(2.0D);
    }

    public VeridiumDart(Level level, LivingEntity shooter, ItemStack itemStack, @Nullable ItemStack firedFromWeapon) {
        super(ReduxEntities.INFUSED_VERIDIUM_DART.get(), level, shooter, itemStack, firedFromWeapon);
        this.setBaseDamage(2.0D);
    }

    public VeridiumDart(EntityType<? extends VeridiumDart> entityType, Level level, double x, double y, double z, ItemStack itemStack, @Nullable ItemStack firedFromWeapon) {
        super(entityType, x, y, z, level, itemStack, firedFromWeapon);
        this.setBaseDamage(2.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.GLOWING, 200, 0);
        target.addEffect(mobeffectinstance, this.getEffectSource());
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(ReduxItems.VERIDIUM_DART.get());
    }

    public static class Uninfused extends AbstractDart {

        public Uninfused(EntityType<? extends VeridiumDart.Uninfused> type, Level level) {
            super(type, level);
            this.setBaseDamage(2.0D);
        }

        public Uninfused(Level level, LivingEntity shooter, ItemStack itemStack, @Nullable ItemStack firedFromWeapon) {
            super(ReduxEntities.VERIDIUM_DART.get(), level, shooter, itemStack, firedFromWeapon);
            this.setBaseDamage(2.0D);
        }

        public Uninfused(EntityType<? extends VeridiumDart.Uninfused> entityType, Level level, double x, double y, double z, ItemStack itemStack, @Nullable ItemStack firedFromWeapon) {
            super(entityType, x, y, z, level, itemStack, firedFromWeapon);
            this.setBaseDamage(2.0D);
        }

        @Override
        protected ItemStack getDefaultPickupItem() {
            return new ItemStack(ReduxItems.VERIDIUM_DART.get());
        }
    }



}
