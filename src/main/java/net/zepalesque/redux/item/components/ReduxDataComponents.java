package net.zepalesque.redux.item.components;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

import java.util.function.UnaryOperator;

public class ReduxDataComponents {
    public static final DeferredRegister<DataComponentType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, Redux.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> INFUSION = register("veridium_infusion", builder -> builder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT));

    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return TYPES.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}
