package net.zepalesque.redux.world.tree.trunk;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.tree.foliage.SkyrootFoliagePlacer;

public class ReduxTrunkPlacers {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(BuiltInRegistries.TRUNK_PLACER_TYPE, Redux.MODID);

    public static final DeferredHolder<TrunkPlacerType<?>, TrunkPlacerType<BlightwillowTrunkPlacer>> BLIGHTWILLOW_TRUNK = TRUNK_PLACERS.register("blightwillow_trunk", () -> new TrunkPlacerType<>(BlightwillowTrunkPlacer.CODEC));
}
