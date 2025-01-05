
package net.zepalesque.redux.entity.projectile;

import com.aetherteam.nitrogen.entity.BossMob;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.entity.ReduxEntities;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

// TODO: Rewrite from scratch
public class Ember extends Projectile {
    public Ember(EntityType<? extends Ember> entityType, Level level) {
        super(entityType, level);
    }

    public static final double VELOCITY_THRESHOLD_XZ = 0.075D;
    public static final double VELOCITY_THRESHOLD_Y = 0.05D;
    public static final double BOUNCE_FRICTION_XZ = 0.75D;
    public static final double BOUNCE_FRICTION_Y = 0.7D;

    private @Nullable UUID source;
    private final ArrayList<UUID> hitEntities = new ArrayList<>();
    public final int lifetime = 80;
    public Ember(Level level, Player owner) {
        this(ReduxEntities.EMBER.get(), level);
        this.setOwner(owner);
    }

    public Ember(Level level, Player owner, Entity source) {
        this(level, owner);
        this.setEmberSource(source);

    }

    protected void hit(Entity entity) {
        this.hitEntities.add(entity.getUUID());
    }

    protected void setEmberSource(Entity entity) {
        this.source = entity.getUUID();
    }

    protected boolean originatedFrom(Entity entity) {
        return entity.getUUID().equals(this.source);
    }

    protected boolean hasHit(Entity entity) {
        return this.hitEntities != null && this.hitEntities.contains(entity.getUUID());
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    /**
     * Called to update the entity's position/logic.
     */

    public void tick() {
        super.tick();
        Vec3 vec3 = this.getDeltaMovement();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;
        this.updateRotation();

        this.setDeltaMovement(vec3.multiply(0.999D, 0.99D, 0.999D));
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult))
            this.onHit(hitresult);
        if (!this.isNoGravity() && hitresult.getType() != HitResult.Type.BLOCK) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D, 0.0D));
        }
        if (hitresult.getType() == HitResult.Type.MISS) {
            this.setPos(d0, d1, d2);
        }
        if (this.tickCount >= this.lifetime && !this.isRemoved()) {
            this.remove(RemovalReason.DISCARDED);
        }
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        if (this.source != null) {
            compound.putUUID("Source", this.source);
        }
        if (this.hitEntities != null && !this.hitEntities.isEmpty()) {
            CompoundTag hits = new CompoundTag();
            for (int i = 0; i < this.hitEntities.size(); i++) {
                UUID id = this.hitEntities.get(i);
                hits.putUUID(String.valueOf(i), id);
            }
            compound.put("Hits", hits);
        }
    }
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("Source")) {
            this.source = compound.getUUID("Source");
        }
        if (compound.contains("Hits") && compound.get("Hits") instanceof CompoundTag hits) {
            for (String s : hits.getAllKeys()) {
                this.hitEntities.add(hits.getUUID(s));
            }
        }
    }

     public static Vec3 bounceVector(Vec3 velocity, Vec3 normal) {
        double mult = velocity.x * normal.x + velocity.y * normal.y + velocity.z * normal.z;
        return new Vec3(
                velocity.x - 2 * mult * normal.x,
                velocity.y - 2 * mult * normal.y,
                velocity.z - 2 * mult * normal.z
        );
    }

    public static Vec3 bounceAxis(Vec3 velocity, Direction direction) {
        Direction.Axis axis = direction.getAxis();
        double x = velocity.x;
        double y = velocity.y;
        double z = velocity.z;

        if (axis == Direction.Axis.X) {
            x = -x;
        } else if (axis == Direction.Axis.Y) {
            y = -y;
        } else if (axis == Direction.Axis.Z) {
            z = -z;
        }

        return new Vec3(x, y, z);
    }

    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (result.getEntity() instanceof LivingEntity livingentity && !this.ownedBy(livingentity) && !this.originatedFrom(livingentity) && !this.hasHit(livingentity) && !(livingentity instanceof BossMob<?>)) {
            // TODO
//         livingentity.hurt(ReduxDamageTypes.entitySource(this.level(), ReduxDamageTypes.EMBER, this.getOwner()), 1.0F);
            this.hit(livingentity);
        }

    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Direction d = result.getDirection();
        Direction.Axis axis = d.getAxis();
        Vec3 loc = result.getLocation();
        Vec3 velocity = this.getDeltaMovement();
        velocity = velocity.multiply(Math.abs(velocity.x) > VELOCITY_THRESHOLD_XZ ? 1 : 0, Math.abs(velocity.y) > VELOCITY_THRESHOLD_Y ? 1 : 0, Math.abs(velocity.z) > VELOCITY_THRESHOLD_XZ ? 1 : 0);
         Vec3 bounce = bounceAxis(velocity, d);

        // Spawn spark particles
        double spread = velocity.length() * 2.5;
        for (int i = 0; i < Mth.floor(velocity.length() * 15); i++) {
            float angle = this.level().getRandom().nextFloat() * 2 * Mth.PI;
            // trigonometry, how fun
            double opp = Mth.sin(angle) * spread;
            double adj = Mth.cos(angle) * spread;
            if (axis == Direction.Axis.X) {
                            this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), velocity.length(), opp, adj);
            } else if (axis == Direction.Axis.Y) {
                            this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), opp, velocity.length(), adj);
            } else if (axis == Direction.Axis.Z) {
                            this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), opp, adj, velocity.length());
            }
        }
        SoundEvent sound;
        if (velocity.length() <= 0.75) sound = ReduxSounds.EMBER_BOUNCE_SMALL.get();
        else if (velocity.length() <= 1.5) sound = ReduxSounds.EMBER_BOUNCE_MEDIUM.get();
        else sound = ReduxSounds.EMBER_BOUNCE_BIG.get();
        this.level().playSound(null, loc.x(), loc.y(), loc.z(), sound, SoundSource.NEUTRAL, (float) (velocity.length() * 10D), 0.8F + (this.level().random.nextFloat() * 0.4F));

        Vec3 scaled = bounce.multiply(Ember.BOUNCE_FRICTION_XZ, Ember.BOUNCE_FRICTION_Y, Ember.BOUNCE_FRICTION_XZ);
        this.setDeltaMovement(scaled);
        this.setPos(loc);
    }

    @Override
    public void recreateFromPacket(ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        double d0 = packet.getXa();
        double d1 = packet.getYa();
        double d2 = packet.getZa();

        this.setDeltaMovement(d0, d1, d2);
    }
}

