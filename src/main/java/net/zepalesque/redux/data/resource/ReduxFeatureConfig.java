package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.builders.AetherConfiguredFeatureBuilders;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import com.aetherteam.aether.world.configuration.ShelfConfiguration;
import com.aetherteam.aether.world.feature.AetherFeatures;
import com.aetherteam.aether.world.foliageplacer.CrystalFoliagePlacer;
import com.aetherteam.aether.world.foliageplacer.GoldenOakFoliagePlacer;
import com.aetherteam.aether.world.foliageplacer.HolidayFoliagePlacer;
import com.aetherteam.aether.world.treedecorator.HolidayTreeDecorator;
import com.aetherteam.aether.world.trunkplacer.CrystalTreeTrunkPlacer;
import com.aetherteam.aether.world.trunkplacer.GoldenOakTrunkPlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockhandlers.WoodHandlers;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.PetalPrismaticness;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.feature.*;
import net.zepalesque.redux.world.feature.config.*;
import net.zepalesque.redux.world.stateprov.SimpleConditionAlternativeStateProvider;
import net.zepalesque.redux.world.tree.decorator.BlightwillowRootsTrunkDecorator;
import net.zepalesque.redux.world.tree.decorator.EnchantedVineDecorator;
import net.zepalesque.redux.world.tree.decorator.PatchTreeDecorator;
import net.zepalesque.redux.world.tree.foliage.BlightwillowFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.GenesisHookedFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.GenesisPineFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.GlaciaFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SkyrootFoliagePlacer;
import net.zepalesque.redux.world.tree.foliage.SmallGoldenOakFoliagePlacer;
import net.zepalesque.redux.world.tree.trunk.GenesisHookedTrunkPlacer;
import net.zepalesque.redux.world.tree.trunk.IntProviderTrunkPlacer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;


