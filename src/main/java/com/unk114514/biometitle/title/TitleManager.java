package com.unk114514.biometitle.title;

import com.unk114514.biometitle.config.BiomeTitleConfig;
import com.unk114514.biometitle.config.ColorTypes;
import com.unk114514.biometitle.config.SubtitleTypes;
import com.unk114514.biometitle.config.TitleColors;
import com.unk114514.biometitle.helper.ColorHelper;
import com.unk114514.biometitle.helper.CustomColorHelper;
import com.unk114514.biometitle.helper.TitleHelper;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.unk114514.biometitle.helper.TextStyleHelper.setShadow;
import static com.unk114514.biometitle.helper.TextStyleHelper.withColor;

public class TitleManager {
    private final Random random;

    private static boolean enabled;
    private static boolean showSubtitles;
    private static boolean enableShadows;
    private static TitleColors color;
    private static ColorTypes colorType;
    private static String customColor;
    private static int checkIntervalTicks;
    private static int displayCooldown;
    private static int fadeIn;
    private static int stay;
    private static int fadeOut;
    private static SubtitleTypes subtitleType;
    private static Map<String, String> colors;

    private int tickCounter = 0;
    private int cooldownCounter = 0;
    private Biome lastBiome = null;

    public TitleManager(Random random) {
        this.random = random;
        refresh();
    }

    public void refresh() {
        BiomeTitleConfig config = AutoConfig.getConfigHolder(BiomeTitleConfig.class)
                .getConfig();
        enabled = config.enabled;
        showSubtitles = config.showSubtitles;
        enableShadows = config.enableShadows;
        color = config.color;
        colorType = config.colorType;
        customColor = config.customColor;
        checkIntervalTicks = config.checkIntervalTicks;
        displayCooldown = config.displayCooldown;
        fadeIn = config.fadeIn;
        fadeOut = config.fadeOut;
        stay = config.stay;
        subtitleType = config.subtitleType;
        colors = config.titleColors;
    }

    public void tick(MinecraftClient client) {
        if (!enabled) {
            return;
        }

        if (client.player == null || client.world == null) {
            return;
        }

        tickCounter++;
        cooldownCounter--;

        if (tickCounter % checkIntervalTicks != 0) {
            return;
        }

        BlockPos playerPos = client.player.getBlockPos();
        Biome currentBiome = getBiomeAt(playerPos, client);
        if (currentBiome == null) {
            return;
        }

        if (currentBiome != lastBiome && cooldownCounter <= 0) {
            showTitleForBiome(client, currentBiome);
            lastBiome = currentBiome;
            cooldownCounter = displayCooldown;
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

        int color = getColor(registry.getId(biome));

        TitleHelper.showTitle(
                setShadow(withColor(Text.literal(mainTitle), color), enableShadows),
                showSubtitles ? setShadow(withColor(Text.literal(subTitle), color), enableShadows) : null,
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

    private int getColor(Identifier biomeId) {
        if (colorType == ColorTypes.PRESET) {
            return color.getColorValue();
        } else if (colorType == ColorTypes.CUSTOM) {
            return ColorHelper.tryParse(customColor);
        } else if (colorType == ColorTypes.FROM_CONFIG) {
            return CustomColorHelper.getColor(colors, biomeId);
        } else if (colorType == ColorTypes.RANDOM) {
            return random.nextInt(0, 0xFFFFFF + 1);
        }
        return 16777215;
    }

    private String getSubtitle(Biome biome, Registry<Biome> biomeRegistry) {
        if (subtitleType == SubtitleTypes.ID) {
            Identifier id = biomeRegistry.getId(biome);
            if (id != null) {
                return id.toString();
            }
        } else if (subtitleType == SubtitleTypes.NAME) {
            return getFormattedBiomePath(biome, biomeRegistry);
        } else if (subtitleType == SubtitleTypes.TIPS) {
            Identifier id = biomeRegistry.getId(biome);
            if (id != null) {
                return Text.translatable("tips.biometitle.%s"
                        .formatted(id.toString().replace(":", "."))).getString();
            }
        }
        return "Unknown";
    }
}