package aDragz.hideOrHunt.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class mainTab implements TabCompleter{
    
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            if (command.getName().equalsIgnoreCase("hideorhunt")) {
                if (args.length == 1) {
                    ArrayList<String> tabList = new ArrayList<>();
                    tabList.addAll(Arrays.asList("join", "leave"));

                    //Check permissions
                    if (sender.hasPermission("HideOrHunt.admin.start")) {
                        tabList.add("start");
                    }

                    if (sender.hasPermission("HideOrHunt.admin.end")) {
                        tabList.add("end");
                    }

                    if (sender.hasPermission("HideOrHunt.admin.amount")) {
                        tabList.add("amount");
                    }

                    if (sender.hasPermission("HideOrHunt.admin.list")) {
                        tabList.add("list");
                    }

                    return tabList;
                }
            }

            return null;
        }
    
}
