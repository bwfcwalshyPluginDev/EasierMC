package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class IronCable implements BlockBase {

    @Override
    public String getName() {
        return ChatColor.GRAY + "Iron Cable";
    }

    @Override
    public String getSimpleName() {
        return "IronCable";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.STAINED_GLASS_PANE, getName(), Arrays.asList(ChatColor.GRAY + "Carries up to 32 EU/t")).setData(7).build();
    }
}
