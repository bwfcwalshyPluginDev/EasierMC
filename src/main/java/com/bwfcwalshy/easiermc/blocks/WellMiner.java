package com.bwfcwalshy.easiermc.blocks;

import com.bwfcwalshy.easiermc.Category;
import com.bwfcwalshy.easiermc.CustomHead;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
        ItemStack is = CustomHead.getSkull("http://textures.minecraft.net/texture/a881a2be964282a4c7c63850e13d12a8f5ddf2fad7b93670e22bb729eae337fe");
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(getName());
        im.setLore(Arrays.asList(ChatColor.GRAY + "You can use this item to dig straight down.", ChatColor.GRAY + "Can shear from 3 blocks away!"));
        is.setItemMeta(im);
        return is;
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("ici", "cdc", "ici").setIngredient('c', Material.COBBLESTONE).setIngredient('i', Material.IRON_INGOT)
                .setIngredient('d', Material.DIAMOND_PICKAXE);
    }

    @Override
    public void tick(Location location) {
        for(int i = 1; i < location.getBlockY(); i++){
            Block b = location.clone().subtract(0, i, 0).getBlock();
            if(b.getType() != Material.AIR && b.getType() != Material.BEDROCK){
                tryToStore(location, new ItemStack(b.getType(), 1, b.getData()));
                b.setType(Material.AIR);
                break;
            }
            // Now to increase the arm.
//            b.setType(Material.END_ROD);
//            if(i > 1) b.getRelative(BlockFace.UP).setType(Material.COBBLE_WALL);
        }
    }
}
