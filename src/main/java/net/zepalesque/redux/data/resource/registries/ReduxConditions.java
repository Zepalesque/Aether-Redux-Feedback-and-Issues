package net.zepalesque.redux.data.resource.registries;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.condition.type.ConfigCondition;
import net.zepalesque.zenith.api.condition.type.ModLoadedCondition;
import net.zepalesque.zenith.core.Zenith;

public class ReduxConditions {

    public static final ResourceKey<Condition<?>> DEEP = createKey("deep_aether");
    public static final ResourceKey<Condition<?>> GENESIS = createKey("aether_genesis");
    public static final ResourceKey<Condition<?>> LOST = createKey("lost_content");
    public static final ResourceKey<Condition<?>> ANCIENT = createKey("ancient_aether");

    public static final ResourceKey<Condition<?>> SKY_COLORS = createKey("sky_colors");
    public static final ResourceKey<Condition<?>> WATER_COLORS = createKey("water_colors");
    public static final ResourceKey<Condition<?>> CLOUDBED = createKey("cloudbed");


    public static void bootstrap(BootstrapContext<Condition<?>> context) {
        context.register(DEEP, new ModLoadedCondition("deep_aether"));
        context.register(GENESIS, new ModLoadedCondition("aether_genesis"));
        context.register(LOST, new ModLoadedCondition("lost_aether_content"));
        context.register(ANCIENT, new ModLoadedCondition("ancient_aether"));
        context.register(SKY_COLORS, new ConfigCondition(ReduxConfig.SERVER.serializerID(), ReduxConfig.SERVER.redux_sky_colors));
        context.register(CLOUDBED, new ConfigCondition(ReduxConfig.SERVER.serializerID(), ReduxConfig.SERVER.cloudbed));
        context.register(WATER_COLORS, new ConfigCondition(ReduxConfig.SERVER.serializerID(), ReduxConfig.SERVER.redux_water_colors));
    }

    private static ResourceKey<Condition<?>> createKey(String name) {
        return ResourceKey.create(Zenith.Keys.CONDITION, Redux.loc(name));
    }
}
