package net.zepalesque.redux.block.natural;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.ReduxBlocks;

import java.util.function.Supplier;

public class HangingAetherVinesBody extends GrowingPlantBodyBlock {

    public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

    private final TagKey<Block> leafTag;
    private final Holder<Block> head;

    public HangingAetherVinesBody(Properties properties, TagKey<Block> leafTag, Holder<Block> head) {
        super(properties, Direction.DOWN, SHAPE, false);
        this.leafTag = leafTag;
        this.head = head;
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.relative(this.growthDirection.getOpposite());
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return !this.canAttachTo(blockstate) ? super.canSurvive(pState, pLevel, pPos) : super.canSurvive(pState, pLevel, pPos) || blockstate.is(this.leafTag);
    }

    @Override
    protected GrowingPlantHeadBlock getHeadBlock() {
        try {
            Block b = this.head.value();
            return (GrowingPlantHeadBlock) b;
        } catch (ClassCastException e) { // Don't cast the IllegalStateException that occurs if the holder has no value, as it should give enough info
            throw new IllegalStateException("HangingAetherVinesBody's associated head block was not an instance of GrowingPlantHeadBlock!", e);
        }
    }

    public static final MapCodec<HangingAetherVinesBody> CODEC = RecordCodecBuilder.mapCodec(builder ->
            builder.group(
                    propertiesCodec(),
                            TagKey.codec(Registries.BLOCK).fieldOf("leaf_tag").forGetter(block -> block.leafTag),
                            BuiltInRegistries.BLOCK.holderByNameCodec().fieldOf("head_block").forGetter(block -> block.head)

                    )
                    .apply(builder, HangingAetherVinesBody::new));


    @Override
    protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
        return CODEC;
    }
}
