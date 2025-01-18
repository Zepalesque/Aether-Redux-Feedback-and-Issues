package net.zepalesque.redux.mixin.mixins.common.world.structure;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.structure.SilverDungeonStructure;
import com.aetherteam.aether.world.structurepiece.LargeAercloudChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Mixin(SilverDungeonStructure.class)
public class SilverDungeonMixin {


    private @Unique static final float DIRECTION_MAX_Y = 0.27f;
    private @Unique static final float TILT_RADIANS = 0.3f;
    private @Unique static final float DIRECTION_DISPLACEMENT_AMOUNT = 0.5f;
    private @Unique static final float RADIUS_XZ_MIN = 1.5f;
    private @Unique static final float RADIUS_XZ_MAX = 2.7f;
    private @Unique static final float RADIUS_Y_MIN = 1.1f;
    private @Unique static final float RADIUS_Y_MAX = 1.5f;
    private @Unique static final float Y_FLATTENING_CUTOFF_RATIO = 0.9f;


    // TODO: make this actually work as intended
    /*@Inject(
            method = "buildCloudBed",
            at = @At("HEAD"),
            cancellable = true)
    private void buildCloudBed(StructurePiecesBuilder builder, RandomSource random, BlockPos origin, Direction direction, CallbackInfo ci) {

        BlockPos offset = origin.below();

        Vector3f d1 = redux$sampleDirection(random, DIRECTION_MAX_Y, new Vector3f());
        Vector3f binormal = new Vector3f(0, random.nextBoolean() ? -1 : 1, 0).cross(d1).normalize();
        Vector3f normal = binormal.cross(d1, new Vector3f());
        Vector3f tiltedNormal = new Vector3f(normal).mul(Mth.cos(TILT_RADIANS)).add(new Vector3f(binormal).mul(Mth.sin(TILT_RADIANS)));
        Vector3f tiltedBinormal = d1.cross(tiltedNormal, new Vector3f());

        Vector3f blockPosRelative = new Vector3f(d1).negate().mul(*//*config.bounds()*//* 100 / 2.0f);

        Vector3f iterationDisplacement = new Vector3f();
        Vector3f deltaFromCurrentCenter = new Vector3f();
        Vector3f scaledDirection = new Vector3f();
        Vector3f scaledTiltedNormal = new Vector3f();
        Vector3f scaledTiltedBinormal = new Vector3f();

        Map<ChunkPos, Set<BlockPos>> chunks = new HashMap<>();
        Set<BlockPos> positions = new HashSet<>();
        for (int tries = 0; tries < 100; tries++) {
            redux$sampleDirection(random, 1.0f, iterationDisplacement);
            iterationDisplacement.mul(DIRECTION_DISPLACEMENT_AMOUNT);
            iterationDisplacement.add(d1);

            blockPosRelative.add(iterationDisplacement);

            float radiusXZ = Mth.randomBetween(random, RADIUS_XZ_MIN, RADIUS_XZ_MAX);
            float radiusY = Mth.randomBetween(random,  RADIUS_Y_MIN, RADIUS_Y_MAX);
            float rangeX = Mth.sqrt(Mth.square(d1.x() * radiusXZ) + Mth.square(tiltedNormal.x() * radiusY) + Mth.square(tiltedBinormal.x() * radiusXZ));
            float rangeY = Mth.sqrt(Mth.square(d1.y() * radiusXZ) + Mth.square(tiltedNormal.y() * radiusY) + Mth.square(tiltedBinormal.y() * radiusXZ));
            float rangeZ = Mth.sqrt(Mth.square(d1.z() * radiusXZ) + Mth.square(tiltedNormal.z() * radiusY) + Mth.square(tiltedBinormal.z() * radiusXZ));
            float rangeYWithCutoff = rangeY * Y_FLATTENING_CUTOFF_RATIO;
            d1.mul(1.0f / radiusXZ, scaledDirection);
            tiltedNormal.mul(1.0f / radiusY, scaledTiltedNormal);
            tiltedBinormal.mul(1.0f / radiusXZ, scaledTiltedBinormal);

            for (int n = 0; n < 10; ++n) {

                for (int dz = Mth.ceil(blockPosRelative.z() - rangeZ); dz <= Mth.floor(blockPosRelative.z() + rangeZ); dz++) {
                    for (int dy = Mth.ceil(blockPosRelative.y() - rangeY); dy <= Mth.floor(blockPosRelative.y() + rangeYWithCutoff); dy++) {
                        for (int dx = Mth.ceil(blockPosRelative.x() - rangeX); dx <= Mth.floor(blockPosRelative.x() + rangeX); dx++) {
                            BlockPos newPosition = offset.offset(dx, dy, dz);
                            if (!positions.contains(newPosition)) {
                                deltaFromCurrentCenter.set(dx - blockPosRelative.x(), dy - blockPosRelative.y(), dz - blockPosRelative.z());
                                if (Mth.square(deltaFromCurrentCenter.dot(scaledDirection)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedNormal)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedBinormal)) < 1.0f) {
                                    positions.add(newPosition);
                                }
                            }
                            chunks.computeIfAbsent(new ChunkPos(newPosition), (pos) -> new HashSet<>());
                        }
                    }
                }
            }
        }

        chunks.forEach(((chunkPos, blockPosSet) -> {
            blockPosSet.addAll(positions.stream().filter(pos -> (new ChunkPos(pos).equals(chunkPos))).toList());
            builder.addPiece(new LargeAercloudChunk(blockPosSet,
                    BlockStateProvider.simple(AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)),
                    new BoundingBox(chunkPos.getMinBlockX(), origin.getY(), chunkPos.getMinBlockZ(), chunkPos.getMaxBlockX(), origin.getY(), chunkPos.getMaxBlockZ()),
                    direction));
        }));
        ci.cancel();
    }*/

    @Unique
    private static Vector3f redux$sampleDirection(RandomSource random, float yRange, Vector3f destination) {
        float thetaXZ = random.nextFloat() * Mth.TWO_PI;
        float sinThetaY = Mth.randomBetween(random, -yRange, yRange);
        float cosThetaY = Mth.sqrt(1.0f - sinThetaY * sinThetaY);
        destination.set(
                Mth.sin(thetaXZ) * cosThetaY,
                sinThetaY,
                Mth.cos(thetaXZ) * cosThetaY
        );
        return destination;
    }

}
