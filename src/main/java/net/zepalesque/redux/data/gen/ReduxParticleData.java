package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.ArrayUtil;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.data.prov.ReduxParticleProvider;

import java.util.List;

public class ReduxParticleData extends ReduxParticleProvider {
    public ReduxParticleData(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(ReduxParticles.GILDENROOT_LEAF.get(), Redux.loc("leaves/gildenroot"));

        ResourceLocation[] lightning_textures = ArrayUtil.generateContents(new ResourceLocation[10], i -> Redux.loc("lightning/lightning" + i));
        this.spriteSet(ReduxParticles.WHIRLWIND_LIGHTNING.get(), List.of(lightning_textures));
    }
}
