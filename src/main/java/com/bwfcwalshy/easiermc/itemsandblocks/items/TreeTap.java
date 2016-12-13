package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        return new ItemStackBuilder(Material.WOOD_HOE, getName(), Arrays.asList(ChatColor.GRAY + "Use this to tap some rubber from trees!"
                , ChatColor.GRAY + "This gives a 2% chance for rubber to be given from oak logs.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape(" w ", "www", "w  ").setIngredient('w', Material.WOOD);
    }

    private Random random = new Random();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        System.out.println(e.getItem());

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            System.out.println(e.getClickedBlock().getType() + " - " + e.getClickedBlock().getData());
            if(e.getClickedBlock().getType() == Material.LOG && e.getClickedBlock().getData() == 0){
                if(random.nextInt(100) <= 2){
                    e.getClickedBlock().getWorld().dropItemNaturally(e.getClickedBlock().getLocation(), handler.getItem("Rubber").getItem());
                }
                e.getPlayer().getInventory().getItemInMainHand().setDurability((short) (e.getItem().getDurability()+1));
                if(e.getPlayer().getInventory().getItemInMainHand().getDurability() <= e.getPlayer().getInventory().getItemInMainHand().getType().getMaxDurability()){
                    e.getPlayer().getInventory().setItemInMainHand(null);
                }
            }
        }
    }
}
