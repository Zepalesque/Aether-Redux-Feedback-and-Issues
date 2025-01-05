package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.ReduxTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ReduxEntityTagsData extends EntityTypeTagsProvider {
    public ReduxEntityTagsData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Redux.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(Provider provider) {
        this.tag(ReduxTags.Entities.VALID_PICKAXE_TARGETS).add(AetherEntityTypes.SLIDER.get());
        this.tag(ReduxTags.Entities.SENTRIES).add(AetherEntityTypes.SLIDER.get(), AetherEntityTypes.SENTRY.get());
        this.tag(ReduxTags.Entities.SENTRITE_MUSIC_DISC_DROPPING).addTag(ReduxTags.Entities.SENTRIES).remove(AetherEntityTypes.SLIDER.get());
    }
}
