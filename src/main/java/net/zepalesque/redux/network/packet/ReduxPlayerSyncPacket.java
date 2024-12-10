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
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

/**
 * Sync packet for values in the {@link ReduxPlayerAttachment} class.
 */
public class ReduxPlayerSyncPacket extends SyncEntityPacket<ReduxPlayerAttachment> {
    public static final Type<ReduxPlayerSyncPacket> TYPE = new Type<>(Redux.loc("sync_redux_player_attachment"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ReduxPlayerSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(
        ReduxPlayerSyncPacket::write,
        ReduxPlayerSyncPacket::decode);

    public ReduxPlayerSyncPacket(Quartet<Integer, String, INBTSynchable.Type, Object> values) {
        super(values);
    }

    public ReduxPlayerSyncPacket(int playerID, String key, INBTSynchable.Type type, Object value) {
        super(playerID, key, type, value);
    }

    @Override
    public Type<ReduxPlayerSyncPacket> type() {
        return TYPE;
    }

    public static ReduxPlayerSyncPacket decode(RegistryFriendlyByteBuf buf) {
        return new ReduxPlayerSyncPacket(SyncEntityPacket.decodeEntityValues(buf));
    }

    @Override
    public Supplier<AttachmentType<ReduxPlayerAttachment>> getAttachment() {
        return ReduxDataAttachments.REDUX_PLAYER;
    }

    public static void execute(ReduxPlayerSyncPacket payload, IPayloadContext context) {
        SyncEntityPacket.execute(payload, context.player());
    }
}
