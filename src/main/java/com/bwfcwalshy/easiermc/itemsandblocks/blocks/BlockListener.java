package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class BlockListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getInventory().getName().equals(ChatColor.GRAY + "Generator")){
            if(e.getClickedInventory() != e.getWhoClicked().getInventory() && e.getSlot() != 10){
                e.setCancelled(true);
            }
        }
    }
}
