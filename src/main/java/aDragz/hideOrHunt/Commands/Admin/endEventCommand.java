package aDragz.hideOrHunt.Commands.Admin;

import org.bukkit.Bukkit;

import aDragz.hideOrHunt.Main;
import aDragz.hideOrHunt.Events.endEvent.Player.endEvent;

public class endEventCommand {

    static Main plugin = Main.getPlugin(Main.class);

    @SuppressWarnings("deprecation")
    public static void forceStop() {
        //Check if the event is running
        if (!startEvent.eventStarted) {
            Bukkit.broadcastMessage(plugin.getConfig().getString("Messages.Event.not_started")
                    .replaceAll("%prefix%", plugin.getConfig().getString("Messages.Prefix"))
                    .replaceAll("&", "§"));

            return;
        }
        endEvent.stop(true);
    }
}
