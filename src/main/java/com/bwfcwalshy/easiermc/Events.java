package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;

import java.util.stream.Collectors;

public class Events implements Listener {

    private EasierMC plugin;
    private Handler handler;
    public Events(EasierMC pl){
        this.plugin = pl;
        this.handler = pl.getHandler();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(handler.isBlock(e.getItemInHand())) {
            handler.addBlock(handler.getBlock(e.getItemInHand()), e.getBlockPlaced().getLocation());
        }
    }

    @EventHandler
    public void onRemove(BlockBreakEvent e){
        if(handler.isBlock(e.getBlock().getLocation())){
            BlockBase block = handler.getBlock(e.getBlock().getLocation());
            handler.removeBlock(e.getBlock().getLocation());
            e.getBlock().setType(Material.AIR);
            e.getBlock().getDrops().clear();
            if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), block.getItem());
        }
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e){
        if(handler.isBlock(e.getBlock().getLocation())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e){
        if(e.getHand() == EquipmentSlot.HAND && e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(handler.isBlock(e.getClickedBlock().getLocation())){
                handler.getBlock(e.getClickedBlock().getLocation()).onInteract(e);
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) e.getWhoClicked();

        if(e.getInventory().getName().equals(ChatColor.BLUE + "EasierMC Recipes")){
            e.setCancelled(true);

            Category category = Category.getCategory(e.getCurrentItem());
            System.out.println("Clicked " + category);

            Inventory inv = generateInventory(category);
            player.openInventory(inv);
        }else if(Category.isCategory(e.getInventory().getName())){
            e.setCancelled(true);

            // Code for the recipe
            EasierMCBase base = handler.getItemFromEverything(e.getCurrentItem());
            if(base.getRecipe() != null && base.getRecipe() instanceof ShapedRecipe){
                Inventory craftInv = Bukkit.createInventory(null, InventoryType.WORKBENCH, e.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.BLUE + "'s Recipe");

                ShapedRecipe recipe = (ShapedRecipe) base.getRecipe();
                String shape = StringUtils.join(recipe.getShape());

                craftInv.setItem(0, base.getRecipe().getResult());
                for(int i = 0; i < 9; i++){
                    ItemStack is = recipe.getIngredientMap().get(shape.charAt(i));
                    craftInv.setItem(i+1, is);
                }

                player.openInventory(craftInv);
            }else if(base.getRecipe() != null && base.getRecipe() instanceof ShapelessRecipe){

            }else if(base.getRecipe() == null && base.getAdvancedRecipe() != null){
                Inventory craftInv = Bukkit.createInventory(null, InventoryType.WORKBENCH, e.getCurrentItem().getItemMeta().getDisplayName() + ChatColor.BLUE + "'s Recipe");

                AdvancedRecipe recipe = base.getAdvancedRecipe();
                String shape = StringUtils.join(recipe.getShape());

                craftInv.setItem(0, base.getItem());
                for(int i = 0; i < 9; i++){
                    ItemStack is = recipe.getIngredients().get(shape.charAt(i));
                    craftInv.setItem(i+1, is);
                }

                player.openInventory(craftInv);
                player.updateInventory();
            }
        }else if(e.getInventory().getName().endsWith(ChatColor.BLUE + "'s Recipe")){
            e.setCancelled(true);
            System.out.println(e.getSlot());
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

        return inv;
    }
}
