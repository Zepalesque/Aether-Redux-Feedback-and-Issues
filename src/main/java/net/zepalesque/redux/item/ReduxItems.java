package net.zepalesque.redux.item;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.ring.RingItem;
import com.aetherteam.aether.item.combat.DartItem;
import com.aetherteam.aether.item.food.GummySwetItem;
import com.aetherteam.aether.item.materials.SwetBallItem;
import com.google.common.base.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.accessory.AbilityTooltipMiscItem;
import net.zepalesque.redux.item.accessory.AbilityTooltipPendantItem;
import net.zepalesque.redux.item.accessory.AbilityTooltipRingItem;
import net.zepalesque.redux.item.accessory.AirboundCapeItem;
import net.zepalesque.redux.item.accessory.ConstructionRingItem;
import net.zepalesque.redux.item.accessory.SnailshellShieldItem;
import net.zepalesque.redux.item.accessory.SolarEmblemItem;
import net.zepalesque.redux.item.accessory.VampireAmuletItem;
import net.zepalesque.redux.item.food.ReduxFoods;
import net.zepalesque.redux.item.misc.BlightedSporesItem;
import net.zepalesque.redux.item.tools.VeridiumAxeItem;
import net.zepalesque.redux.item.tools.VeridiumHoeItem;
import net.zepalesque.redux.item.tools.VeridiumPickaxeItem;
import net.zepalesque.redux.item.tools.VeridiumShovelItem;
import net.zepalesque.redux.item.util.ReduxItemTiers;
import net.zepalesque.redux.item.weapons.BlightSpearItem;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;
import net.zepalesque.redux.item.weapons.VeridiumDartShooter;
import net.zepalesque.redux.item.weapons.VeridiumSwordItem;

import java.util.function.UnaryOperator;

public class ReduxItems {

    public static class TooltipUtils {
        /**
         * Returns the given component, or the default message if the shift key is up.
         */
        public static final UnaryOperator<Component> SHIFT_OR_DEFAULT = (component -> Screen.hasShiftDown() ? component : Component.translatable("gui.aether_redux.shift_info", Minecraft.getInstance().options.keyShift.getKey().getDisplayName().getString()).withStyle(ChatFormatting.DARK_GRAY));
        /**
         * Returns the given component, or null if the shift key is up.
         */
        public static final UnaryOperator<Component> SHIFT_OR_NULL = (component -> Screen.hasShiftDown() ? component : null);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Redux.MODID);

    public static final RegistryObject<Item> GOLDEN_SWET_BALL = register("golden_swet_ball", () -> new SwetBallItem(new Item.Properties()));
    public static final RegistryObject<Item> VANILLA_SWET_BALL = register("vanilla_swet_ball", () -> new SwetBallItem(new Item.Properties()));
    public static final RegistryObject<Item> VANILLA_GUMMY_SWET = register("vanilla_gummy_swet", GummySwetItem::new);
    public static final RegistryObject<Item> BLUE_SWET_JELLY = register("blue_swet_jelly", () -> new Item(new Item.Properties().food(ReduxFoods.SWET_JELLY)));
    public static final RegistryObject<Item> GOLDEN_SWET_JELLY = register("golden_swet_jelly", () -> new Item(new Item.Properties().food(ReduxFoods.SWET_JELLY)));
    public static final RegistryObject<Item> VANILLA_SWET_JELLY = register("vanilla_swet_jelly", () -> new Item(new Item.Properties().food(ReduxFoods.SWET_JELLY)));

    public static final RegistryObject<Item> BLUEBERRY_PIE = register("blueberry_pie", () -> new Item(new Item.Properties().food(ReduxFoods.BLUEBERRY_PIE)));
    public static final RegistryObject<Item> ENCHANTED_BLUEBERRY_PIE = register("enchanted_blueberry_pie", () -> new Item(new Item.Properties().food(ReduxFoods.ENCHANTED_BLUEBERRY_PIE)));



