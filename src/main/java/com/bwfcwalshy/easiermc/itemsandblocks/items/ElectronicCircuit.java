package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.ItemBase;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class ElectronicCircuit implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.GOLD + "Electronic Circuit";
    }

    @Override
    public String getSimpleName() {
        return "ElectronicCircuit";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.REDSTONE_COMPARATOR, getName()).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "ccc", "rir", "ccc").setIngredient('c', handler.getBlock("IronCable").getItem(), 1).setIngredient('r', Material.REDSTONE)
                .setIngredient('i', Material.IRON_INGOT);
    }
}
