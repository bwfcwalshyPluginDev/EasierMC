package com.bwfcwalshy.easiermc;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Configuration {

    private final String fileName;
    private final JavaPlugin plugin;
    private File configFile;
    private FileConfiguration fileConfiguration;

    public Configuration(String fileName, JavaPlugin plugin) {
        if(plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        } else {
            this.plugin = plugin;
            this.fileName = fileName;
            File dataFolder = plugin.getDataFolder();
            if(dataFolder == null) {
                throw new IllegalStateException();
            } else {
                this.configFile = new File(plugin.getDataFolder(), fileName);
                this.saveDefaultYaml();
            }
        }
    }

    public void reloadYaml() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.configFile);
        InputStreamReader defConfigStream = new InputStreamReader(this.plugin.getResource(this.fileName));
        if(defConfigStream != null) {
            YamlConfiguration e = YamlConfiguration.loadConfiguration(defConfigStream);
            this.fileConfiguration.setDefaults(e);
        }

        try {
            defConfigStream.close();
        } catch (IOException var3) {
            var3.printStackTrace();
        }

    }

    public FileConfiguration getYaml() {
        if(this.fileConfiguration == null) {
            this.reloadYaml();
        }

        return this.fileConfiguration;
    }

    public void saveYaml() {
        if(this.fileConfiguration != null && this.configFile != null) {
            try {
                this.getYaml().save(this.configFile);
            } catch (IOException var2) {
                this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, var2);
            }

        }
    }

    public void saveDefaultYaml() {
        if(!this.configFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }

    }

    public void modifyYaml() {
        this.saveYaml();
        this.reloadYaml();
    }
}
