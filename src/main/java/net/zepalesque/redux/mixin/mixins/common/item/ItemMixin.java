package net.zepalesque.redux.mixin.mixins.common.item;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "getFood", at = @At("RETURN"), cancellable = true)
    public void getFoodProperties(CallbackInfoReturnable<FoodProperties> cir) {
    }


    @Inject(method = "overrideStackedOnOther", at = @At("RETURN"), cancellable = true)
    protected void overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player, CallbackInfoReturnable<Boolean> cir) {

    }

}
