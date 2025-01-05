package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ReduxBiomeTagsData extends BiomeTagsProvider {

    public ReduxBiomeTagsData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, @Nullable ExistingFileHelper helper) {
        super(output, registries, Redux.MODID, helper);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        this.tag(AetherTags.Biomes.IS_AETHER).add(
                ReduxBiomes.GILDED_GROVES,
                ReduxBiomes.THE_BLIGHT
        );
        this.tag(ReduxTags.Biomes.HAS_CLOUDBED).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.MODIFY_MUSIC).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.MODIFY_SKY_COLOR).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.MODIFY_WATER_COLOR).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_SENTRITE).addTag(AetherTags.Biomes.IS_AETHER);
        this.tag(ReduxTags.Biomes.HAS_WYNDSPROUTS).add(
                AetherBiomes.SKYROOT_GROVE,
                AetherBiomes.SKYROOT_MEADOW,
                AetherBiomes.SKYROOT_WOODLAND);
    }
}
