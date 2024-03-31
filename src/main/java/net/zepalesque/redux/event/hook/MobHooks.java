
package net.zepalesque.redux.event.hook;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.entity.monster.Zephyr;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.entity.passive.Phyg;
import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.capability.animation.sentry.SentryAnimation;
import net.zepalesque.redux.capability.animation.sentry.battle.BattleSentryAnimation;
import net.zepalesque.redux.capability.aprilfools.WackyBat;
import net.zepalesque.redux.capability.cockatrice.CockatriceExtension;
import net.zepalesque.redux.capability.living.ReduxLiving;
import net.zepalesque.redux.capability.living.VampireAmulet;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.entity.ai.goal.CockatriceMeleeAttackGoal;
import net.zepalesque.redux.entity.ai.goal.CockatriceRangedStrafeGoal;
import net.zepalesque.redux.entity.ai.target.HurtByOtherTypeTargetGoal;
import net.zepalesque.redux.entity.misc.Rebux;

public class MobHooks {

    public static void wackyBatAI(LivingEntity entity) {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
    }

    public static void modifyCockatriceAI(Cockatrice cockatrice) {
        if (ReduxConfig.COMMON.cockatrice_burn_in_daylight.get()) {
            cockatrice.goalSelector.addGoal(2, new RestrictSunGoal(cockatrice));
            cockatrice.goalSelector.addGoal(3, new FleeSunGoal(cockatrice, 1.0D));
        }
        cockatrice.goalSelector.addGoal(1, new CockatriceMeleeAttackGoal(cockatrice, 1.5, false));
        final WrappedGoal[] toRemove = new WrappedGoal[1];
        final WrappedGoal[] removeRanged = new WrappedGoal[1];
        cockatrice.targetSelector.getAvailableGoals().forEach((goal) -> {
            if ((goal.getGoal().getClass().equals(HurtByTargetGoal.class) && goal.getPriority() == 1)) {
                toRemove[0] = goal;}});
        if (toRemove[0] != null) {
            cockatrice.targetSelector.getAvailableGoals().removeIf((wrappedGoal -> wrappedGoal == toRemove[0]));
            cockatrice.targetSelector.addGoal(1, new HurtByOtherTypeTargetGoal(cockatrice));
        }            cockatrice.goalSelector.addGoal(1, new CockatriceMeleeAttackGoal(cockatrice, 1.5, false));

        cockatrice.goalSelector.getAvailableGoals().forEach((goal) -> {
            if (goal.getGoal().getClass().equals(RangedAttackGoal.class) && goal.getPriority() == 2) {
                removeRanged[0] = goal;}});
        if (toRemove[0] != null) {
            cockatrice.goalSelector.getAvailableGoals().removeIf((wrappedGoal -> wrappedGoal == removeRanged[0]));
            cockatrice.goalSelector.addGoal(2, new CockatriceRangedStrafeGoal(cockatrice, 1.0, 60, 10.0F));
        }
    }

    public static void modifyZephyrAI(Zephyr zephyr) {
        zephyr.targetSelector.getAvailableGoals().removeIf((wrappedGoal -> wrappedGoal.getGoal() instanceof NearestAttackableTargetGoal));

//        zephyr.goalSelector.getAvailableGoals().forEach((goal) -> {
//            if (goal.getGoal().getClass().equals(Zephyr.ZephyrShootSnowballGoal.class) && goal.getPriority() == 2) {
//                removeRanged[0] = goal;}});
//        if (toRemove[0] != null) {
//            cockatrice.goalSelector.getAvailableGoals().removeIf((wrappedGoal -> wrappedGoal == removeRanged[0]));
//            cockatrice.goalSelector.addGoal(2, new CockatriceRangedStrafeGoal(cockatrice, 1.0, 60, 10.0F));
//        }
    }

    public static void modifyEntityQuicksoil(final LivingEntity entity)
    {
        entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.7D, 1D, 1.7D));
        final double maxMotion = 1D;
        double x;
        double z;

        x = Mth.clamp(entity.getDeltaMovement().x, -maxMotion, maxMotion);
        z = Mth.clamp(entity.getDeltaMovement().z, -maxMotion, maxMotion);
        entity.setDeltaMovement(x, entity.getDeltaMovement().y, z);
    }

    public static void updateCapabilities(LivingEntity living)
    {
        if (living instanceof FlyingCow || living instanceof Phyg)
        {
            WackyBat.get(living).ifPresent(WackyBat::tick);
        }
        if (living instanceof Cockatrice cockatrice)
        {
            CockatriceExtension.get(cockatrice).ifPresent(CockatriceExtension::tick);
        }
        if (living instanceof Moa moa && moa.level().isClientSide())
        {
            MoaAnimation.get(moa).ifPresent(MoaAnimation::tick);
        }
        if (living instanceof Player player)
        {
            ReduxPlayer.get(player).ifPresent(ReduxPlayer::tick);
        }
        if (living instanceof Sentry sentry && sentry.level().isClientSide())
        {
            SentryAnimation.get(sentry).ifPresent(SentryAnimation::tick);
        }
        if (living instanceof Mimic mimic && mimic.level().isClientSide())
        {
            MimicAnimation.get(mimic).ifPresent(MimicAnimation::tick);
        }
        if (Redux.aetherGenesisCompat() && living instanceof BattleSentry battleSentry && battleSentry.level().isClientSide())
        {
            BattleSentryAnimation.get(battleSentry).ifPresent(BattleSentryAnimation::tick);
        }
        VampireAmulet.get(living).ifPresent(VampireAmulet::tick);
        ReduxLiving.get(living).ifPresent(ReduxLiving::tick);
    }




    public static void createRebux(LivingEntity entity) {
        float hp = entity.getMaxHealth() + entity.getRandom().nextInt(10);
        int total = Mth.floor(hp / 5);
        int coinSize = (int) Math.max(total / 4F, 1);
        int coinCount = Mth.floor(total / (double) coinSize);
        for (int i = 0; i < coinCount; i++) {
            spawnRebux(entity.level(), coinSize, entity.position(), new Vec3((entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0, entity.getRandom().nextDouble() * 0.2 * 2.0, (entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0));
        }
        int remainder = total % coinCount;
        if (remainder > 0) {
            spawnRebux(entity.level(), remainder, entity.position(), new Vec3((entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0, entity.getRandom().nextDouble() * 0.2 * 2.0, (entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0));
        }
    }

    public static void createPlayerRebux(Player entity) {
        ReduxPlayer.get(entity).ifPresent(reduxPlayer -> {
            int total = reduxPlayer.rebuxCount();
            int coinSize = (int) Math.max(total / 4F, 1);
            int coinCount = Mth.floor(total / (double) coinSize);
            for (int i = 0; i < coinCount; i++) {
                spawnRebux(entity.level(), coinSize, entity.position(), new Vec3((entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0, entity.getRandom().nextDouble() * 0.2 * 2.0, (entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0));
            }
            int remainder = total % coinCount;
            if (remainder > 0) {
                spawnRebux(entity.level(), remainder, entity.position(), new Vec3((entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0, entity.getRandom().nextDouble() * 0.2 * 2.0, (entity.getRandom().nextDouble() * 0.2 - 0.1) * 2.0));
            }
        });
    }

    public static void spawnRebux(Level lvl, int value, Vec3 pos, Vec3 movement) {
        Rebux rebux = new Rebux(lvl, pos.x, pos.y, pos.z, value);
        rebux.setDeltaMovement(movement);
        lvl.addFreshEntity(rebux);
    }
}

