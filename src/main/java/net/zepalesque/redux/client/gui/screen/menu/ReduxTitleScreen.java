package net.zepalesque.redux.client.gui.screen.menu;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.client.gui.component.menu.DynamicMenuButton;
import com.aetherteam.aether.client.gui.screen.menu.TitleScreenBehavior;
import com.aetherteam.aether.mixin.mixins.client.accessor.TitleScreenAccessor;
import com.aetherteam.cumulus.mixin.mixins.client.accessor.SplashRendererAccessor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.ForgeHooksClient;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.ReduxMenus;
import net.zepalesque.redux.client.gui.component.menu.ReduxMenuButton;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.function.Predicate;

/** NOTE: Duplicates {@link com.aetherteam.aether.client.gui.screen.menu.AetherTitleScreen}, with a few edits such as always being left-aligned (except the logo) and different button textures and stuff */
public class ReduxTitleScreen extends TitleScreen implements TitleScreenBehavior {
	private static final ResourceLocation PANORAMA_OVERLAY = new ResourceLocation("textures/gui/title/background/panorama_overlay.png");
	private static final ResourceLocation AETHER_LOGO = Redux.locate("textures/gui/title/redux.png");
	private final PanoramaRenderer cube;
	private int rows;
	private static final int baseLogoHeight = 144;
	// TODO: Custom splash text?
	private static final int SPLASH_COLOR = 0xAAAAFF;
	private static final int baseLogoWidth = baseLogoHeight * 2;

	public ReduxTitleScreen() {
		((TitleScreenAccessor) this).aether$setFading(true);
		this.cube = new PanoramaRenderer(ReduxMenus.DYNAMIC_CUBEMAP);
	}

	@Override
	protected void init() {
		super.init();
		this.setupButtons();
	}

	public void setupButtons() {
		int buttonRows = 0;
		int lastY = 0;
		if (AetherConfig.CLIENT.enable_server_button.get()) {
			Component component = ((TitleScreenAccessor) this).callGetMultiplayerDisabledReason();
			boolean flag = component == null;
			Tooltip tooltip = component != null ? Tooltip.create(component) : null;
			Button serverButton = this.addRenderableWidget(Button.builder(Component.translatable("gui.aether.menu.server"), (button) -> {
				ServerData serverData = new ServerData("OATS", "oats.aether-mod.net", false);
				ConnectScreen.startConnecting(this, this.minecraft, ServerAddress.parseString(serverData.ip), serverData, false);
			}).bounds(this.width / 2 - 100, (this.height / 4 + 48) + 24 * 3, 200, 20).tooltip(tooltip).build());
			serverButton.active = flag;
			Predicate<AbstractWidget> predicate = (abstractWidget) -> (abstractWidget.getMessage().equals(Component.translatable("menu.multiplayer")) || abstractWidget.getMessage().equals(Component.translatable("menu.online")));
			this.children().removeIf(button -> button instanceof AbstractWidget abstractWidget && predicate.test(abstractWidget));
			this.renderables.removeIf(button -> button instanceof AbstractWidget abstractWidget && predicate.test(abstractWidget));
		}
		for (Renderable renderable : this.renderables) {
			if (renderable instanceof AbstractWidget abstractWidget) {
				Component buttonText = abstractWidget.getMessage();
				if (TitleScreenBehavior.isImageButton(buttonText)) {
					abstractWidget.visible = false; // The visibility handling is necessary here to avoid a bug where the buttons will render in the center of the screen before they have a specified offset.
				}
				if (abstractWidget instanceof ReduxMenuButton button) { // Sets button values that determine their positioning on the screen.
						++buttonRows;

					if (buttonText.equals(Component.translatable("gui.aether.menu.server"))) {
						button.serverButton = true;
						button.buttonCountOffset = 2;
					} else {
						button.buttonCountOffset = buttonRows;
					}

					if ((Boolean)AetherConfig.CLIENT.enable_server_button.get() && buttonText.equals(Component.translatable("menu.singleplayer"))) {
						++buttonRows;
					}
				}
			}
		}
		this.rows = buttonRows;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		TitleScreenAccessor titleScreenAccessor = (TitleScreenAccessor) this;
		if (this.minecraft != null && titleScreenAccessor.aether$getSplash() == null) {
			titleScreenAccessor.aether$setSplash(this.minecraft.getSplashManager().getSplash());
		}
		float fadeAmount = TitleScreenBehavior.super.handleFading(guiGraphics, this, titleScreenAccessor, this.cube, PANORAMA_OVERLAY, partialTicks);
		float scale = getScale(this, this.getMinecraft());
		this.setupLogo(guiGraphics, fadeAmount, scale);
		int roundedFadeAmount = Mth.ceil(fadeAmount * 255.0F) << 24;
		if ((roundedFadeAmount & -67108864) != 0) {
			ForgeHooksClient.renderMainMenu(this, guiGraphics, this.font, this.width, this.height, roundedFadeAmount);
			if (titleScreenAccessor.aether$getSplash() != null) {
				SplashRendererAccessor splashRendererAccessor = (SplashRendererAccessor) titleScreenAccessor.aether$getSplash();
				if (splashRendererAccessor.cumulus$getSplash() != null && !splashRendererAccessor.cumulus$getSplash().isEmpty()) {
					PoseStack poseStack = guiGraphics.pose();
					float splashX = (float) ReduxTitleScreen.this.width / 2 + (baseLogoHeight / scale);
					float splashY = (int) (20 + ((baseLogoHeight - 24) / scale));
					poseStack.pushPose();
					poseStack.translate(splashX, splashY, 0.0F);
					poseStack.mulPose(Axis.ZP.rotationDegrees(-20.0F));
					float textSize = 1.4F - Mth.abs(Mth.sin((float) (Util.getMillis() % 1000L) / 1000.0F * Mth.TWO_PI) * 0.1F);
					textSize = textSize * (200.0F / scale) / (ReduxTitleScreen.this.font.width(splashRendererAccessor.cumulus$getSplash()) + (64 / scale));
					poseStack.scale(textSize, textSize, textSize);
					guiGraphics.drawCenteredString(ReduxTitleScreen.this.font, splashRendererAccessor.cumulus$getSplash(), 0, (int) (-16 / scale), SPLASH_COLOR | roundedFadeAmount);
					poseStack.popPose();
				}
			}

			TitleScreenBehavior.super.renderRightBranding(guiGraphics, this, this.font, roundedFadeAmount);

		}

		int xOffset = TitleScreenBehavior.super.handleButtonVisibility(this, fadeAmount);
		for (Renderable renderable : this.renderables) {
			renderable.render(guiGraphics, mouseX, mouseY, partialTicks);
			if (renderable instanceof ReduxMenuButton aetherButton) { // Smoothly shifts the Aether-styled buttons to the right slightly when hovered over.
				if (aetherButton.isMouseOver(mouseX, mouseY)) {
					if (aetherButton.hoverOffset < 15) {
						aetherButton.hoverOffset += 4;
					}
				} else {
					if (aetherButton.hoverOffset > 0) {
						aetherButton.hoverOffset -= 4;
					}
				}
			}
			if (renderable instanceof DynamicMenuButton dynamicMenuButton) {  // Increases the x-offset to the left for image buttons if there are menu buttons on the screen.
				if (dynamicMenuButton.enabled) {
					xOffset -= 24;
				}
			}
		}
		TitleScreenBehavior.super.handleImageButtons(this, xOffset);
	}

