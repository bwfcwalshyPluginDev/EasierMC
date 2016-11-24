package com.bwfcwalshy.easiermc.items;

import com.bwfcwalshy.easiermc.Category;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface ItemBase {

    String getName();

    String getSimpleName();

    Category getCategory();

    ItemStack getItem();

    Recipe getRecipe();

    void onInteract(PlayerInteractEvent e);
}
