package com.bwfcwalshy.easiermc.itemsandblocks;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.bwfcwalshy.easiermc.Handler;

public interface EasierMCBase {

    Handler handler = Handler.getInstance();

    String getName();

    String getSimpleName();

    Category getCategory();

    ItemStack getItem();

    default Recipe getRecipe() {
        return null;
    }

    /**
     * @return A clone of this object
     */
    EasierMCBase copy();
}
