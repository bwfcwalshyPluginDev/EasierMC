package com.bwfcwalshy.easiermc.itemsandblocks.multiblock;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import nl.shanelab.multiblock.IMultiBlock;

public interface MultiBlock extends IMultiBlock {

    String getSimpleName();

    Category getCategory();
}
