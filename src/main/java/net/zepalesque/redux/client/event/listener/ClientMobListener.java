package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.event.hook.KeyHooks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ClientMobListener {

    @SubscribeEvent
    public static void cancelSneak(EntityTickEvent event) {
        final Entity entity = event.getEntity();
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity)) {
            KeyHooks.cancelKey(entity, Minecraft.getInstance().options.keyShift);
        }
    }
}
