package me.mrlejen.pvpRoom.Data;

import org.bukkit.Location;
import java.util.UUID;

public class SelectData {
    private final UUID playerUUID;
    private final Location blockLocation;

    public SelectData(UUID playerUUID, Location blockLocation) {
        this.playerUUID = playerUUID;
        this.blockLocation = blockLocation;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public Location getBlockLocation() {
        return blockLocation;
    }
}
