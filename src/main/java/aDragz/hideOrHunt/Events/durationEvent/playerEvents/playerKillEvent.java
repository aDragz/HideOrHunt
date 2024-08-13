package aDragz.hideOrHunt.Events.durationEvent.playerEvents;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;

public class playerKillEvent implements Listener {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    @EventHandler
    public static void playerKill(PlayerDeathEvent event) {
        //Check if the event even started
        if (startEvent.eventStarted) {
            //Check current world, and if it is the event world
            if (event.getPlayer().getWorld().getName().equals(config.getString("Event.World"))) {
                //Grab killer
                Player killer = event.getEntity().getKiller();
                //Check to see if Player's beacon is still placed
                if (beaconLocations.beaconLocations.containsKey(event.getPlayer())) {
                    killer.sendMessage(config.getString("Messages.Event.player_kill_beacon")
                        .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                        .replaceAll("%player%", event.getEntity().getName())
                        .replaceAll("&", "§"));
                } else {
                    killer.sendMessage(config.getString("Messages.Event.player_kill_no_beacon")
                        .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                        .replaceAll("%player%", event.getEntity().getName())
                        .replaceAll("&", "§"));
                }
            }
        }
    }

}
