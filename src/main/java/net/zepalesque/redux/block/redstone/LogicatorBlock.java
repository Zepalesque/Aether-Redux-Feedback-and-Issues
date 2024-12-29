package net.zepalesque.redux.block.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.block.state.enums.LogicatorMode;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.zenith.util.StateUtil;

public class LogicatorBlock extends DiodeBlock {
    public static final MapCodec<LogicatorBlock> CODEC = simpleCodec(LogicatorBlock::new);
    public static final EnumProperty<LogicatorMode> MODE = ReduxStates.MODE_LOGICATOR;
    public static final BooleanProperty LEFT = ReduxStates.LEFT_INPUT, RIGHT = ReduxStates.RIGHT_INPUT;

    public LogicatorBlock(Properties properties) {
        super(properties);
        BlockState b = this.defaultBlockState()
                .setValue(FACING, Direction.NORTH)
                .setValue(POWERED, false)
                .setValue(LEFT, false).setValue(RIGHT, false)
                .setValue(MODE, LogicatorMode.AND);
        this.registerDefaultState(b);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!player.getAbilities().mayBuild) {
            return InteractionResult.PASS;
        } else {
            state = StateUtil.mapValue(state, MODE, LogicatorMode::flipOperationType);
            float f = state.getValue(MODE).isOr() ? 0.55F : 0.5F;
            level.playSound(player, pos, ReduxSounds.LOGICATOR_CLICK.get(), SoundSource.BLOCKS, 0.3F, f);
            level.setBlock(pos, state, 2);
            this.refreshOutputState(level, state, pos);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
    }

    protected static int getLeftInput(SignalGetter level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        // Counterintuitive, but DiodeBlocks are placed backwards
        Direction left = direction.getClockWise();
        return level.getSignal(pos.relative(left), left);
    }

    protected static int getRightInput(SignalGetter level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        // Counterintuitive, but DiodeBlocks are placed backwards
        Direction right = direction.getCounterClockWise();
        return level.getSignal(pos.relative(right), right);
    }

    protected static int getBackInput(SignalGetter level, BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        // Counterintuitive, but DiodeBlocks are placed backwards
        return level.getSignal(pos.relative(direction), direction);
    }

    protected boolean shouldHaveLeftInput(SignalGetter level, BlockPos pos, BlockState state) {
        return getLeftInput(level, pos, state) > 0;
    }
    protected boolean shouldHaveRightInput(SignalGetter level, BlockPos pos, BlockState state) {
        return getRightInput(level, pos, state) > 0;
    }
    protected boolean shouldBeExclusive(SignalGetter level, BlockPos pos, BlockState state) {
        return getBackInput(level, pos, state) > 0;
    }


    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        if (this.shouldUpdateOnPlacement(level, pos, state)) {
            level.scheduleTick(pos, this, 1);
        }
    }

    @Override
    protected boolean shouldTurnOn(Level level, BlockPos pos, BlockState state) {
        LogicatorMode mode = state.getValue(MODE);
        boolean l = this.shouldHaveLeftInput(level, pos, state);
        boolean r = this.shouldHaveRightInput(level, pos, state);
        return mode.operate(l, r);
    }

    protected boolean shouldUpdateOnPlacement(Level level, BlockPos pos, BlockState state) {
        LogicatorMode mode = state.getValue(MODE);
        boolean exclusive = this.shouldBeExclusive(level, pos, state);
        LogicatorMode correct = mode.withExclusivity(exclusive);
        boolean l = this.shouldHaveLeftInput(level, pos, state);
        boolean r = this.shouldHaveRightInput(level, pos, state);
        return correct != mode || l || r /*|| correct.operate(l, r) l and r are both false by here */ || correct.operate(false, false);
    }

    protected void refreshOutputState(Level level, BlockState state, BlockPos pos) {
        if (!this.isLocked(level, pos, state)) {
            BlockState oldState = state;
            boolean l = shouldHaveLeftInput(level, pos, state);
            boolean r = shouldHaveRightInput(level, pos, state);
            boolean exclusive = shouldBeExclusive(level, pos, state);
            boolean or = state.getValue(MODE).isOr();
            boolean powered = this.shouldTurnOn(level, pos, state);

            state = state
                    .setValue(MODE, LogicatorMode.getMode(exclusive, or))
                    .setValue(LEFT, l)
                    .setValue(RIGHT, r)
                    .setValue(POWERED, powered);

            if (state != oldState) {
                level.setBlock(pos, state, 2);
                level.scheduleTick(pos, this, this.getDelay(state), TickPriority.VERY_HIGH);
                this.updateNeighborsInFront(level, pos, state);
            }
        }
    }

    @Override
    protected void checkTickOnNeighbor(Level level, BlockPos pos, BlockState state) {
        if (!this.isLocked(level, pos, state)) {
            boolean wasOn = state.getValue(POWERED);
            boolean shouldTurnOn = this.shouldTurnOn(level, pos, state);
            boolean hadInputL = state.getValue(LEFT);
            boolean shouldHaveInputL = shouldHaveLeftInput(level, pos, state);
            boolean hadInputR = state.getValue(RIGHT);
            boolean shouldHaveInputR = shouldHaveRightInput(level, pos, state);
            boolean wasExclusive = state.getValue(MODE).isExclusive();
            boolean shouldBeExclusive = shouldBeExclusive(level, pos, state);
            if ((wasOn != shouldTurnOn || hadInputL != shouldHaveInputL || hadInputR != shouldHaveInputR || wasExclusive != shouldBeExclusive) && !level.getBlockTicks().willTickThisTick(pos, this)) {
                TickPriority tickpriority = TickPriority.HIGH;
                if (this.shouldPrioritize(level, pos, state)) {
                    tickpriority = TickPriority.EXTREMELY_HIGH;
                } else if (wasOn) {
                    tickpriority = TickPriority.VERY_HIGH;
                }

                level.scheduleTick(pos, this, this.getDelay(state), tickpriority);
            }
        }
    }

    @Override
    public boolean getWeakChanges(BlockState state, LevelReader level, BlockPos pos) {
        return super.getWeakChanges(state, level, pos);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        refreshOutputState(level, state, pos);
    }

    @Override
    protected MapCodec<? extends DiodeBlock> codec() {
        return CODEC;
    }

    @Override
    protected int getDelay(BlockState state) {
        return 1;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, MODE, LEFT, RIGHT, POWERED);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(MODE).isOr()) {
            double d0 = (double)pos.getX() + 0.5 + (random.nextDouble() - 0.5) * 0.2;
            double d1 = (double)pos.getY() + 0.4 + (random.nextDouble() - 0.5) * 0.2;
            double d2 = (double)pos.getZ() + 0.5 + (random.nextDouble() - 0.5) * 0.2;

            if (random.nextBoolean())
                level.addParticle(DustParticleOptions.REDSTONE, d0, d1, d2, 0.0, 0.0, 0.0);
        }
    }
}
