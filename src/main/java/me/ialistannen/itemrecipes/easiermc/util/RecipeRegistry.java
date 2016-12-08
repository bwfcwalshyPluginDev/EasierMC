package me.ialistannen.itemrecipes.easiermc.util;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.scheduler.BukkitRunnable;

import com.bwfcwalshy.easiermcnewinv.EasierMC;
import com.bwfcwalshy.easiermcnewinv.Handler;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.Category;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.EasierMCBase;
import com.bwfcwalshy.easiermcnewinv.itemsandblocks.multiblock.MultiBlock;
import com.perceivedev.perceivecore.gui.util.Dimension;

/**
 * A registry for Recipes
 */
public enum RecipeRegistry {
    INSTANCE;

    private final Map<ItemStack, Recipe> recipeMap       = new ConcurrentHashMap<>();
    private final Map<ItemStack, Recipe> bukkitRecipeMap = new ConcurrentHashMap<>();
    private final Collection<MultiBlock> multiBlocks     = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * @param recipe The {@link Recipe} to add
     */
    public void addRecipe(Recipe recipe) {
        recipeMap.put(Util.normalize(recipe.getResult()), recipe);
    }

    /**
     * @param recipe The {@link Recipe} to add
     */
    public void addBukkitRecipe(Recipe recipe) {
        bukkitRecipeMap.put(Util.normalize(recipe.getResult()), recipe);
    }

    /**
     * @param multiBlock The {@link MultiBlock} to add
     */
    public void addMultiBlock(MultiBlock multiBlock) {
        multiBlocks.add(multiBlock);
    }

    /**
     * @return All recipes. Unmodifiable
     */
    public Collection<EasierMCBase> getAllRecipes() {
        LinkedList<EasierMCBase> mcBases = Stream
                  .concat(Handler.getInstance().getEntireRegistery().stream(), bukkitRecipeMap.values().stream().map(EasierMcNormalItemBridge::new))
                  .filter(base -> base.getRecipe() != null)
                  .collect(Collectors.toCollection(LinkedList::new));

        mcBases.addAll(multiBlocks);

        return mcBases;
    }

    private static class EasierMcNormalItemBridge implements EasierMCBase {

        private Recipe recipe;

        private EasierMcNormalItemBridge(Recipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public Recipe getRecipe() {
            return recipe;
        }

        @Override
        public String getName() {
            return "normal item";
        }

        @Override
        public String getSimpleName() {
            return "normal item";
        }

        @Override
        public Category getCategory() {
            return null;
        }

        @Override
        public ItemStack getItem() {
            return getRecipe().getResult();
        }
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
                    if (easierMCBase instanceof MultiBlock) {
                        addMultiBlock((MultiBlock) easierMCBase);
                    } else {
                        Recipe recipe = easierMCBase.getRecipe();
                        if (recipe != null) {
                            addRecipe(recipe);
                        }
                    }
                }

                // Load bukkit recipes

                Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
                while (recipeIterator.hasNext()) {
                    addBukkitRecipe(recipeIterator.next());
                }

                long duration = System.currentTimeMillis() - start;
                System.out.println("RecipeRegistry.run() Took: " + duration + " (" + TimeUnit.MILLISECONDS.toSeconds(duration) + ")");

                ItemRegistry.INSTANCE.build(new Dimension(9, 6));
            }
        }.runTaskAsynchronously(EasierMC.getPlugin(EasierMC.class));
    }
}