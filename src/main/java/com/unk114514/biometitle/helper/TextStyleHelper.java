package com.unk114514.biometitle.helper;

import net.minecraft.text.MutableText;

public class TextStyleHelper {
    public static MutableText withColor(MutableText text, int color) {
        return text.styled(style -> style.withColor(color));
    }

    public static MutableText setShadow(MutableText text, boolean showShadow) {
        return showShadow ? text : text.withoutShadow();
    }
}
