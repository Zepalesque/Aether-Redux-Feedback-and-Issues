package net.zepalesque.redux.capability.player;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.INBTSerializable;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.client.particle.ReduxParticleTypes;

public class BlightshadeModule implements INBTSerializable {

    private int blightshadeCooldown;
    private int blightshadeEffectCooldown;
    private final Player player;

    public BlightshadeModule(Player plr) {
        this.player = plr;
    }

    public void tick() {
        if (this.blightshadeCooldown > 0)
        {
            this.blightshadeCooldown--;
        }
        if (this.blightshadeEffectCooldown > 0)
        {
            if (this.blightshadeEffectCooldown == 1)
            {
                this.player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 210, 0));
            }
            this.blightshadeEffectCooldown--;
        }

    }

    public int getBlightshadeCooldown() {
        return this.blightshadeCooldown;
    }

    public boolean blightshade(BlockPos pos, AABB bounds) {
        if (!this.player.level.isClientSide()) {
            if (this.blightshadeCooldown > 0) {
                return false;
            } else {
                this.blightshadeCooldown = 100;
                this.doParticles(bounds);
                this.player.level.playSound(null, pos, ReduxSoundEvents.BLIGHTSHADE_SPRAY.get(), SoundSource.BLOCKS, 0.8F, 0.9F + this.player.level.random.nextFloat() * 0.2F);
                this.blightshadeEffectCooldown = 10;
                return true;
            }
        } else { return false; }
    }

    public void doParticles(AABB bounds) {
        RandomSource rand = this.player.getRandom();
        AABB shrunk = bounds.inflate(-0.1);
        int count = rand.nextInt(50) + 75;
        for (int i = 0; i < count; i++) {
            double x = shrunk.minX + (rand.nextDouble() * shrunk.getXsize());
            double y = shrunk.minY + (shrunk.getYsize() * 0.25) + (rand.nextDouble() * shrunk.getYsize() * 0.75);
            double z = shrunk.minZ + (rand.nextDouble() * shrunk.getZsize());
            this.player.level.addParticle(ReduxParticleTypes.BLIGHTSHADE.get(), x, y, z, 0.05, 0.05, 0.05);
        }
    }




    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("cooldown", this.blightshadeCooldown);
        tag.putInt("effect_cooldown", this.blightshadeEffectCooldown);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof CompoundTag tag) {
            this.blightshadeCooldown = tag.getInt("cooldown");
            this.blightshadeEffectCooldown = tag.getInt("effect_cooldown");
        }
    }
}
