package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ReduxBlockTagsData extends ReduxBlockTagsProvider {

    public ReduxBlockTagsData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Redux.MODID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider provider) {
        Redux.BLOCK_SETS.forEach(set -> set.blockTagData(this));

        // Adds every single Redux block as a block that should be treaded as an Aether Block and get the tool debuff
        IntrinsicTagAppender<Block> tag = this.tag(AetherTags.Blocks.TREATED_AS_AETHER_BLOCK);
        for (DeferredHolder<Block, ? extends Block> block : ReduxBlocks.BLOCKS.getEntries()) {
            tag.add(block.get());
        }

        this.tag(AetherTags.Blocks.AETHER_ANIMALS_SPAWNABLE_ON).add(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
        this.tag(AetherTags.Blocks.SWET_SPAWNABLE_ON).add(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());

        this.tag(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR).add(AetherBlocks.QUICKSOIL.get());

        this.tag(ReduxTags.Blocks.ROCK_REPLACEABLE).addTag(BlockTags.DIRT);

        this.tag(BlockTags.REPLACEABLE).add(
                ReduxBlocks.GOLDEN_CLOVERS.get()
        );

        this.tag(BlockTags.LEAVES).add(
                ReduxBlocks.GILDENROOT_LEAVES.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                ReduxBlocks.GILDENROOT_LEAVES.get(),
                ReduxBlocks.GOLDEN_CLOVERS.get(),
                ReduxBlocks.GILDENROOT_LEAF_PILE.get(),
                ReduxBlocks.BLEAKMOSS_BLOCK.get(),
                ReduxBlocks.BLEAKMOSS_CARPET.get()
        );

        this.tag(ReduxTags.Blocks.SHORT_AETHER_GRASS_BLIGHT_COLORING).add(
                ReduxBlocks.BLEAKMOSS_BLOCK.get()
        );

        this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(
                ReduxBlocks.GOLDEN_CLOVERS.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                ReduxBlocks.GOLDEN_CLOVERS.get(),
                ReduxBlocks.GOLDEN_VINES.get(),
                ReduxBlocks.GOLDEN_VINES_PLANT.get()
        );
        
        this.tag(BlockTags.CLIMBABLE).add(
                ReduxBlocks.GOLDEN_VINES_PLANT.get()
        );

        this.tag(BlockTags.FALL_DAMAGE_RESETTING).add(
                ReduxBlocks.GOLDEN_VINES_PLANT.get()
        );

        this.tag(AetherTags.Blocks.SENTRY_BLOCKS).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.LOCKED_CARVED_PILLAR.get(),
                ReduxBlocks.LOCKED_SENTRY_PILLAR.get(),
                ReduxBlocks.LOCKED_CARVED_BASE.get(),
                ReduxBlocks.LOCKED_SENTRY_BASE.get(),
                ReduxBlocks.TRAPPED_CARVED_PILLAR.get(),
                ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(),
                ReduxBlocks.TRAPPED_CARVED_BASE.get(),
                ReduxBlocks.TRAPPED_SENTRY_BASE.get(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get(),
                ReduxBlocks.LOCKED_RUNELIGHT.get(),
                ReduxBlocks.LOCKED_SENTRITE_BRICKS.get()
        );
        this.tag(AetherTags.Blocks.LOCKED_DUNGEON_BLOCKS).add(
                ReduxBlocks.LOCKED_CARVED_PILLAR.get(),
                ReduxBlocks.LOCKED_SENTRY_PILLAR.get(),
                ReduxBlocks.LOCKED_CARVED_BASE.get(),
                ReduxBlocks.LOCKED_SENTRY_BASE.get(),
                ReduxBlocks.LOCKED_RUNELIGHT.get(),
                ReduxBlocks.LOCKED_SENTRITE_BRICKS.get()
        );
        this.tag(AetherTags.Blocks.DUNGEON_BLOCKS).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get()
        );
        this.tag(AetherTags.Blocks.TRAPPED_DUNGEON_BLOCKS).add(
                ReduxBlocks.TRAPPED_CARVED_PILLAR.get(),
                ReduxBlocks.TRAPPED_SENTRY_PILLAR.get(),
                ReduxBlocks.TRAPPED_CARVED_BASE.get(),
                ReduxBlocks.TRAPPED_SENTRY_BASE.get()
        );
        this.tag(AetherTags.Blocks.BOSS_DOORWAY_DUNGEON_BLOCKS).add(
                ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get(),
                ReduxBlocks.SENTRITE_CHAIN.get(),
                ReduxBlocks.SENTRITE_LANTERN.get(),
                ReduxBlocks.RUNIC_LANTERN.get(),
                ReduxBlocks.SENTRITE_BARS.get(),
                ReduxBlocks.VERIDIUM_ORE.get(),
                ReduxBlocks.RAW_VERIDIUM_BLOCK.get(),
                ReduxBlocks.VERIDIUM_BLOCK.get(),
                ReduxBlocks.REFINED_SENTRITE_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get(),
                ReduxBlocks.RUNELIGHT.get(),
                ReduxBlocks.VERIDIUM_ORE.get(),
                ReduxBlocks.RAW_VERIDIUM_BLOCK.get(),
                ReduxBlocks.VERIDIUM_BLOCK.get()
        );

        this.tag(BlockTags.FLOWER_POTS).add(
                ReduxBlocks.POTTED_WYNDSPROUTS.get(),
                ReduxBlocks.POTTED_SKYSPROUTS.get()
        );

        this.tag(BlockTags.CROPS).add(
                ReduxBlocks.WYNDOATS.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                ReduxBlocks.WYNDSPROUTS.get(),
                ReduxBlocks.SKYSPROUTS.get(),
                ReduxBlocks.WYNDOATS.get()
        );



        this.tag(Tags.Blocks.ORES).addTag(ReduxTags.Blocks.VERIDIUM_ORES);

        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
                ReduxTags.Blocks.STORAGE_BLOCKS_VERIDIUM, ReduxTags.Blocks.STORAGE_BLOCKS_RAW_VERIDIUM, ReduxTags.Blocks.STORAGE_BLOCKS_SENTRITE
        );

        this.tag(ReduxTags.Blocks.VERIDIUM_ORES).add(
                ReduxBlocks.VERIDIUM_ORE.get()
        );

        this.tag(ReduxTags.Blocks.STORAGE_BLOCKS_RAW_VERIDIUM).add(
                ReduxBlocks.RAW_VERIDIUM_BLOCK.get()
        );

        this.tag(ReduxTags.Blocks.STORAGE_BLOCKS_VERIDIUM).add(
                ReduxBlocks.VERIDIUM_BLOCK.get()
        );

        this.tag(ReduxTags.Blocks.STORAGE_BLOCKS_SENTRITE).add(
                ReduxBlocks.REFINED_SENTRITE_BLOCK.get()
        );
    }
}
