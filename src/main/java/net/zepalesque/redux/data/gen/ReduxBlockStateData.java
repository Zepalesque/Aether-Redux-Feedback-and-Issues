package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.crop.WyndoatsBlock;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;

public class ReduxBlockStateData extends ReduxBlockStateProvider {

    public ReduxBlockStateData(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        Redux.BLOCK_SETS.forEach(set -> set.blockData(this));

        this.tintableShortGrass(ReduxBlocks.SHORT_AETHER_GRASS.get(), "natural/");
        this.block(ReduxBlocks.CLOUDROOT_LEAVES.get(), "natural/");
        this.crossBlock(ReduxBlocks.CLOUDROOT_SAPLING.get(), "natural/");

        this.pillar(ReduxBlocks.CARVED_PILLAR.get(), "dungeon/");
        this.pillar(ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.CARVED_BASE.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.SENTRY_BASE.get(), "dungeon/");

        this.pillar(ReduxBlocks.LOCKED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/");
        this.pillar(ReduxBlocks.LOCKED_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.LOCKED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.LOCKED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/");

        this.pillar(ReduxBlocks.TRAPPED_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/");
        this.pillar(ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.TRAPPED_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/");
        this.baseBrick(ReduxBlocks.TRAPPED_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/");

        this.invisiblePillar(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(), ReduxBlocks.CARVED_PILLAR.get(), "dungeon/");
        this.invisiblePillar(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(), ReduxBlocks.SENTRY_PILLAR.get(), "dungeon/");
        this.invisibleBaseBrick(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(), ReduxBlocks.CARVED_BASE.get(), "dungeon/");
        this.invisibleBaseBrick(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get(), ReduxBlocks.SENTRY_BASE.get(), "dungeon/");

        this.cubeActivatable(ReduxBlocks.RUNELIGHT.get(), "dungeon/");
        this.cubeActivatable(ReduxBlocks.LOCKED_RUNELIGHT.get(), ReduxBlocks.RUNELIGHT.get(), "dungeon/");

        this.dungeonBlock(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get(), ReduxStoneSets.SENTRITE_BRICKS.block().get(), "construction/");

        this.crossTintedOverlay(ReduxBlocks.WYNDSPROUTS.get(), "natural/");
        this.cropGrowable(ReduxBlocks.WYNDOATS.get(), "natural/", WyndoatsBlock.AGE);

        this.chain(ReduxBlocks.SENTRITE_CHAIN.get(), "construction/");
        this.lantern(ReduxBlocks.SENTRITE_LANTERN.get(), "construction/");

        this.blockDoubleDrops(ReduxBlocks.VERIDIUM_ORE.get(), "natural/");
        this.block(ReduxBlocks.RAW_VERIDIUM_BLOCK.get(), "construction/");
        this.block(ReduxBlocks.VERIDIUM_BLOCK.get(), "construction/");
        this.block(ReduxBlocks.REFINED_SENTRITE_BLOCK.get(), "construction/");

    }
}
