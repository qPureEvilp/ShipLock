package me.lolikillyaaa.ShipLock;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Command_Lockship
  implements CommandExecutor
{
  public static Main plugin;

  public Command_Lockship(Main main)
  {
    plugin = main;
  }

  public Command_Lockship(Events events) {
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
    int max = Integer.parseInt(plugin.getConfig().getString("Max_Locks"));
    if ((commandLabel.equalsIgnoreCase("lockship")) || (commandLabel.equalsIgnoreCase("ls"))) {
      if (args.length == 0) {
        Methods.check(player);
      }
      if (args.length == 1) {
        if (yaml.getStringList("Used_Locks").contains(args[0])) {
          Methods.check(player);
          return false;
        }
        if (Integer.parseInt(args[0]) > max) {
          player.sendMessage(ChatColor.RED + "The max amount of locks is " + max);
          return false;
        }
        Methods.logCommand(player, "lockship " + args[0]);
        List listofstrings1 = yaml.getIntegerList("Used_Locks");
        listofstrings1.add(Integer.valueOf(Integer.parseInt(args[0])));
        yaml.createNewIntegerList("Used_Locks", listofstrings1);
        yaml.set(args[0] + ".X", Integer.valueOf(player.getLocation().getBlockX()));
        yaml.set(args[0] + ".Y", Integer.valueOf(player.getLocation().getBlockY()));
        yaml.set(args[0] + ".Z", Integer.valueOf(player.getLocation().getBlockZ()));
        yaml.save();
        int id = Integer.parseInt(plugin.getConfig().getString("Block_Lock"));
        player.getWorld().spawnFallingBlock(player.getLocation(), Material.getMaterial(id), (byte)0);
        Block blockToChange = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() + 1, player.getLocation().getBlockZ());
        blockToChange.setType(Material.SIGN_POST);
        Sign s = (Sign)blockToChange.getState();
        s.setLine(0, "§a[ShipLock]");
        s.setLine(1, "§bLocked by:");
        s.setLine(2, player.getName());
        s.update();
        player.sendMessage(ChatColor.GREEN + "A lock has been placed! To unlock your ship right click the lock with a stick.");
      }
      if (args.length > 1) {
        OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(args[1]);
        PlayerFile yaml1 = Main.getPlayerYaml(target.getUniqueId());
        Methods.logCommand(player, "lockship " + args[0] + " " + args[1]);
        if (yaml.getStringList("Used_Locks").contains(args[0])) {
        player.sendMessage(ChatColor.RED + " is already locked for " + target.getName());
          return false;
        }
        if (Integer.parseInt(args[0]) > max) {
          player.sendMessage(ChatColor.RED + "The max amount of locks is " + max);
          return false;
        }
        if (!player.hasPermission("ShipLock.Staff")) {
          player.sendMessage(ChatColor.RED + "You do not have permission to run this command");
          return false;
        }
        List listofstrings1 = yaml1.getIntegerList("Used_Locks");
        listofstrings1.add(Integer.valueOf(Integer.parseInt(args[0])));
        yaml1.createNewIntegerList("Used_Locks", listofstrings1);
        yaml1.set(args[0] + ".X", Integer.valueOf(player.getLocation().getBlockX()));
        yaml1.set(args[0] + ".Y", Integer.valueOf(player.getLocation().getBlockY()));
        yaml1.set(args[0] + ".Z", Integer.valueOf(player.getLocation().getBlockZ()));
        yaml1.save();
        int id = Integer.parseInt(plugin.getConfig().getString("Block_Lock"));
        player.getWorld().spawnFallingBlock(player.getLocation(), Material.getMaterial(id), (byte)0);
        Block blockToChange = player.getWorld().getBlockAt(player.getLocation().getBlockX(), player.getLocation().getBlockY() + 1, player.getLocation().getBlockZ());
        blockToChange.setType(Material.SIGN_POST);
        Sign s = (Sign)blockToChange.getState();
        s.setLine(0, "§a[ShipLock]");
        s.setLine(1, "§bLocked by:");
        s.setLine(2, target.getName());
        s.update();
        player.sendMessage(ChatColor.GREEN + "A lock has been placed! To unlock your ship right click the lock with a stick.");
        player.sendMessage(ChatColor.GREEN + "Set lock for " + target.getName() + ".");
      }
    }

    return false;
  }
}