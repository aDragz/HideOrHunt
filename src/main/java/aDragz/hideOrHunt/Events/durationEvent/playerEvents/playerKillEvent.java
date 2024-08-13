package aDragz.hideOrHunt.Events.durationEvent.playerEvents;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;
import aDragz.hideOrHunt.Events.endEvent.Player.endEvent;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class playerKillEvent implements Listener {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    @SuppressWarnings("deprecation")
    @EventHandler
    public static void playerKill(PlayerDeathEvent event) {
        // Check if the event even started
        if (startEvent.eventStarted) {
            // Check current world, and if it is the event world
            if (event.getPlayer().getWorld().getName().equals(config.getString("Event.World"))) {
                // Check to see if Player's beacon is still placed
                if (beaconLocations.beaconLocations.containsKey(event.getPlayer())) {
                    // Grab killer
                    Player killer = event.getEntity().getKiller();

                    // Beacon Placed
                    killer.sendMessage(config.getString("Messages.Event.player_kill_beacon")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("%player%", event.getEntity().getName())
                            .replaceAll("&", "§"));

                    return;
                }
                try {
                    // Grab killer
                    Player killer = event.getEntity().getKiller();

                    // Beacon Not Placed
                    killer.sendMessage(config.getString("Messages.Event.player_kill_no_beacon")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("%player%", event.getEntity().getName())
                            .replaceAll("&", "§"));
                } catch (NullPointerException e) {
                }

                // Remove from players list/
                joinedPlayers.players.remove(event.getEntity().getPlayer());

                // Check to see if there are any players left (1 player left)
                if (joinedPlayers.players.size() == 1) {
                    // End Event

                    Bukkit.broadcastMessage(config.getString("Messages.Event.player_kill_win")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("%player%", joinedPlayers.players.get(0).getName())
                            .replaceAll("&", "§"));

                    endEvent.stop();
                }
            }
        }
    }
}