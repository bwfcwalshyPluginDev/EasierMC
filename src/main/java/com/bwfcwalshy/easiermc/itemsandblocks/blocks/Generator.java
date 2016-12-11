package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

public class Generator implements BlockBase {

    private Inventory inventory;

    @Override
    public String getSimpleName() {
        return "Generator";
    }

    @Override
    public String getName() {
        return ChatColor.GRAY + "Generator";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/12616a70e8f74ccb6f7f44e4aee5dbcbbcb80c9f23e1dccb07a5f156521215", getName())
                .setLore(Arrays.asList(ChatColor.GRAY + "Generate electricity by burning fuel sources.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), " i ", "rrr", "   ").setIngredient('i', Material.IRON_BLOCK).setIngredient('r', Material.IRON_INGOT);
    }

    @Override
    public void tick(Location location) {

    }

    @Override
    public Generator copy() {
        Generator clone = new Generator();
        clone.inventory = cloneInventory(inventory);
        return clone;
    }

    @Override
    public void onInteract(PlayerInteractEvent e){
        if(inventory == null){
            inventory = Bukkit.createInventory(null, 27, getName());

            for(int i = 0; i < 27; i++){
                inventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData(7).build());
            }
            inventory.setItem(10, null);
        }

        e.getPlayer().openInventory(inventory);
    }
}
