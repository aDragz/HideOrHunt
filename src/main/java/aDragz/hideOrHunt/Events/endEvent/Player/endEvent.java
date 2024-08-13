package aDragz.hideOrHunt.Events.endEvent.Player;

import org.bukkit.Bukkit;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.cooldown;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class endEvent {
    
    static Main plugin = Main.getPlugin(Main.class);

    public static void stop() {
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
    }
}