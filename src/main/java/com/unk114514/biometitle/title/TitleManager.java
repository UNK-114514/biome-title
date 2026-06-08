package com.unk114514.biometitle.title;

import com.unk114514.biometitle.config.BiomeTitleConfig;
import com.unk114514.biometitle.config.SubtitleTypeEnum;
import com.unk114514.biometitle.config.TitleColorEnum;
import com.unk114514.biometitle.helper.ColorHelper;
import com.unk114514.biometitle.helper.TitleHelper;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class TitleManager {
    private static boolean enabled;
    private static boolean showSubtitles;
    private static TitleColorEnum color;
    private boolean useCustomColor;
    private String customColor;
    private static int checkIntervalTicks;
    private static int fadeIn;
    private static int stay;
    private static int fadeOut;
    private SubtitleTypeEnum subtitleType;

    private int tickCounter = 0;
    private Biome lastBiome = null;

    public TitleManager() {
        refresh();
    }

    public void refresh() {
        BiomeTitleConfig config = AutoConfig.getConfigHolder(BiomeTitleConfig.class)
                .getConfig();
        enabled = config.enabled;
        showSubtitles = config.showSubtitles;
        color = config.color;
        useCustomColor = config.useCustomColor;
        customColor = config.customColor;
        checkIntervalTicks = config.checkIntervalTicks;
        fadeIn = config.fadeIn;
        fadeOut = config.fadeOut;
        stay = config.stay;
        subtitleType = config.subtitleType;
    }

    public void tick(MinecraftClient client) {
        if (!enabled) {
            return;
        }

        if (client.player == null || client.world == null) {
            return;
        }

        if (tickCounter % checkIntervalTicks != 0) {
            tickCounter++;
            return;
        }
        tickCounter++;

        BlockPos playerPos = client.player.getBlockPos();
        Biome currentBiome = getBiomeAt(playerPos, client);
        if (currentBiome == null) {
            return;
        }

        if (currentBiome != lastBiome) {
            showTitleForBiome(client, currentBiome);
            lastBiome = currentBiome;
        }
    }

    @Nullable
    private Biome getBiomeAt(BlockPos pos, MinecraftClient client) {
        if (client.world == null) {
            return null;
        }
        return client.world.getBiome(pos).value();
    }

    @Nullable
    private Registry<Biome> getBiomeRegistry(MinecraftClient client) {
        if (client.world == null) {
            return null;
        }
        return client.world.getRegistryManager().getOrThrow(RegistryKeys.BIOME);
    }

    private void showTitleForBiome(MinecraftClient client, Biome biome) {
        Registry<Biome> registry = getBiomeRegistry(client);
        if (registry == null) {
            return;
        }

        String mainTitle = getLocalizedBiomeName(biome, registry);
        String subTitle = getSubtitle(biome, registry);

        TitleHelper.showTitle(
                setColor(Text.literal(mainTitle)),
                showSubtitles ? setColor(Text.literal(subTitle)) : null,
                fadeIn, stay, fadeOut
        );
    }

    private String getLocalizedBiomeName(Biome biome, Registry<Biome> registry) {
        Identifier biomeId = registry.getId(biome);
        if (biomeId == null) {
            return "Unknown";
        }
        return Text.translatable(biomeId.toTranslationKey("biome")).getString();
    }

    private String getFormattedBiomePath(Biome biome, Registry<Biome> registry) {
        Identifier biomeId = registry.getId(biome);
        if (biomeId == null) {
            return "Unknown";
        }
        return capitalizeWords(biomeId.getPath());
    }

    private static String capitalizeWords(@Nullable String input) {
        if (input == null || input.isEmpty()) {
            return input == null ? "" : input;
        }

        String[] words = input.split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.isEmpty()) continue;
            String capitalized = word.substring(0, 1).toUpperCase(Locale.ROOT)
                    + word.substring(1).toLowerCase(Locale.ROOT);
            if (!result.isEmpty()) result.append(' ');
            result.append(capitalized);
        }
        return result.toString();
    }

    private MutableText setColor(MutableText text) {
        if (!useCustomColor) {
            return text.styled(style -> style.withColor(color.getColorValue()));
        }
        return text.styled(style -> style.withColor(ColorHelper.getColorValue(customColor)));
    }

    private String getSubtitle(Biome biome, Registry<Biome> biomeRegistry) {
        if (subtitleType == SubtitleTypeEnum.ID) {
            Identifier id = biomeRegistry.getId(biome);
            if (id != null) {
                return id.toString();
            }
        } else if (subtitleType == SubtitleTypeEnum.NAME) {
            return getFormattedBiomePath(biome, biomeRegistry);
        } else if (subtitleType == SubtitleTypeEnum.TIPS) {
            Identifier id = biomeRegistry.getId(biome);
            if (id != null) {
                return Text.translatable("tips.biometitle.%s"
                        .formatted(id.toString().replace(":", "."))).getString();
            }
        }
        return "Unknown";
    }
}