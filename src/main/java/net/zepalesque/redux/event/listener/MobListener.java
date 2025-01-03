package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.attachment.AetherPlayerAttachment;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;
import net.zepalesque.redux.item.ReduxItems;

import java.util.List;

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
        } else if (entity instanceof Slider slider) {
            SliderSignalAttachment attachment = SliderSignalAttachment.get(slider);
            attachment.onUpdate(slider);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (event.getEntity() instanceof Player player) {
            ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(player);
            int jumps = attachment.getPrevPerformedAerjumps();
            if (attachment.getPrevPerformedAerjumps() > 0) {
                float distance = event.getDistance() - jumps - 1F;
                event.setDistance(distance);
            }
        }
    }

/*    @SubscribeEvent
    public static void initializeBoundingBoxes(EntityEvent.Size event) {
        if (event.getEntity().getType().equals(AetherEntityTypes.WHIRLWIND.get())) {
            event.setNewSize(EntityDimensions.fixed(2.25F, 4.125F));
        }
    }*/
}
