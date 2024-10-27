package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DABlocks;

import java.util.concurrent.CompletableFuture;

public class ReduxBlockTagsData extends BlockTagsProvider {

    public ReduxBlockTagsData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, modid, existingFileHelper);
    }

    protected void addTags(HolderLookup.Provider provider) {

        this.tag(AetherTags.Blocks.AERCLOUDS).add(ReduxBlocks.BLIGHTED_AERCLOUD.get());

        this.tag(BlockTags.CLIMBABLE).add(
                ReduxBlocks.GILDED_VINES_PLANT.get(),
                ReduxBlocks.GOLDEN_VINES_PLANT.get(),
                ReduxBlocks.CORRUPTED_VINES_PLANT.get()
                );
        this.tag(ReduxTags.Blocks.ENCHANTED_VINES_SKIP_PLACEMENT).add(
                ReduxBlocks.GILDED_VINES_PLANT.get(),
                ReduxBlocks.GOLDEN_VINES_PLANT.get(),
                ReduxBlocks.GILDED_VINES.get(),
                ReduxBlocks.GOLDEN_VINES.get(),
                AetherBlocks.GOLDEN_OAK_LEAVES.get(),
                ReduxBlocks.GILDED_OAK_LEAVES.get()
        );

        this.tag(ReduxTags.Blocks.MUSHROOM_CAPS).add(
                ReduxBlocks.JELLYSHROOM_JELLY_BLOCK.get(),
                ReduxBlocks.CLOUD_CAP_BLOCK.get()
        );
        this.tag(ReduxTags.Blocks.DO_NOT_REPLACE_AETHER_GRASS).add(
                ReduxBlocks.SHORT_AETHER_GRASS.get(),
                ReduxBlocks.AVELIUM_ROOTS.get(),
                ReduxBlocks.AVELIUM_SPROUTS.get()
        );

        this.tag(ReduxTags.Blocks.ROCK_REPLACEABLE).addTag(BlockTags.DIRT);

        for (WoodHandler woodHandler : Redux.WOOD_HANDLERS)        {
            woodHandler.sporingBlocksBlockTag.ifPresent(
                    (tag) -> {
                        this.tag(tag).add(woodHandler.sporingLog.get().get(), woodHandler.sporingWood.get().get());
                        this.tag(woodHandler.logsBlockTag).addOptionalTag(tag.location());

                    }
            );
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                    woodHandler.planks.get(),
                    woodHandler.bookshelf.get(),
                    woodHandler.stairs.get(),
                    woodHandler.slab.get(),
                    woodHandler.fence.get(),
                    woodHandler.fenceGate.get(),
                    woodHandler.door.get(),
                    woodHandler.trapdoor.get(),
                    woodHandler.sign.get(),
                    woodHandler.wallSign.get(),
                    woodHandler.logWall.get(),
                    woodHandler.woodWall.get()
            ).addTag(woodHandler.logsBlockTag);
            this.tag(Tags.Blocks.BOOKSHELVES).add(woodHandler.bookshelf.get());
            this.tag(woodHandler.logsBlockTag).add(
                    woodHandler.log.get(),
                    woodHandler.wood.get()
            );
            if (woodHandler.hasStrippedLogs)
            {
                this.tag(woodHandler.logsBlockTag).add(
                        woodHandler.strippedLog.get().get(),
                        woodHandler.strippedWood.get().get()
                );
                this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                        woodHandler.strippedLogWall.get().get(),
                        woodHandler.strippedWoodWall.get().get()
                );
            }


            this.tag(BlockTags.LOGS).addTag(woodHandler.logsBlockTag);
            this.tag(BlockTags.PLANKS).add(woodHandler.planks.get());
            this.tag(BlockTags.WOODEN_SLABS).add(woodHandler.slab.get());
            this.tag(BlockTags.WOODEN_STAIRS).add(woodHandler.stairs.get());
            this.tag(BlockTags.WOODEN_FENCES).add(woodHandler.fence.get());
            this.tag(BlockTags.FENCE_GATES).add(woodHandler.fenceGate.get());
            this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(woodHandler.pressurePlate.get());
            this.tag(BlockTags.WOODEN_BUTTONS).add(woodHandler.button.get());
            this.tag(BlockTags.WOODEN_TRAPDOORS).add(woodHandler.trapdoor.get());
            this.tag(BlockTags.WOODEN_DOORS).add(woodHandler.door.get());
            this.tag(BlockTags.STANDING_SIGNS).add(woodHandler.sign.get());
            this.tag(BlockTags.WALL_SIGNS).add(woodHandler.wallSign.get());
            this.tag(BlockTags.WALLS).add(woodHandler.logWall.get(), woodHandler.woodWall.get());
            if (woodHandler.hasStrippedLogs) {
                this.tag(BlockTags.WALLS).add(woodHandler.strippedLogWall.get().get(), woodHandler.strippedWoodWall.get().get());
            }



        }



        this.tag(AetherTags.Blocks.AETHER_DIRT).add(
                ReduxBlocks.AVELIUM.get(),
                ReduxBlocks.COARSE_AETHER_DIRT.get(),
                ReduxBlocks.BLIGHTMOSS_BLOCK.get(),
                ReduxBlocks.FUNGAL_GROWTH.get()
        );
        this.tag(BlockTags.LEAVES).add(
                ReduxBlocks.BLIGHTWILLOW_LEAVES.get(),
                ReduxBlocks.GLACIA_LEAVES.get(),
                ReduxBlocks.PURPLE_GLACIA_LEAVES.get(),
                ReduxBlocks.GILDED_OAK_LEAVES.get(),
                ReduxBlocks.FIELDSPROOT_LEAVES.get(),
                ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                ReduxBlocks.AVELIUM.get(),
                ReduxBlocks.COARSE_AETHER_DIRT.get()
                );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ReduxBlocks.DRIFTSHALE.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE_WALL.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE_SLAB.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE_STAIRS.get(),
                ReduxBlocks.DIVINITE.get(),
                ReduxBlocks.DIVINITE_WALL.get(),
                ReduxBlocks.DIVINITE_SLAB.get(),
                ReduxBlocks.DIVINITE_STAIRS.get(),
                ReduxBlocks.SENTRITE.get(),
                ReduxBlocks.SENTRITE_WALL.get(),
                ReduxBlocks.SENTRITE_SLAB.get(),
                ReduxBlocks.SENTRITE_STAIRS.get(),
                ReduxBlocks.SENTRITE_BRICKS.get(),
                ReduxBlocks.SENTRITE_BRICK_WALL.get(),
                ReduxBlocks.SENTRITE_BRICK_SLAB.get(),
                ReduxBlocks.SENTRITE_BRICK_STAIRS.get(),
                ReduxBlocks.GILDED_HOLYSTONE.get(),
                ReduxBlocks.GILDED_HOLYSTONE_WALL.get(),
                ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(),
                ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get(),
                ReduxBlocks.VERIDIUM_BLOCK.get(),
                ReduxBlocks.RAW_VALKYRUM_BLOCK.get(),
                ReduxBlocks.VERIDIUM_ORE.get(),
                ReduxBlocks.RAW_VERIDIUM_BLOCK.get(),
                ReduxBlocks.RAW_VALKYRUM_BLOCK.get(),
                ReduxBlocks.VERIDIUM_LANTERN.get(),
                ReduxBlocks.VERIDIUM_CHAIN.get(),
                ReduxBlocks.SHELL_SHINGLES.get(),
                ReduxBlocks.ENCHANTED_SHELL_SHINGLES.get(),
                ReduxBlocks.GRAVITITE_BLOCK.get(),
                ReduxBlocks.RAW_GRAVITITE_BLOCK.get(),
                ReduxBlocks.REFINED_SENTRITE_BLOCK.get(),
                ReduxBlocks.SENTRITE_CHAIN.get(),
                ReduxBlocks.SENTRITE_LANTERN.get()
        );
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                ReduxBlocks.VERIDIUM_ORE.get()
        );
        this.tag(AetherTags.Blocks.GRAVITITE_ABILITY_BLACKLIST).add(
                ReduxBlocks.RAW_VALKYRUM_BLOCK.get()
        );
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                ReduxBlocks.GRAVITITE_BLOCK.get(),
                ReduxBlocks.RAW_GRAVITITE_BLOCK.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                ReduxBlocks.BLIGHTMOSS_BLOCK.get(),
                ReduxBlocks.BLIGHTMOSS_CARPET.get(),
                ReduxBlocks.FUNGAL_GROWTH.get(),
                ReduxBlocks.FUNGAL_CARPET.get(),
                ReduxBlocks.ZANBERRY_BUSH.get(),
                ReduxBlocks.ZANBERRY_BUSH_STEM.get(),
                ReduxBlocks.GLACIA_LEAVES.get(),
                ReduxBlocks.PURPLE_GLACIA_LEAVES.get(),
                ReduxBlocks.GILDED_OAK_LEAVES.get(),
                ReduxBlocks.BLIGHTED_SKYROOT_LEAVES.get(),
                ReduxBlocks.BLIGHTWILLOW_LEAVES.get(),
                ReduxBlocks.FIELDSPROOT_LEAVES.get(),
                ReduxBlocks.GOLDEN_LEAF_PILE.get(),
                ReduxBlocks.GILDED_LEAF_PILE.get(),
                ReduxBlocks.BLIGHTWILLOW_LEAF_PILE.get(),
                ReduxBlocks.CLOUD_CAP_BLOCK.get(),
                ReduxBlocks.CLOUDCAP_SPORES.get(),
                ReduxBlocks.JELLYSHROOM_JELLY_BLOCK.get()
        );

        this.tag(AetherTags.Blocks.HOLYSTONE).add(ReduxBlocks.GILDED_HOLYSTONE.get(), ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get());

        this.tag(AetherTags.Blocks.AETHER_ANIMALS_SPAWNABLE_ON).add(ReduxBlocks.AVELIUM.get(), ReduxBlocks.COARSE_AETHER_DIRT.get(), Blocks.SNOW_BLOCK, AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());

        // Adds every single Redux block as a block that should be treaded as an Aether Block and get the tool debuff
        IntrinsicTagAppender<Block> tag = this.tag(AetherTags.Blocks.TREATED_AS_AETHER_BLOCK);
        for (RegistryObject<Block> block : ReduxBlocks.BLOCKS.getEntries())
        {tag.add(block.get()); }

        this.tag(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR).add(AetherBlocks.QUICKSOIL.get(), AetherBlocks.QUICKSOIL_GLASS.get(), AetherBlocks.QUICKSOIL_GLASS_PANE.get());




        this.tag(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT).add(Blocks.SNOW_BLOCK);




        this.tag(ReduxTags.Blocks.AETHER_CARVER_REPLACEABLES).addTag(AetherTags.Blocks.HOLYSTONE).addTag(AetherTags.Blocks.AETHER_DIRT).add(
                AetherBlocks.QUICKSOIL.get(),
                Blocks.SNOW,
                Blocks.POWDER_SNOW
                ).addOptional(DABlocks.AETHER_MUD.getId()).addOptional(DABlocks.GOLDEN_GRASS_BLOCK.getId());
        this.tag(ReduxTags.Blocks.AETHER_MOSS_REPLACEABLES).addTag(AetherTags.Blocks.HOLYSTONE);
        this.tag(ReduxTags.Blocks.COARSE_AETHER_DIRT).add(ReduxBlocks.COARSE_AETHER_DIRT.get()).addOptional(DABlocks.AETHER_COARSE_DIRT.getId());
        this.tag(BlockTags.FLOWERS).add(
                ReduxBlocks.IRIDIA.get(),
                ReduxBlocks.AURUM.get(),
                ReduxBlocks.SPIROLYCTIL.get(),
                ReduxBlocks.BLIGHTSHADE.get()
                );
        this.tag(AetherTags.Blocks.HOLYSTONE).add(ReduxBlocks.BLIGHTMOSS_HOLYSTONE.get(), ReduxBlocks.GILDED_HOLYSTONE.get());
        this.tag(BlockTags.MUSHROOM_GROW_BLOCK).add(ReduxBlocks.AVELIUM.get());
        this.tag(BlockTags.FLOWER_POTS).add(
                ReduxBlocks.POTTED_IRIDIA.get(),
                ReduxBlocks.POTTED_AURUM.get(),
                ReduxBlocks.POTTED_GOLDEN_CLOVER.get(),
                ReduxBlocks.POTTED_SPIROLYCTIL.get(),
                ReduxBlocks.POTTED_BLIGHTSHADE.get(),
                ReduxBlocks.POTTED_LUXWEED.get(),
                ReduxBlocks.POTTED_ZANBERRY_BUSH_STEM.get(),
                ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING.get(),
                ReduxBlocks.POTTED_FIELDSPROOT_SAPLING.get(),
                ReduxBlocks.POTTED_GLACIA_SAPLING.get(),
                ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING.get(),
                ReduxBlocks.POTTED_GILDED_OAK_SAPLING.get(),
                ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING.get(),
                ReduxBlocks.POTTED_LUMINA.get(),
                ReduxBlocks.POTTED_DAGGERBLOOM.get(),
                ReduxBlocks.POTTED_WYNDSPROUTS.get(),
                ReduxBlocks.POTTED_SPLITFERN.get()
                );
        this.tag(ReduxTags.Blocks.HIGHLANDS_GRASSES).add(AetherBlocks.AETHER_GRASS_BLOCK.get());
        this.tag(ReduxTags.Blocks.ENCHANTED_GRASSES).add(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get());
        this.tag(ReduxTags.Blocks.AEVELIUM_GRASSES).add(ReduxBlocks.AVELIUM.get());
        this.tag(BlockTags.SAPLINGS).add(
                ReduxBlocks.BLIGHTWILLOW_SAPLING.get(),
                ReduxBlocks.GLACIA_SAPLING.get(),
                ReduxBlocks.PURPLE_GLACIA_SAPLING.get(),
                ReduxBlocks.ZANBERRY_BUSH_STEM.get(),
                ReduxBlocks.GILDED_OAK_SAPLING.get(),
                ReduxBlocks.BLIGHTED_SKYROOT_SAPLING.get(),
                ReduxBlocks.FIELDSPROOT_SAPLING.get()
        );
        this.tag(BlockTags.WALLS).add(
                ReduxBlocks.DIVINITE_WALL.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE_WALL.get(),
                ReduxBlocks.SENTRITE_WALL.get(),
                ReduxBlocks.SENTRITE_BRICK_WALL.get(),
                ReduxBlocks.SHELL_SHINGLE_WALL.get(),
                ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL.get(),
                ReduxBlocks.GILDED_HOLYSTONE_WALL.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL.get()
        );
        this.tag(BlockTags.STAIRS).add(
                ReduxBlocks.DIVINITE_STAIRS.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE_STAIRS.get(),
                ReduxBlocks.SENTRITE_STAIRS.get(),
                ReduxBlocks.SENTRITE_BRICK_STAIRS.get(),
                ReduxBlocks.SHELL_SHINGLE_STAIRS.get(),
                ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS.get(),
                ReduxBlocks.GILDED_HOLYSTONE_STAIRS.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS.get()
        );
        this.tag(BlockTags.SLABS).add(
                ReduxBlocks.DIVINITE_SLAB.get(),
                ReduxBlocks.POLISHED_DRIFTSHALE_SLAB.get(),
                ReduxBlocks.SENTRITE_SLAB.get(),
                ReduxBlocks.SENTRITE_BRICK_SLAB.get(),
                ReduxBlocks.SHELL_SHINGLE_SLAB.get(),
                ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB.get(),
                ReduxBlocks.GILDED_HOLYSTONE_SLAB.get(),
                ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB.get()
        );

        this.tag(BlockTags.REPLACEABLE).add(
                ReduxBlocks.SHORT_AETHER_GRASS.get(),
                ReduxBlocks.AVELIUM_ROOTS.get(),
                ReduxBlocks.AVELIUM_SPROUTS.get()
        );

        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(ReduxBlocks.VERIDIUM_BLOCK.get(), ReduxBlocks.GRAVITITE_BLOCK.get());




        // 1.20.4
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
                ReduxBlocks.RUNELIGHT.get()
        );

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                ReduxBlocks.CARVED_PILLAR.get(),
                ReduxBlocks.SENTRY_PILLAR.get(),
                ReduxBlocks.CARVED_BASE.get(),
                ReduxBlocks.SENTRY_BASE.get()
        );
    }

}

