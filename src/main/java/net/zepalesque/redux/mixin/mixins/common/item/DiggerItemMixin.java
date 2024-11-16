package net.zepalesque.redux.mixin.mixins.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DiggerItem.class)
public class DiggerItemMixin {
    @Inject(method = "postHurtEnemy", at = @At(value = "HEAD"), cancellable = true)
    protected void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, CallbackInfo ci) {}
}
