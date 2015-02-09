package me.lolikillyaaa.ShipLock;

import java.io.IOException;
import java.util.ArrayList;
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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class Events
  implements Listener
{
  Main plugin;

  public Events(Main plugin)
  {
    this.plugin = plugin;
  }

  @EventHandler(priority=EventPriority.HIGHEST)
  public void onPlayerInteract(PlayerInteractEvent event) throws IOException {
    Player player = event.getPlayer();
    PlayerFile yaml = Main.getPlayerYaml(player.getUniqueId());
    if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Block b = event.getClickedBlock();
      int id = Integer.parseInt(this.plugin.getConfig().getString("Block_Lock"));
      if ((b.getType() == Material.getMaterial(id)) && 
        (player.getItemInHand().getType() == Material.STICK)) {
        int a = Integer.parseInt(this.plugin.getConfig().getString("Max_Locks"));
        for (int i = 1; i < a + 1; i++) {
          List q = yaml.getIntegerList("Used_Locks");
          if (q.contains(Integer.valueOf(i))) {
            int x = yaml.getInteger(i + ".X");
            int y = yaml.getInteger(i + ".Y");
            int z = yaml.getInteger(i + ".Z");
            int x1 = b.getLocation().getBlockX();
            int y1 = b.getLocation().getBlockY();
            int z1 = b.getLocation().getBlockZ();
            if ((x1 == x) && 
              (y1 == y) && 
              (z1 == z)) {
              List drops = new ArrayList();
              for (Integer f : yaml.getIntegerList("Used_Locks")) {
                if (!f.equals(Integer.valueOf(i))) {
                  drops.add(f);
                  yaml.createNewIntegerList("Used_Locks", drops);
                  yaml.save();
                }
              }
              b.setType(Material.AIR);
              player.sendMessage(ChatColor.GREEN + "Removed lock: " + i + "!");
              Methods.breaklock(player, player.getName());
            }
          }
        }
      }
    }
  }

  @EventHandler
  public void onBreak1(BlockBreakEvent event)
  {
    Player player = event.getPlayer();
    if ((event.getBlock().getType() == Material.BEDROCK) && 
      (player.hasPermission("ShipLock.Staff"))) {
      Sign s = (Sign)event.getBlock().getWorld().getBlockAt(event.getBlock().getLocation().getBlockX(), event.getBlock().getLocation().getBlockY() + 1, event.getBlock().getLocation().getBlockZ()).getState();
      String p = s.getLine(2).toString();
      OfflinePlayer target = Bukkit.getServer().getOfflinePlayer(p);
      PlayerFile yaml = Main.getPlayerYaml(target.getUniqueId());
      int a = Integer.parseInt(this.plugin.getConfig().getString("Max_Locks"));
      for (int i = 1; i < a + 1; i++) {
        List q = yaml.getIntegerList("Used_Locks");
        if (q.contains(Integer.valueOf(i))) {
          int x = yaml.getInteger(i + ".X");
          int y = yaml.getInteger(i + ".Y");
          int z = yaml.getInteger(i + ".Z");
          int x1 = event.getBlock().getLocation().getBlockX();
          int y1 = event.getBlock().getLocation().getBlockY();
          int z1 = event.getBlock().getLocation().getBlockZ();
          if ((x1 == x) && 
            (y1 == y) && 
            (z1 == z)) {
            List drops = new ArrayList();
            for (Integer f : yaml.getIntegerList("Used_Locks")) {
              if (!f.equals(Integer.valueOf(i))) {
                drops.add(f);
                yaml.createNewIntegerList("Used_Locks", drops);
                yaml.save();
              }
            }
          }
        }

      }

      player.sendMessage(ChatColor.GREEN + "Broke " + target.getName() + "'s lock!");
      Methods.breaklock(player, target.getName());
    }
  }

  @EventHandler
  public void onBreak(BlockBreakEvent event)
  {
    if (event.getBlock().getType() == Material.SIGN_POST) {
      Sign s = (Sign)event.getBlock().getState();
      if (s.getLine(0).equalsIgnoreCase("§a[ShipLock]")) {
        event.setCancelled(true);
        event.getPlayer().sendMessage(ChatColor.GREEN + "To unlock your ship right click the lock with a stick!");
      }
    }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    PlayerFile yaml = Main.getPlayerYaml(player.getUniqueId());
    if (yaml.contains("Defaults")) {
      return;
    }
    List listofstrings = new ArrayList();
    yaml.createNewIntegerList("Not_Locked", listofstrings);

    List listofstrings1 = new ArrayList();
    yaml.createNewIntegerList("Used_Locks", listofstrings1);
    yaml.set("Defaults", Boolean.valueOf(true));
    yaml.save();
  }
}