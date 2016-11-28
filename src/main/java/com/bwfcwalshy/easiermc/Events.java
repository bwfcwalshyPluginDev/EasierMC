package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;

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
        if(e.getInventory().getName().equals(ChatColor.BLUE + "EasierMC Recipes")){
            e.setCancelled(true);

            Category category = Category.getCategory(e.getCurrentItem());

            Inventory inv = generateInventory(category);
            e.getWhoClicked().openInventory(inv);
        }else if(Category.isCategory(e.getInventory().getName())){
            e.setCancelled(true);

            // Code for the recipe
        }
    }

    private Inventory generateInventory(Category category) {
        Inventory inv = Bukkit.createInventory(null, 54, category.getCategoryName());

        for(EasierMCBase base : handler.getBlockRegistery()){
            if(base.getItem() != null) inv.addItem(base.getItem());
        }

        return inv;
    }
}
