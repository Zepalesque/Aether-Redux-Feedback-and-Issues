package net.zepalesque.redux.network.packet;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncEntityPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import oshi.util.tuples.Quartet;

import java.util.function.Supplier;

public record SliderSignalPacket(int mobID) implements CustomPacketPayload {

    public static final Type<SliderSignalPacket> TYPE = new Type<>(Redux.loc("slider_signal"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SliderSignalPacket> STREAM_CODEC = CustomPacketPayload.codec(
            SliderSignalPacket::write,
            SliderSignalPacket::decode);

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.mobID());
    }

    public static SliderSignalPacket decode(FriendlyByteBuf buf) {
        int mobID = buf.readInt();
        return new SliderSignalPacket(mobID);
    }



    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }


    public static void execute(SliderSignalPacket payload, IPayloadContext context) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null) {
            Entity entity = level.getEntity(payload.mobID());
            if (entity != null && entity.hasData(ReduxDataAttachments.SLIDER_SIGNAL.get()) && entity.getType() == AetherEntityTypes.SLIDER.get()) {
                SliderSignalAttachment.get((Slider) entity).doBeep((Slider) entity);
            }
        }
    }
}
