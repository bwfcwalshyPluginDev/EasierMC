package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.Handler;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
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

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            if(e.getEntity() instanceof Player){
                Player player = (Player) e.getEntity();
                if(handler.getItem(player.getInventory().getBoots()) instanceof LongFallBoots){
                    e.setCancelled(true);
                }
            }
        }
    }
}
