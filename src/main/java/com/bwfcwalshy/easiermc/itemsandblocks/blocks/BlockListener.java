package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class BlockListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(e.getInventory().getName().equals(ChatColor.GRAY + "Generator")){
            if(getClickedInventory(e) != null && getClickedInventory(e) != e.getWhoClicked().getInventory() && e.getSlot() != 10){
                e.setCancelled(true);
            }
        }
    }

    public Inventory getClickedInventory(InventoryClickEvent e){
        if(e.getRawSlot() < 0) {
            return null;
        } else if(e.getView().getTopInventory() != null && e.getRawSlot() < e.getView().getTopInventory().getSize()) {
            return e.getView().getTopInventory();
        } else {
            return e.getView().getBottomInventory();
        }
    }
}
