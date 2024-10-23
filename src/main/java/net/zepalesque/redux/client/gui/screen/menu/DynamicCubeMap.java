//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.client.gui.screen.menu;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.config.ReduxConfig;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class DynamicCubeMap extends CubeMap {
    private final Supplier<String> folder;
    private final String namespace, formattedPath;
    protected final Map<String, ResourceLocation[]> folders = new HashMap<>();

    public DynamicCubeMap(Supplier<String> folder, String namespace, String formattedPath, String defaultFolder) {
        super(new ResourceLocation(namespace, formattedPath.formatted(defaultFolder)));
        this.folder = folder;
        this.namespace = namespace;
        this.formattedPath = formattedPath;
    }

    protected ResourceLocation[] createImageArray(String folder) {
        ResourceLocation[] array = new ResourceLocation[6];
        ResourceLocation base = new ResourceLocation(namespace, formattedPath.formatted(folder));
        for (int i = 0; i < 6; ++i) {
            array[i] = base.withPath(base.getPath() + "_" + i + ".png");
        }
        return array;
    }

    @Override
    public void render(Minecraft mc, float pitch, float yaw, float alpha) {
        if (!ReduxConfig.CLIENT_SPEC.isLoaded()) return;
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();
        Matrix4f matrix4f = (new Matrix4f()).setPerspective(1.4835298F, (float) mc.getWindow().getWidth() / (float) mc.getWindow().getHeight(), 0.05F, 10.0F);
        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(matrix4f, VertexSorting.DISTANCE_TO_ORIGIN);
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.setIdentity();
        posestack.mulPose(Axis.XP.rotationDegrees(180.0F));
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);

        for (int j = 0; j < 4; ++j) {
            posestack.pushPose();
            float f = ((float) (j % 2) / 2.0F - 0.5F) / 256.0F;
            float f1 = ((float) (j / 2) / 2.0F - 0.5F) / 256.0F;
            float f2 = 0.0F;
            posestack.translate(f, f1, 0.0F);
            posestack.mulPose(Axis.XP.rotationDegrees(pitch));
            posestack.mulPose(Axis.YP.rotationDegrees(yaw));
            RenderSystem.applyModelViewMatrix();

            for (int k = 0; k < 6; ++k) {
                RenderSystem.setShaderTexture(0, this.folders.computeIfAbsent(this.folder.get(), this::createImageArray)[k]);
                bufferbuilder.begin(Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
                int l = Math.round(255.0F * alpha) / (j + 1);
                if (k == 0) {
                    bufferbuilder.vertex(-1.0, -1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, 1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, 1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, -1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
                }

                if (k == 1) {
                    bufferbuilder.vertex(1.0, -1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, 1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
                }

                if (k == 2) {
                    bufferbuilder.vertex(1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
                }

                if (k == 3) {
                    bufferbuilder.vertex(-1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, 1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, -1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
                }

                if (k == 4) {
                    bufferbuilder.vertex(-1.0, -1.0, -1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, -1.0, 1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, -1.0, 1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, -1.0, -1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
                }

                if (k == 5) {
                    bufferbuilder.vertex(-1.0, 1.0, 1.0).uv(0.0F, 0.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(-1.0, 1.0, -1.0).uv(0.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, 1.0, -1.0).uv(1.0F, 1.0F).color(255, 255, 255, l).endVertex();
                    bufferbuilder.vertex(1.0, 1.0, 1.0).uv(1.0F, 0.0F).color(255, 255, 255, l).endVertex();
                }

                tesselator.end();
            }

            posestack.popPose();
            RenderSystem.applyModelViewMatrix();
            RenderSystem.colorMask(true, true, true, false);
        }

        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.restoreProjectionMatrix();
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.enableDepthTest();
    }
}
