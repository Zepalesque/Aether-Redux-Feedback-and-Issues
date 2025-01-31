package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.prov.ReduxSoundsProvider;
import net.zepalesque.zenith.api.data.DatagenUtil;

public class ReduxSoundsData extends ReduxSoundsProvider {
    public ReduxSoundsData(PackOutput output, ExistingFileHelper helper) {
        super(output, Redux.MODID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(ReduxSounds.INFUSE_ITEM, sound ->
                definition().with(sound("aether_redux:item/generic/infuse_item"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));
        this.add(ReduxSounds.INFUSION_EXPIRE, sound ->
                definition().with(sound("aether_redux:item/generic/infusion_expire"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));
        this.add(ReduxSounds.LOGICATOR_CLICK, sound ->
                definition().with(sound("random/click"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));
        this.add(ReduxSounds.AERJUMP, sound ->
                definition().with(sound("aether_redux:item/aerbound_cape/aerjump"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));
        this.add(ReduxSounds.SLIDER_SIGNAL, sound ->
                definition().with(sound("aether_redux:entity/slider/signal"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));

        this.add(ReduxSounds.EMBER_BOUNCE_BIG,  sound ->
                definition().with(sound("aether_redux:entity/ember/bounce_big"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));

        this.add(ReduxSounds.EMBER_BOUNCE_MEDIUM, sound ->
                definition().with(sound("aether_redux:entity/ember/bounce_medium"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));

        this.add(ReduxSounds.EMBER_BOUNCE_SMALL,  sound ->
                definition().with(sound("aether_redux:entity/ember/bounce_small"))
                        .subtitle(DatagenUtil.subtitleFor(sound)));


        this.add(ReduxSounds.ITEM_MUSIC_DISC_SENTIENCE, sound ->
                definition().with(sound("aether_redux:item/records/sentience").stream()));
    }
}
