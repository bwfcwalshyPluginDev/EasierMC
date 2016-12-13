package com.bwfcwalshy.easiermc;

import com.bwfcwalshy.easiermc.itemsandblocks.blocks.BlockBase;
import com.bwfcwalshy.easiermc.itemsandblocks.items.ItemBase;
import me.ialistannen.itemrecipes.easiermc.util.Util;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Events implements Listener {

    private Handler handler;
    public Events(EasierMC pl){
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
        if(e.getHand() == EquipmentSlot.HAND){
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (handler.isBlock(e.getClickedBlock().getLocation())) {
                    handler.getBlock(e.getClickedBlock().getLocation()).onInteract(e);
                }
            }
            ItemBase item = handler.getItem(e.getItem());
            if(e.getItem() != null && item != null){
                List<String> lore = e.getItem().getItemMeta().getLore();

                for(String s : lore){
                    if(Util.isHiddenLine(s)){
                        String hiddenStr = Util.showString(s);
                        if(hiddenStr.contains("Version: ")){
                            if(isHigherVersion(EasierMC.VERSION, s)){
                                // Update lore
                                if(!handler.itemStackEquals(e.getItem(), item.getItem(), false, false)){
                                    ItemStack is = e.getItem();
                                    ItemMeta im = is.getItemMeta();
                                    im.setLore(item.getItem().getItemMeta().getLore());
                                    is.setItemMeta(im);
                                    e.getPlayer().getInventory().setItemInMainHand(is);
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    /**
     * Check if a string is higher than the current one.
     * @param currentVer Current version of the plugin.
     * @param version The version to check.
     * @return If version is greater than currentVar
     */
    private boolean isHigherVersion(String currentVer, String version){
        String[] currentVersion = currentVer.replace("Version: v", "").split("\\.");
        String[] ver = version.replace("Version: v", "").split("\\.");

        if(Integer.parseInt(currentVersion[0]) > Integer.parseInt(ver[0]))
            return true;
        else if(Integer.parseInt(currentVersion[1]) > Integer.parseInt(ver[2]))
            return true;
        else if(Integer.parseInt(currentVersion[1]) > Integer.parseInt(ver[2]))
            return true;
        else
            return false;
    }

    private String getVersion(ItemStack is){
        if(!is.hasItemMeta() || !is.getItemMeta().hasLore()) return null;
        for(String s : is.getItemMeta().getLore()){
            if(s.contains("Version: ")){
                return s.replace("Version: v", "");
            }
        }
        return null;
    }
}
