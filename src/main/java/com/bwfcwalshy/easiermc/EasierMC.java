package com.bwfcwalshy.easiermc;

import java.io.File;

import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ItemListener;
import com.bwfcwalshy.easiermc.tasks.BlockTickTask;
import com.bwfcwalshy.easiermc.utils.StringUtil;

import me.ialistannen.itemrecipes.easiermc.util.RecipeRegistry;

public class EasierMC extends JavaPlugin {

    public static final String VERSION = "v0.0.6";

    private Handler        handler;
    private BukkitTask     tickTask;

    @Override
    public void onEnable() {
        if (!new File(getDataFolder(), "config.yml").exists())
            saveDefaultConfig();

        handler = new Handler(this);
        handler.registerBlocks();
        handler.registerRecipes();

        getCommand("easiermc").setExecutor(new EasierMCCommand());

        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);

        tickTask = getServer().getScheduler().runTaskTimer(this, new BlockTickTask(this), 20L, 20L);

        ConfigurationSection sec = getConfig().getConfigurationSection("Blocks");
        if (sec != null) {
            for (String s : sec.getKeys(false)) {
                if (handler.isValidBlock(getConfig().getString("Blocks." + s + ".Block")))
                    handler.addBlock(handler.getBlock(getConfig().getString("Blocks." + s + ".Block")), getLocationFromString(s));
                else
                    getLogger().warning("Block with the name '" + s + "' attempted to load but was not a valid block!");
            }
        }

        // Load the recipes for the Gui
        RecipeRegistry.INSTANCE.loadRecipes();

        getLogger().info("**********************************");
        getLogger().info("* EasierMC has finished loading! *");
        getLogger().info("*                                *");
        getLogger().info("*" + StringUtil.padToLength("Registered " + handler.getBlockRegistery().size() + " blocks", ' ', 32) + "*");
        getLogger().info("*" + StringUtil.padToLength("Registered " + handler.getItemRegistery().size() + " items", ' ', 32) + "*");
        getLogger().info("*                                *");
        getLogger().info("*" + StringUtil.padToLength("Loaded " + handler.getBlocks().size() + " blocks!", ' ', 32) + "*");
        getLogger().info("**********************************");
    }

    @Override
    public void onDisable() {
        tickTask.cancel();

        // Save data
        getConfig().set("Blocks", null);
        for (Location loc : handler.getBlocks().keySet()) {
            BlockBase block = handler.getBlocks().get(loc);

            getConfig().set("Blocks." + getLocationString(loc) + ".Block", block.getSimpleName());
        }
        saveConfig();
    }

    public Handler getHandler() {
        return this.handler;
    }

    private String getLocationString(Location loc) {
        return loc.getWorld().getName() + "," + loc.getBlockX() + "," + loc.getBlockY() + "," + loc.getBlockZ();
    }

    private Location getLocationFromString(String s) {
        String[] split = s.split(",");
        if (split.length != 4)
            return null;
        return new Location(Bukkit.getWorld(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]));
    }
}

