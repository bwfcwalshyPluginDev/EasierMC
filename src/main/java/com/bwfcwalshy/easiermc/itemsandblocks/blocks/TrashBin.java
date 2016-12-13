package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

public class TrashBin implements BlockBase {

    @Override
    public String getName() {
        return ChatColor.DARK_GRAY + "Trash Bin";
    }

    @Override
    public String getSimpleName() {
        return "TrashBin";
    }

    @Override
    public Category getCategory() {
        return Category.BLOCKS;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.CAULDRON_ITEM, ChatColor.DARK_GRAY + "Trash Bin").build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("iii", "ili", "iii")
                .setIngredient('i', Material.IRON_INGOT)
                .setIngredient('l', Material.LAVA_BUCKET);
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.setCancelled(true);
        Inventory bin = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "Trash Bin");
        e.getPlayer().openInventory(bin);
    }

    @Override
    public TrashBin copy() {
        return this;
    }
}
