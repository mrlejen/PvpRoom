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

public class PlayerCountSettingEvent implements Listener {
    private final Main plugin;

    public PlayerCountSettingEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("§dPla§5yer Count Sett§dings")) {
            if (e.getCurrentItem() == null) return;
            Material clickedItem = e.getCurrentItem().getType();

            FileConfiguration config = plugin.getDataConfig();
            Player player = (Player) e.getWhoClicked();
            Boolean status = plugin.getDataConfig().getBoolean("plugin.status");

            if (status.equals(false)) return;

            if (clickedItem == Material.BARRIER) {
                e.setCancelled(true);
            }

            if (e.isLeftClick()) {

                if (String.valueOf(e.getAction()).equals("PICKUP_ALL")) {
                    //add
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a+1 Player")) {
                        int playercount = plugin.getDataConfig().getInt("player.count");
                        plugin.getDataConfig().set("player.count",playercount +1);
                        plugin.saveDataConfig();
                        e.getInventory().setItem(4,PvpRoom.createItem(Material.PLAYER_HEAD, "Player Count §a-"+plugin.getDataConfig().getInt("player.count")+"-"));
                        e.setCancelled(true);
                        return;
                    }

                    //remove
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§c-1 Player")) {
                        int playercount = plugin.getDataConfig().getInt("player.count");
                        if(playercount <= 2){
                            player.sendMessage("§b[PvpRoom] §cInvalid usage! You cannot specify less than 2.");
                            e.setCancelled(true);
                            return;
                        }
                        plugin.getDataConfig().set("player.count", playercount-1);
                        plugin.saveDataConfig();
                        e.getInventory().setItem(4,PvpRoom.createItem(Material.PLAYER_HEAD, "Player Count §a-"+plugin.getDataConfig().getInt("player.count")+"-"));
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
