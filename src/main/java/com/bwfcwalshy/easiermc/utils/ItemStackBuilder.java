package com.bwfcwalshy.easiermc.utils;

import me.ialistannen.itemrecipes.easiermc.util.Util;

import com.bwfcwalshy.easiermc.CustomHead;
import com.bwfcwalshy.easiermc.EasierMC;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * The ItemStackBuilder allows ItemStacks to be very easily created.
 * ItemStackBuilder also comes with an automatic lore-shorter, so if your lore is greater than MAX_LORE_LENGTH then it will be moved to a new line.
 * The lore will also keep whatever ChatColor it was using as well! And it will not split it mid-word, it will always check for the <b>closest</b> space and split there.
 * <br>
 * ItemStackBuilder also comes with an auto-updater, this means you can make your items or blocks automatically update the lore.
 * To do this the builder will save the current version in the lore hidden with ChatColors and EasierMC will check that when a player first moves or when a player interacts.
 * If the item is not up to date it will make sure it is!
 * <br>
 * To enable to auto-updater just do {@link #autoUpdate()} and if you want to disable it or enable it at other times you can do {@link #setAutoUpdate(boolean)}
 */
public class ItemStackBuilder {

    /**
     * Maximum length a lore can be before being split. (This is measured in characters)
     */
    private static final int MAX_LORE_LENGTH = 45;

    private ItemStack is;
    private ItemMeta im;

    private boolean autoUpdate;

    /**
     * Create an ItemStack with the material, display name and lore.
     * @param material The item Material.
     * @param name The item display name.
     * @param lore The item lore.
     */
    public ItemStackBuilder(Material material, String name, List<String> lore) {
        is = new ItemStack(material);
        im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(BetterLore.trimLoreToLength(lore, MAX_LORE_LENGTH));
    }

    /**
     * Create an ItemStack with the material, amount and display name.
     * @param material The item Material.
     * @param amount The item amount.
     * @param name The item display name.
     */
    public ItemStackBuilder(Material material, int amount, String name) {
        is = new ItemStack(material, amount);
        im = is.getItemMeta();
        im.setDisplayName(name);
    }

    /**
     * Create an ItemStack with the material, amount and display name.
     * @param material The item Material.
     * @param amount The item amount.
     * @param name The item display name.
     * @param lore The item lore.
     */
    public ItemStackBuilder(Material material, int amount, String name, List<String> lore) {
        is = new ItemStack(material, amount);
        im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(BetterLore.trimLoreToLength(lore, MAX_LORE_LENGTH));
    }

    /**
     * Create an ItemStack with the material and display name.
     * @param material The item Material.
     * @param name The item display name.
     */
    public ItemStackBuilder(Material material, String name) {
        is = new ItemStack(material);
        im = is.getItemMeta();
        im.setDisplayName(name);
    }

    /**
     * Create an ItemStack with a custom skull URL and display name
     * @param skullURL The item skull URL. Make sure this is the full URL! http://textures.minecraft.net/texture/{@literal <SkullURL>}
     * @param name The item display name.
     */
    public ItemStackBuilder(String skullURL, String name) {
        is = CustomHead.getSkull(skullURL);
        im = is.getItemMeta();
        im.setDisplayName(name);
    }

    /**
     * Create an ItemStack with only the material.
     * @param material The item Material.
     */
    public ItemStackBuilder(Material material) {
        is = new ItemStack(material);
        im = is.getItemMeta();
    }

    /**
     * Sets the ItemStack data.
     * @param data The item data value.
     */
    public ItemStackBuilder setData(int data) {
        is.setDurability((short) data);
        return this;
    }

    /**
     * Sets the ItemStack display name.
     * @param The item display name.
     */
    public ItemStackBuilder setDisplayName(String name) {
        if (im == null) im = is.getItemMeta();
        im.setDisplayName(name);
        return this;
    }

    /**
     * Sets the ItemStack lore.
     * @param lore The item lore.
     */
    public ItemStackBuilder setLore(List<String> lore) {
        im.setLore(BetterLore.trimLoreToLength(lore, MAX_LORE_LENGTH));
        return this;
    }

    /**
     * Sets the ItemStack to auto-update
     */
    public ItemStackBuilder autoUpdate(){
        this.autoUpdate = true;
        return this;
    }

    /**
     * Sets whether or not the ItemStack should auto-update.
     * @param autoUpdate Whether or not the ItemStack should auto-update.
     */
    public ItemStackBuilder setAutoUpdate(boolean autoUpdate){
        this.autoUpdate = autoUpdate;
        return this;
    }

    /**
     * Builds the ItemStack.
     * @return The built ItemStack.
     */
    public ItemStack build() {
        List<String> lore = im.getLore() == null ? new ArrayList<>() : im.getLore();
        if(autoUpdate)
            lore.add(Util.hideString("Version: " + EasierMC.VERSION));
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }
}
