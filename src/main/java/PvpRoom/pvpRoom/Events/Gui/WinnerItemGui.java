package PvpRoom.pvpRoom.Events.Gui;

import PvpRoom.pvpRoom.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;

public class WinnerItemGui implements Listener {
    private final Main plugin;

    public WinnerItemGui(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void event(InventoryClickEvent e) {
        FileConfiguration config = plugin.getRoomDataConfig();
        Player player = (Player) e.getWhoClicked();
        Set<String> roomKeys = config.getConfigurationSection("rooms").getKeys(false);
        for (String key : roomKeys) {
            String name = config.getString("rooms." + key + ".name");


            if (e.getView().getTitle().equals("§b["+name+"] §dWinner§5 Items")) {
                if (e.getCurrentItem() == null) return;
                Material clickedItem = e.getCurrentItem().getType();
                if (String.valueOf(e.getAction()).equals("SWAP_WITH_CURSOR")) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§cNUN")) {
                        ItemStack Item = new ItemStack(e.getCursor());
                        ItemStack Item2 = new ItemStack(e.getCurrentItem());
                        ItemStack Item3 = new ItemStack(Material.AIR);
                        config.set("rooms." + key + ".items.sort" +  e.getSlot() + ".item", e.getCursor());
                        plugin.saveRoomDataConfig();
                        e.setCurrentItem(Item);
                        e.setCursor(Item3);

                        e.setCancelled(true);
                        return;
                    }
                }
                if (String.valueOf(e.getAction()).equals("DROP_ONE_SLOT") || String.valueOf(e.getAction()).equals("DROP_ALL_SLOT")) {
                    if (clickedItem == Material.BARRIER) {
                        e.setCancelled(true);
                        return;
                    }
                    config.set("rooms." + key + ".items.sort" +  e.getSlot(),null);
                    e.setCurrentItem(new ItemStack(Material.AIR));
                    plugin.saveRoomDataConfig();
                    e.setCancelled(true);
                    return;
                }
                if (clickedItem == Material.BARRIER) {
                    e.setCancelled(true);
                }
                if(e.getClickedInventory().getType() != InventoryType.PLAYER){

                    e.setCancelled(true);


                }

            }
        }
    }
}
