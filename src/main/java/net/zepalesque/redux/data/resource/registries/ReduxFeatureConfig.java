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
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
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
import net.zepalesque.redux.world.tree.foliage.BlightwillowFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SkyrootFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SmallGoldenOakFoliagePlacer;
import net.zepalesque.redux.world.tree.trunk.BlightwillowTrunkPlacer;
import net.zepalesque.unity.data.UnityTags;
import net.zepalesque.unity.extendablestate.UnityStateLists;
import net.zepalesque.zenith.api.block.predicate.InBiomePredicate;
import net.zepalesque.zenith.api.world.feature.gen.ExtendableStateListBlockFeature;
import net.zepalesque.zenith.api.world.feature.gen.LargeRockFeature;
import net.zepalesque.zenith.api.world.feature.gen.SurfaceRuleLakeFeature;
import net.zepalesque.zenith.api.world.tree.trunk.IntProviderTrunkPlacer;
import net.zepalesque.zenith.core.registry.ZenithFeatures;

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
    public static final ResourceKey<ConfiguredFeature<?, ?>> VARIED_GILDENROOT_TREE = createKey("varied_gildenroot");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GOLDEN_OAK_TREE = createKey("small_golden_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GOLDEN_OAK_TREE = createKey("large_golden_oak");


    public static final ResourceKey<ConfiguredFeature<?, ?>> SENTRITE_ORE = createKey(name(ReduxStoneSets.SENTRITE.block()) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_HOLYSTONE_ORE = createKey(name(ReduxStoneSets.GILDED_HOLYSTONE.block()) + "_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES = createKey("gilded_groves_trees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_TREES = createKey("the_blight_trees");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SURFACE_RULE_WATER_LAKE = createKey("surface_rule_water_lake");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBROSIUM_ROCK = createKey("ambrosium_rock");

    public static final ResourceKey<ConfiguredFeature<?, ?>> WYNDSPROUTS_PATCH = createKey(name(ReduxBlocks.WYNDSPROUTS) + "_patch");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEAKMOSS_VEGETATION = createKey("bleakmoss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLEAKMOSS_BONEMEAL = createKey("bleakmoss_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_SHADEROOT_TREE = createKey("small_shaderoot");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTWILLOW_TREE = createKey("blightwillow");


    // Overrides
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE = AetherConfiguredFeatures.CRYSTAL_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE = AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE = AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;

    // rip bootstap :pensive:
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

        register(context, SMALL_SHADEROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        prov(ReduxBlocks.SHADEROOT_LEAVES),
                        new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());

        register(context, BLIGHTWILLOW_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(ReduxWoodSets.BLIGHTWILLOW.log()),
                        // TODO
                        new BlightwillowTrunkPlacer(UniformInt.of(12, 14), Optional.of(prov(ReduxWoodSets.BLIGHTWILLOW.wood()))),
                        // TODO
                        prov(drops(ReduxBlocks.SHADEROOT_LEAVES)),
                        new BlightwillowFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(7, 0, 3)
                ).ignoreVines()
                        // TODO
                        //.decorators()
                        .build());

        register(context, LARGE_GILDENROOT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new HookedTrunkPlacer(8, 14, 14),
                        prov(ReduxBlocks.GILDENROOT_LEAVES),
                        new HookedFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 1, 4)
                ).ignoreVines().build());

        BlockPredicate has_golden_vines = InBiomePredicate.inTag(ReduxTags.Biomes.HAS_GOLDEN_VINES);
        BlockPredicate on_enchanted = BlockPredicate.matchesTag(new Vec3i(0, -1, 0), UnityTags.Blocks.SHORT_AETHER_GRASS_STATE_ENCHANTING);
        BlockPredicate golden_vine_predicate = BlockPredicate.anyOf(has_golden_vines, on_enchanted);

        register(context, SMALL_GOLDEN_OAK_TREE, Feature.TREE,
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
                                UniformInt.of(1, 3), Optional.of(golden_vine_predicate))
                )).build());

        register(context, LARGE_GOLDEN_OAK_TREE, Feature.TREE,
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
                                UniformInt.of(1, 5), Optional.of(golden_vine_predicate))
                        )).build());

        register(context, GOLDEN_OAK_TREE, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GOLDEN_OAK_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.35F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GOLDEN_OAK_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

        register(context, VARIED_GILDENROOT_TREE, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GILDENROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get())), 0.60F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GILDENROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get()))));

        register(context, GROVE_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(
                                PlacementUtils.inlinePlaced(
                                        configs.getOrThrow(VARIED_GILDENROOT_TREE),
                                        PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.GILDENROOT_SAPLING.flower().get()))
                                , 0.375F)),
                        PlacementUtils.inlinePlaced(
                                configs.getOrThrow(GOLDEN_OAK_TREE),
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())
                        )));

        register(context, BLIGHT_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(
                                PlacementUtils.inlinePlaced(
                                        configs.getOrThrow(SMALL_SHADEROOT_TREE),
                                        PlacementUtils.filteredByBlockSurvival(ReduxFlowerSets.SHADEROOT_SAPLING.flower().get()))
                                , 0.375F)),
                        PlacementUtils.inlinePlaced(
                                configs.getOrThrow(BLIGHTWILLOW_TREE),
                                // TODO: Filter via Blightwillow Sapling
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())
                        )));

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

        register(context, WYNDSPROUTS_PATCH, Feature.FLOWER,
                patch(24, 5, 3, prov(ReduxBlocks.WYNDSPROUTS)));

        // TODO
        register(context, BLEAKMOSS_VEGETATION, ZenithFeatures.EXTENDABLE_STATE_LIST_BLOCK.get(),
                new ExtendableStateListBlockFeature.Config(UnityStateLists.FLUTEMOSS.get(), Optional.empty()));

        register(context, BLEAKMOSS_BONEMEAL, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(UnityTags.Blocks.AETHER_CARVER_REPLACEABLES,
                        prov(ReduxBlocks.BLEAKMOSS_BLOCK),
                        Holder.direct(new PlacedFeature(configs.getOrThrow(BLEAKMOSS_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(1, 2),
                        0.75F));
    }
}
