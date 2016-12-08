package com.bwfcwalshy.easiermcnewinv.recipe.panes;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.bwfcwalshy.easiermcnewinv.utils.ItemStackBuilder;
import com.perceivedev.perceivecore.gui.components.Label;
import com.perceivedev.perceivecore.gui.components.panes.AnchorPane;
import com.perceivedev.perceivecore.gui.util.Dimension;

import nl.shanelab.multiblock.MultiBlockPattern;
import nl.shanelab.multiblock.PatternObject;

public class MultiBlockPane extends AnchorPane {

    private MultiBlockPattern pattern;
    private int layers;
    private int lowestZ;
    private int highestZ;

    public MultiBlockPane(MultiBlockPattern pattern) {
        super(5, 5);

        this.pattern = pattern;

        for(PatternObject patternObject : pattern.getPatternObjects()){
            if(lowestZ > patternObject.getZ()) lowestZ = patternObject.getZ();
            if(highestZ < patternObject.getZ()) highestZ = patternObject.getZ();
        }
        layers = highestZ - lowestZ;
    }

    @Override
    public void render(Inventory inventory, Player player, int x, int y) {
        render(inventory, player, x, y, 0);
        
        super.render(inventory, player, x, y);
    }

    private void render(Inventory inventory, Player player, int x, int y, int layer){
        new ArrayList<>(components).forEach(this::removeComponent);

        Label label = new Label(new ItemStackBuilder(Material.ARROW, ChatColor.GRAY + ChatColor.BOLD.toString() + "Next layer"
                , Arrays.asList(ChatColor.GRAY + "Layer " + ChatColor.RED + layer + ChatColor.GRAY + "/" + ChatColor.GOLD + layers)).build(), Dimension.ONE);
        
        addComponent(label, 4, 2);
    }

    public int getLayers(){
        return this.layers;
    }
}
