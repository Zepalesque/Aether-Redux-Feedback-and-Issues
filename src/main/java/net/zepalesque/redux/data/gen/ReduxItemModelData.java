package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.item.ReduxItems;

public class ReduxItemModelData extends ReduxItemModelProvider {

    public ReduxItemModelData(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    protected void registerModels() {
        Redux.BLOCK_SETS.forEach(set -> set.itemData(this));

        itemBlockFlatCustomTexture(ReduxBlocks.SHORT_AETHER_GRASS.get(), "natural/aether_medium_grass");
        this.itemBlock(ReduxBlocks.GILDENROOT_LEAVES.get());
        this.leafPile(ReduxBlocks.GILDENROOT_LEAF_PILE.get());
        this.leafPile(ReduxBlocks.GOLDEN_OAK_LEAF_PILE.get());
        this.leafPile(ReduxBlocks.SKYROOT_LEAF_PILE.get());

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
        this.itemOverlayDungeonBlock(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get(), ReduxStoneSets.SENTRITE_BRICKS.block().get(), "construction/", "lock");

        this.item(ReduxItems.WYND_OAT_PANICLE.get(), "materials/");
        this.item(ReduxItems.WYND_OATS.get(), "food/");

        itemBlockFlatTintOverlay(ReduxBlocks.WYNDSPROUTS.get(), "natural/");
        itemBlockFlatTintOverlay(ReduxBlocks.SKYSPROUTS.get(), "natural/");

        this.item(ReduxItems.RAW_VERIDIUM.get(), "materials/");
        this.item(ReduxItems.VERIDIUM_INGOT.get(), "materials/");
        this.item(ReduxItems.VERIDIUM_NUGGET.get(), "materials/");

        this.item(ReduxItems.REFINED_SENTRITE.get(), "materials/");
        this.item(ReduxItems.SENTRITE_CHUNK.get(), "materials/");

        this.item(ReduxBlocks.SENTRITE_LANTERN.get().asItem(), "misc/");
        this.item(ReduxBlocks.SENTRITE_CHAIN.get().asItem(), "misc/");
        this.itemBlockFlat(ReduxBlocks.SENTRITE_BARS.get(), "construction/");

        this.handheldItem(ReduxItems.VERIDIUM_PICKAXE.get(), "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_AXE.get(), "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_AXE.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_SHOVEL.get(), "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(), "tools/");
        this.handheldItem(ReduxItems.VERIDIUM_HOE.get(), "tools/");
        this.handheldGlow(ReduxItems.INFUSED_VERIDIUM_HOE.get(), "tools/");

        this.itemBlock(ReduxBlocks.VERIDIUM_ORE.get());
        this.itemBlock(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());
        this.itemBlock(ReduxBlocks.VERIDIUM_BLOCK.get());
        this.itemBlock(ReduxBlocks.REFINED_SENTRITE_BLOCK.get());

        this.item(ReduxBlocks.GOLDEN_CLOVERS.get().asItem(), "misc/");
        this.itemBlockFlatOther(ReduxBlocks.GOLDEN_VINES.get(), ReduxBlocks.GOLDEN_VINES_PLANT.get(), "natural/");

        this.item(ReduxBlocks.LOGICATOR.get().asItem(), "misc/");

        this.item(ReduxItems.AERBOUND_CAPE.get(), "accessories/");
    }


    public static class Grass extends ReduxItemModelProvider {
        public Grass(PackOutput output, ExistingFileHelper helper) {
            super(output, Redux.MODID, helper);
        }

        @Override
        protected void registerModels() {


        }
    }

}
