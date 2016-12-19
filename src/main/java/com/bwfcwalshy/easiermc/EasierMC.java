package com.bwfcwalshy.easiermc;

import me.ialistannen.itemrecipes.easiermc.util.RecipeRegistry;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockListener;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ItemListener;
import com.bwfcwalshy.easiermc.tasks.BlockTickTask;
import com.bwfcwalshy.easiermc.utils.StringUtil;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class EasierMC extends JavaPlugin {

    public static final String VERSION = "v0.0.10-8";

    private Handler handler;
    private BukkitTask tickTask;
    private BlockTickTask blockTickTask;

    private Configuration dataConf;

    @Override
    public void onEnable() {
        dataConf = new Configuration("data.yml", this);

        handler = new Handler(this);
        handler.registerBlocks();
        handler.registerRecipes();

        getCommand("easiermc").setExecutor(new EasierMCCommand(this));

        getServer().getPluginManager().registerEvents(new Events(this), this);
        getServer().getPluginManager().registerEvents(new ItemListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);

        blockTickTask = new BlockTickTask(this);
        tickTask = getServer().getScheduler().runTaskTimer(this, blockTickTask, 20L, 1L);

        ConfigurationSection sec = getData().getConfigurationSection("Blocks");
        if (sec != null) {
            for (String s : sec.getKeys(false)) {
                if (handler.isValidBlock(getData().getString("Blocks." + s + ".Block"))) {
                    BlockBase base = handler.addBlock(handler.getBlock(getData().getString("Blocks." + s + ".Block")), getLocationFromString(s));
                    base.loadData(getData(), "Blocks." + s + ".Data");
                } else
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
        getData().set("Blocks", null);
        for (Location loc : handler.getBlocks().keySet()) {
            BlockBase block = handler.getBlocks().get(loc);

            getData().set("Blocks." + getLocationString(loc) + ".Block", block.getSimpleName());
            block.saveData(getData(), "Blocks." + getLocationString(loc) + ".Data");
        }
        dataConf.saveYaml();
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

    public FileConfiguration getData() {
        return this.dataConf.getYaml();
    }

    // I do not want others accessing this!!!!
    protected BlockTickTask getTickTask() {
        return this.blockTickTask;
    }
}

