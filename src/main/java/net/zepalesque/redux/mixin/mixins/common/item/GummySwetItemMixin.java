package net.zepalesque.redux.mixin.mixins.common.item;

import com.aetherteam.aether.item.food.GummySwetItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.property.ReduxFoods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GummySwetItem.class)
public abstract class GummySwetItemMixin {

    @WrapOperation(method = "getFoodProperties", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getFoodProperties(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)Lnet/minecraft/world/food/FoodProperties;"))
    public FoodProperties getFoodProperties(GummySwetItem instance, ItemStack stack, LivingEntity living, Operation<FoodProperties> original) {
        if (ReduxConfig.SERVER.gummy_swet_nerf.get()) return ReduxFoods.GUMMY_SWET_NERF;
        else return original.call(instance, stack, living);
    }
}
