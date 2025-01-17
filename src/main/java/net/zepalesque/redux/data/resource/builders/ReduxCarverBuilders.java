package net.zepalesque.redux.data.resource.builders;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.carver.ReduxCarvers;
import net.zepalesque.unity.data.UnityTags;

public class ReduxCarverBuilders {

    protected static ResourceKey<ConfiguredWorldCarver<?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_CARVER, Redux.loc(name));
    }

    public static ConfiguredWorldCarver<?> createAetherCave(HolderGetter<Block> blocks) {
        HeightProvider y = UniformHeight.of(VerticalAnchor.aboveBottom(8), VerticalAnchor.absolute(180));
        FloatProvider yScale = UniformFloat.of(0.1F, 0.9F);
        FloatProvider horizRadMult = UniformFloat.of(0.7F, 1.4F);
        FloatProvider vertRadMult = UniformFloat.of(0.8F, 1.3F);
        FloatProvider floor = UniformFloat.of(-1.0F, -0.4F);
        return createBaseCaves(blocks, y, yScale, horizRadMult, vertRadMult, floor);
    }

    public static ConfiguredWorldCarver<?> createBaseCaves(HolderGetter<Block> blocks, HeightProvider y, FloatProvider yScale, FloatProvider horizRadMult, FloatProvider vertRadMult, FloatProvider floor) {
        CaveCarverConfiguration config = new CaveCarverConfiguration(
                0.2F,
                y,
                yScale,
                VerticalAnchor.aboveBottom(0),
                blocks.getOrThrow(UnityTags.Blocks.AETHER_CARVER_REPLACEABLES),
                horizRadMult,
                vertRadMult,
                floor);
        return new ConfiguredWorldCarver<>(ReduxCarvers.AETHER_CAVE.get(), config);
    }
}
