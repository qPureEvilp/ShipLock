package me.lolikillyaaa.ShipLock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Methods
  implements Listener
{
  public static Main plugin;

  public Methods(Main instance)
  {
    plugin = instance;
  }

  public Methods(Events events) {
	// TODO Auto-generated constructor stub
}

public static void logCommand(Player player, String string)
  {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    Main.log1.set("<" + dateFormat.format(date) + ">    " + "PLAYER: " + player.getName(), " issued command: " + string);
    saveConfig();
  }

  public static void breaklock(Player player, String string) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    Main.log1.set("<" + dateFormat.format(date) + ">    " + "PLAYER: " + player.getName(), " broke " + string + "'s lock!");
    saveConfig();
  }
  public static void saveConfig() {
    try {
      Main.log1.save(Main.file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void check(Player player) { PlayerFile yaml = Main.getPlayerYaml(player.getUniqueId());
    int a = Integer.parseInt(plugin.getConfig().getString("Max_Locks"));
    for (int i = 1; i < a + 1; i++) {
      List q = yaml.getIntegerList("Used_Locks");
      if (q.contains(Integer.valueOf(i))) {
        player.sendMessage(ChatColor.RED + "Ship lock: " + i + " is locked at X: " + yaml.getInteger(new StringBuilder(String.valueOf(i)).append(".X").toString()) + " Y: " + yaml.getInteger(new StringBuilder(String.valueOf(i)).append(".Y").toString()) + " Z: " + yaml.getInteger(new StringBuilder(String.valueOf(i)).append(".Z").toString()) + ".");
      }
      if (!q.contains(Integer.valueOf(i))) {
        player.sendMessage(ChatColor.GREEN + "Ship lock: " + i + " is not locked!");
      }
    }
    player.sendMessage(ChatColor.GOLD + "Type: /lockship <#> to lock a ship!");
  }
}