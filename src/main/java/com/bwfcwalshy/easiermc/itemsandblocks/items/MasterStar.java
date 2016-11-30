package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
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
        return new ItemStackBuilder(Material.NETHER_STAR, getName(), Arrays.asList(ChatColor.AQUA + "This Master Star is used to make the most powerful weapons around!")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("xx", "xx").setIngredient('x', Material.NETHER_STAR);
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {

    }
}
