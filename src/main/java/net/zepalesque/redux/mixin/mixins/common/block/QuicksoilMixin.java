package net.zepalesque.redux.mixin.mixins.common.block;

import com.aetherteam.aether.block.natural.QuicksoilBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(QuicksoilBlock.class)
public class QuicksoilMixin extends BlockBehaviorMixin {

    @Override
    public void redux$pathFindable(BlockState state, PathComputationType type, CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && type == PathComputationType.LAND) {
            cir.setReturnValue(false);
        }
    }


    @Inject(method = "getFriction", at = @At("HEAD"), cancellable = true)
    public void redux$getFriction(BlockState state, LevelReader level, BlockPos pos, Entity entity, CallbackInfoReturnable<Float> cir) {
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get()) {
            if (entity instanceof Boat) {
                cir.setReturnValue(0.8F);
                return;
            }
            cir.setReturnValue(0.6F);
        }
    }
}
