package com.bwfcwalshy.easiermc.blocks;

import com.bwfcwalshy.easiermc.Category;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import com.bwfcwalshy.easiermc.CustomHead;
import com.bwfcwalshy.easiermc.Handler;

import java.util.Arrays;

public class BlockBreaker implements BlockBase {

    @Override
    public String getSimpleName() {
        return "BlockBreaker";
    }

    @Override
    public String getName() {
        return ChatColor.GRAY + "Block Breaker";
    }

    @Override
    public Category getCategory() {
        return Category.BASIC_MACHINE;
    }

    @Override
    public ItemStack getItem() {
        ItemStack is = CustomHead.getSkull("http://textures.minecraft.net/texture/5e8057b7a7c3b14579b491f1cb3e9c809037181e3cec5e7aa37de8b95241ceb5");
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(getName());
        im.setLore(Arrays.asList(ChatColor.GRAY + "You can use this item to break any block that is in-front of it."
                , ChatColor.DARK_PURPLE + "The items will go into a chest below or above", ChatColor.DARK_PURPLE + "if neither are there the items will be spat out."));
        is.setItemMeta(im);
        return is;
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("icc", "drc", "icc").setIngredient('c', Material.COBBLESTONE).setIngredient('d', Material.DIAMOND_PICKAXE)
                .setIngredient('i', Material.IRON_INGOT).setIngredient('r', Material.REDSTONE);
    }

    @Override
    public void tick(Location location) {
        Block b = location.getBlock();
        if(!(b.getState() instanceof Skull)){
            System.out.println("Block at " + location.getX() + "," + location.getY() + "," + location.getZ() + " is not a skull! BLOCK REMOVED!");
            Handler.getInstance().getBlocks().remove(location);
            return;
        }
        Skull skull = (Skull) b.getState();
        // If powered it will stop.
        if(b.isBlockPowered())
            return;

        Block b2 = b.getRelative(skull.getRotation());
        if(b2.getType() != Material.AIR && b2.getType() != Material.BEDROCK){
            tryToStore(location, new ItemStack(b2.getType(), 1, b2.getData()));
            b2.setType(Material.AIR);
        }
    }
}
