package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.mixin.mixins.common.accessor.ConfigBuilderAccessor;
import net.zepalesque.zenith.api.function.Functions;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public interface TranslatingConfig {

    String getID();

    String getModID();

    default <T> String translationFor(ModConfigSpec.ConfigValue<T> config) {
        List<String> path = config.getPath();

        return translationFor(path);
    }

    default String translationFor(List<String> path) {
        Stream<CharSequence> base = Stream.of("config", this.getModID(), this.getID());
        Stream<CharSequence> camel = path.stream().map(TranslatingConfig::toCamelCase);

        return String.join(".", Stream.concat(base, camel)::iterator);
    }

    default String translationFor(List<String> path, String id) {
        return translationFor(path) + "." + toCamelCase(id);
    }

    /**
     * Cancels out {@link net.zepalesque.zenith.api.data.DatagenUtil#localize(String)}
     */
    static String toCamelCase(String s) {
        return s.toLowerCase().replace(' ', '_');
    }

    default ModConfigSpec.Builder withTranslation(String name, ModConfigSpec.Builder builder) {
        builder.translation(translationFor(((ConfigBuilderAccessor) builder).redux$getCurrentPath(), name));
        return builder;
    }

    default <T, C extends ModConfigSpec.ConfigValue<T>, A extends T> C tranlateAndDefine(ModConfigSpec.Builder builder, String name, A arg1, Functions.F3<ModConfigSpec.Builder, String, A, C> definition) {
        withTranslation(name, builder);
        return definition.apply(builder, name, arg1);
    }

    default ModConfigSpec.BooleanValue tranlateAndDefine(ModConfigSpec.Builder builder, String name, boolean defaultValue) {
        withTranslation(name, builder);
        return builder.define(name, defaultValue);
    }

    static ModConfigSpec.BooleanValue defineBool(ModConfigSpec.Builder builder, String name, Boolean arg1) {
        return builder.define(name, arg1.booleanValue());
    }
}
