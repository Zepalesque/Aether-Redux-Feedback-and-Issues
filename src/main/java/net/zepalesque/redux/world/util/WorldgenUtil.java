package net.zepalesque.redux.world.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;

// TODO: Move to Zenith
public class WorldgenUtil {

    // Offsets a MutableBlockPos in a direction, a certain amount of blocks
    public static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos mutable, Vec3i origin, Direction direction, int amount) {
        return mutable.set(origin.getX() + (direction.getStepX() * amount), origin.getY() + (direction.getStepY() * amount), origin.getZ() + (direction.getStepZ() * amount));
    }
}
