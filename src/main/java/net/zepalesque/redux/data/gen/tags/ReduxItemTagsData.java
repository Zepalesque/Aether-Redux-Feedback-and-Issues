package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.AetherTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ReduxItemTagsData extends ReduxItemTagsProvider {

    public ReduxItemTagsData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, Redux.MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        Redux.BLOCK_SETS.forEach(set -> set.itemTagData(this));

        this.tag(ItemTags.PICKAXES).add(
                ReduxItems.VERIDIUM_PICKAXE.get(),
                ReduxItems.INFUSED_VERIDIUM_PICKAXE.get()
        );

        this.tag(ItemTags.SHOVELS).add(
                ReduxItems.VERIDIUM_SHOVEL.get(),
                ReduxItems.INFUSED_VERIDIUM_SHOVEL.get()
        );

        this.tag(ItemTags.HOES).add(
                ReduxItems.VERIDIUM_HOE.get(),
                ReduxItems.INFUSED_VERIDIUM_HOE.get()
        );

        this.tag(ItemTags.AXES).add(
                ReduxItems.VERIDIUM_AXE.get(),
                ReduxItems.INFUSED_VERIDIUM_AXE.get()
        );

        this.tag(ItemTags.SWORDS).add(
                ReduxItems.VERIDIUM_SWORD.get(),
                ReduxItems.INFUSED_VERIDIUM_SWORD.get()
        );

        this.tag(AetherTags.Items.DART_SHOOTERS).add(
                ReduxItems.VERIDIUM_DART_SHOOTER.get(),
                ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER.get()
        );

        this.tag(AetherTags.Items.DARTS).add(
                ReduxItems.VERIDIUM_DART.get()
        );

        this.tag(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS).add(
                ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(),
                ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(),
                ReduxItems.INFUSED_VERIDIUM_HOE.get(),
                ReduxItems.INFUSED_VERIDIUM_AXE.get(),
                ReduxItems.INFUSED_VERIDIUM_SWORD.get(),
                ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER.get()
        );

        this.tag(AetherTags.Items.SLIDER_DAMAGING_ITEMS).add(
                ReduxItems.VERIDIUM_PICKAXE.get(),
                ReduxItems.INFUSED_VERIDIUM_PICKAXE.get()
        );

        this.tag(Tags.Items.INGOTS).add(
                ReduxItems.VERIDIUM_INGOT.get(),
                ReduxItems.REFINED_SENTRITE.get()
        );
        this.tag(Tags.Items.NUGGETS).add(
                ReduxItems.VERIDIUM_NUGGET.get(),
                ReduxItems.SENTRITE_CHUNK.get()
        );

        this.tag(AetherTags.Items.BRONZE_DUNGEON_LOOT).add(
                ReduxItems.AERBOUND_CAPE.get()
        );
        this.tag(AetherTags.Items.ACCESSORIES_CAPES).add(
                ReduxItems.AERBOUND_CAPE.get()
        );

        this.tag(AetherTags.Items.TREATED_AS_AETHER_ITEM).add(
                ReduxItems.VERIDIUM_PICKAXE.get(),
                ReduxItems.VERIDIUM_AXE.get(),
                ReduxItems.VERIDIUM_HOE.get(),
                ReduxItems.VERIDIUM_SHOVEL.get(),
//                ReduxItems.VERIDIUM_SWORD.get(),
                ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(),
                ReduxItems.INFUSED_VERIDIUM_AXE.get(),
                ReduxItems.INFUSED_VERIDIUM_HOE.get(),
                ReduxItems.INFUSED_VERIDIUM_SHOVEL.get()
//                ReduxItems.INFUSED_VERIDIUM_SWORD.get()
        );
        this.tag(AetherTags.Items.SENTRY_BLOCKS).add(
                ReduxBlocks.CARVED_PILLAR.get().asItem(),
                ReduxBlocks.SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.CARVED_BASE.get().asItem(),
                ReduxBlocks.SENTRY_BASE.get().asItem(),
                ReduxBlocks.LOCKED_CARVED_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_CARVED_BASE.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_BASE.get().asItem(),
                ReduxBlocks.TRAPPED_CARVED_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_CARVED_BASE.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_BASE.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get().asItem(),
                ReduxBlocks.RUNELIGHT.get().asItem(),
                ReduxBlocks.LOCKED_RUNELIGHT.get().asItem(),
                ReduxBlocks.LOCKED_SENTRITE_BRICKS.get().asItem()
        );

        this.tag(AetherTags.Items.LOCKED_DUNGEON_BLOCKS).add(
                ReduxBlocks.LOCKED_CARVED_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.LOCKED_CARVED_BASE.get().asItem(),
                ReduxBlocks.LOCKED_SENTRY_BASE.get().asItem(),
                ReduxBlocks.LOCKED_RUNELIGHT.get().asItem(),
                ReduxBlocks.LOCKED_SENTRITE_BRICKS.get().asItem()
        );

        this.tag(AetherTags.Items.DUNGEON_BLOCKS).add(
                ReduxBlocks.CARVED_PILLAR.get().asItem(),
                ReduxBlocks.SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.CARVED_BASE.get().asItem(),
                ReduxBlocks.SENTRY_BASE.get().asItem(),
                ReduxBlocks.RUNELIGHT.get().asItem()
        );

        this.tag(AetherTags.Items.TRAPPED_DUNGEON_BLOCKS).add(
                ReduxBlocks.TRAPPED_CARVED_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.TRAPPED_CARVED_BASE.get().asItem(),
                ReduxBlocks.TRAPPED_SENTRY_BASE.get().asItem()
        );

        this.tag(AetherTags.Items.BOSS_DOORWAY_DUNGEON_BLOCKS).add(
                ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get().asItem(),
                ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get().asItem()
        );

        this.tag(AetherTags.Items.PHYG_TEMPTATION_ITEMS).replace(true).add(
                ReduxItems.WYND_OATS.get()
        );
        this.tag(AetherTags.Items.FLYING_COW_TEMPTATION_ITEMS).replace(true).add(
                ReduxItems.WYND_OAT_PANICLE.get()
        );

    }
}
