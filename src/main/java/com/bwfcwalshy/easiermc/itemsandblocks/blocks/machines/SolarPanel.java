package com.bwfcwalshy.easiermc.itemsandblocks.blocks.machines;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.MachineBase;
import com.bwfcwalshy.easiermc.recipe.AdvancedRecipe;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Collections;

public class SolarPanel implements MachineBase {

    private int currentEU;

    @Override
    public String getName() {
        return ChatColor.WHITE + "Solar Panel";
    }

    @Override
    public String getSimpleName() {
        return "SolarPanel";
    }

    @Override
    public Category getCategory() {
        return Category.MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/54e1565fd4e738d1a9da1233405b396853e50a622686e781f70e161473ccde4", getName())
                .setLore(Collections.singletonList(ChatColor.GRAY + "This solar panel outputs " + getEuOutput() + " per tick")).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return new AdvancedRecipe(getItem(), "cgc", "gcg", "ere").setIngredient('c', handler.getItem("CoalDust").getItem()).setIngredient('g', Material.GLASS)
                .setIngredient('e', handler.getItem("ElectronicCircuit").getItem()).setIngredient('r', handler.getBlock("Generator").getItem());
    }

    @Override
    public int getEuCapacity() {
        return 1000;
    }

    @Override
    public int getEuOutput() {
        return 1;
    }

    @Override
    public int getEuInput() {
        return 0;
    }

    @Override
    public int getCurrentEu() {
        return currentEU;
    }

    @Override
    public void setCurrentEu(int currentEU) {
        this.currentEU = currentEU;
    }

    @Override
    public void tick(Location location, int tick){
        if(currentEU < getEuCapacity()) {
            if((location.getWorld().getTime() >= 1000 && location.getWorld().getTime() < 10000) && !location.getWorld().isThundering())
                currentEU++;
        }
        handleOutput(location);
    }
}
