package net.zepalesque.redux.misc;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;

public class ReduxTags {

    public static class Blocks {
        public static final TagKey<Block> COARSE_AETHER_DIRT = tag("coarse_aether_dirt");
        public static final TagKey<Block> AETHER_CARVER_REPLACEABLES = tag("aether_carver_replaceables");
        public static final TagKey<Block> AETHER_MOSS_REPLACEABLES = tag("aether_moss_replaceables");
        public static final TagKey<Block> FROSTED_PLANTS_PLACEMENT = tag("frosted_plants_placement");
        public static final TagKey<Block> HIGHLANDS_GRASSES = tag("highlands_grasses");
        public static final TagKey<Block> ENCHANTED_GRASSES = tag("enchanted_grasses");
        public static final TagKey<Block> AEVELIUM_GRASSES = tag("aevelium_grasses");
        public static final TagKey<Block> QUICKSOIL_BEHAVIOR = tag("quicksoil_behavior");
        public static final TagKey<Block> LOG_WALLS = tagGenesis("log_walls");
        public static final TagKey<Block> ENCHANTED_VINES_SKIP_PLACEMENT = tag("enchanted_vines_skip_placement");
        public static final TagKey<Block> MUSHROOM_CAPS = tag("mushroom_caps");
        public static final TagKey<Block> DO_NOT_REPLACE_AETHER_GRASS = tag("do_not_replace_aether_grass");
        public static final TagKey<Block> ROCK_REPLACEABLE = tag("rock_replaceable");

