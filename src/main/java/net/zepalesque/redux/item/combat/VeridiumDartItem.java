package net.zepalesque.redux.item.combat;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import com.aetherteam.aether.item.combat.DartItem;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.entity.projectile.VeridiumDart;
import org.jetbrains.annotations.Nullable;

public class VeridiumDartItem extends DartItem {

    public VeridiumDartItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractDart createDart(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack firedFromWeapon) {
        if (firedFromWeapon != null && firedFromWeapon.is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS)) {
            return new VeridiumDart(level, shooter, ammo, firedFromWeapon);
        } else {
            return new VeridiumDart.Uninfused(level, shooter, ammo, firedFromWeapon);
        }
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        VeridiumDart.Uninfused dart = new VeridiumDart.Uninfused(ReduxEntities.VERIDIUM_DART.get(), level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
        dart.pickup = AbstractArrow.Pickup.ALLOWED;
        dart.setNoGravity(true);
        return dart;
    }
}
