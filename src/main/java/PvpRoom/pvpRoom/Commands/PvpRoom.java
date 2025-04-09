package PvpRoom.pvpRoom.Commands;

import PvpRoom.pvpRoom.Data.SelectData;
import PvpRoom.pvpRoom.Events.SelectEvent;
import PvpRoom.pvpRoom.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PvpRoom implements CommandExecutor, TabCompleter , Listener {
    private final Main plugin;
    private final SelectEvent selectEvent;;
    public PvpRoom(Main plugin, SelectEvent selectEvent) {
        this.plugin = plugin;
        this.selectEvent = selectEvent;

    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.isOp()) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }




        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "help":
                    player.sendMessage("§b[PvpRoom] §aAvailable commands:\n" +
                            "§e/pvproom info - Plugin Info\n" +
                            "§e/pvproom selector - Get Selector Item\n" +
                            "§e/pvproom select door <Room Name> - Select The Door\n"+
                            "§e/pvproom select room <Room Name> - Select The Room\n"+
                            "§e/pvproom status on - Turn On The Plugin\n"+
                            "§e/pvproom status off - Turn Off The Plugin\n"+
                            "§e/pvproom settings - Plugin Settings\n"+
                            "§e/pvproom create <Room Name> - Create A New Room\n"+
                            "§e/pvproom remove <Room Name> - Remove A Room\n"+
                            "§e/pvproom delete <Room Name> - Delete A Room\n"+
                            "§e/pvproom help - Show Help message\n");
                    return true;
                case "selector":
                    ItemStack sword = new ItemStack(Material.GOLDEN_SWORD);
                    ItemMeta meta = sword.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName(ChatColor.AQUA + "PvpRoom Selector");
                        ArrayList<String> menuLore = new ArrayList<>();
                        menuLore.add("You Can Select The Position By This Item");
                        meta.setLore(menuLore);
                        meta.addEnchant(Enchantment.QUICK_CHARGE,5,true);
                        sword.setItemMeta(meta);
                    }
                    player.getInventory().addItem(sword);
                    player.sendMessage("§b[PvpRoom] §eDone Get The Selector Item");

                    return true;
                case "info":
                        PluginDescriptionFile pdf = this.plugin.getDescription();
                        player.sendMessage("§b[PvpRoom] §ePlugin information:\n"+
                                "§7Version:§d "+pdf.getVersion()+"\n"+
                                "§7Authors:§d "+pdf.getAuthors()+"\n"+
                                "§7Description:§d "+pdf.getDescription()+"\n"+
                                "§7Website:§d "+pdf.getWebsite()+"\n");

                    return true;
                case "select":
                    player.sendMessage("§b[PvpRoom] §aAvailable Select commands:\n" +
                            "§e/pvproom select door <Room Name> - To Select The Door\n" +
                            "§e/pvproom select room <Room Name> - To Select The Room");
                    return true;
                case "status":
                    player.sendMessage("§b[PvpRoom] §aAvailable status commands:\n" +
                            "§e/pvproom status on - To Enable The Plugin\n" +
                            "§e/pvproom status off - To Disable The Plugin");
                    return true;
                case "settings":
                    String Check = plugin.getRoomDataConfig().getString("rooms");
                    if(Check == null){
                        player.sendMessage("§b[PvpRoom] §cInvalid usage! You need to create a room first:\n" +
                                "§e/pvproom create lejenroom - To Enable The Plugin");
                        return true;
                    }
                    Inventory gui = Bukkit.createInventory(null, 9, "§dSett§5ings");
                    for(int i=0; i<=8; i++){
                        if(i !=2 && i !=4 && i !=6){
                            gui.setItem(i, createItem(Material.BARRIER, "§CNUN"));
                        }
                        if(i ==2){
                            gui.setItem(2, createItem(Material.OAK_DOOR, "§dRooms§5 Sett§5ings"));
                        }
                        if(i ==4){
                            if (plugin.getDataConfig().get("plugin.status") != null) {
                        Boolean status = plugin.getDataConfig().getBoolean("plugin.status");
                        if(status.equals(true)){
                            gui.setItem(4, createItem(Material.GREEN_WOOL, "§ePlugin Status §aOn"));
                        }
                        if(status.equals(false)){
                            gui.setItem(4, createItem(Material.RED_WOOL, "§ePlugin Status §cOff"));
                        }

                    }
                        }
                        if(i ==6){
                            gui.setItem(i, createItem(Material.BLUE_CANDLE, "§9Discord Link"));
                        }
                    }
                    player.openInventory(gui);
                    return true;
                case "create":
                    player.sendMessage("§b[PvpRoom] §aAvailable status commands:\n" +
                            "§e/pvproom create <Room Name> - To Create The Room\n");
                    return true;
                case "remove":
                    player.sendMessage("§b[PvpRoom] §aAvailable status commands:\n" +
                            "§e/pvproom remove <Room Name> - To Create The Room\n");
                    return true;
                case "delete":
                    player.sendMessage("§b[PvpRoom] §aAvailable status commands:\n" +
                            "§e/pvproom delete <Room Name> - To Create The Room\n");
                    return true;
                default:
                    player.sendMessage("§b[PvpRoom] §cInvalid usage! Type §5/pvproom help§c for a list of commands.");
                    return true;
            }
        }




        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("create")) {
                FileConfiguration config = plugin.getRoomDataConfig();
                if (config.get("rooms") == null) {
                    config.set("rooms." + 1 + ".time.door.open", 40);
                    config.set("rooms." + 1 + ".time.door.close", 40);
                    config.set("rooms." + 1 + ".player.count", 2);
                    config.set("rooms." + 1 + ".doorblock.block", "GLASS");
                    config.set("rooms." + 1 + ".name", args[1]);
                    config.set("rooms." + 1 + ".status", false);
                    plugin.saveRoomDataConfig();
                    player.sendMessage("§b[PvpRoom] §2Room created successfully! Name: §5" + args[1]);
                    return true;
                }
                for (String key : config.getConfigurationSection("rooms").getKeys(false)) {
                    String name = config.getString("rooms." + key + ".name");
                    if (name != null && name.equalsIgnoreCase(args[1])) {
                        player.sendMessage("§b[PvpRoom] §cThis room name is already taken.");
                        return true;
                    }
                    int count = config.getConfigurationSection("rooms").getKeys(false).size();
                    if(count >= 45){
                        player.sendMessage("§b[PvpRoom] §cThe Max Room Number Is 45.");
                        return true;
                    }
                }
                int lastRoomId = config.getConfigurationSection("rooms").getKeys(false).stream()
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0);

                int newRoomId = lastRoomId + 1;

                config.set("rooms." + newRoomId + ".time.door.open", 40);
                config.set("rooms." + newRoomId + ".time.door.close", 40);
                config.set("rooms." + newRoomId + ".player.count", 2);
                config.set("rooms." + newRoomId + ".doorblock.block", "GLASS");
                config.set("rooms." + newRoomId + ".name", args[1]);
                config.set("rooms." + newRoomId + ".status", false);
                plugin.saveRoomDataConfig();

                player.sendMessage("§b[PvpRoom] §2Room created successfully! Name: §5" + args[1]);
                return true;
            }




            if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")) {
                FileConfiguration config = plugin.getRoomDataConfig();
                if (config.get("rooms") == null) {
                    player.sendMessage("§b[PvpRoom] §c There No Rooms To Delete It");
                    return true;
                }
                for (String key : config.getConfigurationSection("rooms").getKeys(false)) {
                    String name = config.getString("rooms." + key + ".name");
                    if (name != null && name.equalsIgnoreCase(args[1])) {
                        config.set("rooms." + key, null);
                        plugin.saveRoomDataConfig();
                        player.sendMessage("§b[PvpRoom] §2Room Deleted successfully!.");
                        return true;
                    }
                }
                player.sendMessage("§b[PvpRoom] §cThere Is No Room In That Name.");
                return true;
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("select")) {
                FileConfiguration config = plugin.getRoomDataConfig();
                if (config.get("rooms") == null) {
                    player.sendMessage("§b[PvpRoom] §c There No Rooms §e Use §a/pvproom create §eCommand To Create New Rooms");
                    return true;
                }
                if (args[1].equalsIgnoreCase("door")) {
                    for (String key : config.getConfigurationSection("rooms").getKeys(false)) {
                        String name = config.getString("rooms." + key + ".name");
                        if (name != null && name.equalsIgnoreCase(args[2])) {
                            HashMap<UUID, SelectData> firstPosition = selectEvent.getFirstPosition();
                            HashMap<UUID, SelectData> secondPosition = selectEvent.getSecondPosition();
                            UUID playerUUID = player.getUniqueId();
                            SelectData data = firstPosition.get(playerUUID);
                            SelectData data2 = secondPosition.get(playerUUID);
                            if (data == null){
                                player.sendMessage("§b[PvpRoom] §cCan you select the First Position?\nType §5/pvproom selector§c to get select tool");
                                return false;
                            }
                            if (data2 == null){
                                player.sendMessage("§b[PvpRoom] §cCan you select the Second Position?\nType §5/pvproom selector§c to get select tool");
                                return false;
                            }
                            UUID storedUUID = data.getPlayerUUID();
                            Location storedLocation = data.getBlockLocation();
                            Location storedLocation2 = data2.getBlockLocation();
                            player.sendMessage("§b[PvpRoom] §aDone Select The Door");
                            config.set("rooms." + key +".door."+player.getWorld().getName()+"."+"x",storedLocation.getBlockX());
                            config.set("rooms." + key +".door."+player.getWorld().getName()+"."+"y",storedLocation.getBlockY());
                            config.set("rooms." + key +".door."+player.getWorld().getName()+"."+"z",storedLocation.getBlockZ());
                            config.set("rooms." + key +".door."+player.getWorld().getName()+"."+"x2",storedLocation2.getBlockX());
                            config.set("rooms." + key +".door."+player.getWorld().getName()+"."+"y2",storedLocation2.getBlockY());
                            config.set("rooms." + key +".door."+player.getWorld().getName()+"."+"z2",storedLocation2.getBlockZ());
                            plugin.saveRoomDataConfig();
                            return true;
                        }
                    }
                    player.sendMessage("§b[PvpRoom] §cThere Is No Room In That Name.");
                    return true;
                }


                if (args[1].equalsIgnoreCase("room")) {
                    for (String key : config.getConfigurationSection("rooms").getKeys(false)) {
                        String name = config.getString("rooms." + key + ".name");
                        if (name != null && name.equalsIgnoreCase(args[2])) {
                            HashMap<UUID, SelectData> firstPosition = selectEvent.getFirstPosition();
                            HashMap<UUID, SelectData> secondPosition = selectEvent.getSecondPosition();
                            UUID playerUUID = player.getUniqueId();
                            SelectData data = firstPosition.get(playerUUID);
                            SelectData data2 = secondPosition.get(playerUUID);
                            if (data == null){
                                player.sendMessage("§b[PvpRoom] §cCan you select the First Position?\nType §5/pvproom selector§c to get select tool");
                                return false;
                            }
                            if (data2 == null){
                                player.sendMessage("§b[PvpRoom] §cCan you select the Second Position?\nType §5/pvproom selector§c to get select tool");
                                return false;
                            }
                            UUID storedUUID = data.getPlayerUUID();
                            Location storedLocation = data.getBlockLocation();
                            Location storedLocation2 = data2.getBlockLocation();
                            player.sendMessage("§b[PvpRoom] §aDone Select The Room");
                            config.set("rooms." + key +".room."+player.getWorld().getName()+"."+"x",storedLocation.getBlockX());
                            config.set("rooms." + key +".room."+player.getWorld().getName()+"."+"y",storedLocation.getBlockY());
                            config.set("rooms." + key +".room."+player.getWorld().getName()+"."+"z",storedLocation.getBlockZ());
                            config.set("rooms." + key +".room."+player.getWorld().getName()+"."+"x2",storedLocation2.getBlockX());
                            config.set("rooms." + key +".room."+player.getWorld().getName()+"."+"y2",storedLocation2.getBlockY());
                            config.set("rooms." + key +".room."+player.getWorld().getName()+"."+"z2",storedLocation2.getBlockZ());
                            plugin.saveRoomDataConfig();
                            return true;
                        }
                    }
                    player.sendMessage("§b[PvpRoom] §cThere Is No Room In That Name.");
                    return true;
                }
            }
        }




        if (args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "door":
                    player.sendMessage("§b[PvpRoom] §aAvailable Select commands:\n" +
                            "§e/pvproom select door <Room Name> - To Select The Door");
                    return true;
                case "room":
                    player.sendMessage("§b[PvpRoom] §aAvailable Select commands:\n" +
                            "§e/pvproom select room <Room Name> - To Select The Room");
                    return true;
                case "on":
                    plugin.getDataConfig().set("plugin.status",true);
                    player.sendMessage("§b[PvpRoom] §aDone Enable The Plugin.");
                    plugin.saveDataConfig();
                    return true;
                case "off":
                    plugin.getDataConfig().set("plugin.status",false);
                    plugin.saveDataConfig();
                    player.sendMessage("§b[PvpRoom] §aDone Disable The Plugin.");
                    return true;
            }
        }
        player.sendMessage("§b[PvpRoom] §cInvalid usage! Type §5/pvproom help§c for a list of commands.");
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "create", "remove", "delete", "selector", "info", "select", "status", "settings");
        }

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "select":
                    return Arrays.asList("door", "room");

                case "status":
                    return Arrays.asList("on", "off");

                case "create":
                    return Arrays.asList("room1", "room2", "room3", "room4", "room5");

                case "delete":
                case "remove":
                    FileConfiguration config = plugin.getRoomDataConfig();
                    if (config.getConfigurationSection("rooms") == null) return Collections.emptyList();

                    List<String> names = new ArrayList<>();
                    for (String key : config.getConfigurationSection("rooms").getKeys(false)) {
                        String name = config.getString("rooms." + key + ".name");
                        if (name != null) names.add(name);
                    }
                    return names;
            }
        }
        if (args.length == 3) {
            switch (args[1].toLowerCase()) {
                case "door":
                case "room":
                    FileConfiguration config = plugin.getRoomDataConfig();
                    if (config.getConfigurationSection("rooms") == null) return Collections.emptyList();

                    List<String> names = new ArrayList<>();
                    for (String key : config.getConfigurationSection("rooms").getKeys(false)) {
                        String name = config.getString("rooms." + key + ".name");
                        if (name != null) names.add(name);
                    }
                    return names;
            }
        }

        return Collections.emptyList();
    }

    public static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        return item;
    }

}
