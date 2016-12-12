package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;
import java.util.Random;

public class TreeTap implements ItemBase {

    @Override
    public String getName() {
        return ChatColor.WHITE + "TreeTap";
    }

    @Override
    public String getSimpleName() {
        return "TreeTap";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.WOOD_HOE, getName(), Arrays.asList(ChatColor.GRAY + "Use this to tap some rubber from trees!")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape(" w ", "www", "w  ").setIngredient('w', Material.WOOD);
    }

    private Random random = new Random();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.LOG && e.getClickedBlock().getData() == 0){
                if(random.nextInt(100) <= 2){
                    e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), handler.getItem("Rubber").getItem());
                    e.getItem().setDurability((short) (e.getItem().getDurability()-1));
                }
            }
        }
    }
}
