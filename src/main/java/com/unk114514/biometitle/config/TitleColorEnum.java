package com.unk114514.biometitle.config;

public enum TitleColorEnum {
    BLACK(0),
    DARK_BLUE(170),
    DARK_GREEN(43520),
    DARK_AQUA(43690),
    DARK_RED(11141120),
    DARK_PURPLE(11141290),
    GOLD(16755200),
    GRAY(11184810),
    DARK_GRAY(5592405),
    BLUE(5592575),
    GREEN(5635925),
    AQUA(5636095),
    RED(16733525),
    LIGHT_PURPLE(16733695),
    YELLOW(16777045),
    WHITE(16777215);

    private final int colorValue;

    TitleColorEnum(int colorValue) {
        this.colorValue = colorValue;
    }

    public int getColorValue() {
        return colorValue;
    }
}
