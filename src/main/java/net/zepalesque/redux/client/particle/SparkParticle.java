package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SparkParticle extends TextureSheetParticle {
   SparkParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
      super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
      this.gravity = 1.0F;
      this.friction = 0.995F;
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

   public void tick() {
      Vec3 velocity = new Vec3(this.xd, this.yd, this.zd);
      Vec3 pos = new Vec3(this.x, this.y, this.z);
      velocity = velocity.multiply(Math.abs(velocity.x)>0.1 ? 1 : 0, Math.abs(velocity.y)>0.1 ? 1 : 0, Math.abs(velocity.z)>0.1 ? 1 : 0);
      HitResult hitresult = getHitResult(pos, velocity.length() == 0 ? velocity.add(0, -0.04, 0) : velocity, this.level);
      if (velocity.length() > 0D && hitresult.getType() == HitResult.Type.BLOCK) {
         Vec3 bounce = this.bounceAxis(velocity, ((BlockHitResult)hitresult).getDirection());
         Vec3 scaled = bounce.scale(0.5D);
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
      super.tick();

      int i = 10;
      if (this.lifetime - this.age < i) {
         this.alpha = (float)(this.lifetime - this.age) / i;
      }

   }
   private static HitResult getHitResult(Vec3 startVec, Vec3 endVecOffset, Level level) {
      Vec3 vec3 = startVec.add(endVecOffset);
      return level.clip(new ClipContext(startVec, vec3, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, (Entity) null));
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