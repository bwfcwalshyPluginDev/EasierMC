package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.ItemBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class Scrap implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.GRAY + "Scrap";
    }

    @Override
    public String getSimpleName() {
        return "Scrap";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.BRICK, getName()).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
