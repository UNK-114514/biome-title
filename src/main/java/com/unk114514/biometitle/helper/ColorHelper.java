package com.unk114514.biometitle.helper;

public class ColorHelper {
    public static int tryParse(String input) throws NumberFormatException {
        if (input.startsWith("0x")) {
            return Integer.parseInt(input.substring(2), 16);
        } else if (input.startsWith("#")) {
            return Integer.parseInt(input.substring(1), 16);
        }
        return Integer.parseInt(input);
    }
}
