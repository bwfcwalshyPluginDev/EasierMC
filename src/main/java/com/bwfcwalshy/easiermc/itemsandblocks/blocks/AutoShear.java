package com.bwfcwalshy.easiermc.itemsandblocks.blocks;

import com.bwfcwalshy.easiermc.itemsandblocks.Category;
import com.bwfcwalshy.easiermc.itemsandblocks.bases.BlockBase;
import com.bwfcwalshy.easiermc.utils.ItemStackBuilder;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;

import java.util.Arrays;

public class AutoShear implements BlockBase {

    @Override
    public String getSimpleName() {
        return "AutoShear";
    }

    @Override
    public String getName() {
        return ChatColor.YELLOW + "Auto Shear";
    }

    @Override
    public Category getCategory() {
        return Category.BASIC_MACHINE;
    }

    @Override
    public ItemStack getItem() {
        return new ItemStackBuilder("http://textures.minecraft.net/texture/a881a2be964282a4c7c63850e13d12a8f5ddf2fad7b93670e22bb729eae337fe", getName())
                .setLore(Arrays.asList(ChatColor.GRAY + "You can use this item to shear sheep that are in-front of it.", ChatColor.GRAY + "Can shear from 3 blocks away!")).autoUpdate().build();
    }

    @Override
    public Recipe getRecipe() {
        return new ShapedRecipe(getItem()).shape("cic", "isi", "cic").setIngredient('c', Material.COBBLESTONE).setIngredient('i', Material.IRON_INGOT).setIngredient('s', Material.SHEARS);
    }

    @Override
    public void tick(Location location, int tick) {
        if (tick != 20) return;
        location.getWorld().getEntities().stream().filter(e -> e.getType() == EntityType.SHEEP && location.distance(e.getLocation()) <= 3).forEach(e -> {
            Sheep sheep = (Sheep) e;
            if (!sheep.isSheared()) {
                sheep.setSheared(true);
                tryToStore(location, new ItemStack(Material.WOOL, rand.nextInt(3) + 1, sheep.getColor().getData()));
            }
        });
    }

    @Override
    public AutoShear copy() {
        return this;
    }
}
