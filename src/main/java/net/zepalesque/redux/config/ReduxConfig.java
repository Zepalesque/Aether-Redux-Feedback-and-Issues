package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.enums.AACompatType;
import net.zepalesque.zenith.api.serialization.config.DataSerializableConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ReduxConfig {

    public static class Server extends DataSerializableConfig {

        public final ModConfigSpec.ConfigValue<Boolean> redux_sky_colors;
        public final ModConfigSpec.ConfigValue<Boolean> redux_water_colors;
        public final ModConfigSpec.ConfigValue<Boolean> cloudbed;
        public final ModConfigSpec.ConfigValue<Boolean> revamped_quicksoil_movement;
        public final ModConfigSpec.IntValue max_veridium_tool_infusion;
        public final ModConfigSpec.ConfigValue<Boolean> consistent_break_speeds;
        public final ModConfigSpec.ConfigValue<Boolean> raw_ores;

        public Server(ModConfigSpec.Builder builder) {
            super(() -> SERVER_SPEC, "redux_server");
            builder.push("Tweaks");
            redux_sky_colors = builder
                    .comment("Use Redux's alternative sky colors for the Aether")
                    .define("Redux Sky Colors", true);
            redux_water_colors = builder
                    .comment("Use Redux's alternative water colors for the Aether")
                    .define("Redux Water Colors", true);
            cloudbed = builder
                    .comment("Replace the Aether's large Aercloud features with a noise-based cloudbed")
                    .define("Cloudbed", true);
            raw_ores = builder
                    .comment("Use raw ores like modern vanilla versions, instead of just getting the ore block when mining it")
                    .define("Raw Ores", true);
            builder.pop();
            builder.push("Gameplay");
            max_veridium_tool_infusion = builder
                    .comment("The maximum amount of infusion a Veridium tool is able to carry. Note that by default, a tools infusion level is increased by 4 when it is infused with a single Ambrosium Shard.")
                    .defineInRange("Max Veridium Tool Infusion", 64, 1, 127);
            revamped_quicksoil_movement = builder
                    .comment("Changes quicksoil to make it use a better movement system, based on the way it worked in the Aether II: Highlands in 1.12")
                    .define("Revamped Quicksoil Movement", true);
            consistent_break_speeds = builder
                    .comment("Slows down the mining speeds of some Aether blocks, to be more vanilla-consistent")
                    .define("Consistent Break Speeds", false);
            builder.pop();
        }
    }

    public static class Common extends DataSerializableConfig {

        public final ModConfigSpec.ConfigValue<Boolean> bronze_dungeon_upgrade;
        public final ModConfigSpec.EnumValue<AACompatType> redux_noise;

        public Common(ModConfigSpec.Builder builder) {
            super(() -> COMMON_SPEC, "redux_common");
            builder.push("TODO");
            bronze_dungeon_upgrade = builder
                    .comment("Upgrades the Bronze Dungeon structure with new blocks and more depth")
                    .define("Bronze Dungeon Upgrade", true);
            redux_noise = builder
                    .comment("Uses an alternative noise for the Aether. By default, this is disabled with the Ancient Aether mod installed.")
                    .defineEnum("Redux Noise", AACompatType.WITHOUT_AA);
            builder.pop();
        }
    }

    public static class Client {

        public final ModConfigSpec.ConfigValue<Boolean> leaf_particles;
        public final ModConfigSpec.ConfigValue<Boolean> improved_whirlwinds;

        public final ModConfigSpec.ConfigValue<Boolean> jappafied_textures;
        public final ModConfigSpec.ConfigValue<Boolean> slider_sfx_upgrade;
        public final ModConfigSpec.ConfigValue<Boolean> slider_signal_sfx;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("Visual");

            leaf_particles = builder
                    .comment("Use nice falling leaf particles for Aether leaf blocks")
                    .define("Leaf Particles", true);
            improved_whirlwinds = builder
                    .comment("Gives Whirlwinds a new design, based on Minecraft 1.21's new Breeze mob")
                    .define("Improved Whirlwinds", true);

            jappafied_textures = Redux.ASSETS_CONFIG.register(builder
                    .comment("Use textures designed to fit with the Jappafied Aethers resource pack.")
                    .define("Jappafied Textures", false), "resource/", "jappafied");


            builder.pop();

            builder.push("Audio");
            builder.push("Slider");

            slider_sfx_upgrade = Redux.ASSETS_CONFIG.register(builder
                    .comment("Improve the hurt, death, and ambient sounds of the Slider.")
                    .define("Slider SFX Upgrades", true), "resource/sfx/", "slider");

            slider_signal_sfx = builder
                    .comment("Gives the Slider a subtle signal effect before sliding.")
                    .define("Slider Movement Signal", true);

            builder.pop(2);

            builder.push("Builtin Resource Pack Customization");



            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC, SERVER_SPEC, CLIENT_SPEC;
    public static final Common COMMON;
    public static final Server SERVER;
    public static final Client CLIENT;

    static {
        final Pair<Server, ModConfigSpec> server = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = server.getRight();
        SERVER = server.getLeft();

        final Pair<Common, ModConfigSpec> common = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = common.getRight();
        COMMON = common.getLeft();

        final Pair<Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = client.getRight();
        CLIENT = client.getLeft();
    }

}
