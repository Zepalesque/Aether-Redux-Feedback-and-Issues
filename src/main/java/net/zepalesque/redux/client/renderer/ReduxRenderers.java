package net.zepalesque.redux.client.renderer;

import com.aetherteam.aether.client.renderer.entity.SliderRenderer;
import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.entity.EmberRenderer;
import net.zepalesque.redux.client.renderer.entity.VeridiumDartRenderer;
import net.zepalesque.redux.client.renderer.entity.ReduxEvilWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.ReduxWhirlwindRenderer;
import net.zepalesque.redux.client.renderer.entity.layer.SliderSignalLayer;
import net.zepalesque.redux.client.renderer.entity.model.WhirlwindModel;
import net.zepalesque.redux.entity.ReduxEntities;

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
        event.registerEntityRenderer(ReduxEntities.EMBER.get(), EmberRenderer::new);
        event.registerEntityRenderer(ReduxEntities.INFUSED_VERIDIUM_DART.get(), VeridiumDartRenderer::new);
        event.registerEntityRenderer(ReduxEntities.VERIDIUM_DART.get(), VeridiumDartRenderer.Uninfused::new);
    }

    @SubscribeEvent
    public static void addRenderLayers(EntityRenderersEvent.AddLayers event) {
        if (event.getRenderer(AetherEntityTypes.SLIDER.get()) instanceof SliderRenderer renderer) {
            renderer.addLayer(new SliderSignalLayer(renderer));
        }
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
