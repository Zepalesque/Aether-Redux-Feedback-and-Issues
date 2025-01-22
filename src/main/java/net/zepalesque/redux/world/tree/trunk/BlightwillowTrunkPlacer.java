package net.zepalesque.redux.world.tree.trunk;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.zepalesque.redux.ArrayUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

// TODO: move root stuff to custom root placer -- can return false on root placement method if overhangs go over a block down (USE A MAP, DO NOT IMMEDIATELY PLACE)
public class BlightwillowTrunkPlacer extends TrunkPlacer {


    public static final MapCodec<BlightwillowTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            IntProvider.codec(5, Integer.MAX_VALUE).fieldOf("height").forGetter(placer -> placer.height),
            BlockStateProvider.CODEC.optionalFieldOf("wood_block").forGetter(placer -> placer.wood)
            ).apply(builder, BlightwillowTrunkPlacer::new));

    protected final Optional<BlockStateProvider> wood;
    protected final IntProvider height;
    public BlightwillowTrunkPlacer(IntProvider height, Optional<BlockStateProvider> wood) {
        super(0, 0, 0);
        this.height = height;
        this.wood = wood;
    }

    private static final Direction[] HORIZONTAL_PLANE = Direction.Plane.HORIZONTAL.stream().toArray(Direction[]::new);
    private static final Direction[] HORIZONTAL_PLANE_SHUFFLE = HORIZONTAL_PLANE.clone();

    @Override
    protected TrunkPlacerType<?> type() {
        return ReduxTrunkPlacers.BLIGHTWILLOW_TRUNK.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> setter, RandomSource random, int height, BlockPos origin, TreeConfiguration config) {
        setDirtAt(level, setter, random, origin.below(), config);

        this.placeLog(level, setter, random, origin, config);

        for(int i = 1; i < height; i++) {
            this.placeLog(level, setter, random, origin.above(i), config);
        }

        BlockPos top = origin.above(height - 1);

        int baseRootHeight = Math.max(height - 11, 2);

        // Method to ensure there will be one of all 4 possible root heights for the tree
        ArrayUtil.shuffle(HORIZONTAL_PLANE_SHUFFLE, random);


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

            if (this.wood.isPresent()) {

                // Place side roots
                int rootSize = baseRootHeight + ArrayUtils.indexOf(HORIZONTAL_PLANE_SHUFFLE, d);

                BlockPos rootStart = origin.relative(d, 1);
                for (int i = 0; i < rootSize; i++) {
                    BlockPos pos = rootStart.above(i);
                    if (i < rootSize - 1) {
                        this.placeLog(level, setter, random, pos, config);
                    } else if (validTreePos(level, pos)) {
                        setter.accept(pos, this.wood.get().getState(random, pos));
                    }
                }
            }


        }

        // Reset shuffling array to ensure consistency
        ArrayUtil.copyFrom(HORIZONTAL_PLANE_SHUFFLE, HORIZONTAL_PLANE, null);

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(origin.above(height), 0, false));
    }



    @Override
    public int getTreeHeight(RandomSource random) {
        return this.height.sample(random);
    }
}
