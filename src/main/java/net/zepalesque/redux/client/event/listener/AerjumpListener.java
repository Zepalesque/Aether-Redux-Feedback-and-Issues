package net.zepalesque.redux.client.event.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.network.packet.AerjumpPacket;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class AerjumpListener {

    private static boolean prevJumpBindState = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Level level = Minecraft.getInstance().level;
        Player player = mc.player;

        if (level != null && player != null) {
            boolean jumpPressed = mc.options.keyJump.isDown();
            if (jumpPressed && !prevJumpBindState) {
                PacketDistributor.sendToServer(new AerjumpPacket.Request(player.getId()));
            }
            prevJumpBindState = jumpPressed;
        }
    }
}
