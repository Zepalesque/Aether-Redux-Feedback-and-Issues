package net.zepalesque.redux;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.dispenser.DispenseUsableItemBehavior;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import com.aetherteam.aether_genesis.item.GenesisItems;
import com.aetherteam.cumulus.CumulusConfig;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.builderdog.ancient_aether.entity.AncientAetherEntityTypes;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import net.minecraftforge.resource.PathPackResources;
import net.zepalesque.redux.advancement.ReduxAdvancementSounds;
import net.zepalesque.redux.advancement.trigger.ReduxAdvancementTriggers;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.api.condition.AbstractCondition;
import net.zepalesque.redux.api.condition.ConditionSerializers;
import net.zepalesque.redux.api.packconfig.PackConfigBootstrap;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.ReduxSoundTypes;
import net.zepalesque.redux.block.util.dispenser.ShellShinglesDispenserBehavior;
import net.zepalesque.redux.blockentity.ReduxBlockEntityTypes;
import net.zepalesque.redux.blockhandlers.WoodHandlers;
import net.zepalesque.redux.builtin.BuiltinPackUtils;
import net.zepalesque.redux.client.ReduxClient;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.client.ReduxMenus;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import net.zepalesque.redux.client.render.ReduxRenderers;
import net.zepalesque.redux.client.render.entity.BlightbunnyRenderer;
import net.zepalesque.redux.client.render.geo.MykapodRenderer;
import net.zepalesque.redux.client.resource.ReduxOverridesPackResources;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.pack.ReduxPackConfig;
import net.zepalesque.redux.data.ReduxAdvancementData;
import net.zepalesque.redux.data.ReduxBlockstateData;
import net.zepalesque.redux.data.ReduxItemModelData;
import net.zepalesque.redux.data.ReduxLanguageData;
import net.zepalesque.redux.data.ReduxLootModifierData;
import net.zepalesque.redux.data.ReduxRecipeData;
import net.zepalesque.redux.data.ReduxRegistrySets;
import net.zepalesque.redux.data.ReduxSoundData;
import net.zepalesque.redux.data.loot.ReduxLootData;
import net.zepalesque.redux.data.tags.ReduxAdvancementOverrideTagData;
import net.zepalesque.redux.data.tags.ReduxBiomeTagsData;
import net.zepalesque.redux.data.tags.ReduxBlockTagsData;
import net.zepalesque.redux.data.tags.ReduxDamageTypeTagData;
import net.zepalesque.redux.data.tags.ReduxEntityTypeTagData;
import net.zepalesque.redux.data.tags.ReduxItemTagsData;
import net.zepalesque.redux.data.tags.ReduxSoundEventTagData;
import net.zepalesque.redux.effect.ReduxEffects;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.entity.dataserializer.ReduxDataSerializers;
import net.zepalesque.redux.event.hook.SwetHooks;
import net.zepalesque.redux.event.listener.MobSoundListener;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.loot.condition.ReduxLootConditions;
import net.zepalesque.redux.loot.functions.ReduxLootFunctions;
import net.zepalesque.redux.loot.modifiers.ReduxLootModifiers;
import net.zepalesque.redux.misc.ReduxPackSources;
import net.zepalesque.redux.misc.ReduxPotions;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;
import net.zepalesque.redux.recipe.condition.DataRecipeCondition;
import net.zepalesque.redux.recipe.serializer.ReduxRecipeSerializers;
import net.zepalesque.redux.world.biome.ReduxRegion;
import net.zepalesque.redux.world.biome.modifier.ReduxBiomeModifierCodecs;
import net.zepalesque.redux.world.biome.surfacerule.ReduxConditionSources;
import net.zepalesque.redux.world.carver.ReduxCarvers;
import net.zepalesque.redux.world.density.ReduxDensityFunctionTypes;
import net.zepalesque.redux.world.feature.ReduxFeatures;
import net.zepalesque.redux.world.placement.ReduxPlacementModifiers;
import net.zepalesque.redux.world.stateprov.ReduxStateProviders;
import net.zepalesque.redux.world.structure.ReduxStructureTypes;
import net.zepalesque.redux.world.tree.decorator.ReduxTreeDecorators;
import net.zepalesque.redux.world.tree.foliage.ReduxFoliagePlacers;
import net.zepalesque.redux.world.tree.root.ReduxRootPlacers;
import net.zepalesque.redux.world.tree.trunk.ReduxTrunkPlacers;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import teamrazor.aeroblender.AeroBlenderConfig;
import terrablender.api.Regions;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Mod(Redux.MODID)
public class Redux {
    public static final String MODID = "aether_redux";
    public static final String DISPLAY = ModList.get().getModFileById(MODID).getMods().get(0).getDisplayName();
    public static final String DISPLAY_LOWERCASE_THE = DISPLAY.replace("The", "the");
    public static final String VERSION_ID = ModList.get().getModFileById(MODID).versionString();

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final int REDUX_PURPLE = 0x9384F4;

