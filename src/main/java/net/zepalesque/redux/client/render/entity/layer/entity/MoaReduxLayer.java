package net.zepalesque.redux.client.render.entity.layer.entity;

import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.capability.animation.moa.MoaAnimation;
import net.zepalesque.redux.client.render.entity.model.entity.MoaReduxModel;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.MoaFeetType;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class MoaReduxLayer extends RenderLayer<Moa, MoaModel> {

    protected final MoaRenderer parent;
    private final MoaReduxModel updated, talons;

    private static final Map<ResourceLocation, ResourceLocation> TRANSLATION_MAP = new HashMap<>();

    public MoaReduxLayer(MoaRenderer entityRenderer, MoaReduxModel updated, MoaReduxModel talons) {
        super(entityRenderer);
        this.updated = updated;
        this.talons = talons;
        this.parent = entityRenderer;
    }

    @Override
    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (MoaUtils.useNewModel(moa)) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.translate(0F, 1.5F, /*-0.125F*/ 0F);
            MoaReduxModel model = this.updated;
            model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            if (Minecraft.getInstance().player != null && !moa.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation feathersLoc = getTextureLocation(moa);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(feathersLoc));
                model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    @Nonnull
    @Override
    public ResourceLocation getTextureLocation(Moa moa) {
        return this.parent.getTextureLocation(moa);
    }
}
