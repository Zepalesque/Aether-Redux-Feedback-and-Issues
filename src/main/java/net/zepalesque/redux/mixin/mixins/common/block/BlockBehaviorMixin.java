package net.zepalesque.redux.mixin.mixins.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {

    // Maybe PR a neoforge event? idk
    @Inject(method = "isPathfindable", at = @At("HEAD"), cancellable = true)
    public void redux$pathFindable(BlockState state, PathComputationType pathComputationType, CallbackInfoReturnable<Boolean> cir) {}
}
