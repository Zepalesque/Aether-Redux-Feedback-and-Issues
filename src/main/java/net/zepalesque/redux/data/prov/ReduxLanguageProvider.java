package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zepalesque.unity.data.prov.UnityLanguageProvider;
import net.zepalesque.zenith.api.data.DatagenUtil;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ReduxLanguageProvider extends UnityLanguageProvider {
    public ReduxLanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void addJukeboxSong(ResourceKey<JukeboxSong> songName, String name) {
        this.addJukeboxSong(songName.location().getPath(), name);
    }
}
