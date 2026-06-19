package aster.speclight;

import com.simibubi.create.content.kinetics.base.BlockBreakingKineticBlockEntity;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.NbtCompound;

import java.util.UUID;

public class EnlightenmentAttachComponent implements UUIDComponent, AutoSyncedComponent {
    private final BlockBreakingKineticBlockEntity bbkbe;
    private UUID linkedPlayer = null;

    public EnlightenmentAttachComponent(BlockBreakingKineticBlockEntity bbkbe){
        this.bbkbe = bbkbe;
    }

     @Override
    public UUID getLinkedUUID(){
         return linkedPlayer;
     }

     @Override
        public void setLinkedUUID(UUID newlink){
         this.linkedPlayer = newlink;

     }

     @Override
     public boolean hasLink(){
         return linkedPlayer != null;
     }

     @Override
    public void readFromNbt(NbtCompound nbt){
         this.linkedPlayer = nbt.getUuid("linkedPlayer");
     }

     @Override
    public void writeToNbt(NbtCompound nbt){
         nbt.putUuid("linkedPlayer", linkedPlayer);
     }

}
