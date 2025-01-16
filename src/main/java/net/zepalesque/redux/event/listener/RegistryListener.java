package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.item.food.GummySwetItem;
import net.minecraft.core.component.DataComponents;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.item.property.ReduxFoods;

@EventBusSubscriber(modid = Redux.MODID, bus = EventBusSubscriber.Bus.MOD)
public class RegistryListener {

    @SubscribeEvent
    public static void modifyComponents(ModifyDefaultComponentsEvent event) {
        if (true) // TODO: Config
            event.modifyMatching(item -> item instanceof GummySwetItem, builder -> builder.set(DataComponents.FOOD, ReduxFoods.GUMMY_SWET_NERF));
    }
}
