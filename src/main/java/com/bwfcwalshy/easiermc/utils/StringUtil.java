package com.bwfcwalshy.easiermc.utils;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

public class StringUtil {

    /**
     * Pads a String to a given length
     *
     * @param string      The string to pad
     * @param paddingChar The padding character
     * @param length      The desired length of the string
     * @return The padded String, or the original string if it was {@code >= length}
     */
    public static String padToLength(String string, char paddingChar, int length) {
        String result = string.trim();

        if (result.length() >= length) {
            return result;
        }
        int difference = length - result.length();

        result = StringUtils.repeat(Character.toString(paddingChar), difference / 2) + result + StringUtils.repeat(Character.toString(paddingChar), difference / 2);
        if (difference % 2 != 0) {
            result += " ";
        }

        return result;
    }

    public static ChatColor getColorFromEnergy(int currentEU, int maxEU) {
        int quater = maxEU / 5;
        if (currentEU <= quater) {
            return ChatColor.RED;
        } else if (currentEU <= (quater * 2)) {
            return ChatColor.GOLD;
        } else if (currentEU <= (quater * 3)) {
            return ChatColor.YELLOW;
        } else if (currentEU <= (quater * 4)) {
            return ChatColor.GREEN;
        } else {
            return ChatColor.DARK_GREEN;
        }
    }

    public static short getGlassDataFromChatColor(ChatColor chatColor){
        switch(chatColor){
            case AQUA:
                return 3;
            case BLACK:
                return 15;
            case BLUE:
                return 11;
            case DARK_AQUA:
                return 9;
            case DARK_BLUE:
                return 11;
            case DARK_GRAY:
                return 7;
            case GRAY:
                return 8;
            case DARK_GREEN:
                return 13;
            case GREEN:
                return 5;
            case DARK_RED:
                return 14;
            case RED:
                return 14;
            case DARK_PURPLE:
                return 10;
            case LIGHT_PURPLE:
                return 6;
            case GOLD:
                return 1;
            case YELLOW:
                return 4;
            case WHITE:
                return 0;
            default:
                return 0;
        }
    }
}
