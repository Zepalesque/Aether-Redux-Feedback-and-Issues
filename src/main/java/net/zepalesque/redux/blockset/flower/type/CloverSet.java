package net.zepalesque.redux.blockset.flower.type;

import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;

import java.util.function.Supplier;

public class CloverSet<B extends Block> extends BaseFlowerSet<B>{
    public CloverSet(String id, String textureFolder, Supplier<B> constructor) {
        super(id, textureFolder, constructor);
    }

    @Override
    public void blockData(ReduxBlockStateProvider data) {
        data.clover(this.flower().get(), this.textureFolder);
        data.pottedClover(this.pot().get(), this.flower().get(), this.textureFolder);
    }

    // Clover should not use potted prefix
    @Override
    public BaseFlowerSet<B> withPottedPrefix() {
        return this;
    }

    @Override
    public void itemData(ReduxItemModelProvider data) {
        data.itemBlockFlatCustomTexture(this.flower().get(), this.textureFolder + data.blockName(this.flower.get()) + "_top");
    }
}
