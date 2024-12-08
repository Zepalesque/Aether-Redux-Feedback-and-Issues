package net.zepalesque.redux.pack;

import com.google.common.collect.ImmutableMap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReduxPackConfig {


    private static final HashMap<Supplier<Boolean>, PackResources> RESOURCES = new HashMap<>();
    private static boolean locked = false;

    public static ConfigAssembledPackResources.AssembledResourcesSupplier generate(Path path) {
        ImmutableMap<Supplier<Boolean>, PackResources> builder = ImmutableMap.copyOf(RESOURCES);
        locked = true;
        return new ConfigAssembledPackResources.AssembledResourcesSupplier(builder, path);
    }

    public static PathPackResources createPack(String path, String id) {
        Path resource = ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + path + id);
        PackLocationInfo loc = new PackLocationInfo(id, Component.empty(), PackSource.BUILT_IN, Optional.empty());
        return new PathPackResources(loc, resource);
    }

    public static <B, T extends ModConfigSpec.ConfigValue<B>> T register(T config, String path, String id, Function<B, Boolean> predicate) {
        if (!locked) {
            RESOURCES.putIfAbsent(() -> predicate.apply(config.get()), createPack(path, id));
            Redux.LOGGER.info("Registered pack config for pack {}{}...", path, id);
        } else {
            Redux.LOGGER.warn("Attempted to register pack config for pack {}{} after locking was already complete!", path, id);
        }
        return config;
    }

    public static <T extends ModConfigSpec.ConfigValue<Boolean>> T register(T config, String path, String id, boolean predicate) {
        return register(config, path, id, bool -> bool == predicate);
    }

    public static <T extends ModConfigSpec.ConfigValue<Boolean>> T register(T config, String path, String id) {
        return register(config, path, id, true);
    }
}
