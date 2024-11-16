package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.foliageplacer.CrystalFoliagePlacer;
import com.aetherteam.aether.world.foliageplacer.GoldenOakFoliagePlacer;
import com.aetherteam.aether.world.trunkplacer.CrystalTreeTrunkPlacer;
import com.aetherteam.nitrogen.world.foliageplacer.HookedFoliagePlacer;
import com.aetherteam.nitrogen.world.trunkplacer.HookedTrunkPlacer;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.resource.builders.ReduxFeatureBuilders;
import net.zepalesque.redux.world.feature.gen.CloudbedFeature;
import net.zepalesque.redux.world.feature.gen.ReduxFeatures;
import net.zepalesque.redux.world.tree.decorator.GoldenVineDecorator;
import net.zepalesque.redux.world.tree.foliage.SkyrootFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SmallGoldenOakFoliagePlacer;
import net.zepalesque.zenith.world.feature.gen.LargeRockFeature;
import net.zepalesque.zenith.world.feature.gen.SurfaceRuleLakeFeature;
import net.zepalesque.zenith.world.feature.gen.ZenithFeatures;
import net.zepalesque.zenith.world.tree.trunk.IntProviderTrunkPlacer;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ReduxFeatureConfig extends ReduxFeatureBuilders {

    public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM_PATCH = createKey(name(ReduxFlowerSets.AURUM.flower()) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUCKY_CLOVER_PATCH = createKey(name(ReduxFlowerSets.LUCKY_CLOVER.flower()) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_CLOVERS_PATCH = createKey(name(ReduxBlocks.GOLDEN_CLOVERS) + "_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDBED = createKey("cloudbed");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GILDENROOT_TREE = createKey("small_gildenroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GILDENROOT_TREE = createKey("large_gildenroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GOLDEN_VINE_TREE = createKey("large_vine_golden_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GOLDEN_VINE_TREE = createKey("small_vine_golden_oak");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GOLDEN_OAK_TREE = createKey("small_golden_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GOLDEN_OAK_TREE = createKey("large_golden_oak");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_GOLDEN_TREES = createKey("grove_golden_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_GILDED_TREES = createKey("grove_gilded_trees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SENTRITE_ORE = createKey(name(ReduxStoneSets.SENTRITE.block()) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_HOLYSTONE_ORE = createKey(name(ReduxStoneSets.GILDED_HOLYSTONE.block()) + "_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES = createKey("gilded_groves_trees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SURFACE_RULE_WATER_LAKE = createKey("surface_rule_water_lake");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBROSIUM_ROCK = createKey("ambrosium_rock");

    // Overrides
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_BONEMEAL = createKey("aether_grass_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH = AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH = AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION;

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE = AetherConfiguredFeatures.CRYSTAL_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE = AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE = AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;




    // bootstap
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

        register(context, CLOUDBED, ReduxFeatures.CLOUDBED.get(),
                new CloudbedFeature.Config(
                        prov(AetherFeatureStates.COLD_AERCLOUD),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        8,
                        ReduxDensityFunctions.get(functions, ReduxDensityFunctions.CLOUDBED_NOISE),
                        10,
                        ReduxDensityFunctions.get(functions, ReduxDensityFunctions.CLOUDBED_Y_OFFSET),
                        10));

        register(context, SMALL_GILDENROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        prov(ReduxBlocks.GILDENROOT_LEAVES),
                        new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, LARGE_GILDENROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new HookedTrunkPlacer(8, 14, 14),
                        prov(ReduxBlocks.GILDENROOT_LEAVES),
                        new HookedFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 1, 4)
                ).ignoreVines().build());

        register(context, SMALL_GOLDEN_VINE_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                                .add(AetherFeatureStates.SKYROOT_LOG, 7)
                        ),
                        new IntProviderTrunkPlacer(UniformInt.of(7, 9)),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new SmallGoldenOakFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().decorators(List.of(
                        new GoldenVineDecorator(0.25F,
                                prov(ReduxBlocks.GOLDEN_VINES_PLANT),
                                prov(ReduxBlocks.GOLDEN_VINES),
                                UniformInt.of(1, 3))
                )).build());

        register(context, LARGE_GOLDEN_VINE_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                                .add(AetherFeatureStates.SKYROOT_LOG, 3)
                        ),
                        new IntProviderTrunkPlacer(UniformInt.of(11, 14)),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(10)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(13))
                ).ignoreVines().decorators(List.of(
                        new GoldenVineDecorator(0.25F,
                                prov(ReduxBlocks.GOLDEN_VINES_PLANT),
                                prov(ReduxBlocks.GOLDEN_VINES),
                                UniformInt.of(1, 5))
                        )).build());

        register(context, SMALL_GOLDEN_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                                .add(AetherFeatureStates.SKYROOT_LOG, 7)
                        ),
                        new IntProviderTrunkPlacer(UniformInt.of(6, 8)),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new SmallGoldenOakFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, LARGE_GOLDEN_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                                .add(AetherFeatureStates.SKYROOT_LOG, 7)
                        ),
                        new IntProviderTrunkPlacer(UniformInt.of(8, 12)),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(9))
                ).ignoreVines().build());

        register(context, GOLDEN_OAK_TREE, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GOLDEN_OAK_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.35F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GOLDEN_OAK_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

        register(context, GROVE_GOLDEN_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GOLDEN_VINE_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.35F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GOLDEN_VINE_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

        register(context, GROVE_GILDED_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GILDENROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get())), 0.60F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GILDENROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get()))));

        register(context, GROVE_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(GROVE_GILDED_TREES), PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get())), 0.375F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(GROVE_GOLDEN_TREES), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

        register(context, SENTRITE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxStoneSets.SENTRITE.block()), 48, 0.0F));

        register(context, GILDED_HOLYSTONE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxStoneSets.GILDED_HOLYSTONE.block()), 24, 0.3F));

        register(context, AURUM_PATCH, Feature.FLOWER,
                patch(12, 7, 3, prov(ReduxFlowerSets.AURUM.flower())));

        register(context, LUCKY_CLOVER_PATCH, Feature.FLOWER,
                patch(14, 7, 3, prov(ReduxFlowerSets.LUCKY_CLOVER.flower())));

        register(context, GOLDEN_CLOVERS_PATCH, Feature.FLOWER,
                patch(24, 7, 3, petals(drops(ReduxBlocks.GOLDEN_CLOVERS))));

        register(context, SURFACE_RULE_WATER_LAKE, ZenithFeatures.SURFACE_RULE_LAKE.get(),
                new SurfaceRuleLakeFeature.Config(BlockStateProvider.simple(Blocks.WATER)));

        register(context, AMBROSIUM_ROCK, ZenithFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                        .add(AetherFeatureStates.HOLYSTONE, 5)
                        .add(drops(ReduxStoneSets.GILDED_HOLYSTONE.block()), 3)
                        .add(AetherFeatureStates.AMBROSIUM_ORE, 1)
                ), Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)), Optional.empty()));

        // Overrides
        register(context, GRASS_PATCH, Feature.RANDOM_PATCH, patch(48, 7, 3, prov(ReduxBlocks.SHORT_AETHER_GRASS), NOT_ON_COARSE_DIRT));
        register(context, TALL_GRASS_PATCH, Feature.NO_OP, new NoneFeatureConfiguration());

        register(context, GRASS_BONEMEAL, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(prov(ReduxBlocks.SHORT_AETHER_GRASS)));

        register(context, CRYSTAL_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(ReduxWoodSets.CRYSTAL.log()),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.CRYSTAL_LEAVES, 4).add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1).build()),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, SKYROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                        new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());
    }
}
