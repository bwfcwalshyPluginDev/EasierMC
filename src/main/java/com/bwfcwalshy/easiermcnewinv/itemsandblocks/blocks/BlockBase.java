package com.bwfcwalshy.easiermcnewinv.itemsandblocks.blocks;

import com.bwfcwalshy.easiermcnewinv.itemsandblocks.EasierMCBase;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public interface BlockBase extends EasierMCBase {

    Random rand = new Random();

    default void tick(Location location){
        return;
    }

    default void onInteract(PlayerInteractEvent e){
        return;
    }

    default void tryToStore(Location location, ItemStack toStore) {
        Random rand = new Random();
        Block b = location.getBlock();
        // Now to check if there's a chest
        if(b.getRelative(BlockFace.UP).getType() == Material.CHEST || b.getRelative(BlockFace.UP).getType() == Material.TRAPPED_CHEST ||
                b.getRelative(BlockFace.DOWN).getType() == Material.CHEST || b.getRelative(BlockFace.DOWN).getType() == Material.TRAPPED_CHEST){
            Block chestBlock = (b.getRelative(BlockFace.UP).getType() == Material.CHEST ? b.getRelative(BlockFace.UP) : b.getRelative(BlockFace.DOWN));
            Chest chest = (Chest) chestBlock.getState();
            // Check if inv is full
            ItemStack is = toStore;
            if(canFit(chest.getInventory(), is))
                chest.getInventory().addItem(is);
            else{
                Item item = b.getLocation().getWorld().dropItemNaturally(b.getLocation().add(0, 1, 0), toStore);
                item.setVelocity(new Vector(rand.nextDouble()/4, 0.5, rand.nextDouble()/4));
            }
        }else{
            // Spit it out
            Item item = b.getLocation().getWorld().dropItemNaturally(b.getLocation().add(0, 1, 0), toStore);
            item.setVelocity(new Vector(rand.nextDouble()/2, 0.5, rand.nextDouble()/2));
        }
    }

    default boolean canFit(Inventory inv, ItemStack is){
        for(ItemStack stack : inv.getContents()){
            if(stack != null && stack.getType() != Material.AIR){
                if(stack.getAmount() < stack.getMaxStackSize()) {
                    if (stack.getType() == is.getType() && stack.getData().getData() == is.getData().getData())
                        return true;
                }
            }else {
                // Empty slot
                return true;
            }
        }
        return false;
    }
}
