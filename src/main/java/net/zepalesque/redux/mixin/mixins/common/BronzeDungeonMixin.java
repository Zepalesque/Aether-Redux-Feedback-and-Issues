package net.zepalesque.redux.mixin.mixins.common;

import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonRoom;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.zepalesque.redux.mixin.ReduxDungeonProcessors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(BronzeDungeonRoom.class)
public class BronzeDungeonMixin {

    @WrapOperation(method = "makeSettings", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;<init>()V"))
    private static StructurePlaceSettings redux$makeSettings(Operation<StructurePlaceSettings> original) {
        return original.call().addProcessor(ReduxDungeonProcessors.BRONZE_BLOCKS).addProcessor(ReduxDungeonProcessors.BRONZE_TRAPS);
    }
}
