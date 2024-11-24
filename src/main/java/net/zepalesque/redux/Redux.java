package net.zepalesque.redux;

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
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
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
import net.zepalesque.redux.pack.PackUtils;
import net.zepalesque.redux.pack.ReduxPackConfig;
import net.zepalesque.redux.recipe.ReduxRecipes;
import net.zepalesque.redux.tile.ReduxTiles;
import net.zepalesque.redux.world.biome.ReduxRegion;
import net.zepalesque.redux.world.biome.ReduxSurfaceRules;
import net.zepalesque.redux.world.biome.tint.ReduxBiomeTints;
import net.zepalesque.redux.world.feature.gen.ReduxFeatures;
import net.zepalesque.redux.world.tree.decorator.ReduxTreeDecorators;
import net.zepalesque.redux.world.tree.foliage.ReduxFoliagePlacers;
import net.zepalesque.zenith.api.blockset.BlockSet;
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

    public Redux(ModContainer mod, IEventBus bus, Dist dist) {
        bus.addListener(EventPriority.LOWEST, ReduxData::dataSetup);
        bus.addListener(this::commonSetup);
        bus.addListener(this::registerDataMaps);
        bus.addListener(this::packSetup);
        if (dist == Dist.CLIENT) {
            bus.addListener(EventPriority.LOWEST, ReduxColors::blockColors);
            bus.addListener(ReduxColors::itemColors);
            bus.addListener(ReduxColors::resolvers);
        }

        // TODO: Replace with Reflection#initialize?
        ReduxWoodSets.init();
        ReduxStoneSets.init();
        ReduxFlowerSets.init();

        DeferredRegister<?>[] registers = {
                ReduxBlocks.BLOCKS,
                ReduxItems.ITEMS,
                ReduxEntities.ENTITIES,
                ReduxTiles.TILES,
                ReduxBiomeTints.TINTS,
                ReduxFeatures.FEATURES,
                ReduxFoliagePlacers.FOLIAGE_PLACERS,
                ReduxParticles.PARTICLES,
                ReduxRecipes.TYPES,
                ReduxRecipes.Serializers.SERIALIZERS,
                ReduxSounds.SOUNDS,
                ReduxLootModifiers.GLOBAL_LOOT_MODIFIERS,
                ReduxTreeDecorators.TREE_DECORATORS,
                ReduxDataComponents.TYPES
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
        });
    }

    private void registerDataMaps(RegisterDataMapTypesEvent event) {
    }

    public  void packSetup(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            String pathString = "resource/overrides_pack";
            String id = "overrides_pack";
            PackUtils.setupPack(event, pathString, id, true, ReduxPackConfig::generate);
        } else if (event.getPackType() == PackType.SERVER_DATA) {
            if (ReduxConfig.COMMON.bronze_dungeon_upgrade.get()) { PackUtils.setupPack(event, "dungeon_upgrades/bronze", "bronze_upgrade", true); }
            if (ReduxConfig.COMMON.redux_noise.get().get()) { PackUtils.setupPack(event, "redux_noise", "redux_noise", true); }
        }
    }

    public static ResourceLocation loc(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public static boolean compat(String modid) {
        return ModList.get().isLoaded(modid);
    }


}
