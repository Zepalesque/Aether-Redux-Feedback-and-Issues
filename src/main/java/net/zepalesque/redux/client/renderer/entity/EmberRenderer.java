package net.zepalesque.redux.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.DragonFireballRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.Fireball;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.Ember;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class EmberRenderer extends EntityRenderer<Ember> {
    private static final ResourceLocation TEXTURE_LOCATION = Redux.loc("textures/entity/projectile/ember.png");
    private static final RenderType RENDER_TYPE = RenderType.entityTranslucent(TEXTURE_LOCATION);

    private static final int FULLBRIGHT = LightTexture.pack(15, 15);

    public EmberRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    /**
     * <p>[CODE COPY] - {@link DragonFireballRenderer#render(DragonFireball, float, float, PoseStack, MultiBufferSource, int)}</p>
     * <p>Note that the difference here is that the ember has fullbright lighting and fades out</p>
     */
    @Override
    public void render(Ember entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        int i = 10;
        int alpha = entity.lifetime - entity.tickCount < i ? (int) (255F * ((float)(entity.lifetime - entity.tickCount) / i)) : 255;
        float size = 0.125F;
        float radius = size / 2;
        poseStack.pushPose();
        poseStack.translate(0.0F, radius, 0.0F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));
        PoseStack.Pose posestack$pose = poseStack.last();
        VertexConsumer vertexconsumer = buffer.getBuffer(RENDER_TYPE);
        vertex(vertexconsumer, posestack$pose, FULLBRIGHT, -radius, -radius, 0, 1, alpha);
        vertex(vertexconsumer, posestack$pose, FULLBRIGHT, radius, -radius, 1, 1, alpha);
        vertex(vertexconsumer, posestack$pose, FULLBRIGHT, radius, radius, 1, 0, alpha);
        vertex(vertexconsumer, posestack$pose, FULLBRIGHT, -radius, radius, 0, 0, alpha);
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
    }

    private static void vertex(VertexConsumer consumer, PoseStack.Pose pose, int packedLight, float x, float y, int u, int v, int alpha) {
        consumer.addVertex(pose, x/* - 0.5F*/, (float)y/* - 0.25F*/, 0.0F)
                .setColor(255, 255, 255, alpha)
                .setUv((float)u, (float)v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }


    @Override
    public ResourceLocation getTextureLocation(Ember entity) {
        return TEXTURE_LOCATION;
    }
}
