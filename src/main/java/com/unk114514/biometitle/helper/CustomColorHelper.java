package com.unk114514.biometitle.helper;

import net.minecraft.util.Identifier;

import java.util.Map;

public class CustomColorHelper {
    public static int getColor(Map<String, String> colors, Identifier biomeId) {
        if (colors.containsKey(biomeId.toString())) {
            return ColorHelper.tryParse(colors.get(biomeId.toString()));
        }
        return 16777215;
    }
}