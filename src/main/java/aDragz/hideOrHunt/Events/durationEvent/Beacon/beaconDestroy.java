package aDragz.hideOrHunt.Events.durationEvent.Beacon;

import java.util.EventListener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Commands.Admin.startEvent;
import aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace.gracePeriod;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class beaconDestroy implements EventListener, Listener{
    
    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    static World world = Bukkit.getWorld(config.getString("Spawn.World"));
    static int x = config.getInt("Spawn.X");
    static int y = config.getInt("Spawn.Y");
    static int z = config.getInt("Spawn.Z");

    static final Location defaultLoc = new Location(world, x, y, z);

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBeaconDestroy(BlockBreakEvent event) {
        //Check if the event even started
        if (startEvent.eventStarted) {
            //Check current world, and if it is the event world
            if (event.getPlayer().getWorld().getName().equals(config.getString("Event.World"))) {
                //Check if the block placed is a beacon
                if (event.getBlock().getType().toString().equals("BEACON")) {
                    //Check if grace period is still active
                    if (gracePeriod.gracePeriodStarted) {
                        event.getPlayer().sendMessage(config.getString("Messages.Event.beacon_grace")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("&", "§"));
                        event.setCancelled(true);
                        return;
                    }

                    //Check if the player has joined the event
                    if (!joinedPlayers.players.contains(event.getPlayer())) {
                        event.setCancelled(true);
                        return;
                    }
                    
                    //Grab Location
                    Location loc = event.getBlock().getLocation();

                    //Find Location in beaconLocations
                    if (beaconLocations.beaconLocations.containsValue(loc)) {
                        //Make it not drop the beacon
                        event.setDropItems(false);

                        //Lightning
                        loc.getWorld().strikeLightningEffect(loc);
                        
                        //Grab name of player
                        String playerName = beaconLocations.beaconLocations.entrySet().stream()
                            .filter(entry -> loc.equals(entry.getValue()))
                            .map(entry -> entry.getKey().getName())
                            .findFirst()
                            .orElse(null);

                        //Remove Location from beaconLocations
                        beaconLocations.beaconLocations.values().remove(loc);
                        Bukkit.broadcastMessage(config.getString("Messages.Event.beacon_destroyed")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("%player%", playerName)
                            .replaceAll("&", "§"));

                        Player player = Bukkit.getPlayer(playerName);
                        player.sendMessage(config.getString("Messages.Event.beacon_destroyed_player")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("%player%", event.getPlayer().getName())
                            .replaceAll("&", "§"));

                        //Set player spawn location to default location
                        player.setBedSpawnLocation(defaultLoc, true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void tntExplode(EntityExplodeEvent event) {
        //Check if the event even started
        if (startEvent.eventStarted) {
            //Check current world, and if it is the event world
            if (event.getEntity().getWorld().getName().equals(config.getString("Event.World"))) {
                    event.setCancelled(true);
            }
        }            
    }
}
