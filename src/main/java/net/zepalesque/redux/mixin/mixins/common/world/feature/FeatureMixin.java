package net.zepalesque.redux.mixin.mixins.common.world.feature;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Feature.class)
public abstract class FeatureMixin<FC extends FeatureConfiguration> {
    @Shadow protected abstract void setBlock(LevelWriter level, BlockPos pos, BlockState state);
}
