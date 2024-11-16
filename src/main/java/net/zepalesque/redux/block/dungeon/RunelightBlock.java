package net.zepalesque.redux.block.dungeon;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class RunelightBlock extends Block {
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    protected final boolean creativeInteractOnly;

    public RunelightBlock(Properties properties, boolean creativeInteractOnly) {
        super(properties);
        this.creativeInteractOnly = creativeInteractOnly;
        this.registerDefaultState(this.getStateDefinition().any().setValue(LIT, true));
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (this.isValidForInteraction(player)) {
            if (!level.isClientSide()) {
                state = state.cycle(LIT);
                level.setBlock(pos, state, 3);
                // TODO: sfx
                level.gameEvent(player, state.getValue(LIT) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
                return InteractionResult.CONSUME;
            } else {
                return InteractionResult.SUCCESS;
            }
        }
        return super.useWithoutItem(state, level, pos, player, hit);
    }



    public boolean isValidForInteraction(Player player) {
        return !this.creativeInteractOnly || player.getAbilities().instabuild;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(LIT);
    }
}
