package net.zepalesque.redux.event.listener;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.event.hook.BlockBreakHooks;
import net.zepalesque.redux.event.hook.ToolActionHooks;

@EventBusSubscriber(modid = Redux.MODID)
public class ToolsListener {

    @SubscribeEvent
    public static void setupToolModifications(BlockEvent.BlockToolModificationEvent event) {
        LevelAccessor levelAccessor = event.getLevel();
        BlockPos pos = event.getPos();
        BlockState oldState = event.getState();
        ItemAbility toolAction = event.getItemAbility();
        BlockState newState = ToolActionHooks.setupToolActions(levelAccessor, pos, oldState, toolAction);
        if (newState != oldState && !event.isSimulated() && !event.isCanceled()) {
            event.setFinalState(newState);
        }
    }

    @SubscribeEvent
    public static void modifyMiningSpeed(PlayerEvent.BreakSpeed event) {
        float modifiedSpeed = BlockBreakHooks.modify(event.getState().getBlock(), event.getNewSpeed());
        if (modifiedSpeed != event.getNewSpeed()) {
            event.setNewSpeed(modifiedSpeed);
        }
    }
}
