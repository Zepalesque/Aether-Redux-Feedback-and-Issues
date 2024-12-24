package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ReduxGlowParticle extends TextureSheetParticle {
   static final RandomSource RANDOM = RandomSource.create();
   private final SpriteSet sprites;
   private final boolean animated;

   ReduxGlowParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites, boolean animated) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      this.animated = animated;
      this.friction = 0.96F;
      this.speedUpWhenYMotionIsBlocked = true;
      this.sprites = sprites;
      this.quadSize *= 0.75F;
      this.hasPhysics = false;
      this.setSpriteFromAge(sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float partialTick) {
      float f = ((float)this.age + partialTick) / (float)this.lifetime;
      f = Mth.clamp(f, 0.0F, 1.0F);
      int i = super.getLightColor(partialTick);
      int j = i & 255;
      int k = i >> 16 & 255;
      j += (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.animated) {
         this.setSpriteFromAge(sprites);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Shimmerstar implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Shimmerstar(SpriteSet sprites) {
         this.sprite = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ReduxGlowParticle shimmerstar = new ReduxGlowParticle(level, x, y, z, 0.5D - ReduxGlowParticle.RANDOM.nextDouble(), ySpeed, 0.5D - ReduxGlowParticle.RANDOM.nextDouble(), this.sprite, false);
         shimmerstar.pickSprite(this.sprite);
         shimmerstar.yd *= 0.05F;
         if (xSpeed == 0.0D && zSpeed == 0.0D) {
            shimmerstar.xd *= 0.05F;
            shimmerstar.zd *= 0.05F;
         }
         shimmerstar.setLifetime((int)(8.0D / (level.random.nextDouble() * 0.8D + 0.2D)));
         return shimmerstar;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Lightning implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Lightning(SpriteSet sprites) {
         this.sprite = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         ReduxGlowParticle lightning = new ReduxGlowParticle(level, x, y, z, 0.5D - ReduxGlowParticle.RANDOM.nextDouble(), ySpeed, 0.5D - ReduxGlowParticle.RANDOM.nextDouble(), this.sprite, true);
         lightning.yd *= 0.0125F;
         if (xSpeed == 0.0D && zSpeed == 0.0D) {
            lightning.xd *= 0.0125F;
            lightning.zd *= 0.0125F;
         }
         lightning.setLifetime(10);
         return lightning;
      }
   }

}