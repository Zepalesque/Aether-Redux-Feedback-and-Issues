package net.zepalesque.redux.client.event.hook;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ToggleKeyMapping;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class KeyHooks {

    public static void cancelKey(final Entity entity, KeyMapping key) {
        if (entity == Minecraft.getInstance().player) {
            ToggleKeyMapping.set(key.getKey(), false);
        }
    }
}