        public static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Redux.locate(name));
        }

        public static TagKey<Block> tagGenesis(String name) {
            return TagKey.create(Registries.BLOCK,new ResourceLocation("aether_genesis",name));
        }
    }

    public static class Items {
        public static final TagKey<Item> REDUX_AERBUNNY_FOOD_ITEMS = tag("redux_aerbunny_food_items");
        public static final TagKey<Item> REDUX_PHYG_FOOD_ITEMS = tag("redux_phyg_food_items");
        public static final TagKey<Item> REDUX_FLYING_COW_FOOD_ITEMS = tag("redux_flying_cow_food_items");
        public static final TagKey<Item> GLIMMERCOW_TEMPTATION_ITEMS = tag("glimmercow_temptation_items");
        public static final TagKey<Item> MYKAPOD_TEMPTATION_ITEMS = tag("mykapod_temptation_items");
        public static final TagKey<Item> MYKAPOD_SHED_FOOD = tag("mykapod_shed_food");
        public static final TagKey<Item> MYKAPOD_FOLLOW_ITEMS = tag("mykapod_follow_items");
        public static final TagKey<Item> SWET_JELLY = tag("swet_jelly");
        public static final TagKey<Item> CHAINS = tag("chains");
        public static final TagKey<Item> BLUE_SWET_JELLY = tag("blue_swet_jelly");
        public static final TagKey<Item> GOLDEN_SWET_JELLY = tag("golden_swet_jelly");
        public static final TagKey<Item> GOLDEN_SWET_BALL = tag("golden_swet_ball");
        public static final TagKey<Item> BLUEBERRY_PIE_EGGS = tag("eggs_for_blueberry_pie");
        public static final TagKey<Item> VERIDIUM_ADVANCEMENT_INFUSABLE = tag("veridium_advancement_infusable");
        public static final TagKey<Item> BLIGHTWARDING_ACCESSORIES = tag("blightwarding_accessories");
        public static final TagKey<Item> IS_SKYROOT_TOOL = tag("is_skyroot_tool");
        public static final TagKey<Item> BLUE_CRYSTAL_SAPLINGS = tag("blue_crystal_saplings");

        public static final TagKey<Item> INFUSED_VERIDIUM_ITEMS = tag("infused_veridium_items");

        public static final TagKey<Item> MOUSE_EAR_CAPS = tag("compat/mouse_ear_caps");

        public static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Redux.locate(name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> BLIGHTED_MOBS = tag("blighted_mobs");
        public static final TagKey<EntityType<?>> CAN_WALK_THROUGH_AUBURN_BUSH = tag("can_walk_through_auburn_bush");
        public static final TagKey<EntityType<?>> SENTRIES = tag("sentries");
        public static final TagKey<EntityType<?>> SWET_PASSTHROUGH = tag("swet_passthrough");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Redux.locate(name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> IS_GILDED = tag("is_gilded");
        public static final TagKey<Biome> IS_FROSTED = tag("is_frosted");
        // TODO: Implement in 2.1
        public static final TagKey<Biome> IS_QUICKSOIL_DESERT = tag("is_quicksoil_desert");

        public static final TagKey<Biome> AA_SKY_GRASS = tagAncient("has_aether_grass_patches");

        public static final TagKey<Biome> HAS_AETHER_CAVES = tag("has_aether_caves");
        public static final TagKey<Biome> HAS_CLOUD_LAYER = tag("has_cloud_layer");
        public static final TagKey<Biome> HAS_BLIGHTED_CAVES = tag("has_blighted_caves");
        public static final TagKey<Biome> HAS_FUNGAL_CAVES = tag("has_fungal_caves");
        public static final TagKey<Biome> HAS_MOSSY_HOLYSTONE_ORE = tag("has_mossy_holystone_ore");
        public static final TagKey<Biome> HAS_MOSSY_ROCKS = tag("has_mossy_rocks");
        public static final TagKey<Biome> HAS_BOTH_SPROUTS = tag("has_both_sprouts");
        public static final TagKey<Biome> HAS_WYNDSPROUTS = tag("has_wyndsprouts");
        public static final TagKey<Biome> HAS_VANILLA_SWET = tag("has_vanilla_swet");
        public static final TagKey<Biome> HAS_ENDERMAN = tag("has_enderman");
        public static final TagKey<Biome> HAS_VERIDIUM_ORE = tag("has_veridium_ore");
        public static final TagKey<Biome> HAS_DIVINITE = tag("has_divinite");
        public static final TagKey<Biome> HAS_SENTRITE = tag("has_sentrite");
        public static final TagKey<Biome> MODIFY_WATER_COLOR = tag("has_redux_water_color");
        public static final TagKey<Biome> HAS_REDUX_SKY_COLOR = tag("has_redux_sky_color");
        public static final TagKey<Biome> CHANGE_VANILLA_GRASS_COLORS = tag("change_vanilla_grass_colors");

        public static final TagKey<Biome> FOREST_COLORS = tag("forest_colors");
        public static final TagKey<Biome> WOODLAND_COLORS = tag("woodland_colors");
        public static final TagKey<Biome> MEADOW_COLORS = tag("meadow_colors");

        public static final TagKey<Biome> MODIFY_MUSIC = tag("modify_music");

        public static final TagKey<Biome> DENSE_LEAF_FALL = tag("dense_leaf_fall");


        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, Redux.locate(name));
        }

        private static TagKey<Biome> tagAncient(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", name));
        }
    }
    public static class DamageTypes {

        public static final TagKey<DamageType> IS_ATTACK = tag("is_attack");
        public static final TagKey<DamageType> FIREBALL = tag("fireball");
        public static final TagKey<DamageType> BYPASS_MYKAPOD = tag("bypass_mykapod");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, Redux.locate(name));
        }
    }
    public static class Sounds {

        public static final TagKey<SoundEvent> AETHER_MUSIC = tag("aether_music");
        public static final TagKey<SoundEvent> ALWAYS_ALLOW = tag("always_allow");

        private static TagKey<SoundEvent> tag(String name) {
            return TagKey.create(Registries.SOUND_EVENT, Redux.locate(name));
        }
    }
    // Portmanteau of Advancement and Override, for context
    public static class Adverrides {

        public static final TagKey<AdvancementSoundOverride> LOW_PRIORITY = tag("low_priority");

        private static TagKey<AdvancementSoundOverride> tag(String name) {
            return TagKey.create(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Redux.locate(name));
        }
    }
}
