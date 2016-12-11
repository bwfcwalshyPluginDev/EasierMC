package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Arrays;

public class LongFallBoots implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.GRAY + "Long Fall Boots";
    }

    @Override
    public String getSimpleName() {
        return "LongFallBoots";
    }

    @Override
    public Category getCategory() {
        return Category.ARMOUR;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.DIAMOND_BOOTS, getName(), Arrays.asList(ChatColor.GRAY + "Fall with great heights without fear of dying!")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "ioi", "idi", "idi").setIngredient('i', Material.IRON_INGOT, 2).setIngredient('o', Material.OBSIDIAN, 2).setIngredient('d', Material.DIAMOND, 2);
    }
}
