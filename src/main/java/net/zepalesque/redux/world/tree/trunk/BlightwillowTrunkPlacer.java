package net.zepalesque.redux.world.tree.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class BlightwillowTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<BlightwillowTrunkPlacer> CODEC =
            IntProvider.codec(5, Integer.MAX_VALUE).fieldOf("height").xmap(BlightwillowTrunkPlacer::new, placer -> placer.height).stable();

    protected final IntProvider height;
    public BlightwillowTrunkPlacer(IntProvider height) {
        super(0, 0, 0);
        this.height = height;
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ReduxTrunkPlacers.BLIGHTWILLOW_TRUNK.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, int height, BlockPos origin, TreeConfiguration config) {
        setDirtAt(level, setter, random, origin.below(), config);

        for(int i = 0; i < height; i++) {
            this.placeLog(level, setter, random, origin.above(i), config);
        }

        BlockPos top = origin.above(height - 1);

        // Branches
        for (Direction d : Direction.Plane.HORIZONTAL) {
            // Lower branch
            BlockPos lower = top.below(4).relative(d);
            this.placeLog(level, setter, random, lower, config, state -> state.trySetValue(RotatedPillarBlock.AXIS, d.getAxis()));

            // Upper branch
            BlockPos upper = top.below(2);
            for (int i = 1; i < 3; i++) {
                BlockPos pos = upper.relative(d, i);
                this.placeLog(level, setter, random, pos, config, state -> state.trySetValue(RotatedPillarBlock.AXIS, d.getAxis()));
            }
            BlockPos upper2 = top.below(1).relative(d, 3);
            this.placeLog(level, setter, random, upper2, config);
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(height), 0, false));
    }

    @Override
    public int getTreeHeight(RandomSource random) {
        return this.height.sample(random);
    }
}
