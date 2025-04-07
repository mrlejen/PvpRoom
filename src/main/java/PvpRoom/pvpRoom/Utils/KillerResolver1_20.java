package PvpRoom.pvpRoom.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.lang.reflect.Method;
// ==== In the future it will be used! ====
public class KillerResolver1_20 implements KillerResolver {
    @Override
    public Player getKiller(PlayerDeathEvent event) {
        try {
            Method getDamageSource = event.getClass().getMethod("getDamageSource");
            Object damageSource = getDamageSource.invoke(event);
            Method getCausingEntity = damageSource.getClass().getMethod("getCausingEntity");
            Object causingEntity = getCausingEntity.invoke(damageSource);
            if (causingEntity instanceof Player) {
                return (Player) causingEntity;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
