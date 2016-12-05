package me.ialistannen.itemrecipes.easiermc.util;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.scheduler.BukkitRunnable;

import com.bwfcwalshy.easiermcnewinv.EasierMC;
import com.bwfcwalshy.easiermcnewinv.Handler;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.EasierMCBase;
import com.perceivedev.perceivecore.gui.util.Dimension;

/**
 * A registry for Recipes
 */
public enum RecipeRegistry {
    INSTANCE;

    private final Map<ItemStack, Recipe> recipeMap = new ConcurrentHashMap<>();

    /**
     * @param recipe The {@link Recipe} to add
     */
    public void addRecipe(Recipe recipe) {
        recipeMap.put(Util.normalize(recipe.getResult()), recipe);
    }

    /**
     * @param result The result of the recipe
     *
     * @return The Recipe or null if none
     */
    public Recipe getRecipe(ItemStack result) {
        return recipeMap.get(Util.normalize(result));
    }

    /**
     * @return All recipes. Unmodifiable
     */
    public Collection<EasierMCBase> getAllRecipes() {
        return Handler.getInstance().getEntireRegistery().stream().filter(base -> base.getRecipe() != null).collect(Collectors.toList());
    }

    /**
     * Loads the recipes
     */
    public void loadRecipes() {
        init();
    }

    private void init() {
        new BukkitRunnable() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();

                for (EasierMCBase easierMCBase : Handler.getInstance().getEntireRegistery()) {
                    Recipe recipe = easierMCBase.getRecipe();
                    if(recipe == null) {
                        continue;
                    }
                    addRecipe(recipe);
                }
                long duration = System.currentTimeMillis() - start;
                System.out.println("RecipeRegistry.run() Took: " + duration + " (" + TimeUnit.MILLISECONDS.toSeconds(duration) + ")");

                ItemRegistry.INSTANCE.build(new Dimension(9, 6));
            }
        }.runTaskAsynchronously(EasierMC.getPlugin(EasierMC.class));
    }

}
