package net.zepalesque.redux.blockset.flower.type;

import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;

import java.util.function.Supplier;

public class EnchantedFlowerSet<B extends Block> extends TintedFlowerSet<B> {
    public EnchantedFlowerSet(String id, String textureFolder, Supplier<B> constructor, int tintdex, int itemTint) {
        super(id, textureFolder, constructor, tintdex, itemTint);
    }

    @Override
    public void blockData(ReduxBlockStateProvider data) {
        data.crossEnchantableOverlay(this.flower().get(), this.textureFolder);
        data.potPrefix(this.pot().get(), this.flower().get(), this.textureFolder, "enchanted_");
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        data.itemBlockFlatPrefix(this.flower().get(), this.textureFolder, "enchanted_");
    }
}
