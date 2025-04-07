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

            FileConfiguration config = plugin.getDataConfig();
            Player player = (Player) e.getWhoClicked();
            Boolean status = plugin.getDataConfig().getBoolean("plugin.status");

            if(status.equals(false)) return;

            if (clickedItem == Material.BARRIER) {
                e.setCancelled(true);
            }

            if (e.isLeftClick()) {
                if (String.valueOf(e.getAction()).equals("SWAP_WITH_CURSOR")) {
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§aDoor Block")) {
                        ItemStack Item = new ItemStack(e.getCursor());
                        ItemStack Item2 = new ItemStack(e.getCurrentItem());
                        ItemStack Item3 = new ItemStack(Material.AIR);
                        ItemMeta meta2 = Item.getItemMeta();
                        if (meta2 != null) {
                            meta2.addEnchant(Enchantment.QUICK_CHARGE,5,true);
                            Item.setItemMeta(meta2);
                        }
                       if(!e.getCursor().getType().isBlock()){
                           player.sendMessage("§b[PvpRoom] §cInvalid usage! You can replace this block with the Blocks.");
                           e.setCancelled(true);
                           return;
                       }

                        plugin.getDataConfig().set("doorblock.block",e.getCursor().getType().name());
                        plugin.saveDataConfig();
                        e.setCurrentItem(Item);
                        e.setCursor(Item3);

                        e.setCancelled(true);
                        return;
                    }
                    e.setCancelled(true);

                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dDoor§5 Ti§dmes")) {
                    Inventory gui = Bukkit.createInventory(null, 45, "§dDoor§5 Ti§dmes Sett§dings");
                    gui.setItem(0, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(1, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(2, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(4, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(6, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(7, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(8, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(9, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(10, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(11, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(13, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(15, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(16, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(17, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(18, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(19, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(20, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(22, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(23, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(24, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(25, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(26, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(27, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(28, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(29, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(30, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(31, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(33, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(34, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(35, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(36, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(37, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(38, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(40, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(42, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(43, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(44, PvpRoom.createItem(Material.BARRIER, "§CNUN"));







                    gui.setItem(3, PvpRoom.createItem(Material.GREEN_WOOL, "§a+5 Open"));
                    gui.setItem(5, PvpRoom.createItem(Material.GREEN_WOOL, "§a+5 Close"));
                    gui.setItem(12, PvpRoom.createItem(Material.GREEN_WOOL, "§a+1 Open"));
                    gui.setItem(14, PvpRoom.createItem(Material.GREEN_WOOL, "§a+1 Close"));
                    gui.setItem(30, PvpRoom.createItem(Material.RED_WOOL, "§4-1 Open"));
                    gui.setItem(32, PvpRoom.createItem(Material.RED_WOOL, "§4-1 Close"));
                    gui.setItem(39, PvpRoom.createItem(Material.RED_WOOL, "§4-5 Open"));
                    gui.setItem(41, PvpRoom.createItem(Material.RED_WOOL, "§4-5 Close"));
                    String closedoortime1 = plugin.getDataConfig().getString("time.door.close");
                    String opendoortime1 = plugin.getDataConfig().getString("time.door.open");
                    if(opendoortime1 == null){
                        gui.setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-2-"));
                    }
                    if(closedoortime1 == null){
                        gui.setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-2-"));
                    }
                    if(closedoortime1 != null){
                        int closedoortime = plugin.getDataConfig().getInt("time.door.close");
                        int closedoortime2 = (int) (closedoortime / 20);
                        gui.setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-"+closedoortime2+"-"));
                    }
                    if(opendoortime1 != null){
                        int opendoortime = plugin.getDataConfig().getInt("time.door.open");
                        int opendoortime2 = (int) (opendoortime / 20);
                        gui.setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-"+opendoortime2+"-"));
                    }




                    player.openInventory(gui);
                }










                if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§eCus§6tom §dSett§5ings")) {
                    Inventory gui = Bukkit.createInventory(null, 45, "§eCus§6tom §dSett§5ings");
                    gui.setItem(0, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(1, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(2, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(4, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(6, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(7, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(8, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(9, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(10, PvpRoom.createItem(Material.PLAYER_HEAD, "§dPla§5yer Count Sett§dings"));
                    gui.setItem(11, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(13, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(15, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(16, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(17, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(18, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(19, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(20, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(22, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(23, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(24, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(25, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(26, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(27, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(28, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(29, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(30, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(31, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(33, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(34, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(35, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(36, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(37, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(38, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(40, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(42, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(43, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(44, PvpRoom.createItem(Material.BARRIER, "§CNUN"));







                    gui.setItem(3, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(5, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(12, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(14, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(30, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(32, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(39, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(41, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(21, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(23, PvpRoom.createItem(Material.BARRIER, "§CNUN"));

                    player.openInventory(gui);
                }
























            }
               if(e.getClickedInventory().getType() != InventoryType.PLAYER){

                   e.setCancelled(true);


            }


        }
    }
}
