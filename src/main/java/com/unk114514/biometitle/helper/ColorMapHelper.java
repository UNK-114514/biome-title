package com.unk114514.biometitle.helper;

import net.minecraft.util.Identifier;

import java.util.Map;

public class ColorMapHelper {
    public static int getColor(Map<String, String> colorMap, Identifier biomeId) {
        if (colorMap.containsKey(biomeId.toString())) {
            return ColorHelper.tryParse(colorMap.get(biomeId.toString()));
        }
        return 16777215;
    }
}
