package com.bwfcwalshy.easiermcnewinv.recipe.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

import com.bwfcwalshy.easiermcnewinv.Handler;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermcnewinv.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermcnewinv.utils.ItemStackBuilder;
import com.perceivedev.perceivecore.gui.base.AbstractPane;
import com.perceivedev.perceivecore.gui.base.Pane;
import com.perceivedev.perceivecore.gui.components.Button;
import com.perceivedev.perceivecore.gui.components.Label;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePane;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePaneNode;
import com.perceivedev.perceivecore.gui.components.simple.DisplayColor;
import com.perceivedev.perceivecore.gui.components.simple.SimpleLabel;
import com.perceivedev.perceivecore.gui.components.simple.StandardDisplayTypes;
import com.perceivedev.perceivecore.gui.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;

import me.ialistannen.itemrecipes.easiermc.util.ItemRegistry;
import me.ialistannen.itemrecipes.easiermc.util.Util;

/**
 * A {@link TreePaneNode}, that displays the recipe for an item
 */
public class ItemRecipeNode extends TreePaneNode {

    private Pane         pane;
    private ItemStack    result;
    private Recipe       recipe;
    private Dimension    size;
    private EasierMCBase base;

    /**
     * Creates a new {@link TreePaneNode} with the given parent and no children
     *
     * @param parent The parent node
     * @param recipe The {@link Recipe}
     * @param size The size of the pane
     */
    public ItemRecipeNode(TreePaneNode parent, Recipe recipe, Dimension size, EasierMCBase base) {
        super(parent);

        this.result = recipe.getResult();
        this.recipe = recipe;
        this.size = size;
        this.base = base;
    }

    /**
     * @return The resulting item
     */
    ItemStack getResult() {
        return result.clone();
    }

    /**
     * @return The item, with a count of one and normalized damage value
     */
    public ItemStack getResultNormalized() {
        return Util.normalize(result);
    }

    /**
     * @param parent The new parent
     */
    public void setParent(TreePaneNode parent) {
        super.setParent(parent);
    }

    /**
     * @return The {@link EasierMCBase}
     */
    public EasierMCBase getBase() {
        return base;
    }

    /**
     * Returns the Pane to display for that node
     *
     * @return The pane for the node
     */
    @Override
    public Pane getPane() {
        if (pane == null) {
            pane = new RecipePane(size.getWidth(), size.getHeight());
        }
        return pane;
    }

    private class RecipePane extends AbstractPane {

        private List<List<ItemStack>> items;

        /**
         * An empty Pane
         *
         * @param width The width of this pane
         * @param height The height of this pane
         */
        private RecipePane(int width, int height) {
            super(width, height);

            if (recipe instanceof ShapedRecipe) {
                items = shapedRecipeToList((ShapedRecipe) recipe);
            } else if (recipe instanceof ShapelessRecipe) {
                items = shapelessRecipeToList((ShapelessRecipe) recipe);
            } else if (recipe instanceof AdvancedRecipe) {
                items = advancedRecipeToList((AdvancedRecipe) recipe);
            } else if (recipe instanceof FurnaceRecipe) {
                items = furnaceRecipeToList((FurnaceRecipe) recipe);
            } else {
                items = new ArrayList<>();
            }
        }

        /**
         * @param shapelessRecipe The {@link ShapelessRecipe} to convert
         *
         * @return The converted {@link ShapedRecipe}
         */
        private List<List<ItemStack>> shapelessRecipeToList(ShapelessRecipe shapelessRecipe) {
            List<List<ItemStack>> items = new ArrayList<>();

            List<ItemStack> tmp = new ArrayList<>();
            for (ItemStack itemStack : shapelessRecipe.getIngredientList()) {
                if (tmp.size() == 3) {
                    items.add(tmp);
                    tmp = new ArrayList<>();
                }
                tmp.add(itemStack);
            }
            if (!tmp.isEmpty()) {
                items.add(tmp);
            }

            return items;
        }

        /**
         * @param shapedRecipe The {@link ShapedRecipe} to convert
         *
         * @return The converted {@link ShapedRecipe}
         */
        private List<List<ItemStack>> shapedRecipeToList(ShapedRecipe shapedRecipe) {
            List<List<ItemStack>> items = new ArrayList<>();

            List<ItemStack> tmp = new ArrayList<>();
            for (String s : shapedRecipe.getShape()) {
                for (char c : s.toCharArray()) {
                    tmp.add(shapedRecipe.getIngredientMap().get(c));
                }
                items.add(tmp);
                tmp = new ArrayList<>();
            }
            items.add(tmp);

            return items;
        }

        private List<List<ItemStack>> advancedRecipeToList(AdvancedRecipe advancedRecipe) {
            List<List<ItemStack>> items = new ArrayList<>();

            List<ItemStack> tmp = new ArrayList<>();

            for (String s : advancedRecipe.getShape()) {
                for (char c : s.toCharArray()) {
                    tmp.add(advancedRecipe.getIngredients().get(c));
                }
                items.add(tmp);
                tmp = new ArrayList<>();
            }
            items.add(tmp);

            return items;
        }

        private List<List<ItemStack>> furnaceRecipeToList(FurnaceRecipe furnaceRecipe) {
            List<List<ItemStack>> lists = new ArrayList<>();

            lists.add(Arrays.asList(null, furnaceRecipe.getInput()));

            // modify the rendered recipe a bit to make it look nicer
            lists.add(Arrays.asList(null, ItemFactory.builder(Material.BLAZE_POWDER).build()));
            lists.add(Arrays.asList(null, ItemFactory.builder(Material.COAL).build()));

            return lists;
        }

