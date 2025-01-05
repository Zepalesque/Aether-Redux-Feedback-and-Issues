package net.zepalesque.redux.item;

import com.aetherteam.aether.data.resources.registries.AetherJukeboxSongs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.combat.DartItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.registries.ReduxJukeboxSongs;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.accessories.cape.AerboundCapeItem;
import net.zepalesque.redux.item.combat.VeridiumDartItem;
import net.zepalesque.redux.item.combat.VeridiumDartShooter;
import net.zepalesque.redux.item.combat.VeridiumSwordItem;
import net.zepalesque.redux.item.property.ReduxFoods;
import net.zepalesque.redux.item.property.ReduxItemTiers;
import net.zepalesque.redux.item.tools.VeridiumAxeItem;
import net.zepalesque.redux.item.tools.VeridiumHoeItem;
import net.zepalesque.redux.item.tools.VeridiumPickaxeItem;
import net.zepalesque.redux.item.tools.VeridiumShovelItem;

import java.util.function.UnaryOperator;

public class ReduxItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Redux.MODID);

    public static final DeferredItem<Item> WYND_OATS = ITEMS.register("wynd_oats", () -> new ItemNameBlockItem(ReduxBlocks.WYNDOATS.get(), new Item.Properties().food(ReduxFoods.WYND_OATS)));
    public static final DeferredItem<Item> WYND_OAT_PANICLE = ITEMS.register("wynd_oat_panicle", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> RAW_VERIDIUM = ITEMS.register("raw_veridium", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VERIDIUM_INGOT = ITEMS.register("veridium_ingot", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> VERIDIUM_NUGGET = ITEMS.register("veridium_nugget", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> REFINED_SENTRITE = ITEMS.register("refined_sentrite", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SENTRITE_CHUNK = ITEMS.register("sentrite_chunk", () -> new Item(new Item.Properties()));

    public static final DeferredItem<VeridiumPickaxeItem.Uninfused> VERIDIUM_PICKAXE = ITEMS.register("veridium_pickaxe", () -> new VeridiumPickaxeItem.Uninfused(ReduxItemTiers.VERIDIUM, new Item.Properties().attributes(PickaxeItem.createAttributes(ReduxItemTiers.VERIDIUM, 0, -2.9F))));
    public static final DeferredItem<VeridiumAxeItem.Uninfused> VERIDIUM_AXE = ITEMS.register("veridium_axe", () -> new VeridiumAxeItem.Uninfused(ReduxItemTiers.VERIDIUM, new Item.Properties().attributes(AxeItem.createAttributes(ReduxItemTiers.VERIDIUM, 1, 0 -3.2F))));
    public static final DeferredItem<VeridiumShovelItem.Uninfused> VERIDIUM_SHOVEL = ITEMS.register("veridium_shovel", () -> new VeridiumShovelItem.Uninfused(ReduxItemTiers.VERIDIUM, new Item.Properties().attributes(ShovelItem.createAttributes(ReduxItemTiers.VERIDIUM, 0, -3.1F))));
    public static final DeferredItem<VeridiumHoeItem.Uninfused> VERIDIUM_HOE = ITEMS.register("veridium_hoe", () -> new VeridiumHoeItem.Uninfused(ReduxItemTiers.VERIDIUM, new Item.Properties().attributes(HoeItem.createAttributes(ReduxItemTiers.VERIDIUM, 0, -1.7F))));
    public static final DeferredItem<VeridiumPickaxeItem> INFUSED_VERIDIUM_PICKAXE = ITEMS.register("infused_veridium_pickaxe", () -> new VeridiumPickaxeItem(ReduxItemTiers.INFUSED_VERIDIUM, new Item.Properties().attributes(PickaxeItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.6F)), VERIDIUM_PICKAXE));
    public static final DeferredItem<VeridiumAxeItem> INFUSED_VERIDIUM_AXE = ITEMS.register("infused_veridium_axe", () -> new VeridiumAxeItem(ReduxItemTiers.INFUSED_VERIDIUM, new Item.Properties().attributes(AxeItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.9F)), VERIDIUM_AXE));
    public static final DeferredItem<VeridiumShovelItem> INFUSED_VERIDIUM_SHOVEL = ITEMS.register("infused_veridium_shovel", () -> new VeridiumShovelItem(ReduxItemTiers.INFUSED_VERIDIUM, new Item.Properties().attributes(ShovelItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 0, -2.8F)), VERIDIUM_SHOVEL));
    public static final DeferredItem<VeridiumHoeItem> INFUSED_VERIDIUM_HOE = ITEMS.register("infused_veridium_hoe", () -> new VeridiumHoeItem(ReduxItemTiers.INFUSED_VERIDIUM, new Item.Properties().attributes(HoeItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 0, -1.4F)), VERIDIUM_HOE));

    public static final DeferredItem<VeridiumSwordItem.Uninfused> VERIDIUM_SWORD = ITEMS.register("veridium_sword", () -> new VeridiumSwordItem.Uninfused(ReduxItemTiers.VERIDIUM, new Item.Properties().attributes(SwordItem.createAttributes(ReduxItemTiers.VERIDIUM, 2, -2.5F))));
    public static final DeferredItem<VeridiumSwordItem> INFUSED_VERIDIUM_SWORD = ITEMS.register("infused_veridium_sword", () -> new VeridiumSwordItem(ReduxItemTiers.INFUSED_VERIDIUM, new Item.Properties().attributes(SwordItem.createAttributes(ReduxItemTiers.VERIDIUM, 4, -2.2F)), VERIDIUM_SWORD));

    public static final DeferredItem<Item> VERIDIUM_DART = ITEMS.register("veridium_dart", () -> new VeridiumDartItem(new Item.Properties()));
    public static final DeferredItem<VeridiumDartShooter.Uninfused> VERIDIUM_DART_SHOOTER = ITEMS.register("veridium_dart_shooter", () -> new VeridiumDartShooter.Uninfused(VERIDIUM_DART, 9, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<VeridiumDartShooter> INFUSED_VERIDIUM_DART_SHOOTER = ITEMS.register("infused_veridium_dart_shooter", () -> new VeridiumDartShooter(VERIDIUM_DART, 7, new Item.Properties().stacksTo(1), ReduxItems.VERIDIUM_DART_SHOOTER));

    public static final DeferredItem<Item> MUSIC_DISC_SENTIENCE = ITEMS.register("music_disc_sentience", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ReduxJukeboxSongs.SENTIENCE)));


    public static final DeferredItem<AerboundCapeItem> AERBOUND_CAPE = ITEMS.register("aerbound_cape", () -> new AerboundCapeItem(new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT)));

    public static void registerAccessories() {
        registerAccessory(AERBOUND_CAPE.get());
    }

    private static <T extends Item & Accessory> void registerAccessory(T accessory) {
        AccessoriesAPI.registerAccessory(accessory, accessory);
    }
}
