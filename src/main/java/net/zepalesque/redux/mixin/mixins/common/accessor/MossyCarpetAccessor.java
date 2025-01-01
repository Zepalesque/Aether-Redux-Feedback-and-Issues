package net.zepalesque.redux.mixin.mixins.common.accessor;

import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.backport.MossyCarpetBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MossyCarpetBlock.class)
public interface MossyCarpetAccessor {
    @Accessor("shapesCache")
    Map<BlockState, VoxelShape> redux$getShapesCache();

    @Mutable
    @Accessor("shapesCache")
    void redux$setShapesCache(Map<BlockState, VoxelShape> shapeByIndex);
/*
    @Accessor("collisionShapeByIndex")
    Map<BlockState, VoxelShape> redux$getCollisionShapeByIndex();

    @Mutable
    @Accessor("collisionShapeByIndex")
    void redux$setCollisionShapeByIndex(Map<BlockState, VoxelShape> collisionShapeByIndex);*/
}