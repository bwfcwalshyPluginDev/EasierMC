package com.bwfcwalshy.easiermc.itemsandblocks.multiblock;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import nl.shanelab.multiblock.MultiBlockActivation;
import nl.shanelab.multiblock.MultiBlockActivationType;
import nl.shanelab.multiblock.MultiBlockPattern;
import nl.shanelab.multiblock.patternobjects.PatternBlock;

import java.util.Arrays;
import java.util.Objects;

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
    public AdvancedCraftingTable copy() {
        return this;
    }

    @Override
    public void onActivate(Plugin plugin, Location location, Player player, MultiBlockActivation activation) {
        if (activation.getType() == MultiBlockActivationType.CORE_PLACED) {
            player.sendMessage(ChatColor.GRAY + "You have placed down an " + ChatColor.AQUA + "Advanced Crafting Table");
        } else if (activation.getType() == MultiBlockActivationType.CORE_INTERACTED) {
            Block b = location.clone().subtract(0, 1, 0).getBlock();
            Dropper dropper = (Dropper) b.getState();

            ItemStack[] itemStacks = dropper.getInventory().getContents();

            System.out.println(itemStacks);

            // I will need to make this process better but for now, it works fine.

            outer:
            for (AdvancedRecipe recipe : handler.getAdvancedRecipes()) {
                System.out.println("Recipe: " + recipe.getResult().getItemMeta().getDisplayName());
                String shape = StringUtils.join(recipe.getShape());
                for (int i = 0; i < 9; i++) {
                    System.out.println(i + " - '" + shape.charAt(i) + "' - " + itemStacks[i] + " - " + recipe.getIngredients().get(shape.charAt(i)));
                    if (!handler.itemStackEquals(itemStacks[i], recipe.getIngredients().get(shape.charAt(i)), true)) {
                        System.out.println("ItemStack does not match.");
                        continue outer;
                    }
                }
                for (int i = 0; i < 9; i++) {
                    if (shape.charAt(i) != ' ') {
                        if (itemStacks[i].getAmount() > recipe.getIngredients().get(shape.charAt(i)).getAmount())
                            itemStacks[i].setAmount(itemStacks[i].getAmount() - recipe.getIngredients().get(shape.charAt(i)).getAmount());
                        else
                            dropper.getInventory().setItem(i, null);
                    }
                }

                ItemStack[] contents = dropper.getInventory().getContents();
                dropper.getInventory().clear();
                dropper.getInventory().addItem(recipe.getResult());
                int totalSum = Arrays.stream(dropper.getInventory().getContents()).filter(Objects::nonNull).mapToInt(ItemStack::getAmount).sum();
                for(int i = 0; i < totalSum; i++)
                    dropper.drop();
                dropper.getInventory().setContents(contents);

                return;
            }
        }
    }

    @Override
    public MultiBlockPattern getMultiBlockPattern() {
        return new MultiBlockPattern(Material.WORKBENCH, new ItemStack(Material.WORKBENCH), new PatternBlock(Material.DROPPER, 0, -1, 0));
    }
}
