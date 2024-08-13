package aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Events.startingEvent.Player.joinedPlayers;

public class gracePeriod {
    //What does the grace period do?
    //
    //The players are not allowed to kill each other
    //Players are invisible

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    static int gracePeriod = config.getInt("Event.GracePeriod.Time");
    static int invisTime = config.getInt("Event.GracePeriod.Invisibility_Time");

    public static boolean gracePeriodStarted = false;

    public static void startGracePeriod() {
        //Grab every player that has joined the event

        gracePeriodStarted = true;
        
        for (Player player : joinedPlayers.players) {
            //Make player invisible through potion
            player.clearActivePotionEffects();
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*invisTime, 0, false, false));
        }
    }
}
