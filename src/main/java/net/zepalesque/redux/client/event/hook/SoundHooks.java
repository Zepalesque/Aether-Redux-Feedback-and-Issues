package net.zepalesque.redux.client.event.hook;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.zepalesque.redux.config.ReduxConfig;

public class SoundHooks {
    public static boolean shouldNormalizePitch(SoundInstance instance) {
        return instance != null && ReduxConfig.CLIENT.slider_sfx_upgrade.get() && instance.getLocation().equals(AetherSoundEvents.ENTITY_SLIDER_AMBIENT.getId());
    }

}
