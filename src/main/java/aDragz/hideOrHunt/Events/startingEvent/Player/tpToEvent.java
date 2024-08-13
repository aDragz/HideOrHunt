package aDragz.hideOrHunt.Events.startingEvent.Player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import aDragz.hideOrHunt.Main;

public class tpToEvent {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    static World world = Bukkit.getWorld(config.getString("Event.World"));
    static int x = config.getInt("Event.X");
    static int y = config.getInt("Event.Y");
    static int z = config.getInt("Event.Z");

    static Location loc = new Location(world, x, y, z);

    public static void teleport() {
        //Teleport every person in the event to the event location
        joinedPlayers.players.forEach(player -> {
            player.teleport(loc);
        });
    }
}
