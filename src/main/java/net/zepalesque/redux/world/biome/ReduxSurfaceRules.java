package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.zenith.world.density.PerlinNoiseFunction;
import terrablender.api.SurfaceRuleManager;

import java.util.ArrayList;
import java.util.List;

@EventBusSubscriber(modid = Redux.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ReduxSurfaceRules {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        RegistryAccess access = server.registryAccess();
        Registry<LevelStem> registry = access.registryOrThrow(Registries.LEVEL_STEM);
        LevelStem stem = registry.get(AetherDimensions.AETHER_LEVEL_STEM);
        if (stem != null) {
            ChunkGenerator generator = stem.generator();
            ServerLevel level = server.getLevel(AetherDimensions.AETHER_LEVEL);
            if (generator instanceof NoiseBasedChunkGenerator noiseGen && level != null) {
                noiseGen.generatorSettings().value().noiseRouter().finalDensity().mapAll(PerlinNoiseFunction.createOrGetVisitor(level.getSeed()));
            }
        }
    }

    public static SurfaceRules.RuleSource makeRules(/*SurfaceRules.SequenceRuleSource base*/) {
        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()))))



        );
    }
}