	/**
	 * Renders the Aether logo on the title screen.
	 * @param guiGraphics The rendering {@link GuiGraphics}.
	 * @param transparency The transparency {@link Float} for the logo.
	 * @param scale The {@link Float} for the scaling of the logo relative to the true screen scale.
	 */
	private void setupLogo(GuiGraphics guiGraphics, float transparency, float scale) {
		int width = (int) (baseLogoWidth / scale);
		int height = (int) (baseLogoHeight / scale);
		int logoX = (int) ((this.width / 2 - (baseLogoWidth / 2) / scale));
		int logoY = (int) (/*25*/ 0 + (10 / scale));
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, transparency);
		guiGraphics.blit(AETHER_LOGO, logoX, logoY, 0, 0, width, height, width, height);
		guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Determines the proper scaling for menu elements relative to the true screen scale.
	 * @param screen The parent {@link ReduxTitleScreen}.
	 * @param minecraft The {@link Minecraft} instance.
	 * @return The {@link Float} scale for menu elements.
	 */
	public static float getScale(ReduxTitleScreen screen, Minecraft minecraft) {
		int guiScale = minecraft.getWindow().calculateScale(minecraft.options.guiScale().get(), minecraft.isEnforceUnicode());  // The true screen GUI scale.
		return calculateScale(screen, guiScale, guiScale - 1);
	}
	/**
	 * Determines the proper scaling for menu elements relative to the given scale factors.
	 * @param screen The parent {@link ReduxTitleScreen}.
	 * @param guiScale The base GUI scale {@link Float}.
	 * @param lowerScale A GUI scale {@link Float} value that is one less than the base.
	 * @return The {@link Float} scale for menu elements.
	 */
	public static float calculateScale(ReduxTitleScreen screen, float guiScale, float lowerScale) {
		float scale = 1.0F;
		if (guiScale > 1) {
			scale = guiScale / lowerScale; // A scale factor to counteract the GUI scale option's changing of menu element's pixel scale (pixels-per-pixel).
		}
		int range = ReduxMenuButton.totalHeightRange(screen.rows, scale);
		if (range > screen.height && scale != 1.0F) { // Recursive check to see if the menu elements can actually fit on the screen, otherwise it'll try to shrink to a lower GUI scale.
            return calculateScale(screen, guiScale, lowerScale - 1);
		} else {
			return scale;
		}
	}

	/**
	 * Changes main menu buttons into Aether-styled main menu buttons.<br><br>
	 * Warning for "unchecked" is suppressed because the buttons should always be able to be cast.
	 * @param renderable A renderable widget.
	 * @return A new renderable widget.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T renderable) {
		if (renderable instanceof Button button) {
			if (TitleScreenBehavior.isMainButton(button.getMessage())) {
				ReduxMenuButton aetherButton = new ReduxMenuButton(this, button);
				return (T) super.addRenderableWidget(aetherButton);
			}
		}
		return super.addRenderableWidget(renderable);
	}

	@Override
	public void onClose() {
		super.onClose();
	}
}