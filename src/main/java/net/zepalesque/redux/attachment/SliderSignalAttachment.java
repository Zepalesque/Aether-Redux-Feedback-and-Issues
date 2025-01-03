package net.zepalesque.redux.attachment;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import net.minecraft.sounds.SoundSource;
import net.zepalesque.redux.client.audio.ReduxSounds;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SliderSignalAttachment implements INBTSynchable {

    private final Map<String, Triple<INBTSynchable.Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("signal_tick", Triple.of(INBTSynchable.Type.INT, (object) -> this.setSignalTick((int) object), this::getSignalTick)),
            Map.entry("move_trajectory", Triple.of(INBTSynchable.Type.INT, (object) -> this.setMoveTrajectory((int) object), this::getMoveTrajectoryIndex))
    );

    protected int signalTick = 0;

    @Nullable
    protected net.minecraft.core.Direction moveTrajectory = null;


    public void onUpdate(Slider slider) {
        this.tickSignal(slider);
    }

    protected void tickSignal(Slider slider) {
        if (this.signalTick > 0) {
            this.signalTick--;
        }
    }

    public boolean shouldGlow(Slider slider) {
        return this.signalTick == 6 || this.signalTick == 1;
    }

    public void doBeep(Slider slider) {
        if (!slider.level().isClientSide()) {
            this.setSynched(slider.getId(), Direction.NEAR, "signal_tick", 6);;
            slider.level().playSound(null, slider.getX(), slider.getY(), slider.getZ(), ReduxSounds.SLIDER_SIGNAL, SoundSource.HOSTILE, 1F, 1F);
        }
    }

    public void syncMoveDirection(Slider slider) {
        if (!slider.level().isClientSide()) {
            this.setSynched(slider.getId(), Direction.NEAR, "move_trajectory", Optional.ofNullable(slider.getMoveDirection()).map(Enum::ordinal).orElse(-1));
        }
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


    public net.minecraft.core.Direction getMoveTrajectory() {
        return moveTrajectory;
    }

    public int getMoveTrajectoryIndex() {
        return moveTrajectory == null ? -1 : moveTrajectory.ordinal();
    }

    public void setMoveTrajectory(net.minecraft.core.Direction moveTrajectory) {
        this.moveTrajectory = moveTrajectory;
    }

    public void setMoveTrajectory(int index) {
        net.minecraft.core.Direction[] array = net.minecraft.core.Direction.values();
        if (index >= array.length || index < 0) return;
        this.moveTrajectory = net.minecraft.core.Direction.values()[index];
    }


    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return null;
    }

    @Override
    public void setSynched(int entityID, Direction direction, String key, Object value) {
        INBTSynchable.super.setSynched(entityID, direction, key, value);
    }
}
