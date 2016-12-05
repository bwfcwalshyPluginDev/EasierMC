package com.bwfcwalshy.easiermcnewinv.itemsandblocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AdvancedRecipe {

    private ItemStack output;
    private String[] shape;
    private Map<Character, ItemStack> ingredients = new HashMap<>();

    public AdvancedRecipe(ItemStack output, String... shape){
        this.output = output;
        this.shape = shape;
    }

    public AdvancedRecipe setIngredient(char c, Material mat){
        this.ingredients.put(c, new ItemStack(mat));
        return this;
    }

    public AdvancedRecipe setIngredient(char c, ItemStack is){
        this.ingredients.put(c, is);
        return this;
    }

    public String[] getShape(){
        return this.shape;
    }

    public Map<Character, ItemStack> getIngredients(){
        return this.ingredients;
    }

    public ItemStack getResult() {
        return output;
    }
}
