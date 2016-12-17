package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import java.util.Collections;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.BlockBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;

public class WellMiner implements BlockBase {

    @Override
    public String getSimpleName() {
        return "WellMiner";
    }

    @Override
    public String getName() {
        return ChatColor.YELLOW + "Well Miner";
    }

    @Override
    public Category getCategory() {
        return Category.BASIC_MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/a881a2be964282a4c7c63850e13d12a8f5ddf2fad7b93670e22bb729eae337fe", getName())
                .setLore(Collections.singletonList(ChatColor.GRAY + "You can use this item to dig straight down.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("ici", "cdc", "ici").setIngredient('c', Material.COBBLESTONE).setIngredient('i', Material.IRON_INGOT)
                .setIngredient('d', Material.DIAMOND_PICKAXE);
    }

    @Override
    public void tick(Location location, int tick) {
        if (tick != 20) return;
        for (int i = 1; i < location.getBlockY(); i++) {
            Block b = location.clone().subtract(0, i, 0).getBlock();
            if (b.getType() != Material.AIR && b.getType() != Material.BEDROCK) {
                for (ItemStack is : b.getDrops()) {
                    tryToStore(location, is);
                }
                b.setType(Material.AIR);
                break;
            }
            // Now to increase the arm.
            b.setType(Material.END_ROD);
            if (i > 1) b.getRelative(BlockFace.UP).setType(Material.COBBLE_WALL);
        }
    }

    @Override
    public WellMiner copy() {
        return this;
    }
}
