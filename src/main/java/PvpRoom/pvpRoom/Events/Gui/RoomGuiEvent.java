package PvpRoom.pvpRoom.Events.Gui;

import PvpRoom.pvpRoom.Commands.PvpRoom;
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

import java.util.ArrayList;
import java.util.Set;

public class RoomGuiEvent implements Listener {
    private final Main plugin;

    public RoomGuiEvent(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void event(InventoryClickEvent e){
        if (e.getView().getTitle().equals("§dRooms§5 Select")) {
            if (e.getCurrentItem() == null) return;
            Material clickedItem = e.getCurrentItem().getType();

            FileConfiguration config = plugin.getRoomDataConfig();
            Player player = (Player) e.getWhoClicked();
            if (clickedItem == Material.BARRIER) {
                e.setCancelled(true);
            }

            if (e.isLeftClick()) {
                if (String.valueOf(e.getAction()).equals("PICKUP_ALL")) {
                    Set<String> roomKeys = config.getConfigurationSection("rooms").getKeys(false);
                    for (String key : roomKeys) {
                        String name = config.getString("rooms." + key + ".name");
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§b["+name+"] §aRoom ")) {
                            Inventory gui = Bukkit.createInventory(null, 27, "§b["+name+"] §aRoom ");

                            for(int i=0; i<=26; i++){
                                gui.setItem(i, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                                if(i == 12){
                                    String gameblock = config.getString("rooms." + key + ".doorblock.block");
                                    if (gameblock != null) {
                                        Material material = Material.getMaterial(gameblock);
                                        ItemStack block = new ItemStack(material);
                                        ItemMeta meta2 = block.getItemMeta();
                                        if (meta2 != null) {
                                            meta2.setDisplayName("§aDoor Block");
                                            ArrayList<String> menuLore = new ArrayList<>();
                                            menuLore.add("You can replace this block with the block you want.");
                                            meta2.setLore(menuLore);
                                            meta2.addEnchant(Enchantment.QUICK_CHARGE,5,true);
                                            block.setItemMeta(meta2);
                                        }
                                        gui.setItem(12, block);
                                    }

                                }
                                if(i == 14){
                                    if (config.get("rooms." + key + ".status") != null) {
                                        Boolean status = config.getBoolean("rooms." + key + ".status");
                                        if(status.equals(true)){
                                            gui.setItem(14, PvpRoom.createItem(Material.GREEN_WOOL, "§eRoom Status §aOn"));
                                        }
                                        if(status.equals(false)){
                                            gui.setItem(14, PvpRoom.createItem(Material.RED_WOOL, "§eRoom Status §cOff"));
                                        }

                                    }

                                }
                                if(i == 1){
                                    gui.setItem(1, PvpRoom.createItem(Material.CLOCK, "§dDoor§5 Ti§dmes Settings"));
                                }

                                if(i == 7){
                                    gui.setItem(7, PvpRoom.createItem(Material.PLAYER_HEAD, "§dPla§5yer Count Sett§dings"));
                                }
                                if(i == 19){
                                    gui.setItem(19, PvpRoom.createItem(Material.NETHER_STAR, "§dWinner§5 Items"));
                                }

                                if(i == 25){
                                    gui.setItem(25, PvpRoom.createItem(Material.END_CRYSTAL, "§7Back"));
                                }
                            }
                            player.openInventory(gui);
                            e.setCancelled(true);
                        }
                    }
                }
            }
            if(e.getClickedInventory().getType() != InventoryType.PLAYER){

                e.setCancelled(true);


            }



        }
    }
}
