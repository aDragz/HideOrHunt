package aDragz.hideOrHunt.Commands.Admin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.cooldown;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.gracePeriod;
import aDragz.hideOrHunt.Events.startingEvent.Beacon.giveBeacon;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;
import aDragz.hideOrHunt.Events.startingEvent.Player.tpToEvent;

public class startEvent {

    public static boolean eventStarted = false;

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();
    static int minPlayers = config.getInt("Event.MinPlayers");

    //This command will be used to start the event
    //Command = /hideorhunt start
    public static void start(CommandSender admin) {
        //Check if the sender is console
        if (!(admin instanceof Player)) {
            if (!startCooldown(admin))
                return;
        } else {
            //Check if the event is already started
            if (eventStarted) {
                admin.sendMessage(config.getString("Messages.Admin.already_started")
                        .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                        .replaceAll("&", "§"));
                return;
            }

            //Check if the event has enough players

            if (joinedPlayers.players.size() <= minPlayers) {
                admin.sendMessage(config.getString("Messages.Admin.not_enough_players")
                        .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                        .replaceAll("&", "§"));
                return;
            }
        }

        //Start the event
        eventStarted = true;

        //Reset beacon locations
        beaconLocations.beaconLocations.clear();

        //Start the grace period
        gracePeriod.startGracePeriod();

        //Teleport all players to the event location
        tpToEvent.teleport();

        //Turn off countdown for the "Event Starts in" message
        Bukkit.getScheduler().cancelTasks(plugin);

        //Start Cooldown
        cooldown.startCooldown();

        //Broadcast to all players that the event has started
        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            //Check to see if onlinePlayer is in the event
            if (joinedPlayers.players.contains(onlinePlayer)) {
                onlinePlayer.sendMessage(config.getString("Messages.Event.start")
                        .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                        .replaceAll("&", "§"));

                //Give everyone beacons
                giveBeacon.givePlayerBeacon(onlinePlayer);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean startCooldown(CommandSender sender) {
        if (eventStarted) {
            Bukkit.broadcastMessage(config.getString("Messages.Admin.already_started")
                    .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                    .replaceAll("&", "§"));
            return false;
        }

        //Check if the event has enough players

        if (joinedPlayers.players.size() <= minPlayers) {
            Bukkit.broadcastMessage(config.getString("Messages.Admin.not_enough_players")
                    .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                    .replaceAll("&", "§"));
            return false;
        }

        return true;
    }
}
