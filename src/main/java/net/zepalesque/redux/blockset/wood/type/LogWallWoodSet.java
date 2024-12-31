package net.zepalesque.redux.blockset.wood.type;

import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.model.generators.ModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.ReduxNaturalWall;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.api.data.DatagenUtil;
import net.zepalesque.zenith.api.item.TabUtil;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;

import java.util.Map;
import java.util.function.Supplier;

public class LogWallWoodSet extends BaseWoodSet {

    protected final DeferredBlock<ReduxNaturalWall> log_wall;
    protected final DeferredBlock<ReduxNaturalWall> wood_wall;
    protected final DeferredBlock<ReduxNaturalWall> stripped_log_wall;
    protected final DeferredBlock<ReduxNaturalWall> stripped_wood_wall;


    public LogWallWoodSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {
        super(id, woodColor, barkColor, sound);
        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;

        this.log_wall = this.logWall(blocks, items, id, woodColor, sound);
        this.wood_wall = this.woodWall(blocks, items, id, woodColor, sound);
        this.stripped_log_wall = this.strippedLogWall(blocks, items, id, woodColor, sound);
        this.stripped_wood_wall = this.strippedWoodWall(blocks, items, id, woodColor, sound);
    }

    protected DeferredBlock<ReduxNaturalWall> logWall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + this.logSuffix(false, false) + "_wall", () -> new ReduxNaturalWall(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .ignitedByLava()
                .strength(2.0F)
                .sound(soundType)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public DeferredBlock<ReduxNaturalWall> logWall() {
        return this.log_wall;
    }

    protected DeferredBlock<ReduxNaturalWall> woodWall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register(id + this.woodSuffix(false, false) + "_wall", () -> new ReduxNaturalWall(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .ignitedByLava()
                .strength(2.0F)
                .sound(soundType)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public DeferredBlock<ReduxNaturalWall> woodWall() {
        return this.wood_wall;
    }

    protected DeferredBlock<ReduxNaturalWall> strippedLogWall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register("stripped_" + id + this.logSuffix(false, false) + "_wall", () -> new ReduxNaturalWall(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .ignitedByLava()
                .strength(2.0F)
                .sound(soundType)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public DeferredBlock<ReduxNaturalWall> strippedLogWall() {
        return this.stripped_log_wall;
    }

    protected DeferredBlock<ReduxNaturalWall> strippedWoodWall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType) {
        var block = registry.register("stripped_" + id + this.woodSuffix(false, false) + "_wall", () -> new ReduxNaturalWall(Properties.of()
                .mapColor(color)
                .instrument(NoteBlockInstrument.BASS)
                .ignitedByLava()
                .strength(2.0F)
                .sound(soundType)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public DeferredBlock<ReduxNaturalWall> strippedWoodWall() {
        return this.stripped_wood_wall;
    }


    @Override
    public void blockData(ReduxBlockStateProvider data) {
        super.blockData(data);
        ModelFile postBig = data.makeWallPostModel(4, 16, "wooden_post_big");
        ModelFile postShort = data.makeWallPostModel(3, 14, "wooden_post_short");
        ModelFile postTall = data.makeWallPostModel(3, 16, "wooden_post_tall");
        ModelFile side = data.makeWallSideModel(5, 14, "wooden_side", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 5);
        ModelFile sideAlt = data.makeWallSideModel(5, 14, "wooden_side_alt", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 11, 16);
        ModelFile sideTall = data.makeWallSideModel(5, 16, "wooden_side_tall", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 5);
        ModelFile sideTallAlt = data.makeWallSideModel(5, 16, "wooden_side_tall_alt", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 11, 16);
        ModelFile sideShort = data.makeWallSideModel(4, 14, "wooden_side_short", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 4);
        ModelFile sideAltShort = data.makeWallSideModel(4, 14, "wooden_side_alt_short", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 12, 16);
        ModelFile sideTallShort = data.makeWallSideModel(4, 16, "wooden_side_tall_short", ModelBuilder.FaceRotation.CLOCKWISE_90, 0, 4);
        ModelFile sideTallAltShort = data.makeWallSideModel(4, 16, "wooden_side_tall_alt_short", ModelBuilder.FaceRotation.COUNTERCLOCKWISE_90, 12, 16);

        data.logWallBlock(this.logWall().get(), this.log().get(), "natural/",
                Redux.MODID, true, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);

        data.logWallBlock(this.strippedLogWall().get(), this.strippedLog().get(), "natural/",
                Redux.MODID, true, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);

        data.logWallBlock(this.woodWall().get(), this.log().get(), "natural/",
                Redux.MODID, false, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);

        data.logWallBlock(this.strippedWoodWall().get(), this.strippedLog().get(), "natural/",
                Redux.MODID, false, postBig, postShort, postTall, side, sideAlt, sideTall, sideTallAlt, sideShort, sideAltShort, sideTallShort, sideTallAltShort);

    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        super.itemData(data);
        data.itemLogWallBlock(this.logWall().get(), this.log().get(), "natural/", Redux.MODID);
        data.itemLogWallBlock(this.strippedLogWall().get(), this.strippedLog().get(), "natural/", Redux.MODID);
        data.itemWoodWallBlock(this.woodWall().get(), this.log().get(), "natural/", Redux.MODID);
        data.itemWoodWallBlock(this.strippedWoodWall().get(), this.strippedLog().get(), "natural/", Redux.MODID);
    }

    @Override
    public void langData(ReduxLanguageProvider data) {
        super.langData(data);
        String name = DatagenUtil.localize(this.id);

        data.addBlock(this.logWall());
        data.addLore(this.logWall(), "Crafted from " + name + " " + this.logSuffix(true, false) + ". Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        data.addBlock(this.strippedLogWall());
        data.addLore(this.strippedLogWall(), "Crafted from Stripped " + name + " " + this.logSuffix(true, false) + ". Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        data.addBlock(this.woodWall());
        data.addLore(this.woodWall(), "Crafted from " + name + " " + this.woodSuffix(true, false) + ". Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        data.addBlock(this.strippedWoodWall());
        data.addLore(this.strippedWoodWall(), "Crafted from Stripped " + name + " " + this.woodSuffix(true, false) + ". Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
    }

    @Override
    public void recipeData(ReduxRecipeProvider data, RecipeOutput consumer) {
        super.recipeData(data, consumer);
        ReduxRecipeProvider.wall(consumer, RecipeCategory.BUILDING_BLOCKS, this.logWall().get(), this.log().get());
        ReduxRecipeProvider.wall(consumer, RecipeCategory.BUILDING_BLOCKS, this.strippedLogWall().get(), this.strippedLog().get());
        ReduxRecipeProvider.wall(consumer, RecipeCategory.BUILDING_BLOCKS, this.woodWall().get(), this.wood().get());
        ReduxRecipeProvider.wall(consumer, RecipeCategory.BUILDING_BLOCKS, this.strippedWoodWall().get(), this.strippedWood().get());
    }

    @Override
    public void blockTagData(ReduxBlockTagsProvider data) {
        super.blockTagData(data);
        data.tag(BlockTags.MINEABLE_WITH_AXE).add(
                this.logWall().get(),
                this.strippedLogWall().get(),
                this.woodWall().get(),
                this.strippedWoodWall().get()
        );
        data.tag(BlockTags.LOGS).add(
                this.logWall().get(),
                this.strippedLogWall().get(),
                this.woodWall().get(),
                this.strippedWoodWall().get()
        );
        data.tag(BlockTags.WALLS).add(
                this.logWall().get(),
                this.strippedLogWall().get(),
                this.woodWall().get(),
                this.strippedWoodWall().get()
        );
    }

    @Override
    public void lootData(ReduxBlockLootProvider data) {
        super.lootData(data);
        data.naturalDrop(this.logWall().get(), this.log().get());
        data.naturalDrop(this.woodWall().get(), this.log().get());
        data.naturalDrop(this.strippedLogWall().get(), this.strippedLog().get());
        data.naturalDrop(this.strippedWoodWall().get(), this.strippedLog().get());
    }

    @Override
    public void flammables(FireAccessor accessor) {
        super.flammables(accessor);
        accessor.callSetFlammable(this.logWall().get(), 5, 5);
        accessor.callSetFlammable(this.woodWall().get(), 5, 5);
        accessor.callSetFlammable(this.strippedLogWall().get(), 5, 5);
        accessor.callSetFlammable(this.strippedWoodWall().get(), 5, 5);
    }

    @Override
    protected Supplier<? extends ItemLike> buildingBlocks(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev) {
        Supplier<? extends ItemLike> superPrev = super.buildingBlocks(event, prev);
        TabUtil.putAfter(event, superPrev, this.logWall(), this.woodWall(), this.strippedLogWall(), this.strippedWoodWall());
        return this.strippedWoodWall();
    }

    @Override
    protected Supplier<? extends ItemLike> naturalBlocks(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev) {
        Supplier<? extends ItemLike> superPrev = super.naturalBlocks(event, prev);
        TabUtil.putAfter(event, superPrev, this.logWall());
        return this.logWall();
    }

    @Override
    public void setupStrippables(Map<Block, Block> strippingMap) {
        super.setupStrippables(strippingMap);
        strippingMap.put(this.logWall().get(), this.strippedLogWall().get());
        strippingMap.put(this.woodWall().get(), this.strippedWoodWall().get());
    }
}
