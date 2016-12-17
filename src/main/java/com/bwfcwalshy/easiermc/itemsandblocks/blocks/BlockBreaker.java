package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import java.util.Arrays;

import com.bwfcwalshy.easiermc.itemsandblocks.bases.BlockBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import com.bwfcwalshy.easiermc.Handler;
import com.bwfcwalshy.easiermc.itemsandblocks.Category;

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
        return new ItemStackBuilder("http://textures.minecraft.net/texture/5e8057b7a7c3b14579b491f1cb3e9c809037181e3cec5e7aa37de8b95241ceb5", getName())
                .setLore(Arrays.asList(ChatColor.GRAY + "You can use this item to break any block that is in-front of it."
                        , ChatColor.DARK_PURPLE + "The items will go into a chest below or above if neither are there the items will be spat out.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("icc", "drc", "icc").setIngredient('c', Material.COBBLESTONE).setIngredient('d', Material.DIAMOND_PICKAXE)
                .setIngredient('i', Material.IRON_INGOT).setIngredient('r', Material.REDSTONE);
    }

    @Override
    public void tick(Location location, int tick) {
        if (tick != 20) return;
        Block b = location.getBlock();
        if (!(b.getState() instanceof Skull)) {
            System.out.println("Block at " + location.getX() + "," + location.getY() + "," + location.getZ() + " is not a skull! BLOCK REMOVED!");
            Handler.getInstance().getBlocks().remove(location);
            return;
        }
        Skull skull = (Skull) b.getState();
        // If powered it will stop.
        if (b.isBlockPowered())
            return;

        Block b2 = b.getRelative(skull.getRotation());
        if (isBreakableBlock(b2.getType())) {
            tryToStore(location, new ItemStack(b2.getType(), 1, b2.getData()));
            b2.setType(Material.AIR);
        }
    }

    @Override
    public BlockBreaker copy() {
        return this;
    }

    private boolean isBreakableBlock(Material mat) {
        switch (mat) {
            case WATER:
            case STATIONARY_WATER:
            case LAVA:
            case STATIONARY_LAVA:
            case AIR:
            case BEDROCK:
            case BARRIER:
            case PORTAL:
            case ENDER_PORTAL:
            case ENDER_PORTAL_FRAME:
                return false;
            default:
                return true;
        }
    }
}
