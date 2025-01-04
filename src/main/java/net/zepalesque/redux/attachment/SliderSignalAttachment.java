package net.zepalesque.redux.attachment;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.network.packet.SliderSignalPacket;
import org.jetbrains.annotations.NotNull;

// Client-Side
public class SliderSignalAttachment {

    protected int signalTick = 0;

    public void onUpdate(Slider slider) {
        this.tickSignal(slider);
    }

    protected void tickSignal(Slider slider) {
        if (this.signalTick > 0 && slider.level().isClientSide()) {
            if (this.signalTick == 2) playSound(slider);
            this.signalTick--;
        }
    }

    public boolean shouldGlow(Slider slider) {
        return this.signalTick == 8 || this.signalTick == 7 || this.signalTick == 2 || this.signalTick == 1;
    }

    public static void sendPacket(Slider slider) {
        PacketDistributor.sendToPlayersNear((ServerLevel) slider.level(), null, slider.getX(), slider.getY(), slider.getZ(), 50D, new SliderSignalPacket(slider.getId()));
    }

    public void doBeep(Slider slider) {
        if (this.getSignalTick() <= 2) {
            this.setSignalTick(8);
            playSound(slider);
        }
    }

    protected void playSound(Slider slider) {
        if (ReduxConfig.CLIENT.slider_signal_sfx.get())
            slider.level().playSound(Minecraft.getInstance().player, slider.getX(), slider.getY(), slider.getZ(), ReduxSounds.SLIDER_SIGNAL, SoundSource.HOSTILE, 1F, 1F);
    }

    public static @NotNull SliderSignalAttachment get(@NotNull Slider slider) {
        return slider.getData(ReduxDataAttachments.SLIDER_SIGNAL.get());
    }

    public int getSignalTick() {
        return signalTick;
    }

    public void setSignalTick(int signalTick) {
        this.signalTick = signalTick;
    }
}
