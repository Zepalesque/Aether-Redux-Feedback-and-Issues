package net.zepalesque.redux;

import com.google.common.reflect.Reflection;
import com.mojang.logging.LogUtils;
import io.github.razordevs.aeroblender.aether.AetherRuleCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.client.ReduxClient;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.ReduxConfigHandler;
import net.zepalesque.redux.data.ReduxData;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.components.ReduxDataComponents;
import net.zepalesque.redux.loot.modifer.ReduxLootModifiers;
import net.zepalesque.redux.network.packet.AerjumpPacket;
import net.zepalesque.redux.network.packet.ReduxPlayerSyncPacket;
import net.zepalesque.redux.network.packet.SliderSignalPacket;
import net.zepalesque.redux.recipe.ReduxRecipes;
import net.zepalesque.redux.tile.ReduxTiles;
import net.zepalesque.redux.world.biome.ReduxRegion;
import net.zepalesque.redux.world.biome.ReduxSurfaceRules;
import net.zepalesque.redux.world.feature.gen.ReduxFeatures;
import net.zepalesque.redux.world.tree.decorator.ReduxTreeDecorators;
import net.zepalesque.redux.world.tree.foliage.ReduxFoliagePlacers;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.packconfig.PackConfig;
import net.zepalesque.zenith.api.packconfig.PackUtils;
import org.slf4j.Logger;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

import java.util.ArrayList;
import java.util.Collection;

@Mod(Redux.MODID)
public class Redux {
    public static final String MODID = "aether_redux";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Collection<BlockSet> BLOCK_SETS = new ArrayList<>();

    public static final PackConfig ASSETS_CONFIG = new PackConfig(loc("asset_overrides"), PackType.CLIENT_RESOURCES, false);

    // TODO
    public static final PackConfig DATA_CONFIG = new PackConfig(loc("data_overrides"), PackType.SERVER_DATA);

    public Redux(ModContainer mod, IEventBus bus, Dist dist) {
        bus.addListener(EventPriority.LOWEST, ReduxData::dataSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::registerDataMaps);
        bus.addListener(this::packSetup);
        bus.addListener(this::registerPackets);
        if (dist == Dist.CLIENT) {
            bus.addListener(EventPriority.LOWEST, ReduxColors::blockColors);
            bus.addListener(ReduxColors::itemColors);
        }

        Reflection.initialize(ReduxWoodSets.class, ReduxStoneSets.class, ReduxFlowerSets.class);

        DeferredRegister<?>[] registers = {
                ReduxBlocks.BLOCKS,
                ReduxItems.ITEMS,
                ReduxEntities.ENTITIES,
                ReduxTiles.TILES,
                ReduxFeatures.FEATURES,
                ReduxFoliagePlacers.FOLIAGE_PLACERS,
                ReduxParticles.PARTICLES,
                ReduxRecipes.TYPES,
                ReduxRecipes.Serializers.SERIALIZERS,
                ReduxSounds.SOUNDS,
                ReduxLootModifiers.GLOBAL_LOOT_MODIFIERS,
                ReduxTreeDecorators.TREE_DECORATORS,
                ReduxDataComponents.TYPES,
                ReduxDataAttachments.ATTACHMENTS
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        ReduxConfigHandler.setup(mod, bus);

        ReduxConfig.SERVER.registerSerializer();
        ReduxConfig.COMMON.registerSerializer();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Regions.register(new ReduxRegion(loc("aether_redux_region"), /*ReduxConfig.COMMON.region_size.get()*/ 20));
            SurfaceRuleManager.addSurfaceRules(AetherRuleCategory.THE_AETHER, "aether_redux", ReduxSurfaceRules.makeRules());
            ReduxBlocks.registerFlammability();
            ReduxBlocks.registerToolConversions();
            ReduxItems.registerAccessories();
            ReduxEntities.addBossConversions();
        });
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(ReduxClient::registerTintOverrides);
    }

    public void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(MODID).versioned("1.0.0").optional();
        registrar.playToServer(AerjumpPacket.Request.TYPE, AerjumpPacket.Request.STREAM_CODEC, AerjumpPacket.Request::execute);
        registrar.playToClient(AerjumpPacket.Accepted.TYPE, AerjumpPacket.Accepted.STREAM_CODEC, AerjumpPacket.Accepted::execute);
        registrar.playToClient(AerjumpPacket.Particles.TYPE, AerjumpPacket.Particles.STREAM_CODEC, AerjumpPacket.Particles::execute);
        registrar.playBidirectional(ReduxPlayerSyncPacket.TYPE, ReduxPlayerSyncPacket.STREAM_CODEC, ReduxPlayerSyncPacket::execute);
        registrar.playToClient(SliderSignalPacket.Signal.TYPE, SliderSignalPacket.Signal.STREAM_CODEC, SliderSignalPacket.Signal::execute);
        registrar.playToClient(SliderSignalPacket.DirectionOverride.TYPE, SliderSignalPacket.DirectionOverride.STREAM_CODEC, SliderSignalPacket.DirectionOverride::execute);
    }

    private void registerDataMaps(RegisterDataMapTypesEvent event) {
    }

    public  void packSetup(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            ASSETS_CONFIG.setup(event);
        } else if (event.getPackType() == PackType.SERVER_DATA) {
            if (ReduxConfig.COMMON.bronze_dungeon_upgrade.get()) { PackUtils.setupPack(event, MODID, "dungeon_upgrades/bronze", "bronze_upgrade", true, true); }
            if (ReduxConfig.COMMON.redux_noise.get().get()) { PackUtils.setupPack(event, MODID, "redux_noise", "redux_noise", true, true); }
        }
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static boolean compat(String modid) {
        return ModList.get().isLoaded(modid);
    }


}
