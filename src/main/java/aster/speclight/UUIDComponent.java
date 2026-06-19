package aster.speclight;

import dev.onyxstudios.cca.api.v3.component.Component;

import java.util.UUID;

public interface UUIDComponent extends Component {
    UUID getLinkedUUID();
    void setLinkedUUID(UUID player);
     boolean hasLink();
}