    public static final RegistryObject<Item> WYND_OATS = register("wynd_oats", () -> new ItemNameBlockItem(ReduxBlocks.WYNDOATS.get(), new Item.Properties().food(ReduxFoods.WYNDSPROUT_SEEDS)));
    public static final RegistryObject<Item> WYND_OAT_PANICLE = register("wynd_oat_panicle", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WYND_BAGEL = register("wynd_bagel", () -> new Item(new Item.Properties().food(ReduxFoods.WYNDSPROUT_BAGEL)));
    public static final RegistryObject<Item> BLUEBERRY_BAGEL = register("blueberry_bagel", () -> new Item(new Item.Properties().food(ReduxFoods.BLUEBERRY_BAGEL)));
    public static final RegistryObject<Item> OATMEAL = register("oatmeal", () -> new BowlFoodItem(new Item.Properties().food(ReduxFoods.OATMEAL).stacksTo(1)));

    public static final RegistryObject<Item> RAW_GRAVITITE = register("raw_gravitite", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> RAW_VALKYRUM = register("raw_valkyrum", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final RegistryObject<Item> GRAVITITE_INGOT = register("gravitite_ingot", () -> new Item(new Item.Properties().rarity(Rarity.RARE)));


    public static final RegistryObject<Item> MYKAPOD_SHELL_CHUNK = register("mykapod_shell_chunk", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SNAILSHELL_SHIELD = register("snailshell_shield", () -> new SnailshellShieldItem(new Item.Properties().stacksTo(1)));



    public static final RegistryObject<Item> ENCHANTED_RING = register("enchanted_ring", () -> new RingItem(ReduxSoundEvents.EQUIP_ENCHANTED_RING, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> GRAND_VICTORY_MEDAL = register("grand_victory_medal", () -> new AbilityTooltipPendantItem(Redux.locate("grand_medal"), ReduxSoundEvents.EQUIP_GRAND_MEDAL, new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "grand_medal_regen", "grand_medal_queen_refight"));
    public static final RegistryObject<Item> SENTRY_RING = register("sentry_ring", () -> new AbilityTooltipRingItem(ReduxSoundEvents.EQUIP_SENTRY_RING, new Item.Properties().stacksTo(1), "sentry_ring_embers"));
    public static final RegistryObject<Item> SHROOM_RING = register("shroom_ring", () -> new AbilityTooltipRingItem(ReduxSoundEvents.EQUIP_SHROOM_RING, new Item.Properties().stacksTo(1), "shroom_ring_adrenaline"));
    public static final RegistryObject<Item> AIRBOUND_CAPE = register("airbound_cape", () -> new AirboundCapeItem("airbound_cape", new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "airbound_cape_jump_boost"));
    public static final RegistryObject<Item> SOLAR_EMBLEM = register("solar_emblem", () -> new SolarEmblemItem(new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT)));
    public static final RegistryObject<Item> RING_OF_CONSTRUCTION = register("ring_of_construction", () -> new ConstructionRingItem(ReduxSoundEvents.EQUIP_CONSTRUCTION_RING, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> RING_OF_WISDOM = register("ring_of_wisdom", () -> new AbilityTooltipRingItem(ReduxSoundEvents.EQUIP_WISDOM_RING, new Item.Properties().stacksTo(1), "wisdom_ring_xp_increase"));

    public static final RegistryObject<Item> MOUSE_EAR_SOUP = register("mouse_ear_soup", () -> new BowlFoodItem(new Item.Properties().food(ReduxFoods.MOUSE_EAR_SOUP).stacksTo(1)));

    public static final RegistryObject<Item> SUBZERO_CROSSBOW = register("subzero_crossbow", () -> new SubzeroCrossbowItem(new Item.Properties().stacksTo(1).durability(1024).rarity(AetherItems.AETHER_LOOT)));

    public static final RegistryObject<SpawnEggItem> VANILLA_SWET_SPAWN_EGG = ITEMS.register("vanilla_swet_spawn_egg", () -> new ForgeSpawnEggItem(ReduxEntityTypes.VANILLA_SWET, 0xF7F3E3, 0xD3CABB, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_SENTRITE_MUSIC_DISC = ITEMS.register("ancient_sentrite_music_disc", () -> (new RecordItem(1, ReduxSoundEvents.MUSIC_DISC_SENTIENCE, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 4300)));

    public static final RegistryObject<SpawnEggItem> SHIMMERCOW_SPAWN_EGG = ITEMS.register("shimmercow_spawn_egg", () -> new ForgeSpawnEggItem(ReduxEntityTypes.SHIMMERCOW, 0x7B7F90, 0x7876CC, new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> MYKAPOD_SPAWN_EGG = ITEMS.register("mykapod_spawn_egg", () -> new ForgeSpawnEggItem(ReduxEntityTypes.MYKAPOD, 0xDAE3E7, 0x7178C2, new Item.Properties()));

    public static final RegistryObject<SpawnEggItem> BLIGHTBUNNY_SPAWN_EGG = ITEMS.register("blightbunny_spawn_egg", () -> new ForgeSpawnEggItem(ReduxEntityTypes.BLIGHTBUNNY, 0x6B609E, 0x8FD6C0, new Item.Properties()));


    public static final RegistryObject<Item> BLIGHTED_SPORES = register("blighted_spores", () -> new BlightedSporesItem(new Item.Properties()));

    // TODO in future update: Blighted Dagger (placeholder name) to make moas lay cockatrice eggs, so you can get a pet cockatrice to help you fight! :D
    public static final RegistryObject<Item> BLIGHTBUNNY_FANG = register("blightbunny_fang", () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> RAW_VERIDIUM = register("raw_veridium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VERIDIUM_INGOT = register("veridium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VERIDIUM_NUGGET = register("veridium_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<VeridiumPickaxeItem.Uninfused> VERIDIUM_PICKAXE = ITEMS.register("veridium_pickaxe", () -> new VeridiumPickaxeItem.Uninfused(ReduxItemTiers.VERIDIUM, 0, -2.9F, new Item.Properties()));
    public static final RegistryObject<VeridiumAxeItem.Uninfused> VERIDIUM_AXE = ITEMS.register("veridium_axe", () -> new VeridiumAxeItem.Uninfused(ReduxItemTiers.VERIDIUM, 1, 0 -3.2F, new Item.Properties()));
    public static final RegistryObject<VeridiumShovelItem.Uninfused> VERIDIUM_SHOVEL = ITEMS.register("veridium_shovel", () -> new VeridiumShovelItem.Uninfused(ReduxItemTiers.VERIDIUM, 0, -3.1F, new Item.Properties()));
    public static final RegistryObject<VeridiumHoeItem.Uninfused> VERIDIUM_HOE = ITEMS.register("veridium_hoe", () -> new VeridiumHoeItem.Uninfused(ReduxItemTiers.VERIDIUM, 0, -1.7F, new Item.Properties()));
    public static final RegistryObject<VeridiumPickaxeItem> INFUSED_VERIDIUM_PICKAXE = ITEMS.register("infused_veridium_pickaxe", () -> new VeridiumPickaxeItem(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.6F, new Item.Properties(), VERIDIUM_PICKAXE));
    public static final RegistryObject<VeridiumAxeItem> INFUSED_VERIDIUM_AXE = ITEMS.register("infused_veridium_axe", () -> new VeridiumAxeItem(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.9F, new Item.Properties(), VERIDIUM_AXE));
    public static final RegistryObject<VeridiumShovelItem> INFUSED_VERIDIUM_SHOVEL = ITEMS.register("infused_veridium_shovel", () -> new VeridiumShovelItem(ReduxItemTiers.INFUSED_VERIDIUM, 0, -2.8F, new Item.Properties(), VERIDIUM_SHOVEL));
    public static final RegistryObject<VeridiumHoeItem> INFUSED_VERIDIUM_HOE = ITEMS.register("infused_veridium_hoe", () -> new VeridiumHoeItem(ReduxItemTiers.INFUSED_VERIDIUM, 0, -1.4F, new Item.Properties(), VERIDIUM_HOE));

    public static final RegistryObject<VeridiumSwordItem.Uninfused> VERIDIUM_SWORD = register("veridium_sword", () -> new VeridiumSwordItem.Uninfused(ReduxItemTiers.VERIDIUM, 2, -2.5F, new Item.Properties()));
    public static final RegistryObject<SwordItem> INFUSED_VERIDIUM_SWORD = register("infused_veridium_sword", () -> new VeridiumSwordItem(ReduxItemTiers.INFUSED_VERIDIUM, 4, -2.2F, new Item.Properties(), ReduxItems.VERIDIUM_SWORD));

    public static final RegistryObject<Item> VERIDIUM_DART = ITEMS.register("veridium_dart", () -> new DartItem(ReduxEntityTypes.VERIDIUM_DART, new Item.Properties()));
    public static final RegistryObject<VeridiumDartShooter.Uninfused> VERIDIUM_DART_SHOOTER = ITEMS.register("veridium_dart_shooter", () -> new VeridiumDartShooter.Uninfused(VERIDIUM_DART, 9, new Item.Properties().stacksTo(1)));
    public static final RegistryObject<VeridiumDartShooter> INFUSED_VERIDIUM_DART_SHOOTER = ITEMS.register("infused_veridium_dart_shooter", () -> new VeridiumDartShooter(VERIDIUM_DART, 7, new Item.Properties().stacksTo(1), ReduxItems.VERIDIUM_DART_SHOOTER));


    public static final RegistryObject<Item> COCKATRICE_FEATHER = register("cockatrice_feather", () -> new AbilityTooltipMiscItem(new Item.Properties().stacksTo(1),
            "cockatrice_feather_protection"));

    public static final RegistryObject<Item> FEATHER_OF_WARDING = register("feather_of_warding", () -> new AbilityTooltipMiscItem(new Item.Properties().stacksTo(1),
            "feather_of_warding_immunity"));

    public static final RegistryObject<Item> ZANBERRY = register("zanberry", () -> new Item(new Item.Properties().food(ReduxFoods.CHROMABERRY)));
    public static final RegistryObject<Item> LIGHTROOT_CLUMP = register("lightroot_clump", () -> new ItemNameBlockItem(ReduxBlocks.LIGHTROOTS.get(),  new Item.Properties().food(ReduxFoods.LIGHTROOT_CLUMP)));

    public static final RegistryObject<Item> VAMPIRE_AMULET = register("vampire_amulet", () -> new VampireAmuletItem(ReduxSoundEvents.EQUIP_VAMPIRE_AMULET, new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "vampire_amulet_night_ability", "vampire_amulet_day_debuff"));

    public static final RegistryObject<Item> SENTRY_CHIP = register("sentry_chip", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPEAR_OF_THE_BLIGHT = register("spear_of_the_blight", () -> new BlightSpearItem(new Item.Properties().stacksTo(1).durability(256), 5.0D, -2.7F));

    public static final RegistryObject<Item> REFINED_SENTRITE = ITEMS.register("refined_sentrite", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SENTRITE_CHUNK = ITEMS.register("sentrite_chunk", () -> new Item(new Item.Properties()));


    public static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item)
    {
        return ITEMS.register(name, item);
    }


}
