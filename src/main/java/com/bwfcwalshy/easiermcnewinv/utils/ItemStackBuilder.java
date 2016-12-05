package com.bwfcwalshy.easiermcnewinv.utils;

import com.bwfcwalshy.easiermcnewinv.CustomHead;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemStackBuilder {

    private static final int MAX_LORE_LENGTH = 45;

    private ItemStack is;
    private ItemMeta im;

    public ItemStackBuilder(Material material, String name, List<String> lore){
        is = new ItemStack(material);
        im = is.getItemMeta();
        im.setDisplayName(name);
        im.setLore(BetterLore.trimLoreToLength(lore, MAX_LORE_LENGTH));
    }

    public ItemStackBuilder(Material material, int amount, String name){
        is = new ItemStack(material, amount);
        im = is.getItemMeta();
        im.setDisplayName(name);
    }

    public ItemStackBuilder(Material material, String name){
        is = new ItemStack(material);
        im = is.getItemMeta();
        im.setDisplayName(name);
    }

    public ItemStackBuilder(String skullURL, String name){
        is = CustomHead.getSkull(skullURL);
        im = is.getItemMeta();
        im.setDisplayName(name);
    }

    public ItemStackBuilder(Material material){
        is = new ItemStack(material);
        im = is.getItemMeta();
    }

    public ItemStackBuilder setData(int data){
        is.setDurability((short) data);
        return this;
    }

    public ItemStackBuilder setDisplayName(String name){
        if(im == null) im = is.getItemMeta();
        im.setDisplayName(name);
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore){
        im.setLore(BetterLore.trimLoreToLength(lore, MAX_LORE_LENGTH));
        return this;
    }

    public ItemStack build(){
        is.setItemMeta(im);
        return is;
    }
}
