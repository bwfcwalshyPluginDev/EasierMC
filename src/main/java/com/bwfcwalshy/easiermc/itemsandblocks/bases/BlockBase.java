package com.bwfcwalshy.easiermc.itemsandblocks.bases;

import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import java.util.Random;
import javax.annotation.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public interface BlockBase extends EasierMCBase {

    Random rand = new Random();

    @Override
    default BlockBase copy() {
        return this;
    }

    default void tick(Location location, int tick) {
        return;
    }

    default void onInteract(PlayerInteractEvent e) {
        return;
    }

    default void tryToStore(Location location, ItemStack toStore) {
        Random rand = new Random();
        Block b = location.getBlock();
        // Now to check if there's a chest
        if (b.getRelative(BlockFace.UP).getType() == Material.CHEST || b.getRelative(BlockFace.UP).getType() == Material.TRAPPED_CHEST ||
                b.getRelative(BlockFace.DOWN).getType() == Material.CHEST || b.getRelative(BlockFace.DOWN).getType() == Material.TRAPPED_CHEST) {
            Block chestBlock = (b.getRelative(BlockFace.UP).getType() == Material.CHEST ? b.getRelative(BlockFace.UP) : b.getRelative(BlockFace.DOWN));
            Chest chest = (Chest) chestBlock.getState();
            // Check if inv is full
            ItemStack is = toStore;
            if (canFit(chest.getInventory(), is))
                chest.getInventory().addItem(is);
            else {
                Item item = b.getLocation().getWorld().dropItemNaturally(b.getLocation().add(0, 1, 0), toStore);
                item.setVelocity(new Vector(rand.nextDouble() / 4, 0.5, rand.nextDouble() / 4));
            }
        } else {
            // Spit it out
            Item item = b.getLocation().getWorld().dropItemNaturally(b.getLocation().add(0, 1, 0), toStore);
            item.setVelocity(new Vector(rand.nextDouble() / 2, 0.5, rand.nextDouble() / 2));
        }
    }

    default boolean canFit(Inventory inv, ItemStack is) {
        for (ItemStack stack : inv.getContents()) {
            if (stack != null && stack.getType() != Material.AIR) {
                if (stack.getAmount() < stack.getMaxStackSize()) {
                    if (stack.getType() == is.getType() && stack.getData().getData() == is.getData().getData())
                        return true;
                }
            } else {
                // Empty slot
                return true;
            }
        }
        return false;
    }

    /**
     * Clones the inventory
     * <p>
     * Returns null if inventory is null
     *
     * @param inventory The inventory to clone
     * @return A cloned inventory
     */
    @Nullable
    default Inventory cloneInventory(@Nullable Inventory inventory) {
        if (inventory == null) {
            return null;
        }

        Inventory clone = Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), inventory.getTitle());

        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack binItem = inventory.getItem(i);
            if (binItem != null) {
                clone.setItem(i, binItem.clone());
            } else {
                clone.setItem(i, null);
            }
        }

        return clone;
    }

    default void saveData(FileConfiguration data, String path) {
        return;
    }

    default void loadData(FileConfiguration data, String path) {
        return;
    }
}
