package com.bwfcwalshy.easiermcnewinv.recipe.panes;

import java.util.ArrayList;
import java.util.Arrays;

import com.perceivedev.perceivecore.gui.components.Button;
import nl.shanelab.multiblock.patternobjects.PatternBlock;
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
        layers = (highestZ - lowestZ > 0 ? highestZ - lowestZ : 1);
    }

    @Override
    public void render(Inventory inventory, Player player, int x, int y) {
        render(inventory, player, x, y, 0);
        
        super.render(inventory, player, x, y);
    }

    private void render(Inventory inventory, Player player, int x, int y, int layer){
        new ArrayList<>(components).forEach(this::removeComponent);

        Button nextLayerBtn = new Button(new ItemStackBuilder(Material.ARROW, ChatColor.GRAY + ChatColor.BOLD.toString() + "Next layer"
                , Arrays.asList(ChatColor.GRAY + "Layer " + ChatColor.RED + layer + ChatColor.GRAY + "/" + ChatColor.GOLD + layers)).build(), Dimension.ONE);
//        int nextLayer = layer += 1;
        nextLayerBtn.setAction(clickEvent -> render(inventory, player, x, y, 1));
        addComponent(nextLayerBtn, 4, 2);

        System.out.println(lowestZ);
        System.out.println(layer);
        int z = lowestZ + layer;
        System.out.println(z);
        int xx = 1;
        int yy = 1;
        for(PatternObject object : pattern.getPatternObjects()){
            System.out.println(object);
            System.out.println(object.getZ());
            if(object.getZ() == z){
                System.out.println("Correct z");
                xx++;
                if(object instanceof PatternBlock) {
                    PatternBlock patternBlock = (PatternBlock) object;
                    Label label = new Label(patternBlock.getItemStack(), Dimension.ONE);
                    addComponent(label, xx, yy);
                }else{
                    // Can't process
                }
                if(xx == 4){
                    xx = 1;
                    yy++;
                }
            }
        }
    }

    public int getLayers(){
        return this.layers;
    }
}
