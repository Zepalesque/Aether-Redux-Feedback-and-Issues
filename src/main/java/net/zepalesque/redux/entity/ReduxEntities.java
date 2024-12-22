package net.zepalesque.redux.entity;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Unique;

import java.util.Map;
import java.util.function.Function;

public class ReduxEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, Redux.MODID);

    public static void addBossConversions() {
        Map<Block, Function<BlockState, BlockState>> slider = Slider.DUNGEON_BLOCK_CONVERSIONS;

        slider.put(ReduxBlocks.LOCKED_CARVED_BASE.get(), state -> ReduxBlocks.CARVED_BASE.get().defaultBlockState());
        slider.put(ReduxBlocks.LOCKED_SENTRY_BASE.get(), state -> ReduxBlocks.SENTRY_BASE.get().defaultBlockState());
        slider.put(ReduxBlocks.LOCKED_CARVED_PILLAR.get(), state -> ReduxBlocks.CARVED_PILLAR.get().defaultBlockState());
        slider.put(ReduxBlocks.LOCKED_RUNELIGHT.get(), state -> ReduxBlocks.RUNELIGHT.get().defaultBlockState());
        slider.put(ReduxBlocks.LOCKED_SENTRITE_BRICKS.get(), state -> ReduxStoneSets.SENTRITE_BRICKS.block().get().defaultBlockState());
    }

}
