package net.zepalesque.redux.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.renderer.ReduxRenderTypes;
import net.zepalesque.redux.client.renderer.api.IPostRenderer;
import org.jetbrains.annotations.NotNull;

public class ReduxEvilWhirlwindRenderer<T extends AbstractWhirlwind> extends ReduxWhirlwindRenderer<T> implements IPostRenderer<T> {

    private static final ResourceLocation EVIL_WHIRLWIND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/whirlwind/evil_whirlwind.png");

    public ReduxEvilWhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull T whirlwind) {
        return EVIL_WHIRLWIND;
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {}

    @Override
    public void internalRender(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    protected float xOffset(float tickCount) {
        return tickCount * 0.015F;
    }

    @Override
    protected void setupRotations(@NotNull AbstractWhirlwind entity, @NotNull PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {}

    @Override
    protected void scale(T livingEntity, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.25F, 1.25F, 1.25F);
    }

    @Override
    protected RenderType renderType(ResourceLocation texture, float xOffset) {
        return ReduxRenderTypes.whirlwindParticleTranslucency(texture, xOffset, 0.0F);
    }
}
