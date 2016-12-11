package me.ialistannen.itemrecipes.easiermc.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.MultiBlock;
import org.bukkit.inventory.ItemStack;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.EasierMCBase;
import com.perceivedev.perceivecore.gui.util.Dimension;

import com.bwfcwalshy.easiermc.recipe.nodes.ItemRecipeNode;

/**
 * A Registry, that maps {@link ItemStack}s to {@link ItemRecipeNode}s
 */
public enum ItemRegistry {
    INSTANCE;

    private Map<ItemStack, ItemRecipeNode> nodeMap = new HashMap<>();

    /**
     * @param recipeNode The {@link ItemRecipeNode} to add
     */
    public void addRecipeNode(ItemRecipeNode recipeNode) {
        if(nodeMap.containsKey(recipeNode.getResultNormalized())) {
            return;
        }
        nodeMap.put(recipeNode.getResultNormalized(), recipeNode);
    }

    /**
     * @param itemStack The {@link ItemStack} you want the node for
     *
     * @return The {@link ItemRecipeNode}, if any
     */
    public ItemRecipeNode getNode(ItemStack itemStack) {
        return nodeMap.get(itemStack);
    }

    /**
     * Returns all nodes for the given Category
     *
     * @param itemCategory The {@link Category} of the nodes
     *
     * @return All nodes with that category
     */
    public List<ItemRecipeNode> getNodes(Category itemCategory) {
        List<ItemRecipeNode> nodes = new LinkedList<>();
        for (Entry<ItemStack, ItemRecipeNode> entry : nodeMap.entrySet()) {
            Category nodeCategory = entry.getValue().getBase().getCategory();
            if (nodeCategory == itemCategory) {
                nodes.add(entry.getValue());
            }
        }

        return nodes;
    }

    /**
     * Builds the Item registry
     */
    public void build(Dimension dimension) {
        long start = System.currentTimeMillis();
        for (EasierMCBase mcBase : RecipeRegistry.INSTANCE.getAllRecipes()) {
            ItemRecipeNode itemRecipeNode;
            if(mcBase instanceof MultiBlock)
                itemRecipeNode = new ItemRecipeNode(null, ((MultiBlock) mcBase).getMultiBlockPattern(), dimension, mcBase);
            else
                itemRecipeNode = new ItemRecipeNode(null, mcBase.getRecipe(), dimension, mcBase);
            addRecipeNode(itemRecipeNode);
        }
        long duration = System.currentTimeMillis() - start;
        System.out.println("ItemRegistry.build() Took: " + duration + " (" + TimeUnit.MILLISECONDS.toSeconds(duration) + ")");
    }
}
