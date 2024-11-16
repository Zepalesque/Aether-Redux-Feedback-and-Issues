package net.zepalesque.redux.client.renderer;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.zepalesque.redux.Redux;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ReduxRenderers {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        Redux.BLOCK_SETS.forEach(set -> set.registerRenderers(event));
    }
}
