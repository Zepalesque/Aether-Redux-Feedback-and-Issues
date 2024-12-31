package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.data.resources.registries.AetherStructures;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.world.structure.modifier.ConditionalStructureModifier;
import net.zepalesque.zenith.api.world.structure.modifier.RemoveStructureModifier;
import net.zepalesque.zenith.core.Zenith;

public class ReduxStructureModifiers {
    public static final ResourceKey<StructureModifier> REMOVE_LARGE_CLOUDS = createKey("remove_large_clouds");

    private static ResourceKey<StructureModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIERS, Redux.loc(name));
    }

    public static void bootstrap(BootstrapContext<StructureModifier> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        HolderGetter<Condition<?>> conditions = context.lookup(Zenith.Keys.CONDITION);

        StructureModifier largeCloud = new RemoveStructureModifier(HolderSet.direct(structures.getOrThrow(AetherStructures.LARGE_AERCLOUD)));
        context.register(REMOVE_LARGE_CLOUDS, new ConditionalStructureModifier(Holder.direct(largeCloud), conditions.get(ReduxConditions.CLOUDBED).orElseThrow()));
    }
}
