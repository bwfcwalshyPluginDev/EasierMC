package com.bwfcwalshy.easiermcnewinv.itemsandblocks.items;

import com.bwfcwalshy.easiermcnewinv.Handler;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class ItemListener implements Listener {

    private Handler handler = Handler.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getHand() == EquipmentSlot.HAND){
            if(e.getItem() != null && e.getItem().getType() != Material.AIR){
                ItemBase base = handler.getItem(e.getItem());
                if(base != null){
                    base.onInteract(e);
                }
            }
        }
    }
}
