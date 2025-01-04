package net.zepalesque.redux.client.renderer.entity.layer;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SliderModel;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.ArrayUtil;
import net.zepalesque.redux.attachment.SliderSignalAttachment;

public class SliderSignalLayer extends RenderLayer<Slider, SliderModel> {

    private static final RenderType NORMAL = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_critical_glow.png"));
    private static final RenderType CRITICAL = RenderType.eyes(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "textures/entity/mobs/slider/slider_awake_glow.png"));

    public SliderSignalLayer(RenderLayerParent<Slider, SliderModel> pRenderer) {
        super(pRenderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, Slider slider, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        SliderSignalAttachment signal = SliderSignalAttachment.get(slider);
        if (slider.isAwake() && signal.shouldGlow(slider)) {
            RenderType renderType = slider.isCritical() ? CRITICAL : NORMAL;
            VertexConsumer consumer = buffer.getBuffer(renderType);
            this.getParentModel().renderToBuffer(poseStack, consumer, 15728640, OverlayTexture.NO_OVERLAY);
        }
    }
}
