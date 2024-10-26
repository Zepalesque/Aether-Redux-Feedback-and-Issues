package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.data.resource.*;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ReduxRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = (new RegistrySetBuilder())
            .add(Registries.DAMAGE_TYPE, ReduxDamageTypes::bootstrap)
            .add(Registries.DENSITY_FUNCTION, ReduxDensityFunctions::bootstrap)
            .add(Registries.CONFIGURED_CARVER, ReduxConfiguredCarvers::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ReduxFeatureConfig::bootstrap)
            .add(Registries.PLACED_FEATURE, ReduxPlacements::bootstrap)
            .add(Registries.BIOME, ReduxBiomes::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ReduxBiomeModifiers::bootstrap);

    public ReduxRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, RegistrySetBuilder builder, String modid, String... otherIds) {
        super(output, registries, builder, buildModidList(modid, otherIds));
    }

    public static Set<String> buildModidList(String modid, String... otherIds) {
        List<String> list = Lists.newArrayList();
        list.add(Aether.MODID);
        list.add(modid);
        list.addAll(Arrays.stream(otherIds).toList());
        return Set.copyOf(list);
    }

    public static HolderLookup.Provider patchLookup(HolderLookup.Provider lookup) {
        return BUILDER.buildPatch(RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY), lookup);
    }


    public static class NoisePack extends DatapackBuiltinEntriesProvider {
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(Registries.NOISE_SETTINGS, ReduxNoiseSettings.NoisePack::bootstrap);

        public NoisePack(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid, String... otherIds) {
            super(output, registries, BUILDER, buildModidList(modid, otherIds));
        }
    }

}
