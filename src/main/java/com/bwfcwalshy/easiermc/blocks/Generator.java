package com.bwfcwalshy.easiermc.blocks;

import com.bwfcwalshy.easiermc.Category;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Generator implements BlockBase {

    @Override
    public String getSimpleName() {
        return "Generator";
    }

    @Override
    public String getName() {
        return ChatColor.GRAY + "Generator";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return null;
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public void tick(Location location) {

    }
}
