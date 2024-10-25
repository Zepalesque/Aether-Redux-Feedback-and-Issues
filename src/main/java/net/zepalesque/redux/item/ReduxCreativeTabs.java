package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether_genesis.block.GenesisBlocks;
import com.legacy.lost_aether.registry.LCBlocks;
import net.builderdog.ancient_aether.block.AncientAetherBlocks;
import net.builderdog.ancient_aether.item.AncientAetherItems;
import net.minecraft.nbt.ByteTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.tools.VeridiumItem;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ReduxCreativeTabs {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        if (tab == AetherCreativeTabs.AETHER_BUILDING_BLOCKS.get()) {
            RegistryObject<? extends ItemLike> f1 = doBuildingWoods(AetherBlocks.GOLDEN_OAK_WOOD, event);

            putAfter(AetherBlocks.MOSSY_HOLYSTONE_WALL, ReduxBlocks.GILDED_HOLYSTONE, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE, ReduxBlocks.GILDED_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_STAIRS, ReduxBlocks.GILDED_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_SLAB, ReduxBlocks.GILDED_HOLYSTONE_WALL, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_WALL, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, event);


            putAfter(AetherBlocks.QUICKSOIL_GLASS_PANE, ReduxBlocks.POLISHED_DRIFTSHALE, event);
            putAfter(ReduxBlocks.POLISHED_DRIFTSHALE, ReduxBlocks.POLISHED_DRIFTSHALE_STAIRS, event);
            putAfter(ReduxBlocks.POLISHED_DRIFTSHALE_STAIRS, ReduxBlocks.POLISHED_DRIFTSHALE_SLAB, event);
            putAfter(ReduxBlocks.POLISHED_DRIFTSHALE_SLAB, ReduxBlocks.POLISHED_DRIFTSHALE_WALL, event);

            putAfter(AetherBlocks.ICESTONE_WALL, ReduxBlocks.DIVINITE, event);
            putAfter(ReduxBlocks.DIVINITE, ReduxBlocks.DIVINITE_STAIRS, event);
            putAfter(ReduxBlocks.DIVINITE_STAIRS, ReduxBlocks.DIVINITE_SLAB, event);
            putAfter(ReduxBlocks.DIVINITE_SLAB, ReduxBlocks.DIVINITE_WALL, event);
            putAfter(ReduxBlocks.DIVINITE_WALL, ReduxBlocks.SENTRITE, event);
            putAfter(ReduxBlocks.SENTRITE, ReduxBlocks.SENTRITE_STAIRS, event);
            putAfter(ReduxBlocks.SENTRITE_STAIRS, ReduxBlocks.SENTRITE_SLAB, event);
            putAfter(ReduxBlocks.SENTRITE_SLAB, ReduxBlocks.SENTRITE_WALL, event);
            putAfter(ReduxBlocks.SENTRITE_WALL, ReduxBlocks.SENTRITE_BRICKS, event);
            putAfter(ReduxBlocks.SENTRITE_BRICKS, ReduxBlocks.SENTRITE_BRICK_STAIRS, event);
            putAfter(ReduxBlocks.SENTRITE_BRICK_STAIRS, ReduxBlocks.SENTRITE_BRICK_SLAB, event);
            putAfter(ReduxBlocks.SENTRITE_BRICK_SLAB, ReduxBlocks.SENTRITE_BRICK_WALL, event);
            putAfter(ReduxBlocks.SENTRITE_BRICK_WALL, ReduxBlocks.SHELL_SHINGLES, event);
            putAfter(ReduxBlocks.SHELL_SHINGLES, ReduxBlocks.SHELL_SHINGLE_STAIRS, event);
            putAfter(ReduxBlocks.SHELL_SHINGLE_STAIRS, ReduxBlocks.SHELL_SHINGLE_SLAB, event);
            putAfter(ReduxBlocks.SHELL_SHINGLE_SLAB, ReduxBlocks.SHELL_SHINGLE_WALL, event);
            putAfter(ReduxBlocks.SHELL_SHINGLE_WALL, ReduxBlocks.ENCHANTED_SHELL_SHINGLES, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLES, ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS, ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB, ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL, event);

            putAfter(AetherBlocks.ZANITE_BLOCK, ReduxBlocks.VERIDIUM_BLOCK, event);
            putAfter(ReduxBlocks.VERIDIUM_BLOCK, ReduxBlocks.REFINED_SENTRITE_BLOCK, event);

            add(ReduxBlocks.SENTRITE_LANTERN, event);
            add(ReduxBlocks.SENTRITE_CHAIN, event);
            if (ReduxConfig.COMMON.gravitite_ingot.get()) {
                putBefore(AetherBlocks.ENCHANTED_GRAVITITE, ReduxBlocks.GRAVITITE_BLOCK, event);
                event.getEntries().remove(stack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            }

        }

        if (tab == AetherCreativeTabs.AETHER_NATURAL_BLOCKS.get()) {



            putAfter(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, ReduxBlocks.AVELIUM, event);


            putAfter(AetherBlocks.HOLYSTONE, ReduxBlocks.DIVINITE, event);
            putAfter(ReduxBlocks.DIVINITE, ReduxBlocks.SENTRITE, event);
            putAfter(ReduxBlocks.SENTRITE, ReduxBlocks.HOLYSILT, event);

            if (!Redux.deepAetherCompat()) {
                putAfter(AetherBlocks.AETHER_DIRT, ReduxBlocks.COARSE_AETHER_DIRT, event);
            }
            
            putAfter(AetherBlocks.AETHER_FARMLAND, ReduxBlocks.SHORT_AETHER_GRASS, event);
            putAfter(ReduxBlocks.SHORT_AETHER_GRASS, ReduxBlocks.SPLITFERN, event);
            putAfter(ReduxBlocks.SPLITFERN, ReduxBlocks.AVELIUM_ROOTS, event);
            putAfter(ReduxBlocks.AVELIUM_ROOTS, ReduxBlocks.AVELIUM_SPROUTS, event);

            putAfter(AetherBlocks.MOSSY_HOLYSTONE, ReduxBlocks.GILDED_HOLYSTONE, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, event);

            putAfter(AetherBlocks.QUICKSOIL, ReduxBlocks.DRIFTSHALE, event);
            putAfter(ReduxBlocks.DRIFTSHALE, ReduxBlocks.BLIGHTMOSS_BLOCK, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_BLOCK, ReduxBlocks.BLIGHTMOSS_CARPET, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_CARPET, ReduxBlocks.FUNGAL_GROWTH, event);
            putAfter(ReduxBlocks.FUNGAL_GROWTH, ReduxBlocks.FUNGAL_CARPET, event);

            doNaturalWoods(AetherBlocks.GOLDEN_OAK_LOG, event);

            putAfter(AetherBlocks.GOLDEN_OAK_LEAVES, ReduxBlocks.GOLDEN_LEAF_PILE, event);
            putAfter(ReduxBlocks.GOLDEN_LEAF_PILE, ReduxBlocks.GOLDEN_VINES, event);
            putAfter(ReduxBlocks.GOLDEN_VINES, ReduxBlocks.GILDED_OAK_LEAVES, event);
            putAfter(ReduxBlocks.GILDED_OAK_LEAVES, ReduxBlocks.GILDED_LEAF_PILE, event);
            putAfter(ReduxBlocks.GILDED_LEAF_PILE, ReduxBlocks.GILDED_VINES, event);

            putBefore(AetherBlocks.HOLIDAY_LEAVES, ReduxBlocks.PURPLE_GLACIA_LEAVES, event);
            putBefore(ReduxBlocks.PURPLE_GLACIA_LEAVES, ReduxBlocks.GLACIA_LEAVES, event);
            putBefore(ReduxBlocks.GLACIA_LEAVES, ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, event);
            putBefore(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, event);
            putBefore(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, ReduxBlocks.BLIGHTWILLOW_LEAVES, event);

            putAfter(AetherBlocks.GOLDEN_AERCLOUD, ReduxBlocks.BLIGHTED_AERCLOUD, event);

            putAfter(AetherBlocks.DECORATED_HOLIDAY_LEAVES, ReduxBlocks.FIELDSPROOT_LEAVES, event);
            putAfter(ReduxBlocks.FIELDSPROOT_LEAVES, ReduxBlocks.CLOUD_CAP_BLOCK, event);
            putAfter(ReduxBlocks.CLOUD_CAP_BLOCK, ReduxBlocks.CLOUDCAP_SPORES, event);
            putAfter(ReduxBlocks.CLOUDCAP_SPORES, ReduxBlocks.JELLYSHROOM_JELLY_BLOCK, event);

            putAfter(AetherBlocks.GOLDEN_OAK_SAPLING, ReduxBlocks.BLIGHTWILLOW_SAPLING, event);
            putAfter(ReduxBlocks.BLIGHTWILLOW_SAPLING, ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, event);
            putAfter(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, ReduxBlocks.GLACIA_SAPLING, event);
            putAfter(ReduxBlocks.GLACIA_SAPLING, ReduxBlocks.PURPLE_GLACIA_SAPLING, event);
            putAfter(ReduxBlocks.PURPLE_GLACIA_SAPLING, ReduxBlocks.GILDED_OAK_SAPLING, event);
            putAfter(ReduxBlocks.GILDED_OAK_SAPLING, ReduxBlocks.FIELDSPROOT_SAPLING, event);

            if (Redux.lostAetherCompat()) {
                event.getEntries().remove(stack(LCBlocks.crystal_sapling));
            }

            putAfter(ReduxBlocks.GILDED_OAK_SAPLING, ReduxBlocks.CRYSTAL_SAPLING, event);
            putAfter(ReduxBlocks.CRYSTAL_SAPLING, ReduxBlocks.CRYSTAL_FRUIT_SAPLING, event);

            putAfter(AetherBlocks.BERRY_BUSH, ReduxBlocks.ZANBERRY_BUSH_STEM, event);
            putAfter(ReduxBlocks.ZANBERRY_BUSH_STEM, ReduxBlocks.ZANBERRY_BUSH, event);


            putAfter(AetherBlocks.WHITE_FLOWER, ReduxBlocks.WYNDSPROUTS, event);
            putAfter(ReduxBlocks.WYNDSPROUTS, ReduxBlocks.SKYSPROUTS, event);
            putAfter(ReduxBlocks.SKYSPROUTS, ReduxBlocks.FIELDSPROOT_PETALS, event);
            putAfter(ReduxBlocks.FIELDSPROOT_PETALS, ReduxBlocks.IRIDIA, event);
            putAfter(ReduxBlocks.IRIDIA, ReduxBlocks.XAELIA_PATCH, event);
            putAfter(ReduxBlocks.XAELIA_PATCH, ReduxBlocks.AURUM, event);
            putAfter(ReduxBlocks.AURUM, ReduxBlocks.GOLDEN_CLOVER, event);
            putAfter(ReduxBlocks.GOLDEN_CLOVER, ReduxBlocks.ZYATRIX, event);
            putAfter(ReduxBlocks.ZYATRIX, ReduxBlocks.LUXWEED, event);
            putAfter(ReduxBlocks.LUXWEED, ReduxBlocks.SPIROLYCTIL, event);
            putAfter(ReduxBlocks.SPIROLYCTIL, ReduxBlocks.BLIGHTSHADE, event);
            putAfter(ReduxBlocks.BLIGHTSHADE, ReduxBlocks.LUMINA, event);
            putAfter(ReduxBlocks.LUMINA, ReduxBlocks.DAGGERBLOOM, event);
            putAfter(ReduxBlocks.DAGGERBLOOM, ReduxBlocks.THERATIP, event);
            putAfter(ReduxBlocks.THERATIP, ReduxBlocks.CLOUDCAP_MUSHLING, event);
            putAfter(ReduxBlocks.CLOUDCAP_MUSHLING, ReduxBlocks.JELLYSHROOM, event);
            putAfter(ReduxBlocks.JELLYSHROOM, ReduxBlocks.SHIMMERSTOOL, event);
            putAfter(ReduxBlocks.SHIMMERSTOOL, ReduxBlocks.FLAREBLOSSOM, event);
            putAfter(ReduxBlocks.FLAREBLOSSOM, ReduxBlocks.INFERNIA, event);

            putAfter(AetherBlocks.ZANITE_ORE, ReduxBlocks.VERIDIUM_ORE, event);
            putAfter(ReduxBlocks.VERIDIUM_ORE, ReduxBlocks.RAW_VERIDIUM_BLOCK, event);
            if (ReduxConfig.COMMON.raw_ores.get()) {
                putAfter(AetherBlocks.GRAVITITE_ORE, ReduxBlocks.RAW_GRAVITITE_BLOCK, event);
            }
            if (Redux.ancientAetherCompat()) {
                putAfter(AncientAetherBlocks.VALKYRUM_ORE, ReduxBlocks.RAW_VALKYRUM_BLOCK, event);
            }
            if (Redux.aetherGenesisCompat()) {
                putAfter(GenesisBlocks.PURPLE_CRYSTAL_TREE_SAPLING, ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING, event);
            }
        }
        if (tab == AetherCreativeTabs.AETHER_REDSTONE_BLOCKS.get()) {
            if (ReduxConfig.COMMON.gravitite_ingot.get()) {
                putBefore(AetherBlocks.ENCHANTED_GRAVITITE, ReduxBlocks.GRAVITITE_BLOCK, event);
                event.getEntries().remove(stack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            }
        }
        if (tab == AetherCreativeTabs.AETHER_FUNCTIONAL_BLOCKS.get()) {
            putAfter(AetherBlocks.AMBROSIUM_TORCH, ReduxBlocks.VERIDIUM_LANTERN, event);
            doSigns(AetherBlocks.SKYROOT_HANGING_SIGN, event);
            doBookshelves(AetherBlocks.SKYROOT_BOOKSHELF, event);
        }
        if (tab == AetherCreativeTabs.AETHER_DUNGEON_BLOCKS.get()) {
            putAfter(AetherBlocks.CARVED_STONE, ReduxBlocks.CARVED_BASE, event);
            putAfter(ReduxBlocks.CARVED_BASE, ReduxBlocks.CARVED_PILLAR, event);

            putAfter(AetherBlocks.LOCKED_CARVED_STONE, ReduxBlocks.LOCKED_CARVED_BASE, event);
            putAfter(ReduxBlocks.LOCKED_CARVED_BASE, ReduxBlocks.LOCKED_CARVED_PILLAR, event);

            putAfter(AetherBlocks.TRAPPED_CARVED_STONE, ReduxBlocks.TRAPPED_CARVED_BASE, event);
            putAfter(ReduxBlocks.TRAPPED_CARVED_BASE, ReduxBlocks.TRAPPED_CARVED_PILLAR, event);

            putAfter(AetherBlocks.BOSS_DOORWAY_CARVED_STONE, ReduxBlocks.BOSS_DOORWAY_CARVED_BASE, event);
            putAfter(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE, ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR, event);


            putAfter(AetherBlocks.SENTRY_STONE, ReduxBlocks.SENTRY_BASE, event);
            putAfter(ReduxBlocks.SENTRY_BASE, ReduxBlocks.SENTRY_PILLAR, event);

            putAfter(AetherBlocks.LOCKED_SENTRY_STONE, ReduxBlocks.LOCKED_SENTRY_BASE, event);
            putAfter(ReduxBlocks.LOCKED_SENTRY_BASE, ReduxBlocks.LOCKED_SENTRY_PILLAR, event);

            putAfter(AetherBlocks.TRAPPED_SENTRY_STONE, ReduxBlocks.TRAPPED_SENTRY_BASE, event);
            putAfter(ReduxBlocks.TRAPPED_SENTRY_BASE, ReduxBlocks.TRAPPED_SENTRY_PILLAR, event);

            putAfter(AetherBlocks.BOSS_DOORWAY_SENTRY_STONE, ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE, event);
            putAfter(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE, ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR, event);

            putAfter(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR, ReduxBlocks.SENTRITE_BRICKS, event);
            putAfter(ReduxBlocks.SENTRITE_BRICKS, ReduxBlocks.LOCKED_SENTRITE_BRICKS, event);

            putAfter(ReduxBlocks.LOCKED_SENTRITE_BRICKS, ReduxBlocks.RUNELIGHT, event);
            putAfter(ReduxBlocks.RUNELIGHT, ReduxBlocks.LOCKED_RUNELIGHT, event);
        }
        if (tab == AetherCreativeTabs.AETHER_FOOD_AND_DRINKS.get()) {
            putAfter(AetherItems.ENCHANTED_BERRY, ReduxItems.BLUEBERRY_PIE, event);
            putAfter(ReduxItems.BLUEBERRY_PIE, ReduxItems.ENCHANTED_BLUEBERRY_PIE, event);
            putAfter(ReduxItems.ENCHANTED_BLUEBERRY_PIE, ReduxItems.WYND_BAGEL, event);
            putAfter(ReduxItems.WYND_BAGEL, ReduxItems.BLUEBERRY_BAGEL, event);
            putAfter(ReduxItems.BLUEBERRY_BAGEL, ReduxItems.WYND_OATS, event);
            putAfter(ReduxItems.WYND_OATS, ReduxItems.OATMEAL, event);


            putAfter(AetherItems.GOLDEN_GUMMY_SWET, ReduxItems.VANILLA_GUMMY_SWET, event);


                putAfter(ReduxItems.VANILLA_GUMMY_SWET, Redux.aetherGenesisCompat() ? ReduxItems.VANILLA_SWET_JELLY : ReduxItems.BLUE_SWET_JELLY, event);
                if (!Redux.aetherGenesisCompat()) {
                    putAfter(ReduxItems.BLUE_SWET_JELLY, ReduxItems.GOLDEN_SWET_JELLY, event);
                    putAfter(ReduxItems.GOLDEN_SWET_JELLY, ReduxItems.VANILLA_SWET_JELLY, event);
            }
            putAfter(AetherItems.WHITE_APPLE, ReduxItems.ZANBERRY, event);

            putBefore(AetherItems.SKYROOT_MILK_BUCKET, ReduxItems.MOUSE_EAR_SOUP, event);

            if (!Redux.aetherGenesisCompat()) {
                event.getEntries().remove(stack(ReduxItems.MOUSE_EAR_SOUP.get()));
            }
        }
        if (tab == AetherCreativeTabs.AETHER_INGREDIENTS.get()) {
            putAfter(AetherItems.ZANITE_GEMSTONE, ReduxItems.RAW_VERIDIUM, event);
            putAfter(ReduxItems.RAW_VERIDIUM, ReduxItems.VERIDIUM_INGOT, event);
            putAfter(ReduxItems.VERIDIUM_INGOT, ReduxItems.VERIDIUM_NUGGET, event);
            putAfter(ReduxItems.VERIDIUM_NUGGET, ReduxItems.REFINED_SENTRITE, event);
            putAfter(ReduxItems.REFINED_SENTRITE, ReduxItems.SENTRITE_CHUNK, event);
            putAfter(ReduxItems.SENTRITE_CHUNK, ReduxItems.SENTRY_CHIP, event);

            putAfter(AetherItems.SKYROOT_STICK, ReduxItems.BLIGHTED_SPORES, event);

            putAfter(AetherItems.GOLDEN_AMBER, ReduxItems.BLIGHTBUNNY_FANG, event);

            if (ReduxConfig.COMMON.raw_ores.get()) {
                putAfter(AetherBlocks.ENCHANTED_GRAVITITE, ReduxItems.RAW_GRAVITITE, event);
            }
            if (Redux.ancientAetherCompat()) {
                putAfter(AncientAetherItems.VALKYRUM, ReduxItems.RAW_VALKYRUM, event);
            }

            putAfter(ReduxItems.VERIDIUM_NUGGET, ReduxItems.MYKAPOD_SHELL_CHUNK, event);

            putAfter(AetherItems.AECHOR_PETAL, ReduxItems.WYND_OATS, event);
            putAfter(ReduxItems.WYND_OATS, ReduxItems.WYND_OAT_PANICLE, event);
            putAfter(ReduxItems.WYND_OAT_PANICLE, ReduxItems.LIGHTROOT_CLUMP, event);

            putAfter(AetherItems.SWET_BALL, Redux.aetherGenesisCompat() ? ReduxItems.VANILLA_SWET_BALL : ReduxItems.GOLDEN_SWET_BALL, event);
            if (!Redux.aetherGenesisCompat()) { putAfter(ReduxItems.GOLDEN_SWET_BALL, ReduxItems.VANILLA_SWET_BALL, event); }

            if (ReduxConfig.COMMON.gravitite_ingot.get()) {
                putBefore(AetherBlocks.ENCHANTED_GRAVITITE, ReduxItems.GRAVITITE_INGOT, event);
                event.getEntries().remove(stack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            }

        }
        if (tab == AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES.get()) {

            putAfter(AetherItems.VALKYRIE_CAPE, ReduxItems.GRAND_VICTORY_MEDAL, event);
            putAfter(ReduxItems.GRAND_VICTORY_MEDAL, ReduxItems.RING_OF_CONSTRUCTION, event);
            putAfter(ReduxItems.RING_OF_CONSTRUCTION, ReduxItems.COCKATRICE_FEATHER, event);
            putAfter(ReduxItems.COCKATRICE_FEATHER, ReduxItems.FEATHER_OF_WARDING, event);

            putAfter(AetherItems.ICE_PENDANT, ReduxItems.ENCHANTED_RING, event);
            putAfter(ReduxItems.ENCHANTED_RING, ReduxItems.RING_OF_WISDOM, event);
            putAfter(ReduxItems.RING_OF_WISDOM, ReduxItems.SHROOM_RING, event);

            putAfter(AetherItems.SENTRY_BOOTS, ReduxItems.SENTRY_RING, event);

            putAfter(AetherItems.IRON_BUBBLE, ReduxItems.SOLAR_EMBLEM, event);

            putAfter(AetherItems.REGENERATION_STONE, ReduxItems.VAMPIRE_AMULET, event);

            putAfter(AetherItems.SWET_CAPE, ReduxItems.AIRBOUND_CAPE, event);

            putAfter(AetherItems.SHIELD_OF_REPULSION, ReduxItems.SNAILSHELL_SHIELD, event);
        }
        if (tab == AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES.get()) {

            putBefore(AetherItems.GRAVITITE_SWORD, ReduxItems.INFUSED_VERIDIUM_HOE, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_HOE, ReduxItems.INFUSED_VERIDIUM_AXE, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_AXE, ReduxItems.INFUSED_VERIDIUM_PICKAXE, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, ReduxItems.INFUSED_VERIDIUM_SHOVEL, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, ReduxItems.INFUSED_VERIDIUM_SWORD, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_SWORD, ReduxItems.VERIDIUM_HOE, event);
            putBefore(ReduxItems.VERIDIUM_HOE, ReduxItems.VERIDIUM_AXE, event);
            putBefore(ReduxItems.VERIDIUM_AXE, ReduxItems.VERIDIUM_PICKAXE, event);
            putBefore(ReduxItems.VERIDIUM_PICKAXE, ReduxItems.VERIDIUM_SHOVEL, event);
            putBefore(ReduxItems.VERIDIUM_SHOVEL, ReduxItems.VERIDIUM_SWORD, event);

            putBefore(AetherItems.GOLDEN_DART_SHOOTER, ReduxItems.SPEAR_OF_THE_BLIGHT, event);

            putAfter(AetherItems.ENCHANTED_DART, ReduxItems.VERIDIUM_DART_SHOOTER, event);
            putAfter(ReduxItems.VERIDIUM_DART_SHOOTER, ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, event);
            putAfter(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, ReduxItems.VERIDIUM_DART, event);

            putAfter(AetherItems.PHOENIX_BOW, ReduxItems.SUBZERO_CROSSBOW, event);

            putAfter(AetherItems.MUSIC_DISC_ASCENDING_DAWN, ReduxItems.ANCIENT_SENTRITE_MUSIC_DISC, event);

            doBoats(AetherItems.SKYROOT_CHEST_BOAT, event);
        }

        if (tab == AetherCreativeTabs.AETHER_SPAWN_EGGS.get()) {
            putBefore(AetherItems.WHIRLWIND_SPAWN_EGG, ReduxItems.VANILLA_SWET_SPAWN_EGG, event);
            putBefore(AetherItems.BLUE_SWET_SPAWN_EGG, ReduxItems.BLIGHTBUNNY_SPAWN_EGG, event);
            putAfter(AetherItems.FLYING_COW_SPAWN_EGG, ReduxItems.SHIMMERCOW_SPAWN_EGG, event);
            putAfter(AetherItems.MOA_SPAWN_EGG, ReduxItems.MYKAPOD_SPAWN_EGG, event);
        }
    }

    private static RegistryObject<? extends ItemLike> doBuildingWoods(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)
        {
            boolean addSporingLogs = woodHandler.hasSporingLogs && woodHandler.sporingLog.isPresent() && woodHandler.sporingWood.isPresent();
            putAfter(b == null ? prevEntry : b, woodHandler.log, event);
            boolean lw = (Redux.aetherGenesisCompat() || woodHandler.alwaysLogWalls);
            boolean s = woodHandler.hasStrippedLogs;
            if (lw) { putAfter(woodHandler.log, woodHandler.logWall, event); }
            putAfter(lw ? woodHandler.logWall : woodHandler.log, woodHandler.wood, event);
            if (lw) { putAfter(woodHandler.wood, woodHandler.woodWall, event); }
            putAfter(lw ? woodHandler.woodWall : woodHandler.wood, s ? woodHandler.strippedLog.get() : addSporingLogs ? woodHandler.sporingLog.get() : woodHandler.planks, event);
            if (lw && s) { putAfter(woodHandler.strippedLog.get(), woodHandler.strippedLogWall.get(), event); }
            if (s) { putAfter(lw ? woodHandler.strippedLogWall.get() : woodHandler.strippedLog.get(), woodHandler.strippedWood.get(), event); }
            if (lw && s) { putAfter(woodHandler.strippedWood.get(), woodHandler.strippedWoodWall.get(), event); }
            if (s) { putAfter(lw ? woodHandler.strippedWoodWall.get() : woodHandler.strippedWood.get(), addSporingLogs ? woodHandler.sporingLog.get() : woodHandler.planks, event); }
            if (addSporingLogs)
            {
                putAfter(woodHandler.sporingLog.get(), woodHandler.sporingWood.get(), event);
                putAfter(woodHandler.sporingWood.get(), woodHandler.planks, event);
            }
            putAfter(woodHandler.planks, woodHandler.stairs, event);
            putAfter(woodHandler.stairs, woodHandler.slab, event);
            putAfter(woodHandler.slab, woodHandler.fence, event);
            putAfter(woodHandler.fence, woodHandler.fenceGate, event);
            putAfter(woodHandler.fenceGate, woodHandler.door, event);
            putAfter(woodHandler.door, woodHandler.trapdoor, event);
            putAfter(woodHandler.trapdoor, woodHandler.pressurePlate, event);
            putAfter(woodHandler.pressurePlate, woodHandler.button, event);
            b = woodHandler.button;
        }
        return b;
    }
    private static RegistryObject<? extends ItemLike> doNaturalWoods(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)
        {
            boolean addSporingLogs = woodHandler.hasSporingLogs && woodHandler.sporingLog.isPresent() && woodHandler.sporingWood.isPresent();
            putAfter(b == null ? prevEntry : b, woodHandler.log, event);
            if (Redux.aetherGenesisCompat() || woodHandler.alwaysLogWalls) {
                putAfter(woodHandler.log, woodHandler.logWall, event);
            }
            b = Redux.aetherGenesisCompat() || woodHandler.alwaysLogWalls ? woodHandler.logWall : woodHandler.log;
            if (addSporingLogs)
            {
                putAfter(b, woodHandler.sporingLog.get(), event);
                putAfter(woodHandler.sporingLog.get(), woodHandler.sporingWood.get(), event);
                b = woodHandler.sporingWood.get();
            }
        }
        return b;
    }
    private static RegistryObject<? extends ItemLike> doSigns(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.signItem, event);
            putAfter(woodHandler.signItem, woodHandler.hangingSignItem, event);
            b = woodHandler.hangingSignItem;
        }
        return b;
    }

    private static RegistryObject<? extends ItemLike> doBoats(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.boatItem, event);
            putAfter(woodHandler.boatItem, woodHandler.chestBoatItem, event);
            b = woodHandler.chestBoatItem;
        }
        return b;
    }
    private static RegistryObject<? extends ItemLike> doBookshelves(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.bookshelf, event);
            b = woodHandler.bookshelf;
        }
        return b;
    }
    private static void doRedstoneWoods(RegistryObject<? extends ItemLike>  pressurePlate, RegistryObject<? extends ItemLike>  door, RegistryObject<? extends ItemLike>  button, RegistryObject<? extends ItemLike>  trapdoor, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> plate = null;
        RegistryObject<? extends ItemLike> dr = null;
        RegistryObject<? extends ItemLike> btn = null;
        RegistryObject<? extends ItemLike> td = null;
        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)
        {
            putAfter(plate == null ? pressurePlate : plate, woodHandler.pressurePlate, event);
            plate = woodHandler.pressurePlate;
            putAfter(dr == null ? door : dr, woodHandler.door, event);
            dr = woodHandler.door;
            putAfter(btn == null ? button : btn, woodHandler.button, event);
            btn = woodHandler.button;
            putAfter(td == null ? trapdoor : td, woodHandler.trapdoor, event);
            td = woodHandler.trapdoor;
        }
    }

    private static void putAfter(RegistryObject<? extends ItemLike> itemBefore, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().putAfter(stack(itemBefore.get()), stack(insertedItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    private static void putBefore(RegistryObject<? extends ItemLike> itemAfter, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().putBefore(stack(itemAfter.get()), stack(insertedItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void add(Supplier<? extends ItemLike> toAdd, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().put(stack(toAdd.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }


    private static ItemStack stack(ItemLike item) {
        ItemStack stack = new ItemStack(item);
        if (item instanceof VeridiumItem) {
            stack.addTagElement(VeridiumItem.NBT_KEY, ByteTag.valueOf((byte)64));
        }
        return stack;
    }

}
