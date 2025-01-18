package net.zepalesque.redux.mixin.mixins.common.world.feature;

import com.aetherteam.aether.world.configuration.AercloudConfiguration;
import com.aetherteam.aether.world.feature.AercloudFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * See <a href="https://github.com/KdotJPG/The-Aether/blob/feature/tuneups/src/main/java/com/aetherteam/aether/world/feature/AercloudFeature.java">github.com/KdotJPG/The-Aether/.../AercloudFeature.java</a>
 */
@Mixin(AercloudFeature.class)
public abstract class AercloudFeatureMixin extends FeatureMixin<AercloudConfiguration> {

    private @Unique static final float DIRECTION_MAX_Y = 0.27f;
    private @Unique static final float TILT_RADIANS = 0.3f;
    private @Unique static final float DIRECTION_DISPLACEMENT_AMOUNT = 0.5f;
    private @Unique static final float RADIUS_XZ_MIN = 1.5f;
    private @Unique static final float RADIUS_XZ_MAX = 2.7f;
    private @Unique static final float RADIUS_Y_MIN = 1.1f;
    private @Unique static final float RADIUS_Y_MAX = 1.5f;
    private @Unique static final float Y_FLATTENING_CUTOFF_RATIO = 0.9f;

    // TODO: Decipher how the heck this works via reverse-engineering idk
    @Inject(
            method = "place",
            at = @At("HEAD"),
            cancellable = true)
    private void redux$place(FeaturePlaceContext<AercloudConfiguration> context, CallbackInfoReturnable<Boolean> cir) {

        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        Vector3f direction = redux$sampleDirection(random, DIRECTION_MAX_Y, new Vector3f());
        Vector3f binormal = new Vector3f(0, random.nextBoolean() ? -1 : 1, 0).cross(direction).normalize();
        Vector3f normal = binormal.cross(direction, new Vector3f());
        Vector3f tiltedNormal = new Vector3f(normal).mul(Mth.cos(TILT_RADIANS)).add(new Vector3f(binormal).mul(Mth.sin(TILT_RADIANS)));
        Vector3f tiltedBinormal = direction.cross(tiltedNormal, new Vector3f());

        AercloudConfiguration config = context.config();

        // TODO (was in original, see link in class javadoc):  the purpose of the counterpart to this in the original code seems to have been to keep the cloud in the feature gen range. Try a more directed approach.
        Vector3f blockPosRelative = new Vector3f(direction).negate().mul(config.bounds() / 2.0f);

        BlockState blockState = config.block().getState(random, context.origin());

        Vector3f iterationDisplacement = new Vector3f();
        Vector3f deltaFromCurrentCenter = new Vector3f();
        Vector3f scaledDirection = new Vector3f();
        Vector3f scaledTiltedNormal = new Vector3f();
        Vector3f scaledTiltedBinormal = new Vector3f();


        for (int amount = 0; amount < config.bounds(); ++amount) {
            redux$sampleDirection(random, 1.0f, iterationDisplacement);
            iterationDisplacement.mul(DIRECTION_DISPLACEMENT_AMOUNT);
            iterationDisplacement.add(direction);

            blockPosRelative.add(iterationDisplacement);

            float radiusXZ = Mth.randomBetween(random, RADIUS_XZ_MIN, RADIUS_XZ_MAX);
            float radiusY = Mth.randomBetween(random,  RADIUS_Y_MIN, RADIUS_Y_MAX);
            float rangeX = Mth.sqrt(Mth.square(direction.x() * radiusXZ) + Mth.square(tiltedNormal.x() * radiusY) + Mth.square(tiltedBinormal.x() * radiusXZ));
            float rangeY = Mth.sqrt(Mth.square(direction.y() * radiusXZ) + Mth.square(tiltedNormal.y() * radiusY) + Mth.square(tiltedBinormal.y() * radiusXZ));
            float rangeZ = Mth.sqrt(Mth.square(direction.z() * radiusXZ) + Mth.square(tiltedNormal.z() * radiusY) + Mth.square(tiltedBinormal.z() * radiusXZ));
            float rangeYWithCutoff = rangeY * Y_FLATTENING_CUTOFF_RATIO;
            direction.mul(1.0f / radiusXZ, scaledDirection);
            tiltedNormal.mul(1.0f / radiusY, scaledTiltedNormal);
            tiltedBinormal.mul(1.0f / radiusXZ, scaledTiltedBinormal);
            for (int dz = Mth.ceil(blockPosRelative.z() - rangeZ); dz <= Mth.floor(blockPosRelative.z() + rangeZ); dz++) {
                for (int dy = Mth.ceil(blockPosRelative.y() - rangeY); dy <= Mth.floor(blockPosRelative.y() + rangeYWithCutoff); dy++) {
                    for (int dx = Mth.ceil(blockPosRelative.x() - rangeX); dx <= Mth.floor(blockPosRelative.x() + rangeX); dx++) {
                        BlockPos newPosition = context.origin().offset(dx, dy, dz);

                        if (level.isEmptyBlock(newPosition)) {
                            deltaFromCurrentCenter.set(dx - blockPosRelative.x(), dy - blockPosRelative.y(), dz - blockPosRelative.z());
                            if (Mth.square(deltaFromCurrentCenter.dot(scaledDirection)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedNormal)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedBinormal)) < 1.0f) {
                                this.setBlock(level, newPosition, blockState);
                            }
                        }
                    }
                }
            }
        }

        cir.setReturnValue(true);
    }

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
