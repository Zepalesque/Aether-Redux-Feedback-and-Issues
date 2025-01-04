package net.zepalesque.redux.attachment;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.network.packet.SliderSignalSyncPacket;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;
import oshi.util.tuples.Quintet;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SliderSignalAttachment implements INBTSynchable {

    private final Map<String, Triple<INBTSynchable.Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("signal_tick", Triple.of(INBTSynchable.Type.INT, (object) -> this.setSignalTick((int) object), this::getSignalTick)),
            Map.entry("move_direction_ordinal", Triple.of(INBTSynchable.Type.INT, (object) -> this.setMoveDirection((int) object), this::getMoveDirectionIndex))
    );

    protected int signalTick = 0, moveDirIndex = -1;



    public void onUpdate(Slider slider) {
        this.tickSignal(slider);
    }

    protected void tickSignal(Slider slider) {
        if (this.signalTick > 0) {
            if (this.shouldGlow(slider) && (this.moveDirIndex != -1 || slider.isCritical()))
                slider.level().playSound(null, slider.getX(), slider.getY(), slider.getZ(), ReduxSounds.SLIDER_SIGNAL, SoundSource.HOSTILE, 1F, 1F);
            this.signalTick--;
        } else if (this.moveDirIndex != -1) {
            this.moveDirIndex = -1;
        }
    }

    public boolean shouldGlow(Slider slider) {
        return this.signalTick == 7 || this.signalTick == 1;
    }

    public void doBeep(Slider slider) {
        if (!slider.level().isClientSide() && this.getSignalTick() == 0) {
            this.setSynched(slider.getId(), Direction.NEAR, "signal_tick", 8, extraForNear(slider));
        }
    }

    public static Quintet<Double, Double, Double, Double, ServerLevel> extraForNear(Slider slider) {
        return new Quintet<>(slider.getX(), slider.getY(), slider.getZ(), 50D, (ServerLevel) slider.level());
    }


    public void syncMoveDirection(Slider slider) {
        if (!slider.level().isClientSide()) {
            Vec3 targetPoint = this.targetVector(slider);
            if (targetPoint != null) {
                int dir = this.getMoveDirection(slider, targetPoint);
                this.setSynched(slider.getId(), Direction.NEAR, "move_direction_ordinal", dir, extraForNear(slider));
            }
        }
    }

    private int getMoveDirection(Slider slider, Vec3 targetPoint) {
        net.minecraft.core.Direction moveDir = slider.getMoveDirection();

        if (moveDir == null) { // Checks if the direction has changed.
            double x = targetPoint.x - slider.getX();
            double y = targetPoint.y - slider.getY();
            double z = targetPoint.z - slider.getZ();
            moveDir = Slider.calculateDirection(x, y, z);
        }
        return moveDir.ordinal();
    }

    private Vec3 targetVector(Slider slider) {
        LivingEntity target = slider.getTarget();
        return target == null ? null : target.position();
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



    public int getMoveDirectionIndex() {
        return this.moveDirIndex;
    }

    public void setMoveDirection(int ordinal) {
        this.moveDirIndex = ordinal;
    }


    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return synchableFunctions;
    }

    @Override
    public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
        return new SliderSignalSyncPacket(entityID, key, type, value);
    }
}
