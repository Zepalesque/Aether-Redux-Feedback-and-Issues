package net.zepalesque.redux.data.gen.loot;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.mixin.mixins.common.accessor.BlockLootAccessor;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.crop.WyndoatsBlock;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.item.ReduxItems;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReduxBlockLoot extends ReduxBlockLootProvider {

    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(AetherBlocks.TREASURE_CHEST.get()).map(ItemLike::asItem).collect(Collectors.toSet());

    public ReduxBlockLoot(HolderLookup.Provider registries) {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        Redux.BLOCK_SETS.forEach(set -> set.lootData(this));

        this.add(ReduxBlocks.SHORT_AETHER_GRASS.get(), shears());

        this.add(ReduxBlocks.GILDENROOT_LEAVES.get(),
                (leaves) -> droppingWithChancesAndSkyrootSticks(leaves, ReduxFlowerSets.GILDENROOT_SAPLING.flower().get(), BlockLootAccessor.aether$getNormalLeavesSaplingChances()));

        this.add(ReduxBlocks.GILDENROOT_LEAF_PILE.get(), shears());
        this.add(ReduxBlocks.GOLDEN_OAK_LEAF_PILE.get(), shears());
        this.add(ReduxBlocks.SKYROOT_LEAF_PILE.get(), shears());

        this.dropSelf(ReduxBlocks.CARVED_PILLAR.get());
        this.dropSelf(ReduxBlocks.SENTRY_PILLAR.get());
        this.dropSelf(ReduxBlocks.CARVED_BASE.get());
        this.dropSelf(ReduxBlocks.SENTRY_BASE.get());

        this.dropNone(ReduxBlocks.LOCKED_CARVED_PILLAR.get());
        this.dropNone(ReduxBlocks.LOCKED_SENTRY_PILLAR.get());
        this.dropNone(ReduxBlocks.LOCKED_CARVED_BASE.get());
        this.dropNone(ReduxBlocks.LOCKED_SENTRY_BASE.get());

        this.dropNone(ReduxBlocks.TRAPPED_CARVED_PILLAR.get());
        this.dropNone(ReduxBlocks.TRAPPED_SENTRY_PILLAR.get());
        this.dropNone(ReduxBlocks.TRAPPED_CARVED_BASE.get());
        this.dropNone(ReduxBlocks.TRAPPED_SENTRY_BASE.get());

        this.dropNone(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR.get());
        this.dropNone(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR.get());
        this.dropNone(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE.get());
        this.dropNone(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE.get());

        this.dropSelf(ReduxBlocks.RUNELIGHT.get());
        this.dropNone(ReduxBlocks.LOCKED_RUNELIGHT.get());
        this.dropNone(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get());

        this.add(ReduxBlocks.WYNDSPROUTS.get(), shearsOr(ReduxItems.WYND_OATS.get(), 0.25F));
        this.dropPottedContents(ReduxBlocks.POTTED_WYNDSPROUTS.get());
        this.add(ReduxBlocks.SKYSPROUTS.get(), shears());
        this.dropPottedContents(ReduxBlocks.POTTED_SKYSPROUTS.get());
        this.add(ReduxBlocks.WYNDOATS.get(),
                this.createCropDrops(
                        ReduxBlocks.WYNDOATS.get(),
                        ReduxItems.WYND_OAT_PANICLE.get(),
                        ReduxItems.WYND_OATS.get(),
                        LootItemBlockStatePropertyCondition.
                                hasBlockStateProperties(ReduxBlocks.WYNDOATS.get())
                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WyndoatsBlock.AGE, WyndoatsBlock.MAX_AGE))));
        this.dropSelf(ReduxBlocks.SENTRITE_CHAIN.get());
        this.dropSelf(ReduxBlocks.SENTRITE_LANTERN.get());
        this.dropSelf(ReduxBlocks.SENTRITE_BARS.get());
        this.dropSelf(ReduxBlocks.REFINED_SENTRITE_BLOCK.get());
        this.dropSelf(ReduxBlocks.RUNIC_LANTERN.get());

        this.dropSelf(ReduxBlocks.VERIDIUM_ORE.get());
        this.dropSelf(ReduxBlocks.VERIDIUM_BLOCK.get());
        this.dropSelf(ReduxBlocks.RAW_VERIDIUM_BLOCK.get());

        this.add(ReduxBlocks.GOLDEN_CLOVERS.get(), this::createPetalsDrops);
        this.add(ReduxBlocks.GOLDEN_VINES.get(), shears());
        this.add(ReduxBlocks.GOLDEN_VINES_PLANT.get(), shears().apply(ReduxBlocks.GOLDEN_VINES.get()));

        this.dropSelf(ReduxBlocks.LOGICATOR.get());
    }

    @Override
    public Iterable<Block> getKnownBlocks() {
        return ReduxBlocks.BLOCKS.getEntries().stream().map(Supplier::get).collect(Collectors.toList());
    }
}
