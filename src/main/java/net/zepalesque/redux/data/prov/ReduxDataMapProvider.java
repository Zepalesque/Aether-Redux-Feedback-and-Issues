package net.zepalesque.redux.data.prov;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.zepalesque.unity.data.prov.UnityDataMapProvider;

import java.util.concurrent.CompletableFuture;

public abstract class ReduxDataMapProvider extends UnityDataMapProvider {

    protected ReduxDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }
}
