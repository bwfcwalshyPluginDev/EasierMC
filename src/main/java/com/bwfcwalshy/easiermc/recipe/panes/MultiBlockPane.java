package com.bwfcwalshy.easiermc.recipe.panes;

import nl.shanelab.multiblock.MultiBlockPattern;
import nl.shanelab.multiblock.PatternObject;
import nl.shanelab.multiblock.patternobjects.PatternBlock;

import com.bwfcwalshy.easiermc.recipe.nodes.ItemRecipeNode;
import com.perceivedev.perceivecore.gui.base.Component;
import com.perceivedev.perceivecore.gui.components.Button;
import com.perceivedev.perceivecore.gui.components.Label;
import com.perceivedev.perceivecore.gui.components.panes.AnchorPane;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePane;
import com.perceivedev.perceivecore.gui.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class MultiBlockPane extends AnchorPane {

    private MultiBlockPattern pattern;
    private int layers;
    private int lowestY;
    private int highestY;
    private TreePane owner;

    private int currentLayer = 0;

    public MultiBlockPane(MultiBlockPattern pattern, TreePane owner) {
        super(5, 5);

        this.owner = owner;
        this.pattern = pattern;

        for (PatternObject patternObject : pattern.getPatternObjects()) {
            if (lowestY > patternObject.getY()) {
                lowestY = patternObject.getY();
            }
            if (highestY < patternObject.getY()) {
                highestY = patternObject.getY();
            }
        }

        // core is at 0:0. Respect it.
        if (lowestY > 0) {
            lowestY = 0;
        }
        if (highestY < 0) {
            highestY = 0;
        }

        layers = (highestY - lowestY > 0 ? highestY - lowestY : 1);
        layers++;
    }

    @Override
    public void render(Inventory inventory, Player player, int x, int y) {
        render(currentLayer);

        super.render(inventory, player, x, y);
    }

    private void render(int layer) {
        // clear components as we will need to replace all but the compass rose. This is easier.
        new ArrayList<>(components).forEach(this::removeComponent);

        {
            Label compassRose = new Label(
                    ItemFactory.builder(Material.COMPASS)
                            .setName("&3&lCompass Rose")
                            .addLore("&6Positive &3X&6: &a&l\u27a1")
                            .addLore("&6Positive &3Z&6: &a&l\u2b07\u2b07")
                            .build()
                    , Dimension.ONE);
            addComponent(compassRose, 2, 0);
        }

        addNavigationButtons(layer);

        for (PatternObject object : pattern.getPatternObjects()) {
            if (layer == 1) {
                Component label;
                if (owner == null) {
                    label = new Label(pattern.getCoreItemStack(), Dimension.ONE);
                } else {
                    label = new ItemRecipeNode.RecipeButton(pattern.getCoreItemStack(), Dimension.ONE, owner);
                }
                addComponent(label, 2, 2);
            }
            if (layer != object.getY() + 1) {
                continue;
            }

            if (object instanceof PatternBlock) {
                PatternBlock patternBlock = (PatternBlock) object;
                Component button;
                if (owner == null) {
                    button = new Label(patternBlock.getItemStack(), Dimension.ONE);
                } else {
                    button = new ItemRecipeNode.RecipeButton(patternBlock.getItemStack(), Dimension.ONE, owner);
                }

                int x = object.getX() + 2;
                int y = object.getZ() + 2;

                addComponent(button, x, y);
            } else {
                // Can't process
                System.out.println("PANIC! Can't process: " + object);
            }
        }
    }

    private void addNavigationButtons(int layer) {
        // do not display it for the last page
        if (layer < layers - 1) {
            Button nextLayerBtn = new Button(
                    ItemFactory.builder(Material.ARROW)
                            .setName("&7&lNext layer")
                            .setLore("&7Layer &c" + (currentLayer + 1) + "&7/&6" + layers)
                            .build()
                    , Dimension.ONE);

            nextLayerBtn.setAction(clickEvent -> {
                currentLayer = Math.min(currentLayer + 1, layers - 1);
                requestReRender();
            });
            addComponent(nextLayerBtn, 4, 2);
        }

        if (layer > 0) {
            Button prevLayerButton = new Button(
                    ItemFactory.builder(Material.ARROW)
                            .setName("&7&lPrevious layer")
                            .setLore("&7Layer &c" + currentLayer + "&7/&6" + layers)
                            .build()
                    , Dimension.ONE);

            prevLayerButton.setAction(clickEvent -> {
                currentLayer = Math.max(currentLayer - 1, 0);
                requestReRender();
            });
            addComponent(prevLayerButton, 0, 2);
        }
    }

    @Override
    public MultiBlockPane deepClone() {
        System.out.println("Cloned!");
        MultiBlockPane clone = (MultiBlockPane) super.deepClone();
        clone.pattern = pattern;
        return clone;
    }

    public int getLayersAmount() {
        return this.layers;
    }
}
