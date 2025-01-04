package net.zepalesque.redux.client.renderer.block.model.baked;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.common.util.TriState;

/**
 * CODE COPY - <a href="https://github.com/The-Aether-Team/The-Aether-II/blob/1.21.1-develop/src/main/java/com/aetherteam/aetherii/client/renderer/block/model/baked/AmbientOcclusionLightModel.java">Aether II - src.main.java.com.aetherteam.aetherii.client.renderer.block.model.baked.AmbientOcclusionLightModel</a>
 */
public class AmbientOcclusionLightModel extends BakedModelWrapper<BakedModel> {
    public AmbientOcclusionLightModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public TriState useAmbientOcclusion(BlockState state, ModelData data, RenderType renderType) {
        return TriState.TRUE;
    }
}