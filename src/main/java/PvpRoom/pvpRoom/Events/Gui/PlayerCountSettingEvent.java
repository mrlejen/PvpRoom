package PvpRoom.pvpRoom.Events.Gui;
import PvpRoom.pvpRoom.Commands.PvpRoom;
import PvpRoom.pvpRoom.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

import java.util.Set;

public class PlayerCountSettingEvent implements Listener {
    private final Main plugin;

    public PlayerCountSettingEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        FileConfiguration config = plugin.getRoomDataConfig();
        Player player = (Player) e.getWhoClicked();

        Set<String> roomKeys = config.getConfigurationSection("rooms").getKeys(false);
        for (String key : roomKeys) {
            String name = config.getString("rooms." + key + ".name");

            if (e.getView().getTitle().equals("§b["+name+"] §aPlayers§5 Count")) {


                if (e.getCurrentItem() == null) return;
                Material clickedItem = e.getCurrentItem().getType();
                if (clickedItem == Material.BARRIER) {
                    e.setCancelled(true);
                }

                if (e.isLeftClick()) {

                    if (String.valueOf(e.getAction()).equals("PICKUP_ALL")) {
                        //add
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a+1 Player")) {
                            int playercount = config.getInt("rooms." + key + ".player.count");
                            config.set("rooms." + key + ".player.count", playercount + 1);
                            plugin.saveRoomDataConfig();
                            e.getInventory().setItem(4, PvpRoom.createItem(Material.PLAYER_HEAD, "Player Count §a-" + config.getInt("rooms." + key + ".player.count") + "-"));
                            e.setCancelled(true);
                            return;
                        }

                        //remove
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c-1 Player")) {
                            int playercount = config.getInt("rooms." + key + ".player.count");
                            if (playercount <= 2) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! You cannot specify less than 2.");
                                e.setCancelled(true);
                                return;
                            }
                            config.set("rooms." + key + ".player.count", playercount - 1);
                            plugin.saveRoomDataConfig();
                            e.getInventory().setItem(4, PvpRoom.createItem(Material.PLAYER_HEAD, "Player Count §a-" + config.getInt("rooms." + key + ".player.count") + "-"));
                            e.setCancelled(true);
                            return;
                        }
                    }

                }
                if (e.getClickedInventory().getType() != InventoryType.PLAYER) {

                    e.setCancelled(true);
                    return;


                }

            }
        }
    }
}
