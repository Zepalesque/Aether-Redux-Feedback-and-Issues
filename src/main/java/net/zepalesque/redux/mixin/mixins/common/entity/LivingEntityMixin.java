package net.zepalesque.redux.mixin.mixins.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {

    @Inject(method = "makePoofParticles", at = @At("HEAD"), cancellable = true)
    protected void redux$poof(CallbackInfo ci) {}

    @Inject(method = "isPushable", at = @At("HEAD"), cancellable = true)
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {}

    @Inject(method = "doPush", at = @At("HEAD"), cancellable = true)
    protected void redux$doPush(Entity entity, CallbackInfo ci) {}

    @Inject(method = "pushEntities", at = @At("HEAD"), cancellable = true)
    protected void redux$pushEntities(CallbackInfo ci) {}

    @Inject(method = "getBoundingBoxForCulling", at = @At("HEAD"), cancellable = true)
    protected void redux$cullBox(CallbackInfoReturnable<AABB> cir) {}
}
