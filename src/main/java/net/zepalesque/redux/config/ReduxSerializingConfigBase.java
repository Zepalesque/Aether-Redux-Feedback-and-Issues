package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.zenith.api.serialization.config.DataSerializableConfig;

import java.util.function.Supplier;

public abstract class ReduxSerializingConfigBase extends DataSerializableConfig implements TranslatingConfig {
    private final String modid;

    public ReduxSerializingConfigBase(Supplier<ModConfigSpec> spec, String id, String modid) {
        super(spec, id);
        this.modid = modid;
    }

    @Override
    public String getID() {
        return this.serializerID();
    }

    @Override
    public String getModID() {
        return this.modid;
    }
}
