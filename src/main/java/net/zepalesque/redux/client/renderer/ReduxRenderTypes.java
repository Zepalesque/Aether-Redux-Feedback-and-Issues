package net.zepalesque.redux.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class ReduxRenderTypes {
    public static RenderType whirlwind(ResourceLocation location, float u, float v) {
        return RenderType.create(
                "whirlwind",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                1536,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(RenderType.RENDERTYPE_BREEZE_WIND_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(u, v))
                        .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                        .setCullState(RenderType.NO_CULL)
                        .setLightmapState(RenderType.LIGHTMAP)
                        .setOverlayState(RenderType.NO_OVERLAY)
                        .setWriteMaskState(RenderType.COLOR_DEPTH_WRITE)
                        .createCompositeState(false)
        );
    }
}
