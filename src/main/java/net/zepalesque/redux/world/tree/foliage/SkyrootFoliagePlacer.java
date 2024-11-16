package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SkyrootFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<SkyrootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec((instance) -> foliagePlacerParts(instance)
            .apply(instance, SkyrootFoliagePlacer::new));

    public SkyrootFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, int maxHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        BlockPos origin = attachment.pos();

        // Place main piece
        placeLeavesRow(level, setter, rand, config, origin, radius - 1, 0, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -1, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -2, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -3, false);
    }

    @Override
    public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
        return 0;
    }
    
    @Override
    protected boolean shouldSkipLocation(RandomSource rand, int x, int y, int z, int radius, boolean large) {
        if (y == 0) {
            // If the y offset is 0, only skip the location if it is on the corners, but do so 75% of the time
            return x + z >= radius * 2 && rand.nextFloat() < 0.75F;
        } else if (y == -1) {
            // If the y offset is -1, skip the corners always, and skip stuff outside a diamond shape 75% of the time
            boolean diamond = x + z <= radius;
            boolean corners = x + z <= radius + 1;
            return !corners || (!diamond && rand.nextFloat() < 0.75F);
        } else if (y == -2) {
            // If the y offset is -2, skip the location if it is on the corners and an unlikely boolean check succeeds
            return x + z >= radius * 2 && rand.nextFloat() < 0.25F;
        } else {
            // If the y offset is -3, skip the location if it is on the corners and a boolean check succeeds
            return x + z >= radius * 2 && rand.nextBoolean();
        }
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ReduxFoliagePlacers.SKYROOT_FOLIAGE.get();
    }
}
