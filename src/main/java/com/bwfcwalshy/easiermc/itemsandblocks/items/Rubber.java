package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.ItemBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Rubber implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.DARK_GRAY + "Rubber";
    }

    @Override
    public String getSimpleName() {
        return "Rubber";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.CLAY_BALL, getName()).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
