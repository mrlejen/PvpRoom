package me.mrlejen.pvpRoom.Events.Gui;

import me.mrlejen.pvpRoom.Commands.PvpRoom;
import me.mrlejen.pvpRoom.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CustomSettingsEvent implements Listener {
    private final Main plugin;

    public CustomSettingsEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("§eCus§6tom §dSett§5ings")) {
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
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dPla§5yer Count Sett§dings")) {
                        Inventory gui = Bukkit.createInventory(null, 9, "§dPla§5yer Count Sett§dings");
                        gui.setItem(0, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(1, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(2, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(3, PvpRoom.createItem(Material.RED_WOOL, "§c-1 Player"));
                        int playercount = plugin.getDataConfig().getInt("player.count");
                        gui.setItem(4, PvpRoom.createItem(Material.PLAYER_HEAD, "Player Count §a-"+playercount+"-"));
                        gui.setItem(5, PvpRoom.createItem(Material.GREEN_WOOL, "§a+1 Player"));
                        gui.setItem(6, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(7, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(8, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        player.openInventory(gui);
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
