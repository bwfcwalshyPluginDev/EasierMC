package com.bwfcwalshy.easiermc.itemsandblocks.blocks.cable;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.Cable;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import java.util.Collections;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class GoldCable implements Cable {

    @Override
    public String getName() {
        return ChatColor.GOLD + "Gold Cable";
    }

    @Override
    public String getSimpleName() {
        return "GoldCable";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE, 6, getName(),
                Collections.singletonList(ChatColor.GRAY + "Carries up to 128 EU/t"))
                .setData(4)
                .build();
    }

    @Override
    public int getMaxOutput() {
        return 128;
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "rrr", "ggg", "rrr")
                .setIngredient('r', handler.getItem("Rubber").getItem())
                .setIngredient('g', Material.GOLD_INGOT);
    }
}
