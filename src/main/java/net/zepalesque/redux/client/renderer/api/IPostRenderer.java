package net.zepalesque.redux.client.renderer.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.NotNull;

public interface IPostRenderer<T extends Entity> {

    void internalRender(@NotNull T entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight);

    // TODO: Investigate, will this cause issues?
    @SuppressWarnings("unchecked")
    default boolean actuallyRender(Entity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        try {
            T t = (T) entity;
            internalRender(t, entityYaw, partialTicks, poseStack, buffer, packedLight);
            return true;
        } catch (ClassCastException e) {
            Redux.LOGGER.error("Cannot post-render Entity {}, skipping", entity.getStringUUID());
            Redux.LOGGER.error("Class cast failed", e);
            return false;
        }
    }
}
