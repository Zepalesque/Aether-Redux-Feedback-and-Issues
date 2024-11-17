package net.zepalesque.redux.pack;

import com.google.common.collect.ImmutableMap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;

import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

public class ReduxPackConfig {
    public static ConfigAssembledPackResources.AssembledResourcesSupplier generate(Path path) {
        ImmutableMap.Builder<Supplier<Boolean>, PackResources> builder = new ImmutableMap.Builder<>();
        builder.put(ReduxConfig.CLIENT.tintable_grass, createPack("resource/", "tintable_grass"));
        builder.put(ReduxConfig.CLIENT.jappafied_textures, createPack("resource/", "jappafied"));

        return new ConfigAssembledPackResources.AssembledResourcesSupplier(builder, path);
    }

    public static PathPackResources createPack(String path, String id) {
        Path resource = ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + path + id);
        PackLocationInfo loc = new PackLocationInfo(id, Component.empty(), PackSource.BUILT_IN, Optional.empty());
        return new PathPackResources(loc, resource);
    }
}
