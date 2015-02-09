package me.lolikillyaaa.ShipLock;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
  public static Main instance;
  private static final Logger log = Logger.getLogger("Minecraft");
  public static Economy econ = null;
  public static FileConfiguration config;
  public static File file = new File("plugins/ShipLock/log.yml");
  public static FileConfiguration log1 = YamlConfiguration.loadConfiguration(file);

  public void loadConfiguration()
  {
    getConfig().addDefault("Block_Lock", "7");
    getConfig().addDefault("Max_Locks", "5");
    getConfig().options().copyDefaults(true);
    saveConfig();
  }

  public FileConfiguration getFileConfig()
  {
    return config;
  }

  public void onEnable()
  {
    loadConfiguration();
    setupEconomy();
    File usersDir = new File("plugins/ShipLock/UserData/");
    if (!usersDir.exists()) {
      usersDir.mkdir();
    }
    PluginManager pm = getServer().getPluginManager();
    pm.registerEvents(new Methods(this), this);
    getCommand("lockship").setExecutor(new Command_Lockship(this));
    getCommand("ls").setExecutor(new Command_Lockship(this));
    getCommand("lockedships").setExecutor(new Command_Lockedship(this));
    getCommand("vls").setExecutor(new Command_Lockedship(this));
    getCommand("lockshipclear").setExecutor(new Command_LockshipClear(this));
    getCommand("lc").setExecutor(new Command_LockshipClear(this));
    getLogger().info(getDescription().getFullName() + " has been enabled");
  }

  public void onDisable()
  {
    saveConfig();
    getLogger().info(getDescription().getFullName() + " has been disabled");
  }

  public static PlayerFile getPlayerYaml(UUID string)
  {
    return new PlayerFile("plugins/ShipLock/UserData/" + string + ".yml");
  }

  public static PlayerFile getOfflinePlayerYaml(String PlayerName)
  {
    return new PlayerFile("plugins/ShipLock/UserData/" + PlayerName + ".yml");
  }
  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null) {
      return false;
    }
    RegisteredServiceProvider rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null) {
      return false;
    }
    econ = (Economy)rsp.getProvider();
    return econ != null;
  }
}