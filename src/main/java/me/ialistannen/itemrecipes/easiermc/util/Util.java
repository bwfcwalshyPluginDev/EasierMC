package me.ialistannen.itemrecipes.easiermc.util;

import org.bukkit.inventory.ItemStack;

import com.perceivedev.perceivecore.util.ItemFactory;

/**
 * Some utility functions
 */
public class Util {

    /**
     * @param item The item to normalize
     *
     * @return The normalized item
     */
    public static ItemStack normalize(ItemStack item) {
        ItemFactory itemFactory = ItemFactory.builder(item).setAmount(1);
        return fixStrangeDurability(itemFactory.build());
    }

    /**
     * Fixed the durability (Short.MAX_VALUE is nothing minecraft can render)
     * <p>
     * Will return a clone of the inout item, if it has a normal durability.
     *
     * @param item The {@link ItemStack} to fix
     *
     * @return A clone of the original item with a correct durability.
     */
    public static ItemStack fixStrangeDurability(ItemStack item) {
        if (item.getDurability() != Short.MAX_VALUE) {
            return item.clone();
        }
        ItemFactory itemFactory = ItemFactory.builder(item);
        itemFactory.setDurability((short) 0);
        return itemFactory.build();
    }
}
