package net.zepalesque.redux.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;

import java.nio.file.Files;

// See https://github.com/TelepathicGrunt/Bumblezone/blob/6bbcb628672a77cfa7a2648be9b4d2428d1eeeb7/neoforge/src/main/java/com/telepathicgrunt/the_bumblezone/configs/neoforge/BzConfigHandler.java#L26
public class ReduxConfigHandler {

    public static void setup(ModContainer mod, IEventBus bus) {

        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve(Redux.MODID));
            mod.registerConfig(ModConfig.Type.CLIENT, ReduxConfig.CLIENT_SPEC, Redux.MODID + "/client.toml");
            mod.registerConfig(ModConfig.Type.COMMON, ReduxConfig.COMMON_SPEC, Redux.MODID + "/common.toml");
            mod.registerConfig(ModConfig.Type.SERVER, ReduxConfig.SERVER_SPEC, Redux.MODID + "/server.toml");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create Aether: Redux config files: ", e);
        }
    }
}
