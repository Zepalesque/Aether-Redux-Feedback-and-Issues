package net.zepalesque.redux.client.audio;

import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.Nullable;

public class NormalizedSoundInstance implements SoundInstance {

    private final SoundInstance inner;
    private final int flags;

    private static final int[] powers = { 0b1, 0b10, 0b100, 0b1000, 0b10000, 0b100000, 0b1000000 };


    /*
     * FLAGS (Add each number for the flags you want to override with the default value of)
     * 1: getSource
     * 2: isLooping
     * 4: isRelative
     * 8: getDelay
     * 16: getVolume
     * 32: getPitch
     * 64: getAttenuation
     *
     */
    public NormalizedSoundInstance(SoundInstance inner, int flags) {

        this.inner = inner;
        this.flags = flags;
    }

    private boolean getFlag(int index) {
        return (flags & powers[index]) != 0;
    }



    @Override
    public ResourceLocation getLocation() {
        return null;
    }

    @Nullable
    @Override
    public WeighedSoundEvents resolve(SoundManager manager) {
        return inner.resolve(manager);
    }

    @Override
    public Sound getSound() {
        return inner.getSound();
    }

    @Override
    public SoundSource getSource() {
        return getFlag(0) ? SoundSource.MASTER : inner.getSource();
    }

    @Override
    public boolean isLooping() {
        return !getFlag(1) && inner.isLooping();
    }

    @Override
    public boolean isRelative() {
        return !getFlag(2) && inner.isRelative();
    }

    @Override
    public int getDelay() {
        return getFlag(3) ? 0 : inner.getDelay();
    }

    @Override
    public float getVolume() {
        return getFlag(4) ? 10F : inner.getVolume();
    }

    @Override
    public float getPitch() {
        return getFlag(5) ? 1F : inner.getPitch();
    }

    @Override
    public double getX() {
        return inner.getX();
    }

    @Override
    public double getY() {
        return inner.getY();
    }

    @Override
    public double getZ() {
        return inner.getZ();
    }

    @Override
    public Attenuation getAttenuation() {
        return getFlag(6) ? Attenuation.LINEAR : inner.getAttenuation();
    }
}
