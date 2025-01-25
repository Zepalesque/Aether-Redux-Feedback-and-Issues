package net.zepalesque.redux.blockset.stone;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockset.stone.type.BaseStoneSet;
import net.zepalesque.redux.blockset.stone.type.BrickStoneSet;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.CommonMatrices;
import net.zepalesque.zenith.api.blockset.CraftingMatrix;
import net.zepalesque.zenith.api.blockset.type.AbstractStoneSet;

public class ReduxStoneSets {

    public static final BaseStoneSet GILDED_HOLYSTONE = register(new BaseStoneSet("gilded_holystone", MapColor.SAND, SoundType.STONE, 0.5F, 0.5F, "natural/"))
            .withLore("The enchanted form of Mossy Holystone. This rock covered in golden moss glitters in the sunlight.")
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, () -> AetherBlocks.MOSSY_HOLYSTONE_WALL, true, BlockSet.TabAdditionPhase.BEFORE)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, () -> AetherBlocks.MOSSY_HOLYSTONE, false, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);
    
    public static final BaseStoneSet BLEAKMOSS_HOLYSTONE = register(new BaseStoneSet("bleakmoss_holystone", MapColor.TERRACOTTA_PURPLE, SoundType.STONE, 0.5F, 0.5F, "natural/"))
            .withLore("The blighted form of Mossy Holystone. The viscious moss on this rock wilts in the sunlight and flourishes in the moonlight.")
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, ReduxStoneSets.GILDED_HOLYSTONE::wall, true, BlockSet.TabAdditionPhase.BEFORE)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, ReduxStoneSets.GILDED_HOLYSTONE::block, false, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);

    public static final BaseStoneSet SENTRITE = register(new BaseStoneSet("sentrite", MapColor.DEEPSLATE, SoundType.NETHER_ORE, 1.0F, 6.0F, "natural/"))
            .withLore("A dark, metallic rock found throughout the Aether. This crude metal is used in a number of parts of Sentry technology, even their walls of Carved Stone are made of a mixture of this and Holystone.")
            .craftsInto(AetherBlocks.CARVED_STONE,
                    new CraftingMatrix(4, builder ->
                            builder
                                    .define('H', AetherBlocks.HOLYSTONE.get())
                                    .pattern("#H")
                                    .pattern("H#")))
            .craftsIntoSet(() -> ReduxStoneSets.POLISHED_SENTRITE, CommonMatrices.SQUARE_2X2)
            .stonecutIntoSet(() -> ReduxStoneSets.POLISHED_SENTRITE)
            .stonecutIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS)
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, ReduxStoneSets.BLEAKMOSS_HOLYSTONE::wall, true, BlockSet.TabAdditionPhase.BEFORE)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, ReduxStoneSets.BLEAKMOSS_HOLYSTONE::block, false, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);

    public static final BaseStoneSet POLISHED_SENTRITE = register(new BaseStoneSet("polished_sentrite", MapColor.DEEPSLATE, SoundType.NETHER_BRICKS, 1.0F, 6.0F, "construction/"))
            .withLore("The polished form of Sentrite. These can be used as a nice building block!")
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, () -> ReduxStoneSets.SENTRITE.wall().get(), true, BlockSet.TabAdditionPhase.BEFORE)
            .craftsIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS, CommonMatrices.SQUARE_2X2)
            .stonecutIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true)
            .withTag(AetherTags.Blocks.DUNGEON_BLOCKS, false)
            .withTag(AetherTags.Blocks.SENTRY_BLOCKS, false)
            .withItemTag(AetherTags.Items.SENTRY_BLOCKS, false);

    public static final BaseStoneSet SENTRITE_BRICKS = register(new BrickStoneSet("sentrite_brick", MapColor.DEEPSLATE, SoundType.NETHER_BRICKS, 1.0F, 6.0F, "construction/"))
            .withLore("Bricks made out of Sentrite. These can be used as a nice building block!")
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, () -> ReduxStoneSets.POLISHED_SENTRITE.wall().get(), true, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);

    public static final BaseStoneSet ANGILITE = register(new BaseStoneSet("angilite", MapColor.TERRACOTTA_YELLOW, SoundType.TUFF, 0.5F, 6.0F, "natural/"))
            // TODO: Idea: glowstone tiles via enchanting angelic stone? Maybe? Could be nice for ceiling lights in the silver dungeons
            .withLore("A stone type found in the Aether. Angilite is commonly used in Valkyrian structures in its tile/brick form, Angelic Stone. Additionally, when enchanted, it buds into a dazzlingly bright glowing stone!")
            .craftsInto(AetherBlocks.ANGELIC_STONE, CommonMatrices.SQUARE_2X2)
            .enchantsInto(() -> Blocks.GLOWSTONE, 0.0F, 200)
            .stonecutInto(AetherBlocks.ANGELIC_STONE, 1)
            .stonecutInto(AetherBlocks.ANGELIC_WALL, 1)
            .stonecutInto(AetherBlocks.ANGELIC_STAIRS, 1)
            .stonecutInto(AetherBlocks.ANGELIC_SLAB, 1)
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, ReduxStoneSets.SENTRITE::wall, true, BlockSet.TabAdditionPhase.BEFORE)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, ReduxStoneSets.SENTRITE::block, false, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);


    public static <T extends AbstractStoneSet> T register(T set) {
        Redux.BLOCK_SETS.add(set);
        return set;
    }
}
