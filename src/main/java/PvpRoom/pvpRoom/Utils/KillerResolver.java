package PvpRoom.pvpRoom.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
// ==== In the future it will be used! ====
public interface KillerResolver {
    Player getKiller(PlayerDeathEvent event);
}
