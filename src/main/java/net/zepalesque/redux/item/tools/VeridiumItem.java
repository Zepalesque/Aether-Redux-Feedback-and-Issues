package net.zepalesque.redux.item.tools;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSoundPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.components.ReduxDataComponents;
import net.zepalesque.redux.recipe.recipes.InfusionRecipe;
import net.zepalesque.zenith.item.CustomStackingBehavior;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;

public interface VeridiumItem extends CustomStackingBehavior {

    String NBT_KEY = "infusion_level";
    String INFUSION_AMOUNT = "infusion_increase";
    int DURABILITY_DMG_MULTIPLIER = 4;
    Component HOVER_TOOLTIP = Component.translatable("tooltip.aether_redux.infusion_info").withStyle(ChatFormatting.GRAY);

    Item getUninfusedItem(ItemStack stack);

    default ItemStack getUninfusedStack(ItemStack stack) {
        ItemStack i = new ItemStack(this.getUninfusedItem(stack));
        DataComponentPatch patch = stack.getComponentsPatch();
        i.applyComponents(patch);
        i.remove(ReduxDataComponents.INFUSION);
        return i;
    }

    @Nullable
    @Override
    default ItemStack transformStack(Ingredient ingredient, ItemStack original, RecipeType<?> type, Optional<CompoundTag> additionalData) {
        if (additionalData.isEmpty()) {
            return original;
        }
        CompoundTag additional = additionalData.get();
        int increase = additional.getByte(InfusionRecipe.ADDED_INFUSION);
        if (increase <= 0) {
            return original;
        }
        int max = ReduxConfig.SERVER.max_veridium_tool_infusion.get();
        int i = Objects.requireNonNullElse(original.get(ReduxDataComponents.INFUSION), 0);
        // The integer i will already be zero if the component is not present, and as the config has a minimum value of 1, it will skip this and add infusion if the item doesn't have the component
        if (i >= max) {
            return null;
        } else {
            int infusion = (byte) Math.min(i + additional.getByte(InfusionRecipe.ADDED_INFUSION), max);
            original.applyComponents(DataComponentPatch.builder().set(ReduxDataComponents.INFUSION.get(), infusion).build());
            return original;
        }
    }

    // If null is returned, do not change the item in the slot
    @Nullable
    default ItemStack deplete(ItemStack stack, @Nullable LivingEntity user, int amount) {
        if (user != null && user.level().isClientSide()) {
            return null;
        }
        if (user instanceof Player p && p.isCreative()) {
            return null;
        }
        int original = Objects.requireNonNullElse(stack.get(ReduxDataComponents.INFUSION), 0);

        if (original > amount) {
            int infusion = (byte) (original - amount);
            stack.set(ReduxDataComponents.INFUSION.get(), infusion);
        } else {
            return this.getUninfusedStack(stack);
        }
        return null;
    }

    default Holder<SoundEvent> getUninfuseSound() {
        return ReduxSounds.INFUSION_EXPIRE;
    }

    default void sendSound(ServerPlayer sp) {
        sp.connection.send(new ClientboundSoundPacket(getUninfuseSound(), SoundSource.PLAYERS, sp.getX(), sp.getY(), sp.getZ(), 0.8F, 0.8F + sp.level().getRandom().nextFloat() * 0.4F, sp.level().getRandom().nextLong()));
    }
}
