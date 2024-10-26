package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockhandlers.WoodHandlers;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;

import java.util.Map;
import java.util.function.Supplier;

public class MappingsHooks {

    public static final Map<String, WoodHandler> WOOD_TYPES = new ImmutableMap.Builder<String, WoodHandler>()
            .put("prismatic", WoodHandlers.FIELDSPROOT)
            .put("fieldsprout", WoodHandlers.FIELDSPROOT)
            .put("springshroom", WoodHandlers.JELLYSHROOM)
            .build();

    public static final Map<ResourceLocation, Supplier<? extends ItemLike>> ITEMS = createItemMap();
    public static final Map<ResourceLocation, Supplier<? extends Block>> BLOCKS = createBlockMap();
    public static final Map<ResourceLocation, ResourceKey<Biome>> BIOMES = createBiomeMap();
    public static final Map<ResourceLocation, Supplier<? extends EntityType<?>>> ENTITIES = createEntityMap();



    private static Map<ResourceLocation, Supplier<? extends ItemLike>> createItemMap() {
        ImmutableMap.Builder<ResourceLocation, Supplier<? extends ItemLike>> builder = new ImmutableMap.Builder<>();
        // Blocks
        MappingsHooks.WOOD_TYPES.forEach((s, w) -> woodItems(s, w, builder));
        builder.put(Redux.locate("blighted_aether_grass_block"), AetherBlocks.AETHER_GRASS_BLOCK);
        builder.put(Redux.locate("frosted_aether_grass_block"), AetherBlocks.AETHER_GRASS_BLOCK);
        builder.put(Redux.locate("aevelium"), ReduxBlocks.AVELIUM);

        builder.put(Redux.locate("aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("blighted_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("frosted_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("enchanted_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);

        builder.put(Redux.locate("aevelium_growth"), ReduxBlocks.AVELIUM_ROOTS);
        builder.put(Redux.locate("aevelium_sprouts"), ReduxBlocks.AVELIUM_SPROUTS);

        builder.put(Redux.locate("flowering_fieldsprout_leaves"), ReduxBlocks.FIELDSPROOT_LEAVES);
        builder.put(Redux.locate("flowering_fieldsprout_sapling"), ReduxBlocks.FIELDSPROOT_SAPLING);

        builder.put(Redux.locate("blightberry_bush"), ReduxBlocks.ZANBERRY_BUSH);
        builder.put(Redux.locate("blightberry_bush_stem"), ReduxBlocks.ZANBERRY_BUSH_STEM);

        builder.put(Redux.locate("chromatic_berry_bush"), ReduxBlocks.ZANBERRY_BUSH);
        builder.put(Redux.locate("chromatic_berry_bush_stem"), ReduxBlocks.ZANBERRY_BUSH_STEM);

        builder.put(Redux.locate("chromatic_shrub"), ReduxBlocks.ZANBERRY_BUSH_STEM);

        builder.put(Redux.locate("chromatic_leaves"), AetherBlocks.SKYROOT_LEAVES);
        builder.put(Redux.locate("chromatic_berry_leaves"), AetherBlocks.SKYROOT_LEAVES);

        builder.put(Redux.locate("springshroom_cap_block"), ReduxBlocks.JELLYSHROOM_JELLY_BLOCK);
        builder.put(Redux.locate("springshroom_spores"), ReduxBlocks.JELLYSHROOM_JELLY_BLOCK);
        builder.put(Redux.locate("springshroom"), ReduxBlocks.JELLYSHROOM);

        builder.put(Redux.locate("pink_prismatic_leaf_pile"), ReduxBlocks.FIELDSPROOT_PETALS);
        builder.put(Redux.locate("teal_prismatic_leaf_pile"), ReduxBlocks.FIELDSPROOT_PETALS);

        builder.put(Redux.locate("pink_prismatic_petals"), ReduxBlocks.FIELDSPROOT_PETALS);
        builder.put(Redux.locate("teal_prismatic_petals"), ReduxBlocks.FIELDSPROOT_PETALS);

        builder.put(Redux.locate("splitbloom"), ReduxBlocks.IRIDIA);

        builder.put(Redux.locate("vallyx"), AetherBlocks.PURPLE_FLOWER);

        builder.put(Redux.locate("sweetblossom"), ReduxBlocks.THERATIP);

        builder.put(Redux.locate("glowsprouts"), ReduxBlocks.LUXWEED);

        builder.put(Redux.locate("blighted_fungi"), ReduxBlocks.CORRUPTED_VINES);
        builder.put(Redux.locate("thorncap"), ReduxBlocks.CORRUPTED_VINES);

        builder.put(Redux.locate("frostbud"), ReduxBlocks.DAGGERBLOOM);
        builder.put(Redux.locate("spikespring"), ReduxBlocks.DAGGERBLOOM);

        builder.put(Redux.locate("frosted_fern"), ReduxBlocks.SPLITFERN);

        builder.put(Redux.locate("frosted_purple_flower"), AetherBlocks.PURPLE_FLOWER);
        builder.put(Redux.locate("gilded_white_flower"), ReduxBlocks.ENCHANTED_WHITE_FLOWER);

        builder.put(Redux.locate("xaelia_flowers"), ReduxBlocks.XAELIA_PATCH);

        builder.put(Redux.locate("vitrium"), ReduxBlocks.SENTRITE);
        builder.put(Redux.locate("vitrium_stairs"), ReduxBlocks.SENTRITE_STAIRS);
        builder.put(Redux.locate("vitrium_slab"), ReduxBlocks.SENTRITE_SLAB);
        builder.put(Redux.locate("vitrium_wall"), ReduxBlocks.SENTRITE_WALL);
        builder.put(Redux.locate("vitrium_bricks"), ReduxBlocks.SENTRITE_BRICKS);
        builder.put(Redux.locate("vitrium_brick_stairs"), ReduxBlocks.SENTRITE_BRICK_STAIRS);
        builder.put(Redux.locate("vitrium_brick_slab"), ReduxBlocks.SENTRITE_BRICK_SLAB);
        builder.put(Redux.locate("vitrium_brick_wall"), ReduxBlocks.SENTRITE_BRICK_WALL);

        builder.put(Redux.locate("volitite"), ReduxBlocks.SENTRITE);
        builder.put(Redux.locate("volitite_stairs"), ReduxBlocks.SENTRITE_STAIRS);
        builder.put(Redux.locate("volitite_slab"), ReduxBlocks.SENTRITE_SLAB);
        builder.put(Redux.locate("volitite_wall"), ReduxBlocks.SENTRITE_WALL);
        builder.put(Redux.locate("volitite_bricks"), ReduxBlocks.SENTRITE_BRICKS);
        builder.put(Redux.locate("volitite_brick_stairs"), ReduxBlocks.SENTRITE_BRICK_STAIRS);
        builder.put(Redux.locate("volitite_brick_slab"), ReduxBlocks.SENTRITE_BRICK_SLAB);
        builder.put(Redux.locate("volitite_brick_wall"), ReduxBlocks.SENTRITE_BRICK_WALL);

        builder.put(Redux.locate("agiosite"), ReduxBlocks.SENTRITE);
        builder.put(Redux.locate("agiosite_stairs"), ReduxBlocks.SENTRITE_STAIRS);
        builder.put(Redux.locate("agiosite_slab"), ReduxBlocks.SENTRITE_SLAB);
        builder.put(Redux.locate("agiosite_wall"), ReduxBlocks.SENTRITE_WALL);
        builder.put(Redux.locate("agiosite_bricks"), ReduxBlocks.SENTRITE_BRICKS);
        builder.put(Redux.locate("agiosite_brick_stairs"), ReduxBlocks.SENTRITE_BRICK_STAIRS);
        builder.put(Redux.locate("agiosite_brick_slab"), ReduxBlocks.SENTRITE_BRICK_SLAB);
        builder.put(Redux.locate("agiosite_brick_wall"), ReduxBlocks.SENTRITE_BRICK_WALL);

        builder.put(Redux.locate("gilded_skyroot_leaves"), ReduxBlocks.GILDED_OAK_LEAVES);
        builder.put(Redux.locate("gilded_skyroot_sapling"), ReduxBlocks.GILDED_OAK_SAPLING);

        // Items
        builder.put(Redux.locate("skyroot_sap"), AetherItems.AMBROSIUM_SHARD);
        builder.put(Redux.locate("blightberry"), ReduxItems.ZANBERRY);
        builder.put(Redux.locate("prismatic_raspberry"), ReduxItems.ZANBERRY);
        builder.put(Redux.locate("chromatic_raspberry"), ReduxItems.ZANBERRY);
        builder.put(Redux.locate("chromaberry"), ReduxItems.ZANBERRY);

        builder.put(Redux.locate("glowbuds"), ReduxItems.BLIGHTED_SPORES);
        builder.put(Redux.locate("luxbuds"), ReduxItems.BLIGHTED_SPORES);

        builder.put(Redux.locate("cockatrice_rib"), () -> Items.BONE);

        builder.put(Redux.locate("bundle_of_aether_grass"), ReduxItems.WYND_OAT_PANICLE);
        builder.put(Redux.locate("bundle_of_wyndsprouts"), ReduxItems.WYND_OAT_PANICLE);
        builder.put(Redux.locate("oats"), ReduxItems.WYND_OATS);
        builder.put(Redux.locate("wyndsprout_seeds"), ReduxItems.WYND_OATS);

        builder.put(Redux.locate("quickroot"), () -> Blocks.HANGING_ROOTS);

        builder.put(Redux.locate("oat_muffin"), ReduxItems.WYND_BAGEL);
        builder.put(Redux.locate("wyndsprout_bagel"), ReduxItems.WYND_BAGEL);
        builder.put(Redux.locate("mini_pancake"), ReduxItems.BLUEBERRY_BAGEL);

        // TODO: Perhaps reimplement as a Vanilla Flower related accessory?
        builder.put(Redux.locate("bittersweet_charm"), AetherItems.GOLDEN_PENDANT);

        builder.put(Redux.locate("phoenix_ring"), ReduxItems.SOLAR_EMBLEM);

        builder.put(Redux.locate("spectral_dart_shooter"), ReduxItems.VERIDIUM_DART_SHOOTER);
        builder.put(Redux.locate("spectral_dart"), ReduxItems.VERIDIUM_DART);


        builder.put(Redux.locate("carved_bricks"), ReduxBlocks.CARVED_BASE);
        builder.put(Redux.locate("carved_brick_stairs"), AetherBlocks.CARVED_STAIRS);
        builder.put(Redux.locate("carved_brick_slab"), AetherBlocks.CARVED_SLAB);
        builder.put(Redux.locate("carved_brick_wall"), AetherBlocks.CARVED_WALL);
        builder.put(Redux.locate("carved_stone_pillar"), ReduxBlocks.CARVED_PILLAR);

        builder.put(Redux.locate("valkyrie_ring"), ReduxItems.RING_OF_CONSTRUCTION);

        builder.put(Redux.locate("silder_music_disc"), ReduxItems.ANCIENT_SENTRITE_MUSIC_DISC);

        return builder.build();
    }
    private static Map<ResourceLocation, Supplier<? extends Block>> createBlockMap() {
        ImmutableMap.Builder<ResourceLocation, Supplier<? extends Block>> builder = new ImmutableMap.Builder<>();
        MappingsHooks.WOOD_TYPES.forEach((s, w) -> woodBlocks(s, w, builder));
        builder.put(Redux.locate("blighted_aether_grass_block"), AetherBlocks.AETHER_GRASS_BLOCK);
        builder.put(Redux.locate("frosted_aether_grass_block"), AetherBlocks.AETHER_GRASS_BLOCK);
        builder.put(Redux.locate("arctic_aether_grass_block"), AetherBlocks.AETHER_GRASS_BLOCK);
        builder.put(Redux.locate("aevelium"), ReduxBlocks.AVELIUM);

        builder.put(Redux.locate("aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("blighted_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("frosted_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("arctic_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);
        builder.put(Redux.locate("enchanted_aether_grass"), ReduxBlocks.SHORT_AETHER_GRASS);


        builder.put(Redux.locate("aevelium_growth"), ReduxBlocks.AVELIUM_ROOTS);
        builder.put(Redux.locate("potted_aevelium_growth"), ReduxBlocks.POTTED_AVELIUM_ROOTS);
        builder.put(Redux.locate("aevelium_sprouts"), ReduxBlocks.AVELIUM_SPROUTS);
        builder.put(Redux.locate("potted_aevelium_sprouts"), ReduxBlocks.AVELIUM_SPROUTS);

        builder.put(Redux.locate("flowering_fieldsprout_leaves"), ReduxBlocks.FIELDSPROOT_LEAVES);
        builder.put(Redux.locate("flowering_fieldsprout_sapling"), ReduxBlocks.FIELDSPROOT_SAPLING);
        builder.put(Redux.locate("potted_flowering_fieldsprout_sapling"), ReduxBlocks.POTTED_FIELDSPROOT_SAPLING);

        builder.put(Redux.locate("blightberry_bush"), ReduxBlocks.ZANBERRY_BUSH);
        builder.put(Redux.locate("blightberry_bush_stem"), ReduxBlocks.ZANBERRY_BUSH_STEM);

        builder.put(Redux.locate("chromatic_berry_bush"), ReduxBlocks.ZANBERRY_BUSH);
        builder.put(Redux.locate("chromatic_berry_bush_stem"), ReduxBlocks.ZANBERRY_BUSH_STEM);

        builder.put(Redux.locate("chromatic_shrub"), ReduxBlocks.ZANBERRY_BUSH_STEM);

        builder.put(Redux.locate("chromatic_leaves"), AetherBlocks.SKYROOT_LEAVES);
        builder.put(Redux.locate("chromatic_berry_leaves"), AetherBlocks.SKYROOT_LEAVES);

        builder.put(Redux.locate("springshroom_cap_block"), ReduxBlocks.JELLYSHROOM_JELLY_BLOCK);
        builder.put(Redux.locate("springshroom_spores"), ReduxBlocks.JELLYSHROOM_JELLY_BLOCK);
        builder.put(Redux.locate("springshroom"), ReduxBlocks.JELLYSHROOM);
        builder.put(Redux.locate("potted_springshroom"), ReduxBlocks.POTTED_JELLYSHROOM);

        builder.put(Redux.locate("pink_prismatic_leaf_pile"), ReduxBlocks.FIELDSPROOT_PETALS);
        builder.put(Redux.locate("teal_prismatic_leaf_pile"), ReduxBlocks.FIELDSPROOT_PETALS);

        builder.put(Redux.locate("pink_prismatic_petals"), ReduxBlocks.FIELDSPROOT_PETALS);
        builder.put(Redux.locate("teal_prismatic_petals"), ReduxBlocks.FIELDSPROOT_PETALS);

        builder.put(Redux.locate("splitbloom"), ReduxBlocks.IRIDIA);
        builder.put(Redux.locate("potted_splitbloom"), ReduxBlocks.POTTED_IRIDIA);

        builder.put(Redux.locate("vallyx"), AetherBlocks.PURPLE_FLOWER);
        builder.put(Redux.locate("potted_vallyx"), AetherBlocks.POTTED_PURPLE_FLOWER);

        builder.put(Redux.locate("sweetblossom"), ReduxBlocks.THERATIP);
        builder.put(Redux.locate("potted_sweetblossom"), ReduxBlocks.POTTED_THERATIP);

        builder.put(Redux.locate("frosted_purple_flower"), AetherBlocks.PURPLE_FLOWER);
        builder.put(Redux.locate("potted_frosted_purple_flower"), AetherBlocks.POTTED_PURPLE_FLOWER);

        builder.put(Redux.locate("gilded_white_flower"), ReduxBlocks.ENCHANTED_WHITE_FLOWER);
        builder.put(Redux.locate("potted_gilded_white_flower"), AetherBlocks.POTTED_WHITE_FLOWER);

        builder.put(Redux.locate("blighted_fungi"), ReduxBlocks.CORRUPTED_VINES);
        builder.put(Redux.locate("potted_blighted_fungi"), () -> Blocks.FLOWER_POT);

        builder.put(Redux.locate("thorncap"), ReduxBlocks.CORRUPTED_VINES);
        builder.put(Redux.locate("potted_thorncap"), () -> Blocks.FLOWER_POT);

        builder.put(Redux.locate("frostbud"), ReduxBlocks.DAGGERBLOOM);
        builder.put(Redux.locate("potted_frostbud"), ReduxBlocks.POTTED_DAGGERBLOOM);

        builder.put(Redux.locate("spikespring"), ReduxBlocks.DAGGERBLOOM);
        builder.put(Redux.locate("potted_spikespring"), ReduxBlocks.POTTED_DAGGERBLOOM);

        builder.put(Redux.locate("frosted_fern"), ReduxBlocks.SPLITFERN);
        builder.put(Redux.locate("potted_frosted_fern"), ReduxBlocks.POTTED_SPLITFERN);

        builder.put(Redux.locate("glowsprouts"), ReduxBlocks.LUXWEED);
        builder.put(Redux.locate("potted_glowsprouts"), ReduxBlocks.POTTED_LUXWEED);
        
        builder.put(Redux.locate("xaelia_flowers"), ReduxBlocks.XAELIA_PATCH);

        builder.put(Redux.locate("vitrium"), ReduxBlocks.SENTRITE);
        builder.put(Redux.locate("vitrium_stairs"), ReduxBlocks.SENTRITE_STAIRS);
        builder.put(Redux.locate("vitrium_slab"), ReduxBlocks.SENTRITE_SLAB);
        builder.put(Redux.locate("vitrium_wall"), ReduxBlocks.SENTRITE_WALL);
        builder.put(Redux.locate("vitrium_bricks"), ReduxBlocks.SENTRITE_BRICKS);
        builder.put(Redux.locate("vitrium_brick_stairs"), ReduxBlocks.SENTRITE_BRICK_STAIRS);
        builder.put(Redux.locate("vitrium_brick_slab"), ReduxBlocks.SENTRITE_BRICK_SLAB);
        builder.put(Redux.locate("vitrium_brick_wall"), ReduxBlocks.SENTRITE_BRICK_WALL);

        builder.put(Redux.locate("volitite"), ReduxBlocks.SENTRITE);
        builder.put(Redux.locate("volitite_stairs"), ReduxBlocks.SENTRITE_STAIRS);
        builder.put(Redux.locate("volitite_slab"), ReduxBlocks.SENTRITE_SLAB);
        builder.put(Redux.locate("volitite_wall"), ReduxBlocks.SENTRITE_WALL);
        builder.put(Redux.locate("volitite_bricks"), ReduxBlocks.SENTRITE_BRICKS);
        builder.put(Redux.locate("volitite_brick_stairs"), ReduxBlocks.SENTRITE_BRICK_STAIRS);
        builder.put(Redux.locate("volitite_brick_slab"), ReduxBlocks.SENTRITE_BRICK_SLAB);
        builder.put(Redux.locate("volitite_brick_wall"), ReduxBlocks.SENTRITE_BRICK_WALL);

        builder.put(Redux.locate("agiosite"), ReduxBlocks.SENTRITE);
        builder.put(Redux.locate("agiosite_stairs"), ReduxBlocks.SENTRITE_STAIRS);
        builder.put(Redux.locate("agiosite_slab"), ReduxBlocks.SENTRITE_SLAB);
        builder.put(Redux.locate("agiosite_wall"), ReduxBlocks.SENTRITE_WALL);
        builder.put(Redux.locate("agiosite_bricks"), ReduxBlocks.SENTRITE_BRICKS);
        builder.put(Redux.locate("agiosite_brick_stairs"), ReduxBlocks.SENTRITE_BRICK_STAIRS);
        builder.put(Redux.locate("agiosite_brick_slab"), ReduxBlocks.SENTRITE_BRICK_SLAB);
        builder.put(Redux.locate("agiosite_brick_wall"), ReduxBlocks.SENTRITE_BRICK_WALL);

        builder.put(Redux.locate("quickroots"), () -> Blocks.HANGING_ROOTS);

        builder.put(Redux.locate("gilded_skyroot_leaves"), ReduxBlocks.GILDED_OAK_LEAVES);
        builder.put(Redux.locate("gilded_skyroot_sapling"), ReduxBlocks.GILDED_OAK_SAPLING);
        builder.put(Redux.locate("potted_gilded_skyroot_sapling"), ReduxBlocks.POTTED_GILDED_OAK_SAPLING);

        builder.put(Redux.locate("wyndsprout_crop"), ReduxBlocks.WYNDOATS);

        builder.put(Redux.locate("carved_bricks"), ReduxBlocks.CARVED_BASE);
        builder.put(Redux.locate("carved_brick_stairs"), AetherBlocks.CARVED_STAIRS);
        builder.put(Redux.locate("carved_brick_slab"), AetherBlocks.CARVED_SLAB);
        builder.put(Redux.locate("carved_brick_wall"), AetherBlocks.CARVED_WALL);
        builder.put(Redux.locate("carved_stone_pillar"), ReduxBlocks.CARVED_PILLAR);

        return builder.build();
    }
    private static Map<ResourceLocation, ResourceKey<Biome>> createBiomeMap() {
        ImmutableMap.Builder<ResourceLocation, ResourceKey<Biome>> builder = new ImmutableMap.Builder<>();

        builder.put(Redux.locate("arctic_pines"), ReduxBiomes.FROSTED_FORESTS);
        builder.put(Redux.locate("frosted_forest"), ReduxBiomes.FROSTED_FORESTS);

        builder.put(Redux.locate("gilded_thicket"), ReduxBiomes.GILDED_GRASSLANDS);

        builder.put(Redux.locate("highfields"), ReduxBiomes.SKYFIELDS);
        builder.put(Redux.locate("highfields_forest"), ReduxBiomes.SKYFIELDS);

        builder.put(Redux.locate("cloudcap_jungle"), ReduxBiomes.CLOUDCAPS);

        return builder.build();
    }
    private static Map<ResourceLocation, Supplier<? extends EntityType<?>>> createEntityMap() {
        ImmutableMap.Builder<ResourceLocation, Supplier<? extends EntityType<?>>> builder = new ImmutableMap.Builder<>();
        MappingsHooks.WOOD_TYPES.forEach((s, w) -> woodEntities(s, w, builder));
        builder.put(Redux.locate("spectral_dart"), ReduxEntityTypes.VERIDIUM_DART);

        return builder.build();
    }


    public static <T> void remap(String unmapped, Supplier<? extends T> newValue, MissingMappingsEvent.Mapping<T> map)
    {
        remap(Redux.locate(unmapped), newValue.get(), map);
    }
    public static void item(String unmapped, Supplier<? extends ItemLike> newValue, MissingMappingsEvent.Mapping<Item> map)
    {
        remap(Redux.locate(unmapped), newValue.get().asItem(), map);
    }
    public static void block(String unmapped, Supplier<? extends Block> newValue, MissingMappingsEvent.Mapping<Block> map)
    {
        remap(Redux.locate(unmapped), newValue.get(), map);
    }
    public static <T> void remap(String unmapped, T newValue, MissingMappingsEvent.Mapping<T> map)
    {
        remap(Redux.locate(unmapped), newValue, map);
    }
    public static <T> void remap(ResourceLocation unmappedLoc, Supplier<? extends T> newValue, MissingMappingsEvent.Mapping<T> map)
    {
        remap(unmappedLoc, newValue.get(), map);
    }
    public static <T> void remap(ResourceLocation unmappedLoc, T newValue, MissingMappingsEvent.Mapping<T> map) {
        if (map.getKey().equals(unmappedLoc)) {
            map.remap(newValue);
        }
    }



    public static void woodItems(String oldId, WoodHandler newHandler, ImmutableMap.Builder<ResourceLocation, Supplier<? extends ItemLike>> map) {
        map.put(Redux.locate(oldId + "_planks"), newHandler.planks);
        map.put(Redux.locate(oldId + "_" + newHandler.logSuffix), newHandler.log);
        map.put(Redux.locate(oldId + "_" + newHandler.woodSuffix), newHandler.wood);
        newHandler.strippedLog.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.logSuffix), reg));
        newHandler.strippedWood.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.woodSuffix), reg));
        map.put(Redux.locate(oldId + "_stairs"), newHandler.stairs);
        map.put(Redux.locate(oldId + "_fence"), newHandler.fence);
        map.put(Redux.locate(oldId + "_fence_gate"), newHandler.fenceGate);
        map.put(Redux.locate(oldId + "_door"), newHandler.door);
        map.put(Redux.locate(oldId + "_trapdoor"), newHandler.trapdoor);
        map.put(Redux.locate(oldId + "_pressure_plate"), newHandler.pressurePlate);
        map.put(Redux.locate(oldId + "_button"), newHandler.button);
        map.put(Redux.locate(oldId + "_bookshelf"), newHandler.bookshelf);
        map.put(Redux.locate(oldId + "_sign"), newHandler.signItem);
        map.put(Redux.locate(oldId + "_hanging_sign"), newHandler.hangingSignItem);
        map.put(Redux.locate(oldId + "_boat"), newHandler.boatItem);
        map.put(Redux.locate(oldId + "_chest_boat"), newHandler.chestBoatItem);
        map.put(Redux.locate(oldId + "_" + newHandler.logSuffix + "_wall"), newHandler.logWall);
        map.put(Redux.locate(oldId + "_" + newHandler.woodSuffix + "_wall"), newHandler.woodWall);
        newHandler.strippedLogWall.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.logSuffix + "_wall"), reg));
        newHandler.strippedWoodWall.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.woodSuffix + "_wall"), reg));
    }

    public static void woodBlocks(String oldId, WoodHandler newHandler, ImmutableMap.Builder<ResourceLocation, Supplier<? extends Block>> map) {
        map.put(Redux.locate(oldId + "_planks"), newHandler.planks);
        map.put(Redux.locate(oldId + "_" + newHandler.logSuffix), newHandler.log);
        map.put(Redux.locate(oldId + "_" + newHandler.woodSuffix), newHandler.wood);
        newHandler.strippedLog.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.logSuffix), reg));
        newHandler.strippedWood.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.woodSuffix), reg));
        map.put(Redux.locate(oldId + "_stairs"), newHandler.stairs);
        map.put(Redux.locate(oldId + "_fence"), newHandler.fence);
        map.put(Redux.locate(oldId + "_fence_gate"), newHandler.fenceGate);
        map.put(Redux.locate(oldId + "_door"), newHandler.door);
        map.put(Redux.locate(oldId + "_trapdoor"), newHandler.trapdoor);
        map.put(Redux.locate(oldId + "_pressure_plate"), newHandler.pressurePlate);
        map.put(Redux.locate(oldId + "_button"), newHandler.button);
        map.put(Redux.locate(oldId + "_bookshelf"), newHandler.bookshelf);
        map.put(Redux.locate(oldId + "_sign"), newHandler.sign);
        map.put(Redux.locate(oldId + "_wall_sign"), newHandler.wallSign);
        map.put(Redux.locate(oldId + "_hanging_sign"), newHandler.hangingSign);
        map.put(Redux.locate(oldId + "_wall_hanging_sign"), newHandler.wallHangingSign);
        map.put(Redux.locate(oldId + "_" + newHandler.logSuffix + "_wall"), newHandler.logWall);
        map.put(Redux.locate(oldId + "_" + newHandler.woodSuffix + "_wall"), newHandler.woodWall);
        newHandler.strippedLogWall.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.logSuffix + "_wall"), reg));
        newHandler.strippedWoodWall.ifPresent(reg -> map.put(Redux.locate("stripped_" + oldId + "_" + newHandler.woodSuffix + "_wall"), reg));
    }

    public static void woodEntities(String oldId, WoodHandler newHandler, ImmutableMap.Builder<ResourceLocation, Supplier<? extends EntityType<? extends Entity>>> map) {
        map.put(Redux.locate(oldId + "_boat"), newHandler.boatEntity);
        map.put(Redux.locate(oldId + "_chest_boat"), newHandler.chestBoatEntity);
    }
}
