package com.bwfcwalshy.easiermcnewinv.itemsandblocks;

import com.bwfcwalshy.easiermcnewinv.Handler;
import com.bwfcwalshy.easiermcnewinv.recipe.AdvancedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface EasierMCBase {

    Handler handler = Handler.getInstance();

    String getName();

    String getSimpleName();

    Category getCategory();

    ItemStack getItem();

    default Recipe getRecipe() {
        return null;
    }
}
