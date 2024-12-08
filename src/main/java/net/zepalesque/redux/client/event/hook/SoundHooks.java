package net.zepalesque.redux.client.event.hook;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.zepalesque.redux.config.ReduxConfig;

public class SoundHooks {
    public static boolean shouldNormalizePitch(SoundInstance instance) {
        return ReduxConfig.CLIENT.slider_sfx.get() && instance.getLocation().equals(AetherSoundEvents.ENTITY_SLIDER_AMBIENT.getId());
    }

}
