package aDragz.hideOrHunt.Events.startingEvent.Beacon;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import aDragz.hideOrHunt.Main;

public class giveBeacon {

    static Main plugin = Main.getPlugin(Main.class);
    static FileConfiguration config = plugin.getConfig();

    static String itemName = config.getString("Beacon.Item_Name");
    static List<String> itemLore = config.getStringList("Beacon.Item_Lore");

    //Give every Player that has joined the event a beacon with name inside the metadata
    @SuppressWarnings("deprecation")
    public static void givePlayerBeacon(Player player) {

        //First of all, clear Inventory of Player. So they cannot transfer items & cheat.
        player.getInventory().clear();

        ItemStack beacon = new ItemStack(Material.BEACON);
        ItemMeta meta = beacon.getItemMeta();

        //Set Name
        meta.setDisplayName(itemName.replaceAll("%player%", player.getName())
        .replaceAll("&", "§"));

        NamespacedKey playerNamespacedKey = new NamespacedKey(plugin, "playerName");

        //Add Custom Meta
        meta.getPersistentDataContainer().set(playerNamespacedKey, PersistentDataType.STRING, "playerName");

        //Set Lore

        //Add &(Colour) to the lore
        itemLore.replaceAll(lore -> lore.replaceAll("%prefix%", config.getString("Messages.Prefix")).replaceAll("&", "§").replaceAll("%player%", player.getName()));

        //Set Coloured Lore
        meta.setLore(itemLore);

        //Set final meta
        beacon.setItemMeta(meta);

        player.getInventory().addItem(beacon);
    }
}
