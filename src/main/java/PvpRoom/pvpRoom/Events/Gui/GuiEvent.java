package PvpRoom.pvpRoom.Events.Gui;

import PvpRoom.pvpRoom.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import PvpRoom.pvpRoom.Commands.PvpRoom;

import java.util.ArrayList;
import java.util.Set;

public class GuiEvent implements Listener {
    private final Main plugin;

    public GuiEvent(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        if (e.getView().getTitle().equals("§dSett§5ings")) {
            if (e.getCurrentItem() == null) return;
            Material clickedItem = e.getCurrentItem().getType();

            FileConfiguration config = plugin.getRoomDataConfig();
            Player player = (Player) e.getWhoClicked();
            if (clickedItem == Material.BARRIER) {
                e.setCancelled(true);
            }

            if (e.isLeftClick()) {
                if (String.valueOf(e.getAction()).equals("PICKUP_ALL")) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dRooms§5 Sett§5ings")) {
                        Inventory gui = Bukkit.createInventory(null, 45, "§dRooms§5 Select");
                            int index = 0;
                        if (config.get("rooms") != null) {
                            Set<String> roomKeys = config.getConfigurationSection("rooms").getKeys(false);
                            for (String key : roomKeys) {
                                if (index <= 44) {
                                    String name = config.getString("rooms." + key + ".name");
                                    String gameblock = config.getString("rooms." + key + ".doorblock.block");
                                    if (name != null && gameblock != null) {
                                        Material material = Material.getMaterial(gameblock);
                                        ItemStack block = new ItemStack(material);
                                        ItemMeta meta2 = block.getItemMeta();
                                        if (meta2 != null) {
                                            meta2.setDisplayName("§b["+name+"] §aRoom ");
                                            ArrayList<String> menuLore = new ArrayList<>();
                                            menuLore.add("Click To Show Room Gui.");
                                            meta2.setLore(menuLore);
                                            meta2.addEnchant(Enchantment.QUICK_CHARGE,5,true);
                                            block.setItemMeta(meta2);
                                        }
                                        gui.setItem(index, block);
                                    }
                                    index++;
                                }
                            }
                        }
                        for (int i = index; i < 45; i++) {
                            gui.setItem(i, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                        }
                        player.openInventory(gui);
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§ePlugin Status §aOn")) {
                        e.getInventory().setItem(4, PvpRoom.createItem(Material.RED_WOOL, "§ePlugin Status §cOff"));
                        plugin.getDataConfig().set("plugin.status", false);
                        plugin.saveDataConfig();
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§ePlugin Status §cOff")) {
                        e.getInventory().setItem(4, PvpRoom.createItem(Material.GREEN_WOOL, "§ePlugin Status §aOn"));
                        plugin.getDataConfig().set("plugin.status", true);
                        plugin.saveDataConfig();
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§9Discord Link")) {
                        player.closeInventory();
                        player.sendMessage("§6Discord Link : §9https://discord.gg/PtSgvBrWGS");
                        e.setCancelled(true);
                        return;
                    }
                }
            }
               if(e.getClickedInventory().getType() != InventoryType.PLAYER){

                   e.setCancelled(true);


            }


        }
    }
}
