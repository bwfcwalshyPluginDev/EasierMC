package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;

public class Events implements Listener {

    private EasierMC plugin;
    public Events(EasierMC pl){
        this.plugin = pl;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if(plugin.getHandler().isBlock(e.getItemInHand())) {
            plugin.getHandler().addBlock(plugin.getHandler().getBlock(e.getItemInHand()), e.getBlockPlaced().getLocation());
        }
    }

    @EventHandler
    public void onRemove(BlockBreakEvent e){
        if(plugin.getHandler().isBlock(e.getBlock().getLocation())){
            BlockBase block = plugin.getHandler().getBlock(e.getBlock().getLocation());
            plugin.getHandler().removeBlock(e.getBlock().getLocation());
            e.getBlock().setType(Material.AIR);
            e.getBlock().getDrops().clear();
            if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), block.getItem());
        }
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e){
        if(plugin.getHandler().isBlock(e.getBlock().getLocation())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvInteract(InventoryInteractEvent e){
        if(e.getInventory().getName().equals(ChatColor.BLUE + "EasierMC Recipes")){
            e.setCancelled(true);


        }
    }
}
