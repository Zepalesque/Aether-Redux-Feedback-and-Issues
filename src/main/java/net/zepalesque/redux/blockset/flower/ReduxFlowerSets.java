package net.zepalesque.redux.blockset.flower;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.bush.CustomBoundsBushBlock;
import net.zepalesque.redux.block.natural.bush.CustomBoundsFlowerBlock;
import net.zepalesque.redux.blockset.flower.type.AetherFlowerSet;
import net.zepalesque.redux.blockset.flower.type.BaseFlowerSet;
import net.zepalesque.redux.blockset.flower.type.CloverSet;
import net.zepalesque.redux.blockset.flower.type.EnchantedFlowerSet;
import net.zepalesque.redux.blockset.flower.type.TintedFlowerSet;
import net.zepalesque.redux.blockset.flower.type.UntintedFlowerSet;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.world.tree.ReduxTreeGrowers;
import net.zepalesque.zenith.api.block.CommonPlantBounds;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.type.AbstractFlowerSet;

public class ReduxFlowerSets {

    public static final BaseFlowerSet<CustomBoundsFlowerBlock.Enchanted> AURUM = register(new EnchantedFlowerSet<>("aurum", "natural/",
            () -> new CustomBoundsFlowerBlock.Enchanted(CommonPlantBounds.FLOWER,
                    MobEffects.LUCK, 60, Properties.ofFullCopy(Blocks.DANDELION).hasPostProcess((s, l, p) -> true)), 1, 0xFFFFFF)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.WHITE_FLOWER, BlockSet.TabAdditionPhase.BEFORE)
            .craftsIntoShapeless(1, () -> Items.YELLOW_DYE, 1, RecipeCategory.MISC)
            .withFlowerTag(BlockTags.FLOWERS)
            .withLore("A golden flower found in the Gilded Groves. Some say it brings good luck!"));

    public static final BaseFlowerSet<SaplingBlock> GILDENROOT_SAPLING = register(new UntintedFlowerSet<>("gildenroot_sapling", "natural/",
            () -> new SaplingBlock(ReduxTreeGrowers.GILDENROOT, Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.QUARTZ)))
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.SKYROOT_SAPLING, BlockSet.TabAdditionPhase.BEFORE)
            .withFlowerTag(BlockTags.SAPLINGS)
            .compost(0.3F)
            .withLore("The sapling of the Gildenroot tree. It can be grown by waiting or using Bone Meal."));

    public static final BaseFlowerSet<SaplingBlock> SHADEROOT_SAPLING = register(new UntintedFlowerSet<>("shaderoot_sapling", "natural/",
            () -> new SaplingBlock(ReduxTreeGrowers.SHADEROOT, Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_PURPLE)))
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, () -> ReduxFlowerSets.GILDENROOT_SAPLING.flower().get(), BlockSet.TabAdditionPhase.BEFORE)
            .withFlowerTag(BlockTags.SAPLINGS)
            .compost(0.3F)
            .withLore("The sapling of the Gildenroot tree. It can be grown by waiting or using Bone Meal."));

    public static final BaseFlowerSet<CustomBoundsBushBlock> LUCKY_CLOVER = register(new CloverSet<>("lucky_clover", "natural/",
            () -> new CustomBoundsBushBlock(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D), Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.GOLD)))
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, ReduxBlocks.GOLDEN_CLOVERS, BlockSet.TabAdditionPhase.AFTER)
            .withLore("A large four-leaved clover found in the Gilded Groves. Makes a nice decoration, and can be placed in a flower pot!"));

    public static final BaseFlowerSet<CustomBoundsFlowerBlock> SPIROLYCTIL = register(new AetherFlowerSet<>("spirolyctil", "natural/",
            () -> new CustomBoundsFlowerBlock(CommonPlantBounds.FLOWER,
                    MobEffects.LEVITATION, 4, Properties.ofFullCopy(Blocks.DANDELION).mapColor(MapColor.TERRACOTTA_LIGHT_BLUE)), 1, ReduxColors.Tints.BLIGHT_GRASS_COLOR)
            .tabAfter(AetherCreativeTabs.AETHER_NATURAL_BLOCKS, AetherBlocks.WHITE_FLOWER, BlockSet.TabAdditionPhase.BEFORE)
            .craftsIntoShapeless(1, () -> Items.LIGHT_BLUE_DYE, 1, RecipeCategory.MISC)
            .withFlowerTag(BlockTags.FLOWERS)
            .withLore("An indigo flower found in the Blight. This plant almost feels like an outlier, as it gives off a much more peaceful vibe than other surrounding Blight foliage."));


    public static <T extends AbstractFlowerSet> T register(T set) {
        Redux.BLOCK_SETS.add(set);
        return set;
    }

}
