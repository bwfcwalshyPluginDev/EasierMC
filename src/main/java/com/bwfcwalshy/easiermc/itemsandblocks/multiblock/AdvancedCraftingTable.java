package com.bwfcwalshy.easiermc.itemsandblocks.multiblock;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import nl.shanelab.multiblock.MultiBlockActivation;
import nl.shanelab.multiblock.MultiBlockActivationType;
import nl.shanelab.multiblock.MultiBlockPattern;
import nl.shanelab.multiblock.patternobjects.PatternBlock;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class AdvancedCraftingTable implements MultiBlock {

    @Override
    public String getSimpleName() {
        return "AdvancedCraftingTable";
    }

    @Override
    public Category getCategory() {
        return Category.BLOCKS;
    }

    @Override
    public void onActivate(Plugin plugin, Location location, Player player, MultiBlockActivation activation) {
        if(activation.getType() == MultiBlockActivationType.CORE_PLACED){
            player.sendMessage(ChatColor.GRAY + "You have placed down an " + ChatColor.AQUA + "Advanced Crafting Table");
        }else if(activation.getType() == MultiBlockActivationType.CORE_INTERACTED){
            player.sendMessage("a");
        }
    }

    @Override
    public MultiBlockPattern getMultiBlockPattern() {
        return new MultiBlockPattern(Material.WORKBENCH, new PatternBlock(Material.DISPENSER, 0, -1, 0));
    }
}
