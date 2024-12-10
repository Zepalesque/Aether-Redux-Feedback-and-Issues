package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;

@EventBusSubscriber(modid = Redux.MODID)
public class MobListener {

    @SubscribeEvent
    public static void onTick(EntityTickEvent.Post event) {
        final Entity entity = event.getEntity();
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity)) {
            QuicksoilHooks.alterMovement(entity);
        }

        if (entity instanceof Player player) {
            ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(player);
            attachment.onUpdate(player);
        }
    }

    // TODO: add this back in if necessary
/*    @SubscribeEvent
    public static void joinAndSetDoubleJumps(EntityJoinLevelEvent event) {
        if (!event.getEntity().level().isClientSide() && event.getEntity() instanceof ServerPlayer player) {
            ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(player);
            if (EquipmentUtil.hasAccessory(player, ReduxItems.AERBOUND_CAPE.get())) {
                attachment.addAerjumpModifier(player, AerboundCapeItem.MODIFIER_ID, 1);
            }
        }
    }*/
}
