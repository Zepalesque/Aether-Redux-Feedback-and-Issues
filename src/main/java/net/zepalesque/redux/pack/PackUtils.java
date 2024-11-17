package net.zepalesque.redux.pack;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.zepalesque.redux.Redux;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class PackUtils {

    public static void setupPack(AddPackFindersEvent event, String path, String id, boolean required, Function<Path, Pack.ResourcesSupplier> packBuilder) {
        PackLocationInfo loc = new PackLocationInfo(id, Component.translatable("pack.aether_redux." + id + ".title"), PackSource.BUILT_IN, Optional.empty());
        String folder = (event.getPackType() == PackType.SERVER_DATA ? "data/" : "resource/");
        Path resourcePath = ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + folder + path);
        PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether_redux." + id + ".description"),
                SharedConstants.getCurrentVersion().getPackVersion(event.getPackType()));
        Pack.Metadata meta = new Pack.Metadata(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), true);
        Pack.ResourcesSupplier resources = packBuilder.apply(resourcePath);
        event.addRepositorySource((source) ->
                source.accept(new Pack(
                        loc,
                        resources,
                        meta,
                        new PackSelectionConfig(required, Pack.Position.TOP, false))
                ));

    }

    public static void setupPack(AddPackFindersEvent event, String path, String id, boolean required) {
        setupPack(event, path, id, required, PathPackResources.PathResourcesSupplier::new);
    }
}
