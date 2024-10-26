package net.zepalesque.redux.world.feature;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.feature.config.*;

public class ReduxFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Redux.MODID);

    public static RegistryObject<Feature<PredicateStateConfig>> TEST_BELOW_BLOCK = FEATURES.register("test_below_block", () -> new TestBelowBlockFeature(PredicateStateConfig.CODEC));
    public static RegistryObject<Feature<PredicateStateConfig>> TEST_AT_BLOCK = FEATURES.register("test_at_block", () -> new TestAtBlockFeature(PredicateStateConfig.CODEC));
    public static RegistryObject<Feature<ConfiguredPatchBoulder.Config>> PATCH_ROCK = FEATURES.register("patch_rock", () -> new ConfiguredPatchBoulder(ConfiguredPatchBoulder.Config.CODEC));
    public static RegistryObject<Feature<CloudcapFeature.Config>> CLOUDCAP = FEATURES.register("cloudcap", () -> new CloudcapFeature(CloudcapFeature.Config.CODEC));
    public static RegistryObject<Feature<SurfaceRuleLakeFeature.Config>> SURFACE_RULE_LAKE = FEATURES.register("surface_rule_lake", () -> new SurfaceRuleLakeFeature(SurfaceRuleLakeFeature.Config.CODEC));
    public static RegistryObject<Feature<FieldsprootTreeFeature.Config>> FIELDSPROOT_TREE = FEATURES.register("fieldsprout_tree", () -> new FieldsprootTreeFeature(FieldsprootTreeFeature.Config.CODEC));
    public static RegistryObject<Feature<CloudbedFeature.Config>> CLOUDBED = FEATURES.register("cloud_layer", () -> new CloudbedFeature(CloudbedFeature.Config.CODEC));
    public static RegistryObject<Feature<JellyshroomFeature.Config>> JELLYSHROOM = FEATURES.register("jellyshroom", () -> new JellyshroomFeature(JellyshroomFeature.Config.CODEC));
    public static RegistryObject<Feature<NoneFeatureConfiguration>> TREE_AWARE_SNOW = FEATURES.register("tree_aware_snow", () -> new TreeAwareSnowLayerFeature(NoneFeatureConfiguration.CODEC));
    public static RegistryObject<Feature<UpwardVineFeature.Config>> UPWARD_VINE = FEATURES.register("upward_vine", () -> new UpwardVineFeature(UpwardVineFeature.Config.CODEC));
    public static RegistryObject<Feature<ConfiguredBoulder.Config>> CONFIGURED_BOULDER = FEATURES.register("configured_boulder", () -> new ConfiguredBoulder(ConfiguredBoulder.Config.CODEC));
}
