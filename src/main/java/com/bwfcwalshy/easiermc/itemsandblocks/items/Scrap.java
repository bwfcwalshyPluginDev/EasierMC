package com.bwfcwalshy.easiermc.itemsandblocks.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

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
        return new ItemStackBuilder(Material.BRICK, getName()).build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public Scrap copy() {
        return this;
    }
}
