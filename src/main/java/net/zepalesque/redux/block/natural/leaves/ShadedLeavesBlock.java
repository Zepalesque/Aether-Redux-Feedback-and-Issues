package net.zepalesque.redux.block.natural.leaves;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class ShadedLeavesBlock extends FallingLeavesBlock {
    private final int lightBlock;

    public ShadedLeavesBlock(Supplier<? extends ParticleOptions> particle, int lightBlock, Properties properties) {
        super(particle, properties);
        this.lightBlock = lightBlock;
    }

    @Override
    protected int getLightBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return this.lightBlock;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return this.lightBlock < 15 && super.propagatesSkylightDown(state, level, pos);
    }
}
