package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class Batbox implements MachineBase {

    @Override
    public String getName() {
        return ChatColor.DARK_GRAY + "Batbox";
    }

    @Override
    public String getSimpleName() {
        return "Batbox";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/6e6366e47831f107ac8a781df1bb3aa54f777612c9157d8be8e84803361e16f", getName())
                .setLore(Arrays.asList(ChatColor.GRAY + "Max I/O 32 EU/t", ChatColor.GRAY + "Capacity: 40,000 EU")).build();
    }

    @Override
    public int euCapacity() {
        return 0;
    }

    @Override
    public int euInputOutput() {
        return 0;
    }
}
