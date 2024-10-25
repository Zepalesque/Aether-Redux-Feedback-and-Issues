package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.providers.AetherItemModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.loaders.ItemLayerModelBuilder;
import net.minecraftforge.client.model.generators.loaders.SeparateTransformsModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;

import java.util.function.Function;
import java.util.function.Supplier;

public class ReduxItemModelData extends AetherItemModelProvider {
    public ReduxItemModelData(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }
    @Override
    protected void registerModels() {

        itemBlockFlatCustomTexture(ReduxBlocks.SHORT_AETHER_GRASS, "natural/aether_medium_grass");
        itemBlock(ReduxBlocks.HOLYSILT);
        itemBlock(ReduxBlocks.DRIFTSHALE);
        itemBlock(ReduxBlocks.POLISHED_DRIFTSHALE);
        itemBlock(ReduxBlocks.POLISHED_DRIFTSHALE_SLAB);
        itemBlock(ReduxBlocks.POLISHED_DRIFTSHALE_STAIRS);
        itemWallBlock(ReduxBlocks.POLISHED_DRIFTSHALE_WALL, ReduxBlocks.POLISHED_DRIFTSHALE, "natural/");
        itemBlock(ReduxBlocks.DIVINITE);
        itemBlock(ReduxBlocks.DIVINITE_SLAB);
        itemBlock(ReduxBlocks.DIVINITE_STAIRS);
        itemWallBlock(ReduxBlocks.DIVINITE_WALL, ReduxBlocks.DIVINITE, "natural/");
        itemBlock(ReduxBlocks.SENTRITE);
        itemBlock(ReduxBlocks.SENTRITE_SLAB);
        itemBlock(ReduxBlocks.SENTRITE_STAIRS);
        itemWallBlock(ReduxBlocks.SENTRITE_WALL, ReduxBlocks.SENTRITE, "natural/");
        itemBlock(ReduxBlocks.SENTRITE_BRICKS);
        itemBlock(ReduxBlocks.SENTRITE_BRICK_SLAB);
        itemBlock(ReduxBlocks.SENTRITE_BRICK_STAIRS);
        itemWallBlock(ReduxBlocks.SENTRITE_BRICK_WALL, ReduxBlocks.SENTRITE_BRICKS, "construction/");
        itemBlockFlatTintOverlay(ReduxBlocks.IRIDIA, "natural/");
        itemBlockFlatTintOverlay(ReduxBlocks.XAELIA_PATCH, "natural/");
        itemBlock(ReduxBlocks.GILDED_HOLYSTONE);
        itemBlock(ReduxBlocks.GILDED_HOLYSTONE_SLAB);
        itemBlock(ReduxBlocks.GILDED_HOLYSTONE_STAIRS);
        itemWallBlock(ReduxBlocks.GILDED_HOLYSTONE_WALL, ReduxBlocks.GILDED_HOLYSTONE, "natural/");
        itemBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE);
        itemBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB);
        itemBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS);
        itemWallBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, "natural/");
        item(ReduxItems.BLUE_SWET_JELLY, "food/");
        item(ReduxItems.GOLDEN_SWET_JELLY, "food/");
        item(ReduxItems.GOLDEN_SWET_BALL, "materials/");
        item(ReduxItems.VANILLA_SWET_BALL, "materials/");
        item(ReduxItems.VANILLA_SWET_JELLY, "food/");
        item(ReduxItems.VANILLA_GUMMY_SWET, "food/");
        vampireAmulet(ReduxItems.VAMPIRE_AMULET, "accessories/");
        item(ReduxItems.ENCHANTED_RING, "accessories/");
        item(ReduxItems.SHROOM_RING, "accessories/");
        item(ReduxItems.RING_OF_WISDOM, "accessories/");
        dartShooterGlow(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, "weapons/");
        dartShooterItem(ReduxItems.VERIDIUM_DART_SHOOTER.get(), "weapons/");
        item(ReduxItems.VERIDIUM_DART, "weapons/");
        item(ReduxItems.SENTRY_CHIP, "materials/");
        item(ReduxItems.WYND_OATS, "food/");
        item(ReduxItems.BLUEBERRY_PIE, "food/");
        item(ReduxItems.ENCHANTED_BLUEBERRY_PIE, "food/");
        itemBlockFlatCustomTexture(ReduxBlocks.GOLDEN_CLOVER, "natural/golden_clover_top");
        enchanableOrTintableFlower(ReduxBlocks.AURUM, "natural/");
        enchanableOrTintableFlower(ReduxBlocks.ZYATRIX, "natural/");
        eggItem(ReduxItems.VANILLA_SWET_SPAWN_EGG);
        eggItem(ReduxItems.SHIMMERCOW_SPAWN_EGG);
        eggItem(ReduxItems.MYKAPOD_SPAWN_EGG);
        eggItem(ReduxItems.BLIGHTBUNNY_SPAWN_EGG);
        rotatedItem(ReduxItems.GRAND_VICTORY_MEDAL.get(), "accessories/");
        item(ReduxItems.SENTRY_RING, "accessories/");
        item(ReduxItems.RING_OF_CONSTRUCTION, "accessories/");
        item(ReduxItems.AIRBOUND_CAPE, "accessories/");
        item(ReduxItems.SOLAR_EMBLEM, "accessories/");
        item(ReduxItems.ANCIENT_SENTRITE_MUSIC_DISC, "misc/");
        item(ReduxItems.MOUSE_EAR_SOUP, "food/");
        item(ReduxItems.OATMEAL, "food/");
        itemBlock(ReduxBlocks.COARSE_AETHER_DIRT);
        aercloudItem(ReduxBlocks.BLIGHTED_AERCLOUD.get());
        item(ReduxItems.BLIGHTBUNNY_FANG, "materials/");


        itemBlock(ReduxBlocks.SHELL_SHINGLES);
        itemBlock(ReduxBlocks.SHELL_SHINGLE_SLAB);
        itemBlock(ReduxBlocks.SHELL_SHINGLE_STAIRS);
        itemWallBlock(ReduxBlocks.SHELL_SHINGLE_WALL, ReduxBlocks.SHELL_SHINGLES, "construction/");
        itemBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLES);
        itemBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB);
        itemBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS);
        itemWallBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL, ReduxBlocks.ENCHANTED_SHELL_SHINGLES, "construction/");

        itemBlock(ReduxBlocks.BLIGHTWILLOW_LEAVES);

        spear(ReduxItems.SPEAR_OF_THE_BLIGHT, "weapons/");

        this.item(ReduxItems.REFINED_SENTRITE.get(), "materials/");
        this.item(ReduxItems.SENTRITE_CHUNK.get(), "materials/");

        this.itemBlock(ReduxBlocks.REFINED_SENTRITE_BLOCK.get());
        this.item(ReduxBlocks.SENTRITE_LANTERN.get().asItem(), "misc/");
        this.item(ReduxBlocks.SENTRITE_CHAIN.get().asItem(), "misc/");

        this.itemBlockWithParent(ReduxBlocks.GOLDEN_LEAF_PILE.get(), (block) -> modLoc(BLOCK_FOLDER + "/" + this.blockName(block) + "1"));
        this.itemBlockWithParent(ReduxBlocks.GILDED_LEAF_PILE.get(), (block) -> modLoc(BLOCK_FOLDER + "/" + this.blockName(block) + "1"));
        this.itemBlockWithParent(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE.get(), (block) -> modLoc(BLOCK_FOLDER + "/" + this.blockName(block) + "1"));

        itemBlockFlatGlow(ReduxBlocks.CLOUDCAP_MUSHLING, "natural/");

        this.itemBlockFlat(ReduxBlocks.BLIGHTWILLOW_SAPLING, "natural/");
        this.itemBlockFlat(ReduxBlocks.FIELDSPROOT_SAPLING.get(), "natural/");
        this.itemBlockFlat(ReduxBlocks.CRYSTAL_SAPLING.get(), "natural/");
        this.itemBlockFlat(ReduxBlocks.CRYSTAL_FRUIT_SAPLING.get(), "natural/");
        this.itemBlockFlat(ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING.get(), "natural/");

        itemBlock(ReduxBlocks.GLACIA_LEAVES);
        this.itemBlockFlat(ReduxBlocks.GLACIA_SAPLING.get(), "natural/");
        itemBlock(ReduxBlocks.PURPLE_GLACIA_LEAVES);
        this.itemBlockFlat(ReduxBlocks.PURPLE_GLACIA_SAPLING.get(), "natural/");

        itemBlock(ReduxBlocks.AVELIUM);
        itemBlock(ReduxBlocks.CLOUD_CAP_BLOCK);
        itemBlock(ReduxBlocks.CLOUDCAP_SPORES);
        itemBlockFlat(ReduxBlocks.JELLYSHROOM, "natural/");
        itemBlockFlatGlow(ReduxBlocks.SHIMMERSTOOL, "natural/");
        itemBlockFlatTintGlow(ReduxBlocks.LUXWEED, "natural/");
        itemBlockFlatTintOverlay(ReduxBlocks.SPIROLYCTIL, "natural/");
        itemBlockFlatTintOverlay(ReduxBlocks.BLIGHTSHADE, "natural/");


        blockCustomTexture(ReduxBlocks.FIELDSPROOT_LEAVES, "natural/", "fieldsproot_leaves");
        itemCustomTexture(ReduxBlocks.FIELDSPROOT_PETALS, "misc/", "fieldsproot_petals");
        item(ReduxItems.VERIDIUM_NUGGET.get(), "materials/");

        itemBlockFlatItemTexture(ReduxBlocks.VERIDIUM_LANTERN, "misc/");
        itemBlockFlatItemTexture(ReduxBlocks.VERIDIUM_CHAIN, "misc/");

        this.itemBlock(ReduxBlocks.ZANBERRY_BUSH.get());
        this.itemBlockFlat(ReduxBlocks.ZANBERRY_BUSH_STEM.get(), "natural/");
        this.itemFullGlow(ReduxItems.BLIGHTED_SPORES, "materials/");

        this.itemFullGlow(ReduxItems.LIGHTROOT_CLUMP, "food/");

        this.itemBlockFlat(ReduxBlocks.AVELIUM_ROOTS.get(), "natural/");
        this.item(ReduxBlocks.AVELIUM_SPROUTS.get().asItem(), "misc/");

        this.aercloudItem(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK.get());

        this.itemBlock(ReduxBlocks.BLIGHTMOSS_BLOCK.get());
        this.itemBlock(ReduxBlocks.BLIGHTMOSS_CARPET.get());
        this.itemBlock(ReduxBlocks.FUNGAL_GROWTH.get());
        this.itemBlock(ReduxBlocks.FUNGAL_CARPET.get());
        this.itemBlock(ReduxBlocks.GILDED_OAK_LEAVES.get());
        this.itemBlockFlat(ReduxBlocks.GILDED_OAK_SAPLING.get(), "natural/");

        this.itemBlockFlat(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(), "natural/");

        this.itemBlock(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get());


        this.itemBlockFlatTintGlowOverlay(ReduxBlocks.LUMINA, "natural/");
        this.itemBlockFlatTintOverlay(ReduxBlocks.DAGGERBLOOM, "natural/");

        this.itemBlockFlatTintOverlay(ReduxBlocks.THERATIP, "natural/");

        this.itemBlockFlatTintGlowOverlay(ReduxBlocks.FLAREBLOSSOM, "natural/");
        this.itemBlockFlatTintOverlay(ReduxBlocks.INFERNIA, "natural/");

        itemBlockFlatTintOverlay(ReduxBlocks.WYNDSPROUTS, "natural/");
        itemBlockFlatTintOverlay(ReduxBlocks.SKYSPROUTS, "natural/");
        itemBlockFlat(ReduxBlocks.SPLITFERN, "natural/");
        this.item(ReduxItems.WYND_OAT_PANICLE, "materials/");

        this.itemBlockFlatOtherBlockTexture(ReduxBlocks.GOLDEN_VINES, ReduxBlocks.GOLDEN_VINES_PLANT, "natural/");
        this.itemBlockFlatOtherBlockTexture(ReduxBlocks.GILDED_VINES, ReduxBlocks.GILDED_VINES_PLANT, "natural/");
        this.itemBlockFlatGlowOtherTexture(ReduxBlocks.CORRUPTED_VINES, ReduxBlocks.CORRUPTED_VINES_PLANT, "natural/");

        this.handheldItem(ReduxItems.VERIDIUM_SWORD.get(), "weapons/");
        this.handheldItem(ReduxItems.VERIDIUM_PICKAXE.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_AXE.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_SHOVEL.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_HOE.get(), "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_SWORD, "weapons/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_PICKAXE, "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_AXE, "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_SHOVEL, "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_HOE, "tools/");

        this.item(ReduxItems.VERIDIUM_INGOT, "materials/");
        this.item(ReduxItems.RAW_VERIDIUM, "materials/");

        this.item(ReduxItems.RAW_GRAVITITE, "materials/");
        this.item(ReduxItems.RAW_VALKYRUM, "materials/");
        this.item(ReduxItems.GRAVITITE_INGOT, "materials/");
        this.itemBlock(ReduxBlocks.GRAVITITE_BLOCK);
        this.itemBlock(ReduxBlocks.RAW_GRAVITITE_BLOCK);

        this.item(ReduxItems.MYKAPOD_SHELL_CHUNK, "materials/");
        this.item(ReduxItems.SNAILSHELL_SHIELD, "accessories/");

        this.itemBlock(ReduxBlocks.VERIDIUM_BLOCK.get());
        this.itemBlock(ReduxBlocks.VERIDIUM_ORE.get());
        this.itemBlock(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());

        this.itemBlock(ReduxBlocks.RAW_VALKYRUM_BLOCK.get());

        this.itemGlow(ReduxItems.COCKATRICE_FEATHER, "accessories/");
        this.itemGlow(ReduxItems.FEATHER_OF_WARDING, "accessories/");
        this.item(ReduxItems.WYND_BAGEL, "food/");
        this.item(ReduxItems.BLUEBERRY_BAGEL, "food/");


        this.item(ReduxItems.ZANBERRY, "food/");



        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)        {
            woodHandler.generateItemModels(this);
        }



        this.itemBlock(ReduxBlocks.CARVED_BASE.get());
        this.itemBlock(ReduxBlocks.CARVED_PILLAR.get());
        this.itemBlock(ReduxBlocks.SENTRY_BASE.get());
        this.itemBlock(ReduxBlocks.SENTRY_PILLAR.get());

        this.itemOverlayColumn(ReduxBlocks.LOCKED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/lock", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.LOCKED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/lock", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.LOCKED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/lock", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.LOCKED_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/lock", "dungeon/");

        this.itemOverlayColumn(ReduxBlocks.TRAPPED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/exclamation", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.TRAPPED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/exclamation", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.TRAPPED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/exclamation", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/exclamation", "dungeon/");

        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/door", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/door", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/door", "dungeon/");
        this.itemOverlayColumn(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/door", "dungeon/");

        this.itemBlock(ReduxBlocks.RUNELIGHT.get(), "_on");
        this.itemOverlayDungeonBlock(ReduxBlocks.LOCKED_RUNELIGHT.get(), ReduxBlocks.RUNELIGHT.get(), "lock", "dungeon/", "_on");
        this.itemOverlayDungeonBlock(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get(), ReduxBlocks.SENTRITE_BRICKS.get(), "construction/", "lock");

    }
    public String blockName(Supplier<? extends Block> block) {
        return ForgeRegistries.BLOCKS.getKey(block.get()).getPath();
    }

    protected ResourceLocation texture(String name) {
        return modLoc("block/" + name);
    }

    protected ResourceLocation texture(String name, String location) {
        return modLoc("block/" + location + name);
    }

    public ItemModelBuilder item(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }
    public ItemModelBuilder vampireAmulet(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        String active = id.getPath() + "_active";
        ItemModelBuilder builder = withExistingParent(active, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath() + "_active"));
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath() + "_inactive")).override().predicate(Redux.locate("active"), 1.0F).model(this.getExistingFile(this.modLoc("item/" + active))).end();
    }

    public ItemModelBuilder enchantedClover(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        String active = "enchanted_" + id.getPath();
        ItemModelBuilder builder = withExistingParent(active, mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + "enchanted_" + id.getPath()));
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()) + "_overlay")
                .texture("layer1", modLoc("item/" + location + id.getPath() + "_partial_tint"))
                .texture("layer2", modLoc("item/" + location + id.getPath()))
                .override().predicate(Redux.locate("enchanted"), 1.0F).model(this.getExistingFile(this.modLoc("item/" + active))).end();
    }

    public ItemModelBuilder enchanableOrTintableFlower(Supplier<? extends Block> block, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(block.get().asItem());
        String ench = "enchanted_" + id.getPath();
        ItemModelBuilder builder = withExistingParent(ench, mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + location + "enchanted_" + id.getPath()));
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer1", texture(blockName(block), location))
                .texture("layer0", texture(blockName(block) + "_overlay", location))
                .override().predicate(Redux.locate("enchanted"), 1.0F).model(this.getExistingFile(this.modLoc("item/" + ench))).end();
    }

    public ItemModelBuilder itemGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).texture("layer1", modLoc("item/" + location + id.getPath() + "_glow")).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder handheldGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).texture("layer1", modLoc("item/" + location + id.getPath() + "_glow")).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }

    public void dartShooterGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        this.withExistingParent(id.getPath(), this.mcLoc("item/handheld"))
                .texture("layer0", this.modLoc("item/" + location + id.getPath()))
                .texture("layer1", modLoc("item/" + location + id.getPath() + "_glow")).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(0.0F, -90.0F, 45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(0.0F, 90.0F, -45.0F).translation(0.0F, 1.5F, -1.0F).scale(0.85F, 0.85F, 0.85F).end()
                .end();
    }


    public ItemModelBuilder itemFullGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 0)).end();
    }

    public ItemModelBuilder blockCustomTexture(Supplier<? extends Block> block, String loc, String name) {
        return withExistingParent(itemName(block.get().asItem()), "block/cube_all")
                .texture("all", modLoc("block/" + loc + name));
    }
    public ItemModelBuilder itemCustomTexture(Supplier<? extends ItemLike> item, String location, String texture) {
        return withExistingParent(itemName(item.get().asItem()), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + texture));
    }

    public ItemModelBuilder itemBlockFlatFullGlow(Supplier<? extends Block> block, String location, String suffix) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block) + suffix, location)).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemBlockFlatFullGlow(Supplier<? extends Block> block, String location) {
        return itemBlockFlatFullGlow(block, location, "");
    }



    public ItemModelBuilder spear(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        ItemModelBuilder small = nested().parent(getExistingFile(mcLoc("item/generated")))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .override().model(getExistingFile(modLoc("item/spear_throwing")))
                .predicate(Redux.locate("throwing"), 1.0F).end().customLoader((itemModelBuilder,existingFileHelper) ->
                        SeparateTransformsModelBuilder.begin(itemModelBuilder, existingFileHelper)
                                .base(nested().parent(getExistingFile(modLoc("item/spear_in_hand"))))
                                .perspective(ItemDisplayContext.GUI, small)
                                .perspective(ItemDisplayContext.GROUND, small)
                                .perspective(ItemDisplayContext.FIXED, small)
                ).end();
    }



    public void itemBlockWithParent(Block block, Function<Block, ResourceLocation> existingParent) {
        this.withExistingParent(this.blockName(block), existingParent.apply(block));
    }

    public ItemModelBuilder handheldItemGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).texture("layer1", modLoc("item/" + location + id.getPath() + "_glow")).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder handheldItemFullGlow(Supplier<? extends Item> item, String location) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item.get());
        return withExistingParent(id.getPath(), mcLoc("item/handheld"))
                .texture("layer0", modLoc("item/" + location + id.getPath())).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 0)).end();
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block) {
        return withExistingParent(blockName(block), texture(blockName(block)));
    }
    public ItemModelBuilder itemBlockOther(Supplier<? extends Block> block, Supplier<? extends Block> other) {
        return withExistingParent(blockName(block), new ResourceLocation(ForgeRegistries.BLOCKS.getKey(other.get()).getNamespace(), "block/" +blockName(other)));
    }

    public ItemModelBuilder itemBlock(Supplier<? extends Block> block, String suffix) {
        return withExistingParent(blockName(block), texture(blockName(block) + suffix));
    }

    public ItemModelBuilder lookalikeBlock(Supplier<? extends Block> block, ResourceLocation lookalike) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), lookalike);
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location));
    }
    public ItemModelBuilder itemBlockFlatCustomTexture(Supplier<? extends Block> block, String locationPlusName) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(locationPlusName));
    }
    public ItemModelBuilder itemBlockFlatTint(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer1", texture(blockName(block), location));
    }
    public ItemModelBuilder itemBlockFlatWithPottedTexture(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture("potted_" + blockName(block), location));
    }
    public ItemModelBuilder itemBlockFlatGlow(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location))
                .texture("layer1", texture(blockName(block) + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemBlockFlatTintOverlay(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer1", texture(blockName(block), location))
                .texture("layer0", texture(blockName(block) + "_overlay", location));
    }

    public ItemModelBuilder itemBlockFlatGlow(Supplier<? extends Block> block, String location, String suffix) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location) + suffix)
                .texture("layer1", texture(blockName(block) + suffix + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemBlockFlatGlowOtherTexture(Supplier<? extends Block> block, Supplier<? extends Block> other, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(other), location))
                .texture("layer1", texture(blockName(other) + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 1)).end();
    }
    public ItemModelBuilder itemBlockFlatTintGlowOverlay(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer1", texture(blockName(block), location))
                .texture("layer2", texture(blockName(block) + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 2)).end()
                .texture("layer0", texture(blockName(block) + "_overlay", location));
    }
    public ItemModelBuilder itemBlockFlatTintGlow(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer1", texture(blockName(block), location))
                .texture("layer0", texture(blockName(block) + "_glow", location)).customLoader((itemModelBuilder,existingFileHelper) ->
                        ItemLayerModelBuilder.begin(itemModelBuilder, existingFileHelper).emissive(15, 15, 0)).end();
    }
    public ItemModelBuilder itemFence(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/fence_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemWallBlock(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return wallInventory(blockName(block), texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemButton(Supplier<? extends Block> block, Supplier<? extends Block> baseBlock, String location) {
        return withExistingParent(blockName(block), mcLoc("block/button_inventory"))
                .texture("texture", texture(blockName(baseBlock), location));
    }

    public ItemModelBuilder itemBlockFlatItemTexture(Supplier<? extends Block> block, String location) {
        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("item/" + location + id.getPath()));
    }

    public ItemModelBuilder itemBlockFlatOtherBlockTexture(Supplier<? extends Block> block, Supplier<? extends Block> blockForTexture, String location) {
        ResourceLocation id = ForgeRegistries.BLOCKS.getKey(block.get());
        ResourceLocation tex = ForgeRegistries.BLOCKS.getKey(blockForTexture.get());
        return withExistingParent(id.getPath(), mcLoc("item/generated"))
                .texture("layer0", modLoc("block/" + location + tex.getPath()));
    }
    protected ResourceLocation texture(String name, String location, String suffix) {
        return modLoc("block/" + location + name + suffix);
    }
    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location, String suffix) {
        return withExistingParent(blockName(block), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block), location, suffix));
    }

    public ItemModelBuilder eggItem(Supplier<? extends Item> item) {
        return this.withExistingParent(ForgeRegistries.ITEMS.getKey(item.get()).getPath(), this.mcLoc("item/template_spawn_egg"));
    }
    public void itemLogWallBlock(Block block, Block baseBlock, String location, String modid) {
        ResourceLocation baseTexture = new ResourceLocation(modid, "block/" + location + this.blockName(baseBlock));
        this.withExistingParent(this.blockName(block), this.mcLoc("block/block"))
                .transforms()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).translation(0.0F, 0.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemDisplayContext.FIXED).rotation(0.0F, 90.0F, 0.0F).translation(0.0F, 0.0F, 0.0F).scale(0.5F, 0.5F, 0.5F).end()
                .end()
                .texture("top", baseTexture + "_top").texture("side", baseTexture)
                .element().from(4.0F, 0.0F, 4.0F).to(12.0F, 16.0F, 12.0F)
                .face(Direction.DOWN).uvs(4.0F, 4.0F, 12.0F, 12.0F).texture("#top").cullface(Direction.DOWN).end()
                .face(Direction.UP).uvs(4.0F, 4.0F, 12.0F, 12.0F).texture("#top").end()
                .face(Direction.NORTH).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end()
                .face(Direction.SOUTH).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end()
                .face(Direction.WEST).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end()
                .face(Direction.EAST).uvs(4.0F, 0.0F, 12.0F, 16.0F).texture("#side").end().end()
                .element().from(5.0F, 0.0F, 0.0F).to(11.0F, 13.0F, 16.0F)
                .face(Direction.DOWN).uvs(5.0F, 0.0F, 11.0F, 16.0F).texture("#top").cullface(Direction.DOWN).end()
                .face(Direction.UP).uvs(5.0F, 0.0F, 11.0F, 16.0F).texture("#top").end()
                .face(Direction.NORTH).uvs(5.0F, 3.0F, 11.0F, 16.0F).texture("#side").cullface(Direction.NORTH).end()
                .face(Direction.SOUTH).uvs(5.0F, 3.0F, 11.0F, 16.0F).texture("#side").cullface(Direction.SOUTH).end()
                .face(Direction.WEST).uvs(0.0F, 3.0F, 16.0F, 16.0F).texture("#side").end()
                .face(Direction.EAST).uvs(0.0F, 3.0F, 16.0F, 16.0F).texture("#side").end().end();
    }

    public void itemWoodWallBlock(Block block, Block baseBlock, String location, String modid) {
        this.wallInventory(this.blockName(block), new ResourceLocation(modid, "block/" + location + this.blockName(baseBlock)));
    }


    // Backported from 1.20.4


    public void itemOverlayColumn(Block block, Block baseBlock, String overlay, String location) {
        ResourceLocation side = ReduxBlockstateData.extendStatic(this.texture(ReduxBlockstateData.nameStatic(baseBlock), location), "_side");
        ResourceLocation end = ReduxBlockstateData.extendStatic(this.texture(ReduxBlockstateData.nameStatic(baseBlock), location), "_top");
        this.withExistingParent(this.blockName(block), BLOCK_FOLDER + "/cube_column")
                .texture("overlay", new ResourceLocation(Aether.MODID, "block/" + overlay))
                .texture("side", side)
                .texture("end", end)
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F)
                .face(Direction.UP).texture("#end").end()
                .face(Direction.DOWN).texture("#end").end()
                .face(Direction.NORTH).texture("#side").end()
                .face(Direction.SOUTH).texture("#side").end()
                .face(Direction.EAST).texture("#side").end()
                .face(Direction.WEST).texture("#side").end()
                .end()
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.GROUND).rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemDisplayContext.FIXED).scale(0.5F, 0.5F, 0.5F).end()
                .end();
    }

    public void itemOverlayDungeonBlock(Block block, Block baseBlock, String overlay, String folder, String suffix) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube"))
                .texture("overlay", new ResourceLocation(Aether.MODID, "block/dungeon/" + overlay)).texture("face", this.texture(this.blockName(baseBlock) + suffix, folder))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, builder) -> builder.texture("#face").cullface(direction).end()).end()
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.GROUND).rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemDisplayContext.FIXED).scale(0.5F, 0.5F, 0.5F).end()
                .end();
    }

    public void itemOverlayDungeonBlock(Block block, Block baseBlock, String folder, String overlay) {
        this.withExistingParent(this.blockName(block), this.mcLoc("block/cube"))
                .texture("overlay", new ResourceLocation(Aether.MODID, "block/dungeon/" + overlay)).texture("face", this.texture(this.blockName(baseBlock), folder))
                .element().from(0.0F, 0.0F, 0.0F).to(16.0F, 16.0F, 16.0F).allFaces((direction, builder) -> builder.texture("#face").cullface(direction).end()).end()
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.GROUND).rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemDisplayContext.FIXED).scale(0.5F, 0.5F, 0.5F).end()
                .end();
    }
}


