package net.zepalesque.redux.item.combat;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.aetherteam.aether.item.combat.DartShooterItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.components.ReduxDataComponents;
import net.zepalesque.redux.item.tools.VeridiumItem;
import net.zepalesque.redux.item.util.TooltipUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VeridiumDartShooter extends DartShooterItem implements VeridiumItem {
    private final Supplier<? extends Item> uninfused;
    private final int useTime;

    public VeridiumDartShooter(Supplier<? extends Item> dartType, int useTime, Properties properties, Supplier<? extends Item> uninfused) {
        super(dartType, properties);
        this.uninfused = uninfused;
        this.useTime = useTime;
    }

    @Override
    public Item getUninfusedItem(ItemStack stack) {
        return this.uninfused.get();
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return this.useTime;
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        ItemStack original = super.finishUsingItem(stack, level, user);
        ItemStack transform = this.deplete(original, user, 1);
        if (!user.level().isClientSide() && transform != null && transform != original) {
            if (user instanceof ServerPlayer sp) {
                this.sendSound(sp);
            }
        }
        return transform == null ? original : transform;

    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag advanced) {
        MutableComponent infusion = Component.translatable("tooltip.aether_redux.infusion_charge", !stack.has(ReduxDataComponents.INFUSION) ? 0 : stack.get(ReduxDataComponents.INFUSION.get())).withStyle(ChatFormatting.GRAY);

        tooltips.add(infusion);
        Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(HOVER_TOOLTIP);
        tooltips.add(info);
        super.appendHoverText(stack, context, tooltips, advanced);
    }

    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, @Nullable T entity, Consumer<Item> onBroken) {
        return super.damageItem(stack, amount, entity, onBroken) * VeridiumItem.DURABILITY_DMG_MULTIPLIER;
    }

    public static class Uninfused extends DartShooterItem {

        private final int useTime;
        public Uninfused(Supplier<? extends Item> dartType, int useTime, Properties properties) {
            super(dartType, properties);
            this.useTime = useTime;
        }

        @Override
        public int getUseDuration(ItemStack stack, LivingEntity entity) {
            return this.useTime;
        }

        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag advanced) {
            Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(HOVER_TOOLTIP);
            tooltips.add(info);
            super.appendHoverText(stack, context, tooltips, advanced);
        }
    }
}
