package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.ItemBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;

public class ReinforcedStick implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.GRAY + "Reinforced Stick";
    }

    @Override
    public String getSimpleName() {
        return "ReinforcedStick";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.STICK, getName(), Arrays.asList(ChatColor.GRAY + "This can be used to make more powerful weapons than a normal stick.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("iii", "isi", "iii").setIngredient('i', Material.IRON_INGOT).setIngredient('s', Material.STICK);
    }

    @Override
    public ReinforcedStick copy() {
        return this;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
    }
}
