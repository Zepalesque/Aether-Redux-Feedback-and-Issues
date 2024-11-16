package net.zepalesque.redux.pack;

import com.aetherteam.aether.client.CombinedPackResources;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.CompositePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.resources.IoSupplier;
import net.neoforged.neoforge.common.conditions.ConditionalOps;
import net.zepalesque.redux.Redux;
import org.apache.commons.compress.utils.Lists;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;



// frick, need to rewrite stuff
// TODO: Wire up the data side of this
public class ConfigAssembledPackResources extends AbstractPackResources {

    private final Map<Supplier<Boolean>, PackResources> packs;
    private final Map<String, Map<Supplier<Boolean>, PackResources>> assets;
    private final Path source;
    private static final Gson GSON = new Gson();
    protected ConfigAssembledPackResources(PackLocationInfo id, ImmutableMap.Builder<Supplier<Boolean>, PackResources> builder, Path source) {
        super(id);
        this.source = source;
        Map<Supplier<Boolean>, PackResources> packs = builder.build();
        this.packs = packs;
        this.assets = this.buildNamespaceMap(PackType.CLIENT_RESOURCES, packs);
    }

    private Map<String, Map<Supplier<Boolean>, PackResources>> buildNamespaceMap(PackType type, Map<Supplier<Boolean>, PackResources> packMap) {
        Map<String, Map<Supplier<Boolean>, PackResources>> map = new HashMap<>();
        for (Map.Entry<Supplier<Boolean>, PackResources> entry : packMap.entrySet()) {
            if (entry.getValue() != null) {
                PackResources pack = entry.getValue();
                for (String namespace : pack.getNamespaces(type)) {
                    map.computeIfAbsent(namespace, k -> new HashMap<>()).put(entry.getKey(), pack);
                }
            }
        }
        map.replaceAll((k, list) -> ImmutableMap.copyOf(list));
        return ImmutableMap.copyOf(map);
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getRootResource(String... paths) {
        Path path = this.resolve(paths);
        return !Files.exists(path) ? null : IoSupplier.create(path);
    }

    protected Path resolve(String... paths) {
        Path path = this.source;

        for (String name : paths) {
            path = path.resolve(name);
        }
        return path;
    }

    @Nullable
    @Override
    public IoSupplier<InputStream> getResource(PackType type, ResourceLocation location) {
        if (location.getPath().matches("lang/.+\\.json")) {
            JsonObject combined = new JsonObject();
            for (PackResources pack : getCandidatePacks(type, location)) {
                IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
                if (ioSupplier != null) {
                    try {
                        JsonObject jsonobject = GSON.fromJson(new InputStreamReader(ioSupplier.get(), StandardCharsets.UTF_8), JsonObject.class);
                        jsonobject.entrySet().forEach(entry -> combined.add(entry.getKey(), entry.getValue()));
                    } catch (IOException e) {
                        Redux.LOGGER.error("Caught exception when trying to combine language files!", e);
                    }
                }
            }

            if (combined.entrySet().isEmpty()) {
                return null;
            }
            String input = GSON.toJson(combined);
            return () -> new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        } else {
            for (PackResources pack : this.getCandidatePacks(type, location)) {
                IoSupplier<InputStream> ioSupplier = pack.getResource(type, location);
                if (ioSupplier != null) {
                    return ioSupplier;
                }
            }
            return null;
        }
    }

    @Override
    public void listResources(PackType type, String resourceNamespace, String paths, ResourceOutput resourceOutput) {
        for (PackResources delegate : this.getAvaliablePacks()) {
            delegate.listResources(type, resourceNamespace, paths, resourceOutput);
        }
    }

    public List<PackResources> getAvaliablePacks() {
        return packs.entrySet().stream().filter(entry -> entry.getKey().get()).map(Map.Entry::getValue).toList();
    }

    @Override
    public @NotNull Set<String> getNamespaces(PackType type) {
        return type == PackType.CLIENT_RESOURCES ? assets.keySet() : Set.of();
    }

    @Override
    public void close() {
        for (PackResources pack : packs.values()) {
            pack.close();
        }
    }

    private List<PackResources> getCandidatePacks(PackType type, ResourceLocation location) {
        if (type == PackType.CLIENT_RESOURCES) {
            Map<Supplier<Boolean>, PackResources> packsWithNamespace = this.assets.get(location.getNamespace());
            return packsWithNamespace == null ? Collections.emptyList() : packsWithNamespace.entrySet().stream().filter(entry -> entry.getKey().get()).map(Map.Entry::getValue).toList();
        } else {
            return Collections.emptyList();
        }
    }

    public String toString() {
        return String.format("%s: %s", this.getClass().getName(), source);
    }


    public record AssembledResourcesSupplier(ImmutableMap.Builder<Supplier<Boolean>, PackResources> builder, Path source) implements Pack.ResourcesSupplier {
        @Override
        public PackResources openPrimary(PackLocationInfo location) {
            return new ConfigAssembledPackResources(location, this.builder, this.source);
        }

        @Override
        public PackResources openFull(PackLocationInfo location, Pack.Metadata info) {
            PackResources packresources = this.openPrimary(location);
            List<String> list = info.overlays();
            if (list.isEmpty()) {
                return packresources;
            } else {
                List<PackResources> list1 = new ArrayList<>(list.size());

                for (String s : list) {
                    Path path = this.source.resolve(s);
                    list1.add(new ConfigAssembledPackResources(location, this.builder, path));
                }

                return new CompositePackResources(packresources, list1);
            }
        }
    }
}
