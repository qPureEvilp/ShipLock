package me.lolikillyaaa.ShipLock;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Command_Lockedship
  implements CommandExecutor
{
  public static Main plugin;

  public Command_Lockedship(Main instance)
  {
    plugin = instance;
  }

  public Command_Lockedship(Events events) {
	// TODO Auto-generated constructor stub
}

public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be used by a player!");
      return false;
    }
    Player player = (Player)sender;
    PlayerFile yaml = Main.getPlayerYaml(player.getUniqueId());
    if ((commandLabel.equalsIgnoreCase("lockedships")) || (commandLabel.equalsIgnoreCase("vls"))) {
      if (args.length == 0) {
        Methods.logCommand(player, "lockedships");
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
      }
      if (args.length > 0) {
        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
        Methods.logCommand(player, "lockedships " + args[0]);
        PlayerFile yaml1 = Main.getPlayerYaml(target.getUniqueId());
        if (!player.hasPermission("ShipLock.Staff")) {
          player.sendMessage(ChatColor.RED + "You do not have permission to run this command!");
          return false;
        }
        int a = Integer.parseInt(plugin.getConfig().getString("Max_Locks"));
        for (int i = 1; i < a + 1; i++) {
          List q = yaml1.getIntegerList("Used_Locks");
          if (q.contains(Integer.valueOf(i))) {
            player.sendMessage(ChatColor.RED + "Ship lock: " + i + " is locked at X: " + yaml1.getInteger(new StringBuilder(String.valueOf(i)).append(".X").toString()) + " Y: " + yaml1.getInteger(new StringBuilder(String.valueOf(i)).append(".Y").toString()) + " Z: " + yaml1.getInteger(new StringBuilder(String.valueOf(i)).append(".Z").toString()) + ".");
          }
          if (!q.contains(Integer.valueOf(i))) {
            player.sendMessage(ChatColor.GREEN + "Ship lock: " + i + " is not locked!");
          }
        }
      }
    }
    return false;
  }
}