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
        int adjusted = this.isCritical() ? 5 + this.getRandom().nextInt(6) : 7 + this.getRandom().nextInt(9);
        cir.setReturnValue(adjusted);
    }


    @Inject(method = "setMoveDirection", at = @At("HEAD"))
    protected void redux$setMoveDirection(Direction moveDirection, CallbackInfo ci) {
        if (moveDirection != null) {
            SliderSignalAttachment signal = SliderSignalAttachment.get((Slider) (Object) this);
            signal.syncMoveDirection((Slider) (Object) this);
        }
    }

    @Inject(method = "customServerAiStep", at = @At("TAIL"))
    protected void redux$customServerAiStep(CallbackInfo ci) {
        SliderSignalAttachment signal = SliderSignalAttachment.get((Slider) (Object) this);
        if (!this.isCritical() && this.moveDelay == 6 || this.isCritical() && this.moveDelay == 4) {
            signal.doBeep((Slider) (Object) this);
        } else if (this.moveDelay == 2) {
            signal.syncMoveDirection((Slider) (Object) this);
        }
    }
}
