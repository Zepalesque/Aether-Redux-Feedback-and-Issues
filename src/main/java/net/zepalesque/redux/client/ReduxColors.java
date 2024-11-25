package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.blockset.util.TintableSet;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.world.biome.tint.ReduxBiomeTints;
import net.zepalesque.zenith.api.blockset.AbstractFlowerSet;
import net.zepalesque.zenith.api.blockset.BlockSet;

import javax.annotation.Nullable;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ReduxColors {

    public static class Tints {
        public static final int AETHER_GRASS_COLOR = 0xADF9C4;
        public static final int GILDED_GRASS_COLOR = 0xFFED96;
        public static final int BLIGHT_GRASS_COLOR = 0xD5BAFF;
    }

    public static ColorResolver AETHER_GRASS = (biome, x, z) -> ReduxBiomeTints.AETHER_GRASS.get().getColor(biome);

    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        Redux.LOGGER.debug("Beginning block color registration for the Aether: Redux");

        event.register((state, level, pos, index) -> getColor(state, level, pos, index, i -> i == 1, false), AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.register((state, level, pos, index) -> getColor(state, level, pos, index, i -> i == 0, true), ReduxBlocks.SHORT_AETHER_GRASS.get());
        event.register((state, level, pos, index) -> getColor(state, level, pos, index, i -> i == 1, true),
                AetherBlocks.WHITE_FLOWER.get(),
                AetherBlocks.POTTED_WHITE_FLOWER.get(),
                AetherBlocks.PURPLE_FLOWER.get(),
                AetherBlocks.POTTED_PURPLE_FLOWER.get(),
                ReduxBlocks.WYNDSPROUTS.get()
        );
        for (BlockSet set : Redux.BLOCK_SETS) {
            if (set instanceof TintableSet tintable && set instanceof AbstractFlowerSet flowerSet) {
                event.register((state, level, pos, index) -> getColor(state, level, pos, index, i -> i == tintable.getTintIndex(), true), flowerSet.flower().get());
            }
        }
    }

    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        Redux.LOGGER.debug("Beginning item color registration for the Aether: Redux");
        event.register((stack, tintIndex) -> tintIndex == 1 ? Tints.AETHER_GRASS_COLOR : 0xFFFFFF,
                AetherBlocks.AETHER_GRASS_BLOCK.get(),
                AetherBlocks.WHITE_FLOWER.get(),
                AetherBlocks.PURPLE_FLOWER.get(),/*
                ReduxBlocks.FLAREBLOSSOM.get(),
                ReduxBlocks.INFERNIA.get(),*/
                ReduxBlocks.WYNDSPROUTS.get()
        );
        event.register((stack, tintIndex) -> tintIndex == 0 ? Tints.AETHER_GRASS_COLOR : 0xFFFFFF,
                ReduxBlocks.SHORT_AETHER_GRASS.get()/*,
                ReduxBlocks.SPLITFERN.get())*/
        );

        for (BlockSet set : Redux.BLOCK_SETS) {
            if (set instanceof TintableSet tintable && set instanceof AbstractFlowerSet flowerSet) {
                event.register((stack, tintIndex) -> tintIndex == tintable.getTintIndex() ? tintable.getDefaultItemTint() : 0xFFFFFF, flowerSet.flower().get());
            }
        }
    }

    public static void resolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(AETHER_GRASS);
    }

    private static int getAverageColor(BlockAndTintGetter level, BlockPos blockPos, ColorResolver colorResolver) {
        if (level != null && blockPos != null) {
            try {
                return level.getBlockTint(blockPos, colorResolver);
            } catch (Exception e) {
                Redux.LOGGER.error("Failed to get Aether Grass color, this is not intended! Ignoring exception and using default color", e);
            }
        }
        return Tints.AETHER_GRASS_COLOR;
    }

    public static int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index, Predicate<Integer> indexGoal, boolean useBelowProperties) {
        if (indexGoal.test(index)) {
            if (level != null && pos != null) {
                if (level.getBlockState(pos.below()).is(ReduxTags.Blocks.BLIGHT_GRASS_BLOCKS) && useBelowProperties) {
                    return Tints.BLIGHT_GRASS_COLOR;
                } else if (state.hasProperty(ReduxStates.ENCHANTED) && state.getValue(ReduxStates.ENCHANTED)) {
                    return 0xFFFFFF;
                }
                return getAverageColor(level, pos, AETHER_GRASS);
            }
            return Tints.AETHER_GRASS_COLOR;
        }
        return 0xFFFFFF;
    }

    private static void register(RegisterColorHandlersEvent.Item colors, ItemColor resolver, Consumer<String> onError, ResourceLocation... locations) {
        for (ResourceLocation location : locations) {
            if (BuiltInRegistries.ITEM.containsKey(location)) {
                colors.register(resolver, BuiltInRegistries.ITEM.get(location));
            } else {
                onError.accept(location.toString());
            }
        }
    }

    private static final Consumer<String> BLOCK_ERROR = s -> {
        Redux.LOGGER.warn("Tried to register compat color for block that does not exist! Could not find: {}", s);
        Redux.LOGGER.warn("This is not NECESSARILY an issue, as some blocks that are mutually-exclusive in compatible mod versions exist, but it is being logged nonetheless");
    };
    private static final Consumer<String> ITEM_ERROR = s -> {
        Redux.LOGGER.warn("Tried to register compat color for item that does not exist! Could not find: {}", s);
        Redux.LOGGER.warn("This is not NECESSARILY an issue, as some items that are mutually-exclusive in compatible mod versions exist, but it is being logged nonetheless");
    };

    private static void register(RegisterColorHandlersEvent.Item colors, ItemColor resolver, ResourceLocation... locations) {
        register(colors, resolver, ITEM_ERROR, locations);
    }
    private static void register(RegisterColorHandlersEvent.Block colors, BlockColor resolver, ResourceLocation... locations) {
        register(colors, resolver, BLOCK_ERROR, locations);
    }

    private static void register(RegisterColorHandlersEvent.Block colors, BlockColor resolver, Consumer<String> onError, ResourceLocation... locations) {
        for (ResourceLocation location : locations) {
            if (BuiltInRegistries.BLOCK.containsKey(location)) {
                colors.register(resolver, BuiltInRegistries.BLOCK.get(location));
            } else {
                onError.accept(location.toString());
            }
        }
    }

}
