package net.zepalesque.redux.blockset.flower.type;

import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.zenith.api.function.Consumers;

import java.util.function.Supplier;

public class AetherFlowerSet<B extends Block> extends TintedFlowerSet<B> {
    public AetherFlowerSet(String id, String textureFolder, Supplier<B> constructor, int tintdex, int itemTint) {
        super(id, textureFolder, constructor, tintdex, itemTint);
    }

    @Override
    public void blockData(ReduxBlockStateProvider data) {
        data.crossTintedOverlay(this.flower().get(), this.textureFolder);
        Consumers.C3<Block, Block, String> pot = this.usePottedPrefix ? data::tintedPotOverlayAlt : data::tintedPotOverlay;
        pot.accept(this.pot().get(), this.flower().get(), this.textureFolder);
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        data.itemBlockFlatTintOverlay(this.flower().get(), this.textureFolder);
    }
}
