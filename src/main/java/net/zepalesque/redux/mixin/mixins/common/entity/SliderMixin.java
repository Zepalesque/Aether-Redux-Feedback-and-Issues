package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slider.class)
public abstract class SliderMixin extends LivingEntityMixin {
    @Shadow public abstract boolean isCritical();

    @Shadow private int moveDelay;

    @Shadow private Direction moveDirection;

    @Inject(method = "getAmbientSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (((Slider) (Object) this).isAwake()) {
            cir.setReturnValue(null);
        }
    }

    @Inject(method = "calculateMoveDelay", at = @At("HEAD"), cancellable = true)
    protected void redux$calculateMoveDelay(CallbackInfoReturnable<Integer> cir) {
        int rand = this.getRandom().nextInt(7);
        int adjusted = (this.isCritical() ? 3 : 7) + rand;
        cir.setReturnValue(adjusted);
    }

    @Inject(method = "customServerAiStep", at = @At("HEAD"))
    protected void redux$customServerAiStep(CallbackInfo ci) {
        if (!this.isCritical() && this.moveDelay == 7 || this.isCritical() && this.moveDelay == 3) {
            SliderSignalAttachment.sendPacket((Slider) (Object) this);
        }
    }
}
