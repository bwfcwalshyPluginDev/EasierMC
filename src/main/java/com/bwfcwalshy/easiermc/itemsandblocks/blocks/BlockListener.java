package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.Handler;
import com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines.ElectricFurnace;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class BlockListener implements Listener {

    private Handler handler = Handler.getInstance();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(ChatColor.GRAY + "Generator")) {
            if (getClickedInventory(e) != null && getClickedInventory(e) != e.getWhoClicked().getInventory() && e.getSlot() != 10) {
                e.setCancelled(true);
            }
        } else if (e.getInventory().getName().equals(ChatColor.DARK_GRAY + "Batbox")) {
            e.setCancelled(true);
        } else if (e.getInventory().getName().equals(ChatColor.GRAY + "Electric Furnace")) {
            if(getClickedInventory(e) != null && e.getClickedInventory() != e.getWhoClicked().getInventory() && e.getSlot() == 1)
                e.setCancelled(true);
        }
    }

    public Inventory getClickedInventory(InventoryClickEvent e) {
        if (e.getRawSlot() < 0) {
            return null;
        } else if (e.getView().getTopInventory() != null && e.getRawSlot() < e.getView().getTopInventory().getSize()) {
            return e.getView().getTopInventory();
        } else {
            return e.getView().getBottomInventory();
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Block block = e.getBlock();
        if(handler.isBlock(block.getLocation())){
            if(handler.getBlock(block.getLocation()) instanceof ElectricFurnace){
                Furnace furnace = (Furnace) block.getState();
                furnace.getInventory().setFuel(null);
                System.out.println("Yay");
            }
        }
    }
}
