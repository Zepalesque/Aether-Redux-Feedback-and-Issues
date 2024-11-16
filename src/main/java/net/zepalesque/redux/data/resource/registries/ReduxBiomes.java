package net.zepalesque.redux.data.resource.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.builders.biome.GildedGroves;

public class ReduxBiomes {
    public static final ResourceKey<Biome> THE_BLIGHT = createKey("the_blight");
    public static final ResourceKey<Biome> FROSTED_FORESTS = createKey("frosted_forests");
    public static final ResourceKey<Biome> GLACIAL_TUNDRA = createKey("glacial_tundra");
    public static final ResourceKey<Biome> GILDED_GROVES = createKey("gilded_groves");
    public static final ResourceKey<Biome> GILDED_GRASSLANDS = createKey("gilded_grasslands");
    public static final ResourceKey<Biome> SKYFIELDS = createKey("skyfields");
    public static final ResourceKey<Biome> CLOUDCAPS = createKey("cloudcaps");
    public static final ResourceKey<Biome> SKYROOT_SHRUBLANDS = createKey("skyroot_shrublands");

    // TODO: Add to 2.1
    public static final ResourceKey<Biome> SHIMMERING_HILLS = createKey("shimmering_hills");
    public static final ResourceKey<Biome> QUICKSOIL_DUNES = createKey("quicksoil_dunes");
    public static final ResourceKey<Biome> QUICKSOIL_OASIS = createKey("quicksoil_oasis");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(Registries.BIOME, Redux.loc(name));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(GILDED_GROVES, GildedGroves.generate(context));
    }
}
