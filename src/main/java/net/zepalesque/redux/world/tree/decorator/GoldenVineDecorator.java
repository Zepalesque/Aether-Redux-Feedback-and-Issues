package net.zepalesque.redux.world.tree.decorator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.zepalesque.redux.Redux;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class GoldenVineDecorator extends TreeDecorator {

    public static final MapCodec<GoldenVineDecorator> CODEC = RecordCodecBuilder.mapCodec((vines) ->
            vines.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(deco -> deco.probability),
                            BlockStateProvider.CODEC.fieldOf("plant_body_provider").forGetter(deco -> deco.bodyBlock),
                            BlockStateProvider.CODEC.fieldOf("plant_head_provider").forGetter(deco -> deco.headBlock),
                            IntProvider.codec(1,128).fieldOf("length").forGetter(deco -> deco.length),
                            BlockPredicate.CODEC.optionalFieldOf("predicate").forGetter(deco -> deco.predicate))
                                    .apply(vines, GoldenVineDecorator::new));
    private final float probability;
    private final BlockStateProvider bodyBlock;
    private final BlockStateProvider headBlock;
    private final IntProvider length;
    private final Optional<BlockPredicate> predicate;

    public GoldenVineDecorator(float vineProbability, BlockStateProvider bodyBlock, BlockStateProvider headBlock, IntProvider vineLength, Optional<BlockPredicate> predicate) {
        this.probability = vineProbability;
        this.bodyBlock = bodyBlock;
        this.headBlock = headBlock;
        this.length = vineLength;
        this.predicate = predicate;
    }

    // Avoid new table instantiation for every single tree, formatted as [X, Z, Y]
    private static final Table<Integer, Integer, Integer> LOWEST_FOR_XZ = HashBasedTable.create();

    public void place(TreeDecorator.Context context) {
        if (context.level() instanceof WorldGenLevel genLevel) {
            if (this.predicate.isPresent()) {
                BlockPredicate bp = this.predicate.get();
                List<BlockPos> logs = context.logs();
                if (!logs.isEmpty()) {
                    if (!bp.test(genLevel, logs.getFirst())) return;
                }
            }
        } else Redux.LOGGER.warn("Failed BlockPredicate check as level was not an instance of WorldGenLevel!");

        if (!LOWEST_FOR_XZ.isEmpty()) {
            LOWEST_FOR_XZ.clear();
        }

        List<BlockPos> leavesClone = context.leaves().clone();
        Collections.reverse(leavesClone);
        for (BlockPos leafPos : leavesClone) {
            int x = leafPos.getX();
            int y = leafPos.getY();
            int z = leafPos.getZ();
            try {
                if (!LOWEST_FOR_XZ.contains(x, z)) LOWEST_FOR_XZ.put(x, z, y);
                else if (y < LOWEST_FOR_XZ.get(x, z)) LOWEST_FOR_XZ.put(x, z, y);
            } catch (NullPointerException exception) {
                Redux.LOGGER.error("Caught error when trying to add leaf to table!", exception);
            }
        }
        RandomSource randomsource = context.random();
        for (Table.Cell<Integer, Integer, Integer> leafPos : LOWEST_FOR_XZ.cellSet()) {
            BlockPos pos = new BlockPos(leafPos.getRowKey(), leafPos.getValue(), leafPos.getColumnKey());
            int length = this.length.sample(randomsource);
            if (randomsource.nextFloat() < probability) {
                BlockPos blockpos = pos.below();
                if (context.isAir(blockpos)) {
                    this.addVine(blockpos, context, length);
                }
            }
        }
    }

    private void addVine(BlockPos pPos, TreeDecorator.Context pContext, int length) {
        for (int i = 1; i <= length; i++) {
            BlockPos placement = pPos.offset(0, 1 - i, 0);
            boolean notAirBelow = !pContext.isAir(placement.below());
            boolean maxLength = i >= length;
            if (notAirBelow || maxLength) {
                pContext.setBlock(placement, this.headBlock.getState(pContext.random(), pPos));
                break;
            } else {
                pContext.setBlock(placement, this.bodyBlock.getState(pContext.random(), pPos));
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.GOLDEN_VINES.get();
    }
}
