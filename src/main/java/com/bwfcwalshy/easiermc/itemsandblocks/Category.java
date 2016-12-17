package com.bwfcwalshy.easiermc.itemsandblocks;

import com.bwfcwalshy.easiermc.Handler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public enum Category {

    BLOCKS(new ItemStack(Material.WORKBENCH), ChatColor.AQUA + "Blocks"),
    ARMOUR(getHandler().getItem("LongFallBoots"), ChatColor.GREEN + "Armour"),
    BASIC_MACHINE(getHandler().getBlock("BlockBreaker"), ChatColor.DARK_GRAY + "Basic Machines"),
    MACHINE(getHandler().getBlock("Generator"), ChatColor.GRAY + "Machines"),
    WEAPONS(new ItemStack(Material.IRON_SWORD), ChatColor.WHITE + "Weapons"),
    MISC(getHandler().getItem("MasterStar"), ChatColor.AQUA + "Miscellaneous");

    private static Handler handler;

    private ItemStack displayItem;
    private String categoryName;

    public static final Category[] values = values();

    Category(EasierMCBase displayItem, String name) {
        this.displayItem = displayItem.getItem();
        this.categoryName = name;
    }

    Category(ItemStack displayItem, String name) {
        this.displayItem = displayItem;
        this.categoryName = name;
    }

    private static Handler getHandler() {
        if (handler == null) handler = Handler.getInstance();
        return handler;
    }

    public static Category[] getValues() {
        return values;
    }

    public ItemStack getDisplayItem() {
        ItemMeta im = displayItem.getItemMeta();
        im.setDisplayName(categoryName);
        if (im.hasLore())
            im.getLore().clear();
        im.setLore(Arrays.asList("", ChatColor.AQUA + "> " + ChatColor.GRAY + "Click to open the category " + ChatColor.YELLOW + ChatColor.stripColor(getCategoryName())));
        displayItem.setItemMeta(im);
//        ItemStack is = new ItemStack(displayItem.getType(), displayItem.getAmount(), displayItem.getDurability());
//        ItemMeta im = is.getItemMeta();
//        im.setDisplayName(categoryName);
//        im.setLore(Arrays.asList("", ChatColor.AQUA + "> " + ChatColor.GRAY + "Click to open the category " + ChatColor.YELLOW + ChatColor.stripColor(getCategoryName())));
//        is.setItemMeta(im);
        return displayItem;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public static Category getCategory(ItemStack currentItem) {
        for (Category c : values)
            if (c.getDisplayItem().equals(currentItem)) return c;
        return null;
    }

    public static boolean isCategory(String name) {
        for (Category c : values)
            if (c.getCategoryName().equals(name)) return true;
        return false;
    }
}
