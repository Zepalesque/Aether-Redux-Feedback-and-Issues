package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class BlightwillowFoliagePlacer extends FoliagePlacer {

    public static final MapCodec<BlightwillowFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, BlightwillowFoliagePlacer::new));


    public BlightwillowFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, int maxHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        BlockPos origin = attachment.pos();

        // Place main blob
        placeLeavesRow(level, setter, rand, config, origin, radius - 1, 0, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -1, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -2, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -3, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -4, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -5, false);
        placeLeavesRow(level, setter, rand, config, origin, radius - 1, -6, false);

        BlockPos spineStart = origin.below(4);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        for (Direction d : Direction.Plane.HORIZONTAL) {
            this.placeSpine(level, setter, rand, config, spineStart.relative(d, radius + 1), radius, mutable, d);
        }
    }

    private void placeSpine(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, BlockPos start, int radius, BlockPos.MutableBlockPos mutable, Direction d) {
        boolean extendBelow = false;
        boolean extendAbove = false;

        // Place inner spine part
        for (int i = -1; i < 8; i++) {
            boolean isHighest = i == 7;
            boolean isLowest = i == -1;
            boolean place = (!isLowest && !isHighest) || rand.nextBoolean();
            if (place) {
                if (isLowest) extendBelow = true;
                if (isHighest) extendAbove = true;
                mutable.setWithOffset(start, 0, i, 0);
                placeLeavesRow(level, setter, rand, config, mutable, 0, 0, false);
            }
        }

        // Place the outside pieces of the spine
        placeSideSpine(level, setter, rand, config, start, radius, mutable, d.getCounterClockWise(), extendBelow);
        placeSideSpine(level, setter, rand, config, start, radius, mutable, d.getClockWise(), extendBelow);
        for (int i = 1; i < radius + 3; i++) {
            boolean place = (i != 1 || ((extendBelow) && rand.nextFloat() < 0.75F)) && (i != radius + 2 || ((extendAbove) && rand.nextFloat() < 0.75F));
            if (place) {
                mutable.setWithOffset(start, d.getNormal().getX(), i, d.getNormal().getZ());
                placeLeavesRow(level, setter, rand, config, mutable, 0, 0, false);
            }
        }
    }

    private void placeSideSpine(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, BlockPos start, int radius, BlockPos.MutableBlockPos mutable, Direction d, boolean extendBelow) {
        for (int i = 0; i <= radius; i++) {
            boolean place = i != 0 || (extendBelow && rand.nextFloat() < 0.75F);
            if (place) {
                mutable.setWithOffset(start, d.getNormal().getX(), i, d.getNormal().getZ());
                placeLeavesRow(level, setter, rand, config, mutable, 0, 0, false);
            }
        }
    }


    // Override vanilla behavior of using the 'large' boolean value to actually affect the size, this is unwanted behavior in this case
    protected void placeLeavesRow(LevelSimulatedReader level, FoliageSetter setter, RandomSource random, TreeConfiguration config, BlockPos pos, int radius, int y, boolean large) {
        // Also avoid creating a new mutable block pos if the radius is 0 anyway
        if (radius <= 0) {
            if (!this.shouldSkipLocationSigned(random, 0, y, 0, radius, large)) {
                tryPlaceLeaf(level, setter, random, config, pos.above(y));
                return;
            }
        }
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
        for(int j = -radius; j <= radius; ++j) {
            for(int k = -radius; k <= radius; ++k) {
                if (!this.shouldSkipLocationSigned(random, j, y, k, radius, large)) {
                    mutablePos.setWithOffset(pos, j, y, k);
                    tryPlaceLeaf(level, setter, random, config, mutablePos);
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource rand, int x, int y, int z, int radius, boolean remove) {
        // when the radius is zero (placing a single block, mainly for the side spines), the remove parameter, usually called large, is used to determine whether to remove the block
        if (radius == 0) return remove;
        // If the y offset is 0 or -6, only skip the location if it is on the corners, but do so 75% of the time
        else if (y == 0 || y == -6) return x + z >= radius * 2 && rand.nextFloat() < 0.75F;
        // If the y offset is -1, skip the location if it is on the corners, with a 10% chance to not skip them
        else if (y == -1) return x + z >= radius * 2  && rand.nextFloat() < 0.9F;
        // If the y offset is -5, skip the location if it is on the corners, but only half of the time
        else if (y == -5) return x + z >= radius * 2 && rand.nextBoolean();
        // Layers -2, -3, and -4 should always be fully filled in
        else return false;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ReduxFoliagePlacers.BLIGHTWILLOW_FOLIAGE.get();
    }
}
