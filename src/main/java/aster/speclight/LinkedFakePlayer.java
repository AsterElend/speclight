package aster.speclight;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LinkedFakePlayer extends ServerPlayerEntity {

    private LinkedFakePlayer(ServerWorld world, GameProfile profile) {
        super(
                world.getServer(),
                world,
                profile
        );
    }

    public static @Nullable LinkedFakePlayer fromUUID(ServerWorld world, UUID uuid) {
        // Try online first — if they're on the server, use their real profile
        ServerPlayerEntity online = world.getServer().getPlayerManager().getPlayer(uuid);
        GameProfile profile = online != null
                ? online.getGameProfile()
                : world.getServer().getUserCache().getByUuid(uuid).orElse(null);

        if (profile == null) return null; // UUID not resolvable — bail out

        return new LinkedFakePlayer(world, profile);
    }


}
