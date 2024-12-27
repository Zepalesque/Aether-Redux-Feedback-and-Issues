package net.zepalesque.redux.client.renderer.api;

import com.google.common.cache.Cache;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.zepalesque.zenith.util.lambda.Consumers;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

// TODO: Move to Zenith
public interface ICachedPostRenderer<T extends Entity> {

    default boolean actuallyRenderInternal(T entity) {
        Cache<T> cache = this.getCaches().get(entity);
        if (cache != null) {
            boolean success = cache.execute(this::internalRender);
            if (success) {
                cache.clear();
                return true;
            }
        }
        return false;
    }

    Map<T, Cache<T>> getCaches();

    void internalRender(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight);

    default void actuallyRender(Entity entity) {
        try {
            T t = (T) entity;
            actuallyRenderInternal(t);
        } catch (ClassCastException ignored) {}
    }

    class Cache<T extends Entity> {

        private final T entity;
        private float entityYaw, partialTicks;
        private PoseStack poseStack;
        private MultiBufferSource buffer;
        private int packedLight;
        private boolean cached = false;

        public Cache(T entity) {
            // TODO: find alternative solution
            this.entity = entity;
        }

        public void cache(float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
            this.entityYaw = entityYaw;
            this.partialTicks = partialTicks;
            this.poseStack = poseStack;
            this.buffer = buffer;
            this.packedLight = packedLight;
            this.cached = true;
        }

        public void clear() {
            this.entityYaw = Float.NaN;
            this.partialTicks = Float.NaN;
            this.poseStack = null;
            this.buffer = null;
            this.packedLight = Integer.MIN_VALUE;
            this.cached = false;
        }

        public boolean execute(Consumers.C6<T, Float, Float, PoseStack, MultiBufferSource, Integer> method) {
            if (this.cached) {
                method.accept(this.entity, this.entityYaw, this.partialTicks, this.poseStack, this.buffer, this.packedLight);
                return true;
            } else return false;
        }
    }
}
