package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.blocks.BlockBase;
import com.bwfcwalshy.easiermc.blocks.BlockBreaker;
import com.bwfcwalshy.easiermc.items.ItemBase;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Category {

    BASIC_MACHINE(getHandler().getBlock("BlockBreaker"), ChatColor.DARK_GRAY + "Basic Machines"),
    MACHINE(getHandler().getBlock("Generator"), ChatColor.GRAY + "Machines"),
    WEAPONS(new ItemStack(Material.IRON_SWORD), ChatColor.WHITE + "Weapons"),
    MISC(getHandler().getItem("MasterStar"));

    private static Handler handler = Handler.getInstance();

    private ItemStack displayItem;
    private String categoryName;

    Category(BlockBase displayItem, String name){
        this.displayItem = displayItem.getItem();
        this.categoryName = name;
    }

    Category(ItemBase displayItem, String name){
        this.displayItem = displayItem.getItem();
        this.categoryName = name;
    }

    Category(ItemStack displayItem, String name){
        this.displayItem = displayItem;
        this.categoryName = name;
    }

    private static Handler getHandler(){
        return handler;
    }
}
