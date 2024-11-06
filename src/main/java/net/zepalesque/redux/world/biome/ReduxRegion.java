package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.mojang.datafixers.util.Pair;
import net.builderdog.ancient_aether.data.resources.registries.AncientAetherBiomes;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import teamrazor.aeroblender.aether.AetherRegionType;
import terrablender.api.Region;

import java.util.function.Consumer;

public class ReduxRegion extends Region {

    public ReduxRegion(ResourceLocation name, int weight) {
        super(name, AetherRegionType.THE_AETHER, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        Climate.Parameter fullRange = Climate.Parameter.span(-1.5F, 1.5F);

        ResourceKey<Biome> frosted = ReduxConfig.COMMON.enable_snowy_biomes.get() ? ReduxBiomes.FROSTED_FORESTS : AetherBiomes.SKYROOT_FOREST;
        ResourceKey<Biome> glaical = ReduxConfig.COMMON.enable_snowy_biomes.get() ? ReduxBiomes.GLACIAL_TUNDRA : AetherBiomes.SKYROOT_FOREST;
        ResourceKey<Biome> cloudcaps = ReduxConfig.COMMON.enable_cloudcaps.get() ? ReduxBiomes.CLOUDCAPS : AetherBiomes.SKYROOT_GROVE;
        ResourceKey<Biome> blight = ReduxConfig.COMMON.enable_the_blight.get() ? ReduxBiomes.THE_BLIGHT : AetherBiomes.SKYROOT_WOODLAND;
        ResourceKey<Biome> highfields = ReduxConfig.COMMON.enable_skyfields.get() ? ReduxBiomes.SKYFIELDS : AetherBiomes.SKYROOT_MEADOW;
        ResourceKey<Biome> shrublands = ReduxConfig.COMMON.enable_skyroot_shrublands.get() ? ReduxBiomes.SKYROOT_SHRUBLANDS : AetherBiomes.SKYROOT_MEADOW;
        ResourceKey<Biome> grove = ReduxConfig.COMMON.enable_gilded_biomes.get() ? ReduxBiomes.GILDED_GROVES : AetherBiomes.SKYROOT_GROVE;
        ResourceKey<Biome> grasslands = ReduxConfig.COMMON.enable_gilded_biomes.get() ? ReduxBiomes.GILDED_GRASSLANDS : AetherBiomes.SKYROOT_MEADOW;
        Climate.Parameter temp1 = Climate.Parameter.span(-1.5F, -0.5F);
        Climate.Parameter temp2 = Climate.Parameter.span(-0.5F, -0.2F);
        Climate.Parameter temp3 = Climate.Parameter.span(-0.2F, 0.2F);
        Climate.Parameter temp4 = Climate.Parameter.span(0.2F, 0.5F);
        Climate.Parameter temp5 = Climate.Parameter.span(0.5F, 1.5F);

        addBiome(mapper, new Climate.ParameterPoint(temp1, Climate.Parameter.span(-1.0F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                frosted);
        addBiome(mapper, new Climate.ParameterPoint(temp1, Climate.Parameter.span(-0.3F, 0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                glaical);
        addBiome(mapper, new Climate.ParameterPoint(temp1, Climate.Parameter.span(0.3F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                cloudcaps);

        addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(-1.0F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                blight);
        addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(-0.3F, -0.15F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_FOREST);
        addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(-0.15F, 0.0F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_MEADOW);
        addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(0.0F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_WOODLAND);
        addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(0.2F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                highfields); //ReduxBiomes.SHIMMERING_HILLS);

        addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-1.0F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                highfields);
        addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-0.3F, -0.1F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_FOREST);
        addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-0.1F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_MEADOW);
        addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(0.2F, 0.5F), fullRange, fullRange, fullRange, fullRange, 0),
                highfields);
        addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(0.5F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                shrublands);

        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-1.0F, -0.6F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_MEADOW);
        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.6F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                shrublands);
        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.3F, -0.05F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_GROVE);
        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.05F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
                shrublands);
        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(0.2F, 0.5F), fullRange, fullRange, fullRange, fullRange, 0),
                AetherBiomes.SKYROOT_GROVE); //ReduxBiomes.QUICKSOIL_DUNES);
        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(0.5F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                shrublands); //ReduxBiomes.QUICKSOIL_OASIS);

        addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(-1.0F, -0.4F), fullRange, fullRange, fullRange, fullRange, 0),
                grasslands);
        addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(-0.4F, 0.3F), fullRange, fullRange, fullRange, fullRange, 0),
                grove);
        addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(0.3F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
                grasslands);

    }
}