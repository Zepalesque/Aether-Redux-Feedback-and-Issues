package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.util.TabUtil;

import java.util.function.Supplier;

@EventBusSubscriber(modid = Redux.MODID, bus = Bus.MOD)
public class ReduxTabs {
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    @SuppressWarnings("unchecked")
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        Supplier<? extends ItemLike> sup = null;
        for (BlockSet set : Redux.BLOCK_SETS) {
            sup = set.addToCreativeTab(event, sup, BlockSet.TabAdditionPhase.BEFORE);
        }

        if (tab == AetherCreativeTabs.AETHER_NATURAL_BLOCKS.get()) {
            TabUtil.putAfter(event,AetherBlocks.SKYROOT_LEAVES,
                    ReduxBlocks.SKYROOT_LEAF_PILE,
                    ReduxBlocks.GILDENROOT_LEAVES,
                    ReduxBlocks.GILDENROOT_LEAF_PILE
            );

            TabUtil.putAfter(event, AetherBlocks.GOLDEN_OAK_LEAVES, ReduxBlocks.GOLDEN_OAK_LEAF_PILE);
            TabUtil.putAfter(event, ReduxFlowerSets.AURUM.flower(), ReduxBlocks.GOLDEN_CLOVERS);
        }

        if (tab == AetherCreativeTabs.AETHER_DUNGEON_BLOCKS.get()) {
            TabUtil.putAfter(event, AetherBlocks.CARVED_STONE,
                    ReduxBlocks.CARVED_BASE,
                    ReduxBlocks.CARVED_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.LOCKED_CARVED_STONE,
                    ReduxBlocks.LOCKED_CARVED_BASE,
                    ReduxBlocks.LOCKED_CARVED_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.TRAPPED_CARVED_STONE,
                    ReduxBlocks.TRAPPED_CARVED_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.BOSS_DOORWAY_CARVED_STONE,
                    ReduxBlocks.BOSS_DOORWAY_CARVED_BASE,
                    ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.SENTRY_STONE,
                    ReduxBlocks.SENTRY_BASE,
                    ReduxBlocks.SENTRY_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.LOCKED_SENTRY_STONE,
                    ReduxBlocks.LOCKED_SENTRY_BASE,
                    ReduxBlocks.LOCKED_SENTRY_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.TRAPPED_SENTRY_STONE,
                    ReduxBlocks.TRAPPED_SENTRY_BASE,
                    ReduxBlocks.TRAPPED_SENTRY_PILLAR
            );

            TabUtil.putAfter(event, AetherBlocks.BOSS_DOORWAY_SENTRY_STONE,
                    ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE,
                    ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR,
                    ReduxStoneSets.SENTRITE_BRICKS.block(),
                    ReduxBlocks.LOCKED_SENTRITE_BRICKS,
                    ReduxBlocks.RUNELIGHT,
                    ReduxBlocks.LOCKED_RUNELIGHT
            );
        }

        if (tab == AetherCreativeTabs.AETHER_BUILDING_BLOCKS.get()) {
            TabUtil.putAfter(event, AetherBlocks.ZANITE_BLOCK,
                    ReduxBlocks.RAW_VERIDIUM_BLOCK,
                    ReduxBlocks.VERIDIUM_BLOCK,
                    ReduxBlocks.REFINED_SENTRITE_BLOCK
            );

            TabUtil.put(event, ReduxBlocks.SENTRITE_LANTERN, ReduxBlocks.SENTRITE_CHAIN);
        }

        if (tab == AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES.get()) {
            TabUtil.putBefore(event, AetherItems.GRAVITITE_SWORD,
                    ReduxItems.INFUSED_VERIDIUM_HOE,
                    ReduxItems.INFUSED_VERIDIUM_AXE,
                    ReduxItems.INFUSED_VERIDIUM_PICKAXE,
                    ReduxItems.INFUSED_VERIDIUM_SHOVEL,
                    /*ReduxItems.INFUSED_VERIDIUM_SWORD,
                    ReduxItems.INFUSED_VERIDIUM_SWORD,*/
                    ReduxItems.VERIDIUM_HOE,
                    ReduxItems.VERIDIUM_AXE,
                    ReduxItems.VERIDIUM_PICKAXE,
                    ReduxItems.VERIDIUM_SHOVEL/*,
                    ReduxItems.VERIDIUM_SWORD*/
            );
        }

        if (tab == AetherCreativeTabs.AETHER_INGREDIENTS.get()) {
            TabUtil.putAfter(event, AetherItems.ZANITE_GEMSTONE,
                    ReduxItems.RAW_VERIDIUM,
                    ReduxItems.VERIDIUM_INGOT,
                    ReduxItems.VERIDIUM_NUGGET,
                    ReduxItems.REFINED_SENTRITE,
                    ReduxItems.SENTRITE_CHUNK
            );

            TabUtil.putAfter(event, AetherItems.AECHOR_PETAL,
                    ReduxItems.WYND_OATS,
                    ReduxItems.WYND_OAT_PANICLE
            );
        }

        CreativeModeTab redstone = BuiltInRegistries.CREATIVE_MODE_TAB.get(CreativeModeTabs.REDSTONE_BLOCKS);

        if (tab == redstone) {
            TabUtil.putAfter(event, () -> Items.COMPARATOR, ReduxBlocks.LOGICATOR);
        }







        // SHOULD BE AT THE VERY END
        sup = null;
        for (BlockSet set : Redux.BLOCK_SETS) {
            sup = set.addToCreativeTab(event, sup, BlockSet.TabAdditionPhase.AFTER);
        }
    }
}
