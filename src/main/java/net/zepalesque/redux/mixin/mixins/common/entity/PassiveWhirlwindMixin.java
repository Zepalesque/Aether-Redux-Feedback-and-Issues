package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.monster.EvilWhirlwind;
import com.aetherteam.aether.entity.monster.PassiveWhirlwind;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PassiveWhirlwind.class)
public abstract class PassiveWhirlwindMixin extends AbstractWhirlwindMixin {

    @Inject(method = "spawnParticles", at = @At(value = "HEAD"), cancellable = true)
    protected void redux$spawnParticles(CallbackInfo ci) {
        if (ReduxConfig.CLIENT.improved_whirlwinds.get()) ci.cancel();
    }
}
