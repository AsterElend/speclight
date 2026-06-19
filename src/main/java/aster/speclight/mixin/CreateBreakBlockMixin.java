package aster.speclight.mixin;

import aster.speclight.LinkedFakePlayer;
import aster.speclight.SpeclightRegistry;
import aster.speclight.UUIDComponent;
import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BlockBreakingKineticBlockEntity.class, remap = false)


public class CreateBreakBlockMixin {

	@Shadow
	protected BlockPos breakingPos;


	@Inject(at = @At("HEAD"), method = "onBlockBroken", cancellable = true)
	private void catchRevelations(BlockState stateToBreak, CallbackInfo ci) {
		UUIDComponent component = SpeclightRegistry.UUID_COMPONENT.get(
				(BlockBreakingKineticBlockEntity) (Object) this
		);
		if (!component.hasLink() ) return;

		//what do you mean, this is a mixin
		BlockBreakingKineticBlockEntity self =
				(BlockBreakingKineticBlockEntity) (Object) this;

		World world = self.getWorld();
		BlockPos pos = self.getPos(); // or wherever the target pos is stored

		if (!(world instanceof ServerWorld serverWorld)) return;

		LinkedFakePlayer fakePlayer = LinkedFakePlayer.fromUUID(
				serverWorld,
				component.getLinkedUUID()
		);

		if (fakePlayer == null) return; // couldn't resolve profile, let Create handle it normally

		// Shadow or accessor the tool stack Create uses if you need it,
		// otherwise ItemStack.EMPTY is fine for most cases
		BlockHelper.destroyBlockAs(
				world,
				pos,
				fakePlayer,
				ItemStack.EMPTY, // or shadow Create's stored tool
				1f,
				stack -> Block.dropStack(world, pos, stack)
		);

		ci.cancel();
	}



}