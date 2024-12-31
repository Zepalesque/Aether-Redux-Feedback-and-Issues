package net.zepalesque.redux.mixin.mixins.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.components.ReduxDataComponents;
import net.zepalesque.redux.item.tools.VeridiumItem;
import net.zepalesque.zenith.api.item.TabUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// mixin for my own class, how very fun
@Mixin(TabUtil.class)
public class TabUtilMixin {

    @WrapOperation(method = "stack", at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack redux$stack(ItemLike item, Operation<ItemStack> original) {
        ItemStack stack = original.call(item);
        if (item instanceof VeridiumItem) {
            stack.set(ReduxDataComponents.INFUSION, ReduxConfig.SERVER.max_veridium_tool_infusion.get());
        }
        return stack;
    }
}