    public static final RandomSource RAND = RandomSource.create();
    public static final Collection<WoodHandler> WOOD_HANDLERS = new ArrayList<>();

    @Nullable
    public static ReduxPackConfig packConfig;


    public Redux() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Low priority so that we don't load Sheets.class before Supplementaries
        bus.addListener(EventPriority.LOW, this::commonSetup);
        bus.addListener(this::clientSetup);
        DistExecutor.unsafeRunForDist(() -> () -> {
            bus.addListener(EventPriority.LOWEST, ReduxColors::blockColors);
            bus.addListener(ReduxColors::itemColors);
            bus.addListener(ReduxColors::resolvers);
            ReduxMenus.MENUS.register(bus);
            ReduxClient.registerMolangQueries();
            return true;
        }, () -> () -> false);
        bus.addListener(EventPriority.HIGH, this::packSetup);
        bus.addListener(this::dataSetup);
        bus.addListener(this::registerRecipeSerializers);
        packConfig = PackConfigBootstrap.register("aether_redux_pack_config", ReduxPackConfig::new);
        ReduxBlocks.BLOCKS.register(bus);
        ReduxItems.ITEMS.register(bus);
        ReduxSoundEvents.SOUNDS.register(bus);
        ReduxBlockEntityTypes.BLOCK_ENTITY_TYPES.register(bus);
        ReduxEntityTypes.ENTITY_TYPES.register(bus);
        ConditionSerializers.CODECS.register(bus);
        ReduxBiomeModifierCodecs.CODECS.register(bus);
        ReduxLootModifiers.LOOT_MODIFIERS.register(bus);
        ReduxEffects.EFFECTS.register(bus);
        ReduxRecipeTypes.RECIPE_TYPES.register(bus);
        ReduxRecipeSerializers.RECIPE_SERIALIZERS.register(bus);
        ReduxCarvers.CARVERS.register(bus);
        ReduxParticleTypes.PARTICLES.register(bus);
        ReduxFoliagePlacers.FOLIAGE_PLACERS.register(bus);
        ReduxFeatures.FEATURES.register(bus);
        ReduxDensityFunctionTypes.DENSITY_FUNCTIONS.register(bus);
        ReduxTreeDecorators.TREE_DECORATORS.register(bus);
        ReduxTrunkPlacers.TRUNK_PLACERS.register(bus);
        ReduxRootPlacers.ROOT_PLACERS.register(bus);
        ReduxLootConditions.LOOT_CONDITION_TYPES.register(bus);
        ReduxStructureTypes.STRUCTURE_TYPES.register(bus);
        ReduxConditionSources.CONDITIONS.register(bus);
        ReduxStateProviders.PROVIDERS.register(bus);
        ReduxDataSerializers.SERIALIZERS.register(bus);
        ReduxPotions.POTIONS.register(bus);
        ReduxAdvancementSounds.SOUNDS.register(bus);
        ReduxBlocks.registerPots();
        ReduxLootFunctions.LOOT_FUNCTION_TYPES.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(MobSoundListener.class);
        WoodHandlers.init();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ReduxConfig.COMMON_SPEC, "aether_redux_common.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ReduxConfig.CLIENT_SPEC, "aether_redux_client.toml");
    }

    public static class Keys {
        public static final ResourceKey<Registry<Codec<? extends AbstractCondition<?>>>> CONDITION_SERIALIZER = ResourceKey.createRegistryKey(new ResourceLocation(Redux.MODID, "condition_serializer"));
    }

    private void replaceBlockSounds() {
        Blocks.GLOWSTONE.soundType = ReduxSoundTypes.GLOWSTONE;
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ReduxAdvancementTriggers.init();
        DistExecutor.unsafeRunForDist(() -> () -> {
            ReduxBlocks.registerWoodTypes(true);
            return true;
        }, () -> () -> {
            ReduxBlocks.registerWoodTypes(false);
            return true;
        });
        ReduxPlacementModifiers.init();
        ReduxPacketHandler.register();
        event.enqueueWork(() -> {
            if (ReduxConfig.COMMON.first_startup_aeroblender_setup.get()) {
                AeroBlenderConfig.COMMON.vanillaAetherRegionWeight.set(0);
                AeroBlenderConfig.COMMON.vanillaAetherRegionWeight.save();
            }
            ReduxBlocks.registerFlammability();
            registerDispenserBehaviors();
            replaceBlockSounds();
            Regions.register(new ReduxRegion(new ResourceLocation(MODID, "aether_redux_region"), ReduxConfig.COMMON.region_size.get()));
            if (ReduxConfig.COMMON.smaller_mimic_hitbox.get()) {
                AetherEntityTypes.MIMIC.get().getDimensions().height = 1.25F;
                if (aetherGenesisCompat())
                {
                    GenesisEntityTypes.SKYROOT_MIMIC.get().getDimensions().height = 1.25F;
                }
            }
            PotionBrewing.addMix(Potions.POISON, ReduxItems.BLIGHTED_SPORES.get(), ReduxPotions.INTOXICATION.get());
            PotionBrewing.addMix(Potions.STRONG_POISON, ReduxItems.BLIGHTED_SPORES.get(), ReduxPotions.INTOXICATION.get());
            PotionBrewing.addMix(Potions.LONG_POISON, ReduxItems.BLIGHTED_SPORES.get(), ReduxPotions.LONG_INTOXICATION.get());
            PotionBrewing.addMix(ReduxPotions.INTOXICATION.get(), Items.REDSTONE, ReduxPotions.LONG_INTOXICATION.get());
            SwetHooks.registerParticle(AetherEntityTypes.BLUE_SWET.get(), AetherItems.SWET_BALL.get());
            SwetHooks.registerParticle(ReduxEntityTypes.VANILLA_SWET.get(), ReduxItems.VANILLA_SWET_BALL.get());
            if (Redux.aetherGenesisCompat()) {
                SwetHooks.registerParticle(GenesisEntityTypes.DARK_SWET.get(), GenesisItems.DARK_SWET_BALL.get());
                SwetHooks.registerParticle(AetherEntityTypes.GOLDEN_SWET.get(), GenesisItems.GOLDEN_SWET_BALL.get());
            } else {
                SwetHooks.registerParticle(AetherEntityTypes.GOLDEN_SWET.get(), ReduxItems.GOLDEN_SWET_BALL.get());
            }
            // TODO: Proper particles for this one
            if (Redux.ancientAetherCompat()) {
                SwetHooks.registerParticle(AncientAetherEntityTypes.FESTIVE_SWET.get(), AetherItems.SWET_BALL.get());
            }
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        EntityRenderers.register(ReduxEntityTypes.MYKAPOD.get(), MykapodRenderer::new);
        EntityRenderers.register(ReduxEntityTypes.BLIGHTBUNNY.get(), BlightbunnyRenderer::new);
        ReduxRenderers.registerCuriosRenderers();
        event.enqueueWork(
                () -> {
                    if (ReduxConfig.CLIENT.first_startup_lightmap_changes.get()) {
//                        AetherConfig.CLIENT.green_sunset.set(true);
//                        AetherConfig.CLIENT.green_sunset.save();
                        AetherConfig.CLIENT.colder_lightmap.set(true);
                        AetherConfig.CLIENT.colder_lightmap.save();
                        ReduxConfig.CLIENT.first_startup_lightmap_changes.set(false);
                        ReduxConfig.CLIENT.first_startup_lightmap_changes.save();
                    }
                    if (ReduxConfig.CLIENT.first_startup_menu_setup.get()) {
                        if (CumulusConfig.CLIENT.enable_menu_api.get()) {
                            CumulusConfig.CLIENT.active_menu.set(ReduxMenus.REDUX_MENU.getId().toString());
                            CumulusConfig.CLIENT.active_menu.save();
                            CumulusConfig.CLIENT.enable_menu_list_button.set(true);
                            CumulusConfig.CLIENT.active_menu.save();
                            AetherConfig.CLIENT.enable_aether_menu_button.set(false);
                            AetherConfig.CLIENT.enable_aether_menu_button.save();
                            AetherConfig.CLIENT.should_disable_cumulus_button.set(false);
                            AetherConfig.CLIENT.should_disable_cumulus_button.save();
                            ReduxConfig.CLIENT.first_startup_menu_setup.set(false);
                            ReduxConfig.CLIENT.first_startup_menu_setup.save();
                        }
                    }

                    ReduxMenus.cycle();
                    ReduxClient.registerItemModelProperties();
                    this.versionRefresh();
                });
    }

    public void versionRefresh() {
        if (!ReduxConfig.CLIENT.version_id.get().equals(VERSION_ID)) {
            ReduxConfig.CLIENT.version_id.set(VERSION_ID);
        }
    }


    private void registerDispenserBehaviors() {
        DispenserBlock.registerBehavior(ReduxItems.BLIGHTED_SPORES.get(), new DispenseUsableItemBehavior<>(ReduxRecipeTypes.SPORE_BLIGHTING.get()));
        DispenserBlock.registerBehavior(ReduxBlocks.SHELL_SHINGLES.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.SHELL_SHINGLE_STAIRS.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.SHELL_SHINGLE_SLAB.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.SHELL_SHINGLE_WALL.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB.get(), new ShellShinglesDispenserBehavior());
        DispenserBlock.registerBehavior(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL.get(), new ShellShinglesDispenserBehavior());
    }

/*    *//**
     * semi-arbitrarily chosen event soon before the resource packs are first reloaded in order to apply the overrides pack before it is done
     *//*
    public void applyResourcePack(RegisterKeyMappingsEvent event)
    {
        if (packConfig != null && packConfig.auto_apply.get()) {
            for (Pack pack : Minecraft.getInstance().getResourcePackRepository().getAvailablePacks()) {
                if (pack.getPackSource() == ReduxPackSources.AUTO_APPLY_RESOURCE) {
                    Minecraft.getInstance().getResourcePackRepository().addPack(pack.getId());
                }
            }
        }
    }*/

    public void registerRecipeSerializers(RegisterEvent event)
    {
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.RECIPE_SERIALIZERS))
        {
            CraftingHelper.register(DataRecipeCondition.Serializer.INSTANCE);
        }
    }

    public void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();
        generator.addProvider(event.includeServer(), new ReduxRegistrySets(packOutput, lookupProvider, ReduxRegistrySets.BUILDER, MODID));
        generator.addProvider(event.includeClient(), new ReduxBlockstateData(packOutput, MODID, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelData(packOutput, MODID, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageData(packOutput, MODID));
        generator.addProvider(event.includeClient(), new ReduxSoundData(packOutput, MODID, fileHelper));
        generator.addProvider(event.includeServer(), new ReduxRecipeData(packOutput, MODID));
        generator.addProvider(event.includeServer(), ReduxLootData.loot(packOutput));
        generator.addProvider(event.includeServer(), new ReduxDamageTypeTagData(packOutput, lookupProvider, MODID, fileHelper));
        generator.addProvider(event.includeServer(), new ReduxSoundEventTagData(packOutput, lookupProvider, MODID, fileHelper));
        generator.addProvider(event.includeServer(), new ReduxAdvancementOverrideTagData(packOutput, lookupProvider, MODID, fileHelper));
        ReduxBlockTagsData block = new ReduxBlockTagsData(packOutput, lookupProvider, MODID, fileHelper);
        generator.addProvider(event.includeServer(), block);
        generator.addProvider(event.includeServer(), new ReduxItemTagsData(packOutput, lookupProvider, MODID, block.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new ReduxBiomeTagsData(packOutput, lookupProvider, MODID, ReduxRegistrySets::patchLookup, fileHelper));
        generator.addProvider(event.includeServer(), new ReduxEntityTypeTagData(packOutput, lookupProvider, MODID, fileHelper));
        generator.addProvider(event.includeServer(), new ReduxAdvancementData(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new ReduxLootModifierData(packOutput, MODID));
        PackMetadataGenerator packMeta = new PackMetadataGenerator(packOutput);
        Map<PackType, Integer> packTypes = Map.of(PackType.SERVER_DATA, SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
        packMeta.add(PackMetadataSection.TYPE, new PackMetadataSection(Component.literal("Aether: Redux data/resources"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), packTypes));
        generator.addProvider(true, packMeta);
    }

    public  void packSetup(AddPackFindersEvent event) {


        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            PackConfigBootstrap.bootstrap();

            overridesPack(event);

            if (ModList.get().isLoaded("tipsmod")) {
                setupMandatoryPack(event, "resource/redux_tips", "Tips Mod Compat", "Tips for the Aether: Redux");
            }

        } else if (event.getPackType() == PackType.SERVER_DATA) {
            if (aetherGenesisCompat()) { this.setupMandatoryDataPack(event, "data/genesis_data", "Genesis Compat", "Compatibility with the Aether: Genesis"); }
            if (lostAetherCompat()) { this.setupMandatoryDataPack(event, "data/lost_content_data", "Lost Content Compat", "Compatibility with the Aether: Lost Content"); }
            if (deepAetherCompat()) { this.setupMandatoryDataPack(event, "data/deep_aether_data", "Deep Aether Compat", "Compatibility with Deep Aether"); }
            if (ancientAetherCompat()) { this.setupMandatoryDataPack(event, "data/ancient_aether_data", "Ancient Aether Compat", "Compatibility with Ancient Aether"); }
            if (ReduxConfig.COMMON.cloud_layer_gen.get()) { this.setupBuiltinDatapack(event, "data/cloudbed", "Redux - Cloudbed", "Highlands-like Cloudbed"); }

            if (ReduxConfig.COMMON.bronze_dungeon_upgrade.get()) { this.setupMandatoryDataPack(event, "data/dungeon_upgrades/bronze", "Bronze Dungeon Upgrade", "Configurable in config/aether_redux_common.toml"); }

            if (ReduxConfig.COMMON.gravitite_ingot.get()) { this.setupMandatoryDataPack(event, "data/gravitite_ingot", "Redux - Gravitite Ingot", "Can be disabled in the common config"); }

            if (ReduxConfig.COMMON.dungeon_stone_recipes.get()) { this.setupMandatoryDataPack(event, "data/dungeon_stone_recipes", "Redux - Light Dungeon Stone Recipes", "Can be disabled in the common config"); }

        }

    }


    public void overridesPack(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {

            if (packConfig != null) {
                String id = "resource/overrides_pack";
                String title = "pack.aether_redux.overrides.title";
                Component desc = Component.translatable("pack.aether_redux.overrides.description");
                ReduxOverridesPackResources combined = ReduxPackConfig.generate(id, title, desc);
                BuiltinPackUtils.mandatory(event, combined, id, title, desc);
            }
        }
    }

    private void setupMandatoryPack(AddPackFindersEvent event, String path, String displayName, String desc) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("packs/" + path);
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable(desc)
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + path, Component.literal("Redux - " + displayName),
                        true,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        false,
                        PackSource.BUILT_IN));
            });
        }
    }

    private void setupOptionalPack(AddPackFindersEvent event, String path, String displayName, String desc) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("packs/" + path);
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable(desc)
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + path, Component.literal("Redux - " + displayName),
                        false,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        false,
                        PackSource.BUILT_IN));
            });
        }
    }
    private void setupMandatoryDataPack(AddPackFindersEvent event, String path, String displayName, String desc) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("packs/" + path);
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable(desc)
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + path, Component.literal("Redux - " + displayName),
                        true,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        false,
                        PackSource.BUILT_IN));
            });
        }

    }
    private void setupBuiltinDatapack(AddPackFindersEvent event, String path, String displayName, String desc) {
        setupDatapack(event, path, displayName, desc, PackSource.BUILT_IN);
    }
    private void setupFeatureDatapack(AddPackFindersEvent event, String path, String displayName, String desc) {
        setupDatapack(event, path, displayName, desc, PackSource.FEATURE);
    }
    private void setupOptionalDatapack(AddPackFindersEvent event, String path, String displayName, String desc) {
        setupDatapack(event, path, displayName, desc, ReduxPackSources.OPTIONAL_DATAPACK);
    }
    private void setupDatapack(AddPackFindersEvent event, String path, String displayName, String desc, PackSource source) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("packs/" + path);
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable(desc)
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + path, Component.literal("Redux - " + displayName),
                        false,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        false,
                        source));
            });
        }
    }
    private void setupDatapackFeatureFlags(AddPackFindersEvent event, String path, String displayName, String desc, PackSource source, FeatureFlag flag, FeatureFlag... otherFlags) {
        if (event.getPackType() == PackType.SERVER_DATA) {
            Path resourcePath = ModList.get().getModFileById(MODID).getFile().findResource("packs/" + path);
            PathPackResources pack = new PathPackResources(ModList.get().getModFileById(MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable(desc)
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + path, Component.literal("Redux - " + displayName),
                        false,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(flag, otherFlags), pack.isHidden()),
                        PackType.SERVER_DATA,
                        Pack.Position.TOP,
                        false,
                        source));
            });
        }
    }

    public static ResourceLocation locate(String name) {
        return new ResourceLocation(MODID, name);
    }

    public static boolean deepAetherCompat() { return ModList.get().isLoaded("deep_aether"); }
    public static boolean lostAetherCompat() { return ModList.get().isLoaded("lost_aether_content"); }
    public static boolean aetherGenesisCompat() { return ModList.get().isLoaded("aether_genesis"); }
    public static boolean ancientAetherCompat() { return ModList.get().isLoaded("ancient_aether"); }
    public static boolean galosphereCompat() { return ModList.get().isLoaded("galosphere"); }
    public static boolean protectCompat() { return ModList.get().isLoaded("aether_protect_your_moa"); }



}
