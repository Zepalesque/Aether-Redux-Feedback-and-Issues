package net.zepalesque.redux.mixin.mixins.common.world.structure.piece;

import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonRoom;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.zepalesque.redux.mixin.ReduxDungeonProcessors;
import net.zepalesque.redux.mixin.mixins.common.world.structure.AetherTemplateStructurePieceMixin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(BronzeDungeonRoom.class)
public class BronzeDungeonMixin extends AetherTemplateStructurePieceMixin {

}
