package com.bwfcwalshy.easiermcnewinv.itemsandblocks.blocks;

import com.bwfcwalshy.easiermcnewinv.itemsandblocks.Category;
import com.bwfcwalshy.easiermcnewinv.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermcnewinv.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Arrays;

public class Generator implements BlockBase {

    @Override
    public String getSimpleName() {
        return "Generator";
    }

    @Override
    public String getName() {
        return ChatColor.GRAY + "Generator";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/12616a70e8f74ccb6f7f44e4aee5dbcbbcb80c9f23e1dccb07a5f156521215", getName())
                .setLore(Arrays.asList(ChatColor.GRAY + "Generate electricity by burning fuel sources.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), " i ", "rrr", "   ").setIngredient('i', Material.IRON_BLOCK).setIngredient('r', Material.IRON_INGOT);
    }

    @Override
    public void tick(Location location) {

    }
}
