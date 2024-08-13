package aDragz.hideOrHunt.Events.durationEvent.Beacon;

import java.util.EventListener;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class beaconPlace implements EventListener, Listener {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBeaconPlace(BlockPlaceEvent event) {

        //Check if the event even started
        if (startEvent.eventStarted) {
            //Check current world, and if it is the event world
            if (event.getPlayer().getWorld().getName().equals(config.getString("Event.World"))) {
                //Check if the block placed is a beacon
                if (event.getBlock().getType().toString().equals("BEACON")) {
                    //Check if the player has joined the event
                    if (!joinedPlayers.players.contains(event.getPlayer())) {
                        event.setCancelled(true);
                    }
                    
                    Player beaconPlayer = event.getPlayer();
                    Location loc = event.getBlock().getLocation();

                    beaconLocations.beaconLocations.put(beaconPlayer, loc);

                    //Destroy 4 blocks above beacon, So spawnpoint is not blocked. Or else player will spawn in the default location.
                    loc.getBlock().getRelative(0, 1, 0).breakNaturally();
                    loc.getBlock().getRelative(0, 2, 0).breakNaturally();
                    loc.getBlock().getRelative(0, 3, 0).breakNaturally();
                    loc.getBlock().getRelative(0, 4, 0).breakNaturally();

                    //Set player spawn location to beacon location
                    //Dev notes: No clue why, but adding 1 y breaks the hashmap above.
                    //Add 1 y
                    loc.setY(loc.getY() + 1);
                    beaconPlayer.setBedSpawnLocation(loc, true);
                    //remove 1 y
                    loc.setY(loc.getY() - 1);
                }
            }
        }
    }
}
