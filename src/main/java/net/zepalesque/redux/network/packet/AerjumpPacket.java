package net.zepalesque.redux.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;

// Me when funni networking
public class AerjumpPacket {

    public record Request(int playerID) implements CustomPacketPayload {

        public static final Type<Request> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(Redux.MODID, "request_airjump"));

        public static final StreamCodec<RegistryFriendlyByteBuf, Request> STREAM_CODEC = CustomPacketPayload.codec(
                Request::write,
                Request::decode);

        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.playerID());
        }

        public static Request decode(FriendlyByteBuf buf) {
            int playerID = buf.readInt();
            return new Request(playerID);
        }

        public static void execute(Request packet, IPayloadContext context) {
            Player player = context.player();
            if (player.getServer() != null && player.getId() == packet.playerID() && player instanceof ServerPlayer sp) {
                ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(sp);
                int jumpIndex = attachment.getPerformedAerjumps();
                if (attachment.tryAerjump(sp, jumpIndex)) {
                    double x = sp.getX();
                    double y = sp.getY();
                    double z = sp.getZ();
                    PacketDistributor.sendToPlayer(sp, new Accepted(jumpIndex));
                    PacketDistributor.sendToPlayersNear((ServerLevel) sp.level(), null, x, y, z, 32.0, new Particles(x, y, z));
                }
            }
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }


    public record Accepted(int jumpIndex) implements CustomPacketPayload {

        public static final CustomPacketPayload.Type<Accepted> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Redux.MODID, "airjump_accepted"));

        public static final StreamCodec<RegistryFriendlyByteBuf, Accepted> STREAM_CODEC = CustomPacketPayload.codec(
                Accepted::write,
                Accepted::decode);

        public void write(FriendlyByteBuf buf) {
            buf.writeInt(this.jumpIndex());
        }

        public static Accepted decode(FriendlyByteBuf buf) {
            int jumpIndex = buf.readInt();
            return new Accepted(jumpIndex);
        }


        public static void execute(Accepted packet, IPayloadContext context) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                ReduxPlayerAttachment attachment = ReduxPlayerAttachment.get(player);
                attachment.doAerjumpMovement(player, packet.jumpIndex());
            }
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }

    public record Particles(double x, double y, double z) implements CustomPacketPayload {

        public static final CustomPacketPayload.Type<Particles> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Redux.MODID, "airjump_fx"));

        public static final StreamCodec<RegistryFriendlyByteBuf, Particles> STREAM_CODEC = CustomPacketPayload.codec(
                Particles::write,
                Particles::decode);

        public void write(FriendlyByteBuf buf) {
            buf.writeDouble(this.x());
            buf.writeDouble(this.y());
            buf.writeDouble(this.z());
        }

        public static Particles decode(FriendlyByteBuf buf) {
            double x = buf.readDouble();
            double y = buf.readDouble();
            double z = buf.readDouble();
            return new Particles(x, y, z);
        }


        public static void execute(Particles packet, IPayloadContext context) {
            Player player = Minecraft.getInstance().player;
            if (player != null) {
                double x = packet.x();
                double y = packet.y();
                double z = packet.z();
                ReduxPlayerAttachment.spawnAerjumpParticles(player.level(),  x, y, z);
            }
        }

        @Override
        public Type<? extends CustomPacketPayload> type() {
            return TYPE;
        }
    }
}
