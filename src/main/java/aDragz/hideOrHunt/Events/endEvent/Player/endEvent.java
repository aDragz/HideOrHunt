package aDragz.hideOrHunt.Events.endEvent.Player;

import org.bukkit.Bukkit;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.cooldown;
import aDragz.hideOrHunt.Events.repeatEvents.repeatCommands;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class endEvent {
    
    static Main plugin = Main.getPlugin(Main.class);

    @SuppressWarnings("deprecation")
    public static void stop(Boolean forced) {
        //Remove beacons
        beaconLocations.beaconLocations.forEach((player, location) -> {
            location.getBlock().setType(org.bukkit.Material.AIR);
        });

        //Remove cooldowns
        cooldown.cooldownTime = -1;
        cooldown.cooldownTotalTime = -1;

        //Stop cooldown
        Bukkit.getScheduler().cancelTasks(plugin);

        startEvent.eventStarted = false;
        joinedPlayers.players.clear();
        beaconLocations.beaconLocations.clear();

        //Start the "event starts in" countdown
        repeatCommands.startCooldown();

        Bukkit.broadcastMessage(plugin.getConfig().getString("Messages.Event.end")
                    .replaceAll("%prefix%", plugin.getConfig().getString("Messages.Prefix"))
                    .replaceAll("&", "§"));

        //If forced, broadcast message
        if (forced) {
            //Grab all players, and check if they have permission to see the message on the forced end
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("HideOrHunt.event.end.message")) {
                    player.sendMessage(plugin.getConfig().getString("Messages.Event.force_end")
                            .replaceAll("%prefix%", plugin.getConfig().getString("Messages.Prefix"))
                            .replaceAll("&", "§"));
                }
            });
        }
    }
}