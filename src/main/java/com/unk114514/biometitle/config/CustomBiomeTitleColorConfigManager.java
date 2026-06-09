package com.unk114514.biometitle.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import static com.unk114514.biometitle.BiomeTitle.LOGGER;

public class CustomBiomeTitleColorConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("custom_biome_title_color_config.json");

    private CustomBiomeTitleColorConfig config;

    public CustomBiomeTitleColorConfigManager() {
        load();
    }

    public void load() {
        try {
           if (CONFIG_PATH.toFile().exists()) {
               FileReader reader = new FileReader(CONFIG_PATH.toFile());
               this.config = GSON.fromJson(reader, CustomBiomeTitleColorConfig.class);
           } else {
               this.config = new CustomBiomeTitleColorConfig();
               save();
           }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public void save() {
        try (FileWriter writer = new FileWriter(CONFIG_PATH.toFile())) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    public CustomBiomeTitleColorConfig getConfig() {
        if (this.config == null) {
            load();
        }
        return this.config;
    }
}
