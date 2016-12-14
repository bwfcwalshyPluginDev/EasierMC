package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Arrays;

public class IronCable implements BlockBase {

    @Override
    public String getName() {
        return ChatColor.GRAY + "Iron Cable";
    }

    @Override
    public String getSimpleName() {
        return "IronCable";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE, 6, getName(), Arrays.asList(ChatColor.GRAY + "Carries up to 32 EU/t")).setData(7).build();
    }

    @Override
    public Recipe getRecipe(){
        return new AdvancedRecipe(getItem(), "rrr", "iii", "rrr").setIngredient('r', handler.getItem("Rubber").getItem()).setIngredient('i', Material.IRON_INGOT);
    }
}