// TODO (maybe not the perfect spot for this but whatever) IntProvider-based straight Trunk placer thing (should really be a vanilla feature)
public class    ReduxFeatureConfig {
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEVELIUM_GRASSES_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.AVELIUM) + "_grasses_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGAL_PATCH = createKey(Folders.CAVE + "fungal_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGAL_PATCH_BONEMEAL = createKey(Folders.CAVE + "fungal_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.AURUM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZYATRIX_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.ZYATRIX) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZANBERRY_BUSH_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.ZANBERRY_BUSH) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTED_SKYROOT_TREE = createKey(Folders.TREE + "blighted_skyroot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_PATCH = createKey(Folders.CAVE + "blightmoss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_PATCH_BONEMEAL = createKey(Folders.CAVE + "blightmoss_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_PINE = createKey(Folders.TREE + "skyroot_pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGAL_VEGETATION = createKey(Folders.CAVE + "fungal_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTSHADE_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.BLIGHTSHADE) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTWILLOW_TREE = createKey(Folders.TREE + "blightwillow_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTED_AERCLOUD = createKey(Folders.MISC + "blighted_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_ROCK = createKey(Folders.SURFACE + "blight_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_TREES = createKey(Folders.TREE + "blight_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_BUSH = createKey(Folders.PATCH + "skyroot_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDCAP_MUSHLING_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.CLOUDCAP_MUSHLING) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GILDED_OAK_TREE = createKey(Folders.TREE + "fancy_gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GOLDEN_OAK_TREE = createKey(Folders.TREE + "fancy_golden_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAGGERBLOOM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.DAGGERBLOOM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THERATIP_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.THERATIP) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPLITFERN_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SPLITFERN) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEROGEL_ORE = createKey(Folders.ORE + name(AetherBlocks.AEROGEL) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_PURPLE_FLOWER_PATCH = createKey(Folders.PATCH + "frosted_purple_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_TREES = createKey(Folders.TREE + "frosted_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIAL_TREES = createKey(Folders.TREE + "glacial_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_VINES_PATCH = createKey(Folders.PATCH + "corrupted_vines_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_HOLYSTONE_ORE = createKey(Folders.ORE + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_HOLYSTONE_ORE = createKey(Folders.ORE + name(ReduxBlocks.BLIGHTMOSS_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ICESTONE_CHUNK = createKey(Folders.ORE + "large_icestone_chunk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_LEAF_PATCH  = createKey(Folders.PATCH + "gilded_leaf_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LEGACY_GILDED_OAK_TREE = createKey(Folders.TREE + "legacy_gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GILDED_TREE = createKey(Folders.TREE + "small_gilded_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GILDED_TREE = createKey(Folders.TREE + "large_gilded_oak_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GOLDEN_VINE_TREE = createKey(Folders.TREE + "large_vine_golden_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GOLDEN_VINE_TREE = createKey(Folders.TREE + "small_vine_golden_oak");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GOLDEN_OAK_TREE = createKey(Folders.TREE + "small_golden_oak");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GOLDEN_OAK_TREE = createKey(Folders.TREE + "large_golden_oak");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_ROCK  = createKey(Folders.SURFACE + "gilded_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_WHITE_FLOWER_PATCH = createKey(Folders.PATCH + "gilded_white_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.LUXWEED) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_CLOVER_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.GOLDEN_CLOVER) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_LEAF_PATCH = createKey(Folders.PATCH + "golden_leaf_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES_LEGACY = createKey(Folders.TREE + "grove_trees_legacy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES = createKey(Folders.TREE + "grove_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSLAND_TREES_LEGACY = createKey(Folders.TREE + "grassland_trees_legacy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSLAND_TREES = createKey(Folders.TREE + "grassland_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYFIELDS_ROCK = createKey(Folders.SURFACE + "skyfields_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUBLANDS_ROCK  = createKey(Folders.SURFACE + "shrublands_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERSTOOL_ROCK = createKey(Folders.SURFACE + name(ReduxBlocks.SHIMMERSTOOL) + "_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERSTOOL_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SHIMMERSTOOL) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLYSILT_DISK  = createKey(Folders.SURFACE + "holysilt_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEROGEL_DISK  = createKey(Folders.SURFACE + "aerogel_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SKYSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYFIELDS_TREES = createKey(Folders.TREE + "skyfields_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUBLANDS_TREES = createKey(Folders.TREE + "shrublands_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRIDIA_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.IRIDIA) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> XAELIA_PATCH  = createKey(Folders.PATCH + "xaelia_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_CLOUDCAP  = createKey(Folders.TREE + "large_cloudcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTROOTS  = createKey(Folders.MISC + "lightroots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIA_TREE = createKey(Folders.TREE + "glacia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_LEAF_TREE = createKey(Folders.TREE + "crystal_leaf_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_RARE_FRUIT_TREE = createKey(Folders.TREE + "crystal_rare_fruit_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_CRYSTAL_RARE_FRUIT_TREE = createKey(Folders.COMPAT + "purple_crystal_rare_fruit_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MUSHROOMS = createKey(Folders.TREE + "large_mushrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_JELLYSHROOM = createKey(Folders.TREE + "large_jellyshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINA_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.LUMINA) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_HOLYSTONE_ORE  = createKey(Folders.ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_ROCK  = createKey(Folders.SURFACE + "mossy_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICESTONE_ROCK  = createKey(Folders.SURFACE + "icestone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELDSPROOT_TREE = createKey(Folders.TREE + "fieldsproot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WYNDSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.WYNDSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GENESIS_WYNDSPROUTS_PATCH = createKey(Folders.PATCH + "genesis_" +  name(ReduxBlocks.WYNDSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GENESIS_SKYSPROUTS_PATCH = createKey(Folders.PATCH + "genesis_" + name(ReduxBlocks.SKYSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_GLACIA_TREE = createKey(Folders.TREE + "purple_glacia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPIROLYCTIL_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.SPIROLYCTIL) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYSHROOM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.JELLYSHROOM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SURFACE_RULE_WATER_LAKE = createKey(Folders.SURFACE + "surface_rule_water_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_LAKE = createKey(Folders.SURFACE + "blight_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_SPRING = createKey(Folders.SURFACE + "blight_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VERIDIUM_ORE = createKey(Folders.ORE + name(ReduxBlocks.VERIDIUM_ORE));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIVINITE_ORE = createKey(Folders.ORE + name(ReduxBlocks.DIVINITE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SENTRITE_ORE = createKey(Folders.ORE + name(ReduxBlocks.SENTRITE) + "_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE_OVERRIDE = AetherConfiguredFeatures.CRYSTAL_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLIDAY_TREE_OVERRIDE = AetherConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE_OVERRIDE = AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLD_DUNGEON_FLOWER_OVERRIDE = AetherConfiguredFeatures.SINGLE_GOLD_DUNGEON_FLOWER_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_TREE_OVERRIDE = AetherConfiguredFeatures.SKYROOT_TREE_CONFIGURATION;

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH_OVERRIDE = AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH_OVERRIDE = AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION;

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH_BONEMEAL = createKey(Folders.MISC + "aether_grass_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_ENCHANTED_GRASS = createKey(Folders.COMPAT + "ancient_enchanted_grass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_SNOW_LAYER = createKey(Folders.SURFACE + "snow_layer");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_LAYER = createKey(Folders.MISC + "cloud_layer");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_GOLDEN_TREES = createKey(Folders.TREE + "grove_golden_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_GILDED_TREES = createKey(Folders.TREE + "grove_gilded_trees");


    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Redux.MODID, name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> aetherKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Aether.MODID, name));
    }


    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
        HolderGetter<ConfiguredFeature<?, ?>> configs = context.lookup(Registries.CONFIGURED_FEATURE);
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);

        register(context, AEVELIUM_GRASSES_PATCH, Feature.RANDOM_PATCH,
                blockBelowPlacementPatch(32, 7, 3, (new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(drops(ReduxBlocks.AVELIUM_SPROUTS), 5)
                                        .add(drops(ReduxBlocks.AVELIUM_ROOTS), 3))),
                        BlockPredicate.matchesTag(ReduxTags.Blocks.AEVELIUM_GRASSES)));

        register(context, AURUM_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, prov(ReduxBlocks.AURUM)));

        register(context, ZYATRIX_PATCH, Feature.FLOWER,
                patchNoCoarse(14, 7, 3, prov(ReduxBlocks.ZYATRIX)));


        register(context, ZANBERRY_BUSH_PATCH, Feature.FLOWER,
                randomPatch(32, 7, 3, prov(ReduxBlocks.ZANBERRY_BUSH)));


        register(context, AETHER_SNOW_LAYER, ReduxFeatures.TREE_AWARE_SNOW.get(), FeatureConfiguration.NONE);
        register(context, GILDED_WHITE_FLOWER_PATCH, Feature.FLOWER,
                patchNoCoarse(16, 7, 3, new SimpleConditionAlternativeStateProvider(drops(ReduxBlocks.ENCHANTED_WHITE_FLOWER), Conditions.ENCHGRASS, AetherFeatureStates.WHITE_FLOWER)));
        register(context, HOLYSILT_DISK, AetherFeatures.SHELF.get(),
                new ShelfConfiguration(prov(ReduxBlocks.HOLYSILT),
                        ConstantFloat.of(3.5F), UniformInt.of(0, 48), HolderSet.direct(AetherBlocks.AETHER_GRASS_BLOCK.getHolder().get(), ReduxBlocks.COARSE_AETHER_DIRT.getHolder().get())));
             register(context, AEROGEL_DISK, AetherFeatures.SHELF.get(),
                new ShelfConfiguration(prov(AetherBlocks.AEROGEL),
                        ConstantFloat.of(3.5F), UniformInt.of(0, 48), HolderSet.direct(AetherBlocks.AETHER_GRASS_BLOCK.getHolder().get(), ReduxBlocks.COARSE_AETHER_DIRT.getHolder().get())));
        register(context, BLIGHTMOSS_PATCH, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(ReduxTags.Blocks.AETHER_MOSS_REPLACEABLES,
                        prov(ReduxBlocks.BLIGHTMOSS_BLOCK),
                        Holder.direct(new PlacedFeature(configs.getOrThrow(BLIGHTMOSS_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(4, 7),
                        0.3F));
        register(context, BLIGHTMOSS_PATCH_BONEMEAL, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(ReduxTags.Blocks.AETHER_CARVER_REPLACEABLES,
                        prov(ReduxBlocks.BLIGHTMOSS_BLOCK),
                        Holder.direct(new PlacedFeature(configs.getOrThrow(BLIGHTMOSS_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(1, 2),
                        0.75F));

        register(context, FUNGAL_PATCH, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(ReduxTags.Blocks.AETHER_MOSS_REPLACEABLES,
                        prov(ReduxBlocks.FUNGAL_GROWTH),
                        Holder.direct(new PlacedFeature(configs.getOrThrow(FUNGAL_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(4, 7),
                        0.3F));

        register(context, BLIGHTED_AERCLOUD, AetherFeatures.AERCLOUD.get(),
                AetherConfiguredFeatureBuilders.aercloud(8, drops(ReduxBlocks.BLIGHTED_AERCLOUD)));

        register(context, FUNGAL_PATCH_BONEMEAL, Feature.VEGETATION_PATCH,
                new VegetationPatchConfiguration(ReduxTags.Blocks.AETHER_CARVER_REPLACEABLES,
                        prov(ReduxBlocks.FUNGAL_GROWTH),
                        Holder.direct(new PlacedFeature(configs.getOrThrow(FUNGAL_VEGETATION),
                                List.of())),
                        CaveSurface.FLOOR,
                        ConstantInt.of(1),
                        0.0F,
                        2,
                        0.8F,
                        UniformInt.of(1, 2),
                        0.75F));

        register(context, CLOUD_LAYER, ReduxFeatures.CLOUDBED.get(),
                new CloudbedFeature.Config(
                        prov(AetherFeatureStates.COLD_AERCLOUD),
                        BlockPredicate.ONLY_IN_AIR_PREDICATE,
                        8,
                        ReduxDensityFunctions.get(functions, ReduxDensityFunctions.CLOUDBED_NOISE),
                        10,
                        ReduxDensityFunctions.get(functions, ReduxDensityFunctions.CLOUDBED_Y_OFFSET),
                        10, Optional.empty()));


        register(context, BLIGHT_ROCK, ReduxFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                        .add(AetherFeatureStates.HOLYSTONE, 12)
                        .add(drops(ReduxBlocks.BLIGHTMOSS_HOLYSTONE), 9)
                        .add(drops(ReduxBlocks.BLIGHTMOSS_BLOCK), 5)
                        .add(AetherFeatureStates.ZANITE_ORE, 1)
                ), Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)), Optional.empty()
                        ));

        register(context, BLIGHTMOSS_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(drops(ReduxBlocks.BLIGHTSHADE), 11)
                                .add(drops(ReduxBlocks.SPIROLYCTIL), 7)
                                .add(drops(ReduxBlocks.BLIGHTMOSS_CARPET), 75)
                                .add(drops(ReduxBlocks.SHORT_AETHER_GRASS), 150)
                                .add(drops(ReduxBlocks.LUXWEED), 120)
                )));
        register(context, FUNGAL_VEGETATION, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(drops(ReduxBlocks.CLOUDCAP_MUSHLING), 40)
                                .add(drops(ReduxBlocks.JELLYSHROOM), 20)
                                .add(drops(ReduxBlocks.SHIMMERSTOOL), 15)
                                .add(drops(ReduxBlocks.FUNGAL_CARPET), 75)
                                .add(drops(ReduxBlocks.AVELIUM_SPROUTS), 150)
                                .add(drops(ReduxBlocks.AVELIUM_ROOTS), 120)
                )));

        register(context, BLIGHTED_SKYROOT_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                new StraightTrunkPlacer(4, 2, 0),
                prov(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES),
                new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(1, 0, 1)
        ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT)).build());
        register(context, BLIGHTSHADE_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(16, 7, 3, prov(ReduxBlocks.BLIGHTSHADE),
                        BlockPredicate.matchesBlocks(AetherBlocks.AETHER_GRASS_BLOCK.get())));

        WeightedStateProvider p = new WeightedStateProvider(
                SimpleWeightedRandomList.<BlockState>builder()
                        .add(drops(WoodHandlers.BLIGHTWILLOW.log), 7)
                        .add(drops(WoodHandlers.BLIGHTWILLOW.sporingLog.orElseThrow()), 1)
        );
        register(context, BLIGHTWILLOW_TREE, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                p,
                new StraightTrunkPlacer(8, 4, 3),
                prov(ReduxBlocks.BLIGHTWILLOW_LEAVES),
                new BlightwillowFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                new TwoLayersFeatureSize(6, 0, 4)
        ).decorators(
                List.of(
                        new BlightwillowRootsTrunkDecorator(p, prov(naturalDrops(WoodHandlers.BLIGHTWILLOW.logWall)), UniformInt.of(1, 4)),
                        new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE), 7, 3, 32)
                )
        ).ignoreVines().dirt(BlockStateProvider.simple(Blocks.AIR)).build());
        register(context, DAGGERBLOOM_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, prov(ReduxBlocks.DAGGERBLOOM)));
        register(context, SPLITFERN_PATCH, Feature.FLOWER,
                randomPatch(24, 9, 3, prov(ReduxBlocks.SPLITFERN)));
        register(context, CLOUDCAP_MUSHLING_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(16, 5, 3, prov(ReduxBlocks.CLOUDCAP_MUSHLING),
                        BlockPredicate.matchesTag(ReduxTags.Blocks.AEVELIUM_GRASSES)));
        register(context, THERATIP_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(14, 7, 3, prov(ReduxBlocks.THERATIP),
                        BlockPredicate.matchesTag(ReduxTags.Blocks.COARSE_AETHER_DIRT)));

        register(context, AEROGEL_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(AetherBlocks.AEROGEL), 24, 0F));
        register(context, FROSTED_PURPLE_FLOWER_PATCH, Feature.FLOWER,
                randomPatch(16, 7, 3, prov(AetherBlocks.PURPLE_FLOWER)));
        register(context, GILDED_HOLYSTONE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.GILDED_HOLYSTONE), 24, 0.3F));
        register(context, BLIGHTMOSS_HOLYSTONE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.BLIGHTMOSS_HOLYSTONE), 24, 0.3F));
        register(context, LARGE_ICESTONE_CHUNK, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(AetherBlocks.ICESTONE), 48, 0.0F));
        register(context, GILDED_LEAF_PATCH, Feature.RANDOM_PATCH,
                blockTestPatch(8, 3, 3, createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE),
                        BlockPredicate.wouldSurvive(ReduxBlocks.AURUM.get().defaultBlockState(), BlockPos.ZERO)));

        register(context, GILDED_ROCK, ReduxFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                        .add(AetherFeatureStates.HOLYSTONE, 5)
                        .add(drops(ReduxBlocks.GILDED_HOLYSTONE), 3)
                        .add(AetherFeatureStates.AMBROSIUM_ORE, 1)
                ), Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)), Optional.empty()));



        register(context, LEGACY_GILDED_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(AetherFeatureStates.SKYROOT_LOG, 3)
                                        .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                        ),
                        new GoldenOakTrunkPlacer(10, 0, 0),
                        prov(ReduxBlocks.GILDED_OAK_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.25F,
                                        prov(ReduxBlocks.GILDED_VINES_PLANT),
                                        prov(ReduxBlocks.GILDED_VINES),
                                        UniformInt.of(1, 5),
                                        Optional.of(Conditions.VINES)),
                                new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());
/*

        register(context, SMALL_GILDED_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(AetherFeatureStates.SKYROOT_LOG, 3)
                                        .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                        ),
                        new StraightTrunkPlacer(4, 2, 0),
                        prov(ReduxBlocks.GILDED_OAK_LEAVES),
                        new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.25F,
                                        prov(ReduxBlocks.GILDED_VINES_PLANT),
                                        prov(ReduxBlocks.GILDED_VINES),
                                        UniformInt.of(1, 3),
                                        Optional.of(Conditions.VINES)),
                                new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());

        register(context, LARGE_GILDED_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(AetherFeatureStates.SKYROOT_LOG, 3)
                                        .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                        ),
                        new GenesisHookedTrunkPlacer(8, 14, 14),
                        prov(ReduxBlocks.GILDED_OAK_LEAVES),
                        new GenesisHookedFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 1, 4)
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.25F,
                                        prov(ReduxBlocks.GILDED_VINES_PLANT),
                                        prov(ReduxBlocks.GILDED_VINES),
                                        UniformInt.of(1, 5),
                                        Optional.of(Conditions.VINES)),
                                new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());
*/

        register(context, ReduxFeatureConfig.FANCY_GILDED_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        new WeightedStateProvider(
                                SimpleWeightedRandomList.<BlockState>builder()
                                        .add(AetherFeatureStates.SKYROOT_LOG, 3)
                                        .add(AetherFeatureStates.GOLDEN_OAK_LOG, 1)
                        ),
                        new FancyTrunkPlacer(10, 0, 0),
                        prov(ReduxBlocks.GILDED_OAK_LEAVES),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.25F,
                                        prov(ReduxBlocks.GILDED_VINES_PLANT),
                                        prov(ReduxBlocks.GILDED_VINES),
                                        UniformInt.of(1, 5),
                                        Optional.of(Conditions.VINES)
                                ), new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());

        register(context, GOLDEN_OAK_TREE_OVERRIDE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LOG),
                        new GoldenOakTrunkPlacer(10, 0, 0),
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LEAVES),
                        new GoldenOakFoliagePlacer(ConstantInt.of(3), ConstantInt.of(1), ConstantInt.of(7)),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines().dirt(prov(AetherBlocks.AETHER_DIRT))
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.25F,
                                        prov(ReduxBlocks.GOLDEN_VINES_PLANT),
                                        prov(ReduxBlocks.GOLDEN_VINES),
                                        UniformInt.of(1, 5),
                                        Optional.of(Conditions.VINES)
                                ), new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32))).ignoreVines().build());

        register(context, GOLD_DUNGEON_FLOWER_OVERRIDE, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(new WeightedStateProvider(
                        new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(ReduxBlocks.INFERNIA.get().defaultBlockState(), 2)
                                .add(ReduxBlocks.FLAREBLOSSOM.get().defaultBlockState(), 1))));


        register(context, SKYROOT_PINE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(5, 5, 0),
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                        new GenesisPineFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 0, 2))
                        .ignoreVines().build());

        register(context, ReduxFeatureConfig.FANCY_GOLDEN_OAK_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.GOLDEN_OAK_LOG),
                        new FancyTrunkPlacer(10, 0, 0),
                        prov(AetherBlocks.GOLDEN_OAK_LEAVES),
                        new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                        new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(10))
                ).ignoreVines()
                        .decorators(Arrays.asList(
                                new EnchantedVineDecorator(0.25F,
                                        prov(ReduxBlocks.GOLDEN_VINES_PLANT),
                                        prov(ReduxBlocks.GOLDEN_VINES),
                                        UniformInt.of(1, 3),
                                        Optional.of(Conditions.VINES)
                                ), new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32))).build());

        register(context, CRYSTAL_TREE_OVERRIDE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(WoodHandlers.CRYSTAL.log),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.CRYSTAL_LEAVES, 4).add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1).build()),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, HOLIDAY_TREE_OVERRIDE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(WoodHandlers.GLACIA.log),
                        new StraightTrunkPlacer(9, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.HOLIDAY_LEAVES, 4).add(AetherFeatureStates.DECORATED_HOLIDAY_LEAVES, 1).build()),
                        new HolidayFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(8)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines()
                        .decorators(ImmutableList.of(new HolidayTreeDecorator(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>().add(AetherFeatureStates.SNOW, 10).add(AetherFeatureStates.PRESENT, 1).build()))))
                        .build());

        register(context, GLACIA_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(WoodHandlers.GLACIA.log),
                        new StraightTrunkPlacer(9, 10, 0),
                        prov(ReduxBlocks.GLACIA_LEAVES),
                        new GlaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, CRYSTAL_LEAF_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(WoodHandlers.CRYSTAL.log),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        prov(AetherBlocks.CRYSTAL_LEAVES),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, CRYSTAL_RARE_FRUIT_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(WoodHandlers.CRYSTAL.log),
                        new CrystalTreeTrunkPlacer(7, 0, 0),
                        new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                                .add(AetherFeatureStates.CRYSTAL_LEAVES, 9)
                                .add(AetherFeatureStates.CRYSTAL_FRUIT_LEAVES, 1)),
                        new CrystalFoliagePlacer(ConstantInt.of(0), ConstantInt.of(0), ConstantInt.of(6)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());

        register(context, LIGHTROOTS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration(ReduxBlocks.LIGHTROOTS.get(),
                        30, true, false, true, 0.5F,
                        blocks.get(AetherTags.Blocks.AETHER_DIRT).orElseThrow()));

        register(context, PURPLE_GLACIA_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        prov(WoodHandlers.GLACIA.log),
                        new StraightTrunkPlacer(5, 2, 0),
                        prov(ReduxBlocks.PURPLE_GLACIA_LEAVES),
                        new GlaciaFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), ConstantInt.of(1)),
                        new TwoLayersFeatureSize(1, 0, 1)).ignoreVines().build());


        register(context, GLOWSPROUTS_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(24, 7, 3, prov(ReduxBlocks.LUXWEED),
                        BlockPredicate.matchesBlocks(AetherBlocks.AETHER_GRASS_BLOCK.get())));

        register(context, CORRUPTED_VINES_PATCH, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 7, 3, PlacementUtils.onlyWhenEmpty(
                        ReduxFeatures.UPWARD_VINE.get(), new UpwardVineFeature.Config(
                                prov(ReduxBlocks.CORRUPTED_VINES_PLANT),
                                prov(ReduxBlocks.CORRUPTED_VINES),
                                BiasedToBottomInt.of(2, 12)
                        )))
        );

        register(context, GOLDEN_CLOVER_PATCH, Feature.FLOWER,
                patchNoCoarse(12, 7, 3, prov(ReduxBlocks.GOLDEN_CLOVER)));

        register(context, GOLDEN_LEAF_PATCH, Feature.RANDOM_PATCH,
                blockTestPatch(8, 3, 3, createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE),
                        BlockPredicate.wouldSurvive(ReduxBlocks.AURUM.get().defaultBlockState(), BlockPos.ZERO)));


        register(context, SKYFIELDS_ROCK, ReduxFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(prov(AetherFeatureStates.HOLYSTONE),
                        Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)), Optional.empty()));

        // Todo: Fungal Holystone?
        register(context, GLIMMERSTOOL_ROCK, ReduxFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(prov(AetherFeatureStates.HOLYSTONE),
                        Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)),
                        Optional.of(patchData(32, 11, 3, prov(ReduxBlocks.SHIMMERSTOOL), BlockPredicate.matchesTag(ReduxTags.Blocks.AEVELIUM_GRASSES), Optional.of(0.75D)))));

        register(context, GLIMMERSTOOL_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(32, 7, 3, prov(ReduxBlocks.SHIMMERSTOOL), BlockPredicate.matchesTag(ReduxTags.Blocks.AEVELIUM_GRASSES)));

        register(context, IRIDIA_PATCH, Feature.FLOWER,
                randomPatch(24, 7, 3, prov(ReduxBlocks.IRIDIA)));
        register(context, XAELIA_PATCH, Feature.FLOWER,
                randomPatch(32, 7, 3, prov(ReduxBlocks.XAELIA_PATCH)));

        register(context, LARGE_CLOUDCAP, ReduxFeatures.CLOUDCAP.get(),
                new CloudcapFeature.Config(
                        prov(ReduxBlocks.CLOUD_CAP_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.DOWN, false)),
                        prov(ReduxBlocks.CLOUDCAP_SPORES),
                        prov(WoodHandlers.CLOUDCAP.log),
                        prov(naturalDrops(WoodHandlers.CLOUDCAP.logWall)),
                        UniformInt.of(17, 21),
                        UniformInt.of(1, 4),
                        UniformInt.of(1, 3),
                        UniformInt.of(7, 9))
        );
        register(context, LARGE_MUSHROOMS, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                                PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_JELLYSHROOM), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.JELLYSHROOM.get())), 0.3F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_CLOUDCAP), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.CLOUDCAP_MUSHLING.get()))));
        register(context, LARGE_JELLYSHROOM, ReduxFeatures.JELLYSHROOM.get(),
                new JellyshroomFeature.Config(
                        prov(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK),
                        prov(WoodHandlers.JELLYSHROOM.log),
                        UniformInt.of(7, 9)
                        ));

        register(context, LUMINA_PATCH, Feature.FLOWER,
                randomPatch(12, 7, 3, prov(ReduxBlocks.LUMINA)));

        register(context, MOSSY_HOLYSTONE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(AetherBlocks.MOSSY_HOLYSTONE), 24, 0.3F));

        register(context, MOSSY_ROCK, ReduxFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(new WeightedStateProvider(new SimpleWeightedRandomList.Builder<BlockState>()
                        .add(AetherFeatureStates.HOLYSTONE, 5)
                        .add(drops(AetherBlocks.MOSSY_HOLYSTONE), 3)
                ), Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)), Optional.empty()));

        register(context, ICESTONE_ROCK, ReduxFeatures.LARGE_ROCK.get(),
                new LargeRockFeature.Config(prov(AetherFeatureStates.ICESTONE),
                        Optional.of(blocks.getOrThrow(ReduxTags.Blocks.ROCK_REPLACEABLE)), Optional.empty()));


        register(context, FIELDSPROOT_TREE, ReduxFeatures.FIELDSPROOT_TREE.get(),
                new FieldsprootTreeFeature.Config(
                        prov(ReduxBlocks.FIELDSPROOT_LEAVES),
                        prov(WoodHandlers.FIELDSPROOT.log),
                        prov(naturalDrops(WoodHandlers.FIELDSPROOT.wood)),
                        new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                                .add(ConstantInt.of(0), 5)
                                .add(ConstantInt.of(1), 4)
                                .add(ConstantInt.of(2), 3)
                                .add(ConstantInt.of(3), 2)
                                .add(ConstantInt.of(4), 1)
                                .build()), 7, 3, 32));

        register(context, SKYROOT_BUSH, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                prov(AetherFeatureStates.SKYROOT_LOG),
                new StraightTrunkPlacer(1, 0, 0),
                prov(AetherFeatureStates.SKYROOT_LEAVES),
                new BushFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), 2),
                new TwoLayersFeatureSize(0, 0, 0)
        ).ignoreVines().build());


        register(context, SURFACE_RULE_WATER_LAKE, ReduxFeatures.SURFACE_RULE_LAKE.get(),
                new SurfaceRuleLakeFeature.Config(BlockStateProvider.simple(Blocks.WATER)));

        register(context, BLIGHT_LAKE, ReduxFeatures.SURFACE_RULE_LAKE.get(),
                new SurfaceRuleLakeFeature.Config(BlockStateProvider.simple(Blocks.WATER)));
        register(context, BLIGHT_SPRING, Feature.SPRING,
                AetherConfiguredFeatureBuilders.spring(Fluids.WATER.defaultFluidState(), true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, AetherBlocks.HOLYSTONE.get(), AetherBlocks.AETHER_DIRT.get())));

        register(context, WYNDSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, prov(ReduxBlocks.WYNDSPROUTS)));
        register(context, GENESIS_WYNDSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, prov(ReduxBlocks.WYNDSPROUTS)));
        register(context, GENESIS_SKYSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, prov(ReduxBlocks.SKYSPROUTS)));
        register(context, SKYSPROUTS_PATCH, Feature.FLOWER,
                randomPatch(24, 5, 3, prov(ReduxBlocks.SKYSPROUTS)));
        register(context, SPIROLYCTIL_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(16, 7, 3, prov(ReduxBlocks.SPIROLYCTIL),
                        BlockPredicate.matchesBlocks(AetherBlocks.AETHER_GRASS_BLOCK.get())));
        register(context, JELLYSHROOM_PATCH, Feature.FLOWER,
                blockBelowPlacementPatch(8, 7, 3, prov(ReduxBlocks.JELLYSHROOM),
                        BlockPredicate.matchesTag(ReduxTags.Blocks.AEVELIUM_GRASSES)));


        register(context, BLIGHT_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(BLIGHTED_SKYROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get())), 0.25F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(BLIGHTWILLOW_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.BLIGHTWILLOW_SAPLING.get()))));

        register(context, GROVE_TREES_LEGACY, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.67F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LEGACY_GILDED_OAK_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get()))));


        register(context, GROVE_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GILDED_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())), 0.25F),
        new WeightedPlacedFeature(
                PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GILDED_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())), 0.125F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));


        register(context, GRASSLAND_TREES_LEGACY, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.33F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LEGACY_GILDED_OAK_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get()))));

        register(context, GRASSLAND_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.65F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GILDED_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get()))));

        register(context, SMALL_GILDED_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        prov(ReduxBlocks.GILDED_OAK_LEAVES),
                        new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).decorators(List.of(new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).ignoreVines().build());

        register(context, LARGE_GILDED_TREE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new GenesisHookedTrunkPlacer(8, 14, 14),
                        prov(ReduxBlocks.GILDED_OAK_LEAVES),
                        new GenesisHookedFoliagePlacer(ConstantInt.of(2), ConstantInt.of(1), ConstantInt.of(2)),
                        new TwoLayersFeatureSize(2, 1, 4)
                ).ignoreVines().decorators(List.of(new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GILDED_LEAF_PILE), 7, 3, 32))).build());

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
                        new EnchantedVineDecorator(0.25F,
                                prov(ReduxBlocks.GOLDEN_VINES_PLANT),
                                prov(ReduxBlocks.GOLDEN_VINES),
                                UniformInt.of(1, 3), Optional.of(Conditions.VINES)),
                        new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32)
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
                        new EnchantedVineDecorator(0.25F,
                                prov(ReduxBlocks.GOLDEN_VINES_PLANT),
                                prov(ReduxBlocks.GOLDEN_VINES),
                                UniformInt.of(1, 5), Optional.of(Conditions.VINES)),
                        new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32)
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
                ).ignoreVines().decorators(List.of(new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32))).build());

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
                ).ignoreVines().decorators(List.of(new PatchTreeDecorator(createLeafPileLayers(ReduxBlocks.GOLDEN_LEAF_PILE), 7, 3, 32))).build());

        register(context, GOLDEN_OAK_TREE_OVERRIDE, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GOLDEN_OAK_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.35F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GOLDEN_OAK_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

        register(context, GROVE_GOLDEN_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GOLDEN_VINE_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get())), 0.35F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GOLDEN_VINE_TREE), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));

        register(context, GROVE_GILDED_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(SMALL_GILDED_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())), 0.60F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(LARGE_GILDED_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get()))));

        register(context, GROVE_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(GROVE_GILDED_TREES), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GILDED_OAK_SAPLING.get())), 0.375F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(GROVE_GOLDEN_TREES), PlacementUtils.filteredByBlockSurvival(AetherBlocks.GOLDEN_OAK_SAPLING.get()))));


        register(context, FROSTED_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(PURPLE_GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.PURPLE_GLACIA_SAPLING.get())), 0.25F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get()))));

        register(context, GLACIAL_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(
                        PlacementUtils.inlinePlaced(configs.getOrThrow(GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.GLACIA_SAPLING.get())), 0.05F)),
                        PlacementUtils.inlinePlaced(configs.getOrThrow(PURPLE_GLACIA_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.PURPLE_GLACIA_SAPLING.get()))));


        register(context, SKYFIELDS_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(ReduxFeatureConfig.CRYSTAL_RARE_FRUIT_TREE),
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.05F),
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(ReduxFeatureConfig.CRYSTAL_LEAF_TREE),
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.3F)
                ),
                        PlacementUtils.inlinePlaced(
                                configs.getOrThrow(FIELDSPROOT_TREE), PlacementUtils.filteredByBlockSurvival(ReduxBlocks.FIELDSPROOT_SAPLING.get()))
                ));

        register(context, SHRUBLANDS_TREES, Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(
                        new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configs.getOrThrow(ReduxFeatureConfig.SKYROOT_PINE),
                                PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get())), 0.05F)
                ),
                        PlacementUtils.inlinePlaced(
                                configs.getOrThrow(SKYROOT_BUSH), PlacementUtils.filteredByBlockSurvival(AetherBlocks.SKYROOT_SAPLING.get()))
                ));

        register(context, VERIDIUM_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.VERIDIUM_ORE), 9, 0.1F));

        register(context, DIVINITE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.DIVINITE), 48, 0.0F));

        register(context, SENTRITE_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(AetherTags.Blocks.HOLYSTONE),
                drops(ReduxBlocks.SENTRITE), 48, 0.0F));

        register(context, TALL_GRASS_PATCH_OVERRIDE, Feature.NO_OP, FeatureConfiguration.NONE);
        register(context, GRASS_PATCH_OVERRIDE, Feature.RANDOM_PATCH,
                patchNoCoarse(32, 7, 3, prov(ReduxBlocks.SHORT_AETHER_GRASS)));

        register(context, GRASS_PATCH_BONEMEAL, Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(prov(ReduxBlocks.SHORT_AETHER_GRASS)));

        register(context, SKYROOT_TREE_OVERRIDE, Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LOG),
                        new StraightTrunkPlacer(4, 2, 0),
                        BlockStateProvider.simple(AetherFeatureStates.SKYROOT_LEAVES),
                        new SkyrootFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 1)
                ).ignoreVines().build());


    }

    private static RandomPatchConfiguration randomPatch(int tries, int xz, int y, BlockStateProvider state) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(state)));
    }
    private static RandomPatchConfiguration patchNoCoarse(int tries, int xz, int y, BlockStateProvider state) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ReduxFeatures.TEST_BELOW_BLOCK.get(), new PredicateStateConfig(state, BlockPredicate.not(BlockPredicate.matchesTag(ReduxTags.Blocks.COARSE_AETHER_DIRT)))));
    }
    private static RandomPatchConfiguration blockBelowPlacementPatch(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate) {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ReduxFeatures.TEST_BELOW_BLOCK.get(), new PredicateStateConfig(state, predicate)));
    }

    private static LargeRockFeature.PatchData patchData(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate, Optional<Double> chance) {
        return new LargeRockFeature.PatchData(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ReduxFeatures.TEST_BELOW_BLOCK.get(), new PredicateStateConfig(state, predicate)), chance);
    }
    
    private static RandomPatchConfiguration blockTestPatch(int tries, int xz, int y, BlockStateProvider state, BlockPredicate predicate)
    {
        return new RandomPatchConfiguration(tries, xz, y, PlacementUtils.onlyWhenEmpty(
                ReduxFeatures.TEST_AT_BLOCK.get(), new PredicateStateConfig(state, predicate)));
    }
    private static BlockStateProvider createLeafPileLayers(BlockState state)
    {
        if (state.hasProperty(ReduxStates.LEAF_LAYERS)) {
            return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 1), 6)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 2), 5)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 3), 4)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 4), 3)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 5), 2)
                    .add(state.setValue(ReduxStates.LEAF_LAYERS, 6), 1)
            ); } else {
            return BlockStateProvider.simple(state);
        }
    }

    private static BlockStateProvider createFlowerbedProvider(BlockState state) {
        if (state.hasProperty(PinkPetalsBlock.FACING) || state.hasProperty(PinkPetalsBlock.AMOUNT)) {
            SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
            if (state.hasProperty(PinkPetalsBlock.FACING))
            {
                for (Direction d : PinkPetalsBlock.FACING.getPossibleValues())
                {
                    if (state.hasProperty(PinkPetalsBlock.AMOUNT))
                    {
                        BlockState temp = state.setValue(PinkPetalsBlock.FACING, d);
                        for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                            builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i);
                        }
                    } else {
                        builder.add(state.setValue(PinkPetalsBlock.FACING, d), 1);
                    }
                }
            } else if (state.hasProperty(PinkPetalsBlock.AMOUNT))
            {
                for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                    builder.add(state.setValue(PinkPetalsBlock.AMOUNT, i), i);
                }
            }
            return new WeightedStateProvider(builder);
        }
        return BlockStateProvider.simple(state);
    }



    private static BlockStateProvider createFlowerbedProvider(BlockState state, BlockState uncommon) {
        if (state.hasProperty(PinkPetalsBlock.FACING) || state.hasProperty(PinkPetalsBlock.AMOUNT) || uncommon.hasProperty(PinkPetalsBlock.FACING) || uncommon.hasProperty(PinkPetalsBlock.AMOUNT)) {
            SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
            if (state.hasProperty(PinkPetalsBlock.FACING))
            {
                for (Direction d : PinkPetalsBlock.FACING.getPossibleValues())
                {
                    if (state.hasProperty(PinkPetalsBlock.AMOUNT))
                    {
                        BlockState temp = state.setValue(PinkPetalsBlock.FACING, d);
                        for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                            builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                        }
                    } else {
                        builder.add(state.setValue(PinkPetalsBlock.FACING, d), 6);
                    }
                }
            } else if (state.hasProperty(PinkPetalsBlock.AMOUNT))
            {
                for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                    builder.add(state.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                }
            }
            else {
                builder.add(state, 6);
            }



            if (uncommon.hasProperty(PinkPetalsBlock.FACING))
            {
                for (Direction d : PinkPetalsBlock.FACING.getPossibleValues())
                {
                    if (uncommon.hasProperty(PinkPetalsBlock.AMOUNT))
                    {
                        BlockState temp = uncommon.setValue(PinkPetalsBlock.FACING, d);
                        for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                            builder.add(temp.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                        }
                    } else {
                        builder.add(uncommon.setValue(PinkPetalsBlock.FACING, d), 3);
                    }
                }
            } else if (uncommon.hasProperty(PinkPetalsBlock.AMOUNT))
            {
                for (int i : PinkPetalsBlock.AMOUNT.getPossibleValues()) {
                    builder.add(uncommon.setValue(PinkPetalsBlock.AMOUNT, i), i * 3);
                }
            }
            else {
                builder.add(uncommon, 2);
            }
            return new WeightedStateProvider(builder);
        }

        return BlockStateProvider.simple(state);
    }
    private static BlockStateProvider createLeafPileLayers(RegistryObject<? extends Block> block)
    {
        return createLeafPileLayers(drops(block));
    }

    private static BlockStateProvider createPetals(RegistryObject<? extends Block> block)
    {
        BlockState state = drops(block);
        if (state.hasProperty(ReduxStates.PETAL_1) && state.hasProperty(ReduxStates.PETAL_2) && state.hasProperty(ReduxStates.PETAL_3) && state.hasProperty(ReduxStates.PETAL_4)) {
            return new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(state.setValue(ReduxStates.PETAL_2, PetalPrismaticness.ZERO), 4)
                    .add(state.setValue(ReduxStates.PETAL_2, PetalPrismaticness.ZERO).setValue(ReduxStates.PETAL_3, PetalPrismaticness.ZERO), 3)
                    .add(state.setValue(ReduxStates.PETAL_2, PetalPrismaticness.ZERO).setValue(ReduxStates.PETAL_3, PetalPrismaticness.ZERO).setValue(ReduxStates.PETAL_4, PetalPrismaticness.ZERO), 2)

            ); } else {
            return BlockStateProvider.simple(state);
        }
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

    private static String name(RegistryObject<?> reg)
    {
        return reg.getId().getPath();
    }

    private static BlockStateProvider prov(BlockState state) {
        return BlockStateProvider.simple(drops(state));
    }

    private static BlockStateProvider prov(RegistryObject<? extends Block> block) {
        return prov(block.get().defaultBlockState());
    }

    private static BlockState drops(BlockState state) {
        return state.hasProperty(AetherBlockStateProperties.DOUBLE_DROPS) ? state.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true) : state;
    }

    private static BlockState drops(RegistryObject<? extends Block> block) {
        return drops(block.get().defaultBlockState());
    }

    private static BlockState naturalDrops(RegistryObject<? extends Block> block) {
        BlockState b = block.get().defaultBlockState();
        return b.hasProperty(ReduxStates.NATURAL_GEN) ? drops(b.setValue(ReduxStates.NATURAL_GEN, true)) : drops(b);
    }
}
