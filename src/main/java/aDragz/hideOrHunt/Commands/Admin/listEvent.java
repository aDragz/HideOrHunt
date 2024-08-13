package aDragz.hideOrHunt.Commands.Admin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class listEvent {
        
    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    //Grab a list of players, and send it to the admin (with amount of players)

    public static void displayList(Player admin) {
        //Grab list of players
        List<Player> players = new ArrayList<>();
        players.addAll(joinedPlayers.players);

        //Grab amount of players in the event
        int amount = joinedPlayers.players.size();

        //Top Message (Amount in event)
        admin.sendMessage(config.getString("Messages.Admin.amount_in_event_beacon")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("%amount%", amount + "")
                .replaceAll("&", "§"));

        joinedPlayers.players.forEach(player -> {
            //Check if the player has a active beacon
            if (beaconLocations.beaconLocations.containsKey(player)) {
                admin.sendMessage(config.getString("Messages.Admin.list_beacon")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("%player%", player.getName())
                .replaceAll("&", "§"));
            } else {
                admin.sendMessage(config.getString("Messages.Admin.list_no_beacon")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("%player%", player.getName())
                .replaceAll("&", "§"));
            }
        });
    }
}
