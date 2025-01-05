package net.zepalesque.redux.mixin.mixins.common.entity.ai;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aether.entity.monster.dungeon.boss.goal.AvoidObstaclesGoal;
import com.aetherteam.aether.entity.monster.dungeon.boss.goal.BackOffAfterAttackGoal;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BackOffAfterAttackGoal.class)
public class SliderBackOffMixin {

    // Overrides the specific tick to perform this goal
    @WrapOperation(method = "canUse", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/monster/dungeon/boss/Slider;getMoveDelay()I"))
    protected int redux$canUse(Slider slider, Operation<Integer> original) {
        int old = original.call(slider), newVal;
        if (old == (slider.isCritical() ? 3 : 7)) newVal = 1;
        else newVal = 0;

        return newVal;
    }
}
