package me.lolikillyaaa.ShipLock;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class PlayerFile
{
  private File file = null;
  private Main plugin;
  private YamlConfiguration yaml = new YamlConfiguration();

  public PlayerFile(File file) {
    this.file = file;
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    load();
  }

  public PlayerFile(String path)
  {
    this.file = new File(path);
    if (!this.file.exists()) {
      try {
        this.file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    load();
  }

  private void load()
  {
    try {
      this.yaml.load(this.file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void save()
  {
    try
    {
      this.yaml.save(this.file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void delete() {
    try {
      this.file.delete();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public final File getfile(String PlayerName) {
    return this.file;
  }

  public int getInteger(String s)
  {
    return this.yaml.getInt(s);
  }

  public void reload()
  {
    save();
    load();
  }

  public String getString(String s)
  {
    return this.yaml.getString(s);
  }

  public Object get(String s)
  {
    return this.yaml.get(s);
  }

  public boolean getBoolean(String s)
  {
    return this.yaml.getBoolean(s);
  }

  public void add(String s, Object o)
  {
    if (!contains(s))
      set(s, o);
  }

  public void addToStringList(String s, String o)
  {
    this.yaml.getStringList(s).add(o);
  }

  public void removeFromStringList(String s, String o)
  {
    this.yaml.getStringList(s).remove(o);
  }

  public List<String> getStringList(String s)
  {
    return this.yaml.getStringList(s);
  }

  public void addToIntegerList(String s, int o)
  {
    this.yaml.getIntegerList(s).add(Integer.valueOf(o));
  }

  public void removeFromIntegerList(String s, int o)
  {
    this.yaml.getIntegerList(s).remove(o);
  }

  public List<Integer> getIntegerList(String s)
  {
    return this.yaml.getIntegerList(s);
  }

  public void createNewStringList(String s, List<String> list)
  {
    this.yaml.set(s, list);
  }

  public void createNewIntegerList(String s, List<Integer> list)
  {
    this.yaml.set(s, list);
  }

  public void remove(String s)
  {
    set(s, null);
  }

  public boolean contains(String s)
  {
    return this.yaml.contains(s);
  }

  public double getDouble(String s)
  {
    return this.yaml.getDouble(s);
  }

  public void set(String s, Object o)
  {
    this.yaml.set(s, o);
  }

  public void increment(String s)
  {
    this.yaml.set(s, Integer.valueOf(getInteger(s) + 1));
  }

  public void decrement(String s)
  {
    this.yaml.set(s, Integer.valueOf(getInteger(s) - 1));
  }

  public void increment(String s, int i)
  {
    this.yaml.set(s, Integer.valueOf(getInteger(s) + i));
  }

  public void decrement(String s, int i)
  {
    this.yaml.set(s, Integer.valueOf(getInteger(s) - i));
  }

  public YamlConfigurationOptions options()
  {
    return this.yaml.options();
  }
}