package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.builders.AetherPlacedFeatureBuilders;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import com.aetherteam.aether.world.placementmodifier.ImprovedLayerPlacementModifier;
import com.aetherteam.nitrogen.data.resources.builders.NitrogenPlacedFeatureBuilders;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.world.feature.placement.ConditionPlacementModule;

import java.util.List;

public class ReduxPlacements {

    public static final ResourceKey<PlacedFeature> CLOUDBED = copyKey(ReduxFeatureConfig.CLOUDBED);
    public static final ResourceKey<PlacedFeature> SENTRITE_ORE = copyKey(ReduxFeatureConfig.SENTRITE_ORE);
    public static final ResourceKey<PlacedFeature> GROVE_TREES = copyKey(ReduxFeatureConfig.GROVE_TREES);
    public static final ResourceKey<PlacedFeature> AURUM_PATCH = copyKey(ReduxFeatureConfig.AURUM_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_CLOVERS_PATCH = copyKey(ReduxFeatureConfig.GOLDEN_CLOVERS_PATCH);
    public static final ResourceKey<PlacedFeature> AMBROSIUM_ROCK = copyKey(ReduxFeatureConfig.AMBROSIUM_ROCK);
    public static final ResourceKey<PlacedFeature> LUCKY_CLOVER_PATCH = copyKey(ReduxFeatureConfig.LUCKY_CLOVER_PATCH);

    public static final ResourceKey<PlacedFeature> GILDED_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE);


    public static final ResourceKey<PlacedFeature> SPARSE_BLUE_AERCLOUD = createKey("sparse_blue_aercloud");
    public static final ResourceKey<PlacedFeature> DENSE_BLUE_AERCLOUD = createKey("dense_blue_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_ZANITE_ORE = createKey("sparse_zanite_ore");
    public static final ResourceKey<PlacedFeature> SPARSE_AMBROSIUM_ORE = createKey("sparse_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_ZANITE_ORE = createKey("dense_zanite_ore");
    public static final ResourceKey<PlacedFeature> DENSE_AMBROSIUM_ORE = createKey("dense_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> SURFACE_RULE_WATER_LAKE = copyKey(ReduxFeatureConfig.SURFACE_RULE_WATER_LAKE);

    public static final ResourceKey<PlacedFeature> BONEMEAL_OVERRIDE = AetherPlacedFeatures.AETHER_GRASS_BONEMEAL;



    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);
        DungeonBlacklistFilter blacklist = new DungeonBlacklistFilter();
        NoiseThresholdCountPlacement threshold = NoiseThresholdCountPlacement.of(-0.8D, 5, 10);

        register(context, CLOUDBED, configs.getOrThrow(ReduxFeatureConfig.CLOUDBED));

        register(context, SENTRITE_ORE, configs.getOrThrow(ReduxFeatureConfig.SENTRITE_ORE),
                InSquarePlacement.spread(),
                HeightRangePlacement.of(TrapezoidHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.aboveBottom(128))),
                BiomeFilter.biome()
        );



//        register(context, GILDED_HOLYSTONE_ORE, configs.getOrThrow(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE),
//                CountPlacement.of(24),
//                InSquarePlacement.spread(),
//                HeightRangePlacement.of(UniformHeight.of(VerticalAnchor.BOTTOM, VerticalAnchor.absolute(128))),
//                ConditionPlacementModule.of(ReduxConditions.MOSSY_ORE),
//                BiomeFilter.biome()
//        );

        register(context, GROVE_TREES, configs.getOrThrow(ReduxFeatureConfig.GROVE_TREES),
                CountPlacement.of(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(6), 9)
                        .add(ConstantInt.of(4), 3)
                        .add(ConstantInt.of(2), 5)
                        .add(ConstantInt.of(10), 1)
                        .build())),
                ImprovedLayerPlacementModifier.of(Heightmap.Types.OCEAN_FLOOR, ConstantInt.of(2), 4),
                BiomeFilter.biome(),
                PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()),
                blacklist
        );

        register(context, AURUM_PATCH, configs.getOrThrow(ReduxFeatureConfig.AURUM_PATCH),
                threshold,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(8),
                BiomeFilter.biome());

        register(context, LUCKY_CLOVER_PATCH, configs.getOrThrow(ReduxFeatureConfig.LUCKY_CLOVER_PATCH),
                threshold,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 2), 4),
                RarityFilter.onAverageOnceEvery(16),
                BiomeFilter.biome());


        register(context, GOLDEN_CLOVERS_PATCH, configs.getOrThrow(ReduxFeatureConfig.GOLDEN_CLOVERS_PATCH),
                threshold,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING, UniformInt.of(0, 1), 4),
                BiomeFilter.biome());

        register(context, SPARSE_BLUE_AERCLOUD,
                configs.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION),
                AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 48));

        register(context, DENSE_BLUE_AERCLOUD,
                configs.getOrThrow(AetherConfiguredFeatures.BLUE_AERCLOUD_CONFIGURATION),
                AetherPlacedFeatureBuilders.aercloudPlacement(32, 64, 14));

        register(context, SPARSE_AMBROSIUM_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(10, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));

        register(context, SPARSE_ZANITE_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(7, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));

        register(context, DENSE_AMBROSIUM_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_AMBROSIUM_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(30, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(128))));

        register(context, DENSE_ZANITE_ORE, configs.getOrThrow(AetherConfiguredFeatures.ORE_ZANITE_CONFIGURATION),
                NitrogenPlacedFeatureBuilders.commonOrePlacement(21, HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(75))));

        register(context, SURFACE_RULE_WATER_LAKE, configs.getOrThrow(ReduxFeatureConfig.SURFACE_RULE_WATER_LAKE),
                RarityFilter.onAverageOnceEvery(15),
                HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG),
                BiomeFilter.biome()
        );

        // Overrides
        register(context, BONEMEAL_OVERRIDE, configs.getOrThrow(ReduxFeatureConfig.GRASS_BONEMEAL), PlacementUtils.isEmpty());

        register(context, AMBROSIUM_ROCK, configs.getOrThrow(ReduxFeatureConfig.AMBROSIUM_ROCK),
                threshold,
                ImprovedLayerPlacementModifier.of(Heightmap.Types.MOTION_BLOCKING,
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(UniformInt.of(1, 2), 5)
                                .add(UniformInt.of(1, 4), 3)
                                .build()), 4),
                RarityFilter.onAverageOnceEvery(24),
                InSquarePlacement.spread(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(new Vec3i(0, -1, 0), BlockTags.DIRT)),
                BiomeFilter.biome()
        );
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration, PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Redux.loc(name));
    }

    private static ResourceKey<PlacedFeature> copyKey(ResourceKey<ConfiguredFeature<?, ?>> configFeat) {
        return createKey(configFeat.location().getPath());
    }

    private static ResourceKey<PlacedFeature> aetherKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Aether.MODID, name));
    }

    private static String name(DeferredHolder<?, ?> reg) {
        return reg.getId().getPath();
    }


}
