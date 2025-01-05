package net.zepalesque.redux.blockset.util;

public interface MutableLoreGeneration<T extends MutableLoreGeneration<T>> extends ReduxGeneration {
    T withLore(String lore);
}
