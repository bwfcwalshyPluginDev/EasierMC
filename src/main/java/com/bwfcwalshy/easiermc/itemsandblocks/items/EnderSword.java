package com.bwfcwalshy.easiermc.itemsandblocks.items;

import com.bwfcwalshy.easiermc.Handler;
import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.multiblock.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Arrays;

public class EnderSword implements ItemBase {

    private Handler handler = Handler.getInstance();

    @Override
    public String getName() {
        return ChatColor.DARK_PURPLE + "Ender " + ChatColor.GOLD + "Sword";
    }

    @Override
    public String getSimpleName() {
        return "EnderSword";
    }

    @Override
    public Category getCategory() {
        return Category.WEAPONS;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder(Material.DIAMOND_SWORD, getName(), Arrays.asList(ChatColor.DARK_PURPLE + "This is the most powerful sword known to man."
                , ChatColor.DARK_PURPLE + "Powered by a dragon egg, this sword will guarentee you can slay any dragon in the land.")).build();
    }

    @Override
    public Recipe getRecipe() {
        return null;
    }

    @Override
    public AdvancedRecipe getAdvancedRecipe(){
        return new AdvancedRecipe(getItem(), "sds", "sds", "rer").setIngredient('s', handler.getItem("MasterStar").getItem()).setIngredient('d', Material.DIAMOND_BLOCK)
                .setIngredient('r', handler.getItem("ReinforcedStick").getItem()).setIngredient('e', Material.DRAGON_EGG);
    }
}
