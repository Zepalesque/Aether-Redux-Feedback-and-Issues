package net.zepalesque.redux.client.gui.component;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicImageButton extends ImageButton {


    @Nullable Supplier<Boolean> activeSupplier = null;
    @Nullable Supplier<Boolean> visibleSupplier = null;

    public DynamicImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, ResourceLocation resourceLocation, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, resourceLocation, onPress);
    }

    public DynamicImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, onPress);
    }

    public DynamicImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress);
    }

    public DynamicImageButton setActiveSupplier(@Nullable Supplier<Boolean> activeSupplier) {
        this.activeSupplier = activeSupplier;
        return this;
    }
    public DynamicImageButton setVisibleSupplier(@Nullable Supplier<Boolean> visibleSupplier) {
        this.visibleSupplier = visibleSupplier;
        return this;
    }



    public DynamicImageButton(int x, int y, int width, int height, int xTexStart, int yTexStart, int yDiffTex, ResourceLocation resourceLocation, int textureWidth, int textureHeight, OnPress onPress, Component message) {
        super(x, y, width, height, xTexStart, yTexStart, yDiffTex, resourceLocation, textureWidth, textureHeight, onPress, message);
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.activeSupplier != null) {
            this.active = this.activeSupplier.get();
        }
        if (this.visibleSupplier != null) {
            this.visible = this.visibleSupplier.get();
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }
}
