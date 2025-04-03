package me.mrlejen.pvpRoom;

import me.mrlejen.pvpRoom.Commands.PvpRoom;
import me.mrlejen.pvpRoom.Events.*;
import me.mrlejen.pvpRoom.Events.Gui.CustomSettingsEvent;
import me.mrlejen.pvpRoom.Events.Gui.DoorGuiEvent;
import me.mrlejen.pvpRoom.Events.Gui.GuiEvent;
import me.mrlejen.pvpRoom.Events.Gui.PlayerCountSettingEvent;
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
    private FileConfiguration dataConfig;
    @Override
    public void onEnable() {

        SelectEvent selectEvent = new SelectEvent();
        getConfig().set("Version","0.2-Alpha");
        getConfig();
        saveConfig();
        saveDefaultConfig();
        reloadConfig();
        createDataFile();
        String status = getDataConfig().getString("plugin.status");
        if(status == null){
            getDataConfig().set("plugin.status", false);
            saveDataConfig();
        }
        String opendoortime1 = getDataConfig().getString("time.door.open");
        String closedoortime1 = getDataConfig().getString("time.door.close");
        if(opendoortime1 == null){
            getDataConfig().set("time.door.open", 40);
            saveDataConfig();
        }
        if(closedoortime1 == null){
            getDataConfig().set("time.door.close", 40);
            saveDataConfig();
        }
        String playercount = getDataConfig().getString("player.count");
        if(playercount == null){
            getDataConfig().set("player.count", 2);
            saveDataConfig();
        }
        String gameblock = getDataConfig().getString("doorblock.block");
        if(gameblock == null){
            getDataConfig().set("doorblock.block", "GLASS");
            saveDataConfig();
        }


        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[PvpRoom] Plugin Its On!");

        // commands
        PvpRoom pvpRoomCommand = new PvpRoom(this, selectEvent);
        getCommand("pvproom").setExecutor(pvpRoomCommand);
        getCommand("pvproom").setTabCompleter(pvpRoomCommand);

        // events
        getServer().getPluginManager().registerEvents(selectEvent, this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MoveEvent(this),this);
        pluginManager.registerEvents(new DieEvent(this),this);
        pluginManager.registerEvents(new QuitEvent(this),this);
        pluginManager.registerEvents(new TeleportEvent(this),this);
        pluginManager.registerEvents(new GuiEvent(this),this);
        pluginManager.registerEvents(new DoorGuiEvent(this),this);
        pluginManager.registerEvents(new CustomSettingsEvent(this),this);
        pluginManager.registerEvents(new PlayerCountSettingEvent(this),this);
    }


    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[PvpRoom] Plugin Its Off!");
    }
    private void createDataFile() {
        dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            try {
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
}
