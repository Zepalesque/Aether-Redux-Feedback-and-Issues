package net.zepalesque.redux.client;

import com.aetherteam.aether.Aether;
import com.aetherteam.cumulus.Cumulus;
import com.aetherteam.cumulus.CumulusConfig;
import com.aetherteam.cumulus.api.Menu;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.client.gui.screen.menu.DynamicCubeMap;
import net.zepalesque.redux.client.gui.screen.menu.ReduxTitleScreen;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.List;
import java.util.function.Supplier;

public class ReduxMenus {
    public static final DeferredRegister<Menu> MENUS = DeferredRegister.create(Cumulus.MENU_REGISTRY_KEY, Redux.MODID);

    private static final ResourceLocation ICON = Redux.locate("textures/gui/menu_api/menu_icon_redux.png");
    private static final Component MENU_NAME = Component.translatable("aether_redux.menu_title.redux");

    public static final CubeMap DYNAMIC_CUBEMAP = new DynamicCubeMap(ReduxConfig.CLIENT.menu_directory, Redux.MODID, "textures/gui/title/panorama/%s/panorama", "skyfields");


    private static final ResourceLocation THE_AETHER_REGULAR_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/options_background.png");
    private static final ResourceLocation THE_AETHER_DARK_BACKGROUND = new ResourceLocation(Aether.MODID, "textures/gui/title/light_sentry_background.png");
    private static final ResourceLocation THE_AETHER_HEADER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/header_separator.png");
    private static final ResourceLocation THE_AETHER_FOOTER_SEPARATOR = new ResourceLocation(Aether.MODID, "textures/gui/title/footer_separator.png");
    private static final ResourceLocation THE_AETHER_TAB_BUTTON = new ResourceLocation(Aether.MODID, "textures/gui/title/tab_button.png");

    private static final Menu.Background REDUX_BACKGROUND = new Menu.Background()
            .regularBackground(THE_AETHER_REGULAR_BACKGROUND)
            .darkBackground(THE_AETHER_DARK_BACKGROUND)
            .headerSeparator(THE_AETHER_HEADER_SEPARATOR)
            .footerSeparator(THE_AETHER_FOOTER_SEPARATOR)
            .tabButton(THE_AETHER_TAB_BUTTON);


    public static final RegistryObject<Menu> REDUX_MENU = register("aether_redux_menu", ICON, MENU_NAME, ReduxTitleScreen::new, () -> new Menu.Properties().music(ReduxMusic.REDUX_MENU).background(REDUX_BACKGROUND));

    public static RegistryObject<Menu> register(String id, ResourceLocation icon, Component name, Supplier<TitleScreen> screen, Supplier<Menu.Properties> properties) {
        return MENUS.register(id, () -> new Menu(icon, name, screen.get(), () -> CumulusConfig.CLIENT.active_menu.get().equals("aether_redux:" + id), properties.get()));
    }

    public static void cycle() {
        if (ReduxConfig.CLIENT_SPEC.isLoaded() && CumulusConfig.CLIENT_SPEC.isLoaded()) {
            List<? extends String> choices = ReduxConfig.CLIENT.menu_choices.get();
            if (!choices.isEmpty()) {
                if (choices.size() > 1) {
                    if (CumulusConfig.CLIENT.active_menu.get().equals(REDUX_MENU.getId().toString())) {
                        if (ReduxConfig.CLIENT.randomize_menu_cycling.get()) {
                            ReduxConfig.CLIENT.menu_directory.set(choices.get(Redux.RAND.nextInt(choices.size())));
                        } else {
                            int index = choices.indexOf(ReduxConfig.CLIENT.menu_directory.get());
                            if (index == choices.size() - 1) index = 0; else index++;
                            ReduxConfig.CLIENT.menu_directory.set(choices.get(index));
                        }
                    }
                } else if (!CumulusConfig.CLIENT.active_menu.get().equals(REDUX_MENU.getId().toString())) {
                    ReduxConfig.CLIENT.menu_directory.set(choices.get(0));
                }
            }

        }
    }

}
