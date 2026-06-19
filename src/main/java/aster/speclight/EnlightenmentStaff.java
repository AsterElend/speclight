package aster.speclight;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import de.dafuqs.spectrum.api.energy.InkCost;
import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

import java.util.List;

public class EnlightenmentStaff extends Item implements InkPowered {

    public static final int INK_COST = 100;

    public EnlightenmentStaff(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext ctx){
        if (ctx.getWorld().isClient ||
                !(ctx.getWorld().getBlockEntity(ctx.getBlockPos()) instanceof BlockBreakingKineticBlockEntity bbkbe))
            return ActionResult.SUCCESS;
        if (InkPowered.tryDrainEnergy(ctx.getPlayer(), new InkCost(InkColors.GRAY, INK_COST))) {
            SpeclightRegistry.UUID_COMPONENT.get(bbkbe).setLinkedUUID(ctx.getPlayer().getUuid());
            ctx.getPlayer().playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 1f, 1f);
            return ActionResult.SUCCESS;
        }

        return ActionResult.FAIL;

    }


    @Override
    public List<InkColor> getUsedColors() {
        return List.of(InkColors.GRAY);
    }
}
