package net.zepalesque.redux.block.backport;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MossyCarpetBlock extends Block implements BonemealableBlock {
    public static final MapCodec<MossyCarpetBlock> CODEC = simpleCodec(MossyCarpetBlock::new);
    public static final BooleanProperty BASE = BlockStateProperties.BOTTOM;
    private static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
    private static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
    private static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
    private static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
    private static final Map<Direction, EnumProperty<WallSide>> PROPERTY_BY_DIRECTION = ImmutableMap.copyOf(
        Util.make(Maps.newEnumMap(Direction.class), p_380156_ -> {
            p_380156_.put(Direction.NORTH, NORTH);
            p_380156_.put(Direction.EAST, EAST);
            p_380156_.put(Direction.SOUTH, SOUTH);
            p_380156_.put(Direction.WEST, WEST);
        })
    );
    private static final float AABB_OFFSET = 1.0F;
    private static final VoxelShape DOWN_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 1.0, 16.0);
    private static final VoxelShape WEST_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 16.0, 16.0);
    private static final VoxelShape EAST_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    private static final VoxelShape NORTH_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 1.0);
    private static final VoxelShape SOUTH_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 16.0, 16.0);
    private static final int SHORT_HEIGHT = 10;
    private static final VoxelShape WEST_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 1.0, 10.0, 16.0);
    private static final VoxelShape EAST_SHORT_AABB = Block.box(15.0, 0.0, 0.0, 16.0, 10.0, 16.0);
    private static final VoxelShape NORTH_SHORT_AABB = Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 1.0);
    private static final VoxelShape SOUTH_SHORT_AABB = Block.box(0.0, 0.0, 15.0, 16.0, 10.0, 16.0);
    private final Map<BlockState, VoxelShape> shapesCache;

    @Override
    public MapCodec<MossyCarpetBlock> codec() {
        return CODEC;
    }

    public MossyCarpetBlock(BlockBehaviour.Properties p_380381_) {
        super(p_380381_);
        this.registerDefaultState(
            this.stateDefinition
                .any()
                .setValue(BASE, Boolean.valueOf(true))
                .setValue(NORTH, WallSide.NONE)
                .setValue(EAST, WallSide.NONE)
                .setValue(SOUTH, WallSide.NONE)
                .setValue(WEST, WallSide.NONE)
        );
        this.shapesCache = ImmutableMap.copyOf(
            this.stateDefinition.getPossibleStates().stream().collect(Collectors.toMap(Function.identity(), MossyCarpetBlock::calculateShape))
        );
    }


    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    private static VoxelShape calculateShape(BlockState state) {
        VoxelShape voxelshape = Shapes.empty();
        if (state.getValue(BASE)) {
            voxelshape = DOWN_AABB;
        }
        voxelshape = switch (state.getValue(NORTH)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, NORTH_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, NORTH_AABB);
        };

        voxelshape = switch (state.getValue(SOUTH)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, SOUTH_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, SOUTH_AABB);
        };

        voxelshape = switch (state.getValue(EAST)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, EAST_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, EAST_AABB);
        };

        voxelshape = switch (state.getValue(WEST)) {
            case NONE -> voxelshape;
            case LOW -> Shapes.or(voxelshape, WEST_SHORT_AABB);
            case TALL -> Shapes.or(voxelshape, WEST_AABB);
        };
        return voxelshape.isEmpty() ? Shapes.block() : voxelshape;
    }

    @Override
    protected VoxelShape getShape(BlockState p_380262_, BlockGetter p_379532_, BlockPos p_379586_, CollisionContext p_380281_) {
        return this.shapesCache.get(p_380262_);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState p_380336_, BlockGetter p_380068_, BlockPos p_379717_, CollisionContext p_379651_) {
        return p_380336_.getValue(BASE) ? DOWN_AABB : Shapes.empty();
    }


    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState p_379574_, LevelReader p_379768_, BlockPos p_380354_) {
        BlockState blockstate = p_379768_.getBlockState(p_380354_.below());
        return p_379574_.getValue(BASE) ? !blockstate.isAir() : blockstate.is(this) && blockstate.getValue(BASE);
    }

    private static boolean hasFaces(BlockState state) {
        if (state.getValue(BASE)) {
            return true;
        } else {
            for (EnumProperty<WallSide> enumproperty : PROPERTY_BY_DIRECTION.values()) {
                if (state.getValue(enumproperty) != WallSide.NONE) {
                    return true;
                }
            }

            return false;
        }
    }

    private static boolean canSupportAtFace(BlockGetter level, BlockPos pos, Direction direction) {
        return direction != Direction.UP && MultifaceBlock.canAttachTo(level, direction, pos, level.getBlockState(pos));
    }

    private BlockState getUpdatedState(BlockState state, BlockGetter level, BlockPos pos, boolean tip) {
        BlockState blockstate = null;
        BlockState blockstate1 = null;
        tip |= state.getValue(BASE);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> enumproperty = getPropertyForFace(direction);
            WallSide wallside = canSupportAtFace(level, pos, direction)
                ? (tip ? WallSide.LOW : state.getValue(enumproperty))
                : WallSide.NONE;
            if (wallside == WallSide.LOW) {
                if (blockstate == null) {
                    blockstate = level.getBlockState(pos.above());
                }

                if (blockstate.is(this) && blockstate.getValue(enumproperty) != WallSide.NONE && !blockstate.getValue(BASE)) {
                    wallside = WallSide.TALL;
                }

                if (!state.getValue(BASE)) {
                    if (blockstate1 == null) {
                        blockstate1 = level.getBlockState(pos.below());
                    }

                    if (blockstate1.is(this) && blockstate1.getValue(enumproperty) == WallSide.NONE) {
                        wallside = WallSide.NONE;
                    }
                }
            }

            state = state.setValue(enumproperty, wallside);
        }

        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext p_380111_) {
        return getUpdatedState(this.defaultBlockState(), p_380111_.getLevel(), p_380111_.getClickedPos(), true);
    }

    // TODO: Mixin into SimpleBlockFeature
    public void placeAt(LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
        BlockState blockstate = this.defaultBlockState();
        BlockState blockstate1 = getUpdatedState(blockstate, level, pos, true);
        level.setBlock(pos, blockstate1, 3);
        BlockState blockstate2 = createTopperWithSideChance(level, pos, random::nextBoolean);
        if (!blockstate2.isAir()) {
            level.setBlock(pos.above(), blockstate2, flags);
        }
    }

    @Override
    public void setPlacedBy(Level p_380310_, BlockPos p_380202_, BlockState p_379659_, @Nullable LivingEntity p_379877_, ItemStack p_380344_) {
        if (!p_380310_.isClientSide) {
            RandomSource randomsource = p_380310_.getRandom();
            BlockState blockstate = createTopperWithSideChance(p_380310_, p_380202_, randomsource::nextBoolean);
            if (!blockstate.isAir()) {
                p_380310_.setBlock(p_380202_.above(), blockstate, 3);
            }
        }
    }

    private BlockState createTopperWithSideChance(BlockGetter level, BlockPos pos, BooleanSupplier placeSide) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = level.getBlockState(blockpos);
        boolean flag = blockstate.is(this);
        if ((!flag || !blockstate.getValue(BASE)) && (flag || blockstate.canBeReplaced())) {
            BlockState blockstate1 = this.defaultBlockState().setValue(BASE, Boolean.valueOf(false));
            BlockState blockstate2 = getUpdatedState(blockstate1, level, pos.above(), true);

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                EnumProperty<WallSide> enumproperty = getPropertyForFace(direction);
                if (blockstate2.getValue(enumproperty) != WallSide.NONE && !placeSide.getAsBoolean()) {
                    blockstate2 = blockstate2.setValue(enumproperty, WallSide.NONE);
                }
            }

            return hasFaces(blockstate2) && blockstate2 != blockstate ? blockstate2 : Blocks.AIR.defaultBlockState();
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }


    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            BlockState blockstate = getUpdatedState(state, level, pos, false);
            return !hasFaces(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_379510_) {
        p_379510_.add(BASE, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected BlockState rotate(BlockState p_379325_, Rotation p_380164_) {
        return switch (p_380164_) {
            case CLOCKWISE_180 -> p_379325_.setValue(NORTH, p_379325_.getValue(SOUTH))
            .setValue(EAST, p_379325_.getValue(WEST))
            .setValue(SOUTH, p_379325_.getValue(NORTH))
            .setValue(WEST, p_379325_.getValue(EAST));
            case COUNTERCLOCKWISE_90 -> p_379325_.setValue(NORTH, p_379325_.getValue(EAST))
            .setValue(EAST, p_379325_.getValue(SOUTH))
            .setValue(SOUTH, p_379325_.getValue(WEST))
            .setValue(WEST, p_379325_.getValue(NORTH));
            case CLOCKWISE_90 -> p_379325_.setValue(NORTH, p_379325_.getValue(WEST))
            .setValue(EAST, p_379325_.getValue(NORTH))
            .setValue(SOUTH, p_379325_.getValue(EAST))
            .setValue(WEST, p_379325_.getValue(SOUTH));
            default -> p_379325_;
        };
    }

    @Override
    protected BlockState mirror(BlockState p_379462_, Mirror p_380184_) {
        return switch (p_380184_) {
            case LEFT_RIGHT -> p_379462_.setValue(NORTH, p_379462_.getValue(SOUTH)).setValue(SOUTH, p_379462_.getValue(NORTH));
            case FRONT_BACK -> p_379462_.setValue(EAST, p_379462_.getValue(WEST)).setValue(WEST, p_379462_.getValue(EAST));
            default -> super.mirror(p_379462_, p_380184_);
        };
    }

    @Nullable
    public static EnumProperty<WallSide> getPropertyForFace(Direction direction) {
        return PROPERTY_BY_DIRECTION.get(direction);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader p_379909_, BlockPos p_379807_, BlockState p_379358_) {
        return p_379358_.getValue(BASE) && !createTopperWithSideChance(p_379909_, p_379807_, () -> true).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_380168_, RandomSource p_380045_, BlockPos p_380299_, BlockState p_379595_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel p_379402_, RandomSource p_379670_, BlockPos p_379387_, BlockState p_379934_) {
        BlockState blockstate = createTopperWithSideChance(p_379402_, p_379387_, () -> true);
        if (!blockstate.isAir()) {
            p_379402_.setBlock(p_379387_.above(), blockstate, 3);
        }
    }
}
