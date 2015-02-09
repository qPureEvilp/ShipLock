package me.lolikillyaaa.ShipLock;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Command_LockshipClear
  implements CommandExecutor
{
  public static Main plugin;

  public Command_LockshipClear(Main instance)
  {
    plugin = instance;
  }

  public Command_LockshipClear(Events events) {
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
    if (((commandLabel.equalsIgnoreCase("lockshipclear")) || (commandLabel.equalsIgnoreCase("lc"))) && 
      (args.length == 0)) {
      Methods.logCommand(player, "lockshipclear");
      player.sendMessage(ChatColor.RED + "Cleared all locks!");
      int a = Integer.parseInt(plugin.getConfig().getString("Max_Locks"));
      for (int i = 1; i < a + 1; i++) {
        int x = yaml.getInteger(i + ".X");
        int y = yaml.getInteger(i + ".Y");
        int z = yaml.getInteger(i + ".Z");
        Block blockToChange = player.getWorld().getBlockAt(x, y, z);
        blockToChange.setType(Material.AIR);
        List drops = new ArrayList();
        yaml.createNewIntegerList("Used_Locks", drops);
        yaml.save();
      }

    }

    if ((args.length > 0) && 
      (player.hasPermission("Shiplock.staff"))) {
      int a = Integer.parseInt(plugin.getConfig().getString("Max_Locks"));
      player.sendMessage(ChatColor.RED + "Cleared all " + args[0] + "'s locks!");
      OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[0]);
      Methods.logCommand(player, "lockshipclear " + args[0]);
      PlayerFile yaml1 = Main.getPlayerYaml(target.getUniqueId());
      for (int i = 1; i < a + 1; i++) {
        int x = yaml1.getInteger(i + ".X");
        int y = yaml1.getInteger(i + ".Y");
        int z = yaml1.getInteger(i + ".Z");
        Block blockToChange = player.getWorld().getBlockAt(x, y, z);
        blockToChange.setType(Material.AIR);
        List drops = new ArrayList();
        yaml1.createNewIntegerList("Used_Locks", drops);
        yaml1.save();
      }

    }

    return false;
  }
}