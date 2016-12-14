package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.Handler;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class ItemListener implements Listener {

    private Handler handler = Handler.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getHand() == EquipmentSlot.HAND){
            if(e.getItem() != null && e.getItem().getType() != Material.AIR){
                EasierMCBase base = handler.getItemFromEverything(e.getItem());
                System.out.println(base);
                if(base != null){
                    if(handler.isHigherVersion(handler.getVersion(base.getItem()), handler.getVersion(e.getItem()))){
                        System.out.println("Updated " + e.getPlayer().getName() + "'s " + base.getSimpleName());
                        e.getPlayer().getInventory().setItemInMainHand(base.getItem());
                    }

                    if(base instanceof ItemBase)
                        ((ItemBase) base).onInteract(e);
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
