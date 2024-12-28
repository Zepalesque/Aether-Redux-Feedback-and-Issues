package net.zepalesque.redux.client.event.listener;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.TickRateManager;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.api.ICachedPostRenderer;

@EventBusSubscriber(Dist.CLIENT)
public class RenderListener {

    @SubscribeEvent
    public static void renderPost(RenderLevelStageEvent event) {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
            LevelRenderer renderer = event.getLevelRenderer();
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            ClientLevel level = minecraft.level;
            if (level == null || player == null) return;
            Frustum frustum = event.getFrustum();
            Camera camera = event.getCamera();
            EntityRenderDispatcher dispatch = minecraft.getEntityRenderDispatcher();
            DeltaTracker deltaTracker = minecraft.getTimer();
            TickRateManager tickratemanager = level.tickRateManager();

            Vec3 vec3 = camera.getPosition();
            double x = vec3.x();
            double y = vec3.y();
            double z = vec3.z();

            PoseStack posestack = event.getPoseStack();

            for (Entity entity : level.entitiesForRendering()) {
                if (entity.getType() == AetherEntityTypes.WHIRLWIND.get() || entity.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get()) {
                    if (dispatch.shouldRender(entity, frustum, x, y, z) || entity.hasIndirectPassenger(player)) {
                        BlockPos blockpos = entity.blockPosition();
                        if ((level.isOutsideBuildHeight(blockpos.getY()) || renderer.isSectionCompiled(blockpos))
                                && (
                                entity != camera.getEntity()
                                        || camera.isDetached()
                                        || camera.getEntity() instanceof LivingEntity && ((LivingEntity) camera.getEntity()).isSleeping()
                        )) {

                            float f2 = deltaTracker.getGameTimeDeltaPartialTick(!tickratemanager.isEntityFrozen(entity));
                            renderEntity(entity, x, y, z, f2, posestack, dispatch);
                        }
                    }
                }
            }
            ICachedPostRenderer.refreshAndClearAll();
        }
    }

    private static void renderEntity(Entity entity, double camX, double camY, double camZ, float partialTick, PoseStack poseStack, EntityRenderDispatcher dispatcher) {
        double x = Mth.lerp(partialTick, entity.xOld, entity.getX());
        double y = Mth.lerp(partialTick, entity.yOld, entity.getY());
        double z = Mth.lerp(partialTick, entity.zOld, entity.getZ());
        render(entity, x - camX, y - camY, z - camZ,  partialTick, poseStack, dispatcher);
    }

    private static <E extends Entity> void render(
            E entity,
            double x,
            double y,
            double z,
            float partialTicks,
            PoseStack poseStack,
            EntityRenderDispatcher dispatcher) {

        EntityRenderer<? super E> entityrenderer = dispatcher.getRenderer(entity);

        if (entityrenderer instanceof ICachedPostRenderer<?> post) {
            Vec3 vec3 = entityrenderer.getRenderOffset(entity, partialTicks);
            double d2 = x + vec3.x();
            double d3 = y + vec3.y();
            double d0 = z + vec3.z();
            poseStack.pushPose();
            poseStack.translate(d2, d3, d0);
            boolean b = post.actuallyRender(entity);
            poseStack.popPose();
            if (!b) {
                Redux.LOGGER.debug("Did not render entity: {}", entity);
            }
        }
    }
}
