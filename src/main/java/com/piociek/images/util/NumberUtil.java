package com.piociek.images.util;

public abstract class NumberUtil {
    public static String fillWithZeros(int number, int fillUpToNumber) {
        if (number > fillUpToNumber) {
            return String.valueOf(number);
        }
        StringBuilder fill = new StringBuilder();

        int rest = fillUpToNumber / 10;
        int expected = number / 10;

        while (rest != expected) {
            fill.append("0");
            rest = rest / 10;
        }

        return fill.toString() + number;
    }
}
