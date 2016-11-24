package com.bwfcwalshy.easiermc.items;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getHand() == EquipmentSlot.HAND){
            if(e.getItem() != null && e.getItem().getType() != Material.AIR){

            }
        }
    }
}
