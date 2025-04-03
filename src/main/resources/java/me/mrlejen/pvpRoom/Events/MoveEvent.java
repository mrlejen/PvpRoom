package me.mrlejen.pvpRoom.Events;
import me.mrlejen.pvpRoom.Data.CheckData;
import me.mrlejen.pvpRoom.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.HashMap;
import java.util.UUID;

import static org.bukkit.Material.AIR;

public class MoveEvent implements Listener {
    private final Main plugin;

    public MoveEvent(Main plugin) {
        this.plugin = plugin;
    }
    HashMap<UUID, Boolean> first = CheckData.getcheckdata();
    @EventHandler
    public void move(PlayerMoveEvent e) {
        FileConfiguration config = plugin.getDataConfig();
        Player player = e.getPlayer();

        if (e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockY() == e.getFrom().getBlockY() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()) return;

        String worldName = player.getWorld().getName();
        Boolean status = plugin.getDataConfig().getBoolean("plugin.status");
        if(status.equals(false)) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;
        int x1 = config.getInt("room." + worldName + ".x");
        int y1 = config.getInt("room." + worldName + ".y");
        int z1 = config.getInt("room." + worldName + ".z");
        int x2 = config.getInt("room." + worldName + ".x2");
        int y2 = config.getInt("room." + worldName + ".y2");
        int z2 = config.getInt("room." + worldName + ".z2");
        int xx1 = config.getInt("door." + worldName + ".x");
        int yy1 = config.getInt("door." + worldName + ".y");
        int zz1 = config.getInt("door." + worldName + ".z");
        int xx2 = config.getInt("door." + worldName + ".x2");
        int yy2 = config.getInt("door." + worldName + ".y2");
        int zz2 = config.getInt("door." + worldName + ".z2");
        Location loc1 = new Location(player.getWorld(), x1, y1, z1);
        Location loc2 = new Location(player.getWorld(), x2, y2, z2);
        Location loc3 = new Location(player.getWorld(), xx1, yy1, zz1);
        Location loc4 = new Location(player.getWorld(), xx2, yy2, zz2);
        if (!isWithin(player.getLocation(), loc1, loc2)) return;
        if (isWithin(player.getLocation(), loc1, loc2)) {
            int playerCount = countPlayersInRoom(loc1, loc2);
            int playercount2 = plugin.getDataConfig().getInt("player.count");
            if (playerCount == playercount2){

                String gameblock = plugin.getDataConfig().getString("doorblock.block");
                int closedoortime = plugin.getDataConfig().getInt("time.door.close");
                        if (gameblock != null) {
                            Material material = Material.getMaterial(gameblock);
                            if(checkd(loc3, loc4, material)) {
                                return;
                            }
                            if(checkd(loc3, loc4, Material.GLASS)) {
                                return;
                            }

                        }

                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        if (gameblock != null) {
                            Material material = Material.getMaterial(gameblock);
                            setBlocksInRegion(loc3, loc4, material);
                            return;
                        }
                        setBlocksInRegion(loc3, loc4, Material.GLASS);
                     },closedoortime);



                return;


            }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                int playercount23 = countPlayersInRoom(loc1, loc2);
                if (playercount23 == 1) {
                    Player attacker = TeleportEvent.gettallplayer(loc1, loc2);
                    if(checkd(loc3, loc4, AIR)) {
                        return;
                    }
                    if (first.containsKey(attacker.getUniqueId()) &&
                            first.get(attacker.getUniqueId())) {
                        Boolean hsmap = Boolean.valueOf(first.get(attacker.getUniqueId()));
                       if(hsmap == true){
                           return;
                       }
                    }
                    BukkitRunnable countdownTask = new BukkitRunnable() {
                        int timeLeft1 = plugin.getDataConfig().getInt("time.door.open");
                        int timeLeft = timeLeft1 / 20;

                        @Override
                        public void run() {
                            first.put(attacker.getUniqueId(),true);
                            if (attacker == null) {
                                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No attacker found. Stopping countdown.");
                                cancel();
                                return;
                            }
                            if (timeLeft > 0) {
                                attacker.spigot().sendMessage(
                                        net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
                                        net.md_5.bungee.api.chat.TextComponent.fromLegacyText("§eThe Door Will Open In §a" + timeLeft)
                                );
                                attacker.getWorld().playSound(attacker.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 2);
                                timeLeft--;
                            } else {
                                first.put(attacker.getUniqueId(),false);
                                setBlocksInRegion(loc3, loc4, AIR);
                                cancel();
                            }

                        }
                    };

                    countdownTask.runTaskTimer(plugin, 0L, 20L);

                }
                return;
            }, 20L);
            return;
        }
    }

    public static boolean isWithin(Location loc, Location loc1, Location loc2) {
        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        return loc.getBlockX() >= minX && loc.getBlockX() <= maxX &&
                loc.getBlockY() >= minY && loc.getBlockY() <= maxY &&
                loc.getBlockZ() >= minZ && loc.getBlockZ() <= maxZ;
    }

    public static int countPlayersInRoom(Location loc1, Location loc2) {
        return (int) Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.getWorld().equals(loc1.getWorld()))
                .filter(p -> isWithin(p.getLocation(), loc1, loc2))
                .filter(p -> !p.isDead())
                .count();
    }

    private void setBlocksInRegion(Location loc1, Location loc2, Material material) {
        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        boolean shouldBuild = false;


        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    if (block.getType() != material) {
                        shouldBuild = true;
                        break;
                    }
                }
                if (shouldBuild) break;
            }
            if (shouldBuild) break;
        }


        if (shouldBuild) {
            for (int x = minX; x <= maxX; x++) {
                for (int y = minY; y <= maxY; y++) {
                    for (int z = minZ; z <= maxZ; z++) {
                        Block block = loc1.getWorld().getBlockAt(x, y, z);
                        block.setType(material);
                    }
                }
            }
        }
    }
    public static boolean checkd(Location loc1, Location loc2, Material material) {
        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        boolean shouldBuild = false;


        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Block block = loc1.getWorld().getBlockAt(x, y, z);
                    if (block.getType() != material) {
                        shouldBuild = true;
                        break;
                    }
                }
                if (shouldBuild) break;
            }
            if (shouldBuild) break;
        }


        return !shouldBuild;
    }
}