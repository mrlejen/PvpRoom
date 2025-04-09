package PvpRoom.pvpRoom.Events.Update;

import PvpRoom.pvpRoom.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateVersionEvent implements Listener {
    private final Main plugin;
    private final String currentVersion;

    private static final String REPO_API_URL = "https://api.github.com/repos/mrlejen12/PvpRoom/releases/latest";

    public UpdateVersionEvent(Main plugin) {
        this.plugin = plugin;
        PluginDescriptionFile pdf = plugin.getDescription();
        this.currentVersion = pdf.getVersion();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.isOp()) return;
        checkForUpdates(player);
    }

    public void checkForUpdates(Player player) {
        new Thread(() -> {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(REPO_API_URL).openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Accept", "application/vnd.github+json");

                if (connection.getResponseCode() == 200) {
                    JSONParser parser = new JSONParser();
                    JSONObject jsonResponse = (JSONObject) parser.parse(new InputStreamReader(connection.getInputStream()));
                    String latestVersion = jsonResponse.get("tag_name").toString();

                    if (!latestVersion.equals(currentVersion)) {
                        player.sendMessage("§b[PvpRoom] §a A new version is available: " + latestVersion + ". Please update!\n"+
                        "§b[PvpRoom] §a Download link: §9https://github.com/mrlejen12/PvpRoom/releases");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
