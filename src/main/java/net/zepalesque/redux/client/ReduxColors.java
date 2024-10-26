package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.builderdog.ancient_aether.block.blockstate.AetherGrassType;
import net.builderdog.ancient_aether.block.blockstate.AncientAetherBlockStateProperties;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.util.compat.AncientCompatUtil;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ReduxColors {

    public static Map<Biome, Integer> AETHER_GRASS_COLORS = new HashMap<>();
    public static ColorResolver AETHER_GRASS_RESOLVER = (biome, x, z) -> AETHER_GRASS_COLORS.getOrDefault(biome, ReduxBiomes.AETHER_GRASS_COLOR);


    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        Redux.LOGGER.debug("Beginning block color registration for the Aether: Redux");
        // Register Redux's stuff
        event.register((state, level, pos, index) -> index == 1 ? level != null && pos != null  ? getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.register((state, level, pos, index) -> state.hasProperty(ReduxStates.ENCHANTED) && state.getValue(ReduxStates.ENCHANTED) ? 0xFFFFFF : getColor(state, level, pos, index, 0), ReduxBlocks.SHORT_AETHER_GRASS.get(), ReduxBlocks.SPLITFERN.get());
        event.register((state, level, pos, index) -> {
            int color = getColor(state, level, pos, 1, 1);
            return index == 1 ? color : index == 2 ? FastColor.ARGB32.color(255, (FastColor.ARGB32.red(color) + 255) / 2, (FastColor.ARGB32.green(color) + 255) / 2, (FastColor.ARGB32.blue(color) + 255) / 2) : 0xFFFFFF;
        }, ReduxBlocks.GOLDEN_CLOVER.get());
        event.register((state, level, pos, index) -> getColor(state, level, pos, index, 1),
                ReduxBlocks.WYNDSPROUTS.get(),
                ReduxBlocks.POTTED_WYNDSPROUTS.get(),
                AetherBlocks.WHITE_FLOWER.get(),
                AetherBlocks.POTTED_WHITE_FLOWER.get(),
                AetherBlocks.PURPLE_FLOWER.get(),
                AetherBlocks.POTTED_PURPLE_FLOWER.get(),
                ReduxBlocks.SKYSPROUTS.get(),
                ReduxBlocks.POTTED_SKYSPROUTS.get(),
                ReduxBlocks.LUXWEED.get(),
                ReduxBlocks.POTTED_LUXWEED.get(),
                ReduxBlocks.BLIGHTSHADE.get(),
                ReduxBlocks.POTTED_BLIGHTSHADE.get(),
                ReduxBlocks.DAGGERBLOOM.get(),
                ReduxBlocks.POTTED_DAGGERBLOOM.get(),
                ReduxBlocks.THERATIP.get(),
                ReduxBlocks.POTTED_THERATIP.get(),
                ReduxBlocks.FLAREBLOSSOM.get(),
                ReduxBlocks.POTTED_FLAREBLOSSOM.get(),
                ReduxBlocks.INFERNIA.get(),
                ReduxBlocks.POTTED_INFERNIA.get(),
                ReduxBlocks.AURUM.get(),
                ReduxBlocks.POTTED_AURUM.get(),
                ReduxBlocks.IRIDIA.get(),
                ReduxBlocks.POTTED_IRIDIA.get(),
                ReduxBlocks.LUMINA.get(),
                ReduxBlocks.POTTED_LUMINA.get(),
                ReduxBlocks.ZYATRIX.get(),
                ReduxBlocks.POTTED_ZYATRIX.get(),
                ReduxBlocks.SPIROLYCTIL.get(),
                ReduxBlocks.POTTED_SPIROLYCTIL.get(),
                ReduxBlocks.XAELIA_PATCH.get()
        );

        // Ancient Aether Compat
        if (Redux.ancientAetherCompat()) {
            register(event, (state, level, pos, index) -> level != null && pos != null ? state.hasProperty(AncientAetherBlockStateProperties.TYPE) && state.getValue(AncientAetherBlockStateProperties.TYPE) == AetherGrassType.ENCHANTED ? 0xFFFFFF : getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR,
                    new ResourceLocation("ancient_aether", "sky_grass"));
            // These will always be registered and log if there is an issue
            register(event, (state, level, pos, index) -> getColor(state, level, pos, index, 1),
                    new ResourceLocation("ancient_aether", "wynd_thistle"),
                    new ResourceLocation("ancient_aether", "potted_wynd_thistle"),
                    new ResourceLocation("ancient_aether", "sky_blues"),
                    new ResourceLocation("ancient_aether", "potted_sky_blues")

                    );
            // Only logs if AA version is 0.9.0 or later
            register(event, (state, level, pos, index) -> getColor(state, level, pos, index, 1),
                    new ResourceLocation("ancient_aether", "sunset_rose"),
                    new ResourceLocation("ancient_aether", "potted_sunset_rose"),
                    new ResourceLocation("ancient_aether", "elevetia"),
                    new ResourceLocation("ancient_aether", "potted_elevetia")

                    );
            // Only logs in versions prior to 0.9.0
            register(event, (state, level, pos, index) -> getColor(state, level, pos, index, 1),
                    new ResourceLocation("ancient_aether", "highland_viola"),
                    new ResourceLocation("ancient_aether", "potted_highland_viola"),
                    new ResourceLocation("ancient_aether", "sakura_blossoms"),
                    new ResourceLocation("ancient_aether", "potted_sakura_blossoms"),
                    new ResourceLocation("ancient_aether", "trapped_sakura_blossoms")
            );
        }

        // Deep Aether compat
        if (Redux.deepAetherCompat()) {
            register(event, (state, level, pos, index) -> getColor(state, level, pos, index, 1),
                    new ResourceLocation("deep_aether", "radiant_orchid"),
                    new ResourceLocation("deep_aether", "potted_radiant_orchid"),
                    new ResourceLocation("deep_aether", "aerlavender"),
                    new ResourceLocation("deep_aether", "potted_aerlavender"),
                    new ResourceLocation("deep_aether", "tall_aerlavender"),
                    new ResourceLocation("deep_aether", "feather_grass"),
                    new ResourceLocation("deep_aether", "potted_tall_aerlavender"),
                    new ResourceLocation("deep_aether", "sky_tulips"),
                    new ResourceLocation("deep_aether", "potted_sky_tulips"),
                    new ResourceLocation("deep_aether", "iaspove"),
                    new ResourceLocation("deep_aether", "potted_iaspove"),
                    new ResourceLocation("deep_aether", "golden_aspess"),
                    new ResourceLocation("deep_aether", "potted_golden_aspess"),
                    new ResourceLocation("deep_aether", "echaisy"),
                    new ResourceLocation("deep_aether", "potted_echaisy")
            );
            register(event, (state, level, pos, index) -> pos != null ? getColor(state, level, state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos, index, 1)  : ReduxBiomes.AETHER_GRASS_COLOR,
                    new ResourceLocation("deep_aether", "tall_feather_grass")
            );
        }
    }

    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        Redux.LOGGER.debug("Beginning item color registration for the Aether: Redux");
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.WHITE_FLOWER.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.PURPLE_FLOWER.get());
        event.register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SHORT_AETHER_GRASS.get());
        event.register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPLITFERN.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.FLAREBLOSSOM.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.INFERNIA.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUXWEED.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.WYNDSPROUTS.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.SKYFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SKYSPROUTS.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.SKYFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.XAELIA_PATCH.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.AURUM.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASSLANDS_COLOR : 0xFFFFFF, ReduxBlocks.ZYATRIX.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.SKYFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.IRIDIA.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.SKYFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.DAGGERBLOOM.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.SHRUBLANDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.THERATIP.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.FROSTED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUMINA.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPIROLYCTIL.get());
        event.register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.BLIGHTSHADE.get());

        // Ancient Aether Compat
        if (Redux.ancientAetherCompat()) {
            register(event, (stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("ancient_aether", "sky_grass"));

            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.FROZEN_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("ancient_aether", "wynd_thistle"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("ancient_aether", "sky_blues"));

            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("ancient_aether", "sunset_rose"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.PALE_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("ancient_aether", "elevetia"));
        }
        // Deep AetherAa Compat
        if (Redux.deepAetherCompat()) {
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AERGLOW_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "radiant_orchid"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AERLAVENDER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "aerlavender"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AERLAVENDER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "tall_aerlavender"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLUE_AERGLOW_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "feather_grass"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLUE_AERGLOW_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "tall_feather_grass"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "sky_tulips"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "iaspove"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "golden_aspess"));
            register(event, (stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF,
                    new ResourceLocation("deep_aether", "echaisy"));
        }
    }

    public static int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index, int indexGoal) {
        return index == indexGoal ? level != null && pos != null ? level.getBlockState(pos.below()).is(ReduxBlocks.BLIGHTMOSS_BLOCK.get()) ? ReduxBiomes.BLIGHT_GRASS_COLOR : getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF;
    }

    private static int getAverageColor(BlockAndTintGetter level, BlockPos blockPos, ColorResolver colorResolver) {
        try {
            return level.getBlockTint(blockPos, colorResolver);
        } catch (Exception e) {
            Redux.LOGGER.error("Failed to get Aether Grass color, this is not intended! Ignoring exception and using default color", e);
            return ReduxBiomes.AETHER_GRASS_COLOR;
        }
    }

    public static void resolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(AETHER_GRASS_RESOLVER);
    }

    private static void register(RegisterColorHandlersEvent.Item colors, ItemColor resolver, Consumer<String> onError, ResourceLocation... locations) {
        for (ResourceLocation location : locations) {
            if (ForgeRegistries.ITEMS.containsKey(location)) {
                colors.register(resolver, ForgeRegistries.ITEMS.getValue(location));
            } else {
                onError.accept(location.toString());
            }
        }
    }

    private static final Consumer<String> BLOCK_ERROR = s -> {
        Redux.LOGGER.warn("Tried to register compat color for block that does not exist! Could not find: {}", s);
    };
    private static final Consumer<String> ITEM_ERROR = s -> {
        Redux.LOGGER.warn("Tried to register compat color for item that does not exist! Could not find: {}", s);
    };

    private static void register(RegisterColorHandlersEvent.Item colors, ItemColor resolver, ResourceLocation... locations) {
    register(colors, resolver, ITEM_ERROR, locations);
    }
    private static void register(RegisterColorHandlersEvent.Block colors, BlockColor resolver, ResourceLocation... locations) {
    register(colors, resolver, BLOCK_ERROR, locations);
    }
    private static void register(RegisterColorHandlersEvent.Block colors, BlockColor resolver, Consumer<String> onError, ResourceLocation... locations) {
        for (ResourceLocation location : locations) {
            if (ForgeRegistries.BLOCKS.containsKey(location)) {
                colors.register(resolver, ForgeRegistries.BLOCKS.getValue(location));
            } else {
                onError.accept(location.toString());
            }
        }
    }
}
