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


    private static final int STORED_PAST_TICKS = 5;
    protected final double[] xPast = new double[STORED_PAST_TICKS], yPast = new double[STORED_PAST_TICKS], zPast = new double[STORED_PAST_TICKS];

    SparkParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z, 0.0D, 0.0D, 0.0D);
        Arrays.fill(this.xPast, x);
        Arrays.fill(this.yPast, y);
        Arrays.fill(this.zPast, z);
        this.gravity = 1.0F;
        this.friction = 1F;
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
        velocity = velocity.multiply(Math.abs(velocity.x) > Ember.VELOCITY_THRESHOLD_XZ ? 1 : 0, Math.abs(velocity.y) > Ember.VELOCITY_THRESHOLD_Y ? 1 : 0, Math.abs(velocity.z) > Ember.VELOCITY_THRESHOLD_XZ ? 1 : 0);
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
        calculatePastPos(this.xPast, this.xo);
        calculatePastPos(this.yPast, this.yo);
        calculatePastPos(this.zPast, this.zo);


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
    protected void renderRotatedQuad(VertexConsumer buffer, Camera camera, Quaternionf quaternion, float partialTicks) {
        super.renderRotatedQuad(buffer, camera, quaternion, partialTicks);

        for (int i = 0; i < STORED_PAST_TICKS; i++) {
            float x = (float) Mth.lerp(partialTicks, i == 0 ? this.xo : this.xPast[i - 1], this.xPast[i]);
            float y = (float) Mth.lerp(partialTicks, i == 0 ? this.yo : this.yPast[i - 1], this.yPast[i]);
            float z = (float) Mth.lerp(partialTicks, i == 0 ? this.zo : this.zPast[i - 1], this.zPast[i]);
            float alphamult = 1 - Mth.inverseLerp(i, 0, STORED_PAST_TICKS);
            float alpha = this.alpha * (alphamult);
            this.renderRotatedQuad(buffer, quaternion, x, y, z, partialTicks, alpha);
        }
    }

    protected void renderRotatedQuad(VertexConsumer buffer, Quaternionf quaternion, float x, float y, float z, float partialTicks, float alpha) {
        float ao = this.alpha;
        this.alpha = alpha;
        super.renderRotatedQuad(buffer, quaternion, x, y, z, partialTicks);
        this.alpha = ao;
    }

    private void calculatePastPos(double[] prevPos, double original) {
        for (int j = prevPos.length - 2; j > 0; j--) {
            double d = prevPos[j];
            prevPos[j + 1] = d;
        }
        prevPos[0] = original;
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

            this.onGround = y1 != y && y1 < 0.0;
            if (x1 != x) {
                this.xd *= 0.75;
            }

            if (z1 != z) {
                this.zd *= 0.75;
            }
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