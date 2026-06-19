package aster.speclight.mixin;

import aster.speclight.SpeclightRegistry;
import aster.speclight.UUIDComponent;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.utility.CreateLang;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.UUID;

@Mixin(value = KineticBlockEntity.class, remap = false)
public abstract class KineticGoggleTooltipMixin {

    @Inject(
            method = "addToGoggleTooltip",
            at = @At("HEAD")
    )
    private void speclight$addLinkedPlayerTooltip(List<Text> tooltip, boolean isPlayerSneaking, CallbackInfoReturnable<Boolean> cir) {
        // Only act on BlockBreakingKineticBlockEntity subclasses
        if (!((Object) this instanceof BlockBreakingKineticBlockEntity bbkbe)) return;
        UUIDComponent component = SpeclightRegistry.UUID_COMPONENT.get(bbkbe);
        if (!component.hasLink()) return;
        UUID linkedUUID = component.getLinkedUUID();
        String name = bbkbe.getWorld().getServer().getUserCache().getByUuid(linkedUUID).toString();
        CreateLang.translate("speclight.gui.goggles.linked_player", name).forGoggles(tooltip);

    }






}