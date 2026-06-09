package com.unk114514.biometitle.helper;

import net.minecraft.util.Identifier;

import java.util.Map;

import static com.unk114514.biometitle.BiomeTitle.customBiomeTitleColorConfigManager;

public class CustomColorHelper {
    public static int getColor(Identifier biomeId) {
        Map<String, String> config = customBiomeTitleColorConfigManager.getConfig().config;
        if (config.containsKey(biomeId.toString())) {
            return ColorHelper.getColorValue(config.get(biomeId.toString()));
        }
        return 16777215;
    }
}
