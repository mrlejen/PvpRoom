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

public class DoorGuiEvent implements Listener {
    private final Main plugin;

    public DoorGuiEvent(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void click(InventoryClickEvent e){
        FileConfiguration config = plugin.getRoomDataConfig();
        Player player = (Player) e.getWhoClicked();
        Set<String> roomKeys = config.getConfigurationSection("rooms").getKeys(false);
        for (String key : roomKeys) {
            String name = config.getString("rooms." + key + ".name");

            if (e.getView().getTitle().equals("§b["+name+"] §aDoor§5 Ti§dmes")) {
                if (e.getCurrentItem() == null) return;
                Material clickedItem = e.getCurrentItem().getType();

                if (clickedItem == Material.BARRIER) {
                    e.setCancelled(true);
                }

                if (e.isLeftClick()) {

                    if (String.valueOf(e.getAction()).equals("PICKUP_ALL")) {

                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a+5 Open")) {
                            int number = 20 * 5;
                            String opendoortime1 = config.getString("rooms." + key + ".time.door.open");
                            if (opendoortime1 == null) {
                                config.set("rooms." + key + ".time.door.open", number);
                            }
                            if (opendoortime1 != null) {
                                int number2 =config.getInt("rooms." + key + ".time.door.open");
                                config.set("rooms." + key + ".time.door.open", number2 + 20 + 20 + 20 + 20 + 20);
                            }

                            plugin.saveRoomDataConfig();
                            int opendoortime = config.getInt("rooms." + key + ".time.door.open");
                            int opendoortime2 = (int) (opendoortime / 20);
                            e.getInventory().setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-" + opendoortime2 + "-"));

                            e.setCancelled(true);
                            return;
                        }

                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a+1 Open")) {
                            String opendoortime1 = config.getString("rooms." + key + ".time.door.open");
                            if (opendoortime1 == null) {
                                config.set("rooms." + key + ".time.door.open", 20);
                            }
                            if (opendoortime1 != null) {
                                int number2 = config.getInt("rooms." + key + ".time.door.open");
                                config.set("rooms." + key + ".time.door.open", number2 + 20);
                            }

                            plugin.saveRoomDataConfig();
                            int opendoortime = config.getInt("rooms." + key + ".time.door.open");
                            int opendoortime2 = (int) (opendoortime / 20);
                            e.getInventory().setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-" + opendoortime2 + "-"));

                            e.setCancelled(true);
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4-5 Open")) {
                            String opendoortime1 = config.getString("rooms." + key + ".time.door.open");
                            if (opendoortime1 == null) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just 2.");
                                e.setCancelled(true);
                                return;
                            }


                            int number2 = config.getInt("rooms." + key + ".time.door.open");
                            int number3 = (int) (number2 / 20);
                            if (number3 <= 5) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just " + number3 + ".");
                                e.setCancelled(true);
                                return;
                            }
                            config.set("rooms." + key + ".time.door.open", number2 - 20 - 20 - 20 - 20 - 20);


                            plugin.saveRoomDataConfig();
                            int opendoortime = config.getInt("rooms." + key + ".time.door.open");
                            e.getInventory().setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-" + opendoortime / 20 + "-"));

                            e.setCancelled(true);
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4-1 Open")) {
                            String opendoortime1 = config.getString("rooms." + key + ".time.door.open");
                            if (opendoortime1 == null) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just 2.");
                                e.setCancelled(true);
                                return;
                            }


                            int number2 = config.getInt("rooms." + key + ".time.door.open");
                            int number3 = (int) (number2 / 20);
                            if (number3 <= 1) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just " + config.getInt("rooms." + key + ".time.door.open") / 20 + ".");
                                e.setCancelled(true);
                                return;
                            }
                           config.set("rooms." + key + ".time.door.open", number2 - 20);


                            plugin.saveRoomDataConfig();
                            e.getInventory().setItem(21, PvpRoom.createItem(Material.OAK_DOOR, "§8Open Door §2-" + config.getInt("rooms." + key + ".time.door.open") / 20 + "-"));

                            e.setCancelled(true);
                            return;
                        }


                        //close door


                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a+5 Close")) {
                            int number = 20 * 5;
                            String closedoortime1 = config.getString("rooms." + key + ".time.door.close");
                            if (closedoortime1 == null) {
                                config.set("rooms." + key + ".time.door.close", number);
                            }
                            if (closedoortime1 != null) {
                                int number2 = config.getInt("rooms." + key + ".time.door.close");
                                config.set("rooms." + key + ".time.door.close", number2 + 20 + 20 + 20 + 20 + 20);
                            }

                            plugin.saveRoomDataConfig();
                            int closedoortime = config.getInt("rooms." + key + ".time.door.close");
                            int closedoortime2 = (int) (closedoortime / 20);
                            e.getInventory().setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-" + closedoortime2 + "-"));

                            e.setCancelled(true);
                            return;
                        }

                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§a+1 Close")) {
                            String closedoortime1 = config.getString("rooms." + key + ".time.door.close");
                            if (closedoortime1 == null) {
                                config.set("rooms." + key + ".time.door.close", 20);
                            }
                            if (closedoortime1 != null) {
                                int number2 = config.getInt("rooms." + key + ".time.door.close");
                                config.set("rooms." + key + ".time.door.close", number2 + 20);
                            }

                            plugin.saveRoomDataConfig();
                            int closedoortime = config.getInt("rooms." + key + ".time.door.close");
                            int closedoortime2 = (int) (closedoortime / 20);
                            e.getInventory().setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-" + closedoortime2 + "-"));

                            e.setCancelled(true);
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4-5 Close")) {
                            String closedoortime1 = config.getString("rooms." + key + ".time.door.close");
                            if (closedoortime1 == null) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just 2.");
                                e.setCancelled(true);
                                return;
                            }


                            int number2 = config.getInt("rooms." + key + ".time.door.close");
                            int number3 = (int) (number2 / 20);
                            if (number3 <= 5) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just " + number3 + ".");
                                e.setCancelled(true);
                                return;
                            }
                            config.set("rooms." + key + ".time.door.close", number2 - 20 - 20 - 20 - 20 - 20);


                            plugin.saveRoomDataConfig();
                            int closedoortime = config.getInt("rooms." + key + ".time.door.close");
                            e.getInventory().setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-" + closedoortime / 20 + "-"));


                            e.setCancelled(true);
                            return;
                        }
                        if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§4-1 Close")) {
                            String closedoortime1 = config.getString("rooms." + key + ".time.door.close");
                            if (closedoortime1 == null) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just 2.");
                                e.setCancelled(true);
                                return;
                            }


                            int number2 = config.getInt("rooms." + key + ".time.door.close");
                            int number3 = (int) (number2 / 20);
                            if (number3 <= 1) {
                                player.sendMessage("§b[PvpRoom] §cInvalid usage! the time just " + config.getInt("rooms." + key + ".time.door.close") / 20 + ".");
                                e.setCancelled(true);
                                return;
                            }
                            config.set("rooms." + key + ".time.door.close", number2 - 20);


                            plugin.saveRoomDataConfig();
                            e.getInventory().setItem(23, PvpRoom.createItem(Material.IRON_DOOR, "§8Close Door §2-" + config.getInt("rooms." + key + ".time.door.close") / 20 + "-"));

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
