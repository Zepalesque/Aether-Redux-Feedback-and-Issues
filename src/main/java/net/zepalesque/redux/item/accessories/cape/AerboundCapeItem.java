package net.zepalesque.redux.item.accessories.cape;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.accessories.cape.CapeItem;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.api.slot.SlotType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;

import java.util.List;

public class AerboundCapeItem extends CapeItem {
    public AerboundCapeItem(Properties properties) {
        super(Redux.loc("aerbound_cape"), properties);
    }

    public static final ResourceLocation MODIFIER_ID = Redux.loc("aerbound_cape_aerjump");

    /**
     * Removes the aerjumps modifier when the Aerbound Cape is unequipped.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference   The {@link SlotReference} of the accessory.
     */
    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        if (livingEntity instanceof Player player) {
            ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(player);
            attachment.removeAerjumpModifier(player, MODIFIER_ID);
        }
    }

    /**
     * Adds the aerjumps modifier when the Aerbound Cape is unequipped.
     *
     * @param stack       The accessory {@link ItemStack}.
     * @param reference   The {@link SlotReference} of the accessory.
     */
    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        LivingEntity livingEntity = reference.entity();
        if (livingEntity instanceof Player player) {
            ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(player);
            attachment.addAerjumpModifier(player, MODIFIER_ID, 1);
        }
    }

    @Override
    public void getAttributesTooltip(ItemStack stack, SlotType type, List<Component> tooltips, TooltipContext tooltipContext, TooltipFlag tooltipType) {
        tooltips.add(Component.translatable("tooltip.aether_redux.aerbound_cape_aerjump_ability", Minecraft.getInstance().options.keyJump.getKey().getDisplayName()).withStyle(ChatFormatting.BLUE));
        super.getAttributesTooltip(stack, type, tooltips, tooltipContext, tooltipType);
    }
}
