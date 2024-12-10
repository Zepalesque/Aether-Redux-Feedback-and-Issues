package net.zepalesque.redux.item.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.UnaryOperator;

public class TooltipUtils {
    /**
     * Returns the given component, or the default message if the shift key is up.
     */
    public static final UnaryOperator<Component> TOOLTIP_SHIFT_FOR_INFO = (component -> Screen.hasShiftDown() ? component : Component.translatable("tooltip.aether_redux.shift_info", Minecraft.getInstance().options.keyShift.getKey().getDisplayName().getString()).withStyle(ChatFormatting.DARK_GRAY));
    /**
     * Returns the given component, or null if the shift key is up.
     */
    public static final UnaryOperator<Component> TOOLTIP_HIDDEN_SHIFT_INFO = (component -> Screen.hasShiftDown() ? component : null);
}