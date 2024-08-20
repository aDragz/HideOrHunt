package aDragz.hideOrHunt;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import aDragz.hideOrHunt.Commands.mainCommand;
import aDragz.hideOrHunt.Commands.mainTab;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconDestroy;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconPlace;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.playerKillEvent;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.hitPlayerEvent;
import aDragz.hideOrHunt.Events.repeatEvents.repeatCommands;

public final class Main extends JavaPlugin {

    @SuppressWarnings("deprecation")
    @Override
    public void onEnable() {

        //Save config.yml
        saveDefaultConfig();

        //Register command
        this.getCommand("hideorhunt").setExecutor(new mainCommand());

        //Register tab completer
        this.getCommand("hideorhunt").setTabCompleter(new mainTab());

        //Register events
        //Event Started (Beacon)
        getServer().getPluginManager().registerEvents(new beaconPlace(), this);
        getServer().getPluginManager().registerEvents(new beaconDestroy(), this);

        //Event Started (Player)
        getServer().getPluginManager().registerEvents(new playerKillEvent(), this);

        //Grace
        getServer().getPluginManager().registerEvents(new hitPlayerEvent(), this);

        //Check to see if repeat commands are enabled
        if (getConfig().getBoolean("Repeat_Commands.Enabled")) {
            //Start repeat commands
            Bukkit.getConsoleSender().sendMessage("[" + this.getDescription().getPrefix() + "]" + " Repeat Commands Enabled");
            repeatCommands.startCooldown();
        }

    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
    }
}
