package me.mrlejen.pvpRoom.Events;

import me.mrlejen.pvpRoom.Data.SelectData;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class SelectEvent implements Listener {

    private final HashMap<UUID, SelectData> firstPosition = new HashMap<>();
    private final HashMap<UUID, SelectData> secondPosition = new HashMap<>();
    public HashMap<UUID, SelectData> getFirstPosition() {
        return firstPosition;
    }

    public HashMap<UUID, SelectData> getSecondPosition() {
        return secondPosition;
    }

    @EventHandler
        public void click(PlayerInteractEvent e) {
            Player p = e.getPlayer();
        Action a = e.getAction();

        if (e.getItem() == null) {
            return;
        }

        if (e.getItem().getType() != Material.GOLDEN_SWORD) {
            return;
        }

        if (e.getItem().getItemMeta() == null || e.getItem().getItemMeta().getDisplayName() == null) {
            return;
        }

        String ItemName = e.getItem().getItemMeta().getDisplayName();
        String ItemLore = String.valueOf(e.getItem().getItemMeta().getLore());
        String ItemEnchants = String.valueOf(e.getItem().getItemMeta().getEnchants());

        if (!ItemName.equals(ChatColor.AQUA + "PvpRoom Selector")) {
            return;
        }
        if (!ItemLore.equals("[You Can Select The Position By This Item]")) {
            return;
        }
        if (!ItemEnchants.equals("{CraftEnchantment{holder=Reference{ResourceKey[minecraft:enchantment / minecraft:quick_charge]=Enchantment Quick Charge}}=5}")) {
            return;
        }
            if (e.getClickedBlock() == null) {
            return;
        }
        Location clickedLocation = e.getClickedBlock().getLocation();
        UUID playerId = p.getUniqueId();

        // Left Click (First Position)
        if (a == Action.LEFT_CLICK_BLOCK) {
            if (firstPosition.containsKey(playerId) &&
                    firstPosition.get(playerId).getBlockLocation().equals(clickedLocation)) {
                p.sendMessage(ChatColor.RED + "[PvpRoom] " + ChatColor.GRAY + "You have already selected this block as your first position!");
                return;
            }

            firstPosition.put(playerId, new SelectData(playerId, clickedLocation));
            p.sendMessage(ChatColor.AQUA + "[PvpRoom] " + ChatColor.LIGHT_PURPLE + "First position set at (" +
                    clickedLocation.getBlockX() + ", " +
                    clickedLocation.getBlockY() + ", " +
                    clickedLocation.getBlockZ() + ").");
        }

// Right Click (Second Position)
        if (a == Action.RIGHT_CLICK_BLOCK) {
            if (secondPosition.containsKey(playerId) &&
                    secondPosition.get(playerId).getBlockLocation().equals(clickedLocation)) {
                p.sendMessage(ChatColor.RED + "[PvpRoom] " + ChatColor.GRAY + "You have already selected this block as your second position!");
                return;
            }

            secondPosition.put(playerId, new SelectData(playerId, clickedLocation));
            p.sendMessage(ChatColor.AQUA + "[PvpRoom] " + ChatColor.LIGHT_PURPLE + "Second position set at (" +
                    clickedLocation.getBlockX() + ", " +
                    clickedLocation.getBlockY() + ", " +
                    clickedLocation.getBlockZ() + ").");
        }

    }
}
