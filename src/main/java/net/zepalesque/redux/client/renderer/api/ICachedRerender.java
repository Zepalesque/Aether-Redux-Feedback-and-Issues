package net.zepalesque.redux.client.renderer.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.zepalesque.zenith.util.lambda.Consumers;
import org.jetbrains.annotations.NotNull;

public interface ICachedRerender<T extends Entity> {

    default boolean actuallyRender() {
        Cache<T> cache = this.getCache();
        boolean success = cache.execute(this::internalRender);
        if (success) {
            cache.clear();
            return true;
        }
        return false;
    }

    Cache<T> getCache();

    void internalRender(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight);

    class Cache<T extends Entity> {

        private T entity;
        private float entityYaw, partialTicks;
        private PoseStack poseStack;
        private MultiBufferSource buffer;
        private int packedLight;
        private boolean cached = false;

        public Cache() {}

        public void cache(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
            this.entity = entity;
            this.entityYaw = entityYaw;
            this.partialTicks = partialTicks;
            this.poseStack = poseStack;
            this.buffer = buffer;
            this.packedLight = packedLight;
            this.cached = true;
        }

        public void clear() {
            this.entity = null;
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
