package aDragz.hideOrHunt.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.amountEvent;
import aDragz.hideOrHunt.Commands.Admin.listEvent;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Commands.Player.joinEvent;
import aDragz.hideOrHunt.Commands.Player.leaveEvent;

public class mainCommand implements CommandExecutor {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("hideorhunt")) {
            //check if args length is greater than or equal to 1
            if (args.length >= 1) {
                //switch statement
                switch (args[0].toLowerCase()) {
                    case ("join"):
                        joinEvent.playerJoin(sender);
                        break;
                    case ("leave"):
                        leaveEvent.playerLeave(sender);
                        break;
                    case ("start"):
                        if (!sender.hasPermission("HideOrHunt.admin.start")) {
                            sender.sendMessage(config.getString("Messages.Admin.no_permission")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("&", "§"));
                            return true;
                        }

                        Player playerStart = (Player) sender;
                        startEvent.start(playerStart);
                        break;
                    case ("amount"):
                        if (!sender.hasPermission("HideOrHunt.admin.amount")) {
                            sender.sendMessage(config.getString("Messages.Admin.no_permission")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("&", "§"));
                            return true;
                        }

                        Player playerAmount = (Player) sender;
                        amountEvent.grabAmount(playerAmount);
                        break;
                    case ("list"):
                        if (!sender.hasPermission("HideOrHunt.admin.list")) {
                            sender.sendMessage(config.getString("Messages.Admin.no_permission")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("&", "§"));
                            return true;
                        }

                        Player playerList = (Player) sender;
                        listEvent.displayList(playerList);
                        break;
                    default:
                        sender.sendMessage(config.getString("Messages.Event.unknown_command")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("&", "§"));
                        break;
                }
            }
        }
        return true;
    }

}