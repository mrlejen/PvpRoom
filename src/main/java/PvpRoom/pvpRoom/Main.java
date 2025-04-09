package PvpRoom.pvpRoom;

import PvpRoom.pvpRoom.Commands.PvpRoom;
import PvpRoom.pvpRoom.Events.*;
import PvpRoom.pvpRoom.Events.Gui.*;

import PvpRoom.pvpRoom.Events.Update.UpdateVersionEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {

    private File dataFile;
    private File roomDataFile;

    private FileConfiguration dataConfig;
    private FileConfiguration roomDataConfig;

    @Override
    public void onEnable() {


        getConfig().set("Version", "0.4-Alpha");
        saveDefaultConfig();
        saveConfig();


        createDataFile();
        createRoomDataFile();

//== Data Config ==
        if (getDataConfig().get("plugin.status") == null) {
            getDataConfig().set("plugin.status", false);
        }
        saveDataConfig();




        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[PvpRoom] Plugin is enabled!");

        // Register commands
        SelectEvent selectEvent = new SelectEvent();
        PvpRoom pvpRoomCommand = new PvpRoom(this, selectEvent);
        getCommand("pvproom").setExecutor(pvpRoomCommand);
        getCommand("pvproom").setTabCompleter(pvpRoomCommand);

        // Register events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(selectEvent, this);
        pluginManager.registerEvents(new MoveEvent(this), this);
        pluginManager.registerEvents(new DieEvent(this), this);
        pluginManager.registerEvents(new QuitEvent(this), this);
        pluginManager.registerEvents(new TeleportEvent(this), this);
        pluginManager.registerEvents(new GuiEvent(this), this);
        pluginManager.registerEvents(new DoorGuiEvent(this), this);
        pluginManager.registerEvents(new PlayerCountSettingEvent(this), this);
        pluginManager.registerEvents(new RoomGuiEvent(this), this);
        pluginManager.registerEvents(new RoomGui2Event(this), this);
        pluginManager.registerEvents(new WinnerItemGui(this), this);
        pluginManager.registerEvents(new UpdateVersionEvent(this), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[PvpRoom] Plugin is disabled!");
    }

    // ------------------ data.yml ------------------

    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
                getDataFolder().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public void saveDataConfig() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ------------------ room_data.yml ------------------

    private void createRoomDataFile() {
        roomDataFile = new File(getDataFolder(), "room_data.yml");
        if (!roomDataFile.exists()) {
            try {
                getDataFolder().mkdirs();
                roomDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        roomDataConfig = YamlConfiguration.loadConfiguration(roomDataFile);
    }

    public FileConfiguration getRoomDataConfig() {
        return roomDataConfig;
    }

    public void saveRoomDataConfig() {
        try {
            roomDataConfig.save(roomDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
