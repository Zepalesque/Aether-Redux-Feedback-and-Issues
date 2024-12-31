package net.zepalesque.redux.event.listener;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.event.hook.BlockBreakHooks;

@EventBusSubscriber(modid = Redux.MODID)
public class BlockBreakListener {

    @SubscribeEvent
    public static void modifyMiningSpeed(PlayerEvent.BreakSpeed event) {
        float modifiedSpeed = BlockBreakHooks.modify(event.getState().getBlock(), event.getNewSpeed());
        if (modifiedSpeed != event.getNewSpeed()) {
            event.setNewSpeed(modifiedSpeed);
        }
    }
}
