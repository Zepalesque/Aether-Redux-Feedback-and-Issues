package net.zepalesque.redux.block.natural.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ShadedLeavesBlock extends FallingLeavesBlock {
    public ShadedLeavesBlock(Supplier<? extends ParticleOptions> particle, Properties properties) {
        super(particle, properties);
    }

    @Override
    protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return 7;
    }
}
