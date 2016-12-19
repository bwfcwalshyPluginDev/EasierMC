package com.bwfcwalshy.easiermc.utils;

import java.util.*;
import org.bukkit.ChatColor;

public class BetterLore {

    /**
     * Trims the lore to a given length
     *
     * @param lore      The lore to trim
     * @param maxLength The max length of the lore
     * @return The trimmed lore
     */
    public static List<String> trimLoreToLength(List<String> lore, int maxLength) {
        Objects.requireNonNull(lore, "lore can not be null!");
        if (maxLength < 1) {
            throw new IllegalArgumentException("MaxLength must be > 0");
        }

        List<String> newLore = new LinkedList<>();
        for (String line : lore) {
            Queue<String> parts = new LinkedList<>(Arrays.asList(line.split(" ")));
            String result = "";
            for (String tmp : parts) {
                if (lengthUnformatted(result) + lengthUnformatted(tmp + " ") < maxLength) {
                    result += tmp + " ";
                } else {
                    newLore.add(result);
                    result = ChatColor.getLastColors(result) + tmp + " ";
                }
            }
            newLore.add(result);
        }

        return newLore;
    }

    private static int lengthUnformatted(String formatted) {
        return ChatColor.stripColor(formatted).length();
    }
}
