package aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Events.durationEvent.Beacon.beaconLocations;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class cooldown {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    //Grace Period
    public static int cooldownLength = config.getInt("Event.GracePeriod.Time");
    public static int cooldownTime;

    static List<Integer> cooldownDurationList = config.getIntegerList("Event.GracePeriod.Duration_Announcements");

    //Total time of the event
    public static int cooldownTotalLength = config.getInt("Event.Game_Time.Time");
    public static int cooldownTotalTime;
    
    static List<Integer> cooldownTotalDurationList = config.getIntegerList("Event.Game_Time.Duration_Announcements");

    static World world = Bukkit.getWorld(config.getString("Event.Game_Time.Tp.World"));
    static int x = config.getInt("Event.Game_Time.Tp.X");
    static int y = config.getInt("Event.Game_Time.Tp.Y");
    static int z = config.getInt("Event.Game_Time.Tp.Z");

    //Make Grace & Total into 1, to reduce CPU usage
    public static void startCooldown() {

        cooldownTime = cooldownLength;
        cooldownTotalTime = cooldownTotalLength;
        
        //Create cooldown for 60 seconds
        Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @SuppressWarnings("deprecation")
            @Override
            public void run() {
                cooldownTime--;
                cooldownTotalTime--;

                //Grace Period

                if (cooldownDurationList.contains(cooldownTime)) {
                    //See if it needs minutes or seconds
                    if (cooldownTime >= 60) {
                        int minutes = cooldownTime / 60;
                        int seconds = cooldownTime % 60;
                        Bukkit.broadcastMessage(config.getString("Messages.Event.grace_period_end_in_minutes")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("%minutes%", String.valueOf(minutes))
                                .replaceAll("%seconds%", String.valueOf(seconds))
                                .replaceAll("&", "§"));
                    } else {
                        Bukkit.broadcastMessage(config.getString("Messages.Event.grace_period_end_in")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("%seconds%", String.valueOf(cooldownTime))
                                .replaceAll("&", "§"));
                    }
                }

                //If cooldown is over, end grace period
                if (cooldownTime == 0) {
                    Bukkit.broadcastMessage(config.getString("Messages.Event.grace_period_end")
                            .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                            .replaceAll("&", "§"));
                    
                    //Check who did not place beacon, and kill them
                    joinedPlayers.players.forEach(player -> {
                        if (!beaconLocations.beaconLocations.containsKey(player)) {
                            //kill player
                            player.setHealth(0);
                        }
                    });

                    //Turn grace period off
                    gracePeriod.gracePeriodStarted = false;
                }

                //Total Time Period
                if (cooldownTotalDurationList.contains(cooldownTotalTime)) {
                    //See if it needs minutes or seconds
                    if (cooldownTotalTime >= 60) {
                        int minutes = cooldownTotalTime / 60;
                        int seconds = cooldownTotalTime % 60;
                        Bukkit.broadcastMessage(config.getString("Messages.Event.game_period_end_in_minutes")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("%minutes%", String.valueOf(minutes))
                                .replaceAll("%seconds%", String.valueOf(seconds))
                                .replaceAll("&", "§"));
                    } else {
                        Bukkit.broadcastMessage(config.getString("Messages.Event.game_period_end_in")
                                .replaceAll("%prefix%", config.getString("Messages.Prefix"))
                                .replaceAll("%seconds%", String.valueOf(cooldownTotalTime))
                                .replaceAll("&", "§"));
                    }
                }

                //If cooldown is over, tp every alive player to the ffa location
                if (cooldownTotalTime == 0) {
                    //Teleport all players to the ffa location
                    Location tpLocation = new Location(world, x, y, z);
                    beaconLocations.beaconLocations.forEach((player, location) -> {
                        player.teleport(tpLocation);
                    });

                    //Set beacons to air
                    beaconLocations.beaconLocations.forEach((player, location) -> {
                        location.getBlock().setType(org.bukkit.Material.AIR);
                    });

                    //Clear beacon locations
                    beaconLocations.beaconLocations.clear();

                    Bukkit.getScheduler().cancelTasks(plugin);
                }
            }
        }, 0L, 20L);
    }
}
