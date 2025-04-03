package me.mrlejen.pvpRoom.Commands;

import me.mrlejen.pvpRoom.Data.SelectData;
import me.mrlejen.pvpRoom.Events.SelectEvent;
import me.mrlejen.pvpRoom.Main;
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
                            "§e/pvproom select door - Select The Door\n"+
                            "§e/pvproom select room - Select The Room\n"+
                            "§e/pvproom status on - Turn On The Plugin\n"+
                            "§e/pvproom status off - Turn Off The Plugin\n"+
                            "§e/pvproom settings - Plugin Settings\n"+
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
                            "§e/pvproom select door - To Select The Door\n" +
                            "§e/pvproom select room - To Select The Room");
                    return true;
                case "status":
                    player.sendMessage("§b[PvpRoom] §aAvailable status commands:\n" +
                            "§e/pvproom status on - To Enable The Plugin\n" +
                            "§e/pvproom status off - To Disable The Plugin");
                    return true;
                case "settings":
                    String gameblock = plugin.getDataConfig().getString("doorblock.block");
                    Boolean status = plugin.getDataConfig().getBoolean("plugin.status");

                    if(status.equals(false)){
                        player.sendMessage("§b[PvpRoom] §cCan you Type §5/pvproom status on");
                        return true;
                    }
                    if (gameblock != null) {
                        Material material = Material.getMaterial(gameblock);
                        ItemStack sword2 = new ItemStack(material);
                        ItemMeta meta2 = sword2.getItemMeta();
                        if (meta2 != null) {
                            meta2.setDisplayName("§aDoor Block");
                            ArrayList<String> menuLore = new ArrayList<>();
                            menuLore.add("You can replace this block with the block you want.");
                            meta2.setLore(menuLore);
                            meta2.addEnchant(Enchantment.QUICK_CHARGE,5,true);
                            sword2.setItemMeta(meta2);
                        }
                        Inventory gui = Bukkit.createInventory(null, 9, "§dSett§5ings");
                        gui.setItem(2, sword2);
                        gui.setItem(4, createItem(Material.CLOCK, "§dDoor§5 Ti§dmes"));
                        gui.setItem(6, createItem(Material.EMERALD, "§eCus§6tom §dSett§5ings"));
                        gui.setItem(0, createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(1, createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(3, createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(5, createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(7, createItem(Material.BARRIER, "§CNUN"));
                        gui.setItem(8, createItem(Material.BARRIER, "§CNUN"));
                        player.openInventory(gui);
                        return true;
                    }
                    ItemStack sword2 = new ItemStack(Material.GLASS);
                    ItemMeta meta2 = sword2.getItemMeta();
                    if (meta2 != null) {
                        meta2.setDisplayName("§aDoor Block");
                        ArrayList<String> menuLore = new ArrayList<>();
                        menuLore.add("You can replace this block with the block you want.");
                        meta2.setLore(menuLore);
                        meta2.addEnchant(Enchantment.QUICK_CHARGE,5,true);
                        sword2.setItemMeta(meta2);
                    }
                    Inventory  gui = Bukkit.createInventory(null, 9, "§dSett§5ings");
                    gui.setItem(2, sword2);
                    gui.setItem(4, createItem(Material.CLOCK, "§dDoor§5 Times"));
                    gui.setItem(6, createItem(Material.EMERALD, "§eCus§6tom §dSett§5ings"));
                    gui.setItem(0, createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(1, createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(3, createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(5, createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(7, createItem(Material.BARRIER, "§CNUN"));
                    gui.setItem(8, createItem(Material.BARRIER, "§CNUN"));
                    player.openInventory(gui);
                    return true;
                default:
                    player.sendMessage("§b[PvpRoom] §cInvalid usage! Type §5/pvproom help§c for a list of commands.");
                    return true;
            }
        }









        if (args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "door":
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
                    plugin.getDataConfig().set("door."+player.getWorld().getName()+"."+"x",storedLocation.getBlockX());
                    plugin.getDataConfig().set("door."+player.getWorld().getName()+"."+"y",storedLocation.getBlockY());
                    plugin.getDataConfig().set("door."+player.getWorld().getName()+"."+"z",storedLocation.getBlockZ());
                    plugin.getDataConfig().set("door."+player.getWorld().getName()+"."+"x2",storedLocation2.getBlockX());
                    plugin.getDataConfig().set("door."+player.getWorld().getName()+"."+"y2",storedLocation2.getBlockY());
                    plugin.getDataConfig().set("door."+player.getWorld().getName()+"."+"z2",storedLocation2.getBlockZ());
                    plugin.saveDataConfig();

                    return true;
                case "room":
                    HashMap<UUID, SelectData> firstPositionn = selectEvent.getFirstPosition();
                    HashMap<UUID, SelectData> secondPositionn = selectEvent.getSecondPosition();
                    UUID playerUUIDD = player.getUniqueId();
                    SelectData dataa = firstPositionn.get(playerUUIDD);
                    SelectData dataa2 = secondPositionn.get(playerUUIDD);
                    if (dataa == null){
                        player.sendMessage("§b[PvpRoom] §cCan you select the First Position?\nType §5/pvproom selector§c to get select tool");
                        return false;
                    }
                    if (dataa2 == null){
                        player.sendMessage("§b[PvpRoom] §cCan you select the Second Position?\nType §5/pvproom selector§c to get select tool");
                        return false;
                    }
                    UUID storedUUIDD = dataa.getPlayerUUID();
                    Location storedLocationn = dataa.getBlockLocation();
                    Location storedLocationn2 = dataa2.getBlockLocation();
                    player.sendMessage("§b[PvpRoom] §aDone Select The Room");
                    plugin.getDataConfig().set("room."+player.getWorld().getName()+"."+"x",storedLocationn.getBlockX());
                    plugin.getDataConfig().set("room."+player.getWorld().getName()+"."+"y",storedLocationn.getBlockY());
                    plugin.getDataConfig().set("room."+player.getWorld().getName()+"."+"z",storedLocationn.getBlockZ());
                    plugin.getDataConfig().set("room."+player.getWorld().getName()+"."+"x2",storedLocationn2.getBlockX());
                    plugin.getDataConfig().set("room."+player.getWorld().getName()+"."+"y2",storedLocationn2.getBlockY());
                    plugin.getDataConfig().set("room."+player.getWorld().getName()+"."+"z2",storedLocationn2.getBlockZ());
                    plugin.saveDataConfig();
                    return true;
                case "on":
                    FileConfiguration config = plugin.getDataConfig();
                    String worldName = player.getWorld().getName();
                    String x1 = config.getString("room." + worldName + ".x");
                    String y1 = config.getString("room." + worldName + ".y");
                    String z1 = config.getString("room." + worldName + ".z");
                    String x2 = config.getString("room." + worldName + ".x2");
                    String y2 = config.getString("room." + worldName + ".y2");
                    String z2 = config.getString("room." + worldName + ".z2");
                    String xx1 = config.getString("door." + worldName + ".x");
                    String yy1 = config.getString("door." + worldName + ".y");
                    String zz1 = config.getString("door." + worldName + ".z");
                    String xx2 = config.getString("door." + worldName + ".x2");
                    String yy2 = config.getString("door." + worldName + ".y2");
                    String zz2 = config.getString("door." + worldName + ".z2");
                    if(x1 == null || x2 == null || xx1 == null || xx2 == null || y1 == null || y2 == null|| yy1 == null || yy2 == null || z1 == null || z2 == null || zz1 == null || zz2 == null){
                        player.sendMessage("§b[PvpRoom] §cCan you select the (Room | Door) Position?\nType §5/pvproom select door§c AND §5/pvproom select room");
                        return true;
                    }
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
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("help", "selector", "info","select","status","settings");
        }
        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "select":
                    return Arrays.asList("door", "room");
                case "status":
                    return Arrays.asList("on", "off");
            }
        }
        return null;
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
