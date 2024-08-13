package aDragz.hideOrHunt.Commands.Player;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class leaveEvent {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    public static void playerLeave(CommandSender sender) {
        //No permission needed to leave the event

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
            sender.sendMessage(config.getString("Messages.Event.leave")
                    .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                    .replaceAll("&", "§"));

            joinedPlayers.players.remove(player);

            //Tell admins they left the event
            for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
                if (onlinePlayer.hasPermission("HideOrHunt.admin.leave")) {
                    onlinePlayer.sendMessage(config.getString("Messages.Event.admin_leave")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("%player%", sender.getName())
                            .replaceAll("&", "§"));
                }
            }
        } else {
            player.sendMessage(config.getString("Messages.Event.not_joined")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("&", "§"));
            return;
        }
    }

}
