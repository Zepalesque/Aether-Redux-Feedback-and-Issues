package net.zepalesque.redux.data.resource.builders;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.data.resources.builders.AetherConfiguredFeatureBuilders;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.zenith.world.feature.gen.BlockWithPredicateFeature;
import net.zepalesque.zenith.world.feature.gen.ZenithFeatures;

import java.util.function.Supplier;

public class ReduxFeatureBuilders extends AetherConfiguredFeatureBuilders {

    public static final BlockPredicate NOT_ON_COARSE_DIRT = BlockPredicate.not(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), ReduxTags.Blocks.COARSE_AETHER_DIRT));

    public static RandomPatchConfiguration patch(int tries, int xz, int y, BlockStateProvider state) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(state)));
    }

    public static RandomPatchConfiguration patch(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ZenithFeatures.BLOCK_WITH_PREDICATE.get(), new BlockWithPredicateFeature.Config(state, predicate)));
    }

    protected static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    protected static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Redux.loc(name));
    }

    protected static String name(DeferredHolder<?, ?> reg) {
        return reg.getId().getPath();
    }

    protected static BlockStateProvider prov(BlockState state) {
        return BlockStateProvider.simple(drops(state));
    }

    protected static BlockStateProvider prov(Supplier<? extends Block> block) {
        return prov(block.get().defaultBlockState());
    }

    protected static BlockState drops(BlockState state) {
        return state.hasProperty(AetherBlockStateProperties.DOUBLE_DROPS) ? state.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true) : state;
    }

    protected static BlockState drops(Supplier<? extends Block> block) {
        return drops(block.get().defaultBlockState());
    }

    protected static BlockState naturalDrops(Supplier<? extends Block> block) {
        BlockState b = block.get().defaultBlockState();
        return b.hasProperty(ReduxStates.NATURAL_GEN) ? drops(b.setValue(ReduxStates.NATURAL_GEN, true)) : drops(b);
    }


    protected static BlockStateProvider petals(BlockState state) {
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (Direction d : PinkPetalsBlock.FACING.getPossibleValues()) {
            BlockState temp = state.setValue(PinkPetalsBlock.FACING, d);
            for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i);
            }
        }
        return new WeightedStateProvider(builder);
    }
}
