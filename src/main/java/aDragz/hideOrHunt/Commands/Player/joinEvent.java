package aDragz.hideOrHunt.Commands.Player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class joinEvent {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    public static void playerJoin(CommandSender sender) {

        //No permission needed to join the event

        //Check if the sender is a player
        if (!(sender instanceof Player)) {
            sender.sendMessage(config.getString("Messages.Event.not_player")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("&", "§"));

            return;
        }

        //Check to see if the event has started
        if (startEvent.eventStarted) {
            sender.sendMessage(config.getString("Messages.Event.already_started")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("&", "§"));
            return;
        }

        Player player = (Player) sender;

        if (joinedPlayers.players.contains(player)) {
            sender.sendMessage(config.getString("Messages.Event.already_joined")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("&", "§"));
            return;
        } else {
            sender.sendMessage(config.getString("Messages.Event.join")
                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                .replaceAll("&", "§"));

            joinedPlayers.players.add(player);

            //Broadcast to all players that the player has joined the event

            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("HideOrHunt.admin.join")) {
                    onlinePlayer.sendMessage(config.getString("Messages.Event.admin_join")
                        .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                        .replaceAll("%player%", sender.getName())
                        .replaceAll("&", "§"));
                }
            }
        }
    }
}