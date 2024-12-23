package net.zepalesque.redux.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.AechorPlantModel;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BreezeRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.SquidRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.inventory.InventoryMenu;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.client.renderer.entity.model.WhirlwindModel;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public class ReduxWhirlwindRenderer<T extends AbstractWhirlwind> extends LivingEntityRenderer<T, EntityModel<T>> {

    private static final ResourceLocation WHIRLWIND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/whirlwind/whirlwind.png");
    private static final ResourceLocation EVIL_WHIRLWIND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/whirlwind/evil_whirlwind.png");

    public ReduxWhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context, new WhirlwindModel<>(context.bakeLayer(ReduxRenderers.ModelLayers.WHIRLWIND)), 0.0F);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull T whirlwind) {
        if (whirlwind.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get()) return EVIL_WHIRLWIND;
        else return WHIRLWIND;
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (ReduxConfig.CLIENT.improved_whirlwinds.get()) {
            float age = this.getBob(entity, partialTicks);
            VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.breezeWind(getTextureLocation(entity), this.xOffset(age) % 1.0F, 0.0F));
            poseStack.pushPose();
            this.model.setupAnim(entity, 0.0F, 0.0F, age, 0.0F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            if (entity.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get()) poseStack.scale(1.25F, 1.25F, 1.25F);
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }

    private float xOffset(float tickCount) {
        return tickCount * 0.01F;
    }

    @Override
    protected void setupRotations(@NotNull AbstractWhirlwind entity, @NotNull PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) { }
}
