package net.zepalesque.redux.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.renderer.ReduxRenderTypes;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.client.renderer.api.IPostRenderer;
import net.zepalesque.redux.client.renderer.entity.model.WhirlwindModel;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.util.lambda.Consumers;
import net.zepalesque.zenith.util.lambda.Functions;
import org.jetbrains.annotations.NotNull;

public class ReduxWhirlwindRenderer<T extends AbstractWhirlwind> extends LivingEntityRenderer<T, WhirlwindModel<T>> {

    private static final ResourceLocation WHIRLWIND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/whirlwind/whirlwind.png");

    public ReduxWhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context, new WhirlwindModel<>(context.bakeLayer(ReduxRenderers.ModelLayers.WHIRLWIND)), 0.0F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull T whirlwind) {
        return WHIRLWIND;
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        if (ReduxConfig.CLIENT.improved_whirlwinds.get()) {
            float age = this.getBob(entity, partialTicks);
            VertexConsumer vertexconsumer = buffer.getBuffer(renderType(getTextureLocation(entity), this.xOffset(age) % 1.0F));
            poseStack.pushPose();
            this.model.setupAnim(entity, 0.0F, 0.0F, age, 0.0F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            this.scale(entity, poseStack, partialTicks);
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

    protected RenderType renderType(ResourceLocation texture, float xOffset) {
        return RenderType.breezeWind(texture, xOffset, 0.0F);
    }

    protected float xOffset(float tickCount) {
        return tickCount * 0.1F;
    }

    @Override
    protected void setupRotations(@NotNull AbstractWhirlwind entity, @NotNull PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {}
}
