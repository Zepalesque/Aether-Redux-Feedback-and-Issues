package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.unity.block.UnityBlocks;
import net.zepalesque.unity.data.prov.UnityBlockStateProvider;
import net.zepalesque.unity.data.resource.builders.UnityFeatureBuilders;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;

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
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(
                                        AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()
                                ))),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.THE_BLIGHT),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.4),
                                        SurfaceRules.state(UnityFeatureBuilders.drops(UnityBlocks.COARSE_AETHER_DIRT)))))
        );
    }
}