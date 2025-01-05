package net.zepalesque.redux.attachment;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.network.packet.SliderSignalPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// Client-Side
public class SliderSignalAttachment {

    protected int signalTick = 0;

    @Nullable
    protected Direction overrideDirection = null;

    @Nullable
    protected Entity target = null;


    public void onUpdate(Slider slider) {
        this.tickSignal(slider);
    }

    protected void tickSignal(Slider slider) {
        if (this.signalTick > 0 && slider.level().isClientSide()) {
            if (this.signalTick == 2) playSound(slider);
            else if (this.signalTick == 1) this.overrideDirection = null;
            this.signalTick--;
        }
    }

    public boolean shouldGlow(Slider slider) {
        return this.signalTick == 8 || this.signalTick == 7 || this.signalTick == 2 || this.signalTick == 1;
    }

    public static void sendSignal(Slider slider) {
        PacketDistributor.sendToPlayersNear(
                (ServerLevel) slider.level(), null,
                slider.getX(), slider.getY(), slider.getZ(), 50D,
                new SliderSignalPacket.Signal(
                        slider.getId()
                ));
    }

    public static void syncDirection(Slider slider, Direction direction) {
        PacketDistributor.sendToPlayersNear(
                (ServerLevel) slider.level(), null,
                slider.getX(), slider.getY(), slider.getZ(), 50D,
                new SliderSignalPacket.DirectionOverride(
                        slider.getId(), direction
                ));
    }

    public static void syncTarget(Slider slider, Entity target) {
        PacketDistributor.sendToPlayersNear(
                (ServerLevel) slider.level(), null,
                slider.getX(), slider.getY(), slider.getZ(), 50D,
                new SliderSignalPacket.SyncTarget(
                        slider.getId(),
                        Optional.ofNullable(target).map(Entity::getId)
                ));
    }

    public void beginSignal(Slider slider) {
        if (this.getSignalTick() <= 2) {
            this.overrideDirection = null;
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

    public void setOverrideDirection(Slider entity, Direction direction) {
        this.overrideDirection = direction;
    }

    public Direction getOverrideDirection(Slider entity) {
        return this.overrideDirection;
    }

    public void setTarget(Slider slider, @Nullable Entity entity) {
        this.target = entity;
    }

    @Nullable
    public Entity getTarget(Slider slider) {
        return this.target;
    }
}
