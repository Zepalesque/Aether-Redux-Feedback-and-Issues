package net.zepalesque.redux.data.resource.registries;

import com.aetherteam.aether.client.AetherSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.resource.builders.ReduxJukebuxBuilders;

public class ReduxJukeboxSongs extends ReduxJukebuxBuilders {

    public static final ResourceKey<JukeboxSong> SENTIENCE = create("sentience");


    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        register(context, SENTIENCE, (Holder.Reference<SoundEvent>) ReduxSounds.ITEM_MUSIC_DISC_SENTIENCE.getDelegate(), 149, 1);

    }

}
