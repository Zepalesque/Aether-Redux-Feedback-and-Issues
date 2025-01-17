package net.zepalesque.redux.network.packet;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SliderSignalPacket {

    public record Signal(int mobID) implements CustomPacketPayload {

        public static final Type<Signal> TYPE = new Type<>(Redux.loc("slider_signal"));

        public static final StreamCodec<RegistryFriendlyByteBuf, Signal> STREAM_CODEC = CustomPacketPayload.codec(
                Signal::write,
                Signal::decode);

        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.mobID());
        }

        public static Signal decode(FriendlyByteBuf buf) {
            int mobID = buf.readInt();
            return new Signal(mobID);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }

        public static void execute(Signal payload, IPayloadContext context) {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(payload.mobID());
                if (entity != null && entity.hasData(ReduxDataAttachments.SLIDER_SIGNAL.get()) && entity.getType() == AetherEntityTypes.SLIDER.get()) {
                    SliderSignalAttachment.get((Slider) entity).beginSignal((Slider) entity);
                }
            }
        }
    }

    public record DirectionOverride(int mobID, Direction direction) implements CustomPacketPayload {

        public static final Type<DirectionOverride> TYPE = new Type<>(Redux.loc("slider_signal_direction_override"));

        public static final StreamCodec<RegistryFriendlyByteBuf, DirectionOverride> STREAM_CODEC = CustomPacketPayload.codec(
                DirectionOverride::write,
                DirectionOverride::decode);

        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.mobID());
            buf.writeEnum(this.direction());
        }

        public static DirectionOverride decode(FriendlyByteBuf buf) {
            int mobID = buf.readInt();
            Direction direction = buf.readEnum(Direction.class);
            return new DirectionOverride(mobID, direction);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }


        public static void execute(DirectionOverride payload, IPayloadContext context) {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(payload.mobID());
                if (entity != null && entity.hasData(ReduxDataAttachments.SLIDER_SIGNAL.get()) && entity.getType() == AetherEntityTypes.SLIDER.get()) {
                    SliderSignalAttachment.get((Slider) entity).setOverrideDirection((Slider) entity, payload.direction());
                }
            }
        }
    }

    public record SyncTarget(int mobID, Optional<Integer> target) implements CustomPacketPayload {

        public static final Type<SyncTarget> TYPE = new Type<>(Redux.loc("slider_signal_target_sync"));

        public static final StreamCodec<RegistryFriendlyByteBuf, SyncTarget> STREAM_CODEC = CustomPacketPayload.codec(
                SyncTarget::write,
                SyncTarget::decode);

        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.mobID());
            buf.writeOptional(this.target(), FriendlyByteBuf::writeInt);
        }

        public static SyncTarget decode(FriendlyByteBuf buf) {
            int mobID = buf.readInt();
            Optional<Integer> mob = buf.readOptional(FriendlyByteBuf::readInt);
            return new SyncTarget(mobID, mob);
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }


        public static void execute(SyncTarget payload, IPayloadContext context) {
            ClientLevel level = Minecraft.getInstance().level;
            if (level != null) {
                Entity entity = level.getEntity(payload.mobID());
                if (entity != null && entity.hasData(ReduxDataAttachments.SLIDER_SIGNAL.get()) && entity.getType() == AetherEntityTypes.SLIDER.get()) {
                    Optional<Entity> mob = payload.target().map(level::getEntity);
                    SliderSignalAttachment.get((Slider) entity).setTarget((Slider) entity, mob.orElse(null));
                }
            }
        }
    }
}
