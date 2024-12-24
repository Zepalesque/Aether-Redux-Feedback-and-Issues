package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.client.particle.AetherParticleTypes;
import com.aetherteam.aether.entity.monster.EvilWhirlwind;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EvilWhirlwind.class)
public class EvilWhirlwindMixin extends AbstractWhirlwindMixin {

    @Inject(method = "spawnParticles", at = @At(value = "HEAD"), cancellable = true)
    protected void redux$spawnParticles(CallbackInfo ci) {
        if (ReduxConfig.CLIENT.improved_whirlwinds.get()) {
            EvilWhirlwind self = (EvilWhirlwind) (Object) this;
            if (self.getRandom().nextFloat() < 0.65 && self.tickCount > 10 && self.deathTime < 10) {
                final double minRadius = 4.5F / 16;
                final double maxRadius = 22.5F / 16;
                double rX = self.getRandom().nextDouble();
                double rY = self.getRandom().nextDouble();
                double rZ = self.getRandom().nextDouble();

                double radius = Mth.lerp(rY, minRadius, maxRadius);
                double height = Mth.lerp(rY, 0.5, 3.5);

                double x = self.getX() - Mth.lerp(rX, -radius, radius);
                double y = self.getY() + height;
                double z = self.getZ() + Mth.lerp(rZ, -radius, radius);

                self.level().addParticle(ReduxParticles.WHIRLWIND_LIGHTNING.get(), x, y, z, 0.0D, 0.0D, 0.0D);
            }
            ci.cancel();
        }
    }
}
