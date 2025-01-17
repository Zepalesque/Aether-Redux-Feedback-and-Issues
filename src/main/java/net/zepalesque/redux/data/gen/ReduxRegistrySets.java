package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.Aether;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.data.resource.registries.ReduxBiomeModifiers;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.redux.data.resource.registries.ReduxCarverConfig;
import net.zepalesque.redux.data.resource.registries.ReduxConditions;
import net.zepalesque.redux.data.resource.registries.ReduxDensityFunctions;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;
import net.zepalesque.redux.data.resource.registries.ReduxJukeboxSongs;
import net.zepalesque.redux.data.resource.registries.ReduxNoiseSettings;
import net.zepalesque.redux.data.resource.registries.ReduxPlacements;
import net.zepalesque.redux.data.resource.registries.ReduxStructureModifiers;
import net.zepalesque.zenith.core.Zenith;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ReduxRegistrySets extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, ReduxBiomes::bootstrap)
            .add(Registries.CONFIGURED_CARVER, ReduxCarverConfig::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, ReduxFeatureConfig::bootstrap)
            .add(Registries.DENSITY_FUNCTION, ReduxDensityFunctions::bootstrap)
            .add(Registries.JUKEBOX_SONG, ReduxJukeboxSongs::bootstrap)
            .add(Registries.PLACED_FEATURE, ReduxPlacements::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ReduxBiomeModifiers::bootstrap)
            .add(NeoForgeRegistries.Keys.STRUCTURE_MODIFIERS, ReduxStructureModifiers::bootstrap)
            .add(Zenith.Keys.CONDITION, ReduxConditions::bootstrap);

    public ReduxRegistrySets(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid, String... otherIds) {
        super(output, registries, BUILDER, buildModidList(modid, otherIds));
    }
    public static Set<String> buildModidList(String modid, String... otherIds) {
        List<String> list = Lists.newArrayList();
        list.add(Aether.MODID);
        list.add(modid);
        list.addAll(Arrays.stream(otherIds).toList());
        return Set.copyOf(list);
    }

    public static class NoisePack extends DatapackBuiltinEntriesProvider {
        public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
                .add(Registries.NOISE_SETTINGS, ReduxNoiseSettings.NoisePack::bootstrap);

        public NoisePack(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String modid, String... otherIds) {
            super(output, registries, BUILDER, buildModidList(modid, otherIds));
        }
    }

}