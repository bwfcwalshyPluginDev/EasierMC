package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.ItemBase;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TapeMeasure implements ItemBase {

    private Map<UUID, Block> points;

    public TapeMeasure() {
        points = new HashMap<>();
    }

    @Override
    public String getName() {
        return ChatColor.YELLOW + "Tape Measure";
    }

    @Override
    public String getSimpleName() {
        return "TapeMeasure";
    }

    @Override
    public Category getCategory() {
        return Category.MISC;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.COMPASS, getName()).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "iii", "iyi", "iii").setIngredient('i', Material.IRON_INGOT).setIngredient('y', new ItemStackBuilder(Material.INK_SACK).setData(11).build());
    }

    @Override
    public TapeMeasure copy() {
        // there is points, but that is a shared instance
        return this;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            Block b = e.getClickedBlock();
            if (points.containsKey(player.getUniqueId())) {
                Block b1 = points.get(player.getUniqueId());
                int x = (b1.getX() > b.getX() ? b1.getX() - b.getX() : b.getX() - b1.getX());
                int y = (b1.getY() > b.getY() ? b1.getY() - b.getY() : b.getY() - b1.getY());
                int z = (b1.getZ() > b.getZ() ? b1.getZ() - b.getZ() : b.getZ() - b1.getZ());
                player.sendMessage(ChatColor.AQUA + "There are " + ChatColor.YELLOW + x + " meters X, " + y + " meters Y and " + z + " meters Z" + " (Total: " + (x + y + z) + ")");
                points.remove(player.getUniqueId());
            } else {
                player.sendMessage(ChatColor.AQUA + "Set first point at " + ChatColor.YELLOW + b.getX() + ", " + b.getY() + ", " + b.getZ());
                points.put(player.getUniqueId(), e.getClickedBlock());
            }
        }
    }
}
