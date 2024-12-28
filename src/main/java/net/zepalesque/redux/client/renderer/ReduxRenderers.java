package net.zepalesque.redux.client.renderer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.PassiveWhirlwind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.entity.ReduxEvilWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.ReduxWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.model.WhirlwindModel;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ReduxRenderers {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModelLayers.WHIRLWIND, WhirlwindModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Redux.BLOCK_SETS.forEach(set -> set.registerRenderers(event));
        event.registerEntityRenderer(AetherEntityTypes.WHIRLWIND.get(), ReduxWhirlwindRenderer::new);
        event.registerEntityRenderer(AetherEntityTypes.EVIL_WHIRLWIND.get(), ReduxEvilWhirlwindRenderer::new);
    }

    public static void registerAccessoryRenderers() {
    }


    public static class ModelLayers {

        public static final ModelLayerLocation WHIRLWIND = register("whirlwind");

        private static ModelLayerLocation register(String name) {
            return register(name, "main");
        }

        private static ModelLayerLocation register(String name, String type) {
            return register(Redux.loc(name), type);
        }

        private static ModelLayerLocation register(ResourceLocation location, String type) {
            return new ModelLayerLocation(location, type);
        }
    }
}
