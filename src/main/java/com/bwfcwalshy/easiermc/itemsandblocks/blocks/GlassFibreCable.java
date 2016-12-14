package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GlassFibreCable implements BlockBase {

    @Override
    public String getName() {
        return ChatColor.WHITE + "Glass Fibre Cable";
    }

    @Override
    public String getSimpleName() {
        return "GlassFibreCable";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.THIN_GLASS, getName(), Arrays.asList(ChatColor.GRAY + "Carries up to 512 EU/t")).build();
    }
}
