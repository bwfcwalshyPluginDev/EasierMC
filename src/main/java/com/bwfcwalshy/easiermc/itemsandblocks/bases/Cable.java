package com.bwfcwalshy.easiermc.itemsandblocks.bases;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;

/**
 * A base class for cables
 */
public interface Cable extends BlockBase {

    @Override
    default Category getCategory() {
        return Category.MISC;
    }

    /**
     * @return The maximum EU/Tick this cable can handle
     */
    int getMaxOutput();
}
