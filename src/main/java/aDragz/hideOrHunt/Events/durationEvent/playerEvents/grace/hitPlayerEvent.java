package aDragz.hideOrHunt.Events.durationEvent.playerEvents.grace;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class hitPlayerEvent implements Listener {
    @EventHandler
    public static void hitPlayer(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            if (gracePeriod.gracePeriodStarted) {
                //Grace period is still active
                event.setCancelled(true);
            }
        }
    }
    
}
