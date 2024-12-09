package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import net.zepalesque.redux.client.audio.NormalizedSoundInstance;
import net.zepalesque.redux.client.event.hook.SoundHooks;

@EventBusSubscriber(Dist.CLIENT)
public class SoundListener {

    @SubscribeEvent
    public static void onPlaySound(PlaySoundEvent event) {
        if (SoundHooks.shouldNormalizePitch(event.getSound())) {
            SoundInstance normalized = new NormalizedSoundInstance(event.getSound(), 32);
            event.setSound(normalized);
        }
    }
}
