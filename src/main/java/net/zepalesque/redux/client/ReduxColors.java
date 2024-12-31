package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.util.TintableSet;
import net.zepalesque.unity.client.UnityColors;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.type.AbstractFlowerSet;

public class ReduxColors {

    public static class Tints {
        public static final int GILDED_GRASS_COLOR = 0xFFED96;
        public static final int BLIGHT_GRASS_COLOR = 0xD5BAFF;
    }

    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        Redux.LOGGER.debug("Beginning block color registration for the Aether: Redux");

        event.register((state, level, pos, index) -> UnityColors.getColor(state, level, pos, index, i -> i == 1, true),
                AetherBlocks.WHITE_FLOWER.get(),
                AetherBlocks.POTTED_WHITE_FLOWER.get(),
                AetherBlocks.PURPLE_FLOWER.get(),
                AetherBlocks.POTTED_PURPLE_FLOWER.get(),
                ReduxBlocks.WYNDSPROUTS.get()
        );
        for (BlockSet set : Redux.BLOCK_SETS) {
            if (set instanceof TintableSet tintable && set instanceof AbstractFlowerSet flowerSet) {
                event.register((state, level, pos, index) -> UnityColors.getColor(state, level, pos, index, i -> i == tintable.getTintIndex(), true), flowerSet.flower().get());
            }
        }
    }

    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        Redux.LOGGER.debug("Beginning item color registration for the Aether: Redux");
        event.register((stack, tintIndex) -> tintIndex == 1 ? UnityColors.AETHER_GRASS_COLOR : 0xFFFFFF,
                /*ReduxBlocks.FLAREBLOSSOM.get(),
                ReduxBlocks.INFERNIA.get(),*/
                ReduxBlocks.WYNDSPROUTS.get()
        );
        /*event.register((stack, tintIndex) -> tintIndex == 0 ? UnityColors.AETHER_GRASS_COLOR : 0xFFFFFF,
                ReduxBlocks.SPLITFERN.get())
        );*/

        for (BlockSet set : Redux.BLOCK_SETS) {
            if (set instanceof TintableSet tintable && set instanceof AbstractFlowerSet flowerSet) {
                event.register((stack, tintIndex) -> tintIndex == tintable.getTintIndex() ? tintable.getDefaultItemTint() : 0xFFFFFF, flowerSet.flower().get());
            }
        }
    }
}
