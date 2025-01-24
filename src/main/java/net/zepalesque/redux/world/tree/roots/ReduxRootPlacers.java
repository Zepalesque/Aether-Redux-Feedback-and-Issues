package net.zepalesque.redux.world.tree.roots;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxRootPlacers {

    public static final DeferredRegister<RootPlacerType<?>> ROOT_PLACERS = DeferredRegister.create(BuiltInRegistries.ROOT_PLACER_TYPE, Redux.MODID);

}
