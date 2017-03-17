package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.ItemBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class CoalDust implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.DARK_GRAY + "Coal Dust";
    }

    @Override
    public String getSimpleName() {
        return "CoalDust";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.COAL, getName()).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe(){
        return new FurnaceRecipe(getItem(), Material.COAL);
    }
}
