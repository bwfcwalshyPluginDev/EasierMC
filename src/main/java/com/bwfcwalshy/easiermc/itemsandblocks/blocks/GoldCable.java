package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GoldCable implements BlockBase {

    @Override
    public String getName() {
        return ChatColor.GOLD + "Gold Cable";
    }

    @Override
    public String getSimpleName() {
        return "GoldCable";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE, getName(), Arrays.asList(ChatColor.GRAY + "Carries up to 128 EU/t")).setData(4).build();
    }
}
