package net.zepalesque.redux.util.compat;

import com.google.common.collect.ImmutableMap;
import net.builderdog.ancient_aether.block.AncientAetherBlocks;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.lang.module.ModuleDescriptor;
import java.util.Map;
import java.util.function.Supplier;

public class AncientCompatUtil {

    private static final Lazy<Map<Block, Supplier<? extends ParticleOptions>>> PARTICLE_MAP = Lazy.of(() -> new ImmutableMap.Builder<Block, Supplier<? extends ParticleOptions>>()
            .put(AncientAetherBlocks.CRYSTAL_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_CRYSTAL_SKYROOT_LEAVES)
            .put(AncientAetherBlocks.ENCHANTED_SKYROOT_LEAVES.get(), ReduxParticleTypes.FALLING_ENCHANTED_SKYROOT_LEAVES)
            .put(AncientAetherBlocks.SAKURA_LEAVES.get(), ReduxParticleTypes.SAKURA_PETALS)
            .build());

    public static @Nullable Supplier<? extends ParticleOptions> getParticle(Block self) {
        return PARTICLE_MAP.get().get(self);
    }
}
