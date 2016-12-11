package com.bwfcwalshy.easiermc.itemsandblocks.multiblock;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import nl.shanelab.multiblock.IMultiBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public interface MultiBlock extends IMultiBlock, EasierMCBase {

    String getSimpleName();

    Category getCategory();

    default String getName(){
        return null;
    }

    /**
     * This is used for the display item.
     * @return An ItemStack for the display item of this multiblock.
     */
    ItemStack getItem();

    default Recipe getRecipe(){
        return null;
    }
}