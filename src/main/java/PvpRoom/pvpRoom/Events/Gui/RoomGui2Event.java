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

public class RoomGui2Event implements Listener {
    private final Main plugin;

    public RoomGui2Event(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void event(InventoryClickEvent e) {
        FileConfiguration config = plugin.getRoomDataConfig();
        Player player = (Player) e.getWhoClicked();
        Set<String> roomKeys = config.getConfigurationSection("rooms").getKeys(false);
        for (String key : roomKeys) {
            String name = config.getString("rooms." + key + ".name");


        if (e.getView().getTitle().equals("§b["+name+"] §aRoom ")) {
            if (e.getCurrentItem() == null) return;
            Material clickedItem = e.getCurrentItem().getType();


            if (clickedItem == Material.BARRIER) {
                e.setCancelled(true);
            }

            if (e.isLeftClick()) {
                if (String.valueOf(e.getAction()).equals("PICKUP_ALL")) {

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§7Back")) {
                        Inventory gui = Bukkit.createInventory(null, 45, "§dRooms§5 Select");
                        int index = 0;
                        if (config.get("rooms") != null) {
                            Set<String> roomKeys2 = config.getConfigurationSection("rooms").getKeys(false);
                            for (String key2 : roomKeys2) {
                                if (index <= 44) {
                                    String name2 = config.getString("rooms." + key2 + ".name");
                                    String gameblock2 = config.getString("rooms." + key2 + ".doorblock.block");
                                    if (name2 != null && gameblock2 != null) {
                                        Material material = Material.getMaterial(gameblock2);
                                        ItemStack block = new ItemStack(material);
                                        ItemMeta meta2 = block.getItemMeta();
                                        if (meta2 != null) {
                                            meta2.setDisplayName("§b["+name2+"] §aRoom ");
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












                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§eRoom Status §aOn")) {
                        e.getInventory().setItem(14, PvpRoom.createItem(Material.RED_WOOL, "§eRoom Status §cOff"));
                        config.set(("rooms." + key + ".status"), false);
                        plugin.saveRoomDataConfig();
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§eRoom Status §cOff")) {
                        String worldName = player.getWorld().getName();
                        String x1 = config.getString("rooms." + key + ".room." + worldName + ".x");
                        String y1 = config.getString("rooms." + key + ".room." + worldName + ".y");
                        String z1 = config.getString("rooms." + key + ".room." + worldName + ".z");
                        String x2 = config.getString("rooms." + key + ".room." + worldName + ".x2");
                        String y2 = config.getString("rooms." + key + ".room." + worldName + ".y2");
                        String z2 = config.getString("rooms." + key + ".room." + worldName + ".z2");
                        String xx1 = config.getString("rooms." + key + ".door." + worldName + ".x");
                        String yy1 = config.getString("rooms." + key + ".door." + worldName + ".y");
                        String zz1 = config.getString("rooms." + key + ".door." + worldName + ".z");
                        String xx2 = config.getString("rooms." + key + ".door." + worldName + ".x2");
                        String yy2 = config.getString("rooms." + key + ".door." + worldName + ".y2");
                        String zz2 = config.getString("rooms." + key + ".door." + worldName + ".z2");
                        if (x1 == null || x2 == null || xx1 == null || xx2 == null || y1 == null || y2 == null || yy1 == null || yy2 == null || z1 == null || z2 == null || zz1 == null || zz2 == null) {
                            player.sendMessage("§b[PvpRoom] §cCan you select the (Room | Door) Position?\nType §5/pvproom select door <Room Name>§c AND §5/pvproom select room <Room Name>");
                            e.setCancelled(true);
                            return;
                        }
                        e.getInventory().setItem(14, PvpRoom.createItem(Material.GREEN_WOOL, "§eRoom Status §aOn"));
                        config.set(("rooms." + key + ".status"), true);
                        plugin.saveRoomDataConfig();
                        e.setCancelled(true);
                        return;
                    }
                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dDoor§5 Ti§dmes Settings")) {
                        Inventory gui = Bukkit.createInventory(null, 45, "§b[" + name + "] §aDoor§5 Ti§dmes");
                        for (int i = 0; i <= 44; i++) {
                            gui.setItem(i, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                            if (i == 3) {
                                gui.setItem(3, PvpRoom.createItem(Material.GREEN_WOOL, "§a+5 Open"));
                            }
                            if (i == 5) {
                                gui.setItem(5, PvpRoom.createItem(Material.GREEN_WOOL, "§a+5 Close"));
                            }
                            if (i == 12) {
                                gui.setItem(12, PvpRoom.createItem(Material.GREEN_WOOL, "§a+1 Open"));
                            }
                            if (i == 14) {
                                gui.setItem(14, PvpRoom.createItem(Material.GREEN_WOOL, "§a+1 Close"));
                            }
                            if (i == 30) {
                                gui.setItem(30, PvpRoom.createItem(Material.RED_WOOL, "§4-1 Open"));
                            }
                            if (i == 32) {
                                gui.setItem(32, PvpRoom.createItem(Material.RED_WOOL, "§4-1 Close"));
                            }
                            if (i == 39) {
                                gui.setItem(39, PvpRoom.createItem(Material.RED_WOOL, "§4-5 Open"));
                            }
                            if (i == 41) {
                                gui.setItem(41, PvpRoom.createItem(Material.RED_WOOL, "§4-5 Close"));
                            }
                            String closedoortime1 = config.getString("rooms." + key + ".time.door.close");
                            String opendoortime1 = config.getString("rooms." + key + ".time.door.open");
                            if (opendoortime1 == null) {
                                gui.setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-2-"));
                            }
                            if (closedoortime1 == null) {
                                gui.setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-2-"));
                            }
                            if (closedoortime1 != null) {
                                int closedoortime = config.getInt("rooms." + key + ".time.door.close");
                                int closedoortime2 = (int) (closedoortime / 20);
                                gui.setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-" + closedoortime2 + "-"));
                            }
                            if (opendoortime1 != null) {
                                int opendoortime = config.getInt("rooms." + key + ".time.door.close");
                                int opendoortime2 = (int) (opendoortime / 20);
                                gui.setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-" + opendoortime2 + "-"));
                            }
                        }
                        player.openInventory(gui);
                        e.setCancelled(true);
                        return;
                    }


                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dPla§5yer Count Sett§dings")) {
                        Inventory gui = Bukkit.createInventory(null, 9, "§b[" + name + "] §aPlayers§5 Count");
                        for (int i = 0; i <= 8; i++) {
                            gui.setItem(i, PvpRoom.createItem(Material.BARRIER, "§CNUN"));
                            if (i == 3) {
                                gui.setItem(3, PvpRoom.createItem(Material.GREEN_WOOL, "§a+1 Player"));
                            }
                            if (i == 5) {
                                gui.setItem(5, PvpRoom.createItem(Material.RED_WOOL, "§c-1 Player"));
                            }
                            if (i == 4) {
                                int playercount = config.getInt("rooms." + key + ".player.count");
                                gui.setItem(4, PvpRoom.createItem(Material.PLAYER_HEAD, "Player Count §a-" + playercount + "-"));
                            }


                        }
                        player.openInventory(gui);
                        e.setCancelled(true);
                        return;
                    }

                    if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§dWinner§5 Items")) {
                        Inventory gui = Bukkit.createInventory(null, 45, "§b[" + name + "] §dWinner§5 Items");
                        int index = 0;

                        if (config.getConfigurationSection("rooms." + key + ".items") != null) {
                            Set<String> itemKeys = config.getConfigurationSection("rooms." + key + ".items").getKeys(false);

                            for (String itemKey : itemKeys) {
                                ItemStack theItem = config.getItemStack("rooms." + key + ".items." + itemKey + ".item");
                                try {
                                    int slot = Integer.parseInt(itemKey.replace("sort", ""));
                                    if (slot >= 0 && slot < 45 && theItem != null) {
                                        gui.setItem(slot, theItem);
                                    }
                                } catch (NumberFormatException ex) {

                                }
                            }
                        }


                        for (int i = 0; i < 45; i++) {
                            if (gui.getItem(i) == null || gui.getItem(i).getType() == Material.AIR) {
                                ItemStack filler = new ItemStack(Material.BARRIER);
                                ItemMeta meta = filler.getItemMeta();
                                if (meta != null) {
                                    meta.setDisplayName("§cNUN");
                                    ArrayList<String> menuLore = new ArrayList<>();
                                    menuLore.add("You can replace this block with the item you want.");
                                    menuLore.add("To Delete The Item Drop It.");
                                    meta.setLore(menuLore);
                                    meta.addEnchant(Enchantment.QUICK_CHARGE, 5, true);
                                    filler.setItemMeta(meta);
                                }
                                gui.setItem(i, filler);
                            }
                        }

                        player.openInventory(gui);
                        e.setCancelled(true);
                        return;
                    }
                }
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
                        config.set(("rooms." + key + ".doorblock.block"),e.getCursor().getType().name());
                        plugin.saveRoomDataConfig();
                        e.setCurrentItem(Item);
                        e.setCursor(Item3);

                        e.setCancelled(true);
                        return;
                    }
                }
            }
            if (e.getClickedInventory().getType() != InventoryType.PLAYER) {

                e.setCancelled(true);


            }

        }

        }
    }
}
