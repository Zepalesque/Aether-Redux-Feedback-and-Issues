
package net.zepalesque.redux.entity.projectile;

import com.aetherteam.nitrogen.entity.BossMob;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
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

    private @Nullable Entity cachedSource;
    private @Nullable UUID sourceUUID;
    private @Nullable ArrayList<Entity> cachedHits = new ArrayList<>();
    private @Nullable ArrayList<UUID> hitUUIDs = new ArrayList<>();
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
        this.cachedHits.add(entity);
        this.hitUUIDs.add(entity.getUUID());
    }

    protected void setEmberSource(Entity entity) {
        this.cachedSource = entity;
        this.sourceUUID = entity.getUUID();
    }

    public Entity getEmberSource() {
        if (this.cachedSource != null && !this.cachedSource.isRemoved()) {
            return this.cachedSource;
        } else if (this.sourceUUID != null && this.level() instanceof ServerLevel) {
            this.cachedSource = ((ServerLevel)this.level()).getEntity(this.sourceUUID);
            return this.cachedSource;
        } else {
            return null;
        }
    }

    public ArrayList<Entity> getHits() {
        if (this.cachedHits != null && !this.cachedHits.isEmpty()) {
            return this.cachedHits;
        } else if (this.hitUUIDs != null && !this.hitUUIDs.isEmpty() && this.level() instanceof ServerLevel serverLevel) {
            ArrayList<Entity> collection = new ArrayList<>();
            for (UUID id : this.hitUUIDs) {
                Entity e = serverLevel.getEntity(id);
                if (e != null && !e.isRemoved()) {
                    collection.add(e);
                } else {
                    this.hitUUIDs.remove(id);
                }
            }
            this.cachedHits = collection;
            return this.cachedHits;
        } else {
            return new ArrayList<>();
        }
    }

    protected boolean originatedFrom(Entity entity) {
        return entity.getUUID().equals(this.sourceUUID);
    }

    protected boolean hasHit(Entity entity) {
        return this.hitUUIDs != null && this.hitUUIDs.contains(entity.getUUID());
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
        if (this.sourceUUID != null) {
            compound.putUUID("Source", this.sourceUUID);
        }
        if (this.hitUUIDs != null && !this.hitUUIDs.isEmpty()) {
            CompoundTag hits = new CompoundTag();
            for (int i = 0; i < this.hitUUIDs.size(); i++) {
                UUID id = this.hitUUIDs.get(i);
                hits.putUUID(String.valueOf(i), id);
            }
            compound.put("Hits", hits);
        }
    }
    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.hasUUID("Source")) {
            this.sourceUUID = compound.getUUID("Source");
            this.cachedSource = null;
        }
        if (compound.contains("Hits") && compound.get("Hits") instanceof CompoundTag hits) {
            this.hitUUIDs = new ArrayList<>();
            for (String s : hits.getAllKeys()) {
                this.hitUUIDs.add(hits.getUUID(s));
            }
            this.cachedHits = new ArrayList<>();
        }
    }

     public Vec3 bounceVector(Vec3 velocity, Direction face) {
        Vec3 normal = new Vec3(face.getStepX(), face.getStepY(), face.getStepZ());
        double mult = velocity.x * normal.x + velocity.y * normal.y + velocity.z * normal.z;
        return new Vec3(
                velocity.x - 2 * mult * normal.x,
                velocity.y - 2 * mult * normal.y,
                velocity.z - 2 * mult * normal.z
        );
    }

    public Vec3 bounceAxis(Vec3 velocity, Direction direction) {
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
        }

    }

    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Direction d = result.getDirection();
        Direction.Axis axis = d.getAxis();
        Vec3 loc = result.getLocation();
        Vec3 velocity = this.getDeltaMovement();
        velocity = velocity.multiply(Math.abs(velocity.x)>0.1 ? 1 : 0, Math.abs(velocity.y)>0.1 ? 1 : 0, Math.abs(velocity.z)>0.1 ? 1 : 0);
         Vec3 bounce = this.bounceAxis(velocity, d);
         // Spawn spark particles

        double spread = velocity.length() * 2.5;
        for (int i = 0; i < Mth.floor(velocity.length() * 15); i++) {
            float angle = this.level().getRandom().nextFloat() * 360 * Mth.DEG_TO_RAD;
            // trigonometry, how fun
            double opp = Mth.sin(angle) * spread;
            double adj = Mth.cos(angle) * spread;
            // TODO
            if (axis == Direction.Axis.X) {
                            this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), velocity.length(), opp, adj);
            } else if (axis == Direction.Axis.Y) {
                            this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), opp, velocity.length(), adj);
            } else if (axis == Direction.Axis.Z) {
                            this.level().addParticle(ReduxParticles.SPARK.get(), loc.x(), loc.y(), loc.z(), opp, adj, velocity.length());
            }
        }
        SoundEvent sound =
                velocity.length() <= 0.75 ? ReduxSounds.EMBER_BOUNCE_SMALL.get() :
                        velocity.length() <= 1.5 ? ReduxSounds.EMBER_BOUNCE_MED.get() :
                                ReduxSounds.EMBER_BOUNCE_BIG.get();
        this.level().playSound(null, loc.x(), loc.y(), loc.z(), sound, SoundSource.NEUTRAL, (float) (velocity.length() * 10D), 0.8F + (this.level().random.nextFloat() * 0.4F));
        this.setDeltaMovement(bounce.scale(0.5D));
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

