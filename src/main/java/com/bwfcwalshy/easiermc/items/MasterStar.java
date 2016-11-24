package com.bwfcwalshy.easiermc.items;

import com.bwfcwalshy.easiermc.Category;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class MasterStar implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.AQUA + "Master Star";
    }

    @Override
    public String getSimpleName() {
        return "MasterStar";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        ItemStack is = new ItemStack(Material.NETHER_STAR);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(getName());
        im.setLore(Arrays.asList(ChatColor.AQUA + "This Master Star is used to make the most powerful weapons around!"));
        is.setItemMeta(im);
        return is;
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("xx", "xx").setIngredient('x', Material.NETHER_STAR);
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {

    }
}
