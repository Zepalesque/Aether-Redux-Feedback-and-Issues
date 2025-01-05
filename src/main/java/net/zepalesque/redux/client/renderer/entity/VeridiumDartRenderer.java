package net.zepalesque.redux.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.VeridiumDart;

import javax.annotation.Nonnull;

public class VeridiumDartRenderer extends ArrowRenderer<VeridiumDart> {
    public static final ResourceLocation INFUSED_TEXTURE = Redux.loc("textures/entity/projectile/dart/infused_veridium_dart.png");
    public static final ResourceLocation INFUSED_OVERLAY = Redux.loc("textures/entity/projectile/dart/infused_veridium_dart_glow.png");

    private static final int FULLBRIGHT = LightTexture.pack(15, 15);

    private boolean glowStep = false;

    @Override
    public void render(VeridiumDart entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        glowStep = true;
        // Render glow texture in fullbright
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, FULLBRIGHT);
        glowStep = false;
    }

    public VeridiumDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    public ResourceLocation getTextureLocation(VeridiumDart dart) {
        return glowStep ? INFUSED_OVERLAY : INFUSED_TEXTURE;
    }

    public static class Uninfused extends ArrowRenderer<VeridiumDart.Uninfused> {
        public static final ResourceLocation TEXTURE = Redux.loc("textures/entity/projectile/dart/infused_veridium_dart.png");

        public Uninfused(EntityRendererProvider.Context context) {
            super(context);
        }

        @Nonnull
        public ResourceLocation getTextureLocation(VeridiumDart.Uninfused dart) {
            return TEXTURE;
        }
    }
}
