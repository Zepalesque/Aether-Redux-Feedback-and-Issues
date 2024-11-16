package net.zepalesque.redux.event.hook;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.mixin.mixins.common.accessor.EntityAccessor;

public class QuicksoilHooks {

    public static boolean shouldAlterMovement(final Entity entity) {
        return entity.level().isLoaded(((EntityAccessor) entity).callGetBlockPosBelowThatAffectsMyMovement()) &&
                entity.onGround() &&
                !entity.isInWater() &&
                !(Math.abs(entity.getDeltaMovement().x + entity.getDeltaMovement().y + entity.getDeltaMovement().z) < 0.001D) &&
                !entity.isSpectator()
                && entity.level().getBlockState(((EntityAccessor) entity).callGetBlockPosBelowThatAffectsMyMovement()).is(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR);
    }

    public static void alterMovement(final Entity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.7D, 1D, 1.7D));
        final double maxMotion = 1D;
        double x;
        double z;

        x = Mth.clamp(entity.getDeltaMovement().x, -maxMotion, maxMotion);
        z = Mth.clamp(entity.getDeltaMovement().z, -maxMotion, maxMotion);
        entity.setDeltaMovement(x, entity.getDeltaMovement().y, z);
    }
}
