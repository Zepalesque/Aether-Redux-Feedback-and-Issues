package net.zepalesque.redux.data.resource.builders;

import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.unity.data.resource.builders.base.BaseFeatureBuilders;

import java.util.function.Supplier;

public class ReduxFeatureBuilders extends BaseFeatureBuilders {

    protected static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    protected static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Redux.loc(name));
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
