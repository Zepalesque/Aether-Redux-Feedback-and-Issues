package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.function.Supplier;

public class ReduxConfigBase implements TranslatingConfig {

    private final Supplier<ModConfigSpec> spec;
    private final String id;
    private final String modid;

    public ReduxConfigBase(Supplier<ModConfigSpec> spec, String id, String modid) {
        this.spec = spec;
        this.id = id;
        this.modid = modid;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public String getModID() {
        return this.modid;
    }

    public Supplier<ModConfigSpec> getSpec() {
        return spec;
    }
}