        @Override
        public void render(Inventory inventory, Player player, int x, int y) {
            // clear it
            new ArrayList<>(components).forEach(this::removeComponent);

            SimpleLabel bottomLeft = new SimpleLabel(new Dimension(5, 1), StandardDisplayTypes.FLAT, DisplayColor.DARK_GRAY, "  ");
            if (getInventoryMap().addComponent(0, 5, bottomLeft)) {
                components.add(bottomLeft);
                updateComponentHierarchy(bottomLeft);
            }

            SimpleLabel bottomRight = new SimpleLabel(new Dimension(3, 1), StandardDisplayTypes.FLAT, DisplayColor.DARK_GRAY, "  ");
            if (getInventoryMap().addComponent(6, 5, bottomRight)) {
                components.add(bottomRight);
                updateComponentHierarchy(bottomRight);
            }

            SimpleLabel aboveCraftingItem = new SimpleLabel(new Dimension(1, 2), StandardDisplayTypes.FLAT, DisplayColor.DARK_GRAY, "  ");
            if (getInventoryMap().addComponent(5, 0, aboveCraftingItem)) {
                components.add(aboveCraftingItem);
                updateComponentHierarchy(aboveCraftingItem);
            }

            SimpleLabel belowCraftingItem = new SimpleLabel(new Dimension(1, 2), StandardDisplayTypes.FLAT, DisplayColor.DARK_GRAY, "  ");
            if (getInventoryMap().addComponent(5, 3, belowCraftingItem)) {
                components.add(belowCraftingItem);
                updateComponentHierarchy(belowCraftingItem);
            }

            for (int yPos = 0; yPos < items.size(); yPos++) {
                List<ItemStack> row = items.get(yPos);

                for (int xPos = 0; xPos < row.size(); xPos++) {
                    ItemStack itemStack = row.get(xPos);

                    // Skip null ones, as we need no button for them
                    if (itemStack == null || itemStack.getType() == Material.AIR) {
                        continue;
                    }

                    Optional<TreePane> owner = getOwner();
                    if (!owner.isPresent()) {
                        continue;
                    }

                    TreePane treePane = owner.get();

                    RecipeButton button = new RecipeButton(Util.normalize(itemStack), Dimension.ONE, treePane);
                    if (getInventoryMap().addComponent(xPos + 1, yPos + 1, button)) {
                        components.add(button);
                        updateComponentHierarchy(button);
                    }
                }
            }

            if (getParent() != null) {
                ItemStack parentResult;
                if (getParent() instanceof ItemRecipeNode) {
                    parentResult = ((ItemRecipeNode) getParent()).getResult();
                } else {
                    parentResult = new ItemStackBuilder(Material.BARRIER, ChatColor.RED + ChatColor.BOLD.toString() + "Back").build();
                }

                Button backButton = new Button(parentResult, Dimension.ONE);
                backButton.setAction(clickEvent -> getOwner().ifPresent(treePane -> treePane.select(getParent())));
                if (getInventoryMap().addComponent(5, 5, backButton)) {
                    components.add(backButton);
                    updateComponentHierarchy(backButton);
                }
            }

            Label resultLabel = new Label(getResult(), Dimension.ONE);
            if (getInventoryMap().addComponent(7, 2, resultLabel)) {
                components.add(resultLabel);
                updateComponentHierarchy(resultLabel);
            }

            Label craftingItem = new Label(getCraftingItem(), Dimension.ONE);
            if (getInventoryMap().addComponent(5, 2, craftingItem)) {
                components.add(craftingItem);
                updateComponentHierarchy(craftingItem);
            }

            super.render(inventory, player, x, y);
        }
    }

    private ItemStack getCraftingItem() {
        if (recipe instanceof ShapedRecipe || recipe instanceof ShapelessRecipe)
            return new ItemStack(Material.WORKBENCH);
        else if (recipe instanceof FurnaceRecipe)
            return new ItemStack(Material.FURNACE);
        else if (recipe instanceof AdvancedRecipe)
            return Handler.getInstance().getMuiltiBlock("AdvancedCraftingTable").getItem();
        else
            return null;
    }

    /**
     * A button displaying a recipe
     */
    static class RecipeButton extends Button {

        /**
         * Constructs a button
         *
         * @param itemStack The ItemStack to display
         * @param size The size of the button
         * @param pane The pane this button is in
         *
         * @throws NullPointerException if any parameter is null
         */
        RecipeButton(ItemStack itemStack, Dimension size, TreePane pane) {
            super(itemStack, size);

            setAction(clickEvent -> {
                ItemRecipeNode node = ItemRegistry.INSTANCE.getNode(itemStack);
                if(node != null && node.recipe instanceof MerchantRecipe){
                    Bukkit.getLogger().severe("The recipe type '" + node.recipe.getClass().getSimpleName() + "' is not supported!");
                    return;
                }

                // You try to select the currently selected node. This would set the parent of the node to itself
                // ==> You are trapped in the node and can't go back
                if (pane.getSelected().isPresent() && pane.getSelected().get().equals(node)) {
                    return;
                }

                node.setOwner(pane);
                pane.getSelected().ifPresent(node::setParent);

                pane.select(node);
            });
        }
    }
}
