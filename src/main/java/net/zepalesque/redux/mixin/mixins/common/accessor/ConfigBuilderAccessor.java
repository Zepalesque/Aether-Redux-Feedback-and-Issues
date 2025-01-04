package net.zepalesque.redux.mixin.mixins.common.accessor;

import com.aetherteam.aether.data.resources.builders.AetherNoiseBuilders;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(ModConfigSpec.Builder.class)
public interface ConfigBuilderAccessor {

    @Accessor("currentPath")
    List<String> redux$getCurrentPath();
}
