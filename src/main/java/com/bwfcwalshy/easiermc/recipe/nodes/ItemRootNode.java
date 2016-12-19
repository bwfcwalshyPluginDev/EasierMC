package com.bwfcwalshy.easiermc.recipe.nodes;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.perceivedev.perceivecore.gui.base.Pane;
import com.perceivedev.perceivecore.gui.components.Button;
import com.perceivedev.perceivecore.gui.components.panes.PagedPane;
import com.perceivedev.perceivecore.gui.components.panes.tree.TreePaneNode;
import com.perceivedev.perceivecore.gui.util.Dimension;
import com.perceivedev.perceivecore.util.ItemFactory;
import com.perceivedev.perceivecore.util.TextUtils;

import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The tree pane root node
 */
public class ItemRootNode extends TreePaneNode {

    private PagedPane pagedPane;
    private Dimension size;

    /**
     * Creates a new {@link TreePaneNode} with the given parent and no children
     *
     * @param parent The parent node
     */
    public ItemRootNode(TreePaneNode parent, Dimension size, Category... categories) {
        super(parent);

        this.size = size;

        for (Category category : categories) {
            ItemCategoryNode itemCategoryNode = new ItemCategoryNode(this, category, size);
            addChild(itemCategoryNode);
        }
    }

    private PagedPane createPane() {
        PagedPane pagedPane = new PagedPane(size.getWidth(), size.getHeight());

        for (ItemCategoryNode node : getItemCategoryChildren()) {
            ItemStack icon = ItemFactory.builder(node.getCategory().getDisplayItem())
                    .setName("&c&l" + TextUtils.enumFormat(node.getCategory().name(), true))
                    .build();
            Button button = new Button(icon, Dimension.ONE);
            button.setAction(clickEvent -> getOwner().ifPresent(treePane -> treePane.select(node)));

            pagedPane.addComponent(button);

            node.setParent(this);
        }

        return pagedPane;
    }

    /**
     * @return All children that are {@link ItemCategoryNode}s in a list
     */
    private List<ItemCategoryNode> getItemCategoryChildren() {
        return getChildren().stream()
                .filter(treePaneNode -> treePaneNode instanceof ItemCategoryNode)
                .map(treePaneNode -> (ItemCategoryNode) treePaneNode)
                .collect(Collectors.toList());

    }

    /**
     * Returns the Pane to display for that node
     *
     * @return The pane for the node
     */
    @Override
    public Pane getPane() {
        if (pagedPane == null) {
            pagedPane = createPane();
        }
        return pagedPane;
    }

    @Override
    public ItemRootNode clone() {
        ItemRootNode clone = (ItemRootNode) super.clone();
        clone.size = size;
        clone.pagedPane = pagedPane.deepClone();

        return clone;
    }
}
