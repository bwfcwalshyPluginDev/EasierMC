package com.bwfcwalshy.easiermc.itemsandblocks;

import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.AdvancedRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface EasierMCBase {

    String getName();

    String getSimpleName();

    Category getCategory();

    ItemStack getItem();

    Recipe getRecipe();

    default AdvancedRecipe getAdvancedRecipe(){
        return null;
    }
}
