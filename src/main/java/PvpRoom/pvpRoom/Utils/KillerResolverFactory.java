package PvpRoom.pvpRoom.Utils;
// ==== In the future it will be used! ====
public class KillerResolverFactory {

    public static KillerResolver getResolver() {
        String version = org.bukkit.Bukkit.getBukkitVersion().split("-")[0];
        switch (version) {
            case "1.20":
            case "1.21":
                return new KillerResolver1_20();
            case "1.19":
            default:
                return new KillerResolver1_19();
        }
    }
}
