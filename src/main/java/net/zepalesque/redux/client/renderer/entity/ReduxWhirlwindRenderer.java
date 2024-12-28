package net.zepalesque.redux.client.renderer.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.AbstractWhirlwind;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.client.renderer.ReduxRenderTypes;
import net.zepalesque.redux.client.renderer.ReduxRenderers;
import net.zepalesque.redux.client.renderer.api.ICachedPostRenderer;
import net.zepalesque.redux.client.renderer.entity.model.WhirlwindModel;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

// TODO: USE MAP FOR CACHES, computeIfAbsent + Map<T, Cache<T>>
// TODO: Also, maybe every 100 or so frames (arbitrary, could also be every frame if it's not too resource-intensive) check each entity cache and ensure all are alive, remove ones that aren't
public class ReduxWhirlwindRenderer<T extends AbstractWhirlwind> extends LivingEntityRenderer<T, WhirlwindModel<T>> implements ICachedPostRenderer<T> {

    private static final ResourceLocation WHIRLWIND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/whirlwind/whirlwind.png");
    private static final ResourceLocation EVIL_WHIRLWIND = ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/whirlwind/evil_whirlwind.png");

    private final Map<T, Cache<T>> caches = new HashMap<>();
    protected ReduxWhirlwindRenderer(EntityRendererProvider.Context context) {
        super(context, new WhirlwindModel<>(context.bakeLayer(ReduxRenderers.ModelLayers.WHIRLWIND)), 0.0F);
    }

    public static <E extends AbstractWhirlwind> ReduxWhirlwindRenderer<E> create(EntityRendererProvider.Context context) {
        ReduxWhirlwindRenderer<E> renderer = new ReduxWhirlwindRenderer<>(context);
        RENDERERS.add(renderer);
        return renderer;
    }

    @Override
    @NotNull
    public ResourceLocation getTextureLocation(@NotNull T whirlwind) {
        if (whirlwind.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get()) return EVIL_WHIRLWIND;
        else return WHIRLWIND;
    }

    @Override
    public void render(@NotNull T entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        this.getCaches().computeIfAbsent(entity, Cache::new).cache(entityYaw, partialTicks, buffer, packedLight);
    }

    @Override
    public Map<T, Cache<T>> getCaches() {
        return this.caches;
    }

    public void internalRender(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (ReduxConfig.CLIENT.improved_whirlwinds.get()) {
            boolean isEvil = entity.getType() == AetherEntityTypes.EVIL_WHIRLWIND.get();
            float age = this.getBob(entity, partialTicks);
            VertexConsumer vertexconsumer = buffer.getBuffer(ReduxRenderTypes.whirlwind(getTextureLocation(entity), this.xOffset(age, isEvil) % 1.0F, 0.0F));
            poseStack.pushPose();
            this.model.setupAnim(entity, 0.0F, 0.0F, age, 0.0F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));

            if (isEvil) poseStack.scale(1.25F, 1.25F, 1.25F);
            this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }


    }


    private float xOffset(float tickCount, boolean isEvil) {
        return tickCount * (isEvil ? 0.015F : 0.01F);
    }

    @Override
    protected void setupRotations(@NotNull AbstractWhirlwind entity, @NotNull PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) { }



}
