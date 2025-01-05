package net.zepalesque.redux.client.audio;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Redux.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> EMBER_BOUNCE_BIG = register("entity.ember.bounce_big");
    public static final DeferredHolder<SoundEvent, SoundEvent> EMBER_BOUNCE_MED = register("entity.ember.bounce_med");
    public static final DeferredHolder<SoundEvent, SoundEvent> EMBER_BOUNCE_SMALL = register("entity.ember.bounce_small");

    public static final DeferredHolder<SoundEvent, SoundEvent> AERJUMP = register("item.aerbound_cape.aerjump");
    public static final DeferredHolder<SoundEvent, SoundEvent> INFUSE_ITEM = register("item.generic.infuse");
    public static final DeferredHolder<SoundEvent, SoundEvent> INFUSION_EXPIRE = register("item.generic.infusion_expire");
    public static final DeferredHolder<SoundEvent, SoundEvent> ITEM_MUSIC_DISC_SENTIENCE = register("item.music_disc.sentience");

    public static final DeferredHolder<SoundEvent, SoundEvent> LOGICATOR_CLICK = register("block.logicator.click");

    public static final DeferredHolder<SoundEvent, SoundEvent> SLIDER_SIGNAL = register("entity.slider.signal");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String location) {
        return SOUNDS.register(location, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Redux.MODID, location)));
    }
}
