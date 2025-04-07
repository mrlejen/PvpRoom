package PvpRoom.pvpRoom.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.lang.reflect.Method;
// ==== In the future it will be used! ====
public class VersionUtil {

    public static String getMinecraftVersion() {
        return Bukkit.getBukkitVersion().split("-")[0];
    }

    public static Player getKiller(PlayerDeathEvent event) {
        String version = getMinecraftVersion();

        if (version.startsWith("1.19")) {
            return event.getEntity().getKiller();
        }

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
