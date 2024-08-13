package aDragz.hideOrHunt;

import org.bukkit.plugin.java.JavaPlugin;

import aDragz.hideOrHunt.Commands.mainCommand;
import aDragz.hideOrHunt.Commands.mainTab;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconDestroy;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconPlace;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.playerKillEvent;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.hitPlayerEvent;

public final class Main extends JavaPlugin {

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

    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic
    }
}
