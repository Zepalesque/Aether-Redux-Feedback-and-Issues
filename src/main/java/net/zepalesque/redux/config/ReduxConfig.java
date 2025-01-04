package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import net.neoforged.neoforge.common.ModConfigSpec.EnumValue;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.enums.AACompatType;
import net.zepalesque.redux.pack.ReduxPackConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ReduxConfig {

    public static class Server extends ReduxSerializingConfigBase {

        public final BooleanValue redux_sky_colors;
        public final BooleanValue redux_water_colors;
        public final BooleanValue cloudbed;
        public final BooleanValue revamped_quicksoil_movement;
        public final IntValue max_veridium_tool_infusion;
        public final BooleanValue consistent_break_speeds;
        public final BooleanValue raw_ores;

        public Server(Builder builder) {
            super(() -> SERVER_SPEC, "redux_server", Redux.MODID);
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

    public static class Common extends ReduxSerializingConfigBase {

        public final BooleanValue bronze_dungeon_upgrade;
        public final EnumValue<AACompatType> redux_noise;

        public Common(Builder builder) {
            super(() -> COMMON_SPEC, "redux_common", Redux.MODID);
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

    public static class Client extends ReduxConfigBase {

        public final BooleanValue leaf_particles;
        public final BooleanValue improved_whirlwinds;

        public final BooleanValue tintable_grass;
        public final BooleanValue jappafied_textures;
        public final BooleanValue slider_sfx;
        public final BooleanValue slider_signal_sfx;

        public Client(Builder builder) {
            super(() -> CLIENT_SPEC, "redux_client", Redux.MODID);
            builder.push("Visual");

            leaf_particles = tranlateAndDefine(builder
                    .comment("Use nice falling leaf particles for Aether leaf blocks"),
                    "Leaf Particles", true, TranslatingConfig::defineBool);
            improved_whirlwinds = tranlateAndDefine(builder
                    .comment("Gives Whirlwinds a new design, based on Minecraft 1.21's new Breeze mob"),
                    "Improved Whirlwinds", true, TranslatingConfig::defineBool);

            builder.pop();

            builder.push("Audio");
            slider_signal_sfx = tranlateAndDefine(builder
                    .comment("Gives the Slider a subtle signal effect."),
                    "Slider Movement Signal", true);
            builder.pop();

            builder.push("Builtin Resource Pack Customization");

            tintable_grass = ReduxPackConfig.register(tranlateAndDefine(builder
                    .comment("Use modified models to allow tintable Aether Grass blocks and plants. Only disable if you know what you're doing!"),
                    "Tinted Grass", true), "resource/", "tintable_grass");
            jappafied_textures = ReduxPackConfig.register(tranlateAndDefine(builder
                    .comment("Use textures designed to fit with the Jappafied Aethers resource pack."),
                    "Jappafied Textures", false), "resource/", "jappafied");
            slider_sfx = ReduxPackConfig.register(tranlateAndDefine(builder
                    .comment("Improve the hurt, death, and ambient sounds of the Slider."),
                    "Slider SFX Upgrades", true), "resource/sfx/", "slider");

            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC, SERVER_SPEC, CLIENT_SPEC;
    public static final Common COMMON;
    public static final Server SERVER;
    public static final Client CLIENT;

    static {
        final Pair<Server, ModConfigSpec> server = new Builder().configure(Server::new);
        SERVER_SPEC = server.getRight();
        SERVER = server.getLeft();

        final Pair<Common, ModConfigSpec> common = new Builder().configure(Common::new);
        COMMON_SPEC = common.getRight();
        COMMON = common.getLeft();

        final Pair<Client, ModConfigSpec> client = new Builder().configure(Client::new);
        CLIENT_SPEC = client.getRight();
        CLIENT = client.getLeft();
    }

}
