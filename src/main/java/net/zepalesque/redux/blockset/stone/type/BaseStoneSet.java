package net.zepalesque.redux.blockset.stone.type;

import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.type.BaseFlowerSet;
import net.zepalesque.redux.blockset.util.MutableLoreGeneration;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.api.blockset.AbstractFlowerSet;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.DatagenUtil;
import net.zepalesque.zenith.util.TabUtil;
import com.mojang.datafixers.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BaseStoneSet extends AbstractStoneSet implements MutableLoreGeneration<BaseStoneSet> {

    public final String id, textureFolder;
    protected String lore;

    protected final DeferredBlock<AetherDoubleDropBlock> base;
    protected final DeferredBlock<StairBlock> stairs;
    protected final DeferredBlock<SlabBlock> slab;
    protected final DeferredBlock<WallBlock> wall;
    protected NoteBlockInstrument instrument = NoteBlockInstrument.BASEDRUM;
    protected final Map<CraftingMatrix, Supplier<? extends ItemLike>> crafted_blocks = new HashMap<>();
    protected final Map<CraftingMatrix, Supplier<AbstractStoneSet>> crafted_sets = new HashMap<>();
    protected final Map<Supplier<? extends ItemLike>, Integer> stonecut_blocks = new HashMap<>();
    protected final List<Supplier<AbstractStoneSet>> stonecut_sets = new ArrayList<>();
    protected final Map<Supplier<? extends ItemLike>, Float> smelted_blocks = new HashMap<>();
    protected final Map<Supplier<AbstractStoneSet>, Float> smelted_sets = new HashMap<>();
    protected final Table<Supplier<CreativeModeTab>, Supplier<? extends ItemLike>, Pair<Boolean, TabAdditionPhase>> afterOrdering = HashBasedTable.create();
    protected final Table<Supplier<CreativeModeTab>, Supplier<? extends ItemLike>, Pair<Boolean, TabAdditionPhase>> beforeOrdering = HashBasedTable.create();
    protected final Table<Supplier<CreativeModeTab>, TabAdditionPhase, Boolean> appended = HashBasedTable.create();
    protected final Map<TagKey<Block>, Boolean> tags = new HashMap<>();
    protected final Map<TagKey<Item>, Boolean> itemTags = new HashMap<>();

    public BaseStoneSet(String id, MapColor color, SoundType sound, float breakTime, float blastResistance, String textureFolder) {
        this.id = id;
        this.textureFolder = textureFolder;
        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;
        this.base = this.block(blocks, items, id, color, sound, breakTime, blastResistance);
        this.stairs = this.stairs(blocks, items, id, color, sound, breakTime, blastResistance);
        this.slab = this.slab(blocks, items, id, color, sound, breakTime, blastResistance);
        this.wall = this.wall(blocks, items, id, color, sound, breakTime, blastResistance);
    }

    public BaseStoneSet instrument(NoteBlockInstrument instrument) {
        this.instrument = instrument;
        return this;
    }

    @Override
    protected DeferredBlock<AetherDoubleDropBlock> block(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(true), () -> new AetherDoubleDropBlock(
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<AetherDoubleDropBlock> block() {
        return this.base;
    }

    @Override
    protected DeferredBlock<StairBlock> stairs(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_stairs", () -> new StairBlock(this.block().get().defaultBlockState(),
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<StairBlock> stairs() {
        return this.stairs;
    }

    @Override
    protected DeferredBlock<SlabBlock> slab(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_slab", () -> new SlabBlock(
                BlockBehaviour.Properties.of()
                        // Unchanged as vanilla appears to no longer have higher break times for slabs
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }


    @Override
    public DeferredBlock<SlabBlock> slab() {
        return this.slab;
    }

    @Override
    protected DeferredBlock<WallBlock> wall(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, MapColor color, SoundType soundType, float breakTime, float blastResistance) {
        var block = registry.register(this.baseName(false) + "_wall", () -> new WallBlock(
                BlockBehaviour.Properties.of()
                        .strength(breakTime, blastResistance)
                        .mapColor(color)
                        .sound(soundType)
                        .requiresCorrectToolForDrops()
                        .instrument(this.instrument)
        ));
        items.register(block.getId().getPath(), () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    @Override
    public DeferredBlock<WallBlock> wall() {
        return this.wall;
    }

    @Override
    public BaseStoneSet craftsIntoSet(Supplier<AbstractStoneSet> set, CraftingMatrix shape) {
        this.crafted_sets.put(shape, set);
        return this;
    }

    @Override
    public BaseStoneSet craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape) {
        this.crafted_blocks.put(shape, block);
        return this;
    }

    @Override
    public BaseStoneSet stonecutIntoSet(Supplier<AbstractStoneSet> set) {
        this.stonecut_sets.add(set);
        return this;
    }

    @Override
    public BaseStoneSet stonecutInto(Supplier<? extends ItemLike> block, int count) {
        this.stonecut_blocks.put(block, count);
        return this;
    }

    @Override
    public BaseStoneSet smeltsIntoSet(Supplier<AbstractStoneSet> set, float experience) {
        this.smelted_sets.put(set, experience);
        return this;
    }

    @Override
    public BaseStoneSet smeltsInto(Supplier<? extends ItemLike> block, float experience) {
        this.smelted_blocks.put(block, experience);
        return this;
    }

    @Override
    public BaseStoneSet withTag(TagKey<Block> tag, boolean allBlocks) {
        this.tags.put(tag, allBlocks);
        return this;
    }

    @Override
    public BaseStoneSet withItemTag(TagKey<Item> tag, boolean allBlocks) {
        this.itemTags.put(tag, allBlocks);
        return this;
    }

    @Override
    public BaseStoneSet tabAfter(Supplier<CreativeModeTab> tab, Supplier<? extends ItemLike> placeAfter, boolean allBlocks, TabAdditionPhase phase) {
        this.afterOrdering.put(tab, placeAfter, Pair.of(allBlocks, phase));
        return this;
    }

    @Override
    public BaseStoneSet tabBefore(Supplier<CreativeModeTab> tab, Supplier<? extends ItemLike> placeBefore, boolean allBlocks, TabAdditionPhase phase) {
        this.beforeOrdering.put(tab, placeBefore, Pair.of(allBlocks, phase));
        return this;
    }

    @Override
    public BaseStoneSet tabAppend(Supplier<CreativeModeTab> tab, boolean allBlocks, TabAdditionPhase phase) {
        this.appended.put(tab, phase, allBlocks);
        return this;
    }

    @Override
    public String baseName(boolean isBaseBlock) {
        return this.id;
    }

    @Override
    public void blockData(ReduxBlockStateProvider data) {
        data.block(this.block().get(), this.textureFolder);
        data.stairs(this.stairs().get(), this.block().get(), this.textureFolder);
        data.slab(this.slab().get(), this.block().get(), this.textureFolder);
        data.wallBlock(this.wall().get(), this.block().get(), this.textureFolder);
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        data.itemBlock(this.block().get());
        data.itemBlock(this.stairs().get());
        data.itemBlock(this.slab().get());
        data.itemWallBlock(this.wall().get(), this.block().get(), this.textureFolder);
    }

    @Override
    public void langData(ReduxLanguageProvider data) {

        String blockName = DatagenUtil.getNameLocalized(this.block());

        data.addBlock(this.block());
        if (this.lore != null) { data.addLore(this.block(), this.lore); }
        data.addBlock(this.stairs());
        data.addLore(this.stairs(), "Crafted from " + blockName + ". Stairs are useful for adding verticality to builds and are often used for decoration too!");
        data.addBlock(this.slab());
        data.addLore(this.slab(), "Crafted from " + blockName + ". Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        data.addBlock(this.wall());
        data.addLore(this.wall(), "Crafted from " + blockName + ". Walls can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
    }

    @Override
    public void recipeData(ReduxRecipeProvider data, RecipeOutput consumer) {


        data.stairs(this.stairs(), this.block()).save(consumer);
        ReduxRecipeProvider.slab(consumer, RecipeCategory.BUILDING_BLOCKS, this.slab().get(), this.block().get());
        ReduxRecipeProvider.wall(consumer, RecipeCategory.BUILDING_BLOCKS, this.wall().get(), this.block().get());

        this.crafted_sets.forEach((matrix, set) ->
            matrix.apply(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, set.get().block().get(), matrix.count())
                    .define('#', this.block().get()))
                    .unlockedBy(ReduxRecipeProvider.getHasName(set.get().block().get()), ReduxRecipeProvider.has(set.get().block().get())).save(consumer,
                            data.name(ReduxRecipeProvider.getConversionRecipeName(set.get().block().get(), this.block().get()))
                    )
        );

        this.crafted_blocks.forEach((matrix, block) ->
            matrix.apply(ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, block.get(), matrix.count())
                    .define('#', this.block().get()))
                    .unlockedBy(ReduxRecipeProvider.getHasName(this.block().get()), ReduxRecipeProvider.has(this.block().get())).save(consumer,
                            data.name(ReduxRecipeProvider.getConversionRecipeName(block.get(), this.block().get()))
                    )
        );

        this.stonecut_blocks.forEach((block, count) ->
                data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, block.get(), this.block().get(), count)
        );

        this.stonecut_sets.forEach(set -> {
                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().block().get(), this.block().get());
                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().stairs().get(), this.block().get());
                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().slab().get(), this.block().get(), 2);
                    data.stonecuttingRecipe(consumer, RecipeCategory.BUILDING_BLOCKS, set.get().wall().get(), this.block().get());
                }
        );

        this.smelted_blocks.forEach((block, xp) ->
            data.smeltingOreRecipe(block.get(), this.block().get(), xp).save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(block.get(), this.block().get()) + "_smelting"))
        );

        this.smelted_sets.forEach((set, xp) ->
                data.smeltingOreRecipe(set.get().block().get(), this.block().get(), xp).save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(set.get().block().get(), this.block().get()) + "_smelting"))
        );


    }

    @Override
    public void blockTagData(ReduxBlockTagsProvider data) {
        this.tags.forEach((tag, allBlocks) -> {
            if (allBlocks) {
                data.tag(tag).add(this.block().get(), this.stairs().get(), this.slab().get(), this.wall().get());
            } else {
                data.tag(tag).add(this.block().get());
            }
        });
        data.tag(BlockTags.STAIRS).add(this.stairs().get());
        data.tag(BlockTags.SLABS).add(this.slab().get());
        data.tag(BlockTags.WALLS).add(this.wall().get());
    }

    @Override
    public void itemTagData(ReduxItemTagsProvider data) {
        this.itemTags.forEach((tag, allBlocks) -> {
            if (allBlocks) {
                data.tag(tag).add(this.block().get().asItem(), this.stairs().get().asItem(), this.slab().get().asItem(), this.wall().get().asItem());
            } else {
                data.tag(tag).add(this.block().get().asItem());
            }
        });
    }

    @Override
    public void lootData(ReduxBlockLootProvider data) {
        data.dropSelf(this.block().get());
        data.dropSelf(this.stairs().get());
        data.add(this.slab().get(), data::createSlabItemTable);
        data.dropSelf(this.wall().get());
    }

    @Override
    public BaseStoneSet withLore(String lore) {
        this.lore = lore;
        return this;
    }

    @Override
    public void mapData(ReduxDataMapProvider data) { }

    @Override
    public void flammables(FireAccessor accessor) { }

    @Override
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) { }

    // Ignore the prev value, implementation is different here
    @Override
    public Supplier<? extends ItemLike> addToCreativeTab(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev, TabAdditionPhase phase) {
        for (Table.Cell<Supplier<CreativeModeTab>, Supplier<? extends ItemLike>, Pair<Boolean, TabAdditionPhase>> triple : this.afterOrdering.cellSet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getRowKey();
            if (event.getTab() == tabToAddTo.get()) {
                Supplier<? extends ItemLike> addAfter = triple.getColumnKey();
                Pair<Boolean, TabAdditionPhase> pair = triple.getValue();
                if (pair.getSecond() == phase) {
                    TabUtil.putAfter(event, addAfter, this.block());
                    if (pair.getFirst()) TabUtil.putAfter(event, this.block(), this.stairs(), this.slab(), this.wall());
                }
            }
        }
        for (Table.Cell<Supplier<CreativeModeTab>, Supplier<? extends ItemLike>, Pair<Boolean, TabAdditionPhase>> triple : this.beforeOrdering.cellSet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getRowKey();
            if (event.getTab() == tabToAddTo.get()) {
                Supplier<? extends ItemLike> addBefore = triple.getColumnKey();
                Pair<Boolean, TabAdditionPhase> pair = triple.getValue();
                if (pair.getSecond() == phase) {
                    if (pair.getFirst()) TabUtil.putBefore(event, addBefore, this.wall(), this.slab(), this.stairs(), this.block());
                    else TabUtil.putBefore(event, addBefore, this.block());
                }
            }
        }
        for (Table.Cell<Supplier<CreativeModeTab>, TabAdditionPhase, Boolean> triple : this.appended.cellSet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getRowKey();
            if (event.getTab() == tabToAddTo.get()) {
                boolean addAll = triple.getValue();
                TabAdditionPhase current = triple.getColumnKey();
                if (current == phase) {
                    TabUtil.put(event, this.block());
                    if (addAll) TabUtil.put(event, this.stairs(), this.slab(), this.wall());
                }

            }
        }
        return null;
    }
}
