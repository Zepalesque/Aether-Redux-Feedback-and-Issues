package net.zepalesque.redux.block.natural.crop;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.EventHooks;
import net.zepalesque.redux.item.ReduxItems;

public class WyndoatsBlock extends BushBlock implements BonemealableBlock {
   public static final int MAX_AGE = 5;
   public static final IntegerProperty AGE = BlockStateProperties.AGE_5;

   private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
           Block.box(3.0D, -1.0D, 3.0D, 13.0D, 2.0D, 13.0D),
           Block.box(3.0D, -1.0D, 3.0D, 13.0D, 4.0D, 13.0D),
           Block.box(3.0D, -1.0D, 3.0D, 13.0D, 6.0D, 13.0D),
           Block.box(3.0D, -1.0D, 3.0D, 13.0D, 9.0D, 13.0D),
           Block.box(3.0D, -1.0D, 3.0D, 13.0D, 11.0D, 13.0D),
           Block.box(3.0D, -1.0D, 3.0D, 13.0D, 13.0D, 13.0D)
   };

   public static final MapCodec<WyndoatsBlock> CODEC = simpleCodec(WyndoatsBlock::new);

   public WyndoatsBlock(BlockBehaviour.Properties properties) {
      super(properties);
      this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), Integer.valueOf(0)));
   }



   @Override
   public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
      return SHAPE_BY_AGE[this.getAge(state)];
   }

   protected boolean mayPlaceOn(BlockState p_52302_, BlockGetter p_52303_, BlockPos p_52304_) {
      return p_52302_.getBlock() instanceof FarmBlock;
   }


   protected IntegerProperty getAgeProperty() {
      return AGE;
   }

   public int getMaxAge() {
      return MAX_AGE;
   }

   public int getAge(BlockState state) {
      return state.getValue(this.getAgeProperty());
   }

   public BlockState getStateForAge(int age) {
      return this.defaultBlockState().setValue(this.getAgeProperty(), Integer.valueOf(age));
   }

   public final boolean isMaxAge(BlockState state) {
      return this.getAge(state) >= this.getMaxAge();
   }

   /**
    * @return whether this block needs random ticking.
    */
   @Override
   public boolean isRandomlyTicking(BlockState state) {
      return !this.isMaxAge(state);
   }

   /**
    * Performs a random tick on a block.
    */
   @Override
   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.isAreaLoaded(pos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
      if (level.getRawBrightness(pos, 0) >= 9) {
         int i = this.getAge(state);
         if (i < this.getMaxAge()) {
            float f = getGrowthSpeed(state, level, pos);
            if (CommonHooks.canCropGrow(level, pos, state, random.nextInt((int)(25.0F / f) + 1) == 0)) {
               level.setBlock(pos, this.getStateForAge(i + 1), 2);
               CommonHooks.fireCropGrowPost(level, pos, state);
            }
         }
      }

   }

   public void growCrops(Level level, BlockPos pos, BlockState state) {
      int i = this.getAge(state) + this.getBonemealAgeIncrease(level);
      int j = this.getMaxAge();
      if (i > j) {
         i = j;
      }

      level.setBlock(pos, this.getStateForAge(i), 2);
   }

   protected int getBonemealAgeIncrease(Level level) {
      return Mth.nextInt(level.random, 1, 3);
   }

   protected static float getGrowthSpeed(BlockState blockState, BlockGetter level, BlockPos pos) {
      Block p_52273_ = blockState.getBlock();
      float f = 1.0F;
      BlockPos blockpos = pos.below();

      for(int i = -1; i <= 1; ++i) {
         for(int j = -1; j <= 1; ++j) {
            float f1;
            label71: {
               f1 = 0.0F;
               BlockState blockstate = level.getBlockState(blockpos.offset(i, 0, j));
               TriState soilDecision = blockstate.canSustainPlant(level, blockpos.offset(i, 0, j), Direction.UP, blockState);
               if (soilDecision.isDefault()) {
                  if (!(blockstate.getBlock() instanceof FarmBlock)) {
                     break label71;
                  }
               } else if (!soilDecision.isTrue()) {
                  break label71;
               }

               f1 = 1.0F;
               if (blockstate.isFertile(level, pos.offset(i, 0, j))) {
                  f1 = 3.0F;
               }
            }

            if (i != 0 || j != 0) {
               f1 /= 4.0F;
            }

            f += f1;
         }
      }

      BlockPos blockpos1 = pos.north();
      BlockPos blockpos2 = pos.south();
      BlockPos blockpos3 = pos.west();
      BlockPos blockpos4 = pos.east();
      boolean flag = level.getBlockState(blockpos3).is(p_52273_) || level.getBlockState(blockpos4).is(p_52273_);
      boolean flag1 = level.getBlockState(blockpos1).is(p_52273_) || level.getBlockState(blockpos2).is(p_52273_);
      if (flag && flag1) {
         f /= 2.0F;
      } else {
         boolean flag2 = level.getBlockState(blockpos3.north()).is(p_52273_) || level.getBlockState(blockpos4.north()).is(p_52273_) || level.getBlockState(blockpos4.south()).is(p_52273_) || level.getBlockState(blockpos3.south()).is(p_52273_);
         if (flag2) {
            f /= 2.0F;
         }
      }

      return f;
   }

   protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
      TriState soilDecision = level.getBlockState(pos.below()).canSustainPlant(level, pos.below(), Direction.UP, state);
      if (!soilDecision.isDefault()) {
         return soilDecision.isTrue();
      } else {
         return hasSufficientLight(level, pos) && super.canSurvive(state, level, pos);
      }
   }

   public static boolean hasSufficientLight(LevelReader level, BlockPos pos) {
      return level.getRawBrightness(pos, 0) >= 8;
   }

   @Override
   protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
      if (entity instanceof Ravager && EventHooks.canEntityGrief(level, entity)) {
         level.destroyBlock(pos, true, entity);
      }

      super.entityInside(state, level, pos, entity);
   }

   protected ItemLike getBaseSeedId() {
      return ReduxItems.WYND_OATS.get();
   }

   @Override
   public ItemStack getCloneItemStack(BlockState state, HitResult target, LevelReader level, BlockPos pos, Player player) {
      return new ItemStack(this.getBaseSeedId());
   }

   @Override
   public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
      return !this.isMaxAge(state);
   }

   public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
      return true;
   }

   public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
      this.growCrops(level, pos, state);
   }

   protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
      builder.add(AGE);
   }

   @Override
   protected MapCodec<? extends BushBlock> codec() {
      return CODEC;
   }
}
