package net.zepalesque.redux.world.carver;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxCarvers {
    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(BuiltInRegistries.CARVER, Redux.MODID);

    public static final DeferredHolder<WorldCarver<?>, AetherCaveCarver> AETHER_CAVE = CARVERS.register("aether_cave",
            () -> new AetherCaveCarver(CaveCarverConfiguration.CODEC)
    );
    
}
