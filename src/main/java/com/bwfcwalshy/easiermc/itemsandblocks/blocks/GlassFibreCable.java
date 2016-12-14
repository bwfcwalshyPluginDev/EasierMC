package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Arrays;

public class GlassFibreCable implements BlockBase {

    @Override
    public String getName() {
        return ChatColor.WHITE + "Glass Fibre Cable";
    }

    @Override
    public String getSimpleName() {
        return "GlassFibreCable";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.THIN_GLASS, 4, getName(), Arrays.asList(ChatColor.GRAY + "Carries up to 512 EU/t")).build();
    }

    @Override
    public Recipe getRecipe(){
        return new AdvancedRecipe(getItem(), "ggg", "rdr", "ggg").setIngredient('g', Material.GLASS).setIngredient('r', Material.REDSTONE).setIngredient('d', Material.DIAMOND);
    }
}
