package com.unk114514.biometitle.helper;

public class ColorHelper {
    public static int tryParse(String input) throws NumberFormatException {
        if (input.startsWith("0x")) {   // hex color
            return clampColorInt(
                    Integer.parseInt(input.substring(2), 16));
        } else if (input.startsWith("#")) {
            return clampColorInt(
                    Integer.parseInt(input.substring(1), 16));
        } else if (input.contains(",")) {   // rgb
            return parseRgb(input);
        }
        return Integer.parseInt(input);
    }

    public static int rgbToInt(int r, int g, int b) {
        return (r << 16) | (g << 8) | b;
    }

    public static int parseRgb(String input) {
        int r = clamp(Integer.parseInt(input.split(",")[0].strip()));
        int g = clamp(Integer.parseInt(input.split(",")[1].strip()));
        int b = clamp(Integer.parseInt(input.split(",")[2].strip()));
        return rgbToInt(r, g, b);
    }

    public static int clamp(int input) { // 0 <= input <= 255
        return Math.clamp(input, 0, 255);
    }

    public static int clampColorInt(int color) {    // 0 <= 1nput <= 0xFFFFFF (max)
        return Math.clamp(color, 0, 0xFFFFFF);
    }
}
