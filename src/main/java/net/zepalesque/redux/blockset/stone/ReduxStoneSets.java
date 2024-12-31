package net.zepalesque.redux.blockset.stone;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.tags.BlockTags;
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
    
    public static final BaseStoneSet BLIGHTMOSS_HOLYSTONE = register(new BaseStoneSet("blightmoss_holystone", MapColor.TERRACOTTA_PURPLE, SoundType.STONE, 0.5F, 0.5F, "natural/"))
            .withLore("The blighted form of Mosssy Holystone. The viscious moss on this rock wilts in the sunlight and flourishes in the moonlight.")
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
            .craftsIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS, CommonMatrices.SQUARE_2X2)
            .stonecutIntoSet(() -> ReduxStoneSets.SENTRITE_BRICKS)
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, ReduxStoneSets.BLIGHTMOSS_HOLYSTONE::wall, true, BlockSet.TabAdditionPhase.BEFORE)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, ReduxStoneSets.BLIGHTMOSS_HOLYSTONE::block, false, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true);

    public static final BaseStoneSet SENTRITE_BRICKS = register(new BrickStoneSet("sentrite_brick", MapColor.DEEPSLATE, SoundType.NETHER_BRICKS, 1.0F, 6.0F, "construction/"))
            .withLore("Bricks made of Sentrite. These can be used as a nice building block!")
            .tabAfter(AetherCreativeTabs.AETHER_BUILDING_BLOCKS, () -> ReduxStoneSets.SENTRITE.wall().get(), true, BlockSet.TabAdditionPhase.BEFORE)
            .withTag(BlockTags.MINEABLE_WITH_PICKAXE, true)
            .withTag(AetherTags.Blocks.DUNGEON_BLOCKS, false)
            .withTag(AetherTags.Blocks.SENTRY_BLOCKS, false)
            .withItemTag(AetherTags.Items.SENTRY_BLOCKS, false);



    public static <T extends AbstractStoneSet> T register(T set) {
        Redux.BLOCK_SETS.add(set);
        return set;
    }
}
