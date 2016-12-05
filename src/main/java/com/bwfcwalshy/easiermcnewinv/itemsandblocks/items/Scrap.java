package com.bwfcwalshy.easiermcnewinv.itemsandblocks.items;

import com.bwfcwalshy.easiermcnewinv.itemsandblocks.Category;
import com.bwfcwalshy.easiermcnewinv.utils.ItemStackBuilder;
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
        return new ItemStackBuilder(Material.BRICK, getName()).build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }
}
