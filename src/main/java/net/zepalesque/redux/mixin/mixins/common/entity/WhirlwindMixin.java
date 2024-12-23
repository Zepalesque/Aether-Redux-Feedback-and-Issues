package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.Stream;

@Mixin(AbstractWhirlwind.class)
public class WhirlwindMixin extends LivingEntityMixin {
    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/monster/AbstractWhirlwind;discard()V"), cancellable = true)
    protected void redux$dissipate(CallbackInfo ci) {
        ((AbstractWhirlwind) (Object) this).setHealth(0.0F);
        ci.cancel();
    }
    @Inject(method = "kill", at = @At(value = "HEAD"), cancellable = true)
    protected void redux$kill(CallbackInfo ci) {
        ((AbstractWhirlwind) (Object) this).setHealth(0.0F);
        ci.cancel();
    }

    @WrapOperation(method = "aiStep", at = @At(value = "INVOKE", target = "Ljava/util/stream/Stream;toList()Ljava/util/List;"))
    protected <T> List<T> redux$swirlMobs(Stream<T> stream, Operation<List<T>> original) {
        if (((AbstractWhirlwind) (Object) this).isDeadOrDying()) {
            return List.of();
        } else {
            return original.call(stream);
        }
    }

    @Override
    protected void redux$cullBox(CallbackInfoReturnable<AABB> cir) {
        AABB box = ((AbstractWhirlwind) (Object) this).getBoundingBox();
        AABB larger = new AABB(box.minX - 0.425F, box.minY, box.minZ - 0.425F, box.maxX + 0.425F, box.maxY + 3.325F, box.maxZ + 0.425F);
        cir.setReturnValue(larger);
    }

    @Override
    protected void redux$Poof(CallbackInfo ci) {
        ci.cancel();
    }

    @WrapWithCondition(method = "aiStep", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/monster/AbstractWhirlwind;spawnParticles()V"))
    protected boolean redux$spawnParticles(AbstractWhirlwind instance) {
        return !ReduxConfig.CLIENT.improved_whirlwinds.get() && !((AbstractWhirlwind) (Object) this).isDeadOrDying();
    }
}
