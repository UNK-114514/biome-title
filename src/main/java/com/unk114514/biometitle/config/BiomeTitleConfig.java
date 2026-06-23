package com.unk114514.biometitle.config;

import com.unk114514.biometitle.helper.ColorHelper;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.HashMap;
import java.util.Map;

@Config(name = "biome_title")
public class BiomeTitleConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;

    @ConfigEntry.Gui.Tooltip
    public boolean showSubtitles = true;

    @ConfigEntry.Gui.Tooltip
    public boolean enableShadows = true;

    @ConfigEntry.Gui.EnumHandler
    public TitleColors color = TitleColors.WHITE;

    @ConfigEntry.Gui.EnumHandler
    public ColorTypes colorType = ColorTypes.PRESET;

    @ConfigEntry.Gui.Tooltip
    public String customColor = "16777215";

    @ConfigEntry.Gui.Tooltip
    public int displayCooldown = 20;

    @ConfigEntry.Gui.Tooltip
    public int checkIntervalTicks = 5;

    @ConfigEntry.Gui.Tooltip
    public int fadeIn = 10;

    @ConfigEntry.Gui.Tooltip
    public int stay = 60;

    @ConfigEntry.Gui.Tooltip
    public int fadeOut = 10;

    @ConfigEntry.Gui.EnumHandler
    public SubtitleTypes subtitleType = SubtitleTypes.NAME;

    @ConfigEntry.Gui.CollapsibleObject
    public Map<String, String> titleColors = new HashMap<>();

    @Override
    public void validatePostLoad() throws ValidationException {
        try {
            ColorHelper.tryParse(customColor);
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid Color");
        }

        if (titleColors == null) {
            titleColors = new HashMap<>();
        }

        if (titleColors.isEmpty()) {
            titleColors.putAll(DefaultTitleColor.colors);
        }
    }
}
