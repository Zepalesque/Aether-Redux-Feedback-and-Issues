package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.generators.tags.AetherEntityTagData;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.gen.ReduxBlockStateData;
import net.zepalesque.redux.data.gen.ReduxLootModifierData;
import net.zepalesque.redux.data.gen.ReduxMapData;
import net.zepalesque.redux.data.gen.ReduxItemModelData;
import net.zepalesque.redux.data.gen.ReduxLanguageData;
import net.zepalesque.redux.data.gen.ReduxLootData;
import net.zepalesque.redux.data.gen.ReduxParticleData;
import net.zepalesque.redux.data.gen.ReduxRecipeData;
import net.zepalesque.redux.data.gen.ReduxRegistrySets;
import net.zepalesque.redux.data.gen.ReduxSoundsData;
import net.zepalesque.redux.data.gen.tags.ReduxBiomeTagsData;
import net.zepalesque.redux.data.gen.tags.ReduxBlockTagsData;
import net.zepalesque.redux.data.gen.tags.ReduxEntityTagsData;
import net.zepalesque.redux.data.gen.tags.ReduxItemTagsData;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ReduxData {
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new ReduxBlockStateData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageData(packOutput));
        generator.addProvider(event.includeClient(), new ReduxParticleData(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxSoundsData(packOutput, fileHelper));

        // Server Data
        generator.addProvider(event.includeServer(), new ReduxRecipeData(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), ReduxLootData.create(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ReduxMapData(packOutput, lookupProvider));
        DatapackBuiltinEntriesProvider registrySets = new ReduxRegistrySets(packOutput, lookupProvider, Redux.MODID);
        // Use for structure and damage type data, plus any custom ones that need to access the condition registry
        CompletableFuture<Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);
        generator.addProvider(event.includeServer(), new ReduxLootModifierData(packOutput, registryProvider));

        // Tags
        ReduxBlockTagsData blockTags = new ReduxBlockTagsData(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ReduxItemTagsData(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new ReduxEntityTagsData(packOutput, lookupProvider, fileHelper));

        generator.addProvider(event.includeServer(), new ReduxBiomeTagsData(packOutput, registryProvider, fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_redux.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));


        Path builtinData = packOutput.getOutputFolder().resolve("packs").resolve("data");
        
        DataGenerator.PackGenerator noisePack = generator.new PackGenerator(event.includeServer(), "reduxnoise", new PackOutput(builtinData.resolve("redux_noise")));
        noisePack.addProvider(output -> new ReduxRegistrySets.NoisePack(output, lookupProvider, Redux.MODID));
    }
}