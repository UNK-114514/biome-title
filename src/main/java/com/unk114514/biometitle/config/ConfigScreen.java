package com.unk114514.biometitle.config;

import com.unk114514.biometitle.helper.ColorHelper;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Optional;

import static com.unk114514.biometitle.BiomeTitle.manager;

public class ConfigScreen {
    public static Screen getConfigScreen(Screen parent) {
        BiomeTitleConfig config = AutoConfig.getConfigHolder(BiomeTitleConfig.class).getConfig();

        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Biome Title Config"))
                .setSavingRunnable(() -> {
                    manager.refresh();
                    AutoConfig.getConfigHolder(BiomeTitleConfig.class).save();
                });

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Enabled"), config.enabled)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.enabled = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show Subtitles"), config.showSubtitles)
                .setDefaultValue(true)
                .setSaveConsumer(newValue -> config.showSubtitles = newValue)
                .build());

        general.addEntry(entryBuilder.startEnumSelector(Text.literal("Color"), TitleColorEnum.class, config.color)
                .setDefaultValue(TitleColorEnum.WHITE)
                .setSaveConsumer(newValue -> config.color = newValue)
                .build());

        general.addEntry(entryBuilder.startBooleanToggle(Text.literal("Use Custom Color"), config.useCustomColor)
                .setDefaultValue(false)
                .setSaveConsumer(newValue -> config.useCustomColor = newValue)
                .build());

        general.addEntry(entryBuilder.startStrField(Text.literal("Custom Color"), config.customColor)
                .setDefaultValue("16777215")
                .setErrorSupplier(value -> {
                    try {
                        ColorHelper.getColorValue(value);
                    } catch (NumberFormatException e) {
                        return Optional.of(Text.literal("Invalid Color"));
                    }
                    return Optional.empty();
                })
                .setSaveConsumer(newValue -> config.customColor = newValue)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Check Interval Ticks"), config.checkIntervalTicks)
                .setDefaultValue(5)
                .setSaveConsumer(newValue -> config.checkIntervalTicks = newValue)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Fade In (gt)"), config.fadeIn)
                .setDefaultValue(10)
                .setSaveConsumer(newValue -> config.fadeIn = newValue)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Fade Out (gt)"), config.fadeOut)
                .setDefaultValue(10)
                .setSaveConsumer(newValue -> config.fadeOut = newValue)
                .build());

        general.addEntry(entryBuilder.startIntField(Text.literal("Stay (gt)"), config.stay)
                .setDefaultValue(60)
                .setSaveConsumer(newValue -> config.stay = newValue)
                .build());

        general.addEntry(entryBuilder.startEnumSelector(Text.literal("Subtitle Type"), SubtitleTypeEnum.class, config.subtitleType)
                .setDefaultValue(SubtitleTypeEnum.NAME)
                .setSaveConsumer(newValue -> config.subtitleType = newValue)
                .setTooltip(Text.literal("Tips Translation Key: tips.biometitle.<biome_id_namespace>.<biome_id_path>"))
                .build());

        return builder.build();
    }
}
