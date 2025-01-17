package net.zepalesque.redux.world.carver;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import org.jetbrains.annotations.Nullable;

// TODO: Custom air block?
public class AetherCaveCarver extends CaveWorldCarver {

    public AetherCaveCarver(Codec<CaveCarverConfiguration> codec) {
        super(codec);
    }

    @Nullable
    @Override
    public BlockState getCarveState(CarvingContext context, CaveCarverConfiguration config, BlockPos pos, Aquifer aquifer) {
        if (isDebugEnabled(config)) return aetherDebugState(config, CAVE_AIR);
        else return CAVE_AIR;
    }

    private static BlockState aetherDebugState(CarverConfiguration config, BlockState state) {
        if (state.is(Blocks.CAVE_AIR)) return config.debugSettings.getAirState();
        else if (state.is(Blocks.WATER)) {
            BlockState blockstate = config.debugSettings.getWaterState();
            return blockstate.hasProperty(BlockStateProperties.WATERLOGGED)
                    ? blockstate.setValue(BlockStateProperties.WATERLOGGED, Boolean.valueOf(true))
                    : blockstate;
        } else return state;
    }
}
