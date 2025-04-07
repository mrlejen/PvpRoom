package PvpRoom.pvpRoom.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
// ==== In the future it will be used! ====
public class KillerResolver1_19 implements KillerResolver {
    @Override
    public Player getKiller(PlayerDeathEvent event) {
        return event.getEntity().getKiller();
    }
}
