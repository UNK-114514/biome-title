package com.unk114514.biometitle.config;

import com.unk114514.biometitle.helper.ColorHelper;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "biome_title")
public class BiomeTitleConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip()
    public boolean enabled = true;

    @ConfigEntry.Gui.Tooltip()
    public boolean showSubtitles = true;

    @ConfigEntry.Gui.EnumHandler()
    public TitleColors color = TitleColors.WHITE;

    @ConfigEntry.Gui.EnumHandler()
    public ColorTypes colorType = ColorTypes.PRESET;

    @ConfigEntry.Gui.Tooltip()
    public String customColor = "16777215";

    @ConfigEntry.Gui.Tooltip()
    public int fadeIn = 10;

    @ConfigEntry.Gui.Tooltip()
    public int stay = 60;

    @ConfigEntry.Gui.Tooltip()
    public int fadeOut = 10;

    @ConfigEntry.Gui.Tooltip()
    public int checkIntervalTicks = 5;

    @ConfigEntry.Gui.EnumHandler()
    public SubtitleTypes subtitleType = SubtitleTypes.NAME;

    @Override
    public void validatePostLoad() throws ValidationException {
        try {
            ColorHelper.getColorValue(customColor);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid Color");
        }
    }
}
