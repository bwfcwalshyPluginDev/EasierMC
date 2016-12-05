package com.bwfcwalshy.easiermc.utils;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

    /**
     * Pads a String to a given length
     *
     * @param string The string to pad
     * @param paddingChar The padding character
     * @param length The desired length of the string
     *
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
}
