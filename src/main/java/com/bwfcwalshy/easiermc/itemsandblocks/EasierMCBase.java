package com.bwfcwalshy.easiermc.itemsandblocks;

import com.bwfcwalshy.easiermc.Handler;
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

    default AdvancedRecipe getAdvancedRecipe(){
        return null;
    }
}
