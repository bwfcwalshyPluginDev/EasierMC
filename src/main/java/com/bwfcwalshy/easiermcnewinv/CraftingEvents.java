package com.bwfcwalshy.easiermcnewinv;

import com.bwfcwalshy.easiermcnewinv.itemsandblocks.Category;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.AdvancedRecipe;
import com.bwfcwalshy.easiermcnewinv.utils.ItemStackBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.Arrays;

public class CraftingEvents implements Listener {

    private Handler handler;
    public CraftingEvents(EasierMC main){
        this.handler = main.getHandler();
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) e.getWhoClicked();

        if(e.getCurrentItem() == null) return;

        if(e.getInventory().getName().equals(ChatColor.BLUE + "EasierMC Recipes")){
            e.setCancelled(true);

            Category category = Category.getCategory(e.getCurrentItem());
            if(category != null) {
                Inventory inv = generateInventory(category);
                player.openInventory(inv);
                player.updateInventory();
            }else{
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Go Back")){
                    player.closeInventory();
                }
            }
        }else if(Category.isCategory(e.getInventory().getName())){
            e.setCancelled(true);

            if(handler.getItemFromEverything(e.getCurrentItem()) != null)
                openCraftingRecipe(player, e.getCurrentItem(), e.getInventory().getName());
            else{
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Go Back"))
                    openCategoryInventory(player);
            }
        }else if(e.getInventory().getName().endsWith(ChatColor.BLUE + "'s Recipe")){
            e.setCancelled(true);
            System.out.println(e.getSlot());

            if(handler.getItemFromEverything(e.getCurrentItem()) != null)
                openCraftingRecipe(player, e.getCurrentItem(), e.getInventory().getName());
            else
                if(e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Go Back")) openCategoryInventory(player);
        }
    }

    private void openCraftingRecipe(Player player, ItemStack is, String cameFrom){
        if(is == null || is.getType() == Material.AIR) return;
        EasierMCBase base = handler.getItemFromEverything(is);
        if(base != null){
            if(base.getRecipe() != null){
                if(base.getRecipe() instanceof ShapedRecipe){
                    Inventory craftInv = Bukkit.createInventory(null, InventoryType.WORKBENCH, base.getItem().getItemMeta().getDisplayName() + ChatColor.BLUE + "'s Recipe");

                    ShapedRecipe recipe = (ShapedRecipe) base.getRecipe();
                    String shape = StringUtils.join(recipe.getShape());

                    craftInv.setItem(0, base.getRecipe().getResult());
                    for(int i = 0; i < 9; i++){
                        craftInv.setItem(i+1, recipe.getIngredientMap().get(shape.charAt(i)));
                    }

                    player.openInventory(craftInv);
                    player.updateInventory();
                }else if(base.getRecipe() instanceof ShapelessRecipe){

                }else if(base.getRecipe() instanceof FurnaceRecipe){
                    Inventory craftInv = Bukkit.createInventory(null, InventoryType.FURNACE, base.getItem().getItemMeta().getDisplayName() + ChatColor.BLUE + "'s Recipe");

                    FurnaceRecipe recipe = (FurnaceRecipe) base.getRecipe();

                    player.openInventory(craftInv);
                }
            }else if(base.getAdvancedRecipe() != null){
                Inventory craftInv = Bukkit.createInventory(null, 36, base.getItem().getItemMeta().getDisplayName() + ChatColor.BLUE + "'s Recipe");

                AdvancedRecipe recipe = base.getAdvancedRecipe();
                String shape = StringUtils.join(recipe.getShape());

                int slot = 0;
                for(int i = 0; i < 9; i++){
                    if(i >= 3 && i % 3 == 0)
                        slot += 6;
                    craftInv.setItem(slot, recipe.getIngredients().get(shape.charAt(i)));
                    slot++;
                }
                for(int i = 27; i < 36; i++){
                    craftInv.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData((short) 7).build());
                }
                craftInv.setItem(28, new ItemStackBuilder(Material.ARROW, ChatColor.GREEN + "Go Back", Arrays.asList(" ", ChatColor.GRAY + "Go back to: " + cameFrom)).build());
                player.openInventory(craftInv);
            }else{
                // Item is uncraftable.
            }
        }else{
            // Default MC item
        }
    }

    private Inventory generateInventory(Category category) {
        Inventory inv = Bukkit.createInventory(null, 54, category.getCategoryName());

        int i = 0;
        for(EasierMCBase base : handler.getEntireRegistery()){
            if(base.getCategory() == category){
                if(i >= 45) break;
                inv.addItem(base.getItem());
                i++;
            }
        }

        // Bottom of the GUI
        for(int ii = 45; ii < 54; ii++){
            inv.setItem(ii, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData((short) 7).build());
        }
        inv.setItem(46, new ItemStackBuilder(Material.ARROW, ChatColor.GREEN + "Go Back").build());

        return inv;
    }

    private Inventory recipeInventory;
    public void openCategoryInventory(Player player){
        if(recipeInventory == null){ //9*(Math.round(Category.getValues().length / 9))
            recipeInventory = Bukkit.createInventory(null, 18, ChatColor.BLUE + "EasierMC Recipes");

            for(Category category : Category.getValues()){
                recipeInventory.addItem(category.getDisplayItem());
            }

            for(int i = 9; i < 18; i++){
                recipeInventory.setItem(i, new ItemStackBuilder(Material.STAINED_GLASS_PANE, " ").setData((short) 7).build());
            }
            recipeInventory.setItem(10, new ItemStackBuilder(Material.ARROW, ChatColor.GREEN + "Go Back").build());
        }
        player.openInventory(recipeInventory);
    }
}
