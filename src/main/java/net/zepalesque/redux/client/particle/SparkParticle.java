package net.zepalesque.redux.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.zepalesque.redux.entity.projectile.Ember;
import org.joml.Quaternionf;

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SparkParticle extends TextureSheetParticle {

    SparkParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        this.gravity = 1F;
//        this.hasPhysics = false;
        this.friction = 0.999F;
        this.xd *= 0.8F;
        this.yd *= 0.8F;
        this.zd *= 0.8F;
        this.quadSize = 0.125F;
        this.scale((random.nextFloat() * 0.2F) + 0.4F);
        this.lifetime = (int)(32.0D / (Math.random() * 0.6D + 0.4D));
    }


    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public int getLightColor(float pPartialTick) {
        return 15728880;
    }

    public void tick() {
        Vec3 velocity = new Vec3(this.xd, this.yd, this.zd);
        Vec3 pos = new Vec3(this.x, this.y, this.z);
        velocity = velocity.multiply(1D, Math.abs(velocity.y) > Ember.VELOCITY_THRESHOLD_Y ? 1 : 0, 1D);
        HitResult hitresult = getHitResult(pos, velocity.length() == 0 ? velocity.add(0, -0.04, 0) : velocity, this.level);
        if (velocity.length() > 0D && hitresult.getType() == HitResult.Type.BLOCK) {
            Vec3 bounce = Ember.bounceAxis(velocity, ((BlockHitResult)hitresult).getDirection());
            Vec3 scaled = bounce.multiply(Ember.BOUNCE_FRICTION_XZ, Ember.BOUNCE_FRICTION_Y, Ember.BOUNCE_FRICTION_XZ);
            this.xd = scaled.x;
            this.yd = scaled.y;
            this.zd = scaled.z;
            double x = hitresult.getLocation().x;
            double y = hitresult.getLocation().y;
            double z = hitresult.getLocation().z;
            this.setPos(x, y, z);
            this.gravity = 0.0F;
        } else {
            this.gravity = 1.0F;
        }

        this.baseTick();

        int i = 10;
        if (this.lifetime - this.age < i) {
            this.alpha = (float)(this.lifetime - this.age) / i;
        }
    }

    protected void baseTick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd = this.yd - 0.04 * (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                this.xd *= 1.1;
                this.zd *= 1.1;
            }

            this.xd = this.xd * (double)this.friction;
            this.yd = this.yd * (double)this.friction;
            this.zd = this.zd * (double)this.friction;
        }
    }

    @Override
    public void move(double x, double y, double z) {
        if (!this.stoppedByCollision) {
            double x1 = x;
            double y1 = y;
            double z1 = z;
            if (this.hasPhysics
                    && (x != 0.0 || y != 0.0 || z != 0.0)
                    && x * x + y * y + z * z < MAXIMUM_COLLISION_VELOCITY_SQUARED) {
                Vec3 vec3 = Entity.collideBoundingBox(null, new Vec3(x, y, z), this.getBoundingBox(), this.level, List.of());
                x = vec3.x;
                y = vec3.y;
                z = vec3.z;
            }

            if (x != 0.0 || y != 0.0 || z != 0.0) {
                this.setBoundingBox(this.getBoundingBox().move(x, y, z));
                this.setLocationFromBoundingbox();
            }

            if (Math.abs(y1) >= 1.0E-5F && Math.abs(y) < 1.0E-5F) {
                this.stoppedByCollision = true;
            }

            this.onGround = y1 != y && y1 < 0.0;
            /*if (x1 != x) {
                this.xd *= 0.99;
            }

            if (z1 != z) {
                this.zd *= 0.99;
            }*/
        }
    }


    private static HitResult getHitResult(Vec3 startVec, Vec3 endVecOffset, Level level) {
        Vec3 vec3 = startVec.add(endVecOffset);
        return level.clip(new ClipContext(startVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, CollisionContext.empty()));
    }


    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;


        public Provider(SpriteSet pSprites) {
            this.sprite = pSprites;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SparkParticle particle = new SparkParticle(pLevel, pX, pY, pZ);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }


}