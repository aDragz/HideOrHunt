package aDragz.hideOrHunt.Commands.Admin;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class amountEvent {
    
    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    public static void grabAmount(Player admin) {
        //Grab amount of players in the event
        int amount = joinedPlayers.players.size();

        //Send the amount of players in the event to the admin
        admin.sendMessage(config.getString("Messages.Admin.amount_in_event")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("%amount%", amount + "")
                .replaceAll("&", "§"));
        }
}
