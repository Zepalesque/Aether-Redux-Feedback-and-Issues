package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class BranchLeavesDecorator extends TreeDecorator {

    public static final MapCodec<BranchLeavesDecorator> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter(deco -> deco.probability),
                            IntProvider.CODEC.fieldOf("range").forGetter(deco -> deco.range),
                            IntProvider.CODEC.fieldOf("radius").forGetter(deco -> deco.radius),
                            BlockStateProvider.CODEC.fieldOf("leaf_block").forGetter(deco -> deco.leaf))
                                    .apply(builder, BranchLeavesDecorator::new));
    private final float probability;
    private final IntProvider range;
    private final IntProvider radius;
    private final BlockStateProvider leaf;

    public BranchLeavesDecorator(float probability, IntProvider range, IntProvider radius, BlockStateProvider leaf) {
        this.probability = probability;
        this.range = range;
        this.radius = radius;
        this.leaf = leaf;
    }

    public void place(Context context) {
        int yTest = Integer.MIN_VALUE;
        int maxRange = this.range.sample(context.random());
        for (BlockPos pos : context.logs()) {
            if (pos.getY() > yTest) {
                yTest = pos.getY();
            }
        }
        int highest = yTest;
        List<BlockPos> positions = context.logs().stream().filter(pos -> pos.getY() > highest - maxRange && (context.random().nextBoolean() ? Mth.isMultipleOf(pos.getY(), 2) : Mth.isMultipleOf(pos.getY() + 1, 2))).toList();
        for (BlockPos pos : positions) {
            if (context.random().nextFloat() < this.probability) {
                int rad = this.radius.sample(context.random());
                Direction d = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
                placeBlob(pos, context, rad);
                placeBlob(pos.relative(d), context, rad);
            }
        }
    }

    private void placeBlob(BlockPos pos, Context context, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    int total = Math.abs(x) + Math.abs(y) + Math.abs(z);
                    boolean place = context.random().nextBoolean() ? total <= 1 : total <= 2;
                    if (place) {
                        BlockPos p1 = pos.offset(x, y, z);
                        if (context.level().isStateAtPosition(p1, BlockBehaviour.BlockStateBase::isAir)) {
                            context.setBlock(p1, this.leaf.getState(context.random(), p1));
                            context.leaves().add(p1);
                        }
                    }
                }
            }
        }

    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.BRANCH_LEAVES.get();
    }
}
