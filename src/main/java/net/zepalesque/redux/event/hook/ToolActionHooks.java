package net.zepalesque.redux.event.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.HashMap;
import java.util.Map;

public class ToolActionHooks {

    public static final Map<Block, Block> STRIPPABLES = new HashMap<>();

    public static final Map<Block, Block> FLATTENABLES = new HashMap<>();

    public static final Map<Block, Block> TILLABLES = new HashMap<>();


    public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ItemAbility action) {
        Block oldBlock = old.getBlock();
        if (action == ItemAbilities.AXE_STRIP) {
            if (STRIPPABLES.containsKey(oldBlock)) {
                return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
            }
        } else if (action == ItemAbilities.SHOVEL_FLATTEN) {
            if (FLATTENABLES.containsKey(oldBlock)) {
                return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
            }
        } else if (action == ItemAbilities.HOE_TILL) {
            if (accessor.getBlockState(pos.above()).isAir()) {
                if (TILLABLES.containsKey(oldBlock)) {
                    return TILLABLES.get(oldBlock).withPropertiesOf(old);
                }
            }
        }
        return old;
    }
}
