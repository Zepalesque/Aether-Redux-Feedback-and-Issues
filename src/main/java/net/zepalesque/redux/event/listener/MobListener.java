package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.accessories.cape.AerboundCapeItem;

@EventBusSubscriber(modid = Redux.MODID)
public class MobListener {

    @SubscribeEvent
    public static void handleQuicksoilMovement(EntityTickEvent.Post event) {
        final Entity entity = event.getEntity();
        if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity)) {
            QuicksoilHooks.alterMovement(entity);
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
