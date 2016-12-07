package com.bwfcwalshy.easiermcnewinv.itemsandblocks.multiblock;

import com.bwfcwalshy.easiermcnewinv.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.Category;
import com.bwfcwalshy.easiermcnewinv.utils.ItemStackBuilder;
import nl.shanelab.multiblock.MultiBlockActivation;
import nl.shanelab.multiblock.MultiBlockActivationType;
import nl.shanelab.multiblock.MultiBlockPattern;
import nl.shanelab.multiblock.patternobjects.PatternBlock;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.WORKBENCH, ChatColor.YELLOW + "Advanced Crafting Table").build();
    }

    @Override
    public void onActivate(Plugin plugin, Location location, Player player, MultiBlockActivation activation) {
        if (activation.getType() == MultiBlockActivationType.CORE_PLACED) {
            player.sendMessage(ChatColor.GRAY + "You have placed down an " + ChatColor.AQUA + "Advanced Crafting Table");
        } else if (activation.getType() == MultiBlockActivationType.CORE_INTERACTED) {
            Block b = location.clone().subtract(0, 1, 0).getBlock();
            Dropper dropper = (Dropper) b.getState();

            ItemStack[] itemStacks = dropper.getInventory().getContents();

            // I will need to make this process better but for now, it works fine.

            outer:
            for (AdvancedRecipe recipe : handler.getAdvancedRecipes()) {
                String shape = StringUtils.join(recipe.getShape());
                for (int i = 0; i < 9; i++) {
                    if (shape.charAt(i) != ' ' && !handler.itemStackEquals(recipe.getIngredients().get(shape.charAt(i)), itemStacks[i], true)) {
                        continue outer;
                    }
                }
                for(int i = 0; i < 9; i++){
                    if(shape.charAt(i) != ' '){
                        if(itemStacks[i].getAmount() > recipe.getIngredients().get(shape.charAt(i)).getAmount())
                            itemStacks[i].setAmount(itemStacks[i].getAmount() - recipe.getIngredients().get(shape.charAt(i)).getAmount());
                        else
                            dropper.getInventory().setItem(i, null);
                    }
                }

                ItemStack[] contents = dropper.getInventory().getContents();
                dropper.getInventory().clear();
                dropper.getInventory().addItem(recipe.getResult());
                dropper.drop();
                dropper.getInventory().setContents(contents);

                return;
            }
        }
    }

    @Override
    public MultiBlockPattern getMultiBlockPattern() {
        return new MultiBlockPattern(Material.WORKBENCH, new PatternBlock(Material.DROPPER, 0, -1, 0));
    }
}
