package net.zepalesque.redux.data.resource.registries;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.zepalesque.redux.data.resource.builders.ReduxCarverBuilders;

public class ReduxCarverConfig extends ReduxCarverBuilders {

    public static final ResourceKey<ConfiguredWorldCarver<?>> AETHER_CAVES = createKey("aether_caves");

    public static void bootstrap(BootstrapContext<ConfiguredWorldCarver<?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        context.register(AETHER_CAVES, createAetherCave(blocks));
    }
}
