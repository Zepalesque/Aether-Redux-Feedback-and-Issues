package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherLanguageProvider;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zepalesque.unity.data.prov.UnityLanguageProvider;
import net.zepalesque.zenith.api.data.DatagenUtil;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ReduxLanguageProvider extends UnityLanguageProvider {
    public ReduxLanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void addConfigs(ModConfigSpec spec) {
        spec.getValues().entrySet().forEach(this::genFor);
    }

    protected <T extends UnmodifiableConfig.Entry> void genFor(T entry) {
        if (entry instanceof ModConfigSpec.ConfigValue<?> val) {
            ModConfigSpec.ValueSpec spec = val.getSpec();
            String translation = spec.getTranslationKey(), comment = spec.getComment();
            if (translation != null && comment != null) {
                this.add(translation, comment);
            }
        }
    }
}
