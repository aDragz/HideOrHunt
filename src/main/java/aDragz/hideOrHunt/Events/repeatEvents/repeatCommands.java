package aDragz.hideOrHunt.Events.repeatEvents;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;

public class repeatCommands {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    public static int repeatCooldown = config.getInt("Repeat_Commands.Time");
    public static int repeatTime;

    //Grab active durations
    static List<Integer> repeatTotalDurationList = config.getIntegerList("Repeat_Commands.Active_Durations");

    public static void startCooldown() {
            
            repeatTime = repeatCooldown;
    
            //Create cooldown for 60 seconds
            plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
                @Override
                public void run() {
                    repeatTime--;
    
                    if (repeatTotalDurationList.contains(repeatTime)) {
                        //Grab Command
                        List<String> commands = config.getStringList("Repeat_Commands.Commands." + repeatTime);

                        //Change Colour codes & Prefix
                        commands.replaceAll(command -> command.replaceAll("%prefix%", config.getString("Messages.Prefix")).replaceAll("&", "§"));
                        
                        commands.forEach(command -> {
                            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
                        });
                    }
    
                    if (repeatTime == 0) {
                        startEvent.start(Bukkit.getConsoleSender());
                        //Set time back to default, to repeat
                        repeatTime = repeatCooldown;
                    }
            }
            }, 0L, 20L);
    }
}
