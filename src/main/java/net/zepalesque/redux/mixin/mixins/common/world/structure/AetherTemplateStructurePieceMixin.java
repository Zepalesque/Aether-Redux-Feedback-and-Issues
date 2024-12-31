package net.zepalesque.redux.mixin.mixins.common.world.structure;

import com.aetherteam.aether.world.structurepiece.AetherTemplateStructurePiece;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonRoom;
import net.minecraft.core.Holder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.zepalesque.redux.mixin.ReduxDungeonProcessors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AetherTemplateStructurePiece.class)
public class AetherTemplateStructurePieceMixin {
    // TODO: PR to make this possible without mixins
/*    @Inject(method = "addProcessors", at = @At(value = "RETURN"), cancellable = true)
    protected static void redux$makeSettings(StructurePlaceSettings settings, Holder<StructureProcessorList> processors, CallbackInfoReturnable<StructurePlaceSettings> cir) {
        if ( instanceof BronzeDungeonRoom) {
            StructurePlaceSettings original = cir.getReturnValue();
            StructurePlaceSettings modified = original.addProcessor(ReduxDungeonProcessors.BRONZE_BLOCKS).addProcessor(ReduxDungeonProcessors.BRONZE_TRAPS);
            cir.setReturnValue(modified);
        }
    }*/
}
