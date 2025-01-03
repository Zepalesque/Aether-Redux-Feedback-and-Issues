package net.zepalesque.redux.network.packet;

import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public class SliderSignalSyncPacket extends SyncEntityPacket<SliderSignalAttachment> {

    public static final Type<SliderSignalSyncPacket> TYPE = new Type<>(Redux.loc("sync_slider_signal_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SliderSignalSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
            SliderSignalSyncPacket::write,
            SliderSignalSyncPacket::decode);

    public SliderSignalSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public SliderSignalSyncPacket(int entityID, String key, INBTSynchable.Type type, Object value) {
        super(entityID, key, type, value);
    }


    public static SliderSignalSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new SliderSignalSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<SliderSignalAttachment>> getAttachment() {
        return ReduxDataAttachments.SLIDER_SIGNAL;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void execute(SliderSignalSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
