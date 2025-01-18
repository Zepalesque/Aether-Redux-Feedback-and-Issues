package net.zepalesque.redux.mixin.mixins.common.world.structure.piece;

import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeBossRoom;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonRoom;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.zepalesque.redux.mixin.ReduxDungeonProcessors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(BronzeBossRoom.class)
public class BronzeBossMixin {

    // TODO: PR to make this possible without mixins
    @WrapOperation(method = "makeSettings", at = @At(value = "NEW", target = "()Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;"))
    private static StructurePlaceSettings redux$makeSettings(Operation<StructurePlaceSettings> original) {
        return original.call().addProcessor(ReduxDungeonProcessors.BRONZE_LOCKED).addProcessor(ReduxDungeonProcessors.BRONZE_BLOCKS);
    }
}
