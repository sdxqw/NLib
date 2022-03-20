package io.github.xnotro.settings;

import io.github.xnotro.utils.Messages;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

@SuppressWarnings("ALL")
public class Configuration {
    JavaPlugin plugin;
    File file;
    FileConfiguration fileConfiguration;
    String fileName;

    /**
     * Load config file with default directory.
     * @param plugin Your plugin instance.
     * @param fileName The string for file name.
     */
    public Configuration(@NotNull JavaPlugin plugin, String fileName) {
        this.plugin = plugin;
        this.fileName = fileName;
        file = new File( plugin.getDataFolder(), fileName );
        loadConfiguration();
    }

    /**
     * Load config file with custom directory.
     * @param plugin Your plugin instance.
     * @param fileName The string for file name.
     * @param directory The string for directory.
     */
    public Configuration(@NotNull JavaPlugin plugin, String fileName, String directory) {
        this.plugin = plugin;
        this.fileName = fileName;
        File dir = new File(plugin.getDataFolder(), directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        if (dir.isDirectory()) {
            file = new File(dir, fileName);
        } else {
            file = new File(dir.getParentFile(), fileName);
        }
        loadConfiguration();
    }

    /**
     * Load configuration.
     */
    public void loadConfiguration() {
        if (plugin.getResource(fileName) != null) {
            if (!file.exists()) {
                plugin.saveResource(fileName, false);
            } else {
                file.mkdirs();
                try {
                    file.createNewFile();
                } catch (IOException var3) {
                    var3.printStackTrace();
                }
            }
        }
        if (file.exists()) {
            try {
                fileConfiguration = loadConfiguration(file);
            } catch (InvalidConfigurationException var4) {
                Messages.sendLog( plugin, Level.SEVERE, fileName + " can't be loaded ! Check file syntax first !" );
                Messages.sendLog( plugin, Level.SEVERE, var4.getMessage() );
                File renamed = new File(file.getParentFile(), fileName + ".old");
                if (renamed.exists()) {
                    renamed.delete();
                }
                file.renameTo(renamed);
                loadConfiguration();
            } catch (IOException var5) {
                Messages.sendLog( plugin, Level.SEVERE, "Can't read file " + fileName );
                fileConfiguration = new YamlConfiguration();
            }
        } else {
            fileConfiguration = new YamlConfiguration();
        }

    }

    /**
     * Load yaml configuration.
     * @param file Define the file to load.
     * @return Config.
     */
    private static @NotNull YamlConfiguration loadConfiguration(File file) throws IOException, InvalidConfigurationException {
        Validate.notNull(file, "File cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (FileNotFoundException var3) {
            var3.printStackTrace();
        }
        return config;
    }

    /**
     * Save the configuration.
     */
    public void save() {
        try {
            fileConfiguration.save(file);
        } catch (IOException var2) {
            var2.printStackTrace();
        }
    }

    /**
     * Update the configuration.
     * @param configuration Configuration of the String to get.
     * @param plugin Define the plugin.
     */
    public static void updateConfig(String configuration, @NotNull JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), configuration);
        file.getParentFile().mkdirs();
        if (!file.exists()) {
            plugin.saveResource(configuration, false);
        }
        FileConfiguration default_conf = YamlConfiguration.loadConfiguration(new InputStreamReader(plugin.getResource(configuration)));
        YamlConfiguration config = null;
        try {
            config = loadConfiguration(file);
        } catch (InvalidConfigurationException var8) {
            plugin.getLogger().log(Level.SEVERE, configuration + " can't be loaded ! Check file syntax first !");
            plugin.getLogger().log(Level.SEVERE, var8.getMessage());
            File renamed = new File(file.getParentFile(), configuration + ".old");
            if (renamed.exists()) {
                renamed.delete();
            }
            file.renameTo(renamed);
            return;
        } catch (IOException var9) {
            plugin.getLogger().log(Level.SEVERE, "Can't read file " + configuration);
        }
        Iterator var5 = default_conf.getKeys(true).iterator();
        while(true) {
            String path;
            do {
                if (!var5.hasNext()) {
                    var5 = config.getKeys(true).iterator();

                    while(true) {
                        do {
                            if (!var5.hasNext()) {
                                try {
                                    config.save(file);
                                } catch (IOException var7) {
                                    var7.printStackTrace();
                                }
                                return;
                            }
                            path = (String)var5.next();
                        } while(default_conf.contains(path) && config.get(path).getClass().getName() == default_conf.get(path).getClass().getName());
                        plugin.getLogger().log(Level.WARNING, path + " removed to " + configuration);
                        config.set(path, (Object)null);
                    }
                }
                path = (String)var5.next();
            } while(config.contains(path) && config.get(path).getClass().getName() == default_conf.get(path).getClass().getName());
            plugin.getLogger().log(Level.WARNING, path + " added to " + configuration);
            config.set(path, default_conf.get(path));
        }
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public String getString(String path) {
        return fileConfiguration.getString(path);
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public boolean getBoolean(String path) {
        return fileConfiguration.getBoolean(path);
    }

    /**
     * Gets the requested String by path
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public int getInt(String path) {
        return fileConfiguration.getInt(path);
    }

    /**
     * Gets the requested String by path
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public double getDouble(String path) {
        return fileConfiguration.getDouble(path);
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public ConfigurationSection getConfigurationSection(String path) {
        return fileConfiguration.getConfigurationSection(path);
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public boolean contains(String path) {
        return fileConfiguration.contains(path);
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public boolean isString(String path) {
        return fileConfiguration.isString(path);
    }

    /**
     * Gets the requested key.
     * @param deep Set the deep.
     * @return The path if exists.
     */
    public Set<String> getKeys(boolean deep) {
        return fileConfiguration.getKeys(deep);
    }

    /**
     * Gets the requested key.
     * @param path Path of the String to get.
     * @param deep Set the deep.
     * @return The path if exists.
     */
    public Set<String> getKeysFromPath(String path, boolean deep) {
        return fileConfiguration.getConfigurationSection(path).getKeys(deep);
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @return The path if exists.
     */
    public List<String> getStringList(String path) {
        return fileConfiguration.getStringList(path);
    }

    /**
     * Gets the file.
     * @return The file if exists.
     */
    public File getFile() {
        return file;
    }

    /**
     * Gets the requested String by path.
     * @param path Path of the String to get.
     * @param o Set the object value.
     */
    public void set(String path, Object o) {
        fileConfiguration.set(path, o);
        try {
            fileConfiguration.save(file);
        } catch (IOException var4) {
            var4.printStackTrace();
        }
    }

    /**
     * Get the file configuration.
     * @return The file configuration.
     */
    public FileConfiguration getConfiguration() {
        return this.fileConfiguration;
    }

}
