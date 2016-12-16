package com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable;

import java.util.Collections;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

public class IronCable implements Cable {

    @Override
    public String getName() {
        return ChatColor.GRAY + "Iron Cable";
    }

    @Override
    public String getSimpleName() {
        return "IronCable";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE, 6, getName(),
                Collections.singletonList(ChatColor.GRAY + "Carries up to 32 EU/t"))
                .setData(7)
                .build();
    }

    @Override
    public int getMaxThroughput() {
        return 32;
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "rrr", "iii", "rrr")
                .setIngredient('r', handler.getItem("Rubber").getItem())
                .setIngredient('i', Material.IRON_INGOT);
    }
}
