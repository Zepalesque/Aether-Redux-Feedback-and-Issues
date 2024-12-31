package net.zepalesque.redux.data.resource.registries;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.DensityFunctions;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.builders.ReduxNoiseBuilders;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;

import java.util.Optional;

public class ReduxDensityFunctions extends ReduxNoiseBuilders {

    public static final ResourceKey<DensityFunction> CLOUDBED_NOISE = createKey("cloudbed_noise");
    public static final ResourceKey<DensityFunction> CLOUDBED_Y_OFFSET = createKey("cloudbed_y_offset");

    public static final ResourceKey<DensityFunction> REDUX_3D_NOISE = createKey("base_3d_noise_redux");
    public static final ResourceKey<DensityFunction> REDUX_FINAL_DENSITY = createKey("redux_final_density");

    public static void bootstrap(BootstrapContext<DensityFunction> context) {
        HolderGetter<DensityFunction> functions = context.lookup(Registries.DENSITY_FUNCTION);
        context.register(CLOUDBED_NOISE, DensityFunctions.mul(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(0, 1, 1, 1, 1, 1), 0.01D, 0.0D, 42), DensityFunctions.constant(1.5D)));

        context.register(CLOUDBED_Y_OFFSET, DensityFunctions.mul(new PerlinNoiseFunction(new NormalNoise.NoiseParameters(0, 1, 1), 0.005D, 0.0D, 95), DensityFunctions.constant(1.5D)));

        context.register(REDUX_3D_NOISE, BlendedNoise.createUnseeded(
                0.25, // xz scale
                0.375, // y scale
                80.0, // xz factor
                80.0, // y factor
                8.0 // smear scale multiplier, capped at 8
        ));

        context.register(REDUX_FINAL_DENSITY, createFinal(getFunction(functions, REDUX_3D_NOISE)));

    }

    private static ResourceKey<DensityFunction> createKey(String name) {
        return ResourceKey.create(Registries.DENSITY_FUNCTION, Redux.loc(name));
    }

    public static DensityFunction get(HolderGetter<DensityFunction> densityFunctions, ResourceKey<DensityFunction> key) {
        return new DensityFunctions.HolderHolder(densityFunctions.getOrThrow(key));
    }

    public static DensityFunction get(RegistryAccess access, ResourceKey<DensityFunction> key) {
        Optional<HolderLookup.RegistryLookup<DensityFunction>> optional = access.lookup(Registries.DENSITY_FUNCTION);
        if (optional.isPresent()) {
            HolderLookup.RegistryLookup<DensityFunction> lookup = optional.get();
            return lookup.getOrThrow(key).value();

        } else {
            throw new NullPointerException("Optional value is not present!");
        }
    }

    public static DensityFunction get(Level level, ResourceKey<DensityFunction> key) {
        return level.registryAccess().registryOrThrow(Registries.DENSITY_FUNCTION).getOrThrow(key);
    }

}