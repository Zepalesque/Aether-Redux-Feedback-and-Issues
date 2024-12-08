package net.zepalesque.redux.mixin.mixins.common.entity;

import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class MobMixin {

    @Inject(method = "playAmbientSound", at = @At("HEAD"), cancellable = true)
    protected void redux$playAmbientSound(CallbackInfo ci) {}

}
