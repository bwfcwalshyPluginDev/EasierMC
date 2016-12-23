package com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.Cable;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Collections;

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
                .autoUpdate()
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
