package net.zepalesque.redux.blockset.wood.type;

import com.aetherteam.aether.block.construction.BookshelfBlock;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.DatagenUtil;
import net.zepalesque.zenith.util.TabUtil;

import java.util.function.Supplier;

public abstract class AbstractBookshelfSet<B extends BookshelfBlock> extends LogWallWoodSet {

    protected final DeferredBlock<B> bookshelf;

    public AbstractBookshelfSet(String id, MapColor woodColor, MapColor barkColor, SoundType sound) {
        super(id, woodColor, barkColor, sound);
        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;

        this.bookshelf = bookshelf(blocks, items, id, woodColor, sound);
    }


    protected abstract DeferredBlock<B> bookshelf(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType);

    public DeferredBlock<B> bookshelf() {
        return this.bookshelf;
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        super.itemData(data);
        data.itemBlock(this.bookshelf().get());
    }

    @Override
    public void langData(ReduxLanguageProvider data) {
        super.langData(data);
        String name = DatagenUtil.getNameLocalized(this.id);

        data.addBlock(this.bookshelf());
        data.addLore(this.bookshelf(), "A nice bookshelf made of " + name + " wood. These are nice for decoration, and also will enhance the abilities of Enchanting Tables!");
    }

    @Override
    public void recipeData(ReduxRecipeProvider data, RecipeOutput consumer) {
        super.recipeData(data, consumer);
        ReduxRecipeProvider.bookshelf(consumer, this.planks(), this.bookshelf());
    }

    @Override
    public void lootData(ReduxBlockLootProvider data) {
        super.lootData(data);
        data.add(this.bookshelf().get(), (bookshelf) -> data.createSingleItemTableWithSilkTouch(bookshelf, Items.BOOK, ConstantValue.exactly(3.0F)));
    }

    @Override
    public void blockTagData(ReduxBlockTagsProvider data) {
        super.blockTagData(data);
        data.tag(BlockTags.MINEABLE_WITH_AXE).add(this.bookshelf().get());
        data.tag(Tags.Blocks.BOOKSHELVES).add(this.bookshelf().get());
    }

    @Override
    public void flammables(FireAccessor accessor) {
        super.flammables(accessor);
        accessor.callSetFlammable(this.bookshelf().get(), 30, 20);
    }

    @Override
    protected Supplier<? extends ItemLike> functionalBlocks(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev) {
        TabUtil.putAfter(event, prev, this.bookshelf());
        return super.functionalBlocks(event, this.bookshelf());
    }

    @Override
    public void mapData(ReduxDataMapProvider data) {
        super.mapData(data);
        var fuels = data.builder(NeoForgeDataMaps.FURNACE_FUELS);
        fuels.add(this.bookshelf().get().asItem().builtInRegistryHolder(), new FurnaceFuel(300), false);
    }
}
