package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slider.class)
public class SliderMixin extends MobMixin {
    @Inject(method = "getAmbientSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (((Slider) (Object) this).isAwake()) {
            cir.setReturnValue(null);
        }
    }
}
