package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.BlockBase;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {

    private List<UUID> checkedPlayers = new ArrayList<>();

    private Handler handler;

    public Events(EasierMC pl) {
        this.handler = pl.getHandler();
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (handler.isBlock(e.getItemInHand())) {
            handler.addBlock(handler.getBlock(e.getItemInHand()), e.getBlockPlaced().getLocation());
        }
    }

    @EventHandler
    public void onRemove(BlockBreakEvent e) {
        if (handler.isBlock(e.getBlock().getLocation())) {
            BlockBase block = handler.getBlock(e.getBlock().getLocation());
            handler.removeBlock(e.getBlock().getLocation());
            e.getBlock().setType(Material.AIR);
            e.getBlock().getDrops().clear();
            if (e.getPlayer().getGameMode() != GameMode.CREATIVE)
                e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), block.getItem());
        }
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e) {
        if (handler.isBlock(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        // Make sure I don't keep doing full inventory checks.
        if (checkedPlayers.contains(e.getPlayer().getUniqueId())) return;
        System.out.println("Noobtubes fired");
        for (int i = 0; i < e.getPlayer().getInventory().getContents().length; i++) {
            ItemStack is = e.getPlayer().getInventory().getContents()[i];
            if (is != null && handler.getItemFromEverything(is) != null) {
                EasierMCBase base = handler.getItemFromEverything(is);
                System.out.println(is);
                System.out.println(base);
                if (handler.isHigherVersion(handler.getVersion(is), handler.getVersion(base.getItem()))) {
                    e.getPlayer().getInventory().setItem(i, base.getItem());
                }
            }
        }
        checkedPlayers.add(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onBlockClick(PlayerInteractEvent e) {
        if (e.getHand() == EquipmentSlot.HAND) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (handler.isBlock(e.getClickedBlock().getLocation())) {
                    handler.getBlock(e.getClickedBlock().getLocation()).onInteract(e);
                }
            }
        }
    }
}
