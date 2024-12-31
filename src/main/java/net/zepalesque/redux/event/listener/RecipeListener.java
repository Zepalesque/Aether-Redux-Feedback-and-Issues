package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.world.inventory.ClickAction;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemStackedOnOtherEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.recipe.ReduxRecipes;
import net.zepalesque.zenith.api.recipe.StackingRecipeHelper;
import net.zepalesque.zenith.recipe.StackingRecipeHelper;

@EventBusSubscriber(modid = Redux.MODID)
public class RecipeListener {

    @SubscribeEvent
    public static void onStackItem(ItemStackedOnOtherEvent event) {
        if (event.getClickAction() == ClickAction.SECONDARY &&
                StackingRecipeHelper.stack(event, stack -> stack.is(AetherItems.AMBROSIUM_SHARD), ReduxRecipes.INFUSION.get())) {
            event.setCanceled(true);
        }
    }
}
