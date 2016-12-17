package com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable;

import java.util.Collections;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.Cable;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

public class GlassFibreCable implements Cable {

    @Override
    public String getName() {
        return ChatColor.WHITE + "Glass Fibre Cable";
    }

    @Override
    public String getSimpleName() {
        return "GlassFibreCable";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.THIN_GLASS, 4, getName(),
                Collections.singletonList(ChatColor.GRAY + "Carries up to 512 EU/t"))
                .build();
    }

    @Override
    public int getMaxOutput() {
        return 512;
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "ggg", "rdr", "ggg")
                .setIngredient('g', Material.GLASS)
                .setIngredient('r', Material.REDSTONE)
                .setIngredient('d', Material.DIAMOND);
    }
}
