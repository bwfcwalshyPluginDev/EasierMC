package com.bwfcwalshy.easiermc.recipe.nodes;

import me.ialistannen.itemrecipes.easiermc.util.ItemRegistry;
import me.ialistannen.itemrecipes.easiermc.util.Util;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.perceivedev.perceivecore.gui.base.Pane;
import com.perceivedev.perceivecore.gui.components.Button;
import com.perceivedev.perceivecore.gui.components.panes.AnchorPane;
import com.perceivedev.perceivecore.gui.components.panes.PagedPane;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePane;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePaneNode;
import com.perceivedev.perceivecore.gui.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;

import org.bukkit.Material;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * A {@link TreePaneNode}, that displays all items in a {@link Category}
 */
class ItemCategoryNode extends TreePaneNode {

    private Category category;
    private Dimension size;

    /**
     * Creates a new {@link TreePaneNode} with the given parent and no children
     *
     * @param parent   The parent node
     * @param category The {@link Category}
     * @param size     The size of the Pane
     */
    ItemCategoryNode(TreePaneNode parent, Category category, Dimension size) {
        super(parent);
        this.category = category;
        this.size = size;
    }

    /**
     * @return The {@link Category}
     */
    Category getCategory() {
        return category;
    }

    private PagedPane generatePagedPane() {
        PagedPane pagedPane = new PagedPane(size.getWidth(), size.getHeight());

        BiConsumer<PagedPane, AnchorPane> pagePopulateFunction = pagedPane.getPagePopulateFunction();
        pagedPane.setPagePopulateFunction((pagedPane1, anchorPane) -> {
            pagePopulateFunction.accept(pagedPane1, anchorPane);

            int x = pagedPane1.getWidth() / 2 - 1;
            int y = pagedPane1.getHeight() - 1;

            Button backButton = new Button(ItemFactory.builder(Material.BARRIER).setName("&c&lBack").build(), Dimension.ONE);
            backButton.setAction(clickEvent -> getOwner().ifPresent(treePane -> treePane.select(getParent())));

            anchorPane.addComponent(backButton, x, y);
        });

        Optional<TreePane> owner = getOwner();

        if (!owner.isPresent()) {
            return pagedPane;
        }

        TreePane treePane = owner.get();

        List<ItemRecipeNode> nodes = ItemRegistry.INSTANCE.getNodes(category)
                .stream()
                .sorted(Comparator.comparing(o -> o.getResult().getType()))
                .collect(Collectors.toList());
        for (ItemRecipeNode node : nodes) {
            node.setParent(this);

            ItemRecipeNode.RecipeButton button = new ItemRecipeNode.RecipeButton(Util.normalize(node.getResult()), Dimension.ONE, treePane);
            pagedPane.addComponent(button);
        }

        return pagedPane;
    }

    /**
     * Returns the Pane to display for that node
     *
     * @return The pane for the node
     */
    @Override
    public Pane getPane() {
        return generatePagedPane();
    }

    @Override
    public ItemCategoryNode clone() {
        ItemCategoryNode clone = (ItemCategoryNode) super.clone();
        clone.size = size;
        clone.category = category;

        return clone;
    }
}
