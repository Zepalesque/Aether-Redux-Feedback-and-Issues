package net.zepalesque.redux.client.renderer.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.util.lambda.Consumers;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

// TODO: Move to Zenith
public interface ICachedPostRenderer<T extends Entity> {

    // Registry for renderers that should have their caches refreshed after rendering
    List<ICachedPostRenderer<?>> RENDERERS = new ArrayList<>();

    static void refreshAndClearAll() {
        for (ICachedPostRenderer<?> renderer : RENDERERS) {
            renderer.refreshCaches();
            renderer.clearCaches();
        }
    }

    default boolean actuallyRenderInternal(T entity, PoseStack poseStack) {
        Cache<T> cache = this.getCaches().get(entity);
        return cache != null && cache.execute(this::internalRender, poseStack);
    }

    Map<T, Cache<T>> getCaches();

    /**
     * Gets rid of any {@link Entity#isRemoved() removed} entities in the cache map
     */
    default void refreshCaches() {
        Set<Map.Entry<T, Cache<T>>> entries = this.getCaches().entrySet();
        // Uses Iterator#remove, which works with a Map's entrySet -- See Map#entrySet documentation
        entries.removeIf(entry -> entry.getKey().isRemoved());
    }

    /**
     * {@link Cache#clear() Clears} all caches in the cache map
     */
    default void clearCaches() {
        Collection<Cache<T>> keys = this.getCaches().values();
    }

    void internalRender(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight);

    // TODO: Investigate, will this cause issues?
    @SuppressWarnings("unchecked")
    default boolean actuallyRender(Entity entity, PoseStack poseStack) {
        try {
            T t = (T) entity;
            return actuallyRenderInternal(t, poseStack);
        } catch (ClassCastException e) {
            Redux.LOGGER.error("Cannot post-render Entity {}, skipping", entity.getStringUUID());
            Redux.LOGGER.error("Class cast failed", e);
            return false;
        }
    }

    class Cache<T extends Entity> {

        private final T entity;
        private float entityYaw, partialTicks;
        private MultiBufferSource buffer;
        private int packedLight;
        private boolean cached = false;

        public Cache(T entity) {
            // TODO: find alternative solution
            this.entity = entity;
        }

        /**
         * Caches values, cached during the {@link EntityRenderer#render(Entity, float, float, PoseStack, MultiBufferSource, int) render} method of the renderer
         */
        public void cache(float entityYaw, float partialTicks, MultiBufferSource buffer, int packedLight) {
            this.entityYaw = entityYaw;
            this.partialTicks = partialTicks;
            this.buffer = buffer;
            this.packedLight = packedLight;
            this.cached = true;
        }

        /**
         * Sets this cache's values to be default values
         */
        public void clear() {
            this.entityYaw = Float.NaN;
            this.partialTicks = Float.NaN;
            this.buffer = null;
            this.packedLight = Integer.MIN_VALUE;
            this.cached = false;
        }

        public boolean execute(Consumers.C6<T, Float, Float, PoseStack, MultiBufferSource, Integer> method, PoseStack poseStack) {
            if (this.cached) {
                method.accept(this.entity, this.entityYaw, this.partialTicks, poseStack, this.buffer, this.packedLight);
                return true;
            } else return false;
        }
    }
}
