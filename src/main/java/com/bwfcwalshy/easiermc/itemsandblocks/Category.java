package com.bwfcwalshy.easiermc.itemsandblocks;

import com.bwfcwalshy.easiermc.Handler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum Category {

    BASIC_MACHINE(getHandler().getBlock("BlockBreaker"), ChatColor.DARK_GRAY + "Basic Machines"),
    MACHINE(getHandler().getBlock("Generator"), ChatColor.GRAY + "Machines"),
    WEAPONS(new ItemStack(Material.IRON_SWORD), ChatColor.WHITE + "Weapons"),
    MISC(getHandler().getItem("MasterStar"), ChatColor.AQUA + "Miscellaneous");

    private static Handler handler = Handler.getInstance();

    private ItemStack displayItem;
    private String categoryName;

    public static Category[] values = values();

    Category(EasierMCBase displayItem, String name){
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

    public ItemStack getDisplayItem(){
        ItemStack is = new ItemStack(displayItem.getType());
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(categoryName);
        im.setLore(Arrays.asList("", ChatColor.AQUA + "> " + ChatColor.GRAY + "Click to open the category " + ChatColor.YELLOW + ChatColor.stripColor(getCategoryName())));
        is.setItemMeta(im);
        return is;
    }

    public String getCategoryName(){
        return this.categoryName;
    }
}
