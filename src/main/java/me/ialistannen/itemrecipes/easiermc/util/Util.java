package me.ialistannen.itemrecipes.easiermc.util;

import com.perceivedev.perceivecore.util.ItemFactory;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.IntStream;

/**
 * Some utility functions
 */
public class Util {

    /**
     * @param item The item to normalize
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

    /**
     * Hides text in color codes
     *
     * @param text The text to hide
     * @return The hidden text
     */
    @Nonnull
    public static String hideString(@Nonnull String text) {
        Objects.requireNonNull(text, "text can not be null!");

        StringBuilder output = new StringBuilder();

        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        String hex = Hex.encodeHexString(bytes);

        for (char c : hex.toCharArray()) {
            output.append(ChatColor.COLOR_CHAR).append(c);
        }

        return output.toString();
    }

    /**
     * Reveals the text hidden in color codes
     *
     * @param text The hidden text
     * @return The revealed text
     * @throws IllegalArgumentException if an error occurred while decoding.
     */
    @Nonnull
    public static String showString(@Nonnull String text) {
        Objects.requireNonNull(text, "text can not be null!");

        if (text.isEmpty()) {
            return text;
        }

        char[] chars = text.toCharArray();

        char[] hexChars = new char[chars.length / 2];

        IntStream.range(0, chars.length)
                .filter(value -> value % 2 != 0)
                .forEach(value -> hexChars[value / 2] = chars[value]);

        try {
            return new String(Hex.decodeHex(hexChars), StandardCharsets.UTF_8);
        } catch (DecoderException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Couldn't decode text", e);
        }
    }

    public static boolean isHiddenLine(String s) {
        return ChatColor.stripColor(s).isEmpty();
    }
}
